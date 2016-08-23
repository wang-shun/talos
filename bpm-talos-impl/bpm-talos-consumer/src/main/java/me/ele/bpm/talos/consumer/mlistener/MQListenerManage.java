package me.ele.bpm.talos.consumer.mlistener;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.annotation.Resource;

import me.ele.bpm.talos.common.soa.MetricUtils;
import me.ele.bpm.talos.common.util.JsonHelper;
import me.ele.bpm.talos.consumer.document.CommonDocument;
import me.ele.bpm.talos.consumer.model.RouterConf;
import me.ele.bpm.talos.consumer.model.RouterNode;
import me.ele.bpm.talos.consumer.sequence.IndexSequence;
import me.ele.bpm.talos.mq.push.model.MessageEx;
import me.ele.config.HuskarHandle;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;

import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * 消费端动态生成管理类
 * @author jianming.zhou
 *
 */
@Component
public class MQListenerManage {
	
	private Log log = LogFactory.getLog(MQListenerManage.class);
	
	@Resource
	private ConnectionFactory connectionFactory;
	@Autowired
	private IndexSequence indexSequence;
	
	private final String ROUTER_CONF = "ROUTER_CONF";
	private Map<String, Runnable> consumerMap = new HashMap<String, Runnable>();
	private String oldRouterConfStr = "";
	private String hostName;
	
	private Connection conn;
	
	public MQListenerManage() throws UnknownHostException {
		hostName = getHostName();
	}
	
	/**
	 * 后去配置节点信息
	 * @return
	 */
	public RouterConf getNewRouterConf() {
		RouterConf routerConf = null;
		String routerConfStr = HuskarHandle.get().getMyConfig().getProperty(ROUTER_CONF, "");
		if (!this.oldRouterConfStr.equals(routerConfStr)) {
			try {
				routerConf = JsonHelper.getMapper().readValue(routerConfStr, RouterConf.class);
				this.oldRouterConfStr = routerConfStr;
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		
		return routerConf;
	}
	
	/**
	 * 获取HostName
	 * @return
	 * @throws UnknownHostException
	 */
	private String getHostName() throws UnknownHostException {
		InetAddress ia = InetAddress.getLocalHost();
		String hostName = ia.getHostName();
		return hostName;
		
	}
	
	@Scheduled(fixedDelay = 3000)
	public void initConsumer() {
		RouterConf routerConf = getNewRouterConf();
		if (null != routerConf) {
			for (String serverNode : routerConf.getRouters().keySet()) {
				if (hostName.contains(serverNode)) {
					Map<String, RouterNode> nodeMap = routerConf.getRouters().get(serverNode);
					for (String business : nodeMap.keySet()) {
						RouterNode routerNode = nodeMap.get(business);
						for (String queue : routerNode.getQueues().values()) {
							String key = business + "_" + queue;
							if (!consumerMap.containsKey(business)) {
								Thread thread = new Thread(new MQListener(queue));
								thread.start();
								consumerMap.put(key, thread);
								log.info("initConsumer key:{}, thread:{}", key, thread.getName());
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * MQ监听线程
	 * @author jianming.zhou
	 *
	 */
	private class MQListener implements Runnable {
		
		private Log log = LogFactory.getLog(MQListener.class);
		
		private Channel channel;
		private QueueingConsumer consumer;
		
		private String queueName;

		public MQListener(String queueName) {
			this.queueName = queueName;
		}

		@Override
		public void run() {
			initDeclare();
			
	        while(true){  
	        	try {
	        		MetricUtils.record("Queue[" + queueName + "] consumerThread");
	        		Delivery delivery = consumer.nextDelivery();  
	            	MessageEx message = (MessageEx) SerializationUtils.deserialize(delivery.getBody());
	            	log.info(message);
	            	indexSequence.pushDocument(message.getSequenceKey(), new CommonDocument(message));
	            	/** 确认消息已经收到 */  
	            	channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);  
	        	} catch (ShutdownSignalException e) {
	            	log.error(e.getMessage(), e);
	            	shutdownSleep();
	            	recovery();
	            } catch (Exception e) {
	            	log.error(e.getMessage(), e);
	            	errorSleep();
	            }
	        } 
		}
		
		/**
		 * 恢复
		 */
		private void recovery() {
			initDeclare();
		}
		
		/**
		 * 初始化Exchange及Queue
		 */
		public void initDeclare() {
			try {
				initConnection();
				channel = conn.createChannel();
				consumer = new QueueingConsumer(channel); 
				channel.queueDeclare(queueName, true, false, false, null);
				channel.basicConsume(queueName, false, consumer);  
				channel.basicQos(1);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}
	
	/**
	 * 初始化连接
	 * @throws TimeoutException 
	 * @throws IOException 
	 */
	public synchronized void initConnection() throws IOException, TimeoutException {
		if (conn == null || !conn.isOpen()) {
			conn = connectionFactory.newConnection();
		}
	}
	
	
	/**
	 * 默认10s等待
	 */
	private void shutdownSleep() {
		try {
			int millis = HuskarHandle.get().getMyConfig().getInt("SHUTDOWN_WAIT_TIME", 1000*10);
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 默认1s等待
	 */
	private void errorSleep() {
		try {
			int millis = HuskarHandle.get().getMyConfig().getInt("ERROR_WAIT_TIME", 1000);
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
	}

}
