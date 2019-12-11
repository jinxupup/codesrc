package com.jjb.dmp.biz.dao;

import java.util.List;

import com.jjb.dmp.infrastructure.TmDmpStrategyFunction;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

public interface TmDmpStrategyFunctionDao extends BaseDao<TmDmpStrategyFunction>{

	Page<TmDmpStrategyFunction> queryPage(Page<TmDmpStrategyFunction> page);
	
	TmDmpStrategyFunction getByKey(String stClass, String funCd);

	List<TmDmpStrategyFunction> queryByStClass(String stClass);

}
