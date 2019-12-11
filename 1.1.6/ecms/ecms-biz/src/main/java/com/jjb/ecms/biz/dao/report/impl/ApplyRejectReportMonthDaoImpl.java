package com.jjb.ecms.biz.dao.report.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.report.ApplyRejectReportMonthDao;
import com.jjb.ecms.biz.service.query.ApplyProcessUtils;
import com.jjb.ecms.facility.dto.ApplyRejectMonthDto;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;
@Repository
public class ApplyRejectReportMonthDaoImpl extends AbstractBaseDao<ApplyRejectMonthDto> implements ApplyRejectReportMonthDao{

	private static final String NS = "com.jjb.ecms.biz.ApplyFailed";
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private ApplyProcessUtils applyProcessUtils;
	
	@Override
	public List<ApplyRejectMonthDto> getRejectReprtMonth(Map<String, Object> map) {
		applyProcessUtils.ifSelectOwningBranch(map);
		return super.queryForList(NS+".selectApplyFailed", map);
	}

	@Override
	public Page<ApplyRejectMonthDto> getRejectPageMonth(Page<ApplyRejectMonthDto> page) {
		applyProcessUtils.ifSelectOwningBranch(page);
		return super.queryForPageList(NS+".selectApplyFailed", page.getQuery(), page);
	}

}
