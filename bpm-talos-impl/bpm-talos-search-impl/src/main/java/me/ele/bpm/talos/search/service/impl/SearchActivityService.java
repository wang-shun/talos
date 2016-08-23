package me.ele.bpm.talos.search.service.impl;

import me.ele.bpm.elasticsearch.base.ElasticBaseSearch;
import me.ele.bpm.talos.common.soa.MetricUtils;
import me.ele.bpm.talos.search.model.SearchActivities4AadminModel;
import me.ele.bpm.talos.search.model.SearchResult;
import me.ele.bpm.talos.search.model.SearchResultNew;
import me.ele.bpm.talos.search.service.ISearchActivityService;
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
 * Created by yemengying on 15/11/26.
 */
public class SearchActivityService implements ISearchActivityService {

    private Log log = LogFactory.getLog(SearchActivityService.class);


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
            log.info("family:host:{},port:{},cluster ： {}",host,port,clusterName);
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
    public SearchResult searchActivity4Admin(SearchActivities4AadminModel search) {
        MetricUtils.record("searchActivity");
        try {
            log.info("family查询活动参数:{}", search.toString());

            QueryBuilder baseQuery = ElasticBaseSearch.getInstance().getQueryBuilder(search);


            //根据查询的城市id 获得要查询的文档
            String documentType = "activity_%s";
            String index = (search.getCityId() == null) ? "family_restaurant_activity_all_city" : "family_restaurant_activity";
            documentType = (search.getCityId() == null) ? "activity" : String.format(documentType, search.getCityId());

            SearchResponse response ;

            if(baseQuery != null) {
                log.info("family查询活动语句:{}", baseQuery.toString());
                response = ElasticBaseSearch.getInstance().getIndexAndType(client, search).setTypes(documentType).setIndices(index)
                        .setQuery(baseQuery).execute().actionGet();
                log.info("family查询活动结果:{}", response.toString());
            }else{
                log.info("family查询活动语句:{}", "无任何筛选条件");
                response = ElasticBaseSearch.getInstance().getIndexAndType(client, search).setTypes(documentType).setIndices(index)
                        .execute().actionGet();
                log.info("family查询活动结果:{}", response.toString());
            }

            return ResultUtils.dealSearchResult(response);


        }catch(IllegalAccessException e){
            log.error("不会出现的异常出现了");
            return new SearchResult();
        }

    }

    @Override
    public SearchResultNew searchActivity4AdminNew(SearchActivities4AadminModel search) {
        MetricUtils.record("searchOrder");
        try {
            log.info("family查询活动参数:{}", search.toString());

            QueryBuilder baseQuery = ElasticBaseSearch.getInstance().getQueryBuilder(search);


            //根据查询的城市id 获得要查询的文档
            String documentType = "activity_%s";
            String index = (search.getCityId() == null) ? "family_restaurant_activity_all_city" : "family_restaurant_activity";
            documentType = (search.getCityId() == null) ? "activity" : String.format(documentType, search.getCityId());

            SearchResponse response ;

            if(baseQuery != null) {
                log.info("family查询活动语句:{}", baseQuery.toString());
                response = ElasticBaseSearch.getInstance().getIndexAndType(client, search).setTypes(documentType).setIndices(index)
                        .setQuery(baseQuery).execute().actionGet();
                log.info("family查询活动结果:{}", response.toString());
            }else{
                log.info("family查询活动语句:{}", "无任何筛选条件");
                response = ElasticBaseSearch.getInstance().getIndexAndType(client, search).setTypes(documentType).setIndices(index)
                        .execute().actionGet();
                log.info("family查询活动结果:{}", response.toString());
            }

            return ResultUtils.dealSearchResultNew(response);


        }catch(IllegalAccessException e){
            log.error("不会出现的异常出现了");
            return new SearchResultNew();
        }
    }
}
