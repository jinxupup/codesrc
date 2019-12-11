package com.jjb.ecms.biz.dao.report.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.report.ApplyRejectReportDao;
import com.jjb.ecms.biz.service.query.ApplyProcessUtils;
import com.jjb.ecms.facility.dto.ApplyRejectReportQueryDto;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;

@Repository("/applyRejectReportDaoImpl")
public class ApplyRejectReportDaoImpl extends
		AbstractBaseDao<ApplyRejectReportQueryDto> implements ApplyRejectReportDao {

	public static final String NS = "com.jjb.ecms.biz.ApplyRejectReportMapper";

	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private ApplyProcessUtils applyProcessUtils;
	
	@Override
	public List<ApplyRejectReportQueryDto> selectApplyRejectData(Map<String, Object> params) {
		applyProcessUtils.ifSelectOwningBranch(params);
		return super.queryForList(NS+".selectMainToReport", params);
	}

	@Override
	public Page<ApplyRejectReportQueryDto> getPage(Page<ApplyRejectReportQueryDto> page, ApplyRejectReportQueryDto applyRejectReportQueryDto) {
		applyProcessUtils.ifSelectOwningBranch(page);
		return super.queryForPageList(NS+".selectMain", page.getQuery(), page);
		
	}

}
