package com.jjb.dmp.engine.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 决策变量信息
 * @author BIG.D.W.K
 *
 */
public class FieldVar implements Serializable {
    private static final long serialVersionUID = 1L;

    private String stClass;

    /**
     * 变量代码
     */
    private String varCd;

    /**
     * 变量名称
     */
    private String varName;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 选项来源
     */
    private String optionType;
    
    /**
     * 选项参数
     */
    private String optionParam;

    /**
     * 选项
     */
    private List<Map<String, Object>> options;

    
	public String getStClass() {
		return stClass;
	}

	public void setStClass(String stClass) {
		this.stClass = stClass;
	}

	public String getVarCd() {
		return varCd;
	}

	public void setVarCd(String varCd) {
		this.varCd = varCd;
	}

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	public List<Map<String, Object>> getOptions() {
		return options;
	}

	public void setOptions(List<Map<String, Object>> options) {
		this.options = options;
	}

	public String getOptionParam() {
		return optionParam;
	}

	public void setOptionParam(String optionParam) {
		this.optionParam = optionParam;
	}
}    