package me.ele.bpm.talos.index.client;

import javax.annotation.PostConstruct;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class IndexClient {

	private Client client;
	
    private String host;
    private int port;
    private String clusterName;

	public Client getClient() {
		return client;
	}
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
	public void init() {
		if (null == client) {
			Settings settings = ImmutableSettings
					.settingsBuilder()
					.put("client.transport.sniff", true)
					.put("cluster.name", clusterName).build();
			
			TransportClient transportClient = new TransportClient(settings);
			String[] hosts = host.split(",");
			for(String host : hosts){
				transportClient.addTransportAddress(new InetSocketTransportAddress(host, port));
			}
			client = transportClient;
		}
	}
	
}
