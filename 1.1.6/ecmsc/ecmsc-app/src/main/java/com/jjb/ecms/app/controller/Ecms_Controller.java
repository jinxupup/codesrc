package com.jjb.ecms.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.acl.infrastructure.TmAclBranch;
import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;

@Controller
@RequestMapping("/ecms_")
public class Ecms_Controller extends BaseController{
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private CacheContext cacheContext; 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
	@RequestMapping("/getCardFaceByProductCd")
	public Json cardFaceByProductCd(){
		String _PARENT_KEY = getPara("_PARENT_KEY");
		String _PARENT_VALUE = getPara("_PARENT_VALUE");
		Json j = Json.newSuccess();
		try{
			List<LinkedHashMap<Object, Object>> list = new ArrayList<LinkedHashMap<Object, Object>>();
			
			LinkedHashMap<Object, Object> map = cacheContext.getSimpleProductCardFaceLinkedMap(_PARENT_VALUE);
			if(map!=null && map.size()>0){
				for(Map.Entry<Object, Object> entry:map.entrySet()){
					LinkedHashMap m = new LinkedHashMap<String, String>();
					TmAclDict aclDict = (TmAclDict) entry.getValue();
					m.put("key", entry.getKey());
					m.put("value", aclDict.getCodeName());
					list.add(m);
				}
			}
			j.setObj(list);
			
		}catch(Exception e){
			j.setFail("根据卡产品["+_PARENT_VALUE+"]获取卡面列表失败");
		}
		return j;
	}
	
	@ResponseBody
	@RequestMapping("/getBranchesSelect")
	public Json getBranchesSelect(String query, @RequestParam(required=false,defaultValue="10") Integer maxSize, @RequestParam(required=false) String parentBranch
			, @RequestParam(required=false, defaultValue="false") Boolean isAll,@RequestParam(required=false,defaultValue="") String type){
		Json json = Json.newSuccess();
		String userNo = OrganizationContextHolder.getUserNo();
		logger.info("获取受理网点....type={}，userId={}",type,userNo);
		LinkedHashMap<Object, Object> branches = null;
		if(isAll) {
			branches = cacheContext.getBranchMapByParam(AppConstant.allBranch);
		}else {
			 branches = cacheContext.getBranchMapByParam(type);
		}
		LinkedHashMap<Object, Object> filteredBranches = new LinkedHashMap<Object, Object>();
		for(Entry entry :branches.entrySet()) {
			String label = (String)entry.getKey();
			String value = (String)entry.getValue();
			if(StringUtils.regexCheck(label, query) 
					|| StringUtils.regexCheck(value, query)) {
				filteredBranches.put(label,value);
				if(filteredBranches.size() >= maxSize) {
					break;
				}
			}
		}
		filteredBranches.put("", "");
		json.setObj(filteredBranches);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/getBrancheByCode")
	public Json getBrancheByCode(String branchCode){
		Json json = Json.newSuccess();
		if(StringUtils.isNotBlank(branchCode)) {
			TmAclBranch branch = cacheContext.getTmAclBranchByCode(branchCode);
			if(branch != null) {
				Map<String,String> map = new HashMap<String,String>();
				map.put("key", branch.getBranchCode());
				map.put("value", branch.getBranchName());
				json.setObj(map);
			}else {
				json.setFail("查询不到对应的营业网点");
				json.setCode("0");
			}
		}else {
			json.setFail("查询不到对应的营业网点");
			json.setCode("0");
		}
		return json;
	}

}
