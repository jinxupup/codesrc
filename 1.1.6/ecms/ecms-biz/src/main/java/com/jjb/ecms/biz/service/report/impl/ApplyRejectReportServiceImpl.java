package com.jjb.ecms.biz.service.report.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.dao.report.ApplyRejectReportDao;
import com.jjb.ecms.biz.dao.report.ApplyRejectReportMonthDao;
import com.jjb.ecms.biz.service.report.ApplyRejectReportService;
import com.jjb.ecms.facility.dto.ApplyRejectMonthDto;
import com.jjb.ecms.facility.dto.ApplyRejectReportQueryDto;
import com.jjb.unicorn.facility.model.Page;

@Service("/applyRejectReportService")
public class ApplyRejectReportServiceImpl implements ApplyRejectReportService{

	@Autowired
	private ApplyRejectReportDao applyRejectReportDao;
	
	@Autowired
	private ApplyRejectReportMonthDao applyRejectReportMonthDao;
	
	@Override
	@Transactional
	public List<ApplyRejectReportQueryDto> getApplyRejectReportData(Map<String, Object> map) {
		
		return applyRejectReportDao.selectApplyRejectData(map);
	}

	@Override
	@Transactional
	public Page<ApplyRejectReportQueryDto> getPage(Page<ApplyRejectReportQueryDto> page, ApplyRejectReportQueryDto applyRejectReportQueryDto) {
		
		return applyRejectReportDao.getPage(page,applyRejectReportQueryDto);
	}
	
	@Override
	@Transactional
	public List<ApplyRejectMonthDto> getRejectReprtMonth(Map<String, Object> map) {
		return applyRejectReportMonthDao.getRejectReprtMonth(map);
	}

	@Override
	@Transactional
	public Page<ApplyRejectMonthDto> getRejectPageMonth(Page<ApplyRejectMonthDto> page){
		return applyRejectReportMonthDao.getRejectPageMonth(page);
	}

}
