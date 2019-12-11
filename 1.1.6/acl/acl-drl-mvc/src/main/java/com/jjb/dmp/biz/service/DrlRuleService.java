package com.jjb.dmp.biz.service;

import java.util.List;

import com.jjb.dmp.engine.model.DecisionRowVar;
import com.jjb.dmp.engine.model.SimpleRuleVar;
import com.jjb.dmp.infrastructure.TmDmpRule;
import com.jjb.dmp.infrastructure.TmDmpRuleset;


/**
 * 
 * @author BIG.D.W.K
 *
 */

public interface DrlRuleService {
	
	List<TmDmpRule> queryByRsId(String rsId);
	
	TmDmpRule getTmDmpRule(String ruleId);
	
	void saveTmDmpRule(TmDmpRule tmDmpRule);
	
	void editTmDmpRule(TmDmpRule tmDmpRule);
	
	void deleteTmDmpRule(String ruleId);

	void editSimpleRule(TmDmpRule tmDmpRule, SimpleRuleVar simpleRuleVar);


	void saveSimpleRule(TmDmpRule tmDmpRule,SimpleRuleVar simpleRuleVar);

	List<DecisionRowVar> saveDecisionTableRows(TmDmpRuleset tmDmpRuleset, List<DecisionRowVar> rows);
}
