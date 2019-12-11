package com.jjb.ecms.biz.dao.report;

import java.util.List;
import java.util.Map;

import com.jjb.ecms.facility.dto.ApplyUncompletedDto;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;


public interface ApplyUncompletedReportDao extends BaseDao<ApplyUncompletedDto>{

	public List<ApplyUncompletedDto> selectApplyUncompletedData(Map<String, Object> params);

	public Page<ApplyUncompletedDto> getPage(Page<ApplyUncompletedDto> page, ApplyUncompletedDto applyUncompletedDto);

}
