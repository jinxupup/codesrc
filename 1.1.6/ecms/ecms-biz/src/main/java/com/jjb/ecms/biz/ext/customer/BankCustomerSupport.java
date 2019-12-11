package com.jjb.ecms.biz.ext.customer;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.infrastructure.TmAppCustInfo;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.ecms.service.api.ExtSystemService;
import com.jjb.ecms.service.dto.Trans90020.Trans90020Req;
import com.jjb.ecms.service.dto.Trans90020.Trans90020Resp;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.facility.util.StringUtils;

@Component
public class BankCustomerSupport {
	
	private Logger logger = LoggerFactory.getLogger(BankCustomerSupport.class);
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private ExtSystemService extSystemService;
	
	/**
	 * 获取行内客户号
	 * @return
	 */
	public String getCustomerNumber(TmAppMain main,TmAppCustInfo cust,TmAppPrimCardInfo cardInfo) {
		Long tonken = System.currentTimeMillis();
		String appNo = "";
		String custId = "";
		try {
			if (main == null) {
				logger.error("获取行内客户号失败，申请件主表信息为空");
				throw new ProcessException("获取行内客户号失败,申请件信息无效");
			}
			appNo = main.getAppNo();
			if (cust == null) {
				logger.error(LogPrintUtils.printAppNoLog(appNo, tonken, Trans90020Req.servId)+"获取行内客户号失败，客户表信息为空");
				throw new ProcessException("获取行内客户号失败,客户信息为空");
			}
			
			Trans90020Req req = setTrans90020Request(tonken, main,cust,cardInfo);
			logger.debug(LogPrintUtils.printAppNoLog(appNo, tonken, Trans90020Req.servId)+"获取行内客户号请求报文:" + req.getReqXml());
			logger.info(LogPrintUtils.printAppNoLog(appNo, tonken, Trans90020Req.servId)+"获取行内客户号请求报文:" 
					+ (req.getReqXml()==null ? 0:req.getReqXml().length()));
			Trans90020Resp resp = extSystemService.s90020(req);
			logger.info(LogPrintUtils.printAppNoLog(appNo, tonken, Trans90020Req.servId)+"获取行内客户号响应:" + resp.toString());
			if(resp!=null) {
				custId = resp.getBankCustomerId();
			}
		} catch (Exception e) {
			logger.error("获取行内客户号发生错误" + appNo, e);
			throw new ProcessException("获取行内客户号异常:"+e.getMessage());
		}
		return custId;
	}
	/**
	 * 设置联机请求
	 * @param tonken
	 * @param tmAppMain
	 * @return
	 */
	private Trans90020Req setTrans90020Request(Long tonken, TmAppMain tmAppMain,TmAppCustInfo custInfo,TmAppPrimCardInfo cardInfo) {
		//开关-是否可以调用综合前置，为空也是不开启
		Map<String, String> ccifConf = cacheContext.getInterNetAddrParam(AppDitDicConstant.ext_ccfrontend_conf);
		if(ccifConf==null || ccifConf.size()==0){
			throw new ProcessException("未查询到联机交易["+Trans90020Req.servId+"]网络配置，调用失败！");
		}
		if(ccifConf==null || !ccifConf.containsKey(Trans90020Req.servId+"extHost") 
				|| !ccifConf.containsKey(Trans90020Req.servId+"extPort")){
			throw new ProcessException("缺失有效的联机交易["+Trans90020Req.servId+"]网络配置，推送失败！");
		}
		Trans90020Req req = new Trans90020Req();
		req.setTokenId(String.valueOf(tonken));
		req.setOrg(tmAppMain.getOrg());
		req.setAppNo(tmAppMain.getAppNo());
		req.setExtHost(ccifConf.get(Trans90020Req.servId+"extHost"));
		String port = ccifConf.get(Trans90020Req.servId+"extPort");
		req.setExtPort(StringUtils.stringToIntegerNotNull(port));
		req.setCharset(ccifConf.get(Trans90020Req.servId+"charset"));
		req.setLvMsgLength(ccifConf.get(Trans90020Req.servId+"lvMsgLength"));
		req.setConnectTimeOut(ccifConf.get(Trans90020Req.servId+"connectTimeOut"));
//		req.setSourceSysId(ccifConf.get(Trans90020Req.servId+"sourceSysId"));
//		req.setConsumerId(ccifConf.get(Trans90020Req.servId+"consumerId"));
//		req.setServiceCode(ccifConf.get(Trans90020Req.servId+"serviceCode"));
//		req.setServiceScene(ccifConf.get(Trans90020Req.servId+"serviceScene"));
//		req.setChannelId(ccifConf.get(Trans90020Req.servId+"channelId"));
		String xml = createRequstXml(tmAppMain, custInfo,cardInfo,ccifConf);
		req.setReqXml(xml);
		return req;
	}
	
