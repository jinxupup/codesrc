package com.jjb.ecms.biz.dao.audit;

import java.util.List;

import com.jjb.ecms.infrastructure.TmBizAudit;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

/**
 * @ClassName TmBizAuditDao
 * Company jydata-tech
 * @Description TODO
 * Author wxl
 * Date 2019/7/6 14:32
 * Version 1.0
 */
public interface TmBizAuditDao extends BaseDao<TmBizAudit> {

    //保存审计历史
    public Integer saveAuditHistoryDao(TmBizAudit tmBizAudit);

    //查找审计历史
    public List<TmBizAudit> findAuditHistoryDao(TmBizAudit tmBizAudit);

    //查找审计历史
    public Page<TmBizAudit> findAuditHistoryPageDao(Page<TmBizAudit> page);

    //清除审计历史
    public Integer clearAuditHistoryDao(TmBizAudit tmBizAudit);






}
