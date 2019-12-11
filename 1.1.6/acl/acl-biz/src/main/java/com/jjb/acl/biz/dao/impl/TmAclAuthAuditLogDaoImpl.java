package com.jjb.acl.biz.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.jjb.acl.biz.dao.TmAclAuthAuditLogDao;
import com.jjb.acl.infrastructure.TmAclAuthAuditLog;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.DateUtils;

/**
 * 
 * @ClassName TmAclAuthAuditLogDaoImpl
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author H.N
 * @Date 2017年12月2日 下午2:02:46
 * @version 1.0.0
 */
@Repository("tmAclAuthAuditLogDao")
public class TmAclAuthAuditLogDaoImpl extends AbstractBaseDao<TmAclAuthAuditLog> implements TmAclAuthAuditLogDao {
	public static final String  NS = "acl.biz.TmAclAuthAuditLog";
	@Override
	public Page<TmAclAuthAuditLog> getQueryPage(Page<TmAclAuthAuditLog> page, String queryType) {
		if(null==page.getQuery()){
			page.setQuery(new Query());
		}
		String sqlId = NS;
		if("MS".equals(queryType)){
			//登陆者提交的数据
			sqlId = sqlId + ".selectMySubmitList";
		}else if("MC".equals(queryType)){
			//登陆者审核的数据
			sqlId = sqlId + ".selectMyCheckedList";
		}else {
			//登陆者待审核的数据
			sqlId = sqlId + ".selectAll";
		}
		page.getQuery().put("branchCode", OrganizationContextHolder.getBranchCode());
		page.getQuery().put("curUserNo", OrganizationContextHolder.getUserNo());
		//给开始日期和截至日期附加时间部分
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		if(StringUtils.isNotEmpty((String)page.getQuery().get("beginDate"))){
					
			try {
				Date date = sdf.parse((String) page.getQuery().get("beginDate"));
				page.getQuery().put("beginDate",DateUtils.getDateStart(date));
			} catch (ParseException e) {
				
			}				
		}
		if(StringUtils.isNotEmpty((String)page.getQuery().get("endDate"))){
			try {
				Date date = sdf.parse((String) page.getQuery().get("endDate"));
				page.getQuery().put("endDate",DateUtils.getDateEnd(date));
			} catch (ParseException e) {
				
			}				
		}
		Page<TmAclAuthAuditLog> p = queryForPageList(sqlId, page.getQuery(), page);
		return p;
	}

	
	
}
