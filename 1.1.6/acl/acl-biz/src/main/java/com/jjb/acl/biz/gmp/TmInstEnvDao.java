package com.jjb.acl.biz.gmp;

import java.util.List;

import com.jjb.acl.infrastructure.TmInstEnv;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * Created by smh on 2018/11/20.
 */
public interface TmInstEnvDao extends BaseDao<TmInstEnv> {

    /**
     * 查询TmInstEnv中与TmInstance的istanceId相等的数据
     * @param instanceId
     * @return
     */

    public List<TmInstEnv> selectcByInstanceId(Integer instanceId);
}
