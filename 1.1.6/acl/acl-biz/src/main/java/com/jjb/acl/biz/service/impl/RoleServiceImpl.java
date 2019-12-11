package com.jjb.acl.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.biz.dao.TmAclRoleDao;
import com.jjb.acl.biz.dao.TmAclRoleResourceDao;
import com.jjb.acl.biz.dao.TmAclUserRoleDao;
import com.jjb.acl.biz.service.RoleService;
import com.jjb.acl.infrastructure.TmAclRole;
import com.jjb.acl.infrastructure.TmAclRoleResource;
import com.jjb.acl.infrastructure.TmAclUserRole;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.BizException;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.BeanUtil;

/**
 * 
 * @author BIG.D.W.K
 * modify by H.N
 *
 */
@Service("roleService")
@Transactional(readOnly=false)
public class RoleServiceImpl implements RoleService {

	@Autowired
	private TmAclRoleDao tmAclRoleDao;
	
	@Autowired
	private TmAclRoleResourceDao tmAclRoleResourceDao;
	
	@Autowired
	private TmAclUserRoleDao tmAclUserRoleDao;
	
	@Autowired
	public TmAclRole getRootRole(){
		TmAclRole tmAclRole = new TmAclRole();
		tmAclRole.setRoleName("root");
		tmAclRole.setOrg(OrganizationContextHolder.getOrg());
		TmAclRole resultTm = tmAclRoleDao.queryForOne(tmAclRole);
		return resultTm;
	}
	
	@Override
	public TmAclRole getTmAclRole(int roleId) {
		TmAclRole tmAclRole = new TmAclRole();
		tmAclRole.setRoleId(roleId);
		return tmAclRoleDao.queryByKey(tmAclRole);
	}
	
	@Override
	public Page<TmAclRole> getRolePage(Page<TmAclRole> page){
		page = tmAclRoleDao.queryPage(page);
		return page;
	}

	@Override
	public void saveTmAclRole(TmAclRole tmAclRole) {
		TmAclRole tmAclRoleDb = new TmAclRole();
		tmAclRoleDb.setRoleName(tmAclRole.getRoleName());
		List<TmAclRole> roles = tmAclRoleDao.queryForList(tmAclRoleDb);
		if(roles!=null&&roles.size()>0){
			throw new BizException("已存在该名称的角色");
		}
		tmAclRoleDao.save(tmAclRole);
	}

	@Override
	public void editTmAclRole(TmAclRole tmAclRole) {
		TmAclRole tmAclRoleDb = new TmAclRole();
		tmAclRoleDb.setRoleId(tmAclRole.getRoleId());
		tmAclRoleDb = tmAclRoleDao.queryByKey(tmAclRoleDb);
		if(tmAclRoleDb == null){
			throw new BizException("找不到该编号的角色");
		}
		TmAclRole entity = new TmAclRole();
		entity.setRoleName(tmAclRole.getRoleName());
		List<TmAclRole> roles = tmAclRoleDao.queryForList(entity);
		for(TmAclRole role:roles){
			if(role.getRoleId()!=tmAclRole.getRoleId() && tmAclRole.getRoleName().equals(role.getRoleName())){
				throw new BizException("已存在该名称的角色");
			}
		}
		tmAclRole.setJpaVersion(tmAclRoleDb.getJpaVersion());
		BeanUtil.merge(tmAclRoleDb, tmAclRole);
		tmAclRoleDao.update(tmAclRoleDb);
	}
	
	@Override
	public void deleteTmAclRole(int roleId) {

		//删除该角色下的用户
		List<TmAclUserRole> users= tmAclUserRoleDao.selectByRoleId(roleId);
		for(TmAclUserRole user:users){
			TmAclUserRole tmAclUserRole = new TmAclUserRole();
			tmAclUserRole.setRoleId(roleId);
			tmAclUserRole.setUserId(user.getUserId());
			tmAclUserRoleDao.deleteByKey(tmAclUserRole);
		}
		
		//删除该角色下的资源
		List<TmAclRoleResource> resources= tmAclRoleResourceDao.getAclRoleResourceByRoleId(roleId);
		for(TmAclRoleResource resource:resources){
			TmAclRoleResource tmAclRoleResource = new TmAclRoleResource();
			tmAclRoleResource.setRoleId(roleId);
			tmAclRoleResource.setResourceCode(resource.getResourceCode());
			tmAclRoleResource.setSysType(resource.getSysType());
			tmAclRoleResourceDao.deleteByKey(tmAclRoleResource);
		}
		
		//删除角色
		TmAclRole tmAclRole = new TmAclRole();
		tmAclRole.setRoleId(roleId);
		tmAclRoleDao.deleteByKey(tmAclRole);
	}

	@Override
	public void deleteBatchTmAclRole(List<Integer> roleIds) {
		for(Integer roleId : roleIds){
			deleteTmAclRole(roleId);
		}
	}

	@Override
	public List<TmAclRole> getTmAclRoleName(int userId) {
		return tmAclRoleDao.selectByUserId(userId);
	}

	@Override
	/**
	 * 更新角色主表
	 * add by H.N 20171110
	 */
	public void updateRole(TmAclRole tmAclRole){
		this.tmAclRoleDao.updateNotNullable(tmAclRole);
	}

	@Override
	public List<String> getRoleResourceAuthTypeListByRoleId(Integer roleId) {
		List<String> list = tmAclRoleDao.getRoleResourceAuthTypeListByRoleId(roleId);
		return list;
	}
}
