package me.ele.bpm.talos.redis.client;

import me.ele.bpm.talos.redis.exception.SystemExceptionCode;
import me.ele.contract.exception.SystemException;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisClient implements IRedisClient {
	
	private Log logger = LogFactory.getLog(RedisClient.class);

	public JedisPool jedisPool;
	public String ENV;  //环境变量
	
	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
	public void setENV(String env) {
		ENV = env + "_";
	}

	@Override
	public String get(String key) {
		Jedis client = jedisPool.getResource();
		String ret;
		try {
			ret = client.get(ENV + key);
		} catch (Exception e) {
			logger.error("redis error:", e);
			throw new SystemException(SystemExceptionCode.SYSTEM_REDIS_CLIENT_CREATE_FAILED.getDec(), e);
		} finally {
			jedisPool.returnResource(client);// 向连接池“归还”资源
		}
		return ret;
	}

	@Override
	public String set(String key, String value) {
		Jedis client = jedisPool.getResource();
		String ret;
		try {
			ret = client.set(ENV + key, value);
		} catch (Exception e) {
			logger.error("redis error:", e);
			throw new SystemException(SystemExceptionCode.SYSTEM_REDIS_CLIENT_CREATE_FAILED.getDec(), e);
		} finally {
			jedisPool.returnResource(client);// 向连接池“归还”资源
		}
		return ret;
	}
	
	@Override
	public String rpop(String key) {
		Jedis client = jedisPool.getResource();
		String ret;
		try {
			ret = client.rpop(ENV + key);
		} catch (Exception e) {
			logger.error("redis error:", e);
			throw new SystemException(SystemExceptionCode.SYSTEM_REDIS_CLIENT_CREATE_FAILED.getDec(), e);
		} finally {
			jedisPool.returnResource(client);// 向连接池“归还”资源
		}
		return ret;
	}
	
	@Override
	public Long lpush(String key, String... values) {
		Jedis client = jedisPool.getResource();
		Long ret;
		try {
			ret = client.lpush(ENV + key, values);
		} catch (Exception e) {
			logger.error("redis error:", e);
			throw new SystemException(SystemExceptionCode.SYSTEM_REDIS_CLIENT_CREATE_FAILED.getDec(), e);
		} finally {
			jedisPool.returnResource(client);// 向连接池“归还”资源
		}
		return ret;
	}
	
}
