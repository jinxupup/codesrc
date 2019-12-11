package com.jjb.acl.biz.dao;

import com.jjb.acl.infrastructure.TmAclParam;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

public interface TmAclParamDao extends BaseDao<TmAclParam>{

	Page<TmAclParam> queryPage(Page<TmAclParam> page);
	
	
	/**
	 * 根据类型及代码获取用户对象
	 * @param type code
	 * @return
	 */
	TmAclParam getParamByType(String type,String code);
	
}