package com.jjb.ecms.biz.cache;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jjb.acl.biz.dao.TmAclBranchDao;
import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.acl.infrastructure.TmAclBranch;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.unicorn.cache.RedisUtil;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.SerializeUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 
 * @Description: 缓存
 * @author JYData-R&D-HN
 * @date 2016年9月25日 下午3:41:42
 * @version V1.0
 */
@Component
public class BranchCacheContext implements Serializable{

	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private TmAclBranchDao tmBranchDao;
	@Autowired
	private RedisUtil redisUtil;
	/**
	 * 从数据库查询所有的机构网点信息
	 * @return
	 */
	public List<TmAclBranch> getAllBranchByDb(){
		Map<String,Object> params = new LinkedHashMap<String, Object>();
//		List<String> ss = new ArrayList<String>();
//		ss.add("sort");
//		ss.add("parentCode");
//		ss.add("branchCode");
		params.put("_SORT_NAME", "parentCode");
		params.put("_SORT_ORDER", "asc");
		List<TmAclBranch> tmBranchs = tmBranchDao.queryForList(new TmAclBranch(), params);
		return tmBranchs;
	}
	/**
	 * 初始化所有的机构网点
	 * @return
	 */
	public LinkedHashMap<String, TmAclBranch> initTmAclBranchMap(){
		long delrs1 = redisUtil.del(AppConstant.allBranch);
		logger.info("初始化【"+AppConstant.allBranch+"】，删除Redis操作的结果："+delrs1);
		long delrs2 = redisUtil.del(AppConstant.allBranchMap);
		logger.info("初始化【"+AppConstant.allBranchMap+"】，删除Redis操作的结果："+delrs2);
		
		LinkedHashMap<String, TmAclBranch>  tmBranchMap = new LinkedHashMap<>();
		//查出所有的机构
		List<TmAclBranch> tmBranchs = getAllBranchByDb();
		if (CollectionUtils.sizeGtZero(tmBranchs)) {
			for(TmAclBranch enty : tmBranchs){
				if(StringUtils.isNotEmpty(enty.getBranchCode())) {
					tmBranchMap.put(enty.getBranchCode(), enty);
					redisUtil.hsetnx(AppConstant.allBranch, enty.getBranchCode(), SerializeUtils.serialize(enty));
				}
			}
			if(tmBranchMap!=null && tmBranchMap.size()>0) {
				redisUtil.setByte(AppConstant.allBranchMap, SerializeUtils.serialize(tmBranchMap));
				logger.info("初始化【"+AppConstant.allBranchMap+"】，新增数量："+tmBranchMap.size());
			}
		}
		return tmBranchMap;
	}
	
	/**
	 * 获取所有的机构网点信息
	 * @return
	 */
	public LinkedHashMap<String, TmAclBranch> getAllBranchMap(){
		LinkedHashMap<String, TmAclBranch> tmBranchMap = null;
		byte [] bytes = redisUtil.getByByte(AppConstant.allBranchMap);
		Object obj = SerializeUtils.unSerialize(bytes);
		if(obj!=null && obj instanceof LinkedHashMap) {
			tmBranchMap = (LinkedHashMap<String, TmAclBranch>) obj;
		}else {
			tmBranchMap = initTmAclBranchMap();
		}
		return tmBranchMap;
	}
	
	/**
	 * 根据branchCode从(全行)机构网点中查询单个网点信息
	 * @return
	 */
	public TmAclBranch getTmAclBranchByCode(String branchCode){
		if(StringUtils.isNotEmpty(branchCode)) {
			byte [] bytes = redisUtil.hget(AppConstant.allBranch, branchCode.getBytes());
			Object obj = SerializeUtils.unSerialize(bytes);
			if(obj == null){
				initTmAclBranchMap();
			}else if(obj instanceof TmAclBranch) {
				return (TmAclBranch) obj;
			}
		}
		return null;
	}
	
