package me.ele.bpm.talos.search.service;

import me.ele.bpm.talos.search.model.SearchOrderNaposModel;
import me.ele.bpm.talos.search.model.SearchOrderNewModel;
import me.ele.bpm.talos.search.model.SearchOrderPandoraModel;

public interface ISearchOrderService {

	/**
	 * 订单运营后台后台的调用接口(已经迁移至 common search)
	 * @param search
	 * @return
	 * @throws IllegalAccessException
	 */
	public String searchOrderNew(SearchOrderNewModel search) throws IllegalAccessException;


	/**
	 * 为pandora分离的订单接口(已经迁移至 common search)
	 * @return
	 * @throws IllegalAccessException
	 */
	public String searchOrder4Pandora(SearchOrderPandoraModel search) throws IllegalAccessException;


	/**
	 * 为napos提供的订单接口 (已经迁移至 common search)
	 * @param search
	 * @return
	 * @throws IllegalAccessException
	 */
	public String searchOrder4Napos(SearchOrderNaposModel search) throws  IllegalAccessException;



	public boolean ping();


}
