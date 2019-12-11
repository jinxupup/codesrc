package com.jjb.ecms.service.api;

import com.jjb.unicorn.facility.meta.RPCQueueName;

/**
 *@ClassName AccountVerficationService
 *@Description 约定还款账户验证
 *@Author lixing
 *Date 2018/12/31 14:32
 *Version 1.0
 */
@RPCQueueName("ecms.rpc.applyProductsService")
public interface ApplyProductsService {

    public void applyExecute(String param,long start) throws Exception;
}
