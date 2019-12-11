package com.jjb.dmp.biz.dao;

import com.jjb.dmp.engine.bean.RuleQueryBean;
import com.jjb.unicorn.base.dao.BaseDao;
import com.jjb.unicorn.facility.model.Page;

public interface RuleQueryBeanDao extends BaseDao<RuleQueryBean>{

	Page<RuleQueryBean> queryPage(Page<RuleQueryBean> page);

}
