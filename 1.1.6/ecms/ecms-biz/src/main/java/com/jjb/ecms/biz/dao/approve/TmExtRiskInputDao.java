package com.jjb.ecms.biz.dao.approve;

import com.jjb.ecms.infrastructure.TmExtRiskInput;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * 第三方风控信息
 * @Description: 
 * @author JYData-R&D-HN
 * @date 2018年5月9日 下午5:26:05
 * @version V1.0
 */
public interface TmExtRiskInputDao extends BaseDao<TmExtRiskInput>{
	/**
	 * 根据申请件编号获取所有联系人信息
	 * 
	 * @param appNo
	 * @return
	 */
	public TmExtRiskInput getTmExtRiskInputByAppNo(String appNo);

	public void saveTmExtRiskInput(TmExtRiskInput tmExtRiskInput);

	public void updateTmExtRiskInput(TmExtRiskInput tmExtRiskInput);

}