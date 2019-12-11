package com.jjb.ecms.biz.ext.push;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.acl.facility.enums.sys.MsgType;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.param.TmProductDao;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmDitDic;
import com.jjb.ecms.infrastructure.TmExtRiskInput;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.ecms.service.api.AsyncPushService;
import com.jjb.ecms.service.api.CreditSysPushService;
import com.jjb.ecms.service.dto.T9000.T9000Req;
import com.jjb.ecms.service.dto.T9000.T9000Resp;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.facility.util.StringUtils;


/**
 * 与CIS联机交易，获取客户征信报告 server
 * 获取本地保存客户征信报告
 * @author hn
 * @version 1.0
 */
@Component
public class CreditSysPushSupport {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CacheContext cacheContext;
	
	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired
	private CreditSysPushService creditSysPushService;
	@Autowired
	private AsyncPushService asyncPushService;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	TmProductDao tmProductDao;
	/**
	 * 异步调用cas-adapter前置服务推送审批结果给行内或渠道
	 * @param appNo
	 * @return
	 */
	public void asynPushApplyProgress(final String appNo, final String pushType){
		final long start1 = System.currentTimeMillis();
		TmDitDic ditDic = cacheContext.getApplyOnlineOnOff(AppDitDicConstant.onLinOff_isPushProgress);
		if (ditDic != null && StringUtils.isNotEmpty(ditDic.getRemark())
				&& ditDic.getRemark().equals(AppDitDicConstant.onLinOff_on)) {
			int taskSize = 50;//线程并发数,默认50
			// 使用线程发起工作流
			taskExecutor.setMaxPoolSize(taskSize);
			taskExecutor.execute(new Runnable() {
		        @Override
		        public void run() {
		            try {
		            	pushApplyProgress(appNo, pushType,null);
		            } catch (Exception e) {
		               logger.error("异常发起推送进件["+appNo+"]审批结论异常,"+ LogPrintUtils.printAppNoEndLog(appNo, start1, null),e);
		            }
		        }
		    });
		}else{
			logger.info("推送["+pushType+"]结果"+LogPrintUtils.printAppNoLog(appNo, start1,null)+"，未开启开关配置");
		}
	}
	
	
	/**
	 * 调用cas-adapter前置服务推送审批结果给行内或渠道
	 * @param appNo
	 * @return String RtfState
	 */
	public String pushApplyProgress(String appNo, String pushType,TmAppMain main){
		String rtfState = RtfState.H25.name();
		long start1 = System.currentTimeMillis();
		logger.info(LogPrintUtils.printAppNoLog(appNo, start1,null)+"调用审批前置推送["+pushType+"]结果开始");
		if(StringUtils.isEmpty(appNo)){
			logger.error(LogPrintUtils.printAppNoLog(appNo, start1,null)+"调用审批前置推送["+pushType+"]结果异常,申请件编号为空");
			throw new ProcessException("推送审批结果失败，申请件编号不能为空!");
		}
		try {
			String org = OrganizationContextHolder.getOrg();
			if(main==null) {
				main = applyQueryService.getTmAppMainByAppNo(appNo); 
			}
			if(main==null){
				throw new ProcessException("系统当前无此申请件["+appNo+"]");
			}
			TmExtRiskInput riskInput = applyQueryService.getTmExtRiskInputByAppNo(appNo);
			if(StringUtils.isEmpty(org)){
				org = main.getOrg();
			}
			T9000Req req = new T9000Req();
			req.setOrg(org);
			req.setTokenId(start1+"");
            req.AppNo=appNo;
			req.setAuditReason(main.getRefuseCode());
			logger.info(LogPrintUtils.printAppNoLog(appNo, start1, T9000Req.servIdCcif)
					+"推送类型["+pushType+"],当前申请件状态["+main.getRtfState()+"],终审额度["+main.getAccLmt()+"]");
			//拒绝情况
			if(StringUtils.equals(main.getRtfState(), RtfState.H17.name())
					|| StringUtils.equals(main.getRtfState(), RtfState.H25.name()) 
					|| StringUtils.equals(main.getRtfState(), RtfState.K15.name()) 
					|| StringUtils.equals(main.getRtfState(), RtfState.G15.name())
					|| StringUtils.equals(main.getRtfState(), RtfState.F09.name()) 
					|| StringUtils.equals(main.getRtfState(), RtfState.F29.name())
					|| StringUtils.equals(main.getRtfState(), RtfState.A20.name())
					|| StringUtils.equals(main.getRtfState(), RtfState.M05.name())){
				req.setJjAuditisrisk(AppConstant.ONE);//是否人工拒绝：是
				req.setFinalAuditresult(AppConstant.TWO);//系统最终决定拒绝
				req.setFinalCreditlimt("0");
				rtfState = RtfState.H25.name();
			}else if(StringUtils.equals(main.getRtfState(), RtfState.H18.name())
					|| StringUtils.equals(main.getRtfState(), RtfState.H26.name()) 
					|| StringUtils.equals(main.getRtfState(), RtfState.H21.name()) 
					|| StringUtils.equals(main.getRtfState(), RtfState.K10.name())
					|| StringUtils.equals(main.getRtfState(), RtfState.L05.name()) 
					|| StringUtils.equals(main.getRtfState(), RtfState.P05.name())){//通过
				req.setFinalAuditresult(AppConstant.ONE);//系统最终决定通过
				req.setJjAuditisrisk(AppConstant.ZERO);//是否人工拒绝：否
				if(main.getAccLmt()==null || main.getAccLmt().compareTo(new BigDecimal(0))==0) {
					throw new ProcessException("客户最终授信额度不能为空！");
				}else {
					req.setFinalCreditlimt(StringUtils.valueOf(main.getAccLmt()));
				}
				rtfState = RtfState.H26.name();
			}
			req.setPushType(StringUtils.setValue(pushType, AppConstant.ONE));//默认推送审批结论（非终审结论）
			if(riskInput!=null) {
				req.setBuscore(riskInput.getBuscore());
				req.setRejectReason(riskInput.getRejectReason());
				req.setFinalTotScore(riskInput.getFinalTotScore());
				req.setRiskLevel1(riskInput.getRisklevel1());
				req.setRiskLevel2(riskInput.getRisklevel2());
				req.setAuditisriskJj(riskInput.getAuditisriskJj());
				req.setAdjustIndex(riskInput.getAdjustindex());
				req.setFinalOtherLimit(riskInput.getFinalOtherLimit());
//				req.setBrScore(riskInput.getBrScore()); //等待领导方行就送过去，否则坚决不送
//				req.setBrWorkPlace(riskInput.getBrWorkplace());
			}
			if(StringUtils.equals(main.getAppSource(), "51")) {
				//51信用卡的网络配置-以后如果还要返回审批结果给其他行或者渠道，则在加不同的网络配置，切忌参数混用
				Map<String, String> ccifConf = cacheContext.getInterNetAddrParam(AppDitDicConstant.ext_ccif_conf);
				if(ccifConf==null || ccifConf.size()==0){
					throw new ProcessException("未查询到联机交易["+T9000Req.servIdCcif+"](推送审批结论)网络配置，调用失败！");
				}
				if(ccifConf==null || !ccifConf.containsKey(T9000Req.servIdCcif+"extHost") 
						|| !ccifConf.containsKey(T9000Req.servIdCcif+"extPort")){
					throw new ProcessException("缺失有效的联机交易["+T9000Req.servIdCcif+"](推送审批结论)网络配置，推送失败！");
				}
				req.setExtHost(ccifConf.get(T9000Req.servIdCcif+"extHost"));
				req.setExtPort(Integer.valueOf(ccifConf.get(T9000Req.servIdCcif+"extPort")));
				req.setCharset(ccifConf.get(T9000Req.servIdCcif+"charset"));
				req.setLvMsgLength(ccifConf.get(T9000Req.servIdCcif+"lvMsgLength"));
			}else {//非51渠道推送，如微信公众号
				req.setPushType(StringUtils.setValue(pushType, AppConstant.TWO));//默认推送审批结论（非终审结论）
				//TODO 待开放非51渠道的推送
			}
			OrganizationContextHolder.setOrg(org);
			T9000Resp resp = creditSysPushService.T9000(req);
			if(resp==null || resp.getReturnMsg()==null || !AppConstant.RETURN_CODE.equals(resp.getReturnCode())){
				if(resp==null){
					resp = new T9000Resp();
				}
				throw new ProcessException("接受推送审批结果的系统方反馈结果["+resp.getStatus()+"],"+resp.getReturnCode()+":"+resp.getReturnMsg()+"");
			}
		} catch (Exception e) {
			logger.error(LogPrintUtils.printAppNoEndLog(appNo, start1,null)+"调用["+pushType+"]前置推送审批结果失败!",e);
			throw new ProcessException("推送审批结果失败！错误原因["+e.getMessage()+"]");
		}finally{
			logger.info(LogPrintUtils.printAppNoEndLog(appNo, start1,null)+"调用审批前置推送审批结果完成，耗时["+(System.currentTimeMillis()-start1)+"]");
		}
		return rtfState;
	}
	/**
	 * 推送申请件信息
	 * @param appNo
	 * @param pushType
	 */
	public void asynPushApplyInfo(String appNo ,String pushTyp) {
		try {
			 if(MsgType.CreditLife.name().equals(pushTyp)) {
				 asynPushApplyInfo_CreditLife(appNo);
			 }
		} catch (Exception e) {
			logger.error(LogPrintUtils.printAppNoLog(appNo, null, "asynPushApplyInfo")+"推送异常",e);
		}
	}
	
