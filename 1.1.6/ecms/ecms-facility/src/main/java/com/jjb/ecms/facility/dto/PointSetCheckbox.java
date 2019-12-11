package com.jjb.ecms.facility.dto;

import java.io.Serializable;

public class PointSetCheckbox implements Serializable {
	private static final long serialVersionUID = 3161490759698117239L;
	
	private boolean checked = false;//默认不选中
	private String code;
	private String desc;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	/**
	 * @return the checked
	 */
	public boolean isChecked() {
		return checked;
	}
	/**
	 * @param checked the checked to set
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
