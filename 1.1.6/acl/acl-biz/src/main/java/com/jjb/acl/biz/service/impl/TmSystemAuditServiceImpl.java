package com.jjb.acl.biz.service.impl;

import com.jjb.acl.biz.dao.TmSystemAuditDao;
import com.jjb.acl.biz.service.TmSystemAuditService;
import com.jjb.acl.infrastructure.TmSystemAudit;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.facility.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description 修改记录
 * @author J.J
 * @date 2017年7月14日下午3:56:40
 */
@Service("tmSystemAuditService")
public class TmSystemAuditServiceImpl implements TmSystemAuditService {

	@Autowired
	private TmSystemAuditDao tmSystemAuditDao;
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Page
	 * @param Page ,TmSystemAudit
	 * @return Page
	 */
	@Override
	@Transactional
	public Page<TmSystemAudit> getPage(Page<TmSystemAudit> page, String operatorId) {
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		if (page.getQuery() != null && page.getQuery().size() > 0) {
			// 给开始日期和截至日期附加时间部分
			if (StringUtils.isNotEmpty((String) page.getQuery().get("beginDate"))) {
				try {
					String startStr = StringUtils.valueOf(page.getQuery().get("beginDate"));
					Date date1 = DateUtils.stringToDate(startStr, DateUtils.FULL_YMD_LINE);
					page.getQuery().put("beginDate",DateUtils.getDateForSerch(date1));
				} catch (ParseException e) {
					logger.error("案件转分配查询时间格式转换发生异常"+ e.getMessage());
				}
			}
			if (StringUtils.isNotEmpty((String) page.getQuery().get("endDate"))) {
				try {
					String endStr = StringUtils.valueOf(page.getQuery().get("endDate"));
					Date date1 = DateUtils.stringToDate(endStr, DateUtils.FULL_YMD_LINE);
					page.getQuery().put("endDate", DateUtils.getDateForSerch(date1));
				} catch (ParseException e) {
					logger.error("案件转分配时间格式转换发生异常"+ e.getMessage());
				}
			}
		}
		
		if(operatorId != null && !"".equals(operatorId)){
			page.getQuery().put("operatorId", operatorId);
		}
		return tmSystemAuditDao.getPage(page);
	}

	/**
	 * 通过id获取TmSystemAudit
	 * @param ID
	 * @return TmSystemAudit
	 */
	@Override
	@Transactional
	public TmSystemAudit getTmSystemAuditById(int id) {
		TmSystemAudit tmSystemAudit = new TmSystemAudit();
		tmSystemAudit.setId(id);
		return tmSystemAuditDao.queryByKey(tmSystemAudit);
	}

	/**
	 * 新增历史操作记录
	 * @param TmSystemAudit
	 */
	@Override
	@Transactional
	public void saveTmSystemAuditField(TmSystemAudit tmSystemAudit) {
		if(tmSystemAudit!=null){
			tmSystemAudit.setId(null);
			tmSystemAudit.setOrg(OrganizationContextHolder.getOrg());
			tmSystemAudit.setUpdateDate(new Date());
			tmSystemAudit.setUpdateUser(OrganizationContextHolder.getUserNo());
			tmSystemAuditDao.save(tmSystemAudit);
		}
	}
}
