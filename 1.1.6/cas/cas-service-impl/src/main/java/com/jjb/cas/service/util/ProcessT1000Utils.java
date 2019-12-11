package com.jjb.cas.service.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jjb.ecms.facility.dto.ApplyProcessQueryDto;
import com.jjb.ecms.service.dto.T1000.T1000Req;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.StringUtils;

public class ProcessT1000Utils {

	private static Logger logger = LoggerFactory.getLogger(ProcessT1000Utils.class);
	/**
	 * 设置查询条件
	 * @param req
	 * @param start
	 * @return
	 */
	public static Page<ApplyProcessQueryDto> putReqFieldIntoPageIfNotNull(T1000Req req, long start) {
		
		Page<ApplyProcessQueryDto> page = new Page<ApplyProcessQueryDto>();
		Query query = page.getQuery();
		
		String name = req.getName();
		String idType = req.getIdType();
		String idNo = req.getIdNo();
		String cellPhone = req.getCellphone();
		String cardNo = req.getCardNo();
		Date beginDate = req.getStartDate();
		Date endDate = req.getEndDate();
		String rtfState1 = req.getRtfState();
		String stateType = req.getStateType();
		String appNo = req.getAppNo();
		String imageNum = req.getImageNum();
		String owningBranch = req.getOwningBranch();
		String appSource = req.getAppSource();
		String spreader = req.getSpreader();
		String inputUser = req.getInputUser();
		
		int curPage = 1;
		int rowCnt = 5;
		try {
			curPage = req.getCurPage();
		} catch (Exception e) {
			curPage = 1;
		}
		try {
			rowCnt =req.getRowCnt();
		} catch (Exception e) {
			rowCnt = 5;
		}
		query.put("org", OrganizationContextHolder.getOrg());
		page.setPageNumber(curPage);
		page.setPageSize(rowCnt);
		
		if (StringUtils.isEmpty(name)) {
			logger.debug("客户姓名为空-PID["+start+"]");
		} else {
			query.put("name", name);
		}
		if (StringUtils.isEmpty(idType) || StringUtils.isEmpty(idNo)) {
			logger.debug("证件类型与证件号码为空-PID["+start+"],idNo["+idNo+"],idType["+idType+"]");
		} else {
			if(!StringUtils.isEmpty(idType)){
				query.put("idType", idType);
			}
			if(!StringUtils.isEmpty(idNo)){
				query.put("idNo", idNo);
			}
		}
		if (StringUtils.isEmpty(cellPhone)) {
			logger.debug("客户手机号码为空-PID["+start+"]");
		} else {
			query.put("cellPhone", cellPhone);
		}
		if (StringUtils.isEmpty(cardNo)) {
			logger.debug("卡号为空-PID["+start+"]");
		} else {
			query.put("cardNo", cardNo);
		}
		if (StringUtils.isEmpty(StringUtils.valueOf(beginDate))) {
			logger.debug("开始时间为空-PID["+start+"]");
		} else {
			query.put("beginDate", beginDate);
		}
		if (StringUtils.isEmpty(StringUtils.valueOf(endDate))) {
			logger.debug("截止时间为空-PID["+start+"]");
		} else {
			query.put("endDate", endDate);
		}
		//申请状态为空的情况下才使用状态类型做查询条件
		if (StringUtils.isEmpty(rtfState1)) {
			logger.debug("申请状态为空-PID["+start+"]");
			
			if(StringUtils.isEmpty(stateType)) {
				logger.debug("状态类型为空-PID["+start+"]");
			}else if(StringUtils.equals(stateType, "F")){//如果是查询失败
				String [] rtfInd = new String[]{"M05","A20"};
				query.put("rtfState", rtfInd);
			}else if(StringUtils.equals(stateType, "S") || StringUtils.equals(stateType, "E")){//如果是查询成功申请的
				String [] rtfInd = new String[]{"L05","N05","P05"};
				query.put("rtfState", rtfInd);
				//如果是有效核卡数量
				if(StringUtils.equals(stateType, "E")) {
					query.put("ifNewUser", "Y");
					query.put("ifSwiped", "Y");
				}
			}else if(StringUtils.equals(stateType, "A")){//如果是查询审批中
				String [] rtfInd = new String[]{"L05","N05","P05","M05","A20"};
				query.put("notInState", rtfInd);
			}else if(StringUtils.equals(stateType, "P")){//如果是查询待预审
				String [] rtfInd = new String[]{"B16","F03","B28"};
				query.put("rtfState", rtfInd);
			}
			
		} else {
			logger.debug("申请状态["+rtfState1+"]-PID["+start+"]");
			String [] rtfInd = rtfState1.split("\\|");
			if(rtfInd!=null && rtfInd.length>0){
				query.put("rtfState", rtfInd);
			}
		}
			
		if (StringUtils.isEmpty(appNo)) {
			logger.debug("申请件编号为空-PID["+start+"]");
		} else {
			query.put("appNo", appNo);
		}
		if (StringUtils.isEmpty(imageNum)) {
			logger.debug("影像批次号为空-PID["+start+"]");
		} else {
			query.put("imageNum", imageNum);
		}
		if (StringUtils.isEmpty(owningBranch)) {
			logger.debug("受理网点为空-PID["+start+"]");
		} else {
			query.put("owningBranch", owningBranch);
		}
		if (StringUtils.isEmpty(appSource)) {
			logger.debug("申请渠道为空-PID["+start+"]");
		} else {
			query.put("appSource", appSource);
		}
		if (StringUtils.isEmpty(spreader)) {
			logger.debug("推广人为空-PID["+start+"]");
		} else {
			query.put("spreader", spreader);
			if (StringUtils.isEmpty(appSource)) {
				throw new ProcessException("请输入您要查询的申请渠道");
			}
		}
		if (StringUtils.isEmpty(inputUser)) {
			logger.debug("录入人为空-PID["+start+"]");
		} else {
			query.put("inputUser", inputUser);
		}
		if(query==null || query.size()==0) {
			throw new ProcessException("请输入有效的查询条件");
		}
		page.setQuery(query);
		
		return page;
	}


