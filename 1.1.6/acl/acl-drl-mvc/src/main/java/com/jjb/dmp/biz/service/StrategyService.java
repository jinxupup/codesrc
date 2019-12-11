package com.jjb.dmp.biz.service;

import java.util.List;

import com.jjb.dmp.engine.model.FieldVar;
import com.jjb.dmp.engine.model.StragegyVar;
import com.jjb.dmp.infrastructure.TmDmpStrategy;
import com.jjb.unicorn.facility.model.Page;


/**
 * 
 * @author BIG.D.W.K
 *
 */

public interface StrategyService {
	Page<TmDmpStrategy> getPage(Page<TmDmpStrategy> page);

	TmDmpStrategy getTmDmpStrategy(String stId);
	
	void saveTmDmpStrategy(TmDmpStrategy tmDmpStrategy);
	
	void editTmDmpStrategy(TmDmpStrategy tmDmpStrategy);
	
	void deleteTmDmpStrategy(String stId);
	
	/**
	 * 产生决策变量
	 * @param tmDmpStrategy
	 * @param inputFieldVars
	 * @param outputFieldVars
	 */
	void exchangeFieldVar(TmDmpStrategy tmDmpStrategy, List<FieldVar> inputFieldVars, List<FieldVar> outputFieldVars);

	List<FieldVar> exchangeFunctionVar(TmDmpStrategy tmDmpStrategy);

	/**
	 * 产生策略对象
	 * @param stId
	 * @return
	 */
	StragegyVar genStragegyVar(String stId);

	/**
	 * 更新drl字段
	 * @param stId
	 */
	void updateStrategyDrl(String stId);

	/**
	 * 根据策略类型获取默认策略
	 * 
	 * @param stClass
	 * @return
	 */
	TmDmpStrategy getDefaultStrategy(String stClass);

}
