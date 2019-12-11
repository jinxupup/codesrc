package com.jjb.ecms.biz.dao.apply.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.apply.TmAppPrimCardInfoDao;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: 申请主卡卡片信息表
 * @author JYData-R&D-Big T.T
 * @date 2016年8月31日 下午6:43:58
 * @version V1.0  
 */
@Repository("tmAppPrimCardInfoDao")
public class TmAppPrimCardInfoDaoImpl extends AbstractBaseDao<TmAppPrimCardInfo> implements TmAppPrimCardInfoDao {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public TmAppPrimCardInfo getTmAppPrimCardInfoByAppNo(String appNo)  throws ProcessException{

		if(StringUtils.isEmpty(appNo)){
			logger.info("申请件编号为空,请刷新！");
			throw new ProcessException("申请件编号为空，请刷新！");
		}
		TmAppPrimCardInfo entity = new TmAppPrimCardInfo();
		entity.setAppNo(appNo);
		List<TmAppPrimCardInfo> list = queryForList(entity);
		if(CollectionUtils.isNotEmpty(list) && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void saveTmAppPrimCardInfo(TmAppPrimCardInfo tmAppPrimCardInfo) {
		// TODO Auto-generated method stub
		
		save(tmAppPrimCardInfo);
	}

	@Override
	public void updateTmAppPrimCardInfo(TmAppPrimCardInfo tmAppPrimCardInfo) {
		// TODO Auto-generated method stub
		
		update(tmAppPrimCardInfo);
	}
	
	@Override
	public void deleteTmAppPrimCardInfo(TmAppPrimCardInfo entity) {
		if(entity!=null && StringUtils.isNotEmpty(entity.getAppNo())){
			List<TmAppPrimCardInfo> list = queryForList(entity);
			if(CollectionUtils.sizeGtZero(list)){
				for (TmAppPrimCardInfo dbEntity : list) {
					deleteByKey(dbEntity);
				}
			}
		}
	}

//	@Override
//	public TmAppPrimCardInfo queryByKey(TmAppPrimCardInfo tmAppPrimCardInfo){
//		return queryByKey(tmAppPrimCardInfo);
//	}

	/* 
	 * 
	 */
	@Override
	public void updateNotNullTmAppPrimCardInfo(
			TmAppPrimCardInfo tmAppPrimCardInfo) {
		updateNotNullable(tmAppPrimCardInfo);
		
	}
	
}