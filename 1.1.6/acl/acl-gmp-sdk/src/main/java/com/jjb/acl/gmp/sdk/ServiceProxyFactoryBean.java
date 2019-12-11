package com.jjb.acl.gmp.sdk;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.jjb.unicorn.mq.config.AmqpInvokerClientFactoryBean;

/**
 * 可视为 {@link AmqpInvokerClientFactoryBean}的GMP定制版，尽最大可能简化配置
 * @author LI.J
 *
 */
public class ServiceProxyFactoryBean<T> extends AmqpInvokerClientFactoryBean<T>
{
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	@Autowired
	private GMPDestinationResolver destinationResolver;

	private boolean global = false;
	
	@PostConstruct
	public void init() throws Exception {
		setAmqpTemplate(amqpTemplate);
		if (!global)
			setDestinationResolver(destinationResolver);
	}

	public boolean isGlobal() {
		return global;
	}

	public void setGlobal(boolean global) {
		this.global = global;
	}
}
