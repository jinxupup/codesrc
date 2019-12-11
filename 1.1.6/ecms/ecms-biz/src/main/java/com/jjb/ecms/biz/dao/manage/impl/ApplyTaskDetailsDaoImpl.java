package com.jjb.ecms.biz.dao.manage.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.manage.ApplyTaskDetailsDao;
import com.jjb.ecms.facility.dto.ApplyTaskDetailsDto;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;

/**
 * @Description: 申请工作统计明细类、申请件与工作流未分配任务查询
 * @author JYData-R&D-L.L
 * @date 2016年9月8日 下午2:34:06
 * @version V1.0
 */
@Repository("applyTaskDetailsDao")
public class ApplyTaskDetailsDaoImpl extends AbstractBaseDao<ApplyTaskDetailsDto> implements ApplyTaskDetailsDao {
	private static final String selectDetails = "com.jjb.ecms.biz.ApplyTaskDetails.selectDetails";
	private static final String selectWorkDetails = "com.jjb.ecms.biz.ApplyTaskDetails.selectWorkDetails";
	//非初审未分配任务清单
	private static final String getTaskUndistributedList = "com.jjb.ecms.biz.ApplyTaskDetails.getTaskUndistributedList";
	//初审未分配任务清单
	private static final String getTaskUndistributedLists = "com.jjb.ecms.biz.ApplyTaskDetails.getTaskUndistributedLists";
	//根据任务节点名称获取任务数量
	private static final String getTaskCntBytaskKey = "com.jjb.ecms.biz.ApplyTaskDetails.getTaskCntBytaskKey";

	/**
	 * 新工作量查询统计详细信息
	 * @AUTH hejn 20190424
	 * @param page
	 * @return
	 */
	@Override
	public Page<ApplyTaskDetailsDto> getTaskWorkDetails(Page<ApplyTaskDetailsDto> page) {
		Page<ApplyTaskDetailsDto> p = queryForPageList(selectWorkDetails, page.getQuery(), page);
		return p;
	}

	/**
	 * 工作流任务详情
	 * @param page
	 * @return
	 */
	@Override
	public Page<ApplyTaskDetailsDto> getTaskDetails(Page<ApplyTaskDetailsDto> page) {
		Page<ApplyTaskDetailsDto> p = queryForPageList(selectDetails, page.getQuery(), page);
		return p;
	}


	/**
	 * 任务查询
	 * @param applyAndActivitiDto
	 * @return
	 */
	@Override
	public List<ApplyTaskDetailsDto> getTaskUndistributedList(ApplyTaskDetailsDto applyTaskDetailsDto) {
		return queryForList(getTaskUndistributedList, applyTaskDetailsDto);
	}
	/**
	 * 任务查询
	 * @param applyAndActivitiDto
	 * @return
	 */
	@Override
	public List<ApplyTaskDetailsDto> getTaskUndistributedLists(ApplyTaskDetailsDto applyTaskDetailsDto) {
		return queryForList(getTaskUndistributedLists, applyTaskDetailsDto);
	}
	/**
	 * 根据任务名称获取已分配任务数量
	 * @param ApplyTaskDetailsDto
	 * @return 集合List
	 */
	@Override
	public List<ApplyTaskDetailsDto> getTaskCntBytaskKey(ApplyTaskDetailsDto applyTaskDetailsDto){
		return queryForList(getTaskCntBytaskKey, applyTaskDetailsDto);
	}

}
