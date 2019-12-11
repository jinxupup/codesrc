package com.jjb.ecms.biz.service.report.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.dao.report.SupplementRemindReportDao;
import com.jjb.ecms.biz.service.report.SupplementRemindReportService;
import com.jjb.ecms.facility.dto.SupplementRemindReportQueryDto;
import com.jjb.unicorn.facility.model.Page;

@Service("/SupplementRemindReportService")
public class SupplementRemindReportServiceImpl implements SupplementRemindReportService {

	@Autowired
    SupplementRemindReportDao supplementRemindReportDao;
	
	@Override
	@Transactional
	public List<SupplementRemindReportQueryDto> getSupplementRemindReportData(
			String startDate, String endDate) {
		Map<String,Object> params = new HashMap();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		return supplementRemindReportDao.selectSupplementRemindData(params);
	}

	@Override
	@Transactional
	public Page<SupplementRemindReportQueryDto> getPage(Page<SupplementRemindReportQueryDto> page, SupplementRemindReportQueryDto supplementRemindReportQueryDto) {
		
		return supplementRemindReportDao.getPage(page,supplementRemindReportQueryDto);
	}

	@Override
	@Transactional
	public List<SupplementRemindReportQueryDto> getSupplementRemindReportData(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		return supplementRemindReportDao.selectSupplementRemindData(map);
	}
	

}
