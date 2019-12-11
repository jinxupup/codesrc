package com.jjb.ecms.biz.dao.apply;

import com.jjb.ecms.infrastructure.TmCustomer;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * @Description: TODO
 * @author JYData-R&D-Big Star
 * @date 2017年3月22日 下午5:57:08
 * @version V1.0  
 */
public interface TmCustomerDao extends BaseDao<TmCustomer>{
	/**
	 * 根据custId查询客户信息
	 */
	public TmCustomer getTmCustomerByCustId(String custId);

}
