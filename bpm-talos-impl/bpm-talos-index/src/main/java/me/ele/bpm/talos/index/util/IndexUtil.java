package me.ele.bpm.talos.index.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import me.ele.bpm.talos.index.client.ClientEnum;
import me.ele.bpm.talos.index.client.ClientFactory;
import me.ele.bpm.talos.index.client.IndexClient;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

/**
 * Index工具类
 * 
 * @author jianming.zhou
 *
 */
@Deprecated
public class IndexUtil extends IndexClient {

	private Log log = LogFactory.getLog(IndexUtil.class);
	
	private Client client;
	
	@PostConstruct
	public void init() {
		client = ClientFactory.getClinet(ClientEnum.BD);
	}
	
	/**
	 * Index 创建 && field映射
	 * 
	 * @param index
	 * @param type
	 * @param fileName
	 * @throws IOException
	 */
	public void putMapping(String index, String type, String fileName) throws IOException {
        String file = String.format("type_mapping/%s.json", fileName);
//		XContentBuilder xcBuilder = XContentFactory.jsonBuilder(SkyrimUtil.read(file));
		IndicesAdminClient adminClient = client.admin().indices();
		IndicesExistsResponse exitResponse = adminClient.prepareExists(index).get();
		if (!exitResponse.isExists()) {
			adminClient.prepareCreate(index).get();
			log.info("index[{}]不存在，创建", index);
		}
//		PutMappingResponse response = adminClient
//				  .preparePutMapping(index)
//				  .setType(type)
//				  .setSource(xcBuilder)
//				  .get();
		log.info("[putMapping] index:{}, type:{}, mappingPath:{}", index, type, fileName); 
//		log.info("response:{}", response);
	}
	
	/**
	 * 创建Index
	 * 
	 * @param index
	 * @param type
	 * @param id
	 * @param paramMap
	 * @throws IOException
	 */
	public void createIndex(String index, String type, String id, Map<String, Object> paramMap) throws IOException {
		XContentBuilder xcBuilder = getXContentBuilder(paramMap);
    	IndexResponse response = client.prepareIndex(index, type, id)
			    .setSource(xcBuilder)  
			    .get();
		log.info("[createIndex] index:{}, type:{}, id:{}", index, type, id); 
		log.info("[response] id:{}", response.getId());
	}
	
	/**
	 * 批量创建Index
	 * 
	 * @param index
	 * @param type
	 * @param paramMaps
	 * @throws IOException
	 */
	public void createIndexBatch(String index, String type, Map<String, Map<String, Object>> paramMaps) {
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for (Entry<String, Map<String, Object>> entry : paramMaps.entrySet()) {
			try {
				XContentBuilder xcBuilder = getXContentBuilder(entry.getValue());
				bulkRequest.add(client.prepareIndex(index, type, entry.getKey()).setSource(xcBuilder));
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		BulkResponse response = bulkRequest.get();
		if (response.hasFailures()) {
			log.error("[createIndexBatch] error!");
		}
		log.info("[createIndexBatch] index:{}, type:{}", index, type); 
	}
	
	/**
	 * 删除Index
	 * 
	 * @param index
	 * @param type
	 * @param id
	 */
	public void deleteIndex(String index, String type, String id) {
		DeleteResponse response = client.prepareDelete(index, type, id).get();
		log.info("[deleteIndex] index:{}, type:{}, id:{}", index, type, id); 
		log.info("[response] id:{}", response.getId());
	}
	
	public void deleteIndexBatch(String index, String type, List<String> ids) {
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for (String id : ids) {
			bulkRequest.add(client.prepareIndex(index, type, id));
		}
		
		BulkResponse response = bulkRequest.get();
		if (response.hasFailures()) {
			log.error("[deleteIndexBatch] error!");
		}
		log.info("[deleteIndexBatch] index:{}, type:{}", index, type); 
	}
	
	/**
	 * 更新Index
	 * 
	 * @param index
	 * @param type
	 * @param id
	 * @param paramMap
	 * @throws IOException
	 */
	public void updateIndex(String index, String type, String id, Map<String, Object> paramMap) throws IOException {
		XContentBuilder xcBuilder = getXContentBuilder(paramMap);
		UpdateResponse response = client.prepareUpdate(index, type, id)
		         .setDoc(xcBuilder)
		         .get();
		log.info("[updateIndex] index:{}, type:{}, id:{}", index, type, id); 
		log.info("[response] id:{}", response.getId());
	}
	
	/**
	 * 批量更新Index
	 * 
	 * @param index
	 * @param type
	 * @param paramMaps
	 */
	public void updateIndexBatch(String index, String type, Map<String, Map<String, Object>> paramMaps) {
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for (Entry<String, Map<String, Object>> entry : paramMaps.entrySet()) {
			try {
				XContentBuilder xcBuilder = getXContentBuilder(entry.getValue());
				bulkRequest.add(client.prepareUpdate(index, type, entry.getKey()).setSource(xcBuilder));
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		BulkResponse response = bulkRequest.get();
		if (response.hasFailures()) {
			log.error("[updateIndexBatch] error!");
		}
		log.info("[updateIndexBatch] index:{}, type:{}", index, type); 
	}
	
	/**
	 * XContentBuilder生成器
	 * 
	 * @param paramMap
	 * @return
	 * @throws IOException
	 */
	private XContentBuilder getXContentBuilder(Map<String, Object> paramMap) throws IOException {
		XContentBuilder xcBuilder = XContentFactory.jsonBuilder();
		xcBuilder.startObject();
		for (Entry<String, Object> entry : paramMap.entrySet()) {
			xcBuilder.field(entry.getKey(), entry.getValue());
		}
    	xcBuilder.endObject();
    	return xcBuilder;
	}
	
}
