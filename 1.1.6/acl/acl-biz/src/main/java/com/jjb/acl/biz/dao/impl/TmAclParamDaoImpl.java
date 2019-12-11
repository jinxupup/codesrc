package com.jjb.acl.biz.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jjb.acl.biz.dao.TmAclParamDao;
import com.jjb.acl.infrastructure.TmAclParam;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.kits.StrKit;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;

@Repository("tmAclParamDao")
public class TmAclParamDaoImpl extends AbstractBaseDao<TmAclParam> implements TmAclParamDao {

	public static final String  selectAll = "acl.biz.TmAclParam.selectAll";
	
	@Override
	public Page<TmAclParam> queryPage(Page<TmAclParam> page){
		
		
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		page.getQuery().put("org", OrganizationContextHolder.getOrg());
		Page<TmAclParam> p = queryForPageList(selectAll, page.getQuery(), page);
		
		return p;
	}
	
	
		public TmAclParam getParamByType(String type,String code){
			
			TmAclParam tmAclParam = new TmAclParam();
			tmAclParam.setType(type);
			tmAclParam.setCode(code);
			
			List<TmAclParam> list = queryForList(tmAclParam);
			if(StrKit.notBlankList(list)){
				return list.get(0);
			}
			
			return null;
		}
}
