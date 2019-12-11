package com.jjb.acl.biz.dao;

import java.util.List;

import com.jjb.acl.infrastructure.TmAclUserRole;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

public interface TmAclUserRoleDao extends BaseDao<TmAclUserRole>{

	Page<TmAclUserRole> queryPage(Page<TmAclUserRole> page);

	List<TmAclUserRole> selectByRoleId(int roleId);

	int deleteByUserId(int userId);

	List<Integer> getRoleUserIdListByRoleId(Integer roleId);

	List<Integer> getUserRoleIdListByUserId(Integer userId);
	
}