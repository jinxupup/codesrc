/**
 * 
 */
package com.jjb.ecms.facility.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 申请工作详情类
 * @author JYData-R&D-L.L
 * @date 2016年9月7日 下午5:00:03
 * @version V1.0  
 */
public class ApplyTaskDetailsDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String startDate;//开始时间
	private String endDate;//结束时间
	private String operatorId;//操作员ID
	private String proName;//任务名称
	private String appNo;//申请件编号
	private String name;//客户姓名
	private String idNo;//客户证件号码
	private String appType;//申请类型
	private String productCd;//产品编号
	private String corpName;//单位名称
	private String rtfState;//申请件状态
	private String procInstId;//工作流-processId
	private String taskId;//工作流-任务表主键ID
	private String taskDefKey;//工作流-任务表 -申请件任务key
	private String owner;//工作流-任务表-任务所属人
	private String org;//机构
	private Integer taskCnt;//工作流-任务表-任务数量
	private String priorit;//第一优先级
	
	private String currRtfState;//当前申请件状态
	private Date taskProcDate;//任务处理时间-yyyy-mm-dd HH:mm:ss
	private Date inputDate;//申请时间-yyyy-mm-dd HH:mm:ss
	private BigDecimal appLmt;//申请额度
	private BigDecimal basicLmt;//初审额度
	private BigDecimal finalLmt;//终审额度
	
	private String inputRemark;//录入人员备注/客户经理备注
	private String reviewRemark;//复核备注
	private String preRemark;//预审备注
	private String basicRemark;//初审备注
	private String telRemark;//电调备注
	private String finalRemark;//终审备注
	private String patchRemark;//补件备注
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
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
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getProductCd() {
		return productCd;
	}
	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}
	public String getCorpName() {
		return corpName;
	}
	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}
	public String getRtfState() {
		return rtfState;
	}
	public void setRtfState(String rtfState) {
		this.rtfState = rtfState;
	}
	public String getProcInstId() {
		return procInstId;
	}
	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskDefKey() {
		return taskDefKey;
	}
	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public Integer getTaskCnt() {
		return taskCnt;
	}
	public void setTaskCnt(Integer taskCnt) {
		this.taskCnt = taskCnt;
	}
	public String getPriorit() {
		return priorit;
	}
	public void setPriorit(String priorit) {
		this.priorit = priorit;
	}
	public String getCurrRtfState() {
		return currRtfState;
	}
	public void setCurrRtfState(String currRtfState) {
		this.currRtfState = currRtfState;
	}
	public Date getTaskProcDate() {
		return taskProcDate;
	}
	public void setTaskProcDate(Date taskProcDate) {
		this.taskProcDate = taskProcDate;
	}
	public Date getInputDate() {
		return inputDate;
	}
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	public BigDecimal getAppLmt() {
		return appLmt;
	}
	public void setAppLmt(BigDecimal appLmt) {
		this.appLmt = appLmt;
	}
	public BigDecimal getBasicLmt() {
		return basicLmt;
	}
	public void setBasicLmt(BigDecimal basicLmt) {
		this.basicLmt = basicLmt;
	}
	public BigDecimal getFinalLmt() {
		return finalLmt;
	}
	public void setFinalLmt(BigDecimal finalLmt) {
		this.finalLmt = finalLmt;
	}
	public String getInputRemark() {
		return inputRemark;
	}
	public void setInputRemark(String inputRemark) {
		this.inputRemark = inputRemark;
	}
	public String getReviewRemark() {
		return reviewRemark;
	}
	public void setReviewRemark(String reviewRemark) {
		this.reviewRemark = reviewRemark;
	}
	public String getPreRemark() {
		return preRemark;
	}
	public void setPreRemark(String preRemark) {
		this.preRemark = preRemark;
	}
	public String getBasicRemark() {
		return basicRemark;
	}
	public void setBasicRemark(String basicRemark) {
		this.basicRemark = basicRemark;
	}
	public String getTelRemark() {
		return telRemark;
	}
	public void setTelRemark(String telRemark) {
		this.telRemark = telRemark;
	}
	public String getFinalRemark() {
		return finalRemark;
	}
	public void setFinalRemark(String finalRemark) {
		this.finalRemark = finalRemark;
	}
	public String getPatchRemark() {
		return patchRemark;
	}
	public void setPatchRemark(String patchRemark) {
		this.patchRemark = patchRemark;
	}
		
}
