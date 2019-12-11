package com.jjb.ecms.facility.dto;

import java.io.Serializable;

/**
 * @description: 人工核查结果模型
 * @author -BigZ.Y
 * @date 2016年9月18日19:20:57
 */
public class PersonCheckResultDto implements Serializable{

	
	private static final long serialVersionUID = 1L;

	private String checkTypeItem;//查证种类
	
	private String checkResultItem;//查证结果

	public String getCheckTypeItem() {
		return checkTypeItem;
	}

	public void setCheckTypeItem(String checkTypeItem) {
		this.checkTypeItem = checkTypeItem;
	}

	public String getCheckResultItem() {
		return checkResultItem;
	}

	public void setCheckResultItem(String checkResultItem) {
		this.checkResultItem = checkResultItem;
	}
	
	
}
