package com.jjb.ecms.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: 汉字转拼音
 * @author JYData-R&D-Big T.T
 * @date 2016年11月15日 上午11:02:48
 * @version V1.0
 */
@SuppressWarnings("rawtypes")
public class ChineseToPinYin {
	private static Logger logger = LoggerFactory.getLogger(ChineseToPinYin.class);

	/**
	 * 获取姓名全拼
	 * @param cnStr 要转换的姓名
	 * @return 姓名大写全拼，姓和名之间加空格（三个字以内姓取第一个字，三个字以上姓取前两个字）， 非中文字符保留原样
	 */
	public static String getFullSpell(String cnStr) {
		logger.info("要转换的文字：{}", cnStr);
		if (null == cnStr || "".equals(cnStr.trim())) {
			return cnStr;
		}
		
		int spaceIndex = cnStr.length() > 3 ? 2 : 1;// 计算空格添加位置（三个字以内姓取第一个字，三个字以上姓取前两个字）
		StringBuilder result = new StringBuilder();// 转换结果
		// 遍历所有字符并转换，非中文字符保留原样
		char[] charArr = cnStr.toCharArray();
		for (int i = 0; i < charArr.length; i++) {
			if(i == spaceIndex){
				result.append(" ");// 姓和名之间加空格
			}
			result.append(charToPinYin(charArr[i]));
		}
		logger.info("转换结果：{}", result);
		return result.toString();
	}

	/**
	 * 获取单个字符全拼
	 * @param c 字符
	 * @return 大写全拼无声调，非中文字符保留原样
	 */
	private static String charToPinYin(char c) {
		String result;
		try {
			// PinyinHelper.addPinyinDict("/user.dict"); // 支持添加自定义字典
			result = org.apache.commons.lang.StringUtils.upperCase(PinyinHelper.convertToPinyinString(StringUtils.valueOf(c), "", PinyinFormat.WITHOUT_TONE));
		} catch (PinyinException e) {
			logger.error("汉字转拼音异常，不能识别的文字：{}", c);
			return StringUtils.valueOf(c);
		}
		return result;
	}
	
}