	/**
	 * 异步推送审批结果消息给信用生活
	 * @param appNo
	 */
	private void asynPushApplyInfo_CreditLife(final String appNo) throws Exception{
		TmAppMain tmAppMain =applyQueryService.getTmAppMainByAppNo(appNo); 
		if(tmAppMain == null) {
			logger.error("申请件信息为空，推送审批结果消息失败asynPushApplyInfo，appNo="+appNo);
			return;
		}
		JSONObject json=new JSONObject();
		json.put("AppNo", tmAppMain.getAppNo());
		json.put("IdNo", tmAppMain.getIdNo());
		
		String rs="";
		switch(tmAppMain.getRtfState()) {
		        case  "P05" :{
			          rs="S";//成功申请
			          break;
			         }
		        case  "M05" :{
			          rs="F";//失败申请
			          break;
			         }
		        default :
		        	  rs="I";//审批处理中
		}
		json.put("CurrRs", rs);
		json.put("PushDate", DateUtils.dateToString(new Date(), DateUtils.DAY_YMD));
		TmProduct productParm=new TmProduct();
		productParm.setProductCd(tmAppMain.getProductCd());
		List<TmProduct> proList=tmProductDao.getProductByPram(productParm);
		String proDesc=(proList !=null && !proList.isEmpty()) ? proList.get(0).getProductDesc() : "";
		json.put("ProductDesc", proDesc);
		String msg=json.toJSONString();
		logger.info(String.format("推送审批结果消息,appNo=%s,msg=%s", tmAppMain.getAppNo(),msg));
		asyncPushService.T9002(msg);
		
	}
}