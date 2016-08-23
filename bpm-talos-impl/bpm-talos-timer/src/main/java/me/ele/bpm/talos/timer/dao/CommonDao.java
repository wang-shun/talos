package me.ele.bpm.talos.timer.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.ele.bpm.talos.common.util.Utils;
import me.ele.bpm.talos.timer.es.IndexClientFactory;
import me.ele.bpm.talos.timer.mapper.CommonMapper;
import me.ele.bpm.talos.timer.model.ProcessConf;
import me.ele.bpm.talos.timer.model.ProcessNode;
import me.ele.bpm.talos.timer.session.SessionFactory;
import me.ele.contract.exception.ServiceException;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;

/**
 * 通用Dao层
 * @author jianming.zhou
 *
 */
@Component
public class CommonDao {
	
	private Log log = LogFactory.getLog(CommonDao.class);
	
	public List<Map<String, Object>> selectMain(ProcessNode processNode, ProcessConf processConf) throws ServiceException {
		SqlSessionFactory sessionFactory = SessionFactory.getSqlSessionFactory(processNode.getDatabase_name());
		SqlSession sqlSession = sessionFactory.openSession();
		try {
			CommonMapper commonMapper = sqlSession.getMapper(CommonMapper.class);
			
			Map<String, String> columnMap = processNode.getColumn_map();
			String columnStr = Utils.convertToString(new ArrayList<String>(columnMap.values()), ",");
			Object accordingValue = getAccordingValueByES(processConf, processNode.getTable_according_key());
			String preconditionStr = getPrecondition(processNode.getTable_preconditions());
			
			List<Map<String, Object>> result = null;
			if (null != accordingValue) {
				result = commonMapper.selectGT(processNode.getTable_name(), columnStr, 
						processNode.getTable_according_key(), accordingValue, preconditionStr);
				log.info("sql[ tableName:{}, column:({}), accordingKey:{}, accordingValue:{}, precondition:{} ]", 
						processNode.getTable_name(), columnStr, processNode.getTable_according_key(), accordingValue, 
						preconditionStr);
			} else {
				result = commonMapper.selectAll(processNode.getTable_name(), columnStr);
				log.info("sql[ tableName:{}, column:({}) ]", processNode.getTable_name(), columnStr);
			}
			return result;
		} finally {
			sqlSession.close();
		}
	}
	
	public List<Map<String, Object>> selectPart(ProcessNode processNode, List<Object> accordingValues) {
		SqlSessionFactory sessionFactory = SessionFactory.getSqlSessionFactory(processNode.getDatabase_name());
		SqlSession sqlSession = sessionFactory.openSession();
		try {
			CommonMapper commonMapper = sqlSession.getMapper(CommonMapper.class);
			
			Map<String, String> columnMap = processNode.getColumn_map();
			List<String> columnList = new ArrayList<String>(columnMap.values());
			columnList.add(processNode.getTable_according_key());
			String columnStr = Utils.convertToString(columnList, ",");
			Object accordingValue = Utils.convertToString(accordingValues, ",");
			String preconditionStr = getPrecondition(processNode.getTable_preconditions());

			List<Map<String, Object>> result = null;
			if (null != accordingValue) {
				result = commonMapper.selectIN(processNode.getTable_name(), columnStr, 
						processNode.getTable_according_key(), accordingValue, preconditionStr);
				log.info("sql[ tableName:{}, column:[{}], accordingKey:{}, accordingValue:{}, precondition:{} ]", 
						processNode.getTable_name(), columnStr, processNode.getTable_according_key(), accordingValue, 
						preconditionStr);
			}
			return result;
		} finally {
			sqlSession.close();
		}
	}
	
	/**
	 * 获得SQL起始值
	 * @param clientName
	 * @param accordingKey
	 * @return
	 * @throws ServiceException 
	 */
	private Object getAccordingValueByES(ProcessConf processConf, String accordingKey) throws ServiceException {
		String index = processConf.getStart_index_key();
		String type = processConf.getStart_doctype_key();
		Client client = IndexClientFactory.get(processConf.getCluster_name());
		if (null == client) {
			log.error("ES集群[{}]未找到!");
			throw new ServiceException("ES集群["+ processConf.getCluster_name() +"]未找到!");
		}
		SearchResponse response = client.prepareSearch(index)
				.setTypes(type)
				.setSize(1)
				.addSort(accordingKey, SortOrder.DESC)
//		        .setExplain(true)
				.get();
		if (response.getHits().getHits().length == 1) {
			Object result = response.getHits().getHits()[0].sourceAsMap().get(accordingKey);
			return result;
		}
		log.info("未找到AccordingValue!");
		return null;
	}
	
	/**
	 * 组装SQL前置条件
	 * @param tablePreconditions
	 * @return
	 */
	private String getPrecondition(List<List<Object>> tablePreconditions) {
		if (null != tablePreconditions && !tablePreconditions.isEmpty()) {
			StringBuilder builder = new StringBuilder();
			for (List<Object> precondition : tablePreconditions) {
				if (precondition.size() == 3) {
					builder.append(" AND ");
					builder.append(precondition.get(0));
					builder.append(" ");
					builder.append(precondition.get(1));
					builder.append(" ");
					builder.append(precondition.get(2).toString()
							.replace("[", "(").replace("]", ")"));
				}
			}
			return builder.toString();
		}
		return "";
	}
	
}
