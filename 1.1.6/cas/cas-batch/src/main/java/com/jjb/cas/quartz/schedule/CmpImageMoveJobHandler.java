package com.jjb.cas.quartz.schedule;

import com.jjb.cas.quartz.CasImageMove;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 影像转移
 * @author wxl
 *
 */
@JobHandler(value="cmpImageMoveJobHandler")
@Service
public class CmpImageMoveJobHandler extends IJobHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
	@Autowired
	private CasImageMove casImageMove;
	
	@Override
	public ReturnT<String> execute(String param) throws Exception {
		long start = System.currentTimeMillis();
		logger.info("开始执行影像转移,"+param);

		try {
			casImageMove.imageMove();
		} catch (Exception e) {
			logger.error("执行影像转移.异常", e);
			return FAIL;
		}finally {
			logger.info("结束执行影像转移...耗时:( "+(System.currentTimeMillis()-start)+" )");
		}
		return SUCCESS;
	}
	
}
