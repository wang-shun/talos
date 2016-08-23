package me.ele.bpm.talos.index.loader;


import org.elasticsearch.client.transport.TransportClient;

/**
 * Created by lupeidong on 16/6/24.
 */
public class Cluster{
    private TransportClient client;
    private int version;
    private String enable;

    public Cluster(TransportClient client,String enable,int version){
        this.client = client;
        this.version = version;
        this.enable = enable;
    }

    public int getVersion(){
        return version;
    }

    public TransportClient getClient(){
        return this.client;
    }

    public String getEnable(){
        return this.enable;
    }


}
