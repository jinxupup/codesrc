package com.jjb.ecms.app.controller.utils;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.DateUtils;
/**
 * 
 * @ClassName ConvertEndDateUtil
 * @Description TODO
 * @author JJ.G
 * @Date 2018年1月8日 下午5:54:47
 * @version 1.0.0
 */
public class ConvertEndDateUtil {

	/**
	 * 将前台页面输入的查询结束日期加一天
	 * @param query
	 * @param endDateName 
	 * @throws ParseException
	 */
	public static void convertEndDate(Query query, String endDateName) throws ParseException {
		Object endDate = query.get(endDateName);
		if(endDate != null && !"".equals(endDate)){
			Date newEndDate  = DateUtils.stringToDate((String)endDate, DateUtils.DAY_YMD_LINE);
			query.put(endDateName, DateUtils.dateToString(DateUtils.getNextDay(newEndDate, 1), DateUtils.DAY_YMD_LINE));
		}
	}
	
	public static void convertEndDate(Map<String, Object> query, String endDateName) throws ParseException {
		Object endDate = query.get(endDateName);
		if(endDate != null && !"".equals(endDate)){
			Date newEndDate  = DateUtils.stringToDate((String)endDate, DateUtils.DAY_YMD_LINE);
			query.put(endDateName, DateUtils.dateToString(DateUtils.getNextDay(newEndDate, 1), DateUtils.DAY_YMD_LINE));
		}
	}
	
}
