package com.jjb.ecms.biz.dao.param.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.param.TmProductCardFaceDao;
import com.jjb.ecms.infrastructure.TmProductCardFace;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;

/**
 * 卡产品对于卡面代号关系
 * @author hn
 *
 */
@Repository("tmProductCardFaceDao")
public class TmProductCardFaceDaoImpl extends AbstractBaseDao<TmProductCardFace> implements TmProductCardFaceDao{

	/**
	 * 更具参数获取产品类别
	 * @param product
	 */
	public List<TmProductCardFace> getProductCardFaceByPram(TmProductCardFace product){
		List<TmProductCardFace> list = queryForList(product);
		return list;
	}
}
