package com.jjb.acl.biz.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.biz.dao.TmAclBranchDao;
import com.jjb.acl.biz.service.BranchService;
import com.jjb.acl.infrastructure.TmAclBranch;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.BizException;
import com.jjb.unicorn.facility.kits.StrKit;
import com.jjb.unicorn.facility.model.Page;

/**
 * 
 * @author BIG.LTM
 * modify by H.N 20171108
 *
 */
@Transactional
@Service("branchService")
public class BranchServiceImpl implements BranchService{

	@Autowired
	private TmAclBranchDao tmAclBranchDao;
//	@Autowired
//	private TmAclUserDao tmAclUserDao;
	
	@Override
	public TmAclBranch getTmAclBranch(String branchCode){
		TmAclBranch tmAclBranch = new TmAclBranch();
		tmAclBranch.setOrg(OrganizationContextHolder.getOrg());
		tmAclBranch.setBranchCode(branchCode);
		return tmAclBranchDao.queryByKey(tmAclBranch);
	}

	@Override
	public Page<TmAclBranch> getPage(Page<TmAclBranch> page) {
		
		page = tmAclBranchDao.queryPage(page);//.queryForPageList(t, page);//.queryPage(page);
	
		return page;
	}

	@Override
	@Transactional
	public void saveTmAclBranch(TmAclBranch tmAclBranch) {
		
		TmAclBranch tmAclBranchDb = tmAclBranchDao.queryByKey(tmAclBranch);
		if(tmAclBranchDb!=null){
			throw new RuntimeException("该网点已存在或已存在此代码的分支机构");
		}
		
		//add by H.N 20171108 start
		tmAclBranch.setOrg(OrganizationContextHolder.getOrg());
//		tmAclBranch.setCreateDate(new Date());
//		tmAclBranch.setCreateBy(OrganizationContextHolder.getUserNo());
		tmAclBranch.setUpdateDate(new Date());
		tmAclBranch.setUpdateBy(OrganizationContextHolder.getUserNo());
		//add by H.N 20171108 end		
		tmAclBranchDao.save(tmAclBranch);
	}

	@Override
	public void editTmAclBranch(TmAclBranch tmAclBranch) {
		
		TmAclBranch tmAclBranchDb = new TmAclBranch();
		tmAclBranchDb.setBranchCode(tmAclBranch.getBranchCode());
		tmAclBranchDb.setOrg(OrganizationContextHolder.getOrg());
		tmAclBranchDb = tmAclBranchDao.queryByKey(tmAclBranchDb);
		if(tmAclBranchDb==null){
			throw new BizException("找不到该编号的机构");
		}
		
		boolean isChangeParent = false;//是否更改了上级
		if(tmAclBranchDb.getParentCode()==null || tmAclBranch.getParentCode()==null){
			if(tmAclBranchDb.getParentCode() != tmAclBranch.getParentCode()){
				isChangeParent = true;
			}
		}else{
			if(!tmAclBranchDb.getParentCode().equals(tmAclBranch.getParentCode())){
				isChangeParent = true;
			}
		}
		//修改下级的机构路径
		if(isChangeParent){
			
			String srcParentPath = tmAclBranchDb.getParentPath() + tmAclBranchDb.getBranchCode() + "/";
			String destParentPath = tmAclBranch.getParentPath() + tmAclBranchDb.getBranchCode() + "/";
			
			List<TmAclBranch> children = tmAclBranchDao.selectLeftLikePath(srcParentPath);
			for(TmAclBranch child:children){
				if(child.getBranchCode().equals(tmAclBranch.getBranchCode()) ){
					continue;
				}
				if(child.getParentPath()==null){
					continue;
				}
				
				String childPath = child.getParentPath();
				String right = childPath.substring(srcParentPath.length());
				
				child.setParentPath(destParentPath + right);
				tmAclBranchDao.update(child);
			}
		}
		
//		BeanUtil.merge(tmAclBranchDb, tmAclBranch);
		//add by H.N 20171108 start
		//tmAclBranch.setBranchSequence(tmAclBranchDb.getBranchSequence());
		tmAclBranch.setOrg(OrganizationContextHolder.getOrg());
		tmAclBranch.setUpdateDate(new Date());
		tmAclBranch.setUpdateBy(OrganizationContextHolder.getUserNo());
		//add by H.N 20171108 end
		tmAclBranchDao.update(tmAclBranch);
	}

	@Override
	public void deleteTmAclBranch(String branchCode) {
		TmAclBranch tmAclBranch = new TmAclBranch();
		tmAclBranch.setBranchCode(branchCode);
		tmAclBranch.setOrg(OrganizationContextHolder.getOrg());
		
		TmAclBranch tmAclBranchChild = new TmAclBranch();
		tmAclBranchChild.setParentCode(branchCode);
		List<TmAclBranch> children = tmAclBranchDao.queryForList(tmAclBranchChild);
		if(StrKit.notBlankList(children)){
			throw new BizException("存在下级机构，不能删除");
		}
		
		tmAclBranchDao.deleteByKey(tmAclBranch);
	}

	@Override
	public void deleteBatchTmAclBranch(List<String> ids) {
		for(String id : ids){
			deleteTmAclBranch(id);
		}
	}
	
	@Override
	public List<TmAclBranch> getAllBranch() {
		List<TmAclBranch> list=tmAclBranchDao.getAllBranch();
		return list;
	}
	
	/**
	 * 获取所有的网点参数:从APS合并入
	 */
	@Override
	public Page<TmAclBranch> getPage(Page<TmAclBranch> page, TmAclBranch tmAclBranch) {
		// TODO Auto-generated method stub
		page = tmAclBranchDao.queryForPageList(tmAclBranch, page);
		return page;
	}

	/**
	 * 根据网点机构编号删除网点参数 :从APS合并入
	 */
	@Override
	public void deleteTmAclBranchByBranchCode(String branchCode) {
		TmAclBranch TmAclBranch = new TmAclBranch();
		TmAclBranch.setBranchCode(branchCode);
		List<TmAclBranch> list = tmAclBranchDao.queryForList(TmAclBranch);
		if(null != list && !list.isEmpty()){
			for (int i = 0; i < list.size(); i++) {
				tmAclBranchDao.deleteByKey(list.get(i));
			}
		}
	}
	
//	/*
//	 * 根据独立发卡标识查找网点机构
//	 */
//	@Override
//	public List<TmAclBranch> getBranchByHairpin(String authHairpin) {
//		TmAclBranch tmAclBranch = new TmAclBranch();
//		tmAclBranch.setIfEnableHairpin(authHairpin);
//		tmAclBranch.setOrg(OrganizationContextHolder.getOrg());
//		tmAclBranch.setBranchCode(OrganizationContextHolder.getBranchCode());
//		List<TmAclBranch> list=tmAclBranchDao.queryForList(tmAclBranch);
//		return list;
//	}
}