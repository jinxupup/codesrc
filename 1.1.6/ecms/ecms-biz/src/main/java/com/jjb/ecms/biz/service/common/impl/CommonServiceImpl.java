/**
 * 
 */
package com.jjb.ecms.biz.service.common.impl;

import com.alibaba.fastjson.JSON;
import com.jjb.acl.access.service.AccessRoleService;
import com.jjb.acl.biz.dao.TmAclBranchDao;
import com.jjb.acl.biz.dao.TmAclDictDao;
import com.jjb.acl.biz.dao.TmAclUserDao;
import com.jjb.acl.facility.enums.bus.AppType;
import com.jjb.acl.facility.enums.bus.BscSuppIndicator;
import com.jjb.acl.facility.enums.bus.CardClassType;
import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.acl.facility.enums.bus.SubCardType;
import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.acl.infrastructure.TmAclBranch;
import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.acl.infrastructure.TmAclRole;
import com.jjb.acl.infrastructure.TmAclUser;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.apply.TmAppHistoryDao;
import com.jjb.ecms.biz.dao.apply.TmAppModifyHisDao;
import com.jjb.ecms.biz.dao.apply.TmMirCardDao;
import com.jjb.ecms.biz.dao.common.TmDitDicDao;
import com.jjb.ecms.biz.dao.param.TmFieldDao;
import com.jjb.ecms.biz.dao.param.TmProductDao;
import com.jjb.ecms.biz.ext.ddcheck.DDCheck;
import com.jjb.ecms.biz.ext.risk.RiskUtils;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.common.CommonService;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.facility.dto.ApplyPageOnOffParamDto;
import com.jjb.ecms.facility.dto.ApplyRiskInfoDto;
import com.jjb.ecms.facility.dto.FieldProductDto;
import com.jjb.ecms.facility.dto.ValidFieldInfoDto;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCheatCheckData;
import com.jjb.ecms.infrastructure.TmAppCustInfo;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppModifyHis;
import com.jjb.ecms.infrastructure.TmAppPrimAnnexEvi;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.ecms.infrastructure.TmDitDic;
import com.jjb.ecms.infrastructure.TmField;
import com.jjb.ecms.infrastructure.TmMirCard;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.ByteArrayInputStream;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.facility.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 公共接口
 * @author JYData-R&D-BigK.K
 * @date 2016年9月20日 下午7:42:05
 * @version V1.0  
 */
