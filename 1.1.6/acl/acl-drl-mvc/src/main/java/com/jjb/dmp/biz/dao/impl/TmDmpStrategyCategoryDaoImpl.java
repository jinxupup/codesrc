package com.jjb.dmp.biz.dao.impl;

import org.springframework.stereotype.Repository;

import com.jjb.dmp.biz.dao.TmDmpStrategyCategoryDao;
import com.jjb.dmp.infrastructure.TmDmpStrategyCategory;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;

@Repository("tmDmpStrategyCategoryDao")
public class TmDmpStrategyCategoryDaoImpl extends AbstractBaseDao<TmDmpStrategyCategory> implements TmDmpStrategyCategoryDao{

	@Override
	public Page<TmDmpStrategyCategory> queryPage(Page<TmDmpStrategyCategory> page) {
		
		TmDmpStrategyCategory tmDmpStrategyCategory = new TmDmpStrategyCategory();
		Page<TmDmpStrategyCategory> p = queryForPageList(tmDmpStrategyCategory, page.getQuery(),page);
		
		return p;
	}

	@Override
	public TmDmpStrategyCategory getByClass(String stClass) {
	
		TmDmpStrategyCategory tmDmpStrategyCategory = new TmDmpStrategyCategory();
		tmDmpStrategyCategory.setStClass(stClass);
		
		return queryByKey(tmDmpStrategyCategory);
	}
	
}
