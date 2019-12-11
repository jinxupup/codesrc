package com.jjb.ecms.facility.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 待办任务模型
 * @author hn
 * @date 2016年8月29日11:30:35
 */
public class ApplyTaskListDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;//姓名

	private String appNo;//申请件编号

	private String appType;//申请类型

	private String productCd;//申请卡产品

	private String idType;//证件类型

	private String idNo;//证件号码

	private BigDecimal appLmt;//申请额度

	private BigDecimal chkLmt;//初审额度

	private String approveQuickFlag;//快速审批标识

	private String isPriority;//优先标识

	private String owningBranch;//受理网点

	private String proInstId;//流程实例Id

	private String taskDefKey;//任务定义Key

	private String proName;//流程实例名

	private String taskId;//任务ID

	private String taskName;//任务名

	private String owner;//任务所属人

	private String assignee;//任务分配人

	private Date claimTime;//获取时间

	private Date createTime; //进件时间

	private String corpName;//单位名

	private String backMark;//退回标识

	private String appSource;//申请渠道
	private String cellPhone;

	private String incomeType;//进件方式
	private String custType;
	private String acceptName;
	private Date startDate;
	private Date endDate;
	private String acceptNum;
	private String applyFromType;

	public String getTaskLastOpUser() {
		return taskLastOpUser;
	}

	public void setTaskLastOpUser(String taskLastOpUser) {
		this.taskLastOpUser = taskLastOpUser;
	}

	//	private String lastOpUser;
	private String taskLastOpUser;
	private String sugLmt;//系统建议额度

	private String isInitiateRetrial;//已重审标记
	private String retrialFlag;//重审件标记（对应tmappmain表ob_text2字段）
	private String spreaderInfo; //推广人
	private String appFlag; //申请件标签

	public String getAppFlag() {
		return appFlag;
	}

	public void setAppFlag(String appFlag) {
		this.appFlag = appFlag;
	}
	public String getCellPhone() {
		return cellPhone;
	}
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
	public String getSpreaderInfo() {
		return spreaderInfo;
	}

	public void setSpreaderInfo(String spreaderInfo) {
		this.spreaderInfo = spreaderInfo;
	}

	public String getSpreaderName() {
		return spreaderName;
	}

	public void setSpreaderName(String spreaderName) {
		this.spreaderName = spreaderName;
	}

	public String getSpreaderNo() {
		return spreaderNo;
	}

	public void setSpreaderNo(String spreaderNo) {
		this.spreaderNo = spreaderNo;
	}

	public String getInputInfo() {
		return inputInfo;
	}

	public void setInputInfo(String inputInfo) {
		this.inputInfo = inputInfo;
	}

	public String getInputNo() {
		return inputNo;
	}

	public void setInputNo(String inputNo) {
		this.inputNo = inputNo;
	}

	public String getInputName() {
		return inputName;
	}

	public void setInputName(String inputName) {
		this.inputName = inputName;
	}

	private String spreaderName;
	private String spreaderNo;
	private String inputInfo;//录入人
	private String inputNo;
	private String inputName;
	/**
	 * @return the sugLmt
	 */
	public String getSugLmt() {
		return sugLmt;
	}

	/**
	 * @param sugLmt
	 *            the sugLmt to set
	 */
	public void setSugLmt(String sugLmt) {
		this.sugLmt = sugLmt;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the appNo
	 */
	public String getAppNo() {
		return appNo;
	}

	/**
	 * @param appNo
	 *            the appNo to set
	 */
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	/**
	 * @return the appType
	 */
	public String getAppType() {
		return appType;
	}

	/**
	 * @param appType
	 *            the appType to set
	 */
	public void setAppType(String appType) {
		this.appType = appType;
	}

	/**
	 * @return the idType
	 */
	public String getIdType() {
		return idType;
	}

	/**
	 * @param idType
	 *            the idType to set
	 */
	public void setIdType(String idType) {
		this.idType = idType;
	}

	/**
	 * @return the idNo
	 */
	public String getIdNo() {
		return idNo;
	}

	/**
	 * @param idNo
	 *            the idNo to set
	 */
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	/**
	 * @return the appLmt
	 */
	public BigDecimal getAppLmt() {
		return appLmt;
	}

	/**
	 * @param appLmt
	 *            the appLmt to set
	 */
	public void setAppLmt(BigDecimal appLmt) {
		this.appLmt = appLmt;
	}

	/**
	 * @return the chkLmt
	 */
	public BigDecimal getChkLmt() {
		return chkLmt;
	}

	/**
	 * @param chkLmt
	 *            the chkLmt to set
	 */
	public void setChkLmt(BigDecimal chkLmt) {
		this.chkLmt = chkLmt;
	}

	/**
	 * @return the approveQuickFlag
	 */
	public String getApproveQuickFlag() {
		return approveQuickFlag;
	}

	/**
	 * @param approveQuickFlag
	 *            the approveQuickFlag to set
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
	 * @param isPriority
	 *            the isPriority to set
	 */
	public void setIsPriority(String isPriority) {
		this.isPriority = isPriority;
	}

	/**
	 * @return the owningBranch
	 */
	public String getOwningBranch() {
		return owningBranch;
	}

	/**
	 * @param owningBranch
	 *            the owningBranch to set
	 */
	public void setOwningBranch(String owningBranch) {
		this.owningBranch = owningBranch;
	}

	/**
	 * @return the proInstId
	 */
	public String getProInstId() {
		return proInstId;
	}

	/**
	 * @param proInstId
	 *            the proInstId to set
	 */
	public void setProInstId(String proInstId) {
		this.proInstId = proInstId;
	}

	/**
	 * @return the taskDefKey
	 */
	public String getTaskDefKey() {
		return taskDefKey;
	}

	/**
	 * @param taskDefKey
	 *            the taskDefKey to set
	 */
	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}

	/**
	 * @return the proName
	 */
	public String getProName() {
		return proName;
	}

	/**
	 * @param proName
	 *            the proName to set
	 */
	public void setProName(String proName) {
		this.proName = proName;
	}

	/**
	 * @return the taskId
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId
	 *            the taskId to set
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * @param taskName
	 *            the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @return the assignee
	 */
	public String getAssignee() {
		return assignee;
	}

	/**
	 * @param assignee
	 *            the assignee to set
	 */
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	/**
	 * @return the claimTime
	 */
	public Date getClaimTime() {
		return claimTime;
	}

	/**
	 * @param claimTime
	 *            the claimTime to set
	 */
	public void setClaimTime(Date claimTime) {
		this.claimTime = claimTime;
	}

	/**
	 * @return the corpName
	 */
	public String getCorpName() {
		return corpName;
	}

	/**
	 * @param corpName
	 *            the corpName to set
	 */
	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	/**
	 * @return the backMark
	 */
	public String getBackMark() {
		return backMark;
	}

	/**
	 * @param backMark
	 *            the backMark to set
	 */
	public void setBackMark(String backMark) {
		this.backMark = backMark;
	}

	/**
	 * @return the appSource
	 */
	public String getAppSource() {
		return appSource;
	}

	/**
	 * @param appSource
	 *            the appSource to set
	 */
	public void setAppSource(String appSource) {
		this.appSource = appSource;
	}

	/**
	 * @return the incomeType
	 */
	public String getIncomeType() {
		return incomeType;
	}

	/**
	 * @param incomeType
	 *            the incomeType to set
	 */
	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
	}

	/**
	 * @return the custType
	 */
	public String getCustType() {
		return custType;
	}

	/**
	 * @param custType
	 *            the custType to set
	 */
	public void setCustType(String custType) {
		this.custType = custType;
	}

	/**
	 * @return the acceptName
	 */
	public String getAcceptName() {
		return acceptName;
	}

	/**
	 * @param acceptName
	 *            the acceptName to set
	 */
	public void setAcceptName(String acceptName) {
		this.acceptName = acceptName;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the acceptNum
	 */
	public String getAcceptNum() {
		return acceptNum;
	}

	/**
	 * @param acceptNum
	 *            the acceptNum to set
	 */
	public void setAcceptNum(String acceptNum) {
		this.acceptNum = acceptNum;
	}

	/**
	 * @return the applyFromType
	 */
	public String getApplyFromType() {
		return applyFromType;
	}

	/**
	 * @param applyFromType
	 *            the applyFromType to set
	 */
	public void setApplyFromType(String applyFromType) {
		this.applyFromType = applyFromType;
	}

	/**
	 * @return the lastOpUser
	 */
//	public String getLastOpUser() {
//		return lastOpUser;
//	}

	/**
	 * @param lastOpUser
	 *            the lastOpUser to set
	 */
//	public void setLastOpUser(String lastOpUser) {
//		this.lastOpUser = lastOpUser;
//	}

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
	 * @return the isInitiateRetrial
	 */
	public String getIsInitiateRetrial() {
		return isInitiateRetrial;
	}

	/**
	 * @param isInitiateRetrial the isInitiateRetrial to set
	 */
	public void setIsInitiateRetrial(String isInitiateRetrial) {
		this.isInitiateRetrial = isInitiateRetrial;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**

	 * @return the retrialFlag
	 */
	public String getRetrialFlag() {
		return retrialFlag;
	}

	/**
	 * @param retrialFlag the retrialFlag to set
	 */
	public void setRetrialFlag(String retrialFlag) {
		this.retrialFlag = retrialFlag;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ApplyTaskListDto [name=" + name + ",  spreaderInfo=" + spreaderInfo + ", spreaderNo=" + spreaderNo + ", spreaderName=" + spreaderName + ", inputInfo=" + inputInfo + ", inputNo=" + inputNo+ ",inputName=" + inputName + ", appNo=" + appNo
				+ ", appType=" + appType + ", productCd=" + productCd + ", idType=" + idType + ", idNo="
				+ idNo + ", appLmt=" + appLmt + ", chkLmt=" + chkLmt
				+ ", approveQuickFlag=" + approveQuickFlag + ", isPriority="
				+ isPriority + ", owningBranch=" + owningBranch + ", isInitiateRetrial=" + isInitiateRetrial
				+ ", retrialFlag=" + retrialFlag + ", proInstId=" + proInstId + ", taskDefKey=" + taskDefKey
				+ ", proName=" + proName + ", taskId=" + taskId + ", taskName="
				+ taskName + ", owner=" + owner + ", assignee=" + assignee
				+ ", claimTime=" + claimTime + ", createTime=" + createTime + ", corpName=" + corpName
				+ ", backMark=" + backMark + ", appSource=" + appSource
				+ ", incomeType=" + incomeType + ", custType=" + custType
				+ ", acceptName=" + acceptName + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", acceptNum=" + acceptNum
				+ ", applyFromType=" + applyFromType + ", taskLastOpUser="
				+ taskLastOpUser + "]";
	}

}
