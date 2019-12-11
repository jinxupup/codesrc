package com.jjb.acl.biz.dao;

import java.util.List;

import com.jjb.acl.infrastructure.TmAclUser;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

/**
 * 
 * @author BIG.D.W.K
 *
 */
public interface TmAclUserDao extends BaseDao<TmAclUser>{

	/**
	 * 根据用户ID获取用户对象
	 * @param page
	 * @return
	 */
	TmAclUser getUserByUserId(String userId);
	
	/**
	 * 根据登陆名获取用户对象
	 * @param userNo
	 * @return
	 */
	TmAclUser getUserByUserNo(String userNo);
	
	/**
	 * 获取用户分页数据
	 * @param page
	 * @return
	 */
	Page<TmAclUser> queryPage(Page<TmAclUser> page);

	/**
	 * 根据角色Id,查询用户列表
	 * @param list
	 * @return
	 */
	List<TmAclUser> selectByRoleId(int roleId);

	List<String> getUserResourceAuthTypeByUserNo(String userNo);
	
		
	/**
	 * 根据角色Id,查询非此角色的用户列表 modify by H.N 20171221
	 * @param list
	 * @return
	 */
	Page<TmAclUser> queryPageWithOutRoleI(Page<TmAclUser> page, int roleId);

}
