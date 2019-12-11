package com.jjb.acl.biz.dao;

import java.util.List;

import com.jjb.acl.infrastructure.TmAclResource;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

public interface TmAclResourceDao extends BaseDao<TmAclResource>{

	Page<TmAclResource> queryPage(Page<TmAclResource> page);

	/**根据系统类型获取资源列表
	 * @param sysType
	 * @return
	 */
	List<TmAclResource> getListBySysType(String sysType);
	
}