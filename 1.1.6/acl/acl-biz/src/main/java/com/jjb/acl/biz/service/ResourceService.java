package com.jjb.acl.biz.service;

import java.util.List;

import com.jjb.acl.infrastructure.TmAclResource;
import com.jjb.unicorn.facility.model.Page;

/**
 * 
 * @author BIG.D.W.K
 *
 */

public interface ResourceService {
	Page<TmAclResource> getPage(Page<TmAclResource> page);

	TmAclResource getTmAclResource(String sysType,String resourceCode);
	
	void saveTmAclResource(TmAclResource TmAclResource);
	
	void editTmAclResource(TmAclResource TmAclResource);
	
	void deleteTmAclResource(String resourceCode,String sysType);
	
	void deleteBatchTmAclResource(List<String> ids,List<String> sysType);

	List<TmAclResource> getListBySysType(String sysType);
}
