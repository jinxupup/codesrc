package com.jjb.dmp.biz.dao;

import com.jjb.dmp.infrastructure.TmDmpStrategy;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

public interface TmDmpStrategyDao extends BaseDao<TmDmpStrategy>{

	Page<TmDmpStrategy> queryPage(Page<TmDmpStrategy> page);
	
	TmDmpStrategy getByKey(String stId);
}