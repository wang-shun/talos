package me.ele.bpm.talos.consumer.mlistener;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.annotation.Resource;

import me.ele.bpm.talos.common.soa.MetricUtils;
import me.ele.bpm.talos.consumer.document.CommonDocument;
import me.ele.bpm.talos.consumer.sequence.IndexSequence;
import me.ele.bpm.talos.mq.push.model.MessageEx;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;

import org.springframework.amqp.utils.SerializationUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

/**
 * MQ监听线程
 * @author jianming.zhou
 *
 */
@Deprecated
public class MQListener implements Runnable {
	
	private Log log = LogFactory.getLog(MQListener.class);
	
	@Resource
	private ConnectionFactory connectionFactory;
	@Resource
	private IndexSequence indexSequence;
	
	private String queueName;

	public MQListener() {
	}
	public MQListener(String queueName) {
		this.queueName = queueName;
	}

	@Override
	public void run() {
		Channel channel = null;
		QueueingConsumer consumer = null;
		try {
			Connection conn = connectionFactory.newConnection();
			channel = conn.createChannel();  
			consumer = new QueueingConsumer(channel);  
			channel.queueDeclare(queueName, true, false, false, null);
			channel.basicConsume(queueName, false, consumer);  
			channel.basicQos(1);
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}  
		
        while(true){  
        	try {
        		MetricUtils.record("consumerThread");
        		Delivery delivery = consumer.nextDelivery();  
            	MessageEx message = (MessageEx) SerializationUtils.deserialize(delivery.getBody());
            	log.info(message);
            	indexSequence.pushDocument(message.getSequenceKey(), new CommonDocument(message));
            	/** 确认消息已经收到 */  
            	channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);  
            } catch (Exception e) {
            	log.error(e.getMessage(), e);
            }
        } 
	}

}
