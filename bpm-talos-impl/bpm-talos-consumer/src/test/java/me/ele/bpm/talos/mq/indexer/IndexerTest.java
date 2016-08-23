package me.ele.bpm.talos.mq.indexer;

import me.ele.bpm.elasticsearch.base.ElasticBaseSearch;
import me.ele.bpm.talos.common.util.Utils;
import me.ele.bpm.talos.index.client.ClientEnum;
import me.ele.bpm.talos.index.client.ClientFactory;
import me.ele.bpm.talos.mq.base.TestBase;
import me.ele.bpm.talos.mq.push.model.buss.FamilyRestaurant;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.junit.Test;

public class IndexerTest extends TestBase {
	
	@Test
	@SuppressWarnings("unused")
	public void indexTest() throws Exception {
		String index = "ers_restaurant";
		String type = "restaurant";
		String id = "1000000279";
		FamilyRestaurant document = new FamilyRestaurant();
		document.setId(id);
		document.setActivities(Utils.toList(118763L,118764L));	
		XContentBuilder builder = ElasticBaseSearch.getInstance().getJsonBuilder4Update(document);
		BulkRequestBuilder bulkRequest = ClientFactory.getClinet(ClientEnum.PANDORA).prepareBulk();
		bulkRequest.add(new UpdateRequest(index, type, id).doc(builder));
		BulkResponse response = bulkRequest.execute().actionGet();
		System.out.println(builder.string());
	}
	
}
