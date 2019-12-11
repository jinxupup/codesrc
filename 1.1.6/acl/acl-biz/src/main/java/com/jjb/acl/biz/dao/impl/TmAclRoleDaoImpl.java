package com.jjb.acl.biz.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jjb.acl.biz.dao.TmAclRoleDao;
import com.jjb.acl.infrastructure.TmAclRole;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;

/**
 * 
 * @author BIG.D.W.K
 *
 */
@Repository("tmAclRoleDao")
public class TmAclRoleDaoImpl extends AbstractBaseDao<TmAclRole> implements TmAclRoleDao{
	
	public static final String  selectAll = "acl.biz.TmAclRole.selectAll";
	public static final String  selectByUserId = "acl.biz.TmAclRole.selectByUserId";
	public static final String  NS = "acl.biz.TmAclRole";

	@Override
	public Page<TmAclRole> queryPage(Page<TmAclRole> page){
	
		Page<TmAclRole> p = queryForPageList(selectAll, page.getQuery(), page);
		
		return p;
	}

	@Override
	public Boolean validate(TmAclRole tmAclRole) {
		// TODO Auto-generated method stub
		
		TmAclRole tm = queryByKey(tmAclRole);
		if (tm != null) {
			return true;
		}
		return false;
	}

	@Override
	public List<TmAclRole> selectByUserId(int userId) {
			
			Query query = new Query();
			query.put("userId", userId);
			List<TmAclRole> p = queryForList(selectByUserId, query);
			return p;
		}

	@Override
	public List<String> getRoleResourceAuthTypeListByRoleId(Integer roleId) {
		String sqlId = NS + ".selectRoleResourceAuthType";
		Query query = new Query();
		query.put("roleId", roleId);
		List<String> q =  getSqlSession().selectList(sqlId, query);
		return q;
	}
			
}
