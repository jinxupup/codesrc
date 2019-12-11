package com.jjb.acl.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.biz.dao.TmAclResourceDao;
import com.jjb.acl.biz.service.ResourceService;
import com.jjb.acl.infrastructure.TmAclResource;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.BizException;
import com.jjb.unicorn.facility.kits.StrKit;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.BeanUtil;

/**
 * 
 * @author BIG.D.W.K
 *
 */

@Transactional(readOnly=false)
@Service("resourceService")
public class ResourceServiceImpl implements ResourceService{

	@Autowired
	private TmAclResourceDao tmAclResourceDao;
	
	@Override
	public TmAclResource getTmAclResource(String sysType,String resourceCode){
		
		TmAclResource tmAclResource = new TmAclResource();
		tmAclResource.setOrg(OrganizationContextHolder.getOrg());
		tmAclResource.setResourceCode(resourceCode);
		tmAclResource.setSysType(sysType);
		return tmAclResourceDao.queryByKey(tmAclResource);

	}

	@Override
	public Page<TmAclResource> getPage(Page<TmAclResource> page) {
		
		page = tmAclResourceDao.queryPage(page);
	
		return page;
	}

	@Override
	public void saveTmAclResource(TmAclResource tmAclResource) {
		TmAclResource tmAclResource1 = new TmAclResource();
		tmAclResource.setOrg(OrganizationContextHolder.getOrg());
		String resourceCode = tmAclResource.getResourceCode();
		tmAclResource1 = tmAclResourceDao.queryByKey(tmAclResource);
		if(tmAclResource1 ==null){
			tmAclResourceDao.save(tmAclResource);
		}else{
			throw new BizException("该资源代码"+resourceCode+"已存在");
		}
	}

	@Override
	public void editTmAclResource(TmAclResource tmAclResource) {
		TmAclResource tmAclResourceDb = new TmAclResource();
		tmAclResourceDb.setSysType(tmAclResource.getSysType());
		tmAclResourceDb.setResourceCode(tmAclResource.getResourceCode());
		tmAclResourceDb = tmAclResourceDao.queryByKey(tmAclResourceDb);
		if(tmAclResourceDb == null){
			throw new BizException("找不到该编号的资源");
		}
		tmAclResource.setJpaVersion(tmAclResourceDb.getJpaVersion());
		BeanUtil.merge(tmAclResourceDb, tmAclResource);
		tmAclResourceDao.update(tmAclResourceDb);
	}

	@Override
	public void deleteTmAclResource(String resourceCode,String sysType) {
		TmAclResource tmAclResource = new TmAclResource();
		tmAclResource.setOrg(OrganizationContextHolder.getOrg());
		tmAclResource.setResourceCode(resourceCode);
		tmAclResource.setSysType(sysType);
		tmAclResource = tmAclResourceDao.queryByKey(tmAclResource);
		if(tmAclResource==null){
			throw new BizException("资源"+resourceCode+"不存在");
		}
		if(StrKit.notBlank(tmAclResource.getParentResourceCode())){
			TmAclResource child = new TmAclResource();
			child.setOrg(OrganizationContextHolder.getOrg());
			child.setParentResourceCode(resourceCode);
			List<TmAclResource> children = tmAclResourceDao.queryForList(child);
			if(StrKit.notBlankList(children)){
				throw new BizException("资源"+resourceCode+"存在下级资源，不允许删除");
			}
		}
		
		tmAclResource.setResourceCode(resourceCode);
		tmAclResourceDao.deleteByKey(tmAclResource);
	}

	@Override
	public void deleteBatchTmAclResource(List<String> ids,List<String> sysType) {
		for(String id : ids){
			//deleteTmAclResource(id);
		}
	}

	@Override
	public List<TmAclResource> getListBySysType(String sysType) {
		List<TmAclResource> list = tmAclResourceDao.getListBySysType(sysType);
		return list;
	}
		
}