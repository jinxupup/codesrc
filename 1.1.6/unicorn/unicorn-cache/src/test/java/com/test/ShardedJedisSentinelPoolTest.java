package com.test;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.jjb.unicorn.cache.config.ShardedJedisSentinelPool;

import junit.framework.TestCase;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class ShardedJedisSentinelPoolTest extends TestCase {

	public void testX() throws Exception {
		
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		String masters = "shard1,shard2";
		
		String sentinels = "10.109.3.205:17001,10.109.3.204:17001,10.109.3.206:17001";
    
		ShardedJedisSentinelPool pool = new ShardedJedisSentinelPool(masters, sentinels, config, 60000);
		
		ShardedJedis jedis = null;
		try {
			jedis = pool.getResource();
			// do somethind...
			// ...
		} finally {
			if (jedis != null) pool.returnResource(jedis);
			pool.destroy();
		}
		
		ShardedJedis j = null;
		for (int i = 0; i < 100; i++) {
			try {
				j = pool.getResource();
				j.set("KEY: " + i, "" + i);
				System.out.print(i);
				System.out.print(" ");
				Thread.sleep(500);
				pool.returnResource(j);
			} catch (JedisConnectionException e) {
				System.out.print("x");
				i--;
				Thread.sleep(1000);
			}
		}
    
		System.out.println("");
    
		for (int i = 0; i < 100; i++) {
			try {
				j = pool.getResource();
				assertEquals(j.get("KEY: " + i), "" + i);
				System.out.print(".");
				Thread.sleep(500);
				pool.returnResource(j);
			} catch (JedisConnectionException e) {
				System.out.print("x");
				i--;
				Thread.sleep(1000);
			}
		}

		pool.destroy();
	}
}
