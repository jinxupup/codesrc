/**
 * 
 */
package com.jjb.ecms.biz.dao.common;

import java.util.List;

import com.jjb.ecms.infrastructure.TmDitDic;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

/**
 * @Description: TODO
 * @author JYData-R&D-BigK.K
 * @date 2016年9月21日 上午9:02:40
 * @version V1.0  
 */
public interface TmDitDicDao extends BaseDao<TmDitDic>{

	/**查询复核项,必输项
	 * @param dicType
	 * @param org
	 * @return
	 */
	List<TmDitDic> tmAppTaskClimitList(String dicType, String org);
	

	List<TmDitDic> queryForList(TmDitDic tmDitDic);
	
	/**
	 * ditdic表根据类型分类
	 * @param tmDitDic
	 * @return
	 */
	List<TmDitDic> selectGroupType(TmDitDic tmDitDic);
	
	/*
	 * 获取所有的参数配置
	 */
	Page<TmDitDic> getPage(Page<TmDitDic> page,TmDitDic ditDic);
	/*
	 * 查找符合条件的参数 
	 */
	TmDitDic getParamByType(String dicType,String itemName);

	/**
	 * @Author smh
	 * @Description TODO  展示自动分配任务列表
	 * @Date 2018/12/20 14:26
	 */
	public Page<TmDitDic> getListPage(Page<TmDitDic> page);

}


