package com.jjb.ecms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jjb.acl.facility.enums.bus.Gender;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: 身份证操作类
 * @author JYData-R&D-Big Star
 * @date 2016年9月5日 下午7:02:45
 * @version V1.0
 */
public class IdentificationCodeUtil {
	private static Logger logger = LoggerFactory
			.getLogger(IdentificationCodeUtil.class);
	public static final int IDENTITYCODE_OLD = 15; // 老身份证15位
	public static final int IDENTITYCODE_NEW = 18; // 新身份证18位
	public static int[] Wi = new int[17];

	/**
	 * 判断身份证号码是否正确。
	 * 
	 * @param code
	 *            身份证号码。
	 * @return 如果身份证号码正确，则返回true，否则返回false。
	 */
	public static boolean isIdentityCode(String code) {

		if (StringUtils.isEmpty(code)) {
			return false;
		}

		String birthDay = "";
		code = code.trim();

		// 长度只有15和18两种情况
		if ((code.length() != IDENTITYCODE_OLD)
				&& (code.length() != IDENTITYCODE_NEW)) {
			return false;
		}

		// 身份证号码必须为数字(18位的新身份证最后一位可以是x)
		Pattern pt = Pattern.compile("\\d{15,17}([\\dxX]{1})?");
		Matcher mt = pt.matcher(code);
		if (!mt.find()) {
			return false;
		}

		// 验证生日
		if (code.length() == IDENTITYCODE_OLD) {
			birthDay = "19" + code.substring(6, 12);
		} else {
			birthDay = code.substring(6, 14);
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		simpleDateFormat.setLenient(false);
		try {
			simpleDateFormat.format(simpleDateFormat.parse(birthDay));
		} catch (Exception e) {
			return false;
		}

		// if (!TimeUtils.isRightDate(birthDay, "yyyyMMdd")) {
		// return false;
		// }

		// 最后一位校验码验证
		if (code.length() == IDENTITYCODE_NEW) {
			String lastNum = getVerify(code.substring(0, IDENTITYCODE_NEW - 1));
			// check last digit
			if (!("" + code.charAt(IDENTITYCODE_NEW - 1)).toUpperCase().equals(
					lastNum)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 获取新身份证的最后一位:检验位
	 * 
	 * @param code
	 *            18位身份证的前17位
	 * @return 新身份证的最后一位
	 */
	private static String getCheckFlag(String code) {

		int[] varArray = new int[code.length()];
		String lastNum = "";
		int numSum = 0;
		// 初始化位权值
		setWiBuffer();
		for (int i = 0; i < code.length(); i++) {
			// varArray[i] = NumberUtil.toInt("" + code.charAt(i));
			varArray[i] = code.charAt(i);
			varArray[i] = varArray[i] * Wi[i];
			numSum = numSum + varArray[i];
		}
		int checkDigit = 12 - numSum % 11;
		switch (checkDigit) {
		case 10:
			lastNum = "X";
			break;
		case 11:
			lastNum = "0";
			break;
		case 12:
			lastNum = "1";
			break;
		default:
			lastNum = String.valueOf(checkDigit);
		}
		return lastNum;
	}

	/**
	 * @param eightcardid
	 * @return
	 */
	public static String getVerify(String eightcardid) {
		int remaining = 0;
		int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };
		int[] vi = { 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 };
		int[] ai = new int[18];
		String returnStr = null;
		try {
			if (eightcardid.length() == 18) {
				eightcardid = eightcardid.substring(0, 17);
			}
			if (eightcardid.length() == 17) {
				int sum = 0;
				String k = null;
				for (int i = 0; i < 17; i++) {
					k = eightcardid.substring(i, i + 1);
					ai[i] = Integer.parseInt(k);
					k = null;
				}
				for (int i = 0; i < 17; i++) {
					sum = sum + wi[i] * ai[i];
				}
				remaining = sum % 11;
			}
			returnStr = remaining == 2 ? "X" : String.valueOf(vi[remaining]);
		} catch (Exception ex) {
			return null;
		} finally {
			wi = null;
			vi = null;
			ai = null;
		}
		return returnStr;
	}

	/**
	 * 初始化位权值
	 */
	private static void setWiBuffer() {
		for (int i = 0; i < Wi.length; i++) {
			int k = (int) Math.pow(2, (Wi.length - i));
			Wi[i] = k % 11;
		}
	}

	/**
	 * 判别是否字符串为null或者没有内容，或者全部为空格。
	 */
	public static boolean empty(String o) {
		return ((null == o) || (o.length() <= 0) || (o.trim().equals("")));
	}

	/**
	 * 将15位身份证号码升级为18位身份证号码
	 * 
	 * @param code
	 *            15位身份证号码
	 * @return 18位身份证号码
	 */
	public static String update2eighteen(String code) {

		if (StringUtils.isEmpty(code)) {
			return "";
		}

		code = code.trim();

		if (code.length() != IDENTITYCODE_OLD || !isIdentityCode(code)) {
			return "";
		}

		code = code.substring(0, 6) + "19" + code.substring(6);
		//
		code = code + getCheckFlag(code);

		return code;
	}

	/**
	 * 15位身份证号码转化为18位的身份证。如果是18位的身份证则直接返回，不作任何变化。
	 * 
	 * @param idCard
	 *            ,15位的有效身份证号码
	 * @return idCard18 返回18位的有效身份证
	 */
	public static String IdCard15to18(String idCard) {
		idCard = idCard.trim();
		StringBuffer idCard18 = new StringBuffer(idCard);
		// 加权因子
		// int[] weight = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
		// 校验码值
		char[] checkBit = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3',
				'2' };
		int sum = 0;
		// 15位的身份证
		if (idCard != null && idCard.length() == 15) {
			idCard18.insert(6, "19");
			for (int index = 0; index < idCard18.length(); index++) {
				char c = idCard18.charAt(index);
				int ai = Integer.parseInt(new Character(c).toString());
				// sum = sum+ai*weight[index];
				// 加权因子的算法
				int Wi = ((int) Math.pow(2, idCard18.length() - index)) % 11;
				sum = sum + ai * Wi;
			}
			int indexOfCheckBit = sum % 11; // 取模
			idCard18.append(checkBit[indexOfCheckBit]);
		}
		return idCard18.toString();
	}

	/**
	 * 转化18位身份证位15位身份证。如果输入的是15位的身份证则不做任何转化，直接返回。
	 * 
	 * @param idCard
	 *            18位身份证号码
	 * @return idCard15
	 */
	public String IdCard18to15(String idCard) {
		idCard = idCard.trim();
		StringBuffer idCard15 = new StringBuffer(idCard);
		if (idCard != null && idCard.length() == 18) {
			idCard15.delete(17, 18);
			idCard15.delete(6, 8);
		}
		return idCard15.toString();

	}

	/*
	 * 根据身份证号码判断出生日期
	 */
	public static Date getBirthdayDate(String idNo) {
		try {
			if (StringUtils.isNotEmpty(idNo)) {
				SimpleDateFormat birthDay = new SimpleDateFormat("yyyyMMdd");
				birthDay.toLocalizedPattern();
				Date db = null;
				if(idNo.length() == IDENTITYCODE_NEW){
					String birthDay1 = idNo.substring(6, 14);
					db = birthDay.parse(birthDay1);
				}else if(idNo.length() == IDENTITYCODE_OLD) {
					String birthDay1 = "19"+idNo.substring(6, 12);
					db = birthDay.parse(birthDay1);
				}
				return new java.sql.Date(db.getTime());
			}
		} catch (ParseException e) {
			logger.error("客户[" + idNo + "]性别转换[yyyyMMdd]异常" + e.getMessage());
		}
		return null;
	}

	/*
	 * 根据身份证号码获取性别
	 */
	public static String getGender(String idNo) {
		try {
			if (StringUtils.isNotEmpty(idNo)) {
				String sex = null;
				if(idNo.length() == IDENTITYCODE_NEW){
					sex = idNo.substring(16, 17);
				}else if (idNo.length() == IDENTITYCODE_OLD) {
					sex = idNo.substring(14, 15);
				}
				if (Integer.parseInt(sex) % 2 == 0) {
					sex = Gender.F.toString();
				} else {
					sex = Gender.M.toString();
				}
				
				return sex;
			}
		} catch (Exception e) {
			logger.error("客户[" + idNo + "]性别转换异常" + e.getMessage());
		}
		return null;
	}

	/**
	 * 根据身份证号获取年龄
	 * @param idNo
	 * @return
	 */
	public static String getAge(String idNo) {
		if(StringUtils.isNotEmpty(idNo)){
			try {
				Integer age = null;
				Calendar calendar = Calendar.getInstance();
				if(idNo.length() == 15){
					age = calendar.get(Calendar.YEAR) - Integer.valueOf("19"+idNo.substring(6,7));
				}
				if(idNo.length() == 18){
					age = calendar.get(Calendar.YEAR) - Integer.valueOf(idNo.substring(6,10));
				}
				return age.toString();
			} catch (Exception e) {
				logger.error("客户[" + idNo + "]计算年龄异常" + e.getMessage());
			}
		}
		
		return null;
	}
	
	public static Integer getAge(Date dateOfBirth) {

		int age = 0;
		Calendar born = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		if (dateOfBirth != null) {
			now.setTime(new Date());
			born.setTime(dateOfBirth);
			if (born.after(now)) {
				throw new IllegalArgumentException(
						"出生日期有误，不能在当前时间之后！");
			}
			age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
			if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
				age -= 1;
			}
		}
		return Integer.valueOf(age);
	}
}
