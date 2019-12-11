/**
 * 
 */
package com.jjb.ecms.facility.dto;

/**
 * @Description: 录入复核对比信息
 * @author JYData-R&D-BigK.K
 * @date 2016年9月6日 上午10:59:10
 * @version V1.0  
 */
public class AppplyCompareInfoForReviewDto {
		//信息域
	private String fieldInfo;
		//对比项
	private String option;
		//录入值
	private String inputValue;
		//复核值
	private String reviewValue;
	public String getFieldInfo() {
		return fieldInfo;
	}
	public void setFieldInfo(String fieldInfo) {
		this.fieldInfo = fieldInfo;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	public String getInputValue() {
		return inputValue;
	}
	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}
	public String getReviewValue() {
		return reviewValue;
	}
	public void setReviewValue(String reviewValue) {
		this.reviewValue = reviewValue;
	}
	
	
		
	
}
