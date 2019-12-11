package com.jjb.dmp.biz.service;

import java.util.Map;

import com.jjb.dmp.engine.model.TrailVar;

public interface StrategyTrialService {

	/**
	 * 试算规则
	 * @param stId
	 * @param inputs
	 */
	TrailVar trail(String stId, Map<String, Object> input);
	
}
