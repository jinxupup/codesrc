package com.jjb.dmp.biz.service;

import java.util.List;

import com.jjb.dmp.engine.model.ActionColumnVar;
import com.jjb.dmp.engine.model.ConditionColumnVar;
import com.jjb.dmp.engine.model.DecisionRowVar;
import com.jjb.dmp.engine.model.SimpleRuleVar;
import com.jjb.dmp.infrastructure.TmDmpRule;
import com.jjb.dmp.infrastructure.TmDmpRuleset;


/**
 * 
 * @author BIG.D.W.K
 *
 */

public interface RulesetService {
	
	List<TmDmpRuleset> queryByStId(String stId);
	
	TmDmpRuleset getTmDmpRuleset(String rsId);
	
	void saveTmDmpRuleset(TmDmpRuleset tmDmpRuleset);
	
	void editTmDmpRuleset(TmDmpRuleset tmDmpRuleset);
	
	void deleteTmDmpRuleset(String rsId);

	/**
	 * 
	 * @param tmDmpRule
	 * @param simpleRuleVar
	 * @param dbOperator 可选值 update insert delete
	 */
	void uniforSimpleRuleVar(TmDmpRule tmDmpRule,SimpleRuleVar simpleRuleVar, String dbOperator);

	/**
	 * 保存决策表行信息
	 * @param tmDmpRuleset
	 * @param genRowsVar
	 */
	void saveDecisionTableRows(TmDmpRuleset tmDmpRuleset,List<DecisionRowVar> genRowsVar);

	/**
	 * 保存决策表配置信息
	 * @param tmDmpRuleset
	 * @param conditions
	 * @param actions
	 */
	void saveDecisionTableConfig(TmDmpRuleset tmDmpRuleset,List<ConditionColumnVar> conditions, List<ActionColumnVar> actions);

}
