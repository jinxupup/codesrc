package com.jjb.ecms.biz.dao.apply.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.apply.TmAppMemoDao;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.infrastructure.TmAppMemo;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 申请录入信息申请备注备忘表
 * @Description: TODO
 * @author JYData-R&D-HN
 * @date 2016年10月18日 下午4:26:27
 * @version V1.0
 */
@Repository("tmAppMemoDao")
public class TmAppMemoDaoImpl extends AbstractBaseDao<TmAppMemo> implements TmAppMemoDao {

	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 根据申请件编号appNo获取申请备注备忘表的信息
	 * @param appNo
	 * @return
	 */
	@Override
	public List<TmAppMemo> getTmAppMemoByAppNo(TmAppMemo appMemo) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("_SORT_NAME", "id");
		queryMap.put("_SORT_ORDER", "asc");
		List<TmAppMemo> list = queryForList(appMemo, queryMap);
		return list;
	}

	/**
	 * 保存申请备注备忘表信息
	 * @param tmAppMain
	 */
	@Override
	public void saveTmAppMemo(List<TmAppMemo> appMemos) {
		if(CollectionUtils.isNotEmpty(appMemos)){
			for (int i = 0; i < appMemos.size(); i++) {
				TmAppMemo appMemo = appMemos.get(i);
				if(appMemo.getCreateDate()==null){
					appMemo.setCreateDate(new Date());
				}
				if(StringUtils.isEmpty(appMemo.getCreateUser())){
					appMemo.setCreateUser(OrganizationContextHolder.getUserNo());
				}
				save(appMemo);
			}
		}
	}
	/**
	 * 保存申请备注备忘表信息
	 * @param tmAppMain
	 */
	@Override
	public void saveTmAppMemo(TmAppMemo appMemo){
		
		if(appMemo==null){
			return;
		}
		//MemoInfo 备注、备忘文本信息内容
		//MemoType 备注备忘类型,(AppCommonConstant 备注：APP_REMARK ,备忘：APP_MEMO)
		//TaskKey 任务名称,取值详见：EcmsAuthority
		if(StringUtils.isNotEmpty(appMemo.getAppNo()) //进件编号
				&& StringUtils.isNotEmpty(appMemo.getMemoInfo())
				&& StringUtils.isNotEmpty(appMemo.getMemoType())){
			TmAppMemo paramMode = new TmAppMemo();
			paramMode.setAppNo(appMemo.getAppNo());
			paramMode.setMemoType(appMemo.getMemoType());
//			paramMode.setTaskKey(appMemo.getTaskKey());
			List<TmAppMemo> list1 = getTmAppMemoByAppNo(paramMode);
			if(CollectionUtils.isNotEmpty(list1)){
				for (int i = 0; i < list1.size(); i++) {
					TmAppMemo dbMemo = list1.get(i);
					if(dbMemo.getMemoInfo().equals(appMemo.getMemoInfo())){
						logger.debug("无备注信息更新"+ LogPrintUtils.printAppNoLog(appMemo.getAppNo(), null,null));
						return;
					}
					appMemo.setMemoVersion(dbMemo.getMemoVersion()+1);
				}
			}else{
				appMemo.setMemoVersion(1);
			}
			if(appMemo.getCreateDate()==null){
				appMemo.setCreateDate(new Date());
			}
			if(StringUtils.isNotEmpty(appMemo.getCreateUser())){
				appMemo.setCreateUser(OrganizationContextHolder.getUserNo());
			}
			save(appMemo);
		}else{
			logger.warn("保存备注失败!关键字段值为空"+LogPrintUtils.printAppNoLog(appMemo.getAppNo(), null,null)
					+"MemoType:"+appMemo.getMemoType()
					+"TaskKey:"+appMemo.getTaskKey()
					+"MemoInfo:"+appMemo.getMemoInfo());
		}
	}

	/**
	 * 删除备注信息
	 */
	@Override
	public void deleteTmAppMemo(TmAppMemo entity) {
		if(entity!=null && StringUtils.isNotEmpty(entity.getAppNo())){
			List<TmAppMemo> list = queryForList(entity);
			if(CollectionUtils.sizeGtZero(list)){
				for (TmAppMemo dbEntity : list) {
					deleteByKey(dbEntity);
				}
			}
		}
	}
	
}

