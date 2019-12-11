package com.jjb.acl.access.realm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjb.acl.access.authc.AclUsernamePasswordToken;
import com.jjb.acl.access.facade.UserInfoHolder;
import com.jjb.acl.access.service.AccessBranchService;
import com.jjb.acl.access.service.AuthenticateService;
import com.jjb.acl.infrastructure.TmAclResource;
import com.jjb.acl.infrastructure.TmAclRole;
import com.jjb.acl.infrastructure.TmAclUser;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;


/**
 * 数据源的域对象
 * @author LSW
 *
 */
@Service
public class AuthDaoExRealm extends AuthorizingRealm {
	
	public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    public static final int SALT_SIZE = 8;
    
    private Logger logger = LoggerFactory.getLogger(AuthDaoExRealm.class);
	
	@Autowired
	private AuthenticateService authenticateService;
	@Autowired
	private AccessBranchService accessBranchService;
	
	/**
	 * 获取授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) getAvailablePrincipal(principals);
        
        String org = principal.getOrgId();
        String loginName = principal.getLoginName();

        TmAclUser user = UserInfoHolder.getUserByLoginName(org, loginName);
        if (user != null) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            //获取可访问的应用菜单
            HashMap<String, String> resourceMap = OrganizationContextHolder.getResourceList();
            for (String resourceCode : resourceMap.keySet()) {
                if (resourceCode != null && resourceCode.length() >0) {
                    info.addStringPermission(resourceCode);            
                }
            }
            // 添加用户权限
            info.addStringPermission("user");
//          // 添加用户角色信息
//          for (Role role : user.getRoleList()) {
//              info.addRole(role.getRoleNo());
//          }
//          // 更新登录IP和时间
//          getAuthorizingService().updateUserLoginInfo(user);
//          // 记录登录日志
//          //LogUtils.saveLog(Servlets.getRequest(), "系统登录");
            return info;
        } else {
            return null;
        }

	}

	/**
	 * 获取认证信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authToken) throws AuthenticationException {
		AclUsernamePasswordToken token = (AclUsernamePasswordToken) authToken;
		
		String orgId = token.getOrgId();
		String loginName = token.getLoginName();
		logger.info("用户名：{}, 机构号：{}",loginName,orgId);
		TmAclUser user = authenticateService.queryUser(orgId, loginName);
		if (user == null) {
			return null;
		}
		
		if ("C".equals(user.getStatus())) {
			throw new AuthenticationException ("msg:该账号已经锁定");
		}
	
		byte[] salt = decodeHex(user.getPassword().substring(0, 16));
		
		Principal principal = new Principal(user);
		principal.setBranchIds(accessBranchService.getAuthBranchIds(user.getBranchCode()));		
		HashMap<String, String> rolesMap = new HashMap<String, String>();
		List<TmAclRole> rolesList = UserInfoHolder.getRolesList(user.getUserNo(), user.getOrg());
		if (rolesList != null && rolesList.size() > 0) {
			for (TmAclRole tmAclRole : rolesList) {
				rolesMap.put(String.valueOf(tmAclRole.getRoleId()), tmAclRole.getRoleName());
			}
		}
		principal.setRoleMap(rolesMap);
		
		HashMap<String, String> resourceMap = new HashMap<String, String>();
		List<TmAclResource> userMenus = UserInfoHolder.getUserMenus(user.getOrg(), user.getUserNo());
		if (userMenus != null && userMenus.size() > 0) {
			for (TmAclResource tmAclResource : userMenus) {
				resourceMap.put(tmAclResource.getResourceCode(), tmAclResource.getResourceName());
			}
		}
		principal.setResourceMap(resourceMap);
		
		AuthenticationInfo authinfo = new SimpleAuthenticationInfo (principal,user.getPassword().substring(16),ByteSource.Util.bytes(salt),getName());
		return authinfo;
	}
	
	/**
     * 授权用户信息
     */
    public static class Principal implements Serializable {
        private static final long serialVersionUID = 1L;

        private String orgId;    // 机构号
        private String id;        // 用户编号
        private String loginName; // 登录名
        private String name;      // 姓名
        private String branchCode; //分行号
        private String cellPhone; //电话号码
        
        private HashMap<String, String> roleMap;//角色清单
        private HashMap<String, String> resourceMap;//资源清单
        //可操作的分行机构
    	private List<String> branchIds = new ArrayList<String>();
    	
    	public HashMap<String, String> getRoleMap() {
    		return roleMap;
    	}
    	
    	public void setRoleMap(HashMap<String, String> roleMap) {
    		this.roleMap = roleMap;
    	}

        public HashMap<String, String> getResourceMap() {
			return resourceMap;
		}

		public void setResourceMap(HashMap<String, String> resourceMap) {
			this.resourceMap = resourceMap;
		}

		public List<String> getBranchIds() {
			return branchIds;
		}

		public void setBranchIds(List<String> branchIds) {
			this.branchIds = branchIds;
		}

		public Principal(TmAclUser user) {
            this.id = user.getUserId().toString();
            this.orgId = user.getOrg();
            this.loginName = user.getUserNo();
            this.name = user.getUserName();
            this.branchCode = user.getBranchCode();
            this.cellPhone = user.getCellphome();
        }
        
        public Principal (String loginName,String id) {
        	this.loginName = loginName;
        	this.id = id;
        }

        
        public String getId() {
            return id;
        }

        public String getLoginName() {
            return loginName;
        }

        public String getName() {
            return name;
        }

        /**
         * 获取SESSIONID
         */
        public String getSessionid() {
            try {
                return null;
            } catch (Exception e) {
                return "";
            }
        }

        @Override
        public String toString() {
            return id;
        }


		public String getOrgId() {
			return orgId;
		}


		public void setOrgId(String orgId) {
			this.orgId = orgId;
		}
		
		public String getBranchCode() {
			return branchCode;
		}

		public void setBranchCode(String branchCode) {
			this.branchCode = branchCode;
		}
		
		public String getCellPhone() {
			return cellPhone;
		}
		
		public void setCellPhone(String cellPhone) {
			this.cellPhone = cellPhone;
		}

    }
    
    @Override
    protected void checkPermission(Permission permission, AuthorizationInfo info) {
        
        super.checkPermission(permission, info);
    }

    @Override
    protected boolean[] isPermitted(List<Permission> permissions, AuthorizationInfo info) {
        
        return super.isPermitted(permissions, info);
    }

    @Override
    public boolean isPermitted(PrincipalCollection principals, Permission permission) {
        
        return super.isPermitted(principals, permission);
    }

    @Override
    protected boolean isPermittedAll(Collection<Permission> permissions, AuthorizationInfo info) {
        
        return super.isPermittedAll(permissions, info);
    }
    
    private  byte[] decodeHex(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException e) {
            throw new RuntimeException (e);
        }
    }
    
    /**
     * 设定密码校验的Hash算法与迭代次数
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(HASH_ALGORITHM);
        matcher.setHashIterations(HASH_INTERATIONS);
        setCredentialsMatcher(matcher);
    }

}
