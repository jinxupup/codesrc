package com.jjb.cas.biz.process;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSON;
import com.jjb.acl.infrastructure.TmAclBranch;
import com.jjb.ecms.biz.dao.apply.TmAppPartnerDao;
import com.jjb.ecms.biz.service.manage.ApplySpreaderInfoService;
import com.jjb.ecms.biz.service.manage.TmRiskListService;
import com.jjb.ecms.infrastructure.TmAppPartner;
import com.jjb.ecms.infrastructure.TmAppSprePerBank;
import com.jjb.ecms.infrastructure.TmRiskList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.apply.TmMirCardDao;
import com.jjb.ecms.biz.ext.risk.CellBjjRiskSysSupport;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.infrastructure.TmMirCard;
import com.jjb.ecms.service.dto.T1013.T1013Req;
import com.jjb.ecms.service.dto.T1013.T1013Resp;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 合伙人准入申请资格验证
 * @author 何嘉能
 *
 */
@Component
public class T1013ProcessUtils {
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private TmMirCardDao tmMirCardDao;
	@Autowired
	private AppCommonUtil appCommonUtil;
	@Autowired
	private CellBjjRiskSysSupport cellBjjRiskSupport;
	@Autowired
	private TmAppPartnerDao tmAppPartnerDao;
	@Autowired
	private ApplySpreaderInfoService applySpreaderInfoService;
	@Autowired
	private TmRiskListService tmRiskListService;
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 执行
	 * @param req
	 * @return
	 * @throws ProcessException
	 */
	@Transactional
	public T1013Resp executeT1013(T1013Req req,String tonkId) throws ProcessException {
		logger.debug("T1013Process-处理合伙人准入资格验证开始...：");
		T1013Resp resp = new T1013Resp();
		String riskResult = "R";
		checkT1013Req(req);
		String name = req.getName();
		String idNo = req.getIdNo();
		Map<String, Object> map = new HashMap<>();
		map.put("idType", "I");
		map.put("idNo", idNo);
//		map.put("name", name);
		String exitCard = StringUtils.setValue(req.getExitCard(), "N");//用户默认无卡
		if(StringUtils.equals(exitCard, "N")) {//如果合伙人用户无卡的话，审批再验证一次
			List<TmMirCard> tmMirCardList1 = tmMirCardDao.getTmMirCardList(map);
			LinkedHashMap<Object,Object> dictMap = cacheContext.getAclDictByType(AppDitDicConstant.T1013CardBlockCode);
			if(CollectionUtils.sizeGtZero(tmMirCardList1)) {
				logger.info(LogPrintUtils.printCommonEndLog(tonkId, "executeT1013")+"该合伙人为已有卡客户");
				exitCard = "Y";//用户已有卡
				if(dictMap==null || dictMap.isEmpty()) {
					dictMap = new LinkedHashMap<>();
					dictMap.put("E", "持卡人冻结止付");
					dictMap.put("T", "银行冻结止付");
				}
				for (TmMirCard mirCard : tmMirCardList1) {
					if(mirCard!=null) {
						if(StringUtils.isNotEmpty(mirCard.getName()) && !StringUtils.equals(name, mirCard.getName())) {
							logger.info("同证件号码["+idNo+"]其填写的客户姓名["+name+"]与系统中客户姓名["+mirCard.getName()+"]不一致！");
							throw new ProcessException("同证件号码与其客户姓名不一致，请仔细核对!");
						}
						String blockCode = mirCard.getBlockCode();
						//E:持卡人冻结止付 ; F:欺诈; T:银行冻结止付 ;Y:催收
						if(StringUtils.isNotEmpty(blockCode) && dictMap.containsKey(blockCode)) {
							throw new ProcessException("您当前持有卡片状态异常,无法成为合伙人");
						}
					}
				}
			}
		}
		req.setExitCard(exitCard);
		riskResult = callDesRisk(req, tonkId);
		resp.setRiskResult(riskResult);
		return resp;
	}
	/**
	 * T1013交易请求参数验证
	 * @param req
	 */
	private void checkT1013Req(T1013Req req) {
		if (req == null) {
			throw new ProcessException("T1013-请求参数不能为空");
		}
		if (StringUtils.isEmpty(req.getName())) {
			throw new ProcessException("T1013-请求参数-[客户姓名]不能为空");
		}
		if (StringUtils.isEmpty(req.getIdNo())) {
			throw new ProcessException("T1013-请求参数-[证件号码]不能为空");
		}
		if (StringUtils.isEmpty(req.getCellPhone())) {
			throw new ProcessException("T1013-请求参数-[手机号码]不能为空");
		}
	}
	
