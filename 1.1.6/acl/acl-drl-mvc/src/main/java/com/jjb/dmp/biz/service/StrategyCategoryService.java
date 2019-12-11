package com.jjb.dmp.biz.service;

import java.util.List;
import java.util.Map;

import com.jjb.dmp.engine.model.FieldVar;
import com.jjb.dmp.infrastructure.TmDmpStrategyCategory;
import com.jjb.dmp.infrastructure.TmDmpStrategyVar;
import com.jjb.dmp.infrastructure.TmDmpVar;
import com.jjb.unicorn.facility.model.Page;


/**
 * 
 * @author BIG.D.W.K
 *
 */

public interface StrategyCategoryService {
	Page<TmDmpStrategyCategory> getPage(Page<TmDmpStrategyCategory> page);

	TmDmpStrategyCategory getTmDmpStrategyCategory(String strategyCategoryCode);
	
	void saveTmDmpStrategyCategory(TmDmpStrategyCategory tmDmpStrategyCategory);
	
	void editTmDmpStrategyCategory(TmDmpStrategyCategory tmDmpStrategyCategory);
	
	void deleteTmDmpStrategyCategory(String strategyCategoryCode);
	
	Map<String, FieldVar> getInputFieldVars(String stClass);
	Map<String, FieldVar> getOutputFieldVars(String stClass);

//	List<FieldVar> generateFieldVar(List<TmDmpStrategyVar> strategyVars);
	List<FieldVar> generateFieldVar(List<TmDmpVar> vars);

	/**
	 * 获取策略类型可用的决策变量
	 * @param stClass
	 * @return
	 */
	List<TmDmpStrategyVar> getFieldVar(String stClass);


}
