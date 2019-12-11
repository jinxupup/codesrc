package com.jjb.cas.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jjb.ecms.biz.dao.apply.TmAppOrderMainDao;
import com.jjb.ecms.infrastructure.TmAppOrderMain;
import com.jjb.ecms.infrastructure.TmProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjb.cas.service.util.ProcessApplyUtils;
import com.jjb.cas.service.util.ProcessT1000Utils;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.query.ApplyQueryRankingDao;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.manage.ApplySpreaderInfoService;
import com.jjb.ecms.biz.service.query.ApplyProcessQueryService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.facility.dto.ApplyProcessQueryDto;
import com.jjb.ecms.facility.dto.ApplyQueryRankingDto;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppSprePerBank;
import com.jjb.ecms.infrastructure.TmDitDic;
import com.jjb.ecms.service.api.QueryService;
import com.jjb.ecms.service.dto.T1000.T1000Apply;
import com.jjb.ecms.service.dto.T1000.T1000Req;
import com.jjb.ecms.service.dto.T1000.T1000Resp;
import com.jjb.ecms.service.dto.T1002.T1002Req;
import com.jjb.ecms.service.dto.T1002.T1002Resp;
import com.jjb.ecms.service.dto.T1007.T1007Req;
import com.jjb.ecms.service.dto.T1007.T1007Resp;
import com.jjb.ecms.service.dto.T1007.T1007UserInfo;
import com.jjb.ecms.service.dto.T1009.Rank;
import com.jjb.ecms.service.dto.T1009.T1009Req;
import com.jjb.ecms.service.dto.T1009.T1009Resp;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.CodeMarkUtils;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: 征审系统查询服务接口实现
 * @author JYData-R&D-Big Star
 * @version V1.0
 */
@Service("queryServiceImpl")
public class QueryServiceImpl implements QueryService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ApplyProcessQueryService applyProcessQueryService;
	@Autowired
	private CodeMarkUtils codeMarkUtils;
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private AppCommonUtil appCommonUtil;
	@Autowired
	private ApplySpreaderInfoService applySpreaderInfoService;
	@Autowired
	private ApplyQueryRankingDao applyQueryRankingDao;
	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired
	private TmAppOrderMainDao tmAppOrderMainDao;
	/**
	 *  申请进度查询
	 */
	@Override
	public T1000Resp T1000(T1000Req req) throws ProcessException {
		long start = System.currentTimeMillis();
		if(req==null){
			logger.error("PID["+start+"],请求业务参数不能为空！");
			throw new ProcessException("请求业务参数不能为空！");
		}
		logger.info("申请进度查询开始-PID["+start+"]...,证件类型["+req.getIdType()
				+"],证件号码["+codeMarkUtils.makeIDCardMask(req.getIdNo())
				+"],客户姓名["+req.getName()+"],客户手机["+req.getCellphone()+"]" );
		appCommonUtil.setOrg(OrganizationContextHolder.getOrg());
		Page<ApplyProcessQueryDto> page = ProcessT1000Utils.putReqFieldIntoPageIfNotNull(req, start);
		logger.debug("申请进度查询开始-PID["+start+"]...,请求参数[{}]" + page.toString());
		Page<ApplyProcessQueryDto> applyProcessQueryDtos = applyProcessQueryService.applyProcessList(page);
		logger.info("申请进度查询-PID["+start+"]，总数量：" + applyProcessQueryDtos.getTotal());
		T1000Resp resp = setValueToT1000Resp(start,req, applyProcessQueryDtos);

		logger.info("联机申请进度查询结束-PID["+start+"]，申请案件共：" + applyProcessQueryDtos.getTotal() + "条");
		return resp;
	}

