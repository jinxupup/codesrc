package com.jjb.acl.access.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjb.acl.infrastructure.TmAclBranch;
import com.jjb.acl.infrastructure.TmAclUser;
import com.jjb.unicorn.base.service.BaseService;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.kits.StrKit;
import com.jjb.unicorn.facility.model.Query;

/**
 *
 * @author BIG.D.W.K
 *
 */
@Service
public class AccessBranchService extends BaseService<TmAclBranch> {

	@Autowired
	private AccessParamService accessParamService;

	@Autowired
	private AccessUserService accessUserService;




	//缓存
	private static Map<String,List<String>> branchCache = new ConcurrentHashMap<String, List<String>>();
	private static Map<String,List<TmAclBranch>> branchAllCache = new ConcurrentHashMap<String, List<TmAclBranch>>();
	private static Map<String,TmAclBranch> branchOneCache = new ConcurrentHashMap<String,TmAclBranch>();

	public List<String> getAuthBranchIds(String branchCode){

		//防止空指针异常
		List<String> branchIds = null;
		if(branchCode != null){
			branchIds =	branchCache.get(branchCode);
		}else{
			branchIds = new ArrayList<String>();
		}

		if(StrKit.notBlankList(branchIds)){
			return branchIds;
		}else{
			branchIds = new ArrayList<String>();
			if(StrKit.notBlank(branchCode)){
				branchIds.add(branchCode);

				TmAclBranch tmAclBranchQuery = new TmAclBranch();
				tmAclBranchQuery.setBranchCode(branchCode);
				tmAclBranchQuery.setOrg(accessParamService.getDefaultOrg());

				TmAclBranch tmAclBranch = super.queryByKey(tmAclBranchQuery);

				if(tmAclBranch!=null && StrKit.notBlank(tmAclBranch.getParentPath())){
					Query query = new Query();
					query.put("parentPath", tmAclBranch.getParentPath() + tmAclBranch.getBranchCode() + "/");

					List<TmAclBranch> branchList = super.queryForList("acl.access.TmAclBranchMapper.selectLeftLikePath", query);
					if(StrKit.notBlankList(branchList)){
						for(TmAclBranch branch:branchList){
							branchIds.add(branch.getBranchCode());
						}
					}
					branchCache.put(branchCode, branchIds);
				}
			}
		}
		return branchIds;
	}

	public List<TmAclBranch> getBranchAll(String org) {
		//防止空指针异常
		List<TmAclBranch> branchList = null;
		if(org != null){
			branchList = branchAllCache.get(org);
		}else{
			branchList = new ArrayList<TmAclBranch>();
		}

		if(StrKit.notBlankList(branchList)) {
			return branchList;
		} else {
			if(StrKit.notBlank(org)){
				TmAclBranch tmAclBranchQuery = new TmAclBranch();
				tmAclBranchQuery.setOrg(org);

				branchList = super.queryForList(tmAclBranchQuery);
				branchAllCache.put(org, branchList);
			}
		}

		return branchList;
	}

	public TmAclBranch getBranchById(String branchCode) {
		//防止空指针异常
		TmAclBranch branch = null;
		if(branchCode != null){
			branch = branchOneCache.get(branchCode);
		}else{
			branch = null;
		}

		if(StrKit.notNull(branch)) {
			return branch;
		} else {
			if(StrKit.notBlank(branchCode)){
				TmAclBranch tmAclBranchQuery = new TmAclBranch();
				tmAclBranchQuery.setBranchCode(branchCode);
				tmAclBranchQuery.setOrg(accessParamService.getDefaultOrg());

				branch = super.queryByKey(tmAclBranchQuery);
				branchOneCache.put(branchCode, branch);
			}
		}

		return branch;
	}


	/**
	 * 根据参数获取机构信息<br/>返回键值对的Map
	 * @param branch
	 * @return
	 */
	public LinkedHashMap getLinkedHashMapBranchByParm(TmAclBranch branch) {
		TmAclBranch br = null;
		String bc = branch.getBranchCode();
		String userNo = OrganizationContextHolder.getUserNo();
		String org = OrganizationContextHolder.getOrg();
		if(bc==null &&bc.equals("") && userNo!=null && !userNo.equals("")){
			TmAclUser user = accessUserService.getUserByOrgAndUserNo(org,userNo);
			bc = user.getBranchCode();
		}else if(bc==null || bc.equals("")){
			bc = OrganizationContextHolder.getBranchCode();
		}
		if(bc!=null && !bc.equals("")){
			br = getBranchById(bc);
		}
		if(br==null || br.getBranchCode()==null || br.getBranchCode().equals("")){
			return null;
		}
		LinkedHashMap<String, String> ret = new LinkedHashMap<String, String>();;
		//查出所有的机构
		List<TmAclBranch> tmBranchs = getBranchAll(org);
		if (null!=tmBranchs && tmBranchs.size()>0) {
			for(TmAclBranch enty : tmBranchs){
				if(enty.getBranchCode().equals(br.getBranchCode())){//获取当前机构网点
					ret.put(enty.getBranchCode(), enty.getBranchName());
				}else if(enty.getParentPath()!=null &&
						enty.getParentPath().contains("/"+br.getBranchCode()+"/")){//获取下级机构网点
					ret.put(enty.getBranchCode(), enty.getBranchName());
				}
			}
		}
		return ret;
	}
}
