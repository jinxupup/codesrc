package com.jjb.acl.biz.gmp;

import java.util.List;

import com.jjb.acl.infrastructure.TmInstance;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * Created by smh on 2018/11/19.
 */
public interface TmInstanceDao extends BaseDao<TmInstance> {
    public TmInstance findBySystemTypeAndInstanceName(String systemType, String instanceName);
    public  TmInstance selectByInstanceId(Integer instanceId);

    public List<TmInstance> selectTmInstance(TmInstance tmInstance);
}
