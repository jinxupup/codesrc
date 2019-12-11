package com.jjb.ecms.facility.nodeobject;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 申请流程公共对象
 * @author JYData-R&D-HN
 * @date 2016年9月1日 上午11:59:44
 * @version V1.0
 */
public class ApplyNodeCommonData implements Serializable {

	private static final long serialVersionUID = -8766043328145094237L;
	private String org; // 机构
	private String appNo; // 申请编号
	private String appType; // 申请类型
	private String name; // 姓名
	private String idType; // 证件类型
	private String idNo; // 证件号码
	private String productCd; // 卡产品代码
	private String approveQuickFlag; // 快速审批标志
	private String appProperty;//进件属性
	private String applyStatus; // 审批结果
	private String rtfStateType; // 审批状态
	private String rejectReason; // 拒绝原因
	private String operatorId; // 操作员ID
	private Date date;
//	private String isRefuse;  //判断走终审还是失败申请
//	private String ifSkipNext;  //判断是否跳过下一个节点（也可以多个），例如免电话调查、初审非直接拒绝
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
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
	public String getProductCd() {
		return productCd;
	}
	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}
	public String getAppProperty() {
		return appProperty;
	}
	public void setAppProperty(String appProperty) {
		this.appProperty = appProperty;
	}
	public String getApplyStatus() {
		return applyStatus;
	}
	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}
	public String getRtfStateType() {
		return rtfStateType;
	}
	public void setRtfStateType(String rtfStateType) {
		this.rtfStateType = rtfStateType;
	}
	public String getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getApproveQuickFlag() {
		return approveQuickFlag;
	}
	public void setApproveQuickFlag(String approveQuickFlag) {
		this.approveQuickFlag = approveQuickFlag;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ApplyNodeCommonData [org=");
		builder.append(org);
		builder.append(", appNo=");
		builder.append(appNo);
		builder.append(", appType=");
		builder.append(appType);
		builder.append(", name=");
		builder.append(name);
		builder.append(", idType=");
		builder.append(idType);
		builder.append(", idNo=");
		builder.append(idNo);
		builder.append(", productCd=");
		builder.append(productCd);
		builder.append(", approveQuickFlag=");
		builder.append(approveQuickFlag);
		builder.append(", appProperty=");
		builder.append(appProperty);
		builder.append(", applyStatus=");
		builder.append(applyStatus);
		builder.append(", rtfStateType=");
		builder.append(rtfStateType);
		builder.append(", rejectReason=");
		builder.append(rejectReason);
		builder.append(", operatorId=");
		builder.append(operatorId);
		builder.append(", date=");
		builder.append(date);
		builder.append("]");
		return builder.toString();
	}
	
	
}
