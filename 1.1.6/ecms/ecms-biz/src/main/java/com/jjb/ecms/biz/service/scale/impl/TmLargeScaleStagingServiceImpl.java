package com.jjb.ecms.biz.service.scale.impl;

import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.facility.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjb.ecms.biz.dao.scale.TmLargeScaleStagingDao;
import com.jjb.ecms.biz.service.scale.TmLargeScaleStagingService;
import com.jjb.ecms.infrastructure.TmLargeScaleStaging;
import com.jjb.unicorn.facility.exception.ProcessException;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @ClassName TmLargeScaleStagingServiceImpl
 * Company jydata-tech
 * @Description TODO
 * Author wuxiaole
 * Date 2019-8-13 下午 8:18
 * Version 1.0
 */
@Service
public class TmLargeScaleStagingServiceImpl implements TmLargeScaleStagingService {
    private static Logger logger = LoggerFactory.getLogger(TmLargeScaleStagingServiceImpl.class);

    @Autowired
    private TmLargeScaleStagingDao tmLargeScaleStagingDao;

    /**
     * 大额准入客户数据页面查询
     * @param page
     * @return
     */
    @Override
    public Page<TmLargeScaleStaging> findLargeAppPageService(Page<TmLargeScaleStaging> page) {

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
            page = tmLargeScaleStagingDao.findLargeAppPageDao(page);
        } catch (ProcessException e) {
            logger.error("大额审批页面查询失败!!!", e);
        }
        return page;
    }


    /**
     * 保存大额准入客户数据
     * @param tmLargeScaleStaging
     * @return
     */
    @Override
    public Integer saveTmLargeScaleStagingService(TmLargeScaleStaging tmLargeScaleStaging) {
        if (tmLargeScaleStaging != null) {
            return tmLargeScaleStagingDao.saveTmLargeScaleStagingDao(tmLargeScaleStaging);
        } else {
            throw new ProcessException("保存大额准入客户数据失败! tmLargeScaleStaging 为空["+tmLargeScaleStaging+"]");
        }
    }

    /**
     * 修改大额分期客户数据
     * @param tmLargeScaleStaging
     * @return
     */
    @Override
    public void updateTmLargeScaleStagingService(TmLargeScaleStaging tmLargeScaleStaging) {
        if (tmLargeScaleStaging != null) {
             tmLargeScaleStagingDao.updateTmLargeScaleStagingDao(tmLargeScaleStaging);
        } else {
            throw new ProcessException("修改大额分期客户数据失败! tmLargeScaleStaging 为空["+tmLargeScaleStaging+"]");
        }
    }

    /**
     * 查询大额分期客户数据
     * @param tmLargeScaleStaging
     * @return
     */
    @Override
    public List<TmLargeScaleStaging> selectTmLargeScaleStagingService(TmLargeScaleStaging tmLargeScaleStaging) {
        if (tmLargeScaleStaging != null) {
            return tmLargeScaleStagingDao.queryForList(tmLargeScaleStaging);
        } else {
            throw new ProcessException("查询大额分期客户数据失败! tmLargeScaleStaging 为空["+tmLargeScaleStaging+"]");
        }
    }

    /**
     * 删除大额分期客户数据
     * @param tmLargeScaleStaging
     * @return
     */
    @Override
    public Integer delectTmLargeScaleStagingService(TmLargeScaleStaging tmLargeScaleStaging) {
        if (tmLargeScaleStaging != null) {
            return tmLargeScaleStagingDao.delectTmLargeScaleStagingDao(tmLargeScaleStaging);
        } else {
            throw new ProcessException("删除大额分期客户数据失败! tmLargeScaleStaging 为空["+tmLargeScaleStaging+"]");
        }
    }
}
