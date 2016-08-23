package me.ele.bpm.talos.index.loader;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.ele.bpm.talos.index.exception.ExceptionCode;
import me.ele.bpm.talos.index.exception.TalosException;
import me.ele.bpm.talos.index.util.HuskarUtil;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import java.io.IOException;


import me.ele.config.HuskarHandle;

import org.codehaus.jackson.map.ObjectMapper;



/**
 * Created by lupeidong on 16/6/24.
 */
/** 机房信息操作类 */
public class IDC {

    Log log = LogFactory.getLog(ClusterCenter.class);
    private String IDCName;

    private static int STABLESCOPE = 30;

    /** 机房稳定计数器 */
    private int successCount = STABLESCOPE;
    private int failCount = 0;

    /** 机房版本 */
    private int IDCVersion;

    /** 机房集群客户端映射表 */
    private Map<String, Cluster> clustersMap = new HashMap<>();

    private static final ObjectMapper  mapper = new ObjectMapper();


    public IDC(String IDCName, Map<String,Cluster>clustersMap, int IDCVersion){
        this.IDCName = IDCName;
        this.clustersMap = clustersMap;
        this.IDCVersion = IDCVersion;

    }

    public IDC(){

    }

    /**
     * 创建搜索引擎集群的elasticsearch客户端
     * @param clusterName 搜索引擎集群名称
     * @param ipList 搜索引擎集群的ip列表
     * @return 搜索引擎集群的client句柄
     * @throws TalosException 当ipList配置不规范无法解析时抛出
     */
    public  TransportClient createTransportClient(
            String clusterName, List<String> ipList) throws TalosException {
        if(ipList == null || ipList.isEmpty()){
            log.error("读取huskar配置时，" + clusterName + "集群 配置的集群连接地址为空");
            throw new TalosException(ExceptionCode.HUSKAR_CONFIG_EXCEPTION);
        }
        Settings settings = ImmutableSettings
                .settingsBuilder()
                .put("client.transport.sniff", true)
                .put("cluster.name", clusterName).build();
        TransportClient client = new TransportClient(settings);
        int index;
        for (String ipWithPort : ipList){
            index = ipWithPort.lastIndexOf(':');
            if (index == -1){
                log.error("读取huskar配置时，" + clusterName +
                        " 中配置的ip地址 [" + ipWithPort + "]无法解析");
                throw new TalosException(ExceptionCode.HUSKAR_CONFIG_EXCEPTION);
            }
            InetSocketTransportAddress address =  new InetSocketTransportAddress(
                    ipWithPort.substring(0, index),
                    Integer.parseInt(ipWithPort.substring(index + 1)));
            client.addTransportAddress(address);
        }
        return client;
    }

    /** 更新机房稳定计数 */
    public synchronized void updateScope(int stableDelta){
        successCount += stableDelta;
        failCount -= stableDelta;
        if (successCount > STABLESCOPE){
            successCount = STABLESCOPE;
            failCount = 0;
        }else if(failCount > STABLESCOPE) {
            failCount = STABLESCOPE;
            successCount = 0;
        }
    }

    /** 判断该机房是否稳定 */
    public boolean isStable(){
        int index = (int)(Math.random()* STABLESCOPE);
        return index < successCount;
    }

    /** 获得客户端 */
    public TransportClient getClient(String clusterName) throws TalosException {
        TransportClient client = clustersMap.get(clusterName).getClient();
        if(client == null){
            log.error("集群 [{}] 不存在,请检查对应huskar配置", clusterName);
            throw new TalosException(ExceptionCode.CLUSTER_NOT_EXIST_EXCEPTION);
        }
        return client;
    }


    public int getSuccessCount() {
        return successCount;
    }


    public int getFailCount() {
        return failCount;
    }

    public String getIDCName() {
        return IDCName;
    }

    public int getIDCVersion() {
        return IDCVersion;
    }


    public Map<String, Cluster> getClustersMap() {
        return this.clustersMap;
    }

    public void setIDCVersion(Integer version) {
        this.IDCVersion = version;
    }

    public void updateIDC(int IDCVersion) throws TalosException {

        /** 根据版本判断是否需要更新cluster */
        Map<String,Cluster> newclustersMap = new HashMap<>();
        String clusterConf = HuskarHandle.get().getMyConfig().getProperty(this.IDCName, "{}");
        Map<String,Map<String,Map<String,List<String>>>> IDCConfg;
        try {
            IDCConfg = mapper.readValue(clusterConf, HashMap.class);
            for(String clusterName : IDCConfg.get("Clusters").keySet()){
                int version = Integer.parseInt(IDCConfg.get("Clusters").get(clusterName).get("Version").get(0));
                if(!this.clustersMap.containsKey(clusterName)
                        || this.clustersMap.get(clusterName).getVersion()!=version){
                    List<String> IPlist = HuskarUtil.getIPList(this.IDCName,clusterName);
                    String enable = HuskarUtil.getEnable(this.IDCName,clusterName);
                    TransportClient client = createTransportClient(clusterName,IPlist);

                    newclustersMap.put(clusterName,new Cluster(client,
                            enable,version));
                }else{
                    newclustersMap.put(clusterName,clustersMap.get(clusterName));
                }
            }
        }catch (IOException e){
            log.error("载入机房集群客户端配置失败[huskar: {}], 描述: [{}]", IDCName, e.getMessage());
            throw new TalosException(ExceptionCode.HUSKAR_JSON_PARSE_EXCEPTION);
        }
        this.clustersMap = newclustersMap;
        setIDCVersion(IDCVersion);
    }

}