@Service("commonService")
public class CommonServiceImpl implements CommonService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private TmDitDicDao tmDitDicDao;
	
	@Autowired
	private TmMirCardDao tmMirCardDao;
	
	@Autowired
	private TmProductDao tmProductDao;
	
	@Autowired
	private TmAclUserDao tmAclUserDao;
	
	@Autowired
	private TmAclBranchDao tmAclBranchDao;
	
	@Autowired
	private TmAppModifyHisDao tmAppModifyHisDao;
	@Autowired
	private AccessRoleService accessRoleService;
	@Autowired
	private CacheContext cacheContext;
	
	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired
	private DDCheck ddCheck;
	@Autowired
	private TmAclDictDao aclDictDao;
	@Autowired
	private TmAppHistoryDao tmAppHistoryDao;
	@Autowired
	private TmFieldDao tmFieldDao;
	/**
	 * 申请录入页面必输项字段配置
	 * @param appType 申请类型
	 * @param tmProduct 卡产品
	 * @param num 附卡编号 从0开始
	 * A:主附同申部分附卡
	 * B:独立主卡
	 * S:独立附卡
	 */
	public HashMap<String,List<ValidFieldInfoDto>> validateField(String appType, TmProduct tmProduct, Integer num) {
		if(StringUtils.isEmpty(appType)){
			logger.error("获取必输配置项[申请类型]参数为空");
			throw new ProcessException("获取必输配置项[申请类型]参数为空");
		}
		if(!AppType.B.name().equals(appType) && num == null){
			logger.error("获取必输配置项[附卡编号]参数为空");
			throw new ProcessException("获取必输配置项[附卡编号]参数为空");
		}
		if(tmProduct == null || StringUtils.isEmpty(tmProduct.getProductCd())){
			logger.error("获取必输配置项[卡产品代码]参数为空");
			throw new ProcessException("获取必输配置项[卡产品代码]参数为空");
		}
		String productCd = tmProduct.getProductCd();
		String cardBin = tmProduct.getBin();
		int cardBinLen = 6;//卡bin长度,默认6位长度
		if(StringUtils.isNotEmpty(cardBin)){
			cardBinLen=cardBin.trim().length();
		}
		int cardNoLen = cardBinLen+tmProduct.getCardNoRangeCeil().length()+1;//卡号长度默认16位
		Integer editCardNoLen = cardNoLen - cardBinLen - 1;
		String appLmtMax = null;
		if(tmProduct.getApprovalMaximum()==null){
			appLmtMax = String.valueOf(5000);//申请额度默认5000元
		}else {
			appLmtMax = tmProduct.getApprovalMaximum().toString();
		}
		List<FieldProductDto> fieldProductDtoList = cacheContext.getFieldProductDtosByProductCd(productCd);
		HashMap<String,List<ValidFieldInfoDto>> validFieldMap = null;
		if(CollectionUtils.sizeGtZero(fieldProductDtoList)){
			logger.info("=====>>开始配置必输项字段");
			String field = "";
			String fieldNew = "";
			String tabName = "";
			
			List<ValidFieldInfoDto> requierdList = new ArrayList<ValidFieldInfoDto>();
			List<ValidFieldInfoDto> attachList = new ArrayList<ValidFieldInfoDto>();//添加附卡的时候必输项
			for (FieldProductDto fieldProductDto : fieldProductDtoList) {
				String requiredFlag = fieldProductDto.getIfRequiredItem();//必输项标志
				if((StringUtils.isNotBlank(requiredFlag) && requiredFlag.contains(appType)) || StringUtils.isNotBlank(fieldProductDto.getFieldRegexp())
						|| (StringUtils.isNotBlank(fieldProductDto.getBetweenMin()) && StringUtils.isNotBlank(fieldProductDto.getBetweenMax()))
						|| StringUtils.isNotBlank(fieldProductDto.getMaxLength())){//找出有校验项
					field = fieldProductDto.getFieldEn();
					tabName = fieldProductDto.getTabName();
					ValidFieldInfoDto validFieldInfoDto = new ValidFieldInfoDto();
					// 附卡信息
					if (AppConstant.TmAppCustInfo.equals(tabName)) {
						if(AppType.S.name().equals(appType)){//独立附卡
							fieldNew = tabName + "." + field;
							validFieldInfoDto.setField(fieldNew);
							requierdList.add(getValidFieldInfoDto(appType,fieldProductDto,validFieldInfoDto,editCardNoLen,appLmtMax));
						}else {//主附同申
							if(num != null){
								for (int i = 0; i < num; i++) {
									ValidFieldInfoDto newValidFieldInfoDto = new ValidFieldInfoDto();
									fieldNew = tabName + "[" + i + "]." + field;
									newValidFieldInfoDto.setField(fieldNew);
									requierdList.add(getValidFieldInfoDto(appType,fieldProductDto,newValidFieldInfoDto,editCardNoLen,appLmtMax));
								}
								//添加附卡操作校验字段项处理
								fieldNew = tabName + "[' + num + ']." + field;
								validFieldInfoDto.setField(fieldNew);
								attachList.add(getValidFieldInfoDto(appType,fieldProductDto,validFieldInfoDto,editCardNoLen,appLmtMax));
							}else {
								fieldNew = tabName + "[1]." + field;
								validFieldInfoDto.setField(fieldNew);
								requierdList.add(getValidFieldInfoDto(appType,fieldProductDto,validFieldInfoDto,editCardNoLen,appLmtMax));
							}
						}
					}else if(AppConstant.TmAppContact.equals(tabName)) {//联系人信息
						if(fieldProductDto.getFieldRemark() != null) {//区分联系人1还是2(0:亲属；1：其他)
							fieldNew = tabName + "["+ fieldProductDto.getFieldRemark() + "]." + field;
						}else {
							fieldNew = tabName + "[0]." + field;//默认亲属联系人必填
						}
						validFieldInfoDto.setField(fieldNew);
						requierdList.add(getValidFieldInfoDto(appType,fieldProductDto,validFieldInfoDto,editCardNoLen,appLmtMax));
					}else {
						fieldNew = tabName + "." + field;
						validFieldInfoDto.setField(fieldNew);
						requierdList.add(getValidFieldInfoDto(appType,fieldProductDto,validFieldInfoDto,editCardNoLen,appLmtMax));
					}
				}
			}
			logger.info("=====>>必输项字段配置成功<<=====");
			validFieldMap = new HashMap<String, List<ValidFieldInfoDto>>();
			if(AppType.A.name().equals(appType)){
				validFieldMap.put(AppConstant.bscSuppInd_S, attachList);
			}
			validFieldMap.put(AppConstant.MAIN_TAB, requierdList);
		}
		
		return validFieldMap;
	}
	
	/**
	 * 转化页面字段校验实体信息
	 * @param appType
	 * @param fieldProductDto
	 * @param validFieldInfoDto
	 * @param editCardNoLen 可编辑自选卡号长度
	 * @param appLmtMax 卡产品最大申请额度，默认5000
	 * @return
	 */
	private ValidFieldInfoDto getValidFieldInfoDto(String appType, FieldProductDto fieldProductDto, ValidFieldInfoDto validFieldInfoDto,
			Integer editCardNoLen ,String appLmtMax) {
		if(fieldProductDto != null && validFieldInfoDto != null){
			if(StringUtils.isNotBlank(fieldProductDto.getBetweenMin()) && StringUtils.isNotBlank(fieldProductDto.getBetweenMax())){
				validFieldInfoDto.setBetweenFlag(true);
				validFieldInfoDto.setBetweenMin(fieldProductDto.getBetweenMin());
				if(fieldProductDto.getFieldEn().endsWith("appLmt")){//申请额度
					validFieldInfoDto.setBetweenMax(fieldProductDto.getBetweenMax().trim().replace("MAX", appLmtMax));
				}else {
					validFieldInfoDto.setBetweenMax(fieldProductDto.getBetweenMax());
				}
			}
			if(StringUtils.isNotBlank(fieldProductDto.getMaxLength())){
				validFieldInfoDto.setLengthFlag(true);
				validFieldInfoDto.setLengthMax(fieldProductDto.getMaxLength());
			}
			if(StringUtils.isNotBlank(fieldProductDto.getFieldRegexp())){
				validFieldInfoDto.setRegexpFlag(true);
				if(fieldProductDto.getFieldEn().endsWith("cardNo")){//自选卡号
					validFieldInfoDto.setRegexp(fieldProductDto.getFieldRegexp().replace("NUM", editCardNoLen.toString()));
				}else {
					validFieldInfoDto.setRegexp(fieldProductDto.getFieldRegexp());
				}
			}
			if(fieldProductDto.getIfRequiredItem().contains(appType)){
				validFieldInfoDto.setNotEmptyFlag(true);
			}
			validFieldInfoDto.setFieldName(fieldProductDto.getFieldName());
		}
		return validFieldInfoDto;
	}
	
	/**
	 * ditdic表根据类型分类
	 * @param tmDitDic
	 * @return
	 */
	@Override
	@Transactional
	public List<TmDitDic> selectGroupType(TmDitDic tmDitDic) {
		return tmDitDicDao.selectGroupType(tmDitDic);
	}
	
	/**
	 * 根据参数查询ditdic列表
	 * @param tmDitDic
	 * @return
	 */
	@Override
	@Transactional
	public List<TmDitDic> queryForList(TmDitDic tmDitDic){
		return tmDitDicDao.queryForList(tmDitDic);
		
	}

	/**
	 * 根据主卡卡号primCardNo
	 * 获取卡产品代码、卡面信息、主卡申请人信息
	 */
	@Override
	@Transactional
	public ApplyInfoDto queryPrimApplicantInfoByPrimCardNo(String primCardNo , ApplyInfoDto applyInfoDto) {
		// TODO Auto-generated method stub
		
		if(applyInfoDto==null || applyInfoDto.getTmAppMain()==null){
			throw new ProcessException("未查询到该卡["+primCardNo+"]申请件信息，请重试!");
		}
		TmMirCard tmMirCard = tmMirCardDao.getByCardNo(primCardNo);
		if(tmMirCard != null){
			TmAppMain tmAppMain = applyInfoDto.getTmAppMain();
			if (tmAppMain != null) {
				tmAppMain.setProductCd(tmMirCard.getProductCd());//设置卡产品代码
			}
			applyInfoDto.setTmAppMain(tmAppMain);
			TmAppPrimCardInfo cardInfo = applyInfoDto.getTmAppPrimCardInfo();
			cardInfo.setCardfaceCd(tmMirCard.getPyhCd()); //设置卡面
			applyInfoDto.setTmAppPrimCardInfo(cardInfo);
			//获取主卡人信息
			String oldAppNo = tmMirCard.getAppNo();
			if(StringUtils.isNotEmpty(oldAppNo)){
				TmAppCustInfo primCust = applyQueryService.getTmAppPrimCustByAppNo(oldAppNo);
				if(primCust!=null){
					primCust.setAppNo(tmAppMain.getAppNo());
				}else{
					logger.error("原主卡申请["+oldAppNo+"]信息不存在,主卡卡号为["+primCardNo+"]");
					throw new ProcessException("原主卡申请["+oldAppNo+"]信息不存在,主卡卡号为["+primCardNo+"]");
				}
				List<TmAppCustInfo> custs = applyInfoDto.getTmAppCustInfoList();
				if(custs==null){
					custs = new ArrayList<TmAppCustInfo>();
				}
				custs.add(primCust);
				
			}else {
				logger.error("卡片未找到申请件编号信息");
				throw new ProcessException("卡片未找到申请件编号信息,主卡卡号为["+primCardNo+"]");
			}
		}
		
		return applyInfoDto;
	}
	
	/***
	 * 重审时，有一些数据作了修改，在往数据库存之前比对一下那些数据作了修改，并替换掉
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public Object compareAndUpdateObject(Object newObj, Object oldObj)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		 if(newObj!=null&&oldObj!=null){//如果新对象不为空，就执行替换方法
			 Field[] field=newObj.getClass().getDeclaredFields();//获取实体类的所有属性，返回field数组
			 for(int j=0;j<field.length;j++){//遍历所有属性
				 String name =field[j].getName();//获取属性名字
				 if(!name.equals("serialVersionUID")){
				 name=name.substring(0,1).toUpperCase()+name.substring(1);//将属性的首字符大写，方便构造get，set方法
				  String type = field[j].getGenericType().toString();    //获取属性的类型
				 Method get=newObj.getClass().getMethod("get"+name);
//				 Method set=newObj.getClass().getMethod("set"+name,type.getClass());
				 if(type.equals("class java.lang.String")){   //如果type是类类型，则前面包含"class "，后面跟类名               
	                    String value = (String) get.invoke(newObj);    //调用getter方法获取属性值
	                    Method set=newObj.getClass().getMethod("set"+name,String.class);
	                   if(value != null){
	                	   set.invoke(oldObj,value);                    
	                    }
	                }
	                if(type.equals("class java.lang.Integer")){                
	                    Integer value = (Integer) get.invoke(newObj);
	                    Method set=newObj.getClass().getMethod("set"+name,Integer.class);
	                    if(value != null){
	                    	set.invoke(oldObj,value);      
	                    }
	                }
	                if(type.equals("class java.lang.Short")){     
	                    Short value = (Short) get.invoke(newObj);
	                    Method set=newObj.getClass().getMethod("set"+name,Short.class);
	                    if(value != null){
	                    	set.invoke(oldObj,value);                
	                    }
	                }
	            	if (type.equals("class java.math.BigDecimal")) {
						BigDecimal value = (BigDecimal) get.invoke(newObj);
						Method set=newObj.getClass().getMethod("set"+name,BigDecimal.class);
						 if(value != null){
							 set.invoke(oldObj,value);   
		                    }
	            	}
	                if(type.equals("class java.lang.Double")){     
	                    Double value = (Double) get.invoke(newObj);
	                    Method set=newObj.getClass().getMethod("set"+name,Double.class);
	                    if(value != null){                    
	                    	set.invoke(oldObj,value);  
	                    }
	                }                  
	                if(type.equals("class java.lang.Boolean")){
	                    Boolean value = (Boolean) get.invoke(newObj);
	                    Method set=newObj.getClass().getMethod("set"+name,Boolean.class);
	                    if(value != null){                      
	                    	set.invoke(oldObj,value);  
	                    }
	                }
	                if(type.equals("class java.util.Date")){           
	                    Date value = (Date) get.invoke(newObj);
	                    Method set=newObj.getClass().getMethod("set"+name,Date.class);
	                    if(value != null){
	                    	set.invoke(oldObj,value);  
	                    }
	                } 
				 }
			 }
			 
		 }
		 
		 return oldObj;
	 }
	
	/**
	 * 获取当前操作员的权限列表
	 * 
	 * @return
	 */
	@Override
	@Transactional
	public List<String> getCurrUserResourceCodeList() {
		HashMap<String, String> resourceMap = OrganizationContextHolder.getResourceList();
		List<String> resourceCodeList = new ArrayList<String>();
		if(resourceMap != null && resourceMap.size() > 0){
			for (String resourceCode : resourceMap.keySet()) {
				resourceCodeList.add(resourceCode);
			}
		}
		return resourceCodeList;
	}
	
	
	/**
	 * 根据用户名获取角色列表
	 * @return
	 */
	public List<TmAclRole> getRoleListByUserNo(String userNo){
		List<TmAclRole> roleList = new ArrayList<TmAclRole>();
		if(StringUtils.isNotEmpty(userNo)){
			roleList = accessRoleService.getRolesList(userNo,OrganizationContextHolder.getOrg());
		}
		return roleList;
	}
	
	/**
	 * 根据用户名获取角色map集合
	 * @return
	 */
	public LinkedHashMap<Object, Object> getRoleMapByUserNo(String userNo){
//		List<TmAclRole> roleList = getRoleListByUserNo(OrganizationContextHolder.getUserNo());
		HashMap<String, String> roleMap = OrganizationContextHolder.getRoleList();
		LinkedHashMap<Object, Object> userRoles = new LinkedHashMap<Object, Object>();
		if(roleMap != null && roleMap.size() > 0){
			for(String roleId : roleMap.keySet()){
				userRoles.put(roleId, roleMap.get(roleId));
			}
		}
		return userRoles;
	}
	
	/**
	 * 获取所有的user
	 * @param 
	 * @return
	 */
	@Override
	@Transactional
	public LinkedHashMap<Object, Object> getAllUser(String userNo) {
		List<TmAclUser> userList = tmAclUserDao.queryForList(new TmAclUser());
		LinkedHashMap<Object, Object> users = new LinkedHashMap<Object, Object>();
		if(CollectionUtils.isNotEmpty(userList)) {
			for(TmAclUser tmAclUser : userList) {
				if(StringUtils.isNotBlank(userNo) && userNo.equals(tmAclUser.getUserNo())){
					continue;
				}
				users.put(tmAclUser.getUserNo(),tmAclUser.getUserName());
			}
		}
		return users;
	}
	
	/**
	 * 根据卡产品代码获取可降级卡产品列表
	 * 例：白金卡可降级所有卡产品的金卡
	 * @param appType
	 * @param productCd
	 * @return
	 */
	@Override
	@Transactional
	public LinkedHashMap<Object, Object> getDemtionProductList(String appType,
			String productCd) {
		// TODO Auto-generated method stub
		LinkedHashMap<Object, Object> proLinkedHashMap = new LinkedHashMap<Object, Object>();
		TmProduct product = new TmProduct();//定义当前的卡产品
		if(StringUtils.isNotBlank(appType) && StringUtils.isNotBlank(productCd)){
			product = cacheContext.getProduct(productCd);
			String cardClass = product == null ? null : product.getCardClass();
			if( StringUtils.isNotBlank(appType) && StringUtils.isNotBlank(cardClass)){
				//如果是独立主卡，可申请任意卡产品
				proLinkedHashMap = getProductList(appType, cardClass);
			}
		}
		if(product!=null){
			if(!(proLinkedHashMap != null && proLinkedHashMap.size() > 0 && proLinkedHashMap.containsKey(product.getProductCd())) && product.getProductCd()!=null){
				//不包含此卡产品
				proLinkedHashMap.put(product.getProductCd(),product.getProductDesc());
			}
		}
		return proLinkedHashMap;
	}
	
	/**
	 * 查询可选卡产品
	 * @param appType
	 * @param cardClass
	 * @return
	 */
	private LinkedHashMap<Object, Object> getProductList(String appType, String cardClass){
		LinkedHashMap<Object, Object> proLinkedHashMap = new LinkedHashMap<Object, Object>();
		Map<String, Object> parameter = new HashMap<String, Object>();//查询过滤条件
		if(StringUtils.isNotBlank(cardClass)){
			if(cardClass.equals(CardClassType.D.state)){//钻石卡
				String[] cardClass_in = {CardClassType.P.state,CardClassType.G.state,CardClassType.N.state};
				parameter.put("cardClass_in", cardClass_in);
			}else if(cardClass.equals(CardClassType.P.state)){//白金卡
				String[] cardClass_in = {CardClassType.G.state,CardClassType.N.state};
				parameter.put("cardClass_in", cardClass_in);
			}else if(cardClass.equals(CardClassType.G.state)){//金卡
				String[] cardClass_in = {CardClassType.N.state};
				parameter.put("cardClass_in", cardClass_in);
			}
		}
		if(StringUtils.isNotBlank(appType)){
			if(!appType.equals(AppType.B.state)){//如果是主付同申或者是独立附卡的话，则不能申请公务卡
				String[] subCardType_ne = {SubCardType.O.state};
				parameter.put("subCardType_ne", subCardType_ne);
			}
		}
		//查询出可降级卡产品
		List<TmProduct> tmProducts = tmProductDao.getDemtionProducts(parameter);
		if(CollectionUtils.isNotEmpty(tmProducts)){
			for(TmProduct tmProduct : tmProducts){
				proLinkedHashMap.put(tmProduct.getProductCd(), tmProduct.getProductDesc());
			}
		}
		return proLinkedHashMap;
	}
	
	
	
	/**
	 * 根据当前用户获取user
	 * @param userNo
	 * @return
	 */
	@Override
	@Transactional
	public TmAclUser getUserByUserNo(String userNo) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(userNo)){
			userNo = OrganizationContextHolder.getUserNo();
		}
		TmAclUser tmAclUser = tmAclUserDao.getUserByUserNo(userNo);
		
		return tmAclUser;
	}
	
	/**
	 * 根据当前支行机构号获取相关信息
	 */
	@Override
	@Transactional
	public TmAclBranch getTmAclBranch(String branchCode) {
		if(StringUtils.isNotBlank(branchCode)){
			//modify by H.N 20171108
//			TmAclBranch tmAclBranch = tmAclBranchDao.getTmAclBranch(branchCode);
			TmAclBranch tmParam = new TmAclBranch();
			tmParam.setBranchCode(branchCode);
			tmParam.setOrg(OrganizationContextHolder.getOrg());
			TmAclBranch tmAclBranch = tmAclBranchDao.queryByKey(tmParam);
			return tmAclBranch;
		}
		return null;
	}

	/**
	 * 修改历史对比项
	 * @param appType 申请类型
	 * @param   I:录入修改页面;C:初审页面;T:电话调查页面;
	 * 
	 * @return Map
	 */
	@Override
	@Transactional
    public Map<String, Map<String,String>> getHisModifyFieldsMap(String appType, String productCd, String pageType){
		if(StringUtils.isBlank(appType)){
			logger.error("获取修改历史对比项[申请类型]参数为空");
			throw new ProcessException("获取修改历史对比项[申请类型]参数为空");
		}
		if(StringUtils.isBlank(productCd)){
			logger.error("获取修改历史对比项[卡产品代码]参数为空");
			throw new ProcessException("获取修改历史对比项[卡产品代码]参数为空");
		}
		if(StringUtils.isBlank(pageType)){
			logger.error("获取修改历史对比项[页面节点类型]参数为空");
			throw new ProcessException("获取修改历史对比项[页面节点类型]参数为空");
		}

		List<FieldProductDto> fieldProductDtoList = cacheContext.getFieldProductDtosByProductCd(productCd);
    	Map<String, Map<String,String>> fieldsMpMap = new HashMap<String, Map<String,String>>();
		if(CollectionUtils.sizeGtZero(fieldProductDtoList)){
			Map<String,String> tmAppMainMap = new HashMap<String, String>();
			Map<String,String> tmAppAuditMap = new HashMap<String, String>();
			Map<String,String> tmAppCustMap = new HashMap<String, String>();
//			Map<String,String> tmAppAttachApplierInfoMap = new HashMap<String, String>();
//			Map<String,String> tmAppCardfaceInfoMap = new HashMap<String, String>();
			Map<String,String> tmAppPrimAnnexEviMap = new HashMap<String, String>();
//			Map<String,String> tmAppPrimApplicantInfoMap = new HashMap<String, String>();
//			Map<String,String> tmEtcCarMap = new HashMap<String, String>();
			Map<String,String> tmAppPrimCardInfoMap = new HashMap<String, String>();
			Map<String,String> tmAppContactInfoMap = new HashMap<String, String>();//联系人
//			Map<String,String> tmAppContactMap1 = new HashMap<String, String>();//亲属联系人
//			Map<String,String> tmAppContactMap2 = new HashMap<String, String>();//其他联系人
			Map<String,String> tmAppInstalLoanMap = new HashMap<String, String>();//贷款分期表
			
			for (FieldProductDto fieldProductDto : fieldProductDtoList) {
				String pageValue = null;
				if("I".equals(pageType)){//录入及修改
					pageValue = fieldProductDto.getIsInput();
				}else if ("C".equals(pageType)) {//初审
					pageValue = fieldProductDto.getValue1();
				}else if ("T".equals(pageType)) {//电调
					pageValue = fieldProductDto.getValue2();
				}else {
					logger.error("获取修改历史对比项[pageType={}]参数不正确",pageType);
					throw new ProcessException("获取修改历史对比项[pageType="+pageType+"]参数不正确");
				}
				if(StringUtils.isNotBlank(pageValue) && !Indicator.N.name().equals(pageValue)){
					String tabName = fieldProductDto.getTabName();
					String field = fieldProductDto.getFieldEn();
					String fieldName = fieldProductDto.getFieldName();
					if(pageValue.contains(appType)){
						if (AppConstant.TmAppMain.equals(tabName)) {
							tmAppMainMap.put(field, fieldName);
						}else if (AppConstant.TmAppPrimCardInfo.equals(tabName)) {
							tmAppPrimCardInfoMap.put(field, fieldName);
						}else if (AppConstant.TmAppAudit.equals(tabName)) {
							tmAppAuditMap.put(field, fieldName);
						}else if (AppConstant.TmAppCustInfo.equals(tabName)) {
							tmAppCustMap.put(field, fieldName);
						}else if (AppConstant.TmAppPrimAnnexEvi.equals(tabName)) {
							tmAppPrimAnnexEviMap.put(field, fieldName);
						}/*else if (AppConstant.TmAppPrimApplicantInfo.equals(tabName)) {
							tmAppPrimApplicantInfoMap.put(field, fieldName);
						}*/else if (AppConstant.TmAppContact.equals(tabName)) {
//							String fieldRemark = fieldProductDto.getFieldRemark();
//							if(StringUtils.isNotBlank(fieldRemark)){
//								if("0".equals(fieldRemark)) {
//									fieldRemark="第一联系人";
//								}else if ("1".equals(fieldRemark)) {
//									fieldRemark="第二联系人";
//								}
//							}else {
//								logger.error("获取历史对比项联系人信息未配置区分标志，请联系管理员配置联系人字段remark");
//							}
							tmAppContactInfoMap.put(field, fieldName);
							
						}/*else if (AppConstant.TmEtcCar.equals(tabName)) {
							tmEtcCarMap.put(field, fieldName);
						}else if (AppConstant.TmAppCardfaceInfo.equals(tabName)) {
							tmAppCardfaceInfoMap.put(field, fieldName);
						}*/else if (AppConstant.TmAppInstalLoan.equals(tabName)) {
							tmAppInstalLoanMap.put(field, fieldName);
						}
					}
				}
			}
			fieldsMpMap.put(AppConstant.TM_APP_CUST_INFO, tmAppCustMap);
			fieldsMpMap.put(AppConstant.TM_APP_MAIN, tmAppMainMap);
			fieldsMpMap.put(AppConstant.TM_APP_AUDIT, tmAppAuditMap);
			fieldsMpMap.put(AppConstant.TM_APP_PRIM_ANNEX_EVI, tmAppPrimAnnexEviMap);
			fieldsMpMap.put(AppConstant.TM_APP_PRIM_CARD_INFO, tmAppPrimCardInfoMap);
			fieldsMpMap.put(AppConstant.TM_APP_CONTACT, tmAppContactInfoMap);
			fieldsMpMap.put(AppConstant.TM_APP_INSTALMENT_CREDIT, tmAppInstalLoanMap);
		}
		
		return fieldsMpMap;
    }	
	
	/**
	 * 申请信息修改后和修改前的值作对比
	 * @param a 修改后的值
	 * @param b 修改前的值
	 * @param appNo 
	 * @param tableName 表名
	 * @param taskState 任务名
	 */
	@Override
	@Transactional
	public Map<String,Object> compareValue(Map<String, Serializable> a,Map<String, Serializable> b,
			String appNo, String tableName, String taskState, Map<String,String> fieldsMap) {
		Map<String,Object> updateValueMap = new HashMap<String, Object>();
		if(fieldsMap != null && !fieldsMap.isEmpty()){
			for(Map.Entry<String,String> enty : fieldsMap.entrySet()){
				Object newValue = (Object) a.get(enty.getKey());
				Object oldValue = (Object) b.get(enty.getKey());

				if (newValue != null) {
					if (newValue instanceof java.util.Date) {
						newValue = simpleDateFormat.format(newValue);
					}
					if (newValue instanceof java.math.BigDecimal) {
						java.text.DecimalFormat df = new java.text.DecimalFormat("0.00##");
						newValue = df.format(newValue);  
					}
				}

				if (oldValue != null) {
					if (oldValue instanceof java.util.Date) {
						oldValue = simpleDateFormat.format(oldValue);
					}
					if (oldValue instanceof java.math.BigDecimal) {
						java.text.DecimalFormat df = new java.text.DecimalFormat("0.00##");
						oldValue = df.format(oldValue);  
					}
				}
				if (newValue == null && oldValue == null) {
					updateValueMap.put(enty.getKey(), newValue);
					continue;
				} else if (newValue != null && oldValue == null) {
					if(StringUtils.isNotBlank(taskState) && !StringUtils.equals(taskState,RtfState.A05.taskName)
							&& !StringUtils.equals(taskState,RtfState.A10.taskName) && !StringUtils.equals(taskState,RtfState.B05.taskName)
							&& !StringUtils.equals(taskState,RtfState.B10.taskName)){
						TmAppModifyHis tmAppModifyHis = new TmAppModifyHis();
						tmAppModifyHis.setTableName(tableName);
						tmAppModifyHis.setColumnName(enty.getKey());
						tmAppModifyHis.setNewValue(newValue.toString());
						tmAppModifyHis.setOldValue("");
						tmAppModifyHis.setUpdateUser(OrganizationContextHolder.getUserNo());
						tmAppModifyHis.setUpdateTime(new Date());
						tmAppModifyHis.setOrg(OrganizationContextHolder.getOrg());
						tmAppModifyHis.setTaskName(taskState);
						tmAppModifyHis.setAppNo(appNo);
//					tmAppModifyHis.setJpaVersion(1);
						tmAppModifyHis.setReservedField1(enty.getValue());
						tmAppModifyHisDao.saveTmAppModifyHis(tmAppModifyHis);
					}
					updateValueMap.put(enty.getKey(), newValue);
				}else if (newValue == null && oldValue != null) {
					if(StringUtils.isNotBlank(taskState) && !StringUtils.equals(taskState,RtfState.A05.taskName)
							&& !StringUtils.equals(taskState,RtfState.A10.taskName) && !StringUtils.equals(taskState,RtfState.B05.taskName)
							&& !StringUtils.equals(taskState,RtfState.B10.taskName)){
						TmAppModifyHis tmAppModifyHis = new TmAppModifyHis();
						tmAppModifyHis.setTableName(tableName);
						tmAppModifyHis.setColumnName(enty.getKey());
						tmAppModifyHis.setNewValue("");
						tmAppModifyHis.setOldValue(oldValue.toString());
						tmAppModifyHis.setUpdateUser(OrganizationContextHolder.getUserNo());
						tmAppModifyHis.setUpdateTime(new Date());
						tmAppModifyHis.setOrg(OrganizationContextHolder.getOrg());
						tmAppModifyHis.setTaskName(taskState);
						tmAppModifyHis.setAppNo(appNo);
//					tmAppModifyHis.setJpaVersion(1);
						tmAppModifyHis.setReservedField1(enty.getValue());
						tmAppModifyHisDao.saveTmAppModifyHis(tmAppModifyHis);
					}
					//增加判断用oldValue或newValue；判断条件为：页面配置中的启用标志
					TmField tmField = new TmField();
					tmField.setFieldEn(enty.getKey());
					tmField=tmFieldDao.queryForOne(tmField);
					if(tmField !=null) {
						if (StringUtils.equals(tmField.getIfUsed(), "Y")) {
							updateValueMap.put(enty.getKey(), newValue);
						} else {
							updateValueMap.put(enty.getKey(), oldValue);
						}
					}else{
						updateValueMap.put(enty.getKey(), oldValue);
					}
				} else if (newValue != null && oldValue != null && newValue.toString().equals(oldValue.toString())) {
					updateValueMap.put(enty.getKey(), newValue);
					continue;
				} else if (newValue != null && oldValue != null && !newValue.toString().equals(oldValue.toString())) {
					if(StringUtils.isNotBlank(taskState) && !StringUtils.equals(taskState,RtfState.A05.taskName)
							&& !StringUtils.equals(taskState,RtfState.A10.taskName) && !StringUtils.equals(taskState,RtfState.B05.taskName)
							&& !StringUtils.equals(taskState,RtfState.B10.taskName)){
						TmAppModifyHis tmAppModifyHis = new TmAppModifyHis();
						tmAppModifyHis.setTableName(tableName);
						tmAppModifyHis.setColumnName(enty.getKey());
						tmAppModifyHis.setNewValue(newValue.toString());
						tmAppModifyHis.setOldValue(oldValue.toString());
						tmAppModifyHis.setUpdateUser(OrganizationContextHolder.getUserNo());
						tmAppModifyHis.setUpdateTime(new Date());
						tmAppModifyHis.setOrg(OrganizationContextHolder.getOrg());
						tmAppModifyHis.setTaskName(taskState);
						tmAppModifyHis.setAppNo(appNo);
//					tmAppModifyHis.setJpaVersion(1);
						tmAppModifyHis.setReservedField1(enty.getValue());
						tmAppModifyHisDao.saveTmAppModifyHis(tmAppModifyHis);
					}
					updateValueMap.put(enty.getKey(), newValue);
				}
			}
		}
		return updateValueMap;
	}
	
	/**
	 * 申请信息修改后更新对应表字段的值
	 * @param clazz 
	 * @param modifiedfieldsMap 发生修改的字段集合
	 */
	@Override
	@Transactional
	public <T> T getModifiedClazz(T clazz, Map<String, Object> modifiedfieldsMap) {
		// TODO Auto-generated method stub
		if(modifiedfieldsMap == null || modifiedfieldsMap.isEmpty() || modifiedfieldsMap.size() == 0){
			return clazz;
		}
		Field[] fields = clazz.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			if(modifiedfieldsMap.containsKey(fields[i].getName())){
				String fieldName = fields[i].getName();
				Object objValue =null;
				try {
					objValue = modifiedfieldsMap.get(fields[i].getName());
					if(objValue != null){
						try {
							if(fields[i].getType().equals(Date.class)){
								fields[i].set(clazz, DateUtils.stringToDate(StringUtils.valueOf(objValue),DateUtils.DAY_YMD_LINE));
							}
							if(fields[i].getType().equals(String.class)){   //如果type是类类型，则前面包含"class "，后面跟类名               
			                   fields[i].set(clazz, objValue.toString());
			                }
			                if(fields[i].getType().equals(Integer.class)){                
			                    fields[i].set(clazz, Integer.valueOf(objValue.toString()));
			                }
			                if(fields[i].getType().equals(Short.class)){     
			                	fields[i].set(clazz, Short.valueOf(objValue.toString()));
			                }
			            	if(fields[i].getType().equals(BigDecimal.class)) {
			            		fields[i].set(clazz, new BigDecimal(objValue.toString()));
			            	}
			                if(fields[i].getType().equals(Double.class)){     
			                	fields[i].set(clazz, Double.valueOf(objValue.toString()));
			                }                  
			                if(fields[i].getType().equals(Boolean.class)){
			                	fields[i].set(clazz, Boolean.valueOf(objValue.toString()));
			                }
						} catch (ParseException e) {
							logger.error("历史修改信息类型转换失败：{}",e);
						}
					}else {//置空
						fields[i].set(clazz, null);
					}
				} catch (Exception e) {
					logger.warn("获取字段["+fieldName+"]值["+objValue+"]异常,"+e.getMessage());
				}
			}
			continue;
		}
		return clazz;
	}

	/**
	 * 将数组转化为字符串
	 * @param strs 字符串数组
	 * @param separator 分隔符
	 * @return
	 */
	@Override
	@Transactional
	public String arrayToString(String[] strs,String separator) {
		String result = null;
		if(strs != null && strs.length > 0){
			StringBuffer strBuf = new StringBuffer();
			int i = 0;
			for (String str : strs) {
				i++;
				if(StringUtils.isEmpty(str)){
					continue;
				}
				if(i == strs.length){
					strBuf = strBuf.append(str);
				}else {
					strBuf = strBuf.append(str + separator);
				}
			}
			result = strBuf.toString();
		}
		
		return result;
	}
	
	/**
	 * 将list转化为字符串
	 * @param list 列表
	 * @param separator 分隔符
	 * @return
	 */
	@Override
	@Transactional
	public String listToString(List<String> list,String separator) {
		String result = null;
		if(CollectionUtils.sizeGtZero(list)){
			StringBuffer strBuf = new StringBuffer();
			int i = 0;
			for (String str : list) {
				i++;
				if(StringUtils.isEmpty(str)){
					continue;
				}
				if(i == list.size()){
					strBuf = strBuf.append(str);
				}else {
					strBuf = strBuf.append(str + separator);
				}
			}
			result = strBuf.toString();
		}
		
		return result;
	}
	
	/**
	 * 约定还款账户有效性校验
	 * @param acctName	约定还款账户姓名
	 * @param idType	证件类型
	 * @param idNo	证件号码
	 * @param branchNo	开户行号
	 * @param acctNo	账户号码
	 * @param acctNoType 行内/外账户标志，用于执行不同的验证方法
	 * @return
	 */
	@Override
	@Transactional
	public String checkAccountNo(String acctName, String idType, String idNo, String branchNo, String acctNo, String acctNoType) {
		return ddCheck.checkAccountNo(acctName, idType, idNo, branchNo, acctNo, acctNoType);
	}
	
	/**
	 * 获取行内客户号
	 * @return
	 * @throws ProcessException
	 */
	public String getInnerCustNo(TmAppMain tmAppMain, List<TmAppCustInfo> custInfos, BscSuppIndicator bscSuppInd,
			TmAppPrimCardInfo tmAppPrimCardInfo,TmAppPrimAnnexEvi  tmAppPrimAnnexEvi) throws ProcessException {
		TmDitDic ditDic = cacheContext.getApplyOnlineOnOff("getInnerCustNo");// 获取开关
		if (ditDic != null && Indicator.Y.name().equals(ditDic.getRemark())) {
			logger.info("获取行内客户号开始");
			String innerCustNo = null;
			try {
//				innerCustNo = bankServiceForAps.queryCustomerAndNewCustomer(getBankCustInfoQueryAndNewReq(tmAppMain, custInfos, bscSuppInd, tmAppPrimCardInfo,tmAppPrimAnnexEvi), false);
			} catch(Exception e) {
				logger.error("获取行内客户号异常{}", e);
				throw new ProcessException("获取行内客户号异常,失败信息-["+e.getMessage()+"]");
			}
			logger.info("行内客户号获取结束[{}]", innerCustNo);
			return innerCustNo;
		}else {
			return null;
		}
	}
	
	
	/**
	 * 获取默认的评分表
	 * @return
	 * @throws ProcessException
	 */
	@Override
	@Transactional
	public List<TmAclDict> geTmAclDictByParam(TmAclDict tmAclDict) throws ProcessException{
		if(tmAclDict==null){
			throw new ProcessException("查询业务字段条件为空，请确认查询条件或稍后再试");
		}
		tmAclDict.setOrg(null);
		boolean rs = true;
		Map<String, Serializable> map = tmAclDict.convertToMap();
		for (String key : map.keySet()) {
			Object ob = map.get(key);
			if(!key.equals("org") && !key.equals("jpaVersion") 
					&& ob!=null && StringUtils.isNotEmpty(ob.toString())){
				rs = false;
			}
		}
		if(rs){
			throw new ProcessException("查询业务字段条件为空，请确认查询条件或稍后再试");
		}

		tmAclDict.setOrg(OrganizationContextHolder.getOrg());
		List<TmAclDict> tmAclDictList = aclDictDao.queryForList(tmAclDict);
		return tmAclDictList;
	}
	
	/**
	 * 退回操作获取上一节点审批经办人
	 * @param appNo 当前申请件编号
	 * @param oldAppNo 重申件原申请件编号
	 * @param rtfState 
	 * @return 当前申请件审批状态
	 */
	@Override
	@Transactional
	public String getHisOpUser(String appNo, String oldAppNo, String rtfState) {
		// TODO Auto-generated method stub
		logger.info("退回操作获取上一节点审批经办人,appNo=[{}], oldAppNo=[{}]",appNo,oldAppNo);
		List<String> rtfList = new ArrayList<String>();
		
		if(StringUtils.isEmpty(appNo)){
			logger.info("申请件退回操作获取上一节点审批经办人时申请件编号为空");
			throw new ProcessException("申请件退回操作获取上一节点审批经办人时申请件编号为空");
		}
		if(StringUtils.isEmpty(rtfState)){
			logger.info("申请件[{}]退回操作获取上一节点审批经办人时审批状态信息为空",appNo);
			throw new ProcessException("申请件["+appNo+"]退回操作获取上一节点审批经办人时审批状态信息为空");
		}
		if(rtfState.equals(RtfState.A25.toString())){//重审退回到人工核查
			if (StringUtils.isNotEmpty(oldAppNo)) {
				appNo = oldAppNo;
			}
			rtfList.add(RtfState.E15.name());//人工核查退回
			rtfList.add(RtfState.E14.name());//人工核查完成
		}else if (rtfState.equals(RtfState.A30.toString())) {//重审退回到初审
			if (StringUtils.isNotEmpty(oldAppNo)) {
				appNo = oldAppNo;
			}
			rtfList.add(RtfState.F01.name());//初审至风控决策
			rtfList.add(RtfState.F02.name());//初审退回人工核查
			rtfList.add(RtfState.F06.name());//初审拒绝至终审
			rtfList.add(RtfState.F07.name());//初审退回至录入修改
			rtfList.add(RtfState.F08.name());//初审至补件
			rtfList.add(RtfState.F09.name());//初审直接拒绝
			rtfList.add(RtfState.F10.name());//初审完成
			rtfList.add(RtfState.F11.name());//初审退回至电调
			rtfList.add(RtfState.F21.name());//初审免电话调查
		}else if (rtfState.equals(RtfState.A35.toString())) {//重审退回到电调
			if (StringUtils.isNotEmpty(oldAppNo)) {
				appNo = oldAppNo;
			}
			rtfList.add(RtfState.F16.name());//电调拒绝至终审
			rtfList.add(RtfState.F18.name());//电调退回至初审
			rtfList.add(RtfState.F19.name());//电调退回至录入修改
			rtfList.add(RtfState.F20.name());//电调完成
		}else if (rtfState.equals(RtfState.A40.toString())) {//重审退回到终审
			if (StringUtils.isNotEmpty(oldAppNo)) {
				appNo = oldAppNo;
			}
			rtfList.add(RtfState.K08.name());//终审退回至电调
			rtfList.add(RtfState.K18.name());//终审退回至初审
			rtfList.add(RtfState.K15.name());//终审拒绝
			rtfList.add(RtfState.K10.name());//终审完成
		}else if (rtfState.equals(RtfState.B15.toString())) {//录入复核退回到录入修改
			rtfList.add(RtfState.A10.name());//录入完成
		}else if (rtfState.equals(RtfState.E15.toString())) {//人工核查退回到录入修改
			rtfList.add(RtfState.A10.name());//录入完成
		}else if (rtfState.equals(RtfState.F02.toString())) {//初审退回到人工核查
			rtfList.add(RtfState.E15.name());//人工核查退回
			rtfList.add(RtfState.E14.name());//人工核查完成
		}else if (rtfState.equals(RtfState.F07.toString())) {//初审退回到录入修改
			rtfList.add(RtfState.A10.name());//录入完成
		} else if(rtfState.equals(RtfState.F03.toString())){//初审退回预审
			rtfList.add(RtfState.B20.name());//预审通过
		} else if (rtfState.equals(RtfState.F11.toString())) {//初审退回到电调
			rtfList.add(RtfState.F16.name());//电调拒绝至终审
			rtfList.add(RtfState.F18.name());//电调退回至初审
			rtfList.add(RtfState.F20.name());//电调完成
		}else if (rtfState.equals(RtfState.F18.toString())) {//电调退回到初审
			rtfList.add(RtfState.F02.name());//初审退回人工核查
			rtfList.add(RtfState.F06.name());//初审退回人工核查
			rtfList.add(RtfState.F07.name());//初审退回至录入修改
			rtfList.add(RtfState.F08.name());//初审至补件
			rtfList.add(RtfState.F09.name());//初审直接拒绝
			rtfList.add(RtfState.F10.name());//初审完成
			rtfList.add(RtfState.F11.name());//初审退回至电调
			rtfList.add(RtfState.F21.name());//初审免电话调查
		}else if (rtfState.equals(RtfState.K08.toString())) {//终审退回到电调
			rtfList.add(RtfState.F16.name());//电调拒绝至终审
			rtfList.add(RtfState.F18.name());//电调退回至初审
			rtfList.add(RtfState.F20.name());//电调完成
		}else if (rtfState.equals(RtfState.K18.toString())) {//终审退回到初审
			rtfList.add(RtfState.F02.name());//初审退回人工核查
			rtfList.add(RtfState.F06.name());//初审退回人工核查
			rtfList.add(RtfState.F07.name());//初审退回至录入修改
			rtfList.add(RtfState.F08.name());//初审至补件
			rtfList.add(RtfState.F09.name());//初审直接拒绝
			rtfList.add(RtfState.F10.name());//初审完成
			rtfList.add(RtfState.F11.name());//初审退回至电调
			rtfList.add(RtfState.F21.name());//初审免电话调查
		}else if (rtfState.equals(RtfState.G10.toString()) || rtfState.equals(RtfState.J05.toString())) {//补件到初审
			rtfList.add(RtfState.F08.name());//初审调查要求补件
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("appNo", appNo);
		map.put("rtfState_in", rtfList.toArray(new String[0]));
		map.put("_SORT_NAME", "createDate");
		map.put("_SORT_ORDER", "DESC");
		List<TmAppHistory> tmHistoryList = tmAppHistoryDao.getAppHistroyByParam(map);
		if (CollectionUtils.sizeGtZero(tmHistoryList)) {
			TmAppHistory tmAppHistory = tmHistoryList.get(0);//已排序，取最新的一条记录
			if(StringUtils.isNotEmpty(tmAppHistory.getRtfState())){
				return tmAppHistory.getOperatorId();
			}
		}

		return null;
	}
	/**
	 * @Author:shiminghong
	 * @Description : 设置人工核查风控信息展示项
	 */
	@Override
	@Transactional
	public void setApplyRiskInfoDto(ApplyRiskInfoDto riskInfo,
			Map<String, Serializable> tmAppNodeInfoRecordMap) {
//		JSONObject respJson =null;
//		//获取风控中英文描述
//		String respExtRiskEnToCnParam =cacheContext.getExtRiskEnToCnParam(AppDitDicConstant.ext_Risk_EnToCn_Param);
//		if (StringUtils.isNotBlank(respExtRiskEnToCnParam)){
//			respJson = JSON.parseObject(respExtRiskEnToCnParam); //转JSON
//		}
		ApplyNodeCheatCheckData nodeCheckData;
		if (tmAppNodeInfoRecordMap.containsKey(AppConstant.APPLY_NODE_CHEAT_CHECK_DATA)) {
			nodeCheckData = (ApplyNodeCheatCheckData) tmAppNodeInfoRecordMap
					.get(AppConstant.APPLY_NODE_CHEAT_CHECK_DATA);
			if (nodeCheckData != null) {
				riskInfo.setStateCode(nodeCheckData.getStateCode());
				riskInfo.setStateDesc(nodeCheckData.getStateDesc());
				riskInfo.setContent(nodeCheckData.getContent());
				riskInfo.setFinalScore(nodeCheckData.getFinalScore());
				riskInfo.setCreditScore(nodeCheckData.getCreditScore());
				riskInfo.setExtScore(nodeCheckData.getExtScore());
				riskInfo.setOtherScore(nodeCheckData.getOtherScore());
				riskInfo.setScoreLevel(nodeCheckData.getScoreLevel());
				riskInfo.setSuggestAmt(nodeCheckData.getSuggestAmt());
				riskInfo.setUnapprovedReasonCode(nodeCheckData.getUnapprovedReasonCode());
				riskInfo.setUnapprovedReasonDesc(nodeCheckData.getUnapprovedReasonDesc());
				riskInfo.setRiskContent(nodeCheckData.getRiskContent());//决策系统返回的原Json数据
				Map<String, Object> riskMap = (Map<String, Object>) JSON.parse(nodeCheckData.getRiskContent());
				if (riskMap != null && riskMap.containsKey("outerProductResult")) {
					Map<String, Object> map2 = (Map<String, Object>) riskMap.get("outerProductResult");
					if ((map2.get("人行征信报告")) != null) {
						Map<String, Object> mapLookPboc = (Map<String, Object>) map2.get("人行征信报告");
						if (StringUtils.isNotEmpty((String) mapLookPboc.get("file_path"))) {
							String a = (String) mapLookPboc.get("file_path");
							riskInfo.setLookPboc(a);
						}
					} else if ((map2.get("人行征信51信用卡")) != null) {
						Map<String, Object> mapLookPboc = (Map<String, Object>) map2.get("人行征信51信用卡");
						if (StringUtils.isNotEmpty((String) mapLookPboc.get("file_path"))) {
							String a = (String) mapLookPboc.get("file_path");
							riskInfo.setLookPboc(a);
						}
					}
					Map<String, Map<String, String>> map1 = new HashMap<String, Map<String, String>>();
					map1 = RiskUtils.getRiskJsonValueToMap("", map1, map2);
					riskInfo.setThirdDataProduct(map1);
				}
				if (riskMap != null && riskMap.containsKey("decisionResult")
						&& riskMap.get("decisionResult") != null) {
					Map<String, Object> desMap = (Map<String, Object>) riskMap.get("decisionResult");
					Map<String, Object> hitMapSrc = (Map<String, Object>) desMap.get("hitRules");
					Map<String, Map<String, String>> map1 = new HashMap<String, Map<String, String>>();
					map1 = RiskUtils.getRiskJsonValueToMap("", map1, hitMapSrc);
					riskInfo.setHitTheRule(map1);
					Map<String, Object> resMapSrc = (Map<String, Object>) desMap.get("resultMap");
					Map<String, Map<String, String>> map2 = new HashMap<String, Map<String, String>>();
					map2 = RiskUtils.getRiskJsonValueToMap("", map2, resMapSrc);
					riskInfo.setTrackingProcess(map2);
				}
				/**
				* @Author:shiminghong
				* @Description :获取初始化风控决策页面所需展示的字段
				*/
				try {
					ObjectInputStream objectInputStream = null;
					ByteArrayInputStream byteArrayInputStream = null;
					byte[] CreditRiskShowParams = cacheContext.getCreditRiskShowParams(AppDitDicConstant.CreditRiskShowParams);
//					JSONObject json = JSONObject.parseObject(CreditRiskShowParams, JSONObject.class, Feature.OrderedField);
//					String showJson = String.valueOf(json.getJSONObject(AppDitDicConstant.CreditRiskShowParams));
					LinkedHashMap<String, LinkedHashMap<String, String>> showMap = new LinkedHashMap<>();
					if (StringUtils.isNotBlank(String.valueOf(CreditRiskShowParams)) && StringUtils.isNotEmpty(riskInfo.getThirdDataProduct())) {
//						showMap = (LinkedHashMap<String, LinkedHashMap<String, String>>) JSON.parseObject(showJson, LinkedHashMap.class, Feature.OrderedField);
						try {
							byteArrayInputStream = new ByteArrayInputStream(CreditRiskShowParams);
							objectInputStream = new ObjectInputStream(byteArrayInputStream);
							showMap = (LinkedHashMap<String, LinkedHashMap<String, String>>) objectInputStream.readObject();
						}catch (IOException e){
							throw  new ProcessException("转换风控决策页面所需展示的字段时IO错误");
						}finally {
							objectInputStream.close();
							byteArrayInputStream.close();
						}
						for (Map.Entry<String, LinkedHashMap<String, String>> mapEntry : showMap.entrySet()) {
							switch (mapEntry.getKey()) {
								case "学信网-学历查询":
									Map<String, String> aMap = mapEntry.getValue();
									if (StringUtils.isNotEmpty(aMap)) {
										for (Map.Entry<String, String> mapLearn : aMap.entrySet()) {
											Map<String, String> learnMap = (Map<String, String>) riskInfo.getThirdDataProduct().get("学信网-学历查询");
											String key = mapLearn.getKey();
											if (StringUtils.isNotEmpty(learnMap)) {
												//以aMap的value作为learnMap的key
												String info = learnMap.get(mapLearn.getValue());
												//覆盖原来的值
												aMap.put(key, info);
											} else if (StringUtils.isEmpty(learnMap)) {
												//覆盖原来的值,默认为空
												aMap.put(key, "");
											}
										}
									}
									break;
								case "人行征信报告":
									Map<String, String> bMap = (Map<String, String>) mapEntry.getValue();
									if (StringUtils.isNotEmpty(bMap)) {
										for (Map.Entry<String, String> mapPboc : bMap.entrySet()) {
											Map<String, String> pbocMap = (Map<String, String>) riskInfo.getThirdDataProduct().get("人行征信报告");
											String key = mapPboc.getKey();
											if (StringUtils.isNotEmpty(pbocMap)) {
												//以aMap的value作为learnMap的key、
												if (StringUtils.equals(key,"说明")){
													bMap.put(key, mapPboc.getValue());
												}else {
													String info = pbocMap.get(mapPboc.getValue());
													//覆盖原来的值
													bMap.put(key, info);
												}
											} else if (StringUtils.isEmpty(pbocMap)) {
												//覆盖原来的值,默认为空
												bMap.put(key, "");
											}
										}
									}
									break;
								case "运营商信息":
									Map<String, String> cMap = (Map<String, String>) mapEntry.getValue();
									if (StringUtils.isNotEmpty(cMap)) {
										for (Map.Entry<String, String> mapOperator : cMap.entrySet()) {
											String mapOperatorKey = mapOperator.getKey();
											if (StringUtils.equals(mapOperator.getKey(), "手机三要素验证结果")) {
												mapOperatorKey = "手机三要素简版—移动联通电信";
											}
											if (StringUtils.equals(mapOperator.getKey(), "手机在网状态验证结果")) {
												mapOperatorKey = "手机在网状态—移动联通电信";
											}
											Map<String, String> operatorMap = (Map<String, String>) riskInfo.getThirdDataProduct().get(mapOperatorKey);
											String key = mapOperator.getKey();
											if (StringUtils.isNotEmpty(operatorMap)) {
												//以aMap的value作为learnMap的key
												String info = operatorMap.get(mapOperator.getValue());
												//覆盖原来的值
												cMap.put(key, info);
											} else if (StringUtils.isEmpty(operatorMap)) {
												//覆盖原来的值,默认为空
												cMap.put(key, "");
											}
										}
									}
									break;
								case "贷前审核-同盾":
									Map<String, String> dMap = (Map<String, String>) mapEntry.getValue();
									if (StringUtils.isNotEmpty(dMap)) {
										for (Map.Entry<String, String> mapTd : dMap.entrySet()) {
											Map<String, String> tdMap = (Map<String, String>) riskInfo.getThirdDataProduct().get("贷前审核-同盾");
											String key = mapTd.getKey();
											if (StringUtils.isNotEmpty(tdMap)) {
												//以aMap的value作为learnMap的key
												String info = tdMap.get(mapTd.getValue());
												//覆盖原来的值
												dMap.put(key, info);
											} else if (StringUtils.isEmpty(tdMap)) {
												//覆盖原来的值,默认为空
												dMap.put(key, "");
											}
										}
									}
									break;
								case "百融多头借贷":
									Map<String, String> eMap = (Map<String, String>) mapEntry.getValue();
									if (StringUtils.isNotEmpty(eMap)) {
										for (Map.Entry<String, String> mapBr : eMap.entrySet()) {
											Map<String, String> brMap = (Map<String, String>) riskInfo.getThirdDataProduct().get("百融借贷意向验证");
											String key = mapBr.getKey();
											if (StringUtils.isNotEmpty(brMap)) {
												//以aMap的value作为learnMap的key
												String info = brMap.get(mapBr.getValue());
												//覆盖原来的值
												eMap.put(key, info);
											} else if (StringUtils.isEmpty(brMap)) {
												//覆盖原来的值,默认为空
												eMap.put(key, "");
											}
										}
									}
									break;
								case "实名信息验证":
									Map<String, String> fMap = (Map<String, String>) mapEntry.getValue();
									if (StringUtils.isNotEmpty(fMap)) {
										for (Map.Entry<String, String> mapRn : fMap.entrySet()) {
											Map<String, String> rnMap = (Map<String, String>) riskInfo.getThirdDataProduct().get("实名信息验证");
											String key = mapRn.getKey();
											if (StringUtils.isNotEmpty(rnMap)) {
												//以aMap的value作为learnMap的key
												String info = rnMap.get(mapRn.getValue());
												//覆盖原来的值
												fMap.put(key, info);
											} else if (StringUtils.isEmpty(rnMap)) {
												//覆盖原来的值,默认为空
												fMap.put(key, "");
											}
										}
									}
									break;
								default:
									Map<String, String> gMap = (Map<String, String>) mapEntry.getValue();
									if (StringUtils.isNotEmpty(gMap)) {
										for (Map.Entry<String, String> mapDefault : gMap.entrySet()) {
											Map<String, String> defaultMap = (Map<String, String>) riskInfo.getThirdDataProduct().get(mapEntry.getKey());
											String key = mapDefault.getKey();
										 	if (StringUtils.isNotEmpty(defaultMap)) {
												//以aMap的value作为learnMap的key
												String info = defaultMap.get(mapDefault.getValue());
												//覆盖原来的值
												gMap.put(key, info);
											} else if (StringUtils.isEmpty(defaultMap)) {
												//覆盖原来的值,默认为空
												gMap.put(key, "");
											}
										}
									}
									break;
							}
						}
					}
					riskInfo.setShowMap(showMap);
				} catch (Exception e) {
					logger.error("获取初始化风控决策页面所需展示的字段异常" + e.getMessage());
				}
				/**
				 * @Author:shiminghong
				 * @Description : 遍历riskInfo中的thirdDataProduct的所有集合，将key英文替换为key中文描述
				 */
	/*			try{
					if (StringUtils.isNotBlank(StringUtils.valueOf(respJson))){
						if (riskInfo.getThirdDataProduct() != null) {
							Map<String, Object> thirdDataProductMap = riskInfo.getThirdDataProduct();
							if (thirdDataProductMap != null) {
								for (Map.Entry<String, Object> objectEntry : thirdDataProductMap.entrySet()) {
									Map<String, String> map = (Map<String, String>) objectEntry.getValue();
									if (map != null) {
										//新建一个hashmap
										HashMap<String, String> newMap = new HashMap<>();
										Set<Map.Entry<String, String>> entries = map.entrySet();
										Iterator<Map.Entry<String, String>> iterator = entries.iterator();
										Map.Entry<String, String> entry;
										while (iterator.hasNext()) {
											entry = iterator.next();
											//往newMap中放入新的Entry
											String newKey = "";
											if (StringUtils.isNotBlank(entry.getKey())) {
												newKey = respJson.getString(entry.getKey().toLowerCase());
											}
											if (StringUtils.isBlank(newKey)) {
												newKey = entry.getKey();
											}
											newMap.put(newKey, entry.getValue());
											//删除老的Entry
											iterator.remove();
										}
										//删除数据之后加入替换之后的数据
										map.putAll(newMap);
									}
								}
							}
						}
					}
			}catch (Exception e){
					logger.error("联机查询所需的中文描述时将key英文替换为key中文描述失败异常"+e.getMessage());
				}*/
			}
		}
	}

	/**
	 * 获取页面参数开关
	 * @return
	 */
	@Override
	@Transactional
	public ApplyPageOnOffParamDto getApplyPageOnOffParamDto() {
		HashMap<String, Object> pageOnOffParamMap = new HashMap<String, Object>();
//		List<TmDitDic> pageOnOffParamList = cacheContext.getPageParameters(AppDitDicConstant.applyPageOnOffParam);
//		Map<String,TmDitDic> onlineOnOffParam = cacheContext.getAllApplyOnlineOnOff();
//		if(CollectionUtils.sizeGtZero(pageOnOffParamList)){
//			for (TmDitDic tmDitDic : pageOnOffParamList) {
//				String remark = tmDitDic.getRemark();
//				String itemName = tmDitDic.getItemName();
//				if(StringUtils.isNotEmpty(remark) && StringUtils.isNotEmpty(itemName)){
//					pageOnOffParamMap.put(itemName, remark);
//				}
//			}
//		}
//		if(onlineOnOffParam!=null){
//			for (TmDitDic dic : onlineOnOffParam.values()) {
//				pageOnOffParamMap.put(dic.getItemName(), dic.getRemark());
//			}
//		}
		ApplyPageOnOffParamDto applyPageOnOffParamDto = new ApplyPageOnOffParamDto();
		if(pageOnOffParamMap != null && pageOnOffParamMap.size() > 0){
			applyPageOnOffParamDto.updateFromMap(pageOnOffParamMap);
		}
		
		return applyPageOnOffParamDto;
	}
}
