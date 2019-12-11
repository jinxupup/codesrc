package com.jjb.ecms.service.api;

import com.jjb.ecms.service.dto.T9000.T9000Req;
import com.jjb.ecms.service.dto.T9000.T9000Resp;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.meta.RPCQueueName;

/**
 * 
 * @Description: 审批系统推送（调用渠道）数据
 * @author JYData-R&D-HN
 * @date 2018年4月11日 下午9:09:35
 * @version V1.0
 */
@RPCQueueName("ecms.rpc.creditSysPush")
public interface CreditSysPushService {

	/**
	 * 推送审批结果
	 * 
	 * @param req
	 * @return
	 * @throws ProcessException
	 */
	T9000Resp T9000(T9000Req req) throws Exception;
}
