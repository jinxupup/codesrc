package com.jjb.acl.biz.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jjb.acl.biz.dao.TmAclDictDao;
import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.kits.StrKit;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;

@Repository("tmAclDictDao")
public class TmAclDictDaoImpl extends AbstractBaseDao<TmAclDict> implements TmAclDictDao {

	public static final String  selectAll = "acl.biz.TmAclDict.selectAll";
	public static final String  selectGroupType = "acl.biz.TmAclDict.selectGroupType";
	public static final String  selectOrderDictByValueAndSort = "acl.biz.TmAclDict.selectOrderDictByValueAndSort";
	@Override
	public Page<TmAclDict> queryPage(Page<TmAclDict> page){


		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		page.getQuery().put("org", OrganizationContextHolder.getOrg());
		if(!page.getQuery().containsKey("_SORT_NAME")) {
			page.getQuery().put("_SORT_NAME", "type,sort,code");
			page.getQuery().put("_SORT_ORDER", "asc");
		}
//		if(StringUtils.isEmpty(page.getSortName()) || StringUtils.equals(page.getSortName(), "1")) {
//			page.setSortName("sort,code");
//		}
		Page<TmAclDict> p = queryForPageList(selectAll, page.getQuery(), page);
		return p;
	}
	
	@Override
	public TmAclDict getDictByType(String type,String code){

		TmAclDict tmAclDict = new TmAclDict();
		tmAclDict.setType(type);
		tmAclDict.setCode(code);
		Map<String,Object> map = new HashMap<String,Object>();
//		if(tmAclDict.getType()!=null && !tmAclDict.getType().equals("")) {
//			map.put("type", tmAclDict.getType());
//		}
//		if(tmAclDict.getCode()!=null && !tmAclDict.getCode().equals("")) {
//			map.put("code", tmAclDict.getCode());
//		}
		map.put("_SORT_NAME", "sort,code");
		map.put("_SORT_ORDER", "asc");

		List<TmAclDict> list = queryForList(tmAclDict, map);
		if(StrKit.notBlankList(list)){
			return list.get(0);
		}

		return null;
	}
	
	@Override
	public List<TmAclDict> selectGroupType(){
		List<TmAclDict> list =  queryForList(selectGroupType, new HashMap<String,Object>());
		return list;
	}

	@Override
	public List<TmAclDict> getOrderDictByValueAndSort(TmAclDict tmAclDict) {
		List<TmAclDict> list =  queryForList(selectOrderDictByValueAndSort, tmAclDict);
		return  list;
	}
}
