package com.jjb.dmp.engine.bean;

import java.io.Serializable;

public class RuleQueryBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer stId;
    private String stName;
    private String stRemark; //remark
    private String stClass;
    private String stKey;
    private String stIfUsed; //ifUsed
 
    private Integer rsId;
    private String ruleSetName;
    private String ruleSetType;
    private String ruleSetEnabled;
    private String isExclusive;
    private String rsRemark; //remark

    private Integer ruleId;
    private String ruleName;
    private String ruleEnabled;
    private String ruleType;
    private Integer priority;
    private String remark;
    private String ruleObject;
    
	public Integer getStId() {
		return stId;
	}
	public void setStId(Integer stId) {
		this.stId = stId;
	}
	public String getStName() {
		return stName;
	}
	public void setStName(String stName) {
		this.stName = stName;
	}
	public String getStRemark() {
		return stRemark;
	}
	public void setStRemark(String stRemark) {
		this.stRemark = stRemark;
	}
	public String getStClass() {
		return stClass;
	}
	public void setStClass(String stClass) {
		this.stClass = stClass;
	}
	public String getStKey() {
		return stKey;
	}
	public void setStKey(String stKey) {
		this.stKey = stKey;
	}
	public String getStIfUsed() {
		return stIfUsed;
	}
	public void setStIfUsed(String stIfUsed) {
		this.stIfUsed = stIfUsed;
	}
	public Integer getRsId() {
		return rsId;
	}
	public void setRsId(Integer rsId) {
		this.rsId = rsId;
	}
	public String getRuleSetName() {
		return ruleSetName;
	}
	public void setRuleSetName(String ruleSetName) {
		this.ruleSetName = ruleSetName;
	}
	public String getRuleSetType() {
		return ruleSetType;
	}
	public void setRuleSetType(String ruleSetType) {
		this.ruleSetType = ruleSetType;
	}
	public String getRuleSetEnabled() {
		return ruleSetEnabled;
	}
	public void setRuleSetEnabled(String ruleSetEnabled) {
		this.ruleSetEnabled = ruleSetEnabled;
	}
	public String getIsExclusive() {
		return isExclusive;
	}
	public void setIsExclusive(String isExclusive) {
		this.isExclusive = isExclusive;
	}
	public String getRsRemark() {
		return rsRemark;
	}
	public void setRsRemark(String rsRemark) {
		this.rsRemark = rsRemark;
	}
	public Integer getRuleId() {
		return ruleId;
	}
	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getRuleEnabled() {
		return ruleEnabled;
	}
	public void setRuleEnabled(String ruleEnabled) {
		this.ruleEnabled = ruleEnabled;
	}
	public String getRuleType() {
		return ruleType;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRuleObject() {
		return ruleObject;
	}
	public void setRuleObject(String ruleObject) {
		this.ruleObject = ruleObject;
	}
    
}    