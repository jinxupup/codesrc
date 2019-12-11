package com.jjb.ecms.service.api;

import com.jjb.ecms.service.dto.Trans0005.Trans0005Req;
import com.jjb.ecms.service.dto.Trans0005.Trans0005Resp;
import com.jjb.unicorn.facility.meta.RPCQueueName;

/**
  *@ClassName MessageSendService
  *@Description 短信发送消息队列接口
  *@Author lixing
  *Date 2018/10/14 16:28
  *Version 1.0
  */
@RPCQueueName("ecms.rpc.messageSendService")
public interface MessageSendService {

    /**
     * @Author lixing
     * @Description 发送短信到综合前置
     * @Date 2018/10/14 16:33
     */
    Trans0005Resp Trans0005(Trans0005Req req) throws Exception;
}
