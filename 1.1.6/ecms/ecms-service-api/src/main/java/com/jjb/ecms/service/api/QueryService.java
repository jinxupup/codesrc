package com.jjb.ecms.service.api;

import com.jjb.ecms.service.dto.T1000.T1000Req;
import com.jjb.ecms.service.dto.T1000.T1000Resp;
import com.jjb.ecms.service.dto.T1002.T1002Req;
import com.jjb.ecms.service.dto.T1002.T1002Resp;
import com.jjb.ecms.service.dto.T1007.T1007Req;
import com.jjb.ecms.service.dto.T1007.T1007Resp;
import com.jjb.ecms.service.dto.T1009.T1009Req;
import com.jjb.ecms.service.dto.T1009.T1009Resp;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.meta.RPCQueueName;

/**
 * 征审系统查询服务接口
 * 
 * @author hp
 *
 */
@RPCQueueName("cas.rpc.query")
public interface QueryService {

	/**
	 * 申请进度查询
	 * 
	 * @param idType
	 * @param idNo
	 * @return
	 * @throws ErrorCodeException
	 */
	T1000Resp T1000(T1000Req req) throws ProcessException;

	/**
	 * 准入资格及老客户查询<br/>
	 * a)该接口配合“T1000-申请进度查询”和“T1005-联机申请录入”使用 <br/>
	 * b)如果使用该接口，客户通过渠道申请，在录入接口请求中字段后，调用该接口，系统后台会根据客户信息、基本申请信息查询客户是否历史申请过(包括已终审有卡和未终审无卡)，
	 * 如果有申请过，则取最新客户数据通过接口返回到渠道端，并且赋值CUSTOMER_TYPE等于O-老客户，
	 * 否则赋值为N-新客户；客户信息录入和确认完后调用“T1005-联机申请录入”接口需要将CUSTOMER_TYPE的值赋值到T1005接口请求字段“APPLY_TYPE”中。<br/>
	 * c)该接口可根据行内要求联机第三方决策系统或征审系统自带的决策规则进行申请件的准入判定。<br/>
	 * 
	 * @param idType
	 * @param idNo
	 * @return
	 * @throws ErrorCodeException
	 */
	T1002Resp T1002(T1002Req req) throws ProcessException;

	/**
	 * 申请件信息查询
	 * 
	 * @param req
	 * @return
	 * @throws ProcessException
	 */
//	S11003Resp S11003(S11003Req req) throws Exception;

	/**
	 * 推广人(系统用户)查询
	 * 
	 * @param req
	 * @return
	 * @throws ProcessException
	 */
	T1007Resp T1007(T1007Req req) throws ProcessException;

	/**
	 * 根据类型查询渠道端进件排行榜
	 * 
	 * @param req
	 * @return
	 * @throws ProcessException
	 */
	T1009Resp T1009(T1009Req req) throws ProcessException;

}
