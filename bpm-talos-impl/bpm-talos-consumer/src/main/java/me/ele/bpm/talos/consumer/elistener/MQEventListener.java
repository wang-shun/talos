package me.ele.bpm.talos.consumer.elistener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import me.ele.bpm.talos.consumer.indexer.Indexer;
import me.ele.bpm.talos.index.document.IIndexDocument;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * 整序数据监听
 * @author jianming.zhou
 *
 */
@Service
public class MQEventListener implements ApplicationListener<MQEvent> {
	
	private Log log = LogFactory.getLog(MQEventListener.class);
	
	/** 线程池 */
//	private ExecutorService pool;
	/** 线程池大小 */
//	private int nThreads = 10;
	
	private Map<String, ExecutorService> poolMap = new HashMap<String, ExecutorService>();
	
	@PostConstruct
	public void init() {
//		pool = Executors.newFixedThreadPool(nThreads);
	}

	@Override
	public void onApplicationEvent(MQEvent event) {
		List<IIndexDocument> indexDocuments = event.getIndexDocuments();
		log.debug("MQEvent:{}", indexDocuments.toString());
		
		String key = (String) event.getSource();
		if (!poolMap.containsKey(key)) {
			ExecutorService pool = Executors.newFixedThreadPool(1);
			poolMap.put(key, pool);
			log.info("New ExecutorService[{}]", key);
		}
		if (key.startsWith("family")) {
			poolMap.get(key).execute(new Indexer(indexDocuments, true));
		} else {
			poolMap.get(key).execute(new Indexer(indexDocuments, false));
		}
	}

}
