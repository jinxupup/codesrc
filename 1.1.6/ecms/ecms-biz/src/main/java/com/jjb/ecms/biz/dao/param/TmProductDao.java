package com.jjb.ecms.biz.dao.param;

import java.util.List;
import java.util.Map;

import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

/**
 * 产品参数
 * @Description: TODO
 * @author JYData-R&D-HN
 * @date 2016年9月11日 下午3:41:06
 * @version V1.0
 */
public interface TmProductDao extends BaseDao<TmProduct>{

	/**
	 * 获取产品数据page
	 * @param page
	 * @return
	 */
	Page<TmProduct> getPage(Page<TmProduct> page,TmProduct product);
	/**
	 * 更具参数获取产品类别
	 * @param product
	 */
	List<TmProduct> getProductByPram(TmProduct product);
	/**
	 * 查询可降级卡产品
	 * @param parameter
	 * @return
	 */
	List<TmProduct> getDemtionProducts(Map<String, Object> parameter);
}
