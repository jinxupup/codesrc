package com.jjb.ams.app.controller.apply;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.ecms.infrastructure.TmAppContact;
import com.jjb.ecms.infrastructure.TmAppCustInfo;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;


/**
 * @Description: 贷款信息录入
 * @author JYData-R&D-Big T.T
 * @date 2016年8月31日 下午6:43:58
 * @version V1.0  
 */

@Controller
@RequestMapping("/ams_loanInput")
public class LoanInputController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(getClass());
	
		
	/**
	 * 贷款信息录入页面展示
	 * @return
	 */
	@RequestMapping("/loanInputPage")
	public String loanInputPage(String appNo){
		
		setAttr("pbocReportList", "");//征信汇总信息
		setAttr("loanList", "");//客户负债相关提示信息
		
		return "credit/loanInput/loanInputPage.ftl";
	}
	
	/**
	 * 共同借款人基本信息列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/commonLoanInfoList")
	public Page<TmAppCustInfo> commonLoanInfoList(String appNo){
		Page<TmAppCustInfo> page = getPage(TmAppCustInfo.class);
		if(StringUtils.isNotEmpty(appNo)){
			Query query = page.getQuery();
			query.put("appNo", appNo);
			page.setQuery(query);
			
		}	
		return page;
	}
	
	/**
	 * 担保人基本信息列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/bondsInfoList")
	public Page<TmAppContact> bondsInfoList(String appNo){
		Page<TmAppContact> page = getPage(TmAppContact.class);
		if(StringUtils.isNotEmpty(appNo)){
			Query query = page.getQuery();
			query.put("appNo", appNo);
			query.put("contactRelation", "");
			page.setQuery(query);
			
		}	
		return page;
	}
	
	/**
	 * 贷款人征信信息列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/pbocInfoList")
	public Page<TmAppCustInfo> pbocInfoList(String appNo){
		Page<TmAppCustInfo> page = getPage(TmAppCustInfo.class);
		if(StringUtils.isNotEmpty(appNo)){
			Query query = page.getQuery();
			query.put("appNo", appNo);
			page.setQuery(query);
			
		}	
		return page;
	}
	
	/**
	 * 贷款相关人员信息列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/peopleInfo")
	public Page<TmAppCustInfo> peopleInfoList(String appNo){
		Page<TmAppCustInfo> page = getPage(TmAppCustInfo.class);
		if(StringUtils.isNotEmpty(appNo)){
			Query query = page.getQuery();
			query.put("appNo", appNo);
			page.setQuery(query);
			
		}	
		return page;
	}
	
}