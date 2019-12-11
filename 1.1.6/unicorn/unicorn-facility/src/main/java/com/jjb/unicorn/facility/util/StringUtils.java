package com.jjb.unicorn.facility.util;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: Common operation with String
 * @author JYData-R&D-Big Star
 * @date 2016年9月5日 下午7:02:45
 * @version V1.0
 */
@SuppressWarnings("unchecked")
public final class StringUtils {

	private static Logger logger = LoggerFactory.getLogger(StringUtils.class);
	public static String[] SPECIAL_CHAR_ARRAY = { ".", "*", "+", "|", "(", ")",
			"[" };

	/**
	 * The constants is used to number change.
	 */
	public static String[] STANDARD_STR = { "分", "角", "", "", "拾", "佰", "仟",
			"萬", "拾", "佰", "仟", "億", "拾", "佰", "仟", "萬", "拾", "佰", "仟", "億",
			"拾", "佰" };

	public static String[] NUMBER_STR = { "元", "零", "壹", "貳", "叁", "肆", "伍",
			"陆", "柒", "捌", "玖" };

	public static String[] KEY_STR = { "零拾", "零佰", "零仟", "零零零", "零零", "零角零分",
			"零分", "零角", "零億零萬零元", "億零萬零元", "零億零萬", "零萬零元", "萬零元", "零億", "零萬",
			"零元", "零零" };

	public static String[] MAP_STR = { "零", "零", "零", "零", "零", "正", "正", "零",
			"億元", "億元", "億", "萬元", "萬元", "億", "萬", "元", "零" };

	public static double MAX_VALUE = Long.MAX_VALUE / 100.0;

	public static double MIN_VALUE = Long.MIN_VALUE / 100.0;

	public static final String BLANK = "";
	public static final String NULL = "null";

	/**
	 * Change chinese numbers to Arabic numerals
	 * 
	 * @param C
	 *            chinese numbers
	 * @return Arabic numerals
	 */
	public static String CToN(String C) {
		if (C == null) {
			return "";
		}
		String result = "";
		String cStr = "Ｏ○ｏ０１２３４５６７８９一二三四五六七八九oO";
		String nStr = "000012345678912345678900";
		char temp;
		for (int i = 0; i < C.length(); i++) {
			temp = C.charAt(i);
			if (cStr.indexOf(String.valueOf(temp)) >= 0) {
				result = result
						+ String.valueOf(nStr.charAt(cStr.indexOf(String
								.valueOf(temp))));
			} else if (String.valueOf(temp).equals("十")) {
				if (i >= C.length() - 1
						|| cStr.indexOf(String.valueOf(C.charAt(i + 1))) < 0) {
					result += "0";
				}
				if (i == 0 || cStr.indexOf(String.valueOf(C.charAt(i - 1))) < 0) {
					result += "1";
				}
			} else {
				result += temp;
			}
		}
		return result;
	}

	/**
	 * Change Arabic numerals to chinese numbers
	 * 
	 * @param N
	 *            Arabic numerals
	 * @return chinese numbers
	 */
	public static String NToC(String N) {
		if (N == null) {
			return "";
		}
		String result = "";
		String cStr = "一二三四五六七八九十零";
		String nStr = "12345678900";
		char temp;
		for (int i = 0; i < N.length(); i++) {
			temp = N.charAt(i);
			if (nStr.indexOf(String.valueOf(temp)) >= 0) {
				result = result
						+ String.valueOf(cStr.charAt(nStr.indexOf(String
								.valueOf(temp))));
			} else {
				result += temp;
			}
		}
		return result;
	}

	/**
	 * delete chinese blank from str
	 * 
	 * @param str
	 *            String
	 * @return String
	 */
	public static String delCNBlank(String str) {
		if (str == null) {
			return "";
		}
		StringBuffer strBuf = new StringBuffer();
		str = str.trim();
		for (int i = 0; i < str.length(); i++) {
			if ("　".equals(String.valueOf(str.charAt(i)))) { // chinese blank
				strBuf.append(" "); // English blank
			}
			if (!"　".equals(String.valueOf(str.charAt(i)))) { // chinese blank
				strBuf.append(str.charAt(i));
			}
		}
		return strBuf.toString();
	}

	/**
	 * If input string is null or "null",return blank.
	 * 
	 * @param s1
	 *            input string
	 * @return "" or input string.
	 */
	public static String nullToSpaceNoTrim(String s1) {

		if (s1 == null || s1.equals("null")) {
			return "";
		}
		return s1;
	}

	/**
	 * If input string is null or "null",return blank.
	 * 
	 * @param s1
	 *            input string
	 * @return "" or input string.
	 */
	public static String nullToSpaceTrim(String s1) {

		if (s1 == null || s1.equals("null")) {
			return "";
		}
		return s1.trim();
	}

	/**
	 * If input string is null or "null",return blank.
	 * 
	 * @param s1
	 *            input string
	 * @return "" or input string.
	 */
	public static String trim2Empty(String s1) {

		if (s1 == null || s1.equals("null")) {
			return "";
		} else {
			s1 = s1.trim();
		}
		return s1;
	}

	/**
	 * Change null or blank to zero
	 * 
	 * @param s1
	 * 
	 * @return "0" or string
	 */
	public static String nullToZero(String s1) {
		if (s1 == null || s1.equals("")) {
			return "0";
		}
		return s1.trim();
	}

	/**
	 * If is not a number,change to "0".
	 * 
	 * @param s1
	 * @return
	 */
	public static long StringTolong(String s1) {
		if (isNums(s1) && isNotEmpty(s1)) {
			return Long.parseLong(s1);
		}
		return Long.parseLong("0");
	}

