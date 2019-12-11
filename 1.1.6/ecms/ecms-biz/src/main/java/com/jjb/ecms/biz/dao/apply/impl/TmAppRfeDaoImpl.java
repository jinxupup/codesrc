package com.jjb.ecms.biz.dao.apply.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.apply.TmAppRfeDao;
import com.jjb.ecms.infrastructure.TmAppRfe;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: 补件信息
 * @author JYData-R&D-HN
 * @date 2016年10月21日 下午11:49:40
 * @version V1.0
 */
@Repository("tmAppRfeDao")
public class TmAppRfeDaoImpl extends AbstractBaseDao<TmAppRfe> implements TmAppRfeDao{

	@Override
	public void saveTmAppRfe(TmAppRfe tmAppRfe) {
		if(tmAppRfe!=null){
			if(tmAppRfe.getPbStartDate()==null){
				tmAppRfe.setPbStartDate(new Date());
			}
			save(tmAppRfe);
		}
	}

	@Override
	public void deleteTmAppRfe(TmAppRfe entity) {
		if(entity!=null && StringUtils.isNotEmpty(entity.getAppNo())){
			List<TmAppRfe> list = queryForList(entity);
			if(CollectionUtils.sizeGtZero(list)){
				for (TmAppRfe dbEntity : list) {
					deleteByKey(dbEntity);
				}
			}
		}
	}

	@Override
	public List<TmAppRfe> getTmAppRfeByParam(TmAppRfe tmAppRfe) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("_SORT_NAME", "pbType");
		queryMap.put("_SORT_ORDER", "asc");
		List<TmAppRfe> list = queryForList(tmAppRfe, queryMap);
		return list;
	}

	
	@Override
	public TmAppRfe getTmAppRfeByKey(Integer key) {
		TmAppRfe tmAppRfe = new TmAppRfe();
		tmAppRfe.setId(key);		

		return queryByKey(tmAppRfe);
	}
	
}