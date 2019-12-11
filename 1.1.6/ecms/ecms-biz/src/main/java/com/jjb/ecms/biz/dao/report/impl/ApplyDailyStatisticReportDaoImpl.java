package com.jjb.ecms.biz.dao.report.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.report.ApplyDailyStatisticReportDao;
import com.jjb.ecms.facility.dto.ApplyDailyStatisticReportDto;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;

@Repository("applyDailyStatisticReportDaoImpl")
public class ApplyDailyStatisticReportDaoImpl extends AbstractBaseDao<ApplyDailyStatisticReportDto> implements ApplyDailyStatisticReportDao{

	public static final String NS = "com.jjb.ecms.biz.ApplyDailyStatisticReportMappper";

	@Override
	public List<ApplyDailyStatisticReportDto> selectApplyDailyStatisticReportData(Map<String, Object> params) {
		return super.queryForList(NS+".selectMain",params);
	}

	@Override
	public Page<ApplyDailyStatisticReportDto> getPage(Page<ApplyDailyStatisticReportDto> page,
			ApplyDailyStatisticReportDto applySuccessReportQueryDto) {
		return super.queryForPageList(NS+".selectMain", page.getQuery(), page);
	}

}
