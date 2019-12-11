package com.jjb.acl.biz.gmp.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jjb.acl.biz.gmp.TmProcessDao;
import com.jjb.acl.infrastructure.TmProcess;
import com.jjb.acl.infrastructure.dto.TmProInstance;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;

/**
 * @ClassName TmProcessDaoImpl
 * Company jydata-tech
 * @Description TODO
 * Author zhangwenlu
 * Date 2018/11/19 16:10
 * Version 1.0
 */
@Repository("tmProcessDao")
public class TmProcessDaoImpl extends AbstractBaseDao<TmProcess> implements TmProcessDao {
    private static final String TOI = "com.jjb.acl.gmp.biz.TmOrgInstanceMapper";
    @Override
    public int saveTmProcess(TmProcess tmProcess) {
        return save(tmProcess);
    }

    @Override
    public TmProcess findOne(int processId) {
        TmProcess tmProcess = new TmProcess();
        tmProcess.setProcessId(processId);
        return queryByKey(tmProcess);
    }

    @Override
    public List<TmProcess> findAll() {
        TmProcess tmProcess = new TmProcess();
        return queryForList(tmProcess);
    }

    @Override
    public List<TmProInstance> selectProEqInstance(String[] strings) {
        String sqlId = TOI + "selectProEqInstance";
        List<TmProInstance> tmProInstance = getSqlSession().selectList(sqlId);
        return tmProInstance;
    }


}
