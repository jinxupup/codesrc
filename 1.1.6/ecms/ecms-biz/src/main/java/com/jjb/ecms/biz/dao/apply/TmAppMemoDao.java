package com.jjb.ecms.biz.dao.apply;


import java.util.List;

import com.jjb.ecms.infrastructure.TmAppMemo;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * 申请录入信息申请备注备忘表
 * @Description: TODO
 * @author JYData-R&D-HN
 * @date 2016年10月18日 下午4:26:19
 * @version V1.0
 */
public interface TmAppMemoDao extends BaseDao<TmAppMemo>{
	
	/**
	 * 根据申请件编号appNo获取申请备注备忘表的信息
	 * @param TmAppMemo
	 * @return List
	 */
	public List<TmAppMemo> getTmAppMemoByAppNo(TmAppMemo appMemo); 
	
	/**
	 * 保存申请备注备忘表信息
	 * @param appMemos
	 */
	public void saveTmAppMemo(List<TmAppMemo> appMemos) ;
	/**
	 * 保存申请备注备忘表信息
	 * @param tmAppMain
	 */
	public void saveTmAppMemo(TmAppMemo appMemo);
	

	/**
	 * 删除申请备注备忘表信息
	 * @param appMemo
	 */
	public void deleteTmAppMemo(TmAppMemo appMemo);
}