package com.jjb.ecms.biz.dao.query.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.query.TmTaskListDao;
import com.jjb.ecms.facility.dto.ApplyTaskListDto;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;

/**
 * @description 任务列表实现类
 * @author hn
 * @date 2016年9月1日16:52:43
 */

@Repository("tmTaskListDao")
public class TmTaskListDaoImpl extends AbstractBaseDao<ApplyTaskListDto> implements TmTaskListDao {

	public static final String  selectAll = "com.jjb.ecms.biz.TmTaskList.selectAll";//查询未分配的任务
	public static final String  selectPreAll = "com.jjb.ecms.biz.TmTaskList.selectPreAll";//查询预审待分配的任务
	public static final String  selectMy = "com.jjb.ecms.biz.TmTaskList.selectMy";//查询我的任务
	public static final String  selectByIdNo = "com.jjb.ecms.biz.TmTaskList.selectByIdNo";//查询该人未审批结束的申请件
	public static final String  selectComplete = "com.jjb.ecms.biz.TmTaskList.selectComplete";//查询该操作员已完成任务
	
	
	/*
	 * 查询未分配的任务
	 */
	@Override
	public Page<ApplyTaskListDto> querytoDoPage(Page<ApplyTaskListDto> page) {
		// 查询未分配任务
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		
		Page<ApplyTaskListDto> p = queryForPageList(selectAll, page.getQuery(), page);
		return p;
	}

	@Override
	public Page<ApplyTaskListDto> querypreToDoPage(Page<ApplyTaskListDto> page) {
		// 查询未分配任务
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}

		Page<ApplyTaskListDto> p = queryForPageList(selectPreAll, page.getQuery(), page);
		return p;
	}


	/*
	 * 查询我的任务
	 */
	@Override
	public Page<ApplyTaskListDto> queryMyTaskPage(Page<ApplyTaskListDto> page) {
		// TODO Auto-generated method stub
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		Page<ApplyTaskListDto> p = queryForPageList(selectMy, page.getQuery(), page);
		return p;
	}

	/*
	 * 查询已完成任务
	 */
	@Override
	public Page<ApplyTaskListDto> queryCompleteTaskPage(Page<ApplyTaskListDto> page){
		// TODO Auto-generated method stub
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		Page<ApplyTaskListDto> p = queryForPageList(selectComplete, page.getQuery(), page);
		return p;
	}

	@Override
	public List<ApplyTaskListDto> queryMyTask(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return queryForList(selectMy, map);
	}

	/**
	 * 根据姓名、证件类型、证件号码判断是否是贷中状态(用于异步查询人行报告)
	 * @param applyTaskListDto
	 * @return
	 */
	@Override
	public List<ApplyTaskListDto> queryApplyTaskListDtoList(ApplyTaskListDto applyTaskListDto) {
		
		return queryForList(selectByIdNo, applyTaskListDto);
	}
}
