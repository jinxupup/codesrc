package com.jjb.ecms.biz.ext.ecss;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jjb.acl.facility.enums.bus.EnumsActivitiNodeType;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.manage.ApplyEcssHistoryService;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.facility.nodeobject.ApplyNodePreCheckData;
import com.jjb.ecms.infrastructure.TmAppImageHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmDitDic;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 影像调阅
 * @Description:
 * @author JYData-R&D-HN
 * @date 2017年9月7日 上午11:54:07
 * @version V1.0
 */
@Component
public class BankImagesSysInfo {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired
	private ApplyEcssHistoryService applyEcssHistoryService;
	@Autowired
	private CacheContext cacheContext;

	/**
	 * 影像调阅
	 * @param appNo
	 * @return
	 */
	public LinkedHashMap<String, String> getImgUrlList(String appNo){
		long start = System.currentTimeMillis();
		List<Map<String, Serializable>> nodeList = applyQueryService.getNodeInfosByAppNo(appNo,EnumsActivitiNodeType.A085.name());
//
//		Map<String, Serializable> nodeMap = applyQueryService.getNodeInfoByAppNo(appNo, EnumsActivitiNodeType.A085.name());
//		ApplyNodePreCheckData applyNodePreCheckData = null;
//		if(nodeMap!=null && nodeMap.containsKey(AppConstant.APPLY_NODE_PRE_CHECK_DATA)){
//			applyNodePreCheckData = (ApplyNodePreCheckData) nodeMap.get(AppConstant.APPLY_NODE_PRE_CHECK_DATA);
//		}
//
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		TmAppMain main = applyQueryService.getTmAppMainByAppNo(appNo);
		try {
			TmDitDic onLinOffPic = cacheContext.getApplyOnlineOnOff(AppDitDicConstant.onLinOff_isCallPic);//联机调用开关参数-是否开启调用影像系统
			if(onLinOffPic==null || StringUtils.isEmpty(onLinOffPic.getRemark())) {
				throw new ProcessException("未开启影像调阅开个或设置的影像地址无效");
			}
			for (int j = 0; j <nodeList.size(); j++) {
				Map<String, Serializable> nodeMap = nodeList.get(j);
				ApplyNodePreCheckData applyNodePreCheckData = null;
				if(nodeMap!=null && nodeMap.containsKey(AppConstant.APPLY_NODE_PRE_CHECK_DATA)){
					applyNodePreCheckData = (ApplyNodePreCheckData) nodeMap.get(AppConstant.APPLY_NODE_PRE_CHECK_DATA);
				}
				if(applyNodePreCheckData==null || StringUtils.isEmpty(applyNodePreCheckData.getPics())){
					throw new ProcessException("未查询到申请件相关影像资料");
				}
//				String picsStr = nodeInfo.getContent().replace("[{", "{");
//				picsStr = picsStr.replace("}]", "}");
				JSONArray js = JSONArray.parseArray(applyNodePreCheckData.getPics());
				if(js==null || js.size()==0) {
					throw new ProcessException("未查询到申请件相关影像资料");
				}
				List<String> list = new ArrayList<>();
				String url = onLinOffPic.getRemark();
				for (int i = 0; i < js.size(); i++) {
					JSONObject jo = js.getJSONObject(i);
					String key1 = jo.getString("picTypeDesc");
					String key2 = jo.getString("picSort");
//				String key = key1+"-"+(i+1);
					String key = j+"-"+key2+"-"+key1;
					String name = jo.getString("picName");
					if(!list.contains(name)) {
						map.put(key, url+"/"+name);
						list.add(name);
					}
				}
			}
		} catch (Exception e) {
			logger.warn("影像调用失败:",e);
			throw new ProcessException("影像调阅失败["+e.getMessage()+"]");
		} finally{
			logger.info("获取客户影像资料url" + AppConstant.END+",appNo-"+appNo+"处理完成 - 耗时:"+(System.currentTimeMillis()-start));
			if(main!=null){
				insertImageHistory(main);
			}
		}

		return map;
	}

	/**
	 * 获取影像上传url
	 * @param appNo
	 * @param thisNode
	 * @return
	 */
	public String getUploadImageUrl(String appNo, String thisNode){
		long start = System.currentTimeMillis();
		String picUploadUrl = "";

		String org = OrganizationContextHolder.getOrg();
		try {
//			TmDitDic onLinOffPicOld = cacheContext.getApplyOnlineOnOff(AppDitDicConstant.onLinOff_isCallAicPicOld);//联机调用开关参数-是否开启调用影像系统
//
//			if(onLinOffPicOld!=null && StringUtils.isNotEmpty(onLinOffPicOld.getRemark()) 
//					&& onLinOffPicOld.getRemark().equals("Y")){//旧影像系统，九江在用
//				//AppDitDicConstant.pic_aicPicShowOld //aic系统旧影像
//				Map<String,String> picParam = cacheContext.getDicParam(AppDitDicConstant.pic_aicPicShowOld);
//				String casName=null;
//				String casPwd=null;
//				String createUser = OrganizationContextHolder.getUserNo();
//				if(picParam!=null){
//					if(picParam.containsKey("picUpload")){
//						picUploadUrl = picParam.get("picUpload");
//					}
//					if(picParam.containsKey("casName")){
//						casName = picParam.get("casName");
//					}
//					if(picParam.containsKey("casPwd")){
//						casPwd = picParam.get("casPwd");
//					}
//				}
//				picUploadUrl = picUploadUrl+"?appNo="+appNo+"&casName="+casName+"&casPassword="+casPwd+"&sysName="+"CAS"+"&createUser="+createUser;
//			}else{
//				logger.warn("该机构["+org+"]申请件["+appNo+"]未开启任何影像上传渠道");
//				throw new ProcessException("未开启任何影像上传渠道");
//			}
		} catch (Exception e) {
			logger.error("获取影像上传Url失败:",e);
			throw new ProcessException("获取影像上传Url失败["+e.getMessage()+"]");
		} finally{
			logger.info("获取影像上传Url" + AppConstant.END+"影像url["+picUploadUrl+"],appNo-"+appNo+"处理完成 - 耗时:"+(System.currentTimeMillis()-start));
		}

		return picUploadUrl;
	}

	/**
	 * 插入影像调阅记录表 
	 *
	 */
	@Transactional
	private void insertImageHistory(TmAppMain main){

		String org = OrganizationContextHolder.getOrg();
		String operator = OrganizationContextHolder.getUserNo();
		String appNo = main.getAppNo();

		try {
			TmAppImageHistory tmAppImageHistory = new TmAppImageHistory();
			tmAppImageHistory.setOrg(org);
			tmAppImageHistory.setAppNo(appNo);
			tmAppImageHistory.setImageNum(main.getImageNum());
			tmAppImageHistory.setTaskNum(main.getTaskNum());
			tmAppImageHistory.setOperatorId(operator);
			tmAppImageHistory.setOperateTime(new Date());
			tmAppImageHistory.setName(main.getName());
			tmAppImageHistory.setIdType(main.getIdType());
			tmAppImageHistory.setIdNo(main.getIdNo());
			applyEcssHistoryService.save(tmAppImageHistory);
		} catch (Exception e) {
			logger.error("插入影像调阅信息记录表表数据产生异常",e);
			throw new ProcessException("插入影像调阅信息记录表表数据产生异常,"+e.getMessage());
		}

	}

}
