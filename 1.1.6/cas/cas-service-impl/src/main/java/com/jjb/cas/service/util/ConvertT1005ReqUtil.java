package com.jjb.cas.service.util;


import java.util.Date;
import java.util.List;

import com.jjb.acl.facility.enums.bus.StmtMediaType;
import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.service.dto.TCustInfo;
import com.jjb.ecms.service.dto.T1005.T1005Req;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 请求参数转换处理
 * @author hp
 *
 */
public class ConvertT1005ReqUtil {
//	private static Logger logger = LoggerFactory.getLogger(ConvertT1005ReqUtil.class);
	
	/**
	 * 系统给请求数据赋默认值
	 * @param req
	 * @return
	 */
	public static T1005Req convertReq(T1005Req req) {
		
		//tmAppMain
		req.setOrg(StringUtils.setValue(req.getOrg(),OrganizationContextHolder.getOrg()));//机构号
		req.setOwningBranch(StringUtils.setValue(req.getOwningBranch(), "06101"));
		req.setRealtimeIssuingFlag(StringUtils.setValue(req.getRealtimeIssuingFlag(), "Y"));
		/******客户信息TmAppCustInfo*****/
		List<TCustInfo> custs = req.getCusts();
		if(CollectionUtils.isNotEmpty(custs)) {
			for (int i = 0; i < custs.size(); i++) {
				TCustInfo cust = custs.get(i);
				
				//tmAppPrimApplicationInfo
				cust.setAttachNo(StringUtils.stringToInteger(StringUtils.setValue(cust.getAttachNo(),"1")));
				cust.setBscSuppInd(StringUtils.setValue(cust.getBscSuppInd(),AppConstant.bscSuppInd_B));
				cust.setIfSelectedCard(StringUtils.setValue(cust.getIfSelectedCard(),Indicator.N.name()));//是否自选卡号
				cust.setNationality(StringUtils.setValue(cust.getNationality(),"156"));//国籍
				cust.setResidencyCountryCd(StringUtils.setValue(cust.getResidencyCountryCd(),"156"));//永久居住地国家代码
				cust.setIdLastAll(StringUtils.setValue(cust.getIdLastAll(),Indicator.N.name()));//证件是否永久有效
				cust.setHomeAddrCtryCd(StringUtils.setValue(cust.getHomeAddrCtryCd(),"156"));//家庭国家代码
				cust.setBankmemFlag(StringUtils.setValue(cust.getBankmemFlag(),Indicator.N.name()));//是否行内员工
				cust.setEmpAddrCtryCd(StringUtils.setValue(cust.getEmpAddrCtryCd(),"156"));//公司国家代码
				cust.setCustType(StringUtils.setValue(req.getClientType(),"A00"));
				cust.setPosPinVerifyInd(StringUtils.setValue(cust.getPosPinVerifyInd(),Indicator.Y.name()));//是否消费凭密
				cust.setPhotoUseFlag(StringUtils.setValue(cust.getPhotoUseFlag(),Indicator.N.name()));//是否彩照卡
				cust.setSmAmtVerifyInd(StringUtils.setValue(cust.getSmAmtVerifyInd(),Indicator.Y.name()));//小额免密
			}
		}
		
		/******卡片及银行专用栏信息TmAppPrimCardInfo*****/
		req.setCardFetchMethod(StringUtils.setValue(req.getCardFetchMethod(),"A"));//介质卡领取方式
		
		if(StringUtils.equals(req.getCardFetchMethod(), "B") 
				&& StringUtils.isEmpty(req.getFetchBranch())) {
			req.setFetchBranch(req.getOwningBranch());//领卡网点
		}else {
			req.setCardMailerInd(StringUtils.setValue(req.getCardMailerInd(),"C"));//卡片寄送地址标志，默认到单位
		}
		req.setStmtMediaType(StringUtils.setValue(req.getStmtMediaType(),StmtMediaType.E.name()));//账单介质类型
		if(!StringUtils.equals(req.getStmtMediaType(),StmtMediaType.E.name())) {
			//账单寄送地址标志 ，如果账单寄送方式不是EMAIL，然后寄送地址也为空，那么默认寄送到其单位
			req.setStmtMailAddrInd(StringUtils.setValue(req.getStmtMailAddrInd(),"C"));
		}
		req.setDdInd(StringUtils.setValue(req.getDdInd(),Indicator.N.name()));//约定还款类型
		req.setDdBankName(StringUtils.setValue(req.getDdBankName(),"08101"));//约定还款银行名称
		req.setIsPrdChange(StringUtils.setValue(req.getIsPrdChange(),Indicator.Y.name()));//是否同意卡片自动降级
		req.setSpreaderNo(StringUtils.setValue(req.getSpreaderNo(),req.getInputNo()));//推广人编号,如果推广人编号为空，则赋值推广人姓名
		req.setSpreaderName(StringUtils.setValue(req.getSpreaderName(),req.getSpreaderNo()));//推广人姓名，如果推广人姓名为空，则赋值推广人编号
		req.setSpreaderIsBankEmployee(StringUtils.setValue(req.getSpreaderIsBankEmployee(),"N"));//推广人是否本行员工
//		req.setInputNo(req.getInputNo());//录入人员编号
//		req.setInputName(req.getInputName());//录入人员姓名
//		req.setInputTelephone(req.getInputTelephone());//录入人联系电话
//		req.setInputBranchTwo(req.getInputBranchTwo());//录入人所属分行
//		req.setInputBranchThree(req.getInputBranchThree());//录入人所属网点
		req.setInputDate(req.getInputDate()==null ? new Date():req.getInputDate());//录入日期

		/******联系人信息TmAppContact*****/
//		req.setContactName(req.getContactName());//联系人中文姓名
//		req.setContactGender(req.getContactGender());//联系人性别
//		req.setContactRelation(req.getContactRelation());//联系人与申请人关系
//		req.setContactMobile(req.getContactMobile());//联系人移动电话
//		req.setContactTelephone(req.getContactTelephone());//联系人联系电话
//		req.setContactEmpPhone(req.getContactEmpPhone());//联系人公司电话号
//		req.setContactOname(req.getContactOname());//其他联系人中文姓名
//		req.setContactOgender(req.getContactOgender());//其他联系人性别
//		req.setContactOrelation(req.getContactOrelation());//其他联系人与申请人关系
//		req.setContactOmobile(req.getContactOmobile());//其他联系人移动电话
//		req.setContactOtelephone(req.getContactOtelephone());//其他联系人联系电话
//		req.setContactOempPhone(req.getContactOempPhone());//其他联系人公司电话号
		
		/******分期*****/
//		req.setIsInstalment(StringUtils.setValue(req.getIs_instalment(),Indicator.N.name():req.getIs_instalment());//是否申请分期，默认为N-否
//		req.setInstalmentCreditActivityNo(req.getInstalment_credit_activity_no());//分期活动编号
//		req.setLoanInitTerm(req.getLoan_init_term());//分期期数
//		req.setMccNo(req.getMcc_no());//分期商户号
//		req.setCashAmt(req.getCash_amt());//分期借款金额
//		req.setLoanFeeMethod(req.getLoan_fee_method());//自定义手续费收取方式
//		req.setLoanFeeCalcMethod(req.getLoan_fee_calc_method());//自定义手续费收取计算方式
//		req.setAppFeeAmt(req.getApp_fee_amt());//自定义手续费收取固定金额
//		req.setAppFeeRate(req.getApp_fee_rate());//自定义手续费收取费率

		return req;
	}
}
