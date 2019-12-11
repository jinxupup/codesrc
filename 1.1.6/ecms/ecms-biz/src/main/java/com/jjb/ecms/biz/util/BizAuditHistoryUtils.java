package com.jjb.ecms.biz.util;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.audit.TmBizAuditService;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmBizAudit;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 审计历史记录工具类
 */
@Component
public class BizAuditHistoryUtils {

    private static Logger logger = LoggerFactory.getLogger(AppCommonUtil.class);

    @Autowired
    public TmBizAuditService tmBizAuditService;
    @Autowired
    private ApplyQueryService applyQueryService;
    @Autowired
    private CacheContext cacheContext;


    /**
     * 保存历史信息
     * 记录申请件操作
     * @param appNo   申请件编号
     * @param OrdType 操作环节
     * @return
     */
    public Integer saveAuditHistory(String appNo, String OrdType) {
        if (StringUtils.isEmpty(appNo)) {
            return null;
        }

        String taskName = null;
        try {
            // 保存审计历史
            TmAppMain tmAppMain = applyQueryService.getTmAppMainByAppNo(appNo);
			if (tmAppMain != null) {
				taskName = tmAppMain.getRtfState();
				if (StringUtils.isNotEmpty(tmAppMain.getRtfState())) {
					TmAclDict dict = cacheContext.getAclDictByTypeAndCode(AppDitDicConstant.RTF_STATE,
							tmAppMain.getRtfState());
					if (dict != null) {
						taskName = dict.getCodeName();
					} else {
						throw new ProcessException(AppDitDicConstant.RTF_STATE + "业务字典获取审批状态失败");
					}
				}
				TmBizAudit tmBizAudit = new TmBizAudit();
	            tmBizAudit.setOrg(tmAppMain.getOrg());
	            tmBizAudit.setAppNo(appNo);
	            tmBizAudit.setName(tmAppMain.getName());
	            tmBizAudit.setIdNo(tmAppMain.getIdNo());
	            tmBizAudit.setOrdType(OrdType);
	            tmBizAudit.setRtfState(taskName);
	            tmBizAudit.setOperatorId(OrganizationContextHolder.getUserNo());
	            tmBizAudit.setCreateDate(new Date());
	            tmBizAudit.setJpaVersion(0);
	            try {
	                if (StringUtils.isEmpty(tmBizAudit.getAppNo()) | StringUtils.isEmpty(tmBizAudit.getOperatorId())
	                        | StringUtils.isEmpty(tmBizAudit.getName()) | StringUtils.isEmpty(tmBizAudit.getIdNo())) {
	                    throw new ProcessException("保存审计历史信息异常" + tmBizAudit.getAppNo() + tmBizAudit.getOperatorId()
	                            + tmBizAudit.getName() + tmBizAudit.getIdNo() + "有空值");
	                } else {
	                    logger.info("开始保存 -->>>>-- 审计历史");
	                    tmBizAuditService.saveAuditHistoryService(tmBizAudit);
	                    logger.info("结束保存 -->>>>-- 审计历史");
	                }
	            } catch (ProcessException e) {
	                logger.error("记录审计历史失败..", e);
	            }
			}
        } catch (ProcessException e) {
            logger.error("保存历史信息异常", e);
        return null;
        }

        return null;
    }

    /**
     * 保存历史详细信息
     *  记录申请件详细信息
     * @param appNo      申请件编号
     * @param OrdType    操作环节
     * @param operatorDo 详细信息
     * @return
     */
    public Integer saveAuditHistoryDetails(String appNo, String OrdType, String operatorDo) {
        if (StringUtils.isEmpty(appNo)) {
            return null;
        }
        String taskName = null;
        try {
            // 保存审计历史
            TmAppMain tmAppMain = applyQueryService.getTmAppMainByAppNo(appNo);
            if (tmAppMain!=null) {
            taskName = tmAppMain.getRtfState();
            if(StringUtils.isNotEmpty(tmAppMain.getRtfState())) {
                TmAclDict dict = cacheContext.getAclDictByTypeAndCode(AppDitDicConstant.RTF_STATE, tmAppMain.getRtfState());
                if (dict != null) {
                    taskName = dict.getCodeName();
                } else {
                    throw new ProcessException(AppDitDicConstant.RTF_STATE+"业务字典获取审批状态失败");
                }
            }
            }
            TmBizAudit tmBizAudit = new TmBizAudit();
            tmBizAudit.setOrg(tmAppMain.getOrg());
            tmBizAudit.setAppNo(appNo);
            tmBizAudit.setName(tmAppMain.getName());
            tmBizAudit.setIdNo(tmAppMain.getIdNo());
            tmBizAudit.setOrdType(OrdType);
            tmBizAudit.setRtfState(taskName);
            tmBizAudit.setOperatorId(OrganizationContextHolder.getUserNo());
            tmBizAudit.setCreateDate(new Date());
            tmBizAudit.setOperatorDo(operatorDo);
            tmBizAudit.setJpaVersion(0);
            try {
                if (StringUtils.isEmpty(tmBizAudit.getAppNo()) | StringUtils.isEmpty(tmBizAudit.getOperatorId())
                        | StringUtils.isEmpty(tmBizAudit.getName()) | StringUtils.isEmpty(tmBizAudit.getIdNo())) {
                    throw new ProcessException("保存审计历史信息异常" + tmBizAudit.getAppNo() + tmBizAudit.getOperatorId()
                            + tmBizAudit.getName() + tmBizAudit.getIdNo() + "有空值");
                } else {
                    logger.info("开始保存 -->>>>-- 审计历史");
                    tmBizAuditService.saveAuditHistoryService(tmBizAudit);
                    logger.info("结束保存 -->>>>-- 审计历史");
                }
            } catch (ProcessException e) {
                logger.error("记录审计历史失败..", e);
            }
        } catch (ProcessException e) {
            logger.error("保存历史信息异常", e);
        }

        return null;
    }

