package com.jjb.ecms.biz.service.query;


import com.jjb.ecms.infrastructure.TmAppOrderMain;
import com.jjb.unicorn.facility.model.Page;

/**
 * @ClassName TmBizAuditDao
 * Company jydata-tech
 * @Description 大额分期页面查询修改
 * Author wxl
 * Date 2019/8/23 14:32
 * Version 1.0
 */
public interface TmAppOrderMainService {


    //查找审计历史-条件查询
    public Page<TmAppOrderMain> queryTmAppOrderMainPage(Page<TmAppOrderMain> page);


    //修改定时器状态
    public void updateOrderMainTimerState(String appNo, String timerState);



}
