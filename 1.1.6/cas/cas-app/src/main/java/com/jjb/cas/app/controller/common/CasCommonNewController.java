package com.jjb.cas.app.controller.common;

import com.jjb.acl.facility.enums.bus.EnumsActivitiNodeType;
import com.jjb.cas.app.controller.utils.CasExceptionPageUtils;
import com.jjb.ecms.biz.dao.node.TmAppNodeInfoDao;
import com.jjb.ecms.biz.ext.cmp.CmpSystemSupport;
import com.jjb.ecms.biz.ext.ecss.BankImagesSysInfo;
import com.jjb.ecms.biz.ext.push.CreditSysPushSupport;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.manage.ApplySpreaderInfoService;
import com.jjb.ecms.biz.util.BizAuditHistoryUtils;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.facility.nodeobject.ApplyNodeTelCheckBisicData;
import com.jjb.ecms.facility.nodeobject.ApplyTelInquiryRecordItem;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppSprePerBank;
import com.jjb.ecms.infrastructure.TmBizAudit;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 公共弹出框、按钮等处理
 *
 * @author hp
 */
@Controller
@RequestMapping("/cas_common")
public class CasCommonNewController extends BaseController {
    //    private static final String riskUrl = "http://localhost:8082/cmp-app/assets/cmp_/returnImageNum";
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BankImagesSysInfo bankImagesSysInfo;
    @Autowired
    private CasExceptionPageUtils exceptionPageUtils;
    @Autowired
    private ApplySpreaderInfoService applySpreaderInfoService;
    @Autowired
    private ApplyQueryService applyQueryService;
    @Autowired
    private CreditSysPushSupport creditSysPushSupport;
    @Autowired
    private CmpSystemSupport cmpSystemSupport;//内容管理平台支持类
    @Autowired
    private BizAuditHistoryUtils bizAuditHistoryUtils;
    @Autowired
    private TmAppNodeInfoDao nodeInforecordDao;

    /**
     *  获取cmp内容平台URl-进行影像调阅</br>
     *1.判断申请件是否存在影像批次号</br>
     *2.如果不存在影像批次号，则调用cmp系统获取一个影像批次号</br>
     *3.组装打开cmp内容平台的url地址返回
     *
     * @param appNo
     * @return
     */
    @ResponseBody
    @RequestMapping("/getCmpSysUrl")
    public Json openCmpSys(String appNo, String sysId) {
        Json json = Json.newSuccess();
        long start = System.currentTimeMillis();
        String showContentUrl = "";
        try {
            showContentUrl = cmpSystemSupport.getCmpSystemShowContentUrl(appNo, sysId, start);
        } catch (Exception e) {
            logger.error(LogPrintUtils.printAppNoLog(appNo, start, "getCmpSysUrl")+"影像调阅异常", e);
            json.setS(false);
            json.setMsg(e.getMessage());
            return json;
        }finally {
            logger.info("获取内容管理平台url结束，"+LogPrintUtils.printAppNoEndLog(appNo, start, "getCmpSysUrl")+",showContentUrl["+showContentUrl+"]");
        }
        json.setObj(showContentUrl);
        bizAuditHistoryUtils.saveAuditHistory(appNo,"打开影像调阅");//保存审计历史
        return json;
    }

    /**
     * wxl
     * 打开影像调阅窗口页面
     *
     * @param appNo
     * @return
     */
    @RequestMapping("/showImages")
    public String showImages(String appNo) {
        try {
            if (StringUtils.isNotBlank(appNo)) {
                LinkedHashMap<String, String> map = bankImagesSysInfo.getImgUrlList(appNo);
                setAttr("picUrlMap", map);
                bizAuditHistoryUtils.saveAuditHistory(appNo, "打开 旧-影像调阅");//保存审计历史
                return "/casTask/apply/commonDialog/applyImgEss.ftl";
            } else {
                throw new ProcessException("无效的申请件编号，请刷新后重试!");
            }
        } catch (Exception e) {
            logger.error("影像调阅异常", e);
            return exceptionPageUtils.doExcepiton("影像URL获取异常" + e.getMessage(), appNo);
        }
    }

    /**
     * 推广人分配列表
     */
    @ResponseBody
    @RequestMapping("/getSpreaderList")
    public Page<TmAppSprePerBank> getSpreaderList() {
        Page<TmAppSprePerBank> page = getPage(TmAppSprePerBank.class);
        String sprName = "";
        try {
            Query query = page.getQuery();
            sprName = (String) query.get("spreaderName");
            if (StringUtils.isNotEmpty(sprName)) {
//                throw new ProcessException("请输入有效的推广员工号");
                query.put("spreaderStatus", "1");
                page = applySpreaderInfoService.getPageForTranferUser(page);
            }
        } catch (Exception e) {
            logger.warn("查询推广人[" + sprName + "]异常" + e.getMessage());
            throw new ProcessException("请输入有效的推广员工号");
        }
        return page;

    }

    /**
     * 推送审批结论
     *
     * @param appNo
     * @return
     */
    @ResponseBody
    @RequestMapping("/creditSysPushProgress")
    public Json creditSysPushProgress(String appNo, String pushType) {
        Json json = Json.newSuccess();
        try {
            TmAppMain main = applyQueryService.getTmAppMainByAppNo(appNo);
            creditSysPushSupport.pushApplyProgress(appNo, pushType, main);
        } catch (Exception e) {
            json.setS(false);
            json.setMsg("推送失败," + e.getMessage());
        }
        return json;
    }


