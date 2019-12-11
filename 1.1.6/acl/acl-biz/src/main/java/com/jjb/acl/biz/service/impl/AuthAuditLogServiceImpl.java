package com.jjb.acl.biz.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjb.acl.biz.dao.TmAclAuthAuditLogDao;
import com.jjb.acl.biz.service.AuthAuditLogService;
import com.jjb.acl.facility.enums.auth.AuditType;
import com.jjb.acl.facility.enums.auth.AuthStatus;
import com.jjb.acl.facility.enums.auth.ResourceAuthType;
import com.jjb.acl.infrastructure.TmAclAuthAuditLog;
import com.jjb.acl.infrastructure.TmAclBranch;
import com.jjb.acl.infrastructure.TmAclResource;
import com.jjb.acl.infrastructure.TmAclRole;
import com.jjb.acl.infrastructure.TmAclUser;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.BizException;
import com.jjb.unicorn.facility.model.Page;

/**
 * 
 * @author H.Nan
 *
 */

@Service("authAuditLogService")
public class AuthAuditLogServiceImpl implements AuthAuditLogService {

	@Autowired
	private TmAclAuthAuditLogDao tmAclAuthAuditLogDao;

	@Override
	public Integer saveTmAclAuthAuditLog(TmAclUser tmAclUser, TmAclRole tmAclRole,
			List<TmAclResource> tmAclResources,TmAclBranch tmAclBranch, String auditType,String resourceAuthType) {
		//授权审核类型：AuditType  A-角色成员添加     B-角色资源添加    C-用户重置密码
		TmAclAuthAuditLog tmAclAuthAuditLog = new TmAclAuthAuditLog();
		tmAclAuthAuditLog.setAuditType(auditType);
		tmAclAuthAuditLog.setCreateBy(OrganizationContextHolder.getUserNo());
		tmAclAuthAuditLog.setCreateBranchNo(tmAclBranch.getBranchCode());
		tmAclAuthAuditLog.setCreateDate(new Date());
		tmAclAuthAuditLog.setOrg(OrganizationContextHolder.getOrg());
		tmAclAuthAuditLog.setStatus(AuthStatus.W.name());
		if(tmAclBranch.getBranchLevel().equals("1") || ResourceAuthType.C.name().equals(resourceAuthType)){
			tmAclAuthAuditLog.setVisibleAuditBranchNo(tmAclBranch.getBranchCode());
		}else if(ResourceAuthType.B.name().equals(resourceAuthType)){
			tmAclAuthAuditLog.setVisibleAuditBranchNo(tmAclBranch.getParentPath());
		}
		
		switch (AuditType.valueOf(auditType)) {
		case A:
			tmAclAuthAuditLog.setRoleId(tmAclRole.getRoleId());
			tmAclAuthAuditLog.setRoleName(tmAclRole.getRoleName());
			tmAclAuthAuditLog.setUserId(tmAclUser.getUserId());
			tmAclAuthAuditLog.setUserName(tmAclUser.getUserName());
			tmAclAuthAuditLog.setUserNo(tmAclUser.getUserNo());
			tmAclAuthAuditLog.setUserType(tmAclUser.getUserType());
			break;
			
		case B:
			tmAclAuthAuditLog.setRoleId(tmAclRole.getRoleId());
			tmAclAuthAuditLog.setRoleName(tmAclRole.getRoleName());
			StringBuffer remark = new StringBuffer();
			for(TmAclResource resource:tmAclResources){
				if(!resource.equals(ResourceAuthType.A.name())){
					remark.append(resource.getSysType());
					remark.append("-");
					remark.append(resource.getResourceName());
					remark.append(",");
				}
			}
			tmAclAuthAuditLog.setCreateRemark(remark.toString());
			break;
			
		case C:
			tmAclAuthAuditLog.setUserId(tmAclUser.getUserId());
			tmAclAuthAuditLog.setUserName(tmAclUser.getUserName());
			tmAclAuthAuditLog.setUserNo(tmAclUser.getUserNo());
			tmAclAuthAuditLog.setUserType(tmAclUser.getUserType());
			break;

		default:
			throw new BizException("未定义的授权审核操作类型");
		}
		
		tmAclAuthAuditLogDao.save(tmAclAuthAuditLog);
		return tmAclAuthAuditLog.getId();
		
	}

	@Override
	public Page<TmAclAuthAuditLog> getPage(Page<TmAclAuthAuditLog> page, String queryType) {
		// TODO Auto-generated method stub
		return tmAclAuthAuditLogDao.getQueryPage(page, queryType);
	}

	@Override
	public TmAclAuthAuditLog getTmAclAuthAuditLogById(Integer logId) {
		TmAclAuthAuditLog entity = new TmAclAuthAuditLog();
		entity.setId(logId);
		return tmAclAuthAuditLogDao.queryByKey(entity);
	}

	@Override
	public void updateAuditLog(TmAclAuthAuditLog tmAclAuthAuditLog) {
		tmAclAuthAuditLogDao.updateNotNullable(tmAclAuthAuditLog);
		
	}

	@Override
	public List<TmAclAuthAuditLog> getTmAclAuthAuditLogUntreated(Integer userId,
			Integer roleId, String auditType) {
		TmAclAuthAuditLog entity = new TmAclAuthAuditLog();
		entity.setAuditType(auditType);
		entity.setStatus(AuthStatus.W.name());
		if(AuditType.A.name().equals(auditType)){
			//角色成员添加
			entity.setUserId(userId);
			entity.setRoleId(roleId);
		}else if(AuditType.C.name().equals(auditType)){
			entity.setUserId(userId);
		}else if(AuditType.B.name().equals(auditType)){
			//角色资源添加
			entity.setRoleId(roleId);
		}else{
			return null;
		}
		List<TmAclAuthAuditLog> logs = tmAclAuthAuditLogDao.queryForList(entity);
		return logs;
	}

}
