package com.jjb.ecms.biz.service.report.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.dao.report.ApplyUncompletedReportDao;
import com.jjb.ecms.biz.service.report.ApplyUncompletedReportServcie;
import com.jjb.ecms.facility.dto.ApplyUncompletedDto;
import com.jjb.unicorn.facility.model.Page;


@Service("/applyUncompletedReportService")
public class ApplyUncompletedReportServiceImpl implements ApplyUncompletedReportServcie {

	@Autowired
	private ApplyUncompletedReportDao applyUncompletedReportDao;
	
	@Override
	@Transactional
	public List<ApplyUncompletedDto> getApplyUncompletedReportData(Map<String, Object> map) {
		return applyUncompletedReportDao.selectApplyUncompletedData(map);
	}

	@Override
	@Transactional
	public Page<ApplyUncompletedDto> getPage(Page<ApplyUncompletedDto> page, ApplyUncompletedDto applyUncompletedDto) {
		
		return applyUncompletedReportDao.getPage(page,applyUncompletedDto);
	}

}
