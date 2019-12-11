package com.jjb.ecms.biz.dao.apply.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.apply.TmAppCustInfoDao;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.infrastructure.TmAppCustInfo;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: 附卡申请人信息表
 * @author JYData-R&D-Big T.T
 * @date 2016年8月31日 下午6:43:58
 * @version V1.0
 */
@Repository("tmAppCustInfoDao")
public class TmAppCustInfoDaoImpl extends AbstractBaseDao<TmAppCustInfo> implements TmAppCustInfoDao {
	private Logger logger = LoggerFactory.getLogger(getClass());


	@Override
	public List<TmAppCustInfo> getTmAppCustInfoList(String appNo,String status,String bscSuppInd) {
		TmAppCustInfo entity = new TmAppCustInfo();
		if(StringUtils.isEmpty(appNo)){
			logger.info("申请件编号为空,请刷新！");
			throw new ProcessException("申请件编号为空，请刷新！");
		}
		if(StringUtils.isNotEmpty(status) && StringUtils.equals(bscSuppInd,AppConstant.bscSuppInd_B)){
			entity.setRecordStatus(status);
		}
		if(StringUtils.isNotEmpty(bscSuppInd)){
			entity.setBscSuppInd(bscSuppInd);
		}
		entity.setAppNo(appNo);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("_SORT_NAME", "id");
		queryMap.put("_SORT_ORDER", "asc");
		List<TmAppCustInfo> list = queryForList(entity, queryMap);

		return list;
	}

	@Override
	public void saveTmAppCustInfo(
			TmAppCustInfo tmAppAttachApplierInfo) {
		// TODO Auto-generated method stub
		save(tmAppAttachApplierInfo);
	}

	@Override
	public void updateTmAppCustInfo(
			TmAppCustInfo custInfo) {
		// TODO Auto-generated method stub
		if(custInfo!=null){
			if(StringUtils.isEmpty(custInfo.getOrg())){
				custInfo.setOrg(OrganizationContextHolder.getOrg());
			}
			if(StringUtils.isEmpty(custInfo.getUpdateUser())){
				custInfo.setUpdateUser(OrganizationContextHolder.getUserNo());
			}
			if(custInfo.getUpdateDate()==null){
				custInfo.setUpdateDate(new Date());
			}
			update(custInfo);
		}
	}

	@Override
	public void deleteTmAppCustInfo(TmAppCustInfo entity) {

		if(entity!=null && StringUtils.isNotEmpty(entity.getAppNo())){
			List<TmAppCustInfo> list = queryForList(entity);
			if(CollectionUtils.sizeGtZero(list)){
				for (TmAppCustInfo dbEntity : list) {
					deleteByKey(dbEntity);
				}
			}
		}
	}

	/**
	 * 根据appNo 和 attachNo查询附卡信息
	 */
	public TmAppCustInfo getTmAppCustInfo(String appNo, Integer attachNo,String bscSuppInd) throws ProcessException{

		if(StringUtils.isEmpty(appNo)){
			logger.info("申请件编号为空,请刷新！");
			throw new ProcessException("申请件编号为空，请刷新！");
		}
		TmAppCustInfo custInfo = new TmAppCustInfo();
		custInfo.setAppNo(appNo);
		if(attachNo!=null && StringUtils.equals(bscSuppInd,AppConstant.bscSuppInd_B)){
			custInfo.setAttachNo(attachNo);
		}
		if(StringUtils.isNotEmpty(bscSuppInd)){
			custInfo.setBscSuppInd(bscSuppInd);
		}
		List<TmAppCustInfo> custInfos = queryForList(custInfo);
		TmAppCustInfo info = null;
		if (custInfos != null && custInfos.size() > 0) {
			info = custInfos.get(0);
		}
		return info;
	}

}