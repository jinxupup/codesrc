package com.jjb.acl.access.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jjb.unicorn.base.service.BaseService;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 
 * @author BIG.D.W.K
 *
 */
@Service
public class AccessTableService extends BaseService {

	//根据传入的实体类，获取列表数据
	public List getTable(Object entity){
		String className = entity.getClass().getSimpleName();
		List list = new ArrayList<>();
		if(StringUtils.equals(className, "TmAclDict")) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("_SORT_NAME", "sort,code");
			map.put("_SORT_ORDER", "asc");
			list = super.queryForList(entity,map);
		}else {
			list = super.queryForList(entity);
		}
		return list;
	}

	
}
