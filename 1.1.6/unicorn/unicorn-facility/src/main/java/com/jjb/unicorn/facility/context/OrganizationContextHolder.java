package com.jjb.unicorn.facility.context;

import java.util.HashMap;


/**
 * 当前机构、用户信息线程变量
 * 
 */
public final class OrganizationContextHolder {
	
	private OrganizationContextHolder () {
		
	}

	//用户机构
	private static ThreadLocal<String> org = new ThreadLocal<String>();
	
	//登陆名/用户代码
	private static ThreadLocal<String> userNo = new ThreadLocal<String>();
	
	//请求唯一ID
	private static ThreadLocal <String> sid = new ThreadLocal<String> ();
	
	//用户姓名
	private static ThreadLocal<String> userName = new ThreadLocal<String>();


	//操作员工号
	private static ThreadLocal<String> controlName = new ThreadLocal<String>();
	
	//网点
	private static ThreadLocal<String> branchCode = new ThreadLocal<String>();
	
	//电话号码
	private static ThreadLocal<String> cellPhome = new ThreadLocal<String>();
	
	//角色清单 key-角色id，value-角色中文名称
	private static ThreadLocal<HashMap<String,String>> roleList = new ThreadLocal<HashMap<String,String>>();
	
	//资源清单
	private static ThreadLocal<HashMap<String,String>> resourceList = new ThreadLocal<HashMap<String,String>>();

	
	public static String getOrg()
	{	
		return OrganizationContextHolder.org.get();
	}
	
	
	public static void setOrg(String org)
	{
		OrganizationContextHolder.org.set(org);
	}


	public static String getUserNo() {
		return OrganizationContextHolder.userNo.get();
	}

	public static void setUserNo(String userNo) {
		OrganizationContextHolder.userNo.set(userNo);
	}
	
	public static void setSid (String sid) {
		OrganizationContextHolder.sid.set(sid);
	}
	
	public static void getSid () {
		OrganizationContextHolder.sid.get();
	}
	
	public static String getUserName() {
		return OrganizationContextHolder.userName.get();
	}
	
	public static void setUserName(String userName) {
		OrganizationContextHolder.userName.set(userName);
	}

	public static String getControlName() {
		return OrganizationContextHolder.userName.get();
	}

	public static void setControlName(ThreadLocal<String> controlName) {
		OrganizationContextHolder.controlName = controlName;
	}

	public static String getBranchCode() {
		return OrganizationContextHolder.branchCode.get();
	}
	
	public static void setBranchCode(String branchCode) {
		OrganizationContextHolder.branchCode.set(branchCode);
	}
	
	public static String getCellPhone() {
		return OrganizationContextHolder.cellPhome.get();
	}
	
	public static void setCellPhone(String cellPhome) {
		OrganizationContextHolder.cellPhome.set(cellPhome);
	}
	
	public static HashMap<String,String> getRoleList() {
		return OrganizationContextHolder.roleList.get();
	}
	
	public static void setRoleList(HashMap<String,String> tmAclRole) {
		OrganizationContextHolder.roleList.set(tmAclRole);
	}
	
	public static HashMap<String,String> getResourceList() {
		return OrganizationContextHolder.resourceList.get();
	}
	
	public static void setResourceList(HashMap<String,String> tmAclResource) {
		OrganizationContextHolder.resourceList.set(tmAclResource);
	}
	
	
	
}
