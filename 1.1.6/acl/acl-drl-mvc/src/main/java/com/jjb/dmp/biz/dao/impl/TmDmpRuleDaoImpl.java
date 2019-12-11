package com.jjb.dmp.biz.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jjb.dmp.biz.dao.TmDmpRuleDao;
import com.jjb.dmp.infrastructure.TmDmpRule;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;

@Repository("tmDmpRuleDao")
public class TmDmpRuleDaoImpl extends AbstractBaseDao<TmDmpRule> implements TmDmpRuleDao {

	@Override
	public List<TmDmpRule> queryByRsId(String rsId) {
		
		TmDmpRule tmDmpRule = new TmDmpRule();
		tmDmpRule.setRsId(rsId);
		Map<String, Object> sort = new HashMap<String, Object>();
		sort.put("_SORT_NAME", "priority");
		sort.put("_SORT_ORDER", "asc");
		return queryForList(tmDmpRule,sort);
	}

	@Override
	public TmDmpRule getByKey(String ruleId) {
		TmDmpRule tmDmpRule = new TmDmpRule();
		tmDmpRule.setRuleId(ruleId);
		
		return queryByKey(tmDmpRule);
	}

}
