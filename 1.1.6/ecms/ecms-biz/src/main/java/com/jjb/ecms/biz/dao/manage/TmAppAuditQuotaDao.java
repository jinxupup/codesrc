package com.jjb.ecms.biz.dao.manage;

import com.jjb.ecms.infrastructure.TmAppAuditQuota;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

/**
 * @description 终审人员额度分配查询Dao
 * @author J.J
 * @date 2017年7月14日下午2:38:24
 */
public interface TmAppAuditQuotaDao extends BaseDao<TmAppAuditQuota>{

	/**
	 * 终审人员额度分配查询页面
	 * @param page
	 * @return
	 */
	Page<TmAppAuditQuota> getPage(Page<TmAppAuditQuota> page,TmAppAuditQuota TmAppAuditQuota);
}
