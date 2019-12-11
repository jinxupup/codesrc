package com.jjb.ecms.biz.service.report;

import java.util.List;
import java.util.Map;

import com.jjb.ecms.facility.dto.ApplyRejectMonthDto;
import com.jjb.ecms.facility.dto.ApplyRejectReportQueryDto;
import com.jjb.unicorn.facility.model.Page;


public interface ApplyRejectReportService {
	
	public List<ApplyRejectReportQueryDto> getApplyRejectReportData(Map<String, Object> map);

	public Page<ApplyRejectReportQueryDto> getPage(Page<ApplyRejectReportQueryDto> page, ApplyRejectReportQueryDto applyRejectReportQueryDto);

	public List<ApplyRejectMonthDto> getRejectReprtMonth(Map<String, Object> map);
	
	public Page<ApplyRejectMonthDto> getRejectPageMonth(Page<ApplyRejectMonthDto> page);
}
