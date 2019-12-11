package com.jjb.ecms.biz.dao.report.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.report.SupplementRemindReportDao;
import com.jjb.ecms.biz.service.query.ApplyProcessUtils;
import com.jjb.ecms.facility.dto.SupplementRemindReportQueryDto;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;

@Repository("/supplementRemindReportDaoImpl")
public class SupplementRemindReportDaoImpl extends AbstractBaseDao<SupplementRemindReportQueryDto> implements SupplementRemindReportDao {

	private static final String NS="com.jjb.ecms.biz.SupplementRemindReportMapper";
	
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private ApplyProcessUtils applyProcessUtils;
	
	@Override
	public List<SupplementRemindReportQueryDto> selectSupplementRemindData(Map<String, Object> params) {
		applyProcessUtils.ifSelectOwningBranch(params);
		return super.queryForList(NS+".selectMainToReport",params);
	}
	@Override
	public Page<SupplementRemindReportQueryDto> getPage(Page<SupplementRemindReportQueryDto> page, SupplementRemindReportQueryDto supplementRemindReportQueryDto) {
		applyProcessUtils.ifSelectOwningBranch(page);
		return super.queryForPageList(NS+".selectMain", page.getQuery(), page);
	}
	

}
