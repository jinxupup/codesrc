package com.jjb.acl.biz.gmp;

import java.util.List;

import com.jjb.acl.infrastructure.TmOrg;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * Created by smh on 2018/11/19.
 */
public interface TmOrgDao extends BaseDao<TmOrg> {
    public List<TmOrg> selectAll();
}
