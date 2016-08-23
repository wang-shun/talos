package me.ele.bpm.talos.timer.es;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import me.ele.bpm.talos.common.util.JsonHelper;
import me.ele.bpm.talos.timer.model.EsClientConf;
import me.ele.bpm.talos.timer.model.EsClientNode;
import me.ele.contract.exception.ServerException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

/**
 * 索引服务工厂类
 * @author jianming.zhou
 *
 */
public class IndexClientFactory {
	
	private static Map<String, Client> clientMap = new HashMap<String, Client>();
	
	static {
		try {
			EsClientConf esClientConf = JsonHelper.getMapper().readValue(new File("datasource/esclient.json"), EsClientConf.class);
			for (Entry<String, List<EsClientNode>> entry : esClientConf.getEsClientConf().entrySet()) {
				String clusterName = entry.getKey();
				List<EsClientNode> esClientNodes = entry.getValue();
				if (null != esClientNodes && !esClientNodes.isEmpty()) {
					Settings settings = ImmutableSettings
							.settingsBuilder()
							.put("client.transport.sniff", true)
							.put("cluster.name", clusterName).build();
					TransportClient transportClient = new TransportClient(settings);
					
					for (EsClientNode esClientNode : esClientNodes) {
						String hostStr = esClientNode.getHost();
						String[] hosts = hostStr.split(",");
						int port = esClientNode.getPort();
						for(String host : hosts){
							transportClient.addTransportAddress(new InetSocketTransportAddress(host, port));
						}
					}
					clientMap.put(clusterName, transportClient);
				}
				
			}
		} catch (IOException e) {
			new ServerException("Init EsClient field!");
		}
	}
	
	public static Client get(String clusterName) {
		return clientMap.get(clusterName);
	}

}
