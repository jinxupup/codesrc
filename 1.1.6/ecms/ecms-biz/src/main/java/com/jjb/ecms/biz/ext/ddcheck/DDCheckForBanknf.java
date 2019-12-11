package com.jjb.ecms.biz.ext.ddcheck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.unicorn.facility.exception.ProcessException;

/**
 * 行内账户验证
 * @Description: 
 * @author JYData-R&D-HN
 * @date 2017年10月13日 下午7:04:35
 * @version V1.0
 */
@Component
public class DDCheckForBanknf {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private AppCommonUtil appCommonUtil;

	/**
	 * 行内账户验证
	 * @param acctName
	 * @param idType
	 * @param idNo
	 * @param branchNo
	 * @param acctNo
	 * @return
	 * @throws ProcessException
	 */
	public String ddCheckForAicBanknf(String acctName, String idType,
			String idNo, String branchNo, String acctNo)
			throws ProcessException {
		try {
//			RtnInfoForAps rtnInfoForAps = null;
////			RtnInfoForAps rtnInfoForAps = bankServiceForAps.checkAccountNo(acctName, idType, idNo, branchNo, acctNo);
//			if (rtnInfoForAps == null || StringUtils.isBlank(rtnInfoForAps.getResult()) || "F".equals(rtnInfoForAps.getResult())) {
//				return "无效的约定还款信息";
//			} else {
//				return "";
//			}
			return "";
		} catch (ProcessException e) {
			logger.error("非金融验证约定还款账户异常",e);
			return e.getMessage();
		}catch (Exception e) {
			logger.error("非金融验证约定还款账户异常",e);
			throw new ProcessException("约定还款账户校验-连接服务异常，请联系管理员！错误信息["+e.getMessage()+"]");
		}
	}
}
