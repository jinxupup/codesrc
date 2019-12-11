package com.jjb.acl.biz.gmp.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jjb.acl.biz.gmp.TmInstOrgDao;
import com.jjb.acl.infrastructure.TmInstOrg;
import com.jjb.acl.infrastructure.dto.TmOrgInstance;
import com.jjb.acl.infrastructure.dto.TmOrgInstanceTwo;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * Created by smh on 2018/11/20.
 */
@Repository("tmInstOrgDao")
public class TmInstOrgDaoImpl extends AbstractBaseDao<TmInstOrg>  implements TmInstOrgDao {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final String TOI = "com.jjb.acl.gmp.biz.TmOrgInstanceMapper";
    private static final String TOIT = "com.jjb.acl.gmp.biz.TmOrgInstanceTwoMapper";

    @Override
    public List<TmInstOrg> findByEqInstanceId(Integer InstanceId) {
        if (StringUtils.isEmpty(String.valueOf(InstanceId))){
            logger.info("InstanceId不能为空");
            throw new ProcessException("InstanceId不能为空");
        }
        TmInstOrg tmInstOrg = new TmInstOrg();
        tmInstOrg.setInstanceId(InstanceId);
        return queryForList(tmInstOrg);
    }

    /**
     *
     * @return
     */
    @Override
    public List<TmOrgInstance> selectInstOrgEqInstance() {
        String sqlId = TOI + ".selectInstOrgEqInstance";
        List<TmOrgInstance> tmOrgInstances = getSqlSession().selectList(sqlId);
        return   tmOrgInstances;
    }

    /**
     *
     * @return
     */
    @Override
    public List<TmOrgInstanceTwo> selectInstOrgEqInstanceTwo(String orgId) {
        String sqlId = TOIT + ".selectInstOrgEqInstanceTwo";
        if (StringUtils.isEmpty(String.valueOf(orgId))){
            logger.info("orgId不能为空");
            throw new ProcessException("orgId不能为空");
        }
        TmInstOrg tmInstOrg=new TmInstOrg();
        tmInstOrg.setOrg(orgId);
        List<TmOrgInstanceTwo> tmOrgInstanceTwos = getSqlSession().selectList(sqlId,tmInstOrg);
        return  tmOrgInstanceTwos;
    }

    @Override
    public TmOrgInstance selectInstanceName(String system, String org) {
        String sqlId = TOI + ".selectInstanceName";
        if (StringUtils.isEmpty(system)||(StringUtils.isEmpty(org))){
            logger.info("参数不能为空");
            throw new ProcessException("参数不能为空");
        }
        Query query = new Query();
        query.put("systemType",system);
        query.put("org",org);
        TmOrgInstance tmOrgInstance =getSqlSession().selectOne(sqlId,query);
        return tmOrgInstance;
    }
}
