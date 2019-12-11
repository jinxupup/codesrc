package com.jjb.cas.app.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jjb.acl.facility.enums.bus.AppType;
import com.jjb.acl.facility.enums.bus.TelResult;
import com.jjb.acl.facility.enums.bus.TelType;
import com.jjb.cas.app.controller.utils.CasPagePathConstant;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.approve.TmExtRiskInputDao;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.common.CommonService;
import com.jjb.ecms.biz.service.commonDialog.AlreadyCardService;
import com.jjb.ecms.biz.service.commonDialog.ApplyHistoryService;
import com.jjb.ecms.biz.service.commonDialog.ApplyUpdateHistoryService;
import com.jjb.ecms.biz.service.manage.TmRiskListService;
import com.jjb.ecms.biz.service.node.ApplyInfoPreDtoService;
import com.jjb.ecms.biz.service.query.ApplyTaskTransferListService;
import com.jjb.ecms.biz.util.BizAuditHistoryUtils;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.dto.FaceCheckRecordDto;
import com.jjb.ecms.facility.dto.AlreadyCardsCardInfoDto;
import com.jjb.ecms.facility.dto.ApplyInfoPreDto;
import com.jjb.ecms.facility.dto.ApplyRiskInfoDto;
import com.jjb.ecms.facility.nodeobject.ApplyNodeTelCheckBisicData;
import com.jjb.ecms.facility.nodeobject.ApplyTelInquiryRecordItem;
import com.jjb.ecms.infrastructure.TmAppContact;
import com.jjb.ecms.infrastructure.TmAppCustInfo;
import com.jjb.ecms.infrastructure.TmAppFlag;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppModifyHis;
import com.jjb.ecms.infrastructure.TmDitDic;
import com.jjb.ecms.infrastructure.TmExtRiskInput;
import com.jjb.ecms.infrastructure.TmRiskList;
import com.jjb.ecms.infrastructure.TmTaskTransfer;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.util.Base64Util;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * @author -BigZ.Y
 * @description: 弹出框的通用controller
 * @date 2016年9月6日 下午7:04:33
 */
