package me.ele.bpm.talos.consumer.sequence;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import me.ele.arch.etrace.agent.Trace;
import me.ele.bpm.talos.consumer.elistener.MQEvent;
import me.ele.bpm.talos.index.document.Document;
import me.ele.bpm.talos.index.document.IIndexDocument;
import me.ele.bpm.talos.index.document.MQDocument;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * 整序模块
 * @author jianming.zhou
 *
 */
public class IndexSequence extends Thread {

	private Log log = LogFactory.getLog(IndexSequence.class);
	
	@Autowired
	private ApplicationContext context;
	
	/** 线程池 */
	private ExecutorService pool;
	/** 线程池大小 */
	private int nThreads = 10;
	/** 时间片段 */
	private long timeTip = 100;
	/** 消息丢失等待周期 */
	private int maxCycle = 4;

	/** 消息缓存 */
	private static Map<String, SequenceModel> seqMap;
	
	@PostConstruct
	public void init() {
		pool = Executors.newFixedThreadPool(nThreads);
		seqMap = new HashMap<String, SequenceModel>();
	}

	public void setnThreads(int nThreads) {
		this.nThreads = nThreads;
	}
	public void setTimeTip(long timeTip) {
		this.timeTip = timeTip;
	}
	public void setMaxCycle(int maxCycle) {
		this.maxCycle = maxCycle;
	}

	/**
	 * 将消息加入整序缓存中
	 * @param key business_dataType
	 * @param document
	 * @return
	 */
	public void pushDocument(String key, Document document) {
		log.debug("pushDocument, version:{}", document.getVersion());
		if (null == seqMap.get(key)) {
			SequenceModel seqModel = new SequenceModel();
			seqModel.getDocuments().add(document);
			seqModel.setVersion(document.getVersion() - 1);
			seqMap.put(key, seqModel);
		} else {
			SequenceModel seqModel = seqMap.get(key);
			List<MQDocument> mqDocuments = seqModel.getDocuments();
			if (!mqDocuments.isEmpty()) {
				MQDocument last = mqDocuments.get(mqDocuments.size() - 1);
				seqModel.getDocuments().add(document);
				if (document.getVersion() < last.getVersion()) {
					Collections.sort(mqDocuments);
					log.info("出现逆序，执行整序 curVersion:{} newVersion:{}", last.getVersion(), document.getVersion());
				}
			} else {
				seqModel.getDocuments().add(document);
			}
		}
		//TODO 性能调优？
	}
	
	/**
	 * 整序处理
	 */
	@Override
	public void run() {
		pool = Executors.newFixedThreadPool(nThreads);
		while (true) {
			for (String key : seqMap.keySet()) {
				SequenceModel seqModel = seqMap.get(key);
				if (!seqModel.getDocuments().isEmpty()) {
					pool.execute(new SequenceThread(key, seqModel));
				}
			}
			sleep();
		}
	}
	
	/**
	 * 单类型整序Thread
	 * @param key
	 * @param seqModel business_dataType单一类型
	 */
	
	private class SequenceThread implements Runnable {
		
		/** business_dataType */
		private String key;
		private SequenceModel seqModel;
		
		public SequenceThread(String key, SequenceModel seqModel) {
			this.key = key;
			this.seqModel = seqModel;
		}
		
		@Override
		public void run() {
			try {
				List<MQDocument> documents = seqModel.getDocuments();
				log.debug("current version:{}", seqModel.getVersion());
				
				List<IIndexDocument> indexDocuments = new LinkedList<IIndexDocument>();
				int index = 0;
				while (index < documents.size()) {
					if (seqModel.getVersion() + 1 == documents.get(index).getVersion()) {
						MQDocument mqDocument = documents.remove(index);
						Document document = (Document) mqDocument;
						indexDocuments.add(document);
						seqModel.setVersion(document.getVersion());
						seqModel.setCycle(0);
						log.debug("正常消息 index:{}, type:{}, version:{}", 
								document.getIndex(), document.getType(), document.getVersion());
					} else if (seqModel.getCycle() >= maxCycle ) {
						MQDocument mqDocument = documents.remove(index);
						Document document = (Document) mqDocument;
						indexDocuments.add(document);
						log.warn("消息出现丢失! index:{}, type:{}, lost version:[{},{})", 
								document.getIndex(), document.getType(), seqModel.getVersion() + 1, document.getVersion());
						seqModel.setVersion(document.getVersion());
						seqModel.setCycle(0);
					} else if (seqModel.getVersion() > documents.get(index).getVersion()) {
						MQDocument mqDocument = documents.remove(index);
						Document document = (Document) mqDocument;
						indexDocuments.add(document);
						log.warn("消息出现逆序! index:{}, type:{}, curVersion:{}, newVersion:{}", 
								document.getIndex(), document.getType(), seqModel.getVersion(), document.getVersion());
						seqModel.setVersion(document.getVersion());
						seqModel.setCycle(0);
					} else if (seqModel.getVersion() == documents.get(index).getVersion()) {
						MQDocument mqDocument = documents.remove(index);
						Document document = (Document) mqDocument;
						log.warn("消息出现重复! index:{}, type:{}, version:{}", 
								document.getIndex(), document.getType(), document.getVersion());
					} else {
						seqModel.setCycle(seqModel.getCycle() + 1);
						log.debug("setCycle:{}", seqModel.getCycle() + 1);
						break;
					}
				}
				if (!indexDocuments.isEmpty()) {
					context.publishEvent(new MQEvent(key, indexDocuments));
					log.debug("publishEvent indexDocuments:{}", indexDocuments);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				Trace.logError(e.getMessage(), e);
			}
		}
	}

	private void sleep() {
		try {
			Thread.sleep(timeTip);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
	}
	
}