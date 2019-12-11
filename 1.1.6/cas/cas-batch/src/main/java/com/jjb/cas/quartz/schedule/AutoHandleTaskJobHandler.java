package com.jjb.cas.quartz.schedule;

import com.jjb.cas.quartz.AutoHandleTaskQtz;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: TODO  异步发起工作流
 * @Author: shiminghong
 * @Data: 2019/7/5 11:29
 * @Version 1.0
 */

@JobHandler(value = "autoHandleTaskJobHandler")
@Service
public class AutoHandleTaskJobHandler extends IJobHandler {

    int param = 1;
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AutoHandleTaskQtz autoHandleTaskQuartz;
    @Override
    public ReturnT<String> execute(String s) {
        long start = System.currentTimeMillis();
        logger.info("开始执行第"+param+"次定时处理进件任务(状态B10)");
        try {
            autoHandleTaskQuartz.autoHandleTaskQtz();
            param++;
        }  catch (Exception e) {
            logger.info("定时处理B10状态申请件异常", e);
            return FAIL;
        }
        return SUCCESS;
    }
}
