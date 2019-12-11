package com.jjb.ams.app.controller.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jjb.acl.access.service.AccessDictService;
import com.jjb.acl.facility.enums.bus.AppType;
import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.acl.infrastructure.TmAclBranch;
import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.ams.app.tags.AmsTag;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.facility.dto.FieldPageDto;
import com.jjb.ecms.facility.dto.FieldProductDto;
import com.jjb.ecms.infrastructure.TmAppContact;
import com.jjb.ecms.infrastructure.TmAppCustInfo;
import com.jjb.ecms.infrastructure.TmAppFlag;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.DataTypeUtils;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.facility.util.StringUtils;

@Component
public class AmsAppUtils {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CacheContext cacheContext;

	@Autowired
	private AmsTag amsTag;
	@Autowired
	private AccessDictService accessDictService;
	@Autowired
	private ApplyInputService applyInputService;
	

	/**
	 * 获取配置的需要复核的复核项
	 * @param appType
	 * @param productCd
	 * @param attachNum
	 * @return
	 */
	public LinkedHashMap<String, TreeMap<String, FieldPageDto>> getReviewFields(String productCd, String appType, Integer attachNum) {
		if(StringUtils.isBlank(appType)){
			logger.info("获取复核配置项[申请类型]参数为空");
			throw new ProcessException("获取复核配置项[申请类型]参数为空");
		}else {
			if(AppType.A.equals(appType) && (attachNum == null || attachNum == 0)){
				logger.info("获取复核配置项[附卡数目]-[{}]参数为空",attachNum);
				throw new ProcessException("获取复核配置项[附卡数目]参数为空");
			}
		}
		if(StringUtils.isBlank(productCd)){
			logger.info("获取复核配置项[卡产品]参数为空");
			throw new ProcessException("获取复核配置项[卡产品]参数为空");
		}
		List<FieldProductDto> fieldProductDtoList = cacheContext.getFieldProductDtosByProductCd(productCd);
		if(CollectionUtils.sizeGtZero(fieldProductDtoList)){
			logger.info("=====>>开始配置录入复核对比字段");
			String tabName = "";
			String field = "";
			String fieldName = "";
			String reviewSort = null;
			Integer sort = null;
			String regexp = null;
			TreeMap<String, FieldPageDto> mainInfoMap = new TreeMap<String, FieldPageDto>();//主卡
			TreeMap<String, FieldPageDto>  attachMap_0 = new TreeMap<String, FieldPageDto>();//附卡0
			TreeMap<String, FieldPageDto>  attachMap_1 = new TreeMap<String, FieldPageDto>();//附卡1
			TreeMap<String, FieldPageDto>  attachMap_2 = new TreeMap<String, FieldPageDto>();//附卡2
			TreeMap<String, FieldPageDto>  commonMap = new TreeMap<String, FieldPageDto>();//主附卡都要复核项
			for (FieldProductDto fieldProductDto : fieldProductDtoList) {
				String reviewFlag = fieldProductDto.getIfReviewItem();//复核项标志
				if(StringUtils.isNotBlank(reviewFlag) && !Indicator.N.name().equals(reviewFlag)){
					tabName = fieldProductDto.getTabName();//表名
					field = fieldProductDto.getFieldEn();//字段名
					fieldName = fieldProductDto.getFieldName();
					sort = fieldProductDto.getReviewSort();//复核项顺序
					regexp = fieldProductDto.getFieldRegexp();//正则表达式
					if(sort == null){
						logger.info("复核项[tabName={},field={}]没有配置复核项顺序，请联系管理员配置！", tabName, field);
						continue;
					}
					reviewSort = sort.toString();
					FieldPageDto fieldPageDto = new FieldPageDto();
					String name = null;
					if(appType.equals(AppType.S.name())){//附卡
						if(attachNum != null && attachNum > 0){//主附同申
							for (int i = 0; i < attachNum; i++) {
								FieldPageDto fieldPageItem = new FieldPageDto();
								String newName = tabName + "["+i+"]." + field;
								fieldPageItem.setName(newName);
								fieldPageItem.setFieldName(fieldName);
								fieldPageItem.setId(field+i);
								fieldPageItem.setRegexp(regexp);
								if(i == 0){
									attachMap_0.put(reviewSort, fieldPageItem);
								}
								if(i == 1){
									attachMap_1.put(reviewSort, fieldPageItem);
								}
								if(i == 2){
									attachMap_2.put(reviewSort, fieldPageItem);
								}
							}
						}else {//独立附卡
							name = tabName + "." + field;
							fieldPageDto.setName(name);
							fieldPageDto.setFieldName(fieldName);
							fieldPageDto.setId(field);
							fieldPageDto.setRegexp(regexp);
							attachMap_0.put(reviewSort, fieldPageDto);
						}
					}else if(appType.equals(AppType.B.name())){//主卡复核
						if(tabName.equals(AppConstant.TmAppContact)){//处理一下联系人信息
							String remark = fieldProductDto.getRemark();
							if(StringUtils.isNotBlank(remark)){//区分联系人1还是2(1:亲属；2：其他)
								if(remark.contains("1"))
									remark = "1";
								else if(remark.contains("2"))
									remark = "2";
								name = tabName + "["+remark+"]." + field;
								field = field + fieldProductDto.getFieldRemark();
							}
						}else {
							name = tabName + "." + field;
						}
						fieldPageDto.setName(name);
						fieldPageDto.setFieldName(fieldName);
						fieldPageDto.setId(field);
						fieldPageDto.setRegexp(regexp);
						mainInfoMap.put(reviewSort, fieldPageDto);
					}else if(appType.equals(AppType.A.name())){//主附卡都要复核项
						if(tabName.equals(AppConstant.TmAppContact)){//处理一下联系人信息
							String remark = fieldProductDto.getRemark();
							if(StringUtils.isNotBlank(remark)){//区分联系人1还是2(1:亲属；2：其他)
								if(remark.contains("1"))
									remark = "1";
								else if(remark.contains("2"))
									remark = "2";
								name = tabName + "["+remark+"]." + field;
								field = field + fieldProductDto.getFieldRemark();
							}
						}else {
							name = tabName + "." + field;
						}
						fieldPageDto.setName(name);
						fieldPageDto.setFieldName(fieldName);
						fieldPageDto.setId(field);
						fieldPageDto.setRegexp(regexp);
						commonMap.put(reviewSort, fieldPageDto);
					}
				}
			}

			LinkedHashMap<String, TreeMap<String, FieldPageDto>> reviewFieldMap = new LinkedHashMap<String, TreeMap<String, FieldPageDto>>();
			if(commonMap != null && commonMap.size() > 0){//添加所有公共复核项
				reviewFieldMap.put(AppConstant.COMMON_TAB, commonMap);
			}
			if(appType.equals(AppType.A.name())){
				if(mainInfoMap != null && mainInfoMap.size() > 0) {
					reviewFieldMap.put(AppConstant.MAIN_TAB, mainInfoMap);
				}
				if(attachMap_0 != null && attachMap_0.size() > 0){
					reviewFieldMap.put(AppConstant.bscSuppInd_S_1, attachMap_0);
				}
				if(attachMap_1 != null && attachMap_1.size() > 0){
					reviewFieldMap.put(AppConstant.bscSuppInd_S_2, attachMap_1);
				}
				if(attachMap_2 != null && attachMap_2.size() > 0){
					reviewFieldMap.put(AppConstant.bscSuppInd_S_3, attachMap_2);
				}
			}
			if(appType.equals(AppType.B.name())){
				reviewFieldMap.put(AppConstant.MAIN_TAB, mainInfoMap);
			}
			if(appType.equals(AppType.S.name())){
				if(attachMap_0 != null && attachMap_0.size() > 0){
					reviewFieldMap.put(AppConstant.bscSuppInd_S_1, attachMap_0);
				}
			}

			return reviewFieldMap;
		}

		return null;
	}

