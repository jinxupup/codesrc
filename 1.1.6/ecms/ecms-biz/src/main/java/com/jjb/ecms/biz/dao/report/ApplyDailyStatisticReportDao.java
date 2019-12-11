package com.jjb.ecms.biz.dao.report;

import java.util.List;
import java.util.Map;

import com.jjb.ecms.facility.dto.ApplyDailyStatisticReportDto;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

public interface ApplyDailyStatisticReportDao extends BaseDao<ApplyDailyStatisticReportDto> {
	
	public List<ApplyDailyStatisticReportDto> selectApplyDailyStatisticReportData(Map<String, Object> params);

	public Page<ApplyDailyStatisticReportDto> getPage(Page<ApplyDailyStatisticReportDto> page, ApplyDailyStatisticReportDto applySuccessReportQueryDto);

}
