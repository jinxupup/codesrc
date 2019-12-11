package com.jjb.ecms.biz.service.query.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjb.ecms.biz.dao.apply.TmAppOrderMainDao;
import com.jjb.ecms.biz.service.query.TmAppOrderMainService;
import com.jjb.ecms.infrastructure.TmAppOrderMain;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.StringUtils;


/**
 * @ClassName TmBizAuditDaoImpl
 * Company jydata-tech
 * @Description 大额分期页面查询修改
 * Author wxl
 * Date 2019/8/23 14:32
 * Version 1.0
 */
@Service("TmAppOrderMainService")
public class TmAppOrderMainServiceImpl implements TmAppOrderMainService {



    @Autowired
    public TmAppOrderMainDao tmAppOrderMainDao;


    /**
     * 条件查询
     * @param page 分页类
     * @return page 分页类
     */
    @Override
    public Page<TmAppOrderMain> queryTmAppOrderMainPage(Page<TmAppOrderMain> page) {
        if(null == page.getQuery()){
            page.setQuery(new Query());
        }
        return tmAppOrderMainDao.queryTmAppOrderMainPage(page);
    }


    /**
     * 更具AppNo查询ID修改timerState
     * @param  appNo
     * @return true / false
     */
    @Override
    public void updateOrderMainTimerState(String appNo, String timerState) {
        if (StringUtils.isEmpty(appNo)) {
            throw new ProcessException("更具AppNo查询ID失败appNo为空");
        }
        TmAppOrderMain orderMain = tmAppOrderMainDao.getTmAppOrderMainByAppNo(appNo);
        orderMain.setTimerState(timerState);
        try {
            tmAppOrderMainDao.updateTmAppOrderMain(orderMain);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
