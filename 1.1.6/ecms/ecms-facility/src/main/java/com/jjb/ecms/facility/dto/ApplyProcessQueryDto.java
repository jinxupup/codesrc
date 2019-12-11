/**
 *
 */
package com.jjb.ecms.facility.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 申请进度查询DTO
 * @author JYData-R&D-BigK.K
 * @date 2016年9月18日 上午11:07:42
 * @version V1.0
 */
public class ApplyProcessQueryDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/*TM_APP_MAIN 表数据*/
	private String appNo;
	private String appType;
	private String rtfState;
	private String productCd;
	private String appSource;
	private String owningBranch;
	private String imageNum;
	private String appProperty;
	private String refuseCode;
	private String remark;
	private String taskLastOpUser;//上一任务所属人
	private String owner;//ACT_RU_TASK拷贝的任务所属人
	private String taskId;//ACT_RU_TASK拷贝的任务编号
	private String updateUser;
	private Date updateDate;
	private String accLmt;
	private String sugLmt;
	private String fileFlag;//归档标志
	/*TM_APP_CUST_INFO 表数据*/
	private String name;
	private String idType;
	private String idNo;
	private String cellPhone;
	private String corpName;
	private String empPhone;
	private String custType;
	private String recordStatus;//客户申请记录状态(主要是附卡的)
	private String bscSuppInd;//主附卡标志(申请客户信息类型)
	
	/*TM_APP_PRIM_CARD_INFO*/
	private String spreader;
	private String inputUser;
	private Date inputDate;
	private String spreaderIsEff;//是否属于有效的推广
	private String spreaderNo; // 推广人编号
	private String spreaderSupCode; //推广主管代码
	private String spreaderAreaCode;//推广区域代码
	
	
	/*ACT_RU_TASK*/
	private String assignee;
	private String taskDefKey;//任务定义Key
	
	/*TM_APP_AUDIT*/
	private String isInstalment;//是否分期
	private String isRealtimeIssuing;//是否实时建账建卡
	private String isOldCust;//是否老客户
	private String isReturned;//退回标记
	private String isRetrialApp;//重审件(新件)标记
	private String isHaveRetrial;//重审件(原件)标记

	
	/*TM_MIR_CARD*/
	private String cardNo;
	private String blockCode;//卡产品代码
	private String ifSwiped;//是否完成首刷
	private String activateInd;//是否已激活
	private String cardStatus;//核卡情况
	private String ifNewUser;//是否新用户

	/*TM_PRODUCT*/
	private String productDesc;//卡产品描述

	/**组合查询条件*/
	private Date beginDate;
	private Date endDate;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getRtfState() {
		return rtfState;
	}

	public void setRtfState(String rtfState) {
		this.rtfState = rtfState;
	}

	public String getProductCd() {
		return productCd;
	}

	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}

	public String getAppSource() {
		return appSource;
	}

	public void setAppSource(String appSource) {
		this.appSource = appSource;
	}

	public String getOwningBranch() {
		return owningBranch;
	}

	public void setOwningBranch(String owningBranch) {
		this.owningBranch = owningBranch;
	}

	public String getImageNum() {
		return imageNum;
	}

	public void setImageNum(String imageNum) {
		this.imageNum = imageNum;
	}

	public String getAppProperty() {
		return appProperty;
	}

	public void setAppProperty(String appProperty) {
		this.appProperty = appProperty;
	}

	public String getRefuseCode() {
		return refuseCode;
	}

	public void setRefuseCode(String refuseCode) {
		this.refuseCode = refuseCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTaskLastOpUser() {
		return taskLastOpUser;
	}

	public void setTaskLastOpUser(String taskLastOpUser) {
		this.taskLastOpUser = taskLastOpUser;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getAccLmt() {
		return accLmt;
	}

	public void setAccLmt(String accLmt) {
		this.accLmt = accLmt;
	}

	public String getSugLmt() {
		return sugLmt;
	}

	public void setSugLmt(String sugLmt) {
		this.sugLmt = sugLmt;
	}

	public String getFileFlag() {
		return fileFlag;
	}

	public void setFileFlag(String fileFlag) {
		this.fileFlag = fileFlag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
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

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	public String getBscSuppInd() {
		return bscSuppInd;
	}

	public void setBscSuppInd(String bscSuppInd) {
		this.bscSuppInd = bscSuppInd;
	}

	public String getSpreader() {
		return spreader;
	}

	public void setSpreader(String spreader) {
		this.spreader = spreader;
	}

	public String getInputUser() {
		return inputUser;
	}

	public void setInputUser(String inputUser) {
		this.inputUser = inputUser;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public String getSpreaderIsEff() {
		return spreaderIsEff;
	}

	public void setSpreaderIsEff(String spreaderIsEff) {
		this.spreaderIsEff = spreaderIsEff;
	}

	public String getSpreaderNo() {
		return spreaderNo;
	}

	public void setSpreaderNo(String spreaderNo) {
		this.spreaderNo = spreaderNo;
	}

	public String getSpreaderSupCode() {
		return spreaderSupCode;
	}

	public void setSpreaderSupCode(String spreaderSupCode) {
		this.spreaderSupCode = spreaderSupCode;
	}

	public String getSpreaderAreaCode() {
		return spreaderAreaCode;
	}

	public void setSpreaderAreaCode(String spreaderAreaCode) {
		this.spreaderAreaCode = spreaderAreaCode;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getTaskDefKey() {
		return taskDefKey;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}

	public String getIsInstalment() {
		return isInstalment;
	}

	public void setIsInstalment(String isInstalment) {
		this.isInstalment = isInstalment;
	}

	public String getIsRealtimeIssuing() {
		return isRealtimeIssuing;
	}

	public void setIsRealtimeIssuing(String isRealtimeIssuing) {
		this.isRealtimeIssuing = isRealtimeIssuing;
	}

	public String getIsOldCust() {
		return isOldCust;
	}

	public void setIsOldCust(String isOldCust) {
		this.isOldCust = isOldCust;
	}

	public String getIsReturned() {
		return isReturned;
	}

	public void setIsReturned(String isReturned) {
		this.isReturned = isReturned;
	}

	public String getIsRetrialApp() {
		return isRetrialApp;
	}

	public void setIsRetrialApp(String isRetrialApp) {
		this.isRetrialApp = isRetrialApp;
	}

	public String getIsHaveRetrial() {
		return isHaveRetrial;
	}

	public void setIsHaveRetrial(String isHaveRetrial) {
		this.isHaveRetrial = isHaveRetrial;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getIfSwiped() {
		return ifSwiped;
	}

	public void setIfSwiped(String ifSwiped) {
		this.ifSwiped = ifSwiped;
	}

	public String getActivateInd() {
		return activateInd;
	}

	public void setActivateInd(String activateInd) {
		this.activateInd = activateInd;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	public String getIfNewUser() {
		return ifNewUser;
	}

	public void setIfNewUser(String ifNewUser) {
		this.ifNewUser = ifNewUser;
	}

	public String getBlockCode() {
		return blockCode;
	}

	public void setBlockCode(String blockCode) {
		this.blockCode = blockCode;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
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
}
