package com.jjb.ecms.biz.service.query;

import java.util.List;

import com.jjb.acl.infrastructure.TmAclUser;
import com.jjb.ecms.facility.dto.ApplyTaskListDto;
import com.jjb.unicorn.facility.model.Page;

/**
 * @description 任务列表service
 * @author hn
 * @date 2016年8月30日16:34:54
 */
public interface ApplyTaskListService {

	/**
	 * 根据系统名字来获取未分配任务
	 */
	Page<ApplyTaskListDto> getTodoTaskList(Page<ApplyTaskListDto> page,String systemType);

	/**
	 * 获取待预审分配任务
	 */
	Page<ApplyTaskListDto> getPreTodoTaskList(Page<ApplyTaskListDto> page,String systemType);
	/**
	 * 根据系统名字来获取我的任务
	 */
	Page<ApplyTaskListDto> getMyTaskList(Page<ApplyTaskListDto> page,String systemType);

	/**
	 * 根据操作员ID来获取已完成任务
	 */
	Page<ApplyTaskListDto> getCompletedTaskList(Page<ApplyTaskListDto> page);
	
	/**
	 * 获取可分配的用户名单 
	 */
	Page<TmAclUser> getUserPage(Page<TmAclUser> page);
	/**
	 * 获取超时天数
	 * @return
	 */
	int getOverDays();
	/**
	 * 判断是否有案件分配权限
	 * @return
	 */
	boolean hasAssignTaskAuth();
	
	/**
	 * 在列表中显示最后一个退回节点 
	 * @return
	 */
	void displayLastNodeInList(Page<ApplyTaskListDto> page);
	
	/**
	 * 取消该任务
	 * @return
	 */
	void cancelTask(String taskId, String appNo);
	
	/**
	 * 根据姓名、证件类型、证件号码判断是否是贷中状态(用于异步查询人行报告)
	 * @param applyTaskListDto
	 * @return
	 */
	List<ApplyTaskListDto> getApplyTaskListDtoList(ApplyTaskListDto applyTaskListDto);
}
