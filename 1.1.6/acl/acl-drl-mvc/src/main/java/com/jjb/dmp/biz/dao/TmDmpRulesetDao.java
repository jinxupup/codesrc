package com.jjb.dmp.biz.dao;

import java.util.List;

import com.jjb.dmp.infrastructure.TmDmpRuleset;
import com.jjb.unicorn.base.dao.BaseDao;

public interface TmDmpRulesetDao extends BaseDao<TmDmpRuleset>{
	
	List<TmDmpRuleset> queryByStId(String stId);
	
	
	TmDmpRuleset getByKey(String rsId);
}
