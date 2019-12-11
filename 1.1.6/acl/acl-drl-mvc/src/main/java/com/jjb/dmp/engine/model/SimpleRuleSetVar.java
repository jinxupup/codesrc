package com.jjb.dmp.engine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 简单规则集
 * @author BIG.D.W.K
 *
 */
public class SimpleRuleSetVar extends AbstractRuleSetVar implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<SimpleRuleVar> rules = new ArrayList<SimpleRuleVar>();

	public List<SimpleRuleVar> getRules() {
		return rules;
	}

	public void setRules(List<SimpleRuleVar> rules) {
		this.rules = rules;
	}

}
