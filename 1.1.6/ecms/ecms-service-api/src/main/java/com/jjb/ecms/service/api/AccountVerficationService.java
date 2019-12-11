package com.jjb.ecms.service.api;

import com.jjb.ecms.service.dto.Trans0004.Trans0004Req;
import com.jjb.ecms.service.dto.Trans0004.Trans0004Resp;
import com.jjb.unicorn.facility.meta.RPCQueueName;

/**
 *@ClassName AccountVerficationService
 *@Description 约定还款账户验证
 *@Author lixing
 *Date 2018/12/31 14:32
 *Version 1.0
 */
@RPCQueueName("ecms.rpc.accountVerficationService")
public interface AccountVerficationService {

    Trans0004Resp Trans0004(Trans0004Req req) throws Exception;
}
