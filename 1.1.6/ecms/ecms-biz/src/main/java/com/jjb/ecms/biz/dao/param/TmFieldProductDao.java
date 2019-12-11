/**
 * 
 */
package com.jjb.ecms.biz.dao.param;

import java.util.List;

import com.jjb.ecms.infrastructure.TmFieldProduct;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * @Description: TODO
 * @author JYData-R&D-Big T.T
 * @date 2017年9月15日 上午10:29:36
 * @version V1.0  
 */

public interface TmFieldProductDao extends BaseDao<TmFieldProduct> {

	public List<TmFieldProduct> getTmFieldProductList(TmFieldProduct tmFieldProduct);
}
