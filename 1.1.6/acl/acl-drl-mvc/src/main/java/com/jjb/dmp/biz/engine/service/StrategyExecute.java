package com.jjb.dmp.biz.engine.service;

import java.util.Map;

import com.jjb.dmp.infrastructure.TmDmpStrategy;

public interface StrategyExecute {

	void executeStClass(String stClass, Map<String, Object> fact);

	void executeStId(String stId, Map<String, Object> fact);

	void executeStrategy(TmDmpStrategy tmStrategy, Map<String, Object> fact);
	
}
