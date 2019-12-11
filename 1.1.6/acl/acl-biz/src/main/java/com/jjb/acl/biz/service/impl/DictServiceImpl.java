package com.jjb.acl.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.biz.dao.TmAclDictDao;
import com.jjb.acl.biz.service.DictService;
import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.BizException;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.BeanUtil;

@Transactional(readOnly=false)
@Service("dictService")
public class DictServiceImpl implements DictService{

	@Autowired
	private TmAclDictDao tmAclDictDao;
	
	@Override
	public TmAclDict getTmAclDict(Integer id){
		
		TmAclDict tmAclDict = new TmAclDict();
		tmAclDict.setId(id);
		return tmAclDictDao.queryByKey(tmAclDict);

	}

	@Override
	public Page<TmAclDict> getPage(Page<TmAclDict> page) {
		
		page.getQuery().put("_SORT_LIST", null);
		page = tmAclDictDao.queryPage(page);//.queryForPageList(t, page);//.queryPage(page);
	
		return page;
	}

	@Override
	public void saveTmAclDict(TmAclDict tmAclDict) {
		TmAclDict tmAclDictN = new TmAclDict();
		tmAclDict.setOrg(OrganizationContextHolder.getOrg());
		tmAclDictN.setType(tmAclDict.getType());
		tmAclDictN.setCode(tmAclDict.getCode());
		List<TmAclDict> list = tmAclDictDao.queryForList(tmAclDictN);
		if(list != null && list.size()>=1){
			throw new BizException("该资字典代码"+tmAclDict.getCode()+"已存在");
		}
		tmAclDictDao.save(tmAclDict);
	}

	@Override
	public void editTmAclDict(TmAclDict tmAclDict) {
		TmAclDict tmAclDictDb = new TmAclDict();
		tmAclDictDb.setId(tmAclDict.getId());
		tmAclDictDb = tmAclDictDao.queryByKey(tmAclDictDb);
		if(tmAclDictDb == null){
			throw new BizException("找不到该编号的数据字典");
		}
		tmAclDict.setJpaVersion(tmAclDictDb.getJpaVersion());
		BeanUtil.merge(tmAclDictDb, tmAclDict);
		tmAclDictDao.update(tmAclDictDb);
	}

	@Override
	public void deleteTmAclDict(Integer id) {
		TmAclDict tmAclDict = new TmAclDict();
		tmAclDict.setId(id);
		tmAclDictDao.deleteByKey(tmAclDict);
	}

	@Override
	public void deleteBatchTmAclDict(List<Integer> ids) {
		for(Integer id : ids){
			deleteTmAclDict(id);
		}
	}

	@Override
	public TmAclDict queryTmAclDict(String type,String code) {
		return tmAclDictDao.getDictByType(type,code);
	}
	
	@Override
	public List<TmAclDict> getByType(String type){
		
		TmAclDict tmAclDict = new TmAclDict();
		tmAclDict.setType(type);
		return tmAclDictDao.queryForList(tmAclDict);
	}
	
	@Override
	public List<TmAclDict> selectGroupType(){
		return tmAclDictDao.selectGroupType();
	}
}
