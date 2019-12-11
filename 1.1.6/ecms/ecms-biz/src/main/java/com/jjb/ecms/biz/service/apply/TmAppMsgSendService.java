
package com.jjb.ecms.biz.service.apply;



import java.util.List;

import com.jjb.ecms.infrastructure.TmAppMsgSend;
import com.jjb.ecms.infrastructure.TmAppUserRelation;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Page;

/**
 * @Description: 短信信息表
 * @author JYData-R&D-Big T.T
 * @date 2019年2月22日 下午7:40:26
 * @version V1.0
 */

public interface TmAppMsgSendService {

	/**
	 * 根据msgType获取短信信息表
	 * @param appNo
	 * @return
	 * @throws ProcessException
	 */
	public List<TmAppMsgSend> getTmAppMsgSendListByMsgType(String msgType) throws ProcessException;
	public List<TmAppMsgSend> getTmAppMsgSendListByCondition(String condition) throws ProcessException;

	/**
	 * 更新短信信息表信息
	 * @param tmAppMsgSend
	 */
	public void updateTmAppMsgSend(TmAppMsgSend tmAppMsgSend);

	public TmAppMsgSend getTmAppMsgSendByAppNo(String appNo) throws ProcessException;

	public Page<TmAppMsgSend> getPage(Page<TmAppMsgSend> page);

	public Page<TmAppUserRelation> getUserPage(Page<TmAppUserRelation> page);

	public void saveTmAppMsgSend(TmAppMsgSend tmAppMsgSend);
	/**
	 * 根据申请件编号获取业务员关系表对象
	 * @param appNo
	 * @return
	 */
	public TmAppUserRelation getTmAppUserRelationByUserNo(String userNo) throws ProcessException;


}
