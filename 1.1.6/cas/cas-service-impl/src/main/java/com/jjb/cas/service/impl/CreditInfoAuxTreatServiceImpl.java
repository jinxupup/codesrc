package com.jjb.cas.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.service.manage.TmRiskListService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.infrastructure.TmDitDic;
import com.jjb.ecms.infrastructure.TmRiskList;
import com.jjb.ecms.service.api.CreditInfoAuxTreatService;
import com.jjb.ecms.service.dto.Trans0139.Trans0139Req;
import com.jjb.ecms.service.dto.Trans0139.Trans0139Resp;
import com.jjb.ecms.service.dto.Trans0140.Trans0140Req;
import com.jjb.ecms.service.dto.Trans0140.Trans0140Resp;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.CodeMarkUtils;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;


/**
 * @Description: 征信信息辅助处理服务</br>
 * 主要用于外部其他系统查询系统已使用征信数据</br>
 * 场景一：法院被执行人征信信息查询跳转，外部系统请求前置，前置请求审批前置，审批前置请求csms查询第三方外部征信数据返回
 * 场景二：行内资产信息查询，外部系统请求前置，前置请求审批前置，审批前置再调用审批主服务或者大数据平台获取相关信息
 * @author hejn
 * @date 2019年11月02日 下午13:27:35
 * @version V1.0
 */
