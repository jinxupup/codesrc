package com.jjb.acl.gmp.sdk;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.jjb.unicorn.mq.config.AmqpInvokerServiceExporter;

/**
 * 可视为 {@link AmqpInvokerServiceExporter}的GMP定制版，尽最大可能简化配置
 * @author LI.J
 *
 */
public class ServiceExporter extends AmqpInvokerServiceExporter implements InitializingBean
{
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	@Autowired
	private GMPMessageConverter messageConverter;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		
		setAmqpTemplate(amqpTemplate);
		setMessageConverter(messageConverter);
	}
}
