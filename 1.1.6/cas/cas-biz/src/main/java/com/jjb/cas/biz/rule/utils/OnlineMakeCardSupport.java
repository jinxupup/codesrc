package com.jjb.cas.biz.rule.utils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.facility.enums.bus.AppType;
import com.jjb.acl.facility.enums.bus.ApplyFileItem;
import com.jjb.acl.facility.enums.bus.BscSuppIndicator;
import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.apply.TmMirCardDao;
import com.jjb.ecms.biz.dao.apply.TmMirMakeResDao;
import com.jjb.ecms.biz.ext.customer.BankCustomerSupport;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.apply.TmMirCardService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.CreateApplyFileItemUtil;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.infrastructure.TmAppAudit;
import com.jjb.ecms.infrastructure.TmAppContact;
import com.jjb.ecms.infrastructure.TmAppCustInfo;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppMemo;
import com.jjb.ecms.infrastructure.TmAppPrimAnnexEvi;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.ecms.infrastructure.TmMirCard;
import com.jjb.ecms.infrastructure.TmMirMakeRes;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.ecms.service.api.ImmediatelyBuildCardService;
import com.jjb.ecms.service.dto.Trans0059.Trans0059Req;
import com.jjb.ecms.service.dto.Trans0059.Trans0059Resp;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.CodeMarkUtils;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

@Component
public class OnlineMakeCardSupport {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ImmediatelyBuildCardService immediatelyBuildCardService;
	@Autowired
	private CodeMarkUtils codeMarkUtils;
	@Autowired
	private ApplyInputService applyInputService;
	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired
	private CacheContext cacheContext;
	//卡片信息
	@Autowired
	private TmMirCardDao tmMirCardDao;
	//mps制卡验证失败处理
	@Autowired
	private TmMirMakeResDao tmMirMakeResDao;
	@Autowired
	private CreateApplyFileItemUtil createApplyFileItemUtil;
	@Autowired
	private TmMirCardService tmMirCardService;
	@Autowired
	private BankCustomerSupport bankCustomerSupport;

