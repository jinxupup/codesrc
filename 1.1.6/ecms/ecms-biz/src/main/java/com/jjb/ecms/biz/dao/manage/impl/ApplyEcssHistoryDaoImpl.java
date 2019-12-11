package com.jjb.ecms.biz.dao.manage.impl;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.manage.ApplyEcssHistoryDao;
import com.jjb.ecms.infrastructure.TmAppImageHistory;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;

/**
 * @Description: 影像调阅记录查询
 * @author JYData-R&D-L.L
 * @date 2016年8月31日 下午6:44:04
 * @version V1.0  
 */
@Repository("applyEcssHistoryDao")
public class ApplyEcssHistoryDaoImpl extends AbstractBaseDao<TmAppImageHistory> implements ApplyEcssHistoryDao {

	/**
	 * 影像调阅历史记录 分页查询
	 * @param page
	 * @return 影像调阅查询记录
	 */
	@Override
	public Page<TmAppImageHistory> getImagePage(Page<TmAppImageHistory> page) {
		
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		page.getQuery().put("org", OrganizationContextHolder.getOrg());
		
		return queryForPageList("com.jjb.ecms.infrastructure.mapping.TmAppImageHistoryMapper.selectAll", page.getQuery(), page);
	}

}
