package com.jjb.ecms.service.api;

import com.jjb.unicorn.facility.meta.RPCQueueName;

/**
 * 
 * @Description:外部调用ecms-adapter前置服务
 * @author JYData-R&D-HN
 * @date 2018年4月2日 下午7:27:41
 * @version V1.0
 */
@RPCQueueName("ecms.rpc.extCallEcmsAdapterService")
public interface ExtCallEcmsAdapterService {

	/**
	 *  外部调用ecms-adapter前置服务
	 * @param xml
	 * @return
	 */
	public String callEcmsAdapter(String xml);

}
