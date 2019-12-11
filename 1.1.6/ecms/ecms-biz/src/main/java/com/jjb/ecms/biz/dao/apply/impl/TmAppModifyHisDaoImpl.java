package com.jjb.ecms.biz.dao.apply.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.apply.TmAppModifyHisDao;
import com.jjb.ecms.infrastructure.TmAppModifyHis;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;

/**
  * @Description: 保存历史记录dao实现类
  * @author JYData-R&D-Big Star
  * @date 2016年9月2日 下午7:18:36
  * @version V1.0
 */
@Repository("tmAppModifyHisDao")
public class TmAppModifyHisDaoImpl extends AbstractBaseDao<TmAppModifyHis> implements TmAppModifyHisDao{
	public static final String  selectAll ="com.jjb.ecms.infrastructure.mapping.TmAppModifyHisMapper.selectAll";
	/**
	 * 获取历史信息列表
	 */
	@Override
	public List<TmAppModifyHis> getTmAppModifyHisList(String appNo) {
		TmAppModifyHis tmh = new TmAppModifyHis();
		tmh.setAppNo(appNo);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("_SORT_NAME", "id");
		queryMap.put("_SORT_ORDER", "asc");
		List<TmAppModifyHis> list = queryForList(tmh, queryMap);
		return list;
	}

	/*
	 * 保存历史信息
	 */
	@Override
	public void saveTmAppModifyHis(TmAppModifyHis TmAppModifyHis) {
		
		save(TmAppModifyHis);
		
	}

	/* 查看修改历史信息
	 */
	@Override
	public Page<TmAppModifyHis> getModifyHistoryInfoPage(
			Page<TmAppModifyHis> page, String appNo) {
			if(null == page.getQuery()){
				page.setQuery(new Query());
			}
			//加上页面查询的条件
			page.getQuery().put("appNo", appNo);
			
			Page<TmAppModifyHis> p = queryForPageList(selectAll, page.getQuery(), page);
			return p;
	}

}
