package com.jjb.ecms.biz.dao.apply;

import java.util.List;

import com.jjb.ecms.infrastructure.TmAppFlag;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * @ClassName TmAppFlagDao
 * Company jydata-tech
 * Author wxl
 * Date 2019/10/15 15:32
 * Version 1.0
 */
public interface TmAppFlagDao extends BaseDao<TmAppFlag> {

    /**
     * 保存申请件标签
     * @param tmAppFlag
     */
    public void saveTmAppFlag(TmAppFlag tmAppFlag);

    /**
     * 查找申请件标签
     * @param appNo 申请件编号
     * @return
     */
    public List<TmAppFlag> getTmAppFlagListByAppNo(String appNo);


    /**
     * 删除申请件标签
     * @param tmAppFlag
     * @return
     */
    public void deleteTmAppFlag(TmAppFlag tmAppFlag);

    /**
     * 更新申请件标签
     * @param TmAppFlag
     * @return
     */
    public void updateTmAppFlag(TmAppFlag tmAppFlag);

}
