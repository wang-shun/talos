package me.ele.bpm.talos.consumer.sequence;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import me.ele.bpm.talos.index.document.MQDocument;

/**
 * 对应一种business_dataType索引
 * @author jianming.zhou
 *
 */
public class SequenceModel {

	/** 版本号 */
	private long version;
	/** 循环次数 */
	private int cycle;
	/** 消息缓存队列 */
	private List<MQDocument> documents;
	
	public SequenceModel() {
		documents = Collections.synchronizedList(new LinkedList<MQDocument>());
	}
	
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	public int getCycle() {
		return cycle;
	}
	public void setCycle(int cycle) {
		this.cycle = cycle;
	}
	public List<MQDocument> getDocuments() {
		return documents;
	}
	public void setDocuments(List<MQDocument> documents) {
		this.documents = documents;
	}

	@Override
	public String toString() {
		return "SequenceModel [version=" + version + ", cycle=" + cycle
				+ ", documents=" + documents + "]";
	}
	
}
