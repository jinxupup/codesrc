package com.jjb.unicorn.cache;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.ShardedJedis;

/**
 * @ClassName RedisUtil
 * @Description 多个Shard的Redis工具类,redis常用的5种数据结构 string,hash.list,set,zset(sorted
 *              set); jedis也是对5种数据类型和value的操作，总共6类操作方法
 * @Author lixing Date 2019/2/18 18:52 Version 1.0
 */
@Component
public class RedisUtil implements Serializable{
	
	private static final long serialVersionUID = 1L;

	// 定义缓存客户端
	@Autowired
	protected ShardedJedisClient shardedClient;

//    @Autowired
//    private RedisTemplate<Serializable, Object> redisTemplate;  

//	{
//		// 读取缓存配置文件
//		shardedClient = new ShardedJedisClientImpl();
//	}

	/**
	 * @Author lixing
	 * @Description 获取jedisClient
	 * @Date 2019/2/19 14:48
	 */
//	public final ShardedJedisClientImpl getjedisClient() {
//		return shardedClient;
//	}

	/**
	 * @Author lixing
	 * @Description 缓存的delete方法， 对value的操作
	 * @Date 2019/2/19 14:46
	 */
	public final Long del(final String key) {
		return shardedClient.execute(shardedJedis -> shardedJedis.del(key));
	}

	/**
	 * @Author hejn
	 * @Description 通过byte数据类型的可以缓存的delete方法， 对value的操作
	 * @Date 2019/2/26 14:46
	 */
	public final Long delByByteKey(final String key) {
		return shardedClient.execute(shardedJedis -> shardedJedis.del(key.getBytes()));
	}

	/**
	 * @Author lixing
	 * @Description 缓存的set方法，对string数据类型操作
	 * @Date 2019/2/19 14:42
	 */
	public final String set(final String key, final String value) {
		return shardedClient.execute(shardedJedis -> shardedJedis.set(key, value));
	}

	/**
	 * @Author lixing
	 * @Description 缓存的set方法，对string数据类型操作
	 * @Date 2019/2/19 14:43
	 */
	public final String set(final String key, final int seconds, final String value) {
		return shardedClient.execute(shardedJedis -> shardedJedis.setex(key, seconds, value));
	}

	/**
	 * @Author lixing
	 * @Description 缓存的get方法，对string数据类型操作
	 * @Date 2019/2/19 14:45
	 */
	public final String get(final String key) {
		return shardedClient.execute(shardedJedis -> shardedJedis.get(key));
	}
	/**
	 * @Author hejn
	 * @Description 缓存的get方法，对byte数据类型操作
	 * @Date 2019/2/19 14:45
	 */
	public final byte[] getByByte(final String key) {
		return shardedClient.execute(shardedJedis -> shardedJedis.get(key.getBytes()));
	}

	/**
	 * @Author lixing
	 * @Description 在名称为key的list尾添加一个值为value的元素 对list的操作
	 * @Date 2019/2/19 14:45
	 */
	public final Long rpush(final String key,final String value) {
		return shardedClient.execute(shardedJedis -> shardedJedis.rpush(key,value));
	}

	/**
	 * @Author lixing
	 * @Description 在名称为key的list头添加一个值为value的元素 对list的操作
	 * @Date 2019/2/19 14:45
	 */
	public final Long lpush(final String key,String value) {
		return shardedClient.execute(shardedJedis -> shardedJedis.lpush(key,value));
	}
	/**
	 * @Author hejn
	 * @Description 取得某个区间内list元素；<br>获取全部lrange('KEY',0,-1)
	 * @Date 2019/3/19 14:45
	 */
	public final List<String> lrange(final String key,final int start ,final int end) {
		return shardedClient.execute(new ShardedJedisAction<List<String>>() {
			@Override
			public List<String> doAction(ShardedJedis shardedJedis) {
				return shardedJedis.lrange(key, start, end);
			}
		});
	}

	/**
	 * @Author lixing
	 * @Description 向名称为key的元素中添加元素member 对set操作
	 * @Date 2019/2/25 16:28
	 */
	public final Long sadd(final String key) {
		return shardedClient.execute(shardedJedis -> shardedJedis.sadd(key));
	}

	/**
	 * @Author lixing
	 * @Description 从zset中删除单个元素，对sorted set操作
	 * @Date 2019/2/19 14:46
	 */
	public final Long zrem(final String key, final String member) {
		return shardedClient.execute(shardedJedis -> shardedJedis.zrem(key, member));
	}

	/**
	 * @Author lixing
	 * @Description 从zset中根据score删除元素 对sorted set操作
	 * @Date 2019/2/25 16:12
	 */
	public final Long zremrangeByScore(final String key, final Double start, final Double end) {
		return shardedClient.execute(new ShardedJedisAction<Long>() {
			@Override
			public Long doAction(ShardedJedis shardedJedis) {
				return shardedJedis.zremrangeByScore(key, start, end);
			}
		});
	}

	/**
	 * @Author lixing
	 * @Description 从zset中查询 对sorted set操作
	 * @Date 2019/2/25 16:13
	 */
	public final Set<String> zrangeByScore(final String key, final double min, final double max,
			final int offset, final int count) {
		return shardedClient.execute(new ShardedJedisAction<Set<String>>() {
			@Override
			public Set<String> doAction(ShardedJedis shardedJedis) {
				return shardedJedis.zrangeByScore(key, min, max, offset, count);
			}
		});
	}

