package com.jjb.unicorn.facility.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hn
 */
public class DateUtils {

	//The number of milliseconds in a minute.
	private final static long MS_IN_MINUTE = 60 * 1000;

	//The number of milliseconds in an hour.
	private final static long MS_IN_HOUR = 60 * 60 * 1000;
	
	public static final String FULL_YMD = "yyyyMMdd HHmmss";

	private static Logger log = LoggerFactory.getLogger(DateUtils.class);

	public static final String FULL_YMD_LINE = "yyyy-MM-dd HH:mm:ss";

	public static final String FULL_YMD_SOLIDUS = "yyyy/MM/dd HH:mm:ss";

	public static final String FULL_YMD_SPACER = "yyyy MM dd HH:mm:ss";

	public static final String DAY_YMD_LINE = "yyyy-MM-dd";

	public static final String DAY_YMD_SOLIDUS = "yyyy/MM/dd";

	public static final String DAY_YMD_SPACER = "yyyy MM dd";

	public static final String DAY_YMD = "yyyyMMdd";
	public static final String DAY_YMDH = "yyyyMMddHH";

	public static final String DAY_YM = "yyyyMM";
	
	public static final String FULL_YMDM="yyyyMMdd hh:mm:ss";

	public static final String FULL_YMDM_LINE = "yyyy-MM-dd HH:mm:ss.SSS";

	public static final String FULL_SECOND_LINE = "yyyy-MM-dd HH:mm:ss";

	public static final String FULL_SECOND_LINE_NO = "yyyyMMddHHmmss";
	public static final String FULL_SECOND_LINE_NO2 = "yyyyMMddHHmmssSSS";
	
	public static final String FULL_THRID_LINE = "yyyyMMddhhmmss";
	
	public static final String SECOND_LINE_NO = "HHmmss";
	
	public static final String THRID_LINE_NO = "HH:mm:ss";

	public static final String MGT_LINE = "yyyy-MM-dd'T'HH:mm:ss";
	
	public static int getAges(Date birth) {
		return DateUtils.getYears(new Date()) - DateUtils.getYears(birth);
	}

