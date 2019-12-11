package com.jjb.ecms.biz.dao.audit.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jjb.ecms.biz.dao.audit.TmBizAuditDao;
import com.jjb.ecms.infrastructure.TmBizAudit;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;

/**
 * @ClassName TmBizAuditDaoImpl
 * Company jydata-tech
 * @Description TODO
 * Author wxl
 * Date 2019/7/6 14:32
 * Version 1.0
 */
@Service("tmBizAuditDao")
public class TmBizAuditDaoImpl extends AbstractBaseDao<TmBizAudit>  implements TmBizAuditDao {


    public static final String selectAll="com.jjb.ecms.infrastructure.mapping.TmBizAuditMapperDto.selectAll";//appNo查询
    public static final String  selectpage = "com.jjb.ecms.infrastructure.mapping.TmBizAuditMapperDto.selectpage";//条件查询

    /**
     * 保存审计历史
     * @param tmBizAudit 对应类
     * @return
     */
    @Override
    public Integer saveAuditHistoryDao(TmBizAudit tmBizAudit) {
        return save(tmBizAudit);
    }

    /**
     * 按AppNo 查询审计历史
     * @param tmBizAudit 对应类
     * @return 该AppNo 下的所有内容
     */
    @Override
    public List<TmBizAudit> findAuditHistoryDao(TmBizAudit tmBizAudit) {
        return queryForList(selectAll,tmBizAudit);
    }

    /**
     * 分页查询
     * @param page 分页类
     * @return 所有审计历史 ,或按条件搜索到的到审计历史
     */
    @Override
    public Page<TmBizAudit> findAuditHistoryPageDao(Page<TmBizAudit> page) {
        if(null == page.getQuery()){
            page.setQuery(new Query());
        }
        Page<TmBizAudit> p = queryForPageList(selectpage, page.getQuery(), page);
        return p;
    }

    /**
     * 清除审计历史-暂不使用
     * @param tmBizAudit
     * @return
     */
    @Override
    public Integer clearAuditHistoryDao(TmBizAudit tmBizAudit) {
        return null;
    }
}
