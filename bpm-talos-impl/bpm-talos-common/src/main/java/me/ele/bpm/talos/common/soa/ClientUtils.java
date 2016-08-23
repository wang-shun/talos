package me.ele.bpm.talos.common.soa;

import me.ele.contract.client.ClientUtil;

/**
 * Client相关工具<p>
 * 
 * 配合@PostConstruct初始化实例
 * 
 * @author jianming.zhou
 *
 */
public class ClientUtils {
	
	public static <T> T getClient(Class<T> clazz) {
		return ClientUtil.getContext().getClient(clazz);
	}
	
	/**
	 * init client
	 */
	public static void initClients() {
		ClientUtil.getContext().initClients("conf/Configure.json");
	}
}