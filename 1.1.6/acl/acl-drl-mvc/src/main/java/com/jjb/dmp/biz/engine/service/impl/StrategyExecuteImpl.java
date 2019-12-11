package com.jjb.dmp.biz.engine.service.impl;

import java.util.List;
import java.util.Map;

import org.drools.KnowledgeBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.jjb.dmp.biz.engine.service.StrategyExecute;
import com.jjb.dmp.biz.service.StrategyCategoryService;
import com.jjb.dmp.biz.service.StrategyService;
import com.jjb.dmp.engine.kit.StrategyLoader;
import com.jjb.dmp.engine.model.RuleRuntimeInfo;
import com.jjb.dmp.infrastructure.TmDmpStrategy;
import com.jjb.unicorn.facility.exception.BizException;

@Service("strategyExecute")
public class StrategyExecuteImpl implements StrategyExecute {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private StrategyService strategyService;
	@Autowired
	private StrategyCategoryService strategyCategoryService;
	
	@Override
	public void executeStClass(String stClass, Map<String, Object> fact) {
		TmDmpStrategy tmDmpStrategy = strategyService.getDefaultStrategy(stClass);
		if(tmDmpStrategy==null){
			throw new BizException("找不到可执行的策略类型为"+stClass+"的策略方案");
		}
		
		executeStrategy(tmDmpStrategy,fact);
		
	}
	
	@Override
	public void executeStId(String stId,Map<String, Object> fact){
		TmDmpStrategy tmDmpStrategy = strategyService.getTmDmpStrategy(stId);
		if(tmDmpStrategy==null){
			throw new BizException("找不到该编号的策略");
		}
		
		executeStrategy(tmDmpStrategy,fact);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeStrategy(TmDmpStrategy tmStrategy,Map<String, Object> fact){
		//填充没设置的决策变量
		StrategyLoader.inputFactMerge(fact,strategyCategoryService.getFieldVar(tmStrategy.getStClass()));
		//加载知识库
		KnowledgeBase knowledgeBase = StrategyLoader.loadKnowledgeBase(tmStrategy.getStObject());
		//执行规则
		StrategyLoader.execute(knowledgeBase,fact);
		
		logger.info("事实数据");
		logger.info(JSON.toJSONString(fact));
		
		List<RuleRuntimeInfo> _rules = (List<RuleRuntimeInfo>) fact.get("_rules");
		if(_rules!=null&&_rules.size()>0){
			for(RuleRuntimeInfo ri:_rules){
				logger.debug("-------");
				logger.debug("规则集："+ri.getRulesetName() + "；规则： " + ri.getName() );
				logger.debug("输出"+JSON.toJSONString(ri));
				logger.debug("-------");
			}
		}
	}
}
