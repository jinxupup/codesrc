package com.jjb.ecms.biz.service.report;

import java.util.List;
import java.util.Map;

import com.jjb.ecms.facility.dto.SupplementRemindReportQueryDto;
import com.jjb.unicorn.facility.model.Page;


public interface SupplementRemindReportService {

	public List<SupplementRemindReportQueryDto> getSupplementRemindReportData(String startDate, String endDate);

	public List<SupplementRemindReportQueryDto> getSupplementRemindReportData(Map<String, Object> map);

	public Page<SupplementRemindReportQueryDto> getPage(Page<SupplementRemindReportQueryDto> page, SupplementRemindReportQueryDto supplementRemindReportQueryDto);


}
