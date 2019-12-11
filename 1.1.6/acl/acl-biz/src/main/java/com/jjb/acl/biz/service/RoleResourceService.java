package com.jjb.acl.biz.service;

import java.util.List;

import com.jjb.acl.infrastructure.TmAclResource;
import com.jjb.acl.infrastructure.TmAclRoleResource;

/**
 * 
 * @author BIG.D.W.K
 *
 */
public interface RoleResourceService {
	
	/**
	 * 通过角色Id获取tmAclRoleResource列表对象信息
	 * @param roleId
	 * @return
	 */
	List<TmAclRoleResource> getTmAclRoleResourceList(Integer roleId);
	
	/**
	 * 通过角色Id和资源系统code获取tmAclRoleResource列表对象信息
	 * @param roleId
	 * @return
	 */
	List<TmAclRoleResource> getTmAclRoleResourceList(Integer roleId, String sysType);
	
	/**
	 * 通过resourceCode获取tmAclRoleResource列表对象信息
	 * @param roleId
	 * @return
	 */
	List<TmAclRoleResource> getTmAclRoleResource(String resourceCode);
	
	/**
	 * 保存角色资源信息
	 * @param roleId
	 * @param list
	 * @param resources
	 */
	public void saveRoleResource(Integer roleId, String sysType, String[] resources);
	
	/**
	 * 删除resource的资源
	 * @param resources
	 */
	void deleteByResource(String resource, String sysType);

	/**
	 * 通过roleId获取该角色拥有资源系统类型列表
	 * @param roleId
	 * @return
	 */
	List<String> getTmAclRoleResourceSysTypeListByRoleId(Integer roleId);

	/**
	 * 
	 * @Description 通过roleId和sysCode获取该角色对应拥有的该系统资源列表
	 * @param roleId
	 * @param sysCode
	 * @return
	 */
	List<TmAclResource> getTmAclResourceListByRoleIdAndSysCode(Integer roleId,
			String sysCode);
	
}
