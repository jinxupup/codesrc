package com.jjb.ecms.adapter.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jjb.ecms.service.api.QueryService;
import com.jjb.ecms.service.dto.T1000.T1000Req;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.util.StringUtils;


/**
 * 
 * @Description: 案件自动分配
 * @author JYData-R&D-HN
 * @date 2018年10月23日 下午13:36:12
 * @version V1.0
 */
@Component
public class AutoHeadersQuartz{

	private static Logger logger = LoggerFactory.getLogger(AutoHeadersQuartz.class);
	
	@Value("#{env['assignQuartzIp'?:'']}")
	private String assignQuartzIp;
	private static int counter = 0;
	@Autowired
	private QueryService queryService;
	
	protected void execute() {
		
		OrganizationContextHolder.setOrg("000064540000");
		
		long ms = System.currentTimeMillis();
		logger.info("Quartz开始 第" + "(" + counter + ")"+ "次 尴尬的自行发送同步心跳......开始");
		T1000Req req = new T1000Req();
		req.setOrg("000064540000");
		req.setTokenId(StringUtils.valueOf(ms));
		req.setAppNo("2018100000000000");
		
		queryService.T1000(req);
		
		logger.info("Quartz结束 第" + "(" + counter + ")" + "次 尴尬的自行发送同步心跳......结束耗时["
				+ (System.currentTimeMillis() - ms) + "]");
		counter++;
	}
}