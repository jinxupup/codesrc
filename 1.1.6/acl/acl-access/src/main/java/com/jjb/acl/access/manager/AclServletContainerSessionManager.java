package com.jjb.acl.access.manager;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.apache.shiro.web.util.WebUtils;

import com.jjb.acl.access.realm.AuthDaoExRealm.Principal;
import com.jjb.unicorn.facility.kits.StrKit;

public class AclServletContainerSessionManager extends
		ServletContainerSessionManager {
	
	public Session getSession(SessionKey key) throws SessionException {
        if (!WebUtils.isHttp(key)) {
            String msg = "SessionKey must be an HTTP compatible implementation.";
            throw new IllegalArgumentException(msg);
        }

        HttpServletRequest request = WebUtils.getHttpRequest(key);
        Session session = null;
        
        //extends shiro
        ServletContext context = request.getServletContext().getContext("/acl-test");
        if (context != null) {
	    	String jsessionId = getSessionIdFromCookie(request.getCookies());
	    	if (StrKit.notBlank(jsessionId)) {
	    		HttpSession obj = (HttpSession) context.getAttribute(jsessionId);
	    		Principal principal = null;
	        	if (obj != null) {
	        		try {
	        			principal = new Principal ((String) obj.getAttribute("loginName"),(String) obj.getAttribute("userId"));
	        		} catch (IllegalStateException ie) {
	        			return session;
	        		}
	        		principal.setOrgId((String) obj.getAttribute("orgId"));
	        		SimplePrincipalCollection simplePrincipal = new SimplePrincipalCollection (principal,"com.jjb.acl.access.realm.UnicornDaoExRealm_0");
	        		obj.setAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY", simplePrincipal);
	        		
	        		return createSession(obj, request.getRemoteHost());
	        	}
	    	}
        }

        HttpSession httpSession = request.getSession(false);
        
        if (httpSession != null) {
            session = createSession(httpSession, request.getRemoteHost());
        }

        return session;
    }
	
	private String getSessionIdFromCookie (Cookie []cookies) {
		if (cookies == null) {
			return null;
		}
		
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if(cookie.getName().equalsIgnoreCase("JSESSIONID")){ //获取键 
				return cookie.getValue().toString();    //获取值 
			}
		}
		
		return null;
	}

}
