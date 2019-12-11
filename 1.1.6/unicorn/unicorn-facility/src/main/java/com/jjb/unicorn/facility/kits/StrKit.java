package com.jjb.unicorn.facility.kits;

import java.util.List;
import java.util.Map;

/**
 * 
 * str工具类，与apache-commons的相比，此为简化版
 * @author jjb
 *
 */
public class StrKit {
	
	/**
	 * 首字母变小写
	 */
	public static String firstCharToLowerCase(String str) {
		char firstChar = str.charAt(0);
		if (firstChar >= 'A' && firstChar <= 'Z') {
			char[] arr = str.toCharArray();
			arr[0] += ('a' - 'A');
			return new String(arr);
		}
		return str;
	}
	
	/**
	 * 首字母变大写
	 */
	public static String firstCharToUpperCase(String str) {
		char firstChar = str.charAt(0);
		if (firstChar >= 'a' && firstChar <= 'z') {
			char[] arr = str.toCharArray();
			arr[0] -= ('a' - 'A');
			return new String(arr);
		}
		return str;
	}
	
	/**
	 * 字符串为 null 或者为  "" 时返回 true
	 */
	public static boolean isBlank(String str) {
		return str == null || "".equals(str.trim());
	}
	
	/**
	 * 字符串不为 null 而且不为  "" 时返回 true
	 */
	public static boolean notBlank(String str) {
		return str != null && !"".equals(str.trim());
	}
	/**
	 * 字符串数组，每一个都不为 null 而且不为  "" 时返回 true
	 * @param strings
	 * @return
	 */
	public static boolean notBlank(String... strings) {
		if (strings == null)
			return false;
		for (String str : strings)
			if (str == null || "".equals(str.trim()))
				return false;
		return true;
	}
	
	/**
	 * 对象或对象数组，每一个都不为 null 时返回 true
	 * @param strings
	 * @return
	 */
	public static boolean notNull(Object... paras) {
		if (paras == null)
			return false;
		for (Object obj : paras)
			if (obj == null)
				return false;
		return true;
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean notBlankList(List list){
		if(list!=null&&list.size()>0){
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean notBlankMap(Map map){
		if(map!=null&&map.size()>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 将带下划线的字段转为驼峰式命名
	 * @param stringWithUnderline
	 * @return
	 */
	public static String toCamelCase(String stringWithUnderline) {
		if (stringWithUnderline.indexOf('_') == -1)
			return stringWithUnderline;
		
		stringWithUnderline = stringWithUnderline.toLowerCase();
		char[] fromArray = stringWithUnderline.toCharArray();
		char[] toArray = new char[fromArray.length];
		int j = 0;
		for (int i=0; i<fromArray.length; i++) {
			if (fromArray[i] == '_') {
				// 当前字符为下划线时，将指针后移一位，将紧随下划线后面一个字符转成大写并存放
				i++;
				if (i < fromArray.length)
					toArray[j++] = Character.toUpperCase(fromArray[i]);
			}
			else {
				toArray[j++] = fromArray[i];
			}
		}
		return new String(toArray, 0, j);
	}
	
	/**
	 * 字符数组转为字符串，中间以separator连接
	 * @param stringArray
	 * @param separator 连接符
	 * @return
	 */
	public static String join(String[] stringArray, String separator) {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<stringArray.length; i++) {
			if (i>0)
				sb.append(separator);
			sb.append(stringArray[i]);
		}
		return sb.toString();
	}
	
	/**
	 * 替换字符串中的特殊字符
	 * @param str
	 * @param newChar
	 * @return
	 */
	public static String replaceSpecial(String str,String newChar){
		String[] specials = {"-", "+" , "*", "?" ,"[" , "]" , "(" , ")" , "{" ,"}"}; 
		String pattern = "[\\"+StrKit.join(specials, "\\")+"]";
		return str.replaceAll(pattern, newChar);
	}
	
}

