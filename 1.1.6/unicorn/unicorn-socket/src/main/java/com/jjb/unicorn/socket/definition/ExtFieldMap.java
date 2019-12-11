package com.jjb.unicorn.socket.definition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Description: 扩展子域DefinitionMap，此map维护两个子Map，一个value为List，一个value为HashMap，分别对应固定顺序和TLV格式的快速操作</p>
 * <p>Company: 上海JYDATA服务有限公司</p>
 * @ClassName: ExtFieldMap
 * @author LI.H
 * @date 2015年12月10日 下午8:18:58
 * @version 1.0
 */
public class ExtFieldMap {
	
	private final static Logger logger = LoggerFactory.getLogger(ExtFieldDefinitionFactory.class);
	
	private String input = "";
	
	private Map<Integer, ArrayList<ExtFieldDefinition>> extFieldDefByArray;
	
	private Map<Integer, HashMap<String, ExtFieldDefinition>> extFieldDefByMap = new HashMap<Integer, HashMap<String, ExtFieldDefinition>>();
	
	/**
	 * <p>初始化此数据结构</p>
	 * @param extFieldDefByArray
	 * @param extFieldDefByMap
	 */
	public ExtFieldMap(Map<Integer, ArrayList<ExtFieldDefinition>> extFieldDefByArray) {
		this.extFieldDefByArray = extFieldDefByArray;
		for (Integer index : extFieldDefByArray.keySet()) {
			logger.debug("初始化复杂子域配置信息[{}]", index);
			HashMap<String, ExtFieldDefinition> result = new HashMap<String, ExtFieldDefinition>();
			for (ExtFieldDefinition efd : extFieldDefByArray.get(index)) {
				logger.debug("初始化复杂孩子域[{}]", efd.getSubindex());
				result.put(efd.getSubindex(), efd);
			}
			this.extFieldDefByMap.put(index, result);
		}
		input = extFieldDefByArray.get(-1).get(0).getSubindex();
	}
	
	/**
	 * <p>目的：判断是否存在某子域</p>
	 * <p>承诺：</p>
	 * @param index 子域索引
	 * @return 存在则响应true
	 */
	public boolean containsKey(Integer index) {
		return extFieldDefByMap.containsKey(index);
	}
	
	/**
	 * <p>目的：获取子域索引中所有孩子域的个数</p>
	 * <p>承诺：</p>
	 * @param index
	 * @return
	 */
	public int getExtFieldSize(Integer index) {
		return extFieldDefByArray.get(index).size();
	}
	
	/**
	 * <p>目的：根据子域索引和数组下标获取</p>
	 * <p>承诺：不保证数组长度一定小于下标，有可能数组越界</p>
	 * @param index
	 * @param arrayIndex
	 * @return
	 */
	public ExtFieldDefinition getExtFieldDefByArrayindex(Integer index, Integer arrayIndex) {
		return extFieldDefByArray.get(index).get(arrayIndex);
	}
	
	/**
	 * <p>目的：判断子域中是否存在某孩子域</p>
	 * <p>承诺：必须在存在该子域的情况下调用此方法，否则会出现空指针异常</p>
	 * @param index 子域索引
	 * @param subindex 孩子域索引
	 * @return
	 */
	public boolean containsKey(Integer index, String subindex) {
		return extFieldDefByMap.get(index).containsKey(subindex);
	}
	
	/**
	 * <p>目的：从子域中获取某孩子域的定义</p>
	 * <p>承诺：必须在存在该子域的情况下调用此方法，否则会出现空指针异常</p>
	 * @param index 子域索引
	 * @param subindex 孩子域索引
	 * @return
	 */
	public ExtFieldDefinition getSubFieldByKey(Integer index, String subindex) {
		return extFieldDefByMap.get(index).get(subindex);
	}
	
	/**
	 * <p>目的：从子域中获取某孩子域的定义</p>
	 * <p>承诺：此方法只用域TLV格式获取，由于TLV格式对于大小写不敏感所以全部转换为大写</p>
	 * @param index 子域索引
	 * @param subindex 孩子域索引
	 * @return
	 */
	public ExtFieldDefinition getSubFieldByUpperKey(Integer index, String subindex) {
		return extFieldDefByMap.get(index).get(subindex.toUpperCase());
	}

	public String getInput() {
		return input;
	}
	
}
