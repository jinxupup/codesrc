package com.jjb.ecms.biz.dao.apply.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.apply.TmTaskTransferDao;
import com.jjb.ecms.infrastructure.TmTaskTransfer;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.exception.ProcessException;

/**
 * @description: TODO
 * @author -BigZ.Y
 * @date 2016年10月27日 下午5:49:31 
 */
@Repository("tmTaskTransferDao")
public class TmTaskTransferDaoImpl extends AbstractBaseDao<TmTaskTransfer> implements TmTaskTransferDao {

	private static final String selectUnCompleted = "com.jjb.ecms.biz.ApplyTmTaskTransferMapper.selectUnCompleted";//获取未完成的任务转移记录
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 获取未完成的记录
	 * @param tmTaskTransfer
	 * @return
	 */
	@Override
	public List<TmTaskTransfer> getUncompletedTask(
			TmTaskTransfer tmTaskTransfer) {
		// TODO Auto-generated method stub
		return queryForList(selectUnCompleted, tmTaskTransfer);
	}

	/**
	 *根据appNo查询案件流转记录
	 * @param appNo
	 * @return
	 */
	@Override
	public List<TmTaskTransfer> getTaskTransferRecordList(String appNo) {
		if (StringUtils.isEmpty(appNo)) {
			logger.info("申请件编号为空,请刷新！");
			throw new ProcessException("申请件编号为空，请刷新！");
		}
		TmTaskTransfer taskTransferRecord = new TmTaskTransfer();
		taskTransferRecord.setAppNo(appNo);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("_SORT_NAME", "id");
		queryMap.put("_SORT_ORDER", "asc");
		List<TmTaskTransfer> list = queryForList(taskTransferRecord, queryMap);
		return list;
	}
	
	
}
