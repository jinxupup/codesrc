package com.jjb.ecms.biz.ext.helper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.jjb.acl.facility.enums.bus.EnumsActivitiNodeType;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.apply.TmAppPrimCardInfoDao;
import com.jjb.ecms.biz.dao.node.TmAppNodeInfoDao;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.biz.util.http.HttpClientUtil1;
import com.jjb.ecms.biz.util.http.HttpClientUtil2;
import com.jjb.ecms.biz.util.http.HttpHelper;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.infrastructure.TmAppContact;
import com.jjb.ecms.infrastructure.TmAppCustInfo;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppNodeInfo;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 访问信用生活审批助手服务支持类
 * @author hp
 *
 */
@Component
public class ApproveHelperSupport {

	private Logger logger = LoggerFactory.getLogger(ApproveHelperSupport.class);

	@Autowired
	private TmAppNodeInfoDao tmAppNodeInforecordDao;
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private TmAppPrimCardInfoDao tmAppPrimCardInfoDao;

	/**
	 * 查询反欺诈变量
	 * 
	 * @param applyInfo
	 * @param contactMap
	 * @return
	 */
	public JSONObject fanqz(ApplyInfoDto applyInfo, Map<String, TmAppContact> contactMap) {
		long start = System.currentTimeMillis();
		String appNo = applyInfo.getAppNo();
		CreditLifeRequest req = null;
		String servId = "审批助手";
		JSONObject returnJs = new JSONObject();
		try {
			Map<String, String> bjjRiskConf = cacheContext.getInterNetAddrParam(AppDitDicConstant.ext_creditLife_conf);
			if(bjjRiskConf==null || bjjRiskConf.size()==0){
				throw new ProcessException("未查询到审批助手系统网络配置，调用失败！");
			}
			String swit = StringUtils.valueOf(bjjRiskConf.get("fanSwitch"));//审批助手开关
			String url = StringUtils.valueOf(bjjRiskConf.get("fanUrl"));//审批助手URL地址
			String ip = StringUtils.setValue(StringUtils.valueOf(bjjRiskConf.get("fanHost")), "127.0.0.1");//审批助手IP地址
			String port = StringUtils.setValue(StringUtils.valueOf(bjjRiskConf.get("fanPort")), "80");//审批助手端口
			String fanCallMode = StringUtils.valueOf(bjjRiskConf.get("fanCallMode"));//审批助手模式
	        String fanUrl = "http://"+ip+":"+port+"/"+url;
			if (StringUtils.isNotBlank(swit) && swit.contains("S_OFF")) {
				if (swit.contains(applyInfo.getTmAppMain().getAppSource())) {
					logger.info(appNo + "开关关闭状态下需调用审批小助手反欺诈");
				} else {
					logger.info(appNo + "开关关闭状态下无需调用审批小助手反欺诈");
					return new JSONObject();
				}
			}
			if (StringUtils.isEmpty(url)) {
				throw new ProcessException("无法调用审批助手系统.系统配置[审批助手地址]为空！");
			}
			JSONObject reqJson = new JSONObject();
			reqJson.put("appNo", appNo);
			req = getParam(applyInfo, contactMap);
			reqJson.put("data", req);
            logger.info(LogPrintUtils.printAppNoLog(appNo, start, servId) + "URL[{}]，调用[{}]请求:{}", fanUrl,fanCallMode,reqJson.toJSONString());
	        String restr = null;
	        //调用模式1
			if (StringUtils.isEmpty(fanCallMode) || StringUtils.equals(fanCallMode, "1")) {
				HashMap<String, String> head = new HashMap<String, String>();
				head.put("Content-type", "application/json; charset=UTF-8");
				restr = HttpHelper.post(fanUrl, reqJson.toJSONString(), head, 30, "UTF-8");
			} else if (StringUtils.equals(fanCallMode, "2")) {//调用模式2
				restr = HttpClientUtil2.pbocQuery(servId, fanUrl, ip, port, reqJson);
			} else if (StringUtils.equals(fanCallMode, "3")) {//调用模式3
				restr = HttpClientUtil1.sendHttpPost(servId, fanUrl, reqJson);
			} else if (StringUtils.equals(fanCallMode, "4")) {//调用模式4
				byte[] byRes = HttpHelper.post(fanUrl, reqJson.toJSONString().getBytes("UTF-8"));
				restr = new String(byRes);
			}
			logger.info(appNo + "审批小助手返回结果报文:" + restr);
			JSONObject respJson = JSONObject.parseObject(restr);
			if (respJson.getIntValue("code") == 0) {
				saveResponse(appNo, restr);
				returnJs = respJson.getJSONObject("data");
			} else {
				logger.error(String.format("审批小助手返回结果异常appNo=%s", appNo));
			}
		} catch (Exception e) {
			logger.error(String.format("请求审批小助手发生错误appNo=%s", appNo), e);
			returnJs.put("tranStatus", AppDitDicConstant.FLAG_SYS_003);
		}finally {
			logger.info(LogPrintUtils.printAppNoEndLog(appNo, start, servId));
		}
		return returnJs;
	}

