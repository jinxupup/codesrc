package com.jjb.dmp.biz.dao;

import java.util.List;

import com.jjb.dmp.infrastructure.TmDmpVar;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

public interface TmDmpVarDao extends BaseDao<TmDmpVar>{

	Page<TmDmpVar> queryPage(Page<TmDmpVar> page);
	
	TmDmpVar getByKey(String varType, String varCd);

	List<TmDmpVar> queryByVarType(String varType);

	/**
	 * 查询指定stClass下的变量
	 * @param stClass
	 * @param isInput  是否过滤查询输入变量
	 * @param isOutput 是否过滤查询输出变量
	 * @return
	 */
	List<TmDmpVar> selectByStClass(String stClass, boolean isInput,boolean isOutput);

}
