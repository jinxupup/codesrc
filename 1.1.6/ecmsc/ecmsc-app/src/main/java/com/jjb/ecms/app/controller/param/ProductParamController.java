package com.jjb.ecms.app.controller.param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SortOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.acl.biz.utils.SystemAuditHistoryUtils;
import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.service.common.CommonService;
import com.jjb.ecms.biz.service.param.ProductParamService;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.ecms.infrastructure.TmProductBranch;
import com.jjb.ecms.infrastructure.TmProductCardFace;
import com.jjb.ecms.infrastructure.TmProductProcess;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;

/**
 *
 * @Description: 产品参数管理
 * @author JYData-R&D-HN
 * @date 2016年9月11日 下午2:59:39
 * @version V1.0
 */
@Controller
@RequestMapping("/productParam")
public class ProductParamController extends BaseController{

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ProductParamService productParamService;
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private CommonService commonService;
	@Autowired
	private SystemAuditHistoryUtils systemAuditHistoryUtils;

	/*
	 * 进入参数页面
	 */
	@RequestMapping("/page")
	public String page(){
		return "/applyParam/product/productParam_V1.ftl";
	}

	/*
	 * 获取所有的系统参数配置
	 */
	@ResponseBody
	@RequestMapping("/list")
	public Page<TmProduct> list(TmProduct tmProduct){

		Page<TmProduct> page = getPage(TmProduct.class);
		page.setSortName("id");
		page.setSortOrder(SortOrder.ASCENDING.name());
		page = productParamService.getProductPage(page,tmProduct);
		TmAclDict tmAclDictOld = new TmAclDict();
		tmAclDictOld.setType("CardFace");
		List<TmAclDict> aclDicts = commonService.geTmAclDictByParam(tmAclDictOld);
		setAttr("cardFaceList", aclDicts);
		return page;
	}
	/*
	 * 获取所有的系统参数配置
	 */
	@ResponseBody
	@RequestMapping("/cardFaceList")
	public Page<TmAclDict> list(){

		TmAclDict tmAclDictOld = new TmAclDict();
		tmAclDictOld.setType("CardFace");
		List<TmAclDict> aclDicts = commonService.geTmAclDictByParam(tmAclDictOld);
		Page<TmAclDict> page = new Page<TmAclDict>();
		page.setPageNumber(1);
		page.setPageSize(100);
		page.setTotal(aclDicts==null ? 0:aclDicts.size());
		page.setRows(aclDicts);
		return page;
	}

