package com.jjb.ecms.biz.cache;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jjb.acl.access.service.AccessDictService;
import com.jjb.acl.access.service.AccessUserService;
import com.jjb.acl.biz.dao.TmAclDictDao;
import com.jjb.acl.biz.dao.TmAclUserDao;
import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.acl.infrastructure.TmAclBranch;
import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.acl.infrastructure.TmAclUser;
import com.jjb.ecms.biz.dao.apply.TmAppUserRelationDao;
import com.jjb.ecms.biz.dao.common.TmDitDicDao;
import com.jjb.ecms.biz.dao.param.FieldProductDtoDao;
import com.jjb.ecms.biz.service.manage.TmAppAuditQuotaService;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.facility.dto.FieldProductDto;
import com.jjb.ecms.infrastructure.TmAppAuditQuota;
import com.jjb.ecms.infrastructure.TmAppUserRelation;
import com.jjb.ecms.infrastructure.TmDitDic;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.ecms.infrastructure.TmProductProcess;
import com.jjb.ecms.util.HttpClientUtil;
import com.jjb.unicorn.cache.RedisUtil;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.ByteArray;
import com.jjb.unicorn.facility.util.ByteArrayOutputStream;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.SerializeUtils;
import com.jjb.unicorn.facility.util.StringUtils;

@Component
public class CacheContext implements Serializable{
	private static final long serialVersionUID = 1L;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TmDitDicDao tmDitDicDao;
	@Autowired
	private TmAclDictDao aclDictDao;
	@Autowired
	private TmAclUserDao tmAclUserDao;
	@Autowired
	private AccessDictService accessDictService;
	@Autowired
	private TmAppAuditQuotaService tmAppAuditQuotaService;
	@Autowired
	private FieldProductDtoDao fieldProductDtoDao;
	@Autowired
	private BranchCacheContext branchCacheContext;
	@Autowired
	private ProductCacheContext productCacheContext;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private TmAppUserRelationDao tmAppUserRelationDao;
	@Autowired
	private AccessUserService accessUserService;



	/**
	 * 根据类型获取TM_DIT_DIC表中参数列表
	 *
	 * @param ditType
	 * @return
	 */
	private List<TmDitDic> getDitDicByDB(String ditType) {
		List<TmDitDic> list = tmDitDicDao.tmAppTaskClimitList(ditType, OrganizationContextHolder.getOrg());
		return list;
	}

	/**
	 * 初始化 tm_dit_dic参数表
	 *
	 * @return
	 */
	public Map<String, String> initInterNetAddrParamMap(String parentKey) {
		// 删除现有redis中缓存数据
		long riskNum = redisUtil.del(AppDitDicConstant.ext_bjj_risk_conf);
		logger.info("初始化【"+AppDitDicConstant.ext_bjj_risk_conf+"】，删除Redis操作的结果："+riskNum);
		long ccifNum = redisUtil.del(AppDitDicConstant.ext_ccif_conf);
		logger.info("初始化【"+AppDitDicConstant.ext_ccif_conf+"】，删除Redis操作的结果："+ccifNum);
		long cmpNum = redisUtil.del(AppDitDicConstant.ext_cmp_conf);
		logger.info("初始化【"+AppDitDicConstant.ext_cmp_conf+"】，删除Redis操作的结果："+cmpNum);
		long creditLifeNum = redisUtil.del(AppDitDicConstant.ext_creditLife_conf);
		logger.info("初始化【"+AppDitDicConstant.ext_creditLife_conf+"】，删除Redis操作的结果："+creditLifeNum);
		long ccfrontendNum = redisUtil.del(AppDitDicConstant.ext_ccfrontend_conf);
		logger.info("初始化【"+AppDitDicConstant.ext_ccfrontend_conf+"】，删除Redis操作的结果："+ccfrontendNum);

		Map<String, Map<String, String>> dicParam = new HashMap<String, Map<String, String>>();
		// 查询数据库
		List<TmDitDic> extBjjRiskNetConf = getDitDicByDB(AppDitDicConstant.ext_bjj_risk_conf);//风控系统
		List<TmDitDic> extCcifConf = getDitDicByDB(AppDitDicConstant.ext_ccif_conf);//综合前置
		List<TmDitDic> extCmpConf = getDitDicByDB(AppDitDicConstant.ext_cmp_conf);//内容平台
		List<TmDitDic> extCreditLifeConf = getDitDicByDB(AppDitDicConstant.ext_creditLife_conf);//信用生活审批小助手
		List<TmDitDic> extCcfrontendConf = getDitDicByDB(AppDitDicConstant.ext_ccfrontend_conf);//信用卡前置

		// 写入数据至redis缓存中
		if (CollectionUtils.isNotEmpty(extBjjRiskNetConf)) {
			Map<String, String> retMap = new HashMap<String, String>();
			for (TmDitDic tmDitDic : extBjjRiskNetConf) {
				if(StringUtils.isNotEmpty(tmDitDic.getItemName()) && StringUtils.isNotEmpty(tmDitDic.getRemark())) {
					retMap.put(tmDitDic.getItemName(), tmDitDic.getRemark());
					redisUtil.hset(AppDitDicConstant.ext_bjj_risk_conf, tmDitDic.getItemName(), tmDitDic.getRemark());
				}
			}
			dicParam.put(AppDitDicConstant.ext_bjj_risk_conf, retMap);
			logger.info("初始化【"+AppDitDicConstant.ext_bjj_risk_conf+"】，新增数量："+retMap.size());
		}
		if (CollectionUtils.isNotEmpty(extCcifConf)) {
			Map<String, String> retMap = new HashMap<String, String>();
			for (TmDitDic tmDitDic : extCcifConf) {
				String field = tmDitDic.getFormName() + tmDitDic.getItemName();
				if(StringUtils.isNotEmpty(field) && StringUtils.isNotEmpty(tmDitDic.getRemark())) {
					retMap.put(field, tmDitDic.getRemark());
					redisUtil.hset(AppDitDicConstant.ext_ccif_conf, field, tmDitDic.getRemark());
				}
			}
			dicParam.put(AppDitDicConstant.ext_ccif_conf, retMap);
			logger.info("初始化【"+AppDitDicConstant.ext_ccif_conf+"】，新增数量："+retMap.size());
		}
		if (CollectionUtils.isNotEmpty(extCmpConf)) {
			Map<String, String> retMap = new HashMap<String, String>();
			for (TmDitDic tmDitDic : extCmpConf) {
				if(StringUtils.isNotEmpty(tmDitDic.getItemName()) && StringUtils.isNotEmpty(tmDitDic.getRemark())) {
					retMap.put(tmDitDic.getItemName(), tmDitDic.getRemark());
					redisUtil.hset(AppDitDicConstant.ext_cmp_conf, tmDitDic.getItemName(), tmDitDic.getRemark());
				}
			}
			dicParam.put(AppDitDicConstant.ext_cmp_conf, retMap);
			logger.info("初始化【"+AppDitDicConstant.ext_cmp_conf+"】，新增数量："+retMap.size());
		}
		if (CollectionUtils.isNotEmpty(extCreditLifeConf)) {
			Map<String, String> retMap = new HashMap<String, String>();
			for (TmDitDic tmDitDic : extCreditLifeConf) {
				if(StringUtils.isNotEmpty(tmDitDic.getItemName()) && StringUtils.isNotEmpty(tmDitDic.getRemark())) {
					retMap.put(tmDitDic.getItemName(), tmDitDic.getRemark());
					redisUtil.hset(AppDitDicConstant.ext_creditLife_conf, tmDitDic.getItemName(), tmDitDic.getRemark());
				}
			}
			dicParam.put(AppDitDicConstant.ext_creditLife_conf, retMap);
			logger.info("初始化【"+AppDitDicConstant.ext_creditLife_conf+"】，新增数量："+retMap.size());
		}
		if (CollectionUtils.isNotEmpty(extCcfrontendConf)) {
			Map<String, String> retMap = new HashMap<String, String>();
			for (TmDitDic tmDitDic : extCcfrontendConf) {
				String field = tmDitDic.getFormName() + tmDitDic.getItemName();
				if(StringUtils.isNotEmpty(tmDitDic.getItemName()) && StringUtils.isNotEmpty(tmDitDic.getRemark())) {
					retMap.put(tmDitDic.getItemName(), tmDitDic.getRemark());
					redisUtil.hset(AppDitDicConstant.ext_ccfrontend_conf, field, tmDitDic.getRemark());
				}
			}
			dicParam.put(AppDitDicConstant.ext_ccfrontend_conf, retMap);
			logger.info("初始化【"+AppDitDicConstant.ext_ccfrontend_conf+"】，新增数量："+retMap.size());
		}
		return dicParam.get(parentKey);
	}

