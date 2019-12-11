package com.jjb.acl.biz.service;

import java.util.List;

import com.jjb.acl.infrastructure.TmAclParam;
import com.jjb.unicorn.facility.model.Page;

public interface ParamService {

	Page<TmAclParam> getPage(Page<TmAclParam> page);

	TmAclParam getTmAclParam(Integer id);
	
	void saveTmAclParam(TmAclParam tmAclParam);
	
	void editTmAclParam(TmAclParam tmAclParam);
	
	void deleteTmAclParam(Integer id);
	
	void deleteBatchTmAclParam(List<Integer> ids);
}
