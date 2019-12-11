package com.jjb.ecms.biz.service.param.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.dao.param.TmMessageTemDao;
import com.jjb.ecms.biz.service.param.SmsTemplateService;
import com.jjb.ecms.infrastructure.TmMessageTemplate;
import com.jjb.unicorn.facility.model.Page;

/**
  *@ClassName SmsTemplateServiceImpl
  *@Description TODO
  *@Author lixing
  *Date 2018/10/13 14:57
  *Version 1.0
  */
@Service("smsTemplateService")
public class SmsTemplateServiceImpl implements SmsTemplateService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TmMessageTemDao tmMessageTemDao;

    @Override
	@Transactional
    public Page<TmMessageTemplate> getPage(Page<TmMessageTemplate> page) {
        return tmMessageTemDao.getPage(page);
    }

    @Override
	@Transactional
    public void saveTmMessageTemplate(TmMessageTemplate tmMessageTemplate) {
        tmMessageTemDao.saveTmMessageTemplate(tmMessageTemplate);

    }

    @Override
	@Transactional
    public TmMessageTemplate queryTmMessageTemplate(TmMessageTemplate tmMessageTemplate) {
        return tmMessageTemDao.queryByKey(tmMessageTemplate);
    }

    @Override
	@Transactional
    public TmMessageTemplate queryByCode(TmMessageTemplate tmMessageTemplate) {
        return tmMessageTemDao.queryByCode(tmMessageTemplate);
    }


    @Override
	@Transactional
    public void updateTmMessageTemplate(TmMessageTemplate tmMessageTemplate) {
        tmMessageTemDao.updateTmMessageTemplate(tmMessageTemplate);

    }

    @Override
	@Transactional
    public void deleteTmMessageTemplate(int id) {
        tmMessageTemDao.deleteTmMessageTemplate(id);

    }
}
