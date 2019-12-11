package com.jjb.dmp.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.dmp.biz.dao.TmDmpStrategyFunctionDao;
import com.jjb.dmp.biz.service.StrategyFunctionService;
import com.jjb.dmp.infrastructure.TmDmpStrategyFunction;
import com.jjb.unicorn.facility.exception.BizException;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.BeanUtil;

/**
 * 
 * @author BIG.D.W.K
 *
 */
@Transactional
@Service("strategyFunctionService")
public class StrategyFunctionServiceImpl implements StrategyFunctionService {

	@Autowired
	private TmDmpStrategyFunctionDao tmDmpStrategyFunctionDao;
	
	@Override
	public TmDmpStrategyFunction getTmDmpStrategyFunction(String stClass, String funCd){
		return tmDmpStrategyFunctionDao.getByKey(stClass,funCd);
	}

	@Override
	public Page<TmDmpStrategyFunction> getPage(Page<TmDmpStrategyFunction> page) {
		page = tmDmpStrategyFunctionDao.queryPage(page);
		return page;
	}

	@Override
	public List<TmDmpStrategyFunction> getFunctionList(String stClass) {
		return tmDmpStrategyFunctionDao.queryByStClass(stClass);
	}
	
	@Override
	@Transactional
	public void saveTmDmpStrategyFunction(TmDmpStrategyFunction tmDmpStrategyFunction) {
		TmDmpStrategyFunction tmDmpStrategyFunctionDb = tmDmpStrategyFunctionDao.queryByKey(tmDmpStrategyFunction);
		if(tmDmpStrategyFunctionDb!=null){
			throw new RuntimeException("已存在此代码的自定义函数");
		}
		tmDmpStrategyFunctionDao.save(tmDmpStrategyFunction);
	}

	@Override
	public void editTmDmpStrategyFunction(TmDmpStrategyFunction tmDmpStrategyFunction) {
		
		TmDmpStrategyFunction tmDmpStrategyFunctionDb = tmDmpStrategyFunctionDao.getByKey(tmDmpStrategyFunction.getStClass(),tmDmpStrategyFunction.getFunCd());
		if(tmDmpStrategyFunctionDb==null){
			throw new BizException("找不到该代码的自定义函数");
		}
		tmDmpStrategyFunction.setJpaVersion(tmDmpStrategyFunctionDb.getJpaVersion());
		BeanUtil.merge(tmDmpStrategyFunctionDb, tmDmpStrategyFunction);
		
		tmDmpStrategyFunctionDao.update(tmDmpStrategyFunctionDb);
	}


	@Override
	public void deleteTmDmpStrategyFunction(String stClass, String funCd) {
		TmDmpStrategyFunction tmDmpStrategyFunction = new TmDmpStrategyFunction();
		tmDmpStrategyFunction.setStClass(stClass);
		tmDmpStrategyFunction.setFunCd(funCd);
		tmDmpStrategyFunctionDao.deleteByKey(tmDmpStrategyFunction);
	}

}