package com.jjb.ecms.biz.dao.param.impl;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.param.TmMessageTemDao;
import com.jjb.ecms.infrastructure.TmMessageTemplate;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;

/**
  *@ClassName TmMessageTemDaoImpl
  *@Description TODO
  *@Author lixing
  *Date 2018/10/13 15:07
  *Version 1.0
  */
@Repository("tmMessageTemDao")
public class TmMessageTemDaoImpl extends AbstractBaseDao<TmMessageTemplate> implements TmMessageTemDao {

    public static final String  selectAll = "com.jjb.ecms.infrastructure.mapping.TmMessageTemplateMapper.selectAll";

    @Override
    public Page<TmMessageTemplate> getPage(Page<TmMessageTemplate> page) {
        if(null == page.getQuery()){
            page.setQuery(new Query());
        }
        page = queryForPageList(selectAll, page.getQuery(), page);
        return page;
    }

    @Override
    public void saveTmMessageTemplate(TmMessageTemplate tmMessageTemplate) {
        save(tmMessageTemplate);

    }

    @Override
    public TmMessageTemplate queryByCode(TmMessageTemplate tmMessageTemplate) {
        return queryForOne(tmMessageTemplate);
    }

    @Override
    public void updateTmMessageTemplate(TmMessageTemplate tmMessageTemplate) {
        update(tmMessageTemplate);

    }

    @Override
    public void deleteTmMessageTemplate(int id) {
        TmMessageTemplate tmMessageTemplate = new TmMessageTemplate();
        tmMessageTemplate.setId(id);
        deleteByKey(tmMessageTemplate);

    }
}
