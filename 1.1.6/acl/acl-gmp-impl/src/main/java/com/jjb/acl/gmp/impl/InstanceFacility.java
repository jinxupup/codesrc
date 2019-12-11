package com.jjb.acl.gmp.impl;

import java.util.LinkedHashMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jjb.acl.biz.gmp.TmInstEnvDao;
import com.jjb.acl.biz.gmp.TmInstanceDao;
import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.acl.infrastructure.TmInstEnv;
import com.jjb.acl.infrastructure.TmInstance;
import com.jjb.unicorn.facility.util.AESCryptoConverter;

@Service
public class InstanceFacility {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

/*	@Autowired
	private RTmInstance rInstance;

	@Autowired
	private RTmInstEnv rInstEnv;*/
	@Autowired
	private TmInstEnvDao tmInstEnvDao;
	@Autowired
    private TmInstanceDao  tmInstanceDao;

	@Value("#{env['mqAddresses']}")
	private String mqAddresses;

	@Value("#{env['mqVHost']?:'/'}")
	private String mqVHost;
	
	@Value("#{env['mqUser']?:'guest'}")
	private String mqUser;
	
	@Value("#{env['mqPassword']?:'guest'}")
	private String mqPassword;

	public LinkedHashMap<String, String> loadInstanceEnvironment(int instanceId)
	{
		logger.info("取实例定义[{}]的环境配置", instanceId);
		
		TmInstance instance = loadInstance(instanceId);
		
		LinkedHashMap<String, String> props = new LinkedHashMap<String, String>();
		
		//先写入instanceName及MQ相关属性，省得每个都要配置，并且使得这些配置可以被实例上的属性覆盖
		props.put("instanceName", instance.getInstanceName());
		props.put("instanceType", StringUtils.lowerCase(instance.getSystemType()));

		props.put("mqAddresses", mqAddresses);
		props.put("mqVHost", mqVHost);
		props.put("mqUser", mqUser);
		props.put("mqPassword", mqPassword);

        for (TmInstEnv env : tmInstEnvDao.selectcByInstanceId(instance.getInstanceId()))
		{
			String value = env.getPropValue();
			if (env.getMaskValue() ==Indicator.Y.toString())
			//	value = new String(Base64.decodeBase64(value));
            {
                value = new String(AESCryptoConverter.decrypt(value));  //AES 128解密
            }

			if (logger.isDebugEnabled())
				logger.debug("[{}]取回配置 {} = {}", instance.getInstanceId(), env.getPropKey(), (env.getMaskValue() == Indicator.Y.toString() ? "******" : value));
			
			props.put(env.getPropKey(), value);
		}
		
		
		return props;

	}
	
	private TmInstance loadInstance(int instanceId) {
		TmInstance instance = tmInstanceDao.selectByInstanceId(instanceId);
		if (instance == null)
			throw new IllegalArgumentException("无效实例号：" + instanceId);
		return instance;
	}

}
