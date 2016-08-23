package me.ele.bpm.talos.index;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.node.NodeBuilder;
import org.junit.Before;
import org.junit.Test;

public class IndexTest {
	
	private static Log log = LogFactory.getLog(IndexTest.class);
	
	private Client client;
	
	@Before
	public void init() {
		//1.
//		Settings settings = ImmutableSettings
//				.settingsBuilder()
//				.put("client.transport.sniff", true)
//				.put("cluster.zzz", "z_node").build();  
//		client = new TransportClient(settings)
//				.addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9300)); 
		
		//2.
//		client = new TransportClient()
//		        .addTransportAddress(new InetSocketTransportAddress("localhost", 9300))
//		        .addTransportAddress(new InetSocketTransportAddress("localhost", 9301));
		
		//3.
		client = NodeBuilder.nodeBuilder()
                .client(true)
                .node()
                .client();
	}

	@Test
	public void createIndexTest() throws Exception {
		IndexRequestBuilder builder = client.prepareIndex("z_index", "z_type", "z_1")
			    .setSource(XContentFactory.jsonBuilder()  
				    .startObject()  
				      .field("author", "zzz")  
				      .field("body", "elasticsearch开源代码测试")  
				      .field("createDate", "201509024")  
				      .field("valid", true)  
				    .endObject())  
			    .setTTL(8000);
		IndexResponse response = builder.get();  
		log.info(response.getId()); 
	}
	
	@Test
	public void putMappingTest() throws Exception {
		XContentBuilder mapping = XContentFactory.jsonBuilder()
                .startObject()
                     .startObject("general")
                          .startObject("properties")
                               .startObject("author")
                                  .field("type", "string")
                                  .field("index", "not_analyzed")
                               .endObject()
                               .startObject("body")
                                  .field("type","string")
                                  .field("index", "analyzed")
                               .endObject()
                          .endObject()
                      .endObject()
                   .endObject();

		PutMappingResponse response = client.admin().indices()
				  .preparePutMapping("test")
				  .setType("general")
				  .setSource(mapping)
				  .get();
		log.info(response.getContext()); 
	}
	
	@Test
	public void deleteTest() {
		DeleteRequestBuilder builder = client.prepareDelete("z_index", "z_type", "z_1")
				.setOperationThreaded(false);
		DeleteResponse response = builder.get(); 
		log.info(response.getId()); 
	}
	
	@Test
	public void updateTest() throws Exception {
		UpdateRequestBuilder builder = client.prepareUpdate("z_index", "z_type", "z_3")
		         .setDoc(XContentFactory.jsonBuilder()
		             .startObject()
		                 .field("body", "asdf")
		             .endObject());
		UpdateResponse response = builder.get();
		log.info(response.getId()); 
	}
	
	@Test
	public void searchByGetTest() {
		GetRequestBuilder builder = client.prepareGet("z_index", "z_type", "z_1");
		GetResponse responseGet = builder.get();
		log.info(responseGet.getSourceAsString());
	}
	
	@Test
	public void searchTest() {
		BoolQueryBuilder qb = QueryBuilders.boolQuery()
				.must(new TermQueryBuilder("",""))
				.should(new QueryStringQueryBuilder("开源").field("body"))
				; 
		SearchRequestBuilder builder = client.prepareSearch("z_index")
				.setTypes("z_type")
				.setSearchType(SearchType.DEFAULT)
				.setFrom(0)
				.setSize(100)
				.setQuery(qb);
		SearchResponse response = builder.get();  
		log.info("response:{}", response);  
		log.info("totalHits:{}", response.getHits().getTotalHits());  
	}
}
