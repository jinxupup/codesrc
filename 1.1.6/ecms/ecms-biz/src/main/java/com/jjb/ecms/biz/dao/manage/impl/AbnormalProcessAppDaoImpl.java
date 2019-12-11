package com.jjb.ecms.biz.dao.manage.impl;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.manage.AbnormalProcessAppDao;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.facility.dto.ApplyAbnormalProcessDto;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
  * @Description: 异常流程申请件管理dao实现类
  * @author JYData-R&D-L.L
  * @date 2016年9月22日 下午5:11:43
  * @version V1.0
 */
@Repository("abnormalProcessAppDao")
public class AbnormalProcessAppDaoImpl extends AbstractBaseDao<ApplyAbnormalProcessDto> implements AbnormalProcessAppDao{
	private Logger logger = LoggerFactory.getLogger(AbnormalProcessAppDaoImpl.class);
	private static final String selectOne = "com.jjb.ecms.biz.ApplyAbnormalProcessDtoMapper.selectOne";
	private static final String selectTwo = "com.jjb.ecms.biz.ApplyAbnormalProcessDtoMapper.selectTwo";
	private static final String selectThree = "com.jjb.ecms.biz.ApplyAbnormalProcessDtoMapper.selectThree";
	private static final String selectFour = "com.jjb.ecms.biz.ApplyAbnormalProcessDtoMapper.selectFour";
	private static final String selectFive = "com.jjb.ecms.biz.ApplyAbnormalProcessDtoMapper.selectFive";
	private static final String selectOneToFive = "com.jjb.ecms.biz.ApplyAbnormalProcessDtoMapper.selectOneToFive";
	private static final String selectOneToFiveCount = "com.jjb.ecms.biz.ApplyAbnormalProcessDtoMapper.selectOneToFiveCount";
	private static final String selectByAppNo = "com.jjb.ecms.biz.ApplyAbnormalProcessDtoMapper.selectByAppNo";
	private static final String selectTaskIdByProcInstId = "com.jjb.ecms.biz.ApplyAbnormalProcessDtoMapper.selectTaskIdByProcInstId";
	
//	private static final String deleteFromAppAdd = "com.jjb.ecms.biz.ApplyAbnormalProcessDtoMapper.deleteFromAppAdd";
//	private static final String deleteFromAttach = "com.jjb.ecms.biz.ApplyAbnormalProcessDtoMapper.deleteFromAttach";
//	private static final String deleteFromCardface = "com.jjb.ecms.biz.ApplyAbnormalProcessDtoMapper.deleteFromCardface";
//	private static final String deleteFromHistory = "com.jjb.ecms.biz.ApplyAbnormalProcessDtoMapper.deleteFromHistory";
//	private static final String deleteFromMain = "com.jjb.ecms.biz.ApplyAbnormalProcessDtoMapper.deleteFromMain";
//	private static final String deleteFromModify = "com.jjb.ecms.biz.ApplyAbnormalProcessDtoMapper.deleteFromModify";
//	private static final String deleteFromNode = "com.jjb.ecms.biz.ApplyAbnormalProcessDtoMapper.deleteFromNode";
//	private static final String deleteFromPrimAnnex = "com.jjb.ecms.biz.ApplyAbnormalProcessDtoMapper.deleteFromPrimAnnex";
//	private static final String deleteFromPrimApp = "com.jjb.ecms.biz.ApplyAbnormalProcessDtoMapper.deleteFromPrimApp";
//	private static final String deleteFromPrimCard = "com.jjb.ecms.biz.ApplyAbnormalProcessDtoMapper.deleteFromPrimCard";
//	private static final String deleteFromPrimContact = "com.jjb.ecms.biz.ApplyAbnormalProcessDtoMapper.deleteFromPrimContact";
//	private static final String deleteFromAppRfe = "com.jjb.ecms.biz.ApplyAbnormalProcessDtoMapper.deleteFromAppRfe";
	private static final String deleteFromEventSubscr = "com.jjb.ecms.biz.ApplyAbnormalProcessDtoMapper.deleteFromEventSubscr";
	private static final String deleteFromExecution = "com.jjb.ecms.biz.ApplyAbnormalProcessDtoMapper.deleteFromExecution";
	private static final String deleteFromIdentitylink = "com.jjb.ecms.biz.ApplyAbnormalProcessDtoMapper.deleteFromIdentitylink";
	private static final String deleteFromIdentitylinkByTaskId = "com.jjb.ecms.biz.ApplyAbnormalProcessDtoMapper.deleteFromIdentitylinkByTaskId";
	private static final String deleteFromRuTask = "com.jjb.ecms.biz.ApplyAbnormalProcessDtoMapper.deleteFromRuTask";
	private static final String deleteFromVariable = "com.jjb.ecms.biz.ApplyAbnormalProcessDtoMapper.deleteFromVariable";
	
