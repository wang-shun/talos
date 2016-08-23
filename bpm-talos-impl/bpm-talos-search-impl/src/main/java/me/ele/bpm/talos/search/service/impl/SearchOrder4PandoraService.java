package me.ele.bpm.talos.search.service.impl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import me.ele.bpm.elasticsearch.base.ElasticBaseSearch;
import me.ele.bpm.talos.common.soa.MetricUtils;
import me.ele.bpm.talos.index.exception.TalosException;
import me.ele.bpm.talos.search.model.SearchOrderModel;
import me.ele.bpm.talos.search.service.ICommonSearchService;
import me.ele.bpm.talos.search.service.ISearchOrder4Pandora;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by yemengying on 15/11/5.
 */
@Service
public class SearchOrder4PandoraService implements ISearchOrder4Pandora {

    private Log log = LogFactory.getLog(Search4BDService.class);

    @Resource
    private ICommonSearchService iCommonSearchService;

    @Override
    public String searchOrder(SearchOrderModel search) throws IllegalAccessException {
    	Map<String, Object> searchMap = search.toMap();
//        log.info("javis order search params = {}", search.toString());
        try {
            return iCommonSearchService.searchString("JAVIS_ORDER_SEARCH", searchMap);
        }catch (TalosException e){
            throw new IllegalAccessException(e.getMessage());
        }
    }

	@Override
	public boolean ping() {
		return true;
	}
}
