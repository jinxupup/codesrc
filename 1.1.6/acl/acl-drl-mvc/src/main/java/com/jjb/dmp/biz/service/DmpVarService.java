package com.jjb.dmp.biz.service;

import java.util.List;

import com.jjb.dmp.infrastructure.TmDmpVar;
import com.jjb.unicorn.facility.model.Page;


/**
 * 
 * @author BIG.D.W.K
 *
 */

public interface DmpVarService {
	Page<TmDmpVar> getPage(Page<TmDmpVar> page);

	TmDmpVar getTmDmpVar(String varType, String varCd);
	
	void saveTmDmpVar(TmDmpVar tmDmpVar);
	
	void editTmDmpVar(TmDmpVar tmDmpVar);
	
	List<TmDmpVar> getVarList(String varType);

	void deleteTmDmpVar(String varType, String varCd);

	List<TmDmpVar> getVarList(TmDmpVar tmDmpVar);

	
}