	/**
	 * 调用风控获取准入结果
	 * @param req
	 * @param tonkId
	 * @return
	 */
	private String callDesRisk(T1013Req req,String tonkId) {
		logger.info("T1013Process-请求风控决策系统验证准入资格" + AppConstant.BEGINING + LogPrintUtils.printCommonEndLog(tonkId, "callDesRisk"));
		appCommonUtil.setOrg(OrganizationContextHolder.getOrg());
		String riskResult = "R";//默认拒绝
		try {

			JSONObject jsReq = new JSONObject();
			JSONObject custJs = new JSONObject();
			Random ran = new Random();
			String ia = StringUtils.valueOf(ran.nextInt(100));
//			String ranNum = String.format("%0" + 3 + "d", (ia.getBytes().length))+ia;
			custJs.put("app_no", "SYS"+System.currentTimeMillis()+"-"+ia);
			custJs.put("name", req.getName());
			custJs.put("id_card", req.getIdNo());
			custJs.put("phone_no", req.getCellPhone());
			custJs.put("blacklist", "是");
			//合伙人准入调用风控决策时，先查询风险名单库，并将命中情况上送决策系统做相关决策
			String spreadPersonalRisk = "N";  //默认N(未名中)  身份信息风险名单情况
			String spreadCellPhoneRisk = "N";  //手机风险名单情况
			String spreadVipRisk = "N";            //白名单情况
			//身份信息风险名单情况以及白名单情况
			if (StringUtils.isNotBlank(req.getIdNo())) {
				List<TmRiskList> custSpreadRiskList = tmRiskListService.getTmRiskListInfo("", "", req.getIdNo());
				if (custSpreadRiskList.size() > 0) {
					for (TmRiskList spreadRiskList : custSpreadRiskList) {
						if (StringUtils.isNotBlank(spreadRiskList.getActType())) {
							if (!StringUtils.equals(spreadPersonalRisk, "B") && !StringUtils.equals(spreadRiskList.getActType(), "W")) {
								spreadPersonalRisk = spreadRiskList.getActType();
							}
							if (StringUtils.equals(spreadRiskList.getActType(), "W")) {
								spreadVipRisk = "V";
							}
						}
					}
				}
			}
			//手机风险名单情况
			if (StringUtils.isNotBlank(req.getName()) && StringUtils.isNotBlank(req.getCellPhone())) {
				List<TmRiskList> spreadCellPhoneRiskList = tmRiskListService.getTmRiskListInfo(req.getName(), req.getCellPhone(), "");
				if (spreadCellPhoneRiskList.size() > 0) {
					for (TmRiskList phoneRiskList : spreadCellPhoneRiskList) {
						if (StringUtils.isNotBlank(phoneRiskList.getActType())) {
							if (!StringUtils.equals(spreadCellPhoneRisk, "B") && !StringUtils.equals(phoneRiskList.getActType(), "W")) {
								spreadCellPhoneRisk = phoneRiskList.getActType();
							}
						}
					}
				}
			}
			custJs.put("spreadPersonalRisk", spreadPersonalRisk);
			custJs.put("spreadCellPhoneRisk", spreadCellPhoneRisk);
			custJs.put("spreadVipRisk", spreadVipRisk);

			Map<String, String> bjjRiskConf = cacheContext.getInterNetAddrParam(AppDitDicConstant.ext_bjj_risk_conf);
			if (bjjRiskConf == null || bjjRiskConf.size() == 0) {
				throw new ProcessException("未查询到风控系统网络配置，调用失败！");
			}
			String productCode = "";
			String productCodeNc = bjjRiskConf.get("riskT1013ProCodeNC");
			String productCodeOc = bjjRiskConf.get("riskT1013ProCodeOC");
			
			//如果是新客户，就使用合伙人-已有卡业务条线代码
			if(StringUtils.equals(req.getExitCard(), "Y")) {
				//如果用合伙人-已有卡业务条线代码为空就使用合伙人-新客业务条线代码
				productCode = StringUtils.setValue(productCodeOc, productCodeNc);
			}else {
				//如果是新客户，就使用合伙人-新客业务条线代码;如果用合伙人-新客业务条线代码为空就使用合伙人-已有卡业务条线代码
				productCode = StringUtils.setValue(productCodeNc, productCodeOc);;
			}
			if(StringUtils.isEmpty(productCodeOc)) {
				throw new ProcessException("合伙人准入风控条线业务码为空!");
			}
			jsReq.put("productCode", productCode);
			custJs.put("qryRsn", StringUtils.setValue(bjjRiskConf.get("pbocQryRsn"), "03"));
			jsReq.put("requestParam", custJs);
			JSONObject resJs = cellBjjRiskSupport.sendBjjRiskSys("decision", jsReq, bjjRiskConf);
			logger.debug(LogPrintUtils.printCommonStartLog(tonkId, "callDesRisk")+"结果:"+resJs);
			String reason = "";//拒绝原因
			if (resJs != null) {
				try {
					String stateCode = resJs.getString("stateCode");
					String stateDesc = resJs.getString("stateDesc");
					String serialNumber = resJs.getString("serialNumber");
					logger.info(LogPrintUtils.printCommonStartLog(tonkId, "callDesRisk")+"返回的状态码["+stateCode+"]");
					logger.info(LogPrintUtils.printCommonStartLog(tonkId, "callDesRisk")+"返回的状态描述["+stateDesc+"]");
					logger.info(LogPrintUtils.printCommonStartLog(tonkId, "callDesRisk")+"返回的交易流水号["+serialNumber+"]");
					JSONObject resoultJs = resJs.getJSONObject("decisionResult");
					if (resoultJs != null) {
						String content = resoultJs.getString("result");
						if (StringUtils.equals(stateCode, "00000")) {
							if(StringUtils.equals(content, "通过")) {
								riskResult = "P";
							}else {
								riskResult = "R";
							}
						}
						logger.info(LogPrintUtils.printCommonStartLog(tonkId, "callDesRisk")+"返回的风险决策结果[" + content + "],合伙人准入结果["+riskResult+"]");
					}
					logger.info(LogPrintUtils.printCommonStartLog(tonkId, "callDesRisk")+"返回的拒绝原因码[" + reason + "]");
					//合伙人数据入库
					TmAppPartner tmAppPartner= new TmAppPartner();
					tmAppPartner.setName(req.getName());
					tmAppPartner.setIdNo(req.getIdNo());
					tmAppPartner.setCellPhone(req.getCellPhone());
					tmAppPartner.setExitCard(req.getExitCard());
					//合伙人类型默认为M MGM
					if (StringUtils.isBlank(req.getPartnerType())){
						tmAppPartner.setPartnerType("M");
					}else{
						tmAppPartner.setPartnerType(req.getPartnerType());
					}
					tmAppPartner.setCorpName(req.getCorpName());
					//部门
					tmAppPartner.setEmpDepartment(req.getEmpDepartment());
					//决策结果
					tmAppPartner.setDecisionResult(riskResult);
					//拒绝原因
					tmAppPartner.setRefuseReason(resJs.getString("reasonDesc"));
					//决策返回报文
					tmAppPartner.setDecisionJson(JSON.toJSONString(resJs));
					if (StringUtils.isNotEmpty(tmAppPartner)){
						tmAppPartnerDao.saveTmAppPartner(tmAppPartner);
					}
					//通过之后保存至推广人列表  如果存在则更新
					if (StringUtils.equals(riskResult,"P")){
						TmAppSprePerBank tmAppSprePerBank = new TmAppSprePerBank();
						TmAppSprePerBank spreader = new TmAppSprePerBank();
						spreader.setSpreaderNum(req.getIdNo());
						List<TmAppSprePerBank> spreaderList = applySpreaderInfoService.getSpreaderByParam(spreader);
						if (spreaderList != null && spreaderList.size() > 0) {
							tmAppSprePerBank=spreaderList.get(0);
							TmAclBranch tmAclBranch=cacheContext.getTmAclBranchByCode("06106");
							tmAppSprePerBank.setSpreaderBankId(tmAclBranch.getBranchCode());
							tmAppSprePerBank.setSpreaderBankName(tmAclBranch.getBranchName());
							tmAppSprePerBank.setSpreaderOrg(tmAclBranch.getParentCode());
							tmAppSprePerBank.setSpreaderName(req.getName());
							tmAppSprePerBank.setSpreaderPhone(req.getCellPhone());
							tmAppSprePerBank.setSpreaderNum(req.getIdNo());
							tmAppSprePerBank.setSpreaderStatus("1");  //有效的状态
							tmAppSprePerBank.setSprUserType("6");
							applySpreaderInfoService.updateSpreader(tmAppSprePerBank);
						}else{
							TmAclBranch tmAclBranch=cacheContext.getTmAclBranchByCode("06106");
							tmAppSprePerBank.setSpreaderBankId(tmAclBranch.getBranchCode());
							tmAppSprePerBank.setSpreaderBankName(tmAclBranch.getBranchName());
							tmAppSprePerBank.setSpreaderOrg(tmAclBranch.getParentCode());
							tmAppSprePerBank.setSpreaderName(req.getName());
							tmAppSprePerBank.setSpreaderPhone(req.getCellPhone());
							tmAppSprePerBank.setSpreaderNum(req.getIdNo());
							tmAppSprePerBank.setSpreaderStatus("1");  //有效的状态
							tmAppSprePerBank.setSprUserType("6");
							applySpreaderInfoService.saveSpreader(tmAppSprePerBank);
						}
					}
				} catch (Exception e) {
					logger.error("解析风控响应数据异常", e);
					throw new ProcessException("解析风控决策结果数据异常，请联系管理员");
				}
			}
		} catch (Exception e) {
			logger.error(LogPrintUtils.printCommonStartLog(tonkId, "callDesRisk")+"调用系统风险系统异常！"+ e.getMessage());
		} finally {
			logger.info("T1013Process-请求风控决策系统验证准入资格" + AppConstant.END + LogPrintUtils.printCommonEndLog(tonkId, "callDesRisk"));
		}
		return riskResult;

	}
}
