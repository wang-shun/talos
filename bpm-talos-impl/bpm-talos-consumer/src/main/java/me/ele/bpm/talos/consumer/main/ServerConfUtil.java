package me.ele.bpm.talos.consumer.main;

/**
 * 用于加载动态配置
 * @author jianming.zhou
 *
 */
public class ServerConfUtil {
	
	/** 服务名称，默认AppId */
	private static String serviceName = "bpm.talos.core";
	/** 集群 */
	private static String group = "overall";
	/** etraceUrl */
	private static String etraceUrl;
	
	public static String getServiceName() {
		return serviceName;
	}
	public static void setServiceName(String serviceName) {
		ServerConfUtil.serviceName = serviceName;
	}
	public static String getGroup() {
		return group;
	}
	public static void setGroup(String group) {
		ServerConfUtil.group = group;
	}
	public static String getEtraceUrl() {
		return etraceUrl;
	}
	public static void setEtraceUrl(String etraceUrl) {
		ServerConfUtil.etraceUrl = etraceUrl;
	}

}
