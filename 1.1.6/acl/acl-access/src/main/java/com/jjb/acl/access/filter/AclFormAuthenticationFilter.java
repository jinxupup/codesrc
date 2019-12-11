package com.jjb.acl.access.filter;

import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjb.acl.access.authc.AclUsernamePasswordToken;
import com.jjb.acl.access.realm.AuthDaoExRealm.Principal;
import com.jjb.acl.access.service.AccessParamService;
import com.jjb.acl.access.service.AccessUserService;

/**
 * 自定义验证类
 * @author LSW
 *
 */
@Service
public class AclFormAuthenticationFilter extends FormAuthenticationFilter {
	private Logger logger = LoggerFactory.getLogger(getClass());
	public static final String DEFAULT_ORGID_PARAM = "orgId";	
	public static final String DEFAULT_LOGINNAME_PARAM = "loginName";	
	public static final String DEFAULT_MESSAGE_PARAM = "message";
	
	private String usernameParam = DEFAULT_USERNAME_PARAM;	       //用户名
	private String passwordParam = DEFAULT_PASSWORD_PARAM;	       //密码
	private String rememberMeParam = DEFAULT_REMEMBER_ME_PARAM;	   //记住我
	private String orgIdParam = DEFAULT_ORGID_PARAM;	           //机构号
	private String loginNameParam = DEFAULT_LOGINNAME_PARAM;       //登录名
	private String messageParam = DEFAULT_MESSAGE_PARAM;           //异常信息
	
	@Autowired
	private AccessParamService accessParamService;
	
	@Autowired
	private AccessUserService accessUserService;//增加用户状态判断 modify by H.N 20171221
	
	public AclFormAuthenticationFilter () {
		
		setLoginUrl(DEFAULT_LOGIN_URL);
	}
	
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        boolean rememberMe = isRememberMe(request);   //记住我
        //String orgId = getOrgId(request);
        String orgId = accessParamService.getDefaultOrg();

        String loginName = getLoginName(request);
        String password = getPassword(request);
        if (password == null) {
        	password = "";
        }
        String host = getRemoteAddr((HttpServletRequest)request);
        
        return new AclUsernamePasswordToken(orgId,loginName,password.toCharArray(),rememberMe,host);
    }

	public String getUsernameParam() {
		
		return usernameParam;
	}

	public void setUsernameParam(String usernameParam) {
		
		this.usernameParam = usernameParam;
	}

	public String getPasswordParam() {
		
		return passwordParam;
	}

	public void setPasswordParam(String passwordParam) {
		this.passwordParam = passwordParam;
	}

	public String getRememberMeParam() {
		return rememberMeParam;
	}

	public void setRememberMeParam(String rememberMeParam) {
		this.rememberMeParam = rememberMeParam;
	}

	public String getOrgIdParam() {
		return orgIdParam;
	}

	public void setOrgIdParam(String orgIdParam) {
		this.orgIdParam = orgIdParam;
	}

	public String getLoginNameParam() {
		return loginNameParam;
	}

	public void setLoginNameParam(String loginNameParam) {
		this.loginNameParam = loginNameParam;
	}
	
	public String getMessageParam() {
        return messageParam;
    }
	
	protected String getOrgId (ServletRequest request) {
		
		return WebUtils.getCleanParam(request, getOrgIdParam());
	}
	
	protected String getLoginName (ServletRequest request) {
		
		return WebUtils.getCleanParam(request, getLoginNameParam());
	}
	
	protected String getPassword (ServletRequest request) {
		
		return WebUtils.getCleanParam(request, getPasswordParam());
	}
	
	protected String getRemoteAddr (HttpServletRequest request) {
		String remoteAddr = request.getRemoteAddr();
		
		return remoteAddr == null ? "" : remoteAddr;
	}
	
	@Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
    }

    /**
     * 登录失败
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        String className = e.getClass().getName();
        String message = "";
        
        if (IncorrectCredentialsException.class.getName().equals(className) || UnknownAccountException.class.getName().equals(className)) {
            message = "Username or password is wrong ...!";
        } else if (e.getMessage() != null && startsWith(e.getMessage(), "msg:")) {
            message = replace(e.getMessage(), "msg:", "");
        } else {
            message = "System is unusual, please try agin!";
			logger.error("登录失败", e);

		}
        request.setAttribute(getFailureKeyAttribute(), className);
        request.setAttribute(getMessageParam(), message);
        return true;
    }
    
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
            ServletRequest request, ServletResponse response) throws Exception {
    	/** 增加判断，若用户的状态为停用状态，则直接调用登录失败的方法并返回登录状态为false modify by H.N 20171221 */
    	Principal principal = (Principal) subject.getPrincipal();
    	
    	String userStatus = accessUserService.getUserByOrgAndUserNo(principal.getLoginName(), principal.getOrgId()).getStatus();
    	
    	if(null == userStatus || userStatus.trim().equals("") || userStatus.equals("B") ){//空或者B则为无效
            String message = "The abnormal state of the user,please contact your administrator";
            request.setAttribute("userStatus", "FALSE");
            request.setAttribute(getFailureKeyAttribute(), "UnicornFormAuthenticationFilter");
            request.setAttribute(getMessageParam(), message);
            return true;
    	}
    	/** 增加判断，若用户的状态为停用状态，则直接调用登录失败的方法并返回登录状态为false modify by H.N 20171221 */
    	
    	Session session = subject.getSession();
    	Serializable sessionId = session.getId();
    	
    	HttpServletRequest httpServlet = WebUtils.toHttp(request);
//    	Principal principal = (Principal) subject.getPrincipal();
    	 
    	HttpSession httpSession = httpServlet.getSession();
    	httpSession.setAttribute("orgId", principal.getOrgId());
    	httpSession.setAttribute("userId", principal.getId());
    	httpSession.setAttribute("loginName", principal.getLoginName());
    	httpSession.setAttribute("name", principal.getName());
       
    	request.getServletContext().setAttribute(sessionId.toString(), httpSession);
    	issueSuccessRedirect(request, response);
    	//we handled the success redirect directly, prevent the chain from continuing:
    	return false;
	}
    
    
    private boolean startsWith(final String s, final String prefix) {
        return startsWith(s, prefix, false);
    }
    
    
    private boolean startsWith(final String s, final String prefix, final boolean ignoreCase) {
        if (s == null || prefix == null) {
            return s == null && prefix == null;
        }
        if (prefix.length() > s.length()) {
            return false;
        }
        return s.toString().regionMatches(ignoreCase, 0, prefix.toString(), 0, prefix.length());
    }
    
    public String replace(String text, String repl, String with) {
        return replace(text, repl, with, -1);
    }
    
    public String replace(String text, String repl, String with, int max) {
        if ((text == null) || (repl == null) || (with == null) || (repl.length() == 0) || (max == 0)) {
            return text;
        }

        StringBuilder buf = new StringBuilder(text.length());
        int start = 0;
        int end = 0;

        while ((end = text.indexOf(repl, start)) != -1) {
            buf.append(text.substring(start, end)).append(with);
            start = end + repl.length();

            if (--max == 0) {
                break;
            }
        }

        buf.append(text.substring(start));
        return buf.toString();
    }
}
