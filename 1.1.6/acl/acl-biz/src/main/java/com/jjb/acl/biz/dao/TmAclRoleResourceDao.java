package com.jjb.acl.biz.dao;

import java.util.List;

import com.jjb.acl.infrastructure.TmAclResource;
import com.jjb.acl.infrastructure.TmAclRoleResource;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * 
 * @author BIG.D.W.K
 *
 */
public interface TmAclRoleResourceDao extends BaseDao<TmAclRoleResource>{

	void deleteByRoleIdAndSysType(int roleId, String sysType);

	/**
	 * 根据角色ID查询资源
	 * @param roleId
	 * @return
	 */
	List<TmAclRoleResource> getAclRoleResourceByRoleId(Integer roleId);

	/**
	 * 删除resource资源
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
	 * @Description  通过roleId和sysCode获取该角色对应拥有的该系统资源列表
	 * @param roleId
	 * @param sysCode
	 * @return
	 */
	List<TmAclResource> getTmAclResourceListByRoleIdAndSysCode(Integer roleId,
			String sysCode);
}
