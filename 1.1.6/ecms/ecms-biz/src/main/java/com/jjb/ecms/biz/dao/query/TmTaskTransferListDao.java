package com.jjb.ecms.biz.dao.query;

import com.jjb.ecms.facility.dto.ApplyTaskListDto;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

/**
 * @description 未分配任务列表查询
 * @author hn 
 */
public interface TmTaskTransferListDao extends BaseDao<ApplyTaskListDto>{

	/*
	 * 查询未分配任务 
	 */
	Page<ApplyTaskListDto> queryTaskTransferPage(Page<ApplyTaskListDto> page);
	
}
