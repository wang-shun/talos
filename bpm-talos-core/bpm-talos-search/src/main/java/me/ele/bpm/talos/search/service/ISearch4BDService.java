package me.ele.bpm.talos.search.service;

import me.ele.bpm.talos.search.model.SearchActivitiesModel;

/**
 * Created by yemengying on 15/11/3.
 */
public interface ISearch4BDService {

	/**
	 * 为BD开发的活动查询接口
	 * @param search
	 * @return
	 * @throws Exception
	 */
	@Deprecated
    public String searchRstActivity(SearchActivitiesModel search) throws Exception;
    
	/**
	 * python调用熔断需要
	 * 
	 * @return true
	 */
	public boolean ping();
}
