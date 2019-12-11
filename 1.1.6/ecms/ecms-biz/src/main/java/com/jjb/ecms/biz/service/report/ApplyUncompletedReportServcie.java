package com.jjb.ecms.biz.service.report;

import java.util.List;
import java.util.Map;

import com.jjb.ecms.facility.dto.ApplyUncompletedDto;
import com.jjb.unicorn.facility.model.Page;

public interface ApplyUncompletedReportServcie {
	public List<ApplyUncompletedDto> getApplyUncompletedReportData(Map<String, Object> map);

	public Page<ApplyUncompletedDto> getPage(Page<ApplyUncompletedDto> page, ApplyUncompletedDto applyUncompletedDto);
}