@Controller
@RequestMapping("/cas_commonDialog")
public class CasCommonController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApplyQueryService applyQueryService;

    @Autowired
    private AlreadyCardService alreadyCardService;

    @Autowired
    private ApplyHistoryService applyHistoryService;

    @Autowired
    private ApplyTaskTransferListService applyTaskTransferListService;

    @Autowired
    private ApplyUpdateHistoryService applyUpdateHistoryService;

    @Autowired
    private CacheContext cacheContext;

    @Autowired
    private ApplyInfoPreDtoService applyInfoPreDtoService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private TmExtRiskInputDao tmExtRiskInputDao;
    @Autowired
    private BizAuditHistoryUtils bizAuditHistoryUtils;

    @Autowired
    private TmRiskListService tmRiskListService;
    /*
     * 跳转已有卡窗口
     */
    @RequestMapping("/alreadyCardLayout")
    public String alreadyCardLayout() {

        String appNo = getPara("appNo");
        List<AlreadyCardsCardInfoDto> alreadyCardsCardInfoDtos = alreadyCardService.getAlreadyCardsCardInfoDtos(appNo);
        if (CollectionUtils.sizeGtZero(alreadyCardsCardInfoDtos)) {
            setAttr("applyName", alreadyCardsCardInfoDtos.get(0).getName());
            setAttr("applyIdNo", alreadyCardsCardInfoDtos.get(0).getIdNo());
        }
        setAttr("alreadyCardsCardInfoDtos", alreadyCardsCardInfoDtos);// 为页面赋值

        setAttr("appNo", appNo);
        return "apply/commonDialog/alreadyCard/alreadyCardLayout.ftl";
    }

    /*
     * 跳转审批历史窗口
     */
    @RequestMapping("/approveHistoryLayout")
    public String approveHistoryLayout() {
        String appNo = getPara("appNo");
        if (StringUtils.isEmpty(appNo)) {
            throw new ProcessException("查看审批历史信息时的appNo为空！");
        }
        // 获取审批历史
        List<TmAppHistory> tmAppHistoryList = applyHistoryService
                .getTmAppHistoryList(appNo);
        setAttr("tmAppHistoryList", tmAppHistoryList);
        bizAuditHistoryUtils.saveAuditHistory(appNo,"查看审批历史信息");//审计历史保存
        return "casTask/apply/commonDialog/approveHistory/approveHistoryLayout.ftl";
    }

    /**
    * @Author:shiminghong
    * @Description :  跳转生物识别记录窗口
    */
    @RequestMapping("/faceCheckRecordLayout")
    public String faceCheckRecordLayout(){
        String appNo=getPara("appNo");
        if (StringUtils.isBlank(appNo)){
            throw new ProcessException("查看生物识别时的appNo为空");
        }
        //获取生物识别的记录
        TmExtRiskInput tmExtRiskInput = tmExtRiskInputDao.getTmExtRiskInputByAppNo(appNo);
        //单行展示项
        FaceCheckRecordDto fcrd = new FaceCheckRecordDto();
        List<FaceCheckRecordDto> faceCheckRecordDtos = new ArrayList<FaceCheckRecordDto>();

        if (tmExtRiskInput!=null&& StringUtils.isNotBlank(tmExtRiskInput.getExtCheckIdFlag())){
        fcrd.setAppNo(tmExtRiskInput.getAppNo());
        fcrd.setCreateTime(tmExtRiskInput.getCreateTime());
        fcrd.setUpdateTime(tmExtRiskInput.getUpdateTime());
        if (StringUtils.equals(tmExtRiskInput.getExtCheckIdFlag(),"0")){
            fcrd.setFinalResult("失败");
        }else if (StringUtils.equals(tmExtRiskInput.getExtCheckIdFlag(),"1")){
            fcrd.setFinalResult("通过");
        }else if (StringUtils.equals(tmExtRiskInput.getExtCheckIdFlag(),"2")){
            fcrd.setFinalResult("未核验");
        }else if (StringUtils.equals(tmExtRiskInput.getExtCheckIdFlag(),"3")){
            fcrd.setFinalResult("异常跳过");
        }
            String extCheckIdRs=tmExtRiskInput.getExtCheckIdRs();
            //列表展示项
        if (tmExtRiskInput!=null&&StringUtils.isNotBlank(extCheckIdRs)){
                  JSONObject jsonObject= JSON.parseObject(extCheckIdRs);
                    JSONArray jsonArray = jsonObject.getJSONArray("AFR");
                    if (jsonArray!=null&&jsonArray.size()>0){
                        for (int i = 0 ;i<jsonArray.size();i++){
                            JSONObject json = jsonArray.getJSONObject(i);
                            if (StringUtils.isNotBlank(StringUtils.valueOf(json))){
                                FaceCheckRecordDto faceCheckRecordDto= new FaceCheckRecordDto();
                                faceCheckRecordDto.setCurrCnt(json.getString("currCnt"));
                                if (StringUtils.equals(json.getString("checkRs"),"F")){
                                    faceCheckRecordDto.setCheckRs("失败");
                                }else if (StringUtils.equals(json.getString("checkRs"),"S")){
                                    faceCheckRecordDto.setCheckRs("成功");
                                }
                                faceCheckRecordDto.setCheckRsDesc(json.getString("checkRsDesc"));
                                faceCheckRecordDtos.add(faceCheckRecordDto);
                            }
                        }
                    }
        }

        }
        setAttr("fcrd",fcrd);
        setAttr("faceCheckRecordDtos",faceCheckRecordDtos);

        bizAuditHistoryUtils.saveAuditHistory(appNo,"打开生物识别历史记录");//保存审计历史
        return "casTask/apply/commonDialog/faceCheckRecord/faceCheckRecordLayout.ftl";
    }

    /*
     * 跳转风控信息窗口
     */
    @RequestMapping("/creditRiskInfoLayout")
    public String creditRiskInfoLayout() {
        String appNo = getPara("appNo");
        if (StringUtils.isEmpty(appNo)) {
            throw new ProcessException("查看风控信息时的appNo为空！");
        }
        ApplyRiskInfoDto applyRiskInfoDto = new ApplyRiskInfoDto();
        ApplyInfoDto applyInfoDto = applyQueryService.getApplyInfoByAppNo(appNo);
        Map<String, Serializable> tmAppNodeInfoRecordMap = applyInfoDto.getTmAppNodeInfoRecordMap();//节点信息记录表
        commonService.setApplyRiskInfoDto(applyRiskInfoDto, tmAppNodeInfoRecordMap);
        setAttr("applyRiskInfoDto", applyRiskInfoDto);
        setAttr("appNo", appNo);
        //查询申请件标签
        List<TmAppFlag> tmAppFlagList = applyQueryService.getTmAppFlagListByAppNo(appNo);
        List<String> appFlagList = new ArrayList<String>();
        if (tmAppFlagList.size() != 0) {
            setAttr("tmAppFlagList", tmAppFlagList);
            for (TmAppFlag tmAppFlag : tmAppFlagList) {
                if (tmAppFlag != null) {
                    appFlagList.add(tmAppFlag.getFlagCode());
                }
            }
            setAttr("appFlagList", commonService.listToString(appFlagList, ","));
        }
        bizAuditHistoryUtils.saveAuditHistory(appNo,"打开决策信息");//保存审计历史
        return "casTask/apply/commonDialog/creditRiskInfo/creditRiskInfoLayout.ftl";
    }

    /*
     * 跳转案件流转记录窗口
     */
    @RequestMapping("/taskTransRecordLayout")
    public String taskTransRecordLayout() {
        String appNo = getPara("appNo");
        if (StringUtils.isEmpty("appNo")) {
            throw new ProcessException("查看案件流转记录时的appNo为空！");
        }
        //获取案件流转记录
        List<TmTaskTransfer> taskTransList = applyTaskTransferListService.getTransferTaskListByAppNo(appNo);
        setAttr("tmTaskTransferList", taskTransList);
        bizAuditHistoryUtils.saveAuditHistory(appNo,"打开案件流转记录");//保存审计历史记录
        return "casTask/apply/commonDialog/taskTransRecord/taskTransRecordLayout.ftl";
    }

    /*
     * 跳转修改历史窗口
     */
    @RequestMapping("/updateHistoryLayout")
    public String updateHistoryLayout() {
        String appNo = getPara("appNo");
        // 获取申请信息修改历史
        List<TmAppModifyHis> tmAppModifyHisList = applyUpdateHistoryService
                .getTmAppModifyHisList(appNo);
        setAttr("tmAppModifyHisList", tmAppModifyHisList);

        bizAuditHistoryUtils.saveAuditHistory(appNo, "查看修改历史信息");//保存审计历史
        return "casTask/apply/commonDialog/updateHistory/updateHistoryLayout.ftl";
    }

    /*
     * 电话调查记录
     */
    @RequestMapping("/telephoneSurveyLayout")
    public String telephoneSurveyLayout(String appNo, String phone) {

        // 拨打电话
        TmAppMain main = applyQueryService.getTmAppMainByAppNo(appNo);
        TmAppCustInfo primCust = applyQueryService.getTmAppPrimCustByAppNo(appNo);
        Map<String, TmAppCustInfo> attachCustMap = applyQueryService.getTmAppAttachCustInfoMapByAppNo(appNo);
        TmAppCustInfo attachCust;
        String cellphone = null;
        String homephone = null;
        String empPhone = null;
        String contactMobile = null;
        String otherContactMobile = null;
        String pbocMobile = null;
        String spreaderTelephone = null;

        Map<String, String> telphone = new HashMap<String, String>();
        String appType = main.getAppType();

        if (AppType.S.name().equals(appType) && attachCustMap.containsKey(AppConstant.bscSuppInd_S_1)) {
            attachCust = attachCustMap.get(AppConstant.bscSuppInd_S_1);
            cellphone = attachCust.getCellphone();
            homephone = attachCust.getHomePhone();
            empPhone = attachCust.getEmpPhone();
        } else {
            cellphone = primCust.getCellphone();
            homephone = primCust.getHomePhone();
            empPhone = primCust.getEmpPhone();
            Map<String, TmAppContact> tmAppContactInfoMap = applyQueryService.getTmAppContactByAppNo(appNo);// 联系人信息表
            if (!tmAppContactInfoMap.isEmpty() && tmAppContactInfoMap.size() > 0) {
                if (tmAppContactInfoMap.get(AppConstant.M_CON_ITEM_INFO_PREFIX + "1") != null) {
                    contactMobile = tmAppContactInfoMap.get(AppConstant.M_CON_ITEM_INFO_PREFIX + "1").getContactMobile();
                }
                if (tmAppContactInfoMap.get(AppConstant.M_CON_ITEM_INFO_PREFIX + "2") != null) {
                    otherContactMobile = tmAppContactInfoMap.get(AppConstant.M_CON_ITEM_INFO_PREFIX + "2").getContactMobile();
                }
            }
        }
        if (empPhone != null) {
            telphone.put("A", empPhone);
        } else {
            telphone.put("A", "");
        }
        if (cellphone != null) {
            telphone.put("B", cellphone);
        } else {
            telphone.put("B", "");
        }
        if (pbocMobile != null) {
            telphone.put("C", pbocMobile);
        } else {
            telphone.put("C", "");
        }
        telphone.put("D", "");
        if (homephone != null) {
            telphone.put("E", homephone);
        } else {
            telphone.put("E", "");
        }
        if (spreaderTelephone != null) {
            telphone.put("F", spreaderTelephone);
        } else {
            telphone.put("F", "");
        }
        if (contactMobile != null) {
            telphone.put("G", contactMobile);
        } else {
            telphone.put("G", "");
        }
        if (otherContactMobile != null) {
            telphone.put("H", otherContactMobile);
        } else {
            telphone.put("H", "");
        }

        // 人工节点---初审调查电话调查信息
        Map<String, Serializable> tmAppNodeInfoRecordMap = applyQueryService.getNodeInfoByAppNo(appNo).getTmAppNodeInfoRecordMap();
//		List<ApplyTelInquiryRecordItem> applyTelInquiryRecordDtoList1 = new ArrayList<ApplyTelInquiryRecordItem>();
        List<ApplyTelInquiryRecordItem> applyTelInquiryRecordDtoList = null;
        if (tmAppNodeInfoRecordMap.containsKey(AppConstant.APPLY_NODE_TEL_CHECK_BISIC_DATA)) {
            ApplyNodeTelCheckBisicData applyNodeTelCheckBisicData = (ApplyNodeTelCheckBisicData) tmAppNodeInfoRecordMap.get(AppConstant.APPLY_NODE_TEL_CHECK_BISIC_DATA);
            if (applyNodeTelCheckBisicData != null) {
                applyTelInquiryRecordDtoList = applyNodeTelCheckBisicData.getTelInquiryRecordItemList();
            }
        }

        setAttr("applyTelInquiryRecordDtoList", applyTelInquiryRecordDtoList);

        setAttr("appNo", appNo);
        setAttr("telMap", telphone);


        return "apply/commonDialog/telphoneSurvey/telphoneSurveyLayout.ftl";
    }

    /**
     * 上传人行征信报告页面
     */
    @RequestMapping("/uploadCreditReportLayout")
    public String uploadCreditReportLayout() {
        String appNo = getPara("appNo");
        setAttr("appNo", appNo);
        return "apply/commonDialog/creditReport/uploadCreditReportLayout.ftl";
    }

    /**
     * 电话调查结果信息
     */

    @RequestMapping("/telphoneCheckLayout")
    public String telphoneCheckLayout() {

        String appNo = getPara("appNo");

        // 获取所有节点信息
        ApplyInfoDto applyInfoDto = applyQueryService.getNodeInfoByAppNo(appNo);
        // 初审电话调查信息
        ApplyNodeTelCheckBisicData applyNodeTelCheckBisicData = (ApplyNodeTelCheckBisicData) applyInfoDto
                .getTmAppNodeInfoRecordMap().get(
                        AppConstant.APPLY_NODE_TEL_CHECK_BISIC_DATA);
        List<ApplyTelInquiryRecordItem> applyTelInquiryRecordDtoList = new ArrayList<ApplyTelInquiryRecordItem>();
        if (applyNodeTelCheckBisicData != null) {
            applyTelInquiryRecordDtoList = applyNodeTelCheckBisicData
                    .getTelInquiryRecordItemList();
        }
        if (applyTelInquiryRecordDtoList != null && applyTelInquiryRecordDtoList.size() > 0) {
            for (ApplyTelInquiryRecordItem enty : applyTelInquiryRecordDtoList) {
                if (StringUtils.isNotBlank(enty.getTelType()) && StringUtils.isNotBlank(enty.getTelResult())) {
                    //变历枚举类TelType
                    for (TelType telType : TelType.values()) {
                        //如果是汉字，即老数据
                        if (telType.lab.equals(enty.getTelType())) {
                            enty.setTelType(telType.name() + "-" + enty.getTelType());
                        } else if (telType.name().equals(enty.getTelType())) {//如果是英文，即新数据
                            enty.setTelType(enty.getTelType() + "-" + TelType.valueOf(enty.getTelType()).lab);
                        }
                    }
                    //遍历枚举类TelResult
                    for (TelResult telResult : TelResult.values()) {
                        //如果是汉字，即老数据
                        if (telResult.lab.equals(enty.getTelResult())) {
                            enty.setTelResult(telResult.name() + "-" + enty.getTelResult());
                        } else if (telResult.name().equals(enty.getTelResult())) {//如果是英文，即新数据
                            enty.setTelResult(enty.getTelResult() + "-" + TelResult.valueOf(enty.getTelResult()).lab);
                        }
                    }
                }
            }
        }
        setAttr("applyTelInquiryRecordDtoList", applyTelInquiryRecordDtoList);
        return cacheContext.getPageConfig(CasPagePathConstant.telphoneSurveyLayout);
    }


    /**
     * 历史申请
     */
    @RequestMapping("/applyHistoryLayout")
    public String applyHistoryLayout() {
        String appNo = getPara("appNo");
        String button = getPara("button");
        TmAppMain tmAppMain = applyQueryService.getTmAppMainByAppNo(appNo);
        String idType = null;
        String idNo = null;
        if (tmAppMain != null) {
            idType = tmAppMain.getIdType();
            idNo = tmAppMain.getIdNo();
        }
        if (StringUtils.isEmpty(idType) || StringUtils.isEmpty(idNo)) {
            throw new ProcessException("历史申请查看出现异常！appNo=[" + appNo + "],idType=" + idType + ",idNo=" + idNo);
        }
        ApplyInfoPreDto applyInfoPreDto = new ApplyInfoPreDto();
        applyInfoPreDto.setAppNo(appNo);
        applyInfoPreDto.setIdType(idType);
        applyInfoPreDto.setIdNo(idNo);
        List<ApplyInfoPreDto> applyInfoPreDtoList = applyInfoPreDtoService.getApplyInfoPreDtoList(applyInfoPreDto);
        setAttr("applyInfoPreDtoList", applyInfoPreDtoList);
        List<TmAppFlag> tmAppFlagList = applyQueryService.getTmAppFlagListByAppNo(appNo);
        if (tmAppFlagList.size() != 0) {
            setAttr("tmAppFlagList", tmAppFlagList);
        }
        if (button == null) {
            setAttr("buttonNotOpen", "buttonNotOpen");//控制申请件标签的打开
        }
        bizAuditHistoryUtils.saveAuditHistory(appNo, "查看历史申请信息");//审计历史保存
        return "casTask/apply/commonDialog/applyHistory/applyHistoryLayout.ftl";
    }

    /**
     * 调用征信，转发到不同查询方式的页面
     */
    @RequestMapping("/whichQueryMethod")
    public String whichQueryMethod(String localOrCis, String appNo) {
        setAttr("localOrCis", localOrCis);
        setAttr("appNo", appNo);
        return "apply/common/cis5OrgProductBut_V1.ftl";
    }

    /**
     * 影像上传
     *
     * @param appNo
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/uploadImage")
    public Json uploadImage(String appNo) throws IOException {
        Json json = Json.newSuccess();
        TmDitDic onLinOffPic = cacheContext.getApplyOnlineOnOff(AppDitDicConstant.onLinOff_isCallPic);//联机调用开关参数-是否开启调用影像系统
        if (onLinOffPic != null && StringUtils.isNotEmpty(onLinOffPic.getRemark()) && onLinOffPic.getRemark().equals("Y")) {
            String aicPicImageNoUrl = "";
            String aicPicUploadUrl = "";
            Map<String, String> picParam = cacheContext.getInterNetAddrParam(AppDitDicConstant.pic_aicPicShow);
            if (picParam != null) {
                if (picParam.containsKey("aicPicImageNoUrl")) {
                    aicPicImageNoUrl = picParam.get("aicPicImageNoUrl");
                }
                if (picParam.containsKey("aicPicUploadUrl")) {
                    aicPicUploadUrl = picParam.get("aicPicUploadUrl");
                }
            } else {
                json.setS(false);
                json.setMsg("aic影像功能未开启，请联系管理员");

                return json;
            }
        } else {
            json.setS(false);
            json.setMsg("aic影像查看功能开关未打开，请联系管理员");
        }
        return json;
    }

    /**
     * 打开影像调阅窗口页面
     *
     * @param picUrl
     * @return
     */
    @RequestMapping("/openImageUrlForEcss")
    public String openImageUrlForEcss(String picUrl) {
        if (StringUtils.isNotBlank(picUrl)) {
            picUrl = Base64Util.decodeToString(picUrl);
            logger.info("影像调阅的url为" + picUrl);
            setAttr("picUrl", picUrl);

            return "apply/commonDialog/applyImgEss.ftl";
        } else {
            throw new ProcessException("没有获取到影像调阅url");
        }
    }

    /**
     * 根据地区代码查询地区名称
     */
    @ResponseBody
    @RequestMapping("/selectAreaByCode")
    public Json selectAreaCode(String dicType, String areaCode) {
        Json json = Json.newSuccess();
        try {
            if (StringUtils.isEmpty(dicType) || StringUtils.isEmpty(areaCode)) {
                logger.error("获取地区代码清单异常,请求参数dicType[" + dicType + "],areaCode[" + areaCode + "]");
                json = Json.newFail();
                json.setS(false);
                json.setMsg("操作无效，请刷新页面重试!");
                return json;
            }
            if (areaCode.length() > 4) {
                areaCode = areaCode.substring(0, 4);
            }
            //获取TM_ACL_DICT中TYPE为AreaCode的记录，也即发证机关所在地，其中Map的key为地区名称,value为地区代码
            Map<Object,Object> aclDictParam = cacheContext.getAclDictByType(dicType);
            String sb = null;
            if (aclDictParam != null && aclDictParam.size() > 0) {
                sb = StringUtils.valueOf(aclDictParam.get(areaCode));
            } else {
                throw new ProcessException("未配置地区代码数据，请配置");
            }
            String str = areaCode;
            if (StringUtils.isNotEmpty(sb)) {
                str = str + "-" + sb;
            }
            json.setObj(str);
        } catch (Exception e) {
            json.setS(false);
            json.setMsg("获取发证机关所在地地区名称失败," + e.getMessage());
        }
        return json;
    }
}
