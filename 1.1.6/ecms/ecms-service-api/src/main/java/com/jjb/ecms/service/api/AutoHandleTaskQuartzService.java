package com.jjb.ecms.service.api;

import com.jjb.unicorn.facility.meta.RPCQueueName;

/**
 * @Description: TODO 定时处理进件任务
 * @Author: shiminghong
 * @Data: 2019/7/8 9:36
 * @Version 1.0
 */

@RPCQueueName("cas.rpc.autoHandleTaskQuartzService")
public interface AutoHandleTaskQuartzService {

    /**
    * @Author:shiminghong
    * @Description :
    */
    void autoHandleTaskQuartz();
}