	/**
	 * @Author lixing
	 * @Description 有序集合添加元素 将一个member 元素及其 score 值加入到有序集 key 当中 对sorted set操作
	 * @Date 2019/2/25 16:13
	 */
	public final Long zadd(final String key, final double score, final String member) {
		return shardedClient.execute(new ShardedJedisAction<Long>() {
			@Override
			public Long doAction(ShardedJedis shardedJedis) {
				return shardedJedis.zadd(key, score, member);
			}
		});
	}

	/**
	 * @Author lixing
	 * @Description 被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员 功能描述: 有序集合添加元素 将多个member 元素及其
	 *              score 值加入到有序集 key 当中 对sorted set操作
	 * @Date 2019/2/25 16:15
	 */
	public final Long zadd(final String key, final Map<String, Double> scoreMembers) {
		return shardedClient.execute(new ShardedJedisAction<Long>() {
			@Override
			public Long doAction(ShardedJedis shardedJedis) {
				return shardedJedis.zadd(key, scoreMembers);
			}
		});
	}

	/**
	 * @Author lixing
	 * @Description zset数量统计，对sorted set操作
	 * @Date 2019/2/25 16:16
	 */
	public final Long zcard(final String key) {
		return shardedClient.execute(new ShardedJedisAction<Long>() {
			@Override
			public Long doAction(ShardedJedis shardedJedis) {
				return shardedJedis.zcard(key);
			}
		});
	}

	/**
	 * @Author lixing
	 * @Description zset查询单个元素score 对sorted set操作
	 * @Date 2019/2/25 16:16
	 */
	public final Double zscore(final String key, final String member) {
		return shardedClient.execute(new ShardedJedisAction<Double>() {
			@Override
			public Double doAction(ShardedJedis shardedJedis) {
				return shardedJedis.zscore(key, member);
			}
		});
	}

	/**
	 * @Author lixing
	 * @Description hget查询元素 对hash操作
	 * @Date 2019/2/25 16:16
	 */
	public final String hget(final String key, final String field) {
		return shardedClient.execute(new ShardedJedisAction<String>() {
			@Override
			public String doAction(ShardedJedis shardedJedis) {
				return shardedJedis.hget(key, field);
			}
		});
	}
	
	public final byte[] hget(final String key, final byte[] field) {
		return shardedClient.execute(shardedJedis -> shardedJedis.hget(key.getBytes(), field));
	}
	
	/**
	 * @Author lixing
	 * @Description hget查询元素 对hash操作
	 * @Date 2019/2/25 16:17
	 */
	public final Map<String, String> hget(final String key) {
		return shardedClient.execute(new ShardedJedisAction<Map<String, String>>() {
			@Override
			public Map<String, String> doAction(ShardedJedis shardedJedis) {
				return shardedJedis.hgetAll(key);
			}
		});
	}
	/**
	 * @Author hejn
	 * @Description hget查询元素 对byte数据类型的hash操作
	 * @Date 2019/2/25 16:17
	 */
	public final Map<byte[], byte[]> hgetByByte(final String key) {
		return shardedClient.execute(new ShardedJedisAction<Map<byte[], byte[]>>() {
			@Override
			public Map<byte[], byte[]> doAction(ShardedJedis shardedJedis) {
				return shardedJedis.hgetAll(key.getBytes());
			}
		});
	}

	/**
	 * @Author lixing
	 * @Description hset增加元素 对hash操作
	 * @Date 2019/2/25 16:17
	 */
	public final Long hset(final String key, final String field, final String value) {
		return shardedClient.execute(new ShardedJedisAction<Long>() {
			@Override
			public Long doAction(ShardedJedis shardedJedis) {
				return shardedJedis.hset(key, field, value);
			}
		});
	}
	public final Long hsetnx(final String key, final String field, final byte [] value) {
		return shardedClient.execute(new ShardedJedisAction<Long>() {
			@Override
			public Long doAction(ShardedJedis shardedJedis) {
				return shardedJedis.hsetnx(key.getBytes(), field.getBytes(), value);
			}
		});
	}

	/**
	 * @Author lixing
	 * @Description hset删除元素 对hash操作
	 * @Date 2019/2/25 16:18
	 */
	public final Long hdel(final String key, final String... fields) {
		return shardedClient.execute(new ShardedJedisAction<Long>() {
			@Override
			public Long doAction(ShardedJedis shardedJedis) {
				return shardedJedis.hdel(key, fields);
			}
		});
	}

	/**
	 * @Author lixing
	 * @Description hmset添加 对hash操作
	 * @Date 2019/2/25 16:18
	 */
	public final String hmset(String key, Map<String, String> hash) {
		return shardedClient.execute(new ShardedJedisAction<String>() {
			@Override
			public String doAction(ShardedJedis shardedJedis) {
				return shardedJedis.hmset(key, hash);
			}
		});
	}

	/**
	 * @Author hejn
	 * @Description hmset添加 对hash操作,hash 数据类型为byte
	 * @Date 2019/2/26 16:18
	 */
	public final String hmsetByte(String byteKey, Map<byte[], byte[]> hash) {
		return shardedClient.execute(new ShardedJedisAction<String>() {
			@Override
			public String doAction(ShardedJedis shardedJedis) {
				return shardedJedis.hmset(byteKey.getBytes(), hash);
			}
		});
	}

	/**
	 * @Author hejn
	 * @Description添加序列化对象
	 * @param byteKey
	 * @param bytesValue
	 * @return
	 * @Date 2019/2/26 16:18
	 */
	public final String setByte(final String byteKey, final byte[] bytesValue) {
		return shardedClient.execute(new ShardedJedisAction<String>() {
			@Override
			public String doAction(ShardedJedis shardedJedis) {
				return shardedJedis.set(byteKey.getBytes(), bytesValue);
			}
		});
	}

}
