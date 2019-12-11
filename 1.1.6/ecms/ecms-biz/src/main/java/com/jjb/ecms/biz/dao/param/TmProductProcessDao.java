package com.jjb.ecms.biz.dao.param;

import java.util.List;

import com.jjb.ecms.infrastructure.TmProductProcess;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

/**
 * 产品参数
 * @Description: TODO
 * @author JYData-R&D-HN
 * @date 2016年9月11日 下午3:41:06
 * @version V1.0
 */
public interface TmProductProcessDao extends BaseDao<TmProductProcess>{

	/**
	 * 获取产品数据page
	 * @param page
	 * @return
	 */
	Page<TmProductProcess> getPage(Page<TmProductProcess> page, TmProductProcess product);
	/**
	 * 更具参数获取产品类别
	 * @param product
	 */
	List<TmProductProcess> getProductByPram(TmProductProcess product);
	/**
	 * 删除产品渠道流程参数
	 * @param product
	 * @return
	 */
	void deleteProductByPram(TmProductProcess product);
}
