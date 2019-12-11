package com.jjb.ams.app.controller.apply;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.acl.facility.enums.bus.AppType;
import com.jjb.acl.facility.enums.bus.IdType;
import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.acl.infrastructure.TmAclBranch;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.apply.TmMirCardDao;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.apply.TmMirCardService;
import com.jjb.ecms.biz.service.common.CommonService;
import com.jjb.ecms.biz.service.node.ApplyCardAcctInfoDtoService;
import com.jjb.ecms.biz.service.node.ApplyCardCustrInfoDtoService;
import com.jjb.ecms.biz.service.node.ApplyInfoPreDtoService;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.facility.dto.ApplyCardAcctInfoDto;
import com.jjb.ecms.facility.dto.ApplyCardCustrInfoDto;
import com.jjb.ecms.facility.dto.ApplyInfoPreDto;
import com.jjb.ecms.infrastructure.TmAppAudit;
import com.jjb.ecms.infrastructure.TmAppCustInfo;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.ecms.infrastructure.TmAppnoSeq;
import com.jjb.ecms.infrastructure.TmDitDic;
import com.jjb.ecms.infrastructure.TmMirCard;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.ecms.util.IdentificationCodeUtil;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;


/**
 * @Description: 申请信息预录入
 * @author JYData-R&D-Big T.T
 * @date 2016年8月31日 下午6:43:58
 * @version V1.0  
 */

@Controller
@RequestMapping("/ams_applyPreInput")
public class ApplyPreInputController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ApplyInputService applyInputService;
	@Autowired
	private ApplyInfoPreDtoService applyInfoPreDtoService;
	@Autowired
	private TmMirCardService tmMirCardService ;
	@Autowired
	private ApplyCardCustrInfoDtoService applyCardCustrInfoDtoService;
	@Autowired
	private ApplyCardAcctInfoDtoService applyCardAcctInfoDtoService;
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private CommonService commonService;
	@Autowired
	private TmMirCardDao tmMirCardDao;
	@Autowired
	private ApplyQueryService applyQueryService;
	
	/**
	 * 	申请预录入增加页面
	 * @return
	 */
	@RequestMapping("/ams_applyPreInputpage")
	public String applyPreInputpage(Integer id){

		if(id != null){
			TmAppnoSeq tmAppnoSeq = new TmAppnoSeq();
			tmAppnoSeq.setSeq(id);
			setAttr("tmAppnoSeq", tmAppnoSeq);
		}
		//启用核身页面参数
		setAttr("pageOnOffParamDto", commonService.getApplyPageOnOffParamDto());//页面开关参数
		
		return "/amsTask/applyCC/applyPreInput/applyPreInputPage.ftl";
	}

