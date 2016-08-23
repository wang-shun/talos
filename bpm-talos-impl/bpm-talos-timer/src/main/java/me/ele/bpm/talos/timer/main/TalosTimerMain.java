package me.ele.bpm.talos.timer.main;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TalosTimerMain {

	private static Log logger = LogFactory.getLog(TalosTimerMain.class);

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		logger.info("[Talos定时器，开始启动！]");
		initSpringContext();
		long end = System.currentTimeMillis();
		logger.info("[Talos定时器，启动成功！用时：{}毫秒]", end - start);
	}

	// 初始化spring容器
	private static ApplicationContext initSpringContext() {
		return new ClassPathXmlApplicationContext(
				"classpath:bpm-talos-timer.xml");
	}

}
