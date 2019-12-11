package com.jjb.ecms.service.api;

import com.jjb.ecms.service.dto.Trans90020.Trans90020Req;
import com.jjb.ecms.service.dto.Trans90020.Trans90020Resp;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.meta.RPCQueueName;

/**
 * 调用外部系统
 * </br> 申请行内客户号
 * @author hp
 *
 */
@RPCQueueName("ecms.rpc.extSystemService")
public interface ExtSystemService {
	/**
	 * 申请行内客户号
	 * @param req
	 * @return
	 * @throws ProcessException
	 */
	public Trans90020Resp s90020(Trans90020Req req) throws ProcessException;
}