	/**
	 * change string to long
	 * 
	 * @param s1
	 * @return
	 */
	public static long stringTolong(Object obj) {
		String s1 = valueOf(obj);
		if (isNotEmpty(s1) && isNums(s1)) {
			if (s1.indexOf(".") > -1) {
				s1 = s1.substring(0, s1.indexOf("."));
			}
			try {
				return Long.parseLong(s1);
			} catch (Exception e) {
				logger.warn("字符["+obj+"]转换成数值异常"+e.getMessage());
			}
		}
		return Long.parseLong("0");
	}
	public static Integer stringToInteger(String abc){
		Integer i = null;
		try {
			if(isNumberString(abc)){
				i = Integer.parseInt(abc);
			}
		} catch (NumberFormatException e) {
			logger.warn("字符串["+abc+"]转换成Integer类型失败");
		}
		return i;
	}
	public static Integer stringToIntegerNotNull(String abc){
		Integer i = 0;
		try {
			abc = nullToZero(abc);
			if(isNumberString(abc)){
				i = Integer.parseInt(abc);
			}
			
		} catch (NumberFormatException e) {
			logger.warn("字符串["+abc+"]转换成Integer类型失败");
		}
		return i;
	}
	/**
	 * Change " to “
	 * 
	 * @param str
	 * 
	 * @return String
	 */
	public static String forTuiJianCard(String str) {
		String chrs = "";
		try {
			int l = str.length();
			for (int i = 0; i < l; i++) {
				char chr = str.charAt(i);
				switch (chr) {
				case '"':
					chrs = chrs + "“";
					break;
				case '\\':
					chrs = chrs + "\\\\";
					break;

				default:
					chrs = chrs + chr;
				}
			}

		} catch (Exception ex) {
			return null;
		}
		str = chrs;
		return chrs;
	}

	/**
	 * Change to html
	 * 
	 * @param str
	 * 
	 * @return
	 */
	public static String toHtml(String str) {
		String chrs = "";
		try {
			int l = str.length();
			for (int i = 0; i < l; i++) {
				char chr = str.charAt(i);
				switch (chr) {
				case '<':
					chrs = chrs + "&lt;";
					break;
				case '>':
					chrs = chrs + "&gt;";
					break;
				case '"':
					chrs = chrs + "&quot;";
					break;
				default:
					chrs = chrs + chr;
				}
			}

		} catch (Exception ex) {
			return null;
		}
		str = chrs;
		return chrs;
	}

	/**
	 * Change String array to int array.
	 * 
	 * @param strArray
	 * 
	 * @return int array.
	 */
	public int[] strArray2IntArray(String[] strArray) {
		if (strArray == null) {
			return null;
		}
		int[] intArray = new int[strArray.length];
		try {
			for (int i = 0; i < strArray.length; i++) {
				intArray[i] = Integer.parseInt(strArray[i]);
			}
		} catch (Exception ex) {
			logger.error(ex.toString());
		}
		return intArray;
	}

	/**
	 * Change array to string
	 * 
	 * @param array1
	 * 
	 * @return string that split with "'"
	 */
	public static String arrayToStr(String[] array1) {
		String sResult = "";
		if (array1 == null) {
			return sResult;
		}
		for (int i = 0; i < array1.length; i++) {
			if ("".equals(sResult)) {
				sResult = array1[i];
			} else {
				sResult += "," + array1[i];
			}
		}
		return sResult;
	}

	/**
	 * Change array to string with split Char
	 * 
	 * @param array1
	 * 
	 * @param splitChar
	 * 
	 * @return
	 */
	public static String arrayToStr(String[] array1, String splitChar) {
		String sResult = "";
		if (array1 == null) {
			return sResult;
		}
		for (int i = 0; i < array1.length; i++) {
			if ("".equals(sResult)) {
				sResult = array1[i];
			} else {
				sResult += splitChar + array1[i];
			}
		}
		return sResult;
	}

	/**
	 * Change int array to string .
	 * 
	 * @param array1
	 * 
	 * @return
	 */
	public static String arrayToStr(int[] array1) {
		return arrayToStr(array1, ",");
	}

	/**
	 * Change int array to string with split Char.
	 * 
	 * @param array1
	 * 
	 * @param splitChar
	 * 
	 * @return
	 */
	public static String arrayToStr(int[] array1, String splitChar) {
		String sResult = "";
		if (array1 == null) {
			return sResult;
		}
		for (int i = 0; i < array1.length; i++) {
			if ("".equals(sResult)) {
				sResult = String.valueOf(array1[i]);
			} else {
				sResult += splitChar + String.valueOf(array1[i]);
			}
		}
		return sResult;
	}

	/**
	 * Change Long array to string.
	 * 
	 * @param Long
	 *            []
	 * @return String
	 */
	public static String arrayToStr(Long[] ids, String splitChar) {
		StringBuffer buff = new StringBuffer("");
		if (ids == null) {
			return "";
		}
		for (int i = 0; i < ids.length; i++) {
			buff.append(String.valueOf(ids[i])).append(splitChar);
		}
		return buff.toString();
	}

