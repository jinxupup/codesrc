package com.jjb.dmp.biz.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jjb.dmp.biz.dao.TmDmpStrategyFunctionDao;
import com.jjb.dmp.infrastructure.TmDmpStrategyFunction;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;

@Repository("tmDmpStrategyFunctionDao")
public class TmDmpStrategyFunctionDaoImpl extends AbstractBaseDao<TmDmpStrategyFunction> implements TmDmpStrategyFunctionDao{

	@Override
	public Page<TmDmpStrategyFunction> queryPage(Page<TmDmpStrategyFunction> page) {
		
		TmDmpStrategyFunction tmDmpFieldVar = new TmDmpStrategyFunction();
		Page<TmDmpStrategyFunction> p = queryForPageList(tmDmpFieldVar, page.getQuery(),page);
		
		return p;
	}

	@Override
	public TmDmpStrategyFunction getByKey(String stClass,String funCd) {
	
		TmDmpStrategyFunction tmDmpFieldVar = new TmDmpStrategyFunction();
		tmDmpFieldVar.setStClass(stClass);
		tmDmpFieldVar.setFunCd(funCd);
		
		return queryByKey(tmDmpFieldVar);
	}

	@Override
	public List<TmDmpStrategyFunction> queryByStClass(String stClass) {
		TmDmpStrategyFunction tmDmpFieldVar = new TmDmpStrategyFunction();
		tmDmpFieldVar.setStClass(stClass);
		return queryForList(tmDmpFieldVar);
	}
	
}