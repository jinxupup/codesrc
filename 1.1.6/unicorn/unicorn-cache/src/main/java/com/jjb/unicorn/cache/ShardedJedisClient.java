package com.jjb.unicorn.cache;
/**
  *@ClassName ShardedJedisClient
  *@Description TODO
  *@Author lixing
  *Date 2019/2/18 17:02
  *Version 1.0
  */
public interface ShardedJedisClient {
    void destroy();

    <T> T execute(ShardedJedisAction<T> var1);
}
