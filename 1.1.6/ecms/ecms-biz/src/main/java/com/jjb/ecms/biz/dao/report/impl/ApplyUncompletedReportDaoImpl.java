package com.jjb.ecms.biz.dao.report.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.report.ApplyUncompletedReportDao;
import com.jjb.ecms.biz.service.query.ApplyProcessUtils;
import com.jjb.ecms.facility.dto.ApplyUncompletedDto;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;

@Repository("/applyUncompletedReportDaoImpl")
public class ApplyUncompletedReportDaoImpl extends
		AbstractBaseDao<ApplyUncompletedDto> implements ApplyUncompletedReportDao {

	public static final String NS = "com.jjb.ecms.biz.ApplyUncompletedDtoMapper";
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private ApplyProcessUtils applyProcessUtils;
	
	@Override
	public List<ApplyUncompletedDto> selectApplyUncompletedData(Map<String, Object> params) {
		applyProcessUtils.ifSelectOwningBranch(params);
		return super.queryForList(NS+".selectMainToReport", params);
	}

	@Override
	public Page<ApplyUncompletedDto> getPage(Page<ApplyUncompletedDto> page, ApplyUncompletedDto applyUncompletedDto) {
		applyProcessUtils.ifSelectOwningBranch(page);
		return super.queryForPageList(NS+".selectMain", page.getQuery(), page);
	}

}
