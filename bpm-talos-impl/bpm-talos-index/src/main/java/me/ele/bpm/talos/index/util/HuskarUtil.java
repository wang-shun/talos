package me.ele.bpm.talos.index.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.ele.bpm.talos.index.exception.ExceptionCode;
import me.ele.bpm.talos.index.exception.TalosException;
import me.ele.config.HuskarHandle;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;

import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.client.transport.TransportClient;

import me.ele.bpm.talos.index.loader.ClusterCenter;
import me.ele.bpm.talos.index.loader.Cluster;
import org.elasticsearch.common.settings.*;
import org.elasticsearch.common.transport.*;
import me.ele.bpm.talos.index.loader.*;
/**
 * 该项目的统一HUSKAR配置管理工具
 * 所有huskar配置都需要注册到 HuskarItems中
 */
public class HuskarUtil {

    public static final String SLOW_QUERY_THRESHOULD = "SLOW_QUERY_THRESHOULD";         // 集群配置 慢查询阈值配置

    /** 搜索机房huskar配置参数 */
    public static final String ES_IDCS = "ES_IDCS";                                     // 可用搜索机房列表
    public static final String IDCS_VERSION = "ES_IDCS_VERSION";                        // 搜索机房配置版本号

    /** 搜索目标huskar配置参数 */
    public static final String SEARCH_TEMPLATES = "SEARCH_TEMPLATES";                   // 可用搜索模板列表
    public static final String INDEX_TEMPLATES = "INDEX_TEMPLATES";                     // 可用搜索模板列表
    public static final String SEARCH_TEMPLATES_VERSION = "SEARCH_TEMPLATES_VERSION";   // 搜索模板版本号
    public static final String INDEX_TEMPLATES_VERSION = "INDEX_TEMPLATES_VERSION";     // 搜索模板版本号

    private static final ObjectMapper  mapper = new ObjectMapper();

    private static final Log log = LogFactory.getLog(HuskarUtil.class);

    /** 根据耗时检验是否为慢查询 */
    public static boolean isSlowQuery(long cost){
        if(HuskarHandle.get().getMyConfig().getLong(SLOW_QUERY_THRESHOULD, 200) < cost){
            return true;
        }
        return false;
    }

    /** 获得搜索机房列表 */
    public static List<String> getIDCNAMEList() throws TalosException {
        String idcConf = HuskarHandle.get().getMyConfig().getProperty(ES_IDCS, "[]");
        List<String> IDCNAMEList;
        try {
            IDCNAMEList = mapper.readValue(idcConf, ArrayList.class);
        }catch (IOException e){
            log.error("载入搜索机房名称列表失败[huskar: {}], 描述: [{}]", ES_IDCS, e.getMessage());
            throw new TalosException(ExceptionCode.HUSKAR_JSON_PARSE_EXCEPTION);
        }
        if (IDCNAMEList == null || IDCNAMEList.isEmpty()){
            log.error("[huskar: ES_IDCS] 可用机房名称列表为空或读取失败");
            throw new TalosException(ExceptionCode.CLUSTER_NOT_EXIST_EXCEPTION);
        }
        return IDCNAMEList;
    }

    /** 获得机房配置版本 */
    public static int getIDCListVersion(){
        return HuskarHandle.get().getMyConfig().getInt(IDCS_VERSION, 0);
    }

    /** 根据机房名称获得该机房的搜索集群配置表 */
    public static Map<String, List<String>> getEsClusters(String idcName)
            throws TalosException {
        String IDCConfString = HuskarHandle.get().getMyConfig().getProperty(idcName, "{}");

        Map<String, List<String>> esCluster = new HashMap<> ();
        Map<String,Map<String,Map<String,List<String>>>> IDCConfg;
        try {
            IDCConfg = mapper.readValue(IDCConfString, HashMap.class);
            for(String clusterName : IDCConfg.get("Clusters").keySet()){
                if(!clusterName.equals("Version")){
                    esCluster.put(clusterName,IDCConfg.get("Clusters").get(clusterName).get("IPList"));
                }

            }


        }catch (IOException e){
            log.error("载入机房集群客户端配置失败[huskar: {}], 描述: [{}]", idcName, e.getMessage());
            throw new TalosException(ExceptionCode.HUSKAR_JSON_PARSE_EXCEPTION);
        }
        if (esCluster == null || esCluster.isEmpty()){
            log.error("[huskar: ES_IDCS] 可用机房集群配置为空或读取失败");
            throw new TalosException(ExceptionCode.CLUSTER_NOT_EXIST_EXCEPTION);
        }
        return esCluster;
    }

