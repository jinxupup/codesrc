package com.jjb.cmp.biz.dao;

import java.util.List;

import com.jjb.cmp.infrastructure.TmCmpContent;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * @ClassName TmCmpContentDao
 * @Description TODO
 * @Author smh
 * Date 2018/12/27 17:57
 * Version 1.0
 */
public interface TmCmpContentDao extends BaseDao<TmCmpContent> {
    /**
     * batchNo 通过batchNo获取有效的影像信息
     *
     * @param batchNo
     * @return
     */
    List<TmCmpContent> getTmCmpContentByBatchNo(String batchNo);

    /**
     * 通过 sdasda 获取影像信息
     *
     * @param tmCmpContent
     * @return
     */
    TmCmpContent getTmCmpContent(TmCmpContent tmCmpContent);

    /**
     * 更新影像信息
     *
     * @param tmCmpContent
     * @return
     */
    void updateTmCmpContent(TmCmpContent tmCmpContent);

    /**
     * 保存上传的信息
     *
     * @param tmCmpContent
     */
    void saveTmCmpContent(TmCmpContent tmCmpContent);



    /**
     * 获取全部的信息
     *
     * @param tmCmpContent
     * @return
     */
    List<TmCmpContent> getTmCmpContents(TmCmpContent tmCmpContent);

    /**
     *wxl
     * 删除fastdfs地址对应的一行记录
     * 删除TM_CMP_CONTENT表里的fastdfs地址;cont abs path
     */
    int deleteByKeyy(TmCmpContent tmCmpContent);
    
    /**
     * 通过id查询
     * @param id
     * @return
     */
    TmCmpContent selectByPrimaryKey(Integer id);

}