	/**
	 * Return YEAR
	 * 
	 * @return int
	 */
	public static int getYears(Date d) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(d);
		return c.get(Calendar.YEAR);
	}

	/**
	 * Return YEAR
	 * 
	 * @return int
	 */
	public static int getMonths(Date d) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(d);
		return c.get(Calendar.MONTH);
	}

	/**
	 * Return current day
	 * 
	 * @return String
	 */
	public static int getDaysOfMonth(Date d) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(d);
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Return Hours
	 * 
	 * @return int
	 */
	public static int getHoursOfDay(Date d) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(d);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * Return MINUTE
	 * 
	 * @return int
	 */
	public static int getMinutes(Date d) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(d);
		return c.get(Calendar.MINUTE);
	}

	/**
	 * Return SECOND
	 * 
	 * @return int
	 */
	public static int getSeconds(Date d) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(d);
		return c.get(Calendar.SECOND);
	}

	/**
	 * Return MILLISECOND
	 * 
	 * @return int
	 */
	public static int getMilliSeconds(Date d) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(d);
		return c.get(Calendar.MILLISECOND);
	}

	/**
	 * date add
	 * 
	 * @param date1
	 * @param mins
	 * @param format1
	 *            (yyyy-MM-dd HH:mm:ss)
	 * @return
	 */
	public static String dateAddMin(Date date1, String mins, String format1) {
		String resultDate = "";
		long ldate1 = (long) date1.getTime();
		int iMins = Integer.parseInt(mins);
		ldate1 = ldate1 + iMins * 60 * 1000;
		Date tempDate = new Date(ldate1);
		SimpleDateFormat sFormat = new SimpleDateFormat(format1);
		resultDate = sFormat.format(tempDate);
		if (resultDate == null) {
			resultDate = "";
		}
		return resultDate;

	}

	/**
	 * date add year.
	 * 
	 * @param date1
	 * @param year
	 * @return
	 */
	public static Date dateToAddyears(Date date1, int year) {
		GregorianCalendar c = new GregorianCalendar();
		c.add(Calendar.YEAR, year);
		return c.getTime();
	}

	/**
	 * Return current Year
	 * 
	 * @return String
	 */
	public static String getCurrentYear() {
		GregorianCalendar c = new GregorianCalendar();
		return String.valueOf(c.get(Calendar.YEAR));
	}

	/**
	 * Return current Year
	 * 
	 * @return int
	 */
	public static int getCurrentYearInt() {
		GregorianCalendar c = new GregorianCalendar();
		return c.get(Calendar.YEAR);
	}

	/**
	 * Return current month
	 * 
	 * @return
	 */
	public static String getCurrentWeek() {
		GregorianCalendar c = new GregorianCalendar();
		return String.valueOf(c.get(Calendar.WEEK_OF_YEAR) + 1);
	}

	/**
	 * Return current month
	 * 
	 * @return
	 */
	public static String getCurrentMonth() {
		GregorianCalendar c = new GregorianCalendar();
		return String.valueOf(c.get(Calendar.MONTH) + 1);
	}

	/**
	 * Return date at lowest time on that day.
	 * 
	 * @return String
	 */
	public static Date getDateStart(Date date) {
		if(date==null) {
			return null;
		}
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 
	 * @return
	 */
	public static Date getCurrentDate() {
		GregorianCalendar c = new GregorianCalendar();
		return c.getTime();
	}

	/**
	 * 
	 * @return
	 */
	public static Date getDate(int year, int month, int day) {
		GregorianCalendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, (month - 1));
		c.set(Calendar.DAY_OF_MONTH, day);
		return c.getTime();
	}

	/**
	 * 
	 * @return
	 */
	public static Date getDateDetail(int year, int month, int day, int hour,
			int minute, int second, int mills) {
		GregorianCalendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, (month - 1));
		c.set(Calendar.DAY_OF_MONTH, day);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, second);
		c.set(Calendar.MILLISECOND, mills);
		return c.getTime();
	}

	/**
	 * Return date at highest time on that day.
	 * 
	 * @return String
	 */
	public static Date getDateEnd(Date date) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(date);
		c.set(Calendar.HOUR, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

	/**
	 * Return current day
	 * 
	 * @return String
	 */
	public static String getCurrentDay() {
		GregorianCalendar c = new GregorianCalendar();
		return String.valueOf(c.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * Return current hour
	 * 
	 * @return
	 */
	public static String getCurrentHour() {
		GregorianCalendar c = new GregorianCalendar();
		return String.valueOf(c.get(Calendar.HOUR_OF_DAY));
	}

	/**
	 * Return current minute
	 * 
	 * @return
	 */
	public static String getCurrentMin() {
		GregorianCalendar c = new GregorianCalendar();
		return String.valueOf(c.get(Calendar.MINUTE));
	}

	/**
	 * Return current second
	 * 
	 * @return
	 */
	public static String getCurrentSec() {
		GregorianCalendar c = new GregorianCalendar();
		return String.valueOf(c.get(Calendar.SECOND));
	}

	public static Date getNextDay(Date date, int i) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(Calendar.DATE, i);

		return gc.getTime();
	}

	public static Date getNextHour(Date date, int i) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(Calendar.HOUR, i);

		return gc.getTime();
	}

	public static Date getNextYear(Date date, int i) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(Calendar.YEAR, i);

		return gc.getTime();
	}

	public static Date getNextMonth(Date date, int i) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(Calendar.MONTH, i);

		return gc.getTime();
	}

	public static Date setMonth(Date date, int i) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.set(Calendar.MONTH, i);
		return gc.getTime();
	}

	public static Date setHour(Date date, int i) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.set(Calendar.HOUR_OF_DAY, i);
		return gc.getTime();
	}

	public static Date setYear(Date date, int i) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.set(Calendar.YEAR, i);
		return gc.getTime();
	}

	public static Date setDay(Date date, int i) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.set(Calendar.DAY_OF_MONTH, i);
		return gc.getTime();
	}

	public static Date setMinute(Date date, int i) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.set(Calendar.MINUTE, i);
		return gc.getTime();
	}

	public static Date setSecond(Date date, int i) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.set(Calendar.SECOND, i);
		return gc.getTime();
	}

	public static Date setMILLISECOND(Date date, int i) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.set(Calendar.MILLISECOND, i);
		return gc.getTime();
	}

	/**
	 * convert date to pattern String
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date, String pattern) {
		try {
			if (date == null) {
				return null;
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			return dateFormat.format(date);
		} catch (Exception e) {
			log.warn("Date["+date+"]格式化["+pattern+"]失败");
			return "";
		}
	}

	/**
	 * This method generates a string representation of a date/time in the
	 * format you specify on input
	 * 
	 * @param pattern
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @see java.text.SimpleDateFormat
	 * @throws ParseException
	 */
	public static final Date stringToDate(String strDate, String pattern)
			throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(pattern);

		if (log.isDebugEnabled()) {
//			log.debug("converting '" + strDate + "' to date with mask '" + pattern + "'");
		}

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			log.error("ParseException: " + pe.getMessage());
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}

	/**
	 * 将String类型的日期数据转换成date
	 * 如果默认的格式化模型不可以，则循环各种格式的日期做转换，直到成功为止.
	 * @param dateStr
	 * @param defDateFormat
	 * @return
	 */
	public static Date stringToDate2(String dateStr,String defDateFormat){

		if(dateStr!=null && !dateStr.equals("")){
			try {
				return stringToDate(dateStr, defDateFormat);
			} catch (Exception e) {
				log.warn("ParseException: string-date["+dateStr+"]..def-format["+defDateFormat+"]",e);
			}
			List<String> dateSfList = new ArrayList<String>(); 
			dateSfList.add(DAY_YMD);//yyyyMMdd
			dateSfList.add(DAY_YM);//yyyyMM
			dateSfList.add(DAY_YMD_LINE);//yyyy-MM-dd
			dateSfList.add(FULL_YMDM);//yyyyMMdd hh:mm:ss
			dateSfList.add(FULL_SECOND_LINE_NO);//yyyyMMddHHmmss
			dateSfList.add(FULL_SECOND_LINE);//yyyy-MM-dd HH:mm:ss.SSS
			dateSfList.add(FULL_YMD);//yyyyMMdd HHmmss
			dateSfList.add(FULL_YMD_LINE);//yyyy-MM-dd HH:mm:ss
			dateSfList.add(FULL_YMD_SOLIDUS);//yyyy/MM/dd HH:mm:ss
			dateSfList.add(FULL_YMD_SPACER);//yyyy MM dd HH:mm:ss
			dateSfList.add(DAY_YMD_SOLIDUS);//yyyy/MM/dd
			dateSfList.add(DAY_YMD_SPACER);//yyyy MM dd
			for (int i = 0; i < dateSfList.size(); i++) {
				String sf = dateSfList.get(i);
				try {
					return stringToDate(dateStr, sf);
				} catch (Exception e) {
					log.warn("ParseException: string-date["+dateStr+"]..format["+sf+"]",e);
				}
			}
		}
		log.info("[{}]没有匹配到日期转换的格式",dateStr);
		
		return null;
	}
	 
	/**
	 * 计算两个日期相差天数
	 * @param start 
	 * @param end 
	 * @return 
	 */  
	public static long subtract(Date start,Date end){
		if(start==null || end==null) {
			return -9999;
		}
	    // 先个自计算出距离 1970-1-1 的日期差，然后相减  
	    long one_day = 24*60*60*1000;  
	    long stime = start.getTime();  
	    long etime = end.getTime();  
	    long sdays = stime/one_day;  
	    long edays = etime/one_day;  
	    // 若 start < 1970-1-1,则日期差了一天,需要修正  
	    if (stime < 0){  
	        sdays--;  
	    }  
	    // 若 end < 1970-1-1,则日期差了一天,需要修正  
	    if (etime < 0){  
	        edays--;  
	    }  
	    return edays - sdays;  
	}  
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int days = Integer.parseInt(getCurrentDay());

		GregorianCalendar c = new GregorianCalendar();

		c.set(Calendar.YEAR, 2009);
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);


		Date today = new Date();
		Date today1 = new Date();

		Date date = DateUtils.getDateStart(today);
		Date date1 = DateUtils.getDateStart(today);


	}
	/**
	 * 格式化-格林威治时间 
	 */
	public static String mgtFormat(Date inputDate) {

		// SimpleDateFormat(ISO_FORMAT);
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		
		String dateString = dateFormat.format(MGT_LINE);

		TimeZone tz = dateFormat.getTimeZone();
		String tzName = tz.getDisplayName();
		if (tzName.equals("Greenwich Mean Time")) {
			dateString = dateString.concat("Z");
		} else {
			long tzOffsetMS = tz.getRawOffset();
			long tzOffsetHH = tzOffsetMS / MS_IN_HOUR;
			if (tz.inDaylightTime(inputDate)) {
				tzOffsetHH = tzOffsetHH + 1;
			}
			String hourString = String.valueOf(Math.abs(tzOffsetHH));
			if (hourString.length() == 1) {
				hourString = "0" + hourString;
			}
			long tzOffsetMMMS = tzOffsetMS % MS_IN_HOUR;
			long tzOffsetMM = 0;
			if (tzOffsetMMMS != 0) {
				tzOffsetMM = tzOffsetMMMS / MS_IN_MINUTE;
			}
			String minuteString = String.valueOf(tzOffsetMM);
			if (minuteString.length() == 1) {
				minuteString = "0" + minuteString;
			}
			// String sign = "+";
			// if (String.valueOf(tzOffsetMS).indexOf("+") != -1) {
			// sign = "-";
			// }

			// dateString = dateString.concat(sign + hourString + ":"
			// + minuteString);
		}

		return dateString;
	}

	/**
	 * 解析-格林威治时间 
	 */
	public static Date mgtParse(String inputString) throws ParseException {
		// SimpleDateFormat(ISO_FORMAT);
		SimpleDateFormat isoFormat_ = new SimpleDateFormat(MGT_LINE);
		
		
		isoFormat_.setLenient(false);

		// The length of the input string should be at least 24
		if (inputString.length() < 24) {
			throw new ParseException(
					"An exception occurred because the input date/time string was not at least 24 characters in length.",
					inputString.length());
		}

		// Evaluate the the specified offset and set the time zone.
		String offsetString = inputString.substring(23);
		if (offsetString.equals("Z")) {
			isoFormat_.setTimeZone(TimeZone.getTimeZone("Greenwich Mean Time"));
		} else if (offsetString.startsWith("-") || offsetString.startsWith("+")) {
			SimpleDateFormat offsetFormat = new SimpleDateFormat();
			if (offsetString.length() == 3) {
				offsetFormat.applyPattern("HH");
			} else if (offsetString.length() == 6) {
				offsetFormat.applyPattern("HH:mm");
			} else {
				throw new ParseException(
						"An exception occurred because the offset portion was not the valid length of 3 or 6 characters.",
						25);
			}

			// Validate the given offset.
			offsetFormat.setLenient(false);
			// Date offsetDate = offsetFormat.parse(offsetString.substring(1));

			// Set the time zone with the validated offset.
			isoFormat_.setTimeZone(TimeZone.getTimeZone("GMT" + offsetString));
		} else {
			throw new ParseException(
					"An exception occurred because the offset portion of the input date/time string was not 'Z' or did not start with '+' or '-'.",
					24);
		}

		// Parse the given string.
		Date parseDate = isoFormat_.parse(inputString);

		return parseDate;
	}
	/**
     * 获取系统当前日期
     * @return
     */
    public static String getCurrDate() {
        SimpleDateFormat df = new SimpleDateFormat(DAY_YMD_LINE);
        return df.format(new Date());
    }

	/**
	 * 解决DateUtils 重置时分秒的问题
	 * @param date 时间
	 * @return
	 */
	public static Date getDateForSerch(Date date) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(date);
		return c.getTime();
	}

}
