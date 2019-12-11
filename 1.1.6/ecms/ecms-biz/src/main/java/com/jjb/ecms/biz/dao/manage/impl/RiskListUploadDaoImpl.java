package com.jjb.ecms.biz.dao.manage.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.manage.RiskListUploadDao;
import com.jjb.ecms.infrastructure.TmBatchUpload;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;

/**
 * @author JYData-R&D-BIG.W.W
 * @version V1.0
 * @Description: 风险名单上传
 * @date 2017年11月13日 下午17:03:28
 */
@Repository("riskListUploadDao")
public class RiskListUploadDaoImpl extends AbstractBaseDao<TmBatchUpload> implements RiskListUploadDao {

    /**
     * 保存上传的风险名单资料
     *
     * @param 
     */
    @Override
    public void saveTmRiskUpload(TmBatchUpload tmRiskUpload) {
        save(tmRiskUpload);
    }

    /**
     * 获取上传记录
     *
     * @param
     */
    @Override
    public Page<TmBatchUpload> getTmRiskUploadPage(Page<TmBatchUpload> page) {
        if (null == page.getQuery()) {
            page.setQuery(new Query());
        }
        return queryForPageList("com.jjb.ecms.biz.BatchUploadMapper.selectAll", page.getQuery(), page);
    }

    /**
     * 根据id删除上传记录
     *
     * @param id
     */
    @Override
    public void deleteTmRiskUpload(int id) {
        TmBatchUpload tmRiskUpload = new TmBatchUpload();
        tmRiskUpload.setId(id);
        deleteByKey(tmRiskUpload);
    }

    /**
     * 根据id查询上传的申请资料
     *
     * @param id
     */
    @Override
    public TmBatchUpload getTmRiskUploadByKey(int id) {
        TmBatchUpload tmBatchUpload = new TmBatchUpload();
        tmBatchUpload.setId(id);
        return queryByKey(tmBatchUpload);
    }

    /**
     * 根据文件名查询上传文件
     *
     * @return
     */
    @Override
    public List<TmBatchUpload> getTmRiskUploadByName(String fileName) {
        TmBatchUpload tmBatchUpload = new TmBatchUpload();
        tmBatchUpload.setFileName(fileName);
        return queryForList(tmBatchUpload);
    }

}