/*
	private ArrayList<T1000Apply> passValueFromProcessQueryDtoToT1000Apply(long start, Page<ApplyProcessQueryDto> applyProcessQueryDtos) {

		ArrayList<T1000Apply> applys = new ArrayList<T1000Apply>();

		for (ApplyProcessQueryDto applyProcessQueryDto : applyProcessQueryDtos.getRows()) {
			if(applyProcessQueryDto==null){
				continue;
			}

			// 如果非销卡
			String org = OrganizationContextHolder.getOrg();
			String appType = applyProcessQueryDto.getAppType();
			String cardNo = applyProcessQueryDto.getCardNo();
			if (getCardBlockCode(org, applyProcessQueryDto.getAppNo(),appType ,cardNo)) {
				T1000Apply apply = setQueryValueToT1000Apply(applyProcessQueryDto);
				applys.add(apply);
			} else {
				logger.info("申请进度查询-PID["+start+"]，该申请件申对应主卡为销卡状态，不返回数据:"+ applyProcessQueryDto.getAppNo());
			}
		}
		return applys;
	}*/


	private T1000Resp setValueToT1000Resp(long start,T1000Req req, Page<ApplyProcessQueryDto> applyProcessQueryDtos) {
		T1000Resp resp = new T1000Resp();
		try {
			resp.setCurPage(req.getCurPage());
		} catch (Exception e1) {
			resp.setCurPage(1);
		}
		try {
			resp.setRowCnt(req.getRowCnt());
		} catch (Exception e) {
			resp.setRowCnt(10);
		}

		ArrayList<T1000Apply> applys = new ArrayList<T1000Apply>();
		if(applyProcessQueryDtos.getRows()!=null)
			for (ApplyProcessQueryDto applyProcessQueryDto : applyProcessQueryDtos.getRows()) {
				if(applyProcessQueryDto==null){
					continue;
				}
				if(!StringUtils.equals(applyProcessQueryDto.getActivateInd(),"Y") ){
					applyProcessQueryDto.setCardStatus("A");
				}else if(!StringUtils.equals(applyProcessQueryDto.getIfSwiped(),"Y")){
					applyProcessQueryDto.setCardStatus("NF");
				}else{
					applyProcessQueryDto.setCardStatus("CF");
				}
				T1000Apply apply = ProcessApplyUtils.setQueryValueToT1000Apply(applyProcessQueryDto,cacheContext, req, tmAppOrderMainDao);
				applys.add(apply);
			}
		Query query = applyProcessQueryDtos.getQuery();
		if(StringUtils.isNotEmpty(query.get("spreader")+"")) {
			ApplyQueryRankingDto applyQueryRankingDto = new ApplyQueryRankingDto();
			applyQueryRankingDto.setAppSource(StringUtils.valueOf(query.get("appSource")));
			applyQueryRankingDto.setSpreaderNo(StringUtils.valueOf(query.get("spreader")));
			if(StringUtils.equals(applyQueryRankingDto.getAppSource(),"10")){//10渠道进件
				applyQueryRankingDto = applyQueryRankingDao.querySpreSumNew(applyQueryRankingDto);
				if(applyQueryRankingDto!=null) {
					resp.setSuccCnt(StringUtils.stringToIntegerNotNull(applyQueryRankingDto.getSuccApprovalCnt()));
					resp.setEffSuccCnt(StringUtils.stringToIntegerNotNull(applyQueryRankingDto.getSuccEffCnt()));
					resp.setRefuseCnt(StringUtils.stringToIntegerNotNull(applyQueryRankingDto.getRefuseCnt()));
					resp.setApproveCnt(StringUtils.stringToIntegerNotNull(applyQueryRankingDto.getApproveCnt()));
					resp.setPreCnt(StringUtils.stringToIntegerNotNull(applyQueryRankingDto.getPreCnt()));
				}
			}else{//非10渠道进件,区别在于统计'有效核卡数'的逻辑
				applyQueryRankingDto = applyQueryRankingDao.querySpreSum(applyQueryRankingDto);
				if(applyQueryRankingDto!=null) {
					resp.setSuccCnt(StringUtils.stringToIntegerNotNull(applyQueryRankingDto.getSuccApprovalCnt()));
					resp.setEffSuccCnt(StringUtils.stringToIntegerNotNull(applyQueryRankingDto.getSuccEffCnt()));
					resp.setRefuseCnt(StringUtils.stringToIntegerNotNull(applyQueryRankingDto.getRefuseCnt()));
					resp.setApproveCnt(StringUtils.stringToIntegerNotNull(applyQueryRankingDto.getApproveCnt()));
					resp.setPreCnt(StringUtils.stringToIntegerNotNull(applyQueryRankingDto.getPreCnt()));
				}
			}
		}
		resp.setTotalCnt((int)applyProcessQueryDtos.getTotal());
		resp.setApplys(applys);
		return resp;
	}

	/**
	 * 推广员查询
	 */
	@Override
	public T1007Resp T1007(T1007Req req) throws ProcessException {
		long start = System.currentTimeMillis();
		if(req==null){
			logger.error("PID["+start+"]-T1007,请求参数不能为空！");
			throw new ProcessException("请求参数不能为空！");
		}
		logger.info("推广人查询["+req.getTokenId()+"]开始-LOCL-PID["+start+"]...,推广人工号["+req.getUserNo()
				+"],推广人手机["+codeMarkUtils.makeIDCardMask(req.getPhoneNo())
				+"],推广人所属机构["+req.getBranchCode()+"],当前查询页["+req.getCurPage()+"]" );
		T1007Resp resp = new T1007Resp();
		if(StringUtils.equals(req.getUserNo(), "")) {
			throw new ProcessException("请输入有效的员工工号!");
		}
		try {

			appCommonUtil.setOrg(OrganizationContextHolder.getOrg());

			TmAppSprePerBank spreader = new TmAppSprePerBank();
			Page<TmAppSprePerBank> page = new Page<>();
			page.setPageNumber(req.getCurPage());//需要查询的当前页
			page.setPageSize(20);//需要查询的每页最大记录数

			spreader.setSpreaderNum(req.getUserNo());
			spreader.setSpreaderPhone(req.getPhoneNo());
			spreader.setSpreaderBankId(req.getBranchCode());
			spreader.setSprUserType(req.getQuyType());//查询推广人类型
			spreader.setSpreaderStatus("1");//默认查询有效的推广人

			page = applySpreaderInfoService.getPage(page, spreader);
			List<T1007UserInfo> users = new ArrayList<T1007UserInfo>();
			if(page!=null) {
				resp.setCurPage(req.getCurPage());
				resp.setRowCnt(req.getRowCnt());
				resp.setTotalCnt(page.getTotal());
				List<TmAppSprePerBank> spreList = page.getRows();
				if(spreList!=null) {
					for (int i = 0; i < spreList.size(); i++) {
						TmAppSprePerBank sp = spreList.get(i);
						if(sp==null) {
							continue;
						}
						T1007UserInfo user = new T1007UserInfo();
						user.setUserNo(sp.getSpreaderNum());
						user.setUserNmae(sp.getSpreaderName());
						user.setPhoneNo(sp.getSpreaderPhone());
						user.setSupBranch(sp.getSpreaderOrg());
						user.setBranch(sp.getSpreaderBankId());
						user.setUserState(sp.getSpreaderStatus());
						user.setUserType(sp.getSprUserType());
						users.add(user);
					}
				}
				resp.setUsers(users);
			}
		} catch (Exception e) {
			logger.error("推广人查询异常", e);
			throw new ProcessException("推广人查询失败！请确认查询条件无误并重试！");
		}finally {
			logger.info("推广人查询结束-PID["+start+"]，检索到推广人记录数共：" + resp.getTotalCnt() + "条");
		}

		return resp;
	}

	/**
	 * 排行榜查询
	 */
	@Override
	public T1009Resp T1009(T1009Req req) throws ProcessException {
		long start = System.currentTimeMillis();
		if(req==null){
			logger.error("PID["+start+"]-T1009,请求参数不能为空！");
			throw new ProcessException("请求参数不能为空！");
		}
		T1009Resp resp = new T1009Resp();
		try {
			ApplyQueryRankingDto applyQueryRankingDto = new ApplyQueryRankingDto();
			applyQueryRankingDto.setQuyType(req.getQuyType());
			applyQueryRankingDto.setAppSource(req.getAppSource());
			applyQueryRankingDto.setTotalCnt(req.getTotalCnt());
			applyQueryRankingDto.setSpreaderNo(req.getSpreaderNO());

			//是否开启排行榜日期范围，及日期范围的值
			TmDitDic rdSet = cacheContext.getApplyOnlineOnOff(AppDitDicConstant.onLinOff_RANKING_DATE);
			if(rdSet!=null && StringUtils.equals(rdSet.getRemark(), "Y")) {
				String startDate = rdSet.getIfUsed();//开始统计日期
				String endDate = StringUtils.setValue(rdSet.getFormName(),
						DateUtils.dateToString(DateUtils.getNextDay(new Date(), 1), DateUtils.DAY_YMD));//结束统计日期
				applyQueryRankingDto.setStartDate(startDate);
				applyQueryRankingDto.setEndDate(endDate);
			}

			List<ApplyQueryRankingDto> listRanking = applyQueryRankingDao.queryRankingList(applyQueryRankingDto);
			if(StringUtils.isNotEmpty(req.getSpreaderNO())){
				ApplyQueryRankingDto dto = applyQueryRankingDao.querySpreSum(applyQueryRankingDto);
				if(dto!=null) {
					resp.setSpreadNum(dto.getSuccApprovalCnt());
					resp.setApprovalNum(dto.getSuccEffCnt());//有效核卡数量
					resp.setPreCnt(dto.getApproveCnt());
				}
			}

			List<Rank> ranks = new ArrayList<>();
			if(listRanking!=null) {
				for (int i = 0; i < listRanking.size(); i++) {
					ApplyQueryRankingDto applyQuery = listRanking.get(i);
					if(applyQuery ==null) {
						continue;
					}
					Rank rank = new Rank();
					rank.setCellphone(applyQuery.getCellphone());
					rank.setName(applyQuery.getName());
					rank.setRanking(applyQuery.getRanking());
					rank.setSuccCnt(applyQuery.getSuccCnt());
					ranks.add(rank);
				}
			}
			resp.setRanks(ranks);
		} catch (Exception e) {
			logger.error("根据类型查询渠道端进件排行榜异常", e);
			throw new ProcessException("根据类型查询渠道端进件排行榜失败！请确认查询条件无误并重试！");
		}finally {
			logger.info("渠道端进件排行榜查询结束，查询成功！");
		}

		return resp;
	}

	/**
	 * 准入资格及老客户查询
	 */
	@Override
	public T1002Resp T1002(T1002Req req) throws ProcessException {
		T1002Resp resp = new  T1002Resp();
		if(req==null) {
			logger.error("请求参数[T1002Req]为空");
			throw new ProcessException("准入及老客户查询-请求条件不能为空");
		}
		/*if(StringUtils.isEmpty(req.getName())) {
			throw new ProcessException("客户姓名不能为空");
		}*/
		if(StringUtils.isEmpty(req.getIdNo())) {
			throw new ProcessException("证件号码不能为空");
		}
		/*if(StringUtils.isEmpty(req.getProductCd())) {
			throw new ProcessException("申请产品不能为空");
		}*/
		if(StringUtils.isNotEmpty(req.getProductCd())){
			if(StringUtils.isEmpty(req.getName())) {
				throw new ProcessException("客户姓名不能为空");
			}
		}
		long start = System.currentTimeMillis();
		String tokenId = StringUtils.setValue(req.getTokenId(), start);
		logger.info(LogPrintUtils.printCommonEndLog(tokenId, T1002Req.servId)+"...准入及老客户查询开始");
		try {
			//判断申请卡产品重复
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("idType", req.getIdType());
			parameter.put("idNo", req.getIdNo());
//			parameter.put("productCd", req.getProductCd());
			resp.setAdmittance("A");
			resp.setCustomerType("N");
			TmDitDic ditOnline = cacheContext.getApplyOnlineOnOff(AppDitDicConstant.onLinOff_repeatIdentityVerification);
			if (ditOnline == null || !StringUtils.equals(ditOnline.getRemark(), "Y") ) {
				return resp;
			}
			List<TmAppMain> tmAppMainList = applyQueryService.getTmAppMainByParm(parameter);
				if (CollectionUtils.sizeGtZero(tmAppMainList)) {
					logger.info(LogPrintUtils.printCommonEndLog(tokenId, T1002Req.servId) + "TmAppMian-list:" + (tmAppMainList.size()));
					TmDitDic busSuperCard = cacheContext.getApplyOnlineOnOff(AppDitDicConstant.onLinOff_busSuperCardList);
					String businessSuperCard = "500003";
					if (busSuperCard != null && StringUtils.isNotEmpty(busSuperCard.getRemark())) {
						businessSuperCard = busSuperCard.getRemark();
					}
					logger.info(LogPrintUtils.printCommonEndLog(tokenId, T1002Req.servId) + "商超金卡：" + businessSuperCard);
					for (int i = 0; i < tmAppMainList.size(); i++) {
						//T1002接口传过来的卡产品为以','分割的字符串
						if (StringUtils.isNotBlank(req.getProductCd())) {
							String[] productCds = req.getProductCd().split(",");
							for (int a = 0; a < productCds.length; a++) {
								String productCd = productCds[a];
								TmAppMain tm1 = tmAppMainList.get(i);
								if (tm1 != null && (StringUtils.equals(tm1.getRtfState(), "L05")
										|| StringUtils.equals(tm1.getRtfState(), "N05")
										|| StringUtils.equals(tm1.getRtfState(), "Q05")
										|| StringUtils.equals(tm1.getRtfState(), "P05"))) {
									resp.setCustomerType("O");//老客户
								}
								logger.info(LogPrintUtils.printAppNoLog(tm1.getAppNo(), tokenId, T1002Req.servId) + "...已有状态为[" + tm1.getRtfState() + "]的产品：" + tm1.getProductCd());
								if (StringUtils.isNotEmpty(productCd) && StringUtils.equals(tm1.getProductCd(), productCd)) {
									//A：无风险;B：低风险 ;C：直接拒绝; D：相同产品重复申请
									resp.setAdmittance("D");//重复申请拒绝
									break;
								}
								//悦动卡 重复申请校验
								List<String> sportList = new ArrayList<String>();
								sportList.add("000007");
								sportList.add("000008");
								if (sportList.contains(tm1.getProductCd()) && StringUtils.isNotEmpty(productCd) && sportList.contains(productCd)) {
									logger.warn(LogPrintUtils.printCommonEndLog(tokenId, null) + "悦动卡，同产品存在审批中的申请件");
									resp.setAdmittance("D");//重复申请拒绝
									break;
								}
								List<String> sameProductList = new ArrayList<String>();
								sameProductList.add("000002");
								sameProductList.add("000003");
								sameProductList.add("000005");
								sameProductList.add("000006");
								//如果当前申请是标准信用卡（包括金卡、白金卡），那么判断该客户是否有审批中的商超金卡
								if (tm1 != null && StringUtils.isNotEmpty(productCd)
										&& sameProductList.contains(productCd)
										&& StringUtils.equals(tm1.getProductCd(), businessSuperCard)) {
//						TmProduct p1 = cacheContext.getProduct(tm1.getProductCd());
									resp.setAdmittance("D");//重复申请拒绝
									break;
//						throw new ProcessException("系统检测存在同类型卡种["+p1.getProductDesc()+"]，请选择其他卡片申请！");
								}
								//如果当前是商超金卡申请，那么判断该客户是否有审批中的标准信用卡（包括金卡、白金卡）
								if (tm1 != null && StringUtils.isNotEmpty(productCd) && StringUtils.equals(productCd, businessSuperCard)
										&& StringUtils.isNotEmpty(tm1.getProductCd())
										&& sameProductList.contains(tm1.getProductCd())) {
//						TmProduct p1 = cacheContext.getProduct(tm1.getProductCd());
									resp.setAdmittance("D");//重复申请拒绝
									break;
//						throw new ProcessException("系统检测存在同类型卡种["+p1.getProductDesc()+"]，请选择其他卡片申请！");
								}
							}
						}
					}
					StringBuilder existsProducts = new StringBuilder();
					List<String> dataList = new ArrayList();
					for (int i = 0; i < tmAppMainList.size(); i++) {
						TmAppMain tm1 = tmAppMainList.get(i);
						if (!dataList.contains(tm1.getProductCd())) {
							dataList.add(tm1.getProductCd());
						}
					}
					for (int i = 0; i < dataList.size(); i++) {
						if (i == 0) {
							existsProducts.append(dataList.get(i));
						} else {
							existsProducts.append(",");
							existsProducts.append(dataList.get(i));
						}
					}
					resp.setExitsProducts(existsProducts.toString());
				}

			/*if(StringUtils.isNotEmpty(req.getProductCd())) {
				parameter.put("productCd", req.getProductCd());
				List<TmMirCard> tmMirCardList1 = tmMirCardDao.getTmMirCardList(parameter);
				if (CollectionUtils.sizeGtZero(tmMirCardList1)) {
					logger.info(LogPrintUtils.printCommonEndLog(tokenId, T1002Req.servId) + "TmMirCard-list:" + (tmAppMainList.size()));
					resp.setAdmittance("D");//重复申请拒绝
				}
			}*/
		} catch (Exception e) {
			logger.error("准入及老客户查询处理失败", e);
			throw new ProcessException("准入及老客户查询处理失败,请联系客服");
		}finally {
			logger.info("PID-["+tokenId+"]...准入及老客户查询开始,耗时("+(System.currentTimeMillis()-start)
					+")...返回客户["+codeMarkUtils.makeIDCardMask(req.getIdNo())+"]检查数据："+resp.toString());
		}
		return resp;
	}
}
