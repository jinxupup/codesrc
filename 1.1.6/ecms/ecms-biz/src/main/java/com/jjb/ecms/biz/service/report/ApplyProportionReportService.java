package com.jjb.ecms.biz.service.report;

import java.util.List;
import java.util.Map;

import com.jjb.ecms.facility.dto.ApplyProportionReportDto;
import com.jjb.unicorn.facility.model.Page;

public interface ApplyProportionReportService {
	
	public List<ApplyProportionReportDto> getApplyProportionReportData(Map<String, Object> map);

	public Page<ApplyProportionReportDto> getPage(Page<ApplyProportionReportDto> page, ApplyProportionReportDto applyProportionReportDto);

}
