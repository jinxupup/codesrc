package com.jjb.acl.access.manager;

import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.HttpServletSession;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.apache.shiro.web.util.WebUtils;

public class AclDefaultWebSecurityManager extends DefaultWebSecurityManager {

    protected SubjectContext resolveSession(SubjectContext context) {
        if (context.resolveSession() != null) {
//            log.debug("Context already contains a session.  Returning.");
            return context;
        }
        try {
            //Context couldn't resolve it directly, let's see if we can since we have direct access to 
            //the session manager:
            Session session = resolveContextSession(context);
            if (session != null) {
                context.setSession(session);
            }
        } catch (InvalidSessionException e) {
//            log.debug("Resolved SubjectContext context session is invalid.  Ignoring and creating an anonymous " +
//                    "(session-less) Subject instance.", e);
        }
        
        //session共享
        if (WebUtils.isWeb(context)) {
            Serializable sessionId = context.getSessionId();
            HttpServletRequest a = null;
            ServletRequest request = WebUtils.getRequest(context);
            ServletResponse response = WebUtils.getResponse(context);
            WebSessionKey sessionKey = new WebSessionKey(sessionId, request, response);
            HttpSession httpSession = ((HttpServletRequest)request).getSession();
            
            HttpServletSession session = new HttpServletSession (httpSession,"");
          
            context.setSession(session);
        }
//        getSessionManager().getSession(key)
//        if (context instanceof DefaultWebSubjectContext) {
//        	DefaultWebSubjectContext temp = (DefaultWebSubjectContext) context;
//        	ServletContext appContext = temp.getServletRequest().getServletContext();
////        	String sessionId= (String) appContext.getContext("").getAttribute("sharedSessionId");
//        	
//        	SimpleSession session = new SimpleSession ();
//        	session.setId("fwer2341werwqrq312r32rqwr432");
//        	context.setSession(session);
//        }
        return context;
    }
}
