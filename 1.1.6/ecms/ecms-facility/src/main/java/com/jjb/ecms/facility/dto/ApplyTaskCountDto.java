package com.jjb.ecms.facility.dto;

import java.io.Serializable;

/**
 * @Description: 申请工作量查询信息模型
 * @author JYData-R&D-L.L
 * @date 2016年9月2日 上午11:08:46
 * @version V1.0  
 */
public class ApplyTaskCountDto implements Serializable {	
	private static final long serialVersionUID = 1L;

	private String startDate;
	private String endDate;
	private String operatorId;
	private String proName;
	private String nums;
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
	public String getNums() {
		return nums;
	}
	public void setNums(String nums) {
		this.nums = nums;
	}
	
}
