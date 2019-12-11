package com.jjb.unicorn.socket.definition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import com.thoughtworks.xstream.XStream;

/**
 * <p>Description: 扩展子域工厂类，用来解析扩展子域</p>
 * <p>Company: 上海JYDATA服务有限公司</p>
 * @ClassName: ExtFieldDefinitionFactory
 * @author LI.H
 * @date 2015年12月8日 下午2:11:06
 * @version 1.0
 */
public class ExtFieldDefinitionFactory implements FactoryBean<ExtFieldMap>, InitializingBean {
	
	private final static Logger logger = LoggerFactory.getLogger(ExtFieldDefinitionFactory.class);

	private Resource extFieldMsg;
	
	private Map<Integer, ArrayList<ExtFieldDefinition>> extFieldDef = new HashMap<Integer, ArrayList<ExtFieldDefinition>>();
	
	private ExtFieldMap resultMap;
	
	@Override
	public ExtFieldMap getObject() throws Exception {
		return resultMap;
	}

	@Override
	public Class<?> getObjectType() {
		return ExtFieldMap.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	/**
	 * <p>Title: afterPropertiesSet</p> 
	 * <p>目的：初始化解析扩展域信息</p>
	 * <p>承诺：解析结果为Map</p>
	 * @throws Exception 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet() 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void afterPropertiesSet() throws Exception {
		logger.debug("前置初始化复杂子域配置");
		XStream xStream = new XStream();
		xStream.alias("subfield", Map.class);
		xStream.alias("index", Integer.class);
		xStream.alias("extfields", ArrayList.class);
		xStream.alias("extfield", ExtFieldDefinition.class);
		xStream.alias("subindex", String.class);
		xStream.alias("fieldDef", FieldDefinition.class);
		extFieldDef = (Map<Integer, ArrayList<ExtFieldDefinition>>) xStream.fromXML(extFieldMsg.getInputStream());
		resultMap = new ExtFieldMap(extFieldDef);
		logger.debug("前置初始化复杂子域配置完毕");
	}
	
	public void setExtFieldMsg(Resource extFieldMsg) {
		this.extFieldMsg = extFieldMsg;
	}

}
