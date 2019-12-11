package com.jjb.ecms.biz.dao.manage;

import java.util.List;

import com.jjb.ecms.infrastructure.TmRiskList;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

/**
  * @Description: 个人黑名单管理
  * @author JYData-R&D-L.L
  * @date 2016年9月1日 上午11:55:24
  * @version V1.0
 */
public interface TmRiskListDao extends BaseDao<TmRiskList>{

	/**
	 * 个人黑名单 分页查询
	 * @param page
	 * @return 个人黑名单记录
	 */
	Page<TmRiskList> getTmRiskListPage(Page<TmRiskList> page);
	
	/**
	 * 通过id查询个人黑名单
	 * @param id
	 * @return 个人黑名单记录
	 */
	public TmRiskList getRiskList(int id);
	
	/**
	 * 删除个人黑名单
	 * @param id
	 */
	
	public void delete(int id);
	
	/**
	 * 获取黑名单记录列表
	 */
	public List<TmRiskList> getTmRiskList(TmRiskList tmRiskList);
}
