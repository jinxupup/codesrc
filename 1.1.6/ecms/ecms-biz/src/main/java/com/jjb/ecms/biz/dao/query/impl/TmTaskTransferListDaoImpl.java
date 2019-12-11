package com.jjb.ecms.biz.dao.query.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.query.TmTaskTransferListDao;
import com.jjb.ecms.facility.dto.ApplyTaskListDto;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;

/**
 * @description 任务列表实现类
 * @author hn
 * @date 2016年9月1日16:52:43
 */

@Repository("tmTaskTransferListDao")
public class TmTaskTransferListDaoImpl extends AbstractBaseDao<ApplyTaskListDto> implements TmTaskTransferListDao {

	public static final String  selectTransferAll = "com.jjb.ecms.biz.TmTaskList.selectTransferAll";//查询已分配的任务
	
	@Autowired
	private CacheContext cacheContext;

	/*
	 * 查询已分配的任务
	 */
	@Override
	public Page<ApplyTaskListDto> queryTaskTransferPage(
			Page<ApplyTaskListDto> page) {
		// 查询未分配任务
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		Page<ApplyTaskListDto> p = queryForPageList(selectTransferAll, page.getQuery(), page);
		return p;
	}
	
}
