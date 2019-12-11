package com.jjb.acl.access.facade;

import java.util.ArrayList;
import java.util.List;

import com.jjb.acl.access.realm.AuthDaoExRealm.Principal;
import com.jjb.acl.infrastructure.TmAclResource;
import com.jjb.acl.infrastructure.TmAclRole;
import com.jjb.acl.infrastructure.TmAclUser;

public final class UserInfoHolder {
	
	private UserInfoHolder () {
		
	}
	
	private static  UserInfo userInfo = new UserInfo ();
	
	/**
	 * 获取当前登录人员的机构号
	 * @return
	 */
	public static String getOrg () {
		
		return userInfo.getOrg();
	}
	
	/**
	 * 获取当前登录人员的工号
	 * @return
	 */
	public static String getUserNo () {
		
		return userInfo.getUserNo();
	}
	
	/**
	 * 获取当前登录人员的信息
	 * @return
	 */
	public static TmAclUser getUser () {
		
		return userInfo.getUser();
	}
	
	/**
	 * 通过用户ID获取用户信息
	 * @param userId
	 * @return
	 */
	public static TmAclUser getUserByUserId(String userId) {
		
		return userInfo.getUserByUserId(userId);
	}
	
	public static String entryptPassword(String plainPassword) {
		
		return userInfo.entryptPassword(plainPassword);
	}
	
	public static boolean checkPermission (String permission) {
		
		return userInfo.checkPermission(permission);
	}
	
	public static TmAclUser getUserByLoginName(String orgId,String loginName) {
		
		return userInfo.getUserByLoginName(orgId,loginName);
	}
	
	public static List<TmAclResource> getUserMenus(String org,String userNo) {
		
		return  userInfo.getUserMenus(org, userNo);
		
	}
	
	public static List<TmAclRole> getRolesList(String userNo, String org) {
		
		return userInfo.getRolesList(userNo, org);
	} 
	
	public static boolean validatePassword(String plainPassword, String password) {
        
		return userInfo.validatePassword(plainPassword, password);
    }

	public static Principal getPrincipal(){
		return userInfo.getPrincipal();
	}
	
	/**
	 * 获取有权限操作的机构号
	 * @return
	 */
	public static List<String> getBranchIds(){
		 Principal principal = userInfo.getPrincipal();
		 if(principal!=null){
			 return principal.getBranchIds();
		 }else{
			 return new ArrayList<String>();
		 }
	}
}
