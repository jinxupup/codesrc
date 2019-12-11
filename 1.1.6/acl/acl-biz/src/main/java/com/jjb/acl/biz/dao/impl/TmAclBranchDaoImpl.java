package com.jjb.acl.biz.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jjb.acl.biz.dao.TmAclBranchDao;
import com.jjb.acl.infrastructure.TmAclBranch;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;

@Repository("tmAclBranchDao")
public class TmAclBranchDaoImpl extends AbstractBaseDao<TmAclBranch> implements TmAclBranchDao {

	public static final String  selectAll = "acl.biz.TmAclBranch.selectAll";
	public static final String  selectLeftLikePath = "acl.biz.TmAclBranch.selectLeftLikePath";
	
	@Override
	public Page<TmAclBranch> queryPage(Page<TmAclBranch> page){
		
		
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		page.getQuery().put("org", OrganizationContextHolder.getOrg());
		Page<TmAclBranch> p = queryForPageList(selectAll, page.getQuery(), page);
		
		return p;
	}
	
	@Override
	public List<String> queryCodes(){
		Query query = new Query();
		List<String> q =  getSqlSession().selectList("acl.biz.TmAclBranch.selectCodes", query);
		
		return q;
	}
	
	@Override
	public List<TmAclBranch> getAllBranch() {
		TmAclBranch tmAclBranch = new TmAclBranch();
		return queryForList(tmAclBranch);
	}
	
	@Override
	public List<TmAclBranch> selectLeftLikePath(String parentPathLeft){
		Query query = new Query();
		query.put("parentPath", parentPathLeft);
		return queryForList(selectLeftLikePath, query);
	}
}