	/**
	 * 拼写反欺诈变量查询请求
	 * 
	 * @param applyInfo
	 * @param contactMap
	 * @return
	 * @throws Exception
	 */
	private CreditLifeRequest getParam(ApplyInfoDto applyInfo, Map<String, TmAppContact> contactMap) throws Exception {

		CreditLifeRequest req = new CreditLifeRequest();
		TmAppMain tmAppMain = applyInfo.getTmAppMain();
		TmAppCustInfo custInfo = null;
		for (TmAppCustInfo t : applyInfo.getTmAppCustInfoList()) {
			if ("B".equals(t.getBscSuppInd())) {// 主卡
				custInfo = t;
				break;
			}
		}
		if (custInfo == null)
			throw new Exception("主卡申请人信息为空");
		req.setName(tmAppMain.getName());
		req.setIdNo(tmAppMain.getIdNo());
		req.setPhone(tmAppMain.getCellphone());
		req.setProductCd(tmAppMain.getProductCd());
		req.setMaritalStatus(custInfo.getMaritalStatus());
		req.setHomeLocation(custInfo.getHomeAddrCtryCd());
		req.setHomeState(custInfo.getHomeState());
		req.setHomeCity(custInfo.getHomeCity());
		req.setHomeZone(custInfo.getHomeZone());
		req.setHomeAddr(custInfo.getHomeAdd());
		req.setComMobile(custInfo.getEmpPhone());
		req.setComName(custInfo.getCorpName());
		req.setComState(custInfo.getEmpProvince());
		req.setComCity(custInfo.getEmpCity());
		req.setComZone(custInfo.getEmpZone());
		req.setComAddr(custInfo.getEmpAdd());
		TmAppPrimCardInfo prim=applyInfo.getTmAppPrimCardInfo();
		if(null==prim)
		prim=tmAppPrimCardInfoDao.getTmAppPrimCardInfoByAppNo(tmAppMain.getAppNo());
		req.setSpreader_sup_code(prim==null?"":StringUtils.valueOf(prim.getSpreaderSupCode()));
		req.setSpreader_area_code(prim==null?"":StringUtils.valueOf(prim.getSpreaderAreaCode()));
		if (contactMap != null && contactMap.get(AppConstant.M_CON_ITEM_INFO_PREFIX + "1") != null) {
			TmAppContact con1 = contactMap.get(AppConstant.M_CON_ITEM_INFO_PREFIX + "1");

			req.setEmcName(con1.getContactName());
			req.setEmcRelation(con1.getContactRelation());
			req.setEmcPhone(con1.getContactMobile());
			req.setFamilyName(con1.getContactName());
			req.setFamilyRelation(con1.getContactRelation());
			req.setFamilyPhone(con1.getContactMobile());
		} else {

			throw new Exception("紧急联系人为空");

		}
		return req;
	}
	/**
	 * 保存反欺诈变量数据
	 * @param appNo
	 * @param jstr
	 */
	private void saveResponse(String appNo, String jstr) {
		try {
			TmAppNodeInfo appNodeInfo = new TmAppNodeInfo();
			appNodeInfo.setOrg(OrganizationContextHolder.getOrg());
			appNodeInfo.setAppNo(appNo);
			appNodeInfo.setInfoType(EnumsActivitiNodeType.A095.name());
			appNodeInfo.setContent(jstr);
			appNodeInfo.setOperatorId(OrganizationContextHolder.getUserNo());// 操作员ID
			appNodeInfo.setSetupDate(new Date());// 处理日期
			tmAppNodeInforecordDao.save(appNodeInfo);
		} catch (Exception e) {
			logger.error("保存信用生活返回结果出错appNo=" + appNo, e);
		}
	}
/*
	public static void main(String[] args) {
		JSONObject reqJson = new JSONObject();
		JSONObject dataJson = new JSONObject();
		reqJson.put("appNo", "2019061900001");
		dataJson.put("name", "胡利平");
		CreditLifeRequest req = new CreditLifeRequest();
		req.setName("胡利平");
		req.setIdNo("36050219821222251X");
		req.setPhone("13838384380");
		req.setProductCd("510001");
		req.setMaritalStatus("M");
		req.setHomeLocation("156");
		req.setHomeState("上海市");
		req.setHomeCity("上海市");
		req.setHomeZone("闵行区");
		req.setHomeAddr("泰虹路268弄6号201");
		req.setComMobile("021-12345678");
		req.setComName("上海锦咏数据");
		req.setComState("上海市");
		req.setComCity("上海市");
		req.setComZone("闵行区");
		req.setComAddr("泰虹路268弄6号201");
		req.setEmcName("张明");
		req.setEmcRelation("朋友");
		req.setEmcPhone("18756362321");
		req.setFamilyName("张明");
		req.setFamilyRelation("朋友");
		req.setFamilyPhone("18756362321");

		reqJson.put("data", req);

		System.out.println(reqJson.toJSONString());

		String ur = "http://10.109.3.216:8099/api/jjCaseSync/applyInfo";
		HashMap<String, String> head = new HashMap<String, String>();
		head.put("Content-type", "application/json; charset=UTF-8");

		try {
			String restr = HttpHelper.post(ur, reqJson.toJSONString(), head, 10, "UTF-8");

			System.out.println(restr);

			JSONObject data = JSONObject.parseObject(restr).getJSONObject("data");
			System.out.println(data.toJSONString());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}*/

}
