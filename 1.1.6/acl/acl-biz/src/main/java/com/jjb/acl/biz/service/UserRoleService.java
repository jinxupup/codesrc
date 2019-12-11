package com.jjb.acl.biz.service;

import java.util.List;

import com.jjb.acl.infrastructure.TmAclUserRole;
import com.jjb.unicorn.facility.model.Page;

public interface UserRoleService {

	Page<TmAclUserRole> getPage(Page<TmAclUserRole> page);

	List<TmAclUserRole> getTmAclUserRole(int userId);
	
	void saveTmAclUserRole(TmAclUserRole tmAclUserRole);
	
	void editTmAclUserRole(TmAclUserRole tmAclUserRole);
	
	void deleteTmAclUserRole(int userId);
	
	void deleteBatchTmAclUserRole(List<Integer> ids);
	
	void saveTmAclRoleUsers(Integer roleId,List<String> userIds) throws Exception;
	
	void deleteTmAclUserRoleBykey(int userId,int roleId);
	
	void deleteTmAclUserRoleBykeys(List<Integer> userIds,int roleId) throws Exception;
	/**
	 * 用户添加角色
	 * @throws Exception 
	 */
	void saveTmAclRole(int userId,List<String> roleIds) throws Exception;
	/**
	 * 删除角色
	 * @param roleId
	 * @param userId
	 */
	void deleteTmAclUserRole(int roleId,int userId);

	/**
	 * 根据roleId获取角色所有成员userId的list
	 * @param roleId
	 * @return
	 */
	List<Integer> getRoleUserIdListByRoleId(Integer roleId);

	/**
	 * 
	 * @Description 根据userId获取用户对应的角色roleId的list
	 * @param userId
	 * @return
	 */
	List<Integer> getUserRoleIdListByUserId(Integer userId);
}