	/**
	 * 获取系统中配置各种网络地址配置；如综合前置、决策系统、影像系统等</br>
	 * Map<网络地址类型,参数值></br>
	 * 机构影像类型：</br>
	 * AppDitDicConstant.ext_jjbRisk_conf //九江风控系统</br>
	 * AppDitDicConstant.ext_ccif_conf //九江行内综合前置</br>
	 *
	 * @param dicType 参数类型
	 * @return Map<参数类型,参数值>
	 */
	public Map<String, String> getInterNetAddrParam(String dicType) {
		Map<String, String> retMap = redisUtil.hget(dicType);
		if (retMap == null || retMap.size() == 0) {
			retMap = initInterNetAddrParamMap(dicType);
		}
		return retMap;
	}

	/**
	 * 获取TM_DIT_DIC表中关于业务系统页面路径配置</br>
	 *
	 * @return
	 */
	public Map<String, String> initPageConfig() {
		long delNum = redisUtil.del(AppDitDicConstant.applyPageConfig);
		logger.info("初始化【"+AppDitDicConstant.applyPageConfig+"】，删除Redis操作的结果："+delNum);
		Map<String, String> pageMap = new HashMap<String, String>();
		List<TmDitDic> list = getDitDicByDB(AppDitDicConstant.applyPageConfig);
		if (CollectionUtils.isNotEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {
				TmDitDic dd = list.get(i);
				pageMap.put(dd.getTabName(), dd.getRemark());
				redisUtil.hset(AppDitDicConstant.applyPageConfig, dd.getTabName(), dd.getRemark());
			}
			logger.info("初始化【"+AppDitDicConstant.applyPageConfig+"】，新增数量："+pageMap.size());
		}
		return pageMap;
	}

	/**
	 * 根据key获取系统也页面路径value
	 *
	 * @param formKey
	 * @return
	 */
	public String getPageConfig(String formKey) {
		Map<String, String> pageMap = redisUtil.hget(AppDitDicConstant.applyPageConfig);
		if (pageMap == null || pageMap.size() == 0) {
			pageMap = initPageConfig();
		}

		String pagePath = pageMap.get(formKey);
//		if (StringUtils.isEmpty(pagePath)) {
//			pageMap = initPageConfig();
//			pagePath = pageMap.get(formKey);
//		}
		return pagePath;
	}

	/**
	 * 从数据库查询审批人员额度分配信息
	 *
	 * @return List集合
	 */
	public List<TmAppAuditQuota> getTmAppAuditQuotaForDB() {
		TmAppAuditQuota auditQuota = new TmAppAuditQuota();
		auditQuota.setOrg(OrganizationContextHolder.getOrg());
		return tmAppAuditQuotaService.getTmAppAuditQuotaList(auditQuota);
	}

	/**
	 * 初始化审批人员额度分配Map信息
	 */
	public Map<String, TmAppAuditQuota> initTmAppAuditQuota() {
		long delNum = redisUtil.delByByteKey(AppDitDicConstant.SHENPI_QUOTA);
		logger.info("初始化【"+AppDitDicConstant.SHENPI_QUOTA+"】，删除Redis操作的结果："+delNum);

		Map<String, TmAppAuditQuota> initMap = new HashMap<>();
		List<TmAppAuditQuota> TmAppAuditQuota = getTmAppAuditQuotaForDB();
		if (TmAppAuditQuota == null || TmAppAuditQuota.size() == 0) {
			initMap = null;
			return null;
		}
		initMap = new HashMap<String, TmAppAuditQuota>();
		Map<byte[], byte[]> hash = new HashMap<>();

		for (int i = 0; i < TmAppAuditQuota.size(); i++) {
			TmAppAuditQuota tma = TmAppAuditQuota.get(i);
			byte[] bytes = SerializeUtils.serialize(tma);
			String key = tma.getOperatorId() + "-" + tma.getTaskName();
			hash.put(key.getBytes(), bytes);
//			redisUtil.setByte(key, bytes);
			initMap.put(tma.getOperatorId() + "-" + tma.getTaskName(), tma);
		}
		if(hash!=null && hash.size()>0) {
			redisUtil.hmsetByte(AppDitDicConstant.SHENPI_QUOTA, hash);
			logger.info("初始化【"+AppDitDicConstant.SHENPI_QUOTA+"】，新增数量："+initMap.size());
		}
		return initMap;
	}

	/**
	 * 根据用户与任务id获取当前用户审批额度配置参数
	 *
	 * @param user
	 * @param taskName
	 * @return
	 */
	public TmAppAuditQuota getTmAppAuditQuotaForCache(String user, String taskName) {
		String key = user + "-" + taskName;
		Map<byte[], byte[]> hash = redisUtil.hgetByByte(AppDitDicConstant.SHENPI_QUOTA);

		if (hash == null || hash.size() == 0) {
			initTmAppAuditQuota();
		}
		TmAppAuditQuota aq = null;
		if (hash != null && hash.containsKey(key.getBytes())) {
			Object obj = SerializeUtils.unSerialize(hash.get(key.getBytes()));
			if (obj != null) {
				aq = (TmAppAuditQuota) obj;
			}
		}
		return aq;
	}

