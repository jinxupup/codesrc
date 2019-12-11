package com.jjb.ecms.biz.service.apply.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.dao.apply.TmAppMsgSendDao;
import com.jjb.ecms.biz.dao.apply.TmAppUserRelationDao;
import com.jjb.ecms.biz.service.apply.TmAppMsgSendService;
import com.jjb.ecms.infrastructure.TmAppMsgSend;
import com.jjb.ecms.infrastructure.TmAppUserRelation;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Page;

/**
 * Author HP
 */
@Service("tmAppMsgSendServiceImpl")
public class TmAppMsgSendServiceImpl implements TmAppMsgSendService {
    @Autowired
    private TmAppMsgSendDao tmAppMsgSendDao;
    @Autowired
    private TmAppUserRelationDao tmAppUserRelationDao;

    /**
     * 获取短信信息LIST清单
     */
    @Override
    @Transactional

    public List<TmAppMsgSend> getTmAppMsgSendListByMsgType(String smsType)
            throws ProcessException {
        return tmAppMsgSendDao.getTmAppMsgSendListByMsgType(smsType);
    }

    @Override
    @Transactional

    public List<TmAppMsgSend> getTmAppMsgSendListByCondition(String condition) throws ProcessException{
        return tmAppMsgSendDao.getTmAppMsgSendListByCondition(condition);
    }

    /**
     * 更新业务主表
     */
    @Override
    @Transactional
    public void updateTmAppMsgSend(TmAppMsgSend tmAppMsgSend) {
        if(tmAppMsgSend!=null){
            tmAppMsgSendDao.update(tmAppMsgSend);

        }
    }


    public TmAppMsgSend getTmAppMsgSendByAppNo(String appNo){
        return tmAppMsgSendDao.getTmAppMsgSendByAppNo(appNo);
    }

    @Override
    @Transactional
    public Page<TmAppMsgSend> getPage(Page<TmAppMsgSend> page){
        return tmAppMsgSendDao.getPage(page);
    }

    public Page<TmAppUserRelation> getUserPage(Page<TmAppUserRelation> page){
        return tmAppUserRelationDao.getPage(page);
    }

    @Override
    @Transactional
    public void saveTmAppMsgSend(TmAppMsgSend tmAppMsgSend){
        if(tmAppMsgSend!=null){
            tmAppMsgSendDao.save(tmAppMsgSend);

        }
    }



    /**
     * 根据申请件编号获取业务员关系表对象
     * @param appNo
     * @return
     */
    public TmAppUserRelation getTmAppUserRelationByUserNo(String userNo) throws ProcessException{
        TmAppUserRelation tmAppUserRelation= tmAppUserRelationDao.getTmAppUserRelationByUserNo(userNo);
        return tmAppUserRelation;
    }

}
