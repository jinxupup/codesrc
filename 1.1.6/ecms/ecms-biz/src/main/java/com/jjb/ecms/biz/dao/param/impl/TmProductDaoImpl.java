package com.jjb.ecms.biz.dao.param.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.param.TmProductDao;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;

/**
 * @description: TODO
 * @author -BigZ.Y
 * @date 2016年9月6日 上午9:51:24 
 */
@Repository("tmProductDao")
public class TmProductDaoImpl extends AbstractBaseDao<TmProduct> implements TmProductDao {

	public static final String  selectAll = "com.jjb.ecms.infrastructure.mapping.TmProductMapper.selectAll";
	public static final String  selectDemotionProduct = "com.jjb.ecms.biz.ApplyTmProductMapper.selectDemotionProduct";
	
	/* (non-Javadoc)
	 * @see TmProductDao#getPage(com.jjb.unicorn.facility.model.Page)
	 */
	@Override
	public Page<TmProduct> getPage(Page<TmProduct> page,TmProduct product) {
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		Page<TmProduct> p = queryForPageList(product, page.getQuery(), page);
		
		return p;
	}
	/**
	 * 更具参数获取产品类别
	 * @param product
	 */
	public List<TmProduct> getProductByPram(TmProduct product){
		List<TmProduct> list = queryForList(product);
		return list;
	}
	/**
	 * 查询可降级卡产品
	 * @param parameter
	 * @return
	 */
	@Override
	public List<TmProduct> getDemtionProducts(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		if(parameter == null ){
			parameter = new HashMap<String, Object>();
		}
		return queryForList(selectDemotionProduct, parameter);
	}
}
