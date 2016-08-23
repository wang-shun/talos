package me.ele.bpm.talos.index.loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.ele.bpm.talos.index.exception.ExceptionCode;
import me.ele.bpm.talos.index.exception.TalosException;
import me.ele.bpm.talos.index.util.HuskarUtil;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.collect.HppcMaps;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;



/** 集群控制中心
 * 负责集群客户端获取，热加载，动态调整稳定链路
 * */
public class ClusterCenter {

    private IDCWrapper IDC;                                         // 机房信息
    private long IDCListVersion = -1;                               // 机房配置版本号（热加载标识）

    private ThreadLocal<IDC> currentIDC = new ThreadLocal<>();      // 当前线程使用的机房名称
    private static int STABLESCOPE = 30;                            // 稳定链路判断基准

    Log log = LogFactory.getLog(ClusterCenter.class);

    private List<String> IDCList;
    private Map<String, IDC> IDCMap;        //各个机房的集群客户端
    private HashMap<String,ArrayList<IDC>> clusterIDCMap;                //cluster 分布在哪些IDC上

    public ClusterCenter(){
        this.IDCList = new ArrayList<>();
        this.IDCMap = new HashMap<>();
        this.clusterIDCMap = new HashMap<>();
    }

    /**
     * 加载 搜索引擎 客户端。
     * 当 客户端配置版本 IDCVERSION 不一致时调用。
     * 因此当动态更新客户端配置将会导致多线程同时调用问题
     * */

    public synchronized void loadClients() throws TalosException {
        int currentIDCVersion = HuskarUtil.getIDCListVersion();
        if(IDCListVersion == currentIDCVersion){
            return;
        }
        List<String> newIDCList = HuskarUtil.getIDCNAMEList();     // 提取可用机房列名称表配置

        for (String IDCName: newIDCList){
            int IDCVersion = HuskarUtil.getIDCVersion(IDCName);    // 获取这个IDC的VERSION

            if(!IDCList.contains(IDCName)){                        // 如果时新增的IDC

                Map<String, Cluster> clustersMap = HuskarUtil.getClustersMap(IDCName);
                IDC idc = new IDC(IDCName,clustersMap,IDCVersion);
                IDCMap.put(IDCName,idc);
                /** 更新 clusterIDCMap */
                updateClusterIDCMap(idc);

            }else {                                                 //不是新增的IDC
                IDC idc = IDCMap.get(IDCName);
                if(idc.getIDCVersion() != IDCVersion) {             // 是需要更新的IDC

                    idc.updateIDC(IDCVersion);           //根据versionMap来更新里面的cluster
                    /** 更新 clusterIDCMap */
                    updateClusterIDCMap(idc);
                }

            }
        }

        for(String IDCName : IDCList) {
            if(!newIDCList.contains(IDCName)) {

                IDC idcToRemove = IDCMap.get(IDCName);
                for(String clusterName:clusterIDCMap.keySet()){
                    if(clusterIDCMap.get(clusterName).contains(idcToRemove)){
                        clusterIDCMap.get(clusterName).remove(idcToRemove);
                    }
                }
                //在IDCMAP中删除不要的IDC
                IDCMap.remove(IDCName);
            }
        }

        IDCList = newIDCList;                                   // 更新机房列表
        IDC = new IDCWrapper(IDCList, IDCMap,clusterIDCMap);
        IDCListVersion = currentIDCVersion;                     // 更新集群机房配置版本号码
    }

    /** 更新clusterIDCMap */
    public void updateClusterIDCMap(IDC idc){
        for(String clusterName : idc.getClustersMap().keySet()) {
            if(idc.getClustersMap().get(clusterName).getEnable().equals("true")){

                if(!clusterIDCMap.containsKey(clusterName)) {
                    clusterIDCMap.put(clusterName,new ArrayList<>());
                }

                if(!clusterIDCMap.get(clusterName).contains(idc)) {
                    clusterIDCMap.get(clusterName).add(idc);
                }
            }else {
                if(clusterIDCMap.containsKey(clusterName) && clusterIDCMap.get(clusterName).contains(idc)) {
                    clusterIDCMap.get(clusterName).remove(idc);
                }
            }
        }
    }

    /**
     * 随机获得一个稳定的客户端
     * @param clusterName 集群名称
     * @return 搜索客户端
     * @throws TalosException
     * */
    public TransportClient getStableClient(
            String clusterName)
            throws TalosException {

        int huskarVersion = HuskarUtil.getIDCListVersion();

        log.debug("getStableClient: forCluster[{}], huskarVersion[{}], curVersion[{}]",
                clusterName, huskarVersion, IDCListVersion);

        if (huskarVersion == -1) {
            log.warn("huskar connection fail, [IDCListVersion == -1]");
        } else if (huskarVersion != IDCListVersion) {
            log.debug("!!!!!!!!!!load client!!!!!!!!!");
            loadClients();               // 机房配置版本不一致则重新加载
        }

        IDCWrapper IDC = this.IDC;      // 获得机房信息（该赋值保证线程安全）

        Map<String,ArrayList<IDC>> clusterIDCMap = IDC.getClusterIDCMap();
        ArrayList<IDC> clusterIDCList = clusterIDCMap.get(clusterName);
        int index = (int)(Math.random()*clusterIDCList.size());
        log.debug("随机值: {}", index);
        IDC idc;
        int size = 0;
        do {
            idc = clusterIDCList.get(index);
            index = (index+1)%clusterIDCList.size();
        }while (!idc.isStable() && ++size < clusterIDCList.size());
        currentIDC.set(idc);
        log.debug("机房名称: "+idc.getIDCName() +" successCount: "+ idc.getSuccessCount());
        return idc.getClient(clusterName);
    }


    /** 返回当前线程连接的机房名称 */
    public String getCurrentIDCName(){
        if(currentIDC == null)
            return "None";
        return currentIDC.get().getIDCName();
    }

    /** 客户端请求成功计数调用 */
    public void success(){
        if (currentIDC != null &&
                currentIDC.get().getSuccessCount() < STABLESCOPE){
            currentIDC.get().updateScope(1);
        }
    }

    /** 客户端请求失败计数调用 */
    public void fail(){
        if (currentIDC != null &&
                currentIDC.get().getFailCount() < STABLESCOPE){
            currentIDC.get().updateScope(-1);
        }
    }

    /** 线程安全封装 */
    private class IDCWrapper{

        private List<String> IDCList;                                       // 可用机房列表
        private Map<String, IDC> IDCMap;                                    // 机房映射表

        private Map<String,ArrayList<IDC>> clusterIDCMap;

        public IDCWrapper(List<String> IDCList, Map<String,IDC> IDCMap,Map<String,ArrayList<IDC>> clusterIDCMap) {
            this.IDCList = IDCList;
            this.IDCMap = IDCMap;
            this.clusterIDCMap = clusterIDCMap;

        }

        public List<String> getIDCList() {
            return IDCList;
        }

        public Map<String, IDC> getIDCMap() {
            return IDCMap;
        }

        public Map<String,ArrayList<IDC>> getClusterIDCMap(){
            return clusterIDCMap;
        }

    }






}
