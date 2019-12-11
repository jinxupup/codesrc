package com.jjb.dmp.engine.model;

import java.io.Serializable;
import java.util.List;

/**
 * 决策表
 * @author BIG.D.W.K
 *
 */
public class DecisionTableVar extends AbstractRuleSetVar implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 条件表达式
	 */
	private List<ConditionColumnVar> conditions;
	
	/**
	 * 行动表达式
	 */
	private List<ActionColumnVar> actions;
	
	/**
	 * 
	 */
	private List<DecisionRowVar> rows;

	public List<ConditionColumnVar> getConditions() {
		return conditions;
	}

	public void setConditions(List<ConditionColumnVar> conditions) {
		this.conditions = conditions;
	}

	public List<ActionColumnVar> getActions() {
		return actions;
	}

	public void setActions(List<ActionColumnVar> actions) {
		this.actions = actions;
	}

	public List<DecisionRowVar> getRows() {
		return rows;
	}

	public void setRows(List<DecisionRowVar> rows) {
		this.rows = rows;
	}
	
}
