package com.jjb.acl.access.facade;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;

import com.jjb.acl.access.realm.AuthDaoExRealm.Principal;
import com.jjb.acl.access.service.AccessResourceService;
import com.jjb.acl.access.service.AccessRoleService;
import com.jjb.acl.access.service.AuthenticateService;
import com.jjb.acl.infrastructure.TmAclResource;
import com.jjb.acl.infrastructure.TmAclRole;
import com.jjb.acl.infrastructure.TmAclUser;



public class UserInfo {
	
	public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    public static final int SALT_SIZE = 8;
    
	private AuthenticateService authenticateService = ContextHolder.getBean("authenticateService");
	
	private AccessResourceService accessResourceService = ContextHolder.getBean("accessResourceService");
	
	private AccessRoleService accessRoleService = ContextHolder.getBean("accessRoleService");
	
	/**
	 * 获取机构号
	 * @return
	 */
	public String getOrg () {
		TmAclUser user = getUser ();
		if (user == null) {
			return null;
		}
		
		return user.getOrg();
	}
	
	public String getUserNo () {
		TmAclUser user = getUser ();
		if (user == null) {
			return null;
		}
		
		return user.getUserNo();
	}
	
	/**
     * 获取当前登录用户
     *
     * @return 取不到返回 new User()
     */
	public TmAclUser getUser () {
		Principal principal = getPrincipal();
        if (principal != null) {
        	TmAclUser user = getUserByUserId(principal.getId());
            if (user != null) {
                user.setOrg(principal.getOrgId());
                return user;
            }
            return new TmAclUser();
        }
        
        // 如果没有登录，则返回实例化空的User对象。
        return new TmAclUser();
	}
	
	public Principal getPrincipal() {
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
	
	public TmAclUser getUserByUserId(String userId) {
		TmAclUser temp = new TmAclUser ();
		temp.setUserId(Integer.valueOf(userId));
		
		TmAclUser user = authenticateService.queryByKey(temp);
        if (user == null) {
            return null;
        }
        
        return user;
    }
	
	public String entryptPassword(String plainPassword) {
        byte[] salt = DigestUtil.generateSalt(SALT_SIZE);
        byte[] hashPassword = DigestUtil.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        return EncodeUtil.encodeHex(salt) + EncodeUtil.encodeHex(hashPassword);
    }
	
	
	public boolean checkPermission (String permission) {
		Subject subject = SecurityUtils.getSubject();

        if (subject == null) {
            return false;
        }
        
        return subject.isPermitted(permission);
	}
	
	public TmAclUser getUserByLoginName(String orgId,String loginName) {
		
		return authenticateService.queryUser(orgId,loginName);
	}
	
	public List<TmAclResource> getUserMenus(String org,String userNo) {
		
		return  accessResourceService.getUserMenus(org, userNo);
		
	}
	
	public List<TmAclRole> getRolesList(String userNo, String org) {
		
		return accessRoleService.getRolesList(userNo, org);
	}
	
	
	public boolean validatePassword(String plainPassword, String password) {
        byte[] salt = EncodeUtil.decodeHex(password.substring(0, 16));
        byte[] hashPassword = DigestUtil.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        return password.equals(EncodeUtil.encodeHex(salt) + EncodeUtil.encodeHex(hashPassword));
    }

}
