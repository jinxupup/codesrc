package com.jjb.ecms.facility.dto;

import java.io.Serializable;

/**
 * @description 补件信息DTO
 * @author hn
 * @date 2016年9月23日10:21:23
 */
public class ApplyPatchBoltDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String applyPatchBoltType;//补件类型

	private String applyPatchBoltTitle;//类型名称

	private boolean ifPathBolt;//是否补件

	public String getApplyPatchBoltType() {
		return applyPatchBoltType;
	}

	public void setApplyPatchBoltType(String applyPatchBoltType) {
		this.applyPatchBoltType = applyPatchBoltType;
	}

	public String getApplyPatchBoltTitle() {
		return applyPatchBoltTitle;
	}

	public void setApplyPatchBoltTitle(String applyPatchBoltTitle) {
		this.applyPatchBoltTitle = applyPatchBoltTitle;
	}

	public boolean isIfPathBolt() {
		return ifPathBolt;
	}

	public void setIfPathBolt(boolean ifPathBolt) {
		this.ifPathBolt = ifPathBolt;
	}


}
