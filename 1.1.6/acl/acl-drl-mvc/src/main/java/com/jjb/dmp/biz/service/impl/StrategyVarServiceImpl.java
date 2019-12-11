package com.jjb.dmp.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.dmp.biz.dao.TmDmpStrategyVarDao;
import com.jjb.dmp.biz.service.StrategyVarService;
import com.jjb.dmp.infrastructure.TmDmpStrategyVar;
import com.jjb.unicorn.facility.exception.BizException;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.BeanUtil;

/**
 * 
 * @author BIG.D.W.K
 *
 */
@Transactional
@Service("strategyVarService")
public class StrategyVarServiceImpl implements StrategyVarService {

	@Autowired
	private TmDmpStrategyVarDao tmDmpStrategyVarDao;
	
	@Override
	public TmDmpStrategyVar getTmDmpStrategyVar(String stClass,String varType,String varCd){
		TmDmpStrategyVar tmDmpStrategyVar = new TmDmpStrategyVar();
		tmDmpStrategyVar.setStClass(stClass);
		tmDmpStrategyVar.setVarType(varType);
		tmDmpStrategyVar.setVarCd(varCd);
		return tmDmpStrategyVarDao.queryByKey(tmDmpStrategyVar);
	}

	@Override
	public Page<TmDmpStrategyVar> getPage(Page<TmDmpStrategyVar> page) {
		page = tmDmpStrategyVarDao.queryPage(page);
		return page;
	}

	@Override
	public List<TmDmpStrategyVar> getVarList(String stClass) {
		return tmDmpStrategyVarDao.queryByStClass(stClass);
	}
	
	@Override
	@Transactional
	public void saveTmDmpStrategyVar(TmDmpStrategyVar tmDmpStrategyVar) {
		TmDmpStrategyVar tmDmpStrategyVarDb = tmDmpStrategyVarDao.queryByKey(tmDmpStrategyVar);
		if(tmDmpStrategyVarDb!=null){
			throw new RuntimeException("已存在此代码的决策变量");
		}
		tmDmpStrategyVarDao.save(tmDmpStrategyVar);
	}

	@Override
	public void editTmDmpStrategyVar(TmDmpStrategyVar tmDmpStrategyVar) {
		
		TmDmpStrategyVar tmDmpStrategyVarDb = tmDmpStrategyVarDao.getByKey(tmDmpStrategyVar.getStClass(),tmDmpStrategyVar.getVarType(),tmDmpStrategyVar.getVarCd());
		if(tmDmpStrategyVarDb==null){
			throw new BizException("找不到该代码的决策变量");
		}
		tmDmpStrategyVar.setJpaVersion(tmDmpStrategyVarDb.getJpaVersion());
		BeanUtil.merge(tmDmpStrategyVarDb, tmDmpStrategyVar);
		
		tmDmpStrategyVarDao.update(tmDmpStrategyVarDb);
	}


	@Override
	public void deleteTmDmpStrategyVar(TmDmpStrategyVar tmDmpStrategyVar) {
		tmDmpStrategyVarDao.deleteByKey(tmDmpStrategyVar);
	}

	@Override
	public void addTmDmpStrategyVars(String stClass,List<TmDmpStrategyVar> varList) {
		
		for(TmDmpStrategyVar var:varList){
			var.setStClass(stClass);
			try{
				tmDmpStrategyVarDao.save(var);
			}catch(Exception e){
				TmDmpStrategyVar tmDmpStrategyVarDb = tmDmpStrategyVarDao.queryByKey(var);
				if(tmDmpStrategyVarDb!=null){
					throw new BizException("决策类型中已存在该变量，变量代码："+ var.getVarCd() + "插入失败");
				}else{
					throw new BizException("变量代码："+ var.getVarCd() + "插入失败");
				}
			}
		}
		
	}

}