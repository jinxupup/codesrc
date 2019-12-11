package com.jjb.ecms.biz.dao.apply.impl;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.apply.TmMirMakeResDao;
import com.jjb.ecms.infrastructure.TmMirMakeRes;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;

/**
 * @Description: TODO
 * @author JYData-R&D-Big Star
 * @date 2017年3月22日 下午7:09:36
 * @version V1.0  
 */
@Repository("tmMirMakeResDao")
public class TmMirMakeResDaoImpl extends AbstractBaseDao<TmMirMakeRes> implements TmMirMakeResDao {

	
	@Override
	public TmMirMakeRes getTmMirMakeResByAppNo(String appNo) {
		TmMirMakeRes tmResultCardmakerMps = new TmMirMakeRes();
		tmResultCardmakerMps.setAppNo(appNo);		

		return queryByKey(tmResultCardmakerMps);
	}

}