	/**
	 * 根据用户与任务id获取当前用户审批额度配置参数
	 *
	 * @param user
	 * @return
	 */
	public List<TmAppAuditQuota> getTmAppAuditQuotasForCache(String user) {
		Map<byte[], byte[]> hash = redisUtil.hgetByByte(AppDitDicConstant.SHENPI_QUOTA);
		if (hash == null || hash.size() == 0) {
			initTmAppAuditQuota();
		}
		if (StringUtils.isEmpty(user)) {
			user = OrganizationContextHolder.getUserNo();
			logger.info("根据用户获取其审批环境的额度控制配置参数时用户信息为空，则取当前登录用户[" + user + "]");
		}
		if (StringUtils.isEmpty(user)) {
			throw new ProcessException("未取到用户信息，当前登录或已失效，请重新登录！");
		}
		List<TmAppAuditQuota> ss = new ArrayList<TmAppAuditQuota>();

		if (hash != null && hash.size() > 0) {
			for (byte[] key : hash.keySet()) {
				String keystr = new String(key);
				if (keystr.startsWith(user + "-")) {
					Object obj = SerializeUtils.unSerialize(hash.get(key));
					if (obj != null) {
						TmAppAuditQuota aq = (TmAppAuditQuota) obj;
						ss.add(aq);
					}
				}
			}
		}
		return ss;
	}
	/**
	 * //根据系统获取节点操作权限
	 * @param systemType
	 * @return
	 */
	public List<String> getAuthBySystemType(String systemType) {
		List<String> nodeAuthList= new ArrayList<>();
		List<TmAclDict> tmAclDictList =accessDictService.getByType(systemType);
		for(TmAclDict aclDict : tmAclDictList){
			if (StringUtils.isNotBlank(aclDict.getValue())){
				nodeAuthList.add(aclDict.getValue());
			}
		}
		return  nodeAuthList;
	}
	/**
	 * 初始化系统开关参数至Map<tmDitDic.getItemName(), tmDitDic>
	 *
	 * @return
	 */
	public Map<String, TmDitDic> initApplyOnlineOnOffParamMap() {

		long delNum = redisUtil.delByByteKey(AppDitDicConstant.applyOnlineOnOffParam);
		logger.info("初始化【"+AppDitDicConstant.applyOnlineOnOffParam+"】，删除Redis操作的结果："+delNum);

		// 联机开关列表
		List<TmDitDic> list = getDitDicByDB(AppDitDicConstant.applyOnlineOnOffParam);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		Map<String, TmDitDic> onlineOnOffParam = new HashMap<String, TmDitDic>();
		Map<byte[], byte[]> hash = new HashMap<>();
		if(CollectionUtils.sizeGtZero(list)) {
			for (TmDitDic tmDitDic : list) {
				onlineOnOffParam.put(tmDitDic.getItemName(), tmDitDic);
				String key = StringUtils.setValue(tmDitDic.getItemName(), "def");
				hash.put(key.getBytes(), SerializeUtils.serialize(tmDitDic));
			}
			redisUtil.hmsetByte(AppDitDicConstant.applyOnlineOnOffParam, hash);
			logger.info("初始化【"+AppDitDicConstant.applyOnlineOnOffParam+"】，新增数量："+onlineOnOffParam.size());
		}
		return onlineOnOffParam;
	}

	/**
	 * 根据开关类型获取开关具体值
	 *
	 * @param productCd
	 * @return
	 */
	public TmDitDic getApplyOnlineOnOff(String ditItmeName) {
		Map<byte[], byte[]> hash = redisUtil.hgetByByte(AppDitDicConstant.applyOnlineOnOffParam);
		ditItmeName = StringUtils.setValue(ditItmeName, "def");
		if (hash != null && hash.containsKey(ditItmeName.getBytes())) {
			Object obj = SerializeUtils.unSerialize(hash.get(ditItmeName.getBytes()));
			if (obj != null) {
				return (TmDitDic) obj;
			}
		} else if(hash==null || hash.isEmpty()){
			Map<String, TmDitDic> onlineOnOffParam = initApplyOnlineOnOffParamMap();
			if (onlineOnOffParam.containsKey(ditItmeName)) {
				return onlineOnOffParam.get(ditItmeName);
			}
			return null;
		}
		return null;
	}

	/**
	 * 初始化页面开关和电调配置参数
	 *
	 * @return
	 */
	public List<TmDitDic> initTelCheckParam() {
		long delNum = redisUtil.delByByteKey(AppDitDicConstant.telCheckInfo);
		logger.info("初始化【"+AppDitDicConstant.telCheckInfo+"】，删除Redis操作的结果："+delNum);

		LinkedHashMap<String, List<TmDitDic>> pageParameters = new LinkedHashMap<String, List<TmDitDic>>();
		String p1 = AppDitDicConstant.telCheckInfo;
		String org = OrganizationContextHolder.getOrg();
		// 电话调查核身问题项和必问问题项
		List<TmDitDic> telCheckInfoList = tmDitDicDao.tmAppTaskClimitList(p1, org);
		if (CollectionUtils.sizeGtZero(telCheckInfoList)) {
			pageParameters.put(AppDitDicConstant.telCheckInfo, telCheckInfoList);
			redisUtil.setByte(AppDitDicConstant.telCheckInfo, SerializeUtils.serialize(telCheckInfoList));
			logger.info("初始化【"+AppDitDicConstant.telCheckInfo+"】，新增数量："+telCheckInfoList.size());
		}
		return telCheckInfoList;
	}

	/*
	 * 获取页面开关和电调配置参数（是否核身、电调是否合并到初审等）
	 */
	public List<TmDitDic> getTelCheckParam(String dicType) {
		byte[] bytes = redisUtil.getByByte(AppDitDicConstant.telCheckInfo);
		if (bytes != null) {
			Object obj = SerializeUtils.unSerialize(bytes);
			if (obj != null && obj instanceof List) {
				return (List<TmDitDic>) obj;
			}
		} else {
			return initTelCheckParam();
		}
		return null;
	}

