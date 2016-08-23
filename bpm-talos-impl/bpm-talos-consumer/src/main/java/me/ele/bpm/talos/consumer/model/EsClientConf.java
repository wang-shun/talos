package me.ele.bpm.talos.consumer.model;

import java.util.List;
import java.util.Map;

/**
 * ES Client配置
 * <p>包含所有Client配置
 * @author jianming.zhou
 *
 */
public class EsClientConf {

	Map<String, List<EsClientNode>> esClientConf;

	public Map<String, List<EsClientNode>> getEsClientConf() {
		return esClientConf;
	}
	public void setEsClientConf(Map<String, List<EsClientNode>> esClientConf) {
		this.esClientConf = esClientConf;
	}

	@Override
	public String toString() {
		return "EsClientConf [esClientConf=" + esClientConf + "]";
	}

}
