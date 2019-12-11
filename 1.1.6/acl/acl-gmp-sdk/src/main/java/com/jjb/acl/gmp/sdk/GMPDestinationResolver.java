package com.jjb.acl.gmp.sdk;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.StringUtils;

import com.jjb.acl.gmp.api.GlobalManagementService;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.mq.config.DestinationResolver;

public class GMPDestinationResolver implements DestinationResolver
{
	private Logger logger = LoggerFactory.getLogger(getClass());

	private GlobalManagementService globalManagementService;

	/**
	 * 获取实际的带实例名的队列名
	 * @param queueName
	 * @return
	 */
	@Override
	public String getActualQueueName(String queueName)
	{
		String org = OrganizationContextHolder.getOrg();
		assert org != null : "RPC前必须先指机构上下文";
		Map<String, String> map = getInstanceRoute().get(org);
		if (map == null)
			throw new IllegalArgumentException("无法找到机构信息：" + org);
		//获取前缀
		String names[] = StringUtils.split(queueName, ".");
		if (names == null)
			throw new IllegalArgumentException("无效队列名:" + queueName);
		if (!map.containsKey(names[0]))
			throw new IllegalArgumentException("无法找到前缀:" + names[0]);
		String target = map.get(names[0]) + "." + queueName;
		return target;
	}

	@Cacheable("instanceRouteCache")
	public Map<String, Map<String, String>> getInstanceRoute()
	{
		return globalManagementService.getInstanceRoute();
	}
	
	/**
	 * 清缓存接口，为了使用listener-adapter，必须有一个参数，但为了适应缓存接口，所以要显式指定allEntries=true
	 * @param dummy
	 */
	@CacheEvict(value="instanceRouteCache", allEntries=true)
	public void clearInstanceRoute(Object dummy)
	{
		logger.info("刷新实例路由");
	}

	@Required
	public void setGlobalManagementService(GlobalManagementService globalManagementService) {
		this.globalManagementService = globalManagementService;
	}
	
}
