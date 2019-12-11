package com.jjb.ecms.biz.dao.report.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.report.ApplyProportionReportDao;
import com.jjb.ecms.facility.dto.ApplyProportionReportDto;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;

@Repository("applyProportionReportDaoImpl")
public class ApplyProportionReportDaoImpl extends AbstractBaseDao<ApplyProportionReportDto> implements ApplyProportionReportDao{

	public static final String NS = "com.jjb.ecms.biz.ApplyProportionReportMappper";

	@Override
	public List<ApplyProportionReportDto> selectApplyProportionReportData(Map<String, Object> params) {
		return super.queryForList(NS+".selectMain",params);
	}

	@Override
	public Page<ApplyProportionReportDto> getPage(Page<ApplyProportionReportDto> page, ApplyProportionReportDto applyProportionReportDto) {
		return super.queryForPageList(NS+".selectMain", page.getQuery(), page);
	}

}
