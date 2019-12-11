package com.jjb.cas.biz.rule.risk;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jjb.acl.facility.enums.bus.EnumsActivitiNodeType;
import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.cas.biz.rule.utils.ApplyRuleUtils;
import com.jjb.ecms.biz.activiti.ActivitiUtils;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.ext.helper.ApproveHelperSupport;
import com.jjb.ecms.biz.ext.risk.CellBjjRiskSysSupport;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.biz.util.NodeObjectUtil;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCheatCheckData;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCommonData;
import com.jjb.ecms.infrastructure.TmAppContact;
import com.jjb.ecms.infrastructure.TmAppCustInfo;
import com.jjb.ecms.infrastructure.TmAppFlag;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ActivitiException;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 调用决策系统-预审版
 * @author hp
 *
 */
@Component
public class ApplyInfoRiskToPartnerUtils{

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CellBjjRiskSysSupport cellBjjRiskSupport;
	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private NodeObjectUtil nodeObjectUtil;
	@Autowired
	private ApplyInputService applyInputService;
	@Autowired
	private ApproveHelperSupport approveHelper;
	@Autowired
	private ActivitiUtils activitiUtils;
	@Autowired
	private AppCommonUtil appCommonUtil;
	@Autowired
	private ApplyRuleUtils applyRuleUtils;
	/**
	 * 重新风控
	 */
	public ApplyNodeCommonData reCellRiskPartnerExecute(String appNo) throws Exception {
		long start = System.currentTimeMillis();
		Map<String, Serializable> resultMap  = applyQueryService.getNodeInfoByAppNo(appNo, EnumsActivitiNodeType.A070.name());
		ApplyNodeCommonData applyNodeCommonData = null;
		if(resultMap!=null && resultMap.containsKey(AppConstant.APPLY_NODE_COMMON_DATA)){
			applyNodeCommonData = (ApplyNodeCommonData) resultMap.get(AppConstant.APPLY_NODE_COMMON_DATA);
		}
		ApplyInfoDto dto = new ApplyInfoDto();
		TmAppMain main = applyQueryService.getTmAppMainByAppNo(appNo);
		if(main==null) {
			throw new ProcessException("风控决策失败，无效的申请件["+appNo+"]信息");
		}
		String oldRtf = main.getRtfState();
		dto.setTmAppMain(main);
		dto.setTmAppCustInfoList(applyQueryService.getTmAppCustInfoListByAppNo(appNo));
		dto.setTmAppPrimCardInfo(applyQueryService.getTmAppPrimCardInfoByAppNo(appNo));
		Map<String, Serializable> nodeData = new HashMap<>();
		nodeData.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
		dto.setTmAppNodeInfoRecordMap(nodeData);
		dto.setAppNo(appNo);
		logger.info("重新调用风控系统for合伙人版本"+ AppConstant.BEGINING+ LogPrintUtils.printAppNoLog(appNo, start,null));
		dto = cellRiskPartnerExecute(start, dto,"重新调用合伙人流程风控决策!");
		String remark = "";
		if(dto!=null && dto.getTmAppMain()!=null) {
			TmAppMain newMain = dto.getTmAppMain();
			main.setSugLmt(newMain.getSugLmt());
			main.setPointResult(newMain.getPointResult());
			remark = newMain.getRemark();
		}
		main.setRtfState(oldRtf);
		TmAppHistory history = AppCommonUtil.insertApplyHist(main.getAppNo(),
				OrganizationContextHolder.getUserNo(),
				AppCommonUtil.stringToEnum(RtfState.class, oldRtf, null), main.getRefuseCode(), remark);
		applyInputService.updateTmAppMain(main);
		applyInputService.saveTmAppHistory(history);
		logger.info("重新调用风控系统for合伙人版本"+AppConstant.END+LogPrintUtils.printAppNoEndLog(appNo, start,null));
		return applyNodeCommonData;
	}

