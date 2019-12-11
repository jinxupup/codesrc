package com.jjb.dmp.engine.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 规则运行信息
 * @author BIG.D.W.K
 *
 */
public class RuleRuntimeInfo  implements Serializable {

	private static final long serialVersionUID = 1L;

	private String stClass;
	
	private String rulesetName;
	
	private String name;
	
	private String desc;
	
	//创建时的time
	private long currentTimeMillis;
	
	//Map<String,Map<String,Object>> 格式  Map<输出变量的varCd,Map<"old"/"new",旧值/新值>>
	Map<String,Map<String,Object>> outputs = new HashMap<String, Map<String,Object>>();

	/**
	 * 
	 * @param stClass
	 * @param rulesetName
	 * @param ruleName
	 * @param varCd 输出变量代码
	 * @param oldValue
	 * @param newValue
	 */
	public RuleRuntimeInfo(String stClass,String rulesetName,String ruleName,String varCd,Object oldValue,Object newValue){
		
		this.stClass = stClass;
		this.rulesetName = rulesetName;
		this.name = ruleName;
		
		if(!outputs.containsKey(varCd)){
			outputs.put(varCd, new HashMap<String, Object>());
		}
		outputs.get(varCd).put("old", oldValue);
		outputs.get(varCd).put("new", newValue);
		
		this.currentTimeMillis = System.currentTimeMillis();
	}
	
	public RuleRuntimeInfo(String stClass,String rulesetName,String ruleName){
		
		this.stClass = stClass;
		this.rulesetName = rulesetName;
		this.name = ruleName;
		
		this.currentTimeMillis = System.currentTimeMillis();
	}
	
	public void addOutput(String varCd,Object oldValue,Object newValue){
		if(!outputs.containsKey(varCd)){
			outputs.put(varCd, new HashMap<String, Object>());
		}
		outputs.get(varCd).put("old", oldValue);
		outputs.get(varCd).put("new", newValue);
	}
	
	public String getStClass() {
		return stClass;
	}

	public void setStClass(String stClass) {
		this.stClass = stClass;
	}

	public String getRulesetName() {
		return rulesetName;
	}

	public void setRulesetName(String rulesetName) {
		this.rulesetName = rulesetName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Map<String, Map<String, Object>> getOutputs() {
		return outputs;
	}

	public void setOutputs(Map<String, Map<String, Object>> outputs) {
		this.outputs = outputs;
	}

	public long getCurrentTimeMillis() {
		return currentTimeMillis;
	}

	public void setCurrentTimeMillis(long currentTimeMillis) {
		this.currentTimeMillis = currentTimeMillis;
	}
}	