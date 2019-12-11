package com.jjb.acl.access.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jjb.acl.infrastructure.TmAclUser;
import com.jjb.unicorn.base.service.BaseService;

/** 
 * @author  ltm
 * @date 创建时间：2016年9月26日 下午6:48:52 
 * @version   
 */
@Service
public class AccessUserService extends BaseService<TmAclUser>{

	private final String selectUsers = "acl.access.TmAclUser.selectByResourceCode";
	private final String getTheUser = "acl.access.TmAclUser.selectByOrgAndUserNo";
	
	/**
	 * 根据ResourceCode获取该资源下用户
	 * @return
	 */
	public List<TmAclUser> getUserMenus(String[] resourceCodes){
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("resourceCodes", resourceCodes);
		
		List<TmAclUser> list = super.queryForList(selectUsers, param);
		return list;
	}
	
	/**
	 * 根据userNo和org获取用户
	 * @param userNo
	 * @param org
	 * @return
	 */
	public TmAclUser getUserByOrgAndUserNo(String userNo, String org){
		TmAclUser param = new TmAclUser();
		TmAclUser resultTm = new TmAclUser();
		param.setOrg(org);
		param.setUserNo(userNo);
		
		List<TmAclUser> list = super.queryForList(getTheUser, param);
		if(!list.isEmpty()) 
			resultTm = list.get(0);
		
		return resultTm;
	}
}
