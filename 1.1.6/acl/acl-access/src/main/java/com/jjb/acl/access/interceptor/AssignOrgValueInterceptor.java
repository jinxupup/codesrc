package com.jjb.acl.access.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jjb.acl.access.facade.UserInfoHolder;
import com.jjb.acl.access.realm.AuthDaoExRealm.Principal;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;

public class AssignOrgValueInterceptor extends HandlerInterceptorAdapter {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		Principal principal = UserInfoHolder.getPrincipal();
		if(principal!=null){
			OrganizationContextHolder.setOrg(principal.getOrgId());
			OrganizationContextHolder.setUserNo(principal.getLoginName());
			OrganizationContextHolder.setSid(String.valueOf(principal.getId()));
			OrganizationContextHolder.setBranchCode(principal.getBranchCode());
			OrganizationContextHolder.setUserName(principal.getName());
			OrganizationContextHolder.setCellPhone(principal.getCellPhone());
			OrganizationContextHolder.setRoleList(principal.getRoleMap());
			OrganizationContextHolder.setResourceList(principal.getResourceMap());
		}
		return true;
	}
}
