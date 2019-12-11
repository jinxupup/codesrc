package com.jjb.ecms.biz.dao.apply.impl;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.apply.TmCustomerDao;
import com.jjb.ecms.infrastructure.TmCustomer;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;

/**
 * @Description: TODO
 * @author JYData-R&D-Big Star
 * @date 2017年3月22日 下午6:01:29
 * @version V1.0  
 */
@Repository("tmCustomerDao")
public class TmCustomerDaoImpl extends AbstractBaseDao<TmCustomer> implements TmCustomerDao {

	@Override
	public TmCustomer getTmCustomerByCustId(String custId) {
		TmCustomer tmCustomer = new TmCustomer();
		tmCustomer.setCustId(custId);	

		return queryByKey(tmCustomer);
	}

}