	/**
	 * 调用mps实时制卡
	 * @param applyInfoDto
	 */
	public void onlineMakeCard(ApplyInfoDto applyInfoDto) {
		long tokenId = System.currentTimeMillis();
		if(applyInfoDto==null || applyInfoDto.getTmAppMain()==null 
				|| !CollectionUtils.sizeGtZero(applyInfoDto.getTmAppCustInfoList())) {
			throw new ProcessException("实时建账制卡失败，没有有效的申请信息");
		}
		String appNo = applyInfoDto.getAppNo();
		Boolean rollBack= false;
		TmAppMain tmAppMain = applyInfoDto.getTmAppMain();
		try{
			logger.info("实时制卡"+AppConstant.BEGINING
					+LogPrintUtils.printAppNoLog(appNo, tokenId, Trans0059Req.servId));
			List<TmAppCustInfo> custs = applyInfoDto.getTmAppCustInfoList();
			boolean exitsEcifCustNo = false;
			for (int i = 0; i < custs.size(); i++) {
				TmAppCustInfo cust = custs.get(i);
				if(cust!=null && StringUtils.isNotEmpty(cust.getBankCustomerId())) {
					logger.info(LogPrintUtils.printAppNoLog(appNo, tokenId,null)+"...客户["+cust.getName()+"]的行内客户号为空");
					exitsEcifCustNo = true;
				}else {
					//TODO 添加获取行内客户号逻辑
					String bankCustId = bankCustomerSupport.getCustomerNumber(tmAppMain,cust,applyInfoDto.getTmAppPrimCardInfo());
					logger.info(LogPrintUtils.printAppNoLog(appNo, tokenId,null)+"...客户["+cust.getName()+"]的行内客户号为:"+bankCustId);
					if(StringUtils.isNotEmpty(bankCustId)) {
						cust.setBankCustomerId(bankCustId);
						applyInputService.updateTmAppCustInfo(cust);
						custs.set(i, cust);
						applyInfoDto.setTmAppCustInfoList(custs);
						Map<String, TmAppCustInfo> custMap = applyInfoDto.getTmAppCustInfoMap();
						if(custMap!=null) {
							String key = cust.getBscSuppInd()+StringUtils.setValue(cust.getAttachNo(), "1");
							custMap.put(key, cust);
							applyInfoDto.setTmAppCustInfoMap(custMap);
						}
						exitsEcifCustNo = true;
					}
				}
			}
			if(!exitsEcifCustNo) {
				logger.info(LogPrintUtils.printAppNoLog(appNo, tokenId,null)+"...客户均未成功获取行内客户号，不做实时制卡操作");
				return;
			}
	//		TmAppAudit tmAppAudit = applyInfoDto.getTmAppAudit();
			String oldRefuseCode = tmAppMain.getRefuseCode();
	//		String oldRemark = tmAppMain.getRemark();
			
			//调用核心进行建账制卡
			List<ApplyFileItem> applyFiles = createApplyFileItem(applyInfoDto);
			//设置请求参数
			Trans0059Req req = setT0059Request(tokenId, tmAppMain);
			boolean noBankCustCode = false;
			String mkRemark = "";
			if(applyFiles!=null && applyFiles.size()>0){
				for (int i = 0; i < applyFiles.size(); i++) {
					ApplyFileItem applyFile = applyFiles.get(i);
					if(applyFile==null || StringUtils.isEmpty(applyFile.BankCustomerId)) {
						logger.info(LogPrintUtils.printAppNoLog(appNo, tokenId, null)+"["+applyFile.Name+"]行内客户号为空,故不制卡");
						noBankCustCode = true;
						mkRemark = mkRemark+"客户["+applyFile.Name+"]行内客户号为空，故不做制卡;";
						continue;
					}
					//实体类数据转换成map
					Map<String, Serializable> applyFileItemMap = new HashMap<String, Serializable>();
					try {
						applyFile.ChannelId=StringUtils.setValue(req.getChannelId(), "94");
						applyFileItemMap = ReflectBeanUtils.objectToMap(applyFile);
					} catch (Exception e) {
						logger.error("PID-["+tokenId+"]...申请件["+appNo+"]待制卡数据转换成map失败", e);
						throw new ProcessException("待制卡数据转换失败", e);
					}
					if(applyFileItemMap==null || applyFileItemMap.isEmpty()){
						logger.error("申请件["+appNo+"]待制卡数据转换后为空,原实体类数据"+applyFile);
						throw new ProcessException("待制卡数据转换后为空");
					}
					logger.info("PID-["+tokenId+"]..."+codeMarkUtils.makeMask(applyFileItemMap.toString()));
					Trans0059Resp resp = null;
					try {
						 resp = immediatelyBuildCardService.Trans0059(req,applyFile);
					} catch (Exception e) {
						logger.error("PID-["+tokenId+"]...申请件["+appNo+"]制卡异常", e);
						throw new ProcessException("制卡交易失败",e.getMessage());
					}
					
					if(resp==null){
						logger.error("PID-["+tokenId+"]...申请件["+appNo+"]制卡异常，核心返回数据为空");
						throw new ProcessException("制卡异常，核心返回的制卡后卡片数据为空，请联系并检查核心系统是否制卡成功");
					}
	
					if(resp!=null&&(!AppConstant.RETURN_MSG.equals(resp.getReturnMsg()))){
						/*通联返回该申请件已在制卡状态时,直接将这笔申请件的状态改为制卡验证失败*/
						if(resp.getReturnMsg().contains("申请件重复")){
							tmAppMain.setRtfState(RtfState.Q05.name());
							tmAppMain.setRemark("核心系统检测到客户已经有该产品卡片，故当前申请件状态置为制卡异常阶段");
							updateTmAppMain(appNo,null,tmAppMain.getRtfState(),tmAppMain.getRemark());
						}else {
							logger.error("PID-[" + tokenId + "]...申请件[" + appNo + "]制卡异常，综合前置返回交易信息是[" + resp.getReturnMsg() + "]");
							throw new ProcessException("制卡异常，综合前置反馈[" + resp.getReturnMsg() + "]");
						}
					} else if(resp.getCardNo()==null|| resp.getAppNo()==null){
						tmAppMain.setRtfState(RtfState.Q05.name());
						tmAppMain.setRemark("制卡失败，故当前申请件状态置为制卡异常阶段");
						updateTmAppMain(appNo,null,tmAppMain.getRtfState(),tmAppMain.getRemark());
					}else {
						tmAppMain.setRtfState(RtfState.N05.name());//默认状态：N05(待制卡)
						tmAppMain.setUpdateDate(new Date());
						tmAppMain.setUpdateUser(OrganizationContextHolder.getUserNo());
					}
					//这里好像有点问题--  如果不满足，就没后续处理
					if(!StringUtils.equals(tmAppMain.getRtfState(), RtfState.Q05.name())) {
						//回盘文件处理
						setApplyResponseItem(tmAppMain, tokenId, resp,oldRefuseCode);
					}
				}
				logger.info("实时制卡"+AppConstant.END
						+LogPrintUtils.printAppNoEndLog(appNo, tokenId, Trans0059Req.servId));
				if(noBankCustCode) {
					tmAppMain.setRemark("[系统备注]"+mkRemark);
					updateTmAppMain(appNo,null,RtfState.Q05.name(),tmAppMain.getRemark());
				}
			}
		}
		catch (Exception e){
			logger.error(LogPrintUtils.printAppNoEndLog(appNo, tokenId, null),e);
			tmAppMain.setRemark("[系统备注]" + e.getMessage());
			updateTmAppMain(appNo,null,RtfState.Q05.name(),tmAppMain.getRemark());
			rollBack=true;
		}if (rollBack){
			throw new ProcessException("制卡失败:"+tmAppMain.getRemark());
		}
	}

