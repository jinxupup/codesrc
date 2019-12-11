package com.jjb.dmp.engine.model;

import java.io.Serializable;

public class ConditionColumnVar implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String uuid;
	
	private String name;
	
	private String title; //页面表格头显示的title
	
	private FilterCriteriaVar criteria;
	
	private String paramType;
	
	public ConditionColumnVar()
	{
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FilterCriteriaVar getCriteria() {
		return criteria;
	}

	public void setCriteria(FilterCriteriaVar criteria) {
		this.criteria = criteria;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}	