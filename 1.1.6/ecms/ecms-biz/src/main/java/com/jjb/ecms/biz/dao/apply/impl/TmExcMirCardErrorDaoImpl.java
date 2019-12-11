package com.jjb.ecms.biz.dao.apply.impl;


import com.jjb.ecms.biz.dao.apply.TmExcMirCardErrorDao;
import com.jjb.ecms.infrastructure.TmMirCardExce;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import org.springframework.stereotype.Repository;

/**
 * 
  * @Description: 已申请卡信息处理表
  * @author JYData-R&D-Big Star
  * @date 2016年9月5日 下午7:18:28
  * @version V1.0
 */
@Repository("tmExcMirCardErrorDao")
public class TmExcMirCardErrorDaoImpl extends AbstractBaseDao<TmMirCardExce> implements TmExcMirCardErrorDao {

	@Override
	public void saveTmExcMirCardError(TmMirCardExce tmExcMirCardError){
		save(tmExcMirCardError);
	}

}
