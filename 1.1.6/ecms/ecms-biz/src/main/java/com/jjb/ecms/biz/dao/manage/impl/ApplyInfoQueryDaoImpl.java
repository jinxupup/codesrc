package com.jjb.ecms.biz.dao.manage.impl;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.manage.ApplyInfoQueryDao;
import com.jjb.ecms.facility.dto.ApplyInfoQueryDto;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;

/**
 * @description 申请件信息查询
 * @author J.J
 * @date 2017年10月30日上午10:16:16
 */
@Repository("applyInfoQueryDao")
public class ApplyInfoQueryDaoImpl extends AbstractBaseDao<ApplyInfoQueryDto> implements ApplyInfoQueryDao {
	
	public static final String  selectMain = "com.jjb.ecms.biz.ApplyInfoQuery.selectMain";//查询独立主卡或主附同申申请进度

	/**
	 * 申请件信息查询
	 */
	@Override
	public Page<ApplyInfoQueryDto> applyInfoList(Page<ApplyInfoQueryDto> page) {
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		Page<ApplyInfoQueryDto> mainPage= queryForPageList(selectMain, page.getQuery(), page);
		return mainPage;
	}
}
