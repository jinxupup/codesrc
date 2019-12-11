/**
 * 
 */
package com.jjb.ecms.facility.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 各审批节点的操作区实体类
 * @author JYData-R&D-Big T.T
 * @date 2018年1月30日 下午1:55:59
 * @version V1.0  
 */

public class ApplyOperateDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//TM_APP_MAIN
	private String rtfState;//申请件状态
	private String approveQuickFlag;//快速审批
	private String isPriority;//优先处理标志
	private BigDecimal sugLmt;//系统建议额度
	private Integer pointResult;//评分值
	private String isFreeTelCheck;//是否免电话调查
	private BigDecimal chkLmt;//初审额度
	private String isSendSmsRefused;//是否发送拒绝短信
	private String isSendSmsPatch;//是否发送补件短信
	private String specialApprove;//特批
	private BigDecimal bankInnerSugLmt;//内评建议额度
	private Integer bankInnerPointResult;//内评评分值
	private String bankInnerBreakRule;//触犯规则
	private String bankInnerMome;//内评评论
	private String productCd;//卡产品代码
	private BigDecimal accLmt;//核准额度
	private BigDecimal appLmt;//申请额度
	private String refuseCode;//拒绝原因
	private String riskClassic;//风险等级（备用字段obText8）
	private String isOldCust;//是否是老客户
	private Date createDate;//主卡申请时间
	private String pyhCd;//卡面
	
	//TM_APP_PRIM_APPLICANT_INFO
	private String homeState;//家庭所在的省
	private String homeCity;//家庭所在的市
	private String homeZone;//家庭所在的区
	private String homeAdd;//家庭地址
	private String name;//姓名
	private String idNo;//证件号码
	private String idType;//证件类型
	private String cellphone;//手机号
	private String email;//电子邮箱
	private String gender;//性别
	private Date birthday;//生日
	private String homePhone;//家庭电话
	private String empAdd;//公司地址
	private String empPhone;//公司电话
	private String embLogo;//凸印名称
	
	//TM_APP_PRIM_CARD_INFO
	private String reviewNo;//复核人工号
	private String reviewName;//复核人姓名
	private String stmtMediaType;//账单类型
	private String stmtMailAddrInd;//账单寄送地址
	private String spreaderName;//推广人姓名
	private String spreaderTelephone;//推广人手机号
	private String spreaderType;//推广渠道
	private String spreaderMode;//推广方式
	private String spreaderNo;//推广人编号
	private String spreaderBranchOne;//推广人所属分行
	private String isPrdChange;//卡产品是否降级
	
	//TM_APP_PRIM_CONTACT_INFO
	private String contactName;//联系人姓名
	private String contactMobile;//联系人电话
	private String contactRelation;//联系人与申请人关系
	private String contactOMobile;//其他联系人电话
	
	/**
	 * @return the rtfState
	 */
	public String getRtfState() {
		return rtfState;
	}
	/**
	 * @param rtfState the rtfState to set
	 */
	public void setRtfState(String rtfState) {
		this.rtfState = rtfState;
	}
	/**
	 * @return the approveQuickFlag
	 */
	public String getApproveQuickFlag() {
		return approveQuickFlag;
	}
	/**
	 * @param approveQuickFlag the approveQuickFlag to set
	 */
	public void setApproveQuickFlag(String approveQuickFlag) {
		this.approveQuickFlag = approveQuickFlag;
	}
	/**
	 * @return the isPriority
	 */
	public String getIsPriority() {
		return isPriority;
	}
	/**
	 * @param isPriority the isPriority to set
	 */
	public void setIsPriority(String isPriority) {
		this.isPriority = isPriority;
	}
	/**
	 * @return the sugLmt
	 */
	public BigDecimal getSugLmt() {
		return sugLmt;
	}
	/**
	 * @param sugLmt the sugLmt to set
	 */
	public void setSugLmt(BigDecimal sugLmt) {
		this.sugLmt = sugLmt;
	}
	/**
	 * @return the pointResult
	 */
	public Integer getPointResult() {
		return pointResult;
	}
	/**
	 * @param pointResult the pointResult to set
	 */
	public void setPointResult(Integer pointResult) {
		this.pointResult = pointResult;
	}
	/**
	 * @return the isFreeTelCheck
	 */
	public String getIsFreeTelCheck() {
		return isFreeTelCheck;
	}
	/**
	 * @param isFreeTelCheck the isFreeTelCheck to set
	 */
	public void setIsFreeTelCheck(String isFreeTelCheck) {
		this.isFreeTelCheck = isFreeTelCheck;
	}
	/**
	 * @return the chkLmt
	 */
	public BigDecimal getChkLmt() {
		return chkLmt;
	}
	/**
	 * @param chkLmt the chkLmt to set
	 */
	public void setChkLmt(BigDecimal chkLmt) {
		this.chkLmt = chkLmt;
	}
	/**
	 * @return the isSendSmsRefused
	 */
	public String getIsSendSmsRefused() {
		return isSendSmsRefused;
	}
	/**
	 * @param isSendSmsRefused the isSendSmsRefused to set
	 */
	public void setIsSendSmsRefused(String isSendSmsRefused) {
		this.isSendSmsRefused = isSendSmsRefused;
	}
	/**
	 * @return the isSendSmsPatch
	 */
	public String getIsSendSmsPatch() {
		return isSendSmsPatch;
	}
	/**
	 * @param isSendSmsPatch the isSendSmsPatch to set
	 */
	public void setIsSendSmsPatch(String isSendSmsPatch) {
		this.isSendSmsPatch = isSendSmsPatch;
	}
	/**
	 * @return the specialApprove
	 */
	public String getSpecialApprove() {
		return specialApprove;
	}
	/**
	 * @param specialApprove the specialApprove to set
	 */
	public void setSpecialApprove(String specialApprove) {
		this.specialApprove = specialApprove;
	}
	/**
	 * @return the bankInnerSugLmt
	 */
	public BigDecimal getBankInnerSugLmt() {
		return bankInnerSugLmt;
	}
	/**
	 * @param bankInnerSugLmt the bankInnerSugLmt to set
	 */
	public void setBankInnerSugLmt(BigDecimal bankInnerSugLmt) {
		this.bankInnerSugLmt = bankInnerSugLmt;
	}
	/**
	 * @return the bankInnerPointResult
	 */
	public Integer getBankInnerPointResult() {
		return bankInnerPointResult;
	}
	/**
	 * @param bankInnerPointResult the bankInnerPointResult to set
	 */
	public void setBankInnerPointResult(Integer bankInnerPointResult) {
		this.bankInnerPointResult = bankInnerPointResult;
	}
	/**
	 * @return the bankInnerBreakRule
	 */
	public String getBankInnerBreakRule() {
		return bankInnerBreakRule;
	}
	/**
	 * @param bankInnerBreakRule the bankInnerBreakRule to set
	 */
	public void setBankInnerBreakRule(String bankInnerBreakRule) {
		this.bankInnerBreakRule = bankInnerBreakRule;
	}
	/**
	 * @return the bankInnerMome
	 */
	public String getBankInnerMome() {
		return bankInnerMome;
	}
	/**
	 * @param bankInnerMome the bankInnerMome to set
	 */
	public void setBankInnerMome(String bankInnerMome) {
		this.bankInnerMome = bankInnerMome;
	}
	/**
	 * @return the productCd
	 */
	public String getProductCd() {
		return productCd;
	}
	/**
	 * @param productCd the productCd to set
	 */
	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}
	/**
	 * @return the accLmt
	 */
	public BigDecimal getAccLmt() {
		return accLmt;
	}
	/**
	 * @param accLmt the accLmt to set
	 */
	public void setAccLmt(BigDecimal accLmt) {
		this.accLmt = accLmt;
	}
	/**
	 * @return the appLmt
	 */
	public BigDecimal getAppLmt() {
		return appLmt;
	}
	/**
	 * @param appLmt the appLmt to set
	 */
	public void setAppLmt(BigDecimal appLmt) {
		this.appLmt = appLmt;
	}
	/**
	 * @return the homeState
	 */
	public String getHomeState() {
		return homeState;
	}
	/**
	 * @param homeState the homeState to set
	 */
	public void setHomeState(String homeState) {
		this.homeState = homeState;
	}
	/**
	 * @return the homeCity
	 */
	public String getHomeCity() {
		return homeCity;
	}
	/**
	 * @param homeCity the homeCity to set
	 */
	public void setHomeCity(String homeCity) {
		this.homeCity = homeCity;
	}
	/**
	 * @return the homeZone
	 */
	public String getHomeZone() {
		return homeZone;
	}
	/**
	 * @param homeZone the homeZone to set
	 */
	public void setHomeZone(String homeZone) {
		this.homeZone = homeZone;
	}
	/**
	 * @return the homeAdd
	 */
	public String getHomeAdd() {
		return homeAdd;
	}
	/**
	 * @param homeAdd the homeAdd to set
	 */
	public void setHomeAdd(String homeAdd) {
		this.homeAdd = homeAdd;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the idNo
	 */
	public String getIdNo() {
		return idNo;
	}
	/**
	 * @param idNo the idNo to set
	 */
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	/**
	 * @return the idType
	 */
	public String getIdType() {
		return idType;
	}
	/**
	 * @param idType the idType to set
	 */
	public void setIdType(String idType) {
		this.idType = idType;
	}
	/**
	 * @return the cellphone
	 */
	public String getCellphone() {
		return cellphone;
	}
	/**
	 * @param cellphone the cellphone to set
	 */
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}
	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	/**
	 * @return the homePhone
	 */
	public String getHomePhone() {
		return homePhone;
	}
	/**
	 * @param homePhone the homePhone to set
	 */
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	/**
	 * @return the empAdd
	 */
	public String getEmpAdd() {
		return empAdd;
	}
	/**
	 * @param empAdd the empAdd to set
	 */
	public void setEmpAdd(String empAdd) {
		this.empAdd = empAdd;
	}
	/**
	 * @return the empPhone
	 */
	public String getEmpPhone() {
		return empPhone;
	}
	/**
	 * @param empPhone the empPhone to set
	 */
	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}
	/**
	 * @return the embLogo
	 */
	public String getEmbLogo() {
		return embLogo;
	}
	/**
	 * @param embLogo the embLogo to set
	 */
	public void setEmbLogo(String embLogo) {
		this.embLogo = embLogo;
	}
	/**
	 * @return the reviewNo
	 */
	public String getReviewNo() {
		return reviewNo;
	}
	/**
	 * @param reviewNo the reviewNo to set
	 */
	public void setReviewNo(String reviewNo) {
		this.reviewNo = reviewNo;
	}
	/**
	 * @return the reviewName
	 */
	public String getReviewName() {
		return reviewName;
	}
	/**
	 * @param reviewName the reviewName to set
	 */
	public void setReviewName(String reviewName) {
		this.reviewName = reviewName;
	}
	/**
	 * @return the stmtMediaType
	 */
	public String getStmtMediaType() {
		return stmtMediaType;
	}
	/**
	 * @param stmtMediaType the stmtMediaType to set
	 */
	public void setStmtMediaType(String stmtMediaType) {
		this.stmtMediaType = stmtMediaType;
	}
	/**
	 * @return the stmtMailAddrInd
	 */
	public String getStmtMailAddrInd() {
		return stmtMailAddrInd;
	}
	/**
	 * @param stmtMailAddrInd the stmtMailAddrInd to set
	 */
	public void setStmtMailAddrInd(String stmtMailAddrInd) {
		this.stmtMailAddrInd = stmtMailAddrInd;
	}
	/**
	 * @return the spreaderName
	 */
	public String getSpreaderName() {
		return spreaderName;
	}
	/**
	 * @param spreaderName the spreaderName to set
	 */
	public void setSpreaderName(String spreaderName) {
		this.spreaderName = spreaderName;
	}
	/**
	 * @return the spreaderTelephone
	 */
	public String getSpreaderTelephone() {
		return spreaderTelephone;
	}
	/**
	 * @param spreaderTelephone the spreaderTelephone to set
	 */
	public void setSpreaderTelephone(String spreaderTelephone) {
		this.spreaderTelephone = spreaderTelephone;
	}
	/**
	 * @return the spreaderType
	 */
	public String getSpreaderType() {
		return spreaderType;
	}
	/**
	 * @param spreaderType the spreaderType to set
	 */
	public void setSpreaderType(String spreaderType) {
		this.spreaderType = spreaderType;
	}
	/**
	 * @return the spreaderMode
	 */
	public String getSpreaderMode() {
		return spreaderMode;
	}
	/**
	 * @param spreaderMode the spreaderMode to set
	 */
	public void setSpreaderMode(String spreaderMode) {
		this.spreaderMode = spreaderMode;
	}
	/**
	 * @return the spreaderNo
	 */
	public String getSpreaderNo() {
		return spreaderNo;
	}
	/**
	 * @param spreaderNo the spreaderNo to set
	 */
	public void setSpreaderNo(String spreaderNo) {
		this.spreaderNo = spreaderNo;
	}
	/**
	 * @return the spreaderBranchOne
	 */
	public String getSpreaderBranchOne() {
		return spreaderBranchOne;
	}
	/**
	 * @param spreaderBranchOne the spreaderBranchOne to set
	 */
	public void setSpreaderBranchOne(String spreaderBranchOne) {
		this.spreaderBranchOne = spreaderBranchOne;
	}
	/**
	 * @return the isPrdChange
	 */
	public String getIsPrdChange() {
		return isPrdChange;
	}
	/**
	 * @param isPrdChange the isPrdChange to set
	 */
	public void setIsPrdChange(String isPrdChange) {
		this.isPrdChange = isPrdChange;
	}
	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}
	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	/**
	 * @return the contactMobile
	 */
	public String getContactMobile() {
		return contactMobile;
	}
	/**
	 * @param contactMobile the contactMobile to set
	 */
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}
	/**
	 * @return the contactOMobile
	 */
	public String getContactOMobile() {
		return contactOMobile;
	}
	/**
	 * @param contactOMobile the contactOMobile to set
	 */
	public void setContactOMobile(String contactOMobile) {
		this.contactOMobile = contactOMobile;
	}
	/**
	 * @return the contactRelation
	 */
	public String getContactRelation() {
		return contactRelation;
	}
	/**
	 * @param contactRelation the contactRelation to set
	 */
	public void setContactRelation(String contactRelation) {
		this.contactRelation = contactRelation;
	}
	/**
	 * @return the riskClassic
	 */
	public String getRiskClassic() {
		return riskClassic;
	}
	/**
	 * @param riskClassic the riskClassic to set
	 */
	public void setRiskClassic(String riskClassic) {
		this.riskClassic = riskClassic;
	}
	public String getRefuseCode() {
		return refuseCode;
	}
	public void setRefuseCode(String refuseCode) {
		this.refuseCode = refuseCode;
	}
	/**
	 * @return the isOldCust
	 */
	public String getIsOldCust() {
		return isOldCust;
	}
	/**
	 * @param isOldCust the isOldCust to set
	 */
	public void setIsOldCust(String isOldCust) {
		this.isOldCust = isOldCust;
	}
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the pyhCd
	 */
	public String getPyhCd() {
		return pyhCd;
	}
	/**
	 * @param pyhCd the pyhCd to set
	 */
	public void setPyhCd(String pyhCd) {
		this.pyhCd = pyhCd;
	}
	
}
