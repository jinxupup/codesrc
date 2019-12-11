package com.jjb.ecms.biz.dao.report;

import java.util.List;
import java.util.Map;

import com.jjb.ecms.facility.dto.ApplyProportionReportDto;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

public interface ApplyProportionReportDao extends BaseDao<ApplyProportionReportDto> {
	
	public List<ApplyProportionReportDto> selectApplyProportionReportData(Map<String, Object> params);

	public Page<ApplyProportionReportDto> getPage(Page<ApplyProportionReportDto> page, ApplyProportionReportDto applyProportionReportDto);

}
