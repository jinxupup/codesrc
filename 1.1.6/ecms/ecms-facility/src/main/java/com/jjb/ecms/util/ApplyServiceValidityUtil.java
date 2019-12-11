package com.jjb.ecms.util;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: 申请资料验证用要素
 * @author JYData-R&D-Big Star
 * @date 2016年11月23日 上午10:56:56
 * @version V1.0
 */
public class ApplyServiceValidityUtil {

	/**
	 * 手机号码验证,11位，不知道详细的手机号码段，只是验证开头必须是1和位数
	 * */
	public static boolean isPhone(String cellPhoneNr) {
		String reg = "^[1][\\d]{10}";
		return startCheck(reg, cellPhoneNr);
	}

	/**
	 * 正则表达式的检验 startCheck(这里用一句话描述这个方法的作用)
	 * 
	 * @param reg
	 * @param string
	 * @return boolean
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean startCheck(String reg, String string) {
		boolean tem = false;
		if (string == null) {
			return tem;
		}
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(string);
		tem = matcher.matches();
		return tem;
	}

	/**
	 * 性别是否相同
	 * 
	 * @param idNo
	 * @param birthdate
	 * @return
	 */
	public static boolean isGender(String idNo, String grand) {
		boolean result = false;

		// 根据身份证取得性别和出生日期信息
		if (!StringUtils.isEmpty(idNo)) {
			boolean isValiate = IdentificationCodeUtil.isIdentityCode(idNo);
			if (isValiate) {
				String paiSex = IdentificationCodeUtil.getGender(idNo);
				if (paiSex != null && grand != null && paiSex.equals(grand)) {
					result = true;
				}
			}
		}
		
		return result;

	}

	/**
	 * 生日是否相同
	 * 
	 * @param idNo
	 * @param birthdate
	 * @return
	 */
	public static boolean isBirthday(String idNo, Date birthdate) {
		boolean result = false;
		// 根据身份证取得性别和出生日期信息
		if (!StringUtils.isEmpty(idNo)) {
			boolean isValiate = IdentificationCodeUtil.isIdentityCode(idNo);
			if (isValiate) {
				// 验证生日
				Date birthday = IdentificationCodeUtil.getBirthdayDate(idNo);
				if(birthday != null && birthdate != null && birthday.compareTo(birthdate) == 0){
					result = true;
				}
			}
		}

		return result;

	}

	/**
	 * 国内电话校验
	 * 
	 * @param item
	 * @return
	 */
	public static boolean isFixePhone(String item) {

		boolean result = false;
		if ((isPhoneNr(item) || isphonenrwithoutcode(item) || isPhoneNrWithoutLine(item))) {
			result = true;
		}
		return result;
	}

	/**
	 * 验证国内电话号码 格式：010-67676767，区号长度3-4位，必须以"0"开头，号码是7-8位
	 * */
	public static boolean isPhoneNr(String phoneNr) {
		String regex = "^[0]\\d{2,3}\\-\\d{7,8}|^[0]\\d{2,3}\\-\\d{7,8}-\\d{1,4}";
		return startCheck(regex, phoneNr);
	}

	/**
	 * 验证国内电话号码 格式：6767676, 号码位数必须是7-8位,头一位不能是"0"
	 * */
	public static boolean isphonenrwithoutcode(String phoneNr) {
		String reg = "^[1-9]\\d{6,7}";
		return startCheck(reg, phoneNr);
	}

	/**
	 * 验证国内电话号码 格式：0106767676，共11位或者12位，必须是0开头
	 * */
	public static boolean isPhoneNrWithoutLine(String phoneNr) {
		String reg = "^[0]\\d{10,11}";
		return startCheck(reg, phoneNr);
	}

	/**
	 * email格式是否正确
	 * 
	 * @param item
	 * @return
	 */
	public static boolean isEmail(String item) {

		boolean result = false;

		String reg = "^([a-zA-Z0-9_.\\-+])+@(([a-zA-Z0-9\\-])+\\.)+[a-zA-Z0-9]{2,4}";
		if (startCheck(reg, item)) {
			result = true;
		}

		return result;
	}
}
