package com.jjb.dmp.biz.service;

import com.jjb.dmp.engine.bean.RuleQueryBean;
import com.jjb.unicorn.facility.model.Page;

public interface RuleQueryService {

	Page<RuleQueryBean> query(Page<RuleQueryBean> page);

}
