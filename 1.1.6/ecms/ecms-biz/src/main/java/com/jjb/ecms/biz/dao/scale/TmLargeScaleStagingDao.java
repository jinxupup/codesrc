package com.jjb.ecms.biz.dao.scale;

import com.jjb.ecms.infrastructure.TmLargeScaleStaging;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

/**
 * @ClassName TmLargeScaleStagingService
 * Company jydata-tech
 * @Description
 * Author wuxiaole
 * Date 2019-8-13 下午 7:46
 * Version 1.0
 */
public interface TmLargeScaleStagingDao extends BaseDao<TmLargeScaleStaging> {

    /**
     * 大额审批客户数据页面查询
     * @param
     * @return
     */
    public Page<TmLargeScaleStaging> findLargeAppPageDao(Page<TmLargeScaleStaging> page);

    /**
     * 保存大额准入客户数据
     * @param tmLargeScaleStaging
     * @return
     */
    public Integer saveTmLargeScaleStagingDao(TmLargeScaleStaging tmLargeScaleStaging);


    /**
     * 修改大额分期客户数据
     * @param tmLargeScaleStaging
     * @return
     */
    public void updateTmLargeScaleStagingDao(TmLargeScaleStaging tmLargeScaleStaging);


    /**
     * 查询大额分期客户数据
     * @param tmLargeScaleStaging
     * @return
     */
    public TmLargeScaleStaging selectTmLargeScaleStagingDao(TmLargeScaleStaging tmLargeScaleStaging);


    /**
     * 删除大额分期客户数据
     * @param tmLargeScaleStaging
     * @return
     */
    public Integer delectTmLargeScaleStagingDao(TmLargeScaleStaging tmLargeScaleStaging);
}
