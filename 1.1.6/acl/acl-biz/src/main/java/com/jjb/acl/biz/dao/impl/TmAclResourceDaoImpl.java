package com.jjb.acl.biz.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jjb.acl.biz.dao.TmAclResourceDao;
import com.jjb.acl.infrastructure.TmAclResource;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;

@Repository("tmAclResourceDao")
public class TmAclResourceDaoImpl extends AbstractBaseDao<TmAclResource> implements TmAclResourceDao{

	public static final String  selectAll = "acl.biz.TmAclResource.selectAll";
	
	@Override
	public Page<TmAclResource> queryPage(Page<TmAclResource> page){
		
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		page.getQuery().put("org", OrganizationContextHolder.getOrg());

		Page<TmAclResource> p = queryForPageList(selectAll, page.getQuery(), page);
		
		return p;
	}

	@Override
	public List<TmAclResource> getListBySysType(String sysType){
		
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("sysType", sysType);
		List<TmAclResource> list = queryForList(selectAll, parameter);
		
		return list;
	}
	
}