	/**
	 * 是否属于成功申请的申请
	 * @param rtfState
	 * @return
	 */
	public static boolean isSuccApply(String rtfState) {
		List<String> list = new ArrayList<>();
		list.add("L05");
		list.add("N05");
		list.add("P05");
		if(StringUtils.isNotEmpty(rtfState) && list.contains(rtfState)) {
			return true;
		}
		return false;
	}
	/**
	 * 是否属于失败申请的申请
	 * @param rtfState
	 * @return
	 */
	public static boolean isEffSuccApply(String rtfState,String appSource,String spreaderIsEff) {
		if(StringUtils.equals(appSource, "05") && StringUtils.equals(spreaderIsEff, "Y") ) {
			return true;
		}
		return false;
	}
	/**
	 * 是否属于失败申请的申请
	 * @param rtfState
	 * @return
	 */
	public static boolean isFailApply(String rtfState) {
		List<String> list = new ArrayList<>();
		list.add("M05");
		list.add("A20");
		if(StringUtils.isNotEmpty(rtfState) && list.contains(rtfState)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 是否属于失败申请的申请
	 * @param rtfState
	 * @return
	 */
	public static boolean isExaApproveApply(String rtfState) {

		List<String> list = new ArrayList<>();
		list.add("L05");
		list.add("N05");
		list.add("P05");
		list.add("L05");
		list.add("A20");
		list.add("M05");
		if(StringUtils.isNotEmpty(rtfState) && !list.contains(rtfState)) {
			return true;
		}
		return false;
	}
	/**
	 * 是否属于预审申请的申请
	 * @param rtfState
	 * @return
	 */
	public static boolean isPreCheckApply(String rtfState) {

		List<String> list = new ArrayList<>();
		list.add("B16");
		list.add("F03");
		if(StringUtils.isNotEmpty(rtfState) && list.contains(rtfState)) {
			return true;
		}
		return false;
	}
}
