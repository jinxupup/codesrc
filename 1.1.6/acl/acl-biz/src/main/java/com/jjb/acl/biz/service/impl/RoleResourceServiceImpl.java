package com.jjb.acl.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.biz.dao.TmAclRoleResourceDao;
import com.jjb.acl.biz.service.RoleResourceService;
import com.jjb.acl.infrastructure.TmAclResource;
import com.jjb.acl.infrastructure.TmAclRoleResource;


/**
 * 
 * @author BIG.D.W.K
 *
 */

@Transactional(readOnly=false)
@Service("roleResourceService")
public class RoleResourceServiceImpl implements RoleResourceService {
	
	@Autowired
	private TmAclRoleResourceDao tmAclRoleResourceDao;
	
	@Override
	public List<TmAclRoleResource> getTmAclRoleResourceList(Integer roleId) {
		List<TmAclRoleResource> list = new ArrayList<TmAclRoleResource>();
		list = tmAclRoleResourceDao.getAclRoleResourceByRoleId(roleId);
		return list;
	}

	@Override
	public List<TmAclRoleResource> getTmAclRoleResourceList(Integer roleId, String sysType) {
		List<TmAclRoleResource> list = new ArrayList<TmAclRoleResource>();
		TmAclRoleResource tmAclRoleResource = new TmAclRoleResource();
		tmAclRoleResource.setRoleId(roleId);
		tmAclRoleResource.setSysType(sysType);
		list = tmAclRoleResourceDao.queryForList(tmAclRoleResource);
		return list;
	}
	
	@Override
	public void saveRoleResource(Integer roleId,String sysType, String[] resources) {
		if(roleId!=null) {
			//首先删除
			tmAclRoleResourceDao.deleteByRoleIdAndSysType(roleId, sysType);
			//依次添加
			if(resources !=null){
				for (String resourceCode:resources){
					TmAclRoleResource tmAclRoleResource = new TmAclRoleResource();
					tmAclRoleResource.setRoleId(roleId);
					tmAclRoleResource.setSysType(sysType);
					tmAclRoleResource.setResourceCode(resourceCode);
					tmAclRoleResourceDao.save(tmAclRoleResource);
				}	
			}
		}
	}
/**
 * 根据资源代码获取资源列表
 */
	@Override
	public List<TmAclRoleResource> getTmAclRoleResource(String resourceCode) {
		List<TmAclRoleResource> list = new ArrayList<TmAclRoleResource>();
		TmAclRoleResource tmAclRoleResource = new TmAclRoleResource();
		tmAclRoleResource.setResourceCode(resourceCode);
		list = tmAclRoleResourceDao.queryForList(tmAclRoleResource);
		
		return list;
	}
	
	/**
	 * 删除resource的资源
	 * @param resources
	 */
	@Override
	public void deleteByResource(String resource, String sysType){
		tmAclRoleResourceDao.deleteByResource(resource, sysType);
	}

	@Override
	public List<String> getTmAclRoleResourceSysTypeListByRoleId(Integer roleId) {
		List<String> list = tmAclRoleResourceDao.getTmAclRoleResourceSysTypeListByRoleId(roleId);
		return list;
	}

	@Override
	public List<TmAclResource> getTmAclResourceListByRoleIdAndSysCode(
			Integer roleId, String sysCode) {
		List<TmAclResource> list = tmAclRoleResourceDao.getTmAclResourceListByRoleIdAndSysCode(roleId,sysCode);
		return list;
	}
}
