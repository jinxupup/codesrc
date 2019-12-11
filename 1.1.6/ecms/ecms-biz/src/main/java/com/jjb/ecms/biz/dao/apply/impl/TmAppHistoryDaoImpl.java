package com.jjb.ecms.biz.dao.apply.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.apply.TmAppHistoryDao;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
  * @Description: 保存历史记录dao实现类
  * @author JYData-R&D-Big Star
  * @date 2016年9月2日 下午7:18:36
  * @version V1.0
 */
@Repository("tmAppHistoryDao")
public class TmAppHistoryDaoImpl extends AbstractBaseDao<TmAppHistory> implements TmAppHistoryDao {
	public static final String  selectAll ="com.jjb.ecms.infrastructure.mapping.TmAppHistoryMapper.selectAll";
	public static final String  getAppHistroyByParam ="com.jjb.ecms.biz.ApplyTmAppHistory.getAppHistroyByParam";
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 获取历史信息列表
	 */
	@Override
	public List<TmAppHistory> getTmAppHistoryByAppNo(String appNo)  throws ProcessException{
		if(StringUtils.isEmpty(appNo)){
			logger.info("申请件编号为空,请刷新！");
			throw new ProcessException("申请件编号为空，请刷新！");
		}
		TmAppHistory entity = new TmAppHistory();
		entity.setAppNo(appNo);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("_SORT_NAME", "id");
		queryMap.put("_SORT_ORDER", "asc");
		List<TmAppHistory> list = queryForList(entity, queryMap);
		return list;
	}

	/*
	 * 保存历史信息
	 */
	@Override
	public void saveTmAppHistory(TmAppHistory tmAppHistory) {
//		tmAppHistory.setJpaVersion(0);
		if(tmAppHistory!=null) {
			if(tmAppHistory.getCreateDate()==null){
				tmAppHistory.setCreateDate(new Date());
			}
			save(tmAppHistory);
		}
		
	}
	/*
	 * 更新历史信息
	 */	
	@Override
	public void updateTmAppHistory(TmAppHistory tmAppHistory) {
		// TODO Auto-generated method stub
		if(tmAppHistory!=null) {
			update(tmAppHistory);
		}
	}

	@Override
	public void deleteTmAppHistory(TmAppHistory entity) {
		if(entity!=null && StringUtils.isNotEmpty(entity.getAppNo())){
			List<TmAppHistory> list = queryForList(entity);
			if(CollectionUtils.sizeGtZero(list)){
				for (TmAppHistory dbEntity : list) {
					deleteByKey(dbEntity);
				}
			}
		}
	}

	/* 查询审批历史信息
	 */
	@Override
	public Page<TmAppHistory> getapprovalHistoryInfoPage(
			Page<TmAppHistory> page, String appNo) {
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		//加上页面查询的条件
		page.getQuery().put("appNo", appNo);
		
		Page<TmAppHistory> p = queryForPageList(selectAll, page.getQuery(), page);
		return p;
	}
	/**
	 * 根据参数获取审批历史信息
	 * @param TmAppHistory
	 * @return
	 */
	@Override
	public List<TmAppHistory> getAppHistroyByParam(Map<String,Object> map){
		if(map==null || map.size()==0) {
			return null;
		}
		return queryForList(getAppHistroyByParam, map);
	}
	/**
	 * 查询申请件历史信息
	 * @param TmAppHistory
	 * @return
	 */
	@Override
	public List<TmAppHistory> getTmAppHistoryList(TmAppHistory history) {
		if(history==null || StringUtils.isEmpty(history.getAppNo())
				|| StringUtils.isEmpty(history.getIdNo())
				|| StringUtils.isEmpty(history.getRtfState())
				|| StringUtils.isEmpty(history.getName())
				|| StringUtils.isEmpty(history.getId())
				|| StringUtils.isEmpty(history.getOperatorId())) {
			return null;
		}
		Map<String,Object> map = new HashMap<>();
		map.put("_SORT_NAME", "id");
		map.put("_SORT_ORDER", "asc");
		return queryForList(history, map);
	}
}
