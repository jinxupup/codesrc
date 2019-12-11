package com.jjb.acl.access.web.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.acl.access.filter.AclFormAuthenticationFilter;
import com.jjb.acl.access.realm.AuthDaoExRealm.Principal;
import com.jjb.acl.access.service.AccessDictService;
import com.jjb.acl.access.service.AccessResourceService;
import com.jjb.acl.access.service.AuthenticateService;
import com.jjb.acl.facility.model.ResourceTreeFace;
import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.acl.infrastructure.TmAclResource;
import com.jjb.acl.infrastructure.TmAclUser;
import com.jjb.unicorn.facility.context.AppHolder;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.kits.StrKit;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Tree;

/**
 * 用户登录.
 */
@Controller
public class LoginController  {

	private Logger logger = LoggerFactory.getLogger(getClass());
	//页面编码格式
	public static final String CHAR_SET = "UTF-8";
	//cookies-name
	//public static final String loginName = "LOGINED";
	//private String strlogin = null;

	private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final int DEFAULT_VALID_DAYS = 90;//add by H.N 20171128:缺省[密码有效期时间]
	private static final int DEFAULT_DUE_DAYS = 3;//add by H.N 20171128:缺省[密码过期前提醒时间]
	@Autowired
	private AuthenticateService authenticateService;
	@Autowired
	private AccessResourceService accessResourceService;
	//@Autowired
	//private SessionDAO sessionDao;
	//private AppHolder appholder = AppHolder.getInstance();
	
	@Autowired
	private AccessDictService accessDictService;
//	private TmAclDictDao tmAclDictDao;//add by H.N 20171128:取得字典中的[密码有效期时间]、[密码过期前提醒时间]
	
	/**
	 * 
	 * 登录静态页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/loginftl")
	public String loginftl(HttpServletRequest request, HttpServletResponse response, Model model){
		
		try {
    		String message = request.getParameter("message");
    		if(StrKit.notBlank(message)){
    			if(StrKit.notBlank(AppHolder.getURIEncoding())){
    				message = new String(message.getBytes(AppHolder.getURIEncoding()),CHAR_SET);
    			}else{
    				message = new String(message.getBytes("ISO-8859-1"),CHAR_SET);
    			}
    		}
    		request.setAttribute("message", message);
    		//配置是否提供谷歌浏览器下载
//    		List<TmAclDict> listTmAclDict = accessDictService.getByTypeAndValue("FileUploadConfig","Y");
//    		if(!CollectionUtils.isEmpty(listTmAclDict)){
//    			TmAclDict dict = listTmAclDict.get(0);
//    			if(dict != null && StringUtils.isNotBlank(dict.getRemark())){
//    				request.setAttribute("ftpFileUploadUrl", dict.getRemark().trim());
//    			}
//    		}
    		
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return "/loginftl.ftl";
	}
	
    /**
     * 登录
     */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {

        if (logger.isDebugEnabled()) {
            //logger.debug("login, active session size:" + sessionDao.getActiveSessions().size());
        }
        
        // 如果已登录，再次访问主页，则退出原账号。不允许刷新Index
        /*if ("true".equals(appholder.getConfig("notAllowRefreshIndex"))) {
        	Cookie logined = CookieUtil.getCookie(request, loginName);
            if(logined != null)
            	 strlogin = logined.getValue();
            if (StringUtils.isBlank(strlogin) || "false".equals(strlogin)) {
                CookieUtil.setCookie(response, loginName, "true");
            } else if ("true".equals(strlogin)) {
                CookieUtil.setCookie(response, loginName, "false");
                // 调用退出登录api
            }
        }*/
        
        return "/login.ftl";
    }

