/**
 * 
 */
package com.jjb.ecms.facility.dto;

import java.io.Serializable;

/**
 * @Description: 字段校验信息实体类
 * @author JYData-R&D-Big T.T
 * @date 2017年12月1日 下午4:16:26
 * @version V1.0  
 */

public class ValidFieldInfoDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Boolean betweenFlag = false;//区间取值的标志
	
	private String betweenMin;//区间最小值
	
	private String betweenMax;//区间最大值

	private Boolean lengthFlag = false;//字符串长度限制标志
	
	private String lengthMax;//字符串最大值
	
	private Boolean regexpFlag = false;//正则表达式标志
	
	private String regexp;//正则表达式的值

	private String field;//字段
	
	private String fieldName;//字段名称
	
	private Boolean notEmptyFlag = false;//不为空的标志
	
	/**
	 * @return the betweenFlag
	 */
	public Boolean getBetweenFlag() {
		return betweenFlag;
	}

	/**
	 * @param betweenFlag the betweenFlag to set
	 */
	public void setBetweenFlag(Boolean betweenFlag) {
		this.betweenFlag = betweenFlag;
	}

	/**
	 * @return the betweenMin
	 */
	public String getBetweenMin() {
		return betweenMin;
	}

	/**
	 * @param betweenMin the betweenMin to set
	 */
	public void setBetweenMin(String betweenMin) {
		this.betweenMin = betweenMin;
	}

	/**
	 * @return the betweenMax
	 */
	public String getBetweenMax() {
		return betweenMax;
	}

	/**
	 * @param betweenMax the betweenMax to set
	 */
	public void setBetweenMax(String betweenMax) {
		this.betweenMax = betweenMax;
	}

	/**
	 * @return the lengthFlag
	 */
	public Boolean getLengthFlag() {
		return lengthFlag;
	}

	/**
	 * @param lengthFlag the lengthFlag to set
	 */
	public void setLengthFlag(Boolean lengthFlag) {
		this.lengthFlag = lengthFlag;
	}

	/**
	 * @return the lengthMax
	 */
	public String getLengthMax() {
		return lengthMax;
	}

	/**
	 * @param lengthMax the lengthMax to set
	 */
	public void setLengthMax(String lengthMax) {
		this.lengthMax = lengthMax;
	}

	/**
	 * @return the regexpFlag
	 */
	public Boolean getRegexpFlag() {
		return regexpFlag;
	}

	/**
	 * @param regexpFlag the regexpFlag to set
	 */
	public void setRegexpFlag(Boolean regexpFlag) {
		this.regexpFlag = regexpFlag;
	}

	/**
	 * @return the regexp
	 */
	public String getRegexp() {
		return regexp;
	}

	/**
	 * @param regexp the regexp to set
	 */
	public void setRegexp(String regexp) {
		this.regexp = regexp;
	}

	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Boolean getNotEmptyFlag() {
		return notEmptyFlag;
	}
	public void setNotEmptyFlag(Boolean notEmptyFlag) {
		this.notEmptyFlag = notEmptyFlag;
	}
}
