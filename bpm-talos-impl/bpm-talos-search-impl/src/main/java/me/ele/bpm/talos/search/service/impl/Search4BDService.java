package me.ele.bpm.talos.search.service.impl;

import javax.annotation.PostConstruct;

import me.ele.bpm.elasticsearch.base.ElasticBaseSearch;
import me.ele.bpm.talos.common.soa.MetricUtils;
import me.ele.bpm.talos.search.model.SearchActivitiesModel;
import me.ele.bpm.talos.search.service.ISearch4BDService;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.stereotype.Service;

/**
 * Created by yemengying on 15/11/3.
 */
public class Search4BDService implements ISearch4BDService {

    private Log log = LogFactory.getLog(Search4BDService.class);

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
            log.info("bd:host:{},port:{},cluster ：{}",host,port,clusterName);
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
    public String searchRstActivity(SearchActivitiesModel search) throws IllegalAccessException {
    	MetricUtils.record("searchRstActivity");
        log.info("bd查询参数:{}", search.toString());
        QueryBuilder baseQuery = ElasticBaseSearch.getInstance().getQueryBuilder(search);

        log.info("bd查询参数语句:{}",baseQuery.toString());

        SearchResponse response = ElasticBaseSearch.getInstance().getIndexAndType(client, search).setQuery(baseQuery).execute().actionGet();

        log.info("bd查询参数结果:{}",response.toString());

        return response.toString();
    }
    
	@Override
	public boolean ping() {
		return true;
	}
}
