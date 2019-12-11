package com.jjb.ecms.biz.dao.report;

import java.util.List;
import java.util.Map;

import com.jjb.ecms.facility.dto.ApplyRejectMonthDto;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;
/**
 * 
 * @ClassName ApplyRejectReportMonthDao
 * @Description TODO 申请失败月报表持久层
 * @author H.N
 * @Date 2017年12月25日 上午10:24:40
 * @version 1.0.0
 */
public interface ApplyRejectReportMonthDao extends BaseDao<ApplyRejectMonthDto>{

	public List<ApplyRejectMonthDto> getRejectReprtMonth(Map<String, Object> map);
	
	public Page<ApplyRejectMonthDto> getRejectPageMonth(Page<ApplyRejectMonthDto> page);
}
