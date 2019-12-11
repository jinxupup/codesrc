package com.jjb.ecms.biz.dao.manage;

import com.jjb.ecms.infrastructure.TmAppSprePerBank;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

/**
 * @description 推广人信息
 * @author J.J
 * @date 2017年7月7日下午2:59:31
 */
public interface ApplySpreaderInfoDao extends BaseDao<TmAppSprePerBank>{
	/**
	 * 获取推广人信息</br>
	 * 不支持模糊搜索
	 * @param page
	 * @param tmAppSprePerBank
	 * @return
	 */
	Page<TmAppSprePerBank> getPage(Page<TmAppSprePerBank> page,TmAppSprePerBank tmAppSprePerBank);
	/**
	 * 获取推广人信息</br>
	 * 不支持模糊搜索
	 * @param page
	 * @return
	 */
	Page<TmAppSprePerBank> getPageForTranferUser(Page<TmAppSprePerBank> page);
	
}
