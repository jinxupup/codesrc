package com.jjb.ecms.biz.service.manage;

import java.util.List;

import com.jjb.ecms.facility.dto.ApplyTaskDetailsDto;
import com.jjb.unicorn.facility.model.Page;

/**
  * @Description: 申请工作统计明细类
  * @author JYData-R&D-L.L
  * @date 2016年9月8日 下午2:34:06
  * @version V1.0
 */
public interface ApplyTaskDetailsService {
	/**
	 * 新工作量查询统计详细信息
	 * @AUTH hejn 20190424
	 * @param page
	 * @return
	 */
	Page<ApplyTaskDetailsDto> getTaskWorkDetails(Page<ApplyTaskDetailsDto> page);
	
	/**
	 * 查询统计工作详细信息
	 * @param page
	 * @return
	 */
	Page<ApplyTaskDetailsDto> getTaskDetails(Page<ApplyTaskDetailsDto> page);
	
	/**
	 * 根据参数获取初审未分配任务列表
	 * @param ApplyTaskDetailsDto
	 * @return 集合List
	 */
	List<ApplyTaskDetailsDto> getTaskUndistributedList(ApplyTaskDetailsDto applyTaskDetailsDto);
	/**
	 * 根据参数获取非初审未分配任务列表
	 * @param ApplyTaskDetailsDto
	 * @return 集合List
	 */
	List<ApplyTaskDetailsDto> getTaskUndistributedLists(ApplyTaskDetailsDto applyTaskDetailsDto);
	/**
	 * 根据任务名称获取已分配任务数量
	 * @param ApplyTaskDetailsDto
	 * @return 集合List
	 */
	List<ApplyTaskDetailsDto> getTaskCntBytaskKey(ApplyTaskDetailsDto applyTaskDetailsDto);
}
