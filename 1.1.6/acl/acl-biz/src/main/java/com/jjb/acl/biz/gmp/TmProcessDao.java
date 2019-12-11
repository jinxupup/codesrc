package com.jjb.acl.biz.gmp;

import java.util.List;

import com.jjb.acl.infrastructure.TmProcess;
import com.jjb.acl.infrastructure.dto.TmProInstance;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * Created by zhangwenlu on 2018/11/19.
 */
public interface
TmProcessDao extends BaseDao<TmProcess>{
    public int saveTmProcess(TmProcess tmProcess);
    public TmProcess findOne(int processId);
    List<TmProcess> findAll();
    public List<TmProInstance> selectProEqInstance(String[] strings);
}