/**
 * 	申请预录入信息列表
 * @return
 */
	@ResponseBody
	@RequestMapping("/applyPreInputAddlist")
	public Page<ApplyInfoPreDto> applyPreInputAddlist(){
		
		Page<ApplyInfoPreDto> page = getPage(ApplyInfoPreDto.class);
		Query query = page.getQuery();
		query.put("createUser", OrganizationContextHolder.getUserNo());
		query.put("rtfState", RtfState.A05.name());
		page.setQuery(query);
		page = applyInfoPreDtoService.getApplyInfoPreDtoPage(page);
	
		
		return page;
	}
	

	/**
	 * 主卡卡号验证是否有效
	 * @param primCardNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/primCardNoValide")
	public Json primCardNoValide(String primCardNo){
		Json json = Json.newSuccess();
		try {
			String flag = tmMirCardService.validateByPrimCardNo(primCardNo);
			json.setMsg(flag);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("====》》》》主卡卡号"+primCardNo+"验证失败《《《《====", e);
			json.setFail(e.getMessage());
		}
		
		return json;
	}
	
	/**
	 * 判断申请卡产品重复
	 * @param idNo
	 * @param productCd
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/reApplyOfProduct")
	public Json reApplyOfProduct(String idNo, String productCd,String idType,String name) {
		long tokenId = System.currentTimeMillis();
		Json json = Json.newSuccess();
		if (StringUtils.isEmpty(idType) && StringUtils.isEmpty(idNo) && StringUtils.isEmpty(name)) {
			logger.warn("判断申请卡产品重复"+LogPrintUtils.printCommonEndLog(tokenId, null) + "无有效客户信息");
			json.setFail("无有效客户信息");
			return json;
		}
		String nameValue = name == null ? "" : name.toString().trim();//去空格
		String idNoValue = idNo == null ? "" : idNo.toString().trim();//去空格

		// 0：未匹配；1：没有有效的客户信息信息；2：同产品存在审批中的申请件；
		String exitApply = "0";
		TmDitDic ditOnline = cacheContext.getApplyOnlineOnOff(AppDitDicConstant.onLinOff_repeatIdentityVerification);
		if (ditOnline != null && StringUtils.isNotEmpty(ditOnline.getRemark())
				&& ditOnline.getRemark().equals("Y")) {
			//判断申请卡产品重复
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("idType", idType);
			parameter.put("idNo", idNoValue);
//			parameter.put("productCd", nameValue);
			List<TmAppMain> tmAppMainList = applyQueryService.getTmAppMainByParm(parameter);
			if (null != tmAppMainList && tmAppMainList.size() > 0) {
				TmDitDic busSuperCard = cacheContext.getApplyOnlineOnOff(AppDitDicConstant.onLinOff_busSuperCardList);
				String businessSuperCard = "500003";//默认产品代码
				if (busSuperCard != null && StringUtils.isNotEmpty(busSuperCard.getRemark())) {
					businessSuperCard = busSuperCard.getRemark();
				}
				for (int i = 0; i < tmAppMainList.size(); i++) {
					TmAppMain tm1 = tmAppMainList.get(i);
					if (tm1 != null && StringUtils.equals(tm1.getProductCd(), productCd)) {
						logger.warn("判断申请卡产品重复"+LogPrintUtils.printCommonEndLog(tokenId, null) + "同产品存在审批中的申请件");
						json.setFail("卡产品重复申请");
						return json;
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
						TmProduct p1 = cacheContext.getProduct(tm1.getProductCd());
						logger.warn("判断申请卡产品重复"+LogPrintUtils.printCommonEndLog(tokenId, null) + "系统检测存在同类型卡种[" + p1.getProductDesc() + "]，请选择其他卡片申请！");
						json.setFail("系统检测存在同类型卡种["+p1.getProductDesc()+"],请选择其他卡片申请");
						return json;
					}
					//如果当前是商超金卡申请，那么判断该客户是否有审批中的标准信用卡（包括金卡、白金卡）
					if (tm1 != null && StringUtils.equals(productCd, businessSuperCard)
							&& StringUtils.isNotEmpty(tm1.getProductCd())
							&& sameProductList.contains(tm1.getProductCd())) {
						TmProduct p1 = cacheContext.getProduct(tm1.getProductCd());
						logger.warn("系统检测存在同类型卡种[" + p1.getProductDesc() + "]，请选择其他卡片申请！");
						json.setFail("系统检测存在同类型卡种["+p1.getProductDesc()+"]，请选择其他卡片申请！");
						return json;
					}
				}
			}
			Map<String, Object> map = new HashMap<>();
			map.put("idType", idType);
			map.put("idNo", idNoValue);
			map.put("name", nameValue);
			map.put("productCd", productCd);

			List<TmMirCard> tmMirCardList1 = tmMirCardDao.getTmMirCardList(map);
			if (null != tmMirCardList1 && tmMirCardList1.size() > 0) {
				json.setFail("卡产品重复申请");
				return json;
			}
		}
		return json;
	}



	/**
	 * 预录入信息的保存
	 * @param
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/applyPreInputAdd")
	public Json applyPreInputAdd(TmAppCustInfo cust){
		
		if(StringUtils.isNotEmpty(cust.getIdType()) && IdType.I.name().equals(cust.getIdType())
				&& StringUtils.isNotEmpty(cust.getIdNo())){
			Boolean idFlag = IdentificationCodeUtil.isIdentityCode(cust.getIdNo());
			if(!idFlag){
				Json json = Json.newFail();
				json.setS(false);
				json.setMsg("证件号码不正确");
				return json;
			}
		}
		TmAppMain tmAppMain = getBean(TmAppMain.class);
		//重复申请判定 是否开启
		TmDitDic ditRepeatPreApply =  cacheContext.getApplyOnlineOnOff(AppDitDicConstant.onLinOff_repeatIdentityVerification);
		if(ditRepeatPreApply!=null && StringUtils.isNotEmpty(ditRepeatPreApply.getRemark())
				&& ditRepeatPreApply.getRemark().equals("Y")) {
			//增加唯一性校验主附同申以及独立主卡时 姓名+证件类型+证件号码+申请卡产品  唯一性
			if (StringUtils.equals(tmAppMain.getAppType(), "A") || StringUtils.equals(tmAppMain.getAppType(), "B")) {
				String name = cust.getName();
				String idType = cust.getIdType();
				String idNo = cust.getIdNo();
				String productCode = tmAppMain.getProductCd();
				if (StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(idType) && StringUtils.isNotEmpty(idNo) && StringUtils.isNotEmpty(productCode)) {
					Map<String, Object> parameter = new HashMap<String, Object>();
					parameter.put("idNo", idNo);
					parameter.put("productCd", productCode);
					parameter.put("idType", idType);
					parameter.put("name", name);
					List<TmAppMain> tmAppMainList = applyQueryService.getTmAppMainByParm(parameter);
					if (CollectionUtils.sizeGtZero(tmAppMainList)) {
						Json json = Json.newFail();
						json.setMsg("卡产品重复申请!");
						json.setS(false);
						return json;
					}
				}
			}
			//增加唯一性校验独立副卡时 姓名+证件类型+证件号码+主卡卡号  唯一性
			if (StringUtils.equals(tmAppMain.getAppType(), "S")) {
				String name = cust.getName();
				String idType = cust.getIdType();
				String idNo = cust.getIdNo();
				String primCacdNo = cust.getPrimCardNo();
				if (StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(idType) && StringUtils.isNotEmpty(idNo) && StringUtils.isNotEmpty(primCacdNo)) {
					Map<String, Object> parameter = new HashMap<String, Object>();
					parameter.put("idNo", idNo);
					parameter.put("primCacdNo", primCacdNo);
					parameter.put("idType", idType);
					parameter.put("name", name);
					List<TmAppMain> tmAppMainList = applyQueryService.getTmAppMainByParm(parameter);
					if (CollectionUtils.sizeGtZero(tmAppMainList)) {
						Json json = Json.newFail();
						json.setMsg("卡产品重复申请!");
						json.setS(false);
						return json;
					}
				}
			}
		}
		TmAppPrimCardInfo tmAppPrimCardInfo = getBean(TmAppPrimCardInfo.class);
		tmAppMain.setRtfState(RtfState.A05.name());
		String appType = tmAppMain.getAppType();
		
		ApplyInfoDto applyInfoDto = new ApplyInfoDto();
		List<ApplyCardAcctInfoDto> applyCardAcctInfoDtoList = null;
		ApplyCardAcctInfoDto applyCardAcctInfoDto = new ApplyCardAcctInfoDto();
		TmAppAudit audit = new TmAppAudit();
		//去除页面输入的姓名、证件号码首尾空格
		if(StringUtils.isNotBlank(cust.getName())){
			cust.setName(cust.getName().trim());
		}
		if(StringUtils.isNotBlank(cust.getIdNo())){
			cust.setIdNo(cust.getIdNo().trim());
		}
		//后期优化，待办任务和申请进度查询只查TM_APP_MAIN一张表
		if(cust!=null){
			//姓名，证件号码，移动电话,上一任务人
			if(StringUtils.isNotEmpty(cust.getName())){
				tmAppMain.setName(cust.getName());
			}
			if(StringUtils.isNotEmpty(cust.getIdNo())){
				tmAppMain.setIdNo(cust.getIdNo());
			}
			if(StringUtils.isNotEmpty(cust.getIdType())){
				tmAppMain.setIdType(cust.getIdType());
			}
			if(StringUtils.isNotEmpty(cust.getCellphone())){
				tmAppMain.setCellphone(cust.getCellphone());
			}
			applyCardAcctInfoDto.setIdType(cust.getIdType());
			applyCardAcctInfoDto.setIdNo(cust.getIdNo());
		}
		applyCardAcctInfoDtoList = applyCardAcctInfoDtoService.getApplyCardAcctInfoDtoList(applyCardAcctInfoDto);
		
		Map<String,TmAppCustInfo> custMap = new HashMap<String, TmAppCustInfo>();
		
		if(AppType.A.name().equals(appType) || AppType.B.name().equals(appType)){			
			cust.setBscSuppInd(AppConstant.bscSuppInd_B);
		}	
		if(AppType.S.name().equals(appType)){
			//根据主卡卡号，设置附卡的卡产品代码
			if(cust!=null && StringUtils.isNotEmpty(cust.getPrimCardNo())){
				TmMirCard tmMirCard = tmMirCardService.getTmMirCardByCardNo(cust.getPrimCardNo());
				tmAppMain.setProductCd(tmMirCard.getProductCd());
				cust.setBscSuppInd(AppConstant.bscSuppInd_S);
			}else{
				String pIdNo = getPara("pIdNo");
				if(StringUtils.isNotEmpty(pIdNo)){//如果填的是主卡身份证号，则要找到主卡卡号
					ApplyCardCustrInfoDto applyCardCustrInfoDto = new ApplyCardCustrInfoDto();
					applyCardCustrInfoDto.setIdType(IdType.I.name());
					applyCardCustrInfoDto.setIdNo(pIdNo);
					List<ApplyCardCustrInfoDto> list = applyCardCustrInfoDtoService.getApplyCardCustrInfoDtoList(applyCardCustrInfoDto);
					if(CollectionUtils.sizeGtZero(list)){
						for (ApplyCardCustrInfoDto cardCustrInfoDto : list) {
							String bscSuppInd = cardCustrInfoDto.getBscSuppInd();
							String blockCode = cardCustrInfoDto.getBlockCode();
							String productCd = cardCustrInfoDto.getProductCd();
							String cardNo = cardCustrInfoDto.getCardNo();
							if(StringUtils.isNotEmpty(bscSuppInd) && bscSuppInd.equals(AppConstant.bscSuppInd_B) 
								&& StringUtils.isEmpty(blockCode) && StringUtils.isNotEmpty(productCd) && StringUtils.isNotEmpty(cardNo)){
								
								TmProduct tmProduct = cacheContext.getProduct(productCd);
								if(tmProduct!=null){
									
									String subCardType = tmProduct.getSubCardType(); //获取卡的类型
									//判断是不是公务卡，O是公务卡，N是普通卡
									if(StringUtils.equals(subCardType,"N")){
										cust.setPrimCardNo(cardNo);//设置附卡主卡卡号
										tmAppMain.setProductCd(productCd);//设置附卡的卡产品代码
										break;//取到主卡卡号就结束遍历
									}
								}
							}
						}
					}
				}
			}
			cust.setAttachNo(1);
//			tmAppAttachInfoMap.put("attachCust0", cust);
//			applyInfoDto.setTmAppAttachInfoMap(tmAppAttachInfoMap);
		}
		custMap.put(cust.getBscSuppInd()+cust.getAttachNo(), cust);
		applyInfoDto.setTmAppCustInfoMap(custMap);
		audit.setIsRealtimeIssuing("Y");
		//设置老客户标志(初审用)
		if(CollectionUtils.sizeGtZero(applyCardAcctInfoDtoList)){
			audit.setIsOldCust(Indicator.Y.name());
		}else {
			audit.setIsOldCust(Indicator.N.name());
		}
		applyInfoDto.setTmAppMain(tmAppMain);
		if(tmAppPrimCardInfo==null){
			tmAppPrimCardInfo = new TmAppPrimCardInfo();
		}
		tmAppPrimCardInfo.setInputNo(OrganizationContextHolder.getUserNo());
		tmAppPrimCardInfo.setInputName(OrganizationContextHolder.getUserName());
		tmAppPrimCardInfo.setInputTelephone(OrganizationContextHolder.getCellPhone());
		tmAppPrimCardInfo.setInputDate(new Date());
		TmAclBranch branch = null;
		if(OrganizationContextHolder.getBranchCode()!=null){
			branch = cacheContext.getTmAclBranchByCode(OrganizationContextHolder.getBranchCode());
		}
		//网点机构
		if(branch!=null && StringUtils.isNotEmpty(branch.getBranchCode())){
			if(StringUtils.isNotEmpty(branch.getBranchLevel()) && branch.getBranchLevel().equals("1")){
				tmAppPrimCardInfo.setInputBranchOne(branch.getBranchCode());
			}else if(StringUtils.isNotEmpty(branch.getBranchLevel()) && branch.getBranchLevel().equals("2")){
				tmAppPrimCardInfo.setInputBranchTwo(branch.getBranchCode());//当前分行机构
				tmAppPrimCardInfo.setInputBranchOne(branch.getParentCode());//上级总行机构
			}else if(StringUtils.isNotEmpty(branch.getBranchLevel()) && branch.getBranchLevel().equals("3")){
				tmAppPrimCardInfo.setInputBranchThree(branch.getBranchCode());//当前分支行网点机构
				tmAppPrimCardInfo.setInputBranchTwo(branch.getParentCode());//上级分行网点机构或者总行机构
				if(StringUtils.isNotEmpty(branch.getParentCode())){
					TmAclBranch branch1 = cacheContext.getTmAclBranchByCode(branch.getParentCode());
					if(branch1!=null && StringUtils.isNotEmpty(branch.getParentCode())){
						tmAppPrimCardInfo.setInputBranchOne(branch.getParentCode());//上级的上级机构
					}
				}
			}else{
				tmAppPrimCardInfo.setInputBranchThree(branch.getBranchCode());
			}
		}
		applyInfoDto.setTmAppPrimCardInfo(tmAppPrimCardInfo);//获得录入员和录入日期
		
		Json json = Json.newSuccess();
		try{
			applyInputService.saveApplyInput(applyInfoDto);
			json.setObj(applyInfoDto.getTmAppMain());
			json.setS(true);
		}catch(Exception e){
			logger.error("====》》》》预录入信息保存失败《《《《====", e);
			json.setFail(e.getMessage());
			json.setS(false);
		}
		
		return json;
	}
		
	/**
	 * 历史申请信息记录查询
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/historyApplyList")	
	public Page<ApplyInfoPreDto> historyApplyList(ApplyInfoPreDto applyInfoPreDto){
			
		Page<ApplyInfoPreDto> page = new Page<ApplyInfoPreDto>();
		Query query = page.getQuery();
		query.put("appNo", applyInfoPreDto.getAppNo());
		query.put("idType", applyInfoPreDto.getIdType());
		query.put("idNo", applyInfoPreDto.getIdNo());
		page.setQuery(query);
		page = applyInfoPreDtoService.getApplyInfoPreDtoPage(page);
		
		return page;
	}
		
	/**
	 * 已申请的卡记录信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getCardList")
	public Page<ApplyCardAcctInfoDto> getCardList(String idType , String idNo){
		
		Query query = new Query();
		query.put("idType", idType);
		query.put("idNo", idNo);
		
		Page<ApplyCardAcctInfoDto> page = getPage(ApplyCardAcctInfoDto.class);
		page.setQuery(query);
		page = applyCardAcctInfoDtoService.getApplyCardAcctInfoDtoPage(page);
		
		return page;
	}
	
	/**
	 * 删除预录入申请信息
	 * @param
	 * @return
	 */
	
	@ResponseBody

	@RequestMapping("/delete")
	public Json delect(ApplyInfoPreDto applyInfoPreDto){
		 
		Json json = Json.newSuccess();
		try {

			applyInputService.deleteApplyInput(applyInfoPreDto);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("====》》》》预录入申请信息件"+applyInfoPreDto.getAppNo()+"删除操作失败《《《《====", e);
			json.setFail(e.getMessage());
		}
		
		return json;
	}
	
}