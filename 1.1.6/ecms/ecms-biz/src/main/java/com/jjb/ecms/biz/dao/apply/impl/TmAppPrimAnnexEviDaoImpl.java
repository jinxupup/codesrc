package com.jjb.ecms.biz.dao.apply.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.apply.TmAppPrimAnnexEviDao;
import com.jjb.ecms.infrastructure.TmAppPrimAnnexEvi;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: 申请附件证明信息
 * @author JYData-R&D-Big T.T
 * @date 2016年8月31日 下午6:43:58
 * @version V1.0  
 */
@Repository("tmAppPrimAnnexEviDao")
public class TmAppPrimAnnexEviDaoImpl extends AbstractBaseDao<TmAppPrimAnnexEvi> implements TmAppPrimAnnexEviDao {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public TmAppPrimAnnexEvi getTmAppPrimAnnexEviByAppNo(String appNo) throws ProcessException{

		if(StringUtils.isEmpty(appNo)){
			logger.info("申请件编号为空,请刷新！");
			throw new ProcessException("申请件编号为空，请刷新！");
		}
		TmAppPrimAnnexEvi entity = new TmAppPrimAnnexEvi();
		entity.setAppNo(appNo);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("appNo", appNo);
		List<TmAppPrimAnnexEvi> list = queryForList(entity, queryMap);
		if(CollectionUtils.isNotEmpty(list) && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void saveTmAppPrimAnnexEvi(TmAppPrimAnnexEvi tmAppPrimAnnexEvi) {
		// TODO Auto-generated method stub
		
		save(tmAppPrimAnnexEvi);
	}

	@Override
	public void updateTmAppPrimAnnexEvi(TmAppPrimAnnexEvi tmAppPrimAnnexEvi) {
		// TODO Auto-generated method stub
		
		update(tmAppPrimAnnexEvi);
	}

	@Override
	public void deleteTmAppPrimAnnexEvi(TmAppPrimAnnexEvi entity) {
		if(entity!=null && StringUtils.isNotEmpty(entity.getAppNo())){
			List<TmAppPrimAnnexEvi> list = queryForList(entity);
			if(CollectionUtils.sizeGtZero(list)){
				for (TmAppPrimAnnexEvi dbEntity : list) {
					deleteByKey(dbEntity);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see TmAppPrimAnnexEviDao#updateNotNullTmAppPrimAnnexEvi(TmAppPrimAnnexEvi)
	 */
	@Override
	public void updateNotNullTmAppPrimAnnexEvi(
			TmAppPrimAnnexEvi tmAppPrimAnnexEvi) {
		updateNotNullable(tmAppPrimAnnexEvi);
		
	}
	
}