package me.ele.bpm.talos.search.service;

import me.ele.bpm.talos.search.model.SearchOrderModel;
import org.springframework.stereotype.Service;

/**
 * Created by yemengying on 15/11/5.
 */
public interface ISearchOrder4Pandora {

	/**
	 * 本来为pandora开发 现在为javis调用
	 * @param search
	 * @return
	 * @throws IllegalAccessException
	 */
    public String searchOrder(SearchOrderModel search) throws IllegalAccessException;
    
	/**
	 * python调用熔断需要
	 * 
	 * @return true
	 */
	public boolean ping();
}
