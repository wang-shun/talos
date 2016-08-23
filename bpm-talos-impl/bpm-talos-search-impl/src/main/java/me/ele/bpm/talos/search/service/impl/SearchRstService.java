package me.ele.bpm.talos.search.service.impl;

import me.ele.bpm.elasticsearch.base.ElasticBaseSearch;
import me.ele.bpm.talos.common.soa.MetricUtils;
import me.ele.bpm.talos.search.model.SearchResult;
import me.ele.bpm.talos.search.model.SearchResultNew;
import me.ele.bpm.talos.search.model.SearchRst4FamilyModel;
import me.ele.bpm.talos.search.service.ISearchRstService;
import me.ele.bpm.talos.search.utils.ResultUtils;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;

import javax.annotation.PostConstruct;

/**
 * Created by yemengying on 15/11/27.
 */
public class SearchRstService implements ISearchRstService{

    private Log log = LogFactory.getLog(SearchRstService.class);

    private TransportClient client;

    private String host;
    private int port;
    private String clusterName;


    public void setHost(String host) {
        this.host = host;
    }
    public void setPort(int port) {
        this.port = port;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    @PostConstruct
    public void init() {
        if (null == client) {
            log.info("rst:host:{},port:{},cluster:{}",host,port,clusterName);
            Settings settings = ImmutableSettings
                    .settingsBuilder()
                    .put("client.transport.sniff", true)
                    .put("cluster.name", clusterName).build();

            String[] hosts = host.split(",");
            client = new TransportClient(settings);
            for(String host : hosts){
                client.addTransportAddress(new InetSocketTransportAddress(host, port));
            }
        }
    }
    @Override
    public SearchResult searchRst4Family(SearchRst4FamilyModel search) {
        MetricUtils.record("searchRst4Family");
        try {
            log.info("family查询餐厅参数:{}", search.toString());

            QueryBuilder baseQuery = ElasticBaseSearch.getInstance().getQueryBuilder(search);

            SearchResponse response ;

            if(baseQuery != null) {
                log.info("family查询餐厅语句:{}", baseQuery.toString());
                response = ElasticBaseSearch.getInstance().getIndexAndType(client, search)
                        .setQuery(baseQuery).execute().actionGet();
                log.info("family查询餐厅结果:{}", response.toString());
            }else{
                log.info("family查询餐厅语句:{}", "无任何筛选条件");
                response = ElasticBaseSearch.getInstance().getIndexAndType(client, search)
                        .execute().actionGet();
                log.info("family查询餐厅结果:{}", response.toString());
            }

            return ResultUtils.dealSearchResult(response);
        }catch(IllegalAccessException e){
            log.error("不会出现的异常出现了");
            return new SearchResult();
        }

    }

    @Override
    public SearchResultNew searchRst4FamilyNew(SearchRst4FamilyModel search) {
        MetricUtils.record("searchRst4Family");
        try {
            log.info("family查询餐厅参数:{}", search.toString());

            QueryBuilder baseQuery = ElasticBaseSearch.getInstance().getQueryBuilder(search);

            SearchResponse response ;

            if(baseQuery != null) {
                log.info("family查询餐厅语句:{}", baseQuery.toString());
                response = ElasticBaseSearch.getInstance().getIndexAndType(client, search)
                        .setQuery(baseQuery).execute().actionGet();
                log.info("family查询餐厅结果:{}", response.toString());
            }else{
                log.info("family查询餐厅语句:{}", "无任何筛选条件");
                response = ElasticBaseSearch.getInstance().getIndexAndType(client, search)
                        .execute().actionGet();
                log.info("family查询餐厅结果:{}", response.toString());
            }

            return ResultUtils.dealSearchResultNew(response);
        }catch(IllegalAccessException e){
            log.error("不会出现的异常出现了");
            return new SearchResultNew();
        }
    }
}
