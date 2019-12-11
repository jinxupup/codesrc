/**
 * 
 */
package com.jjb.ecms.facility.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * @Description: 页面字段模型
 * @author JYData-R&D-Big T.T
 * @date 2017年11月20日 下午5:33:02
 * @version V1.0  
 */

public class FieldPageDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//组件id
	
	private String name;//组件名称
	
	private String fieldName;

	private String value;//值
    
    private String componentType;//组件类型
    
    private String dictType;//数据字典类型
    
    private String change;//下拉框改变事件
    
    private String textName;//备注框name属性或多选框是否单选或异步加载下拉框点击事件
    
    private String nullable;//下拉框是否启用空选项
    
    private String showCode;//是否显示code
    
    private String fieldAr;//field标签占比
    
    private String labelAr;//label标签占比
    
    private String inputAr;//input标签占比
    
    private String ifReadonly;//是否是只读
    
    private String ifRequire;//是否必填
    
    private Map<Object, Object> options;//选择options
    
    private String regexp;//正则表达式
    
    private String isRow;//换行始末状态

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the componentType
	 */
	public String getComponentType() {
		return componentType;
	}

	/**
	 * @param componentType the componentType to set
	 */
	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}

	/**
	 * @return the dictType
	 */
	public String getDictType() {
		return dictType;
	}

	/**
	 * @param dictType the dictType to set
	 */
	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	/**
	 * @return the change
	 */
	public String getChange() {
		return change;
	}

	/**
	 * @param change the change to set
	 */
	public void setChange(String change) {
		this.change = change;
	}

	/**
	 * @return the textName
	 */
	public String getTextName() {
		return textName;
	}

	/**
	 * @param textName the textName to set
	 */
	public void setTextName(String textName) {
		this.textName = textName;
	}

	/**
	 * @return the nullable
	 */
	public String getNullable() {
		return nullable;
	}

	/**
	 * @param nullable the nullable to set
	 */
	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	/**
	 * @return the showCode
	 */
	public String getShowCode() {
		return showCode;
	}

	/**
	 * @param showCode the showCode to set
	 */
	public void setShowCode(String showCode) {
		this.showCode = showCode;
	}

	/**
	 * @return the fieldAr
	 */
	public String getFieldAr() {
		return fieldAr;
	}

	/**
	 * @param fieldAr the fieldAr to set
	 */
	public void setFieldAr(String fieldAr) {
		this.fieldAr = fieldAr;
	}

	/**
	 * @return the labelAr
	 */
	public String getLabelAr() {
		return labelAr;
	}

	/**
	 * @param labelAr the labelAr to set
	 */
	public void setLabelAr(String labelAr) {
		this.labelAr = labelAr;
	}

	/**
	 * @return the inputAr
	 */
	public String getInputAr() {
		return inputAr;
	}

	/**
	 * @param inputAr the inputAr to set
	 */
	public void setInputAr(String inputAr) {
		this.inputAr = inputAr;
	}

	/**
	 * @return the ifReadonly
	 */
	public String getIfReadonly() {
		return ifReadonly;
	}

	/**
	 * @param ifReadonly the ifReadonly to set
	 */
	public void setIfReadonly(String ifReadonly) {
		this.ifReadonly = ifReadonly;
	}

	/**
	 * @return the ifRequire
	 */
	public String getIfRequire() {
		return ifRequire;
	}

	/**
	 * @param ifRequire the ifRequire to set
	 */
	public void setIfRequire(String ifRequire) {
		this.ifRequire = ifRequire;
	}

	/**
	 * @return the options
	 */
	public Map<Object, Object> getOptions() {
		return options;
	}

	/**
	 * @param options the options to set
	 */
	public void setOptions(Map<Object, Object> options) {
		this.options = options;
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
	 * @return the isRow
	 */
	public String getIsRow() {
		return isRow;
	}

	/**
	 * @param isRow the isRow to set
	 */
	public void setIsRow(String isRow) {
		this.isRow = isRow;
	}

	@Override
	public String toString() {
		return String
				.format("FieldPageDto [id=%s, name=%s, fieldName=%s, value=%s, componentType=%s, dictType=%s, change=%s, textName=%s, nullable=%s, showCode=%s, fieldAr=%s, labelAr=%s, inputAr=%s, ifReadonly=%s, ifRequire=%s, options=%s, regexp=%s, isRow=%s]",
						id, name, fieldName, value, componentType, dictType,
						change, textName, nullable, showCode, fieldAr, labelAr,
						inputAr, ifReadonly, ifRequire, options, regexp, isRow);
	}
	
}
