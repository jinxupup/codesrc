package com.jjb.ecms.biz.dao.apply.impl;

import com.jjb.ecms.biz.dao.apply.TmAppPartnerDao;
import com.jjb.ecms.infrastructure.TmAppPartner;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @Description: TODO
 * @Author: shiminghong
 * @Data: 2019/9/23 15:45
 * @Version 1.0
 */
@Repository("tmAppPartnerDao")
public class TmAppPartnerDaoImpl extends AbstractBaseDao<TmAppPartner> implements TmAppPartnerDao {
    @Override
    public void saveTmAppPartner(TmAppPartner tmAppPartner) {
        if (tmAppPartner!=null){
            tmAppPartner.setCreateDate(new Date());
            tmAppPartner.setUpdateDate(new Date());
            int i = save(tmAppPartner);
        }
    }
}
