package com.jjb.dmp.engine.model;

import java.io.Serializable;

/**
 * 抽象规则集
 * @author BIG.D.W.K
 *
 */
public abstract class AbstractRuleSetVar implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	private String desc;
	
	private String enabled = "Y";
	
	/**
	 * 排它
	 */
	private String exclusive = "N";
	
	private FilterCriteriaVar exclude;
	
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

	public FilterCriteriaVar getExclude() {
		return exclude;
	}

	public void setExclude(FilterCriteriaVar exclude) {
		this.exclude = exclude;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getExclusive() {
		return exclusive;
	}

	public void setExclusive(String exclusive) {
		this.exclusive = exclusive;
	}

}
