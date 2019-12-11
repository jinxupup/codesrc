package com.jjb.acl.biz.gmp.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jjb.acl.biz.gmp.TmInstanceDao;
import com.jjb.acl.infrastructure.TmInstance;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * Created by smh on 2018/11/19.
 */
@Repository("tmInstanceDao")
public class TmInstanceDaoImpl extends AbstractBaseDao<TmInstance> implements TmInstanceDao{
    private static final String TIS = "com.jjb.acl.gmp.biz.TmInstanceNewMapper";
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public TmInstance findBySystemTypeAndInstanceName(String systemType, String instanceName) {
    String sqlId = TIS + ".findBySystemTypeAndInstanceName";


/*        TmInstance tmInstance =new TmInstance();
        tmInstance.setSystemType(systemType);
        tmInstance.setInstanceName(instanceName);
        tmInstance=  queryForOne(tmInstance);*/
        Query query = new Query();
        query.put("systemType",systemType);
        query.put("instanceName",instanceName);
        TmInstance tmInstance = new TmInstance();
        tmInstance = getSqlSession().selectOne(sqlId,query);
        return  tmInstance;
    }

    /**
     * @Author smh
     * @Description TODO
     * @Date 2018/11/21 17:39
     */
    public TmInstance selectByInstanceId(Integer instanceId) {
        if (StringUtils.isEmpty(String.valueOf(instanceId))){
            logger.info("instanceId不能为空");
            throw new ProcessException("instanceId不能为空");
        }
        TmInstance tmInstance = new TmInstance();
        tmInstance.setInstanceId(instanceId);
        return queryByKey(tmInstance);

    }

    @Override
    public List<TmInstance> selectTmInstance(TmInstance tmInstance) {
        return  queryForList(tmInstance);
    }
}
