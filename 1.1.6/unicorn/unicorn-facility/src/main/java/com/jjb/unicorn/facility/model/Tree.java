package com.jjb.unicorn.facility.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jjb.unicorn.facility.kits.StrKit;

/**
 * 
 * @author jjb
 * @param <T>
 * 
 */
public class Tree<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private T t ; //自身节点
	private String id;//自身节点id、代码或其他可以标识的字段值。
	private String name;//名称
	
	private String pId; //父节点id、代码或其他可以标识的字段值。
	private String pName;//父节点名称
	
	//子节点
	private List<Tree<T>> children;

	
	/**
	 * 将列表数据类型转换为树状结构
	 * @param list
	 * @param TreeFace 主键字段与名称字段取值
	 * @return
	 */
	public static <T> Tree<T> listToTree(List<T> list, TreeFace<T> treeFace){
		
		Map<String,Tree<T>> treeMap = new LinkedHashMap<String, Tree<T>>();
		for(T t:list){
			Tree<T> node = new Tree<T>();
			String id = treeFace.getId(t);
			node.setId(id);
			node.setName(treeFace.getName(t));
			node.setT(treeFace.getT(t));
			treeMap.put(id, node);
		}
		
		Tree<T> root = new Tree<T>();
		root.setChildren(new ArrayList<Tree<T>>());
		for(Map.Entry<String, Tree<T>> entry: treeMap.entrySet()){
			Tree<T> node = entry.getValue();
			T t = node.getT();
			
			if(treeFace.isRoot(t)){
				root.getChildren().add(node);
				continue;
			}
			
			String pId = treeFace.getPId(t);
			if(StrKit.notBlank(pId)){
				Tree<T> parent = treeMap.get(pId);
				if(parent==null){
					continue ;
				}
				
				if(parent!=null && parent.getChildren()==null){
					parent.setChildren(new ArrayList<Tree<T>>());
				}
				
				parent.getChildren().add(node);
				node.setpId(pId);
				node.setpName(treeFace.getName(parent.getT()));
			}
		}
		
		return root;
	}
	
	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	public List<Tree<T>> getChildren() {
		return children;
	}

	public void setChildren(List<Tree<T>> children) {
		this.children = children;
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public static void main(String[] args) {
		
		
		
		Tree.listToTree(null, new TreeFace<Object>() {
			
			
			@Override
			public String getId(Object t) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getName(Object t) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean isRoot(Object t) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String getPId(Object t) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		
	}
}
