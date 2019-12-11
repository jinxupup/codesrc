package com.jjb.acl.biz.dao.impl;

import com.jjb.acl.biz.dao.TmSystemAuditDao;
import com.jjb.acl.infrastructure.TmSystemAudit;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import org.springframework.stereotype.Repository;

/**
 * @description
 * @author J.J
 * @date 2017年7月14日下午4:04:11
 */
@Repository("tmSystemAuditDao")
public class TmSystemAuditDaoImpl extends AbstractBaseDao<TmSystemAudit> implements TmSystemAuditDao {

	public static final String selectAll = "com.jjb.acl.gmp.biz.SystemAuditMapper.selectAll";//查询历史操作记录表

	@Override
	public Page<TmSystemAudit> getPage(Page<TmSystemAudit> page) {
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		Page<TmSystemAudit> systemAuditPage = queryForPageList(selectAll, page.getQuery(), page);
		return systemAuditPage;
	}

}
