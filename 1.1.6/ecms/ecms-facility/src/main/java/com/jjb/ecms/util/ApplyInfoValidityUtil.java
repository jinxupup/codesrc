package com.jjb.ecms.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import com.jjb.unicorn.facility.exception.ProcessException;

/**
 * 
 * @Description: 申请资料验证用要素
 * @author JYData-R&D-Big Star
 * @date 2016年9月5日 下午7:02:00
 * @version V1.0
 */
public class ApplyInfoValidityUtil {

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
		int sexCode = -1;

		String paiSex = null;

		if (!StringUtils.isEmpty(idNo)) {
			boolean isValiate = IdentificationCodeUtil.isIdentityCode(idNo);
			if (isValiate) {
				sexCode = Integer.parseInt(idNo.substring(16, 17));
			}
			paiSex = (sexCode % 2 == 0 ? "F" : "M");
		}

		if (paiSex != null && grand != null && paiSex.toString().equals(grand)) {
			result = true;
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
		StringBuffer paiBirthdate = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		if (!StringUtils.isEmpty(idNo)) {
			boolean isValiate = IdentificationCodeUtil.isIdentityCode(idNo);
			if (isValiate) {
				// 验证生日
				if (18 == idNo.length()) {
					paiBirthdate.append(idNo.substring(6, 10)).append("/")
							.append(idNo.substring(10, 12)).append("/")
							.append(idNo.substring(12, 14));
				}
			}
			if (paiBirthdate.toString().equals(sdf.format(birthdate))) {
				result = true;
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
	
	public static <T> Map<String, Serializable> fillEntityFieldMap(Object obj,
			Class<T> templateClazz, Logger logger) {
		return fillEntityFieldMap(obj, templateClazz, logger, null, null);
	}

	/**
	 * 根据传入模板类，将数据对象中符合模板中属性填充到map中
	 * 
	 * @param obj
	 *            数据对象
	 * @param templateClazz
	 *            模板类
	 * @param logger
	 *            日志
	 * @param prefix
	 *            前缀
	 * @param distMap
	 *            目标map 如果为空 new一个
	 * @return
	 */
	public static <T> Map<String, Serializable> fillEntityFieldMap(Object obj,
			Class<T> templateClazz, Logger logger, String prefix,
			Map<String, Serializable> distMap) {
		if (distMap == null) {
			distMap = new HashMap<String, Serializable>();// 目标map
		}
		Class<? extends Object> srcClazz = obj.getClass();// 数据对象类
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(templateClazz);
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();// 模板中属性
			if (propertyDescriptors != null) {
				for (PropertyDescriptor pd : propertyDescriptors) {
					if (pd != null) {
						String fieldName = pd.getName();
						String srcFieldName = fieldName;
						PropertyDescriptor srcPd = null;
						if (StringUtils.isNotBlank(prefix)) {
							srcFieldName = prefix
									+ toUpperCaseFirstOne(fieldName);
						}

						try {
							srcPd = new PropertyDescriptor(srcFieldName,
									srcClazz);
							logger.debug("get方法名[{}],属性名[{}],模板属性名[{}]", srcPd
									.getReadMethod().getName(), srcFieldName,
									fieldName);
							Serializable value = (Serializable) (srcPd
									.getReadMethod().invoke(obj));
							if (value != null) {
								distMap.put(fieldName, value);
							}
						} catch (Exception e) {
							logger.debug("数据对象类中没有该属性[{}],异常信息[{}]",
									srcFieldName, e.getMessage());
						}
					}
				}
			}
		} catch (IntrospectionException e) {
			throw new ProcessException(e.getMessage());
		}
		return distMap;
	}

	// 首字母转大写
	public static String toUpperCaseFirstOne(String name) {
		char[] ch = name.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			if (i == 0) {
				ch[0] = Character.toUpperCase(ch[0]);
			}
		}
		return new StringBuilder().append(ch).toString();
	}

	// 首字母转小写
	public static String toLowerCaseFirstOne(String name) {
		char[] ch = name.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			if (i == 0) {
				ch[0] = Character.toLowerCase(ch[0]);
			}
		}
		return new StringBuilder().append(ch).toString();
	}
}
