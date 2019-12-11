package com.jjb.unicorn.cache;

import redis.clients.jedis.ShardedJedis;

/**
  *@ClassName ShardedJedisAction
  *@Description TODO
  *@Author lixing
  *Date 2019/2/18 16:13
  *Version 1.0
  */
public interface ShardedJedisAction<T> {
    T doAction(ShardedJedis var1);
}
