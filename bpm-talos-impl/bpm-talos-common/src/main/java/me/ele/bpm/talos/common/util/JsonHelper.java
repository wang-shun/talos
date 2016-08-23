package me.ele.bpm.talos.common.util;

import org.codehaus.jackson.map.ObjectMapper;

public class JsonHelper {

	private static ObjectMapper mapper = new ObjectMapper();
	
	public static ObjectMapper getMapper() {
		return mapper;
	}
}
