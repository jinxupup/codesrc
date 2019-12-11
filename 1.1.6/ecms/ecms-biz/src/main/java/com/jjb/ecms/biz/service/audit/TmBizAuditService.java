package com.jjb.ecms.biz.service.audit;


import java.util.List;

import com.jjb.ecms.infrastructure.TmBizAudit;
import com.jjb.unicorn.facility.model.Page;

/**
 * @ClassName TmBizAuditDao
 * Company jydata-tech
 * @Description TODO
 * Author zhangwenlu
 * Date 2019/7/6 14:32
 * Version 1.0
 */
public interface TmBizAuditService {

    //保存审计历史
    public Integer saveAuditHistoryService(TmBizAudit tmBizAudit);

    //查找审计历史-appNo查询
    public List<TmBizAudit> findAuditHistoryService(TmBizAudit tmBizAudit);

    //查找审计历史-条件查询
    public Page<TmBizAudit> findAuditHistoryPageService(Page<TmBizAudit> page);

    //清除审计历史
    public Integer clearAuditHistoryService(TmBizAudit tmBizAudit);






}
