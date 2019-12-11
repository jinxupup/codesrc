package com.jjb.ecms.biz.dao.node.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.node.TmAppNodeInfoDao;
import com.jjb.ecms.infrastructure.TmAppNodeInfo;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @description 节点信息Dao实现类
 * @author hn
 * @date 2016年9月1日17:21:03 
 */
@Repository("tmAppNodeInforecordDao")
public class TmAppNodeInfoDaoImpl extends AbstractBaseDao<TmAppNodeInfo> implements TmAppNodeInfoDao {

	@Override
	public void saveTmAppNodeInfo(TmAppNodeInfo tmAppNodeInforecord) {
		save(tmAppNodeInforecord);
	}
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 获取该申请件所有的节点信息 
	 */
	@Override
	public List<TmAppNodeInfo> getTmAppNodeInfoList(String appNo)  throws ProcessException{
		if(StringUtils.isEmpty(appNo)){
			logger.info("申请件编号为空，请刷新");
			throw new ProcessException("申请件编号为空，请刷新！");			
		}
		TmAppNodeInfo entity = new TmAppNodeInfo();
		entity.setAppNo(appNo);
		List<TmAppNodeInfo> list = queryForList(entity);
		return list;
	}
	
	/*
	 * 获取该申请件所有的节点信息 
	 */
	@Override
	public List<TmAppNodeInfo> getTmAppNodeInfoList(String appNo,String nodeType)  throws ProcessException{
		if(StringUtils.isEmpty(appNo) ||StringUtils.isEmpty(nodeType) ){
			logger.info("申请件编号["+appNo+"]或者节点类型["+nodeType+"]为空，请刷新");
			throw new ProcessException("申请件编号["+appNo+"]或者节点类型["+nodeType+"]为空，请刷新！");			
		}
		TmAppNodeInfo entity = new TmAppNodeInfo();
		entity.setAppNo(appNo);
		entity.setInfoType(nodeType);
		List<TmAppNodeInfo> list = queryForList(entity);
		return list;
	}
	/**
	 * 更新节点信息列表记录
	 * @param tmAppNodeInforecord
	 */
	@Override
	public void updateTmAppNodeInfoList(
			List<TmAppNodeInfo> nodeInfoList) {
		if(nodeInfoList!=null){
			for (int i = 0; i < nodeInfoList.size(); i++) {
				update(nodeInfoList.get(i));
			}
		}
	}
	@Override
	public void updateTmAppNodeInfo(TmAppNodeInfo tmAppNodeInforecord) {
		update(tmAppNodeInforecord);
	}

	@Override
	public void deleteTmAppNodeInfo(TmAppNodeInfo entity) {
		if(entity!=null && StringUtils.isNotEmpty(entity.getAppNo())){
			List<TmAppNodeInfo> list = queryForList(entity);
			if(CollectionUtils.sizeGtZero(list)){
				for (TmAppNodeInfo dbEntity : list) {
					deleteByKey(dbEntity);
				}
			}
		}
	}
	
}
