package com.jjb.dmp.biz.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.dmp.biz.engine.service.StrategyExecute;
import com.jjb.dmp.biz.service.StrategyCategoryService;
import com.jjb.dmp.biz.service.StrategyService;
import com.jjb.dmp.biz.service.StrategyTrialService;
import com.jjb.dmp.engine.kit.StragegyVarInstance;
import com.jjb.dmp.engine.model.FieldVar;
import com.jjb.dmp.engine.model.RuleRuntimeInfo;
import com.jjb.dmp.engine.model.TrailVar;
import com.jjb.dmp.infrastructure.TmDmpStrategy;
import com.jjb.unicorn.facility.exception.BizException;

@Transactional
@Service("strategyTrialService")
public class StrategyTrialServiceImpl implements StrategyTrialService {

	@Autowired
    StrategyService strategyService;
	@Autowired
    StrategyExecute strategyExecute;
	@Autowired
	private StrategyCategoryService strategyCategoryService;
	
	@Override
	public TrailVar trail(String stId, Map<String,Object> fact){
		
		TrailVar trailVar = new TrailVar();
		
		TmDmpStrategy tmDmpStrategy = strategyService.getTmDmpStrategy(stId);
		if(tmDmpStrategy==null){
			throw new BizException("找不到该策略");
		}
		
		trailVar.setDrl(tmDmpStrategy.getStObject());
		trailVar.setFact(fact);
		trailVar.setInput(fact);
		
		try{
//			strategyExecute.executeStId(stId, fact);
			strategyExecute.executeStClass(tmDmpStrategy.getStClass(),fact);
		
			Map<String, FieldVar> inputFields = strategyCategoryService.getInputFieldVars(tmDmpStrategy.getStClass());
			Map<String, FieldVar> outputFields = strategyCategoryService.getOutputFieldVars(tmDmpStrategy.getStClass());
			
			Map<String,Object> out = new HashMap<String, Object>();
			for(Map.Entry<String, FieldVar> of:outputFields.entrySet()){
				Object ovalue = trailVar.getFact().get(of.getKey());
				out.put(of.getKey(), ovalue);
			}
			
			List<RuleRuntimeInfo> ruleRuntimeInfos = (List<RuleRuntimeInfo>) fact.get(StragegyVarInstance.RULES_PROP);
			trailVar.setRuleRuntimeInfos(ruleRuntimeInfos);
			trailVar.setOut(out);

		}catch(Exception e){
			e.printStackTrace();
			trailVar.setSuccess(false);
			trailVar.setErrMsg(e.getMessage());
		}
		return trailVar;
	}
	
}
