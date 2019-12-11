package com.jjb.dmp.biz.service;

import java.util.List;

import com.jjb.dmp.infrastructure.TmDmpStrategyFunction;
import com.jjb.unicorn.facility.model.Page;


/**
 * 
 * @author BIG.D.W.K
 *
 */

public interface StrategyFunctionService {
	Page<TmDmpStrategyFunction> getPage(Page<TmDmpStrategyFunction> page);

	TmDmpStrategyFunction getTmDmpStrategyFunction(String stClass, String funCd);
	
	void saveTmDmpStrategyFunction(TmDmpStrategyFunction tmDmpStrategyFunction);
	
	void editTmDmpStrategyFunction(TmDmpStrategyFunction tmDmpStrategyFunction);
	
	List<TmDmpStrategyFunction> getFunctionList(String stClass);

	void deleteTmDmpStrategyFunction(String stClass, String funCd);

	
}
