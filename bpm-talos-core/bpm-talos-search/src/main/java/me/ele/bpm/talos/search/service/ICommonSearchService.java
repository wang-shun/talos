package me.ele.bpm.talos.search.service;

import me.ele.bpm.talos.index.exception.TalosException;
import me.ele.bpm.talos.search.model.SearchResult;

import java.util.Map;

/**
 * Created by yemengying on 15/11/27.
 */
public interface ICommonSearchService {

    /**
     * 通用搜索接口
     * @param templateCode 模板id
     * @param params 参数映射表
     * @return SearchResult
     * @throws TalosException
     * */
    public SearchResult search(String templateCode, Map<String, Object> params) throws TalosException;

    /**
     * 通用搜索调用，返回字符串
     * */
    public String searchString(String templateCode, Map<String, Object> params) throws TalosException;

    public boolean ping();
}
