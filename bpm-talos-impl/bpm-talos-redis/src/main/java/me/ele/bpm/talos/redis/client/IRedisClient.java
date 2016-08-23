package me.ele.bpm.talos.redis.client;

public interface IRedisClient {

	public String get(String key);
	
	public String set(String key, String value);

	public String rpop(String key);
	
	public Long lpush(String key, String...values);

}