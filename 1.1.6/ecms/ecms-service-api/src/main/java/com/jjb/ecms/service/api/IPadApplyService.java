package com.jjb.ecms.service.api;

import com.jjb.ecms.service.dto.T1005.T1005Req;
import com.jjb.ecms.service.dto.T1005.T1005Resp;
import com.jjb.ecms.service.dto.T1006.T1006Req;
import com.jjb.ecms.service.dto.T1006.T1006Resp;
import com.jjb.ecms.service.dto.T1008.T1008Req;
import com.jjb.ecms.service.dto.T1008.T1008Resp;
import com.jjb.ecms.service.dto.T1010.T1010Req;
import com.jjb.ecms.service.dto.T1010.T1010Resp;
import com.jjb.ecms.service.dto.T1011.T1011Req;
import com.jjb.ecms.service.dto.T1011.T1011Resp;
import com.jjb.ecms.service.dto.T1012.T1012Req;
import com.jjb.ecms.service.dto.T1012.T1012Resp;
import com.jjb.ecms.service.dto.T1013.T1013Req;
import com.jjb.ecms.service.dto.T1013.T1013Resp;
import com.jjb.unicorn.facility.exception.ProcessException;

/**
 * @Description: 征审系统ipad申请联机服务
 * @author JYData-R&D-Big Star
 * @date 2016年6月14日 下午3:37:45
 * @version V1.0
 */
public interface IPadApplyService {

	/**
	 * ipad申请</br>渠道端联机申请提交
	 *
	 * @param T1005Req
	 * @return
	 * @throws ProcessException
	 */
	T1005Resp T1005(T1005Req req) throws ProcessException;

	/**
	 * 接收渠道端提交的“预审操作”
	 *
	 * @param T1006Req
	 * @return
	 * @throws ProcessException
	 */
	T1006Resp T1006(T1006Req req) throws ProcessException;

	/**
	 * @Author smh
	 * @Description TODO  4.1.8.T1008-受理外部决策结果
	 * @Date 2018/11/23 14:34
	 */

	T1008Resp T1008(T1008Req t1008Req) throws ProcessException;
	/**
	 * 任务锁定通知<br>
	 * 渠道方\行内其他系统等锁定审批系统申请件
	 * @param req
	 * @return
	 * @throws ProcessException
	 */

//	S11012Resp S11012(S11012Req req) throws ProcessException;


	/**
	 * 用于支持客户经理自主分配到其指定的其他客户经理名下
	 * @param req
	 * @return
	 * @throws ProcessException
	 */
	T1010Resp T1010(T1010Req req) throws ProcessException;

	/**
	 * 审批系统前置服务提供联机接口给微信公众号上送大额分期准入申请信息
	 * @param req
	 * @return
	 * @throws ProcessException
	 */
	T1011Resp T1011(T1011Req req) throws ProcessException;


	/**
	 * 审批系统前置服务提供联机接口给中联惠捷系统调用查询客户“大额分期准入申请”结果
	 * @param req
	 * @return
	 * @throws ProcessException
	 */
	T1012Resp T1012(T1012Req req) throws ProcessException;

	/**
	 * 合伙人准入资格申请
	 * @param req
	 * @return
	 * @throws ProcessException
	 */
	T1013Resp T1013(T1013Req req) throws ProcessException;

}
