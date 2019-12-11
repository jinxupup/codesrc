package com.jjb.dmp.biz.dao.impl;

import org.springframework.stereotype.Service;

import com.jjb.dmp.biz.dao.RuleQueryBeanDao;
import com.jjb.dmp.engine.bean.RuleQueryBean;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;

@Service("ruleQueryBeanDao")
public class RuleQueryBeanDaoImpl extends AbstractBaseDao<RuleQueryBean> implements RuleQueryBeanDao {

	private final String queryRuleBeanSqlId = "com.jjb.dmp.mapping.RuleQueryBeanMapper.selectAll";
	
	@Override
	public Page<RuleQueryBean> queryPage(Page<RuleQueryBean> page){
		return queryForPageList(queryRuleBeanSqlId, page.getQuery(), page);
	}
	
}
