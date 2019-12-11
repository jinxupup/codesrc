package com.jjb.ecms.biz.service.report.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.dao.report.ApplyProportionReportDao;
import com.jjb.ecms.biz.service.report.ApplyProportionReportService;
import com.jjb.ecms.facility.dto.ApplyProportionReportDto;
import com.jjb.unicorn.facility.model.Page;

@Service("applyProportionReportService")
public class ApplyProportionReportServiceImpl implements ApplyProportionReportService {
	
	@Autowired
	private ApplyProportionReportDao applyProportionReportDao;

	@Override
	@Transactional
	public List<ApplyProportionReportDto> getApplyProportionReportData(Map<String, Object> map) {
		
		return applyProportionReportDao.selectApplyProportionReportData(map);
	}

	@Override
	@Transactional
	public Page<ApplyProportionReportDto> getPage(Page<ApplyProportionReportDto> page,
			ApplyProportionReportDto applyProportionReportDto) {
		return applyProportionReportDao.getPage(page, applyProportionReportDto);
	}

}