	/**
	 * 初始化拒绝原因
	 */
	public Map<String, Map<String, String>> initApplyRejectReasonMap() {
		long delNum1 = redisUtil.del(AppConstant.REJECT_REASON_MAP);
		logger.info("初始化【"+AppConstant.REJECT_REASON_MAP+"】，删除Redis操作的结果："+delNum1);
		long delNum2 = redisUtil.del(AppConstant.REJECT_REASON_FLAG_MAP);
		logger.info("初始化【"+AppConstant.REJECT_REASON_FLAG_MAP+"】，删除Redis操作的结果："+delNum2);
		Map<String, Map<String, String>> applyRejectReasonMap = new HashMap<>();//拒绝原因码
		List<TmAclDict> list = accessDictService.getByType(AppConstant.REJECT_REASON);
		if (CollectionUtils.isNotEmpty(list)) {
			Map<String, String> rejectReasonMap = new HashMap<String, String>();
			Map<String, String> rejectReasonFlagMap = new HashMap<String, String>();
			for (int i = 0; i < list.size(); i++) {
				TmAclDict dd = list.get(i);
				rejectReasonMap.put(dd.getCode(), dd.getCodeName());
				if (StringUtils.equals(dd.getValue2(),AppConstant.REJECT_FLAG)) {
					rejectReasonFlagMap.put(dd.getCode(), dd.getValue2());
				}
			}
			applyRejectReasonMap.put(AppConstant.REJECT_REASON_MAP, rejectReasonMap);
			applyRejectReasonMap.put(AppConstant.REJECT_REASON_FLAG_MAP, rejectReasonFlagMap);
			if(rejectReasonMap!=null && rejectReasonMap.size()>0) {
				redisUtil.hmset(AppConstant.REJECT_REASON_MAP, rejectReasonMap);
				logger.info("初始化【"+AppConstant.REJECT_REASON_MAP+"】，新增数量："+rejectReasonMap.size());
			}
			if(rejectReasonFlagMap!=null && rejectReasonFlagMap.size()>0) {
				redisUtil.hmset(AppConstant.REJECT_REASON_FLAG_MAP, rejectReasonFlagMap);
				logger.info("初始化【"+AppConstant.REJECT_REASON_FLAG_MAP+"】，新增数量："+rejectReasonMap.size());
			}
		}
		return applyRejectReasonMap;
	}
	/**
	 * 获取拒绝原因
	 */
	public Map<String, Map<String, String>> getApplyRejectReasonMap(){
		Map<String, Map<String, String>> applyRejectReasonMap = new HashMap<>();//拒绝原因码
		Map<String, String> map = redisUtil.hget(AppConstant.REJECT_REASON_MAP);
		applyRejectReasonMap.put(AppConstant.REJECT_REASON_MAP, map);
		applyRejectReasonMap.put(AppConstant.REJECT_REASON_FLAG_MAP, redisUtil.hget(AppConstant.REJECT_REASON_FLAG_MAP));
		if(map == null || map.size() == 0){
			applyRejectReasonMap = initApplyRejectReasonMap();
		}
		return applyRejectReasonMap;
	}


	/**
	 * 初始化字段所在的位置
	 * @return
	 */
	private List<String> initFieldRegionList(){
		long delNum = redisUtil.del(AppConstant.FIELD_REGIN);
		logger.info("初始化【"+AppConstant.FIELD_REGIN+"】，删除Redis操作的结果："+delNum);
		List<String> fieldRegionList = new ArrayList<String>();
		List<TmAclDict> list = accessDictService.getByType(AppConstant.FIELD_REGIN);
		if(CollectionUtils.sizeGtZero(list)){
			for (TmAclDict tmAclDict : list) {
				fieldRegionList.add(tmAclDict.getCode());
				redisUtil.rpush(AppConstant.FIELD_REGIN, tmAclDict.getCode());
			}
			logger.info("初始化【"+AppConstant.FIELD_REGIN+"】，新增数量："+fieldRegionList.size());
		}
		return fieldRegionList;
	}
	/**
	 * 获取字段所在的位置
	 * @return
	 */
	public String[] getFieldRegions(){
		List<String> list = redisUtil.lrange(AppConstant.FIELD_REGIN, 0, -1);
		if(CollectionUtils.isEmpty(list)){
			list = initFieldRegionList();
		}
		if(list!=null) {
			return list.toArray(new String[list.size()]);
		}
		return null;
	}

	/**
	 * 初始化系统补件超时至Map<tmDitDic.getItemName(), tmDitDic.getRemark()>
	 * @return
	 */
	public Map<String, String> initApplyPatchBoltParamMap() {
		long delNum = redisUtil.del(AppDitDicConstant.applyPatchBoltParam);
		logger.info("初始化【"+AppDitDicConstant.applyPatchBoltParam+"】，删除Redis操作的结果："+delNum);
		// 补件超时参数
		List<TmDitDic> list = getDitDicByDB(AppDitDicConstant.applyPatchBoltParam);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		Map<String,String> patchBoltParamMap=new HashMap<String, String>();
		if (CollectionUtils.sizeGtZero(list)) {
			for (TmDitDic tmDitDic : list) {
				patchBoltParamMap.put(tmDitDic.getItemName(), tmDitDic.getRemark());
			}
			redisUtil.hmset(AppDitDicConstant.applyPatchBoltParam, patchBoltParamMap);
			logger.info("初始化【"+AppDitDicConstant.applyPatchBoltParam+"】，新增数量："+patchBoltParamMap.size());
		}
		return patchBoltParamMap;
	}

	/**
	 * 获取系统补件超时
	 * Map<tmDitDic.getItemName(), tmDitDic.getRemark()>
	 * @return
	 */
	public String getPatchBoltParamByType(String type) {
		Map<String,String> patchBoltParamMap = redisUtil.hget(AppDitDicConstant.applyPatchBoltParam);
		if(patchBoltParamMap==null || patchBoltParamMap.size()==0){
			patchBoltParamMap = initApplyPatchBoltParamMap();
		}
		if(patchBoltParamMap!=null && patchBoltParamMap.containsKey(type)){
			return patchBoltParamMap.get(type);
		}
		return null;
	}

	/**
	 * 从数据库获取页面字段配置信息
	 * @return
	 */
	private List<FieldProductDto> getFieldProductDtoForDB() {
		try {
			FieldProductDto fieldProductDto = new FieldProductDto();
			fieldProductDto.setIfUsed(Indicator.Y.name());
			return fieldProductDtoDao.getFieldProductDtoList(fieldProductDto);
		} catch (Exception e) {
			logger.error("从数据库获取卡产品关联字段配置信息出现异常");
			throw new ProcessException("从数据库获取卡产品关联字段配置信息出现异常", e);
		}
	}

