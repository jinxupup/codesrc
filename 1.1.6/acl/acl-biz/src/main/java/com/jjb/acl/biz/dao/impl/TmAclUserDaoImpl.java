package com.jjb.acl.biz.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jjb.acl.biz.dao.TmAclUserDao;
import com.jjb.acl.biz.utils.ContextUtil;
import com.jjb.acl.infrastructure.TmAclUser;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.kits.StrKit;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;

/**
 * 
 * @author BIG.D.W.K
 *
 */
@Repository("tmAclUserDao")
public class TmAclUserDaoImpl extends AbstractBaseDao<TmAclUser> implements TmAclUserDao {

	public static final String  selectAll = "acl.biz.TmAclUser.selectAll";
	public static final String  selectByRoleId = "acl.biz.TmAclUser.selectByRoleId";
	public static final String  NS = "acl.biz.TmAclUser";
	public static final String  selectAllByBranch = "acl.biz.TmAclUser.selectAllByBranch";
	public static final String  selectWithOutRoleId = "acl.biz.TmAclUser.selectWithOutRoleId";
	@Autowired
    ContextUtil contextUtil;
	@Override
	public TmAclUser getUserByUserId(String userId) {
		
		TmAclUser tmAclUser = new TmAclUser();
		tmAclUser.setUserNo(userId);
		
		List<TmAclUser> list = queryForList(tmAclUser);
		if(StrKit.notBlankList(list)){
			return list.get(0);
		}
		
		return null;
	}
	
	@Override
	public TmAclUser getUserByUserNo(String userNo) {
		
		TmAclUser tmAclUser = new TmAclUser();
		tmAclUser.setUserNo(userNo);
		
		List<TmAclUser> list = queryForList(tmAclUser);
		if(StrKit.notBlankList(list)){
			return list.get(0);
		}
		
		return null;
	}
	
	@Override
	public Page<TmAclUser> queryPage(Page<TmAclUser> page){
		if(null==page.getQuery()){
			page.setQuery(new Query());
		}
		String sqlId ;
		if(page.getQuery().get("branchLevel") != null){
			sqlId = selectAll;
		}else {
			List<String> branchList = contextUtil.getOwningBranchMap(OrganizationContextHolder.getBranchCode());
			page.getQuery().put("branchCodes",branchList.toArray(new String[0]));
			sqlId = selectAllByBranch;
		}
		Page<TmAclUser> p = queryForPageList(sqlId, page.getQuery(), page);
		return p;
	}
	
	@Override
	public List<TmAclUser> selectByRoleId(int roleId){
		
		Query query = new Query();
		query.put("roleId", roleId);
		query.put("org", OrganizationContextHolder.getOrg());//modify by H.N 20171221
		List<String> branchList = contextUtil.getOwningBranchMap(OrganizationContextHolder.getBranchCode());
		query.put("branchCodes",branchList.toArray(new String[0]));
		List<TmAclUser> p = queryForList(selectByRoleId, query);
		return p;
	}

	@Override
	public List<String> getUserResourceAuthTypeByUserNo(String userNo) {
		String sqlId = NS + ".selectUserResourceAuthType";
		Query query = new Query();
		query.put("userNo", userNo);
		List<String> q =  getSqlSession().selectList(sqlId, query);
		return q;
	}
	
	/**
	 * modify by H.N 20171221:查询不再此角色下的用户列表
	 */
	@Override
	public Page<TmAclUser> queryPageWithOutRoleI(Page<TmAclUser> page, int roleId){
		if(null==page.getQuery()){
			page.setQuery(new Query());
		}
		page.getQuery().put("roleId", roleId);
		page.getQuery().put("org", OrganizationContextHolder.getOrg());
		
		if(page.getQuery().get("byBranchFlag") != null){
			List<String> branchList = contextUtil.getOwningBranchMap(OrganizationContextHolder.getBranchCode());
			page.getQuery().put("branchCodes",branchList.toArray(new String[0]));
		}
		Page<TmAclUser> p = queryForPageList(selectWithOutRoleId, page.getQuery(), page);
		return p;
	}
	
}
