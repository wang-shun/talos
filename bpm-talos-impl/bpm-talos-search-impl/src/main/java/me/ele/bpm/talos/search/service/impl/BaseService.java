package me.ele.bpm.talos.search.service.impl;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import javax.annotation.PostConstruct;

/**
 * Created by yemengying on 15/11/5.
 */
public class BaseService {

    protected Client client;


    protected String host;
    protected int port;
    protected String clusterName;

    public void setClient(Client client) {
        this.client = client;
    }

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
    @SuppressWarnings("resource")
    public void init() {
        if (null == client) {

            Settings settings = ImmutableSettings
                    .settingsBuilder()
                    .put("client.transport.sniff", true)
                    .put("cluster.name", clusterName).build();
            client = new TransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(host, port));
        }
    }
}
