package com.jjb.ecms.biz.service.report;

import java.util.List;
import java.util.Map;

import com.jjb.ecms.facility.dto.ApplySuccessReportQueryDto;
import com.jjb.unicorn.facility.model.Page;

public interface ApplySuccessReportService {
	
	public List<ApplySuccessReportQueryDto> getApplySucessReportData(Map<String, Object> map);

	public Page<ApplySuccessReportQueryDto> getPage(Page<ApplySuccessReportQueryDto> page, ApplySuccessReportQueryDto applySuccessReportQueryDto);
}
