package com.jjb.ecms.biz.dao.approve;

import java.util.List;

import com.jjb.ecms.infrastructure.TmExtTriggerRules;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * 第三方风控信息标签信息
 * @Description: 
 * @author JYData-R&D-HN
 * @date 2018年5月9日 下午5:26:05
 * @version V1.0
 */
public interface TmExtTriggerRulesDao extends BaseDao<TmExtTriggerRules>{
	/**
	 * 根据申请件编号获取所有联系人信息
	 * 
	 * @param appNo
	 * @return
	 */
	public List<TmExtTriggerRules> getTmExtTriggerRulesByAppNo(String appNo);

	public void saveTmExtTriggerRules(TmExtTriggerRules tmExtRiskInput);

	public void updateTmExtTriggerRules(TmExtTriggerRules tmExtRiskInput);

}