	@RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {

        if (logger.isDebugEnabled()) {
            //logger.debug("login, active session size:" + sessionDao.getActiveSessions().size());
        }
        
        // 如果已登录，再次访问主页，则退出原账号。不允许刷新Index
        /*if ("true".equals(appholder.getConfig("notAllowRefreshIndex"))) {
        	Cookie logined = CookieUtil.getCookie(request, loginName);
            if(logined != null)
            	 strlogin = logined.getValue();
            if (StringUtils.isBlank(strlogin) || "false".equals(strlogin)) {
                CookieUtil.setCookie(response, loginName, "true");
            } else if ("true".equals(strlogin)) {
                CookieUtil.setCookie(response, loginName, "false");
                // 调用退出登录api
            }
        }*/

        return "forward:" + "/loginftl.ftl";
    }


    /**
     * 登录失败，真正登录的POST请求由Filter完成
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model) {
        Principal principal = getPrincipal();

        /** 增加判断，若用户的状态为停用状态，则不再调用跳转管理首页的逻辑 modify by H.N 20171221 */
        if(null == request.getAttribute("userStatus") || !request.getAttribute("userStatus").equals("FALSE")){
            // 如果已经登录，则跳转到管理首页
            if (principal != null) {
            	setUserMenus(model);
            	// 判断是否是初次登录
            	boolean IsFirst = IsFirstLogin();
            	if (IsFirst) {
            		model.addAttribute("firstLogin", true);
            	} else {
            		model.addAttribute("firstLogin", false);
            	}
                return  "/main.ftl";
            }
        }

        String message = (String) request.getAttribute(AclFormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);
        /** 增加判断，若用户的状态为停用状态，则直接调用登录失败的方法并返回登录状态为false modify by H.N 20171221 */
        
        if ((null == message || message.length() == 0) || message.equals("null")) {
            message = "用户或密码错误,请重试.";
        }