	/**
	 * 调用风控
	 * @param start
	 * @param
	 * @param
	 * @param remark
	 * @return
	 */
	public ApplyInfoDto cellRiskPartnerExecute(long start, ApplyInfoDto infoDto,String remark) throws Exception{
		if(infoDto==null || StringUtils.isEmpty(infoDto.getAppNo())) {
			throw new ProcessException("风控决策失败，无效的申请件信息");
		}
		String appNo =  infoDto.getAppNo();
		TmAppMain main = infoDto.getTmAppMain();
		if(main==null) {
			throw new ProcessException("风控决策失败，无效的申请件["+appNo+"]信息");
		}
		appCommonUtil.setOrg(main.getOrg());
		main.setRemark(remark);
		RtfState nextState = RtfState.H15;
		Map<String, Serializable> nodeData = new HashMap<String, Serializable>();
		ApplyNodeCommonData applyNodeCommonData = null;
		if(infoDto.getTmAppNodeInfoRecordMap()!=null && infoDto.getTmAppNodeInfoRecordMap().containsKey(AppConstant.APPLY_NODE_COMMON_DATA)) {
			applyNodeCommonData = (ApplyNodeCommonData) infoDto.getTmAppNodeInfoRecordMap().get(AppConstant.APPLY_NODE_COMMON_DATA);
		}
		if(applyNodeCommonData==null) {
			applyNodeCommonData = AppCommonUtil.setApplyNodeCommonData(applyNodeCommonData, main);
		}
		List<TmAppFlag> tmAppFlagList = new ArrayList<>();
		try {
			if (!StringUtils.equals(main.getAppType(), "S")) {
				JSONObject jsReq = new JSONObject();
				JSONObject custJs = new JSONObject();
				TmAppCustInfo cust = null;
				if (CollectionUtils.sizeGtZero(infoDto.getTmAppCustInfoList())){
					cust=infoDto.getTmAppCustInfoList().get(0);
				}
				if (cust == null) {
					logger.info(LogPrintUtils.printAppNoLog(appNo, start, null) + "没有有效的主卡申请人信息");
					throw new ActivitiException("无有效申请人信息");
				}
				//调用决策前查询风险名单库,并将命中情况上送决策系统做相关决策
				Map<String, String> riskListInfoMap = applyRuleUtils.getRiskListInfoMap(main, infoDto);
				if (StringUtils.isNotEmpty(riskListInfoMap)) {
					custJs.put("custPersonalRisk", riskListInfoMap.get("custPersonalRisk"));
					custJs.put("custCellPhoneRisk", riskListInfoMap.get("custCellPhoneRisk"));
					custJs.put("custVipRisk", riskListInfoMap.get("custVipRisk"));
					custJs.put("spreadPersonalRisk", riskListInfoMap.get("spreadPersonalRisk"));
					custJs.put("spreadCellPhoneRisk", riskListInfoMap.get("spreadCellPhoneRisk"));
					custJs.put("firstContactRisk", riskListInfoMap.get("firstContactRisk"));
					custJs.put("secondContactRisk", riskListInfoMap.get("secondContactRisk"));
				}
				String homeState = StringUtils.valueOf(cust.getHomeState());
				String homeCity = StringUtils.valueOf(cust.getHomeCity());
				String homeZone = StringUtils.valueOf(cust.getHomeZone());
				String homeAdd = StringUtils.valueOf(cust.getHomeAdd());
				String empProvince = StringUtils.valueOf(cust.getEmpProvince());
				String empCity = StringUtils.valueOf(cust.getEmpCity());
				String empZone = StringUtils.valueOf(cust.getEmpZone());
				String empAdd = StringUtils.valueOf(cust.getEmpAdd());
				custJs.put("app_no", cust.getAppNo());
				custJs.put("name", cust.getName());
				custJs.put("id_card", cust.getIdNo());
				custJs.put("phone_no", cust.getCellphone());
				custJs.put("appLmt", main.getAppLmt());
				custJs.put("companyName", cust.getCorpName());
				custJs.put("EmpPhone", cust.getEmpPhone());
				custJs.put("tel_home", cust.getHomePhone());
				custJs.put("age", cust.getAge());
				custJs.put("MaritalStatus", cust.getMaritalStatus());
				custJs.put("HomeADD", homeAdd);
				custJs.put("YearIncome", cust.getYearIncome());
				custJs.put("Qualification", cust.getQualification());
				custJs.put("home_state", homeState);
				custJs.put("home_city", homeCity);
				custJs.put("emp_province", empProvince);
				custJs.put("emp_city", empCity);
				custJs.put("CardType", main.getProductCd());
				custJs.put("email", cust.getEmail());
				custJs.put("homeAddFull", (homeState+homeCity+homeZone+homeAdd).replace(" ",""));
				custJs.put("empAddFull", (empProvince+empCity+empZone+empAdd).replace(" ",""));
				TmAppPrimCardInfo card = infoDto.getTmAppPrimCardInfo();
				if(card!=null) {
					custJs.put("spreaderSupCode", card.getSpreaderSupCode());
					custJs.put("spreaderAreaCode", card.getSpreaderAreaCode());
					custJs.put("spreaderTeamCode", card.getSpreaderTeamCode());
				}
				custJs.put("blacklist", "是");
				//联系人信息
				Map<String, TmAppContact> contactMap = applyQueryService.getTmAppContactByAppNo(appNo);
				if(contactMap!=null && contactMap.get(AppConstant.M_CON_ITEM_INFO_PREFIX+"1")!=null) {
					TmAppContact con1 = contactMap.get(AppConstant.M_CON_ITEM_INFO_PREFIX+"1");
					custJs.put("firstContactName", con1.getContactName());
					custJs.put("firstContactMobile", con1.getContactMobile());
					custJs.put("firstContactRelation", con1.getContactRelation());
				}
				if(contactMap!=null && contactMap.get(AppConstant.M_CON_ITEM_INFO_PREFIX+"2")!=null) {
					TmAppContact con2 = contactMap.get(AppConstant.M_CON_ITEM_INFO_PREFIX+"2");
					custJs.put("secondContactName", con2.getContactName());
					custJs.put("secondContactMobile", con2.getContactMobile());
					custJs.put("secondContactRelation", con2.getContactRelation());
				}
				TmProduct product = cacheContext.getProduct(main.getProductCd());
				if(product == null || StringUtils.isEmpty(main.getProductCd())){
					throw new ActivitiException("产品数据为空，该产品或已失效，请联系管理员");
				}
				String riskPro = activitiUtils.getRiskProCd1ByProductAndAppSource(appNo, start,
						main.getProductCd(), main.getAppSource(), "RiskToPartner");
				if(StringUtils.isEmpty(riskPro)) {
					logger.info("系统未配置产品["+main.getProductCd()+"]与申请渠道["+main.getAppSource()+"]的风控业务条线");
					throw new ActivitiException("系统未配置有效的风险决策产品跳线信息，调用失败，请联系管理员！");
				}
				Map<String, String> bjjRiskConf = cacheContext.getInterNetAddrParam(AppDitDicConstant.ext_bjj_risk_conf);
				if (bjjRiskConf == null || bjjRiskConf.size() == 0) {
					throw new ActivitiException("未查询到风控系统网络配置，调用失败！");
				}
				jsReq.put("productCode", riskPro);
				custJs.put("qryRsn", StringUtils.setValue(bjjRiskConf.get("pbocQryRsn"), "03"));
				//调用审批小助手
				//custJs.put("fanqz", approveHelper.fanqz(infoDto, contactMap));
				JSONObject fanqzJs = approveHelper.fanqz(infoDto, contactMap);
				if(fanqzJs==null || fanqzJs.size()==0 || fanqzJs.containsKey("tranStatus")) {
					TmAppFlag flag = applyRuleUtils.setCallRiskErrorSysFlag(appNo, null, AppDitDicConstant.FLAG_SYS_003);
					tmAppFlagList.add(flag);
					if(StringUtils.equals(main.getAppSource(), "01")) {
						nextState=RtfState.H15;
						main.setRtfState(nextState.name());
						main.setRemark("[系统备注]未获取到客户反欺诈，跳过决策进入人工审核");
						throw new ProcessException("未获取到客户反欺诈，跳过决策进入人工审核");
					}
				}
				tmAppFlagList=applyRuleUtils.setRiskFlagForAntiFraudResult(appNo, tmAppFlagList, fanqzJs);
				custJs.putAll(fanqzJs);
				jsReq.put("requestParam", custJs);
				JSONObject resJs = cellBjjRiskSupport.sendBjjRiskSys("decision", jsReq, bjjRiskConf);
				if (resJs != null) {
					ApplyNodeCheatCheckData checkData = new ApplyNodeCheatCheckData();
					try {
						checkData.setFraudData(fanqzJs.toJSONString());
						checkData.setStateCode(resJs.getString("stateCode"));
						checkData.setStateDesc(resJs.getString("stateDesc"));
						checkData.setSerialNumber(resJs.getString("serialNumber"));
						JSONObject resoultJs = resJs.getJSONObject("decisionResult");
						if (resoultJs != null) {
							checkData.setContent(resoultJs.getString("result"));
							checkData.setFinalScore(StringUtils.stringToInteger(resoultJs.getString("score")));//信用分
							checkData.setSuggestAmt(StringUtils.stringToBigDecimal(resoultJs.getString("quota")));//建议额度
							checkData.setInterestRate(resoultJs.getString("interstRate"));//利率
							JSONObject resultMapJs = resoultJs.getJSONObject("resultMap");
							if (resultMapJs != null) {
								checkData.setBaseLmt(StringUtils.stringToBigDecimal(resultMapJs.getString("baseLimits")));
								checkData.setIncomeLmt(StringUtils.stringToBigDecimal(resultMapJs.getString("incomeLimits")));
								if(checkData.getFinalScore()==null) {
									checkData.setFinalScore(StringUtils.stringToInteger(resultMapJs.getString("score")));//信用分
								}
								if(checkData.getSuggestAmt()==null) {
									checkData.setSuggestAmt(StringUtils.stringToBigDecimal(resultMapJs.getString("quota")));//建议额度
								}

								JSONArray js = resultMapJs.getJSONArray("reject_reason");
								if(js==null || js.size()==0) {
									js = resultMapJs.getJSONArray("refuseCode");
								}
								if (js != null) {
									String reason = "";
									for (int i = 0; i < js.size(); i++) {
										if (js.get(i) != null) {
											reason = reason + "," + js.get(i).toString();
										}
									}
									checkData.setUnapprovedReasonCode(reason);//未通过原因码
									checkData.setUnapprovedReasonDesc(resJs.getString("reasonDesc"));//未通过原因描述
								}
								//保存申请件标签
								tmAppFlagList = applyRuleUtils.setTmAppFlagToRiskFlag(appNo, resultMapJs);
							}
						}
//						checkData.setRiskClassic(riskClassic); // 风险等级
//						checkData.setCreditScoreLevel(creditScoreLevel);//评分等级
						checkData.setRiskContent(resJs.toJSONString());//决策系统返回的原Json数据
						nodeData.put(AppConstant.APPLY_NODE_CHEAT_CHECK_DATA, checkData);
					} catch (Exception e) {
						logger.error("解析风控响应数据异常", e);
						throw new ActivitiException("解析风控决策结果数据异常，请联系管理员");
					}
					String refuseCode = "";
					if (StringUtils.isNotEmpty(checkData.getUnapprovedReasonCode())) {
						refuseCode = checkData.getUnapprovedReasonCode() + "-" + checkData.getUnapprovedReasonDesc();
					}
					logger.info("系统风控之准入接口响应码[" + refuseCode + "]");

					if (resJs != null && resJs.containsKey("stateCode") && StringUtils.equals(resJs.get("stateCode"), "00000")) {

						String result = checkData.getContent();
						BigDecimal sugAmt = checkData.getSuggestAmt();//决策最大额度
						if(sugAmt!=null){
							BigDecimal bignum=new BigDecimal("1000");
							sugAmt=((sugAmt.divide(bignum)).setScale(0,BigDecimal.ROUND_HALF_UP)).multiply(bignum);
						}
						BigDecimal proMaxAmt = product.getApprovalMaximum();//产品可授信最高额度
						if (StringUtils.equals(result, "通过")) {
							//建议额度为空或者为0 或者大于产品最高授信额度，则转人工
							if (sugAmt == null || (sugAmt != null && sugAmt.compareTo(new BigDecimal(0)) <= 0)) {
								nextState = RtfState.H15;
								main.setRemark(main.getRemark()+"系统备注：因风控返回建议额度为空或者为0，系统自动进入人工审批节点");
							}else if ((proMaxAmt != null && proMaxAmt.compareTo(sugAmt) <= 0)) {
								nextState = RtfState.H15;
								main.setRemark(main.getRemark()+"系统备注：因风控返回建议额度大于产品配置最大授信额度，系统自动进入人工审批节点");
							} else {
								nextState = RtfState.H18;
								main.setRemark(main.getRemark()+refuseCode);
								main.setAccLmt(sugAmt);
							}

						} else if (StringUtils.concat(result, "拒绝")) {
							nextState = RtfState.H17;
							main.setRefuseCode(StringUtils.setValue(refuseCode, "风险决策综合评分不足"));//拒绝原因
						} else if (StringUtils.equals(result, "复议")) {
							nextState = RtfState.H15;
							main.setRemark(main.getRemark()+refuseCode);
						} else {
							logger.warn("系统风控返回响应吗: {} 不能识别，跳转到(人工)初审 ", refuseCode);
							nextState = RtfState.H15;
							main.setRemark(main.getRemark()+refuseCode);
						}
						main.setSugLmt(sugAmt);
						if (checkData.getFinalScore() != null) {
							main.setPointResult(checkData.getFinalScore());
						}
					} else {
						String failMsg = "无有效风控系统决策结果";
						if (checkData != null) {
							failMsg = refuseCode;
							logger.error("风控决策结论失败[" + failMsg + "]");
						}
						throw new ActivitiException(failMsg);
					}
				}

			}
		} catch (Exception e) {
			logger.error(LogPrintUtils.printAppNoLog(appNo, start, null)+"调用系统风险系统异常！"+ e.getMessage());
			nextState = RtfState.H15;//所有异常均跳入人工审批
			if(e instanceof ProcessException) {
				main.setRemark(main.getRemark()+e.getMessage());
			}else if(e instanceof ActivitiException) {
				main.setRemark(main.getRemark()+e.getMessage());
			}else {
				main.setRemark(main.getRemark()+"调用[决策系统]处理发生未知异常，请联系管理员!");
			}
			TmAppFlag erFlag = applyRuleUtils.setCallRiskErrorSysFlag(appNo, e.getMessage(),null);
			tmAppFlagList.add(erFlag);
//			throw  new ProcessException(e.getMessage());
		} finally {
			//判断标签是否属于拒绝和警告等级
			boolean rs = applyRuleUtils.tumHumanApprovalByFlag(appNo, tmAppFlagList,nextState,start);
			if(rs) {
				nextState=RtfState.H15;
			}
			applyNodeCommonData.setRtfStateType(StringUtils.valueOf(nextState.state));
			main.setRtfState(nextState.state);
			applyNodeCommonData = AppCommonUtil.setApplyNodeCommonData(applyNodeCommonData, main);
			nodeData.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
			infoDto.setTmAppMain(main);
			infoDto.setTmAppNodeInfoRecordMap(nodeData);
			nodeObjectUtil.insertAllNodeRec(nodeData, appNo);
			if(CollectionUtils.sizeGtZero(tmAppFlagList)) {
				//保存申请件标签
				applyInputService.saveOrDelTmAppFlagList(appNo,tmAppFlagList);
			}
			logger.info("系统风控决策" + AppConstant.END + LogPrintUtils.printAppNoEndLog(appNo, start, null));
		}
		return infoDto;
	}


}
