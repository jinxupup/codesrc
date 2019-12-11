package com.jjb.unicorn.facility.config;

import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.support.PropertiesLoaderSupport;

public class UnicornEnvironmentFactoryBean extends PropertiesLoaderSupport implements FactoryBean<Properties> {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Properties getObject() throws Exception {
		System.out.println("开始执行获取环境变量");
		Properties props = new Properties();

		Map<String, String> env = System.getenv();

		if (StringUtils.isNotEmpty(env.get("instanceName"))) {
			logger.info("发现 instanceName环境变量，开始从环境变量取实例配置。");
			props.putAll(System.getenv());

		} else {
			// 直接从配置的本地文件取值
			loadProperties(props);
		}
		// 命令行上的参数更优先
		props.putAll(System.getProperties());
		System.out.println("环境变量:" + props);
		return props;
	}

	@Override
	public Class<?> getObjectType() {
		return Properties.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