@Service("creditInfoAuxTreatServiceImpl")
public class CreditInfoAuxTreatServiceImpl implements CreditInfoAuxTreatService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private AppCommonUtil appCommonUtil;
	@Autowired
	private TmRiskListService tmRiskListService;
	@Autowired
	private CacheContext cacheContext;
	/**
	 * 04003000031-行内征信信息查询</br>
	 * 1.法院执行人查询
	 * @param Trans0139Req
	 * @return Trans0139Resp
	 * @throws ProcessException
	 */
	@Override
	public Trans0139Resp Trans0139(Trans0139Req req) throws ProcessException {

//		Trans0139Resp resp1 = new Trans0139Resp();
//		Trans0139Resp resp2 = new Trans0139Resp();
//		resp1.setJexExecut1Datetypeid103("2");//执行人
//		resp1.setJexExecut1StatuteClosed("0");//未结案
//		resp2.setJexExecut1Datetypeid103("2");//执行人
//		resp2.setJexExecut1StatuteClosed("1");//已结案
//		Map<String, Trans0139Resp> result = new HashMap<String, Trans0139Resp>();
//		result.put("421301193810208117",resp1);
//		result.put("433127199006070122",resp1);
//		result.put("530425195208207998",resp2);
//		result.put("230710194802044905",resp2);

		checkTrans0139Req(req);
		String appNo = req.getAppNo();
		String tokenId = StringUtils.setValue(req.getTokenId(), System.currentTimeMillis());
		Trans0139Resp resp = new Trans0139Resp();
		if(StringUtils.isBlank(resp.getJexExecut1Datetypeid103())){
			resp.setJexExecut1Datetypeid103("0");//默认非执行人
			resp.setJexExecut1StatuteClosed("");//默认为空
		}
		logger.info(LogPrintUtils.printAppNoLog(appNo, tokenId, Trans0139Req.servIdSimple)
				+"证件号码["+appCommonUtil.makeIDCardMask(req.getIdNo())
				+"],客户姓名["+req.getName()+"],客户手机["+CodeMarkUtils.markMobile(req.getCellPhone())+"]" );
		try {
			TmDitDic dic = cacheContext.getApplyOnlineOnOff(AppDitDicConstant.onLinOff_trans0139Test);
			//是否开启测试模式
			if(dic!=null && StringUtils.equals(dic.getRemark(), "Y")) {
				TmRiskList tmRiskList = new TmRiskList();
				tmRiskList.setIdNo(req.getIdNo());
				tmRiskList.setName(req.getName());
//				tmRiskList.setCellPhone(req.getCellPhone());
				List<TmRiskList> riskList = tmRiskListService.getTmRiskList(tmRiskList);
				if(CollectionUtils.sizeGtZero(riskList)) {
					TmRiskList rlDb = riskList.get(0);
					if(rlDb!=null) {
						if(StringUtils.equals(rlDb.getHomeAdd(), "0") || StringUtils.equals(rlDb.getHomeAdd(), "2")) {
							resp.setJexExecut1Datetypeid103(rlDb.getHomeAdd());//是否被执行人
						}
						if(StringUtils.equals(rlDb.getEmpAdd(), "0") || StringUtils.equals(rlDb.getEmpAdd(), "1")) {
							resp.setJexExecut1StatuteClosed(rlDb.getEmpAdd());//是否已结案
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(LogPrintUtils.printAppNoLog(appNo, tokenId, Trans0139Req.servIdSimple)+"处理异常",e);
			throw new ProcessException("行内征信信息查询异常，请重试!");
		}finally {
			logger.info(LogPrintUtils.printAppNoEndLog(appNo, tokenId, Trans0139Req.servIdSimple));
		}
		return resp;
	}
	/**
	 * @Author hejn
	 * @Description Trans0139Req参数校验
	 * @Date 2019/11/01 17:21
	 */
	private void checkTrans0139Req(Trans0139Req req) {
		if (req == null) {
			throw new ProcessException(Trans0139Req.servId+"-请求参数不能为空");
		}
//		if (StringUtils.isEmpty(req.getAppNo())) {
//			throw new ProcessException(Trans0139Req.servId+"-请求参数-[业务流水号]不能为空");
//		}
		if (StringUtils.isEmpty(req.getName())) {
			throw new ProcessException(Trans0139Req.servId+"-请求参数-[客户姓名]不能为空");
		}
		if (StringUtils.isEmpty(req.getIdNo())) {
			throw new ProcessException(Trans0139Req.servId+"-请求参数-[证件号码]不能为空");
		}
	}

	/**
	 * 04003000031账户资产信息查询
	 * @param Trans0140Req
	 * @return Trans0140Resp
	 * @throws ProcessException
	 */
	@Override
	public Trans0140Resp Trans0140(Trans0140Req req) throws ProcessException {
		checkTrans0140Req(req);
		String appNo = req.getAppNo();
		String tokenId = StringUtils.setValue(req.getTokenId(), System.currentTimeMillis());
		Trans0140Resp resp = new Trans0140Resp();
		resp.setBankInterviewFlag("1");//默认1：需要面签
		logger.info(LogPrintUtils.printAppNoLog(appNo, tokenId, Trans0140Req.servIdSimple)
				+"证件号码["+appCommonUtil.makeIDCardMask(req.getIdNo())
				+"],客户姓名["+req.getName()+"],客户手机["+CodeMarkUtils.markMobile(req.getCellPhone())+"]" );
		try {
			TmDitDic dic = cacheContext.getApplyOnlineOnOff(AppDitDicConstant.onLinOff_trans0140Test);
			//是否开启测试模式
			if(dic!=null && StringUtils.equals(dic.getRemark(), "Y")) {
				TmRiskList tmRiskList = new TmRiskList();
				tmRiskList.setIdNo(req.getIdNo());
				tmRiskList.setName(req.getName());
//				tmRiskList.setCellPhone(req.getCellPhone());
				List<TmRiskList> riskList = tmRiskListService.getTmRiskList(tmRiskList);
				if(CollectionUtils.sizeGtZero(riskList)) {
					TmRiskList rlDb = riskList.get(0);
					if(rlDb!=null && (StringUtils.equals(rlDb.getMemo(), "0")
							|| StringUtils.equals(rlDb.getMemo(), "1"))) {
						resp.setBankInterviewFlag(rlDb.getMemo());
//						白名单现在不用了
//						Date start = DateUtils.getDateStart(new Date());
//						Date end = DateUtils.getDateStart(rlDb.getValidDate());
//						long dateExpCnt = DateUtils.subtract(start, end);
//						resp.setBankWhitelistLmt(null);//白名单额度，暂时还未设置该值
//						if(dateExpCnt>=0 && rlDb.getValidDate()!=null) {//设置了有效期，且有效期处于有效范围内
//							resp.setBankWhitelistFlag("1");//是白名单
//							resp.setBankWhitelistExp(StringUtils.valueOf(dateExpCnt));//在有效期内,以及剩余有效天数
//						}else if(rlDb.getValidDate()!=null) {//未设置有效期，则长期有效
//							resp.setBankWhitelistFlag(null);//是白名单
//							resp.setBankWhitelistExp("超过期限");//在有效期内,以及剩余有效天数
//						}else if(rlDb.getValidDate()==null) {//未设置有效期，则长期有效
//							resp.setBankWhitelistFlag("1");
//						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(LogPrintUtils.printAppNoLog(appNo, tokenId, Trans0140Req.servIdSimple)+"处理异常",e);
			throw new ProcessException("账户资产信息查询异常，请重试!");
		}finally {
			logger.info(LogPrintUtils.printAppNoEndLog(appNo, tokenId, Trans0140Req.servIdSimple));
		}
		return resp;
	}
	/**
	 * @Author hejn
	 * @Description Trans0140Req参数校验
	 * @Date 2019/11/01 17:21
	 */
	private void checkTrans0140Req(Trans0140Req req) {
		if (req == null) {
			throw new ProcessException(Trans0140Req.servId+"-请求参数不能为空");
		}
		if (StringUtils.isEmpty(req.getName())) {
			throw new ProcessException(Trans0140Req.servId+"-请求参数-[客户姓名]不能为空");
		}
		if (StringUtils.isEmpty(req.getIdNo())) {
			throw new ProcessException(Trans0140Req.servId+"-请求参数-[证件号码]不能为空");
		}
	}

}

