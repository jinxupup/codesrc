package com.jjb.dmp.biz.dao;

import java.util.List;

import com.jjb.dmp.infrastructure.TmDmpStrategyVar;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

public interface TmDmpStrategyVarDao extends BaseDao<TmDmpStrategyVar>{

	Page<TmDmpStrategyVar> queryPage(Page<TmDmpStrategyVar> page);
	
	TmDmpStrategyVar getByKey(String stClass,String varType, String varCd);

	List<TmDmpStrategyVar> queryByStClass(String stClass);

}
