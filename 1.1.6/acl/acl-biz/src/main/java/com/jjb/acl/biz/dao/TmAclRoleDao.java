package com.jjb.acl.biz.dao;

import java.util.List;

import com.jjb.acl.infrastructure.TmAclRole;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

/**
 * 
 * @author BIG.D.W.K
 *
 */
public interface TmAclRoleDao extends BaseDao<TmAclRole>{

	
	/**
	 * 获取用户分页数据
	 * @param page
	 * @return
	 */
	public Page<TmAclRole> queryPage(Page<TmAclRole> page);
	

	/**
	 * 角色名称验证
	 * @param roleName
	 */
	public Boolean validate(TmAclRole tmAclRole);
	
	/**
	 * 根据userId,查询用户列表
	 * @param userId
	 * @return
	 */
	List<TmAclRole> selectByUserId(int userId);


	public List<String> getRoleResourceAuthTypeListByRoleId(Integer roleId);
	
}


