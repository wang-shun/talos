package me.ele.bpm.talos.consumer.model;

import java.util.Map;

/**
 * MQ路由配置
 * @author jianming.zhou
 *
 */
public class RouterConf {
	
	/** <ServerNode, <Business, RouterNode>> */
	private Map<String, Map<String, RouterNode>> routers;

	public Map<String, Map<String, RouterNode>> getRouters() {
		return routers;
	}
	public void setRouters(Map<String, Map<String, RouterNode>> routers) {
		this.routers = routers;
	}
	
	@Override
	public String toString() {
		return "RouterConf [routers=" + routers + "]";
	}

}
