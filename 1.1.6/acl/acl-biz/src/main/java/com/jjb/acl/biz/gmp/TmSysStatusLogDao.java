package com.jjb.acl.biz.gmp;

import com.jjb.acl.infrastructure.TmSysStatusLog;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * Created by smh on 2018/11/19.
 */
public interface TmSysStatusLogDao extends BaseDao<TmSysStatusLog>{
        public int saveTmSysStatusLog(TmSysStatusLog tmSysStatusLog);
}
