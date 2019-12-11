	/**
 * 
 */
package com.jjb.ecms.biz.service.common;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jjb.acl.infrastructure.TmAclBranch;
import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.acl.infrastructure.TmAclRole;
import com.jjb.acl.infrastructure.TmAclUser;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.facility.dto.ApplyPageOnOffParamDto;
import com.jjb.ecms.facility.dto.ApplyRiskInfoDto;
import com.jjb.ecms.facility.dto.ValidFieldInfoDto;
import com.jjb.ecms.infrastructure.TmDitDic;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.unicorn.facility.exception.ProcessException;

/**
 * @Description: 公共接口
 * @author JYData-R&D-BigK.K
 * @date 2016年9月20日 下午7:41:39
 * @version V1.0   
 */
public interface CommonService {
	
	/**
	 * 申请录入页面必输项字段配置
	 * @param appType 申请类型
	 * @param tmProduct 卡产品
	 * @param num 附卡编号 从0开始
	 * A:主附同申部分附卡
	 * B:独立主卡
	 * S:独立附卡
	 */
	public HashMap<String,List<ValidFieldInfoDto>> validateField(String appType, TmProduct tmProduct, Integer num);
	
	/**
	 * ditdic表根据类型分类
	 * @param tmDitDic
	 * @return
	 */
	public List<TmDitDic> selectGroupType(TmDitDic tmDitDic);
	/**
	 * 根据参数查询ditdic列表
	 * @param tmDitDic
	 * @return
	 */
	public List<TmDitDic> queryForList(TmDitDic tmDitDic);
	
	/**
	 * 根据主卡卡号获取主卡申请人信息、卡产品代码以及卡面信息(限附卡申请时调用)
	 */
	public ApplyInfoDto queryPrimApplicantInfoByPrimCardNo(String primCardNo , ApplyInfoDto applyInfoDto);
	
	/***
	 * 重审时，有一些数据作了修改，在往数据库存之前比对一下那些数据作了修改，并替换掉
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	 public  Object  compareAndUpdateObject(Object newObj,Object oldObj) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException;

	/**
	 * 获取当前操作员的权限列表
	 * @return
	 */
	public List<String> getCurrUserResourceCodeList();
	
	/**
	 * 根据用户名获取角色列表
	 * @param userNo
	 * @return
	 */
	public List<TmAclRole> getRoleListByUserNo(String userNo);
	
	/**
	 * 根据用户名获取角色map集合
	 * @param userNo
	 * @return
	 */
	public LinkedHashMap<Object, Object> getRoleMapByUserNo(String userNo);
	/**
	 * 根据卡产品代码获取可降级卡产品列表
	 * 例：白金卡可降级所有卡产品的金卡
	 * @param appType
	 * @param productCd
	 * @return
	 */
	public LinkedHashMap<Object, Object> getDemtionProductList(String appType, String productCd);
	
	/**
	 * 获取所有的user
	 * @param 
	 * @return
	 */
	public LinkedHashMap<Object, Object> getAllUser(String userNo);
	
	/**
	 * 根据当前用户获取user
	 * @param userNo
	 * @return
	 */
	public TmAclUser getUserByUserNo(String userNo);
	
	/**
	 * 根据当前支行机构号获取相关信息
	 * @param branchCode
	 * @return
	 */
	public TmAclBranch getTmAclBranch(String branchCode);
	
	/**
	 * 修改历史对比项
	 * @param appType 申请类型
	 * @param pageType 
	 * @return Map
	 */
	 public Map<String, Map<String,String>> getHisModifyFieldsMap(String appType, String productCd, String pageType);
	 
	/**
	 * 申请信息修改后和修改前的值作对比
	 * @param a 修改后的值
	 * @param b 修改前的值
	 * @param appNo 
	 * @param tableName 表名
	 * @param taskState 任务名
	 * @param fieldsMap 表的对比项字段
	 */
	public Map<String,Object> compareValue(Map<String, Serializable> a,Map<String, Serializable> b,
		String appNo, String tableName, String taskState, Map<String,String> fieldsMap);
	
	/**
	 * 申请信息修改后更新对应表字段的值
	 * @param tabName 表名
	 * @param modifiedfieldsMap 发生修改的字段集合
	 */
	public <T> T getModifiedClazz(T clazz, Map<String, Object> modifiedfieldsMap);
	
	/**
	 * 将数组t转化为字符串
	 * @param strs 字符串数组
	 *  @param separator 分隔符
	 * @return
	 */
	public String arrayToString(String[] strs, String separator);
	
	/**
	 * 将list转化为字符串
	 * @param list 列表
	 * @param separator 分隔符
	 * @return
	 */
	public String listToString(List<String> list,String separator);
	
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
	public String checkAccountNo(String acctName, String idType, String idNo, String branchNo, String acctNo, String acctNoType);
	
	/**
	 * 获取默认的评分表
	 * @return
	 * @throws ProcessException
	 */
	public List<TmAclDict> geTmAclDictByParam(TmAclDict tmAclDict) throws ProcessException;
	
	/**
	 * 退回操作获取上一节点审批经办人
	 * @param appNo 当前申请件编号
	 * @param oldAppNo 重申件原申请件编号
	 * @param rtfState 
	 * @return 当前申请件审批状态
	 */
	public String getHisOpUser(String appNo, String oldAppNo, String rtfState) throws ProcessException;
	
	/**
	  * 设置人工核查风控信息展示项
	  * @param applyRiskInfoDto
	  * @param tmAppNodeInfoRecordMap
	  */
	 public void setApplyRiskInfoDto(ApplyRiskInfoDto applyRiskInfoDto, Map<String, Serializable> tmAppNodeInfoRecordMap);
	 
	/**
	 * 获取页面参数开关
	 * @return
	 */
	public ApplyPageOnOffParamDto getApplyPageOnOffParamDto();
	
}
