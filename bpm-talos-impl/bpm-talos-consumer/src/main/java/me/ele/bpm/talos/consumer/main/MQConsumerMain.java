package me.ele.bpm.talos.consumer.main;

import me.ele.arch.etrace.agent.config.AgentConfiguration;
import me.ele.bpm.talos.common.soa.ClientUtils;
import me.ele.bpm.talos.consumer.sequence.IndexSequence;
import me.ele.config.HuskarHandle;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;
import me.ele.huskar.common.exception.HuskarException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * MQConsumer启动程序
 * @author jianming.zhou
 *
 */
public class MQConsumerMain {

	private static Log logger = LogFactory.getLog(MQConsumerMain.class);
	
	private static ApplicationContext context;
	
	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		logger.info("[MQ_CONSUMER服务开始启动！]");
		context = initSpringContext();
		initHuskar();
		initEtrace();
		initThread();
		long end = System.currentTimeMillis();
		logger.info("[MQ_CONSUMER服务启动成功！用时：{}毫秒]", end - start);
	}

	/**
	 * 初始化spring容器
	 * @return
	 */
	private static ApplicationContext initSpringContext() {
		return new ClassPathXmlApplicationContext(
				"classpath:bpm-talos-consumer.xml");
	}
	
	/**
	 * 初始化Huskar
	 * @throws HuskarException
	 */
	public static void initHuskar() throws HuskarException {
		ClientUtils.initClients();
		HuskarHandle.get().initMy(ServerConfUtil.getServiceName(), ServerConfUtil.getGroup());
	}
	
	/**
	 * 初始化Etrace
	 */
	public static void initEtrace() {
		AgentConfiguration.setAppId(ServerConfUtil.getServiceName());
		AgentConfiguration.setCollectorIp(ServerConfUtil.getEtraceUrl());
	}
	
	/**
	 * 初始化独立线程
	 */
	public static void initThread() {
		IndexSequence indexSequence = context.getBean(IndexSequence.class);
		indexSequence.start();
	}

}
