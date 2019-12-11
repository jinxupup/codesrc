package com.jjb.ecms.biz.service.audit.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjb.ecms.biz.dao.audit.TmBizAuditDao;
import com.jjb.ecms.biz.service.audit.TmBizAuditService;
import com.jjb.ecms.infrastructure.TmBizAudit;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @ClassName TmBizAuditDaoImpl
 * Company jydata-tech
 * @Description TODO
 * Author wxl
 * Date 2019/7/6 14:32
 * Version 1.0
 */
@Service("tmBizAuditService")
public class TmBizAuditServiceImpl implements TmBizAuditService {

	private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    public TmBizAuditDao tmBizAuditDao;


    /**
     * 保存审计历史
     * @param tmBizAudit 对应类
     * @return 1成功 0失败
     */
    @Override
    public Integer saveAuditHistoryService(TmBizAudit tmBizAudit) {
        int save = 0;
        try {
                save = tmBizAuditDao.saveAuditHistoryDao(tmBizAudit);
                if (save == 0) {
                    throw new ProcessException("保存历史信息入库异常!");
            }
        } catch (ProcessException e) {
            logger.error("保存历史信息异常",e);
        }
        return save;
    }


    /**
     * 查找审计历史
     * @param tmBizAudit 对应类
     * @return
     */
    @Override
    public List<TmBizAudit> findAuditHistoryService(TmBizAudit tmBizAudit) {
        List<TmBizAudit> auditHistoryDao = null;
        if (StringUtils.isEmpty(tmBizAudit)) {
            logger.error("tmBizAudit 为空 ,未获取到查询条件! ");
        } else {
            try {
                auditHistoryDao = tmBizAuditDao.findAuditHistoryDao(tmBizAudit);
            } catch (Exception e) {
                logger.error("查询审计历史失败,返回null "+e.getMessage());
            }
        }
        return auditHistoryDao ;
    }

    /**
     * 查找审计历史-条件查询
     * @param page 分页类
     * @return page 分页类
     */
    @Override
    public Page<TmBizAudit> findAuditHistoryPageService(Page<TmBizAudit> page) {
        // TODO Auto-generated method stub
        if(null == page.getQuery()){
            page.setQuery(new Query());
        }
        Page<TmBizAudit> p = tmBizAuditDao.findAuditHistoryPageDao(page);
        return p;
    }

    /**
     * 清除审计历史 暂不使用
     * @param tmBizAudit
     * @return
     */
    @Override
    public Integer clearAuditHistoryService(TmBizAudit tmBizAudit) {
        return tmBizAuditDao.clearAuditHistoryDao(tmBizAudit);
    }

}
