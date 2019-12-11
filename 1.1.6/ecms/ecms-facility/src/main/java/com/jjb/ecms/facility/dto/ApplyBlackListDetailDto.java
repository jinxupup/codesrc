package com.jjb.ecms.facility.dto;

import java.io.Serializable;

/**
 * @description: 申请欺诈黑名单详细信息模型
 * @author -BigZ.Y
 * @date 2016年9月13日16:49:10 
 */
public class ApplyBlackListDetailDto implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private String id;//ID

	private String name;//申请人姓名
	
	private String blacklistSrc;//黑名单来源
	
	private String memo;//上黑名单原因说明
	
	private String idType;//证件类型
	
	private String idNo;//证件号码
	
	private String cellPhone;//申请人手机号
	
	private String homePhone;//家庭电话
	
	private String homeAdd;//家庭地址
	
	private String corpName;//公司名称
	
	private String empPhone;//公司电话
	
	private String empAdd;//公司地址
	
	private String actionType;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBlacklistSrc() {
		return blacklistSrc;
	}

	public void setBlacklistSrc(String blacklistSrc) {
		this.blacklistSrc = blacklistSrc;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
}
