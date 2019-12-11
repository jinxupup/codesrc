package com.jjb.dmp.engine.model;

import java.io.Serializable;

public class FunctionVar implements Serializable{

	public static final String FUNCTION_PREFIX = "_FUNCTION_";
	
	private static final long serialVersionUID = 1L;
	
	/*函数代码，真正的函数名*/
    private String funCd;
    /*函数中文名*/
    private String funName;
    /*函数中入参*/
    private String funParam;
    //数据类型
    private String dataType; //TODO 变成枚举
    //函数体
    private String funContent;
    //说明
    private String remark;
    //是否启用
    private String ifUsed;
    
    //真正的函数字符串
    private String realFunction;
    //函数调用字符串
    private String realFunctionCall;
    
    //函数调用字符串，动作中
    private String realFunctionActionCall; 
    
	public String getFunCd() {
		return funCd;
	}
	public void setFunCd(String funCd) {
		this.funCd = funCd;
	}
	public String getFunName() {
		return funName;
	}
	public void setFunName(String funName) {
		this.funName = funName;
	}
	public String getFunParam() {
		return funParam;
	}
	public void setFunParam(String funParam) {
		this.funParam = funParam;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getFunContent() {
		return funContent;
	}
	public void setFunContent(String funContent) {
		this.funContent = funContent;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIfUsed() {
		return ifUsed;
	}
	public void setIfUsed(String ifUsed) {
		this.ifUsed = ifUsed;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getRealFunction() {
		return realFunction;
	}
	public void setRealFunction(String realFunction) {
		this.realFunction = realFunction;
	}
	public String getRealFunctionCall() {
		return realFunctionCall;
	}
	public void setRealFunctionCall(String realFunctionCall) {
		this.realFunctionCall = realFunctionCall;
	}
	public String getRealFunctionActionCall() {
		return realFunctionActionCall;
	}
	public void setRealFunctionActionCall(String realFunctionActionCall) {
		this.realFunctionActionCall = realFunctionActionCall;
	}
	
}
