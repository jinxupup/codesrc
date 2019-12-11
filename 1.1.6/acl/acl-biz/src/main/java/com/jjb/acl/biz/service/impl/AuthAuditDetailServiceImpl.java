package com.jjb.acl.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjb.acl.biz.dao.TmAclAuthDetailDao;
import com.jjb.acl.biz.service.AuthAuditDetailService;
import com.jjb.acl.infrastructure.TmAclAuthDetail;
import com.jjb.acl.infrastructure.TmAclRole;

/**
 * 
 * @author H.Nan
 *
 */

@Service("authAuditDetailService")
public class AuthAuditDetailServiceImpl implements AuthAuditDetailService{

	
	@Autowired
	private TmAclAuthDetailDao tmAclAuthDetailDao;

	@Override
	public void saveTmAuthAuditDetail(int logId, TmAclRole curTmAclRole,
			String sysType, String resource) {
		TmAclAuthDetail tmAclAuthDetail = new TmAclAuthDetail();
		tmAclAuthDetail.setLogId(logId);
		tmAclAuthDetail.setOrg(curTmAclRole.getOrg());
		tmAclAuthDetail.setResourceCode(resource);
		tmAclAuthDetail.setRoleId(curTmAclRole.getRoleId());
		tmAclAuthDetail.setRoleName(curTmAclRole.getRoleName());
		tmAclAuthDetail.setSysType(sysType);
		
		tmAclAuthDetailDao.save(tmAclAuthDetail);
		
	}

	@Override
	public List<TmAclAuthDetail> getTmAclAuthDetailByLogId(Integer logId) {
		TmAclAuthDetail entity = new TmAclAuthDetail();
		entity.setLogId(logId);
		return tmAclAuthDetailDao.queryForList(entity);
	}
}
