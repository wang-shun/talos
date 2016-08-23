package me.ele.bpm.talos.consumer.model;

import java.util.Map;

/***
 * 路由配置节点
 * @author jianming.zhou
 *
 */
public class RouterNode {
	
	/** 交换机名称 */
	private String exchange;
	/** 路由是否有效 */
	private boolean valid;
	/** 队列映射<数据类型，队列名称> */
	private Map<String, String> queues;
	
	public String getExchange() {
		return exchange;
	}
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public Map<String, String> getQueues() {
		return queues;
	}
	public void setQueues(Map<String, String> queues) {
		this.queues = queues;
	}
	
	@Override
	public String toString() {
		return "RouterNode [exchange=" + exchange + ", valid=" + valid
				+ ", queues=" + queues + "]";
	}
	
}
