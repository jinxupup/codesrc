package com.jjb.ecms.biz.dao.manage.impl;


import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.manage.ApplySpreaderInfoDao;
import com.jjb.ecms.infrastructure.TmAppSprePerBank;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;

/**
 * @description 
 * @author J.J
 * @date 2017年7月7日下午3:01:23
 */

@Repository("applySpreaderInfoDao")
public class ApplySpreaderInfoDaoImpl extends AbstractBaseDao<TmAppSprePerBank> implements ApplySpreaderInfoDao{
	public static final String  querySpreByParam = "com.jjb.ecms.biz.SpreaderBankMapper.querySpreByParam";
	/**
	 * 获取推广人信息</br>
	 * 不支持模糊搜索
	 * @param page
	 * @param tmAppSprePerBank
	 * @return
	 */
	@Override
	public Page<TmAppSprePerBank> getPage(Page<TmAppSprePerBank> page, TmAppSprePerBank tmAppSprePerBank) {
  		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		Page<TmAppSprePerBank> spreaderPerformance = queryForPageList(tmAppSprePerBank, page.getQuery(), page);
		return spreaderPerformance;
	}
	/**
	 * 获取推广人信息</br>
	 * 不支持模糊搜索
	 * @param page
	 * @return
	 */
	@Override
	public Page<TmAppSprePerBank> getPageForTranferUser(Page<TmAppSprePerBank> page) {
		if (null == page.getQuery()) {
			page.setQuery(new Query());
		}
		Page<TmAppSprePerBank> spreaderPerformance = queryForPageList(querySpreByParam, page.getQuery(), page);
		return spreaderPerformance;
	}
}
