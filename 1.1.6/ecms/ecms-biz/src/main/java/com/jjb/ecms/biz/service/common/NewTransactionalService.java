package com.jjb.ecms.biz.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jjb.ecms.biz.dao.apply.TmAppHistoryDao;
import com.jjb.ecms.biz.dao.apply.TmAppMainDao;
import com.jjb.ecms.biz.dao.apply.TmAppMemoDao;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppMemo;

/**
 * @Description: TODO  为了在事务控制里即使失败回滚也需要强制保存一些信息而存在的类。
 *               TODO :Propagation.REQUIRES_NEW 当前的方法必须启动新的事务，并在它自己的事务内运行，如果有事务正在运行，则将其挂起
 * @Author: shiminghong
 * @Data: 2019/5/18 11:23  NewTransactionalService
 * @Version 1.0
 */
@Component
public class NewTransactionalService {
    @Autowired
    ApplyQueryService applyQueryService;
    @Autowired
    ApplyInputService applyInputService;
    @Autowired
    TmAppHistoryDao tmAppHistoryDao;
    @Autowired
    TmAppMainDao tmAppMainDao;
    @Autowired
    TmAppMemoDao tmAppMemoDao;

    /**
     * 新建事务保存tmappmain信息
     *
     * @param tmAppMain
     */
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateTmAppMain(TmAppMain tmAppMain) {
        applyInputService.updateTmAppMain(tmAppMain);
    }

    /**
     * 新建事务保存tmapphistory信息
     *
     * @param tmAppHistory
     */
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveTmAppHistory(TmAppHistory tmAppHistory) {
        applyInputService.saveTmAppHistory(tmAppHistory);
    }

    /**
     * 新建事务保存tmappmemo信息
     *
     * @param tmAppMemo
     */
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveTmAppMemo(TmAppMemo tmAppMemo) {
        applyInputService.saveTmAppMemo(tmAppMemo);
    }
}