	/**
	 * 根据卡产品页面字段配置初始化
	 * Map<卡产品, List<TmFieldProduct>
	 */
	private Map<byte[], byte[]> initFieldProductDto() {
		long delNum = redisUtil.delByByteKey(AppConstant.FIELD_PRODUCT);
		logger.info("初始化【"+AppConstant.FIELD_PRODUCT+"】，删除Redis操作的结果："+delNum);
		List<FieldProductDto> fieldProductDtoList = getFieldProductDtoForDB();
		Map<byte[], byte[]> fieldProductDtoMap = new HashMap<>();// 根据卡产品配置字段信息

		if (CollectionUtils.sizeGtZero(fieldProductDtoList)) {
			List<TmProduct> tmProductList = productCacheContext.getProductListForDB();
			if (CollectionUtils.sizeGtZero(tmProductList)) {
				for (TmProduct tmProduct : tmProductList) {
					List<FieldProductDto> fieldProductDtos = new ArrayList<FieldProductDto>();
					for (FieldProductDto fieldProductDto : fieldProductDtoList) {
						if (StringUtils.equals(fieldProductDto.getProductCd(), tmProduct.getProductCd())) {
							fieldProductDtos.add(fieldProductDto);
						}
					}
					String key = StringUtils.setValue(tmProduct.getProductCd(), "def");
					fieldProductDtoMap.put(key.getBytes(), SerializeUtils.serialize(fieldProductDtos));
				}
				if(fieldProductDtoMap!=null && fieldProductDtoMap.size()>0) {
					redisUtil.hmsetByte(AppConstant.FIELD_PRODUCT, fieldProductDtoMap);
					logger.info("初始化【"+AppConstant.FIELD_PRODUCT+"】，新增数量："+fieldProductDtoMap.size());
				}
			}
		}
		return fieldProductDtoMap;
	}

	/**
	 * 根据卡产品获取字段配置信息
	 * @param productCd
	 * @return
	 */
	public List<FieldProductDto> getFieldProductDtosByProductCd(String productCd) {
		if (StringUtils.isNotBlank(productCd)) {
			Map<byte[], byte[]> map = redisUtil.hgetByByte(AppConstant.FIELD_PRODUCT);
			if ((map == null || map.size() == 0)) {
				map = initFieldProductDto();
			}
			byte[] bytes = map.get(productCd.getBytes());
			Object obj = SerializeUtils.unSerialize(bytes);
			if (obj != null) {
				return (List<FieldProductDto>) obj;
			}
			return null;
		}
		logger.info("卡产品关联字段卡产品不存在");
		return null;
	}
	/**
	 * 获取所有已配置字段的卡产品信息
	 * @return
	 */
	public List<FieldProductDto> getProductWithFieldList(FieldProductDto fieldProductDto) {
		if (fieldProductDto == null) {
			fieldProductDto = new FieldProductDto();
		}
		return fieldProductDtoDao.getProductList(fieldProductDto);
	}

	/**
	 * 初始化所有的用户
	 * add by H.N 20171109
	 * @return
	 */
	public LinkedHashMap<byte[], byte[]> initTmAclUserMap() {
		long delNum = redisUtil.delByByteKey(AppConstant.ALL_USERS);
		logger.info("初始化【"+AppConstant.ALL_USERS+"】，删除Redis操作的结果："+delNum);
		LinkedHashMap<byte[], byte[]> tmUserMap = new LinkedHashMap<byte[], byte[]>();
		// 查出所有的用户
		HashMap<String, Object> map = new HashMap<>();
		map.put("_SORT_NAME", "userNo");
		map.put("_SORT_ORDER", "DESC");
		List<TmAclUser> tmUsers = tmAclUserDao.queryForList(new TmAclUser(), map);
		if (CollectionUtils.sizeGtZero(tmUsers)) {
			for (TmAclUser enty : tmUsers) {
//				tmUserMap.put(enty.getUserNo(), enty);
				String key = StringUtils.setValue(enty.getUserNo(), "def");
				tmUserMap.put(key.getBytes(), SerializeUtils.serialize(enty));
			}
			redisUtil.hmsetByte(AppConstant.ALL_USERS, tmUserMap);
			logger.info("初始化【"+AppConstant.ALL_USERS+"】，新增数量："+tmUserMap.size());
		}
		return tmUserMap;
	}
	/**
	 * 初始化所有的用户
	 * add by H.N 20181109
	 * @return
	 */
	public TmAclUser getTmAclUserByUserName(String userName) {
		if (StringUtils.isEmpty(userName)) {
			return null;
		}
		Map<byte[], byte[]> redisMap = redisUtil.hgetByByte(AppConstant.ALL_USERS);
		if (redisMap == null || redisMap.size() == 0) {
			redisMap = initTmAclUserMap();
		}
		if (redisMap != null && redisMap.size() > 0) {
			byte[] bytes = redisMap.get(userName.getBytes());
			Object obj = SerializeUtils.unSerialize(bytes);
			if (obj != null) {
				return (TmAclUser) obj;
			}
		}
		return null;
	}
	/**
	 * 初始化， 用于异步加载大数据，包括省、市、区、国籍
	 */
	private void initAclDictByAddr() {
		long delNum = redisUtil.delByByteKey(AppConstant.ALL_ADDRESS);
		logger.info("初始化【"+AppConstant.ALL_ADDRESS+"】，删除Redis操作的结果："+delNum);
	}
	/**
	 * 获取用于异步加载大数据，包括省、市、区、国籍
	 * @param tmAclDict
	 * @return
	 */
	public List<TmAclDict> getAclDictAddress(TmAclDict tmAclDict) {
		List<TmAclDict> list = null;
		if (tmAclDict != null) {
			String value2 = tmAclDict.getValue2();// 省或市
			String dictType = tmAclDict.getType();// 数据字典类型
			logger.debug("联动异步加载[dictType={},[父级的值={}]]", dictType, value2);
			if (StringUtils.isNotEmpty(dictType) && StringUtils.isNotEmpty(value2)) {
				String key = value2 + dictType;

				Map<byte[], byte[]> redisMap = redisUtil.hgetByByte(AppConstant.ALL_ADDRESS);
				if (redisMap != null && redisMap.size() > 0) {
					byte[] bytes = redisMap.get(key.getBytes());
					Object obj = SerializeUtils.unSerialize(bytes);
					if (obj != null) {
						list = (List<TmAclDict>) obj;
					}
				}
				if (CollectionUtils.sizeGtZero(list)) {
					return list;
				} else {
					list = aclDictDao.queryForList(tmAclDict);
					if (CollectionUtils.sizeGtZero(list)) {
						LinkedHashMap<byte[], byte[]> hash = new LinkedHashMap<>();
						hash.put(key.getBytes(), SerializeUtils.serialize(list));
						redisUtil.hmsetByte(AppConstant.ALL_ADDRESS, hash);
						logger.info("初始化【"+AppConstant.ALL_ADDRESS+"】，本轮新增数量："+hash.size());
					}
				}
			}
		}
		return list;
	}


