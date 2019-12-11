package com.jjb.acl.biz.service;

import java.util.List;

import com.jjb.acl.infrastructure.TmAclBranch;
import com.jjb.unicorn.facility.model.Page;

/**
 * 
 * @author BIG.LTM
 * modify by H.N 20171108
 *
 */

public interface BranchService {
	Page<TmAclBranch> getPage(Page<TmAclBranch> page);

	TmAclBranch getTmAclBranch(String branchCode);
//	/*
//	 * 查询一条参数 
//	 */
//	TmAclBranch getTmAclBranch(String branchCode);
	
	
	void saveTmAclBranch(TmAclBranch TmAclBranch);
//	/*
//	 * 保存系统参数
//	 */
//	void saveTmAclBranch(TmAclBranch tmAclBranch);
	
	
	void editTmAclBranch(TmAclBranch tmAclBranch);
//	/*
//	 * 编辑参数 
//	 */
//	void editTmAclBranch(TmAclBranch tmAclBranch);
	
	
	void deleteTmAclBranch(String branchCode);
//	/*
//	 * 删除参数 
//	 */
//	void deleteTmAclBranch(String branchCode);
	
	void deleteBatchTmAclBranch(List<String> ids);

	List<TmAclBranch> getAllBranch();
	
	/*
	 * 获取所有的网点参数 
	 */
	Page<TmAclBranch> getPage(Page<TmAclBranch> page, TmAclBranch tmAclBranch);
	
	/**
	 * 根据网点机构编号删除网点参数 
	 */
	public void deleteTmAclBranchByBranchCode(String branchCode);
	
//	/*
//	 * 根据独立发卡标识获取网点信息
//	 */
//
//	public List<TmAclBranch> getBranchByHairpin(String authHairpin);

}
