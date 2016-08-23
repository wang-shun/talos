package me.ele.bpm.talos.search.service.impl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import me.ele.bpm.elasticsearch.base.ElasticBaseSearch;
import me.ele.bpm.talos.search.model.KnowledgeModel4Search;
import me.ele.bpm.talos.search.model.SearchKnowledgeModel;
import me.ele.bpm.talos.search.model.SearchResult;
import me.ele.bpm.talos.search.service.ICommonSearchService;
import me.ele.bpm.talos.search.service.ISearchKnowledgeBaseService;
import me.ele.bpm.talos.search.utils.ResultUtils;
import me.ele.contract.exception.ServiceException;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SearchKnowledgeBaseService implements ISearchKnowledgeBaseService {

    @Resource
    private ICommonSearchService iCommonSearchService;

	/**
	 * 客服知识库搜索接口
	 * 切换至CommonSearch
	 * */
	public SearchResult searchKnowledge(SearchKnowledgeModel searchModel)
			throws ServiceException {
        Map<String, Object> map = new HashMap<>();
		if(searchModel.getKeyword() != null){
			map.put("key", searchModel.getKeyword());
		}
        map.put("limit", searchModel.getLimit());
        map.put("offset", searchModel.getOffset());
        SearchResult result = iCommonSearchService.search("PANDORA_WIKI", map);
		return result;
	}
	
}
