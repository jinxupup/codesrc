package com.jjb.ams.app.tags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.service.common.CommonService;
import com.jjb.ecms.facility.dto.FieldProductDto;
import com.jjb.ecms.infrastructure.TmDitDic;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.tags.UnicornFunctionRouterTag;

@Component
public class AmsTag extends UnicornFunctionRouterTag{
	@Autowired
	private CacheContext cacheContext;

	@Autowired
	private CommonService commonService;
	/**
	 * 根据方法类型，获取表数据的列表
	 * @param arguments
	 * 
	 * arguments[0] 方法名
	 * arguments[1] 表对应的实体类（需包含包名）。 例子：com.jjb.acl.infrastructure.TmAclDict
	 * arguments[2] 查询对象，json对象。为空需要传入  {}
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> List<T> tableList(List arguments){
		if(!argsSizeLess(arguments,2)){
			return null;
		}
		
		String method = arguments.get(1).toString();
		String filters = arguments.get(2).toString();
		if(StringUtils.isNotEmpty(method) && method.equals("getDitDicGroupType")){//获取卡产品下拉列表
			TmDitDic tmDitDic = new TmDitDic();
			if(StringUtils.isNotEmpty(filters)){
				tmDitDic.setDicType(filters);
			}
			return (List<T>) commonService.selectGroupType(tmDitDic);
		}
		List list = new ArrayList();
		
		return list ;
	}
	
	/**
	 * 根据方法类型，获取表数据的map
	 * @param arguments
	 * 
	 * arguments[0] 方法名
	 * arguments[1] 表对应的实体类（需包含包名）。 例子：com.jjb.acl.infrastructure.TmAclDict
	 * arguments[2] 查询对象，json对象。为空需要传入  {}
	 * arguments[3] keyField
	 * arguments[4] valueField
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LinkedHashMap<Object,Object> tableMap(List arguments){
		if(!argsSizeLess(arguments,3)){
			return null;
		}
		String method = arguments.get(1).toString();
		String filters = arguments.get(2).toString();
		String filters2 = null;
		if(arguments.size()>3){
			filters2 = arguments.get(3).toString();
		}
		if(StringUtils.isNotEmpty(method)){
			if(method.equals("productForStatus")){//获取卡产品下拉列表
				return cacheContext.getSimpleProductLinkedMap(filters,filters2);
			}else if (method.equals("productCardFace")){//获取卡面列表
				if(StringUtils.isNotEmpty(filters)){
					LinkedHashMap<Object, Object> map = cacheContext.getSimpleProductCardFaceLinkedMap(filters);
					LinkedHashMap m = new LinkedHashMap<String, String>();
					if(map!=null && map.size()>0){
						for(Map.Entry<Object, Object> entry:map.entrySet()){
							TmAclDict aclDict = (TmAclDict) entry.getValue();
							m.put(entry.getKey(), aclDict.getCodeName());
						}
					}
					return m;
				}
			} else if (method.equals("branchMap")){//获取受理网点下拉列表（所有）
				/*
				 * @tableMap.arguments: 
				 * 第二个参数：网点类型:filters（issueInd、cardCollectInd、independEntity）
				 */
				return cacheContext.getBranchMapByParam(filters);
			} else if (method.equals("subBranchMap")){//获取受理网点下拉列表（所有）
				/*
				 * @tableMap.arguments: 
				 * 第二个参数：作为查询条件的网点代码 
				 */
				String userNo = null;
				//如果网点代码没有传，则查询当前登录用户下属网点机构信息
				if(StringUtils.isEmpty(filters)){
					userNo = OrganizationContextHolder.getUserNo();
				}
				return cacheContext.getSubBranchByBranchOrUser(filters,userNo);
			}/*
			else if (method.equals("allOwningBranch")){//获取受理网点下拉列表（所有）
				return cacheContext.getBranchMapByParam(filters);
			}else if(method.equals("productForLowerLevel")){//获取可降级的卡产品下拉列表
				if(StringUtils.isNotEmpty(filters)){
//					return commonService.getDemtionProductList(arguments.get(3) == null ? null : arguments.get(3).toString(), filters);
					return cacheContext.getLowerLevelProductLinkedMap(filters);
				}
			}*/else if(method.equals("hasProductBranch")){//获取机构有权查看的产品列表
				return cacheContext.getProductByBranchMap();
			}/*else if(method.equals("hasAuthOwningBranch")){//获取有权限查看的受理网点下拉框
				return cacheContext.getBranchMapByParam(filters);
			}else if(method.equals("hasindependOwningBranch")){//根据产品id获取有独立发卡权限的受理网点下拉框
				return cacheContext.getBranchMapByParam(filters);
			}*/else if(method.equals("getCurrentUserRoles")){//获取当前用户组角色下拉框
				return commonService.getRoleMapByUserNo(OrganizationContextHolder.getUserNo());
			}/*else if(method.equals("taskNameForCount")){//获取任务名下拉列表（工作量查询专用）
				return cacheContext.getTaskNameForCount();
			}*/else if(method.equals("getAllUser")){
				if(StringUtils.isNotEmpty(filters) && filters.equals("userNo")){//过滤掉自身(终审人员额度分配用到)
					filters = OrganizationContextHolder.getUserNo();
				}
				return commonService.getAllUser(filters);
			}else if(method.equals("copyFieldProductCd")){//获取关联卡产品字段配置信息--用于配置字段
				FieldProductDto fieldProductDto = new FieldProductDto();
				if(StringUtils.isNotEmpty(filters)){
					fieldProductDto.setProductCd(filters);
				}
				List<FieldProductDto> fieldProductDtos = cacheContext.getProductWithFieldList(fieldProductDto);
				LinkedHashMap<Object, Object> productCdMap = new LinkedHashMap<Object, Object>();
				if(CollectionUtils.sizeGtZero(fieldProductDtos)){
					for (FieldProductDto fieldProduct : fieldProductDtos) {
						productCdMap.put(fieldProduct.getProductCd(), fieldProduct.getProductDesc());
					}
				}
				return productCdMap;
			}else if(method.equals("dictType")){//根据字段类型获取字典数据--用于配置字段
//				Map<String, String> map = cacheContext.getAclDictByType(filters);
				LinkedHashMap<Object, Object> dictMap = cacheContext.getAclDictByType(filters);
//				dictMap.putAll(map);
//				if(CollectionUtils.sizeGtZero(list)){
//					for(TmAclDict dict:list){
//						dictMap.put(dict.getCode(), dict.getCodeName());
//					}
//				}
				return dictMap;
			}/*else if(method.equals("bpmnNodeToFailed")){//得到bpmn通向失败的节点
				return cacheContext.getBpmnNodeTOFailed();	
			}else if(method.equals("mccNo")){//获取商户号无参数
				return cacheContext.getMccName();
			}else if(method.equals("instalmentCreditActivityNo")){//获取分期活动号
				return cacheContext.getActivityName(filters);
			}else if(method.equals("terms")){//得到分期信息,根据传入的活动号查询所有活动对应的分期,如果为null就查出所有的map
				return cacheContext.getTermsByTerm(filters);
			}else if(method.equals("mccNoWithPra")){//获取商户号带参数,参数为null或""就返回null
				return cacheContext.getMersByProgId(filters);
			}*/
		}
		
		return new LinkedHashMap<Object, Object>();
	}
	/**
	 * 根据表实体，获取表数据的map
	 * @param arguments
	 * 
	 * arguments[0] 方法名
	 * arguments[1] 表对应的实体类（需包含包名）。 例子：com.jjb.acl.infrastructure.TmAclDict
	 * arguments[2] 查询对象，json对象。为空需要传入  {}
	 * arguments[3] keyField
	 * arguments[4] valueField
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<String,String>> tableMapList(List arguments){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		if(!argsSizeLess(arguments,3)){
			return null;
		}
		String argType = arguments.get(1).toString();
		String filters = arguments.get(2).toString();
		if(StringUtils.isNotEmpty(filters)){
			LinkedHashMap<Object, Object> map = cacheContext.getSimpleProductCardFaceLinkedMap(filters);
			for(Map.Entry<Object, Object> entry:map.entrySet()){
				Map m = new HashMap<String, String>();
				m.put("key", entry.getKey());		
				m.put("value", entry.getValue());
				list.add(m);
			}
		}
		return list;
	}
	
	/**
	 * 根据表实体，获取表数据的map
	 * @param arguments
	 * 
	 * arguments[0] 方法名
	 * arguments[1] 表对应的实体类（需包含包名）。 例子：com.jjb.acl.infrastructure.TmAclDict
	 * arguments[2] 查询对象，json对象。为空需要传入  {}
	 * arguments[3] keyField
	 * arguments[4] valueField
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getOneStr(List arguments){
		if(!argsSizeLess(arguments,3)){
			return null;
		}
		String argType = arguments.get(1).toString();
		String filters = arguments.get(2).toString();
		String oneStr = "";
		if(StringUtils.isNotEmpty(argType) && argType.equals("productByCd")){
			if(StringUtils.isNotEmpty(filters)){
				TmProduct product = cacheContext.getProduct(filters);
				if(product!=null){
					oneStr = product.getProductCd()+"-"+product.getProductDesc();
				}
			}
		}
		return oneStr;	
	}
	/**
	 * 根据表实体，获取表数据的map
	 * @param arguments
	 * 
	 * arguments[0] 方法名
	 * arguments[1] 表对应的实体类（需包含包名）。 例子：com.jjb.acl.infrastructure.TmAclDict
	 * arguments[2] 查询对象，json对象。为空需要传入  {}
	 * arguments[3] keyField
	 * arguments[4] valueField
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getCardFaceCode(List arguments){
		if(!argsSizeLess(arguments,3)){
			return null;
		}
		String argType = arguments.get(1).toString();
		String filters = arguments.get(2).toString();
		return "1101";	
	}
	
	
}
