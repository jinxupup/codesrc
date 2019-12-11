package com.jjb.acl.access.filter;

import org.apache.shiro.web.filter.authc.LogoutFilter;

//@Service
public class AclLogoutFilter extends LogoutFilter {

//	@Autowired
//	private UnicornSessionListener sessionListener;
//	
//	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
//		
//		HttpServletRequest httpRequest = (HttpServletRequest)request;
//		// 将当前用户的session从userNameMap中移出
//		sessionListener.removeByLoginName(httpRequest.getSession());
//		
//		super.preHandle(request, response);
//		
//		return false;
//	}
}
