package com.jjb.acl.biz.gmp.impl;


import org.springframework.stereotype.Repository;

import com.jjb.acl.biz.gmp.TmNodeDao;
import com.jjb.acl.infrastructure.TmNode;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;

/**
 * @ClassName TmNodeDaoImpl
 * Company jydata-tech
 * @Description TODO
 * Author zhangwenlu
 * Date 2018/11/19 17:24
 * Version 1.0
 */
@Repository("tmNodeDao")
public class TmNodeDaoImpl extends AbstractBaseDao<TmNode> implements TmNodeDao {

    @Override
    public TmNode findOne(String nodeCode) {
        if(nodeCode==null){
            return null;
        }
        TmNode tmNode = new TmNode();
        tmNode.setNodeCode(nodeCode);
        return queryByKey(tmNode);
    }
}
