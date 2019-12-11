package com.jjb.unicorn.socket.definition;

/**
 * <p>Description: 扩展域定义，封装FieldDefinition，其中key代表在子域中的名字</p>
 * <p>Company: 上海JYDATA服务有限公司</p>
 * @ClassName: ExtFieldDefinition
 * @author LI.H
 * @date 2015年12月7日 下午8:06:28
 * @version 1.0
 */
public class ExtFieldDefinition {
	
	/**
	 * <p>孩子与0域key值</p>
	 */
	public final static String ZERO = "0";
	
	/**
	 * <p>TLV字段枚举前缀</p>
	 */
	public final static String F = "F";
	
	/**
	 * <p>TLV格式子域datasetid字段key值</p>
	 */
	public final static String DATASET_ID = "datasetid";
	
	/**
	 * <p>TLV格式子域datasetlength字段key值</p>
	 */
	public final static String DATASET_LENGTH = "datasetlength";
	
	/**
	 * <p>TLV格式中的TAG</p>
	 */
	public final static String TAG = "tag";
	
	/**
	 * <p>TLV格式中的LENGTH</p>
	 */
	public final static String LENGTH = "length";
	
	private String subindex;
	
	private FieldDefinition fieldDef;
	
	public ExtFieldDefinition(String subindex, FieldDefinition fieldDef) {
		super();
		this.subindex = subindex;
		this.fieldDef = fieldDef;
	}

	public String getSubindex() {
		return subindex;
	}

	public void setSubindex(String subindex) {
		this.subindex = subindex;
	}

	public FieldDefinition getFieldDef() {
		return fieldDef;
	}

	public void setFieldDef(FieldDefinition fieldDef) {
		this.fieldDef = fieldDef;
	}




}
