package com.jjb.cas.quartz.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjb.cas.quartz.AutoAssigend;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

/**
 * 定时分配任务处理
 * @author hp
 *
 */
@JobHandler(value="autoAssigendJobHandler")
@Service
public class AutoAssigendJobHandler extends IJobHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
	@Autowired
	private AutoAssigend assigend;
	
	@Override
	public ReturnT<String> execute(String param) throws Exception {
		long start = System.currentTimeMillis();
		logger.info("开始执行自动分配,"+param);
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
			assigend.autoAssignee();
		} catch (Exception e) {
			logger.error("定时分配异常", e);
			return FAIL;
		}finally {
			logger.info("结束执行自动分配,耗时("+(System.currentTimeMillis()-start)+")");
		}

		return SUCCESS;
	}
	
}
