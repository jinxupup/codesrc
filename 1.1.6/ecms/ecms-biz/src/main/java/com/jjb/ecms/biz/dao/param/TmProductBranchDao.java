package com.jjb.ecms.biz.dao.param;

import java.util.List;

import com.jjb.ecms.infrastructure.TmProductBranch;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * @description 
 * @author J.J
 * @date 2017年8月14日下午5:28:19
 */
public interface TmProductBranchDao extends BaseDao<TmProductBranch> {

	/**
	 * 根据参数获取TmProductBranch
	 * @param product
	 */ 
	public List<TmProductBranch> getProductBranchByParam(TmProductBranch tmProductBranch);
}
