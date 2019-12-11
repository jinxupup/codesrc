package com.jjb.dmp.biz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjb.dmp.biz.dao.RuleQueryBeanDao;
import com.jjb.dmp.biz.service.RuleQueryService;
import com.jjb.dmp.engine.bean.RuleQueryBean;
import com.jjb.unicorn.facility.model.Page;


@Service("ruleQueryService")
public class RuleQueryServiceImpl implements RuleQueryService {

	@Autowired
	private RuleQueryBeanDao ruleQueryBeanDao;
	
	@Override
	public Page<RuleQueryBean>  query(Page<RuleQueryBean> page){
		return ruleQueryBeanDao.queryPage(page);
	}
	
}
