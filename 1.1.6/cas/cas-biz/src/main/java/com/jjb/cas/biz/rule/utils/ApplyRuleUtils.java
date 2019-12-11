package com.jjb.cas.biz.rule.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.manage.TmRiskListService;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.infrastructure.TmAppFlag;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmRiskList;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 调用决策系统-预审版
 * @author hp
 *
 */
@Component
public class ApplyRuleUtils{

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private TmRiskListService tmRiskListService;
	@Autowired
	private ApplyInputService applyInputService;
	@Autowired
	private ApplyQueryService applyQueryService;

	/**
	 * 根据决策返回信息提取风险警告标签内容
	 * @param appNo
	 * @param tmAppFlagList
	 * @param resultMapJs
	 */
	public List<TmAppFlag> setTmAppFlagToRiskFlag(String appNo, JSONObject resultMapJs) {
		List<TmAppFlag> tmAppFlagList = new ArrayList<>();
		//保存申请件标签
		JSONArray riskFlagJS = resultMapJs.getJSONArray(AppDitDicConstant.FLAG_TYEP_RISK);
		if (riskFlagJS != null) {
			for (int i = 0; i < riskFlagJS.size(); i++) {
				if (riskFlagJS.get(i) != null) {
					String flagCode = StringUtils.valueOf(riskFlagJS.get(i));
					TmAclDict dict = cacheContext.getAclDictByTypeAndCode(AppDitDicConstant.FLAG_TYEP_RISK, flagCode);
					TmAppFlag flag = new TmAppFlag();
					flag.setAppNo(appNo);
					flag.setFlagType(AppDitDicConstant.FLAG_TYEP_RISK);
					flag.setFlagCode(riskFlagJS.get(i).toString());
					if(dict!=null) {
						flag.setFlagDesc(dict.getCodeName());
						flag.setFlagLevel(dict.getValue2());
					}
					flag.setFlagStatus("A");//默认有效
					flag.setCreateUser(AppConstant.SYS_AUTO);
					flag.setCreateDate(new Date());
					tmAppFlagList.add(flag);
				}
			}
		}
		return tmAppFlagList;
	}

