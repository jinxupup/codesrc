package com.jjb.ecms.biz.service.param.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.common.TmDitDicDao;
import com.jjb.ecms.biz.service.param.SysParamService;
import com.jjb.ecms.infrastructure.TmDitDic;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.model.Page;

@Service("sysParamService")
public class SysParamServiceImpl implements SysParamService {

	@Autowired
	private TmDitDicDao ditDicDao;
	@Autowired
	private CacheContext cacheContext;
	
	/*
	 * 获取所有的cas系统参数 
	 */
	@Override
	@Transactional
	public Page<TmDitDic> getPage(Page<TmDitDic> page, TmDitDic ditDic) {
		page = ditDicDao.getPage(page,ditDic);
		return page;
	}

	/*
	 * 保存系统参数
	 */
	@Override
	@Transactional
	public void saveTmDitDic(TmDitDic tmDitDic) {
		if (tmDitDic != null) {
			tmDitDic.setOrg(OrganizationContextHolder.getOrg());
			ditDicDao.save(tmDitDic); 
			sysParam();
		}
	}

	/*
	 * 查询一条参数 
	 */
	@Override
	@Transactional
	public TmDitDic getTmDitDic(int id) {
		TmDitDic tmDitDic = new TmDitDic();
		tmDitDic.setId(id);
		return ditDicDao.queryByKey(tmDitDic);
	}

	/*
	 * 编辑参数 
	 */
	@Override
	@Transactional
	public void editTmDitDic(TmDitDic tmDitDic) {
		if (tmDitDic != null) {
			tmDitDic.setOrg(OrganizationContextHolder.getOrg());
			ditDicDao.update(tmDitDic);
			sysParam();
		}
	}
	
	/*
	 * 删除参数 
	 */
	@Override
	@Transactional
	public void deleteTmDitDic(int id) {
		TmDitDic tmDitDic = new TmDitDic();
		tmDitDic.setId(id);
		ditDicDao.deleteByKey(tmDitDic);
		sysParam();
	}
	
	/**
	 * 根据条件获取参数数据
	 * @param ditDic
	 * @return
	 */
	@Override
	@Transactional
	public List<TmDitDic> getTmDitDic(TmDitDic ditDic){
		if(ditDic==null){
			return null;
		}
		return ditDicDao.queryForList(ditDic);
	}
	
	/**
	 * 刷新参数
	 */
	private void sysParam(){
		cacheContext.initApplyOnlineOnOffParamMap();
		cacheContext.initTelCheckParam();
		cacheContext.initApplyPatchBoltParamMap();
	}
	/**
	 *@Author smh
	 *@Description TODO 展示自动分配任务列表
	 *@Date 2018/12/20 14:22
	 */
	@Override
	@Transactional
	public Page<TmDitDic> getListPage(Page<TmDitDic> page) {
		return ditDicDao.getListPage(page);
	}

}