	/**
	 * 根据网点机构或者用户查询其下属分支行网点机构<br>
	 * 如果是一级网点或者无上级网点时默认显示所有网点信息
	 * @return Map<String, TmAclBranch>
	 */
	public LinkedHashMap<String, TmAclBranch> getSubBranchByBranchOrUser(String branchCode,String userNo){
		TmAclBranch br = null;
		LinkedHashMap<String, TmAclBranch> tmBranchMap = getAllBranchMap();
		if(StringUtils.isNotEmpty(branchCode)){
			br = tmBranchMap.get(branchCode);
		}else if(StringUtils.isNotEmpty(userNo)){
			String useBr = OrganizationContextHolder.getBranchCode();
			if(StringUtils.isNotEmpty(useBr)){
				br = tmBranchMap.get(useBr);
			}
		}
		if(br==null || StringUtils.isEmpty(br.getBranchCode())){
			return null;
		}
		LinkedHashMap<String, TmAclBranch> ret = null;
		if(tmBranchMap != null && !tmBranchMap.isEmpty()){
			ret = new LinkedHashMap<String, TmAclBranch>();
			for(TmAclBranch enty : tmBranchMap.values()){
				if(enty.getBranchCode().equals(br.getBranchCode())){//获取当前机构网点
					ret.put(enty.getBranchCode(), enty);
				}else if(StringUtils.isNotEmpty(enty.getParentPath()) &&
						enty.getParentPath().contains("/"+br.getBranchCode()+"/")){//获取下级机构网点
					ret.put(enty.getBranchCode(), enty);
				}
			}
		}
		return ret;
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
		LinkedHashMap<Object, Object> branchMap = new LinkedHashMap<Object, Object>();
		
		if(StringUtils.equals(AppConstant.allBranch,param)) {
			LinkedHashMap<String, TmAclBranch> tmBranchMap = getAllBranchMap();
			if (tmBranchMap != null && !tmBranchMap.isEmpty()) {
				for (TmAclBranch enty : tmBranchMap.values()) {
					branchMap.put(enty.getBranchCode(), enty.getBranchName());
				}
			}
		}
		
		
		//根据用户获取当前网点及所有下级机构网点
		Map<String, TmAclBranch> tempMap = getSubBranchByBranchOrUser(null, OrganizationContextHolder.getUserNo());
		if(StringUtils.isNotBlank(param) && tempMap!=null && tempMap.size()>0
				&& !StringUtils.equals(param, AppConstant.allBranch)){
			if(AppConstant.issueInd.equals(param)){//说明是发卡权限
				String issueInd = null;
				for(TmAclBranch enty : tempMap.values()){
					issueInd = enty.getBranchIssueInd();
					if(StringUtils.isNotBlank(issueInd) && issueInd.equals(Indicator.Y.name())){//有发卡权限标志
						branchMap.put(enty.getBranchCode(), enty.getBranchName());
					}
				}
			}else if(AppConstant.cardCollectInd.equals(param)){//说明有领卡权限
				String cardCollectInd = null;
				for(TmAclBranch enty : tempMap.values()){
					cardCollectInd = enty.getCardCollectInd();
					if(StringUtils.isNotBlank(cardCollectInd) && cardCollectInd.equals(Indicator.Y.name())){//有领卡权限标志
						branchMap.put(enty.getBranchCode(), enty.getBranchName());
					}
				}
			}/*else if(AppConstant.independEntity.equals(param)){//独立发卡网点权限
				String ifEnableHairpin = null;
				for(TmAclBranch enty : tempMap.values()){
					ifEnableHairpin = enty.getIfEnableHairpin();
					if(StringUtils.isNotBlank(ifEnableHairpin) && ifEnableHairpin.equals(Indicator.Y.name())){//有独立发卡网点权限标志
						branchMap.put(enty.getBranchCode(), enty.getBranchName());
					}
				}
			}*/
		}else if(tempMap!=null && tempMap.size()>0){
			for(TmAclBranch enty : tempMap.values()){
				branchMap.put(enty.getBranchCode(), enty.getBranchName());
			}
		}
		return branchMap;
	}
	
	/**
	 * 根据网点等级查询
	 * @param tmAclBranch
	 * @return
	 */
	public LinkedHashMap<Object, Object> getBranchMapByLevel(String branchLevel){
		LinkedHashMap<Object, Object> branchMap = null;
		LinkedHashMap<String, TmAclBranch> tmBranchMap = getAllBranchMap();
		if(StringUtils.isNotBlank(branchLevel)){
			if(tmBranchMap != null && !tmBranchMap.isEmpty()){
				branchMap = new LinkedHashMap<Object, Object>();
				for(TmAclBranch enty : tmBranchMap.values()){
					if(StringUtils.isEmpty(enty.getBranchLevel())){
						continue;
					}else if(branchLevel.equals(enty.getBranchLevel()) 
							|| (branchLevel.equals("2") && "1".equals(enty.getBranchLevel()))){//二级网点需要带出一级根网点
						branchMap.put(enty.getBranchCode(), enty.getBranchName());
					}
				}
			}
		}
		return branchMap;
	}
	
	/**
	 *  刷新网点机构缓存
	 */
	public void refresh() {
		initTmAclBranchMap();
	}
}
