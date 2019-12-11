package com.jjb.cas.quartz.schedule;

import com.jjb.cas.quartz.AutoSaveMirCardQuartz;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 定时短信发送处理
 * @author hp
 *
 */
@JobHandler(value="saveMirCardJobHandler")
@Service
public class SaveMirCardJobHandler extends IJobHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());
	private static final long serialVersionUID = 1L;
	@Autowired
	private AutoSaveMirCardQuartz autoSaveMirCardQuartz;


	
	@Override
	public ReturnT<String> execute(String param) throws Exception {
		long start = System.currentTimeMillis();
		logger.info("开始执行已申请卡数据保存,"+param);
		try {
			//autoSaveMirCardQuartz.mirCardMessageSave();
			//private Logger logger = LoggerFactory.getLogger(getClass());

			autoSaveMirCardQuartz.mirCardMessageSave();
		} catch (Exception e) {
			logger.error("定时保存已申请卡数据异常", e);
			return FAIL;
		}finally {
			logger.info("结束执行定时保存已申请卡数据,耗时("+(System.currentTimeMillis()-start)+")");
		}
		return SUCCESS;
	}
	
}
