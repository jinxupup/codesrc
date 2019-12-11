package com.jjb.acl.biz.gmp.impl;

import org.springframework.stereotype.Repository;

import com.jjb.acl.biz.gmp.TmSysStatusLogDao;
import com.jjb.acl.infrastructure.TmSysStatusLog;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;

/**
 * Created by smh on 2018/11/19.
 */
@Repository("tmSysStatusLog")
public class TmSysStatusLogDaoImpl extends AbstractBaseDao<TmSysStatusLog> implements TmSysStatusLogDao {
    @Override
    public int saveTmSysStatusLog(TmSysStatusLog tmSysStatusLog) {
        return  save(tmSysStatusLog);
    }
}
