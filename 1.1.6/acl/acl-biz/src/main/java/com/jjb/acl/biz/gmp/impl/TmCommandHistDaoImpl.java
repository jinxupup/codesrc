package com.jjb.acl.biz.gmp.impl;

import org.springframework.stereotype.Repository;

import com.jjb.acl.biz.gmp.TmCommandHistDao;
import com.jjb.acl.infrastructure.TmCommandHist;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;

/**
 * Created by smh on 2018/11/20.
 */
@Repository("tmCommandHistDao")
public class TmCommandHistDaoImpl extends AbstractBaseDao<TmCommandHist> implements TmCommandHistDao{


    @Override
    public int saveTmCommandHist(TmCommandHist tmCommandHist) {
        return save(tmCommandHist);
    }
}
