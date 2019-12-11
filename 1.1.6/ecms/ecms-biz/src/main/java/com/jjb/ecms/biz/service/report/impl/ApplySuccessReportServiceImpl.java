package com.jjb.ecms.biz.service.report.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.dao.report.ApplySuccessReportDao;
import com.jjb.ecms.biz.service.report.ApplySuccessReportService;
import com.jjb.ecms.facility.dto.ApplySuccessReportQueryDto;
import com.jjb.unicorn.facility.model.Page;

@Service("applySuccessReportService")
public class ApplySuccessReportServiceImpl implements ApplySuccessReportService{
    @Autowired
    private ApplySuccessReportDao applySuccessReportDao;
    
	@Override
	@Transactional
	public List<ApplySuccessReportQueryDto> getApplySucessReportData(Map<String, Object> map) {
		return applySuccessReportDao.selectApplySuccessData(map);
		
	}

	@Override
	@Transactional
	public Page<ApplySuccessReportQueryDto> getPage(Page<ApplySuccessReportQueryDto> page, ApplySuccessReportQueryDto applySuccessReportQueryDto) {
		
		return applySuccessReportDao.getPage(page,applySuccessReportQueryDto);
	}
	

}
