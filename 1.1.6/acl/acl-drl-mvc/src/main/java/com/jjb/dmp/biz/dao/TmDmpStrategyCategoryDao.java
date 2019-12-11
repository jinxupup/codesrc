package com.jjb.dmp.biz.dao;

import com.jjb.dmp.infrastructure.TmDmpStrategyCategory;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

public interface TmDmpStrategyCategoryDao extends BaseDao<TmDmpStrategyCategory>{

	Page<TmDmpStrategyCategory> queryPage(Page<TmDmpStrategyCategory> page);
	
	TmDmpStrategyCategory getByClass(String stClass);
}