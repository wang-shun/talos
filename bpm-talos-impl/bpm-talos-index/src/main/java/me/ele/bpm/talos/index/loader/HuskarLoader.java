package me.ele.bpm.talos.index.loader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.ele.bpm.talos.common.model.Pair;
import me.ele.bpm.talos.index.exception.TalosException;
import me.ele.bpm.talos.index.util.HuskarUtil;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;

/** 集群控制中心
 * 负责集群客户端获取，热加载，动态调整稳定链路
 * */
public abstract class HuskarLoader<T> {

    private String versionKey;                  // 资源版本号配置    名称
    private String avaliableElemListKey;        // 可用资源列表配置  名称

    private Pair<List, Map> elemWrapper;
    private long version = -1;

    Log log = LogFactory.getLog(HuskarLoader.class);

    /**
     * 构造集合资源加载器
     * @param versionKey 资源版本号标识名称
     * @param avaliableElemListKey 资源列表名称
     * */
    public HuskarLoader(String versionKey, String avaliableElemListKey){
        this.versionKey = versionKey;
        this.avaliableElemListKey = avaliableElemListKey;
    }

    /**
     * 根据配置生成资源对象，需要覆写
     * @param key 资源名称
     * @param conf 资源配置
     * @return 资源对象
     * */
    public abstract T createHotLoaderElem(String key, String conf) throws TalosException;


    /**
     * 加载huskar中的集合资源配置，
     * 生成资源对象
     * */
    public synchronized void load() throws TalosException {
        int currentVersion = HuskarUtil.getInt(versionKey);
        if(version == currentVersion){
            return;
        }
        List<String> elemList = HuskarUtil.getStringList(avaliableElemListKey);     // 提取可用机房列名称表配置

        /** 获得各个机房的集群客户端 */
        Map<String, T> elemMap = new HashMap<>();
        for (String elemName: elemList){
            elemMap.put(elemName, createHotLoaderElem(elemName, HuskarUtil.getString(elemName)));
        }

        Pair<List, Map> wrapper = new Pair<>(elemList, elemMap);
        elemWrapper = wrapper;
        version = currentVersion;                     // 更新集群机房配置版本号码
    }

    /**
     * 根据资源名称活动资源
     * @param elemName 资源名称
     * @return 资源对象
     * */
    public T get(String elemName) throws TalosException {
        ensureUpdated();
        return (T)elemWrapper.second.get(elemName);
    }

    /** 获得所有资源 */
    public Pair<List, Map> getElemWrapper() throws TalosException {
        ensureUpdated();
        return elemWrapper;
    }

    /** 确保资源为最新版本 */
    private void ensureUpdated() throws TalosException {
        int huskarVersion = HuskarUtil.getInt(versionKey);
        if (huskarVersion == -1) {
            log.warn("huskar connection fail, [{} == -1]", versionKey);
        } else if (huskarVersion != version) {
            load();               // 机房配置版本不一致则重新加载
        }
    }

}