	/**
	 * Get number from string.
	 * 
	 * @param str
	 * @return string
	 */
	public static String getNum(String str) {
		try {
			if (str == null || (str.trim()).length() < 1) {
				return "";
			}
			String temp = "0123456789";
			String tempString = "";
			String string = "";
			for (int i = 0; i < str.length(); i++) {
				tempString = str.substring(i, i + 1);
				if (temp.indexOf(tempString) > -1) {
					string += tempString;
				}
			}
			return string;
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	/**
	 * Get count of split char from string.
	 * 
	 * @param sList
	 * 
	 * @param splitChar
	 * 
	 * @return int
	 */
	public static int getCharNum(String sList, String splitChar) {
		if (sList == null || sList.equals("") || splitChar == null
				|| splitChar.equals("")) {
			return -1;
		}
		int StringAtLocal = sList.indexOf(splitChar);
		int StartLocal = 0;
		int iCount = 0;
		while (StringAtLocal >= 0) {
			StartLocal = StringAtLocal + splitChar.length();
			StringAtLocal = sList.indexOf(splitChar, StartLocal);
			iCount++;
		}
		return iCount;
	}

	/**
	 * Judge whether a char is between form 0 to 9.
	 * 
	 * @param cCheck
	 * 
	 * @see isNums
	 * @return true or false
	 */
	private static boolean IsDigit(char cCheck) {
		return (('0' <= cCheck) && (cCheck <= '9'));
	}

	/**
	 * Change string to int. For example:034-->34,23.3--->23,0023.4--23
	 * 
	 * @param str1
	 * 
	 * @return int
	 */
	public static int getNumToInt(String str1) {
		try {
			if (str1 == null || "".equals(str1)) {
				str1 = "0";
			}
			int flag = 0;
			String tmpStr = "";
			char cCheck;
			for (int i = 0; i < str1.length(); i++) {
				cCheck = str1.charAt(i);
				if (!IsDigit(cCheck)) {
					break;
				}
				if (cCheck != '0') {
					flag = 1;
				}
				if (cCheck == '0' && flag == 0) {
					continue;
				}
				tmpStr += String.valueOf(cCheck);
			}

			if ("".equals(tmpStr)) {
				tmpStr = "0";
			}
			return Integer.parseInt(tmpStr);
		} catch (Exception ex) {
			return -1;
		}
	}

	/**
	 * Add increase with length to the front of string of number.
	 * 
	 * @param str1
	 *            string of number
	 * @param length
	 *            the length of return string
	 * @param inscrease
	 * @return String
	 */
	public static String getChangeFormatString(String str1, int length,
			int increase) {
		try {
			if (length < 1) {
				return str1;
			}
			if (str1 == null) {
				str1 = "";
			}
			if (str1.length() > length) {
				return str1;
			}
			int maxNum = getNumToInt(str1);
			if (maxNum == -1) {
				return str1;
			}
			maxNum += increase;
			if (maxNum <= 0) {
				return str1;
			}
			String retStr = String.valueOf(maxNum);
			for (int i = 0; i < length - String.valueOf(maxNum).length(); i++) {
				retStr = "0" + retStr;
			}
			if (str1.length() > length) {
				retStr += str1.substring(length, str1.length());
			}
			return retStr;
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	/**
	 * Replace blanks or newline tags with certain characters.
	 * 
	 * @return String
	 */
	public static String returnToBr(String s) {
		if (s == null || s.equals("")) {
			return s;
		}
		StringBuffer stringbuffer = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '\n') {
				stringbuffer = stringbuffer.append("<br>");
			} else if (s.charAt(i) == '\r') {
				stringbuffer = stringbuffer.append("&nbsp;");
			} else if (s.charAt(i) == ' ') {
				stringbuffer = stringbuffer.append("&nbsp;");
			} else {
				stringbuffer.append(s.substring(i, i + 1));

			}
		}
		String s1 = stringbuffer.toString();
		return s1;
	}

	/**
	 * Remove the blanks from double end of string.
	 * 
	 * @param str
	 * @return String
	 */
	public static String trim(String str) {
		if (str == null) {
			return null;
		}
		str = str.trim();
		if (str == null) {
			return null;
		}
		return str;
	}

	/**
	 * Change string to Chinese character set.
	 * 
	 * @param str
	 * @return
	 */
	public static String CS2(String str) {
		try {
			if (str == null) {
				return null;
			}
			str = str.trim();
			if (str == null) {
				return null;
			}
			str = new String(str.getBytes("8859_1"), "GBK");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return str;
	}

	/**
	 * Change string's character set from one to another.
	 * 
	 * @param str
	 * @param srcCharset
	 * @param ObjCharset
	 * @return String
	 */
	public static String charsetChange(String str, String srcCharset,
			String ObjCharset) {
		try {
			if (str != null) {
				str = new String(str.getBytes(srcCharset), ObjCharset);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * Change numeric string to int.
	 * 
	 * @param str
	 * 
	 * @return int
	 */

	public static int parseInt(String str) {
		try {
			if(StringUtils.isEmpty(str)){
				return 0;
			}else {
				if (isInt(str)) {
					str = str.trim();
					return Integer.parseInt(str);
				} else {
					return 0;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	/**
	 * Change the special tag to HTML language.
	 * 
	 * @param str
	 * 
	 * @return String
	 */
	public static String toHtml2(String str) {
		try {
			char ch;
			int length = 0;
			String temp = "";
			if (str != null) {
				str = str.trim();
				if (str.length() != 0) {
					length = str.length();
					for (int i = 0; i < length; i++) {
						ch = str.charAt(i);
						switch (ch) {
						case (char) 13:
							temp = temp + "<br>";
							break;
						case (char) 34:
							temp = temp + "&quot;";
							break;
						case (char) 32:
							if (i < length) {
								if (str.charAt(i + 1) == ((char) 32)
										|| str.charAt(i + 1) == ((char) 9)
										|| str.charAt(i - 1) == ((char) 32)
										|| str.charAt(i - 1) == ((char) 9)) {
									temp = temp + "&nbsp;";
								} else {
									temp = temp + " ";
								}
							} else {
								temp = temp + "&nbsp;";
							}
							break;
						case (char) 9:
							temp = temp + "&nbsp;";
							break;
						case '<':
							temp = temp + "&lt;";
							break;
						case '>':
							temp = temp + "&gt;";
							break;
						default:
							temp = temp + ch;
						}
					}
				}
				str = temp;
			} else {
				str = "";
			}
			return str;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Get the length of a string that parses to bytes.
	 * 
	 * @param s
	 * 
	 * @return int
	 */
	public static int absoluteLength(String s) {
		if (s == null) {
			return 0;
		}
		try {
			return new String(s.getBytes("GBK"), "8859_1").length();
		} catch (Exception e) {
			return s.length();
		}
	}

	/**
	 * Judge a character is Ascill or others（such as Chinese, Japanese or Korea）
	 * 
	 * @param char c
	 * @return boolean, if true,c is Ascill
	 */
	public static boolean isLetter(char c) {
		int k = 0x80;
		return c / k == 0 ? true : false;
	}

	/**
	 * calculate the byte length of a string taking Chinese as two bytes exp.
	 * "test" is 4 "测试" is 4
	 * 
	 * @param src
	 * @return the byte length
	 */
	public static int byteLength(String src) {
		if (src == null)
			return 0;

		char[] chars = src.toCharArray();
		int result = 0;
		for (char c : chars) {
			if (c > 127)
				result++;
			result++;
		}
		return result;
	}

	/**
	 * calculate the byte length of a string according UTF-8
	 * 
	 * @param src
	 * @return byte length of a string exp. "test" is 4 "测试" is 6
	 */
	public static int byteLengthUtf(String src) {
		if (src == null)
			return 0;

		char[] chars = src.toCharArray();
		int result = 0;
		for (char c : chars) {
			if (c > 127)
				result += 3;
			else
				result++;
		}
		return result;
	}

	/**
	 * Add "\n" to string
	 * 
	 * @param s
	 * @param len
	 *            : the length of a line
	 * @return new string
	 */
	public List<String> strNewline(String s, int len) {
		if (s == null) {
			return null;
		}

		try {

			List<String> ls = new ArrayList<String>();

			char ch;
			char ch1 = 13;

			int len2 = s.length();

			for (int i = 0; i < len2;) {
				int tempLen = 0;
				String newStr = "";
				while (tempLen < len && i < len2) {
					ch = s.charAt(i++);
					if (ch == ch1) {
						break;
					} else if (ch < 32) {
						continue;
					} else if (ch <= 256) {
						if (ch == '\\') {
							if (tempLen == len - 2) {
								tempLen--;
							} else {
								tempLen++;
							}
						} else {
							tempLen++;
						}
					} else if (ch > 256) {
						tempLen = tempLen + 2;
					}

					if (tempLen < len - 1) {
						newStr += ch;

					} else {
						newStr += ch;
						if (i < len2 && s.charAt(i) == ch1) {
							i++;
						}
						break;
					}
				}
				ls.add(newStr);
			}
			return ls;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Add string to a set.
	 * 
	 * @param str
	 * @param delim
	 * @return Set
	 */
	@SuppressWarnings("rawtypes")
	public static Set stringTokenizer(String str, String delim) {
		if (str == null || "".equals(str)) {
			return new HashSet();
		}
		StringTokenizer st = new StringTokenizer(str, delim);
		Set afterCutSet = new HashSet();
		while (st.hasMoreTokens()) {
			String str1 = nullToZero(st.nextToken());
			afterCutSet.add(new Long(str1));
		}
		return afterCutSet;
	}

	/**
	 * Add string to a list.
	 * 
	 * @param str
	 * @param delim
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List strTokenizer(String str, String delim) {
		if (str == null || "".equals(str)) {
			return new ArrayList();
		}
		StringTokenizer st = new StringTokenizer(str, delim);
		List afterCutList = new ArrayList();
		while (st.hasMoreTokens()) {
			String str1 = nullToZero(st.nextToken());
			afterCutList.add(new Long(str1));
		}
		return afterCutList;
	}

	/**
	 * get os string
	 * 
	 * @return String
	 */
	public static String getOSName() {
		return (String) System.getProperties().get("os.name");
	}

	/**
	 * Get real path according current path.
	 * 
	 * @param clz
	 * @param cutPath
	 * @param makePath
	 * @return RealPath
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static String getRealPath(Class clz, String cutPath, String makePath) {
		try {
			Class clzObj = clz.newInstance().getClass();
			String strClassName = clzObj.getName();
			String strClassFileName = strClassName.substring(
					strClassName.lastIndexOf(".") + 1, strClassName.length());
			URL url = null;
			url = clzObj.getResource(strClassFileName + ".class");
			String strURL = url.toString();

			if (StringUtils.getOSName().indexOf("Windows") > -1) {
				strURL = strURL.substring(strURL.indexOf("/") + 1);
			} else {
				strURL = strURL.substring(strURL.indexOf("/"));
			}

			strURL = strURL.substring(0, strURL.lastIndexOf("/") + 1);

			if (cutPath != null && !"".equals(cutPath)) {
				strURL = strURL.substring(0, strURL.lastIndexOf(cutPath));
			}
			strURL = strURL + makePath;
			File file = new File(strURL);
			if (!file.exists()) {
				file.mkdirs();
			}
			return strURL;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Add string a flag by intervals.
	 * 
	 * @param data
	 * @param intervals
	 * @param flag
	 * @return String
	 */
	public static String addFlagsToStr(String data, int intervals, String flag) {
		if (data == null || data.length() <= intervals || intervals < 1
				|| flag == null || "".equals(flag)) {
			return data;
		}
		String result = "";
		int i = 1;
		int len = data.length();
		int prix = len % intervals;
		if (prix > 0) {
			result = data.substring(0, prix);
			for (; len > i * intervals; i++) {
				result = result
						+ flag
						+ data.substring((i - 1) * intervals + prix, i
								* intervals + prix);
			}
		} else {
			result = data.substring(0, intervals);
			for (; len > i * intervals; i++) {
				result = result + flag
						+ data.substring(i * intervals, (i + 1) * intervals);
			}
		}
		return result;
	}

	/**
	 * Change a string involved split flag to a Long array and the array is
	 * sorted by ascend.
	 * 
	 * @param String
	 * @return Long[]
	 */
	@SuppressWarnings("rawtypes")
	public static Long[] phaseArraysToLong(String arrays, String split) {
		String temp;
		List list = new ArrayList();
		while (arrays.length() > 0 && arrays.indexOf(split) > 0) {
			temp = arrays.substring(0, arrays.indexOf(split));
			arrays = arrays.substring(arrays.indexOf(split) + 1,
					arrays.lastIndexOf(split) + 1);
			list.add(temp);
		}
		Long[] ids = new Long[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ids[i] = Long.valueOf((String) list.get(i));
		}
		Arrays.sort(ids);
		return ids;
	}

	/**
	 * Add commas to numeric string by a certain interval length.
	 * 
	 * @param data
	 *            numeric string
	 * @param flag
	 * @return String
	 */
	public static String addCommasToDecimalStr(String data, int intervals,
			String flag) {
		if (data.indexOf(".") < 0) {
			return addFlagsToStr(data, intervals, flag);
		}
		StringBuffer buff = new StringBuffer();
		buff.append(
				addFlagsToStr(data.substring(0, data.indexOf(".")), intervals,
						flag)).append(".")
				.append(data.substring(data.indexOf(".") + 1, data.length()));
		return buff.toString();
	}

	/**
	 * Change monetary numbers to monetary words.
	 * 
	 * @param src
	 *            monetary numbers
	 * @return String
	 */
	public static String changeLNum2UNum(double src) {
		if (src == 0.0)
			return "零元正";
		else if (src > MAX_VALUE || src < MIN_VALUE)
			return "溢出";

		String result = "";

		boolean isNegative = (src < 0) ? true : false;
		long source = Math.round(src * 100.0);
		if (isNegative)
			source = -source;

		StringBuffer sb = new StringBuffer(64);

		String str = Long.toString(source);
		int length = str.length() + 1;
		if (length < 4) {
			sb.append("0/00".substring(0, 5 - length)).append(str).toString();
			length = 4;
		} else {
			sb.append(str.substring(0, length - 3)).append("/")
					.append(str.substring(length - 3)).toString();
		}

		StringBuffer resultBuffer = new StringBuffer(64);
		char a;
		for (int i = 0; i < length; i++) {
			a = sb.charAt(i);
			resultBuffer.append(NUMBER_STR[a - '/']).append(
					STANDARD_STR[length - i - 1]);
		}

		result = resultBuffer.toString();

		for (int i = 0; i < KEY_STR.length; i++)
			result = result.replaceAll(KEY_STR[i], MAP_STR[i]);

		if (result.startsWith("元"))
			result = result.substring(1);

		if (isNegative) {
			resultBuffer.delete(0, resultBuffer.length());
			result = resultBuffer.append("負").append(result).toString();
		}

		return result;
	}

	/**
	 * Add chars to string from left.
	 * 
	 * @param str
	 *            Original string.
	 * @param totalWidth
	 *            The return value's length.
	 * @param paddingChar
	 *            The char will be padded to string.
	 * @return string
	 */
	public static String padLeft(String str, int totalWidth, char paddingChar) {
		if (absoluteLength(str) >= totalWidth)
			return str;
		str = trim(str);
		int k = totalWidth - absoluteLength(str);
		for (; k > 0; k--)
			str = paddingChar + str;
		return str;
	}

	/**
	 * Get substring according to length. If str.length > substring.length, add
	 * symbol to the right of substring.
	 * 
	 * @param str
	 * @param length
	 * @param symbol
	 * @return String object.
	 */
	public static String contentOmit(String str, int length, String symbol) {

		String returnStr = null;
		if (str == null) {
			str = "";
		}
		if (symbol == null) {
			symbol = "";
		}

		returnStr = limitLength(str, length - byteLength(symbol)) + symbol;
		return returnStr;
	}

	public static String limitLength(String s, int length) {
		StringBuffer buf = new StringBuffer();
		int bytes = 0;
		for (int i = 0; i < s.length() && bytes < length; i++) {
			char c = s.charAt(i);
			if (c > 255) {
				if (bytes + 2 > length)
					break;
				bytes += 2;
			} else {
				bytes++;
			}
			buf.append(c);
		}
		return buf.toString();
	}

	/**
	 * Check whether string matches the format of email.
	 * 
	 * @param checkString
	 * @return
	 */
	public static boolean isEmail(String checkString) {
		String regString = "([a-zA-Z_0-9]+)@([a-zA-Z0-9]+)\\.([a-zA-Z]+)";
		return regexCheck(checkString, regString);
	}

	/**
	 * <p>
	 * Checks if a String is empty ("") or null.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isEmpty(null)      = true
	 * StringUtils.isEmpty("")        = true
	 * StringUtils.isEmpty(" ")       = false
	 * StringUtils.isEmpty("bob")     = false
	 * StringUtils.isEmpty("  bob  ") = false
	 * </pre>
	 * 
	 * <p>
	 * NOTE: This method changed in Lang version 2.0. It no longer trims the
	 * String. That functionality is available in isBlank().
	 * </p>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is empty or null
	 */
	public static boolean isEmpty(Object obj) {
		String str = valueOf(obj);
		str = StringUtils.nullToSpaceTrim(str); 
		return str == null || str.length() == 0 || str.equals("null");
	}
	
	/**
	 * 去空格后判断是否还是空字符串
	 * @param str
	 * @return
	 */
	public static boolean isTrimAfterEmpty(String str) {
		str = StringUtils.nullToSpaceTrim(str); 
		return str == null || str.length() == 0 || str.equals("null");
	}
	/**
	 * 去空格后判断还是个空字符串
	 * @param str
	 * @return
	 */
	public static boolean isTrimAfterNotEmpty(String str) {
		return !isTrimAfterEmpty(str);
	}
	/**
	 * <p>
	 * Checks if a String is whitespace, empty ("") or null.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isBlank(null)      = true
	 * StringUtils.isBlank("")        = true
	 * StringUtils.isBlank(" ")       = true
	 * StringUtils.isBlank("bob")     = false
	 * StringUtils.isBlank("  bob  ") = false
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is null, empty or whitespace
	 * @since 2.0
	 */
	public static boolean isBlank(String str) {

		if (isEmpty(str))
			return true;

		for (char c : str.toCharArray())
			if (c != ' ' && c != '\t')
				return false;

		return true;
	}

	/**
	 * <p>
	 * Checks if a String is not empty ("") and not null.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isNotEmpty(null)      = false
	 * StringUtils.isNotEmpty("")        = false
	 * StringUtils.isNotEmpty(" ")       = true
	 * StringUtils.isNotEmpty("bob")     = true
	 * StringUtils.isNotEmpty("  bob  ") = true
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is not empty and not null
	 */
	public static boolean isNotEmpty(Object obj) {
		return !StringUtils.isEmpty(obj);
	}

	/**
	 * <p>
	 * Checks if a String is not empty (""), not null and not whitespace only.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isNotBlank(null)      = false
	 * StringUtils.isNotBlank("")        = false
	 * StringUtils.isNotBlank(" ")       = false
	 * StringUtils.isNotBlank("bob")     = true
	 * StringUtils.isNotBlank("  bob  ") = true
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is not empty and not null and not
	 *         whitespace
	 * @since 2.0
	 */
	public static boolean isNotBlank(String str) {
		return !StringUtils.isBlank(str);
	}

	/**
	 * <p>
	 * Replaces all occurrences of a String within another String.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> reference passed to this method is a no-op.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.replace(null, *, *)        = null
	 * StringUtils.replace("", *, *)          = ""
	 * StringUtils.replace("any", null, *)    = "any"
	 * StringUtils.replace("any", *, null)    = "any"
	 * StringUtils.replace("any", "", *)      = "any"
	 * StringUtils.replace("aba", "a", null)  = "aba"
	 * StringUtils.replace("aba", "a", "")    = "b"
	 * StringUtils.replace("aba", "a", "z")   = "zbz"
	 * </pre>
	 * 
	 * @see #replace(String text, String searchString, String replacement, int
	 *      max)
	 * @param text
	 *            text to search and replace in, may be null
	 * @param searchString
	 *            the String to search for, may be null
	 * @param replacement
	 *            the String to replace it with, may be null
	 * @return the text with any replacements processed, <code>null</code> if
	 *         null String input
	 */
	public static String replace(String text, String searchString,
			String replacement) {
		return replace(text, searchString, replacement, -1);
	}

	/**
	 * <p>
	 * Replaces a String with another String inside a larger String, for the
	 * first <code>max</code> values of the search String.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> reference passed to this method is a no-op.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.replace(null, *, *, *)         = null
	 * StringUtils.replace("", *, *, *)           = ""
	 * StringUtils.replace("any", null, *, *)     = "any"
	 * StringUtils.replace("any", *, null, *)     = "any"
	 * StringUtils.replace("any", "", *, *)       = "any"
	 * StringUtils.replace("any", *, *, 0)        = "any"
	 * StringUtils.replace("abaa", "a", null, -1) = "abaa"
	 * StringUtils.replace("abaa", "a", "", -1)   = "b"
	 * StringUtils.replace("abaa", "a", "z", 0)   = "abaa"
	 * StringUtils.replace("abaa", "a", "z", 1)   = "zbaa"
	 * StringUtils.replace("abaa", "a", "z", 2)   = "zbza"
	 * StringUtils.replace("abaa", "a", "z", -1)  = "zbzz"
	 * </pre>
	 * 
	 * @param text
	 *            text to search and replace in, may be null
	 * @param searchString
	 *            the String to search for, may be null
	 * @param replacement
	 *            the String to replace it with, may be null
	 * @param max
	 *            maximum number of values to replace, or <code>-1</code> if no
	 *            maximum
	 * @return the text with any replacements processed, <code>null</code> if
	 *         null String input
	 */
	public static String replace(String text, String searchString,
			String replacement, int max) {
		if (isEmpty(text) || isEmpty(searchString) || replacement == null
				|| max == 0) {
			return text;
		}
		int start = 0;
		int end = text.indexOf(searchString, start);
		if (end == -1) {
			return text;
		}
		int replLength = searchString.length();
		int increase = replacement.length() - replLength;
		increase = (increase < 0 ? 0 : increase);
		increase *= (max < 0 ? 16 : (max > 64 ? 64 : max));
		StringBuffer buf = new StringBuffer(text.length() + increase);
		while (end != -1) {
			buf.append(text.substring(start, end)).append(replacement);
			start = end + replLength;
			if (--max == 0) {
				break;
			}
			end = text.indexOf(searchString, start);
		}
		buf.append(text.substring(start));
		return buf.toString();
	}

	/**
	 * 将字符串转化成BigDecimal格式
	 * @param str
	 * @return
	 */
	public static BigDecimal stringToBigDecimal(String str){
		BigDecimal ss = null;
		if(StringUtils.isNotEmpty(str)){
			try {
				DecimalFormat df = new DecimalFormat("#0,000.00");
				ss = new BigDecimal(df.parse(str).doubleValue());
			} catch (Exception e) {
				logger.error("字符串类型金额转换成 BigDecimal失败 ：["+str+"]");
			}
		}
		return ss;
	}
	/**
	 * 判断字段不为空，为空默认为0
	 * @param str
	 * @return
	 */
	public static BigDecimal stringToBigDecimalNotNull(String str){
		
		return stringToBigDecimal(nullToZero(str));
	}
	
	/**
	 * 大数相除，当除数为零返回0，否则正常返回除运算结果并保留小数
	 * @param bigD：被除数
	 * @param byBigD：除数
	 * @param round：小数位
	 * @return
	 */
	public static BigDecimal divideNotZero(BigDecimal bigD, BigDecimal byBigD, int round){
		if(byBigD.intValue()==0){
			return new BigDecimal(0);
		} else {
			return bigD.divide(byBigD, 4, BigDecimal.ROUND_UP);
		}
	}
	
	/**
	 * 原创
	 * 
	 * @param text
	 * @return
	 */
	public static String capitailze(String text) {
		assert isNotBlank(text) && text.length() > 1;
		return Character.toUpperCase(text.charAt(0)) + text.substring(1);
	}
	/**
	 * 将Object转换成String字符串
	 * @param obj
	 * @return
	 */
	public static String valueOf(Object obj) {
		return (obj == null) ? "" : obj.toString();
	}
	
	/**
	 * 过滤String 字符串中不可见的字符
	 * 去除特殊字符
	 * @param in
	 * @return
	 */
	public static String stripNonValidCharacters(String in) {
		String str = "";
		if (in != null && !in.equals("")) {
			StringBuffer out = new StringBuffer(); // Used to hold the output.
			Pattern p = Pattern.compile("\\s+");
			Matcher m = p.matcher(in);
			in = m.replaceAll(" ");
			in = in.replace("  ", " ");

			char current; // Used to reference the current character.

			if (in == null || ("".equals(in)))
				return ""; // vacancy test.
			for (int i = 0; i < in.length(); i++) {
				current = in.charAt(i); // NOTE: No IndexOutOfBoundsException
										// caught
										// here; it should not happen.
				if ((current == 0x9) || (current == 0xA) || (current == 0xD)
						|| ((current >= 0x20) && (current <= 0xD7FF))
						|| ((current >= 0xE000) && (current <= 0xFFFD))
						|| ((current >= 0x10000) && (current <= 0x10FFFF)))
					out.append(current);
			}
			str = out.toString();
		}
		return str;
	}
	public static boolean equals(Object obj,String eqStr){
		boolean rs = false;
		String str = valueOf(obj);
		if(eqStr==null || eqStr.equals("")){
			return str.equals(eqStr);
		}else if(isNotEmpty(str)){
			return str.equals(eqStr);
		}
		return rs;
	}
	/**
	 * 判断字符中是否包含另外一个字符串
	 * @param obj
	 * @param eqStr
	 * @return
	 */
	public static boolean concat(Object obj,String eqStr){
		boolean rs = false;
		String str = valueOf(obj);
		if(eqStr==null){
			return rs;
		}else if(isNotEmpty(str)){
			return str.contains(eqStr);
		}
		return rs;
	}
	
	/**
	 * 拼接两个字符串以竖线隔开
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String jointStrToVertical(String str1,String str2){
		if(StringUtils.isNotEmpty(str2)){
			if(StringUtils.isEmpty(str1)){
				return str2;
			}
			if(!str1.contains(str2)){
				return str1+"|"+str2;
			}
			return str1;
		}
		return str1;
	}
	/**
	 * 如果object为空，则返回为value，否则返回object，可用此方法代替三目运算
	 * @param object
	 * @param value
	 */
	public static String setValue(Object object, Object value){
		String str1 = valueOf(object);
		String str2 = valueOf(value);
		if(StringUtils.isEmpty(str1)){
			return str2;
		}else{
			return str1;
		}
	}
	
	/**
	 * Check whether string is int.
	 * 
	 * @param numberString
	 * @return boolean
	 */
	public static boolean isInt(String numberString) {
		String regexStr = "-*" + "\\d*";
		return regexCheck(numberString, regexStr);
	}

	/**
	 * Check whether string is make from double.
	 * <p>
	 * isNumber("0"): false ;
	 * </p>
	 * <p>
	 * isNumber("213"): true ;
	 * </p>
	 * <p>
	 * isNumber("124312a"): false
	 * </p>
	 * <p>
	 * isNumber("124312a.13"): false
	 * </p>
	 * <p>
	 * isNumber("12.431.213"): false
	 * </p>
	 * <p>
	 * isNumber("124312.13"): true
	 * </p>
	 * 
	 * @param numberString
	 * @return
	 */
	public static boolean isNums(String numberString) {
//		String regexStr = "^[-\\+]?[\\d]*$";//整数
		String regexStr ="^(-?\\d+)(\\.\\d+)?$";//正负+浮点数
//		String regexStr = "-*" + "\\d*" + "." + "\\d*";
		return regexCheck(numberString, regexStr);
	}

	/**
	 * check if a string contains only 0-9
	 * 
	 * @param numberString
	 * @return
	 */
	public static boolean isNumberString(String numberString) {
		if (isEmpty(numberString)) {
			return false;
		}
		char[] chars = numberString.toCharArray();
		for (char c : chars) {
			if (c < '0' || c > '9')
				return false;
		}
		return true;
	}

	/**
	 * Check whether url is right.
	 * 
	 * @param checkString
	 * @return
	 */
	public static boolean isURL(String url) {
		String regString = "http://([\\w-]+\\.)+[\\w-]+(/[\\w-   ./?%&=]*)?";
		return regexCheck(url, regString);
	}

	/**
	 * Check whether string matches regex.
	 * 
	 * @param checkString
	 * @param regexStr
	 * @return boolean
	 */
	public static boolean regexCheck(String checkString, String regexStr) {
		Pattern pattern = Pattern.compile(regexStr);
		Matcher matcher = pattern.matcher(checkString);
		return matcher.find();
	}

	public static void testReg() {

		Pattern p = Pattern.compile("[.,\"\\?!:']");// 增加对应的标点

		Matcher m = p
				.matcher("I am a, I am \"Hello\" I. ok? hello! hello: ok.");

		String first = m.replaceAll(" ");


		p = Pattern.compile(" {2,}");// 去除多余空格

		m = p.matcher(first);

		String second = m.replaceAll(" ");


	}

	/**
	 * 
	 * get sub string
	 * 
	 * @param str
	 * 
	 * @param byteBeginIndex
	 * 
	 * @param byteEndIndex
	 * 
	 * @return sub string
	 * 
	 */
	public static String subStringByByte(String str, int byteBeginIndex,
			int byteEndIndex) {

		if (str == null || "".equals(str) || byteBeginIndex > byteEndIndex) {
			return "";
		}

		String result = "";
		int charLength = 0;
		int tempIndex1 = 0;
		int tempIndex2 = 0;
		int charBeginIndex = -1;
		int charEndIndex = -1;

		if (byteEndIndex > byteBeginIndex && byteBeginIndex >= 0) {
			for (int i = 0; i < str.length(); i++) {
				charLength = str.substring(i, i + 1).getBytes().length;
				tempIndex1 = tempIndex2;
				tempIndex2 += charLength;

				if (byteBeginIndex >= tempIndex1 && byteBeginIndex < tempIndex2) {
					charBeginIndex = i;
				}

				if (byteEndIndex >= tempIndex1 && byteEndIndex < tempIndex2) {
					charEndIndex = byteEndIndex == tempIndex1 ? i : i + 1;
					break;
				}
			}

			charEndIndex = charEndIndex == -1 ? (charBeginIndex == -1 ? 0 : str
					.length()) : charEndIndex;
			charBeginIndex = charBeginIndex == -1 ? 0 : charBeginIndex;
			result = str.substring(charBeginIndex, charEndIndex);
		}

		return result;
	}

	/**
	 * To SBC case.
	 * 
	 * The code of spacer of SBC is 12288，DBC is 32. The value between of other
	 * characters of DBC code(33-126) and SBC code(65281-65374)is 65248.
	 * 
	 * @param input
	 *            String
	 * @return String SBC case.
	 */
	public static String ToSBC(String input) {
		if (input == null) {
			return input;
		}
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 32) {
				c[i] = (char) 12288;
				continue;
			}
			if (c[i] < 127) {
				c[i] = (char) (c[i] + 65248);
			}
		}
		return new String(c);
	}

	/**
	 * to DBC case
	 * 
	 * The code of spacer of SBC is 12288，DBC is 32. The value between of other
	 * characters of DBC code(33-126) and SBC code(65281-65374)is 65248.
	 * 
	 * @param input
	 *            String
	 * @return String DBC case
	 */
	public static String ToDBC(String input) {
		if (input == null) {
			return input;
		}
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375) {
				c[i] = (char) (c[i] - 65248);
			}
		}
		return new String(c);
	}

	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	public static boolean isLetter(String strName) {
		strName = strName.replaceAll(" ", "");
		return strName.matches("^[a-zA-Z]+$");
	}

	public static boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c) != true) {
				return false;
			}
		}
		return true;

	}

	/**
	 * @param input
	 * @param regex
	 * @return
	 */
	public static String[] split(String input, String regex) {
		if (trim2Empty(input).equals(""))
			return null;
		if (ArrayUtils.indexOf(SPECIAL_CHAR_ARRAY, regex) > -1) {
			regex = "\\" + regex;
		}
		return input.split(regex);
	}

/**
	* <pre>
	* Given a string, this method replaces all occurrences of 
	*  '<' with '<', all occurrences of '>' with 
	*  '>', and (to handle cases that occur inside attribute 
	*  values), all occurrences of double quotes with 
	*  '"' and all occurrences of '&' with '&'. 
	*  Without such filtering, an arbitrary string 
	*  could not safely be inserted in a Web page. 
	*  </pre>
*/
	public static String filter(String input) {
		if (!hasSpecialChars(input)) {
			return (input);
		}
		StringBuffer filtered = new StringBuffer(input.length());
		char c;
		for (int i = 0; i < input.length(); i++) {
			c = input.charAt(i);
			if (c == '<') {
				filtered.append("<");
			} else if (c == '>') {
				filtered.append(">");
			} else if (c == '"') {
				filtered.append("\"");
			} else if (c == '&') {
				filtered.append("&");
			} else {
				filtered.append(c);
			}
		}
		return (filtered.toString());
	}

	/**
	 * @param input
	 * @return
	 */
	public static boolean hasSpecialChars(String input) {
		boolean flag = false;
		if ((input != null) && (input.length() > 0)) {
			char c;
			for (int i = 0; i < input.length(); i++) {
				c = input.charAt(i);
				switch (c) {
				case '<':
					flag = true;
					break;
				case '>':
					flag = true;
					break;
				case '"':
					flag = true;
					break;
				case '&':
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

}
