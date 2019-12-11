package com.jjb.ecms.biz.service.manage;

import com.jjb.ecms.infrastructure.TmRiskList;
import com.jjb.unicorn.facility.model.Page;

import java.util.List;

/**
  * @Description:个人风险名单管理
  * @author JYData-R&D-L.L
  * @date 2016年9月1日 上午11:49:02
  * @version V1.0
 */
public interface TmRiskListService {

	/**
	 * 个人风险名单 分页查询
	 * @param page
	 * @return 个人风险名单记录
	 */
	Page<TmRiskList> getTmRiskListPage(Page<TmRiskList> page);
	
	/**
	 * 通过id查询个人风险名单
	 * @param id
	 * @return 个人风险名单记录
	 */
	public TmRiskList getRiskList(int id);
	
	/**
	 * 删除个人风险名单
	 * @param id
	 */
	public void delete(int id);
	
	/**
	 * 添加个人风险名单
	 * @param tmRiskList
	 */
	public void saveTmRiskList(TmRiskList tmRiskList);
	
	/**
	 * 更新个人风险名单
	 * @param tmRiskList
	 */
	public void updateTmRiskList(TmRiskList tmRiskList);

	/**
	 * 查询个人风险名单
	 * @param tmRiskList
	 */
	public List<TmRiskList> getTmRiskList(TmRiskList tmRiskList);
	/**
	 * 通过名字+手机号或者证件号来查询
	 */

	public List<TmRiskList> getTmRiskListInfo(String name,String cellPhone,String idNo);
}
