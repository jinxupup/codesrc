package com.jjb.ecms.service.api;

import com.jjb.ecms.service.dto.Trans0139.Trans0139Req;
import com.jjb.ecms.service.dto.Trans0139.Trans0139Resp;
import com.jjb.ecms.service.dto.Trans0140.Trans0140Req;
import com.jjb.ecms.service.dto.Trans0140.Trans0140Resp;
import com.jjb.unicorn.facility.exception.ProcessException;

/**
 * @Description: 征信信息辅助处理服务</br>
 * 主要用于外部其他系统查询系统已使用征信数据</br>
 * 场景一：法院被执行人征信信息查询跳转，外部系统请求前置，前置请求审批前置，审批前置请求csms查询第三方外部征信数据返回
 * 场景二：行内资产信息查询，外部系统请求前置，前置请求审批前置，审批前置再调用审批主服务或者大数据平台获取相关信息
 * @author hejn
 * @date 2019年11月02日 下午13:27:35
 * @version V1.0
 */
public interface CreditInfoAuxTreatService {

	/**
	 * 04003000031-行内征信信息查询</br>
	 * 1.法院执行人查询
	 * @param Trans0139Req
	 * @return Trans0139Resp
	 * @throws ProcessException
	 */
	Trans0139Resp Trans0139(Trans0139Req req) throws ProcessException;

	/**
	 * 04003000031账户资产信息查询
	 * @param Trans0140Req
	 * @return Trans0140Resp
	 * @throws ProcessException
	 */
	Trans0140Resp Trans0140(Trans0140Req req) throws ProcessException;

}
