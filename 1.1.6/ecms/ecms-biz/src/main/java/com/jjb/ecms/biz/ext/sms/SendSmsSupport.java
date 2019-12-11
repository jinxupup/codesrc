package com.jjb.ecms.biz.ext.sms;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jjb.acl.facility.enums.bus.Gender;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.apply.TmAppMsgSendService;
import com.jjb.ecms.biz.service.param.SmsTemplateService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.infrastructure.TmAppAudit;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppMsgSend;
import com.jjb.ecms.infrastructure.TmDitDic;
import com.jjb.ecms.infrastructure.TmMessageTemplate;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.ecms.service.api.MessageSendService;
import com.jjb.ecms.service.dto.Trans0005.Trans0005Req;
import com.jjb.ecms.service.dto.Trans0005.Trans0005Resp;
import com.jjb.ecms.util.CodeMarkKit;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.facility.util.MapBuilder;
import com.jjb.unicorn.facility.util.StringUtils;
/**
 * 与CIS联机交易，获取客户征信报告 server
 * 获取本地保存客户征信报告
 * @author hn
 * @version 1.0
 */
@Component
public class SendSmsSupport {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private MessageSendService messageSendService;
	@Autowired
	private SmsTemplateService smsTemplateService;
	@Autowired
	private AppCommonUtil appCommonUtil;
	@Autowired
	private TmAppMsgSendService tmAppMsgSendService;
	@Autowired
	private ApplyQueryService applyQueryService;
	/**
	 * 发送短息
	 * @param params 参数
	 * @param isNeedParam 是否需要进行参数判断
	 * @return
	 */
	public Boolean   sendMessage(Map<String, Object> params,boolean isNeedParam,TmAppMsgSend tmAppMsgSend){
		long start = System.currentTimeMillis();
		String org = (String)params.get("ORG");
		String appNo = (String)params.get("APP_NO");
		try {
			appCommonUtil.setOrg(org);
			logger.info("发送短信开始"+ LogPrintUtils.printAppNoLog(appNo, start,null));
			if(isNeedParam){
				TmDitDic ditDic = cacheContext.getApplyOnlineOnOff(AppDitDicConstant.onLinOff_sendRefuseMessage);
				//开关-是否开启短信发送开关
				if (ditDic != null && StringUtils.isNotEmpty(ditDic.getRemark())
						&& ditDic.getRemark().equals(AppDitDicConstant.onLinOff_on)) {

					return sendMsg(params, start,tmAppMsgSend);
				}
				logger.info("发送短信模板到综合前置失败, 调用开关-"+(ditDic==null ? false:ditDic.getRemark())+"(未开启)"+LogPrintUtils.printAppNoEndLog(appNo, start,null));
				tmAppMsgSend.setCondition("F");
				if (StringUtils.isBlank(tmAppMsgSend.getMsgSendTimes())) {
					tmAppMsgSend.setMsgSendTimes("1");
				} else {
					int t = Integer.parseInt(tmAppMsgSend.getMsgSendTimes()) + 1;
					String s = String.valueOf(t);
					tmAppMsgSend.setMsgSendTimes(s);
				}
				tmAppMsgSend.setRemark("发送短信模板到综合前置失败");
				tmAppMsgSendService.updateTmAppMsgSend(tmAppMsgSend);
			}else{
				return sendMsg(params, start,tmAppMsgSend);
			}
		} catch (Exception e) {
			logger.error("调用综合前置失败,"+LogPrintUtils.printAppNoEndLog(appNo, start,null), e);
			tmAppMsgSend.setCondition("F");
			if (StringUtils.isBlank(tmAppMsgSend.getMsgSendTimes())) {
				tmAppMsgSend.setMsgSendTimes("1");
			} else {
				int t = Integer.parseInt(tmAppMsgSend.getMsgSendTimes()) + 1;
				String s = String.valueOf(t);
				tmAppMsgSend.setMsgSendTimes(s);
			}
			tmAppMsgSend.setRemark("调用综合前置失败");
			tmAppMsgSendService.updateTmAppMsgSend(tmAppMsgSend);
		}
		return false;
	}
	/**
	 * 发送短信
	 * @param params
	 * @return
	 */
	private Boolean sendMsg(Map<String, Object> params,long start,TmAppMsgSend tmAppMsgSend){
		Long tokenId = System.currentTimeMillis();
		Date txnTime = new Date();
		String appNo = (String)params.get(AppConstant.app_no);
		String name = (String)params.get(AppConstant.name);
		String cellPhone = (String)params.get(AppConstant.cellPhone);
		String msgCd = (String)params.get(AppConstant.productCd);
		String smsType = (String)params.get(AppConstant.sms_type);
		String logs = setLogMsg(null, name, cellPhone, msgCd, txnTime);
		Trans0005Resp resp =null;
		appCommonUtil.setOrg(OrganizationContextHolder.getOrg());

		logger.info("短信开始发送综合前置-"+LogPrintUtils.printAppNoLog(appNo, start,null)+logs);
		if(StringUtils.isEmpty(msgCd)){
			logger.info("短信发送失败,未获取到短信模版["+smsType+"]"+LogPrintUtils.printAppNoEndLog(appNo, start,null));
			tmAppMsgSend.setCondition("F");
			if (StringUtils.isBlank(tmAppMsgSend.getMsgSendTimes())) {
				tmAppMsgSend.setMsgSendTimes("1");
			} else {
				int t = Integer.parseInt(tmAppMsgSend.getMsgSendTimes()) + 1;
				String s = String.valueOf(t);
				tmAppMsgSend.setMsgSendTimes(s);
			}
			tmAppMsgSend.setRemark("未获取到短信模版");
			tmAppMsgSendService.updateTmAppMsgSend(tmAppMsgSend);
			return false;
		}

		String ContentTemplate=(String)params.get(AppConstant.msgContent);
		//如果短信内容为空，则配置短信内容
		if(StringUtils.isBlank(ContentTemplate)){
			TmMessageTemplate tmMessageTemplate = new TmMessageTemplate();
			tmMessageTemplate.setTeCode(smsType);
			tmMessageTemplate = smsTemplateService.queryByCode(tmMessageTemplate);
			if(StringUtils.isNotEmpty(tmMessageTemplate)){
				ContentTemplate =  AppCommonUtil.processTemplate(tmMessageTemplate.getContentTemplate(),params);
				tmAppMsgSend.setMsgContent(ContentTemplate);
				tmAppMsgSendService.updateTmAppMsgSend(tmAppMsgSend);

			}
		}
		try {
			//空短信判断
			if(StringUtils.isNotEmpty(ContentTemplate)) {
				Trans0005Req req = setT0005Request(tokenId, appNo, OrganizationContextHolder.getOrg());
				req.setTel(cellPhone);
				req.setSndCntnt(ContentTemplate);
				tmAppMsgSend.setCondition("I");
				tmAppMsgSendService.updateTmAppMsgSend(tmAppMsgSend);
				resp = messageSendService.Trans0005(req);
			}else{
				if (StringUtils.isBlank(tmAppMsgSend.getMsgSendTimes())) {
					tmAppMsgSend.setMsgSendTimes("1");
				} else {
					int t = Integer.parseInt(tmAppMsgSend.getMsgSendTimes()) + 1;
					String s = String.valueOf(t);
					tmAppMsgSend.setMsgSendTimes(s);
				}
				tmAppMsgSend.setCondition("F");
				tmAppMsgSend.setRemark("调用接口失败");
				tmAppMsgSendService.updateTmAppMsgSend(tmAppMsgSend);
				return false;
			}
		} catch (Exception e) {
			logger.error("申请件编号["+appNo+"]发送短信失败");
			if (StringUtils.isBlank(tmAppMsgSend.getMsgSendTimes())) {
				tmAppMsgSend.setMsgSendTimes("1");
			} else {
				int t = Integer.parseInt(tmAppMsgSend.getMsgSendTimes()) + 1;
				String s = String.valueOf(t);
				tmAppMsgSend.setMsgSendTimes(s);
			}
			tmAppMsgSend.setCondition("F");
			tmAppMsgSend.setRemark("调用接口失败");
			tmAppMsgSendService.updateTmAppMsgSend(tmAppMsgSend);
		}
		if(resp==null){
			logger.error("PID-["+tokenId+"]...申请件["+appNo+"]发送短信失败，核心返回数据为空！");
			if (StringUtils.isBlank(tmAppMsgSend.getMsgSendTimes())) {
				tmAppMsgSend.setMsgSendTimes("1");
			} else {
				int t = Integer.parseInt(tmAppMsgSend.getMsgSendTimes()) + 1;
				String s = String.valueOf(t);
				tmAppMsgSend.setMsgSendTimes(s);
			}
			tmAppMsgSend.setCondition("F");
			tmAppMsgSend.setRemark("调用接口成功，处理失败");
			tmAppMsgSendService.updateTmAppMsgSend(tmAppMsgSend);
			throw new ProcessException("发送短信异常，综合前置数据为空！");
		}if(resp!=null&&(!AppConstant.RETURN_CODE.equals(resp.getReturnCode()))){
			logger.error("PID-["+tokenId+"]...申请件["+appNo+"]发送短信失败，核心返回数据为空！");
			if (StringUtils.isBlank(tmAppMsgSend.getMsgSendTimes())) {
				tmAppMsgSend.setMsgSendTimes("1");
			} else {
				int t = Integer.parseInt(tmAppMsgSend.getMsgSendTimes()) + 1;
				String s = String.valueOf(t);
				tmAppMsgSend.setMsgSendTimes(s);
			}
			tmAppMsgSend.setCondition("F");
			tmAppMsgSend.setRemark("调用接口成功，处理失败");
			tmAppMsgSendService.updateTmAppMsgSend(tmAppMsgSend);
			throw new ProcessException("PID-["+tokenId+"]...申请件["+appNo+"]发送短信失败，综合前置返回交易信息是["+resp.getReturnMsg()+"]");
		}
		logger.info("短信发送完成"+LogPrintUtils.printAppNoEndLog(appNo, start,null));
		if (StringUtils.isBlank(tmAppMsgSend.getMsgSendTimes())) {
			tmAppMsgSend.setMsgSendTimes("1");
		} else {
			int t = Integer.parseInt(tmAppMsgSend.getMsgSendTimes()) + 1;
			String s = String.valueOf(t);
			tmAppMsgSend.setMsgSendTimes(s);
		}
		tmAppMsgSend.setCondition("S");
		tmAppMsgSend.setRemark(null);
		tmAppMsgSendService.updateTmAppMsgSend(tmAppMsgSend);
		return true;

	}
	/**
	 * 日志打印
	 */
	private String setLogMsg(String cardNo, String name, String cellPhone,
			String msgCd, Date txnTime) {
		StringBuffer sb = new StringBuffer();
		sb.append(",[");
		sb.append("短信模板号:");
		sb.append(msgCd);
		sb.append("],");
		sb.append(",[");
		sb.append("客户姓名:");
		sb.append(name);
		sb.append("],");
		sb.append(",[");
		sb.append("手机:");
		sb.append(CodeMarkKit.markMobile(cellPhone));
		sb.append("],");
		if(StringUtils.isNotEmpty(cardNo)){
			sb.append(",[");
			sb.append("卡号:");
			sb.append(CodeMarkKit.makeCardMask(cardNo));
			sb.append("],");
		}
		sb.append("交易时间:");
		sb.append(DateUtils.dateToString(txnTime, DateUtils.FULL_YMDM_LINE));
		sb.append("],");
		return sb.toString();
	}
	/**
	 * 设置联机请求
	 * @param tonken
	 * @param tmAppMain
	 * @return
	 */
	private Trans0005Req setT0005Request(Long tonken, String appNo,String org) {
		//开关-是否开启调用综合前置系统
		Map<String, String> ccifConf = cacheContext.getInterNetAddrParam(AppDitDicConstant.ext_ccif_conf);
		if(ccifConf==null || ccifConf.size()==0){
			throw new ProcessException("未查询到联机交易["+Trans0005Req.servId+"]网络配置，调用失败！");
		}
		if(ccifConf==null || !ccifConf.containsKey(Trans0005Req.servId+"extHost") 
				|| !ccifConf.containsKey(Trans0005Req.servId+"extPort")){
			throw new ProcessException("缺失有效的联机交易["+Trans0005Req.servId+"]网络配置，推送失败！");
		}
		Trans0005Req req = new Trans0005Req();
		req.setTokenId(String.valueOf(tonken));
		req.setOrg(org);
		req.setAppNo(appNo);
		req.setExtHost(ccifConf.get(Trans0005Req.servId+"extHost"));
		String port = ccifConf.get(Trans0005Req.servId+"extPort");
		req.setExtPort(StringUtils.stringToIntegerNotNull(port));
		req.setCharset(ccifConf.get(Trans0005Req.servId+"charset"));
		req.setLvMsgLength(ccifConf.get(Trans0005Req.servId+"lvMsgLength"));
		req.setConnectTimeOut(ccifConf.get(Trans0005Req.servId+"connectTimeOut"));
		req.setSourceSysId(ccifConf.get(Trans0005Req.servId+"sourceSysId"));
		req.setConsumerId(ccifConf.get(Trans0005Req.servId+"consumerId"));
		req.setServiceCode(ccifConf.get(Trans0005Req.servId+"serviceCode"));
		req.setServiceScene(ccifConf.get(Trans0005Req.servId+"serviceScene"));
		return req;
	}
	
