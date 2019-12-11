package com.jjb.ecms.biz.service.query;

import java.util.List;

import com.jjb.acl.infrastructure.TmAclUser;
import com.jjb.ecms.facility.dto.ApplyTaskListDto;
import com.jjb.ecms.infrastructure.TmTaskTransfer;
import com.jjb.unicorn.facility.model.Page;

/**
 * @description 已分配任务列表service
 * @author hn
 * @date 2016年9月2日10:53:33
 */
public interface ApplyTaskTransferListService {

	/**
	 * 获取已分配任务 
	 */
	Page<ApplyTaskListDto> getTransferTaskList(Page<ApplyTaskListDto> page,String systemType);
	
	/**
	 * 根据appNo获取任务流转记录 
	 */
	List<TmTaskTransfer> getTransferTaskListByAppNo(String appNo);
	
	/**
	 * 获取可分配的用户名单 
	 */
	Page<TmAclUser> getUserPage(Page<TmAclUser> page, String selectTask);
	/**
	 * 任务转分配
	 * @param userNo
	 * @param taskIdAndAppNo
	 */
	void transferTask(String userNo, String taskIdAndAppNo);
	/**
	 * 预审任务转分配
	 * @param spreaderNum
	 * @param taskIdAndAppNo
	 */
	void spreaderSave(String spreaderNum, String taskIdAndAppNo);

}
