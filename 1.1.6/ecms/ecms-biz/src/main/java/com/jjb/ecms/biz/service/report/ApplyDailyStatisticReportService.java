package com.jjb.ecms.biz.service.report;

import java.util.List;
import java.util.Map;

import com.jjb.ecms.facility.dto.ApplyDailyStatisticReportDto;
import com.jjb.unicorn.facility.model.Page;

public interface ApplyDailyStatisticReportService {
	
	public List<ApplyDailyStatisticReportDto> getApplyDailyStatisticReportData(Map<String, Object> map);

	public Page<ApplyDailyStatisticReportDto> getPage(Page<ApplyDailyStatisticReportDto> page, ApplyDailyStatisticReportDto applySuccessReportQueryDto);

}
