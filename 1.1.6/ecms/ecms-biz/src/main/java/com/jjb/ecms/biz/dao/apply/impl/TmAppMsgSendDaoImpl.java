package com.jjb.ecms.biz.dao.apply.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.apply.TmAppMsgSendDao;
import com.jjb.ecms.infrastructure.TmAppMsgSend;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: 附卡申请人信息表
 * @author JYData-R&D-Big T.T
 * @date 2016年8月31日 下午6:43:58
 * @version V1.0
 */
@Repository("tmAppMsgSendDao")
public class TmAppMsgSendDaoImpl extends AbstractBaseDao<TmAppMsgSend> implements TmAppMsgSendDao {
	public static final String  selectAll = "com.jjb.ecms.infrastructure.mapping.TmAppMsgSendMapper.selectAll";
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public List<TmAppMsgSend> getTmAppMsgSendListByCondition(String condition){

		TmAppMsgSend entity = new TmAppMsgSend();
		if(StringUtils.isEmpty(condition)){
			logger.info("短信类型为空,请刷新！");
			throw new ProcessException("短信类型为空，请刷新！");
		}
		entity.setCondition(condition);
		List<TmAppMsgSend> list = queryForList(entity);

		return list;
	}

	@Override
	public List<TmAppMsgSend> getTmAppMsgSendListByMsgType(String smsType){

		TmAppMsgSend entity = new TmAppMsgSend();
		if(StringUtils.isEmpty(smsType)){
			logger.info("短信类型为空,请刷新！");
			throw new ProcessException("短信类型为空，请刷新！");
		}
		entity.setMsgType(smsType);
		List<TmAppMsgSend> list = queryForList(entity);

		return list;
	}

	public TmAppMsgSend getTmAppMsgSendByAppNo(String appNo){
		TmAppMsgSend entity = new TmAppMsgSend();
		if(StringUtils.isEmpty(appNo)){
			logger.info("申请件编号为空,请刷新！");
			throw new ProcessException("申请件编号为空，请刷新！");
		}
		entity.setAppNo(appNo);
		TmAppMsgSend tmAppMsgSend = queryForOne(entity);
		return tmAppMsgSend;
	}

	@Override
	public Page<TmAppMsgSend> getPage(Page<TmAppMsgSend> page) {
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		page = queryForPageList(selectAll, page.getQuery(), page);
		return page;
	}

}