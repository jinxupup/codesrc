package com.jjb.dmp.biz.dao.impl;

import org.springframework.stereotype.Repository;

import com.jjb.dmp.biz.dao.TmDmpStrategyDao;
import com.jjb.dmp.infrastructure.TmDmpStrategy;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;

@Repository("tmDmpStrategyDao")
public class TmDmpStrategyDaoImpl extends AbstractBaseDao<TmDmpStrategy> implements TmDmpStrategyDao {

	@Override
	public Page<TmDmpStrategy> queryPage(Page<TmDmpStrategy> page) {
		
		TmDmpStrategy tmDmpStrategy = new TmDmpStrategy();
		Page<TmDmpStrategy> p = queryForPageList(tmDmpStrategy, page.getQuery(),page);
		
		return p;
	}

	@Override
	public TmDmpStrategy getByKey(String stId) {
	
		TmDmpStrategy tmDmpStrategy = new TmDmpStrategy();
		tmDmpStrategy.setStId(stId);
		
		return queryByKey(tmDmpStrategy);
	}
	
}
