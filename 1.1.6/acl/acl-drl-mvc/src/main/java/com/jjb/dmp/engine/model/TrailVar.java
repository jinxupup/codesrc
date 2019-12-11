package com.jjb.dmp.engine.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 运算结果
 * @author BIG.D.W.K
 *
 */
public class TrailVar implements Serializable{

	private static final long serialVersionUID = 1L;

	private boolean success = true;//是否运行成功
	private String errMsg = ""; //错误信息
	
	/**
	 * 最终的事实数据
	 */
	private Map<String,Object> fact;
	
	/**
	 * 传入数据
	 */
	private Map<String,Object> input;
	
	/**
	 * 传出数据
	 */
	private Map<String,Object> out;
	
	/**
	 * 触发记录
	 */
	private List<RuleRuntimeInfo> ruleRuntimeInfos;
	
	/**
	 * drl 规则信息
	 */
	private String drl;

	public Map<String, Object> getFact() {
		return fact;
	}

	public void setFact(Map<String, Object> fact) {
		this.fact = fact;
	}

	public Map<String, Object> getOut() {
		return out;
	}

	public void setOut(Map<String, Object> out) {
		this.out = out;
	}

	public Map<String, Object> getInput() {
		return input;
	}

	public void setInput(Map<String, Object> input) {
		this.input = input;
	}

	public List<RuleRuntimeInfo> getRuleRuntimeInfos() {
		return ruleRuntimeInfos;
	}

	public void setRuleRuntimeInfos(List<RuleRuntimeInfo> ruleRuntimeInfos) {
		this.ruleRuntimeInfos = ruleRuntimeInfos;
	}

	public String getDrl() {
		return drl;
	}

	public void setDrl(String drl) {
		this.drl = drl;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}
