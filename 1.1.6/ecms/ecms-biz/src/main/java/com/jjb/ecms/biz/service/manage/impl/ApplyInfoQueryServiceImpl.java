package com.jjb.ecms.biz.service.manage.impl;

import java.text.ParseException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.dao.manage.ApplyInfoQueryDao;
import com.jjb.ecms.biz.service.manage.ApplyInfoQueryService;
import com.jjb.ecms.facility.dto.ApplyInfoQueryDto;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @description 
 * @author J.J
 * @date 2017年10月30日上午10:10:57
 */
@Service("applyInfoQueryService")
public class ApplyInfoQueryServiceImpl implements ApplyInfoQueryService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ApplyInfoQueryDao applyInfoQueryDao;
	
	
	@Override
	@Transactional
	public Page<ApplyInfoQueryDto> applyInfoList(Page<ApplyInfoQueryDto> page) {
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		if(page.getQuery()!=null&&page.getQuery().size()>0){
			//给开始日期和截至日期附加时间部分
			Object dateObj1= page.getQuery().get("beginDate");
			Date beginDate = null;
			if(StringUtils.isNotEmpty(dateObj1) && dateObj1 instanceof String) {
				String str = StringUtils.valueOf(dateObj1);
				try {
					if(str.indexOf('-') !=-1) {
						beginDate = DateUtils.getDateStart(DateUtils.stringToDate(str, DateUtils.DAY_YMD_LINE));
					}else{
						beginDate = DateUtils.getDateStart(DateUtils.stringToDate(str, DateUtils.DAY_YMD));
					}
				} catch (ParseException e) {
					logger.error("案件转分配查询时间格式转换发生异常", e);
				}
			}else if(StringUtils.isNotEmpty(dateObj1) && dateObj1 instanceof Date) {
				beginDate = DateUtils.getDateStart((Date)dateObj1);
			}
			if(beginDate!=null) {
				page.getQuery().put("beginDate", beginDate);
			}
			Object dateObj2= page.getQuery().get("endDate");
			Date endDate = null;
			if(StringUtils.isNotEmpty(dateObj2) && dateObj2 instanceof String) {
				String str = StringUtils.valueOf(dateObj2);
				try {
					if(str.indexOf('-') !=-1) {
						endDate = DateUtils.getDateEnd(DateUtils.stringToDate(str, DateUtils.DAY_YMD_LINE));
					}else{
						endDate = DateUtils.getDateEnd(DateUtils.stringToDate(str, DateUtils.DAY_YMD));
					}
				} catch (ParseException e) {
					logger.error("案件转分配查询时间格式转换发生异常", e);
				}
			}else if(StringUtils.isNotEmpty(dateObj2) && dateObj2 instanceof Date) {
				endDate = DateUtils.getDateEnd((Date)dateObj2);
			}
			if(endDate!=null) {
				page.getQuery().put("endDate", endDate);
			}
		}

		return applyInfoQueryDao.applyInfoList(page);
	}


}
