package com.jjb.dmp.biz.dao;

import java.util.List;

import com.jjb.dmp.infrastructure.TmDmpRule;
import com.jjb.unicorn.base.dao.BaseDao;

public interface TmDmpRuleDao extends BaseDao<TmDmpRule>{
	
	List<TmDmpRule> queryByRsId(String rsId);
	
	TmDmpRule getByKey(String ruleId);
	
}
