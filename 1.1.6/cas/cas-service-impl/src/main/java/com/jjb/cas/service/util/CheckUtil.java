package com.jjb.cas.service.util;

import org.apache.commons.lang.StringUtils;

import com.jjb.acl.facility.enums.bus.IdType;
import com.jjb.ecms.util.IdentificationCodeUtil;
import com.jjb.unicorn.facility.exception.ProcessException;

/**
 * @Description: cas-service-impl判断对象类
 * @author JYData-R&D-Big Star
 * @date 2016年11月23日 上午10:28:59
 * @version V1.0
 */
public class CheckUtil {

	/**
	 * 判断对象是否为空
	 * 
	 * @param o
	 * @throws ProcessException
	 */
	public static void rejectNull(Object o) throws ProcessException {
		CheckUtil.rejectNull(o, null);
	}

	/**
	 * 判断对象是否为空，并给出错误信息
	 * 
	 * @param o
	 * @param errMsg
	 * @throws ProcessException
	 */
	public static void rejectNull(Object o, String errMsg)
			throws ProcessException {
		if (o == null) {
			if (errMsg == null) {
				throw new ProcessException("不允许为空");
			} else {
				throw new ProcessException(errMsg);
			}
		}
	}

	/**
	 * 判断对用不为空
	 * 
	 * @param o
	 * @param errMsg
	 * @throws ProcessException
	 */
	public static void rejectNotNull(Object o, String errMsg)
			throws ProcessException {
		if (o != null) {
			if (errMsg == null) {
				throw new ProcessException(o + "不为空");
			} else {
				throw new ProcessException(errMsg);
			}
		}
	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		return obj == null || obj.equals("");
	}

	/**
	 * 检查证件
	 * 
	 * @param idType
	 *            证件类型
	 * @param idNo
	 *            证件号码
	 * @return boolean true 合法 false非法
	 * @exception
	 * @since 1.0.0
	 */
	public static boolean isIdNo(IdType idType, String idNo) {
		boolean flag = true;
		if (idType == null)
			return false;
		if (StringUtils.isBlank(idNo))
			return false;
		// 判断身份证是否合法
		if (idType == IdType.I) {
			return IdentificationCodeUtil.isIdentityCode(idNo);
		}
		return flag;
	}

	/**
	 * null返回空字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String objToString(Object obj) {
		return obj == null ? "" : obj.toString();
	}

}
