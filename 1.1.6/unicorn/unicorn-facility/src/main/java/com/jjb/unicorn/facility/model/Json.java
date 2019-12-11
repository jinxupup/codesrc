package com.jjb.unicorn.facility.model;

import java.io.Serializable;

/**
 * @description : 
 * @author : jjb
 * @version : 2016年3月28日
 */

public class Json implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private static String DEFAULT_SUCCESS_MSG = "操作成功";
	private static String DEFAULT_FAIL_MSG = "操作失败";
	
	//是否成功
	private boolean s;
	//错误码
	private String errCode;
	//错误描述
	private String msg;
	//附加对象
	private Object obj;

	/**
	 * 创建成功json对象，优先调用
	 * @return
	 */
	public static Json newSuccess(){
		Json json = new Json();
		json.s = true;
		json.msg = DEFAULT_SUCCESS_MSG;
		return json;
	}
	
	/**
	 * 创建失败json对象
	 * @return
	 */
	public static Json newFail(){
		Json json = new Json();
		json.s = true;
		json.msg = DEFAULT_FAIL_MSG;
		return json;
	}
	
	public void setFail(String msg){
		this.s = false;
		this.msg = msg;
	}
	
	public boolean isS() {
		return s;
	}
	public void setS(boolean s) {
		this.s = s;
	}
	public String getCode() {
		return errCode;
	}
	public void setCode(String errCode) {
		this.errCode = errCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
}
