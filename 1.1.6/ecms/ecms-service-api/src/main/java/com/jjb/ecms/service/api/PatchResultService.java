package com.jjb.ecms.service.api;

import com.jjb.ecms.service.dto.T1001.T1001Req;
import com.jjb.ecms.service.dto.T1001.T1001Resp;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.meta.RPCQueueName;

/**
 * @Description: 补件结果反馈接口
 * @author JYData-R&D-Big Star
 * @date 2016年6月13日 下午4:41:23
 * @version V1.0
 */
@RPCQueueName("ecms.rpc.patchResp")
public interface PatchResultService {

	/**
	 * 补件信息反馈
	 * 
	 * @param req
	 * @return
	 * @throws ProcessException
	 */
	T1001Resp T1001(T1001Req req) throws ProcessException;

}
