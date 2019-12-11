package com.jjb.acl.access.authc;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 用户名、密码 token
 * @author LSW
 *
 */
public class AclUsernamePasswordToken extends UsernamePasswordToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9165780192609164277L;
	
	private String orgId;
	
	private String orgName;
	
	private String loginName;
	
	
	public AclUsernamePasswordToken () {
		super ();
	}
	
	
	public AclUsernamePasswordToken (String orgId,String loginName,char []password,boolean rememberMe,String host ) {
		super(loginName,password,rememberMe,host);
		
		this.orgId = orgId;
		this.loginName = loginName;
	}
	

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

}
