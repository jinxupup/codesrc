package com.jjb.ecms.biz.dao.param;

import com.jjb.ecms.infrastructure.TmMessageTemplate;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;
/**
  *@ClassName TmMessageTemDao
  *@Description TODO
  *@Author lixing
  *Date 2018/10/13 15:06
  *Version 1.0
  */
public interface TmMessageTemDao extends BaseDao<TmMessageTemplate> {

    public Page<TmMessageTemplate> getPage(Page<TmMessageTemplate> page);


    public void saveTmMessageTemplate(TmMessageTemplate tmMessageTemplate);

    public TmMessageTemplate queryByCode(TmMessageTemplate tmMessageTemplate);
    public void updateTmMessageTemplate(TmMessageTemplate tmMessageTemplate);

    public void deleteTmMessageTemplate(int id);

}
