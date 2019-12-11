package com.jjb.acl.biz.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jjb.acl.biz.dao.TmAclRoleResourceDao;
import com.jjb.acl.infrastructure.TmAclResource;
import com.jjb.acl.infrastructure.TmAclRoleResource;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Query;


/**
 * 
 * @author BIG.D.W.K
 *
 */
@Repository("tmAclRoleResourceDao")
public class TmAclRoleResourceDaoImpl extends AbstractBaseDao<TmAclRoleResource> implements TmAclRoleResourceDao{
	
	public static final String  selectAll = "acl.biz.TmAclRoleResource.selectAll";
	public static final String  deleteByResource = "acl.biz.TmAclRoleResource.deleteByResource";
	public static final String  NS = "acl.biz.TmAclRoleResource";
	
	@Override
	public List<TmAclRoleResource> getAclRoleResourceByRoleId(Integer roleId) {
		TmAclRoleResource tmAclRoleResource = new TmAclRoleResource();
		tmAclRoleResource.setRoleId(roleId);
		List<TmAclRoleResource> list = queryForList(selectAll, tmAclRoleResource);
		return list;		
	}
			
	@Override
	public void deleteByRoleIdAndSysType(int roleId,String sysType) {
		
		TmAclRoleResource forDelete = new TmAclRoleResource();
		forDelete.setRoleId(roleId);
		forDelete.setSysType(sysType);
		
		List<TmAclRoleResource> list = queryForList(forDelete);
		for(TmAclRoleResource tmAclRoleResource : list){
			deleteByKey(tmAclRoleResource);		
		}
	}
	
	/**
	 * 删除resource资源
	 * @param resources
	 */
	public void deleteByResource(String resource, String sysType){
		TmAclRoleResource tmAclRoleResource = new TmAclRoleResource();
		tmAclRoleResource.setResourceCode(resource);
		tmAclRoleResource.setSysType(sysType);
		delete(deleteByResource, tmAclRoleResource);
	}

	@Override
	public List<String> getTmAclRoleResourceSysTypeListByRoleId(Integer roleId) {
		String sqlId = NS + ".selectRoleResourceSysTypeList";
		Query query = new Query();
		query.put("roleId", roleId);
		List<String> q =  getSqlSession().selectList(sqlId, query);
		return q;
	}

	@Override
	public List<TmAclResource> getTmAclResourceListByRoleIdAndSysCode(
			Integer roleId, String sysCode) {
		String sqlId = NS + ".selectRoleResourceList";
		Query query = new Query();
		query.put("roleId", roleId);
		query.put("sysType", sysCode);
		List<TmAclResource> q =  getSqlSession().selectList(sqlId, query);
		return q;
	}
}
