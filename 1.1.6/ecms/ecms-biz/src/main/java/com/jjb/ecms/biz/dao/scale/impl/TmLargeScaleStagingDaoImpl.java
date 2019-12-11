package com.jjb.ecms.biz.dao.scale.impl;

import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import org.springframework.stereotype.Service;

import com.jjb.ecms.biz.dao.scale.TmLargeScaleStagingDao;
import com.jjb.ecms.infrastructure.TmLargeScaleStaging;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;

/**
 * @ClassName TmLargeScaleStagingDaoImpl
 * Company jydata-tech
 * @Description
 * Author wuxiaole
 * Date 2019-8-13 下午 7:47
 * Version 1.0
 */
@Service("TmLargeScaleStagingDao")
public class TmLargeScaleStagingDaoImpl extends AbstractBaseDao<TmLargeScaleStaging> implements TmLargeScaleStagingDao {



    public static final String selectAll="com.jjb.ecms.infrastructure.mapping.TmLargeScaleStagingMapper.selectAll";
    public static final String updateNeed="com.jjb.ecms.infrastructure.mapping.TmLargeScaleStagingMapper.updateNotNullByPrimaryKey";
    public static final String deleteByPrimaryKey="com.jjb.ecms.infrastructure.mapping.TmLargeScaleStagingMapper.deleteByPrimaryKey";
    public static final String selectpage="com.jjb.ecms.infrastructure.mapping.TmLargeScaleStagingMapperDto.selectpage";



    /**
     * 大额审批客户数据页面查询
     * @param
     * @return
     */
    @Override
    public Page<TmLargeScaleStaging> findLargeAppPageDao(Page<TmLargeScaleStaging> page) {
        if(null == page.getQuery()){
            page.setQuery(new Query());
        }
        return queryForPageList(selectpage, page.getQuery(), page);
    }



    /**
     * 保存大额准入客户数据
     * @param tmLargeScaleStaging
     * @return
     */
    @Override
    public Integer saveTmLargeScaleStagingDao(TmLargeScaleStaging tmLargeScaleStaging) {
        return save(tmLargeScaleStaging);
    }


    /**
     * 修改大额分期客户数据 where ID= ????
     * @param tmLargeScaleStaging
     * @return
     */
    @Override
    public void updateTmLargeScaleStagingDao(TmLargeScaleStaging tmLargeScaleStaging) {
        update(updateNeed, tmLargeScaleStaging);
    }


    /**
     * 查询大额分期客户数据
     * @param tmLargeScaleStaging
     * @return
     */
    @Override
    public TmLargeScaleStaging selectTmLargeScaleStagingDao(TmLargeScaleStaging tmLargeScaleStaging) {
        return queryForOne(tmLargeScaleStaging);
    }


    /**
     * 删除大额分期客户数据
     * @param tmLargeScaleStaging 只需要ID
     * @return
     */
    @Override
    public Integer delectTmLargeScaleStagingDao(TmLargeScaleStaging tmLargeScaleStaging) {
        return delete(deleteByPrimaryKey,tmLargeScaleStaging);
    }
}