	/**
	 * 设置联机请求
	 * @param tonken
	 * @param tmAppMain
	 * @return
	 */
	private Trans0059Req setT0059Request(Long tonken, TmAppMain tmAppMain) {
		//开关-是否可以调用综合前置，为空也是不开启
		Map<String, String> ccifConf = cacheContext.getInterNetAddrParam(AppDitDicConstant.ext_ccif_conf);
		if(ccifConf==null || ccifConf.size()==0){
			throw new ProcessException("未查询到联机交易["+Trans0059Req.servId+"]网络配置，调用失败！");
		}
		if(ccifConf==null || !ccifConf.containsKey(Trans0059Req.servId+"extHost") 
				|| !ccifConf.containsKey(Trans0059Req.servId+"extPort")){
			throw new ProcessException("缺失有效的联机交易["+Trans0059Req.servId+"]网络配置，推送失败！");
		}
		Trans0059Req req = new Trans0059Req();
		req.setTokenId(String.valueOf(tonken));
		req.setOrg(tmAppMain.getOrg());
		req.setAppNo(tmAppMain.getAppNo());
		req.setExtHost(ccifConf.get(Trans0059Req.servId+"extHost"));
		String port = ccifConf.get(Trans0059Req.servId+"extPort");
		req.setExtPort(StringUtils.stringToIntegerNotNull(port));
		req.setCharset(ccifConf.get(Trans0059Req.servId+"charset"));
		req.setLvMsgLength(ccifConf.get(Trans0059Req.servId+"lvMsgLength"));
		req.setConnectTimeOut(ccifConf.get(Trans0059Req.servId+"connectTimeOut"));
		req.setSourceSysId(ccifConf.get(Trans0059Req.servId+"sourceSysId"));
		req.setConsumerId(ccifConf.get(Trans0059Req.servId+"consumerId"));
		req.setServiceCode(ccifConf.get(Trans0059Req.servId+"serviceCode"));
		req.setServiceScene(ccifConf.get(Trans0059Req.servId+"serviceScene"));
		req.setChannelId(ccifConf.get(Trans0059Req.servId+"channelId"));
		req.setBranchId(ccifConf.get(Trans0059Req.servId+"branchId"));
		return req;
	}

	/**
	 * 设置核心回盘文件数据
	 * 卡片、账户、客户信息
	 * @param tmAppMain
	 * @param tokenId
	 * @param oldRefuseCode
	 */
	@Transactional
	public void setApplyResponseItem(TmAppMain tmAppMain, long tokenId, Trans0059Resp resp, String oldRefuseCode) {
		try {
			if(resp!=null){
				//处理TmMirMakeRes表
				insertCardMakerResultItem(resp,tmAppMain);
				logger.info("PID-["+tokenId+"]...applyResponseItemMap:"+codeMarkUtils.makeMask(resp.toString()));
				String refuseCode = "主附卡标识["+resp.getBscSuppInd()+"],";
				// 更新申请件状态，主附同申时，主卡成功即为成功记录
				logger.info("PID-["+tokenId+"]...更新申请件状态["+RtfState.P05.toString()+"],申请编号[{}]", resp.getAppNo());
				//设置历史
				refuseCode = refuseCode + resp.getReasonCd();
				if (StringUtils.equals(resp.getReasonCd(), "A000") || StringUtils.equals(resp.getReasonCd(), "R355")) {
					tmAppMain.setRtfState(RtfState.P05.name());
					tmAppMain.setRefuseCode(null);
					if(StringUtils.equals(resp.getReasonCd(), "R355")) {
						tmAppMain.setRemark("系统备注-核心检测该申请历史已经制卡，当前操作不再新建卡账信息");
					}
					insertApplyResponseItem(resp,tmAppMain);
				}else if(!StringUtils.equals(resp.getReasonCd(), "A000")){
					tmAppMain.setRtfState(RtfState.Q05.name());
					refuseCode = refuseCode + "-"+resp.getReasonCd()+resp.getReasonDesc();
					tmAppMain.setRefuseCode(refuseCode);
				}
				if(refuseCode.length()>200){
						logger.info("PID-["+tokenId+"]...申请件["+tmAppMain.getAppNo()+"]核心制卡返回描述["+refuseCode+"]");
					refuseCode = refuseCode.substring(0, 200);
				}
				tmAppMain.setRemark(refuseCode);
				updateTmAppMain(tmAppMain.getAppNo(),null,tmAppMain.getRtfState(),tmAppMain.getRemark());
			}else{
				logger.error("PID-["+tokenId+"]...申请件["+tmAppMain.getAppNo()+"]已制卡数据读取失败，[Trans0059Resp]数据为空!");
				tmAppMain.setRtfState(RtfState.Q05.name());
				tmAppMain.setRemark("征审系统处理制卡核心回盘数据失败");
				updateTmAppMain(tmAppMain.getAppNo(),null,RtfState.Q05.name(),tmAppMain.getRemark());
			}
		} catch (Exception e) {
			logger.error("PID-["+tokenId+"]...申请件["+tmAppMain.getAppNo()+"]已制卡数据转换成map失败", e);
			//保存历史
			tmAppMain.setRemark("征审系统处理制卡核心回盘数据失败");
			updateTmAppMain(tmAppMain.getAppNo(),null,RtfState.Q05.name(),tmAppMain.getRemark());
			throw new ProcessException("申请件["+tmAppMain.getAppNo()+"]核心回盘文件数据[ApplyResponseItem]处理失败,错误信息:["+e.getMessage()+"]");
		}
	}