	/**配置页面
	 * 查询展示字段
	 * @param productCd 卡产品
	 * @param appType 申请类型
	 * @param pageType 页面类型
	 * @param addType 操作类型(0:遍历附卡并赋数据 ,1:动态添加附卡)
	 * @param num 附卡数目（取值1,2,3）
	 * @param applyInfoDto 数据
	 * Map<字段名,字段名称>
	 */
	public Map<String, TreeMap<String, FieldPageDto>> getPageFieldMap(TmProduct tmProduct, String appType,
																	  String pageType, Integer addType, Integer num, ApplyInfoDto applyInfoDto) {
		if(addType == null){
			logger.error("获取配置页面字段[添加类型]为空");
			return null;
		}
		if(applyInfoDto == null && addType == 0){
			logger.error("获取配置页面字段[回显值实体applyInfoDto]为空");
			return null;
		}
		if(tmProduct == null || StringUtils.isEmpty(tmProduct.getProductCd())){
			logger.error("获取配置页面字段[卡产品代码]为空");
			return null;
		}
		if(StringUtils.isBlank(appType)){
			logger.error("获取配置页面字段[申请类型]为空");
			return null;
		}
		if(StringUtils.isBlank(pageType)){
			logger.error("获取配置页面字段[页面节点]为空");
			return null;
		}
		if(!AppType.B.name().equals(appType) && num == null){
			logger.error("获取配置页面字段[附卡数量]为空");
			return null;
		}
		String productCd = tmProduct.getProductCd();
		String cardBin = tmProduct.getBin();
		int cardBinLen = 6;//卡bin长度,默认6位长度
		if(StringUtils.isNotEmpty(cardBin)){
			cardBinLen=cardBin.trim().length();
		}
		int cardNoLen = cardBinLen+tmProduct.getCardNoRangeCeil().length()+1;//卡号长度默认16位
		logger.info("配置关联卡产品[productCd={},页面类型={}]页面字段开始。。。。。。。。。。。。。。。。。",productCd,pageType);
		//获取页面显示字段信息
		List<FieldProductDto> fieldProductDtoList = cacheContext.getFieldProductDtosByProductCd(productCd);
		Map<String, TreeMap<String, FieldPageDto>> pageFieldMap = null;
		if(CollectionUtils.sizeGtZero(fieldProductDtoList)){
			String[] fieldRegions = cacheContext.getFieldRegions();
			if(fieldRegions != null && fieldRegions.length > 0){
				pageFieldMap = new HashMap<String, TreeMap<String,FieldPageDto>>();
				TreeMap<String,FieldPageDto> attach0 = null,attach1 = null,attach2 = null;
				if(AppType.A.name().equals(appType)){
					attach0 = new TreeMap<String, FieldPageDto>();
					attach1 = new TreeMap<String, FieldPageDto>();
					attach2 = new TreeMap<String, FieldPageDto>();
				}
				Map<String, Serializable> tmAppMainMap = CollectionUtils.getMapForMethod(applyInfoDto.getTmAppMain(), "convertToMap");//主表信息
				if(tmAppMainMap==null){
					tmAppMainMap = new HashMap<String, Serializable>();
				}
				Map<String, Serializable> tmAppAuditMap = CollectionUtils.getMapForMethod(applyInfoDto.getTmAppAudit(), "convertToMap");//主表信息
				if(tmAppAuditMap==null){
					tmAppAuditMap = new HashMap<String, Serializable>();
				}
				Map<String, Serializable> tmAppPrimCardInfoMap = CollectionUtils.getMapForMethod(applyInfoDto.getTmAppPrimCardInfo(), "convertToMap");//卡片信息
				if(tmAppPrimCardInfoMap==null){
					tmAppPrimCardInfoMap = new HashMap<String, Serializable>();
				}
				Map<String, Serializable> tmAppPrimAnnexEviMap = CollectionUtils.getMapForMethod(applyInfoDto.getTmAppPrimAnnexEvi(), "convertToMap");//附件证明信息
				if(tmAppPrimAnnexEviMap==null){
					tmAppPrimAnnexEviMap = new HashMap<String, Serializable>();
				}
				Map<String, Map<String, Serializable>> tmAppCustInfoMap = getCustMaps(applyInfoDto.getTmAppCustInfoMap(),appType);//主卡申请人信息
				Map<String, Map<String, Serializable>> tmAppContactInfoMap = getContactMaps(applyInfoDto.getTmAppContactMap());//联系人信息
				if(addType == 0){
					try {
						tmAppMainMap = applyInfoDto.getTmAppMain().convertToMap();
						tmAppAuditMap = applyInfoDto.getTmAppAudit().convertToMap();
//						//如果是支持分期产品,进入录入页面，默认展示支持分期
//						if("Y".equals(tmProduct.getIsInstalment()) && tmAppAuditMap.get("isInstalment") == null){
//							tmAppAuditMap.put("isInstalment", "Y");
//						}
					} catch (Exception e) {
						logger.error("配置[appType={},productCd={},pageType={}]页面实体对象转换成map发生异常",appType,productCd,pageType);
						throw new ProcessException("配置[appType="+appType+",productCd="+productCd+",pageType="+pageType+"]页面实体对象转换成map发生异常",e);
					}
				}
				try {
					@SuppressWarnings("unchecked")
					TreeMap<String, FieldPageDto>[] reginMap = new TreeMap[fieldRegions.length];
					for (int i = 0; i < fieldRegions.length; i++) {
						reginMap[i] = new TreeMap<String, FieldPageDto>();
					}
					for (FieldProductDto fieldProductDto : fieldProductDtoList) {
						String pageField = null,name = null;
						try {
							Boolean isView = false;//是否是展示页面
							if(pageType.equals(AmsPagePathConstant.AMS_APP_INPUT_MODIFY)){
								pageField = fieldProductDto.getIsInput();
							}else if (pageType.equals(AmsPagePathConstant.AMS_APP_REVIEW) || pageType.equals(AmsPagePathConstant.AMS_APP_DETAIL)
									|| pageType.equals(AmsPagePathConstant.AMS_APP_INPUT_MODIFY)) {//复核、人工核查、补件、终审、详情
								isView = true;
								pageField = fieldProductDto.getIsReview();
							}
							if(StringUtils.isNotBlank(pageField) && !Indicator.N.name().equals(pageField)){
								if(pageField.contains(appType)){
									String fieldRegion = fieldProductDto.getFieldRegion();//字段所在的位置
									if(StringUtils.isNotBlank(fieldRegion)){
										FieldPageDto fieldPageDto = new FieldPageDto();
										String tabName = fieldProductDto.getTabName();
										String field = fieldProductDto.getFieldEn();
										String fieldSort = null;
										if(fieldProductDto.getFieldSort() != null){
											fieldSort = fieldProductDto.getFieldSort().toString();
										}else {
											logger.info("请配置字段[{}-{}]位置的序号",tabName,field);
											continue;
										}
										Integer index = getMapIndex(fieldRegion, fieldRegions, reginMap);
										if(index == null){
											logger.info("字段[{}]，所属的表[{}]没有配置位置",field,tabName);
											continue;
										}
										if(addType == 0) {//修改遍历+数据
											if (AppConstant.TmAppCustInfo.equals(tabName) && tmAppCustInfoMap!=null) {
												for (String key : tmAppCustInfoMap.keySet()) {
													Map<String,Serializable> custMap = tmAppCustInfoMap.get(key);
													if(custMap!=null){
														String bscSuppInd = StringUtils.valueOf(custMap.get("bscSuppInd"));
														Integer attachNo = DataTypeUtils.getIntegerValue(custMap.get("attachNo"));
														name = tabName + "[" + attachNo + "]." + field;
														fieldPageDto.setName(name);
														String attachSort = StringUtils.valueOf(fieldProductDto.getFieldSort());//主附同申附卡字段排序
														if(StringUtils.equals(bscSuppInd, AppConstant.bscSuppInd_S)){
															if(StringUtils.isEmpty(attachSort)){
//																index = "S"+index;
																continue;
															}
														}
														custMap = editCardNo(isView, custMap,cardBinLen,cardNoLen);//自选卡号处理
														if(StringUtils.isEmpty(attachSort)){

														}else{
															reginMap[index].put(fieldSort, getFieldPageDto(custMap,fieldProductDto,fieldPageDto,appType,attachNo));
														}
													}
												}
											}else if(AppConstant.TmAppContact.equals(tabName)) {//联系人信息
												String fieldRemark = fieldProductDto.getFieldRemark();
												String fr2 = (StringUtils.stringToInteger(fieldRemark)+1)+"";
												FieldPageDto fieldPageDto2 = new FieldPageDto();
												if(tmAppContactInfoMap==null || tmAppContactInfoMap.size()<1){
													Map<String,Serializable> contMap = new TmAppContact().convertToMap();
													name = tabName + "["+fieldRemark+"]." + field;
													fieldPageDto.setName(name);
													name = tabName + "["+fieldRemark+"]." + field;
													fieldPageDto2.setName(name);
													reginMap[index].put(fieldSort, getFieldPageDto(contMap,fieldProductDto,fieldPageDto,appType,num));
													reginMap[index].put(fieldSort, getFieldPageDto(contMap,fieldProductDto,fieldPageDto2,appType,num));
												}else if(StringUtils.isNotBlank(fieldRemark) && tmAppContactInfoMap!=null) {//区分联系人是亲属还是其他（1：亲属；2：其他）
													fieldRemark = fieldRemark.trim();
													int i = 0;
													for (String key : tmAppContactInfoMap.keySet()) {
														Map<String,Serializable> contactMap = tmAppContactInfoMap.get(key);
														if(contactMap!=null){
															name = tabName + "["+fieldRemark+"]." + field;
															fieldPageDto.setName(name);
															fieldPageDto2.setName(name);
															name = tabName + "["+fr2+"]." + field;
															reginMap[index].put(fieldSort, getFieldPageDto(contactMap,fieldProductDto,fieldPageDto,appType,i));
															reginMap[index].put(fieldSort, getFieldPageDto(contactMap,fieldProductDto,fieldPageDto2,appType,i));
															i++;
														}
													}
												}else {
													logger.warn("配置页面字段[field={}]区分联系人信息标志remark不存在",field);
												}
											}else {
												name = tabName + "." + field;
												fieldPageDto.setName(name);
												Map<String, Serializable> map = null;
												if(AppConstant.TmAppMain.equals(tabName)){//主表信息
													map = tmAppMainMap;
												}if(AppConstant.TmAppAudit.equals(tabName)){//进件审计信息
													map = tmAppAuditMap;
												}else if(AppConstant.TmAppPrimAnnexEvi.equals(tabName)){//附件证明信息
													map = tmAppPrimAnnexEviMap;
												}else if(AppConstant.TmAppPrimCardInfo.equals(tabName)){//卡片信息
													map = tmAppPrimCardInfoMap;
												}
												reginMap[index].put(fieldSort, getFieldPageDto(map,fieldProductDto,fieldPageDto,appType,null));
											}
										}else if(addType == 1) {//添加附卡
											if (AppConstant.TmAppCustInfo.equals(tabName)) {
												String attachSort = fieldProductDto.getObText1();
												if(StringUtils.isEmpty(attachSort)){
													continue;
												}
												name = tabName + "[" + num + "]." + field;
												fieldPageDto.setName(name);
												if(StringUtils.isNotBlank(fieldProductDto.getDefValue())){//设置默认值
													fieldPageDto.setValue(fieldProductDto.getDefValue());
												}
												attach0.put(attachSort, getFieldPageDto(null,fieldProductDto,fieldPageDto,appType,num));
											}
										}
									}
								}
							}
						} catch (Exception e) {
							logger.warn("配置卡产品[{}]字段[{}]页面[{}]发生异常",productCd,name,pageType,e);
						}
					}
					if(addType == 0){
						for (int i = 0; i < fieldRegions.length; i++) {
							pageFieldMap.put(fieldRegions[i], reginMap[i]);
							//如果没有配置分期字段，则移除map中的分期key值
							if(fieldRegions[i].startsWith("A9") && reginMap[i].size() <= 0){
								pageFieldMap.remove(fieldRegions[i]);
							}
						}
						if(AppType.A.name().equals(appType)){
							pageFieldMap.put(AppConstant.bscSuppInd_S_1, attach0);
							if(attach1.size() > 0){
								pageFieldMap.put(AppConstant.bscSuppInd_S_2, attach1);
							}
							if(attach2.size() > 0){
								pageFieldMap.put(AppConstant.bscSuppInd_S_3, attach2);
							}
						}
					}else if (addType == 1) {
						pageFieldMap.put(AppConstant.bscSuppInd_S+num, attach0);
					}
				} catch (Exception e) {
					logger.error("配置卡产品[{}]页面[{}]发生异常",productCd,pageType);
					throw new ProcessException("配置卡产品["+productCd+"}]页面["+pageType+"]发生异常");
				}
			}
		}
		logger.info("配置关联卡产品[productCd={},页面类型={}]页面字段结束。。。。。。。。。。。。。。。。。",productCd,pageType);
//		for (String key : pageFieldMap.keySet()){
//			TreeMap<String, FieldPageDto> values = pageFieldMap.get(key);
//			for (String key2 : values.keySet()){
//				System.out.println("parent:"+key+" ,   fileKey:"+key2+",   value:"+values.get(key2));
//			}
//		}
		return pageFieldMap;
	}

