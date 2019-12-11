package com.jjb.ecms.service.dto.T1005;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jjb.ecms.service.dto.BasicRequest;
import com.jjb.ecms.service.dto.TCustInfo;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import lombok.Data;

/**
 * 联机录入申请请求
 * 
 * @author hp
 *
 */
@Data
public class T1005Req extends BasicRequest implements Serializable {
	public static final long serialVersionUID = -3049384988652027684L;
	@XStreamOmitField
	public static final String servId="T1005";
	
	public List<TCustInfo> custs;
	
	/*
	 * 申请信息
	 */
	public String org;// 机构号
	public String appType;// 申请类型
	public BigDecimal appLmt;// 申请额度
	public String productCd;// 卡产品代码
	public String clientType;// 客户类型
	public String owningBranch;// 受理网点\发卡网点
	public String appSource;// 申请渠道
	public String appProperty;// 进件属性
	public String appnoExternal;// 申请编号_外部送入
	public String taskNum;// 行内任务编号
	public String imageNum;// 影像批次号
	public String realtimeIssuingFlag;// 是否实时发卡标志
	public String cardFace;// 卡面代码
	public String applyType;// 进件类型
	public String submitType;// 提交操作

	public String orderType;// 多卡同申状态
	public String deviceType;//设备类型,比如：IOS、安卓、PC
	public String deviceGps;//设备GPS定位,	经纬度； S-{经度}N-{纬度}
	public String deviceGpsCN;//设备GPS定位地址描述,根据经纬度得到的中文地址描述
	public String weChatNickName;//微信昵称
	public String telOperatorsType;//运营商类型
	public String telBelong;//运营商所属地
	
	/*
	 * 联系人信息
	 */
	public String contactName;// 联系人中文姓名
	public String contactGender;// 联系人性别
	public String contactRelation;// 联系人与申请人关系
	public String contactMobile;// 联系人移动电话
	public String contactTelephone;// 联系人联系电话
	public String contactEmpPhone;// 联系人公司电话号

	public String contactOname;// 其他联系人中文姓名;
	public String contactOgender;// 其他联系人性别;
	public String contactOrelation;// 其他联系人与申请人关系;
	public String contactOmobile;// 其他联系人移动电话;
	public String contactOtelephone;// 其他联系人联系电话;
	public String contactOempPhone;// 其他联系人公司电话号;
	
	/*
	 * TmBankInfo，卡片及银行专用栏信息
	 */
	public String cardFetchMethod;// 介质卡领取方式
	public String cardMailerInd;// 卡片寄送地址标志
	public String fetchBranch;// 领卡网点
	public String stmtMediaType;// 账单介质类型
	public String stmtMailAddrInd;// 账单寄送地址标志
	public String billingCycle;// 账单日
	public String ddInd;// 约定还款类型
	public String ddBankAcctNo;// 约定还款扣款账号
	public String ddBankName;// 约定还款银行名称
	public String ddBankAcctName;// 约定还款扣款人姓名
	public String ddBankBranch;// 约定还款开户行机构号
	public String creditTypeOther;// 已有信用卡类型
	public String otherCardNo;// 已有信用卡卡号
	public String isPrdChange;// 是否同意卡片自动降级
	public String spreaderNo;// 推广人编号
	public String spreaderName;// 推广人姓名
	public String spreaderTelephone;// 推广人手机号
	public String spreaderCardNo;// 推广人卡号
	public String spreaderIsBankEmployee;// 推广人是否本行员工
	public String spreaderBank;// 推广人所属分行
	public String spreaderOrg;// 推广机构
	public String spreaderType;// 推广渠道\推广方式
	public String spreaderMode;// 三亲核实
	public String spreaderCorpPreNo;// 推广人预审人编号
	public String spreaderSupCode;//推广主管代码
	public String spreaderTeamCode;//推广团队代码
	public String spreaderAreaCode;//推广区域代码
	public String reviewNo;// 复核人编号
	public String reviewName;// 复核人员签名
	public String reviewTelephone;// 复核人员联系方式
	public String reviewBranchTwo;// 复核人所属分行
	public String reviewBranchThree;// 复核人所属网点
	public String preNo;// 预审人编号
	public String preName;// 预审人姓名
	public String preTelephone;// 预审人联系电话
	public String preBranchTwo;// 预审人所属分行
	public String preBranchThree;// 预审人所属网点
	public String inputNo;// 录入人员编号9
	public String inputName;// 录入人员姓名
	public String inputTelephone;// 录入人联系电话
	public String inputBranchTwo;// 录入人所属分行
	public String inputBranchThree;// 录入人所属网点
	public Date inputDate;// 录入日期
	/*
	 * 外部风控决策信息
	 */
	public Integer extMultiLoan1M;// 1个月内多头借贷次数
	public Integer extBillCnt6M;// 6个月内信用卡账单月份数
	public Integer extCarrierHitBl6M;// 6个月内电话催收库命中个数
	public Integer extScore;// 外部风控系统评分
	public String extAuditsRisk;// 是否触发电核规则
	public String extIfLmk;// 是否存在联名卡
	public BigDecimal extLmkLmtTotal;// 已有联名卡总额度
	public String extRefuseCode;// 拒绝原因
	public String extEqBehAbn;// 设备行为异常
	public String extHighRiskBl;// 第三方内部高危黑名单
	public String extContactAbn;// 联系人异常
	public String extBl;// 外部黑名单
	public String extHighRisk;// 外部高风险
	public String telOperatorsAbn;// 通讯录运营商异常
	public String invalidApply;// 无效进件
	public String extReseField1;// 预留字段1
	public String extReseField2;// 预留字段2
	public String extReseField3;// 预留字段3
	public String extReseField4;// 预留字段4
	public String extReseField5;// 预留字段5
	public String extCheckIdFlag;//渠道核身标志 0：失败 1：通过 2:未核验 3：异常跳过
 	public String extCheckIdRs;// 渠道核实结果

