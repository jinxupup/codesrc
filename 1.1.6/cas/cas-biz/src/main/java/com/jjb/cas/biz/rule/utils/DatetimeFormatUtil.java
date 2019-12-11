package com.jjb.cas.biz.rule.utils;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期格式化工具
 * 
 * @author D.C
 *
 */
public class DatetimeFormatUtil {
	/***************************************************************************
	 * The number of milliseconds in a minute.
	 */
	private final static long MS_IN_MINUTE = 60 * 1000;

	/***************************************************************************
	 * The number of milliseconds in an hour.
	 */
	private final static long MS_IN_HOUR = 60 * 60 * 1000;

	/***************************************************************************
	 * The number of milliseconds in a day.
	 */
	// private final static long MS_IN_DAY = 24 * 60 * 60 * 1000;
	/**
	 * 本地日期格式符号表
	 */
	public final static DateFormatSymbols localDateFormatSymbols = new DateFormatSymbols(
			Locale.getDefault());

	/**
	 * 月年格式
	 */
	public static final SimpleDateFormat monthYearFormat = new SimpleDateFormat(
			"MMM yyyy", Locale.getDefault());

	/**
	 * 年月格式
	 */
	public static final SimpleDateFormat monthFormat = new SimpleDateFormat(
			"yyyy-MM");

	/**
	 * 日格式
	 */
	public static final SimpleDateFormat dayFormat = new SimpleDateFormat("d");

	/**
	 * 时间格式
	 */
	public static final SimpleDateFormat minuteFormat = new SimpleDateFormat(
			"HH:mm");

	/**
	 * 时间格式
	 */
	public static final SimpleDateFormat timeFormat = new SimpleDateFormat(
			"HH:mm:ss");

	/**
	 * 24小时的时间 格式
	 */
	public static final SimpleDateFormat time24Format = new SimpleDateFormat(
			"HH:mm:ss");

	/**
	 * 星期格式
	 */
	public static final SimpleDateFormat weekFormat = new SimpleDateFormat(
			"EEE");

	/**
	 * 年月日 短格式
	 */
	public static final SimpleDateFormat shortDateFormat = new SimpleDateFormat(
			"yy-MM-dd");

	/**
	 * 年月日 长格式
	 */
	public static final SimpleDateFormat longDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	/**
	 * 年月日 时分秒 短格式
	 */
	public final static SimpleDateFormat shortDatetimeFormat = new SimpleDateFormat(
			"yy-MM-dd HH:mm:ss");

	/**
	 * 年月日 时分秒 长格式
	 */
	public final static SimpleDateFormat longDatetimeFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private final static SimpleDateFormat isoFormat_ = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss");

	public static String isoFormat(Date inputDate) {

		// SimpleDateFormat(ISO_FORMAT);
		String dateString = isoFormat_.format(inputDate);

		TimeZone tz = isoFormat_.getTimeZone();
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

	public static Date isoParse(String inputString) throws ParseException {

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
}