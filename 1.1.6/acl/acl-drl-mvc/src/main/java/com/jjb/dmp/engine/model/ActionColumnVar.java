package com.jjb.dmp.engine.model;

import java.io.Serializable;

/**
 * 
 * @author BIG.D.W.K
 *
 */
public class ActionColumnVar implements Serializable {

	private static final long serialVersionUID = 1L;

	private String uuid;
	private String name;
	
	private String title; //页面表格头显示的title
	
	private RuleActionVar action;
	private boolean stringParam;
	
	public ActionColumnVar(){}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isStringParam() {
		return stringParam;
	}

	public void setStringParam(boolean stringParam) {
		this.stringParam = stringParam;
	}

	public RuleActionVar getAction() {
		return action;
	}

	public void setAction(RuleActionVar action) {
		this.action = action;
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

