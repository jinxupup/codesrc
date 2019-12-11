/**
 * 
 */
package com.jjb.ecms.biz.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:  调用XML信息模版
 * @author JYData-R&D-BigK.K
 * @date 2016年9月1日 上午11:00:23
 * @version V1.0  
 */
public class NodeObjectTemplate<T> {
	// 规则信息
		protected List<?> nodeObject = new ArrayList<T>();

		public List<?> getNodeObject() {
			return nodeObject;
		}

		public void setNodeObject(List<?> nodeObject) {
			this.nodeObject = nodeObject;
		}
}
