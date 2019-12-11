package com.jjb.acl.biz.dao;

import com.jjb.acl.infrastructure.TmSystemAudit;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

/**
 * @description 修改记录Dao
 * @author J.J
 * @date 2017年7月14日下午3:59:03
 */
public interface TmSystemAuditDao extends BaseDao<TmSystemAudit>{

	/**
	 * 终审人员额度分配查询页面
	 * @param page
	 * @return
	 */
	Page<TmSystemAudit> getPage(Page<TmSystemAudit> page);
}
