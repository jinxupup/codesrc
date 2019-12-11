package com.jjb.acl.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.biz.dao.TmAclUserRoleDao;
import com.jjb.acl.biz.service.UserRoleService;
import com.jjb.acl.infrastructure.TmAclUserRole;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.BizException;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.BeanUtil;

@Transactional(readOnly=false)
@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService{

	@Autowired
	private TmAclUserRoleDao tmAclUserRoleDao;
	
	@Override
	public List<TmAclUserRole> getTmAclUserRole(int userId){
		
		TmAclUserRole tmAclUserRole = new TmAclUserRole();
		tmAclUserRole.setUserId(userId);
//		return tmAclUserRoleDao.queryByKey(tmAclUserRole);//modify by H.N 20171031:bug修改
		return tmAclUserRoleDao.queryForList(tmAclUserRole);
	}

	@Override
	public Page<TmAclUserRole> getPage(Page<TmAclUserRole> page) {
		
		page.getQuery().put("_SORT_LIST", null);
		page = tmAclUserRoleDao.queryPage(page);//.queryForPageList(t, page);//.queryPage(page);
	
		return page;
	}

	@Override
	public void saveTmAclUserRole(TmAclUserRole tmAclUserRole) {
		
		tmAclUserRole.setOrg(OrganizationContextHolder.getOrg());
		//modify by H.N 20171106
		TmAclUserRole tm = this.tmAclUserRoleDao.queryByKey(tmAclUserRole);
		if(null != tm){
			tmAclUserRoleDao.update(tmAclUserRole);
		} else {
			tmAclUserRoleDao.save(tmAclUserRole);
		}
//		tmAclUserRoleDao.save(tmAclUserRole); 
	}

	@Override
	public void editTmAclUserRole(TmAclUserRole tmAclUserRole) {
		TmAclUserRole tmAclUserRoleDb = new TmAclUserRole();
		tmAclUserRoleDb.setUserId(tmAclUserRole.getUserId());
		tmAclUserRoleDb.setRoleId(tmAclUserRole.getRoleId());
		tmAclUserRoleDb = tmAclUserRoleDao.queryByKey(tmAclUserRoleDb);
		if(tmAclUserRoleDb == null){
			throw new BizException("找不到该编号的用户角色");
		}
		tmAclUserRole.setJpaVersion(tmAclUserRoleDb.getJpaVersion());
		BeanUtil.merge(tmAclUserRoleDb, tmAclUserRole);
		tmAclUserRoleDao.update(tmAclUserRoleDb);
		
	}

	@Override
	public void deleteTmAclUserRole(int userId) {
		TmAclUserRole tmAclUserRole = new TmAclUserRole();
		tmAclUserRole.setUserId(userId);
		tmAclUserRoleDao.deleteByKey(tmAclUserRole);
	}

	@Override
	public void deleteBatchTmAclUserRole(List<Integer> ids) {
		for(Integer id : ids){
			deleteTmAclUserRole(id);
		}
	}


	@Override
	public void saveTmAclRoleUsers(Integer roleId, List<String> userIds) throws Exception {//modify by H.N 20171106:抛出异常

		for(String userId:userIds){
			TmAclUserRole tmAclUserRole = new TmAclUserRole();
			tmAclUserRole.setOrg(OrganizationContextHolder.getOrg());
			tmAclUserRole.setRoleId(roleId);
			tmAclUserRole.setUserId(Integer.parseInt(userId));
			saveTmAclUserRole(tmAclUserRole);
		}
	}

	@Override
	public void deleteTmAclUserRoleBykey(int userId, int roleId) {
		TmAclUserRole tmAclUserRole = new TmAclUserRole();
		tmAclUserRole.setOrg(OrganizationContextHolder.getOrg());
		tmAclUserRole.setUserId(userId);
		tmAclUserRole.setRoleId(roleId);
		tmAclUserRoleDao.deleteByKey(tmAclUserRole);
	}

	@Override
	public void deleteTmAclUserRoleBykeys(List<Integer> userIds, int roleId) throws Exception {//modify by H.N 20171106:抛出异常
		for(Integer userId:userIds){
				
			TmAclUserRole tmAclUserRole = new TmAclUserRole();
			tmAclUserRole.setOrg(OrganizationContextHolder.getOrg());
			tmAclUserRole.setRoleId(roleId);
			tmAclUserRole.setUserId(userId);
			tmAclUserRoleDao.deleteByKey(tmAclUserRole);
		}
	}

	@Override
	public void saveTmAclRole(int userId, List<String> roleIds) throws Exception {//modify by H.N 20171106:抛出异常
		
		for(String roleId:roleIds){
			TmAclUserRole tmAclUserRole = new TmAclUserRole();
			tmAclUserRole.setOrg(OrganizationContextHolder.getOrg());
			tmAclUserRole.setUserId(userId);
			tmAclUserRole.setRoleId(Integer.parseInt(roleId));
			saveTmAclUserRole(tmAclUserRole);
		}
	}

	@Override
	public void deleteTmAclUserRole(int roleId, int userId) {
		TmAclUserRole tmAclUserRole = new TmAclUserRole();
		tmAclUserRole.setOrg(OrganizationContextHolder.getOrg());
		tmAclUserRole.setRoleId(roleId);
		tmAclUserRole.setUserId(userId);
		tmAclUserRoleDao.deleteByKey(tmAclUserRole);
	}

	@Override
	public List<Integer> getRoleUserIdListByRoleId(Integer roleId) {
		List<Integer> list = tmAclUserRoleDao.getRoleUserIdListByRoleId(roleId);
		return list;
	}

	@Override
	public List<Integer> getUserRoleIdListByUserId(Integer userId) {
		List<Integer> list = tmAclUserRoleDao.getUserRoleIdListByUserId(userId);
		return list;
	}
}
