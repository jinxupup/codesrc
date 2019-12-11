package com.jjb.acl.biz.dao;

import com.jjb.acl.infrastructure.TmAclAuthAuditLog;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

/**
 * 
 * @ClassName TmAclAuthAuditLogDao
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author H.N
 * @Date 2017年12月2日 下午2:01:49
 * @version 1.0.0
 */
public interface TmAclAuthAuditLogDao extends BaseDao<TmAclAuthAuditLog>{

	Page<TmAclAuthAuditLog> getQueryPage(Page<TmAclAuthAuditLog> page, String queryType);

	

}
