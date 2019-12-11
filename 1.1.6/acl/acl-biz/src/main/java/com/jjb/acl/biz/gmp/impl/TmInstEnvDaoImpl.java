package com.jjb.acl.biz.gmp.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jjb.acl.biz.gmp.TmInstEnvDao;
import com.jjb.acl.infrastructure.TmInstEnv;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * Created by smh on 2018/11/20.
 */
@Repository("tmInsEnvDao")
public class TmInstEnvDaoImpl extends AbstractBaseDao<TmInstEnv> implements TmInstEnvDao {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public List<TmInstEnv> selectcByInstanceId(Integer instanceId) {
        TmInstEnv tmInstEnv = new TmInstEnv();
        if (StringUtils.isEmpty(String.valueOf(instanceId))) {
                logger.info("instanceId不能为空");
                throw new ProcessException("instanceId不能为空");
        }
        tmInstEnv.setInstanceId(instanceId);
        return queryForList(tmInstEnv);

    }
}
