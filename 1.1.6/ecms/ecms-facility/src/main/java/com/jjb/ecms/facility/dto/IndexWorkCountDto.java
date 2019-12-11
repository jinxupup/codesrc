package com.jjb.ecms.facility.dto;

import java.io.Serializable;

/**
 * 首页工作量统计模型
 * @author BIG.LU.KL
 *
 */
public class IndexWorkCountDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String operatorId;
	private String proName;
	private String nums;
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
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	
}
