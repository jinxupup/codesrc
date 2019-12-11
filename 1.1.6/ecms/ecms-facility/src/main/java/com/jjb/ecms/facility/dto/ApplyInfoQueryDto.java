package com.jjb.ecms.facility.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 申请件信息查询
 * @author J.J
 * @date 2017年10月30日上午9:59:30
 */
public class ApplyInfoQueryDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String appNo; //申请件编号
	private String custType;//客户类型
	private String appSource;//申请渠道
	private String name; //客户姓名
	private String idNo; //证件号码
	private String cellPhone; //移动电话
	private String cardNo;//卡号
	private String idType; // 证件类型
	private String productCd;//卡产品代码
	private String rtfState; //审批状态
	private Date beginDate; //查询起始日期
	private Date endDate;//查询截止日期
	private String corpName; //公司名称
	private String empPhone; //公司电话
	private String spreaderNo; // 推广人编号
	private String preNo;//预审人编号
	private String inputNo; //录入员
	private String reviewNo;//复核员
	private String patchBoltNo;//补件操作员
	private String personCheckNo;//人工核查员
	private String phoneNo;//电话调查员
	private String checkNo;//初审员
	private String finalNo;//终审员
	private String inputDate;//录入时间
	private String reviewDate;//复核时间
	private String patchBoltDate;//补件时间
	private String personCheckDate;//人工核查时间
	private String phoneDate;//电话调查时间
	private String checkDate;//初审时间
	private String finalDate;//终审时间
	private String accLmt;
	private String taskOwner;
	private String spreaderSupCode; //推广主管代码
	private String spreaderTeamCode;//推广团队代码
	private String spreaderAreaCode;//推广区域代码

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getAppSource() {
		return appSource;
	}

	public void setAppSource(String appSource) {
		this.appSource = appSource;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdNo() {
		return idNo;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getProductCd() {
		return productCd;
	}

	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}

	public String getRtfState() {
		return rtfState;
	}

	public void setRtfState(String rtfState) {
		this.rtfState = rtfState;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getEmpPhone() {
		return empPhone;
	}

	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}

	public String getSpreaderNo() {
		return spreaderNo;
	}

	public void setSpreaderNo(String spreaderNo) {
		this.spreaderNo = spreaderNo;
	}

	public String getPreNo() {
		return preNo;
	}

	public void setPreNo(String preNo) {
		this.preNo = preNo;
	}

	public String getInputNo() {
		return inputNo;
	}

	public void setInputNo(String inputNo) {
		this.inputNo = inputNo;
	}

	public String getReviewNo() {
		return reviewNo;
	}

	public void setReviewNo(String reviewNo) {
		this.reviewNo = reviewNo;
	}

	public String getPatchBoltNo() {
		return patchBoltNo;
	}

	public void setPatchBoltNo(String patchBoltNo) {
		this.patchBoltNo = patchBoltNo;
	}

	public String getPersonCheckNo() {
		return personCheckNo;
	}

	public void setPersonCheckNo(String personCheckNo) {
		this.personCheckNo = personCheckNo;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getCheckNo() {
		return checkNo;
	}

	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}

	public String getFinalNo() {
		return finalNo;
	}

	public void setFinalNo(String finalNo) {
		this.finalNo = finalNo;
	}

	public String getInputDate() {
		return inputDate;
	}

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}

	public String getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(String reviewDate) {
		this.reviewDate = reviewDate;
	}

	public String getPatchBoltDate() {
		return patchBoltDate;
	}

	public void setPatchBoltDate(String patchBoltDate) {
		this.patchBoltDate = patchBoltDate;
	}

	public String getPersonCheckDate() {
		return personCheckDate;
	}

	public void setPersonCheckDate(String personCheckDate) {
		this.personCheckDate = personCheckDate;
	}

	public String getPhoneDate() {
		return phoneDate;
	}

	public void setPhoneDate(String phoneDate) {
		this.phoneDate = phoneDate;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(String finalDate) {
		this.finalDate = finalDate;
	}

	public String getAccLmt() {
		return accLmt;
	}

	public void setAccLmt(String accLmt) {
		this.accLmt = accLmt;
	}

	public String getTaskOwner() {
		return taskOwner;
	}

	public void setTaskOwner(String taskOwner) {
		this.taskOwner = taskOwner;
	}

	public String getSpreaderSupCode() {
		return spreaderSupCode;
	}

	public void setSpreaderSupCode(String spreaderSupCode) {
		this.spreaderSupCode = spreaderSupCode;
	}

	public String getSpreaderTeamCode() {
		return spreaderTeamCode;
	}

	public void setSpreaderTeamCode(String spreaderTeamCode) {
		this.spreaderTeamCode = spreaderTeamCode;
	}

	public String getSpreaderAreaCode() {
		return spreaderAreaCode;
	}

	public void setSpreaderAreaCode(String spreaderAreaCode) {
		this.spreaderAreaCode = spreaderAreaCode;
	}
}
