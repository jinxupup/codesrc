package com.jjb.acl.access.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjb.acl.infrastructure.TmAclResource;
import com.jjb.acl.infrastructure.TmAclUser;
import com.jjb.unicorn.base.service.BaseService;
import com.jjb.unicorn.facility.context.AppHolder;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;

/**
 * 
 * @author BIG.D.W.K
 *
 */
@Service
public class AccessResourceService extends BaseService<TmAclResource>{

	@Autowired
	AuthenticateService authenticateService;
	
	private final String ACL_SYS_NAME = "ACL";
	
	private final String selectUserResources = "acl.access.TmAclResourceMapper.selectUserResources";
	private final String selectResourceCodes = "acl.access.TmAclResourceMapper.selectResourceCodes";
	/**
	 * 获取当前用户目录和菜单
	 * @return
	 */
	public List<TmAclResource> getUserMenus(){
		
		TmAclUser tmAclUserId = authenticateService.queryUser(OrganizationContextHolder.getOrg(), OrganizationContextHolder.getUserNo());
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("userId", tmAclUserId.getUserId());
		param.put("types", new String[]{"P","M"});
		
		param.put("sysType", getSysTypes());
		
		List<TmAclResource> list = super.queryForList(selectUserResources, param);
		return list;
	}
	
	/**
	 * 获取当前用户目录和菜单
	 * @return
	 */
	public List<TmAclResource> getUserMenus(String org,String userNo){
		
		TmAclUser tmAclUserId = authenticateService.queryUser(org, userNo);
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("userId", tmAclUserId.getUserId());
		param.put("types", new String[]{"P","M","B"});
		param.put("org", org);
		param.put("sysType", getSysTypes());
		
		List<TmAclResource> list = super.queryForList(selectUserResources, param);
		return list;
	}
	
	/**
	 * 根据roleId获取 该角色下有操作权限的资源
	 * @param roleId
	 * @return
	 */
	public List<TmAclResource> getResourceCodes(Integer roleId){
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roleId", roleId);
		List<TmAclResource> list = super.queryForList(selectResourceCodes, param);
		return list;
	}
	
	private String[] getSysTypes(){
		String sysTypes [] = new String[]{};
		String sysTypeStr = AppHolder.getSysType();
		if(sysTypeStr!=null && !"".equals(sysTypeStr.trim())) {
			sysTypes = sysTypeStr.split(",");
		}
		
		if(AppHolder.getDepAclMvc()){
			if(sysTypes==null || sysTypes.length==0) {
				return new String[]{ACL_SYS_NAME};
			}
			List<String> list = new ArrayList<>(Arrays.asList(sysTypes));
			list.add(ACL_SYS_NAME);
			return list.toArray(new String[0]);
		}else{
			return sysTypes;
		}
	}
}
