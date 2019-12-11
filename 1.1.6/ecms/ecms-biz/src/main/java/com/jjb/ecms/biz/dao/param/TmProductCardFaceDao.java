package com.jjb.ecms.biz.dao.param;

import java.util.List;

import com.jjb.ecms.infrastructure.TmProductCardFace;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * 产品参数
 * @Description: TODO
 * @author JYData-R&D-HN
 * @date 2016年9月11日 下午3:41:06
 * @version V1.0
 */
public interface TmProductCardFaceDao extends BaseDao<TmProductCardFace>{

	/**
	 * 更具参数获取产品类别
	 * @param product
	 */
	List<TmProductCardFace> getProductCardFaceByPram(TmProductCardFace productCardFace);
}
