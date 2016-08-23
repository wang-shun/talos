package me.ele.bpm.talos.index.client;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.elasticsearch.client.Client;

/**
 * Index客户端工厂
 * @author jianming.zhou
 *
 */
@Deprecated
public class ClientFactory {
	
	@Resource(name = "bdClient")
	private IndexClient bdClient;
	@Resource(name = "pandoraClient")
	private IndexClient pandoraClient;
	
	private static ClientFactory factory;
	
	@PostConstruct
	public void init() {
		factory = this;
	}
	
	public static Client getClinet(ClientEnum client) {
		switch(client) {
			case BD: return factory.bdClient.getClient(); 
			case PANDORA: return factory.pandoraClient.getClient();
			default: return null;
		}
	}

}
