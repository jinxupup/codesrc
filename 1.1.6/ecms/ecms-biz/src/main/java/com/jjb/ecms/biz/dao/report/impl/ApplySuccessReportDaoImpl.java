package com.jjb.ecms.biz.dao.report.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.report.ApplySuccessReportDao;
import com.jjb.ecms.biz.service.query.ApplyProcessUtils;
import com.jjb.ecms.facility.dto.ApplySuccessReportQueryDto;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;

@Repository("applySuccessReportDaoImpl")
public class ApplySuccessReportDaoImpl extends AbstractBaseDao<ApplySuccessReportQueryDto> implements ApplySuccessReportDao {

	public static final String NS = "com.jjb.ecms.biz.ApplySuccessReportMapper";
	
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private ApplyProcessUtils applyProcessUtils;

	@Override
	public List<ApplySuccessReportQueryDto> selectApplySuccessData(Map<String, Object> params) {
		applyProcessUtils.ifSelectOwningBranch(params);
		return super.queryForList(NS+".selectMainToReport", params);
	}

	@Override
	public Page<ApplySuccessReportQueryDto> getPage(Page<ApplySuccessReportQueryDto> page, ApplySuccessReportQueryDto applySuccessReportQueryDto){
		applyProcessUtils.ifSelectOwningBranch(page);
		return super.queryForPageList(NS+".selectMain", page.getQuery(), page);
	}

}
