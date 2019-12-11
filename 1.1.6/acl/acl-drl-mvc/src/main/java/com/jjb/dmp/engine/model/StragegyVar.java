package com.jjb.dmp.engine.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StragegyVar implements Serializable {

	private static final long serialVersionUID = 1L;

	//策略类别
	private String stClass;
	//名称
	private String name;
	//描述
	private String desc;
	//是否启用
	private String active;
	//自定义函数
	private Map<String, FunctionVar> functionVars = new HashMap<String, FunctionVar>();

	private FilterCriteriaVar exclude = new FilterCriteriaVar();

	//规则集
	private List<AbstractRuleSetVar> ruleSets;

	//输入变量列表
	private Map<String,FieldVar> inputFieldVars;
	//输出变量列表
	private Map<String,FieldVar> outputFieldVars;
	
	
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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public FilterCriteriaVar getExclude() {
		return exclude;
	}

	public void setExclude(FilterCriteriaVar exclude) {
		this.exclude = exclude;
	}

	public List<AbstractRuleSetVar> getRuleSets() {
		return ruleSets;
	}

	public void setRuleSets(List<AbstractRuleSetVar> ruleSets) {
		this.ruleSets = ruleSets;
	}

	public Map<String, FunctionVar> getFunctionVars() {
		return functionVars;
	}

	public void setFunctionVars(Map<String, FunctionVar> functionVars) {
		this.functionVars = functionVars;
	}

	public Map<String, FieldVar> getInputFieldVars() {
		return inputFieldVars;
	}

	public void setInputFieldVars(Map<String, FieldVar> inputFieldVars) {
		this.inputFieldVars = inputFieldVars;
	}

	public Map<String, FieldVar> getOutputFieldVars() {
		return outputFieldVars;
	}

	public void setOutputFieldVars(Map<String, FieldVar> outputFieldVars) {
		this.outputFieldVars = outputFieldVars;
	}

	public String getStClass() {
		return stClass;
	}

	public void setStClass(String stClass) {
		this.stClass = stClass;
	}
}	