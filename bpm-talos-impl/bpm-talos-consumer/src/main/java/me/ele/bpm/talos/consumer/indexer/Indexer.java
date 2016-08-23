package me.ele.bpm.talos.consumer.indexer;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import me.ele.arch.etrace.agent.Trace;
import me.ele.bpm.talos.consumer.factory.IndexClientFactory;
import me.ele.bpm.talos.index.document.Document;
import me.ele.bpm.talos.index.document.IIndexDocument;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.lang3.StringUtils;

/**
 * 索引基础类
 *
 */
public class Indexer implements Runnable {
	
	private Log log = LogFactory.getLog(Indexer.class);
	
	private List<IIndexDocument> docs;
	private boolean refresh;
	
	public Indexer() {
	}
	public Indexer(List<IIndexDocument> docs, boolean refresh) {
		this.docs = docs;
		this.refresh = refresh;
	}

	/**
	 * 索引文档
	 * @param docs
	 * @return
	 */
	@Override
	public void run() {
		try {
//			docs = dealwithDocuments(docs);
//			if (docs == null || docs.isEmpty())
//				return false;
			
			String clusterName = docs.get(0).getCluster();
			BulkRequestBuilder bulkRequest = IndexClientFactory.get(clusterName).prepareBulk();
			if (bulkRequest == null)
				return;
			
			for (IIndexDocument document : docs) {
				log.debug("document index:{}, type:{}, version:{}", 
						document.getIndex(), document.getType(), ((Document) document).getVersion());
				
				String index = document.getIndex();
				String type = document.getType();
				String id = document.getId();
				if (StringUtils.isBlank(index) || StringUtils.isBlank(type) || StringUtils.isBlank(id)) {
					log.error("index[{}],type[{}],id[{}]都不能为空！", index, type, id);
					continue;
				}
				
				switch (document.getAction()) {
					case Document.ADD:
						bulkRequest.add(new IndexRequest(index, type, id).source(document.toDocument()));
						break;
	
					case Document.UPDATE:
						bulkRequest.add(new UpdateRequest(index, type, id).doc(document.toDocument()));
						break;
						
					case Document.DELETE:
						bulkRequest.add(new DeleteRequest(index, type, id));
						break;
				}
			}
			
			if (bulkRequest.numberOfActions() > 0) {
				int tryTime = 2;
				while (tryTime-- > 0) {
					if (refresh) {
						bulkRequest.setRefresh(true);
					}
					BulkResponse response = bulkRequest.execute().actionGet();
					if (response.hasFailures()) {
						log.error("hasFailures");
						for (BulkItemResponse itemResponse : response.getItems()) {
							log.error(itemResponse.getFailureMessage());
						}
					} else {
						break;
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Trace.logError(e.getMessage(), e);
		}
	
	}

	/**
	 * 索引数据预处理
	 * @param docs
	 * @return
	 */
	protected Collection<IIndexDocument> dealwithDocuments(Collection<IIndexDocument> docs) {
		if (docs == null) {
			return docs;
		}
		HashMap<String, IIndexDocument> map = new HashMap<String, IIndexDocument>();
		for (IIndexDocument document : docs) {
			IIndexDocument doc = map.get(document.getId());
			
			if (doc != null) {
				if (doc.getAction() == Document.ADD && document.getAction() != Document.DELETE) {
					document.setAction(Document.ADD);
				}
				map.put(document.getId(), document);
			} else {
				map.put(document.getId(), document);
			}
		}
		return map.values();
	}

}
