package com.jjb.ecms.biz.dao.manage.impl;

import java.text.ParseException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.manage.ApplyTaskCountDao;
import com.jjb.ecms.facility.dto.ApplyTaskCountDto;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
  * @Description:  查询申请工作记录
  * @author JYData-R&D-L.L
  * @date 2016年9月5日 下午4:20:23
  * @version V1.0
 */
@Repository("applyTaskCountDao")
public class ApplyTaskCountDaoImpl extends AbstractBaseDao<ApplyTaskCountDto> implements ApplyTaskCountDao {
	private static final String selectByConditions = "com.jjb.ecms.biz.ApplyTaskCount.selectByConditions";
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 查询申请工作记录
	 * @param page
	 * @return 
	 */
	@Override
	public Page<ApplyTaskCountDto> getTaskCountList(Page<ApplyTaskCountDto> page) {		
//		page.getQuery().put("org", OrganizationContextHolder.getOrg());
		String startDateStr = StringUtils.valueOf(page.getQuery().get("startDate"));
		if(!StringUtils.isEmpty(startDateStr)) {
			try {
				Date startDate = DateUtils.getDateStart(DateUtils.stringToDate(startDateStr, DateUtils.DAY_YMD_LINE));
				page.getQuery().put("startDate", startDate);
			} catch (ParseException e) {
				logger.warn("工作量统计查询[开始时间:"+startDateStr+"]转换异常"+e.getMessage());
			}	
		}
		String endDateStr = StringUtils.valueOf(page.getQuery().get("endDate"));
		if(!StringUtils.isEmpty(page.getQuery().get("endDate").toString())) {
			try {
				Date endDate = DateUtils.getDateEnd(DateUtils.stringToDate(endDateStr, DateUtils.DAY_YMD_LINE));
				page.getQuery().put("endDate", endDate);
			} catch (ParseException e) {
				logger.warn("工作量统计查询[结束时间:"+startDateStr+"]转换异常"+e.getMessage());
			}			
		}
 		return queryForPageList(selectByConditions, page.getQuery(), page);
	}

}
