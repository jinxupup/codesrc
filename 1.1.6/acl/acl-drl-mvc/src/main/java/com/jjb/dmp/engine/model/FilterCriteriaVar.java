package com.jjb.dmp.engine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.jjb.dmp.engine.model.enums.OperatorId;

/**
 * 条件树模型
 * @author BIG.D.W.K
 *
 */
public class FilterCriteriaVar implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String fieldName;
	protected OperatorId operator;
	protected Serializable value;
	protected List<FilterCriteriaVar> criteria;
	
	public FilterCriteriaVar() {
	}

	public FilterCriteriaVar(String fieldName, OperatorId operator, Serializable value) {
		this.fieldName = fieldName;
		this.operator = operator;
		this.value = value;
	}

	public FilterCriteriaVar(OperatorId operator, FilterCriteriaVar... fcs) {
		assert operator == OperatorId.and || operator == OperatorId.or;
		this.operator = operator;
		this.criteria = Arrays.asList(fcs);
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public OperatorId getOperator() {
		return operator;
	}

	public void setOperator(OperatorId operator) {
		this.operator = operator;
	}

	public Serializable getValue() {
		return value;
	}

	public void setValue(Serializable value) {
		this.value = value;
	}

	public List<FilterCriteriaVar> getCriteria() {
		return criteria;
	}

	public void setCriteria(List<FilterCriteriaVar> criteria) {
		this.criteria = criteria;
	}
	
	public static void main(String[] args) {
		FilterCriteriaVar filterCriteria = new FilterCriteriaVar();
		filterCriteria.setFieldName("a");
		filterCriteria.setOperator(OperatorId.and);
		filterCriteria.setCriteria(new ArrayList<FilterCriteriaVar>());
		
		FilterCriteriaVar filterCriteriaI = new FilterCriteriaVar();
		filterCriteriaI.setFieldName("b");
		filterCriteriaI.setOperator(OperatorId.or);
		filterCriteriaI.setCriteria(new ArrayList<FilterCriteriaVar>());
		
		filterCriteria.getCriteria().add(filterCriteriaI);
		String s = JSONObject.toJSONString(filterCriteria);
		System.out.println(s);
		
		FilterCriteriaVar o = JSONObject.parseObject("{\"criteria\":[{\"criteria\":[],\"fieldName\":\"b\",\"operator\":\"OR\"}],\"fieldName\":\"a\",\"operator\":\"AND\"}",FilterCriteriaVar.class);
		System.out.println(o);
		
		
		System.out.println(OperatorId.valueOf("AND"));
	}
}
