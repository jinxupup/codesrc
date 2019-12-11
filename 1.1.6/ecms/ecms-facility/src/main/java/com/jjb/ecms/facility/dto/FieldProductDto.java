/**
 * 
 */
package com.jjb.ecms.facility.dto;

import java.io.Serializable;

/**
 * @Description: 根据卡产品配置页面字段实体类
 * @author JYData-R&D-Big T.T
 * @date 2017年9月14日 上午11:40:10
 * @version V1.0
 */

public class FieldProductDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String org;
	private Integer fieldId;// 字段id
	private String productCd;// 卡产品
	private String productDesc;// 卡产品描述
	private String ifRequiredItem;// 是否是必输项
	private String ifReviewItem;// 是否是复核项
	private String fieldEn;// 字段名
	private String fieldName;// 字段名称
	private String tabName;// 表名字母小写
	private String tabDesc;// 表名描述
	private String fieldRegion;// 字段所在的位置
	private String fieldRegionCn;// 字段所在的位置-中文描述
	private Integer fieldSort;// 字段位置顺序
	private String isInput;// 申请录入显示
	private String isReview;// 复核/人工核查/补件/终审显示
	private Integer reviewSort;// 复核顺序
	private String value1;// 初审显示
	private String value2;// 电调显示
	private String value3;// 预留
	private String value4;// 预留
	private String value5;// 预留
	private String obText1;// 预留
	private String obText2;// 预留
	private String obText3;// 预留
	private String obText4;// 预留
	private String ifUsed;// 是否启用
	private String ifCancel;// 是否删除
	private String remark;// 备注
	private String fieldRemark;// 字段备注
	private String defValue;// 默认值
	private String componentType;// 组件类型
	private String dictType;// 数据字典类型
	private String fieldChange;// 下拉框改变事件
	private String textName;// 备注框name属性
	private String fieldNullable;// 下拉框是否启用空选项
	private String showCode;// 是否显示code
	private String fieldRegexp;// 正则表达式
	private String maxLength;// 字符串最大长度
	private String betweenMin;// 区间最小值
	private String betweenMax;// 区间最大值
	private String fieldAr;// field标签占比
	private String labelAr;// label标签占比
	private String inputAr;// input标签占比
	private String ifReadonly;// 是否只读
	private String isRow;// 换行始末状态
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public Integer getFieldId() {
		return fieldId;
	}
	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}
	public String getProductCd() {
		return productCd;
	}
	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public String getIfRequiredItem() {
		return ifRequiredItem;
	}
	public void setIfRequiredItem(String ifRequiredItem) {
		this.ifRequiredItem = ifRequiredItem;
	}
	public String getIfReviewItem() {
		return ifReviewItem;
	}
	public void setIfReviewItem(String ifReviewItem) {
		this.ifReviewItem = ifReviewItem;
	}
	public String getFieldEn() {
		return fieldEn;
	}
	public void setFieldEn(String fieldEn) {
		this.fieldEn = fieldEn;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getTabName() {
		return tabName;
	}
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	public String getTabDesc() {
		return tabDesc;
	}
	public void setTabDesc(String tabDesc) {
		this.tabDesc = tabDesc;
	}
	public String getFieldRegion() {
		return fieldRegion;
	}
	public void setFieldRegion(String fieldRegion) {
		this.fieldRegion = fieldRegion;
	}
	public String getFieldRegionCn() {
		return fieldRegionCn;
	}
	public void setFieldRegionCn(String fieldRegionCn) {
		this.fieldRegionCn = fieldRegionCn;
	}
	public Integer getFieldSort() {
		return fieldSort;
	}
	public void setFieldSort(Integer fieldSort) {
		this.fieldSort = fieldSort;
	}
	public String getIsInput() {
		return isInput;
	}
	public void setIsInput(String isInput) {
		this.isInput = isInput;
	}
	public String getIsReview() {
		return isReview;
	}
	public void setIsReview(String isReview) {
		this.isReview = isReview;
	}
	public Integer getReviewSort() {
		return reviewSort;
	}
	public void setReviewSort(Integer reviewSort) {
		this.reviewSort = reviewSort;
	}
	public String getValue1() {
		return value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public String getValue3() {
		return value3;
	}
	public void setValue3(String value3) {
		this.value3 = value3;
	}
	public String getValue4() {
		return value4;
	}
	public void setValue4(String value4) {
		this.value4 = value4;
	}
	public String getValue5() {
		return value5;
	}
	public void setValue5(String value5) {
		this.value5 = value5;
	}
	public String getObText1() {
		return obText1;
	}
	public void setObText1(String obText1) {
		this.obText1 = obText1;
	}
	public String getObText2() {
		return obText2;
	}
	public void setObText2(String obText2) {
		this.obText2 = obText2;
	}
	public String getObText3() {
		return obText3;
	}
	public void setObText3(String obText3) {
		this.obText3 = obText3;
	}
	public String getObText4() {
		return obText4;
	}
	public void setObText4(String obText4) {
		this.obText4 = obText4;
	}
	public String getIfUsed() {
		return ifUsed;
	}
	public void setIfUsed(String ifUsed) {
		this.ifUsed = ifUsed;
	}
	public String getIfCancel() {
		return ifCancel;
	}
	public void setIfCancel(String ifCancel) {
		this.ifCancel = ifCancel;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getFieldRemark() {
		return fieldRemark;
	}
	public void setFieldRemark(String fieldRemark) {
		this.fieldRemark = fieldRemark;
	}
	public String getDefValue() {
		return defValue;
	}
	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}
	public String getComponentType() {
		return componentType;
	}
	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}
	public String getDictType() {
		return dictType;
	}
	public void setDictType(String dictType) {
		this.dictType = dictType;
	}
	public String getFieldChange() {
		return fieldChange;
	}
	public void setFieldChange(String fieldChange) {
		this.fieldChange = fieldChange;
	}
	public String getTextName() {
		return textName;
	}
	public void setTextName(String textName) {
		this.textName = textName;
	}
	public String getFieldNullable() {
		return fieldNullable;
	}
	public void setFieldNullable(String fieldNullable) {
		this.fieldNullable = fieldNullable;
	}
	public String getShowCode() {
		return showCode;
	}
	public void setShowCode(String showCode) {
		this.showCode = showCode;
	}
	public String getFieldRegexp() {
		return fieldRegexp;
	}
	public void setFieldRegexp(String fieldRegexp) {
		this.fieldRegexp = fieldRegexp;
	}
	public String getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}
	public String getBetweenMin() {
		return betweenMin;
	}
	public void setBetweenMin(String betweenMin) {
		this.betweenMin = betweenMin;
	}
	public String getBetweenMax() {
		return betweenMax;
	}
	public void setBetweenMax(String betweenMax) {
		this.betweenMax = betweenMax;
	}
	public String getFieldAr() {
		return fieldAr;
	}
	public void setFieldAr(String fieldAr) {
		this.fieldAr = fieldAr;
	}
	public String getLabelAr() {
		return labelAr;
	}
	public void setLabelAr(String labelAr) {
		this.labelAr = labelAr;
	}
	public String getInputAr() {
		return inputAr;
	}
	public void setInputAr(String inputAr) {
		this.inputAr = inputAr;
	}
	public String getIfReadonly() {
		return ifReadonly;
	}
	public void setIfReadonly(String ifReadonly) {
		this.ifReadonly = ifReadonly;
	}
	public String getIsRow() {
		return isRow;
	}
	public void setIsRow(String isRow) {
		this.isRow = isRow;
	}
		
}
