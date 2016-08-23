package me.ele.bpm.talos.consumer.model;

import java.util.LinkedHashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 索引步骤
 * <p>包含完整处理步骤
 * @author jianming.zhou
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcessConf {
	
	/** 索引业务名称 */
	private String business_name;
	/** 索引集群名称(真正集群ip会从huskar读取) */
	private String cluster_name;
	/** 默认index */
	private String start_index_key;
	/** 默认type */
	private String start_doctype_key;
	/** Index文档映射 */
	private String doc_mapping;
	/** 索引路由器名称 */
	private Map<String, Object> index_route;
	/** 文档路由器名称 */
	private Map<String, Object> doctype_route;
	/** 数据拼接过程 */
	private LinkedHashMap<Integer, ProcessNode> processes;
	
	public String getBusiness_name() {
		return business_name;
	}
	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}
	public String getCluster_name() {
		return cluster_name;
	}
	public void setCluster_name(String cluster_name) {
		this.cluster_name = cluster_name;
	}
	public String getStart_index_key() {
		return start_index_key;
	}
	public void setStart_index_key(String start_index_key) {
		this.start_index_key = start_index_key;
	}
	public String getStart_doctype_key() {
		return start_doctype_key;
	}
	public void setStart_doctype_key(String start_doctype_key) {
		this.start_doctype_key = start_doctype_key;
	}
	public String getDoc_mapping() {
		return doc_mapping;
	}
	public void setDoc_mapping(String doc_mapping) {
		this.doc_mapping = doc_mapping;
	}
	public Map<String, Object> getIndex_route() {
		return index_route;
	}
	public void setIndex_route(Map<String, Object> index_route) {
		this.index_route = index_route;
	}
	public Map<String, Object> getDoctype_route() {
		return doctype_route;
	}
	public void setDoctype_route(Map<String, Object> doctype_route) {
		this.doctype_route = doctype_route;
	}
	public LinkedHashMap<Integer, ProcessNode> getProcesses() {
		return processes;
	}
	public void setProcesses(LinkedHashMap<Integer, ProcessNode> processes) {
		this.processes = processes;
	}
	
	@Override
	public String toString() {
		return "ProcessConf [business_name=" + business_name
				+ ", cluster_name=" + cluster_name + ", start_index_key="
				+ start_index_key + ", start_doctype_key=" + start_doctype_key
				+ ", doc_mapping=" + doc_mapping + ", index_route="
				+ index_route + ", doctype_route=" + doctype_route
				+ ", processes=" + processes + "]";
	}
	
}