	/**
	 * 根据产品编号获取产品信息
	 * @param productCd
	 * @return
	 */
	public TmProduct getProduct(String productCd) {
		return productCacheContext.getProduct(productCd);
	}
	/**
	 * 根据产品状态查询产品列表
	 * @param productStatus
	 * @return
	 */
	public LinkedHashMap<Object, Object> getSimpleProductLinkedMap(String productStatus,String productType) {
		return productCacheContext.getSimpleProductLinkedMap(productStatus,productType);
	}
	/**
	 * 根据卡产品编号获取配置的卡面信息Map集合
	 * @param productCd
	 * @return
	 */
	public LinkedHashMap<Object, Object> getSimpleProductCardFaceLinkedMap(String productCd){
		return productCacheContext.getSimpleProductCardFaceLinkedMap(productCd);
	}
	/**
	 * 获取可降级的卡产品下拉列表
	 * @param productCd
	 * @return
	 */
//	public LinkedHashMap<Object, Object> getLowerLevelProductLinkedMap(String productCd){
//		return productCacheContext.getLowerLevelProductLinkedMap(productCd);
//	}
	/**
	 * 获取机构有权限查看的产品列表</br>
	 * 如果产品不是独立发卡产品，所有用户与网点均可使用</br>
	 * 如果产品是独立发卡产品，那么需要与当前登录用户管理的网点做匹配，如果匹配上则可使用
	 */
	public LinkedHashMap<Object, Object> getProductByBranchMap(){
		return productCacheContext.getProductByBranchMap();
	}
	/**
	 * 获取机构有权限查看的产品列表</br>
	 * 如果产品不是独立发卡产品，所有用户与网点均可使用</br>
	 * 如果产品是独立发卡产品，那么需要与当前登录用户管理的网点做匹配，如果匹配上则可使用
	 */
	public TmProductProcess getProductProcessByProduct(String key){
		return productCacheContext.getProductProcessByProduct(key);
	}
	/**
	 * 根据条件获取对应网点数据（页面下拉框用）</br>
	 * 根据
	 * Key-网点机构号
	 * Value-网点机构名称
	 * @param identification(判断是发卡权限issueInd还是领卡权限cardCollectInd)
	 * @return
	 */
	public LinkedHashMap<Object, Object> getBranchMapByParam(String param){
		return branchCacheContext.getBranchMapByParam(param);
	}
	/**
	 * 根据网点等级查询
	 * @param tmAclBranch
	 * @return
	 */
	public LinkedHashMap<Object, Object> getBranchMapByLevel(String branchLevel){
		return branchCacheContext.getBranchMapByLevel(branchLevel);
	}
	/**
	 * 根据branchCode从(全行)机构网点中查询单个TmAclBranch实体数据
	 * @return
	 */
	public TmAclBranch getTmAclBranchByCode(String branchCode){
		return branchCacheContext.getTmAclBranchByCode(branchCode);
	}
	/**
	 * 根据网点机构或者用户查询其下属分支行网点机构<br>
	 * 如果是一级网点或者无上级网点时默认显示所有网点信息
	 * @return Map<String, TmAclBranch>
	 */
	public LinkedHashMap<Object,Object> getSubBranchByBranchOrUser(String branchCode,String userNo){
		LinkedHashMap<String, TmAclBranch> mp = branchCacheContext.getSubBranchByBranchOrUser(branchCode, userNo);
		LinkedHashMap<Object,Object> retMap = new LinkedHashMap<Object,Object>();
		if(mp!=null && mp.size()>0){
			for(TmAclBranch enty : mp.values()){
				retMap.put(enty.getBranchCode(), enty.getBranchName());
			}
		}
		return retMap;
	}
	/**
	 * 根据类型获取业务字典值
	 */
	public LinkedHashMap<Object, Object> getAclDictByType(String dicType){
		return accessDictService.getMapByType(dicType);
	}

	/**
	 * 根据类型与 code值 获取单条业务字典
	 */
	public TmAclDict getAclDictByTypeAndCode(String dicType,String code){
		return accessDictService.get(dicType, code);
	}
	/**
	 * 根据参数类型 与 vaule( value的常见用法为‘上级代码’) 查询参数字典列表
	 * 
	 * @param type
	 * @param value
	 * @return
	 */
	public List<TmAclDict> getAclDictByTypeAndValue(String type, String value) {
		return accessDictService.getByTypeAndValue(type, value);
	}

	/**
	 * 从数据库查询初审人对应终审人信息
	 *
	 * @return
	 */
	public Map<String, TmAppUserRelation> initTmAppUserRelationMap() {
		String key = AppConstant.ALL_USER_RELATION;
		long delNum = redisUtil.del(key);
		logger.info("初始化【" + AppConstant.ALL_USER_RELATION + "】，删除Redis操作的结果：" + delNum);

		TmAppUserRelation tmAppUserRelation = new TmAppUserRelation();
		List<TmAppUserRelation> list = tmAppUserRelationDao.queryForList(tmAppUserRelation);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		Map<String, TmAppUserRelation> tmAppUserRelationMap = new HashMap<>();
		if (CollectionUtils.sizeGtZero(list)) {
			for (int i = 0; i < list.size(); i++) {
				TmAppUserRelation pb = list.get(i);
				if(pb==null || pb.getUserNo() == null){
					continue;
				}
				String field=pb.getUserNo();
				tmAppUserRelationMap.put(field,pb);
				redisUtil.hsetnx(key, field, SerializeUtils.serialize(pb));
			}
		}

		return tmAppUserRelationMap;
	}

	/**
	 * 根据业务员编号（userNo）获取业务员关系表对象（TmAppUserRelation）
	 * @param userNo
	 * @return
	 */
	public TmAppUserRelation getTmAppUserRelationByUserNo(String userNo){
		if(StringUtils.isBlank(userNo)) {
			return null;
		}
		String keys = AppConstant.ALL_USER_RELATION;
		byte[] bytes = redisUtil.hget(keys, userNo.getBytes());
		Object obj = SerializeUtils.unSerialize(bytes);
		if (obj != null && obj instanceof TmAppUserRelation) {
			return (TmAppUserRelation) obj;
		}else {
			Map<String,TmAppUserRelation> map = initTmAppUserRelationMap();
			if (map != null && map.size() > 0) {
				return map.get(userNo);
			}
		}
		return null;
	}


