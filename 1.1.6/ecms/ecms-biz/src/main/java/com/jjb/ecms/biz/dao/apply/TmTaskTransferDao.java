package com.jjb.ecms.biz.dao.apply;

import java.util.List;

import com.jjb.ecms.infrastructure.TmTaskTransfer;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * @description: 任务转移记录表DAO
 * @author -BigZ.Y
 * @date 2016年10月27日 下午5:47:15 
 */
public interface TmTaskTransferDao extends BaseDao<TmTaskTransfer>{
	/**
	 * 获取未完成的记录
	 * @param tmTaskTransfer
	 * @return
	 */
	List<TmTaskTransfer> getUncompletedTask(TmTaskTransfer tmTaskTransfer);
	/**
	 * 根据appNo查询案件流转记录并排序
	 * @param appNo
	 * @return
	 */
	List<TmTaskTransfer> getTaskTransferRecordList(String appNo);

}
