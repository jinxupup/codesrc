package com.jjb.ecms.biz.dao.apply.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.apply.TmAppContactDao;
import com.jjb.ecms.infrastructure.TmAppContact;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

@Repository("tmAppContactDao")
public class TmAppContactDaoImpl extends AbstractBaseDao<TmAppContact> implements TmAppContactDao {

	/**
	 * 根据申请件编号获取所有联系人信息
	 * @param appNo
	 * @return
	 */
	@Override
	public List<TmAppContact> getTmAppContactListByAppNo(String appNo){
		
		TmAppContact entity = new TmAppContact();
		entity.setAppNo(appNo);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("_SORT_NAME", "id");
		queryMap.put("_SORT_ORDER", "asc");
		List<TmAppContact> list = queryForList(entity, queryMap);
		return list;
	}

	@Override
	public void saveTmAppContact(TmAppContact tmAppContact) {
		// TODO Auto-generated method stub
		save(tmAppContact);
	}

	@Override
	public void updateTmAppContact(TmAppContact tmAppContact) {
		// TODO Auto-generated method stub
		if(tmAppContact!=null){
			if(tmAppContact.getUpdateDate()==null){
				tmAppContact.setUpdateDate(new Date());
			}
			if(StringUtils.isEmpty(tmAppContact.getUpdateUser())){
				tmAppContact.setUpdateUser(OrganizationContextHolder.getUserNo());
			}
			
			update(tmAppContact);
		}
		
	}

	@Override
	public void deleteTmAppContact(TmAppContact entity) {
		if(entity!=null && StringUtils.isNotEmpty(entity.getAppNo())){
			List<TmAppContact> list = queryForList(entity);
			if(CollectionUtils.sizeGtZero(list)){
				for (TmAppContact dbEntity : list) {
					deleteByKey(dbEntity);
				}
			}
		}
	}
	
}