	private static final String deleteFromHiProcinst = "com.jjb.ecms.biz.ApplyAbnormalProcessDtoMapper.deleteFromHiProcinst";//删除工作流历史流程实例
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	/**
	 * 查询异常流程申请件
	 * @return
	 */
	@Override
	public List<ApplyAbnormalProcessDto> getAbnormalProcessApp(String appNo, String rtfState,String cardNo,String idNo,String idType) {
		ApplyAbnormalProcessDto abnormalProcessAppDto  = new ApplyAbnormalProcessDto();
		if(StringUtils.isNotEmpty(appNo)){
			abnormalProcessAppDto.setAppNo(appNo);
		}
		if(StringUtils.isNotEmpty(rtfState)){
			abnormalProcessAppDto.setRtfState(rtfState);
		}
		if(StringUtils.isNotEmpty(cardNo)){
			abnormalProcessAppDto.setCardNo(cardNo);
		}
		if(StringUtils.isNotEmpty(idNo)){
			if(StringUtils.isNotEmpty(idType)){
				abnormalProcessAppDto.setIdType(idType);
			}
			abnormalProcessAppDto.setIdNo(idNo);
		}
		List<ApplyAbnormalProcessDto> list = new ArrayList<ApplyAbnormalProcessDto>();
		List<ApplyAbnormalProcessDto> listOne = new ArrayList<ApplyAbnormalProcessDto>();
		List<ApplyAbnormalProcessDto> listTwo = new ArrayList<ApplyAbnormalProcessDto>();
		List<ApplyAbnormalProcessDto> listThree = new ArrayList<ApplyAbnormalProcessDto>();
		List<ApplyAbnormalProcessDto> listFour = new ArrayList<ApplyAbnormalProcessDto>();
		List<ApplyAbnormalProcessDto> listFive = new ArrayList<ApplyAbnormalProcessDto>();
		
		listOne = queryForList(selectOne, abnormalProcessAppDto);
		listTwo = queryForList(selectTwo, abnormalProcessAppDto);
		listThree = queryForList(selectThree, abnormalProcessAppDto);
		listFour = queryForList(selectFour, abnormalProcessAppDto);
		listFive = queryForList(selectFive, abnormalProcessAppDto);
		
		
		list.addAll(listOne);
		list.addAll(listTwo);
		list.addAll(listThree);
		list.addAll(listFour);
		list.addAll(listFive);
		
		return deleteRepeat(list);
	}
	
	/**
	 * 去除重复的异常件信息
	 * @param mList
	 * @return
	 */
	public List<ApplyAbnormalProcessDto> deleteRepeat(List<ApplyAbnormalProcessDto> mList){
		for (int i = 0; i < mList.size()-1; i++) { 
			ApplyAbnormalProcessDto abnormall1 = mList.get(i);
		    for (int j = mList.size()-1; j > i; j--) { 
		    	ApplyAbnormalProcessDto abnormall2 = mList.get(j);
		        if (abnormall2.getAppNo().equals(abnormall1.getAppNo())) {
		        	StringBuffer buffer = new StringBuffer();
		        	buffer.append(abnormall1.getExcMsg()).append("/").append(abnormall2.getExcMsg());
		        	abnormall1.setExcMsg(buffer.toString());
		            mList.remove(j);  
		        }  
		    }  
		}
		return mList;
	}
	
