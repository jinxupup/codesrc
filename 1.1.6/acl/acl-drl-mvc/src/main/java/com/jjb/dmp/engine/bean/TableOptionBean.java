package com.jjb.dmp.engine.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 * 从表中获取选项
 * @author Dai
 *
 */
public class TableOptionBean implements Serializable {
    private static final long serialVersionUID = 1L;

	private String model="";
	private String keyField="";
	private String valueField="";
	private Map<String, String> filter = new HashMap<String, String>();
	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getKeyField() {
		return keyField;
	}
	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}
	public String getValueField() {
		return valueField;
	}
	public void setValueField(String valueField) {
		this.valueField = valueField;
	}
	public Map<String, String> getFilter() {
		return filter;
	}
	public void setFilter(Map<String, String> filter) {
		this.filter = filter;
	}
	
}
