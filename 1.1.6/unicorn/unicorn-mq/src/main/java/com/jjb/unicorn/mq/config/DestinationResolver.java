package com.jjb.unicorn.mq.config;

public interface DestinationResolver {
	String getActualQueueName(String queueName);
}