        if (logger.isDebugEnabled()) {
            //logger.debug("login fail, active session size:" + sessionDao.getActiveSessions().size() + " message: " + message);
        }
//
        // 非授权异常，登录失败，验证码加1。
//        if (!UnauthorizedException.class.getName().equals(exception)) {
//            model.addAttribute("isValidateCodeLogin", UserUtil.isValidateCodeLogin(username, true, false));
//        }
        return "/login.ftl";
    }
    
    public  Principal getPrincipal() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Principal principal = (Principal) subject.getPrincipal();
            if (principal != null) {
                return principal;
            }
        } catch (UnavailableSecurityManagerException e) {

        } catch (InvalidSessionException e) {

        }
        return null;
    }


    /**
     * 登录成功，进入管理首页
     */
	@RequiresPermissions("user")
    @RequestMapping(value = "/main")
    public String main(HttpServletRequest request, HttpServletResponse response, Model model) {
        if (logger.isDebugEnabled()) {
            //logger.debug("show index, active session size:" + sessionDao.getActiveSessions().size());
        }
        
        /////////////// Cookie校验  不允许刷新Index/////////////
        /*if ("true".equals(appholder.getConfig("notAllowRefreshIndex"))) {
            Cookie logined = CookieUtil.getCookie(request, loginName);
            if(logined != null)
            	 strlogin = logined.getValue();
            if (StringUtils.isBlank(strlogin) || "false".equals(strlogin)) {
                CookieUtil.setCookie(response, loginName, "true");
            } else if ("true".equals(strlogin)) {
                CookieUtil.setCookie(response, loginName, "false");
                // 调用退出登录api
            }
        }*/
        ///////////////// Cookie校验  不允许刷新Index /////////////
        
    	// 设置用户可见菜单项
    	setUserMenus(model);
    	// 判断是否是初次登录
    	boolean IsFirst = IsFirstLogin();
    	if (IsFirst) {
    		model.addAttribute("firstLogin", true);
    	} else {
    		model.addAttribute("firstLogin", false);
    	}
    	
    	// 判断是否需要提醒密码即将过期 modify by H.N 20171127
		try {
	    	TmAclUser user = authenticateService.queryUser(OrganizationContextHolder.getOrg(), 
	  				OrganizationContextHolder.getUserNo());
	    	if(null != user){
	    		
	    		if(null == user.getTextObject6() || "".equals(user.getTextObject6())){//如果系统中该用户的密码没有设置密码登录日期，则设置为当前系统日期
	    			user.setTextObject6(df.format(new Date()));
	    			authenticateService.update(user);
	    		} else {
	    			Date d1 = df.parse(user.getTextObject6());//密码最近一次更新的时间
					
					Calendar c = Calendar.getInstance();
					c.setTime(d1);
					List<TmAclDict> listTmAclDict = accessDictService.getByType("PASSWORD_DATE");
					
					String valid_days = "";
					String due_days = "";
					if(!listTmAclDict.isEmpty()){
						TmAclDict tmAclDict = listTmAclDict.get(0);
						valid_days = tmAclDict.getValue();
						due_days = tmAclDict.getValue2();
						c.add(Calendar.DATE, StringUtils.isEmpty(valid_days) ? 
								DEFAULT_VALID_DAYS : Integer.parseInt(valid_days));//过期时间:默认密码有效期为90天
					} else {
						c.add(Calendar.DATE, DEFAULT_VALID_DAYS);//过期时间:默认密码有效期为90天
					}
					
					Date dueDate = c.getTime();

					Date d2 = df.parse(df.format(new Date()));//系统当前时间
					long daysBetween = (dueDate.getTime() - d2.getTime() + 1000000) / (3600 * 24 * 1000);

					if (daysBetween <= (StringUtils.isEmpty(due_days) ? 
							DEFAULT_DUE_DAYS : Integer.parseInt(due_days))) {
			    		model.addAttribute("overduewarn", true);
			    		if (daysBetween <= 0) {// 判断是否由于密码过期强制修改而跳转到修改密码页面强制用户修改密码
			        		model.addAttribute("overdue", true);
			        		model.addAttribute("overduewarn", false);
			        	} else {
			        		model.addAttribute("overdue", false);
			        	}
			    	} else {
			    		model.addAttribute("overduewarn", false);
			    	}
	    		}
	    	}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return  "/main.ftl";
    }
    
    //修改密码页面
  	@RequestMapping(value="/passwordpage",method = RequestMethod.GET)                 // 有两个地方会调用到：flag-1、topbar中的设置密码;flag-2、初始密码修改
  	public String passwordpage(HttpServletRequest request, Model model){
  		String flag = (String)request.getParameter("flag");
  		model.addAttribute("flag", flag);
  		model.addAttribute("userNo",OrganizationContextHolder.getUserNo());
  		
  		return "/password.ftl";
  	}
  	
  	@ResponseBody
	@RequestMapping("/editpassword")
	public Json editpassword(String oldPassword,String newPassword){
		Json j = Json.newSuccess();
		
		try{
			authenticateService.editPassword(oldPassword, newPassword);
			// 修改密码成功，更新用户status字段为N-正常
			authenticateService.updateStatus("N");
			
		}catch(Exception e){
			j.setCode("GLP999");
			j.setFail(e.getMessage());
		}
		
		return j;
	}
  	
  	/**
  	 * 设置用户菜单
  	 * @param model
  	 */
  	private void setUserMenus(Model model){
  		List<TmAclResource> list = accessResourceService.getUserMenus();
    	Tree<TmAclResource> userMenuTree = Tree.listToTree(list, new ResourceTreeFace());
    	
    	model.addAttribute("userMenuTree", userMenuTree);
    	model.addAttribute("userNo",OrganizationContextHolder.getUserNo());
  	}
  	
  	/**
  	 * 首次登录判断
  	 * @return
  	 */
  	private boolean IsFirstLogin() {
  		TmAclUser user = authenticateService.queryUser(OrganizationContextHolder.getOrg(), 
  				OrganizationContextHolder.getUserNo());
  		if("A".equals(user.getStatus())) {
  			return true;
  		}
  		return false;
  	}
}
