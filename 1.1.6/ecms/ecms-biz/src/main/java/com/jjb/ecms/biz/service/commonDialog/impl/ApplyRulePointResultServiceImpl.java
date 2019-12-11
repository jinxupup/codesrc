package com.jjb.ecms.biz.service.commonDialog.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.commonDialog.ApplyRulePointResultService;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.facility.nodeobject.ApplyNodeGradeProcessData;
import com.jjb.ecms.facility.nodeobject.RulePointResultItem;

/**
 * @description: 评分结果service实现类
 * @author -BigZ.Y
 * @date 2016年9月21日 上午10:52:28 
 */
@Service("applyRulePointResultService")
public class ApplyRulePointResultServiceImpl implements ApplyRulePointResultService{

	@Autowired
	private ApplyQueryService applyQueryService;
	
	/**
	 * 获取评分结果
	 * @param appNo
	 * @return
	 */
	@Override
	@Transactional
	public List<RulePointResultItem> getRulePointResultItems(String appNo) {

		//获取所有的节点信息
		ApplyInfoDto applyInfoDto = applyQueryService.getNodeInfoByAppNo(appNo);
		ApplyNodeGradeProcessData applyNodeGradeProcessData = new ApplyNodeGradeProcessData();
		if(applyInfoDto != null && applyInfoDto.getTmAppNodeInfoRecordMap() != null){
			applyNodeGradeProcessData = (ApplyNodeGradeProcessData) applyInfoDto.getTmAppNodeInfoRecordMap().get(AppConstant.APPLY_NODE_GRADE_PROCESS_DATA);
		}
		List<RulePointResultItem> rulePointResultItems = new ArrayList<RulePointResultItem>();
		if(applyNodeGradeProcessData != null){
			for(RulePointResultItem enty : applyNodeGradeProcessData.getRulePointResultItemList()){
				rulePointResultItems.add(enty);
			}
			
		}
		return rulePointResultItems;
	}

	
}
