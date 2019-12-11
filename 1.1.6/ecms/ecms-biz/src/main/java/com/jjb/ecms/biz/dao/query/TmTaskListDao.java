package com.jjb.ecms.biz.dao.query;

import java.util.List;
import java.util.Map;

import com.jjb.ecms.facility.dto.ApplyTaskListDto;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

/**
 * @description 任务列表查询
 * @author hn 
 */
public interface TmTaskListDao extends BaseDao<ApplyTaskListDto>{

	/*
	 * 查询未分配任务 
	 */
	Page<ApplyTaskListDto> querytoDoPage(Page<ApplyTaskListDto> page);

	/*
	 * 查询未分配任务
	 */
	Page<ApplyTaskListDto> querypreToDoPage(Page<ApplyTaskListDto> page);
	
	/*
	 * 查询我的任务
	 */
	Page<ApplyTaskListDto> queryMyTaskPage(Page<ApplyTaskListDto> page);

	/*
	 * 查询已完成任务
	 */
	Page<ApplyTaskListDto> queryCompleteTaskPage(Page<ApplyTaskListDto> page);

	/**
	 * 查询节点任务
	 */
	List<ApplyTaskListDto> queryMyTask(Map<String, Object> map);
	
	/**
	 * 根据姓名、证件类型、证件号码判断是否是贷中状态(用于异步查询人行报告)
	 * @param applyTaskListDto
	 * @return
	 */
	List<ApplyTaskListDto> queryApplyTaskListDtoList(ApplyTaskListDto applyTaskListDto);
}
