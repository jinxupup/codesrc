package com.jjb.unicorn.facility.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author jjb
 *
 * @param <T>
 */
public class Page<T> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//排序字段
	public static final String _SORT_NAME = "_SORT_NAME";
	//排序方式
	public static final String _SORT_ORDER = "_SORT_ORDER";
	
	//查询条件, 
	//query中key为_SORT_LIST,类型为ArrayList<Map<String,String>>的数据，表示排序数据
	private Query query = new Query();
	//页数
	private int pageNumber = 1;
	//每页数量
	private int pageSize = 10;
	//简单排序字段
	private String sortName = "1";
	//简单排序方式
    private String sortOrder="asc";
    
    //排序列表，简单排序字段和简单排序方式会作为_SORT_LIST第一个值
    private List<Map<String,String>> _SORT_LIST = new ArrayList<Map<String,String>>();
    
    //总记录数
    private long total = 0;
    //数据
    private List<T> rows = new ArrayList<T>();
    
    public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}


	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public List<Map<String, String>> get_SORT_LIST() {
		return _SORT_LIST;
	}

	public void set_SORT_LIST(List<Map<String, String>> _SORT_LIST) {
		this._SORT_LIST = _SORT_LIST;
	}
	
}
