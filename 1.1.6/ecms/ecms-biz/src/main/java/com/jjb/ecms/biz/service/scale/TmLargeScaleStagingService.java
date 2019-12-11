package com.jjb.ecms.biz.service.scale;

import com.jjb.ecms.infrastructure.TmLargeScaleStaging;
import com.jjb.unicorn.facility.model.Page;

import java.util.List;

/**
  *@ClassName TmLargeScaleStagingService
  *Company jydata-tech
  *@Description TODO
  *Author wuxiaole
  *Date 2019-8-13 下午 8:17
  *Version 1.0
  */
public interface TmLargeScaleStagingService {


    /**
     * 大额准入客户数据页面查询
     * @param Page<TmLargeScaleStaging>
     * @return page
     */
    public Page<TmLargeScaleStaging> findLargeAppPageService(Page<TmLargeScaleStaging> page);


    /**
     * 保存大额准入客户数据
     * @param tmLargeScaleStaging
     * @return
     */
    public Integer saveTmLargeScaleStagingService(TmLargeScaleStaging tmLargeScaleStaging);


    /**
     * 修改大额分期客户数据
     * @param tmLargeScaleStaging
     * @return
     */
    public void updateTmLargeScaleStagingService(TmLargeScaleStaging tmLargeScaleStaging);


    /**
     * 查询大额分期客户数据
     * @param tmLargeScaleStaging
     * @return
     */
    public List<TmLargeScaleStaging> selectTmLargeScaleStagingService(TmLargeScaleStaging tmLargeScaleStaging);


    /**
     * 删除大额分期客户数据
     * @param tmLargeScaleStaging
     * @return
     */
    public Integer delectTmLargeScaleStagingService(TmLargeScaleStaging tmLargeScaleStaging);
}
