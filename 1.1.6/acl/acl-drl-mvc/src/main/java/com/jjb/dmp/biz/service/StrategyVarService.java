package com.jjb.dmp.biz.service;

import java.util.List;

import com.jjb.dmp.infrastructure.TmDmpStrategyVar;
import com.jjb.unicorn.facility.model.Page;


/**
 * 
 * @author BIG.D.W.K
 *
 */

public interface StrategyVarService {
	Page<TmDmpStrategyVar> getPage(Page<TmDmpStrategyVar> page);

	TmDmpStrategyVar getTmDmpStrategyVar(String stClass, String varType, String varCd);
	
	void saveTmDmpStrategyVar(TmDmpStrategyVar tmDmpStrategyVar);
	
	void editTmDmpStrategyVar(TmDmpStrategyVar tmDmpStrategyVar);
	
	List<TmDmpStrategyVar> getVarList(String stClass);

	void deleteTmDmpStrategyVar(TmDmpStrategyVar tmDmpStrategyVar);

	/**
	 * 新增变量
	 * @param stClass
	 * @param varList
	 */
	void addTmDmpStrategyVars(String stClass, List<TmDmpStrategyVar> varList);


	
}