	/**
	 * 设置页面组件对象数据
	 * @param fieldProductDto
	 * @param fieldPageDto
	 * @return
	 */
	private FieldPageDto getFieldPageDto(Map<String, Serializable> map, FieldProductDto fieldProductDto, FieldPageDto fieldPageDto,
										 String appType, Integer num){
		if(fieldProductDto != null && fieldPageDto != null){
			String field = fieldProductDto.getFieldEn();
			if(StringUtils.isNotBlank(field)){
				fieldPageDto.setIsRow(fieldProductDto.getIsRow());//设置换行始末状态
				if(num == null || StringUtils.equals(appType, AppConstant.bscSuppInd_B)){//主卡
					fieldPageDto.setId(fieldProductDto.getFieldEn());
					if(StringUtils.isNotEmpty(fieldProductDto.getFieldChange())) {
						String chagenText = fieldProductDto.getFieldChange().replaceFirst("#NUM", "");;
						//if (StringUtils.concat(chagenText, "#NUM")) {
						//	chagenText = chagenText.replace("#NUM", num + "");
						//}
						fieldPageDto.setChange(chagenText);
						fieldProductDto.setFieldChange(chagenText);
					}
					if(StringUtils.isNotEmpty(fieldProductDto.getTextName())) {
						String str = fieldProductDto.getTextName().replaceFirst("#NUM", "");
						fieldPageDto.setTextName(str);
						fieldProductDto.setTextName(str);
					}
				}else {//附卡
					fieldPageDto.setId(fieldProductDto.getFieldEn()+num);
					if(StringUtils.isNotEmpty(fieldProductDto.getFieldChange())) {
						String str = fieldProductDto.getFieldChange().replaceFirst("#NUM", num.toString());
						fieldPageDto.setChange(str);
						fieldProductDto.setFieldChange(str);
					}
					if(StringUtils.isNotEmpty(fieldProductDto.getTextName())) {
						String str = fieldProductDto.getTextName().replaceFirst("#NUM", num.toString());
						fieldPageDto.setTextName(str);
						fieldProductDto.setTextName(str);
					}
				}
				fieldPageDto.setFieldName(fieldProductDto.getFieldName());
				String componentType = fieldProductDto.getComponentType();
				if(StringUtils.isNotBlank(componentType)){
					fieldPageDto.setComponentType(componentType);
					String value = null;
					if(map != null) {
						if(map.get(field) == null) {
							String defValue = fieldProductDto.getDefValue();
							if(StringUtils.isNotBlank(defValue)){//设置默认值
								value = defValue.trim();
							}
						}else {
							if("date".equals(componentType)) {
								String dateFomate = StringUtils
										.isBlank(fieldProductDto.getFieldChange()) ? "yyyy-MM-dd"
										: fieldProductDto.getFieldChange();// 设置时间默认格式
								try {
									value = DateUtils.dateToString((Date)map.get(field),dateFomate);
								} catch (Exception e) {//控件是日期类型，数据库字段类型是字符串的处理
									logger.debug("字段["+field+"]值["+map.get(field)+"]转换成["+dateFomate+"]失败");
									value = map.get(field).toString();
								}
							}else {
								value = map.get(field).toString();
							}
						}
						fieldPageDto.setValue(value);
					}
					if("select".equals(componentType) || "multipleSelect".equals(componentType)) {//下拉框的做处理
						String dictType = fieldProductDto.getDictType();
						if(AppConstant.TmAppInstalLoan.equals(fieldProductDto.getTabName())){
							if(field.equals("instalmentCreditActivityNo")){
								//根据产品编号，初始化分期活动数据
								logger.info("before==>field-{},产品编号-{}，option-{}",field,map.get("productCd"),dictType);
								dictType = dictType.replace("productCd", map.get("productCd").toString());
								logger.info("after==>field-{},产品编号-{}，option-{}",field,map.get("productCd"),dictType);
							}else if(field.equals("mccNo") || field.equals("loanInitTerm")){
								logger.info("before==>field-{},分期活动编号-{}，option-{}",field,map.get("instalmentCreditActivityNo"),dictType);
								if(map.get("instalmentCreditActivityNo") != null && StringUtils.isNotBlank(map.get("instalmentCreditActivityNo").toString())){
									dictType = dictType.replace("activityNo", map.get("instalmentCreditActivityNo").toString());
								}
								logger.info("after==>field-{},分期活动编号-{}，option-{}",field,map.get("instalmentCreditActivityNo"),dictType);
							}
						}
						if(StringUtils.isNotEmpty(dictType) && !StringUtils.concat(dictType, "_|")) {
							dictType="ams_|"+dictType;
						}
						LinkedHashMap<Object, Object> options = amsTag.tableMap(java.util.Arrays.asList(StringUtils.valueOf(dictType).split("\\|")));
						fieldPageDto.setOptions(options);
						fieldPageDto.setTextName(StringUtils.isBlank(fieldProductDto.getTextName())?"true":fieldProductDto.getTextName());
					}else if("ajaxSelect".equals(componentType)) {
						//搜索下拉框click事件中参数处理(将参数this替换成组件id)
						String textName = fieldProductDto.getTextName();
						if(StringUtils.isNotEmpty(textName)){
							fieldProductDto.setTextName(textName.replaceFirst("this", "'"+fieldPageDto.getId()+"'"));
						}
						if(StringUtils.isBlank(value)){
							fieldPageDto.setOptions(stringToMap(fieldProductDto.getDictType()));
						}else {//处理异步加载回显的字段
							Map<Object,Object> ajaxMap = new HashMap<Object, Object>();
							//格式:是否显示code|是否是aclDic|数据字典类型
							/* 国家代码：Y|Y|Nationality
							 * 省：N|Y|STATE
							 * 网点：Y|N|firstBranch
							 */
							String fieldRemark = fieldProductDto.getFieldRemark();
							if(StringUtils.isNotEmpty(fieldRemark)){
								String[] params = fieldRemark.trim().split("\\|");
								if(params != null && params.length == 3){
									//下拉选项codeName-codeName模式
									if(StringUtils.isEmpty(params[0])){
										params[0] = Indicator.Y.name();
									}
									if(Indicator.N.name().equals(params[0])){//不显示code
										ajaxMap.put(value, value);
									}else{//code-codeName模式
										if(Indicator.Y.name().equals(params[1])){//是acldict表
											TmAclDict tmAclDict = accessDictService.get(params[2], value);
											if(tmAclDict != null){
												ajaxMap.put(tmAclDict.getCode(), tmAclDict.getCodeName());
											}else {
												logger.info("回显配置字段{[]}没找到aclDic配置信息{[]}",fieldProductDto.getFieldEn(),fieldProductDto.getRemark());
											}
										}else {//网点
											TmAclBranch tmAclBranch = cacheContext.getTmAclBranchByCode(value);
											if(tmAclBranch != null){
												ajaxMap.put(tmAclBranch.getBranchCode(),tmAclBranch.getBranchName());
											}else {
												logger.info("回显配置字段{[]}没找到配置AclBranch信息{[]}",fieldProductDto.getFieldEn(),fieldProductDto.getRemark());
											}
										}
									}
								}
							}
							if(ajaxMap.isEmpty()){
								ajaxMap.put("", "");//默认为空选项
							}
							fieldPageDto.setOptions(ajaxMap);
						}
					}else if("date".equals(componentType)) {
						fieldPageDto.setOptions(stringToMap(fieldProductDto.getDictType()));
					}else {
						fieldPageDto.setDictType(fieldProductDto.getDictType());
					}
				}
				fieldPageDto.setFieldAr(StringUtils.isBlank(fieldProductDto.getFieldAr())?"12":fieldProductDto.getFieldAr());//默认为12栅格
				fieldPageDto.setInputAr(StringUtils.isBlank(fieldProductDto.getInputAr())?"22":fieldProductDto.getInputAr());//默认为22栅格
				fieldPageDto.setLabelAr(StringUtils.isBlank(fieldProductDto.getLabelAr())?"14":fieldProductDto.getLabelAr());//默认为14栅格
				fieldPageDto.setNullable(StringUtils.isBlank(fieldProductDto.getFieldNullable())?"true":fieldProductDto.getFieldNullable());//默认为有空选项
				fieldPageDto.setShowCode(StringUtils.isBlank(fieldProductDto.getShowCode())?"true":fieldProductDto.getShowCode());//默认为显示code
				fieldPageDto.setTextName(fieldProductDto.getTextName());
				fieldPageDto.setIfReadonly(fieldProductDto.getIfReadonly());
				String ifRequiredItem = fieldProductDto.getIfRequiredItem();
				if(ifRequiredItem.contains(appType)){
					fieldPageDto.setIfRequire("true");
				}else {
					fieldPageDto.setIfRequire("false");
				}
			}
		}

		return fieldPageDto;
	}

