package com.jjb.cas.quartz.schedule;

import com.jjb.cas.quartz.AutoPreCheckJobQuartz;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: TODO 当多卡同申的时候父类卡预审通过时 其它的卡自动预审通过
 * @Author: shiminghong
 * @Data: 2019/9/16 14:43
 * @Version 1.0
 */

@JobHandler(value = "autoPreCheckJobHandler")
@Service
public class AutoPreCheckJobHandler extends IJobHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AutoPreCheckJobQuartz autoPreCheckJobQuartz;

    @Override
    public ReturnT<String> execute(String s) {
        long start = System.currentTimeMillis();
        logger.info("多卡重申时，处理预审通过件开始");
        try {
            autoPreCheckJobQuartz.autoPreCheckJobQuartz();
        } catch (Exception e) {
            logger.info("多卡重申时，处理预审通过件异常", e);
            return FAIL;
        }
        return SUCCESS;
    }
}
