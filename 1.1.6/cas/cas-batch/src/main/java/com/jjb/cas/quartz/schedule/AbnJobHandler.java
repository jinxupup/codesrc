package com.jjb.cas.quartz.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjb.cas.quartz.AutoAbnQuartz;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

/**
 * 异常件定时处理
 * @author hp
 *
 */
@JobHandler(value="abnJobHandler")
@Service
public class AbnJobHandler extends IJobHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
	@Autowired
	private AutoAbnQuartz autoAbnQuartz;
	
	@Override
	public ReturnT<String> execute(String param) throws Exception {
		
		long start = System.currentTimeMillis();
		logger.info("开始执行异常件处理,"+param);
		try {
			autoAbnQuartz.abnApplyChangeRtfState();
		} catch (Exception e) {
			logger.error("异常件状态修改处理异常", e);
		}
		try {
			autoAbnQuartz.abnApplyOnlineMarkCard();
		} catch (Exception e) {
			logger.error("异常件重新制卡处理异常", e);
			return FAIL;
		}
		finally {
			logger.info("结束执行异常件处理,耗时("+(System.currentTimeMillis()-start)+")");
		}

		return SUCCESS;
	}
	
}
