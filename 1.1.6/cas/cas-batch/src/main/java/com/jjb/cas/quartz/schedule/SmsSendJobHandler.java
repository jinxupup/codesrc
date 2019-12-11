package com.jjb.cas.quartz.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjb.cas.quartz.AutoSmsSendQuartz;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

/**
 * 定时短信发送处理
 * @author hp
 *
 */
@JobHandler(value="smsSendJobHandler")
@Service
public class SmsSendJobHandler extends IJobHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
	@Autowired
	private AutoSmsSendQuartz autoSmsSendQuartz;
	
	@Override
	public ReturnT<String> execute(String param) throws Exception {
		long start = System.currentTimeMillis();
		logger.info("开始执行定时短信发送,"+param);
//		// 分片参数
//		ShardingUtil.ShardingVO shardingVO = ShardingUtil.getShardingVo();
//		XxlJobLogger.log("分片参数：当前分片序号 = {}, 总分片数 = {}", shardingVO.getIndex(), shardingVO.getTotal());
//		// 业务逻辑
//		for (int i = 0; i < shardingVO.getTotal(); i++) {
//			if (i == shardingVO.getIndex()) {
//				XxlJobLogger.log("第 {} 片, 命中分片开始处理", i);
//			} else {
//				XxlJobLogger.log("第 {} 片, 忽略", i);
//			}
//		}
		try {
			autoSmsSendQuartz.messageSend();
		} catch (Exception e) {
			logger.error("定时发送短信异常", e);
			return FAIL;
		}finally {
			logger.info("结束执行定时短信发送,,耗时("+(System.currentTimeMillis()-start)+")");
		}
		return SUCCESS;
	}
	
}
