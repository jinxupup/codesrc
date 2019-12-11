package com.jjb.dmp.biz.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jjb.dmp.biz.dao.TmDmpRulesetDao;
import com.jjb.dmp.infrastructure.TmDmpRuleset;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;

@Repository("tmDmpRulesetDao")
public class TmDmpRulesetDaoImpl extends AbstractBaseDao<TmDmpRuleset> implements TmDmpRulesetDao {

	@Override
	public List<TmDmpRuleset> queryByStId(String stId) {
		
		TmDmpRuleset tmDmpRuleset = new TmDmpRuleset();
		tmDmpRuleset.setStId(stId);
		return queryForList(tmDmpRuleset);
		
	}

	@Override
	public TmDmpRuleset getByKey(String rsId) {
		TmDmpRuleset tmDmpRuleset = new TmDmpRuleset();
		tmDmpRuleset.setRsId(rsId);
		
		return queryByKey(tmDmpRuleset);
	}

}
