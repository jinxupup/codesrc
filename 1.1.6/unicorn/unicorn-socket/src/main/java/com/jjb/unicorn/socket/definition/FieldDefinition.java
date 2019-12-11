package com.jjb.unicorn.socket.definition;

/**
 * <p>Description: 变量定义类，存放有关变量类型等定义信息</p>
 * <p>Company: 上海JYDATA服务有限公司</p>
 * @ClassName: FieldDefinition
 * @author BIG.CPU
 * @author LI.H
 * @date 2015年12月7日 下午4:54:44
 * @version 1.1
 */
public class FieldDefinition {
	
	private String name;
	
	private FieldType type;

	private int lengthSize;

	private int contentMaxSize;

	/**
	 * @return 变量名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name 变量名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return 变量类型，参见{@link FieldType}
	 */
	public FieldType getType() {
		return type;
	}

	/**
	 * @param type 变量类型，参见{@link FieldType}
	 */
	public void setType(FieldType type) {
		this.type = type;
	}

	/**
	 * @return 变量长度容量，为0表示变量为定长变量
	 */
	public int getLengthSize() {
		return lengthSize;
	}

	/**
	 * @param lengthSize 变量长度容量，为0表示变量为定长变量
	 */
	public void setLengthSize(int lengthSize) {
		this.lengthSize = lengthSize;
	}

	/**
	 * @return 如为变长变量则代表变量最大长度，如为定长变量则代表变量长度
	 */
	public int getContentMaxSize() {
		return contentMaxSize;
	}

	/**
	 * @param contentMaxSize 如为变长变量则代表变量最大长度，如为定长变量则代表变量长度
	 */
	public void setContentMaxSize(int contentMaxSize) {
		this.contentMaxSize = contentMaxSize;
	}

}