    /**
     * 保存审计历史,不按件来保存仅仅记录操作
     * 申请件操作详细信息记录
     * @param OrdType 操作信息
     * @return
     */
    public void saveAuditHistoryByOrdType(String OrdType) {
        try {
            TmBizAudit tmBizAudit = new TmBizAudit();
            tmBizAudit.setOperatorId(OrganizationContextHolder.getUserNo());
            tmBizAudit.setCreateDate(new Date());
            tmBizAudit.setOrdType(OrdType);
            tmBizAudit.setJpaVersion(0);
            try {
                if (StringUtils.isEmpty(OrdType)) {
                    throw new ProcessException("保存审计历史信息异常OrdType为空值");
                } else {
                    logger.info("开始保存 -->>>>-- 审计历史");
                    tmBizAuditService.saveAuditHistoryService(tmBizAudit);
                    logger.info("结束保存 -->>>>-- 审计历史");
                }
            } catch (ProcessException e) {
                logger.error("记录审计历史失败..", e);
            }
        } catch (ProcessException e) {
            logger.error("保存历史信息异常", e);
        }
    }

    /**
     * TODO查找历史信息
     * 申请件操作历史查询
     * @param appNo 申请件编号
     * @return
     */
    public List<TmBizAudit> findAuditHistory(String appNo) {
        if (StringUtils.isEmpty(appNo)) {
            return null;
        }
        List<TmBizAudit> auditHistoryList = null;
        if (StringUtils.isNotEmpty(appNo)) {
            TmBizAudit tmBizAudit = new TmBizAudit();
            tmBizAudit.setAppNo(appNo);
            auditHistoryList = tmBizAuditService.findAuditHistoryService(tmBizAudit);
        } else {
            throw new ProcessException("查询历史信息异常");
        }
        return auditHistoryList;
    }
    /**
     * 条件查询
     * 申请件操作记录
     * @param page 分页类
     * @return 分页类
     */
    public Page<TmBizAudit> findAuditHistoryNew(Page<TmBizAudit> page) {

        if (page.getQuery() != null && page.getQuery().size() > 0) {
            // 给开始日期和截至日期附加时间部分
            if (StringUtils.isNotEmpty((String) page.getQuery().get("startDate"))) {
                try {
                    String startStr = StringUtils.valueOf(page.getQuery().get("startDate"));
                    Date date1 = DateUtils.stringToDate(startStr, DateUtils.FULL_YMD_LINE);
                    page.getQuery().put("startDate",DateUtils.getDateForSerch(date1));
                } catch (ParseException e) {
                    logger.error("案件转分配查询时间格式转换发生异常"+ e.getMessage());
                }
            }
            if (StringUtils.isNotEmpty((String) page.getQuery().get("endDate"))) {
                try {
                    String endStr = StringUtils.valueOf(page.getQuery().get("endDate"));
                    Date date1 = DateUtils.stringToDate(endStr, DateUtils.FULL_YMD_LINE);
                    page.getQuery().put("endDate", DateUtils.getDateForSerch(date1));
                } catch (ParseException e) {
                    logger.error("案件转分配时间格式转换发生异常"+ e.getMessage());
                }
            }
        }
        try {
            page = tmBizAuditService.findAuditHistoryPageService(page);
        } catch (ProcessException e) {
            logger.error("审计历史条件查询失败!!!", e);
        }
        return page;
    }

}
