package com.jjb.acl.biz.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.jjb.acl.biz.dao.TmAclBranchDao;
import com.jjb.acl.infrastructure.TmAclBranch;


@Component
public class ContextUtil{

	@Autowired
	private TmAclBranchDao tmBranchDao;
	/**
	 * 
	 * @Description 根据branchCode获取当前机构及下级机构的机构号字符串
	 * @param branchCode
	 * @return
	 */
	public StringBuffer getOwnerBranch(String branchCode){
		Map<String,StringBuffer> all = new HashMap<String, StringBuffer>();
		all.putAll(getOwnerBranchList());
		return all.get(branchCode);
		
	}
	
	/**
	 * 初始化所有的受理网点
	 * KEY-当前机构号
	 * VALUE-当前及下级所有机构号
	 * @return
	 */
	public Map<String, List<String>> initOwningBranchMap(){
		Map<String, List<String>> owningBranchMap = new HashMap<String, List<String>>();
		List<String> branchIds = new ArrayList<String>();//存放当前及下级机构号
		Map<String, StringBuffer> owningBranchBuffer = getOwnerBranchList();//获取到所有的机构
		String[] _branch = null;
		for(Map.Entry<String, StringBuffer> enty: owningBranchBuffer.entrySet()){
			StringBuffer branchBuffer = enty.getValue();
			if (branchBuffer != null && branchBuffer.length() > 0) {
				_branch = branchBuffer.toString().split(",");
			}
			if (_branch != null) {
				branchIds = new ArrayList<String>();
				for (int i = 0; i < _branch.length; i++) {
					branchIds.add(_branch[i].replace("'", ""));
				}
			}
			owningBranchMap.put(enty.getKey(), branchIds);
		}
		return owningBranchMap;
	}
	
	/**
	 * 获取当前及下级机构号
	 * @param branchCode
	 * @return
	 */
	public List<String> getOwningBranchMap(String branchCode){
		List<TmAclBranch> branchs = tmBranchDao.getAllBranch();
		List<String> currentBranchList = new ArrayList<String>();
		for(TmAclBranch enty:branchs){
			if(enty.getBranchCode().equals(branchCode)){//获取当前机构网点
				currentBranchList.add(enty.getBranchCode());
			}else if(enty.getParentPath()!=null
					&& enty.getParentPath().contains("/"+branchCode+"/")){//获取下级机构网点
				currentBranchList.add(enty.getBranchCode());
			}
		}
		return currentBranchList;
	}
	
	/**
	 * 
	 * @Description 获取所有机构对应的当前机构及下级机构号拼接字符串
	 * @return
	 */
	public Map<String,StringBuffer> getOwnerBranchList()
	{
		//查出所有的机构
		List<TmAclBranch> tmBranchs = tmBranchDao.queryForList(new TmAclBranch());
		Map<String, TmAclBranch> tmbranchMap = new HashMap<String, TmAclBranch>();
		if (!CollectionUtils.isEmpty(tmBranchs)) {
			for(TmAclBranch enty : tmBranchs){
				tmbranchMap.put(enty.getBranchCode(), enty);
			}
		}
		
		Map<String,StringBuffer> branchList = new HashMap<String,StringBuffer>();
		for(Map.Entry<String,TmAclBranch> branch : tmbranchMap.entrySet()){
			String branchId = branch.getKey();
			String parentBranchId = branch.getValue().getParentCode();//.getParentMagBranch();
			//给自己加
			StringBuffer _branch = branchList.get(branchId);
			if(_branch == null){
				_branch = new StringBuffer();
				branchList.put(branchId, _branch);
			}
			if(_branch.length() > 0)_branch.append(",");
			_branch.append("'");
			_branch.append(branchId);
			_branch.append("'");
			
			//给父机构加
			while(!StringUtils.isBlank(parentBranchId)){

				TmAclBranch parentBranch = tmbranchMap.get(parentBranchId);
				if(parentBranch==null || parentBranch.getBranchCode().equals(parentBranch.getParentCode())){//.getParentMagBranch())){
					break;
				}

				String _branchId = parentBranch.getBranchCode();
				
				StringBuffer _parentBranch = branchList.get(_branchId);
				if(_parentBranch == null){
					_parentBranch = new StringBuffer();
					branchList.put(_branchId, _parentBranch);
				}
				if(_parentBranch.length() > 0)_parentBranch.append(",");
				_parentBranch.append("'");
				_parentBranch.append(branchId);
				_parentBranch.append("'");
				
				parentBranchId = parentBranch.getParentCode();//.getParentMagBranch();
			}
		}
			
			return branchList;
		}
	
	
}
