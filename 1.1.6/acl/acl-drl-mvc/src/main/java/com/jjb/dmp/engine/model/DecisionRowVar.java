package com.jjb.dmp.engine.model;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * @author BIG.D.W.K
 *
 */
public class DecisionRowVar extends AbstractRuleVar implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Map<String, Object> conditionData; //Map<String, Object> String是condition的uuid
	
	private Map<String, Object> actionData;	   //Map<String, Object> String是action的uuid

	public Map<String, Object> getConditionData() {
		return conditionData;
	}

	public void setConditionData(Map<String, Object> conditionData) {
		this.conditionData = conditionData;
	}

	public Map<String, Object> getActionData() {
		return actionData;
	}

	public void setActionData(Map<String, Object> actionData) {
		this.actionData = actionData;
	}
	
}
