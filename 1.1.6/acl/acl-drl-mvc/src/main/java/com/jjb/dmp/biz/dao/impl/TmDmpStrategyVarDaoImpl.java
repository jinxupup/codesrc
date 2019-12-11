package com.jjb.dmp.biz.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jjb.dmp.biz.dao.TmDmpStrategyVarDao;
import com.jjb.dmp.infrastructure.TmDmpStrategyVar;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;

@Repository("tmDmpStrategyVarDao")
public class TmDmpStrategyVarDaoImpl extends AbstractBaseDao<TmDmpStrategyVar> implements TmDmpStrategyVarDao {

	@Override
	public Page<TmDmpStrategyVar> queryPage(Page<TmDmpStrategyVar> page) {
		
		TmDmpStrategyVar tmDmpFieldVar = new TmDmpStrategyVar();
		Page<TmDmpStrategyVar> p = queryForPageList(tmDmpFieldVar, page.getQuery(),page);
		
		return p;
	}

	@Override
	public TmDmpStrategyVar getByKey(String stClass,String varType,String varCd) {
	
		TmDmpStrategyVar tmDmpFieldVar = new TmDmpStrategyVar();
		tmDmpFieldVar.setStClass(stClass);
		tmDmpFieldVar.setVarCd(varCd);
		tmDmpFieldVar.setVarType(varType);
		
		return queryByKey(tmDmpFieldVar);
	}

	@Override
	public List<TmDmpStrategyVar> queryByStClass(String stClass) {
		TmDmpStrategyVar tmDmpFieldVar = new TmDmpStrategyVar();
		tmDmpFieldVar.setStClass(stClass);
		return queryForList(tmDmpFieldVar);
	}
	
}