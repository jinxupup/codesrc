package com.jjb.ecms.service.api;

import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.meta.RPCAsync;
import com.jjb.unicorn.facility.meta.RPCQueueName;

/**
 * 
 * @Description: 审批系统推送（调用渠道）数据
 * @author JYData-R&D-HN
 * @date 2018年4月11日 下午9:09:35
 * @version V1.0
 */
@RPCQueueName("ecms.rpc.AsyncPushService")
public interface AsyncPushService {
	/**
	 * 推送申请件消息
	 * 真正的异步消息哦
	 * @param req
	 * @return
	 * @throws ProcessException
	 */
	@RPCAsync
	void T9002(String req) throws Exception;
	
}