	/**
	 * 获取map数组下标
	 * @param fieldRegion
	 * @param fieldRegions
	 * @param reginMap
	 * @return
	 */
	private Integer getMapIndex(String fieldRegion, String[] fieldRegions, TreeMap<String, FieldPageDto>[] reginMap) {
		for (int i = 0; i < fieldRegions.length; i++) {
			if(fieldRegion.equals(fieldRegions[i])){
				return i;
			}
		}

		return null;
	}

	/**
	 * 页面配置字符串转map
	 * @param str
	 * @return
	 */
	private Map<Object,Object> stringToMap(String str) {
		Map<Object,Object> map = null;
		if(StringUtils.isNotBlank(str)){
			map = new LinkedHashMap<Object, Object>();
			String[] options = str.split(",");
			for (int i = 0; i < options.length; i++) {
				String[] keyValues = options[i].split(":");
				if(keyValues.length>1){
					map.put(keyValues[0], keyValues[1]);
				}else{
					logger.info("页面字段"+str+"配置不完善");
					map.put(keyValues[0], "");
				}
			}
		}

		return map;
	}

	/**
	 * 处理自选卡号，去掉前N位卡bin和最后一位校验位
	 * @param isView 是否是展示页面
	 * @param map（primCustMap、tmAppAttachInfoMap[]）主、附卡
	 * @return
	 */
	private Map<String, Serializable> editCardNo(Boolean isView, Map<String, Serializable> map,
												 int cardBinLen,int cardNoLen){
		if(!isView && map != null && map.get("cardNo") != null){
			String cardNo = map.get("cardNo").toString().trim();
			if(cardNo.length() == cardNoLen){
				cardNo = cardNo.substring(cardBinLen, cardNoLen-1);
				map.put("cardNo", cardNo);
			}
		}
		return map;
	}
	/**
	 * 将Map中实体对象转换成 Map<String,Serializable>集合
	 * @param srcMap
	 * @param method
	 * @return
	 */
	public Map<String,Map<String,Serializable>> getCustMaps(Map<String,TmAppCustInfo> srcMap,String appType){
		Map<String,Map<String,Serializable>> map = new HashMap<String, Map<String,Serializable>>();
		if(srcMap!=null) {
			for (String key : srcMap.keySet()) {
				map.put(key,CollectionUtils.getMapForMethod(srcMap.get(key), "convertToMap"));
			}
		}
		TmAppCustInfo temCust = new TmAppCustInfo();

		if(StringUtils.equals(appType, AppConstant.bscSuppInd_B)) {
			if(map==null || !map.containsKey(AppConstant.bscSuppInd_B_1)) {
				temCust.setBscSuppInd(AppConstant.bscSuppInd_B);
				temCust.setAttachNo(1);
				Map<String,Serializable> temMap = temCust.convertToMap();
				map.put(AppConstant.bscSuppInd_B_1, temMap);
			}
		}else if(StringUtils.equals(appType, AppConstant.bscSuppInd_S)) {
			if(map==null || !map.containsKey(AppConstant.bscSuppInd_S_1)) {
				temCust.setBscSuppInd(AppConstant.bscSuppInd_S);
				temCust.setAttachNo(1);
				Map<String,Serializable> temMap = temCust.convertToMap();
				map = new HashMap<String, Map<String, Serializable>>();
				map.put(AppConstant.bscSuppInd_S_1, temMap);
			}
		}else if(StringUtils.equals(appType, "A")) {
			if(!map.containsKey(AppConstant.bscSuppInd_S_1)) {
				temCust.setBscSuppInd(AppConstant.bscSuppInd_S);
				temCust.setAttachNo(1);
				Map<String,Serializable> temMap = temCust.convertToMap();
				map.put(AppConstant.bscSuppInd_S_1, temMap);
			}
			if(!map.containsKey(AppConstant.bscSuppInd_B_1)) {
				temCust.setBscSuppInd(AppConstant.bscSuppInd_B);
				temCust.setAttachNo(1);
				Map<String,Serializable> temMap = temCust.convertToMap();
				map.put(AppConstant.bscSuppInd_B_1, temMap);
			}
		}

		return map;
	}
	/**
	 * 将Map中实体对象转换成 Map<String,Serializable>集合
	 * @param srcMap
	 * @param method
	 * @return
	 */
	public Map<String,Map<String,Serializable>> getContactMaps(Map<String,TmAppContact> srcMap){
		Map<String,Map<String,Serializable>> map = new HashMap<String, Map<String,Serializable>>();
		if(srcMap!=null) {
			for (String key : srcMap.keySet()) {
				map.put(key,CollectionUtils.getMapForMethod(srcMap.get(key), "convertToMap"));
			}
		}
		TmAppContact temCust = new TmAppContact();
		Map<String,Serializable> temMap = temCust.convertToMap();
		if(!map.containsKey(AppConstant.M_CON_ITEM_INFO_PREFIX+1)) {
			map.put(AppConstant.M_CON_ITEM_INFO_PREFIX+1,temMap);
		}
		if(!map.containsKey(AppConstant.M_CON_ITEM_INFO_PREFIX+2)) {
			map.put(AppConstant.M_CON_ITEM_INFO_PREFIX+2,temMap);
		}
		return map;
	}
	/**
	 * 给申请件设置标签信息
	 * @param appNo
	 * @param appFlags
	 */
	public void setTmAppFlagInfo(String appNo, String[] appFlags) {
		if (appFlags == null) {
			appFlags = new String[]{"00"};
		}
		if (appFlags != null) {
			List<TmAppFlag> tmAppFlags = new ArrayList<>();
			for (String appFlag : appFlags) {
				if(StringUtils.isEmpty(appFlags)) {
					continue;
				}
				TmAclDict dict = cacheContext.getAclDictByTypeAndCode(AppDitDicConstant.FLAG_TYEP_APP, appFlag);
				TmAppFlag tmAppFlag = new TmAppFlag();
				tmAppFlag.setAppNo(appNo);
				tmAppFlag.setFlagType(AppDitDicConstant.FLAG_TYEP_APP);
				tmAppFlag.setFlagCode(appFlag);
				if(dict!=null) {
					tmAppFlag.setFlagDesc(dict.getCodeName());
					tmAppFlag.setFlagLevel(dict.getValue2());
				}
				tmAppFlag.setCreateUser(OrganizationContextHolder.getUserNo());
				tmAppFlags.add(tmAppFlag);
			}
			applyInputService.saveOrDelTmAppFlagList(appNo,tmAppFlags);
		}
	}
}
