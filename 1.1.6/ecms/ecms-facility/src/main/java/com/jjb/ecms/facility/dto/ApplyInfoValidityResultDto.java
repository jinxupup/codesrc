package com.jjb.ecms.facility.dto;

import java.io.Serializable;

/**
 * @description: 申请资料校验结果模型DTO
 * @author -BigZ.Y
 * @date 2016年9月21日 上午9:58:11 
 */
public class ApplyInfoValidityResultDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String appType;//主附卡

	private String memo;//问题原因

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
	
}
