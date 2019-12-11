package com.jjb.dmp.engine.model;

import java.io.Serializable;
import java.util.List;

/**
 * 简单规则对象
 * @author BIG.D.W.K
 *
 */
public class SimpleRuleVar extends AbstractRuleVar implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 条件
	 */
	private FilterCriteriaVar criteria;
	
	/**
	 * 动作列表
	 */
	private List<RuleActionVar> actions;

	public FilterCriteriaVar getCriteria() {
		return criteria;
	}

	public void setCriteria(FilterCriteriaVar criteria) {
		this.criteria = criteria;
	}

	public List<RuleActionVar> getActions() {
		return actions;
	}

	public void setActions(List<RuleActionVar> actions) {
		this.actions = actions;
	}
	
}
