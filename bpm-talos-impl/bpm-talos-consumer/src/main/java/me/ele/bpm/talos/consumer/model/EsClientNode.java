package me.ele.bpm.talos.consumer.model;

/**
 * ES Client配置节点
 * <p>单个Client配置
 * @author jianming.zhou
 *
 */
public class EsClientNode {
	
	private String host;
	private int port;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	@Override
	public String toString() {
		return "EsClientNode [host=" + host + ", port=" + port + "]";
	}
	
}
