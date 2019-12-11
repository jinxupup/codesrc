package com.jjb.acl.biz.utils;

import com.jjb.acl.biz.service.TmSystemAuditService;

import com.jjb.acl.infrastructure.TmSystemAudit;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 审计历史记录工具类
 */
@Component
public class SystemAuditHistoryUtils {

    private static Logger logger = LoggerFactory.getLogger(SystemAuditHistoryUtils.class);

    @Autowired
    private TmSystemAuditService tmSystemAuditService;


    /**
     * 系统参数审计历史页面查询
     * @param page
     * @return
     */
    public Page<TmSystemAudit> getSystemAuditPage(Page<TmSystemAudit> page) {
        try {
            page = tmSystemAuditService.getPage(page,"");
        } catch (ProcessException e) {
            logger.error("参数审计历史条件查询失败!!!", e);
        }
        return page;
    }


    /**
     * 保存系统参数审计历史
     *
     * @param operatorId     修改对象
     * @param paramKey       修改内容
     * @param paramOperation 操作类型
     * @param oldObject      老值
     * @param newObject      新值
     */
    public void saveSystemAudit(String operatorId, String paramKey, String paramOperation, String oldObject, String newObject) {
        if (StringUtils.isEmpty(paramKey) | StringUtils.isEmpty(paramOperation)) {
            logger.error("保存参数审计历史信息异常 paramKey / paramOperation 为空值");
            throw new ProcessException("保存参数审计历史信息异常 paramKey / paramOperation 为空值");
        } else {
            TmSystemAudit tmSystemAudit = new TmSystemAudit();
            tmSystemAudit.setOperatorId(operatorId);
            tmSystemAudit.setParamKey(paramKey);
            tmSystemAudit.setParamOperation(paramOperation);
            tmSystemAudit.setOldObject(oldObject);
            tmSystemAudit.setNewObject(newObject);
            try {
                tmSystemAuditService.saveTmSystemAuditField(tmSystemAudit);
            } catch (Exception e) {
                logger.error("保存参数审计历史信息异常-数据存入失败!!! tmSystemAudit = " + tmSystemAudit);
                throw new ProcessException("保存参数审计历史信息异常-数据存入失败!!!");
            }
        }
    }

}
