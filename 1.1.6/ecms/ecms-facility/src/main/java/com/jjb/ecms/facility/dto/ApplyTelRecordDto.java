package com.jjb.ecms.facility.dto;

import java.io.Serializable;

/**
 * @description 电话调查模型
 * @author hn
 * @date 2016年8月29日15:45:55 
 */
public class ApplyTelRecordDto implements Serializable{

	private static final long serialVersionUID = 1L;

	private String phoneType;//致电类型
	
	private String phoneNum;//致电号码
	
	private String phoneDate;//致电日期
	
	private String phoneResult;//致电结果
	
	private String remark;//备注

	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getPhoneDate() {
		return phoneDate;
	}

	public void setPhoneDate(String phoneDate) {
		this.phoneDate = phoneDate;
	}

	public String getPhoneResult() {
		return phoneResult;
	}

	public void setPhoneResult(String phoneResult) {
		this.phoneResult = phoneResult;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
