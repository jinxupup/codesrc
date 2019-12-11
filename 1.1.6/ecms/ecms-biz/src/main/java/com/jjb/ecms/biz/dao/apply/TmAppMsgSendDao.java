package com.jjb.ecms.biz.dao.apply;

import java.util.List;

import com.jjb.ecms.infrastructure.TmAppMsgSend;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

/**
 * @Description: 附卡申请人信息表
 * @author JYData-R&D-Big T.T
 * @date 2016年8月31日 下午6:43:58
 * @version V1.0
 */
public interface TmAppMsgSendDao extends BaseDao<TmAppMsgSend> {

	/**
	 * 根据申请件编号appNo和status获取对应附卡申请人信息
	 * @param msgType status
	 * @return
	 */
	public List<TmAppMsgSend> getTmAppMsgSendListByMsgType(String smsType);
	public List<TmAppMsgSend> getTmAppMsgSendListByCondition(String condition);

	public TmAppMsgSend getTmAppMsgSendByAppNo(String appNo);

	public Page<TmAppMsgSend> getPage(Page<TmAppMsgSend> page);

}