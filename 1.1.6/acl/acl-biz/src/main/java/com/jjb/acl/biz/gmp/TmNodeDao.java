package com.jjb.acl.biz.gmp;

import com.jjb.acl.infrastructure.TmNode;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * Created by hp on 2018/11/19.
 */
public interface TmNodeDao extends BaseDao<TmNode> {
    public TmNode findOne(String nodeCode);
}