	/**
	 * 跳转到产品设置页面
	 * @return
	 */
	@RequestMapping("/pruductAddPage")
	public String pruductAddPage(){
		return "/applyParam/product/pruductAddPage_V1.ftl";
	}
	/**
	 * 同步核心(bmp)产品参数
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/syncBmpPrdoucts")
	public Json syncBmpPrdoucts(){
		Json j = Json.newSuccess();
		try {/*
			Long start = System.currentTimeMillis();
			com.jjb.acl.gmp.sdk.OrganizationContextHolder.setCurrentOrg(OrganizationContextHolder.getOrg());
			Map<String, Product> productMap = parameterFacility.retrieveParameterObject(Product.class);

			if(productMap==null || productMap.size()<1){
				throw new ProcessException("未查询到核心产品列表，请确认核心产品配置是否正常或稍后再试!");
			}

			String defProcessKey = activitiService.getDefProcess();
			String defPointRule = commonService.getDefPointRule();
			logger.info("PID-["+start+"]...开始同步BMP核心产品参数,预计同步总数量"+(productMap==null ? 0:productMap.size())+"，操作人["+OrganizationContextHolder.getUserNo()+"]");
			for (Entry<String, Product> enty : productMap.entrySet()) {
				if(enty==null){
					continue;
				}
				boolean isExit = true;
				//过滤掉 卡产品状态为失效的卡产品
				Product product = enty.getValue();
				logger.info("PID-["+start+"]...开始同步产品["+product.productCode+"-"+product.description+"]相关参数，操作人["+OrganizationContextHolder.getUserNo()+"]");
				if(product.isCardProductStatus == null || product.isCardProductStatus.equals(Indicator.Y)){
					logger.info("卡产品状态失效，不进行数据同步，继续下一条。。。");
					continue;
				}
				TmProduct prod = cacheContext.getProduct(product.productCode);
				if(prod==null){
					logger.info("PID-["+start+"]...该产品["+product.productCode+"-"+product.description+"]在当前系统中不存在，操作人["+OrganizationContextHolder.getUserNo()+"]");
					prod = new TmProduct();
					isExit=false;
				}
				prod.setOrg(OrganizationContextHolder.getOrg());//机构号
				prod.setProductCd(product.productCode);//产品代码
				prod.setProductDesc(product.description);//产品名称描述
				prod.setProductType(product.productType == null ? "" :product.productType.name());//产品类别
				if(StringUtils.isNotEmpty(prod.getProductStatus()) || !product.isCardProductStatus.equals("C")){
					if(product.isCardProductStatus != null && product.isCardProductStatus.equals(Indicator.N)){
						prod.setProductStatus("A");//产品状态
					}else if(product.isCardProductStatus != null && product.isCardProductStatus.equals(Indicator.Y)){
						prod.setProductStatus("B");//产品状态
					}else if(product.isCardProductStatus==null){
						prod.setProductStatus("B");//产品状态
					}
				}
				prod.setBrand(product.brand == null ? "" : product.brand.name());//卡品牌
				prod.setCardClass(product.cardClass == null ? "" : product.cardClass.name());//卡等级
				prod.setJointCode(product.jointCode);//联名卡代码
				prod.setJointDesc(product.jointDescription);//联名卡描述
				prod.setBin(product.bin);//卡BIN
				int cardLen = 16;
				int cardBinLen = 0;
				if(StringUtils.isNotEmpty(product.bin)){
					cardBinLen = product.bin.length();
				}
				int cardrangeLen = 0;
				if(StringUtils.isNotEmpty(product.cardnoRangeCeil)){
					cardrangeLen = product.cardnoRangeCeil.length();
				}
				//卡号长度登录卡bin+卡号上限长度+1位验证位
				cardLen = cardBinLen+cardrangeLen+1;
				prod.setCardNoLen(cardLen);//卡号长度
				prod.setCardNoRangeCeil(product.cardnoRangeCeil);//卡号段上限
				prod.setCardNoRangeFlr(product.cardnoRangeFlr);//卡号段下限
				prod.setNewCardVaildPeriod(product.newCardValidPeriod);//新卡有效期(年)
				prod.setRenewVaildPeriod(product.renewValidPeriod);//续卡有效期(年)
				prod.setRenewCheckMethod(product.renewCheckMethod==null ? "":product.renewCheckMethod.name());//到期续卡审核方式
				prod.setRateFloanInd(product.rateFloanInd==null ? "":product.rateFloanInd.name());//是否支持客户浮动利率
				prod.setFabricationInd(product.fabricationInd==null ? "":product.fabricationInd.name());//是否生产制卡文件
				prod.setIsCardSet(product.isCardSet==null ? "":product.isCardSet.name());//是否套卡
				prod.setIsEtc(product.isEtc==null ? "":product.isEtc.name());//是否ETC卡
				prod.setSubCardType(product.subCardType==null ? "":product.subCardType.name());//卡类型子类型
				if(prod.getCreateTime()==null){
					prod.setCreateTime(new Date());//创建时间
				}
				if(prod.getCreateUser()==null){
					prod.setCreateUser(OrganizationContextHolder.getUserNo());//创建人
				}
				prod.setUpdateTime(new Date());//更新时间
				prod.setUpdateUser(OrganizationContextHolder.getUserNo());//更新人
				if(StringUtils.isEmpty(prod.getProcdefKey())){
					prod.setProcdefKey(defProcessKey);//流程KEY
				}
				if(StringUtils.isEmpty(prod.getPointRule())){
					prod.setPointRule(defPointRule);//使用评分表
				}
	//			prod.setSupProductCd();//同类型上级产品
				prod.setApprovalMaximum(product.maxCreditLimit);//最高审批额度
				prod.setIfEnableAttachCard(product.isAllowApplySupp.name());//是否支持副卡
				if(StringUtils.isEmpty(prod.getIsCellPboc())){
					prod.setIsCellPboc("N");//是否联机查询人行
				}

				if(StringUtils.isEmpty(prod.getIfRealtimeIssuing())){
					prod.setIfRealtimeIssuing("N");//是否实时发卡
				}
				if(StringUtils.isEmpty(prod.getIfEnableHairpin())){
					prod.setIfEnableHairpin("N");//是否独立发卡
				}
				if(StringUtils.isEmpty(prod.getIsInstalment())){
					prod.setIsInstalment("N");//是否支持分期
				}
				if(StringUtils.isEmpty(prod.getLendingMethod())){
					prod.setLendingMethod(LendingType.B.name());//默认放款方式
				}
				if(StringUtils.isEmpty(prod.getIfUseCustomRate())){
					prod.setIfUseCustomRate("N");
				}

				List<String> branchList = new ArrayList<String>();
				//该卡产品对应的分行组号
				String branchGroup = product.branchGroup;
				//如果产品未配置分组，则有所有产品权限
				if(branchGroup!=null && !branchGroup.trim().equals("")){
					//根据该卡产品对应的分行组号得到分行组管理下的参数信息
					BranchGroup bg = parameterFacility.loadParameter(branchGroup, BranchGroup.class);
					if(bg!=null){
						branchList = bg.branchList;
					}
				}
				if(CollectionUtils.sizeGtZero(branchList)){
					logger.info("卡产品[{}]配置分行列表：{}，该产品默认为独立发卡产品",prod.getProductCd(),branchList.toString());
					prod.setIfEnableHairpin("Y");//是否独立发卡---配置分行列表，则默认为独立发卡产品
					for (int i = 0; i < branchList.size(); i++) {
						if (StringUtils.isNotEmpty(branchList.get(i))) {
							 TmAclBranch branch = branchService.getTmAclBranch(branchList.get(i));
							 if(branch == null)
								 throw new ProcessException("网点机构["+branchList.get(i)+"]不存在");
							 logger.info("卡产品[{}]配置支持分行[{}]，该分行独立发卡标识：{}，修改其默认为独立发卡分行",prod.getProductCd(),branchList.get(i),branch.getIfEnableHairpin());
							 if(!"Y".equals(branch.getIfEnableHairpin())){
								 branch.setIfEnableHairpin("Y");
								 branchService.editTmAclBranch(branch);
							 }

						}
					}
				}else {
					prod.setIfEnableHairpin("N");
				}
				productParamService.setTmProductBranch(product.productCode,branchList);//产品对于网点机构
				productParamService.setTmProductCardFace(product.productCode, product.phyCardCdList);//卡面
				if(isExit){
					productParamService.updateTmProduct(prod);
				}else{
					productParamService.saveTmProduct(prod);
				}
			}
			logger.info("PID-["+start+"]...结束同步BMP核心产品参数，操作人["+OrganizationContextHolder.getUserNo()+"]，耗时("+(System.currentTimeMillis()-start)+")");
		*/}catch (Exception e) {
			logger.error("同步BMP核心产品参数失败:",e);
			j.setFail("同步BMP核心产品参数失败:" + e.getMessage());
		}
		return j;
	}

	/**
	 * 新增核心产品
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/productAdd")
	public Json productAdd(TmProduct tmProduct,String cardFace, String owningBranch){
		Json j = Json.newSuccess();
		try{
			TmProduct tp1 = new TmProduct();
			tp1.setProductCd(tmProduct.getProductCd());
			List<TmProduct> dbPros = productParamService.getTmProductByParam(tp1);
			if(dbPros!=null && dbPros.size()>0){
				j.setFail("卡产品代码["+tmProduct.getProductCd()+"]已存在，请重新设置");
			}else{
				//保存审计历史
				systemAuditHistoryUtils.saveSystemAudit("卡产品代码: "+tmProduct.getProductCd(),"产品管理","SAVE","",tmProduct.convertToMap().toString());
				productParamService.saveTmProduct(tmProduct);
				if(StringUtils.isNotEmpty(cardFace)){
					List<String> cfls = new ArrayList<String>();
					String [] ss = cardFace.split(",");
					for (int i = 0; i < ss.length; i++) {
						cfls.add(ss[i]);
					}
					productParamService.setTmProductCardFace(tmProduct.getProductCd(), cfls);
				}
				if (StringUtils.isNotEmpty(owningBranch)) {
					List<String> owningBranchList = new ArrayList<String>();
					String [] string = owningBranch.split(",");
					for (int i = 0; i < string.length; i++) {
						owningBranchList.add(string[i]);
					}
					productParamService.setTmProductBranch(tmProduct.getProductCd(), owningBranchList);
				}
			}

		}catch(Exception e){
			logger.error("配置卡产品失败", e);
			j.setFail(e.getMessage());
		}
		return j;
	}
	/**
	 * 跳转到产品设置页面
	 * @return
	 */
	@RequestMapping("/pruductUpdatePage")
	public String pruductUpdatePage(TmProduct tmProduct){
		tmProduct = productParamService.getTmProductById(tmProduct.getId());
		setAttr("tmProduct", tmProduct);
		List<TmProductCardFace> pcfs = productParamService.getTmCardFaceByProductCd(tmProduct.getProductCd());
		String cardFaceStr = "";
		if(CollectionUtils.isNotEmpty(pcfs)){
			for (int i = 0; i < pcfs.size(); i++) {
				TmProductCardFace pcf2 = pcfs.get(i);
				cardFaceStr = cardFaceStr + "," +pcf2.getCardFaceCd();
			}
			if(StringUtils.isNotEmpty(cardFaceStr) && cardFaceStr.length()>0){
				cardFaceStr = cardFaceStr.substring(1, cardFaceStr.length());
			}

		}
		setAttr("cardFace",cardFaceStr);

		List<TmProductBranch> pbList = productParamService.getTmProductBranchByProductCd(tmProduct.getProductCd());
		String branchStr = "";
		if (CollectionUtils.isNotEmpty(pbList)) {
			for (int i = 0; i < pbList.size(); i++) {
				TmProductBranch pb = pbList.get(i);
				branchStr = branchStr + "," + pb.getBranchCode();
			}
			if(StringUtils.isNotEmpty(branchStr) && branchStr.length()>0){
				branchStr = branchStr.substring(1, branchStr.length());
			}
		}
		setAttr("owningBranch",branchStr);
		return "/applyParam/product/pruductUpdatePage_V1.ftl";
	}
	/**
	 * 跳转到产品进件渠道流程配置页面
	 * @return
	 */
	@RequestMapping("/setPruductProcessPage")
	public String setPruductProcessPage(TmProduct tmProduct){
		LinkedHashMap<Object, Object> appSourceMap = cacheContext.getAclDictByType(AppConstant.APP_SOURCE);
		tmProduct = productParamService.getTmProductById(tmProduct.getId());
		TmProductProcess pro = new TmProductProcess();
		pro.setProductCd(tmProduct.getProductCd());
		List<TmProductProcess> processList = productParamService.getTmProductDitProcByParam(pro);
		Map<String,TmProductProcess> processMap = new HashMap<String, TmProductProcess>();
		if(CollectionUtils.isNotEmpty(processList)) {
			for (int i = 0; i < processList.size(); i++) {
				TmProductProcess process = processList.get(i);
				if(process!=null && StringUtils.isNotEmpty(process.getAppSource()))
					processMap.put(process.getAppSource(), process);
			}			
		}
		setAttr("tmProduct", tmProduct);
		setAttr("appSourceMap", appSourceMap);
		setAttr("processMap", processMap);
		return "applyParam/product/pruductProcessPage_V1.ftl";
	}

	/*
	 * 获取产品流程参数信息
	 */
	@RequestMapping("/productProcessList")
	public String productlist(TmProductProcess tmProductProcess){
		List<TmProductProcess> dbPros = productParamService.getTmProductDitProcByParam(tmProductProcess);
		setAttr("processList", dbPros);
		return "applyParam/product/productProcdefKeyPage_V1.ftl";
	}

	/**
	 * 删除产品渠道对应流程
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteProductProcess")
	public Json deleteProductProcess(TmProductProcess tmProductDitProc){
		Json j = Json.newSuccess();
		try{
			List<TmProductProcess> dbPros = productParamService.getTmProductDitProcByParam(tmProductDitProc);
			if(dbPros==null || dbPros.size()==0){
				j.setFail("卡产品代码对应渠道流程已不存在");
			}else{
				productParamService.deleteTmProductProcess(tmProductDitProc);
				//记录审计历史
				systemAuditHistoryUtils.saveSystemAudit("卡产品代码: "+dbPros.get(0).getProductCd(),"产品管理","DELETE",dbPros.toString(),"");
			}

		}catch(Exception e){
			logger.error("配置卡产品失败", e);
			j.setFail("删除失败" + e.getMessage());
		}
		return j;
	}
	/**
	 * 产品进件渠道流程配置页面提交
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/setProductProcessSubmimt")
	public Json basicCheckSubmit(TmProductProcess tmProductDitProc){
		Json j = Json.newSuccess();
		try {
			TmProductProcess pro = new TmProductProcess();
			pro.setProductCd(tmProductDitProc.getProductCd());
			/*判断是否用默认流程*/
			if(StringUtils.equals(tmProductDitProc.getIsDefault(),"Y")) {
				pro.setIsDefault("Y");
				List<TmProductProcess> dbPros = productParamService.getTmProductDitProcByParam(pro);
				/*判断是否已有默认流程,若有,去除原有默认流程的默认标志*/
				if (dbPros != null && dbPros.size() > 0){
					for (TmProductProcess prod : dbPros) {
						prod.setIsDefault(null);
						productParamService.updateTmProductProc(prod);
					}
				}
				pro.setIsDefault(null);
				tmProductDitProc.setIsDefault("Y");
			}else{
				tmProductDitProc.setIsDefault(null);
			}
			pro.setAppSource(tmProductDitProc.getAppSource());
			List<TmProductProcess> dbPros = productParamService.getTmProductDitProcByParam(pro);
			if (dbPros != null && dbPros.size() > 0) {
				for (TmProductProcess prod : dbPros) {
					tmProductDitProc.setId(prod.getId());
				}
				productParamService.updateTmProductProc(tmProductDitProc);
			} else {
				productParamService.saveTmProductProc(tmProductDitProc);
			}
			//保存审计历史
			systemAuditHistoryUtils.saveSystemAudit("卡产品代码: "+tmProductDitProc.getProductCd(),"产品管理","SAVE","",tmProductDitProc.convertToMap().toString());
		}catch(Exception e){
			logger.info("产品进件流程配置出现异常，流程配置失败",e);
			j.setFail(e.getMessage());
		}
		return j;
	}
	/**
	 * 修改核心产品
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/productUpdate")
	public Json productUpdate(TmProduct tmProduct,String cardFace, String owningBranch){
		Json j = Json.newSuccess();
		try{
			TmProduct tmProductById = productParamService.getTmProductById(tmProduct.getId());
			productParamService.updateTmProduct(tmProduct);
			//保存审计历史
			systemAuditHistoryUtils.saveSystemAudit("卡产品代码:" + tmProduct.getProductCd(),"产品管理","UPDATE",tmProductById.convertToMap().toString(),tmProduct.convertToMap().toString());
			if(StringUtils.isNotEmpty(cardFace)){
				List<String> cfls = new ArrayList<String>();
				String [] ss = cardFace.split(",");
				for (int i = 0; i < ss.length; i++) {
					cfls.add(ss[i]);
				}
				productParamService.setTmProductCardFace(tmProduct.getProductCd(), cfls);
			}
			if (StringUtils.isNotEmpty(owningBranch)) {
				List<String> owningBranchList = new ArrayList<String>();
				String [] string = owningBranch.split(",");
				for (int i = 0; i < string.length; i++) {
					owningBranchList.add(string[i]);
				}
				productParamService.setTmProductBranch(tmProduct.getProductCd(), owningBranchList);
			}
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		return j;
	}
	
	/**
	 * 同步核心(bmp)产品参数
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/syncBmpCardFaces")
	public Json syncBmpCardFaces(){
		Json j = Json.newSuccess();
		try {/*
			Long start = System.currentTimeMillis();
			try {
				com.jjb.acl.gmp.sdk.OrganizationContextHolder.setCurrentOrg(OrganizationContextHolder.getOrg());
				
				Map<String, PhyCardCd> phyCardCdMap = parameterFacility.retrieveParameterObject(PhyCardCd.class);
				if(phyCardCdMap==null || phyCardCdMap.size()<1){
					throw new ProcessException("未查询到核心卡片配置列表，请确认核心卡面配置是否正常或稍后再试!");
				}
				TmAclDict tmAclDictOld = new TmAclDict();
				tmAclDictOld.setType("CardFace");
				List<TmAclDict> oldList = commonService.geTmAclDictByParam(tmAclDictOld);
				logger.info("PID-["+start+"]...开始同步BMP核心卡面列表参数,预计同步总数量"+(phyCardCdMap==null ? 0:phyCardCdMap.size())+"，操作人["+OrganizationContextHolder.getUserNo()+"]");
			
				for (Entry<String, PhyCardCd> enty : phyCardCdMap.entrySet()) {
					if(enty==null || enty.getValue()==null || enty.getValue().pyhCd==null){
						logger.error("PID-["+start+"]...核心取过来的卡面信息为空"+enty);
						continue;
					}
					//过滤掉 卡产品状态为失效的卡产品
					PhyCardCd cardFace = enty.getValue();
					TmAclDict aclDict = null;
					logger.info("PID-["+start+"]...开始同步卡面列表["+cardFace.pyhCd+"-"+cardFace.description+"]相关参数，操作人["+OrganizationContextHolder.getUserNo()+"]");
					boolean isExit = false;
					if(oldList!=null && oldList.size()>0){
						for (int i = 0; i < oldList.size(); i++) {
							TmAclDict oldDict = oldList.get(i);
							if(oldDict!=null && oldDict.getCode()!=null
									&& oldDict.getCode().equals(cardFace.pyhCd)){
								aclDict = oldDict;
								logger.info("PID-["+start+"]...卡面["+cardFace.pyhCd+"]已存在,做更新操作");
								isExit=true;
								oldList.remove(i);
								continue;
							}
						}
					}
					if(aclDict==null || aclDict.getId()==null 
							&& aclDict.getCode()==null || aclDict.getCode().equals("")){
						aclDict = new TmAclDict();
					}
					aclDict.setOrg(OrganizationContextHolder.getOrg());
					aclDict.setType("CardFace");
					aclDict.setTypeName("卡面代码");
					aclDict.setCode(cardFace.pyhCd);//卡面代码
					aclDict.setCodeName(cardFace.description);//卡面中文描述
					aclDict.setValue(cardFace.mediaType==null ? "":cardFace.mediaType.name());//卡面介质类型
					aclDict.setValue2(cardFace.icCardType==null ? "":cardFace.icCardType.name());//芯片卡类型
					aclDict.setValue3(cardFace.changePyhCd);//续卡换卡卡面代码
					if(StringUtils.isEmpty(aclDict.getIfCanDel())){
						aclDict.setIfCanDel("N");
					}
					if(StringUtils.isEmpty(aclDict.getIfUsed())){
						aclDict.setIfUsed("Y");
					}
					if(StringUtils.isEmpty(aclDict.getCreateBy())){
						aclDict.setCreateBy(OrganizationContextHolder.getUserNo());
					}
					if(aclDict.getCreateDate()==null){
						aclDict.setCreateDate(new Date());
					}
					aclDict.setUpdateBy(OrganizationContextHolder.getUserNo());
					aclDict.setUpdateDate(new Date());
					if(isExit){
						dictService.editTmAclDict(aclDict);
					}else{
						dictService.saveTmAclDict(aclDict);
					}
				}
				if(oldList!=null && oldList.size()>0){
					for (int i = 0; i < oldList.size(); i++) {
						TmAclDict oldDict = oldList.get(i);
						if(oldDict!=null && oldDict.getId()!=null){
							logger.info("PID-["+start+"]...开始删除旧卡面列表["+oldDict.getCode()+"-"+oldDict.getCodeName()
									+"]相关参数，操作人["+OrganizationContextHolder.getUserNo()+"]");
							dictService.deleteTmAclDict(oldList.get(0).getId());
						}
					}
				}
			} catch (Exception e) {
				logger.error("保存同步后的核心卡面失败", e);
				throw new ProcessException("保存同步后的核心卡面失败"+ e.getMessage());
			}
			
			logger.info("PID-["+start+"]...结束同步BMP核心产品参数，操作人["+OrganizationContextHolder.getUserNo()+"]，耗时("+(System.currentTimeMillis()-start)+")");
		*/}catch (Exception e) {
			logger.error("同步BMP核心产品异常", e);
			j.setFail(e.getMessage());
		}
		return j;
	}
	/**
	 * 修改独立发卡标识
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/ifEnableHairpinChange")
	public LinkedHashMap<Object, Object>  ifEnableHairpinChange(String enable){
		LinkedHashMap<Object,Object> owningBranchSelectMap = new LinkedHashMap<Object, Object>();
		if(StringUtils.isNotEmpty(enable) && enable.equals("Y")){
			owningBranchSelectMap = cacheContext.getBranchMapByParam(AppConstant.independEntity);
		}
		
		//把数据源转换成json
//		Json json = new Json();
//		 String string = "{";
//	        for (Iterator<Entry<Object, Object>> it = owningBranchSelectMap.entrySet().iterator(); it.hasNext();) {
//	            Entry e = (Entry) it.next();
//	            string += "'" + e.getKey() + "':";
//	            string += "'" + e.getValue() + "',";
//	        }
//	        string = string.substring(0, string.lastIndexOf(","));
//	        string += "}";
//	        
//	   json.setMsg(string);
//	   return json;
	   return owningBranchSelectMap;
	}

}
