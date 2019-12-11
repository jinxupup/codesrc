package com.jjb.ecms.biz.service.param;

import com.jjb.ecms.infrastructure.TmMessageTemplate;
import com.jjb.unicorn.facility.model.Page;

/**
  *@ClassName SmsTemplateService
  *@Description TODO
  *@Author lixing
  *Date 2018/10/13 14:47
  *Version 1.0
  */
public interface SmsTemplateService {


    public Page<TmMessageTemplate> getPage(Page<TmMessageTemplate> page);


    public void saveTmMessageTemplate(TmMessageTemplate tmMessageTemplate);

    public TmMessageTemplate queryTmMessageTemplate(TmMessageTemplate tmMessageTemplate);

    public TmMessageTemplate queryByCode(TmMessageTemplate tmMessageTemplate);

    public void updateTmMessageTemplate(TmMessageTemplate tmMessageTemplate);

    public void deleteTmMessageTemplate(int id);
}
