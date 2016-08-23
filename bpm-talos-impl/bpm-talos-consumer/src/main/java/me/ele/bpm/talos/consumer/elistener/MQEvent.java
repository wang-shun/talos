package me.ele.bpm.talos.consumer.elistener;

import java.util.List;

import me.ele.bpm.talos.index.document.IIndexDocument;

import org.springframework.context.ApplicationEvent;

/**
 * 整序数据事件
 * @author jianming.zhou
 *
 */
public class MQEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;
	
	private List<IIndexDocument> indexDocuments;
	
	public MQEvent(Object source, List<IIndexDocument> indexDocuments) {
		super(source);
		this.indexDocuments = indexDocuments;
	}

	public List<IIndexDocument> getIndexDocuments() {
		return indexDocuments;
	}
	public void setIndexDocuments(List<IIndexDocument> indexDocuments) {
		this.indexDocuments = indexDocuments;
	}
	
}
