package me.ele.bpm.talos.search.service;

import me.ele.bpm.talos.search.model.SearchActivities4AadminModel;
import me.ele.bpm.talos.search.model.SearchResult;
import me.ele.bpm.talos.search.model.SearchResultNew;

/**
 * Created by yemengying on 15/11/26.
 */
public interface ISearchActivityService {

    /**
     * 为family系统提供的业务方查询活动接口
     * @param search
     * @return
     * @throws IllegalAccessException
     */
    public SearchResult searchActivity4Admin(SearchActivities4AadminModel search);

    /**
     * 为family系统提供的业务方查询活动接口 新的
     * @param search
     * @return
     * @throws IllegalAccessException
     */
    public SearchResultNew searchActivity4AdminNew(SearchActivities4AadminModel search);
}
