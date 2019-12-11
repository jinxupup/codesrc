package com.jjb.acl.access.service;import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jjb.acl.infrastructure.TmAclRole;
import com.jjb.unicorn.base.service.BaseService;

/** 
 * @author  ltm
 * @date 创建时间：2016年9月28日 上午11:32:12 
 * @version   
 */
@Service
public class AccessRoleService extends BaseService<TmAclRole>{
	
	private final String  selectRoleIds = "acl.access.TmAclRole.selectRoleIds";
	
	public List<TmAclRole> getRolesList(String userNo, String org){
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userNo", userNo);
			param.put("org", org);
			List<TmAclRole> list = super.queryForList(selectRoleIds, param);
			return list;
		}

}
