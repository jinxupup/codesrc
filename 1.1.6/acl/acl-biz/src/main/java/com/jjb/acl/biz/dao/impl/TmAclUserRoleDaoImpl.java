package com.jjb.acl.biz.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jjb.acl.biz.dao.TmAclUserRoleDao;
import com.jjb.acl.infrastructure.TmAclUserRole;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;

@Repository("tmAclUserRoleDao")
public class TmAclUserRoleDaoImpl extends AbstractBaseDao<TmAclUserRole> implements TmAclUserRoleDao{

	public static final String  selectAll = "acl.biz.TmAclUserRole.selectByRoleId";
	public static final String NS = "acl.biz.TmAclUserRole";
	
	@Override
	public Page<TmAclUserRole> queryPage(Page<TmAclUserRole> page){
		
		
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		page.getQuery().put("org", OrganizationContextHolder.getOrg());
		Page<TmAclUserRole> p = queryForPageList(selectAll, page.getQuery(), page);
		
		return p;
	}

	@Override
	public List<TmAclUserRole> selectByRoleId(int roleId) {
		TmAclUserRole tmAclUserRole = new TmAclUserRole();
		tmAclUserRole.setRoleId(roleId);
		return queryForList(tmAclUserRole);
	}

	@Override
	public int deleteByUserId(int userId) {
		String sqlId = NS + ".deleteByUserId";
		TmAclUserRole entity = new TmAclUserRole();
		entity.setUserId(userId);
		int res =  delete(sqlId, entity);
		return res;
		
	}

	@Override
	public List<Integer> getRoleUserIdListByRoleId(Integer roleId) {
		String sqlId = NS + ".selectRoleUserIdList";
		Query query = new Query();
		query.put("roleId", roleId);
		List<Integer> q =  getSqlSession().selectList(sqlId, query);
		return q;
	}

	@Override
	public List<Integer> getUserRoleIdListByUserId(Integer userId) {
		String sqlId = NS + ".selectUserRoleIdList";
		Query query = new Query();
		query.put("userId", userId);
		List<Integer> q =  getSqlSession().selectList(sqlId, query);
		return q;
	}
	
	
}
