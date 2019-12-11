package com.jjb.ecms.biz.service.report.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.dao.report.ApplyDailyStatisticReportDao;
import com.jjb.ecms.biz.service.report.ApplyDailyStatisticReportService;
import com.jjb.ecms.facility.dto.ApplyDailyStatisticReportDto;
import com.jjb.unicorn.facility.model.Page;

@Service("applyDailyStatisticReportService")
public class ApplyDailyStatisticReportServiceImpl implements ApplyDailyStatisticReportService{
	
	@Autowired
	private ApplyDailyStatisticReportDao applyDailyStatisticReportDao;

	@Override
	@Transactional
	public List<ApplyDailyStatisticReportDto> getApplyDailyStatisticReportData(Map<String, Object> map) {
		
		return applyDailyStatisticReportDao.selectApplyDailyStatisticReportData(map);
	}

	@Override
	@Transactional
	public Page<ApplyDailyStatisticReportDto> getPage(Page<ApplyDailyStatisticReportDto> page,
			ApplyDailyStatisticReportDto applySuccessReportQueryDto) {
		return applyDailyStatisticReportDao.getPage(page, applySuccessReportQueryDto);
	}

}
