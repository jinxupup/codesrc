package com.jjb.ecms.biz.dao.apply;

import java.util.List;

import com.jjb.ecms.infrastructure.TmAppContact;
import com.jjb.unicorn.base.dao.BaseDao;

public interface TmAppContactDao extends BaseDao<TmAppContact>{
	/**
	 * 根据申请件编号获取所有联系人信息
	 * 
	 * @param appNo
	 * @return
	 */
	public List<TmAppContact> getTmAppContactListByAppNo(String appNo);

	public void saveTmAppContact(TmAppContact tmAppContact);

	public void updateTmAppContact(TmAppContact tmAppContact);
	
	/**
	 * 根据申请件编号删除所有联系人信息
	 * 
	 * @param tmAppContact
	 * @return
	 */
	public void deleteTmAppContact(TmAppContact tmAppContact);
}