    /** 获取clustersMap */
    public  static Map<String, Cluster> getClustersMap(String idcName)
            throws TalosException {
        String IDCConfString = HuskarHandle.get().getMyConfig().getProperty(idcName, "{}");

        Map<String, Cluster> clustersMap = new HashMap<> ();
        Map<String,Map<String,Map<String,List<String>>>> IDCConfg;
        try {
            IDCConfg = mapper.readValue(IDCConfString, HashMap.class);
            for(String clusterName : IDCConfg.get("Clusters").keySet()){
                TransportClient client = new IDC().createTransportClient(clusterName,
                        IDCConfg.get("Clusters").get(clusterName).get("IPList"));
                String enable = IDCConfg.get("Clusters").get(clusterName).get("Enable").get(0);
                int version = Integer.parseInt(IDCConfg.get("Clusters").get(clusterName).get("Version").get(0));
                Cluster cluster = new Cluster(client,enable,version);
                clustersMap.put(clusterName,cluster);

            }
        }catch (IOException e){
            log.error("载入机房集群客户端配置失败[huskar: {}], 描述: [{}]", idcName, e.getMessage());
            throw new TalosException(ExceptionCode.HUSKAR_JSON_PARSE_EXCEPTION);
        }
        if (clustersMap == null || clustersMap .isEmpty()){
            log.error("[huskar: ES_IDCS] 可用机房集群配置为空或读取失败");
            throw new TalosException(ExceptionCode.CLUSTER_NOT_EXIST_EXCEPTION);
        }
        return clustersMap ;
    }


    /** 根据机房名称获得该机房的版本 */
    public static int getIDCVersion(String idcName)
            throws TalosException {
        String IDCConfString = HuskarHandle.get().getMyConfig().getProperty(idcName, "{}");
        Integer IDCVersion;
        Map<String, Integer> IDCConfg;
        try {
            IDCConfg = mapper.readValue(IDCConfString, HashMap.class);
            IDCVersion =  IDCConfg.get("Version");
        } catch (IOException e) {
            log.error("载入机房集群客户端配置失败[huskar: {}], 描述: [{}]", idcName, e.getMessage());
            throw new TalosException(ExceptionCode.HUSKAR_JSON_PARSE_EXCEPTION);
        }
        if (IDCVersion == null) {
            log.error("[huskar: ES_IDCS] 可用机房版本配置为空或读取失败");
            throw new TalosException(ExceptionCode.CLUSTER_NOT_EXIST_EXCEPTION);
        }
        return IDCVersion;
    }



        /** 根据机房名称获得该机房的集群版本配置表*/
    public static Map<String,Integer> getVersionMap(String idcName)
                throws TalosException{
        String IDCConfString  = HuskarHandle.get().getMyConfig().getProperty(idcName, "{}");
        Map<String, Integer> VersionMap = new HashMap<> ();
        Map<String,Map<String,Map<String,List<String>>>> IDCConfg;
        try {
            IDCConfg = mapper.readValue(IDCConfString, HashMap.class);
            for(String clusterName : IDCConfg.get("Clusters").keySet()){
                    VersionMap.put(clusterName,Integer.parseInt(IDCConfg.get("Clusters").get(clusterName).get("Version").get(0)));
            }
        }catch (IOException e){
            log.error("载入机房集群客户端配置失败[huskar: {}], 描述: [{}]", idcName, e.getMessage());
            throw new TalosException(ExceptionCode.HUSKAR_JSON_PARSE_EXCEPTION);
        }
        if (VersionMap  == null || VersionMap.isEmpty()){
            log.error("[huskar: ES_IDCS] 可用机房集群版本配置为空或读取失败");
            throw new TalosException(ExceptionCode.CLUSTER_NOT_EXIST_EXCEPTION);
        }
        return VersionMap ;

    }

