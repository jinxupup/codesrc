/**
 * 
 */
package com.jjb.ecms.biz.dao.common.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.common.TmDitDicDao;
import com.jjb.ecms.infrastructure.TmDitDic;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.kits.StrKit;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;

/**
 * @Description: TmDitDic
 * @author JYData-R&D-BigK.K
 * @date 2016年9月21日 上午9:02:12
 * @version V1.0  
 */
@Repository("tmDitDicDao")
public class TmDitDicDaoImpl extends AbstractBaseDao<TmDitDic> implements TmDitDicDao {

	public static final String selectListPage="com.jjb.ecms.biz.TmDitDicMapper.selectListPage";//
	public static final String selectAll="com.jjb.ecms.infrastructure.mapping.TmDitDicMapper.selectAll";//
	public static final String selectGroupType="com.jjb.ecms.biz.TmDitDicMapper.selectGroupType";//
	
	/*
	 * 获取所有的参数配置
	 */
	@Override
	public Page<TmDitDic> getPage(Page<TmDitDic> page,TmDitDic ditDic) {
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		Map<String,String> sortMap = new HashMap<String, String>();
		sortMap.put("_SORT_NAME", "dicType,formName,tabName,itemName,remark");
		sortMap.put("_SORT_ORDER", "ASC");
		page.getQuery().putAll(sortMap);
		Page<TmDitDic> p = queryForPageList(ditDic, page.getQuery(), page);
		
		return p;
	}
	/**
	 * 查找符合条件的参数 (参数类型、参数代码)
	 */
	@Override
	public TmDitDic getParamByType(String dicType,String itemName){
		
		TmDitDic tmDitDic = new TmDitDic();
		tmDitDic.setDicType(dicType);
		tmDitDic.setItemName(itemName);
		
		List<TmDitDic> list = queryForList(tmDitDic);
		if(StrKit.notBlankList(list)){
			return list.get(0);
		}
		
		return null;
	}
	/**
	 * 
	 * 传入参数为TM_DIT_DIC中的dicType字段
	 * return Map
	 */
	@Override
	public List<TmDitDic> tmAppTaskClimitList(String dicType, String org) {
	
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("dicType", dicType);
		parameter.put("org", org);
		parameter.put("_SORT_NAME", "ifUsed");
		parameter.put("_SORT_ORDER", "ASC");
		List<TmDitDic> tmAppTaskClimitList=queryForList(selectAll,parameter);
		
		return tmAppTaskClimitList;
	}
	

	public List<TmDitDic> queryForList(TmDitDic tmDitDic){
		
		return queryForList(tmDitDic,new HashMap<String, Object>());
	}
	/**
	 * ditdic表根据类型分类
	 * @param tmDitDic
	 * @return
	 */
	@Override
	public List<TmDitDic> selectGroupType(TmDitDic tmDitDic) {
		List<TmDitDic> list = null;
		if(tmDitDic != null){
			list = queryForList(selectGroupType,tmDitDic);
		}
		
		return list;
	}

	@Override
	public Page<TmDitDic> getListPage(Page<TmDitDic> page) {
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		page = queryForPageList(selectListPage, page.getQuery(), page);
		return page;
	}
	
}
