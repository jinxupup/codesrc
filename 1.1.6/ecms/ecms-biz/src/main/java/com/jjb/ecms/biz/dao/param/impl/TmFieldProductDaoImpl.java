/**
 * 
 */
package com.jjb.ecms.biz.dao.param.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.param.TmFieldProductDao;
import com.jjb.ecms.infrastructure.TmFieldProduct;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;

/**
 * @Description: TODO
 * @author JYData-R&D-Big T.T
 * @date 2017年9月15日 上午10:32:59
 * @version V1.0  
 */
@Repository("tmFieldProductDao")
public class TmFieldProductDaoImpl extends AbstractBaseDao<TmFieldProduct> implements TmFieldProductDao {

	
	@Override
	public List<TmFieldProduct> getTmFieldProductList(TmFieldProduct tmFieldProduct) {
		// TODO Auto-generated method stub
		if(tmFieldProduct != null){
			return queryForList(tmFieldProduct);
		}
		
		return null;
	}

}
