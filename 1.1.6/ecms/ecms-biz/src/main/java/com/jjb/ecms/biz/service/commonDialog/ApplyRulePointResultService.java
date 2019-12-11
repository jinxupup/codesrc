package com.jjb.ecms.biz.service.commonDialog;

import java.util.List;

import com.jjb.ecms.facility.nodeobject.RulePointResultItem;

/**
 * @description: 评分结果弹窗service
 * @author -BigZ.Y
 * @date 2016年9月21日 上午10:51:50 
 */
public interface ApplyRulePointResultService {

	/**
	 * 获取评分结果
	 * @param appNo
	 * @return
	 */
	List<RulePointResultItem> getRulePointResultItems(String appNo);
}
