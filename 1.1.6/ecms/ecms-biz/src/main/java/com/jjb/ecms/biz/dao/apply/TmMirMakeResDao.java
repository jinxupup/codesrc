package com.jjb.ecms.biz.dao.apply;

import com.jjb.ecms.infrastructure.TmMirMakeRes;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * @Description: TODO
 * @author JYData-R&D-Big Star
 * @date 2017年3月22日 下午7:07:05
 * @version V1.0  
 */
public interface TmMirMakeResDao extends BaseDao<TmMirMakeRes>{
	
	/**
	 *根据appNo查询
	 */
	public TmMirMakeRes getTmMirMakeResByAppNo(String appNo); 

}
