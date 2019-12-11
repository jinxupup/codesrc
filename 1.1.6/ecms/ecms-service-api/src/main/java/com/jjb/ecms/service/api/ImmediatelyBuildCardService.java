package com.jjb.ecms.service.api;

import com.jjb.acl.facility.enums.bus.ApplyFileItem;
import com.jjb.ecms.service.dto.Trans0059.Trans0059Req;
import com.jjb.ecms.service.dto.Trans0059.Trans0059Resp;
import com.jjb.unicorn.facility.meta.RPCQueueName;

/**
  *@ClassName ImmediatelyBuildCardService
  *@Description 实时建账制卡
  *@Author lixing
  *Date 2018/10/17 17:49
  *Version 1.0
  */
@RPCQueueName("ecms.rpc.immediatelyBuildCardService")
public interface ImmediatelyBuildCardService {

    Trans0059Resp Trans0059(Trans0059Req req,ApplyFileItem applyFileItem) throws Exception;
}
