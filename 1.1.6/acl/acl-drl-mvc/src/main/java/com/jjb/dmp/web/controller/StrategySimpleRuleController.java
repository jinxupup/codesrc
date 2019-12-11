package com.jjb.dmp.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jjb.dmp.biz.service.DrlRuleService;
import com.jjb.dmp.biz.service.RulesetService;
import com.jjb.dmp.biz.service.StrategyService;
import com.jjb.dmp.engine.model.FieldVar;
import com.jjb.dmp.engine.model.FilterCriteriaVar;
import com.jjb.dmp.engine.model.RuleActionVar;
import com.jjb.dmp.engine.model.SimpleRuleSetVar;
import com.jjb.dmp.engine.model.SimpleRuleVar;
import com.jjb.dmp.infrastructure.TmDmpRule;
import com.jjb.dmp.infrastructure.TmDmpRuleset;
import com.jjb.dmp.infrastructure.TmDmpStrategy;
import com.jjb.unicorn.facility.kits.StrKit;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * 简单规则
 * @author BIG.D.W.K
 *
 */
@Controller
@RequestMapping("dmp/strategySimpleRuleController")
public class StrategySimpleRuleController extends BaseController{

	@Autowired
	private DrlRuleService drlRuleService;
	
	@Autowired
	private RulesetService rulesetService;
	
	@Autowired
	private StrategyService strategyService;
	
	@RequestMapping("/page")
	public String page(int rsId){
		setAttr("rsId", rsId);
		return "dmp/strategy/rule/simple/simple-rule-list.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/list")
	public Page<TmDmpRule> list(String rsId){
		
		Page<TmDmpRule> page = new Page<TmDmpRule>();
		List<TmDmpRule> rows = drlRuleService.queryByRsId(rsId);
		page.setRows(rows);
		
		return page;
	}
	
	@RequestMapping("/addpage")
	public String addpage(String rsId){
		
		TmDmpRuleset tmDmpRuleset = rulesetService.getTmDmpRuleset(rsId);
		
		TmDmpRule tmDmpRule = new TmDmpRule();
		tmDmpRule.setStId(tmDmpRuleset.getStId());
		tmDmpRule.setRsId(tmDmpRuleset.getRsId());
		
		setAttr("tmDmpRuleset", tmDmpRuleset);
		setAttr("tmDmpRule", tmDmpRule);
		setStrategy(tmDmpRuleset.getStId());
		setSimpleRuleVarJson(tmDmpRuleset,tmDmpRule);
		return "dmp/strategy/ruleset/simple/simple-rule-form.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public Json add(){
		
		Json j = Json.newSuccess();
		
		TmDmpRule tmDmpRule = getBean(TmDmpRule.class);
		
		try{
			drlRuleService.saveSimpleRule(tmDmpRule,genSimpleRuleVar(tmDmpRule));
			j.setObj(tmDmpRule);
		}catch(Exception e){
			e.printStackTrace();
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	
	@RequestMapping("/editpage")
	public String editpage(String ruleId){
		TmDmpRule tmDmpRule = drlRuleService.getTmDmpRule(ruleId);
		
		TmDmpRuleset tmDmpRuleset = rulesetService.getTmDmpRuleset(tmDmpRule.getRsId());
		
		setAttr("tmDmpRuleset", tmDmpRuleset);
		setAttr("tmDmpRule", tmDmpRule);
		
		setEdit();
		setStrategy(tmDmpRule.getStId());
		setSimpleRuleVarJson(tmDmpRuleset,tmDmpRule);
		
		return "dmp/strategy/ruleset/simple/simple-rule-form.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/edit")
	public Json edit(){
		Json j = Json.newSuccess();
		
		try{
			TmDmpRule tmDmpRule = getBean(TmDmpRule.class);
			drlRuleService.editSimpleRule(tmDmpRule, genSimpleRuleVar(tmDmpRule));
		}catch(Exception e){
			j.setFail(e.getMessage());
			e.printStackTrace();
		}
		
		return j;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Json delete(String ruleId){
		Json j = Json.newSuccess();
		try{
			drlRuleService.deleteTmDmpRule(ruleId);
		}catch(Exception e){
			j.setFail(e.getMessage());
			e.printStackTrace();
		}
		
		return j;
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
	
	/**
	 * 设置条件和动作信息
	 * @param tmDmpRuleset 
	 * @param tmDmpRule
	 */
	public void setSimpleRuleVarJson(TmDmpRuleset tmDmpRuleset, TmDmpRule tmDmpRule ){
		
		String filterCriteria = "{}";
		String actionList = "[]";
		
		if(tmDmpRule==null || tmDmpRule.getRuleId()==null){
			setAttr("filterCriteria", filterCriteria);
			setAttr("actionList", actionList);
			return ;
		}
		
		
		String ruleSetObject = tmDmpRuleset.getRuleSetObject();
		SimpleRuleSetVar simpleRuleSetVar = JSONObject.parseObject(ruleSetObject,SimpleRuleSetVar.class);
		if(simpleRuleSetVar!=null){
			List<SimpleRuleVar> rules =simpleRuleSetVar.getRules();
			
			if(rules!=null && rules.size()>0){
				for(SimpleRuleVar simpleRuleVar : rules){
					if(simpleRuleVar.getRuleId().equals(tmDmpRule.getRuleId())){
						if(StrKit.notNull(simpleRuleVar)&&StrKit.notNull(simpleRuleVar.getCriteria())){
							filterCriteria = JSONObject.toJSONString(simpleRuleVar.getCriteria());
						}
						if(StrKit.notNull(actionList)&&StrKit.notNull(simpleRuleVar.getActions())){
							actionList = JSONArray.toJSONString(simpleRuleVar.getActions());
						}
						break;
					}
				}
			}
		}
		
		setAttr("filterCriteria", filterCriteria);
		setAttr("actionList", actionList);
	}

	/**
	 * 产生简单规则的条件和动作信息
	 * @return
	 */
	public SimpleRuleVar genSimpleRuleVar(TmDmpRule tmDmpRule ){
		
		SimpleRuleVar simpleRuleVar = new SimpleRuleVar();
		
		String filterCriteria = getPara("filterCriteria");
		FilterCriteriaVar criteria = JSON.parseObject(filterCriteria,FilterCriteriaVar.class);
		String actionList = getPara("actionList");
		
		List<RuleActionVar> actions = JSONArray.parseArray(actionList, RuleActionVar.class);
		
		simpleRuleVar.setActions(actions);
		simpleRuleVar.setCriteria(criteria);
		simpleRuleVar.setName(tmDmpRule.getRuleName());
		simpleRuleVar.setPriority(tmDmpRule.getPriority());
		//simpleRuleVar.setDesc(tmDmpRule.getRemark());
		simpleRuleVar.setEnabled(tmDmpRule.getRuleEnabled());
		
		return simpleRuleVar;
	}
	
}	
