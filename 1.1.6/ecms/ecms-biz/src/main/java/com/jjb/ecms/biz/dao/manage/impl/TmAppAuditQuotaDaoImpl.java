package com.jjb.ecms.biz.dao.manage.impl;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.manage.TmAppAuditQuotaDao;
import com.jjb.ecms.infrastructure.TmAppAuditQuota;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;

/**
 * @description 
 * @author J.J
 * @date 2017年7月14日下午2:41:05
 */

@Repository("tmAppAuditQuotaDao")
public class TmAppAuditQuotaDaoImpl extends AbstractBaseDao<TmAppAuditQuota> implements TmAppAuditQuotaDao {

	@Override
	public Page<TmAppAuditQuota> getPage(Page<TmAppAuditQuota> page, TmAppAuditQuota TmAppAuditQuota) {
		if (null == page.getQuery()) {
			page.setQuery(new Query());
		}
		Page<TmAppAuditQuota> auditQuota = queryForPageList(TmAppAuditQuota, page.getQuery(), page);
		return auditQuota;
	}
}
