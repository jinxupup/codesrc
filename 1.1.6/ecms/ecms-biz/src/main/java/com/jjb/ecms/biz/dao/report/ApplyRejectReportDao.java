package com.jjb.ecms.biz.dao.report;

import java.util.List;
import java.util.Map;

import com.jjb.ecms.facility.dto.ApplyRejectReportQueryDto;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

public interface ApplyRejectReportDao extends BaseDao<ApplyRejectReportQueryDto>{

	public List<ApplyRejectReportQueryDto> selectApplyRejectData(Map<String, Object> params);

	public Page<ApplyRejectReportQueryDto> getPage(Page<ApplyRejectReportQueryDto> page, ApplyRejectReportQueryDto applyRejectReportQueryDto);

}