	/**
	 * 设置调用风控失败时的系统警告标签信息
	 * @param appNo
	 * @param msg
	 * @return
	 */
	public TmAppFlag setCallRiskErrorSysFlag(String appNo, String msg,String code) {
		TmAppFlag flag = new TmAppFlag();
		flag.setAppNo(appNo);
		flag.setFlagType(AppDitDicConstant.FLAG_TYEP_SYS);
		if(StringUtils.isEmpty(code)) {
			code = AppDitDicConstant.FLAG_SYS_002;//默认
		}
		TmAclDict dict = cacheContext.getAclDictByTypeAndCode(AppDitDicConstant.FLAG_TYEP_SYS, code);
		flag.setFlagCode(code);
		if(dict!=null && StringUtils.isNotEmpty(dict.getCodeName())) {
			if(StringUtils.isNotEmpty(msg)) {
				msg=dict.getCodeName()+"["+msg+"]";
			}else {
				msg=dict.getCodeName();
			}
			flag.setFlagLevel(dict.getValue2());
		}
		if (StringUtils.isNotBlank(msg)&&msg.length()>200){
			flag.setFlagDesc(msg.substring(0, 199));
		}else{
			flag.setFlagDesc(msg);
		}
		flag.setFlagStatus("A");//默认有效
		flag.setCreateUser(AppConstant.SYS_AUTO);
		flag.setCreateDate(new Date());
		return flag;
	}
	/**
	 * 设置调用风控前的风险名单库警告标签信息
	 * @param appNo
	 * @param msg
	 * @return
	 */
	public TmAppFlag setCallRiskListFlag(String appNo, String msg,String code) {
		TmAppFlag flag = new TmAppFlag();
		flag.setAppNo(appNo);
		flag.setFlagType(AppDitDicConstant.FLAG_TYEP_RISK);
		if(StringUtils.isEmpty(code)) {
			code = AppDitDicConstant.FLAG_SYS_004;//默认
		}
		TmAclDict dict = cacheContext.getAclDictByTypeAndCode(AppDitDicConstant.FLAG_TYEP_RISK, code);
		flag.setFlagCode(code);
		if(dict!=null && StringUtils.isNotEmpty(dict.getCodeName())) {
			if(StringUtils.isNotEmpty(msg)) {
				msg=dict.getCodeName()+"["+msg+"]";
			}else {
				msg=dict.getCodeName();
			}
			flag.setFlagLevel(dict.getValue2());
		}
		if (StringUtils.isNotBlank(msg)&&msg.length()>200){
			flag.setFlagDesc(msg.substring(0, 199));
		}else{
			flag.setFlagDesc(msg);
		}
		flag.setFlagStatus("A");//默认有效
		flag.setCreateUser(AppConstant.SYS_AUTO);
		flag.setCreateDate(new Date());
		return flag;
	}
	/**
	 * 根据调用审批助手获取到反欺诈结果后设置反欺诈风险警告项
	 * @param appNo
	 * @param msg
	 * @return
	 */
	public List<TmAppFlag> setRiskFlagForAntiFraudResult(String appNo,
														 List<TmAppFlag> flList,JSONObject afResult) {
		if(afResult==null || afResult.size()==0 || afResult.containsKey("tranStatus")){
			return flList;
		}
		if(CollectionUtils.isEmpty(flList)) {
			flList = new ArrayList<>();
		}
		if(afResult!=null && afResult.size()>0) {
			for (Object key : afResult.keySet()) {
				String keyStr = StringUtils.valueOf(key);
				String value = afResult.getString(keyStr);
//				Long val = null;
//				try {
//					val = StringUtils.stringTolong(value);
//				} catch (Exception e) {
//					logger.warn("申请件["+appNo+"]反欺诈结果["+value+"]解析异常"+e.getMessage());
//				}
				if(StringUtils.isEmpty(value)
						|| StringUtils.equals(value, "0")
						|| StringUtils.equals(value, "-9990996.0")
						|| StringUtils.equals(value, "-1")) {
					continue;
				}
				List<TmAclDict> dictList = cacheContext.getAclDictByTypeAndValue(
						AppDitDicConstant.FLAG_TYEP_RISK, keyStr);
				TmAclDict dict = null;
				if(CollectionUtils.sizeGtZero(dictList)) {
					dict = dictList.get(0);
				}
				String code = "";
				//如果业务字典和key对应的反欺诈结果都是为空，则不记录
				if(dict!=null && StringUtils.isNotEmpty(value)) {
					code = dict.getCode();
				}else {
					continue;
				}
				TmAppFlag flag = new TmAppFlag();
				flag.setAppNo(appNo);
				flag.setFlagType(AppDitDicConstant.FLAG_TYEP_RISK);
				flag.setFlagCode(code);
				if(dict!=null && StringUtils.isNotEmpty(dict.getCodeName())) {
					value = dict.getCodeName().replace("#DESC", value);
					flag.setFlagLevel(dict.getValue2());
				}
				flag.setFlagDesc(value);
				flag.setFlagStatus("A");//默认有效
				flag.setCreateUser(AppConstant.SYS_AUTO);
				flag.setCreateDate(new Date());
				flList.add(flag);
			}
		}
		return flList;
	}
	/**
	 * 调用决策前查询风险名单库
	 *
	 * @param main
	 * @param infoDto
	 * @return
	 */
	public Map<String, String> getRiskListInfoMap(TmAppMain main, ApplyInfoDto infoDto) {
		//查询风险名单库信息
		if (infoDto == null || StringUtils.isEmpty(infoDto.getAppNo())) {
			throw new ProcessException("查询风险名单库失败，无效的申请件信息");
		}
		if (main == null) {
			throw new ProcessException("风控决策失败，无效的申请件信息");
		}
		//客户风险名单信息
		String custName = main.getName();
		String custIdNo = main.getIdNo();
		String custCellPhone = main.getCellphone();
		String custPersonalRisk = "N";
		String custCellPhoneRisk = "N";
		String custVipRisk = "N";
		//身份信息风险名单情况以及白名单情况
		if (StringUtils.isNotBlank(custIdNo)) {
			List<TmRiskList> custPersonalRiskList = tmRiskListService.getTmRiskListInfo("", "", custIdNo);
			if (custPersonalRiskList.size() > 0) {
				for (TmRiskList custRiskList : custPersonalRiskList) {
					if (StringUtils.isNotBlank(custRiskList.getActType())) {
						if (!StringUtils.equals(custPersonalRisk, "B") && !StringUtils.equals(custRiskList.getActType(), "W")) {
							custPersonalRisk = custRiskList.getActType();
						}
						if (StringUtils.equals(custRiskList.getActType(), "W")) {
							custVipRisk = "V";
						}
					}
				}
			}
		}
		//手机风险名单情况
		if (StringUtils.isNotBlank(custName) && StringUtils.isNotBlank(custCellPhone)) {
			List<TmRiskList> custCellPhoneRiskList = tmRiskListService.getTmRiskListInfo(custName, custCellPhone, "");
			if (custCellPhoneRiskList.size() > 0) {
				for (TmRiskList phoneRiskList : custCellPhoneRiskList) {
					if (StringUtils.isNotBlank(phoneRiskList.getActType())) {
						if (!StringUtils.equals(custCellPhoneRisk, "B") && !StringUtils.equals(phoneRiskList.getActType(), "W")) {
							custCellPhoneRisk = phoneRiskList.getActType();
						}
					}
				}
			}
		}
		//客户推广人1风险名单信息
		String spreadPersonalRisk = "N";
		String spreadCellPhoneRisk = "N";
		if (StringUtils.isNotEmpty(infoDto.getTmAppPrimCardInfo())) {
			String spreadName = infoDto.getTmAppPrimCardInfo().getInputName();
			String spreadCellPhone = infoDto.getTmAppPrimCardInfo().getInputTelephone();
			String spreadIdNo = infoDto.getTmAppPrimCardInfo().getInputNo();
			//身份信息风险名单情况
			if (StringUtils.isNotBlank(spreadIdNo)) {
				List<TmRiskList> spreadTmRiskList = tmRiskListService.getTmRiskListInfo("", "", spreadIdNo);
				if (spreadTmRiskList.size() > 0) {
					for (TmRiskList spreadRiskList : spreadTmRiskList) {
						if (StringUtils.isNotBlank(spreadRiskList.getActType())) {
							if (!StringUtils.equals(spreadPersonalRisk, "B") && !StringUtils.equals(spreadRiskList.getActType(), "W")) {
								spreadPersonalRisk = spreadRiskList.getActType();
							}
						}
					}
				}
			}
			//手机风险名单情况
			if (StringUtils.isNotBlank(spreadName) && StringUtils.isNotBlank(spreadCellPhone)) {
				List<TmRiskList> spreadCellPhoneRiskList = tmRiskListService.getTmRiskListInfo(spreadName, spreadCellPhone, "");
				if (spreadCellPhoneRiskList.size() > 0) {
					for (TmRiskList spreadPhoneRiskList : spreadCellPhoneRiskList) {
						if (StringUtils.isNotBlank(spreadPhoneRiskList.getActType())) {
							if (!StringUtils.equals(spreadCellPhoneRisk, "B") && !StringUtils.equals(spreadPhoneRiskList.getActType(), "W")) {
								spreadCellPhoneRisk = spreadPhoneRiskList.getActType();
							}
						}
					}
				}
			}
		}
		//客户推广人2风险名单信息
		if (StringUtils.isNotEmpty(infoDto.getTmAppPrimCardInfo())) {
			String spreadName = infoDto.getTmAppPrimCardInfo().getSpreaderName();
			String spreadCellPhone = infoDto.getTmAppPrimCardInfo().getSpreaderTelephone();
			String spreadIdNo = infoDto.getTmAppPrimCardInfo().getSpreaderNo();
			//身份信息风险名单情况
			if (StringUtils.isNotBlank(spreadIdNo)) {
				List<TmRiskList> spreadTmRiskList = tmRiskListService.getTmRiskListInfo("", "", spreadIdNo);
				if (spreadTmRiskList.size() > 0) {
					for (TmRiskList spreadRiskList : spreadTmRiskList) {
						if (StringUtils.isNotBlank(spreadRiskList.getActType())) {
							if (!StringUtils.equals(spreadPersonalRisk, "B") && !StringUtils.equals(spreadRiskList.getActType(), "W")) {
								spreadPersonalRisk = spreadRiskList.getActType();
							}
						}
					}
				}
			}
			//手机风险名单情况
			if (StringUtils.isNotBlank(spreadName) && StringUtils.isNotBlank(spreadCellPhone)) {
				List<TmRiskList> spreadCellPhoneRiskList = tmRiskListService.getTmRiskListInfo(spreadName, spreadCellPhone, "");
				if (spreadCellPhoneRiskList.size() > 0) {
					for (TmRiskList spreadPhoneRiskList : spreadCellPhoneRiskList) {
						if (StringUtils.isNotBlank(spreadPhoneRiskList.getActType())) {
							if (!StringUtils.equals(spreadCellPhoneRisk, "B") && !StringUtils.equals(spreadPhoneRiskList.getActType(), "W")) {
								spreadCellPhoneRisk = spreadPhoneRiskList.getActType();
							}
						}
					}
				}
			}
		}
		//客户联系人风险名单信息
		String firstContactRisk = "N";
		String secondContactRisk = "N";
		if (StringUtils.isNotEmpty(infoDto.getTmAppContactMap())) {
			if (StringUtils.isNotEmpty(infoDto.getTmAppContactMap().get("mconItemInfo1"))) {
				String firstContactName = infoDto.getTmAppContactMap().get("mconItemInfo1").getContactName();
				String firstContactCellPhone = infoDto.getTmAppContactMap().get("mconItemInfo1").getContactMobile();
//				String firstContactIdNo = infoDto.getTmAppContactMap().get("mconItemInfo1").getContactIdNo();
				if (StringUtils.isNotBlank(firstContactName) && StringUtils.isNotBlank(firstContactCellPhone)) {
					List<TmRiskList> firstContactRiskList = tmRiskListService.getTmRiskListInfo(firstContactName, firstContactCellPhone, null);
					for (TmRiskList fcontactRiskList : firstContactRiskList) {
						if (StringUtils.isNotBlank(fcontactRiskList.getActType())) {
							if (!StringUtils.equals(firstContactRisk, "B") && !StringUtils.equals(fcontactRiskList.getActType(), "W")) {
								firstContactRisk = fcontactRiskList.getActType();
							}
						}
					}
				}
			}
			if (StringUtils.isNotEmpty(infoDto.getTmAppContactMap().get("mconItemInfo2"))) {
				String secondContactName = infoDto.getTmAppContactMap().get("mconItemInfo2").getContactName();
				String secondContactCellPhone = infoDto.getTmAppContactMap().get("mconItemInfo2").getContactMobile();
//				String secondContactIdNo = infoDto.getTmAppContactMap().get("mconItemInfo2").getContactIdNo();
				if (StringUtils.isNotBlank(secondContactName) && StringUtils.isNotBlank(secondContactCellPhone)) {
					List<TmRiskList> secondContactRiskList = tmRiskListService.getTmRiskListInfo(secondContactName, secondContactCellPhone, null);
					for (TmRiskList scontactRiskList : secondContactRiskList) {
						if (StringUtils.isNotBlank(scontactRiskList.getActType())) {
							if (!StringUtils.equals(secondContactRisk, "B") && !StringUtils.equals(scontactRiskList.getActType(), "W")) {
								secondContactRisk = scontactRiskList.getActType();
							}
						}
					}
				}
			}
		}
		Map<String, String> riskListInfoMap = new HashMap<>();
		riskListInfoMap.put("custPersonalRisk", custPersonalRisk);
		riskListInfoMap.put("custCellPhoneRisk", custCellPhoneRisk);
		riskListInfoMap.put("custVipRisk", custVipRisk);
		riskListInfoMap.put("spreadPersonalRisk", spreadPersonalRisk);
		riskListInfoMap.put("spreadCellPhoneRisk", spreadCellPhoneRisk);
		riskListInfoMap.put("firstContactRisk", firstContactRisk);
		riskListInfoMap.put("secondContactRisk", secondContactRisk);

		//保存风险标签
		List<TmAppFlag> tmAppFlagList = new ArrayList<>();
		TmAppFlag tmAppFlag = new TmAppFlag();
		for (Map.Entry<String, String> entry : riskListInfoMap.entrySet()) {
			if (!StringUtils.equals(entry.getValue(), "N")) {
				List<TmAclDict> tmAclDictList=cacheContext.getAclDictByTypeAndValue(AppDitDicConstant.FLAG_TYEP_RISK,entry.getKey()+entry.getValue());
				if (tmAclDictList.size()>0){
					TmAclDict tmAclDict=tmAclDictList.get(0);
					if (StringUtils.isNotEmpty(tmAclDict)){
						tmAppFlag = setCallRiskListFlag(main.getAppNo(), "", tmAclDict.getCode());
						tmAppFlagList.add(tmAppFlag);
					}
				}
			}
		}
		if (CollectionUtils.sizeGtZero(tmAppFlagList)) {
			//保存申请件标签
			applyInputService.saveOrDelTmAppFlagList(main.getAppNo(), tmAppFlagList);
//			for (int i = 0; i < tmAppFlagList.size(); i++) {
//				applyInputService.saveTmAppFlag(tmAppFlagList.get(i));
//			}
		}
		return riskListInfoMap;
	}
	/**
	 * 根据“标签”判断客户是否转人工审批
	 * @param appNo
	 * @param tmAppFlagList
	 * @param nextState
	 * @return
	 */
	public boolean tumHumanApprovalByFlag(String appNo, List<TmAppFlag> tmAppFlagList,
										  RtfState nextState,long start) {
		try {
			//只有自动审批的通过的
			if(nextState!=null && nextState==RtfState.H18) {
				List<TmAppFlag> allFlagList = applyQueryService.getTmAppFlagListByAppNo(appNo);
				if(tmAppFlagList!=null && tmAppFlagList!=null) {
					allFlagList.addAll(tmAppFlagList);
				}
				if(CollectionUtils.sizeGtZero(allFlagList)) {
					for (int i = 0; i < allFlagList.size(); i++) {
						TmAppFlag flag = allFlagList.get(i);
						if(flag!=null  && !flag.getFlagType().equals(AppDitDicConstant.FLAG_TYEP_APP)
								&& (StringUtils.equals(flag.getFlagLevel(),  AppDitDicConstant.FLAG_LEVEL_R)
								|| StringUtils.equals(flag.getFlagLevel(), AppDitDicConstant.FLAG_LEVEL_W))) {
							logger.info(LogPrintUtils.printAppNoLog(appNo, start, null)
									+"标签["+flag.getFlagCode()+"-"+flag.getFlagDesc()+"]等级为["
									+flag.getFlagLevel()+"],故转人工");
							return true;
//							TmAclDict dict = cacheContext.getAclDictByTypeAndCode(flag.getFlagType(), flag.getFlagCode());
//							if(StringUtils.equals(dict.getValue2(), AppDitDicConstant.FLAG_LEVEL_R)
//									|| StringUtils.equals(dict.getValue2(), AppDitDicConstant.FLAG_LEVEL_W)) {
//								return true;
//							}
						}
					}
				}

			}
		} catch (Exception e) {
			logger.error(LogPrintUtils.printAppNoLog(appNo, start, null)+"检索客户标签是否存在拒绝/警告标签失败",e);
		}

		return false;
	}
}
