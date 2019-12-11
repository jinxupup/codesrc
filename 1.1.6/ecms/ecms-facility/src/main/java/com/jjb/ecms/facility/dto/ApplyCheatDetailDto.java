package com.jjb.ecms.facility.dto;

import java.io.Serializable;

/**
 * @description: 申请欺诈详细信息模型
 * @author -BigZ.Y
 * @date 2016年9月11日 下午2:52:32
 */
public class ApplyCheatDetailDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;// 申请人姓名

	private String appNo;// 申请件编号

	private String idType;// 证件类型

	private String idNo;// 证件号码

	private String cellPhone;// 申请人手机号

	private String homePhone;// 家庭电话

	private String homeAdd;// 家庭地址

	private String corpName;// 公司名称

	private String empPhone;// 公司电话

	private String empAdd;// 公司地址

	private String refuseCode;// 拒绝原因

	private String owner;// 任务所属人

	private String rtfState;// 审批状态
	// 申请提交至当前日期的天数
	//(days(current_date) - days(TM_APP_MAIN.CREATE_DATE))
	private Integer diffCurrDay;
	// 申请提交至当前日期的时间 
	//timestampdiff(16,CHAR(TIMESTAMP(current_date)-TM_APP_MAIN.CREATE_DATE))
	private Integer diffCurrTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
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

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getHomeAdd() {
		return homeAdd;
	}

	public void setHomeAdd(String homeAdd) {
		this.homeAdd = homeAdd;
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

	public String getEmpAdd() {
		return empAdd;
	}

	public void setEmpAdd(String empAdd) {
		this.empAdd = empAdd;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getRefuseCode() {
		return refuseCode;
	}

	public void setRefuseCode(String refuseCode) {
		this.refuseCode = refuseCode;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getRtfState() {
		return rtfState;
	}

	public void setRtfState(String rtfState) {
		this.rtfState = rtfState;
	}

	public Integer getDiffCurrDay() {
		return diffCurrDay;
	}

	public void setDiffCurrDay(Integer diffCurrDay) {
		this.diffCurrDay = diffCurrDay;
	}

	public Integer getDiffCurrTime() {
		return diffCurrTime;
	}

	public void setDiffCurrTime(Integer diffCurrTime) {
		this.diffCurrTime = diffCurrTime;
	}

}