	/**
	 * 处理核心返回的mps制卡结果
	 */
	public void insertApplyResponseItem(Trans0059Resp resp,TmAppMain main) throws Exception {
		logger.info("申请件[" + resp.getAppNo() + "]读取回盘文件开始...");
		try {
			// 返回客户信息数据
//			TmMirCustr cust = tmMirCustrDao.getTmMirCustr(resp.getcustId);
//			if (cust == null) {
//				TmMirCustr tmMirCustr = new TmMirCustr();
//				tmMirCustr.setOrg(resp.getorg); // 机构号
//				tmMirCustr.setAppNo(resp.getappNo); // 申请编号
//				tmMirCustr.setCustId(resp.getcustId); // 客户号
//				tmMirCustr.setIdType(resp.getidType==null ? null:resp.getidType.name()); // 证件类型
//				tmMirCustr.setIdNo(resp.getidNo); // 证件号
//				tmMirCustr.setCreditLimit(resp.getcreditLimitCust); // 信用额度
//				tmMirCustr.setName(resp.getname); // 客户姓名
//				tmMirCustr.setSetupDate(resp.getsetupDate); // 处理日期
//				tmMirCustr.setAppRejectReason(resp.getappRejectReason==null ? null:resp.getappRejectReason.name()); // 拒绝原因
//				tmMirCustrDao.save(tmMirCustr);
//				logger.info("客户信息数据插入,申请编号[{}]", resp.getappNo);
//			}
//			// 返回账户信息数据
//			if(StringUtils.isNotEmpty(resp.getacctNo) && resp.getacctType!=null){
//				TmMirAcct acct = new TmMirAcct();
//				acct.setAcctNo(resp.getacctNo);
//				acct.setAcctType(resp.getacctType.name());
//				acct = tmMirAcctDao.getTmMirAcct(acct);
//				if (acct == null) {
//					TmMirAcct tmMirAcct = new TmMirAcct();
//					tmMirAcct.setOrg(resp.getorg); // 机构号
//					tmMirAcct.setAppNo(resp.getappNo); // 申请编号
//					tmMirAcct.setAcctType(resp.getacctType==null ? null:resp.getacctType.name()); // 账户类型
//					tmMirAcct.setAcctNo(resp.getacctNo); // 账户信息
//					tmMirAcct.setCustId(resp.getcustId); // 客户号
//					tmMirAcct.setCreditLimit(resp.getcreditLimitAcct); // 账户层信用额度
//					tmMirAcct.setSetupDate(resp.getsetupDate); // 处理日期
//					tmMirAcct.setAppRejectReason(resp.getappRejectReason==null ? null:resp.getappRejectReason.name()); // 拒绝原因
//					tmMirAcct.setBillingCycle(resp.getbillingCycle);//账单日
//					tmMirAcctDao.save(tmMirAcct);
//					logger.info("账户信息数据插入,申请编号[{}]", resp.getappNo);
//				}
//			}
			// 返回卡信息数据
			TmMirCard card = tmMirCardDao.getByCardNo(resp.getCardNo());
			if (card == null) {
				TmMirCard tmMirCard = new TmMirCard();
				tmMirCard.setName(main.getName());//客户姓名
				tmMirCard.setIdNo(resp.IdNo);//证件号码
				tmMirCard.setIdType(resp.IdType);//证件类型
				tmMirCard.setOrg(StringUtils.setValue(resp.getOrg(),main.getOrg())); // 机构号
				tmMirCard.setAppNo(resp.getAppNo()); // 申请编号
//				tmMirCard.setCustId(resp.getcustId); // 客户号
				tmMirCard.setCardNo(resp.getCardNo()); // 卡号
//				tmMirCard.setAcctNo(resp.getacctNo); // 账号
//				tmMirCard.setAcctType(resp.getacctType==null ? null:resp.getacctType.name()); // 账户类型
				tmMirCard.setProductCd(resp.getProductCd()); // 卡产品
//				tmMirCard.setPyhCd(resp.getpyhCd); // 卡面
//				tmMirCard.setBlockCode(resp.getblockCode); // 封锁码
				tmMirCard.setOwningBranch(main.getOwningBranch()); // 发卡网点
				tmMirCard.setBscSuppInd(resp.getBscSuppInd()); // 主附卡标志
				tmMirCard.setSetupDate(new Date()); // 处理日期
//				tmMirCard.setAppRejectReason(main.getRemark()); // 拒绝原因
				tmMirCardDao.save(tmMirCard);
				logger.info("卡信息数据插入,申请编号[{}]", resp.getAppNo());
			}
			else{
				logger.info("申请件[" +resp.getAppNo()+ "}]"+"建账制卡失败，返回的卡号系统中已存在");
				throw new ProcessException("申请件[" +resp.getAppNo()+ "}]"+"建账制卡失败，返回的卡号系统中已存在");
			}
			logger.info("申请件[" + resp.getAppNo() + "]读取回盘文件结束...");
		} catch (Exception e) {
			logger.error("建账制卡核心回盘文件处理异常,申请编号[{}]", resp.getAppNo());
			logger.error("建账制卡核心回盘文件处理异常,[{}]", e);
			throw e;
		}
	}
	/**
	 * 保存核心返回的制卡结论数据
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public TmMirMakeRes insertCardMakerResultItem(Trans0059Resp response,TmAppMain main)throws Exception {

		logger.info("申请件["+response.AppNo+"]...处理MPS回盘文件开始...");
		try {
			if(StringUtils.isEmpty(response.AppNo) && StringUtils.isEmpty(response.getReturnCode())){
				throw new ProcessException("返回的mps制卡验证数据申请数据为空导致无法继续处理，请重新登录后重试!");
			}
			String appNo = response.AppNo;
			if(StringUtils.isEmpty(response.AppNo) && StringUtils.isNotEmpty(main.getAppNo())) {
				appNo = main.getAppNo();
			}
			if(StringUtils.isNotEmpty(appNo)) {
				TmMirMakeRes makerCardRs = tmMirMakeResDao.getTmMirMakeResByAppNo(appNo);
				String reasonCd = response.ReasonCd;
				if(StringUtils.isEmpty(reasonCd)) {
					reasonCd = response.getReturnCode()+"-"+response.getReturnMsg();
				}
				// 返回信息数据
				BigDecimal creditLimit= StringUtils.stringToBigDecimal(response.CreditLimit);
				if (makerCardRs == null) {
					makerCardRs = new TmMirMakeRes();
					makerCardRs.setOrg(main.getOrg()); // 机构号
					makerCardRs.setProductCd(main.getProductCd());//产品代码
					makerCardRs.setAppNo(appNo); // 申请件编号
					makerCardRs.setIdType(response.IdType==null ? null:response.IdType); // 证件类型
					makerCardRs.setIdNo(response.IdNo); // 证件号
					makerCardRs.setEmbName(response.EmbName); // 凸印名-姓名拼音
					makerCardRs.setBscSuppInd(response.BscSuppInd==null ? null:response.BscSuppInd); // 主附卡标识
					makerCardRs.setCreditLimit(creditLimit); // 信用额度
					makerCardRs.setCompanyName(response.CompanyName); // 公司名称
					makerCardRs.setReasonCode(reasonCd); // 原因码
					makerCardRs.setOwningBranch(response.IssueBranch); // 发卡网点
					makerCardRs.setCreateTime(new Date());
					makerCardRs.setUpdateTime(new Date());
					tmMirMakeResDao.save(makerCardRs);
					logger.info("MPS回盘信息数据插入,申请编号[{}]", response.AppNo);
				}else{
					//tmResultCardmakerMps.setOrg(response.org); // 机构号
					//tmResultCardmakerMps.setAppNo(response.appNo); // 申请件编号
					makerCardRs.setProductCd(response.ProductCd);//产品代码
					makerCardRs.setIdType(response.IdType==null ? null:response.IdType); // 证件类型
					makerCardRs.setIdNo(response.IdNo); // 证件号
					makerCardRs.setEmbName(response.EmbName); // 凸印名-姓名拼音
					makerCardRs.setBscSuppInd(response.BscSuppInd==null ? null:response.BscSuppInd); // 主附卡标识
					makerCardRs.setCreditLimit(creditLimit); // 信用额度
					makerCardRs.setCompanyName(response.CompanyName); // 公司名称
					makerCardRs.setReasonCode(reasonCd); // 原因码
					makerCardRs.setOwningBranch(response.IssueBranch); // 发卡网点
					makerCardRs.setUpdateTime(new Date());
					tmMirMakeResDao.update(makerCardRs);
					logger.info("MPS回盘信息数据更新,申请编号[{}]", response.AppNo);
				}
				logger.info("申请件["+response.AppNo+"]...处理MPS回盘文件结束...");
			}
			
			return null;

		} catch (Exception e) {
			logger.error("MPS回盘文件处理异常,申请编号[{}]", response.AppNo);
			logger.error("MPS回盘文件处理异常,[{}]", e);
			updateTmAppMain(response.AppNo, null, RtfState.Q05.name(),"处理mps制卡验证回盘数据失败"+e.getMessage());
			throw e;
		}

	}
	
	/**
	 * 更新主卡信息表数据
	 */
	@Transactional
	public void updateTmAppMain(String appNo,String refuseCode,String rtfState,String remark) {
		TmAppMain tmAppMain = applyQueryService.getTmAppMainByAppNo(appNo);
		
		if(tmAppMain!=null){
			logger.info("申请编号[{}]，申请类型[{}]", tmAppMain.getAppNo(), tmAppMain.getAppType());
			tmAppMain.setRtfState(rtfState);
			if(StringUtils.isNotEmpty(refuseCode)){
				if(refuseCode.length()>200){
					logger.info("申请件["+tmAppMain.getAppNo()+"]失败描述["+refuseCode+"]");
					refuseCode =  refuseCode.substring(0, 200);
				}
				tmAppMain.setRefuseCode(refuseCode);
			}
			if(StringUtils.isNotEmpty(remark)){
				if(remark.length()>200){
					logger.info("申请件["+tmAppMain.getAppNo()+"]备注描述["+remark+"]");
					remark =  remark.substring(0, 200);
				}
				tmAppMain.setRemark(remark);
			}
			applyInputService.updateTmAppMain(tmAppMain);
			saveHistory(tmAppMain);
		}
	}
	/**
	 * 保存历史
	 * 
	 * @param appMain
	 */
	@Transactional
	public void saveHistory(TmAppMain appMain) {
		RtfState rs = AppCommonUtil.stringToEnum(RtfState.class,appMain.getRtfState(),null);
		TmAppHistory history = AppCommonUtil.insertApplyHist(
				appMain.getAppNo(), AppConstant.SYS_AUTO,
				rs,
				appMain.getRefuseCode(),appMain.getRemark());
		//记录历史
		if(history != null){
			history.setIdType(appMain.getIdType());
			history.setIdNo(appMain.getIdNo());
			history.setName(appMain.getName());
			applyInputService.saveTmAppHistory(history);
		}
		TmAppMemo tmAppMemo = new TmAppMemo();
		tmAppMemo.setAppNo(appMain.getAppNo());
		tmAppMemo.setOrg(appMain.getOrg());
		tmAppMemo.setCreateUser(OrganizationContextHolder.getUserNo());
		tmAppMemo.setCreateDate(new Date());
		tmAppMemo.setMemoType(AppConstant.APP_REMARK);
		tmAppMemo.setRtfState(appMain.getRtfState());
		tmAppMemo.setMemoInfo(appMain.getRemark());
		tmAppMemo.setTaskKey("SYSAUTO");
		tmAppMemo.setTaskDesc("系统自动备注");
		applyInputService.saveTmAppMemo(tmAppMemo);
	}
	/**
	 * 根据申请件信息生成待制卡数据
	 * @param applyNodeLoggingData
	 * @return
	 */
	public List<ApplyFileItem> createApplyFileItem(ApplyInfoDto applyInfoDto){
		Long tokenId = System.currentTimeMillis();
		
		if(applyInfoDto==null){
			return null;
		}
		List<ApplyFileItem> applyFileItemList = new ArrayList<ApplyFileItem>();
		//主表
		TmAppMain appMain = applyInfoDto.getTmAppMain();
		if(OrganizationContextHolder.getUserNo()==null){
			OrganizationContextHolder.setUserNo(OrganizationContextHolder.getUserNo());
		}
		logger.info("RID-["+tokenId+"]...开始调用核心进行实时制卡操作,申请件["+appMain.getAppNo()+"],操作员["+OrganizationContextHolder.getUserNo()+"]");
		if(OrganizationContextHolder.getOrg()==null){
			OrganizationContextHolder.setOrg(appMain.getOrg());
		}
		
		TmProduct product = cacheContext.getProduct(appMain.getProductCd());
//		Product product = paramFacility.loadParameter(appMain.getProductCd(), Product.class);
		if(product==null){
			logger.info("RID-["+tokenId+"]...申请件["+appMain.getAppNo()+"]所选产品["+appMain.getProductCd()+"]信息为空,无法实时制卡");
			throw new ProcessException("申请件["+appMain.getAppNo()+"]所选产品["+appMain.getProductCd()+"]信息为空,无法实时制卡，请重试!");
		}
//		logger.info("RID-["+tokenId+"]...申请件["+appMain.getAppNo()+"],ETC卡标识["+product.isEtc+"]" );
		
		//申请人信息
		Map<String,TmAppCustInfo> custMap = applyInfoDto.getTmAppCustInfoMap();
		//主卡联系人信息
		Map<String, TmAppContact> appPrimContactInfoMap = applyInfoDto.getTmAppContactMap();
		//卡片信息
		TmAppPrimCardInfo appPrimCardInfo = applyInfoDto.getTmAppPrimCardInfo();
		appPrimCardInfo = createApplyFileItemUtil.fillCardFace(appMain, appPrimCardInfo);
		//附件信息
		TmAppPrimAnnexEvi appPrimAnnexEvi = applyInfoDto.getTmAppPrimAnnexEvi();
//		//分期贷款信息
//		TmAppInstalLoan appInstalLoan = applyInfoDto.getTmAppInstalLoan();
		TmAppAudit appAudit = applyInfoDto.getTmAppAudit();//申请审计信息
		// 根据不同申请类型做处理
		switch (AppType.valueOf(appMain.getAppType())){
			//主附同申
			case A:
				boolean ifMarkCard=false;
				if(custMap==null){
					logger.error("主附同申申请件["+appMain.getAppNo()+"]实时制卡时未获取到主卡申请人信息，可能当前登录已超时");
					throw new ProcessException("主附同申申请件["+appMain.getAppNo()+"]实时制卡时未获取到主卡申请人信息，请重新登录系统后重试!");
				}
				List<TmMirCard> tmMirCardList = tmMirCardService.getTmMirCardByCustInfo(appMain,custMap.get(AppConstant.bscSuppInd_B_1));
				if(!CollectionUtils.sizeGtZero(tmMirCardList)){//主附同申，如果主卡制卡失败，则附卡肯定也会失败，需要再次制卡
					if (appPrimCardInfo!=null && StringUtils.isNotEmpty(appPrimCardInfo.getCardfaceCd())) {
						// 成功申请待制卡数据
						ApplyFileItem applyFileItem = createApplyFileItemUtil.createApplyFileItem(appMain, appAudit,
									BscSuppIndicator.B, custMap,
									appPrimContactInfoMap, appPrimCardInfo,
									appPrimAnnexEvi, product, null);
						applyFileItemList.add(applyFileItem);
						logger.info("主附同申[" + appMain.getAppNo() + "]-追加主卡[" + appMain.getName()
								+ "]到待制卡数据中，总记录条数："+ applyFileItemList.size() + " 条");
					}
				} else {
					logger.error("申请件["+appMain.getAppNo()+"]主卡已制卡,本次不在进行制卡操作");
					ifMarkCard = true;
				}
				//处理附卡
				for (TmAppCustInfo cust : custMap.values()) {
					if(cust!=null && StringUtils.equals(cust.getBscSuppInd(), AppConstant.bscSuppInd_S)){
						List<TmMirCard> tmMirCardList1 = tmMirCardService.getTmMirCardByCustInfo(appMain,cust);
						if(!CollectionUtils.sizeGtZero(tmMirCardList1)){
							if(cust.getRecordStatus()==null || !cust.getRecordStatus().equals(Indicator.N.name())){
								//附卡卡面信息
								if(appPrimCardInfo!=null && StringUtils.isNotEmpty(appPrimCardInfo.getCardfaceCd())){
									// 创建申请(附卡)
									applyFileItemList.add(createApplyFileItemUtil.createApplyFileItem(
														appMain, appAudit,BscSuppIndicator.S,
														custMap, null,appPrimCardInfo,
														null,product,cust.getAttachNo()));
									logger.info("主附同申[" + appMain.getAppNo() + "]-追加附卡["+ cust.getName()
											+ "]到待制卡数据中，总记录条数："+ applyFileItemList.size() + " 条");
									ifMarkCard = false;
								}
							}else{
								logger.info("主附同申[" + appMain.getAppNo() + "]-追加附卡[" + cust.getName() + "]附卡状态["
										+ cust.getRecordStatus() + "]为失败");
							}
						}else{
							logger.error("申请件["+appMain.getAppNo()+"]附卡["+cust.getName()+"]已制卡,本次不在进行制卡操作");
							ifMarkCard = true;
						}
					}
				}
				if(ifMarkCard){
					logger.error("申请件" + appMain.getAppNo() + "已制卡成功！该申请件状态为" + appMain.getRtfState());
					throw new ProcessException("申请件" + appMain.getAppNo() + "已制卡成功！请勿重复提交");
				}
				break;
			//独立主卡
			case B:
				if(custMap==null){
					logger.error("独立主卡申请件["+appMain.getAppNo()+"]实时制卡时未获取到主卡申请人信息，可能当前登录已超时");
					throw new ProcessException("独立主卡申请件["+appMain.getAppNo()+"]实时制卡时未获取到主卡申请人信息，请重新登录系统后重试!");
				}
				List<TmMirCard> tmMirCardList2 = tmMirCardService.getTmMirCardByCustInfo(appMain,custMap.get(AppConstant.bscSuppInd_B_1));
				if(!CollectionUtils.sizeGtZero(tmMirCardList2)){
					if(appPrimCardInfo!=null && StringUtils.isNotEmpty(appPrimCardInfo.getCardfaceCd())){
						// 成功申请待制卡数据
						ApplyFileItem applyFileItem = createApplyFileItemUtil.createApplyFileItem(appMain, appAudit,
									BscSuppIndicator.B, custMap,
									appPrimContactInfoMap, appPrimCardInfo,
									appPrimAnnexEvi, product, null);
						applyFileItemList.add(applyFileItem);
						logger.info("独立主卡[" + appMain.getAppNo() + "]-追加主卡[" + appMain.getName()
								+ "]到待制卡数据中，总记录条数："+ applyFileItemList.size() + " 条");
					}
				} else {
					logger.error("申请件" + appMain.getAppNo() + "已制卡成功！该申请件状态为" + appMain.getRtfState());
					throw new ProcessException("申请件" + appMain.getAppNo() + "已制卡成功！请勿重复提交");
				}
			    break;
			    
			//独立附卡	
			case S:
				//处理附卡
				for (TmAppCustInfo cust : custMap.values()) {
					if(cust!=null && StringUtils.equals(cust.getBscSuppInd(), AppConstant.bscSuppInd_S)){
						List<TmMirCard> tmMirCardList1 = tmMirCardService.getTmMirCardByCustInfo(appMain,cust);
						if(!CollectionUtils.sizeGtZero(tmMirCardList1)){
							if(cust.getRecordStatus()==null || !cust.getRecordStatus().equals(Indicator.N.name())){
								//附卡卡面信息
								if(appPrimCardInfo!=null && StringUtils.isNotEmpty(appPrimCardInfo.getCardfaceCd())){
									
									//附卡卡面信息
									//附卡寄送地址，公司地址，家庭地址，若未填写，送相应主卡人相关地址信息
									TmMirCard priMirCard = tmMirCardService.getTmMirCardByCardNo(cust.getPrimCardNo());
									TmAppCustInfo primCust = null;
									if(priMirCard != null && StringUtils.isNotBlank(priMirCard.getAppNo()))
										primCust = applyQueryService.getTmAppPrimCustByAppNo(priMirCard.getAppNo());
									custMap.put(AppConstant.bscSuppInd_B, primCust);
									
									// 创建申请(附卡)
									applyFileItemList.add(createApplyFileItemUtil.createApplyFileItem(
														appMain, appAudit, BscSuppIndicator.S,
														custMap, null, appPrimCardInfo,
														null, product,cust.getAttachNo()));
									logger.info("独立附卡[" + appMain.getAppNo() + "]-追加附卡[" + cust.getName()
											+ "]到待制卡数据中，总记录条数：" + custMap.size() + " 条");
								}
							}else{
								logger.info("独立附卡[" + appMain.getAppNo() + "]-追加附卡[" + cust.getName() + "]附卡状态["
										+ cust.getRecordStatus() + "]为失败");
							}
						}else{
							logger.error("申请件" + appMain.getAppNo() + "已制卡成功！该申请件状态为" + appMain.getRtfState());
							throw new ProcessException("申请件" + appMain.getAppNo() + "已制卡成功！请勿重复提交");
						}
					}
				}
				break;
				
			default: throw new IllegalArgumentException("不存在的申请类型" + appMain.getAppType().toString());
		}
		return applyFileItemList;
	}
}
