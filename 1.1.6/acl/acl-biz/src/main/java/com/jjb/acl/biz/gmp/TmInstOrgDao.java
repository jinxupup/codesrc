package com.jjb.acl.biz.gmp;

import java.util.List;

import com.jjb.acl.infrastructure.TmInstOrg;
import com.jjb.acl.infrastructure.dto.TmOrgInstance;
import com.jjb.acl.infrastructure.dto.TmOrgInstanceTwo;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * Created by smh on 2018/11/19.
 */
public interface TmInstOrgDao extends BaseDao<TmInstOrg>{
    public List<TmInstOrg> findByEqInstanceId(Integer InstanceId);

   public List<TmOrgInstance> selectInstOrgEqInstance();

   public List<TmOrgInstanceTwo> selectInstOrgEqInstanceTwo(String orgId);

    public TmOrgInstance selectInstanceName(String system,String org);

}
