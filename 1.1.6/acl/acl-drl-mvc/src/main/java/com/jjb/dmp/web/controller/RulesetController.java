package com.jjb.dmp.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jjb.dmp.biz.service.RulesetService;
import com.jjb.dmp.biz.service.StrategyService;
import com.jjb.dmp.engine.model.DecisionTableVar;
import com.jjb.dmp.engine.model.FieldVar;
import com.jjb.dmp.engine.model.enums.RuleSetType;
import com.jjb.dmp.infrastructure.TmDmpRuleset;
import com.jjb.dmp.infrastructure.TmDmpStrategy;
import com.jjb.unicorn.facility.kits.StrKit;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * 规则集
 * @author BIG.D.W.K
 *
 */
@Controller
@RequestMapping("dmp/ruleset")
public class RulesetController extends BaseController{

	@Autowired
	private RulesetService rulesetService;
	
	@Autowired
	private StrategyService strategyService;
	
	@RequestMapping("/page")
	public String page(int stId){
		setAttr("stId", stId);
		return "dmp/strategy/rule/rule.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/list")
	public Page<TmDmpRuleset> list(String stId){
		
		Page<TmDmpRuleset> page = new Page<TmDmpRuleset>();
		List<TmDmpRuleset> rows = rulesetService.queryByStId(stId);
		page.setRows(rows);
		
		return page;
	}
	
	@RequestMapping("/addpage")
	public String addpage(String stId,String ruleSetType){
		
		String page = "";
		if(RuleSetType.S.name().equals(ruleSetType)){
			page = "dmp/strategy/ruleset/ruleset-simple-add.ftl";
		}else if(RuleSetType.D.name().equals(ruleSetType)){
			page = "dmp/strategy/ruleset/ruleset-decision-add.ftl";
		}else{
			return "";
		}
		
		TmDmpRuleset tmDmpRuleset = new TmDmpRuleset();
		tmDmpRuleset.setStId(stId);
		tmDmpRuleset.setRuleSetType(ruleSetType);
		setAttr("tmDmpRuleset", tmDmpRuleset);
		return page;
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public Json add(){
		Json j = Json.newSuccess();
		
		TmDmpRuleset tmDmpRuleset = getBean(TmDmpRuleset.class);
		try{
			rulesetService.saveTmDmpRuleset(tmDmpRuleset);
			j.setObj(tmDmpRuleset);
		}catch(Exception e){
			e.printStackTrace();
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	
	@RequestMapping("/editpage")
	public String editpage(String rsId){
		TmDmpRuleset tmDmpRuleset = rulesetService.getTmDmpRuleset(rsId);
		setAttr("tmDmpRuleset", tmDmpRuleset);
		setEdit();
		
		String page = "";
		if(RuleSetType.S.name().equals(tmDmpRuleset.getRuleSetType())){
			page = "dmp/strategy/ruleset/ruleset-simple-edit.ftl";
		}else if(RuleSetType.D.name().equals(tmDmpRuleset.getRuleSetType())){
			
			setStrategy(tmDmpRuleset.getStId());
			setTableDecisionVarJson(tmDmpRuleset);
			
			page = "dmp/strategy/ruleset/ruleset-decision-edit.ftl";
		}else{
			return "";
		}
		
		return page;
	}
	
	@ResponseBody
	@RequestMapping("/edit")
	public Json edit(){
		Json j = Json.newSuccess();
		TmDmpRuleset tmDmpRuleset = getBean(TmDmpRuleset.class);
		try{
			rulesetService.editTmDmpRuleset(tmDmpRuleset);
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Json delete(String rsId){
		Json j = Json.newSuccess();
		try{
			rulesetService.deleteTmDmpRuleset(rsId);
		}catch(Exception e){
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
		String decisionTableVar = "\"{}\""; 
		
		String ruleSetObject = tmDmpRuleset.getRuleSetObject();
		if(StrKit.notBlank(ruleSetObject)){
			
			DecisionTableVar decisionTableVarObj = JSONObject.parseObject(ruleSetObject,DecisionTableVar.class);
			if(StrKit.notNull(decisionTableVarObj)&&StrKit.notNull(decisionTableVarObj.getConditions())){
				actionsdata = JSONArray.toJSONString(decisionTableVarObj.getActions());
			}
			if(StrKit.notNull(conditionsdata)&&StrKit.notNull(decisionTableVarObj.getConditions())){
				conditionsdata = JSONArray.toJSONString(decisionTableVarObj.getConditions());
			}
			
			decisionTableVar = JSONObject.toJSONString(ruleSetObject);
		}
		
		setAttr("conditionsdata", conditionsdata);
		setAttr("actionsdata", actionsdata);
		setAttr("decisionTableVar", decisionTableVar);
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