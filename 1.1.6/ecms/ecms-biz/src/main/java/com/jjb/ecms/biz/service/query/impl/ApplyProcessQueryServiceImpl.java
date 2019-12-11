/**
 * 
 */
package com.jjb.ecms.biz.service.query.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.query.ApplyLendingQueryDao;
import com.jjb.ecms.biz.dao.query.ApplyProcessQueryDao;
import com.jjb.ecms.biz.service.query.ApplyProcessQueryService;
import com.jjb.ecms.biz.service.query.ApplyProcessUtils;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.facility.dto.ApplyLendingDto;
import com.jjb.ecms.facility.dto.ApplyProcessQueryDto;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: 申请进度查询
 * @author JYData-R&D-BigK.K
 * @date 2016年9月18日 上午10:04:46
 * @version V1.0  
 */
@Service("applyProcessQueryService")
public class ApplyProcessQueryServiceImpl implements ApplyProcessQueryService {

	@Autowired
	private ApplyProcessQueryDao applyProcessQueryDao;
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private CacheContext  cacheContext;
	@Autowired
	private ApplyLendingQueryDao applyLendingQueryDao;
	@Autowired
	private ApplyProcessUtils applyProcessUtils;
	/* 申请进度查询
	 */
	@Override
	@Transactional
	public Page<ApplyProcessQueryDto> applyProcessList(
			Page<ApplyProcessQueryDto> page) {
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		if(page.getQuery()!=null&&page.getQuery().size()>0){
			//给开始日期和截至日期附加时间部分
			Object dateObj1= page.getQuery().get("beginDate");
			Date beginDate = null;
			if(StringUtils.isNotEmpty(dateObj1) && dateObj1 instanceof String) {
				String str = StringUtils.valueOf(dateObj1);
				try {
					if(str.indexOf('-') !=-1) {
						beginDate = DateUtils.getDateStart(DateUtils.stringToDate(str, DateUtils.DAY_YMD_LINE));
					}else{
						beginDate = DateUtils.getDateStart(DateUtils.stringToDate(str, DateUtils.DAY_YMD));
					}
				} catch (ParseException e) {
					logger.error("案件转分配查询时间格式转换发生异常", e);
				}
			}else if(StringUtils.isNotEmpty(dateObj1) && dateObj1 instanceof Date) {
				beginDate = DateUtils.getDateStart((Date)dateObj1);
			}
			if(beginDate!=null) {
				page.getQuery().put("beginDate", beginDate);
			}
			Object dateObj2= page.getQuery().get("endDate");
			Date endDate = null;
			if(StringUtils.isNotEmpty(dateObj2) && dateObj2 instanceof String) {
				String str = StringUtils.valueOf(dateObj2);
				try {
					if(str.indexOf('-') !=-1) {
						endDate = DateUtils.getDateEnd(DateUtils.stringToDate(str, DateUtils.DAY_YMD_LINE));
					}else{
						endDate = DateUtils.getDateEnd(DateUtils.stringToDate(str, DateUtils.DAY_YMD));
					}
				} catch (ParseException e) {
					logger.error("案件转分配查询时间格式转换发生异常", e);
				}
			}else if(StringUtils.isNotEmpty(dateObj2) && dateObj2 instanceof Date) {
				endDate = DateUtils.getDateEnd((Date)dateObj2);
			}
			if(endDate!=null) {
				page.getQuery().put("endDate", endDate);
			}
		}
		//如果没选择了受理网点
		if(page.getQuery().get("owningBranch") != null && StringUtils.isNotBlank(page.getQuery().get("owningBranch").toString())){
			String owningBranch = page.getQuery().get("owningBranch").toString();//获取选择的受理网点
			applyProcessUtils.setBranchToPage(page, owningBranch);
		}else{
			String owningBranch =OrganizationContextHolder.getBranchCode();
			if(StringUtils.isNotEmpty(owningBranch)){
				applyProcessUtils.setBranchToPage(page, owningBranch);
			}
		}
		page = applyProcessQueryDao.applyProcessList(page);
		if(page != null){
			List<ApplyProcessQueryDto> applyProcessQueryDtos = page.getRows();
			if(CollectionUtils.sizeGtZero(applyProcessQueryDtos)){
				Map<Object, Object> backMarkMap = cacheContext.getAclDictByType(AppConstant.BACK_MARK);
				for (ApplyProcessQueryDto applyProcessQueryDto : applyProcessQueryDtos) {
					StringBuffer sb = new StringBuffer();
					if(StringUtils.concat(applyProcessQueryDto.getAppProperty(),"K")) {
						sb = sb.append("快速 |");
					}
					if(StringUtils.concat(applyProcessQueryDto.getAppProperty(),"V")) {
						sb = sb.append("VIP|");
					}
					if(StringUtils.concat(applyProcessQueryDto.getAppProperty(),"C")) {
						sb = sb.append("征信不良|");
					}
					if(StringUtils.equals(applyProcessQueryDto.getIsRetrialApp(), "Y")){
						sb = sb.append("重审件 |");
					}
					if(StringUtils.equals(applyProcessQueryDto.getIsHaveRetrial(), "Y")){
						sb = sb.append("已重审 |");
					}
					String backMark = applyProcessQueryDto.getIsReturned();
					if (StringUtils.isNotEmpty(backMark)) {
						String[] splitBackMark = backMark.split("\\|");
						sb = sb.append(backMarkMap.get(splitBackMark[splitBackMark.length - 1]) + "|");
					}
					applyProcessQueryDto.setAppProperty(sb.toString());
				}
			}
		}
		return page;
	}
	
	
	@Override
	@Transactional
	public List<ApplyProcessQueryDto> applyProcessList(Map<String, Object> param) {
		return applyProcessQueryDao.applyProcessList(param);
	}
	@Override
	@Transactional
	public Page<ApplyLendingDto> appPlyLendingQuery(
			Page<ApplyLendingDto> page) {
		// TODO Auto-generated method stub
		return applyLendingQueryDao.applyLendingQuery(page);
	}
	/**
	 * 根据状态类型查询申请件
	 */
	@Override
	public List<ApplyProcessQueryDto> getApplyByRtfStateType(Map<String, Object> param) {
		return applyProcessQueryDao.getApplyByRtfStateType(param);
	}
}
