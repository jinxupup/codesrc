package com.jjb.ecms.biz.ext.ddcheck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.infrastructure.TmDitDic;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.CodeMarkUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 约定还款账户验证
 * @Description: 
 * @author JYData-R&D-HN
 * @date 2017年10月13日 下午7:21:35
 * @version V1.0
 */
@Component
public class DDCheck {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private AppCommonUtil appCommonUtil;
	@Autowired
	private DDCheckForBanknf ddCheckForBanknf;
	@Autowired
	private CodeMarkUtils codeMarkUtils;
	
	/**
	 * 约定还款账户有效性校验
	 * @param acctName	约定还款账户姓名
	 * @param idType	证件类型
	 * @param idNo	证件号码
	 * @param branchNo	开户行号
	 * @param acctNo	账户号码
	 * @param acctNoType 行内/外账户标志，用于执行不同的验证方法
	 * @return
	 */
	public String checkAccountNo(String acctName, String idType, String idNo, 
			String branchNo, String acctNo, String acctNoType) {
		Long tokenId = System.currentTimeMillis();
//		com.jjb.acl.gmp.sdk.OrganizationContextHolder.setCurrentOrg(OrganizationContextHolder.getOrg());// 设置机构号
		if(StringUtils.isBlank(acctNoType)){
			acctNoType = Indicator.N.name();//默认为本行账户
		}
		logger.info("开始验证客户["+acctName+"]约定还款账户["+codeMarkUtils.makeCardMask(acctNo)+"]"+ LogPrintUtils.printCommonStartLog(tokenId,null)+"验证账户类型:"+acctNoType);
		try {
			// 接口验证
			TmDitDic tmDitDic =  cacheContext.getApplyOnlineOnOff(AppDitDicConstant.onLinOff_ddCheck);
			if(tmDitDic != null && StringUtils.isNotBlank(tmDitDic.getRemark()) 
					&& tmDitDic.getRemark().equals(Indicator.Y.name())){
				return ddCheckForBanknf.ddCheckForAicBanknf(acctName, idType, idNo, branchNo,
						acctNo);
			}else {
				return "本行账户查询功能开关未开启,请联系管理员";
			}
		} catch (Exception e) {
			logger.error("客户["+acctName+"]约定还款账户验证失败", e);
			throw new ProcessException(e.getMessage());
		} finally{
			logger.info("结束验证客户["+acctName+"]约定还款账户"+LogPrintUtils.printCommonEndLog(tokenId,null));	
		}
	}

}
