package me.ele.bpm.talos.index.indexer;

import java.util.Collection;
import java.util.HashMap;

import me.ele.bpm.talos.index.client.ClientEnum;
import me.ele.bpm.talos.index.client.ClientFactory;
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

/**
 * 索引基础类
 *
 */
public class Indexer {
	
	private Log log = LogFactory.getLog(Indexer.class);
	
	protected ClientEnum clientName;
	
	public void setClientName(String clientName) {
		this.clientName = ClientEnum.valueOf(clientName);
	}

	/**
	 * 索引文档
	 * @param docs
	 * @return
	 */
	public boolean indexDocuments(Collection<IIndexDocument> docs) {
		try {
			docs = dealwithDocuments(docs);
			if (docs == null || docs.isEmpty())
				return false;
			
			BulkRequestBuilder bulkRequest = ClientFactory.getClinet(clientName).prepareBulk();
			if (bulkRequest == null)
				return false;
			
			String index;
			String type;
			String id;
			for (IIndexDocument document : docs) {
				log.debug("document index:{}, type:{}, version:{}", 
						document.getIndex(), document.getType(), ((Document) document).getVersion());
				
				index = document.getIndex();
				type = document.getType();
				id = document.getId();
				
				switch (document.getAction()) {
				case Document.ADD:
					bulkRequest.add(new IndexRequest(index, type).source(document.toDocument()));
					break;

				case Document.UPDATE:
					bulkRequest.add(new UpdateRequest(index, type, id).doc(document.toDocument()));
					break;
					
				case Document.DELETE:
					bulkRequest.add(new DeleteRequest(index, type, id));
					break;
				}
			}
			int tryTime = 3;
			while (tryTime-- > 0) {
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
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	
		return true;
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
			IIndexDocument doc = document;
			
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
