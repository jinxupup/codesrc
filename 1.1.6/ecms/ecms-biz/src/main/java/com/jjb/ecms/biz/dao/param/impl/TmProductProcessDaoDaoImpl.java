package com.jjb.ecms.biz.dao.param.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.param.TmProductProcessDao;
import com.jjb.ecms.infrastructure.TmProductProcess;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;

/**
 * @description: TODO
 * @author -BigZ.Y
 * @date 2016年9月6日 上午9:51:24 
 */
@Repository("tmProductDitProc")
public class TmProductProcessDaoDaoImpl extends AbstractBaseDao<TmProductProcess> implements TmProductProcessDao {

	public static final String  selectAll = "com.jjb.ecms.infrastructure.mapping.TmProductMapper.selectAll";
	public static final String  selectDemotionProduct = "com.jjb.ecms.biz.ApplyTmProductMapper.selectDemotionProduct";
	
	/* (non-Javadoc)
	 * @see TmProductDao#getPage(com.jjb.unicorn.facility.model.Page)
	 */
	@Override
	public Page<TmProductProcess> getPage(Page<TmProductProcess> page,TmProductProcess product) {
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		Page<TmProductProcess> p = queryForPageList(product, page.getQuery(), page);
		
		return p;
	}
	/**
	 * 更具参数获取产品类别
	 * @param product
	 */
	public List<TmProductProcess> getProductByPram(TmProductProcess product){
		List<TmProductProcess> list = queryForList(product);
		return list;
	}
	/**
	 * 查询可降级卡产品
	 * @param product
	 * @return
	 */
	@Override
	public void deleteProductByPram(TmProductProcess product) {
		deleteByKey(product);
	}
}
