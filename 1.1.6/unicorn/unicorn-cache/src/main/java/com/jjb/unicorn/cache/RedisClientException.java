package com.jjb.unicorn.cache;
/**
  *@ClassName RedisClientException
  *@Description redis异常
  *@Author lixing
  *Date 2019/2/18 17:49
  *Version 1.0
  */
public class RedisClientException extends RuntimeException {
    private static final long serialVersionUID = 7460934076911268418L;

    public RedisClientException(String msg) {
        super(msg);
    }

    public RedisClientException(Throwable exception) {
        super(exception);
    }

    public RedisClientException(String mag, Exception exception) {
        super(mag, exception);
    }
}