	public boolean setSmsToDB(TmAppMain tmAppMain,String smsType) {
		String appNo="";
		try {
			String productCd = tmAppMain.getProductCd();// 获取卡产品代码
			appNo = tmAppMain.getAppNo();
			String org = tmAppMain.getOrg();
			String description = "";
			TmProduct product = cacheContext.getProduct(productCd);
			if (product != null) {
				description = product.getProductDesc();
			} else {
				logger.info("未获取到卡产品代码[" + productCd + "]对应的卡产品！");
			}
			Map<String, Object> params = new MapBuilder<String, Object>().add("productCd", productCd)
					.add("description", description).build();
			String name = tmAppMain.getName();
			String idNo = tmAppMain.getIdNo();
			String cellPhone = tmAppMain.getCellphone();
			Gender gender = AppCommonUtil.getGender(appNo, null, idNo);

			params.put(AppConstant.name, name);
			params.put(AppConstant.gender, gender);
			params.put(AppConstant.cellPhone, cellPhone);
			params.put(AppConstant.sms_type, smsType);
			params.put(AppConstant.app_no, appNo);
			params.put(AppConstant.org, org);

			TmMessageTemplate tmMessageTemplate = new TmMessageTemplate();
			tmMessageTemplate.setTeCode(smsType);
			tmMessageTemplate = smsTemplateService.queryByCode(tmMessageTemplate);
			String msgContent = null;
			if (StringUtils.isNotEmpty(tmMessageTemplate)) {
				msgContent = AppCommonUtil.processTemplate(tmMessageTemplate.getContentTemplate(), params);
			}

			TmAppAudit tmAppAudit = applyQueryService.getTmAppAuditByAppNo(appNo);

			// 根据人工节点或者自动节点判断是否发送拒绝短信
			// if(!StringUtils.equals(tmAppMain.getRtfState(),RtfState.H17.name())) {
			// 人工节点---先根据数据库中信息判断是否发送拒绝短信，若数据库中无对应信息，则按照参数来判断
			if (tmAppAudit == null || StringUtils.isBlank(tmAppAudit.getIsSendSmsRefused())
					|| StringUtils.equals(tmAppAudit.getIsSendSmsRefused(), "Y")) {
//					sendSmsSupport.sendMessage(params, false);
				TmAppMsgSend msg = new TmAppMsgSend();
				msg.setAppNo(appNo);
				msg.setMsgType(smsType);
				msg.setIdNo(idNo);
				msg.setName(name);
				msg.setCellPhone(cellPhone);
				msg.setCondition("W");
				msg.setOrg(org);
				msg.setMsgContent(msgContent);
				tmAppMsgSendService.saveTmAppMsgSend(msg);
			}
			return true;
		} catch (Exception e) {
			logger.error(LogPrintUtils.printAppNoLog(appNo, "setSmsToDB", "")+"插入定时短信发送数据入库", e);
		}
		return false;
	}

}