    /** 根据机房名称获取该机房的集群开关配置表*/
    public static Map<String,String> getEnableMap(String idcName)
            throws TalosException {
        String IDCConfString = HuskarHandle.get().getMyConfig().getProperty(idcName, "{}");
        Map<String, String> EnableMap = new HashMap<> ();
        Map<String,Map<String,Map<String,List<String>>>> IDCConfg;
        try {
            IDCConfg = mapper.readValue(IDCConfString, HashMap.class);
            for(String clusterName : IDCConfg.keySet()){
                if(!clusterName.equals("Version")){
                    EnableMap.put(clusterName,IDCConfg.get("Clusters").get(clusterName).get("Enable").get(0));
                }

            }
        }catch (IOException e){
            log.error("载入机房集群客户端配置失败[huskar: {}], 描述: [{}]", idcName, e.getMessage());
            throw new TalosException(ExceptionCode.HUSKAR_JSON_PARSE_EXCEPTION);
        }
        if (EnableMap == null || EnableMap.isEmpty()){
            log.error("[huskar: ES_IDCS] 可用机房集群开关配置为空或读取失败");
            throw new TalosException(ExceptionCode.CLUSTER_NOT_EXIST_EXCEPTION);
        }
        return EnableMap;
    }

    /** 获得搜索模板配置版本 */
    public static int getSearchTemplatesVersion(){
        return HuskarHandle.get().getMyConfig().getInt(SEARCH_TEMPLATES_VERSION, 0);
    }

    public static int getInt(String key){
        return HuskarHandle.get().getMyConfig().getInt(key, 0);
    }

    public static String getString(String key){
        return HuskarHandle.get().getMyConfig().getProperty(key, "");
    }

    public static List<String> getStringList(String key) throws TalosException {
        String listConf = HuskarHandle.get().getMyConfig().getProperty(key, "[]");
        List<String> list;
        try {
            list = mapper.readValue(listConf, ArrayList.class);
        }catch (IOException e){
            log.error("载入列表失败[huskar: {}], 描述: [{}]", key, e.getMessage());
            throw new TalosException(ExceptionCode.HUSKAR_JSON_PARSE_EXCEPTION);
        }
        if (list == null || list.isEmpty()){
            log.error("[huskar: {}] 列表为空或读取失败", key);
            throw new TalosException(ExceptionCode.CLUSTER_NOT_EXIST_EXCEPTION);
        }
        return list;
    }

    /** 获得机房的某个cluster的IPlist*/
    public static  List<String> getIPList(String idcName,String clusterName)
            throws TalosException {
        String IDCConfString = HuskarHandle.get().getMyConfig().getProperty(idcName, "{}");

        List<String> IPList;
        Map<String,Map<String,Map<String,List<String>>>> IDCConfg;
        try {
            IDCConfg = mapper.readValue(IDCConfString, HashMap.class);
            IPList = IDCConfg.get("Clusters").get(clusterName).get("IPList");

        }catch (IOException e){
            log.error("载入机房集群客户端IPList失败[huskar: {}], 描述: [{}]", idcName, e.getMessage());
            throw new TalosException(ExceptionCode.HUSKAR_JSON_PARSE_EXCEPTION);
        }
        if (IPList == null || IPList.isEmpty()){
            log.error("[huskar: ES_IDCS] 可用机房集群配置为空或读取失败");
            throw new TalosException(ExceptionCode.CLUSTER_NOT_EXIST_EXCEPTION);
        }
        return IPList;
    }

    /** 获得机房的某个cluster的开关*/
    public static  String getEnable(String idcName,String clusterName)
            throws TalosException {
        String IDCConfString = HuskarHandle.get().getMyConfig().getProperty(idcName, "{}");

        String enable;
        Map<String,Map<String,Map<String,List<String>>>> IDCConfg;
        try {
            IDCConfg = mapper.readValue(IDCConfString, HashMap.class);
            enable = IDCConfg.get("Clusters").get(clusterName).get("Enable").get(0);

        }catch (IOException e){
            log.error("载入机房集群客户端Enable失败[huskar: {}], 描述: [{}]", idcName, e.getMessage());
            throw new TalosException(ExceptionCode.HUSKAR_JSON_PARSE_EXCEPTION);
        }
        if (enable == null){
            log.error("[huskar: ES_IDCS] 可用机房集群配置为空或读取失败");
            throw new TalosException(ExceptionCode.CLUSTER_NOT_EXIST_EXCEPTION);
        }
        return enable;
    }

}
