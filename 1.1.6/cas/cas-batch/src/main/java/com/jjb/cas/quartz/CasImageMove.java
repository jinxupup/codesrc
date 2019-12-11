package com.jjb.cas.quartz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jjb.acl.facility.enums.bus.EnumsActivitiNodeType;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.node.TmAppNodeInfoDao;
import com.jjb.ecms.biz.ext.ecss.BankImagesSysInfo;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.manage.ApplyEcssHistoryService;
import com.jjb.ecms.biz.util.http.HttpClientUtil2;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.facility.nodeobject.ApplyNodePreCheckData;
import com.jjb.ecms.infrastructure.TmAppImageHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppNodeInfo;
import com.jjb.ecms.infrastructure.TmDitDic;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 影像转移
 *
 * @author wxl
 * @author wxl
 */
@Component
public class CasImageMove {
//    private static final String removeImage = "http://localhost:8080/cmp-app/assets/cmp_/move/moveImage";
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BankImagesSysInfo bankImagesSysInfo;
    @Autowired
    private ApplyInputService applyInputService;
    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private TmAppNodeInfoDao tmAppNodeInfoDao;
    @Autowired
    private ApplyQueryService applyQueryService;
    @Autowired
    private ApplyEcssHistoryService applyEcssHistoryService;
    /**
     * wxl
     * 影像转移
     *
     * @return
     */
    public void imageMove () {

        //循环WHERE rn>'0' AND rn<='10';
        Integer num = 0;
        while (num<=100) {
            Map<String, Object> map = new HashMap<>();
            map.put("rownum", 100);
            //查询全部得到appNo
            List<TmAppMain> tmAppMains = null;
            try {
//                tmAppMains = tmAppMainDao.select100(map);
                tmAppMains = applyInputService.select100(map);
            } catch (ProcessException e) {
                logger.error("分批查询TM_APP_MAIN失败 ！！！");
                throw new ProcessException("分批查询TM_APP_MAIN失败 ！！！");
            }
            TmAppNodeInfo tmAppNodeInfo;
            //创建影像批次号并存入main表
            for (TmAppMain tmAppMain : tmAppMains) {
                List<TmAppNodeInfo> tmAppNodeInfoList = tmAppNodeInfoDao.getTmAppNodeInfoList(tmAppMain.getAppNo());
                tmAppNodeInfo = new TmAppNodeInfo();
                tmAppNodeInfo.setAppNo(tmAppMain.getAppNo());
                tmAppNodeInfo.setResultCode("S");
                //得到影像地址<影像小分类,,,影像地址>大分类默认为图片;
                LinkedHashMap<String, String> imageMap = getImgUrlList(tmAppMain.getAppNo());
                if (imageMap.isEmpty()) {
                        //imageMap为空该批次号下没有影像信息记录查过不再查
                        tmAppNodeInfo.setResultCode("F");
                    for (TmAppNodeInfo appNodeInfo : tmAppNodeInfoList) {
                        tmAppNodeInfo.setId(appNodeInfo.getId());
                        tmAppNodeInfoDao.updateNotNullable(tmAppNodeInfo);
                    }
                    continue;
                }
                //调用影像系统得到影像批次号同时存入TM_APP_MAIN
                logger.error("准备调用CMP生成影像批次号");
                Map<String, Object> params = new HashMap<>();
                params.put("org", tmAppMain.getOrg());
                params.put("name", tmAppMain.getName());
                params.put("id_no", tmAppMain.getIdNo());
                params.put("id_type", tmAppMain.getIdType());
                params.put("update_user", tmAppMain.getUpdateUser());
//                params.put("image_num", tmAppMain.getImageNum());
//                params.put("sys_id", "CAS");
//                params.put("operator_id", userNo );
                //TODO 新加影像小分类和影像地址
                params.put("imageMap", imageMap);
                //TODO接口调用查询影像批次号 "http://localhost:8080/cmp-app/assets/cmp_/move/moveImage";
                Map<String, String> picParam = cacheContext.getInterNetAddrParam(AppDitDicConstant.ext_cmp_conf);
                String moveOldImgPath = "";
                if (picParam != null && picParam.containsKey(AppDitDicConstant.CMP_moveOldImgPath)) {
                    if (picParam.containsKey(AppDitDicConstant.CMP_moveOldImgPath)) {
                        moveOldImgPath = picParam.get(AppDitDicConstant.CMP_moveOldImgPath);
                    }
                } else {
                    throw new ProcessException("系统未查询到内容管理平台相关网络地址配置");
                }
                //返回批次号 result
                String result = HttpClientUtil2.getBatchNo(moveOldImgPath, params);
                if (JSON.parseObject(result).get("boo").equals("true")){
                    logger.error("http请求影像批次号成功");
                } else {
                    logger.error("http请求失败 ! ! ! ");
                    for (TmAppNodeInfo appNodeInfo : tmAppNodeInfoList) {
                    	if(EnumsActivitiNodeType.A085.name().equals(appNodeInfo.getInfoType())) {
                    		tmAppNodeInfo.setId(appNodeInfo.getId());
                    		tmAppNodeInfo.setMemo("moveFail");
                    		tmAppNodeInfo.setResultCode("F");
                    		tmAppNodeInfoDao.updateNotNullable(tmAppNodeInfo);
                    	}
                    }
                    continue;
                }
                try {
                    //TODO存入影像批次号到main表
                    tmAppMain.setImageNum((String) JSON.parseObject(result).get("imageNo"));
                    //imageMap有参数存入tmAppNodeInfo表成功字段;
                    for (TmAppNodeInfo appNodeInfo : tmAppNodeInfoList) {
                        tmAppNodeInfo.setId(appNodeInfo.getId());
                        if(EnumsActivitiNodeType.A085.name().equals(appNodeInfo.getInfoType()))
                        	tmAppNodeInfo.setMemo("moveSuccess");
                        tmAppNodeInfoDao.updateNotNullable(tmAppNodeInfo);
                    }
                } catch (ProcessException e) {
                    logger.error("接口调用 imageNo 未返回 ！！！" + e);
                }
                try {
                    applyInputService.updateTmAppMain(tmAppMain);
                } catch (ProcessException e) {
                    logger.error("影像批次号存入TM_APP_MAIN表失败 ！！！" + e);
                    continue;
                }
            }
            if (tmAppMains.size() != 100) {
                return ;
            }
            num = num + 100;
        }
    }

    public LinkedHashMap<String, String> getImgUrlList(String appNo){
        long start = System.currentTimeMillis();
        List<Map<String, Serializable>> nodeList = applyQueryService.getNodeInfosByAppNo(appNo, EnumsActivitiNodeType.A085.name());

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