	//TmAppPrimAnnexEvi 附件证明信息
	public String estatesNoInstallAmt;
	public String estatesInstallAmt;
	public String estatesInstallLoanAmt;
	public String buildingName;
	public String houseMonthyAmt;
	public String otherCcBank1;
	public String otherCcLmt1;
	public String otherCcBank2;
	public String otherCcLmt2;
	public String carIdNum;
	public String cardEmissions;
	public String carModel;
	public String cardPrice;

	public Map<String, Serializable> convertToMap() {
		HashMap<String, Serializable> map = new HashMap<String, Serializable>();
		map.put("org", this.org);
		map.put("appType", this.appType);
		map.put("appLmt", this.appLmt);
		map.put("productCd", this.productCd);
		map.put("clientType", this.clientType);
		map.put("owningBranch", this.owningBranch);
		map.put("appSource", this.appSource);
		map.put("appProperty", this.appProperty);
		map.put("appnoExternal", this.appnoExternal);
		map.put("taskNum", this.taskNum);
		map.put("imageNum", this.imageNum);
		map.put("isRealtimeIssuing", this.realtimeIssuingFlag);
		map.put("cardFace", this.cardFace);
		map.put("applyType", this.applyType);
		map.put("submitType", this.submitType);

		map.put("cardFetchMethod", this.cardFetchMethod);
		map.put("cardMailerInd", this.cardMailerInd);
		map.put("fetchBranch", this.fetchBranch);
		map.put("stmtMediaType", this.stmtMediaType);
		map.put("stmtMailAddrInd", this.stmtMailAddrInd);
		map.put("billingCycle", this.billingCycle);
		map.put("ddInd", this.ddInd);
		map.put("ddBankAcctNo", this.ddBankAcctNo);
		map.put("ddBankName", this.ddBankName);
		map.put("ddBankAcctName", this.ddBankAcctName);
		map.put("ddBankBranch", this.ddBankBranch);
		map.put("creditTypeOther", this.creditTypeOther);
		map.put("otherCardNo", this.otherCardNo);
		map.put("isPrdChange", this.isPrdChange);

		map.put("spreaderNo", this.spreaderNo);
		map.put("spreaderName", this.spreaderName);
		map.put("spreaderTelephone", this.spreaderTelephone);
		map.put("spreaderCardNo", this.spreaderCardNo);
		map.put("spreaderAreaCode", this.spreaderAreaCode);
		map.put("spreaderTeamCode", this.spreaderTeamCode);
		map.put("spreaderSupCode", this.spreaderSupCode);
		map.put("spreaderIsBankEmployee", this.spreaderIsBankEmployee);
		map.put("spreaderBranchTwo", this.spreaderBank);
		map.put("spreaderBranchThree", this.spreaderOrg);
		map.put("spreaderType", this.spreaderType);
		map.put("spreaderMode", this.spreaderMode);
		map.put("spreaderCorpPreNo", this.spreaderCorpPreNo);
		map.put("reviewNo", this.reviewNo);
		map.put("reviewName", this.reviewName);
		map.put("reviewTelephone", this.reviewTelephone);
		map.put("reviewBranchTwo", this.reviewBranchTwo);
		map.put("reviewBranchThree", this.reviewBranchThree);
		map.put("preNo", this.preNo);
		map.put("preName", this.preName);
		map.put("preTelephone", this.preTelephone);
		map.put("preBranchTwo", this.preBranchTwo);
		map.put("preBranchThree", this.preBranchThree);
		map.put("inputNo", this.inputNo);
		map.put("inputName", this.inputName);
		map.put("inputTelephone", this.inputTelephone);
		map.put("inputBranchTwo", this.inputBranchTwo);
		map.put("inputBranchThree", this.inputBranchThree);
		map.put("inputDate", this.inputDate);

		map.put("contactName", this.contactName);
		map.put("contactGender", this.contactGender);
		map.put("contactRelation", this.contactRelation);
		map.put("contactMobile", this.contactMobile);
		map.put("contactTelephone", this.contactTelephone);
		map.put("contactEmpPhone", this.contactEmpPhone);

		map.put("contactOname", this.contactOname);
		map.put("contactOgender", this.contactOgender);
		map.put("contactOrelation", this.contactOrelation);
		map.put("contactOmobile", this.contactOmobile);
		map.put("contactOtelephone", this.contactOtelephone);
		map.put("contactOempPhone", this.contactOempPhone);

		map.put("extMultiLoan1M", this.extMultiLoan1M);
		map.put("extBillCnt6M", this.extBillCnt6M);
		map.put("extCarrierHitBl6M", this.extCarrierHitBl6M);
		map.put("extScore", this.extScore);
		map.put("extAuditsRisk", this.extAuditsRisk);
		map.put("extIfLmk", this.extIfLmk);
		map.put("extLmkLmtTotal", this.extLmkLmtTotal);
		map.put("extRefuseCode", this.extRefuseCode);
		map.put("extEqBehAbn", this.extEqBehAbn);
		map.put("extHighRiskBl", this.extHighRiskBl);
		map.put("extContactAbn", this.extContactAbn);
		map.put("extBl", this.extBl);
		map.put("extHighRisk", this.extHighRisk);
		map.put("telOperatorsAbn", this.telOperatorsAbn);
		map.put("invalidApply", this.invalidApply);
		map.put("extReseField1", this.extReseField1);
		map.put("extReseField2", this.extReseField2);
		map.put("extReseField3", this.extReseField3);
		map.put("extReseField4", this.extReseField4);
		map.put("extReseField5", this.extReseField5);
		map.put("extCheckIdFlag", this.extCheckIdFlag);
		map.put("extCheckIdRs", this.extCheckIdRs);

		map.put("estatesNoInstallAmt",this.estatesNoInstallAmt);
		map.put("estatesInstallAmt",this.estatesInstallAmt);
		map.put("estatesInstallLoanAmt",this.estatesInstallLoanAmt);
		map.put("buildingName",this.buildingName);
		map.put("houseMonthyAmt",this.houseMonthyAmt);
		map.put("otherCcBank1",this.otherCcBank1);
		map.put("otherCcLmt1",this.otherCcLmt1);
		map.put("otherCcBank2",this.otherCcBank2);
		map.put("otherCcLmt2",this.otherCcLmt2);
		map.put("carIdNum",this.carIdNum);
		map.put("cardEmissions",this.cardEmissions);
		map.put("carModel",this.carModel);
		map.put("cardPrice",this.cardPrice);
		return map;
	}

}
