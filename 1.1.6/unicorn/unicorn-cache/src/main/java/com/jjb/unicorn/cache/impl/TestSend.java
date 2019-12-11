/*package com.jjb.unicorn.cache.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

import com.jjb.unicorn.cache.RedisUtil;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

*//**
 *@ClassName TestSend
 *@Description 发送交易到九江银行九江综合前置测试
 *@Author lixing
 *Date 2018/10/17 20:16
 *Version 1.0
 *//*
@ContextConfiguration("/redis-context.xml")
public class TestSend {



	@Autowired
	private RedisUtil util;

	@Test
	public void getTnty(){

		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(6000);
		List<JedisShardInfo> shards = new ArrayList<>();
		JedisShardInfo j =new JedisShardInfo("10.109.3.220","6379");
		j.setPassword("credit_redis");
		shards.add(j);
		ShardedJedisPool ShardedJedisPool = new ShardedJedisPool(jedisPoolConfig,shards);
		ShardedJedis shardedJedis = ShardedJedisPool.getResource();
//		shardedJedis.hkeys("allBranch27701");
		shardedJedis.del("allBranch22*");




	}



}
*/