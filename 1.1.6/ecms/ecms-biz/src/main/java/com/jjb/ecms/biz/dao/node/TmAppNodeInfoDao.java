package com.jjb.ecms.biz.dao.node;

import java.util.List;

import com.jjb.ecms.infrastructure.TmAppNodeInfo;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * @description 节点信息查询
 * @author hn
 * @date 2016-9-1 17:15:41
 */
public interface TmAppNodeInfoDao extends BaseDao<TmAppNodeInfo> {

	public void saveTmAppNodeInfo(TmAppNodeInfo tmAppNodeInforecord);

	/**
	 * 获取该申请件所有的节点信息
	 * @param appNo
	 * @param nodeType
	 * @return
	 */
	public List<TmAppNodeInfo> getTmAppNodeInfoList(String appNo);
	/**
	 * 获取该申请件某个类型的节点信息
	 * @param appNo
	 * @param nodeType
	 * @return
	 */
	public List<TmAppNodeInfo> getTmAppNodeInfoList(String appNo,String nodeType);
	/**
	 * 更新节点信息列表记录
	 * @param tmAppNodeInforecord
	 */
	public void updateTmAppNodeInfoList(List<TmAppNodeInfo> nodeInfoList);
	/**
	 * @param appNodeInfo
	 */
	public void updateTmAppNodeInfo(TmAppNodeInfo appNodeInfo);
	
	/**删除节点信息列表记录
	 * @param appNodeInfo
	 */
	public void deleteTmAppNodeInfo(TmAppNodeInfo appNodeInfo);
	

}