	/**
	 * 将拥有对应节点权限的人查出
	 *
	 * @return
	 */
	public Map<String, TmAclUser> initAuthorityUserMap(){
		String key = AppConstant.AUTHORITY_USERS;
		long delNum = redisUtil.del(key);
		logger.info("初始化【" + AppConstant.AUTHORITY_USERS + "】，删除Redis操作的结果：" + delNum);

		String[] authStringTel = new String[]{};
		String[] authStringFnl = new String[]{};
		authStringTel = new String[]{"CAS_APPLY_TEL_SURVEY"};
		authStringFnl = new String[]{"CAS_APPLY_FINALAUDIT"};
		List<TmAclUser> aclUserTel = accessUserService.getUserMenus(authStringTel);
		List<TmAclUser> aclUserFnl= accessUserService.getUserMenus(authStringFnl);
		if (CollectionUtils.isEmpty(aclUserTel) && CollectionUtils.isEmpty(aclUserFnl)) {
			return null;
		}
		String fieldTel = "CAS_APPLY_TEL_SURVEY";
		String fieldFnl = "CAS_APPLY_FINALAUDIT";
		Map<String, TmAclUser> tmAclUserMap = new HashMap<>();
		if (CollectionUtils.sizeGtZero(aclUserTel)) {
			for (int i = 0; i < aclUserTel.size(); i++) {
				TmAclUser pb = aclUserTel.get(i);
				if(pb==null || pb.getUserNo() == null){
					continue;
				}
				String field=fieldTel+"-"+pb.getUserNo();
				tmAclUserMap.put(field,pb);
				redisUtil.hsetnx(key, field, SerializeUtils.serialize(pb));
			}
		}
		if (CollectionUtils.sizeGtZero(aclUserFnl)) {
			for (int i = 0; i < aclUserFnl.size(); i++) {
				TmAclUser pb = aclUserFnl.get(i);
				if(pb==null || pb.getUserNo() == null){
					continue;
				}
				String field=fieldFnl+"-"+pb.getUserNo();
				tmAclUserMap.put(field,pb);
				redisUtil.hsetnx(key, field, SerializeUtils.serialize(pb));
			}
		}
		return tmAclUserMap;
	}

	/**
	 * 通过节点名称加用户编号获取取相应权限用户
	 * @param userNo
	 * @return
	 */
	public TmAclUser getAuthorityUsers(String userNo){
		if(StringUtils.isBlank(userNo)) {
			return null;
		}
		String keys = AppConstant.AUTHORITY_USERS;
		byte[] bytes = redisUtil.hget(keys, userNo.getBytes());
		Object obj = SerializeUtils.unSerialize(bytes);
		if (obj != null && obj instanceof TmAclUser) {
			return (TmAclUser) obj;
		}else {
			Map<String,TmAclUser> map = initAuthorityUserMap();
			if (map != null && map.size() > 0) {
				return map.get(userNo);
			}
		}
		return null;
	}

	/**
	 * @Author:shiminghong
	 * @Description : 初始化获取风控中英文描述
	 */
	public String initExtRiskEnToCnParam() {
		long delNum = redisUtil.del(AppDitDicConstant.ext_Risk_EnToCn_Param);
		logger.info("初始化【"+AppDitDicConstant.ext_Risk_EnToCn_Param+"】，删除Redis操作的结果："+delNum);
		Map<String, String> ccifConf = getInterNetAddrParam(AppDitDicConstant.ext_bjj_risk_conf);
		if (ccifConf != null && StringUtils.isNotBlank(ccifConf.get("riskParamUrl"))) {
			String reqUrl = ccifConf.get("riskParamUrl");
			//联机查询所需的中文描述
			try {
				long start = System.currentTimeMillis();
				logger.info(LogPrintUtils.printCommonStartLog(start, "") + "联机查询所需的中文描述开始");
				HttpPost post = new HttpPost(reqUrl);
				post.setHeader("Content-Type", "application/json");
				post.setHeader("Connection", "close");
				RequestConfig reqConfig = RequestConfig.custom().setConnectTimeout(65000).setConnectionRequestTimeout(10000)
						.setSocketTimeout(65000).build();
				post.setConfig(reqConfig);
				StringEntity entity = new StringEntity("", Charset.forName("UTF-8"));
				entity.setContentEncoding("UTF-8");
				entity.setContentType("application/json");
				post.setEntity(entity);
				String respExtRiskEnToCnParam = HttpClientUtil.execute(post);
				redisUtil.set(AppDitDicConstant.ext_Risk_EnToCn_Param, respExtRiskEnToCnParam);
				return  respExtRiskEnToCnParam;
			} catch (Exception e) {
				logger.error("联机查询所需的中文描述异常", e);
			}
		} else {
			logger.error("联机中文描述未查询到网络配置，调用失败！");
		}

		return  null;
	}

	/**
	* @Author:shiminghong
	* @Description : 获取风控中英文描述
	*/
	public String getExtRiskEnToCnParam(String  extRiskEnToCnParam) {
		if (StringUtils.isBlank(extRiskEnToCnParam)){
			return  null;
		}
		String respExtRiskEnToCnParam = redisUtil.get(extRiskEnToCnParam);
		if (StringUtils.isNotBlank(respExtRiskEnToCnParam)){
			return  respExtRiskEnToCnParam;
		}else {
			respExtRiskEnToCnParam = initExtRiskEnToCnParam();
			if (StringUtils.isNotBlank(respExtRiskEnToCnParam)) {
				return respExtRiskEnToCnParam;
			}
		}
		return null;
	}

	/**
	 * @Author:shiminghong
	 * @Description :  查询初始化风控决策页面所需展示的字段
	 */
	public byte[] getCreditRiskShowParams(String creditRiskShowParams) {

		if (StringUtils.isBlank(creditRiskShowParams)) {
			return null;
		}
		byte[] respCreditRiskShowParams = redisUtil.getByByte(creditRiskShowParams);
		if (StringUtils.isNotBlank(String.valueOf(respCreditRiskShowParams))) {
			return respCreditRiskShowParams;
		} else {
			respCreditRiskShowParams = initCreditRiskShowParams();
			if (StringUtils.isNotBlank(String.valueOf(respCreditRiskShowParams))) {
				return respCreditRiskShowParams;
			}
		}
		return null;
	}

