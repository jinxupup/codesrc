package com.jjb.acl.biz.gmp;

import com.jjb.acl.infrastructure.TmCommandHist;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * Created by smh on 2018/11/20.
 */
public interface TmCommandHistDao extends BaseDao<TmCommandHist>{

    public  int saveTmCommandHist(TmCommandHist tmCommandHist);
}
