package me.ele.bpm.talos.timer.task;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import me.ele.bpm.talos.common.util.JsonHelper;
import me.ele.bpm.talos.index.template.SearchTemplateContainer;
import me.ele.bpm.talos.index.template.Template;
import me.ele.bpm.talos.timer.dao.CommonDao;
import me.ele.bpm.talos.timer.es.IndexClientFactory;
import me.ele.bpm.talos.timer.model.ProcessConf;
import me.ele.bpm.talos.timer.model.ProcessNode;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.client.Client;

/**
 * 
 * @author jianming.zhou
 *
 */
public class TaskScheduler {
	
	private Log log = LogFactory.getLog(TaskScheduler.class);
	
	@Resource
	private CommonDao commonDao;

	/**
	 * Family-活动定时任务
	 */
	public void activityTask() {
		try {
			ProcessConf processConf = JsonHelper.getMapper().readValue(new File("process/family/restaurant.json"), ProcessConf.class);
			if (null != processConf.getProcesses() && !processConf.getProcesses().isEmpty()) {
				List<Map<String, Object>> indexData = new ArrayList<Map<String,Object>>();
				Map<String, String> esColumMap = new LinkedHashMap<String, String>();
				// 1.从数据库组装数据
				// step 1..n
				List<Map<String, Object>> list = null;
				for (Entry<Integer, ProcessNode> entry : processConf.getProcesses().entrySet()) {
					ProcessNode processNode = (ProcessNode) entry.getValue();
					esColumMap.putAll(processNode.getColumn_map());
					if (entry.getKey() == 0) {
						list = commonDao.selectMain(processNode, processConf);
						if (null == list || list.isEmpty()) {
							break;
						}
					} else {
						List<Object> accordingValues = new ArrayList<Object>();
						for (Map<String, Object> one : list) {
							accordingValues.add(one.get(processNode.getDoc_according_key()));
						}
						List<Map<String, Object>> partList = commonDao.selectPart(processNode, accordingValues);
						Map<Object, Map<String, Object>> mergeData = new HashMap<Object, Map<String,Object>>();
						for (Map<String, Object> one : partList) {
							mergeData.put(one.remove(processNode.getTable_according_key()), one);
						}
						for (Map<String, Object> one : list) {
							if (null != mergeData.get(one.get(processNode.getDoc_according_key()))) {
								one.putAll(mergeData.get(one.get(processNode.getDoc_according_key())));
							}
						}
					}
				}
				
				// 2.组装索引数据
				if (null != list && !list.isEmpty()) {
					for (Map<String, Object> data : list) {
						Map<String, Object> one = new HashMap<String, Object>();
						for (String esKey : esColumMap.keySet()) {
							one.put(esKey, data.get(esColumMap.get(esKey)));
						}
						indexData.add(one);
					}
				}
				
				// 3.创建索引
				if (!indexData.isEmpty()) {
					Client indexClient = IndexClientFactory.get(processConf.getCluster_name());
					BulkRequestBuilder bulkRequest = indexClient.prepareBulk();
					Template template = SearchTemplateContainer.getTemplate(processConf.getTemplateId());
					for (Map<String, Object> doc : indexData) {
						String[] index = template.indexDestination(doc);
						String[] types = template.doctypeDestination(doc);
						for (String type : types) {
							bulkRequest.add(indexClient.prepareIndex(index[0], type, String.valueOf(doc.get("id"))).setSource(indexData));
						}
					}
					bulkRequest.get();
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		log.info("activityTask");
	}
}
