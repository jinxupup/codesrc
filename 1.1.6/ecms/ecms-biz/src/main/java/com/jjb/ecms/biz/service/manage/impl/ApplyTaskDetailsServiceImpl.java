package com.jjb.ecms.biz.service.manage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjb.ecms.biz.dao.manage.ApplyTaskDetailsDao;
import com.jjb.ecms.biz.service.manage.ApplyTaskDetailsService;
import com.jjb.ecms.facility.dto.ApplyTaskDetailsDto;
import com.jjb.unicorn.facility.model.Page;

/**
 * @Description: 申请工作统计明细类
 * 获取未分配任务清单列表
 * @author JYData-R&D-L.L
 * @date 2016年9月8日 下午2:34:06
 * @version V1.0
*/
@Service("applyTaskDetailsService")
public class ApplyTaskDetailsServiceImpl implements ApplyTaskDetailsService {

	@Autowired
    private ApplyTaskDetailsDao applyTaskDetailsDao;
	
	/**
	 * 新工作量查询统计详细信息
	 * @AUTH hejn 20190424
	 * @param page
	 * @return
	 */
	@Override
	public Page<ApplyTaskDetailsDto> getTaskWorkDetails(Page<ApplyTaskDetailsDto> page){
		return applyTaskDetailsDao.getTaskWorkDetails(page);
	}

	/**
	 * 查询统计工作详细信息
	 * @param page
	 * @return
	 */
	@Override
	public Page<ApplyTaskDetailsDto> getTaskDetails(Page<ApplyTaskDetailsDto> page) {
		return applyTaskDetailsDao.getTaskDetails(page);
	}
	
	/**
	 * 根据参数获取未分配任务列表
	 * @param ApplyTaskDetailsDto
	 * @return 集合List
	 */
	@Override
	public List<ApplyTaskDetailsDto> getTaskUndistributedList(ApplyTaskDetailsDto applyTaskDetailsDto){
		return applyTaskDetailsDao.getTaskUndistributedList(applyTaskDetailsDto);
	}
	
	/**
	 * 根据参数获取非初审未分配任务列表
	 * @param ApplyTaskDetailsDto
	 * @return 集合List
	 */
	@Override
	public List<ApplyTaskDetailsDto> getTaskUndistributedLists(ApplyTaskDetailsDto applyTaskDetailsDto){
		return applyTaskDetailsDao.getTaskUndistributedList(applyTaskDetailsDto);
	}

	/**
	 * 根据任务名称获取已分配任务数量
	 * @param ApplyTaskDetailsDto
	 * @return 集合List
	 */
	@Override
	public List<ApplyTaskDetailsDto> getTaskCntBytaskKey(ApplyTaskDetailsDto applyTaskDetailsDto){
		return applyTaskDetailsDao.getTaskCntBytaskKey(applyTaskDetailsDto);
	}
}
