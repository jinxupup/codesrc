package com.jjb.dmp.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jjb.dmp.biz.service.DrlRuleService;
import com.jjb.dmp.biz.service.RulesetService;
import com.jjb.dmp.biz.service.StrategyService;
import com.jjb.dmp.engine.model.ActionColumnVar;
import com.jjb.dmp.engine.model.ConditionColumnVar;
import com.jjb.dmp.engine.model.DecisionRowVar;
import com.jjb.dmp.engine.model.DecisionTableVar;
import com.jjb.dmp.engine.model.FieldVar;
import com.jjb.dmp.infrastructure.TmDmpRuleset;
import com.jjb.dmp.infrastructure.TmDmpStrategy;
import com.jjb.unicorn.facility.kits.StrKit;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * 决策表
 * @author BIG.D.W.K
 *
 */
@Controller
@RequestMapping("dmp/strategyDecisionTable")
public class StrategyDecisionTableController extends BaseController{

	@Autowired
	private DrlRuleService drlRuleService;
	
	@Autowired
	private RulesetService rulesetService;
	
	@Autowired
	private StrategyService strategyService;
	
	@RequestMapping("/page")
	public String page(String rsId){
		
		TmDmpRuleset tmDmpRuleset = rulesetService.getTmDmpRuleset(rsId);
		setAttr("tmDmpRuleset", tmDmpRuleset);
		setAttr("rsId", rsId);
		
		return "dmp/strategy/rule/decision/decision-table-list.ftl";
	}
	
	
	@RequestMapping("/editConditionPage")
	public String editConditionPage(String rsId){
		
		TmDmpRuleset tmDmpRuleset = rulesetService.getTmDmpRuleset(rsId);
		setAttr("tmDmpRuleset", tmDmpRuleset);
		
		
		setTableDecisionVarJson(tmDmpRuleset);
		setStrategy(tmDmpRuleset.getStId());
		
		return "dmp/strategy/ruleset/decision/decision-rule-condition.ftl";
	}
	
	/**
	 * 保存决策表
	 * @param rsId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editRows")
	public Json editRows(String rsId){
		Json j = Json.newSuccess();
		
		try{
			
			TmDmpRuleset tmDmpRuleset = rulesetService.getTmDmpRuleset(rsId);
			rulesetService.saveDecisionTableRows(tmDmpRuleset,genRowsVar());
			
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	
	/**
	 * 保存条件动作信息
	 * @param rsId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editCondition")
	public Json editCondition(String rsId){
		
		Json j = Json.newSuccess();
		
		try{
			TmDmpRuleset tmDmpRuleset = rulesetService.getTmDmpRuleset(rsId);
			DecisionTableVar decisionTableVar = genConditionAndActionVar();
			rulesetService.saveDecisionTableConfig(tmDmpRuleset,decisionTableVar.getConditions(),decisionTableVar.getActions());
		}catch(Exception e){
			e.printStackTrace();
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	
	/**
	 * 设置条件和动作信息
	 * @param tmDmpRule
	 */
	public void setTableDecisionVarJson(TmDmpRuleset tmDmpRuleset ){
		
		String conditionsdata = "[]";
		String actionsdata = "[]";
		
		String ruleSetObject = tmDmpRuleset.getRuleSetObject();
		if(StrKit.notBlank(ruleSetObject)){
			
			DecisionTableVar decisionTableVar = JSONObject.parseObject(ruleSetObject,DecisionTableVar.class);
			if(StrKit.notNull(decisionTableVar)&&StrKit.notNull(decisionTableVar.getConditions())){
				actionsdata = JSONObject.toJSONString(decisionTableVar.getActions());
			}
			if(StrKit.notNull(conditionsdata)&&StrKit.notNull(decisionTableVar.getConditions())){
				conditionsdata = JSONArray.toJSONString(decisionTableVar.getConditions());
			}
		}
		
		setAttr("conditionsdata", conditionsdata);
		setAttr("actionsdata", actionsdata);
	}
	
	/**
	 * 产生规则表的行信息
	 * @return
	 */
	public List<DecisionRowVar> genRowsVar(){
		String decisionTable = getPara("decisionTable");
		DecisionTableVar decisionTableVar = JSONObject.parseObject(decisionTable,DecisionTableVar.class);
		if(decisionTableVar==null){
			return new ArrayList<DecisionRowVar>();
		}
		return decisionTableVar.getRows();
	}
	
	/**
	 * 产生规则表的条件和动作信息
	 * @return
	 */
	public DecisionTableVar genConditionAndActionVar(){
		
		String decisionTable = getPara("decisionTable");
		DecisionTableVar decisionTableVar = JSONObject.parseObject(decisionTable,DecisionTableVar.class);
		for(ConditionColumnVar cv:decisionTableVar.getConditions()){
			if(StrKit.isBlank(cv.getUuid())){
				cv.setUuid(UUID.randomUUID().toString().replace("-", ""));
			}
		}
		for(ActionColumnVar ac:decisionTableVar.getActions()){
			if(StrKit.isBlank(ac.getUuid())){
				ac.setUuid(UUID.randomUUID().toString().replace("-", ""));
			}
		}
		
		return decisionTableVar;
	}
	
	/*设置策略基本信息*/
	public void setStrategy(String stId){
		TmDmpStrategy tmDmpStrategy = strategyService.getTmDmpStrategy(stId);
		
		setAttr("tmDmpStrategy", tmDmpStrategy);
		List<FieldVar> inputFieldVars = new ArrayList<FieldVar>();
		List<FieldVar> outputFieldVars = new ArrayList<FieldVar>();
		//决策变量
		strategyService.exchangeFieldVar(tmDmpStrategy,inputFieldVars,outputFieldVars);
		String inputVarList = JSONArray.toJSONString(inputFieldVars);
		String outputVarList = JSONArray.toJSONString(outputFieldVars);
		String functionFieldVars = JSONArray.toJSONString(strategyService.exchangeFunctionVar(tmDmpStrategy));
		setAttr("inputVarList", inputVarList);
		setAttr("outputVarList", outputVarList);
		setAttr("functionFieldVars", functionFieldVars);
	}
}	