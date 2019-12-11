package com.jjb.acl.biz.gmp.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jjb.acl.biz.gmp.TmSysStatusDao;
import com.jjb.acl.infrastructure.TmSysStatus;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * Created by smh on 2018/11/19.
 */
@Repository("tmSysStatusDao")
public class TmSysStatusDaoImpl extends AbstractBaseDao<TmSysStatus> implements TmSysStatusDao{
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public TmSysStatus selectById(Integer ID) {
        	if(StringUtils.isEmpty(String.valueOf(ID))){
                logger.info("ID不能为空");
                throw new ProcessException("ID不能为空");
        }
        TmSysStatus status = new TmSysStatus();
        status.setId(ID);
        return queryByKey(status);
    }

    @Override
    public int saveTmSysStatus(TmSysStatus tmSysStatus) {
        return  save(tmSysStatus);
    }
}