    /**
     * 查看人行报告的 审计历史信息保存
     * @param appNo 申请件编号
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveAuditHistory")
    public Json hasApplyInfo(String appNo){
        //这个是卡表中的appNo，需要查看申请表中是否有这个申请件编号
        Json j = Json.newSuccess();
        if(StringUtils.isNotEmpty(appNo)){
            try {
                bizAuditHistoryUtils.saveAuditHistory(appNo,"查看人行报告");
            } catch (Exception e) {
                j.setS(false);
                j.setMsg("查询该申请件信息出错，请确认该件状态是否正常！");
            }
        }else {
            j.setS(false);
            j.setMsg("未获取到该申请件信息！");
        }
        return j;
    }


    /**
     * 打开审计历史页面
     *
     * @param appNo
     * @return
     */
    @RequestMapping("/openAudit")
    public String openAudit(String appNo) {
        try {
            if (StringUtils.isNotBlank(appNo)) {
                List<TmBizAudit> auditHistoryList = bizAuditHistoryUtils.findAuditHistory(appNo);
                setAttr("auditHistoryList", auditHistoryList);
                return "/casTask/apply/common/casAuditHistoryPage.ftl";
            } else {
                throw new com.jjb.unicorn.facility.exception.ProcessException("无效的申请件编号，请刷新后重试!");
            }
        } catch (Exception e) {
            logger.error("打开审计历史页面异常", e);
            return exceptionPageUtils.doExcepiton("打开审计历史页面"+e.getMessage(),appNo);
        }
    }
    /**
     * 电调记录查询
     */
    @ResponseBody
    @RequestMapping("/getTelCheckRecordList")
    public Page<ApplyTelInquiryRecordItem> getTelCheckRecordList(String appNo) {
        Page<ApplyTelInquiryRecordItem> page = getPage(ApplyTelInquiryRecordItem.class);
        List<ApplyTelInquiryRecordItem> applyTelInquiryRecordDtoList = new ArrayList<ApplyTelInquiryRecordItem>();
        String appNos = appNo;
        try {
            if (StringUtils.isNotEmpty(appNos)) {
                // 获取节点信息
                //List<TmAppNodeInfo> nodeInfoList = nodeInforecordDao.getTmAppNodeInfoList(appNo,EnumsActivitiNodeType.A030.toString());
                List<Map<String, Serializable>> nodeMapList = applyQueryService.getNodeInfosByAppNo(appNo,EnumsActivitiNodeType.A030.toString());
                if(CollectionUtils.sizeGtZero(nodeMapList)){
                    for (Map<String, Serializable> map : nodeMapList){
                        if(map!=null && map.containsKey(AppConstant.APPLY_NODE_TEL_CHECK_BISIC_DATA)){
                            ApplyNodeTelCheckBisicData applyNodeTelCheckBisicData = (ApplyNodeTelCheckBisicData)map.get(AppConstant.APPLY_NODE_TEL_CHECK_BISIC_DATA);
                            if(applyNodeTelCheckBisicData!=null && CollectionUtils.sizeGtZero(applyNodeTelCheckBisicData.getTelInquiryRecordItemList())){
                                applyTelInquiryRecordDtoList.addAll(applyNodeTelCheckBisicData.getTelInquiryRecordItemList());
                            }
                        }
                    }
                }

                /*if(CollectionUtils.isNotEmpty(nodeInfoList)){
                    ApplyNodeTelCheckBisicData applyNodeTelCheckBisicData = new ApplyNodeTelCheckBisicData();
                    // 申请节点信息
                    for (TmAppNodeInfo tmAppNodeInforecord : nodeInfoList) {
                        // 电话调查信息
                            NodeObjectTemplate<ApplyNodeTelCheckBisicData> templete = new NodeObjectTemplate<ApplyNodeTelCheckBisicData>();
                            try {

                                XStream xStream = NodeObjectUtil.getXstream(templete);
                                NodeObjectTemplate<ApplyNodeTelCheckBisicData> objList = (NodeObjectTemplate<ApplyNodeTelCheckBisicData>)xStream.fromXML(tmAppNodeInforecord.getContent());
                                List<?> column = objList.getNodeObject();
                                applyNodeTelCheckBisicData=(ApplyNodeTelCheckBisicData)column.get(0);
                                List<ApplyTelInquiryRecordItem> telInquiryRecordItemList = applyNodeTelCheckBisicData.getTelInquiryRecordItemList();
                                applyTelInquiryRecordDtoList.add(telInquiryRecordItemList.get(0));
                            } catch (Exception e) {
                                String content = "";
                                if(tmAppNodeInforecord.getContent()!=null){
                                    content = StringUtils.stripNonValidCharacters(tmAppNodeInforecord.getContent());
                                }
                                logger.error("无法解析申请件["+appNo+"]的ApplyNodeTelCheckBisicData-Node-XML数据:["+content+"]");
                            }
                            continue;
                    }
                }*/
            }
        } catch (Exception e) {
            logger.warn("查询申请件编号[" + appNos + "]对应电调信息异常" + e.getMessage());
        }
        if(StringUtils.isNotEmpty(applyTelInquiryRecordDtoList)){
            page.setRows(applyTelInquiryRecordDtoList);
        }
        return page;

    }
}