package com.jjb.ecms.biz.dao.param.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.param.TmProductBranchDao;
import com.jjb.ecms.infrastructure.TmProductBranch;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;

/**
 * @description 
 * @author J.J
 * @date 2017年8月14日下午5:31:16
 */

@Repository("tmProductBranchDao")
public class TmProductBranchDaoImpl extends AbstractBaseDao<TmProductBranch> implements TmProductBranchDao{

	/**
	 * 根据参数获取TmProductBranch
	 * @param TmProductBranch
	 */
	public List<TmProductBranch> getProductBranchByParam(TmProductBranch tmProductBranch) {
		List<TmProductBranch> list = queryForList(tmProductBranch);
		return list;
	}

}
