package com.jjb.unicorn.cache.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjb.unicorn.cache.RedisClientException;
import com.jjb.unicorn.cache.ShardedJedisAction;
import com.jjb.unicorn.cache.ShardedJedisClient;
import com.jjb.unicorn.cache.config.ShardedJedisSentinelPool;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
/**
 * 
 * @author hp
 *
 */

@Service
public class ShardedJedisClientImpl implements ShardedJedisClient {

	private static final Logger logger = LoggerFactory.getLogger(ShardedJedisClientImpl.class);

	@Autowired
	private ShardedJedisSentinelPool pool;

	public void destroy() {
		pool.destroy();
	}

	public <T> T execute(ShardedJedisAction<T> action) {
		try {
			T result = this.executeAction(action);
			return result;
		} catch (Exception var4) {
			logger.error("访问Redis异常", var4);
			throw new RedisClientException(var4);
		}
	}

	private <T> T executeAction(ShardedJedisAction<T> action) {

		ShardedJedis shardedJedis = null;
		T var;
		try {
			shardedJedis = pool.getResource();
			var = action.doAction(shardedJedis);
		} catch (JedisConnectionException jedisConnectionException) {
			logger.error("连接Redis异常", jedisConnectionException);
			if (shardedJedis != null) {
				try {
					shardedJedis.setDataSource(pool);
					shardedJedis.close();
				} catch (Exception e) {
					logger.warn("Can not return broken resource.", e);
				}
			}
			throw jedisConnectionException;
		} finally {
			if (shardedJedis != null) {
				try {
					shardedJedis.setDataSource(pool);
					shardedJedis.close();
				} catch (Exception exception) {
					logger.warn("Can not return resource.", exception);
				}
			}
		}
		return var;
	}

}
