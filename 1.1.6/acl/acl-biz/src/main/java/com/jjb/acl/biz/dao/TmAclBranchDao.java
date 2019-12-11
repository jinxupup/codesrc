package com.jjb.acl.biz.dao;

import java.util.List;

import com.jjb.acl.infrastructure.TmAclBranch;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

public interface TmAclBranchDao extends BaseDao<TmAclBranch>{

	Page<TmAclBranch> queryPage(Page<TmAclBranch> page);

	List<String> queryCodes();

	List<TmAclBranch> getAllBranch();

	/**
	 * 返回机构路径左侧匹配
	 * @param parentPathLeft
	 * @return
	 */
	List<TmAclBranch> selectLeftLikePath(String pathLeft);
	
}