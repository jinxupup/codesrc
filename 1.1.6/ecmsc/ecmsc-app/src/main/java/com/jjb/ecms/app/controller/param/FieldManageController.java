package com.jjb.ecms.app.controller.param;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.acl.biz.service.DictService;
import com.jjb.acl.biz.utils.SystemAuditHistoryUtils;
import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.ecms.biz.service.param.TmFieldProductService;
import com.jjb.ecms.biz.service.param.TmFieldService;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.facility.dto.FieldProductDto;
import com.jjb.ecms.infrastructure.TmField;
import com.jjb.ecms.infrastructure.TmFieldProduct;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * @description 页面字段管理controller
 * @author H.N
 * @date 2017年9月4日09:32:05
 */
@Controller
@RequestMapping("/fieldManage")
public class FieldManageController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TmFieldService tmFieldService;
	@Autowired
	private TmFieldProductService tmFieldProductService;
	
	@Autowired
	private DictService dictService;
	@Autowired
	private SystemAuditHistoryUtils systemAuditHistoryUtils;


	/**
	 * 字段管理页面
	 * @return
	 */
	@RequestMapping("/applyFieldPage")
	public String applyFieldPage(){
		
		return "/applyParam/fieldManage/applyFieldPage.ftl";
	}
	
	/**
	 * 卡产品关联字段页面
	 * 字段配置说明：
	 * 联动必输项必须在备注里配置为false，联机进件需要过滤联动必须项；
	 * 联系人字段必须在备注里配置0|1，用于区分是亲属|其他联系人；
	 * 字段在域中显示的顺序必须在sort配置；
	 * 复核项只配主卡或附卡即可；
	 * @return
	 */
	@RequestMapping("/fieldProductPage")
	public String fieldProductPage(String productCd){
		logger.info("开始进入卡产品【{}】关联字段页面",productCd);
		LinkedHashMap<String, TmFieldProduct> tmFieldProductMap = new LinkedHashMap<String, TmFieldProduct>();
		LinkedHashMap<String, List<TmField>> tabListMap = new LinkedHashMap<String, List<TmField>>();
		try {
			TmField tmField = new TmField();
			tmField.setOrg(OrganizationContextHolder.getOrg());
			tmField.setIfUsed(Indicator.Y.name());//只查启用的字段
			List<TmField> tmFieldList = tmFieldService.getTmFieldList(tmField);
			if(CollectionUtils.sizeGtZero(tmFieldList)){
				TmFieldProduct tmFieldProduct = new TmFieldProduct();
				List<TmField> tmAppMains = new ArrayList<TmField>();
				List<TmField> tmAppAudits = new ArrayList<TmField>();
				List<TmField> tmAppCusts = new ArrayList<TmField>();
//				List<TmField> tmAppPrimApplicantInfos = new ArrayList<TmField>();
//				List<TmField> tmAppAttachApplierInfos = new ArrayList<TmField>();
				List<TmField> tmAppPrimAnnexEvis = new ArrayList<TmField>();
				List<TmField> tmAppContacts = new ArrayList<TmField>();
				List<TmField> tmAppPrimCardInfos = new ArrayList<TmField>();
				List<TmField> tmExtRiskInput = new ArrayList<TmField>();
//				List<TmField> tmEtcCars = new ArrayList<TmField>();
//				List<TmField> tmAppCardfaceInfos = new ArrayList<TmField>();
				List<TmField> tmAppInstalLoans = new ArrayList<TmField>();
				for (TmField field : tmFieldList) {
					tmFieldProductMap.put(field.getFieldEn().toString(), tmFieldProduct);
					String tabName = field.getTabName();
					if(StringUtils.isNotBlank(tabName)){
						if(AppConstant.TmAppMain.equals(tabName)){
							tmAppMains.add(field);
						}else if(AppConstant.TmAppAudit.equals(tabName)){
							tmAppAudits.add(field);
						}else if(AppConstant.TmAppCustInfo.equals(tabName)){
							tmAppCusts.add(field);
						}/*else if(AppConstant.TmAppPrimApplicantInfo.equals(tabName)){
							tmAppPrimApplicantInfos.add(field);
						}else if(AppConstant.TmAppAttachApplierInfo.equals(tabName)){
							tmAppAttachApplierInfos.add(field);
						}*/else if(AppConstant.TmAppPrimAnnexEvi.equals(tabName)){
							tmAppPrimAnnexEvis.add(field);
						}else if(AppConstant.TmAppContact.equals(tabName)){
							tmAppContacts.add(field);
						}else if(AppConstant.TmAppPrimCardInfo.equals(tabName)){
							tmAppPrimCardInfos.add(field);
						}else if(AppConstant.TmExtRiskInput.equals(tabName)){
							tmExtRiskInput.add(field);
						}/*else if(AppConstant.TmEtcCar.equals(tabName)){
							tmEtcCars.add(field);
						}else if(AppConstant.TmAppCardfaceInfo.equals(tabName)){
							tmAppCardfaceInfos.add(field);
						}*/else if(AppConstant.TmAppInstalLoan.equals(tabName)){
							tmAppInstalLoans.add(field);
						}
					}
				}
				tabListMap.put("map0", tmAppMains);
				tabListMap.put("map1", tmAppAudits);
				tabListMap.put("map2", tmAppCusts);
				tabListMap.put("map3", tmAppPrimAnnexEvis);
				tabListMap.put("map4", tmAppContacts);
				tabListMap.put("map5", tmAppPrimCardInfos);
				tabListMap.put("map6", tmExtRiskInput);
//				tabListMap.put("map6", tmAppCardfaceInfos);
//				tabListMap.put("map7", tmEtcCars);
//				tabListMap.put("map8", tmAppInstalLoans);//汽车分期信息
			}
			setAttr("tabListMap", tabListMap);
			setAttr("userNo", OrganizationContextHolder.getUserNo());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("卡产品[{}]关联字段页面展示发生异常",productCd);
			throw new ProcessException("进入卡产品【"+ productCd +"】关联字段页面发生异常",e.getMessage());
		}
		if(StringUtils.isNotBlank(productCd)){
			setAttr("productCd", productCd);
			try {
				TmFieldProduct tmFieldProduct = new TmFieldProduct();
				tmFieldProduct.setProductCd(productCd);
				List<TmFieldProduct> tmFieldProductList = tmFieldProductService.getTmFieldProductList(tmFieldProduct);
				if(CollectionUtils.sizeGtZero(tmFieldProductList)){
					for (TmFieldProduct fieldProduct : tmFieldProductList) {
						tmFieldProductMap.put(fieldProduct.getFieldEn(), fieldProduct);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("进入卡产品【{}】关联字段页面发生异常",productCd);
				throw new ProcessException("进入卡产品【"+ productCd +"】关联字段页面发生异常",e.getMessage());
			}
		}
		setAttr("tmFieldProductMap", tmFieldProductMap);
		
		return "/applyParam/fieldManage/fieldProductPage.ftl";
	}

	/**
	 * 查询当前产品已配置的字段数据
	 * @param productCd
	 * @return
	 */
	@RequestMapping("/fieldProductSetPage")
	public String fieldProductSetPage(String productCd){
		logger.info("开始进入卡产品【{}】关联字段页面",productCd);
		LinkedHashMap<String, List<FieldProductDto>> tmFieldProductMap = new LinkedHashMap<String, List<FieldProductDto>>();
		TmFieldProduct tmFieldProduct = new TmFieldProduct();
		tmFieldProduct.setProductCd(productCd);
		List<FieldProductDto> tfpcList = tmFieldProductService.getFieldProductDtoList2(tmFieldProduct);
		if(CollectionUtils.sizeGtZero(tfpcList)){
			for (int i = 0; i < tfpcList.size(); i++) {
				FieldProductDto cust = tfpcList.get(i);
				if(cust!=null){
					String fieldRegion = "未配置";//如果为空，则标识默认为：未配置"
//					if(StringUtils.isNotEmpty(cust.getTabDesc())){
//						fieldRegion = cust.getTabDesc();
//					}
					if(cust.getFieldRegion()!=null && cust.getFieldRegion().startsWith("A")){
						fieldRegion = "申请主页";
					}else if(cust.getFieldRegion()!=null && cust.getFieldRegion().startsWith("B")){
						fieldRegion = "附件证明";
					}else if(cust.getFieldRegion()!=null && cust.getFieldRegion().startsWith("C") || cust.getTabName()==null){
						fieldRegion = "其他";
					}
					List<FieldProductDto> list = new ArrayList<FieldProductDto>();
					if(tmFieldProductMap.containsKey(fieldRegion)){
						list = tmFieldProductMap.get(fieldRegion);
						list.add(cust);
					}else{
						list.add(cust);
					}
					tmFieldProductMap.put(fieldRegion, list);
				}
			}
		}
		setAttr("productCd", productCd);
		setAttr("userNo", OrganizationContextHolder.getUserNo());
		setAttr("tmFieldProductMap", tmFieldProductMap);
		
		return "/applyParam/fieldManage/fieldProductPage_V2.ftl";
	}
	/**
	 * 新增/修改字段页面
	 * @return
	 */
	@RequestMapping("/editFieldPage")
	public String editFieldPage(Integer fieldId){
		logger.info("开始进入字段【fieldId={}】编辑页面",fieldId);
		if(fieldId != null){
			try {
				TmField tmField = tmFieldService.getTmFieldById(fieldId);
				if(tmField != null){
					String componentType = tmField.getComponentType();
					if(StringUtils.isNotBlank(componentType)){
						if("select".equals(tmField.getComponentType()) || "ajaxSelect".equals(tmField.getComponentType())){
							setAttr("options", tmField.getDictType());
							setAttr("change", tmField.getFieldChange());
							if("ajaxSelect".equals(tmField.getComponentType())){
								setAttr("click", tmField.getTextName());
							}
						}else if ("dictSelect".equals(tmField.getComponentType())) {
							setAttr("dictType", tmField.getDictType());
							setAttr("change", tmField.getFieldChange());
						}else if ("multipleSelect".equals(tmField.getComponentType())) {//没有改变事件
							setAttr("options", tmField.getDictType());
							setAttr("single", tmField.getTextName());
							setAttr("showfilter", tmField.getFieldChange());
						}else if ("date".equals(tmField.getComponentType())) {
							setAttr("dateFomate", tmField.getFieldChange());
							setAttr("settings", tmField.getDictType());
						}else if ("textarea".equals(tmField.getComponentType())) {
							setAttr("textName", tmField.getTextName());
							setAttr("rows", tmField.getDictType());
						}
					}
					setAttr("tmField", tmField);// 页面类型
					setEdit();
				}else {
					logger.info("没找到字段【id={}】相关信息",fieldId);
				}
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("进入进入字段【fieldId={}】编辑页面发生异常",fieldId);
				throw new ProcessException("进入进入字段【fieldId="+ fieldId +"】编辑页面发生异常");
			}
		}
		setAttr("types", dictService.selectGroupType());//设置字典类型下拉框的值
		
		return "/applyParam/fieldManage/editFieldPage.ftl";
	}
	
	/**
	 * 获取字段列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/fieldList")
	public Page<TmField> fieldList(){
		long tokenId = System.currentTimeMillis();
		logger.info("====>开始字段列表查询"+LogPrintUtils.printCommonStartLog(tokenId,null));
		Page<TmField> page = getPage(TmField.class);
		page = tmFieldService.getTmFieldPage(page);
		logger.info("====>结束字段列表查询"+LogPrintUtils.printCommonEndLog(tokenId,null));
		return page;
	}
	
	/**
	 * 新增/更新字段提交
	 * @param tmField
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editField")
	public Json addField(){
		Json json = Json.newSuccess();
		TmField tmField = getBean(TmField.class);
		long tokenId = System.currentTimeMillis();
		logger.info("====>新增/更新字段开始"+LogPrintUtils.printCommonStartLog(tokenId,null));
		if(tmField != null){
			if(StringUtils.isNotBlank(tmField.getComponentType())){
				//字段处理
				if("select".equals(tmField.getComponentType()) || "ajaxSelect".equals(tmField.getComponentType())){
					tmField.setDictType(getPara("options"));
					tmField.setFieldChange(getPara("change"));
					if("ajaxSelect".equals(tmField.getComponentType())){
						tmField.setTextName(getPara("click"));
					}
				}else if ("dictSelect".equals(tmField.getComponentType())) {
					tmField.setDictType(getPara("dictType"));
					tmField.setFieldChange(getPara("change"));
				}else if ("multipleSelect".equals(tmField.getComponentType())) {//没有改变事件
					tmField.setDictType(getPara("options"));
					tmField.setTextName(getPara("single"));
					tmField.setFieldChange(getPara("showfilter"));
				}else if ("date".equals(tmField.getComponentType())) {
					tmField.setDictType(getPara("settings"));
					tmField.setFieldChange(getPara("dateFomate"));
				}else if ("textarea".equals(tmField.getComponentType())) {
					tmField.setTextName(getPara("textName"));
					tmField.setDictType(getPara("rows"));
				}
			}
			
			//操作判断
			if (tmField.getFieldId() != null) {//更新操作
				try {
					TmField tmFieldById = tmFieldService.getTmFieldById(tmField.getFieldId());
					tmFieldService.updateTmField(tmField);// 根据id更新字段信息
					//记录审计历史
					systemAuditHistoryUtils.saveSystemAudit("字段: "+tmField.getFieldEn(),"页面字段管理","UPDATE",tmFieldById.convertToMap().toString(),tmField.convertToMap().toString());
				} catch (Exception e) {
					json.setMsg("字段"+tmField.getFieldEn()+"更新失败");
					json.setS(false);
					logger.error("字段【"+tmField.getFieldId()+"】更新失败：{}", e);
				}
				logger.info("====>=更新字段{}结束"+LogPrintUtils.printCommonEndLog(tokenId,null),tmField.getFieldEn());
			}else {//保存操作
				try {
					TmField field = new TmField();
					field.setOrg(OrganizationContextHolder.getOrg());
					field.setFieldEn(tmField.getFieldEn());
					field.setTabName(tmField.getTabName());
					field.setRemark(tmField.getRemark());
					List< TmField> fieldList = tmFieldService.getTmFieldList(field);
					if(CollectionUtils.sizeGtZero(fieldList)){
						json.setMsg("字段【"+tmField.getFieldEn()+"】已存在!");
					}else {
						tmField.setOrg(OrganizationContextHolder.getOrg());
						tmFieldService.saveTmField(tmField);// 保存字段信息
						//记录审计历史
						systemAuditHistoryUtils.saveSystemAudit("字段: "+tmField.getFieldEn(),"页面字段管理","SAVE","",tmField.convertToMap().toString());
					}
				} catch (Exception e) {
					logger.error("新增字段【"+ tmField.getTabName()+"】-【"+tmField.getFieldEn()+"】失败：{}",e);
					json.setMsg("新增字段"+tmField.getFieldEn()+"失败");
					json.setS(false);
				}
				logger.info("====>新增/更新字段{}结束"+LogPrintUtils.printCommonEndLog(tokenId,null),tmField.getFieldEn());
			}
		}
		
		return json;
	}
	
	/**
	 * 新增/更新开产品关联字段
	 * @param applyFieldConfigDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/editFieldProduct")
	public Json editFieldProduct(){
		Json json = Json.newSuccess();
		String productCd = getPara("productCd");
		if(StringUtils.isBlank(productCd)){
			logger.info("字段关联的卡产品不存在");
			json.setS(false);
			json.setMsg("字段关联的卡产品不存在,请刷新重试!");
			return json;
		}
		try {
			List<TmFieldProduct> tmFieldProductList = getList(TmFieldProduct.class, "tmFieldProduct");
			if(CollectionUtils.sizeGtZero(tmFieldProductList)){
				//删除卡产品关联字段
				TmFieldProduct tmFieldProduct = new TmFieldProduct();
				tmFieldProduct.setProductCd(productCd);
				List<TmFieldProduct> tmFieldProducts= tmFieldProductService.getTmFieldProductList(tmFieldProduct);
				if(CollectionUtils.sizeGtZero(tmFieldProducts)){
					FieldProductDto fieldProductDto = new FieldProductDto();
					fieldProductDto.setProductCd(productCd);
					String msg = tmFieldProductService.deleteFieldProduct(fieldProductDto);
					if(StringUtils.isNotBlank(msg)){
						json.setS(false);
						json.setMsg(msg);
					}
				}
				//保存卡产品关联字段
				for (TmFieldProduct fieldProduct : tmFieldProductList) {
					if(fieldProduct!=null && fieldProduct.getFieldEn()!=null){
						fieldProduct.setProductCd(productCd);
						tmFieldProductService.saveTmFieldProduct(fieldProduct);
					}
				}
				//保存审计历史
				if (CollectionUtils.sizeGtZero(tmFieldProductList)) {
					systemAuditHistoryUtils.saveSystemAudit("卡产品代码:" + productCd, "关联字段", "UPDATE", tmFieldProducts.toString(), tmFieldProductList.toString());
				} else {
					systemAuditHistoryUtils.saveSystemAudit("卡产品代码:" + productCd, "关联字段", "SAVE", "", tmFieldProductList.toString());
				}
				logger.info("保存卡产品【{}】关联字段保存操作成功",productCd);
			}
		} catch (Exception e) {
			logger.error("更新产品与字段管理配置异常", e);
			json.setS(false);
			json.setMsg("保存卡产品【" + productCd + "】关联字段保存操作失败");
		}
		return json;
	}
	
	/**
	 * 启用/禁用字段
	 * @param id 主键
	 * @param ifUsed  启用/禁用
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateIfUsed")
	public Json updateIfUsed(Integer fieldId, String ifUsed){
		Json json = Json.newSuccess();
		logger.info("启用/禁用字段【fieldId={},ifUsed={}】", fieldId, ifUsed);
		try {
			TmField tmFieldById = tmFieldService.getTmFieldById(fieldId);
			tmFieldService.updateByIfUsed(fieldId, ifUsed);// 根据fieldId启用、禁用字段
			//记录审计历史
			if (StringUtils.equals("Y",ifUsed)) {
				systemAuditHistoryUtils.saveSystemAudit("字段: " + tmFieldById.getFieldEn(), "页面字段管理",
						"启用字段", "", "");
			} else if (StringUtils.equals("N",ifUsed)) {
				systemAuditHistoryUtils.saveSystemAudit("字段: " + tmFieldById.getFieldEn(), "页面字段管理",
						"不启用字段", "", "");
			}
			json.setS(true);
		} catch (Exception e) {
			json.setMsg("操作失败");
			json.setS(false);
			logger.error("字段【{}】是否启用更新失败：{}", fieldId, e.getMessage());
		}
		return json;
	}
	
	/**
	 * 删除字段操作
	 */
	@ResponseBody
	@RequestMapping("/deleteField")
	public Json deleteField(Integer fieldId){
		@SuppressWarnings("static-access")
		Json json = Json.newSuccess();
		logger.info("删除字段【fieldId={}】", fieldId);
		try {
			TmField tmFieldById = tmFieldService.getTmFieldById(fieldId);
			tmFieldService.deleteTmField(fieldId);
			//记录审计历史
			systemAuditHistoryUtils.saveSystemAudit("字段: " + tmFieldById.getFieldEn(), "页面字段管理",
					"DELETE", tmFieldById.convertToMap().toString(), "");
			TmField field = tmFieldService.getTmFieldById(fieldId);
			if(field!=null){
				TmFieldProduct tmFieldProduct = new TmFieldProduct();
				tmFieldProduct.setFieldEn(field.getFieldEn());
				
				List<TmFieldProduct> tmFieldProducts= tmFieldProductService.getTmFieldProductList(tmFieldProduct);
				if(CollectionUtils.sizeGtZero(tmFieldProducts)){
					//删除卡产品关联字段
					FieldProductDto fieldProductDto = new FieldProductDto();
					fieldProductDto.setFieldId(fieldId);
					String msg = tmFieldProductService.deleteFieldProduct(fieldProductDto);
					if(StringUtils.isNotBlank(msg)){
						json.setS(false);
						json.setMsg(msg);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			json.setS(false);
			json.setMsg("删除字段【"+fieldId+"】操作失败");
			logger.error("删除字段【"+fieldId+"】操作失败",e);
		}
		
		return json;
	}
	
	/**
	 * 同步卡产品字段配置操作
	 */
	@ResponseBody
	@RequestMapping("/copyFieldProduct")
	public Json copyFieldProduct(String productCd, String copyProductCd){
		Json json = Json.newSuccess();
		logger.info("卡产品【productCd={}】同步卡产品【copyProductCd={}】字段配置操作开始》》》》》》", productCd, copyProductCd);
		List<TmFieldProduct> tmFieldProducts = null;
		List<TmFieldProduct> tmFieldProductList = null;
		if(StringUtils.isNotBlank(productCd) && StringUtils.isNotBlank(copyProductCd)){
			try {
				if(StringUtils.isNotBlank(productCd)){
					//删除卡产品关联字段
					TmFieldProduct tmFieldProduct = new TmFieldProduct();
					tmFieldProduct.setProductCd(productCd);
					tmFieldProducts= tmFieldProductService.getTmFieldProductList(tmFieldProduct);
					if(CollectionUtils.sizeGtZero(tmFieldProducts)){
						FieldProductDto fieldProductDto = new FieldProductDto();
						fieldProductDto.setProductCd(productCd);
						tmFieldProductService.deleteFieldProduct(fieldProductDto);
					}
					//复制卡产品关联字段
					tmFieldProduct.setProductCd(copyProductCd);
					tmFieldProductList= tmFieldProductService.getTmFieldProductList(tmFieldProduct);
					if(CollectionUtils.sizeGtZero(tmFieldProductList)){
						for (TmFieldProduct fieldProduct : tmFieldProductList) {
							fieldProduct.setProductCd(productCd);
							tmFieldProductService.saveTmFieldProduct(fieldProduct);
						}
					}
				}else {
					json.setS(false);
					json.setMsg("卡产品不存在，请刷新再试");
				}
				//记录审计历史
				systemAuditHistoryUtils.saveSystemAudit("卡产品代码:" + productCd, "关联字段", "同步卡产品", tmFieldProducts.toString(), tmFieldProductList.toString());
			} catch (Exception e) {
				// TODO: handle exception
				json.setS(false);
				json.setMsg("卡产品【productCd="+productCd+"】同步卡产品【copyProductCd="+copyProductCd+"】字段配置操作失败");
				logger.error("卡产品【productCd="+productCd+"】同步卡产品【copyProductCd="+copyProductCd+"】字段配置操作失败",e);
			}
		}
		logger.info("卡产品【productCd={}】同步卡产品【copyProductCd={}】字段配置操作结束《《《《《", productCd, copyProductCd);
		
		return json;
	}
	
	/**
	 * 删除卡产品关联的字段操作(清空)
	 */
	@ResponseBody
	@RequestMapping("/deleteFieldProduct")
	public Json deleteFieldProduct(String productCd){
		Json json = Json.newSuccess();
		logger.info("删除卡产品【productCd={}】关联的所有字段操作开始》》》》》》》", productCd);
		List<TmFieldProduct> tmFieldProducts = null;
		try {
			if(StringUtils.isNotBlank(productCd)){
				//删除卡产品关联字段
				TmFieldProduct tmFieldProduct = new TmFieldProduct();
				tmFieldProduct.setProductCd(productCd);
				tmFieldProducts= tmFieldProductService.getTmFieldProductList(tmFieldProduct);
				if(CollectionUtils.sizeGtZero(tmFieldProducts)){
					FieldProductDto fieldProductDto = new FieldProductDto();
					fieldProductDto.setProductCd(productCd);
					tmFieldProductService.deleteFieldProduct(fieldProductDto);
				}
			}else {
				json.setS(false);
				json.setMsg("卡产品不存在，请刷新再试");
			}
		} catch (Exception e) {
			// TODO: handle exception
			json.setS(false);
			json.setMsg("删除卡产品【productCd="+productCd+"】关联的所有字段操作失败");
			logger.error("删除卡产品【productCd="+productCd+"】关联的所有字段操作失败",e);
		}
		logger.info("删除卡产品【productCd={}】关联的所有字段操作结束《《《《《《《《", productCd);
		systemAuditHistoryUtils.saveSystemAudit("卡产品代码:" + productCd, "关联字段", "DELETE", tmFieldProducts.toString(), "");
		return json;
	}
}
