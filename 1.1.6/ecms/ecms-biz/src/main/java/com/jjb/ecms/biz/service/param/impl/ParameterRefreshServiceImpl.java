package com.jjb.ecms.biz.service.param.impl;

import javax.annotation.Resource;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.service.param.ParameterRefreshService;

@Service("parameterRefreshService")
public class ParameterRefreshServiceImpl implements ParameterRefreshService{

	@Resource
	private RabbitTemplate ecmsParameterRefreshExchange;
	/**
	 * 初始化所有的参数缓存
	 */
	@Override
	@Transactional
	public void refreshParam(){
		ecmsParameterRefreshExchange.convertAndSend(new Object());
	}
}
