package com.jjb.ecms.biz.dao.report;

import java.util.List;
import java.util.Map;

import com.jjb.ecms.facility.dto.ApplySuccessReportQueryDto;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

public interface ApplySuccessReportDao extends BaseDao<ApplySuccessReportQueryDto>{

	public List<ApplySuccessReportQueryDto> selectApplySuccessData(Map<String, Object> params);

	public Page<ApplySuccessReportQueryDto> getPage(Page<ApplySuccessReportQueryDto> page, ApplySuccessReportQueryDto applySuccessReportQueryDto);
}
