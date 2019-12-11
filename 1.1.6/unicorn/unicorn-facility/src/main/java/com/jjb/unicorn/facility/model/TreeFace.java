package com.jjb.unicorn.facility.model;

/**
 * 
 * @author jjb
 *
 * @param <T>
 */
public abstract class TreeFace<T>{
	
	/**
	 * 获取自身，此方法可以定制对象
	 * @param t
	 * @return
	 */
	public T getT(T t){
		return t;
	}
	/**
	 * 是否根节点的判断
	 * @param t
	 * @return
	 */
	public abstract boolean isRoot(T t);
	/**
	 * 获取父节点主键
	 * @param t
	 * @return
	 */
	public abstract String getPId(T t);
	
	
	/**
	 * 获取主键值
	 * @param t
	 * @return
	 */
	public abstract String getId(T t);
	/**
	 * 获取名称值
	 * @param t
	 * @return
	 */
	public abstract String getName(T t);
	
}
