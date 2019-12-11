package com.jjb.acl.biz.gmp.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jjb.acl.biz.gmp.TmOrgDao;
import com.jjb.acl.infrastructure.TmOrg;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;

/**
 * Created by smh on 2018/11/19.
 */
@Repository("tmOrgDao")
public class TmOrgDaoImpl extends AbstractBaseDao<TmOrg> implements TmOrgDao{

    public List<TmOrg> selectAll() {
        TmOrg tmOrg = new TmOrg();
        return queryForList(tmOrg);

    }
}
