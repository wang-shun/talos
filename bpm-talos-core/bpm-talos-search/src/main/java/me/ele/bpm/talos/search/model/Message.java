package me.ele.bpm.talos.search.model;

public class Message {

	private String business; // 业务端类型
	private String dataStruct; // 数据结构
	private int action;// 动作类型
	private Object payload; // 信息详情Json数据
	
	public String getBusiness() {
		return business;
	}
	public Message setBusiness(String business) {
		this.business = business;
		return this;
	}
	public String getDataStruct() {
		return dataStruct;
	}
	public Message setDataStruct(String dataStruct) {
		this.dataStruct = dataStruct;
		return this;
	}
	public int getAction() {
		return action;
	}
	public Message setAction(int action) {
		this.action = action;
		return this;
	}
	public Object getPayload() {
		return payload;
	}
	public Message setPayload(Object payload) {
		this.payload = payload;
		return this;
	}
	
	

}
