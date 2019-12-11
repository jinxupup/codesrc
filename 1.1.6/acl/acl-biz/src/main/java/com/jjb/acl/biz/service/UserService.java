package com.jjb.acl.biz.service;

import java.util.List;

import com.jjb.acl.infrastructure.TmAclUser;
import com.jjb.unicorn.facility.model.Page;

/**
 * 
 * @author BIG.D.W.K
 *
 */
public interface UserService {

	/**
	 * 根据登陆名获取用户对象
	 * @param userNo
	 * @return
	 */
	TmAclUser getUserByUserNo(String userNo);

	/**
	 * 根据userId获取用户对象
	 * @param userNo
	 * @return
	 */
	TmAclUser getUserByUserId(Integer userId);

	/**
	 * 获取用户分页信息
	 * @param page
	 * @return
	 */
	Page<TmAclUser> getUserPage(Page<TmAclUser> page);
	
	
	Page<TmAclUser> getPage(Page<TmAclUser> page);

	TmAclUser getTmAclUser(int userId);
	
	List<TmAclUser> selectByRoleId(int roleId);
	
	Page<TmAclUser> selectWithOutRoleId(Page<TmAclUser> page, int roleId);//modify by H.N 20171221
	
	void saveTmAclUser(TmAclUser tmAclUser);
	
	void editTmAclUser(TmAclUser tmAclUser);
	
	void deleteTmAclUser(int userId);
	
	void deleteBatchTmAclUser(List<Integer> ids);

	/**
	 * 编辑密码
	 * @param userNo
	 * @param plainPassword
	 */
	void editPassword(String userNo, String plainPassword,String resetFlag);

	/**
	 * 重置密码
	 * @param userNo
	 * @param plainPassword
	 */
	void resetPassword(String userNo);

	/**
	 * 判断密码
	 * @param userNo
	 * @param oldPassord
	 * @return
	 */
	boolean checkPassword(String userNo, String oldPassord);

	List<String> getUserResourceAuthTypeList(String userNo);
	
}
