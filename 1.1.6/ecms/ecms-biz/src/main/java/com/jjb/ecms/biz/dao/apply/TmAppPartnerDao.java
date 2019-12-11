package com.jjb.ecms.biz.dao.apply;

import com.jjb.ecms.infrastructure.TmAppPartner;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * @Description: TODO  合伙人数据表操作
 * @Author: shiminghong
 * @Data: 2019/9/23 15:42
 * @Version 1.0
 */
public interface TmAppPartnerDao extends BaseDao<TmAppPartner> {

    public void  saveTmAppPartner(TmAppPartner tmAppPartner);
}
