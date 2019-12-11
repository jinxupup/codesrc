package com.jjb.cas.quartz;

import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.service.api.AutoHandleTaskQuartzService;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author: shiminghong
 * @Data: 2019/7/5 11:44
 * @Version 1.0
 */
@Component
public class AutoHandleTaskQtz implements Serializable {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AppCommonUtil appCommonUtil;
    @Autowired
    private AutoHandleTaskQuartzService autoHandleTaskQuartzService;

    public String autoHandleTaskQtz() {
        try {
            //指定机构信息，避免GMPDestinationResolver类中的getActualQueueName方法报错
            appCommonUtil.setOrg(OrganizationContextHolder.getOrg());
            autoHandleTaskQuartzService.autoHandleTaskQuartz();
        } catch (Exception e) {
            logger.info("定时处理B10状态申请件异常", e);
            return "处理失败";
        }
        return "处理成功";
    }
}
