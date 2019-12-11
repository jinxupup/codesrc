package com.jjb.ecms.facility.dto;

import java.io.Serializable;

/**
 * 简单的用户实体类，一般都是从acl user 中取出再放入缓存中。
 * @author BIG.K.K
 *
 */
public class SimpleUser  implements Serializable{

	private static final long serialVersionUID = 1L;
	private String userNo;//用户登录名
	private String userChName;//用户中文名
	private String branchId;//用户所属分支行机构
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getUserChName() {
		return userChName;
	}
	public void setUserChName(String userChName) {
		this.userChName = userChName;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	
	
}
