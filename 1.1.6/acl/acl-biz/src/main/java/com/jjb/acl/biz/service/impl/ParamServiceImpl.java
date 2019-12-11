package com.jjb.acl.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.biz.dao.TmAclParamDao;
import com.jjb.acl.biz.service.ParamService;
import com.jjb.acl.infrastructure.TmAclParam;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.BizException;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.BeanUtil;

@Transactional(readOnly=false)
@Service("paramService")
public class ParamServiceImpl implements ParamService {

	@Autowired
	private TmAclParamDao tmAclParamDao;
	
	@Override
	public TmAclParam getTmAclParam(Integer id){
		
		TmAclParam tmAclParam = new TmAclParam();
		tmAclParam.setId(id);
		return tmAclParamDao.queryByKey(tmAclParam);

	}

	@Override
	public Page<TmAclParam> getPage(Page<TmAclParam> page) {
		
		page.getQuery().put("_SORT_LIST", null);
		page = tmAclParamDao.queryPage(page);//.queryForPageList(t, page);//.queryPage(page);
	
		return page;
	}

	@Override
	public void saveTmAclParam(TmAclParam tmAclParam) {
		
		tmAclParam.setOrg(OrganizationContextHolder.getOrg());
		String type = tmAclParam.getType();
		String code = tmAclParam.getCode();
		TmAclParam tmAclParamdb = tmAclParamDao.getParamByType(type, code);
		if(tmAclParamdb == null){
			tmAclParamDao.save(tmAclParam); 
		}else{
			
			throw new BizException("该参数类型已存在");
		}
	}

	@Override
	public void editTmAclParam(TmAclParam tmAclParam) {
		TmAclParam tmAclParamDb = new TmAclParam();
		tmAclParamDb.setId(tmAclParam.getId());
		tmAclParamDb = tmAclParamDao.queryByKey(tmAclParamDb);
		if(tmAclParamDb == null){
			throw new BizException("找不到该编号的参数");
		}
		tmAclParam.setJpaVersion(tmAclParamDb.getJpaVersion());
		BeanUtil.merge(tmAclParamDb, tmAclParam);
		tmAclParamDao.update(tmAclParamDb);
		
		
	}

	@Override
	public void deleteTmAclParam(Integer id) {
		TmAclParam tmAclParam = new TmAclParam();
		tmAclParam.setId(id);
		tmAclParamDao.deleteByKey(tmAclParam);
	}

	@Override
	public void deleteBatchTmAclParam(List<Integer> ids) {
		for(Integer id : ids){
			deleteTmAclParam(id);
		}
	}
}
