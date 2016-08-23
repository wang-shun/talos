package me.ele.bpm.talos.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 与properties的工具类
 * @author yemengying
 *
 */
public class PropertiesHelper {
	
	/**
	 * 将Properties转换为Map
	 * 
	 * @param pro
	 * @return
	 */
	public static Map<String, String> convertPropertiesToMap(Properties pro){
		
		Map<String,String> map = new HashMap<String,String>();
		
		if(null == pro) return map;
		
		Enumeration<Object> keys =  pro.keys();
		
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			String value = pro.getProperty(key);
			map.put(key, value);
		}
		return map;
	}
	
	/**
	 * 加载Properties文件，创建新的Properties对象
	 * 
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static Properties createPropertiesFromProFile(InputStream input) throws IOException{
		
		if(input == null){
			return null ;
		}
		
		Properties pro = new Properties();
		pro.load(input);
		return pro ;
	}

}
