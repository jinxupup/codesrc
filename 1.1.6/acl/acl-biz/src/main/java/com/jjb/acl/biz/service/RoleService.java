package com.jjb.acl.biz.service;

import java.util.List;

import com.jjb.acl.infrastructure.TmAclRole;
import com.jjb.unicorn.facility.model.Page;

/**
 * 
 * @author BIG.D.W.K
 *
 */
public interface RoleService {
	
	/**
	 * add by H.N 20171031
	 * 查询DB中的ROOT用户
	 */
	TmAclRole getRootRole();
	
	/**
	 * 根据userId获取角色对象
	 * @param userId
	 * @return
	 */
	
	List<TmAclRole> getTmAclRoleName(int userId);
	/**
	 * 根据roleId获取角色对象
	 * @param roleId
	 * @return
	 */
	TmAclRole getTmAclRole(int roleId);

	/**
	 * 获取角色分页信息
	 * @param page
	 * @return
	 */
	Page<TmAclRole> getRolePage(Page<TmAclRole> page);
	
	/**
	 * 保存角色对象
	 * @param tmAclRole
	 * @return
	 */
	public void saveTmAclRole(TmAclRole tmAclRole);
	
	/**
	 * 编辑角色对象
	 * @param tmAclRole
	 * @return
	 */
	public void editTmAclRole(TmAclRole tmAclRole);
	
	/**
	 * 删除角色对象信息
	 * @param page
	 * @return
	 */
	public void deleteTmAclRole(int roleId);
	
	/**
	 * 批量删除角色对象信息
	 * @param page
	 * @return
	 */
	public void deleteBatchTmAclRole(List<Integer> roleIds);
	
	/**
	 * 更新角色信息
	 * @param tmAclRole
	 */
	public void updateRole(TmAclRole tmAclRole);

	/**
	 * 根据roleId获取角色对应资源的资源授权类型list
	 * @param roleId
	 * @return
	 */
	List<String> getRoleResourceAuthTypeListByRoleId(Integer roleId);

}
