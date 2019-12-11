package com.jjb.acl.biz.gmp;

import com.jjb.acl.infrastructure.TmSysStatus;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * Created by smh on 2018/11/19.
 */
public interface TmSysStatusDao extends BaseDao<TmSysStatus>{
    /**
     * 不知道是不是用主键,先写在这
     * @param ID
     * @return
     */
    public TmSysStatus selectById(Integer ID);

    public int saveTmSysStatus(TmSysStatus status);
}
