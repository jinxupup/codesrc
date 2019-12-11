package com.jjb.ecms.biz.dao.apply.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.apply.TmEcmsBatchDao;
import com.jjb.ecms.infrastructure.TmEcmsBatch;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;

/**
 * 
  * @Description: 已申请卡信息处理表
  * @author JYData-R&D-Big Star
  * @date 2016年9月5日 下午7:18:28
  * @version V1.0
 */
@Repository("tmEcmsBatchDao")
public class TmEcmsBatchDaoImpl extends AbstractBaseDao<TmEcmsBatch> implements TmEcmsBatchDao {

	//private static final String selectAll = "TmMirCard.selectAll";
	//private static final String selectCpsSystemAppByParam = "com.jjb.ecms.biz.ApplyTmMirCard.selectCpsSystemAppByParam";
//	private static final String selectMirCardByParam = "com.jjb.ecms.biz.ApplyTmMirCard.selectMirCardByParam";


	@Override
	public TmEcmsBatch getTmEcmsBatch(TmEcmsBatch tmEcms){
		TmEcmsBatch tmEcmsBatch = queryByKey(tmEcms);
		return tmEcmsBatch;
	}

	@Override
	public void saveTmEcmsBatch(TmEcmsBatch tmEcmsBatch){
		save(tmEcmsBatch);
	}

	@Override
	public void updateTmEcmsBatch(TmEcmsBatch tmEcmsBatch){
		update(tmEcmsBatch);
	}

	@Override
	public List<TmEcmsBatch> getTmEcmsBatchList(){
		TmEcmsBatch tmEcmsBatch = new TmEcmsBatch();
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("_SORT_NAME", "processTime");
//		map.put("_SORT_ORDER", "DESC");
//		queryForList(tmEcmsBatch, map);
		return queryForList(tmEcmsBatch);
	}

}
