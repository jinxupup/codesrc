package com.jjb.acl.gmp.sdk;

import java.io.File;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.jjb.unicorn.batch.YakResource;

/**
 * <p>Description: 批量资源Bean工厂</p>
 * TODO 此工厂为scope="step"的step使用，不指定scope的step会出现RPC超时，有时间再解决此问题
 * <p>Company: 上海JYDATA服务有限公司</p>
 * @ClassName: BatchFileResourceFactoryBean
 * @author LI.H
 * @date 2015年11月24日 下午9:21:19
 * @version 1.0
 */
public class BatchFileResourceFactoryBean implements FactoryBean<YakResource>, InitializingBean {
	
	private final static Logger logger = LoggerFactory.getLogger(BatchFileResourceFactoryBean.class);
	
	/**
	 * <p>特殊符号$，每个路径中有且只有一个</p>
	 */
	private static final String PLACEHOLDER = "$";
	
	/**
	 * <p>特殊符号/，key值中绝对不允许出现</p>
	 */
	private static final String PLACESLASH = "/";
	
	/**
	 * <p>特殊符号\，key值中绝对不允许出现</p>
	 */
	private static final String PLACEBACKLASH = "\\";
	
	/**
	 * <p>日期占位符</p>
	 */
	private static final String DATE_PLACEHOLDER = "$yyyymmdd";
	
	/**
	 * <p>日期格式</p>
	 */
	private static final String DATE_FORMAT = "yyyyMMdd";
	
	/**
	 * <p>执行日期类型</p>
	 * <p>true为批量日期</p>
	 * <p>false为业务日期</p>
	 * <p>默认为true</p>
	 */
	private boolean batchDateFlag = true;

	private Map<String, String> mappingPlace = new HashMap<String, String>();
	
	/**
	 * <p>含有占位符文件路径</p>
	 */
	private String filePath;
	
	/**
	 * <p>批量状态</p>
	 */
	@Autowired
	private BatchStatusFacility batchStatusFacility;
	
	/**
	 * <p>Title: getObject</p> 
	 * <p>目的：根据注入的filePath经过组装日期最终生成的资源Bean</p>
	 * <p>承诺：配置文件中的filePath必须为file:协议开始</p>
	 * @return
	 * @throws Exception 
	 * @see org.springframework.beans.factory.FactoryBean#getObject() 
	 */
	@Override
	public YakResource getObject() throws Exception {
		String resourceDate = new SimpleDateFormat(DATE_FORMAT).format(batchDateFlag ? batchStatusFacility.getBatchDate() : batchStatusFacility.getSystemStatus().getBusinessDate());
		YakResource resource = new YakResource(getRealUrl(resourceDate), resourceDate);
		logger.info(resource.toString() + ", " + toString());
		return resource;
	}

	@Override
	public Class<?> getObjectType() {
		return YakResource.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	/**
	 * <p>目的：根据文件所在路径生成真实URL</p>
	 * <p>承诺：会根据batchDateFlag对filePath中DATE_PLACEHOLDER类型替换，会根据映射中的value替换filePath中的key</p>
	 * @return
	 * @throws MalformedURLException 
	 */
	private File getRealUrl(String resourceDate) throws MalformedURLException {
		String realUrl = StringUtils.replace(filePath, DATE_PLACEHOLDER, resourceDate);
		for (String key : mappingPlace.keySet()) {
			realUrl = StringUtils.replace(realUrl, key, mappingPlace.get(key));
		}
		return new File(realUrl);
	}
	
	public void setBatchDateFlag(boolean batchDateFlag) {
		this.batchDateFlag = batchDateFlag;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public Map<String, String> getMappingPlace() {
		return mappingPlace;
	}

	public void setMappingPlace(Map<String, String> mappingPlace) {
		this.mappingPlace = mappingPlace;
	}

	/**
	 * <p>Title: afterPropertiesSet</p> 
	 * <p>目的：校验输入资源路径是否合法,检查映射key值是否合法，不允许使用"/"和"\"</p>
	 * <p>承诺：有且只能有一个$符号，key值中不能含有文件分隔符</p>
	 * @throws Exception 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet() 
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		int index = filePath.indexOf(PLACEHOLDER);
		if (index != filePath.lastIndexOf(PLACEHOLDER)) {
			throw new IllegalArgumentException("资源初始化时[$]符号不存在或存在多个");
		}
		for (String key : mappingPlace.keySet()) {
			if (-1 != key.indexOf(PLACESLASH) || -1 != key.indexOf(PLACEBACKLASH)) {
				throw new IllegalArgumentException("资源初始化时提供的占位符映射key值不合法");
			}
		}
	}

	@Override
	public String toString() {
		return "当前Bean配置 [batchDateFlag=" + batchDateFlag + ", mappingPlace=" + mappingPlace.toString() + ", filePath=" + filePath + "]";
	}
	
	

}
