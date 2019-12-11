package com.jjb.ecms.biz.dao.apply.impl;


import com.jjb.ecms.biz.dao.apply.TmMirCardBatchDao;
import com.jjb.ecms.infrastructure.TmMirCardBatch;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
  * @Description: 已申请卡信息处理表
  * @author JYData-R&D-Big Star
  * @date 2016年9月5日 下午7:18:28
  * @version V1.0
 */
@Repository("tmMirCardBatchDao")
public class TmMirCardBatchDaoImpl extends AbstractBaseDao<TmMirCardBatch> implements TmMirCardBatchDao {

	private static final String selectAll = "com.jjb.ecms.infrastructure.mapping.TmMirCardBatchMapper.selectAll";
	//private static final String selectCpsSystemAppByParam = "com.jjb.ecms.biz.ApplyTmMirCard.selectCpsSystemAppByParam";
//	private static final String selectMirCardByParam = "com.jjb.ecms.biz.ApplyTmMirCard.selectMirCardByParam";
	
	@Override
	public List<TmMirCardBatch> getTmMirCardBatchList(){

		Map<String, Object> queryMap = new HashMap<String, Object>();
		List<TmMirCardBatch> list = queryForList(selectAll, queryMap);

		/*TmMirCardBatch tmMirCardBatch = new TmMirCardBatch();
		List<TmMirCardBatch> tmMirCards = queryForList(tmMirCardBatch);*/
		return list;
	}

	@Override
	public void saveTmMirCardBatch(TmMirCardBatch tmMirCardBatch){
		save(tmMirCardBatch);
	}

	@Override
	public Page<TmMirCardBatch> queryTmMirCardBatchListPage(Page<TmMirCardBatch> page) {
		// TODO Auto-generated method stub
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		Page<TmMirCardBatch> p = queryForPageList(selectAll, page.getQuery(), page);
		return p;
	}
	
}
