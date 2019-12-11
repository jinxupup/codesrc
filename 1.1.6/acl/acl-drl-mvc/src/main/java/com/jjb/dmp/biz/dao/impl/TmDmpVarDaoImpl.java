package com.jjb.dmp.biz.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jjb.dmp.biz.dao.TmDmpVarDao;
import com.jjb.dmp.infrastructure.TmDmpVar;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;

@Repository("tmDmpVarDao")
public class TmDmpVarDaoImpl extends AbstractBaseDao<TmDmpVar> implements TmDmpVarDao{

	private static final String selectByStClass = "com.jjb.dmp.mapping.DmpVarMapper.selectByStClass";
	
	/**
	 * 查询指定stClass下的变量
	 * @param stClass
	 * @param isInput  是否过滤查询输入变量
	 * @param isOutput 是否过滤查询输出变量
	 * @return
	 */
	@Override
	public List<TmDmpVar> selectByStClass(String stClass,boolean isInput,boolean isOutput){
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stClass", stClass);
		if(isInput==true){
			params.put("isInput", "Y");
		}
		if(isOutput==true){
			params.put("isOutput", "Y");
		}
		
		return queryForList(selectByStClass,params);
	}
	
	@Override
	public Page<TmDmpVar> queryPage(Page<TmDmpVar> page) {
		
		TmDmpVar tmDmpVar = new TmDmpVar();
		Page<TmDmpVar> p = queryForPageList(tmDmpVar, page.getQuery(),page);
		
		return p;
	}

	@Override
	public TmDmpVar getByKey(String varType,String varCd) {
	
		TmDmpVar tmDmpVar = new TmDmpVar();
		tmDmpVar.setVarType(varType);
		tmDmpVar.setVarCd(varCd);
		
		return queryByKey(tmDmpVar);
	}

	@Override
	public List<TmDmpVar> queryByVarType(String varType) {
		TmDmpVar tmDmpVar = new TmDmpVar();
		tmDmpVar.setVarType(varType);
		return queryForList(tmDmpVar);
	}
	
}