	@Override
	public void delete(String appNo) {
		long start = System.currentTimeMillis();
		logger.info("开始删除异常件工作流数据"+LogPrintUtils.printAppNoLog(appNo, start,null));
		ApplyAbnormalProcessDto abnormalProcessAppDto = new ApplyAbnormalProcessDto();
		abnormalProcessAppDto.setAppNo(appNo);
//		delete(deleteFromAppAdd, abnormalProcessAppDto);
//		delete(deleteFromAttach, abnormalProcessAppDto);
//		delete(deleteFromCardface, abnormalProcessAppDto);
//		delete(deleteFromHistory, abnormalProcessAppDto);
//		delete(deleteFromMain, abnormalProcessAppDto);
//		delete(deleteFromModify, abnormalProcessAppDto);
//		delete(deleteFromNode, abnormalProcessAppDto);
//		delete(deleteFromPrimAnnex, abnormalProcessAppDto);
//		delete(deleteFromPrimApp, abnormalProcessAppDto);
//		delete(deleteFromPrimCard, abnormalProcessAppDto);
//		delete(deleteFromPrimContact, abnormalProcessAppDto);
//		delete(deleteFromAppRfe, abnormalProcessAppDto);
		List<ApplyAbnormalProcessDto> list = queryForList(selectByAppNo, abnormalProcessAppDto);
		if(CollectionUtils.isNotEmpty(list)){
			for(int i=0;i<list.size();i++){
				if(list.get(i)!=null && StringUtils.isNotEmpty(list.get(i).getProcInstId())){
					String procInstId = list.get(i).getProcInstId();
					abnormalProcessAppDto.setProcInstId(procInstId);
					delete(deleteFromEventSubscr, abnormalProcessAppDto);
					
					ApplyAbnormalProcessDto dto2 = new ApplyAbnormalProcessDto();
					dto2.setProcInstId(procInstId);
					List<ApplyAbnormalProcessDto> list2 = queryForList(selectTaskIdByProcInstId,abnormalProcessAppDto);
					delete(deleteFromIdentitylink, abnormalProcessAppDto);
					if(CollectionUtils.isNotEmpty(list2)){
						for(int j=0;j<list2.size();j++){
							ApplyAbnormalProcessDto dto3 = list2.get(j);
							if(dto3!=null && StringUtils.isNotEmpty(dto3.getTaskId())){
								abnormalProcessAppDto.setTaskId(dto3.getTaskId());
								delete(deleteFromIdentitylinkByTaskId, abnormalProcessAppDto);
							}
						}
					}
					delete(deleteFromRuTask, abnormalProcessAppDto);
					delete(deleteFromVariable, abnormalProcessAppDto);
					delete(deleteFromExecution, abnormalProcessAppDto);
				}
				
			}
		}
		

		logger.info("结束删除异常件工作流数据"+LogPrintUtils.printAppNoEndLog(appNo, start,null));
	}
	
	/**
	 * 删除工作流历史流程实例
	 * @param appNo
	 */
	public void deleteFromHiProcinst(String appNo){
		ApplyAbnormalProcessDto abnormalProcessAppDto = new ApplyAbnormalProcessDto();
		abnormalProcessAppDto.setAppNo(appNo);
		delete(deleteFromHiProcinst, abnormalProcessAppDto);
	}

	@Override
	public Page<ApplyAbnormalProcessDto> getAbnormalProcessApp(
			Page<ApplyAbnormalProcessDto> page) {
		// TODO Auto-generated method stub
//		 pagesize*(pagenumber-1) and pagesize*pagenumber;
		page.getQuery().put("pagenumber", page.getPageNumber());
		page.getQuery().put("pagesize", page.getPageSize());
//		List<ApplyAbnormalProcessDto> rows = sqlSession.selectList(selectOneToFive,page.getQuery());
		List<ApplyAbnormalProcessDto> rows = getAbnormalProcessApp(page.getQuery().get("appNo")+"", 
				page.getQuery().get("rtfState")+"", null, 
				page.getQuery().get("idNo")+"", 
				page.getQuery().get("idType")+"");
		page.setRows(rows);
		page.setTotal(rows.size());
		return page;
	}
	
	/**
	 * 根据条件删除 Identitylink无效数据
	 * @param taskId
	 * @param procInstId
	 */
	@Override
	public void deleteFromIdentitylink(String taskId,String procInstId) {
		
		ApplyAbnormalProcessDto abnormalProcessAppDto = new ApplyAbnormalProcessDto();
		if(abnormalProcessAppDto!=null && StringUtils.isNotEmpty(procInstId)) {
			abnormalProcessAppDto.setProcInstId(procInstId);
			delete(deleteFromIdentitylink, abnormalProcessAppDto);
		}
		if(abnormalProcessAppDto!=null && StringUtils.isNotEmpty(taskId)) {
			abnormalProcessAppDto.setTaskId(taskId);
			delete(deleteFromIdentitylinkByTaskId, abnormalProcessAppDto);
		}
	}
}
