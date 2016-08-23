package me.ele.bpm.talos.search.service.impl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import me.ele.bpm.talos.common.soa.MetricUtils;
import me.ele.bpm.talos.index.exception.TalosException;
import me.ele.bpm.talos.search.model.SearchOrderNaposModel;
import me.ele.bpm.talos.search.model.SearchOrderPandoraModel;

import me.ele.bpm.talos.search.model.SearchResult;
import me.ele.bpm.talos.search.service.ICommonSearchService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.joda.time.LocalDate;
import org.elasticsearch.common.joda.time.format.DateTimeFormat;
import org.elasticsearch.common.joda.time.format.DateTimeFormatter;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

import me.ele.bpm.elasticsearch.base.ElasticBaseSearch;
import me.ele.bpm.talos.search.model.SearchOrderNewModel;
import me.ele.bpm.talos.search.service.ISearchOrderService;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

@Service
public class SearchOrderService implements ISearchOrderService{
	
	 private Log log = LogFactory.getLog(SearchOrderService.class);

    @Resource
    private ICommonSearchService iCommonSearchService;

	@Override
	public String searchOrderNew(SearchOrderNewModel search) throws IllegalAccessException {
        Map<String, Object> map = search.toMap();
        try {
            return iCommonSearchService.searchString("EOS_BUSINESS_ORDER", map);
        }catch (TalosException e){
            throw new IllegalAccessException(e.getMessage());
        }
	}

	/**
     *  pandora订单查询接口（已经迁移至commonsearch）
     *  */
    @Override
	public String searchOrder4Pandora(SearchOrderPandoraModel search) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        if(search.getId() != null){
            map.put("id", search.getId());
            if(search.getCreatedAtBegin() != null){
                map.put("begin_date", search.getCreatedAtBegin());
            }
            if(search.getCreatedAtEnd() != null){
                map.put("end_date", search.getCreatedAtEnd());
            }
            try {
                return iCommonSearchService.searchString("PANDORA_ORDER", map);
            }catch (TalosException e){
                throw new IllegalAccessException(e.getMessage());
            }
        }
        map = search.toMap();
        try {
            return iCommonSearchService.searchString("PANDORA_ORDER", map);
        }catch (TalosException e){
            throw new IllegalAccessException(e.getMessage());
        }
    }

	@Override
	public String searchOrder4Napos(SearchOrderNaposModel search) throws IllegalAccessException {
        Map<String, Object> map = search.toMap();
        try {
            return iCommonSearchService.searchString("ERS_NAPOS_ORDER", map);
        }catch (TalosException e){
            throw new IllegalAccessException(e.getMessage());
        }
	}


	@Override
	public boolean ping() {
		return true;
	}

}
