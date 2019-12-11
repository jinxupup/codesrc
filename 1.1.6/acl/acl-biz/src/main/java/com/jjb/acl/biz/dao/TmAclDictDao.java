package com.jjb.acl.biz.dao;

import java.util.List;

import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

public interface TmAclDictDao extends BaseDao<TmAclDict>{

	Page<TmAclDict> queryPage(Page<TmAclDict> page);
	
	/**
	 * 根据类型及代码获取用户对象
	 * @param type code
	 * @return
	 */
	TmAclDict getDictByType(String type,String code);

	/**
	 * 业务字典类型列表
	 * @return
	 */
	List<TmAclDict> selectGroupType();

	/**
	 *  根据value2和sort排序来获取指定的TmAclDict对象集合
	 * @param tmAclDict
	 * @return
	 */
	List<TmAclDict> getOrderDictByValueAndSort(TmAclDict tmAclDict);
	
}