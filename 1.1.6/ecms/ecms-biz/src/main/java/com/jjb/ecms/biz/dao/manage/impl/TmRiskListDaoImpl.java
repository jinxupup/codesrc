package com.jjb.ecms.biz.dao.manage.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.manage.TmRiskListDao;
import com.jjb.ecms.infrastructure.TmRiskList;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;

/**
  * @Description: 个人黑名单管理
  * @author JYData-R&D-L.L
  * @date 2016年9月1日 上午11:56:17
  * @version V1.0
 */
@Repository("tmRiskListDao")
public class TmRiskListDaoImpl extends AbstractBaseDao<TmRiskList> implements TmRiskListDao {

	/**
	 * 个人黑名单 分页查询
	 * @param page
	 * @return 个人黑名单记录
	 */
	@Override
	public Page<TmRiskList> getTmRiskListPage(
			Page<TmRiskList> page) {
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		page.getQuery().put("org", OrganizationContextHolder.getOrg());
		
		return queryForPageList("com.jjb.ecms.infrastructure.mapping.TmRiskListMapper.selectAll", page.getQuery(), page);
	}
	
	/**
	 * 删除个人黑名单
	 * @param id
	 */	
	@Override
	public void delete(int id) {

		TmRiskList tmRiskList = new TmRiskList();
		tmRiskList.setId(id);
		
		deleteByKey(tmRiskList);
	}

	/**
	 * 通过id查询个人黑名单
	 * @param id
	 * @return 个人黑名单记录
	 */
	@Override
	public TmRiskList getRiskList(int id) {
		TmRiskList tmRiskList = new TmRiskList();
		tmRiskList.setId(id);
		return queryByKey(tmRiskList);
	}
	
	
	/**
	 * 获取个人黑名单记录
	 */
	@Override
	public List<TmRiskList> getTmRiskList(TmRiskList tmRiskList) {
		
		List<TmRiskList> list = queryForList(tmRiskList);
		return list;
	}

}
