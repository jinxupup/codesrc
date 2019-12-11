package com.jjb.ecms.facility.nodeobject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 申请流程对象-录入复核信息
 * @author JYData-R&D-HN
 * @date 2016年9月1日 下午2:23:24
 * @version V1.0
 */
public class ApplyNodeReviewData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4741956259488625745L;

	// 复核项map<主附卡标志，map<主附卡复核项,复核项的值>>
	private Map<String ,Map<String, String>> reviewsMap = new HashMap<String, Map<String, String>>();

	// 录入复核备注/备忘信息
	private String memo;

	private String remark;

	
	public Map<String, Map<String, String>> getReviewsMap() {
		return reviewsMap;
	}

	public void setReviewsMap(Map<String, Map<String, String>> reviewsMap) {
		this.reviewsMap = reviewsMap;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
