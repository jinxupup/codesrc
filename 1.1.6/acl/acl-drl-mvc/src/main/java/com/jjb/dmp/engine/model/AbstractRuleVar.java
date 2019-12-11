package com.jjb.dmp.engine.model;

import java.io.Serializable;

public abstract class AbstractRuleVar implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 数据库ruleId
	 */
	private String ruleId;
	
	private int priority;
	
	private String enabled = "Y";
	
	private String name;
	
	private String desc;

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	
}