	/**
	 * @Author:shiminghong
	 * @Description :  查询初始化风控决策页面所需展示的字段
	 */
	public  byte[] initCreditRiskShowParams() {
		long delNum = redisUtil.del(AppDitDicConstant.CreditRiskShowParams);
		logger.info("初始化【" + AppDitDicConstant.CreditRiskShowParams + "】，删除Redis操作的结果：" + delNum);
		TmAclDict tmAclDict = new TmAclDict();
		tmAclDict.setType(AppDitDicConstant.CreditRiskShowParams);
		List<TmAclDict> list = aclDictDao.getOrderDictByValueAndSort(tmAclDict);
//        Iterator<TmAclDict> iterator = list.iterator();
		LinkedHashMap<String, LinkedHashMap<String, String>> map = new LinkedHashMap<>();
//        while (iterator.hasNext()) {
//            TmAclDict aclDict = iterator.next();
		ObjectOutputStream objectOutputStream = null;
		ByteArrayOutputStream byteArrayOutputStream = null;
		byte[] bytes = null;
		try {
			for (int i = 0; i < list.size(); i++) {
				TmAclDict aclDict = list.get(i);
				if (StringUtils.isNotBlank(aclDict.getValue()) && StringUtils.isNotBlank(aclDict.getCode()) && StringUtils.isNotBlank(aclDict.getCodeName())) {
					if (StringUtils.isNotEmpty(map.get(aclDict.getCodeName()))) {
						LinkedHashMap<String, String> temMap = map.get(aclDict.getCodeName());
						temMap.put(aclDict.getValue(), aclDict.getCode());
						map.put(aclDict.getCodeName(), temMap);
					} else {
						LinkedHashMap<String, String> smallMap = new LinkedHashMap<>();
						smallMap.put(aclDict.getValue(), aclDict.getCode());
						map.put(aclDict.getCodeName(), smallMap);
					}
				}
			}

//        }
//		JSONObject jsonObject = new JSONObject(true);
//		jsonObject.put(AppDitDicConstant.CreditRiskShowParams, map);
//		String respCreditRiskShowParams = JSONObject.toJSONString(jsonObject);
//		byte[] bytes=respCreditRiskShowParams.getBytes();
			try {
				byteArrayOutputStream = new ByteArrayOutputStream();
				objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
				objectOutputStream.writeObject(map);
				ByteArray byteArray = byteArrayOutputStream.toByteArray();
				bytes = byteArray.toByteArray();
				objectOutputStream.flush();
				byteArrayOutputStream.flush();
			}catch (IOException io){
				throw new ProcessException("查询初始化风控决策页面所需展示的字段时IO错误");
			}finally {
				objectOutputStream.close();
				byteArrayOutputStream.close();
			}
		} catch (Exception e) {
			logger.error("查询初始化风控决策页面所需展示时错误",e);
		}
		if (StringUtils.isNotBlank(String.valueOf(bytes))) {
			redisUtil.setByte(AppDitDicConstant.CreditRiskShowParams, bytes);
			return bytes;
		}
		return null;
	}
	
	/**
	 * 刷新系统全部缓存
	 * @see com.jjb.acl.cache.InnerCache#refresh()
	 */
	public void refresh() {

		accessDictService.initAclDictParm();
		branchCacheContext.refresh();
//		instalParamCacheContext.refresh();
		productCacheContext.refresh();

//		logger.info("====>开始[initDictParamMap]参数刷新,原参数大小["+(redisUtil.==null ? 0:dicParam.size())+"]");
		initInterNetAddrParamMap("");
//		logger.info("====>结束[initDictParamMap]参数刷新,原参数大小["+(dicParam==null ? 0:dicParam.size())+"]");

//		logger.info("====>开始[initPageConfig]参数刷新,原参数大小["+(pageMap==null ? 0:pageMap.size())+"]");
		initPageConfig();
//		logger.info("====>结束[initPageConfig]参数刷新,新参数大小["+(pageMap==null ? 0:pageMap.size())+"]");

//		logger.info("====>开始[initTmAppAuditQuota]参数刷新,原参数大小["+(TmAppAuditQuotaMap==null ? 0:TmAppAuditQuotaMap.size())+"]");
		initTmAppAuditQuota();
//		logger.info("====>开始[initTmAppAuditQuota]参数刷新,原参数大小["+(TmAppAuditQuotaMap==null ? 0:TmAppAuditQuotaMap.size())+"]");

//		logger.info("====>开始[initApplyOnlineOnOffParamMap]参数刷新,原参数大小["+(redisUtil.==null ? 0:onlineOnOffParam.size())+"]");
		initApplyOnlineOnOffParamMap();
//		logger.info("====>结束[initApplyOnlineOnOffParamMap]参数刷新,新参数大小["+(onlineOnOffParam==null ? 0:onlineOnOffParam.size())+"]");

//		logger.info("====>开始[initPageParameters](页面参数配置)参数刷新,原参数大小["+(pageParameters==null ? 0:pageParameters.size())+"]");
		initTelCheckParam();
//		logger.info("====>结束[initPageParameters](页面参数配置)参数刷新,新参数大小["+(pageParameters==null ? 0:pageParameters.size())+"]");

//		logger.info("====>开始[backMarkMap]参数刷新,原参数大小["+(backMarkMap==null ? 0:backMarkMap.size())+"]");
//		initBackMarkMap();
//		logger.info("====>结束[backMarkMap]参数刷新,新参数大小["+(backMarkMap==null ? 0:backMarkMap.size())+"]");

//		logger.info("====>开始[initAclDictParam]参数刷新,原参数大小["+(aclDictMap==null ? 0:aclDictMap.size())+"]");
//		initAclDictParam(null);
//		logger.info("====>结束[initAclDictParam]参数刷新,新参数大小["+(aclDictMap==null ? 0:aclDictMap.size())+"]");

//		logger.info("====>开始[applyRejectReasonMap]参数刷新,原参数大小["+(applyRejectReasonMap==null ? 0:applyRejectReasonMap.size())+"]");
		initApplyRejectReasonMap();
//		logger.info("====>结束[applyRejectReasonMap]参数刷新,新参数大小["+(applyRejectReasonMap==null ? 0:applyRejectReasonMap.size())+"]");

//		logger.info("====>开始[initFieldRegionList]参数刷新,原参数大小["+(fieldRegionList==null ? 0:fieldRegionList.size())+"]");
		initFieldRegionList();
//		logger.info("====>结束[initFieldProductDto]参数刷新,原参数大小["+(fieldRegionList==null ? 0:fieldRegionList.size())+"]");

//		logger.info("====>开始[initApplyPatchBoltParamMap]参数刷新,原参数大小["+(patchBoltParamMap==null ? 0:patchBoltParamMap.size())+"]");
		initApplyPatchBoltParamMap();
//		logger.info("====>结束[initApplyPatchBoltParamMap]参数刷新,新参数大小["+(patchBoltParamMap==null ? 0:patchBoltParamMap.size())+"]");

//		logger.info("====>开始[initFieldProductDto]参数刷新,原参数大小["+(fieldProductDtoMap==null ? 0:fieldProductDtoMap.size())+"]");
		initFieldProductDto();
//		logger.info("====>结束[initFieldProductDto]参数刷新,原参数大小["+(fieldProductDtoMap==null ? 0:fieldProductDtoMap.size())+"]");

		initTmAclUserMap();

//		logger.info("====>开始[initAclDictByAddr]参数刷新,原参数大小["+(redisUtil==null ? 0:dictGetByTypeCache.size())+"]");
		initAclDictByAddr();//清空异步加载省市区缓存
//		logger.info("====>结束[initAclDictByAddr]参数刷新,新参数大小["+(dictGetByTypeCache==null ? 0:dictGetByTypeCache.size())+"]");

		initTmAppUserRelationMap();//清空用户关系集合缓存

		initAuthorityUserMap();//清空特定节点对应的集合缓存

		initExtRiskEnToCnParam();//清空决策返回数据的中文描述换成参数

		initCreditRiskShowParams(); //查询初始化风控决策页面所需展示的字段

	}

}
