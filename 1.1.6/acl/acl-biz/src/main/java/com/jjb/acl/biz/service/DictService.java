package com.jjb.acl.biz.service;

import java.util.List;

import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.unicorn.facility.model.Page;

public interface DictService {

	Page<TmAclDict> getPage(Page<TmAclDict> page);

	TmAclDict getTmAclDict(Integer id);
	
	void saveTmAclDict(TmAclDict tmAclDict);
	
	void editTmAclDict(TmAclDict tmAclDict);
	
	void deleteTmAclDict(Integer id);
	
	void deleteBatchTmAclDict(List<Integer> ids);

	TmAclDict queryTmAclDict(String type,String code);

	public List<TmAclDict> getByType(String type);

	List<TmAclDict> selectGroupType();
}
