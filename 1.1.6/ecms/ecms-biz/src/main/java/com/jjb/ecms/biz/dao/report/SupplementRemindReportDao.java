package com.jjb.ecms.biz.dao.report;

import java.util.List;
import java.util.Map;

import com.jjb.ecms.facility.dto.SupplementRemindReportQueryDto;
import com.jjb.unicorn.facility.model.Page;

public interface SupplementRemindReportDao {

	public List<SupplementRemindReportQueryDto> selectSupplementRemindData(Map<String, Object> params);

	public Page<SupplementRemindReportQueryDto> getPage(Page<SupplementRemindReportQueryDto> page, SupplementRemindReportQueryDto supplementRemindReportQueryDto);

	

}