	private String createRequstXml(TmAppMain main,TmAppCustInfo custInfo,TmAppPrimCardInfo cardInfo, Map<String, String> ccifConf) {
		
//		req.setSourceSysId(ccifConf.get(Trans90020Req.servId+"sourceSysId"));
//		req.setConsumerId(ccifConf.get(Trans90020Req.servId+"consumerId"));
//		req.setServiceCode(ccifConf.get(Trans90020Req.servId+"serviceCode"));
//		req.setServiceScene(ccifConf.get(Trans90020Req.servId+"serviceScene"));
//		req.setChannelId(ccifConf.get(Trans90020Req.servId+"channelId"));
		String serviceId = ccifConf.get(Trans90020Req.servId+"serviceCode");
		String channelId = ccifConf.get(Trans90020Req.servId+"channelId");
		if (cardInfo == null) {
			cardInfo = new TmAppPrimCardInfo();
		}
		StringBuilder sb=new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
		  .append("<SERVICE>")
		  .append("<SERVICE_HEADER>")
		  .append(String.format("<SERVICE_SN>%s</SERVICE_SN>", StringUtils.valueOf(System.currentTimeMillis())))
		  .append(String.format("<SERVICE_ID>%s</SERVICE_ID>", StringUtils.setValue(serviceId,"90020")))
		  .append(String.format("<ORG>%s</ORG>", StringUtils.setValue(custInfo.getOrg(),"000064540000")))
		  .append(String.format("<CHANNEL_ID>%s</CHANNEL_ID>", StringUtils.setValue(channelId, "98")))
		  .append(String.format("<OP_ID>%s</OP_ID>", ""))
		  .append(String.format("<REQUST_TIME>%s</REQUST_TIME>",DateUtils.dateToString(new Date(), DateUtils.FULL_SECOND_LINE_NO)))
		  .append(String.format("<VERSION_ID>%s</VERSION_ID>", "01"))
		  .append(String.format("<MAC>%s</MAC>", ""))
		  .append("</SERVICE_HEADER>")
		  .append("<SERVICE_BODY>")
		  .append("<EXT_ATTRIBUTES/>")
		  .append("<CustomerInfo>")
		  .append(String.format("<CUST_NAME>%s</CUST_NAME>", StringUtils.valueOf(custInfo.getName())))
		  .append(String.format("<ID_TYPE>%s</ID_TYPE>", StringUtils.setValue(custInfo.getIdType(),"I")))
		  .append(String.format("<ID_NO>%s</ID_NO>", StringUtils.valueOf(custInfo.getIdNo())))
		  .append(String.format("<ID_ISSUER_ADDRESS>%s</ID_ISSUER_ADDRESS>", StringUtils.valueOf("")))
		  .append(String.format("<LIVE_AREAR_NO>%s</LIVE_AREAR_NO>", StringUtils.valueOf("")));
		  //证件是否长期有效
		  if(StringUtils.equals(custInfo.getIdLastAll(), "Y")) {
			try {
				custInfo.setIdLastDate(DateUtils.stringToDate("99991231", DateUtils.DAY_YMD));
			} catch (ParseException e) {
			}
		  }
		  sb.append(String.format("<ID_LAST_DATE>%s</ID_LAST_DATE>", StringUtils.valueOf(DateUtils.dateToString(custInfo.getIdLastDate(), DateUtils.DAY_YMD))))
		  .append(String.format("<GENDER>%s</GENDER>", StringUtils.valueOf(custInfo.getGender())))
		  .append(String.format("<BIRTHDAY>%s</BIRTHDAY>", StringUtils.valueOf(DateUtils.dateToString(custInfo.getBirthday(), DateUtils.DAY_YMD))))
		  .append(String.format("<MARITAL_STATUS>%s</MARITAL_STATUS>", StringUtils.valueOf(custInfo.getMaritalStatus())))
		  .append(String.format("<QUALIFICATION>%s</QUALIFICATION>", StringUtils.valueOf(custInfo.getQualification())))
		  .append(String.format("<LIQUID_ASSET>%s</LIQUID_ASSET>", StringUtils.valueOf(custInfo.getLiquidAsset())))
		  .append(String.format("<HOUSE_OWNERSHIP>%s</HOUSE_OWNERSHIP>", StringUtils.valueOf(custInfo.getHouseOwnership())))
		  .append(String.format("<HOUSE_TYPE>%s</HOUSE_TYPE>", ""))
		  .append(String.format("<MOBILE_PHONE>%s</MOBILE_PHONE>", StringUtils.valueOf(custInfo.getCellphone())))
		  .append(String.format("<EMAIL>%s</EMAIL>", StringUtils.valueOf(custInfo.getEmail())))
		                .append("<PR_OF_COUNTRY>Y</PR_OF_COUNTRY>")
		  .append(String.format("<RESIDENCY_COUNTRY_CD>%s</RESIDENCY_COUNTRY_CD>", StringUtils.setValue(custInfo.getNationality(),"156")))
		  .append(String.format("<NATIONALITY>%s</NATIONALITY>", StringUtils.setValue(custInfo.getNationality(),"156")))
		                .append("<HOME_STAND_FROM></HOME_STAND_FROM>")
		  .append(String.format("<CLIENT_TYPE>%s</CLIENT_TYPE>", StringUtils.setValue(custInfo.getCustType(),"A00")))
		  .append(String.format("<LOGO_NAME>%s</LOGO_NAME>", StringUtils.valueOf(custInfo.getEmbLogo())))
		  .append(String.format("<EMP_STRUCTURE>%s</EMP_STRUCTURE>", StringUtils.setValue(custInfo.getEmpStructure(),"Z")))
		  .append(String.format("<EMP_TYPE>%s</EMP_TYPE>", StringUtils.setValue(custInfo.getEmpType(),"Z")))
		                .append("<EMP_STAND_FROM></EMP_STAND_FROM>")
		  .append(String.format("<OCCUPATION>%s</OCCUPATION>", StringUtils.setValue(custInfo.getOccupation(),"H")))
		                .append("<OCCUPATION_O></OCCUPATION_O>")
		  .append(String.format("<CORP_POST>%s</CORP_POST>", StringUtils.valueOf(custInfo.getEmpPost())))
		  .append(String.format("<TITLE_OF_TECHNICAL>%s</TITLE_OF_TECHNICAL>", StringUtils.valueOf(custInfo.getTitleOfTechnical())))
		  .append(String.format("<IF_BANKMEMBER>%s</IF_BANKMEMBER>", StringUtils.setValue(custInfo.getBankmemFlag(),"N")))
		  .append(String.format("<BANKMEMBER_NO>%s</BANKMEMBER_NO>", StringUtils.valueOf(custInfo.getBankmemberNo())))
		  .append(String.format("<SPREADER_TYPE>%s</SPREADER_TYPE>",StringUtils.setValue(cardInfo.getSpreaderType(),"B")))
		  .append(String.format("<SPREADER_NUM>%s</SPREADER_NUM>", StringUtils.valueOf(cardInfo.getSpreaderNo())))
		  .append(String.format("<SPREADER_NAME>%s</SPREADER_NAME>", StringUtils.valueOf(cardInfo.getSpreaderName())))
		  .append(String.format("<SPREADER_PHONE>%s</SPREADER_PHONE>", StringUtils.valueOf(cardInfo.getSpreaderTelephone())))
		  .append(String.format("<OWNING_BRANCH>%s</OWNING_BRANCH>", StringUtils.setValue(main.getOwningBranch(),"08101")))
		  .append(String.format("<EMP_STABILITY>%s</EMP_STABILITY>", "Y"))
		  .append(String.format("<EMP_STATUS>%s</EMP_STATUS>", ""))
		  .append("<CONTACTINFOS>");
		 
			sb.append("<CONTACTINFO>")  
			   .append(String.format("<ADDR_TYPE>%s</ADDR_TYPE>", "C")) 
			   .append(String.format("<COUNTRY_CD>%s</COUNTRY_CD>", StringUtils.setValue(custInfo.getEmpAddrCtryCd(),"156"))) 
			   .append(String.format("<STATE>%s</STATE>", StringUtils.valueOf(custInfo.getEmpProvince()))) 
			   .append(String.format("<CITY>%s</CITY>", StringUtils.valueOf(custInfo.getEmpCity()))) 
			   .append(String.format("<ZONE>%s</ZONE>", StringUtils.valueOf(custInfo.getEmpZone()))) 
			   .append(String.format("<POSTCODE>%s</POSTCODE>", StringUtils.valueOf(custInfo.getEmpPostcode()))) 
			   .append(String.format("<ADDRESS>%s</ADDRESS>", StringUtils.valueOf(custInfo.getEmpAdd()))) 
			   .append(String.format("<CONTACT_PHONE>%s</CONTACT_PHONE>", StringUtils.valueOf(custInfo.getEmpPhone()))) 
			   .append("</CONTACTINFO>") 
			   ;
			
			sb.append("<CONTACTINFO>")  
			   .append(String.format("<ADDR_TYPE>%s</ADDR_TYPE>", "H")) 
			   .append(String.format("<COUNTRY_CD>%s</COUNTRY_CD>", StringUtils.setValue(custInfo.getHomeAddrCtryCd(),"156"))) 
			   .append(String.format("<STATE>%s</STATE>", StringUtils.valueOf(custInfo.getHomeState()))) 
			   .append(String.format("<CITY>%s</CITY>", StringUtils.valueOf(custInfo.getHomeCity()))) 
			   .append(String.format("<ZONE>%s</ZONE>", StringUtils.valueOf(custInfo.getHomeZone()))) 
			   .append(String.format("<POSTCODE>%s</POSTCODE>", StringUtils.valueOf(custInfo.getHomePostcode()))) 
			   .append(String.format("<ADDRESS>%s</ADDRESS>", StringUtils.valueOf(custInfo.getHomeAdd()))) 
			   .append(String.format("<CONTACT_PHONE>%s</CONTACT_PHONE>", StringUtils.valueOf(custInfo.getHomePhone()))) 
			   .append("</CONTACTINFO>") 
			   ;
		 
		  sb.append("</CONTACTINFOS>")
		    .append("</CustomerInfo>")
		    .append("</SERVICE_BODY>")
		    .append("</SERVICE>");
		    
		
		return sb.toString();
	}

}
