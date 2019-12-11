package com.jjb.acl.biz.service;

import java.util.List;

import com.jjb.acl.infrastructure.TmAclAuthAuditLog;
import com.jjb.acl.infrastructure.TmAclBranch;
import com.jjb.acl.infrastructure.TmAclResource;
import com.jjb.acl.infrastructure.TmAclRole;
import com.jjb.acl.infrastructure.TmAclUser;
import com.jjb.unicorn.facility.model.Page;

/**
 * 
 * @ClassName AuthAuditLogService
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author H.N
 * @Date 2017年12月2日 下午2:03:49
 * @version 1.0.0
 */
public interface AuthAuditLogService {

	
	/**
	 * 角色成员变更、角色资源变更、重置密码，需保存授权审核记录
	 * 授权审核类型：AuditType  A-角色成员添加     B-角色资源添加    C-用户重置密码
	 * @param tmAclUser
	 * @param tmAclRole
	 * @param tmAclResources
	 * @param tmAclBranch
	 * @param auditType 授权审核类型
	 * @return 返回TmAclAuthAuditLog主键id
	 */
	Integer saveTmAclAuthAuditLog(TmAclUser tmAclUser,TmAclRole tmAclRole,List<TmAclResource> tmAclResources,TmAclBranch tmAclBranch,String auditType,String resourceAuthType);

	Page<TmAclAuthAuditLog> getPage(Page<TmAclAuthAuditLog> page, String queryType);

	TmAclAuthAuditLog getTmAclAuthAuditLogById(Integer logId);

	void updateAuditLog(TmAclAuthAuditLog tmAclAuthAuditLog);

	/**
	 * 
	 * @Description 根据审核类型，查询是否有待审核的相同数据
	 * @param userId
	 * @param roleId
	 * @param auditType
	 * @return
	 */
	List<TmAclAuthAuditLog> getTmAclAuthAuditLogUntreated(Integer userId,
			Integer roleId, String auditType);
}
