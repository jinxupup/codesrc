package com.jjb.ecms.facility.nodeobject;

import java.io.Serializable;

/**
 * 预审确认信息
 * @author hp
 *
 */
public class ApplyNodePreCheckData implements Serializable {

	private static final long serialVersionUID = -8766043328145094237L;
	private String opUserNo ;//操作员工号 
	private String signFileInd ;//客户签名状况
	private String idFileInd ;//身份证明状况
	private String jobFileInd ;//工作证明状况
	private String confirmType ;//确认类型 
	private String cfOwningBranch;//客户经理确认的发卡网点
	private String spreaderType;//推广渠道
	private String pics;//预审确认时提交的申请文件数据清单
	
	public String getOpUserNo() {
		return opUserNo;
	}
	public void setOpUserNo(String opUserNo) {
		this.opUserNo = opUserNo;
	}
	public String getSignFileInd() {
		return signFileInd;
	}
	public void setSignFileInd(String signFileInd) {
		this.signFileInd = signFileInd;
	}
	public String getIdFileInd() {
		return idFileInd;
	}
	public void setIdFileInd(String idFileInd) {
		this.idFileInd = idFileInd;
	}
	public String getJobFileInd() {
		return jobFileInd;
	}
	public void setJobFileInd(String jobFileInd) {
		this.jobFileInd = jobFileInd;
	}
	public String getConfirmType() {
		return confirmType;
	}
	public void setConfirmType(String confirmType) {
		this.confirmType = confirmType;
	}
	public String getCfOwningBranch() {
		return cfOwningBranch;
	}
	public void setCfOwningBranch(String cfOwningBranch) {
		this.cfOwningBranch = cfOwningBranch;
	}
	public String getSpreaderType() {
		return spreaderType;
	}
	public void setSpreaderType(String spreaderType) {
		this.spreaderType = spreaderType;
	}
	public String getPics() {
		return pics;
	}
	public void setPics(String pics) {
		this.pics = pics;
	}
	
}
