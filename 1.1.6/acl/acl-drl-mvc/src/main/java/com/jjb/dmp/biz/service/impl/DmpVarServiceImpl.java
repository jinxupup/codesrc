package com.jjb.dmp.biz.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.dmp.biz.dao.TmDmpVarDao;
import com.jjb.dmp.biz.service.DmpVarService;
import com.jjb.dmp.infrastructure.TmDmpVar;
import com.jjb.unicorn.facility.exception.BizException;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.BeanUtil;

/**
 * 
 * @author BIG.D.W.K
 *
 */
@Transactional
@Service("dmpVarService")
public class DmpVarServiceImpl implements DmpVarService{

	@Autowired
	private TmDmpVarDao tmDmpVarDao;
	
	@Override
	public TmDmpVar getTmDmpVar(String varType,String varCd){
		return tmDmpVarDao.getByKey(varType,varCd);
	}

	@Override
	public Page<TmDmpVar> getPage(Page<TmDmpVar> page) {
		page = tmDmpVarDao.queryPage(page);
		return page;
	}

	@Override
	public List<TmDmpVar> getVarList(TmDmpVar tmDmpVar) {
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("_SORT_NAME","varCd");
		params.put("_SORT_ORDER","ASC");
		
		return tmDmpVarDao.queryForList(tmDmpVar, params);
	}
	
	@Override
	public List<TmDmpVar> getVarList(String varType) {
		return tmDmpVarDao.queryByVarType(varType);
	}
	
	@Override
	@Transactional
	public void saveTmDmpVar(TmDmpVar tmDmpVar) {
		TmDmpVar tmDmpVarDb = tmDmpVarDao.queryByKey(tmDmpVar);
		if(tmDmpVarDb!=null){
			throw new RuntimeException("已存在此代码的决策变量");
		}
		tmDmpVarDao.save(tmDmpVar);
	}

	@Override
	public void editTmDmpVar(TmDmpVar tmDmpVar) {
		
		TmDmpVar tmDmpVarDb = tmDmpVarDao.getByKey(tmDmpVar.getVarType(),tmDmpVar.getVarCd());
		if(tmDmpVarDb==null){
			throw new BizException("找不到该代码的决策变量");
		}
		tmDmpVar.setJpaVersion(tmDmpVarDb.getJpaVersion());
		BeanUtil.merge(tmDmpVarDb, tmDmpVar);
		
		tmDmpVarDao.update(tmDmpVarDb);
	}


	@Override
	public void deleteTmDmpVar(String varType, String varCd) {
		TmDmpVar tmDmpVar = new TmDmpVar();
		tmDmpVar.setVarType(varType);
		tmDmpVar.setVarCd(varCd);
		tmDmpVarDao.deleteByKey(tmDmpVar);
	}

}