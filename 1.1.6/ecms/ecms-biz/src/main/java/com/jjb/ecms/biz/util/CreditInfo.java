package com.jjb.ecms.biz.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import lombok.extern.slf4j.Slf4j;


/**
* @Author:shiminghong
* @Description :    惠捷加密解密
*/
@Slf4j
public class CreditInfo {
    private static Logger logger = LoggerFactory.getLogger(CreditInfo.class);

    public String creditInfoTheParsing(String jsonString){
        log.info("征信需要反置换的信息json为："+jsonString);
        JSONObject dataObject = JSON.parseObject(jsonString);
        JSONObject creditInfo = new JSONObject();
        //头信息
        JSONObject applyInfo = dataObject.getJSONObject("APPLYINFO");
        if (null!=applyInfo &&!applyInfo.isEmpty()) {
            log.info("征信头信息json为："+applyInfo.toJSONString());
            JSONObject headInfo = new JSONObject();
            headInfo.put("id", getChineseStr(applyInfo.getString("cardID"),'C'));
            headInfo.put("idtype", applyInfo.getString("applyType"));
            headInfo.put("name", applyInfo.getString("applyName"));
            headInfo.put("reportDate", applyInfo.getString("applyDate"));
            headInfo.put("reportId", applyInfo.getString("applyID"));
            creditInfo.put("HEADINFO", headInfo);
        }
        //BASICINFO信息 
        JSONObject approvalPoliciesInfo13 = dataObject.getJSONObject("ApprovalPoliciesInfo13");
        if (null != approvalPoliciesInfo13 &&!approvalPoliciesInfo13.isEmpty()){
            log.info("征信BASICINFO信息json为："+approvalPoliciesInfo13.toJSONString());
            Map<String,Object> basicInfo = inverseSubstitutionBasicInfo(approvalPoliciesInfo13.toJSONString());
            String sex = (String)basicInfo.get("sex");
            if(!sex.equals("--")){
            	 creditInfo.put("BASICINFO",basicInfo);
            }         
        }
        //SUMMINFO 信息处理 
        JSONObject ApprovalPoliciesInfo1 = dataObject.getJSONObject("ApprovalPoliciesInfo1");
        if (null!=ApprovalPoliciesInfo1&&!ApprovalPoliciesInfo1.isEmpty()){
            log.info("征信SUMMINFO信息json为："+ApprovalPoliciesInfo1.toJSONString());
            JSONObject summInfo = getSummInfo(ApprovalPoliciesInfo1);
            creditInfo.put("SUMMINFO",summInfo);//add by 
        }
        //SUMMINFO 信息处理  end
        //QUERYINFO信息 
        Map<String, Object> stringObjectMap = inverseSubstitutionQueryInfo(jsonString);
        creditInfo.put("QUERYINFO",stringObjectMap);
        //LOAN信息 
        //2018.11.24.
        JSONObject approvalPoliciesInfo3 = dataObject.getJSONObject("ApprovalPoliciesInfo3");//loan

        if (null!=approvalPoliciesInfo3 &&!approvalPoliciesInfo3.isEmpty()){
            log.info("征信LOAN信息json为："+approvalPoliciesInfo3.toJSONString());
            JSONObject loanInfo = getLoanInfo(approvalPoliciesInfo3);
            creditInfo.put("LOAN",loanInfo);//add by 
        }
        //LOAN信息  end
        //CARD1信息 
        JSONObject approvalPoliciesInfo4 = dataObject.getJSONObject("ApprovalPoliciesInfo4");
        if (null!=approvalPoliciesInfo4 &&!approvalPoliciesInfo4.isEmpty()){
            log.info("征信CARD1信息json为："+approvalPoliciesInfo4.toJSONString());
            JSONObject card1 = getCard1Info(approvalPoliciesInfo4);
            creditInfo.put("CARD1",card1);
        }
        //CARD2信息 xj
        Map<String,Object> resultMap = getCard2(dataObject.getJSONObject("ApprovalPoliciesInfo12"));
        if(!resultMap.isEmpty()){
        creditInfo.put("CARD2",resultMap);
        }
        //PUBLICINFO信息 xj
        Map<String,Object> resultMap2 = getPublicInfo(dataObject.getJSONObject("ApprovalPoliciesInfo5"));
        creditInfo.put("PUBLICINFO",resultMap2);
        //GUALOAN信息 xj
        JSONObject approvalPoliciesInfo6 = dataObject.getJSONObject("ApprovalPoliciesInfo6");

        if (null!=approvalPoliciesInfo6&&!approvalPoliciesInfo6.isEmpty()){
            log.info("征信GUALOAN信息json为："+approvalPoliciesInfo6.toJSONString());
            Map<String,Object> guaLoan = getGualoan(approvalPoliciesInfo6);
            creditInfo.put("GUALOAN",guaLoan);
        }
        //GUARANINFO信息 
        JSONObject approvalPoliciesInfo7 = dataObject.getJSONObject("ApprovalPoliciesInfo7");
        if (null!=approvalPoliciesInfo7&&!approvalPoliciesInfo7.isEmpty()){
            log.info("征信GUARANINFO信息json为："+approvalPoliciesInfo7.toJSONString());
            JSONObject guaranInfo = getGuaranInfo(approvalPoliciesInfo7);
            creditInfo.put("GUARANINFO",guaranInfo);
        }
        //ANNOUNCEINFO信息 
        JSONObject approvalPoliciesInfo8 = dataObject.getJSONObject("ApprovalPoliciesInfo8");
        if (null!=approvalPoliciesInfo8&&!approvalPoliciesInfo8.isEmpty()){
            log.info("征信ANNOUNCEINFO信息json为："+approvalPoliciesInfo8.toJSONString());
            JSONObject announceInfo = getDissentOrAnnounceInfo(approvalPoliciesInfo8,1);
            creditInfo.put("ANNOUNCEINFO",announceInfo);
        }
        //DISSENTINFO信息 
        JSONObject approvalPoliciesInfo9 = dataObject.getJSONObject("ApprovalPoliciesInfo9");
        if (null!=approvalPoliciesInfo9&&!approvalPoliciesInfo9.isEmpty()){
            log.info("征信DISSENTINFO信息json为："+approvalPoliciesInfo9.toJSONString());
            JSONObject dissentInfo = getDissentOrAnnounceInfo(approvalPoliciesInfo9,2);
            creditInfo.put("DISSENTINFO",dissentInfo);
        }
        //DEFAULT信息 
        JSONObject approvalPoliciesInfo10 = dataObject.getJSONObject("ApprovalPoliciesInfo10");

        if (null!=approvalPoliciesInfo10&&!approvalPoliciesInfo10.isEmpty()){
            log.info("征信DEFAULT信息json为："+approvalPoliciesInfo10.toJSONString());
            JSONObject defaultInfo = getDefaultInfo(approvalPoliciesInfo10);
            creditInfo.put("DEFAULT",defaultInfo);
        }
        //ASSETDISPOSITON信息 
        JSONObject approvalPoliciesInfo11 = dataObject.getJSONObject("ApprovalPoliciesInfo11");
        if (null!=approvalPoliciesInfo11&&!approvalPoliciesInfo11.isEmpty()){
            log.info("征信ASSETDISPOSITON信息json为："+approvalPoliciesInfo11.toJSONString());
            JSONObject assetDispositon = getAssetDispositon(approvalPoliciesInfo11);
            creditInfo.put("ASSETDISPOSITON",assetDispositon);
        }
        return JSON.toJSONString(creditInfo, SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullNumberAsZero,SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteNullListAsEmpty);
    }
    /**
     * 解析CARD1信息
     * @author 
     * @date 2018/11/28
     */
    private JSONObject getCard1Info(JSONObject json){
        JSONObject card1 = new JSONObject();
        JSONArray policy1 = json.getJSONArray("Policy1");
        if (!policy1.isEmpty()) {
            JSONArray card1BeanList = new JSONArray();
            for (Object obj : policy1) {
                String card1Bean = (String) obj;
                String[] strings = card1Bean.split("\\|");
                JSONObject card1BeanJson = new JSONObject();
                card1BeanJson.put("card1Id", strings[0].isEmpty() ? strings[0] : strings[0].substring(1));
                card1BeanJson.put("card1Date", getInfo4(strings[1]));
                card1BeanJson.put("card1Currency", strings[2].equals("A1") ? "人民币" : "其他");
                card1BeanJson.put("card1Limit", getInfo4(strings[3]));
                card1BeanJson.put("card1Status", getCard1Status(strings[4]));
                card1BeanJson.put("card1Closedate",ren(getInfo4(strings[5])));
                card1BeanJson.put("card1Balance", getInfo4(strings[6]));
                card1BeanJson.put("card1RepayRcord", getLoanBlance(strings[7]));
                card1BeanJson.put("card1M6avgBalance", getInfo4(strings[8]));
                card1BeanJson.put("card1MaxBalance", getInfo4(strings[9]));
                card1BeanJson.put("card1CurdefaultNum", getInfo4(strings[10]));
                card1BeanJson.put("card1CurdefaultAmt", getInfo4(strings[11]));
                card1BeanJson.put("card1CurrentBalance", getInfo4(strings[12]));
                card1BeanJson.put("card1BillingDate", formatDateNumber(strings[13],'D'));
                card1BeanJson.put("card1ActualPayAmount", getInfo4(strings[14]));
                card1BeanJson.put("card1RecentPayDate", formatDateNumber(strings[15],'D'));
                card1BeanJson.put("card1OrgCode", getCard1OrgCode(strings[16]));
                card1BeanJson.put("repayDisc", castToDateRange(strings[17],'C'));
                card1BeanJson.put("card1ShareLimit", getInfo4(strings[18]));
                card1BeanJson.put("card1BussinessNo", getChineseStr(strings[19],'C'));
                card1BeanJson.put("card1AccountDate", getChineseStr(strings[20],'C'));
                card1BeanJson.put("card1ProductType", "贷记卡");
                card1BeanJson.put("card1assureType", getChineseStr(strings[22],'C'));
                card1BeanJson.put("card1OrgType", getChineseStr(strings[23],'C'));
                card1BeanJson.put("specialTradeList", getCard1Info1(strings[0].substring(1),json));
                card1BeanJson.put("allRecord", getCard1Info2(strings[0].substring(1),json));
                card1BeanJson.put("illuminateInfo", getIlluminateInfo(strings[0].substring(1),json));
                card1BeanList.add(card1BeanJson);
            }
            card1.put("card1BeanList",card1BeanList);

        }
        return card1;
    }
    /**
     * 解析CARD1信息中specialTradeList
     * @author 
     * @date 2018/11/29
     */
    private JSONArray getCard1Info1(String key,JSONObject json){
        JSONArray specialTradeList = new JSONArray();
        JSONArray policy2 = json.getJSONArray("Policy2");
        if (!policy2.isEmpty()) {
            for (Object obj : policy2) {
//                JSONArray specialTradeList1 = new JSONArray();
                JSONArray array = (JSONArray) obj;
                for (Object obj1 : array) {
                    String bean = (String) obj1;
                    String[] strings = bean.split("\\|");
                    JSONObject beanInfo = new JSONObject();
                    if(key == null || !key.equals(strings[0].substring(1))){
//                        return new JSONArray();
                        continue;
                    } else {
                        beanInfo.put("loanId", strings[0].isEmpty() ? strings[0] : strings[0].substring(1));
                        beanInfo.put("specialTrade",getSpecialTrade(strings[1]));
                        beanInfo.put("specialCreateDate",formatDateNumber(strings[2],'C'));
                        beanInfo.put("specialModifyMonths",getSpecialModifyMonthsRR(strings[3]));
                        beanInfo.put("specialEventMoney",getInt(strings[4],'C'));
                        beanInfo.put("tradeDetail",getChineseStr(strings[5], 'C'));
    //                    specialTradeList1.add(beanInfo);
                        specialTradeList.add(beanInfo);
                    }
                }
            }
        }
        return specialTradeList;
    }
    /**
     * 解析LOAN、CARD1、CARD1信息中allRecord
     * @author 
     * @date 2018/11/29
     */
    private JSONArray getCard1Info2(String key,JSONObject json){
        JSONArray specialTradeList = new JSONArray();
        JSONArray policy3 = json.getJSONArray("Policy3");
        if (!policy3.isEmpty()) {
            for (Object obj : policy3) {
                JSONArray array = (JSONArray) obj;
                for (Object obj1 : array) {
                    String bean = (String) obj1;
                    String[] strings = bean.split("\\|");
                    JSONObject beanInfo = new JSONObject();
                    if(key == null || !key.equals(strings[0].substring(1))){
                      continue;
                    }else {
                        beanInfo.put("loanId", strings[0].isEmpty() ? strings[0] : strings[0].substring(1));
                        beanInfo.put("defaultDate", formatDateNumber(strings[1], 'D'));
                        beanInfo.put("keepMonth", getStr2Int(strings[2], 'C'));
                        beanInfo.put("defaultAmt", getStr2Int(strings[3], 'C'));
                        specialTradeList.add(beanInfo);
                    }
                }
            }
        }
        return specialTradeList;
    }

    /**
     * 解析LOAN、CARD1、CARD2信息中IlluminateInfo
     * @param key
     * @param json
     * @return
     */
    private JSONObject getIlluminateInfo(String key,JSONObject json){
        JSONObject beanInfo = new JSONObject();
        JSONArray policy4 = json.getJSONArray("Policy4");
        if (!policy4.isEmpty()) {
            for (Object obj : policy4) {
                String bean = (String) obj;
                String[] strings = bean.split("\\|");
                if(key == null || !key.equals(strings[0].substring(1))){
                    continue;
                }else {
                    beanInfo.put("loanId", strings[0].isEmpty() ? strings[0] : strings[0].substring(1));
                    beanInfo.put("orgIlluminate", getChineseStr(strings[1], 'C'));
                    beanInfo.put("orgIlluminateDate", formatDateNumber(strings[2], 'C'));
                    beanInfo.put("selfAnnounce", getChineseStr(strings[3], 'C'));
                    beanInfo.put("selfAnnounceDate", formatDateNumber(strings[4], 'C'));
                    beanInfo.put("dissentInfo", getChineseStr(strings[5], 'C'));
                    beanInfo.put("dissentDate", formatDateNumber(strings[6], 'C'));
                }
            }
        }
        return beanInfo;
    }



    /**
     * 解析GUARANINFO信息
     * @author 
     * @date 2018/11/28
     */
    private JSONObject getGuaranInfo(JSONObject json){
        JSONObject guaranInfo = new JSONObject();
        JSONArray policy1 = json.getJSONArray("Policy1");
        JSONArray guaranInfoList = new JSONArray();
        if (!policy1.isEmpty()) {
            for (Object obj : policy1) {
                String guaranInfoArray = (String) obj;
                String[] strings = guaranInfoArray.split("\\|");
                JSONObject guaranInfoBean = new JSONObject();
                guaranInfoBean.put("guaranId", strings[0].isEmpty() ? strings[0] : strings[0].substring(1));
                guaranInfoBean.put("guaranOrg", getChineseStr(strings[1],'C'));
                guaranInfoBean.put("lastGuaranDate", getStr2Int(strings[2],'C'));
                guaranInfoBean.put("countGuaranAmt", getStr2Int(strings[3],'C'));
                guaranInfoBean.put("lastGuaranPayDate", formatDateNumber(strings[4],'D'));
                guaranInfoBean.put("guaranBal", getStr2Int(strings[5],'C'));
                guaranInfoList.add(guaranInfoBean);
            }
            guaranInfo.put("guaranInfoList",guaranInfoList);
        }
        return guaranInfo;
    }
    /**
     * 解析ANNOUNCEINFO信息 或 DISSENTINFO信息
     * @author 
     * @date 2018/11/28
     */
    private JSONObject getDissentOrAnnounceInfo(JSONObject json,int flag){
        JSONObject resultInfo = new JSONObject();
        JSONArray policy1 = json.getJSONArray("Policy1");
        JSONArray infoList = new JSONArray();
        if (!policy1.isEmpty()) {
            for (Object obj : policy1) {
                String array = (String) obj;
                String[] strings = array.split("\\|");
                JSONObject bean = new JSONObject();
                bean.put("num", strings[0].isEmpty() ? strings[0] : strings[0].substring(1));
                bean.put("content", getChineseStr(strings[1],'C'));
                bean.put("getTime", formatDateNumber(strings[2],'D'));
                infoList.add(bean);
            }
            if (flag==1){
                resultInfo.put("announceInfoList",infoList);
            } else if (flag==2){
                resultInfo.put("dissentInfoList",infoList);
            }

        }
        return resultInfo;
    }
    //******************xj start********************//
    private Map<String,Object> getCard2(JSONObject object){
        Map<String,Object> resultMap = new HashMap<>();
        if(null!=object&&object.size()>0){
            JSONArray array  = object.getJSONArray("Policy1");
            if(array!= null && array.size()>0){
                List<Map<String,Object>> list = new ArrayList<>();
                for(int i=0;i<array.size();i++){
                    Map<String,Object> map = new HashMap<>();
                    String getStr = (String)array.get(i);
                    String[] getArr = getStr.split("\\|");
                    map.put("card2Id",separateString(getArr[0],"F"));
                    //gua
                    map.put("card2Date",getInt(getArr[1],'D'));
                    map.put("card2Currency",getMoneyCurrency(getArr[2]));
                    map.put("card2Limit",getInt(getArr[3],'D'));
                    map.put("card2Status",getCard1Status(getArr[4]));
                    map.put("card2Closedate",ren(getInt(getArr[5],'D')) );
                    map.put("card2Balance",getInt(getArr[6],'D'));
                    map.put("card2RepayRcord",getLoanBlance(getArr[7]));
                    map.put("card2MaxBalance",getInt(getArr[8],'D'));
                    map.put("card2Above180",getInt(getArr[9],'D'));
                    map.put("card2PayDate",formatDateNumber(getArr[10],'D'));
                    map.put("card2M6avgBalance",getInt(getArr[11],'D'));
                    map.put("card2ActualPayAmount",getInt(getArr[12],'D'));
                    map.put("card2RecentPayDate",formatDateNumber(getArr[13],'D'));
                    map.put("card2BussinessNo",getChineseStr(getArr[14],'C'));
                    map.put("card2AccountDate",getChineseStr(getArr[15],'C'));
                    map.put("card2ProductType","准贷记卡");
                    map.put("card2assureType",getChineseStr(getArr[17],'C'));
                    map.put("card2OrgType",getChineseStr(getArr[18],'C'));
                    map.put("card2OrgCode",getChineseStr(getArr[19],'C'));
                    JSONArray specialTradeList = new JSONArray();
                    map.put("specialTradeList", new JSONArray());
                    JSONArray array2  = object.getJSONArray("Policy2");
                    if(array2!= null && array2.size()>0){
                        for(int j=0;j<array2.size();j++){
                            JSONArray obj = array2.getJSONArray(j);
                            for (Object obj2:obj) {
                                Map<String,Object> map2 = new HashMap<>();
                                String getStr2 = (String) obj2;
                                String[] getArr2 = getStr2.split("\\|");
                                if(separateString(getArr2[0],"S").equals(separateString(getArr[0],"F"))){
                                    map2.put("specialTrade",getSpecialTrade(getArr2[1]));
                                    map2.put("specialCreateDate",formatDateNumber(getArr2[2],'C'));
                                    map2.put("specialModifyMonths",getSpecialModifyMonthsRR(getArr2[3]));
                                    map2.put("specialEventMoney",getInt(getArr2[4],'C'));
                                    map2.put("tradeDetail",getChineseStr(getArr2[5], 'C'));
                                    specialTradeList.add(map2);
                                    map.put("specialTradeList", specialTradeList);
                                }
                            }
                        }

                    }
                    map.put("allRecord", getCard1Info2(getArr[0].substring(1),object));
                    map.put("illuminateInfo", getIlluminateInfo(getArr[0].substring(1),object));
                    list.add(map);
                    resultMap.put("card2BeanList",list);

                }
            } else {
                resultMap.put("card2BeanList",null);
            }
        }
        return resultMap;
    }
    private Map<String,Object> getPublicInfo(JSONObject object){
        Map<String,Object> resultMap = new HashMap<>();
        if(null!=object&&object.size()>0){
            //housingFund
            JSONArray array  = object.getJSONArray("Policy1");
            if(array!= null && array.size()>0){
                List<Map<String,Object>> list = new ArrayList<>();
                for(int i=0;i<array.size();i++){
                    Map<String,Object> map = new HashMap<>();
                    String getStr = (String)array.get(i);
                    String[] getArr = getStr.split("\\|");
                    map.put("payId", separateString(getArr[0], "P"));
                    map.put("payAddr", getChineseStr(getArr[1], 'C'));
                    map.put("payDate", formatDateNumber(getArr[2], 'D'));
                    map.put("firstPayM", formatDateNumber(getArr[3], 'D'));
                    map.put("lastPayM", formatDateNumber(getArr[4], 'D'));
                    map.put("payStatus", getHousingPayStatusD(getArr[5]));
                    map.put("payAmountM", getInt(getArr[6], 'D'));
                    map.put("selfPayScale", getPercent('D', getArr[7]));
                    map.put("unitPayScale", getPercent('D', getArr[8]));
                    list.add(map);
                }
                resultMap.put("housingFund",list);
            }else{
                resultMap.put("housingFund",array );
            }
            //housingFundUpdate
            List<Map<String,Object>> list2 = new ArrayList<>();
            JSONArray array2  = object.getJSONArray("Policy2");
            if(array2!= null && array2.size()>0){
                for(int i=0;i<array2.size();i++){
                    Map<String,Object> map = new HashMap<>();
                    String getStr = (String)array2.get(i);
                    String[] getArr = getStr.split("\\|");
                    map.put("updateId", separateString(getArr[0], "V"));
                    map.put("unitName", getChineseStr(getArr[1], 'C'));
                    map.put("updateTime", formatDateNumber(getArr[2], 'D'));
                    list2.add(map);
                }
                resultMap.put("housingFundUpdate",list2);
            }else{
                resultMap.put("housingFundUpdate",array2);
            }
            //endowmentInsurance
            List<Map<String,Object>> list3 = new ArrayList<>();
            JSONArray array3  = object.getJSONArray("Policy3");
            if(array3!= null && array3.size()>0){
                for(int i=0;i<array3.size();i++){
                    Map<String,Object> map = new HashMap<>();
                    String getStr = (String)array3.get(i);
                    String[] getArr = getStr.split("\\|");
                    map.put("payId", separateString(getArr[0], "P"));
                    map.put("payAddr", getChineseStr(getArr[1], 'C'));
                    map.put("payDate", formatDateNumber(getArr[2], 'D'));
                    map.put("payCountM", getInt(getArr[3], 'D'));
                    map.put("workCountM", formatDateNumber(getArr[4], 'D'));
                    map.put("payStatus", getEndowmentStatus(getArr[5]));
                    map.put("selfAmountM", getInt(getArr[6], 'D'));
                    map.put("payAmountM", getInt(getArr[7], 'D'));
                    map.put("updateTime", formatDateNumber(getArr[8], 'D'));
                    list3.add(map);
                }
                resultMap.put("endowmentInsurance",list3);
            }else{
                resultMap.put("endowmentInsurance",array3);
            }

            //endowmentInsInterruption
            List<Map<String,Object>> list4 = new ArrayList<>();
            JSONArray array4  = object.getJSONArray("Policy4");
            if(array4!= null && array4.size()>0){
                for(int i=0;i<array4.size();i++){
                    Map<String,Object> map = new HashMap<>();
                    String getStr = (String)array4.get(i);
                    String[] getArr = getStr.split("\\|");
                    map.put("interId", separateString(getArr[0], "P"));
                    map.put("unitName", getChineseStr(getArr[1], 'C'));
                    map.put("interReason", getInterReason(getArr[2]));
                    list4.add(map);
                }
                resultMap.put("endowmentInsInterruption",list4);
            }
            else{
                resultMap.put("endowmentInsInterruption",array4);
            }

            //enforcementRecord
            List<Map<String,Object>> list5 = new ArrayList<>();
            JSONArray array5 = object.getJSONArray("Policy5");
            if (array5!= null && array5.size() > 0) {
                for (int i = 0; i < array5.size(); i++) {
                    Map<String,Object> map = new HashMap<>();
                    String getStr = (String)array5.get(i);
                    String[] getArr = getStr.split("\\|");
                    map.put("recordId", separateString(getArr[0], "D"));
                    map.put("executiveCourt", getChineseStr(getArr[1], 'C'));
                    map.put("executiveCase", "--");
                    map.put("caseDate", formatDateNumber(getArr[3], 'D'));
                    map.put("closedManner", getClosedManner(getArr[4]));
                    list5.add(map);
                }
                resultMap.put("enforcementRecord", list5);
            }else{
                resultMap.put("enforcementRecord",array5);
            }

            //enforcementCaseRecord
            List<Map<String,Object>> list6 = new ArrayList<>();
            JSONArray array6 = object.getJSONArray("Policy6");
            if (array6!= null && array6.size() > 0) {
                for (int i = 0; i < array6.size(); i++) {
                    Map<String,Object> map = new HashMap<>();
                    String getStr = (String)array6.get(i);
                    String[] getArr = getStr.split("\\|");
                    map.put("recordId", separateString(getArr[0], "E"));
                    map.put("status", getChineseStr(getArr[1], 'C'));
                    map.put("closedDate", formatDateNumber(getArr[2], 'D'));
                    map.put("applyObject", getChineseStr(getArr[3], 'C'));
                    map.put("applyObjectValue",getStr2Int(getArr[4] , 'D'));
                    map.put("executedTarget", getChineseStr(getArr[5], 'C'));
                    map.put("executedTargetM", getStr2Int(getArr[6], 'D'));
                    list6.add(map);
                }
                resultMap.put("enforcementCaseRecord", list6);
            }else{
                resultMap.put("enforcementCaseRecord",array6);
            }
            //competence
            List<Map<String,Object>> list7 = new ArrayList<>();
            JSONArray array7 = object.getJSONArray("Policy7");
            if (array7!= null && array7.size() > 0) {
                for (int i = 0; i < array7.size(); i++) {

                    Map<String,Object> map = new HashMap<>();
                    String getStr = (String)array7.get(i);
                    String[] getArr = getStr.split("\\|");
                    map.put("num", separateString(getArr[0], "A"));
                    map.put("competencyName", getChineseStr(getArr[1], 'C'));
                    map.put("grade", getGrade(getArr[2]));
                    map.put("awardDate", formatDateNumber(getArr[3], 'D'));
                    map.put("endDate", formatDateNumber(getArr[4], 'D'));
                    map.put("revokeDate", formatDateNumber(getArr[5], 'D'));
                    map.put("organName", getChineseStr(getArr[6], 'C'));
                    map.put("area", getChineseStr(getArr[7], 'C'));
                    list7.add(map);
                }
                resultMap.put("competence", list7);
            }else{
                resultMap.put("competence",array7);
            }

            //adminAward
            List<Map<String,Object>> list8 = new ArrayList<>();
            JSONArray array8 = object.getJSONArray("Policy8");
            if (array8!= null && array8.size() > 0) {
                for (int i = 0; i < array8.size(); i++) {

                    Map<String,Object> map = new HashMap<>();
                    String getStr = (String)array8.get(i);
                    String[] getArr = getStr.split("\\|");
                    map.put("num", separateString(getArr[0], "C"));
                    map.put("organName", getChineseStr(getArr[1], 'C'));
                    map.put("content", getChineseStr(getArr[2],'C'));
                    map.put("beginDate", formatDateNumber(getArr[3], 'D'));
                    map.put("endDate", formatDateNumber(getArr[4], 'D'));
                    list8.add(map);
                }
                resultMap.put("adminAward", list8);
            }else{
                resultMap.put("adminAward",array8);
            }
            //vehicle
            List<Map<String,Object>> list9 = new ArrayList<>();
            JSONArray array9 = object.getJSONArray("Policy9");
            if (array9!= null && array9.size() > 0) {
                for (int i = 0; i < array9.size(); i++) {

                    Map<String,Object> map = new HashMap<>();
                    String getStr = (String)array9.get(i);
                    String[] getArr = getStr.split("\\|");
                    map.put("num", separateString(getArr[0], "D"));
                    map.put("licenseCode", getChineseStr(getArr[1], 'C'));
                    map.put("engineCode", getChineseStr(getArr[2],'C'));
                    map.put("brand", getChineseStr(getArr[3], 'C'));
                    map.put("carType", getChineseStr(getArr[4], 'C'));
//                    map.put("useCharacter", getUseCharacter(getArr[5]));
                    map.put("useCharacter", getChineseStr(getArr[5], 'C'));
                    map.put("state", getState(getArr[6]));
                    map.put("pledgeFlag", getPledgeFlag(getArr[7]));
                    map.put("getTime", formatDateNumber(getArr[8], 'D'));
                    list9.add(map);
                }
                resultMap.put("vehicle", list9);
            }else{
                resultMap.put("vehicle",array9);
            }
            //add by 
            JSONArray policy10 = object.getJSONArray("Policy10");
            if(null!= policy10 &&!policy10.isEmpty()){
                JSONArray policy10List = new JSONArray();
                String policy10Str = policy10.toJSONString();
                log.info("接收到Policy10 JSON："+policy10Str);
                for (Object obj : policy10) {
                    String str = (String) obj;
                    String[] policy10Arr = str.split("\\|");
                    int len = policy10Arr.length -1;
                    JSONObject telPayment = new JSONObject();
                    telPayment.put("num", (0>len)? "" : getNum(policy10Arr[0] , "E"));
                    telPayment.put("organName", (1>len)? "" : getChineseStr(policy10Arr[1] , 'C'));
                    telPayment.put("type", (2>len)? "" : getChineseStr(policy10Arr[2] , 'C'));
                    telPayment.put("registerDate", (3>len)? "" : formatDateNumber(policy10Arr[3] , 'D'));
                    telPayment.put("state", (4>len)? "" : getStateT(policy10Arr[4]));
                    telPayment.put("arrearMoney", (5>len)? "" : getChineseStr(policy10Arr[5] , 'C'));
                    telPayment.put("arrearMonths", (6>len)? "" : getChineseStr(policy10Arr[6] , 'C'));
                    telPayment.put("getTime", (7>len)? "" : formatDateNumber(policy10Arr[7] , 'D'));
                    telPayment.put("status24", (8>len)? "" : getRecords(policy10Arr[8]));
                    policy10List.add(telPayment);
                }
                resultMap.put("telPayment", policy10List);
            }else{
                resultMap.put("telPayment", policy10);
            }
            JSONArray policy11 = object.getJSONArray("Policy11");
            if (null!= policy11 &&!policy11.isEmpty()) {
                JSONArray policy11List = new JSONArray();
                String policy11Str = policy11.toJSONString();
                log.info("接收到Policy11 json："+policy11Str);
                for (Object obj2 : policy11) {
                    String str = (String) obj2;
                    String[] policy11Arr = str.split("\\|");
                    int len = policy11Arr.length -1;
                    JSONObject endowmentInsuranceDeliver = new JSONObject();
                    endowmentInsuranceDeliver.put("num", (0>len)? "" : getNum(policy11Arr[0] , "C"));
                    endowmentInsuranceDeliver.put("area", (1>len)? "" : getChineseStr(policy11Arr[1] , 'C'));
                    endowmentInsuranceDeliver.put("retireType", (2>len)? "" : getRetireType(policy11Arr[2]));
                    endowmentInsuranceDeliver.put("registerDate", (3>len)? "" : formatDateNumber(policy11Arr[3] , 'D'));
                    endowmentInsuranceDeliver.put("workDate", (4>len)? "" : formatDateNumber(policy11Arr[4] , 'D'));
                    endowmentInsuranceDeliver.put("money", (5>len)? "" : getStr2Int(policy11Arr[5], 'C'));
                    endowmentInsuranceDeliver.put("pauseReason", (6>len)? "" : getChineseStr(policy11Arr[6] , 'C'));
                    policy11List.add(endowmentInsuranceDeliver);
                }
                resultMap.put("endowmentInsuranceDeliver", policy11List);
            }else{
                resultMap.put("endowmentInsuranceDeliver", policy11);
            }
            JSONArray policy12 = object.getJSONArray("Policy12");

            if (null!= policy12 &&!policy12.isEmpty()) {
                JSONArray policy12List = new JSONArray();
                String policy12Str = policy11.toJSONString();
                log.info("接收到Policy12 json："+policy12Str);
                for (Object obj3 : policy12) {
                    String str = (String) obj3;
                    String[] policy12Arr = str.split("\\|");
                    int len = policy12Arr.length -1;
                    JSONObject taxArrear = new JSONObject();
                    taxArrear.put("num", (0>len)? "" : getNum(policy12Arr[0] , "C"));
                    taxArrear.put("organName", (1>len)? "" : getChineseStr(policy12Arr[1] , 'C'));
                    taxArrear.put("taxArreaAmount", (2>len)? "" : getStr2Int(policy12Arr[2] , 'C'));
                    taxArrear.put("revenueDate", (3>len)? "" : formatDateNumber(policy12Arr[3] , 'D'));
                    policy12List.add(taxArrear);
                }
                resultMap.put("taxArrear", policy12List);
            }else{
                resultMap.put("taxArrear", policy12);
            }
            //add by  end
            //add by 
            JSONArray policy13 = object.getJSONArray("Policy13");
            JSONArray civilJudgementList = new JSONArray();
            if (null!= policy13 &&!policy13.isEmpty()) {
                for (Object obj : policy13) {
                    String civilJudgementArray = (String) obj;
                    String[] strings = civilJudgementArray.split("\\|");
                    JSONObject civilJudgement = new JSONObject();
                    civilJudgement.put("num", strings[0].isEmpty() ? strings[0] : strings[0].substring(1));
                    civilJudgement.put("court", getChineseStr(strings[1],'C'));
                    civilJudgement.put("caseReason", getChineseStr(strings[2],'C'));
                    civilJudgement.put("registerDate", formatDateNumber(strings[3],'D'));
                    civilJudgement.put("closedType", getClosedType(strings[4]));
                    civilJudgementList.add(civilJudgement);
                }
                resultMap.put("civilJudgement",civilJudgementList);
            }else{
                resultMap.put("civilJudgement",policy13);
            }
            JSONArray policy14 = object.getJSONArray("Policy14");
            JSONArray civilJudgementDetailList = new JSONArray();
            if (null!= policy14 &&!policy14.isEmpty()) {
                for (Object obj : policy14) {
                    String civilJudgementDetailArray = (String) obj;
                    String[] strings = civilJudgementDetailArray.split("\\|");
                    JSONObject civilJudgementDetail = new JSONObject();
                    civilJudgementDetail.put("num", strings[0].isEmpty() ? strings[0] : strings[0].substring(1));
                    civilJudgementDetail.put("caseResult", getChineseStr(strings[1],'C'));
                    civilJudgementDetail.put("caseValidatedate", formatDateNumber(strings[2],'D'));
                    civilJudgementDetail.put("suitObject", getChineseStr(strings[3],'C'));
                    civilJudgementDetail.put("suitObjectMoney", getStr2Int(strings[4],'C'));
                    civilJudgementDetailList.add(civilJudgementDetail);
                }
                resultMap.put("civilJudgementDetail",civilJudgementDetailList);
            }else{
                resultMap.put("civilJudgementDetail",policy14);
            }
            JSONArray policy15 = object.getJSONArray("Policy15");
            JSONArray salvationList = new JSONArray();
            if (null!= policy15 &&!policy15.isEmpty()) {
                for (Object obj : policy15) {
                    String salvationArray = (String) obj;
                    String[] strings = salvationArray.split("\\|");
                    JSONObject salvation = new JSONObject();
                    salvation.put("num", strings[0].isEmpty() ? strings[0] : strings[0].substring(1));
                    salvation.put("personnelType", getPersonnelType(strings[1]));
                    salvation.put("area", getChineseStr(strings[2],'C'));
                    salvation.put("organName", getChineseStr(strings[3],'C'));
                    salvation.put("money", getStr2Int(strings[4],'C'));
                    salvation.put("registerDate", formatDateNumber(strings[5],'D'));
                    salvation.put("passDate", formatDateNumber(strings[6],'D'));
                    salvation.put("getTime", formatDateNumber(strings[7],'D'));
                    salvationList.add(salvation);
                }
                resultMap.put("salvation",salvationList);
            }else{
                resultMap.put("salvation",policy15);
            }
            //add by  end
            //adminPunishment
            List<Map<String,Object>> list16 = new ArrayList<>();
            JSONArray array16 = object.getJSONArray("Policy16");
            if (array16!= null && array16.size() > 0) {
                for (int i = 0; i < array16.size(); i++) {


                    Map<String,Object> map = new HashMap<>();
                    String getStr = (String)array16.get(i);
                    String[] getArr = getStr.split("\\|");
                    map.put("num", separateString(getArr[0], "A"));
                    map.put("organName", getChineseStr(getArr[1], 'C'));
                    map.put("content", getChineseStr(getArr[2],'C'));
                    map.put("money", getInt(getArr[3], 'C'));
                    map.put("beginDate", formatDateNumber(getArr[4], 'D'));
                    map.put("endDate", formatDateNumber(getArr[5], 'D'));
                    map.put("result", getChineseStr(getArr[6], 'C'));
                    list16.add(map);
                }
                resultMap.put("adminPunishment", list16);
            }else{
                resultMap.put("adminPunishment",array16);
            }
        }
        return resultMap;
    }
    /**
     * Gualoan信息
     * @author xj
     * @date 2018/11/28
     */
    private Map<String,Object> getGualoan(JSONObject object){
        Map<String,Object> resultMap = new HashMap<>();
        if(!object.isEmpty()){
            JSONArray array  = object.getJSONArray("Policy1");
            if(array!= null && array.size()>0){
                List<Map<String,Object>> list = new ArrayList<>();
                for(int i=0;i<array.size();i++){
                    Map<String,Object> map = new HashMap<>();
                    String getStr = (String)array.get(i);
                    String[] getArr = getStr.split("\\|");
                    map.put("num",separateString(getArr[0],"C"));
                    map.put("organName",getChineseStr(getArr[1],'C'));
                    map.put("contractMoney",getInt(getArr[2],'C'));
                    map.put("beginDate",formatDateNumber(getArr[3],'D'));
                    map.put("guananteeMoney",getInt(getArr[4],'C'));
                    map.put("guaranteeBalance",getInt(getArr[5],'C'));
                    map.put("billingDate",formatDateNumber(getArr[6],'D'));
                    list.add(map);
                }
                resultMap.put("guaranteeInfoList",list);
            }else{
                resultMap.put("guaranteeInfoList",null);
            }

            JSONArray array2  = object.getJSONArray("Policy2");
            if(array2!= null && array2.size()>0){
                List<Map<String,Object>> list2 = new ArrayList<>();
                for(int i=0;i<array2.size();i++){
                    Map<String,Object> map = new HashMap<>();
                    String getStr = (String)array2.get(i);
                    String[] getArr = getStr.split("\\|");
                    map.put("guaLoanId",separateString(getArr[0],"C"));
                    map.put("guaLoanDate",getInt(getArr[1],'C'));
                    map.put("guaLoanDuedate",rel(getInt(getArr[2],'C')));
                    map.put("guaLoanBal",getInt(getArr[3],'C'));
                    map.put("guaLoanType",getGuaLoanType(getArr[4]));
                    map.put("guaLoanOrg",getChineseStr(getArr[5] , 'C'));
                    map.put("guaLoanContractAmt",getStr2Int(getArr[6] , 'C'));
                    map.put("guaLoanAmount",getStr2Int(getArr[7] , 'C'));
                    map.put("guaLoanRefundDate",getInt(getArr[8] , 'D'));
                    list2.add(map);
                }
                resultMap.put("guaLoanInfoList",list2);
            }else{
                resultMap.put("guaLoanInfoList",null);
            }
        }
        return resultMap;
    }
    //******************xj end********************//
    //****************** start******************//
    private JSONObject getLoanInfo(JSONObject json){
        JSONObject loan = new JSONObject();
        if(json != null && !json.isEmpty()){
            JSONArray Policy1ResList = json.getJSONArray("Policy1");//loanBeanList
            JSONArray policy1List = new JSONArray();
            JSONArray policy2List = new JSONArray();
            if(Policy1ResList !=null && !Policy1ResList.isEmpty()){
                for (Object obj : Policy1ResList) {
                    String str = (String) obj;
                    String[] jsonArr = str.split("\\|");
                    int len = jsonArr.length -1;
                    JSONObject tmpJSON = new JSONObject();
                    Integer loanIdE =  (0>len) ? 0 : getNum(jsonArr[0],"E");
//                    tmpJSON.put("loanId",  (0>len)? "" : getNum(jsonArr[0],"E"));
                    tmpJSON.put("loanId", loanIdE);
                    tmpJSON.put("loanDate", (1>len)? "" : getStr2Int(jsonArr[1],'C'));
                    tmpJSON.put("loanAmt", (2>len)? "" : getStr2Int(jsonArr[2],'C'));
                    tmpJSON.put("loanCurrency", (3>len)? "" : getLoanCurrency(jsonArr[3]));
                    tmpJSON.put("loanPrdType", (4>len)? "" : getLoanPrdType(jsonArr[4]));
                    tmpJSON.put("loanDueDate", (5>len)? rel("") : rel(getStr2Int(jsonArr[5] , 'C')));
                    tmpJSON.put("loanClosedate", (6>len)? ren("") :  ren(getStr2Int(jsonArr[6] , 'C')));
                    tmpJSON.put("loanStatus", (7>len)? "" : getLoanStatusR(jsonArr[7]));
                    tmpJSON.put("loanBlance", (8>len)? "" : getStr2Int(jsonArr[8] , 'C'));
                    tmpJSON.put("loanRepayRcord", (9>len)? "" : getLoanBlance(jsonArr[9]));//??
                    tmpJSON.put("currentDefaultAmt", (10>len)? "" : getStr2Int(jsonArr[10] , 'C'));
                    tmpJSON.put("currenDefaultNum", (11>len)? "" : getStr2Int(jsonArr[11] , 'C'));
                    tmpJSON.put("loanClass5", (12>len)? "" : getLoanClass5R(jsonArr[12]));
                    tmpJSON.put("residueRepayPeriods", (13>len)? "" : getNum(jsonArr[13],"E"));
                    tmpJSON.put("currentBalance", (14>len)? "" : getStr2Int(jsonArr[14] , 'C'));
                    tmpJSON.put("billingDateLoan", (15>len)? "" : formatDateNumber(jsonArr[15] , 'C'));
                    tmpJSON.put("actualPaymentAmount", (16>len)? "" : getStr2Int(jsonArr[16] , 'C'));
                    tmpJSON.put("recentPayDate", (17>len)? "" : formatDateNumber(jsonArr[17] , 'C'));
                    tmpJSON.put("overdue31To60", (18>len)? "" : getStr2Int(jsonArr[18] , 'C'));
                    tmpJSON.put("overdue61To90",  (19>len)? "" : getStr2Int(jsonArr[19],'C'));
                    tmpJSON.put("overdue91To180", (20>len)? "" : getStr2Int(jsonArr[20], 'C'));
                    tmpJSON.put("overdueAbove180", (21>len)? "" : getStr2Int(jsonArr[21], 'C'));
                    tmpJSON.put("loanOrgCode",  (22>len)? "" : getCard1OrgCode(jsonArr[22]));
                    tmpJSON.put("loanOrgType", (23>len)? "" : getChineseStr(jsonArr[23] , 'C'));
                    tmpJSON.put("repayDisc", (24>len)? "" : castToDateRange(jsonArr[24] , 'C'));//??
                    tmpJSON.put("assureType", (25>len)? "" : getAssureTypeR(jsonArr[25]));
//                    tmpJSON.put("assureType", (25>len)? "" : getChineseStr(jsonArr[25] , 'C'));
                    tmpJSON.put("loanOrgName", (26>len)? "" : "");
                    tmpJSON.put("loanNum", (27>len)? "" : getStr2Int(jsonArr[27], 'C') + "期");
                    tmpJSON.put("loanPayType", (28>len)? "" : getLoanPayTypeR(jsonArr[28]));
                    // add by  20181210
                    tmpJSON.put("loanBussinessNo", (29>len)? "" : jsonArr[29]);
                    tmpJSON.put("loanAccountDate", (30>len)? "" : getChineseStr(jsonArr[30],'C'));
                    //add by  20181210 end
                    JSONArray specialTradeList = json.getJSONArray("Policy2");//specialTradeList
                    JSONArray policy2TmpList = new JSONArray();
                    if(specialTradeList != null && !specialTradeList.isEmpty()){
                        for (Object obj2 : specialTradeList) {
                            JSONArray objects =  (JSONArray)obj2;
                            for(Object obj3 : objects){
                                String jsonStr2 = (String)obj3;
                                String[] jsonArr2 = jsonStr2.split("\\|");
                                int leng = jsonArr2.length -1;
                                JSONObject tmpJSON2 = new JSONObject();
                                Integer loanIdS =  (0>leng) ? 0 : getNum(jsonArr2[0],"S");
                                if(loanIdS.equals(loanIdE)){
//                                    tmpJSON2.put("loanId" ,  (0>leng) ? "" : getNum(jsonArr2[0],"S"));
                                    tmpJSON2.put("specialTrade" , (1>leng) ? "" : convertSpecialTrade(jsonArr2[1]));
                                    tmpJSON2.put("specialCreateDate" , (2>leng) ? "" : formatDateNumber(jsonArr2[2] , 'C'));
                                    tmpJSON2.put("specialModifyMonths" , (3>leng) ? "" : getSpecialModifyMonthsRR(jsonArr2[3]));
                                    tmpJSON2.put("specialEventMoney" , (4>leng) ? "" : getInt(jsonArr2[4] , 'C'));
                                    tmpJSON2.put("tradeDetail" , (5>leng) ? "" : getChineseStr(jsonArr2[5] , 'C'));
//                                    policy2List.add(tmpJSON2);
                                    policy2TmpList.add(tmpJSON2);
                                }
                                tmpJSON.put("specialTradeList",policy2TmpList);
                            }
                        }
                    }
                    tmpJSON.put("allRecord", getCard1Info2(loanIdE+"",json));
                    tmpJSON.put("illuminateInfo", getIlluminateInfo(loanIdE+"",json));
                    policy1List.add(tmpJSON);
//                    policy1List.add(policy2TmpList);
                }

            }
            loan.put("loanBeanList",policy1List);
//            loan.put("specialTradeList",policy2List);
        }
        return loan;
    }
    //****************** end********************//
    //****************** start******************//
    /**
     * 信用提示
     * @author 
     * @date 2018/12/3
     */
    private JSONObject getSummInfo(JSONObject json){
        JSONObject summinfo = new JSONObject();
        if (json != null && !json.isEmpty()) {
            String Policy1 = json.getString("Policy1");
            if (Policy1!= null && !Policy1.isEmpty()) {
                Map<String, Object> creditTips = new HashMap<>();
                String[] Policy1value = Policy1.split("\\|");
                creditTips.put("personalHLNum",separateString(Policy1value[0],"A"));//个人住房贷款笔数      A||原值
                creditTips.put("personalCHLNum",separateString(Policy1value[1],"A"));//个人商用房（包括商住两用）贷款笔数      A||原值
                creditTips.put("otherLoanNum",separateString(Policy1value[2],"A"));//其他贷款笔数       A||原值
                creditTips.put("firstLoanMonth",rev(formatDateNumber(Policy1value[3],'D')));//首笔贷款发放月份       “--”替换为B0，yyyy.mm 替换为yyyyMM 数字转为英文大写字母 0对照D
                creditTips.put("card1Num",separateString(Policy1value[4],"A"));//贷记卡账户数       A||原值
                creditTips.put("firstCard1Month",rev(formatDateNumber(Policy1value[5],'D')));//首张贷记卡发卡月份     “--”替换为B0，yyyy.mm 替换为yyyyMM 数字转为英文大写字母 0对照D
                creditTips.put("card2Num",separateString(Policy1value[6],"A"));//准贷记卡账户数      A||原值
                creditTips.put("firstCard2Month",rev(formatDateNumber(Policy1value[7],'D')));//首张准贷记卡发卡月份        “--”替换为B0，yyyy.mm 替换为yyyyMM 数字转为英文大写字母 0对照D
                creditTips.put("statementNum",separateString(Policy1value[8],"A"));//本人声明数目       A||原值
                creditTips.put("dissentMarkNum",separateString(Policy1value[9],"A"));//异议标注数目     A||原值
                summinfo.put("creditTips",creditTips);
            }
            String Policy2 = json.getString("Policy2");
            if (Policy2!= null && !Policy2.isEmpty()) {
                Map<String, Object> outstandingSum = new HashMap<>();
                String[] Policy2value = Policy2.split("\\|");
                outstandingSum.put("loanCorporationOrNum",separateString(Policy2value[0],"C"));//贷款法人机构数       C||原值
                outstandingSum.put("loanOrNum",separateString(Policy2value[1],"C"));//贷款机构数
                outstandingSum.put("num",separateString(Policy2value[2],"C")); //	num	笔数
                outstandingSum.put("contractAmount",getInt(Policy2value[3],'F'));//合同总额   0替换为B0，数字转为英文大写字母 0对照F
                outstandingSum.put("balance",getInt(Policy2value[4],'F'));//余额
                outstandingSum.put("m6avgNeedPay",getInt(Policy2value[5],'F'));//最近 6 个月平均应还款
                summinfo.put("outstandingSum",outstandingSum);
            }
            String Policy3 = json.getString("Policy3");
            if (Policy3!= null && !Policy3.isEmpty()) {
                Map<String, Object> noCancelCard1Sum = new HashMap<>();
                String[] Policy3value = Policy3.split("\\|");
                noCancelCard1Sum.put("card1CorporationOrNum",separateString(Policy3value[0],"V"));//发卡法人机构数
                noCancelCard1Sum.put("card1OrNum",separateString(Policy3value[1],"V"));//发卡机构数
                noCancelCard1Sum.put("num",separateString(Policy3value[2],"V"));//num	账户数
                noCancelCard1Sum.put("creditAmount",getInt(Policy3value[3],'E'));//授信总额
                noCancelCard1Sum.put("singleBankMaxCredit",getInt(Policy3value[4],'E'));//单家行最高授信额度
                noCancelCard1Sum.put("singleBankMinCredit",getInt(Policy3value[5],'E')); //单家行最低授信额度
                noCancelCard1Sum.put("usedBalance",getInt(Policy3value[6],'E'));//已用额度
                noCancelCard1Sum.put("card1M6avgBalance",getInt(Policy3value[7],'E')); //最近六个月平均使用额度
                summinfo.put("noCancelCard1Sum",noCancelCard1Sum);
            }
            String Policy4 = json.getString("Policy4");
            if (Policy4!= null && !Policy4.isEmpty()) {
                Map<String, Object> noCancelCard2Sum = new HashMap<>();
                String[] Policy4value = Policy4.split("\\|");
                noCancelCard2Sum.put("card2CorporationOrNum",separateString(Policy4value[0],"X"));//发卡法人机构数
                noCancelCard2Sum.put("card2OrNum",separateString(Policy4value[1],"X"));//发卡机构数
                noCancelCard2Sum.put("num",separateString(Policy4value[2],"X"));//账户数
                noCancelCard2Sum.put("creditAmount",getInt(Policy4value[3],'E'));//授信总额
                noCancelCard2Sum.put("singleBankMaxCredit",getInt(Policy4value[4],'E'));//单家行最高授信额度
                noCancelCard2Sum.put("singleBankMinCredit",getInt(Policy4value[5],'E'));//单家行最低授信额度
                noCancelCard2Sum.put("usedBalance",getInt(Policy4value[6],'E'));//已用额度
                noCancelCard2Sum.put("card2M6avgBalance",getInt(Policy4value[7],'E'));//最近六个月平均使用额度
                summinfo.put("noCancelCard2Sum",noCancelCard2Sum);
            }
            String Policy5 = json.getString("Policy5");
            if (Policy5 != null && !Policy5.isEmpty()) {
                Map<String, Object> guaLoanSum = new HashMap<>();
                String[] Policy5value = Policy5.split("\\|");
                guaLoanSum.put("guaLoanNum",separateString(Policy5value[0],"G"));//担保笔数
                guaLoanSum.put("guaLoanAmount",getInt(Policy5value[1],'C'));//担保金额
                guaLoanSum.put("guaLoanPrincipalAmount",getInt(Policy5value[2],'C'));//担保本金余额
                summinfo.put("guaLoanSum",guaLoanSum);
            }
            String Policy6 = json.getString("Policy6");
            if (Policy6 != null && !Policy6.isEmpty()) {
                Map<String, Object> defaultSum = new HashMap<>();
                String[] Policy6value = (Policy6).split("\\|");
                defaultSum.put("loanDefaultNum", separateString(Policy6value[0], "N"));//贷款逾期笔数
                defaultSum.put("loanDefaultMon", separateString(Policy6value[1], "N"));//贷款逾期月份数
                defaultSum.put("loanSMMaxDefaultAmt", getInt(Policy6value[2],'D'));//贷款单月最高逾期总额
                defaultSum.put("loanLongDefaultMon", separateString(Policy6value[3], "N"));//贷款最长逾期月数
                defaultSum.put("card1DefaultNum", separateString(Policy6value[4], "N"));//贷记卡逾期账户数
                defaultSum.put("card1DefaultMon", separateString(Policy6value[5], "N"));//贷记卡逾期月份数
                defaultSum.put("card1SMMaxDefaultAmt", getInt(Policy6value[6],'D'));//贷记卡单月最高逾期总额
                defaultSum.put("card1LongDefaultMon", separateString(Policy6value[7], "N")); //贷记卡最长逾期上月数
                defaultSum.put("card2M2OutNum", separateString(Policy6value[8], "N"));//准贷记卡60天以上透支账户数
                defaultSum.put("card2M2OutMon", separateString(Policy6value[9], "N"));//准贷记卡60天以上透支月份数
                defaultSum.put("card2M2OutMaxMonAmt", getInt(Policy6value[10],'D'));//准贷记卡60天以上透支单月最高透支额度
                defaultSum.put("card2M2OutLongMon", separateString(Policy6value[11], "N"));//准贷记卡60天以上透支最长透支月数
                summinfo.put("defaultSum", defaultSum);
            }
            String Policy7 = json.getString("Policy7");
            if (Policy7 != null && !Policy7.isEmpty()) {
                Map<String, Object> dzInfoBean = new HashMap<>();
                String[] Policy7value = Policy7.split("\\|");
                dzInfoBean.put("dzNum",separateString(Policy7value[0], "D"));//呆账笔数
                dzInfoBean.put("dzCount",separateString(Policy7value[1], "D"));//呆账金额
                dzInfoBean.put("zcNum",separateString(Policy7value[2], "D"));//资产处置笔数
                dzInfoBean.put("zcCount",separateString(Policy7value[3], "D"));//资产处置金额
                dzInfoBean.put("dcNum",separateString(Policy7value[4], "D"));//代偿人代偿笔数
                dzInfoBean.put("dcCount",separateString(Policy7value[5], "D"));//代偿人代偿金额
                summinfo.put("dzInfoBean", dzInfoBean);
            }
            String Policy8 = json.getString("Policy8");
            if (Policy8 != null && !Policy8.isEmpty()) {
                Map<String, Object> creditScore = new HashMap<>();
                String[] Policy8value = Policy8.split("\\|");
                creditScore.put("score",getInt(Policy8value[0], 'E'));//数字解读
                creditScore.put("scoreLevel",">"+getPercent('E',Policy8value[1])); //相对位置
                creditScore.put("scoreElements", getChineseStr(Policy8value[2],'E'));//说明
                summinfo.put("creditScore", creditScore);
            }
        }
        return summinfo;
    }
    //****************** end********************//
    //****************** start******************//
    /**
     * 解析DEFAULT信息
     * @author 
     * @date 2018/11/28
     */
    private JSONObject getDefaultInfo(JSONObject json){
        JSONObject defaultInfo = new JSONObject();
        JSONArray policy1P = json.getJSONArray("Policy1");
        if(!policy1P.isEmpty()){
            JSONArray policy1List = new JSONArray();
            for (Object obj : policy1P) {
                String str = (String) obj;
                String[] policy1Arr = str.split("\\|");
                int len = policy1Arr.length -1;
                JSONObject tmp = new JSONObject();
                tmp.put("defaultId", (0>len)? "" : getNum(policy1Arr[0] , "C"));
                tmp.put("acctType", (1>len)? "" : getAcctType(policy1Arr[1]));
                tmp.put("acctId", (2>len)? "" : getNum(policy1Arr[2] , "C"));
                tmp.put("defaultDate", (3>len)? "" : formatDateNumber(policy1Arr[3] , 'D'));
                tmp.put("overdueDate", (4>len)? "" : getStr2Int(policy1Arr[4] , 'C'));
                tmp.put("defaultMonths", (5>len)? "" : getStr2Int(policy1Arr[5] , 'C'));
                tmp.put("defaultAmt", (6>len)? "" : getStr2Int(policy1Arr[6] , 'C'));
                policy1List.add(tmp);
            }
            defaultInfo.put("defaultBeanList", policy1List);
        }
        return defaultInfo;
    }
    /**
     * 解析ASSETDISPOSITON信息
     * @author 
     * @date 2018/11/28
     */
    private JSONObject getAssetDispositon(JSONObject json){
        JSONObject assetDispositionList = new JSONObject();
        JSONArray policy1T = json.getJSONArray("Policy1");
        if (policy1T != null && !policy1T.isEmpty()) {
            JSONArray list = new JSONArray();
            for (Object obj2 : policy1T) {
                String str = (String) obj2;
                String[] policy1Arr = str.split("\\|");
                int len = policy1Arr.length -1;
                JSONObject assetDisposition = new JSONObject();
                assetDisposition.put("num", (0>len)? "" : getNum(policy1Arr[0] , "C"));
                assetDisposition.put("organName", (1>len)? "" : getChineseStr(policy1Arr[1] , 'C'));
                assetDisposition.put("receiveTime", (2>len)? "" : formatDateNumber(policy1Arr[2] , 'D'));
                assetDisposition.put("money", (3>len)? "" : getStr2Int(policy1Arr[3] , 'C'));
                assetDisposition.put("latestRepayDate", (4>len)? "" : formatDateNumber(policy1Arr[4] , 'D'));
                assetDisposition.put("balance", (5>len)? "" : getStr2Int(policy1Arr[5], 'C'));
                list.add(assetDisposition);
            }
            assetDispositionList.put("assetDispositionList" , list);
        }
        return assetDispositionList;
    }
    //****************** end********************//
    private String getInfo4(String str){
        if (str.isEmpty()){
            return "0";
        } else {
            return str.equals("B0") ? "0" : getInt(str,'D');
        }
    }
    //S1:呆帐,S2:冻结,S3:未激活,S4:销户,S5:正常,S6:止付
    private String getCard1Status(String str){
        String value = str;
        switch(str){
            case "S1":
                value = "呆账";
                break;
            case "S2":
                value = "冻结";
                break;
            case "S3":
                value = "未激活";
                break;
            case "S4":
                value = "销户";
                break;
            case "S5":
                value = "正常";
                break;
            case "S6":
                value = "止付";
                break;
            default:
                value = "呆账";
                break;
        }
        return value;
    }
    /**
     * 还款记录
     * @param data 传入值
     * @return 返回解析后的值
     */
    private String getLoanBlance(String data){
        if (data == null||data.isEmpty()||"B0".equals(data)){
            return "";
        }

        if(data.length()>24){
            if(data.contains("HF")){
                data = data.replace("HF", "N");
            }
            if(data.contains("JH")){
                data = data.replace("JH", "O");
            }
        }

        StringBuilder result = new StringBuilder();
        char[] chars = data.toCharArray();
        for (char c : chars) {
            switch (c) {
                case 'A':
                    result.append("# ");//未知
                    break;
                case 'B':
                    result.append("* ");//本月无还款历史
                    break;
                case 'C':
                    result.append("/ ");//未开户
                    break;
                case 'D':
                    result.append("C ");//结清
                    break;
                case 'E':
                    result.append("N ");//正常
                    break;
                case 'F':
                    result.append("G ");//其他终止账户雷系
                    break;
                case 'N':
                    result.append("D ");//担保人代偿
                    break;
                case 'O':
                    result.append("Z ");//以资抵债
                    break;
                case ' ':
                    break;
                default:
                    result.append(getStr2Int(String.valueOf(c),'F')).append(" ");
                    break;
            }
        }

        return result.toString().trim();
    }
    //************** start*****************//
    private Map<String, Object> inverseSubstitutionQueryInfo(String json) {
        HashMap<String, Object> map = new HashMap<>();
        Map parse = (Map) JSON.parse(json);
        //ApprovalPoliciesInfo2
        Map approvalPoliciesInfo2 = (Map) parse.get("ApprovalPoliciesInfo2");
        if(null==approvalPoliciesInfo2||approvalPoliciesInfo2.isEmpty()){
            return new HashMap<>();
        }
        //Policy1
        String policy1 = approvalPoliciesInfo2.get("Policy1")+"";
        Map policy1Map = new HashMap();
        try {
            policy1Map = inverseSubstitutionPolicy1(policy1);
            map.put("querySum", policy1Map);
        } catch (Exception e) {
            logger.error("惠捷加密解密失败"+e.getMessage());
            map.put("querySum",  new ArrayList());
        }
        //Policy2
        String policy2 = approvalPoliciesInfo2.get("Policy2")+"";
        try {
            List<Map> list = inverseSubstitutionPolicy2(policy2);
            map.put("orgQueryInfo", list);
        } catch (Exception e) {
            	map.put("orgQueryInfo", new ArrayList());
        }
        //Policy3
        String policy3 = approvalPoliciesInfo2.get("Policy3")+"";
        try {
            List<Map> list = inverseSubstitutionPolicy3(policy3);
            map.put("perQueryInfo", list);
        } catch (Exception e) {
            map.put("perQueryInfo", new ArrayList());
        }
        return map;
    }
    private Map inverseSubstitutionPolicy1(String policy1) throws Exception {
        if (null==policy1||policy1.equals("")||policy1.equals("null")) {
            return new HashMap<String, String>();
        }
        String[] policy1Split = policy1.split("\\|");
        Map<String, String> querySum = new HashMap<String, String>();
        for (int i = 0; i < policy1Split.length; i++) {
            policy1Split[i] = policy1Split[i].substring(1, policy1Split[i].length());
        }
        querySum.put("last1OrgLoanApproval", policy1Split[0]);
        querySum.put("last1OrgCreditApproval", policy1Split[1]);
        querySum.put("last1LoanApproval", policy1Split[2]);
        querySum.put("last1CreditApproval", policy1Split[3]);
        querySum.put("last1SelfQuery", policy1Split[4]);
        querySum.put("last2YManageAfterLoan", policy1Split[5]);
        querySum.put("last2YGuaLoanQualification", policy1Split[6]);
        querySum.put("last2YMerchantName", policy1Split[7]);
        return querySum;
    }
    private List<Map> inverseSubstitutionPolicy2(String policy2) throws Exception {
        if (policy2 == "") {
            new Exception();
        }
        List<Map> list = new ArrayList<>();
        JSONArray platformList = JSON.parseArray(policy2);
        List<String> policy2Maps = JSONArray.parseArray(platformList.toJSONString(), String.class);
        for (String policy2Json : policy2Maps) {
            Map<String, String> policy2Map = new HashMap<>();
            String[] policy2Split = policy2Json.split("\\|");
            //查询编号
            policy2Map.put("orgQueryId", policy2Split[0].substring(1, policy2Split[0].length()));
            //查询日期
            if ("B0".equals(policy2Split[1])) {
                policy2Map.put("orgQueryDate", "0");
            } else {
                policy2Map.put("orgQueryDate", getInt(policy2Split[1], 'D'));
            }
            //查询机构
            if ("B0".equals(policy2Split[2])) {
                policy2Map.put("orgQueryOperator", "--");
            } else if ("A1".equals(policy2Split[2])) {
                policy2Map.put("orgQueryOperator", "九江银行");
            } else {
                char[] chars = policy2Split[2].toCharArray();
                String a = chars[0] + "" + chars[1] + "";
                String b = chars[2] + "" + chars[3] + "";
                char c = (char) Integer.parseInt(a);
                char d = (char) Integer.parseInt(b);
                policy2Map.put("orgQueryOperator", c + "" + d);
            }
            getSelectReason(policy2Map, policy2Split[3]);
            list.add(policy2Map);
        }
        return list;
    }
    private List<Map> inverseSubstitutionPolicy3(String policy3) throws Exception {
        if (policy3 == "") {
            new Exception();
        }
        List<Map> list = new ArrayList<>();
        JSONArray platformList = JSON.parseArray(policy3);
        List<String> policy3Maps = JSONArray.parseArray(platformList.toJSONString(), String.class);
        for (String policy3Json : policy3Maps) {
            Map<String, String> policy3Map = new HashMap<>();
            String[] policy3Split = policy3Json.split("\\|");
            policy3Map.put("perQueryId", policy3Split[0].substring(1, policy3Split[0].length()));
            policy3Map.put("perQueryDate", getInt(policy3Split[1], 'D'));
            //查询人员
            if ("B0".equals(policy3Split[2])) {
                policy3Map.put("perQueryOperator", "--");
            } else if ("A1".equals(policy3Split[2])) {
                policy3Map.put("perQueryOperator", "九江银行");
            } else {
                char[] chars = policy3Split[2].toCharArray();
                String a = chars[0] + "" + chars[1] + "";
                String b = chars[2] + "" + chars[3] + "";
                char c = (char) Integer.parseInt(a);
                char d = (char) Integer.parseInt(b);
                policy3Map.put("perQueryOperator", c + "" + d);
            }
            getSelectReason(policy3Map, policy3Split[3]);
            list.add(policy3Map);
        }
        return list;
    }
    private Map<String, Object> inverseSubstitutionBasicInfo(String json) {
        HashMap<String, Object> map = new HashMap<>();
        Map parse = (Map) JSON.parse(json);
        String userinfo = parse.get("Policy1")+"";
        try {
            Map map1 = inverseSubstitutionUserInfoz(map, userinfo);
            map.put("BASICINFO",map1);
        } catch (Exception e) {
            logger.error("BASICINFO信息处理失败"+e.getMessage());
            map.put("BASICINFO",userinfo);
        }
        String spouseInfo = parse.get("Policy2")+"";
        try {
            Map map1 = inverseSubstitutionSpouseInfo(spouseInfo);
            map.put("spouseInfo",map1);
        } catch (Exception e) {
            logger.error("BASICINFO信息处理失败"+e.getMessage());
            map.put("spouseInfo",spouseInfo);
        }
        String residenceInfo = parse.get("Policy3")+"";
        try {
            //?
            List<Map> list = inverseSubstitutionResidenceInfo(residenceInfo);
            map.put("residenceInfo",list);
        } catch (Exception e) {
            logger.error("BASICINFO信息处理失败"+e.getMessage());
            map.put("residenceInfo",new ArrayList());
        }
        String professionUnit = parse.get("Policy4")+"";
        try {
            //?
            List<Map> list = inverseSubstitutionProfessionUnit(professionUnit);
            map.put("professionUnit",list);
        } catch (Exception e) {
            logger.error("BASICINFO信息处理失败"+e.getMessage());
            map.put("professionUnit",new ArrayList());
        }
        String professionWork = parse.get("Policy5")+"";
        try {
            //?
            List<Map> list = inverseSubstitutionProfessionWork(professionWork);
            map.put("professionWork",list);
        } catch (Exception e) {
            logger.error("BASICINFO信息处理失败"+e.getMessage());
            map.put("professionWork",new ArrayList());
        }
        return map;
    }
    /**
     * 逆转userInfo
     * @param userInfo
     * @return
     * @throws Exception
     */
    private Map inverseSubstitutionUserInfoz(Map<String,Object> map,String userInfo) throws Exception {
        if (userInfo == "") {
            new Exception();
        }
        Map parse = (Map) JSON.parse(userInfo);
        map.put("sex",inverseGetSex(parse.get("sex")+""));
        map.put("birth",formatDateNumber(parse.get("birth") == null ? "":parse.get("birth").toString(),'D'));
        map.put("marital",inverseGetMarital(parse.get("marital")+""));
        map.put("cell",getChineseStr(parse.get("cell")+"",'C'));
        map.put("education",inverseGetEducation(parse.get("education")+""));
        map.put("degree",inverseGetDegree(parse.get("degree")+""));
        map.put("addressnum",getChineseStr(parse.get("addressnum")+"",'C'));
        map.put("jobnum",getChineseStr(parse.get("jobnum")+"",'C'));
        map.put("unitTel",getChineseStr(parse.get("unitTel")+"",'C'));
        map.put("homeTel",getChineseStr(parse.get("homeTel")+"",'C'));
        map.put("correspondenceAddr",getChineseStr(parse.get("correspondenceAddr")+"",'C'));
        map.put("permanentResidenceAddr",getChineseStr(parse.get("permanentResidenceAddr")+"",'C'));
        return map;
    }
    /**
     * 逆转SpouseInfo
     * @param spouseInfo
     * @return
     * @throws Exception
     */
    private Map inverseSubstitutionSpouseInfo(String spouseInfo) throws Exception {
        if (spouseInfo == "") {
            new Exception();
        }
        Map parse = (Map) JSON.parse(spouseInfo);
        Map<String,Object> spouseInfoMap = new HashMap<>();
        spouseInfoMap.put("spouseName",getChineseStr(parse.get("spouseName")+"",'C'));
        spouseInfoMap.put("spouseIdType",inverseGetIDType(parse.get("spouseIdType")+""));
        spouseInfoMap.put("spouseId",getChineseStr(parse.get("spouseId")+"",'C'));
        spouseInfoMap.put("spouseWorkUnit",getChineseStr(parse.get("spouseWorkUnit")+"",'C'));
        spouseInfoMap.put("spouseCell",getChineseStr(parse.get("spouseCell")+"",'C'));
        return spouseInfoMap;
    }
    /**
     * 逆转SpouseInfo
     * @param residenceInfo
     * @return
     * @throws Exception
     */
    private List<Map> inverseSubstitutionResidenceInfo(String residenceInfo) throws Exception {
        if ("null".equals(residenceInfo)||residenceInfo == "") {
            new Exception();
        }
        JSONArray platformList = JSON.parseArray(residenceInfo);
        List<Map> list = JSONArray.parseArray(platformList.toJSONString(), Map.class);
        List<Map> mapList = new ArrayList<Map>();
        for (Map map: list) {
            Map<String,Object> spouseInfoMap = new HashMap<>();
            spouseInfoMap.put("num",getNum(map.get("num")+"" , "A"));
            spouseInfoMap.put("residenceAddr", getChineseStr(map.get("residenceAddr")+"" , 'C'));
            spouseInfoMap.put("residenceCondition",inverseGetResidenceCondition(map.get("residenceCondition")+""));
            spouseInfoMap.put("infoUpdate",formatDateNumber(map.get("infoUpdate")+"" , 'D'));
            mapList.add(spouseInfoMap);
        }
        return mapList;
    }
    /**
     * 逆转SpouseInfo
     * @param professionUnit
     * @return
     * @throws Exception
     */
    private List<Map> inverseSubstitutionProfessionUnit(String professionUnit) throws Exception {
        if ("null".equals(professionUnit)||professionUnit == "") {
            new Exception();
        }
        JSONArray platformList = JSON.parseArray(professionUnit);
        List<Map> list = JSONArray.parseArray(platformList.toJSONString(), Map.class);
        List<Map> mapList = new ArrayList<Map>();
        for (Map map: list) {
            Map<String,Object> professionUnitMap = new HashMap<>();
            professionUnitMap.put("num",getNum(map.get("num")+"" , "C"));
            professionUnitMap.put("unitName",getChineseStr(map.get("unitName")+"" , 'C'));
            professionUnitMap.put("unitAddr",getChineseStr(map.get("unitAddr")+"" , 'C'));
            mapList.add(professionUnitMap);
        }
        return mapList;
    }
    /**
     * 逆转ProfessionWork
     * @param professionWork
     * @return
     * @throws Exception
     */
    private List<Map> inverseSubstitutionProfessionWork(String professionWork) throws Exception {
        if ("null".equals(professionWork)||professionWork == "") {
            new Exception();
        }
        JSONArray platformList = JSON.parseArray(professionWork);
        List<Map> list = JSONArray.parseArray(platformList.toJSONString(), Map.class);
        List<Map> mapList = new ArrayList<Map>();
        for (Map map: list) {
            Map<String,Object> professionUnitMap = new HashMap<>();
            professionUnitMap.put("num",  getNum((String)map.get("num"),"C"));
            professionUnitMap.put("jobs",inverseGetJob(map.get("jobs")+""));
            professionUnitMap.put("industry",inverseGetIndustry(map.get("industry")+""));
            professionUnitMap.put("position",inverseGetPosition(map.get("position")+""));
            professionUnitMap.put("jobTitle",inverseGetJobTitle(map.get("jobTitle")+""));
            professionUnitMap.put("joinDate",rev(getInt(map.get("joinDate")+"",'D')));
            professionUnitMap.put("updateTime",formatDateNumber(map.get("updateTime")+"",'D'));
            mapList.add(professionUnitMap);
        }
        return mapList;
    }
    /**
     * 匹配查询原因
     *
     * @param policy2Map
     * @param anObject
     */
    private void getSelectReason(Map<String, String> policy2Map, String anObject) {
        //查询原因
        if ("A0".equals(anObject)) {
            policy2Map.put("orgQueryReason", "贷款审批");
        } else if ("A1".equals(anObject)) {
            policy2Map.put("orgQueryReason", "信用卡审批");
        } else if ("A2".equals(anObject)) {
            policy2Map.put("orgQueryReason", "担保资格审查");
        } else {
            policy2Map.put("orgQueryReason", anObject);
        }
    }
    //************** end*****************//
    /**
     * 职称况类型转换
     * @param jobTitle
     * @return
     */
    private String inverseGetJobTitle(String jobTitle) {
        switch (jobTitle) {
            case "1":
                return "初级";
            case "2":
                return "中级";
            case "3":
                return "高级";
            case "4":
                return "无";
            default:
                return "--";
        }
    }
    /**
     * 职位况类型转换
     * @param position
     * @return
     */
    private String inverseGetPosition(String position) {
        switch (position) {
            case "1":
                return "一般员工";
            case "2":
                return "中级领导（行政级别局级以下领导或大公司中级管理人员）";
            case "3":
                return "高级领导（行政级别局级及局级以上领导或大公司高级管理人员）";
            case "4":
                return "其他";
            default:
                return "--";
        }
    }
    /**
     * 行业类型转换
     *
     * @param industry
     * @return
     */
    private String inverseGetIndustry(String industry) {
        switch (industry) {
            case "1":
                return "--";
            case "2":
                return "采掘业";
            case "3":
                return "电力、煤气及水的生产和供应业";
            case "4":
                return "房地产业";
            case "5":
                return "公共管理和社会组织";
            case "6":
                return "建筑业";
            case "7":
                return "交通运输、仓储和邮政业";
            case "8":
                return "教育";
            case "9":
                return "金融业";
            case "10":
                return "居民服务和其他服务业";
            case "11":
                return "科学研究、技术服务业和地质勘察业";
            case "12":
                return "农、林、牧、渔业";
            case "13":
                return "批发和零售业";
            case "14":
                return "水利、环境和公共设施管理业";
            case "15":
                return "卫生、社会保障和社会福利业";
            case "16":
                return "文化、体育和娱乐业";
            case "17":
                return "信息传输、计算机服务和软件业";
            case "18":
                return "制造业";
            case "19":
                return "住宿和餐饮业";
            case "20":
                return "租赁和商务服务业";
            default:
                return "";
        }
    }
    /**
     * 职业类型转换
     *
     * @param type
     * @return
     */
    private String inverseGetJob(String type) {
        switch (type) {
            case "0":
                return "";
            case "1":
                return "--";
            case "2":
                return "商业、服务业人员";
            case "3":
                return "办事人员和有关人员";
            case "4":
                return "生产、运输设备操作人员及有关人员";
            case "5":
                return "不便分类的其它从业人员";
            case "6":
                return "专业技术人员";
            case "7":
                return "军人";
            case "8":
                return "国家机关、党群组织、企业、事业单位负责人";
            case "9":
                return "农、林、牧、渔、水利业生产人员";
            default:
                return "";
        }
    }
    /**
     * 证件类型转换
     *
     * @param type
     * @return
     */
    private String inverseGetResidenceCondition(String type) {
        switch (type) {
            case "0":
                return "未知";
            case "1":
                return "租房";
            case "2":
                return "自置";
            case "3":
                return "亲属楼宇";
            case "4":
                return "集体宿舍";
            case "5":
                return "共有住宅";
            case "6":
                return "按揭";
            case "7":
                return "其他";
            default:
                return "--";
        }
    }
    /**
     * 证件类型转换
     *
     * @param type
     * @return
     */
    private String inverseGetIDType(String type) {
        switch (type) {
            case "1":
                return "身份证";
            case "2":
                return "户口簿";
            case "3":
                return "其他证件";
            default:
                return "--";
        }
    }
    /**
     * 学历转换
     *
     * @param education
     * @return
     */
    private String inverseGetEducation(String education) {
        switch (education) {
            case "1":
                return "小学";
            case "2":
                return "初中";
            case "3":
                return "中等专业学校或中等技术学校";
            case "4":
                return "高中";
            case "5":
                return "大学专科和专科学校（简称大专）";
            case "6":
                return "大学本科（简称大学）";
            case "7":
                return "研究生";
            default:
                return "--";
        }
    }
    /**
     * 学历转换
     *
     * @param education
     * @return
     */
    private String inverseGetDegree(String education) {
        switch (education) {
            case "1":
                return "学士";
            case "2":
                return "硕士";
            case "3":
                return "博士";
            case "4":
                return "其他";
            default:
                return "--";
        }
    }
    /**
     * 性别转换
     *
     * @param sex
     * @return
     */
    private String inverseGetSex(String sex) {
        switch (sex) {
            case "1":
                return "男性";
            case "2":
                return "女性";
            default:
                return "--";
        }
    }
    /**
     * 婚姻状况转换
     *
     * @param marital
     * @return
     */
    private String inverseGetMarital(String marital) {
        switch (marital) {
            case "1":
                return "未婚";
            case "2":
                return "已婚";
            case "3":
                return "离婚";
            case "4":
                return "丧偶";
            default:
                return "--";
        }
    }
    //
    /**
     * 贷款机构代码
     * @author 
     * @date 2018/11/27
     */
    private String getCard1OrgCode(String str){
        String result = str;
        if (str==null||str.equals("")){
            return result;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (str.equals("B0")){
            result = "--";
        } else if (str.equals("A1")){
            result = "九江银行";
        } else if (isNum.matches()&&str.length()==4){
            char a1 = (char)(Integer.valueOf(str.substring(0,2)).intValue());
            char a2 = (char)(Integer.valueOf(str.substring(2)).intValue());
            result = String.valueOf(a1)+String.valueOf(a2);
        }
        return result;
    }
    /**
     *
     * @param loanPayType
     * @return
     */
    private String getLoanPayTypeR(String loanPayType) {
        switch (loanPayType){
            case "A1":return "按半年归还";
            case "A2":return "按季归还";
            case "A3":return "按年归还";
            case "A4":return "按其他方式归还";
            case "A5":return "按日归还";
            case "A6":return "按月归还";
            case "A7":return "按周归还";
            case "A8":return "不定期归还";
            case "A9":return "一次性归还";
            default:return "";
        }
    }
    /**
     *
     * @param assureType
     * @return
     */
    private String getAssureTypeR(String assureType) {
        switch (assureType){
            case "A1":return "保证";
            case "A2":return "抵押担保";
            case "A3":return "农户联保";
            case "A4":return "其他担保";
            case "A5":return "信用/免担保";
            case "A6":return "质押（含保证金）担保";
            case "A7":return "组合（不含保证）担保";
            case "A8":return "组合（含保证）担保";
            default:return "";
        }
    }
    /**
     * 贷款机构类型
     * @param loanOrgType
     * @return
     */
    private String getLoanOrgTypeR(String loanOrgType) {
        switch (loanOrgType){
            case "A1":return "村镇银行";
            case "A2":return "机构";
            case "A3":return "金融租赁公司";
            case "A4":return "汽车金融公司";
            case "A5":return "商业银行";
            case "A6":return "外资银行";
            case "A7":return "消费金融有限公司";
            case "A8":return "小额信贷公司";
            case "A9":return "信托投资公司";
            case "A10":return "财务公司";
            default:return "";
        }
    }
    /**
     * 五级分类
     * @param loanClass5
     * @return
     */
    private String getLoanClass5R(String loanClass5) {
        switch (loanClass5){
            case "C1":return"未知";
            case "C2":return"关注";
            case "C3":return"可疑";
            case "C4":return"损失";
            case "C5":return"正常";
            case "C6":return"次级";
            default:return "";
        }
    }
    /**
     * 账户状态
     * @param card1Status
     * @return
     */
    private String getLoanStatusR(String card1Status) {
        switch (card1Status){
            case "S1":return "呆账";
            case "S2":return "结清";
            case "S3":return "逾期";
            case "S4":return "正常";
            case "S5":return "转出";
            default:return  "呆账";
        }
    }
    /**
     * 产品类型：
     * @param str
     * @return
     */
    private String getLoanPrdType(String str){
        switch (str){
            case "T1" : return "个人经营性贷款";
            case "T2" : return "个人汽车贷款";
            case "T3" : return "个人商用房（包括商住两用）贷款";
            case "T4" : return "个人消费贷款";
            case "T5" : return "个人助学贷款";
            case "T6" : return "个人住房贷款";
            case "T7" : return "个人住房公积金贷款";
            case "T8" : return "农户贷款";
            case "T9" : return "其他贷款";
            default: return "其他贷款";
        }
    }
    /**
     * 币种
     * @param str
     * @return
     */
    private String getLoanCurrency(String str){
        if("A1".equals(str)){
            return "人民币";
        }else if("A2".equals(str)){
            return "其他";
        }
        return "";
    }
    /**
     *  specialModifyMonths:特殊交易变更月数
     * @param str
     * @return
     */
    private String getSpecialModifyMonthsRR(String str){
        if(str.equals("B0")){
            return "0";
        }
//        if(str.startsWith("A")){
//            return str.substring(1);
//        }else{
//            return "-"+str.substring(1);
//        }
        return str.substring(1);
    }
    /**
     * 特殊交易类型：
     * @author 
     * @param str
     * @return
     */
    private String convertSpecialTrade(String str){
        switch (str){
            case "T1":  return "其他";
            case "T2":  return "提前还款（部分）";
            case "T3":  return "提前还款（全部）";
            case "T4":  return "担保人代还";
            case "T5":  return "展期（延期）";
            default: return "";
        }
    }
    /**
     * 值转化
     * @param str 字符串
     * @param value 需要转化的值
     * @return 转义后的值
     */
    private String stringJoint(String str, String value){
        return str+value;
    }
    private String stringJoint(String str, int value){
        return stringJoint(str,value+"");
    }
    /**
     * 养老保险缴费状态
     * @param payStatus
     * @return
     */
    private String getEndowmentStatus(String payStatus) {
        switch (payStatus){
            case "P1" :return "未参保";
            case "P2" :return "参保缴费";
            case "P3" :return "暂停缴费(中断)";
            case "P4" :return "终止缴费";
            case "P5" :return "未知";
            case "P6" :return "正常";
            default:
                return "--";
        }
    }
    /**
     * 百分号转换
     * @param payStatus
     * @return
     */
    private String getPercent(char c,String payStatus) {
        return getInt(payStatus,c)+"%";
    }
    /**
     * 住房公积金缴费状态
     * @param payStatus
     * @return
     */
    private String getHousingPayStatusD(String payStatus) {
        switch (payStatus){
            case "C1" :return "封存";
            case "C2" :return "销户";
            case "C3" :return "缴交";
            default:
                return "--";
        }
    }
    /**
     * 中断或终止缴费原因
     */
    private String getInterReason(String interReason) {
        switch (interReason){
            case "P1" :return "在职人员解除劳动合同";
            case "P2" :return "在职人员参军";
            case "P3" :return "在职人员上学";
            case "P4" :return "在职人员劳改劳教";
            case "P5" :return "其他原因中断缴费";
            case "P6" :return "在职人员死亡";
            case "P7" :return "在职人员出国定居";
            case "P8" :return "在职转退休";
            case "P9" :return "农民工退保";
            case "P10" :return "其他原因终止缴费";
            case "P11" :return "在职人员解除/终止劳动合同";
            case "P12" :return "人员参军";
            case "P13" :return "人员上学";
            case "P14" :return "人员被判刑收监执行或被劳动教养";
//            case "其他原因中断缴费" :return "P15";
            case "P16" :return "死亡";
            case "P17" :return "出国定居";
//            case "在职转退休" :return "P18";
            case "P19" :return "退保";
//            case "其他原因终止缴费" :return "P20";
            case "P21" :return "人员统筹范围内转出";
            case "P22" :return "停薪留职";
            case "P23" :return "人员失踪";
            case "P24" :return "港澳台定居";
            case "P25" :return "人员转出统筹范围外";
            default:
                return "--";
        }
    }
    /**
     * 获取货币类型
     * @param value 货币类型
     * @return 转化货币类型
     */
    private String getMoneyCurrency(String value){
        if ("A1".equals(value)){
            return "人民币";
        }
        if ("A2".equals(value)){
            return "其他";
        }
        return "--";
    }

    /**
     * 养老保险缴费状态
     * @param closedManner 入参
     * @return 返回值
     */
    private String getClosedManner(String closedManner) {
        switch (closedManner){
            case "D1" :return "不予执行";
            case "D2" :return "自动履行";
            case "D3" :return "和解履行完毕";
            case "D4" :return "执行完毕";
            case "D5" :return "终结执行";
            case "D6" :return "提级执行";
            case "D7" :return "指定执行";
            case "D8" :return "其它";
            default:
                return "--";
        }
    }
    /**
     * 还款记录
     */
    private String getRecords(String data){
        if (data ==null){
            return "--";
        }
        char[] chars = data.toCharArray();
        StringBuilder result = new StringBuilder();
        for (char c : chars) {
            switch (c) {
                case 'A':
                    result.append("#");
                    break;
                case 'B':
                    result.append("*");
                    break;
                case 'C':
                    result.append("/");
                    break;
                case 'D':
                    result.append("C");
                    break;
                case 'E':
                    result.append("N");
                    break;
                case 'F':
                    result.append("G");
                    break;
                case ' ':
                    break;
                default:
                    result.append(getStr2Int(String.valueOf(c),'F'));
                    break;
            }
        }
        return result.toString();
    }
    /**
     * 字符串转数字
     * @param a 需要转的字符串
     * @param b 设为0的字符
     */
    private String getInt(String a,char b){
        if(a.equals("B0")){
            return "0";
        }
        if(a.equals("")){
            return ".";
        }
        char[] chars = a.toCharArray();
        ArrayList<Integer> integers = new ArrayList<>();
        String s = "";
        for ( char c: chars) {
            int v = c - 'A'-(b-'A');
            s = s+v;
        }
        return s;
    }
    private String getInt2(String a,char b){
        if(a.equals("B0")){
            return "0";
        }
        if(a.equals("")){
            return ".";
        }
        char[] chars = a.toCharArray();
        ArrayList<Integer> integers = new ArrayList<>();
        String s = "";
        for ( char c: chars) {
            int v = c - 'A'-(b-'A');
            s = s+v;
        }
        return s+" ";
    }
    /**
     * 数字转为英文大写字母 0对照C -->  反转
     * @author 
     * @date 2018/11/28
     */
    private String getStr2Int(String a,char b){
        if(a==null || a.isEmpty() || a.equals("B0")){
            return "0";
        }
        char[] chars = a.toCharArray();
        String s = "";
        for ( char c: chars) {
            int v = c - 'A'-(b-'A');
            s = s+v;
        }
        return s;
    }
    /**
     * 字符转中文
     * @param src 待转换字符串
     * @param password 转换密码
     * @return
     */
    private String getChineseStr(String src,char password){
        String string = "";
        if(src ==null || src.equals("") ||  src.equals("B0")){
            return string;
        }
        String[] strings = src.split("#");
        for(int i =0 ;i<strings.length;i++){
            String str = strings[i];
            StringBuffer str2 = new StringBuffer();
            char[] charArr = new char[str.length()];
            char ss[] = str.toCharArray();
            for(int j=0;j<ss.length;j++){
                charArr[j] = (char)(ss[j]-password+'0');
            }
            for(int j=0;j<charArr.length;j++){
                str2.append(charArr[j]);
            }
            string +=(char) Long.parseLong(str2.toString());
        }
        return string;
    }
    /**
     * 字符型日期转换为数值型日期 [AAAABBCC => yyyyMMdd]
     * @param dateStr 日期
     * @param c 字符
     * @return
     */
    private String convertToDateNumber(String dateStr , char c){
        if (dateStr.isEmpty()){
            return dateStr;
        }
        StringBuilder sb = new StringBuilder();
        char[] dateArray = dateStr.toCharArray();
        for (int i = 0; i < dateArray.length; i++) {
            int val = dateArray[i];
            val -= c;
            sb.append(val);
        }
        String res = sb.toString();
        return res;
    }
    /**
     * 值转化 p21 -> 21
     * @param str 字符串
     * @param value 需要转化的值
     * @return 转回来的值
     */
    private String separateString(String str,Object value){
        if(str == null || str.equals("B0") || str.equals("")){
            return "--";
        }else{
            return str.substring(value.toString().length(),str.length());
        }
    }
    /**
     * 数值型日期格式化[yyyyMMdd => yyyy.MM.dd]
     * @author 
     * @param charDate 日期
     * @param c 字符
     * @return
     */
    private String formatDateNumber(String charDate , char c){
        if("B0".equals(charDate) || charDate.isEmpty()){
            return "";
        }
        String date = convertToDateNumber(charDate , c);
        char[] chars = date.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            sb.append(chars[i]);
            if((i == 3) || (i == 5 && charDate.length()>6)){
                sb.append(".");
            }
        }
        return sb.toString();
    }
    /**
     * 转换日期段[AAAABBCC#DDDDCCEE => yyyyMM-yyyyMM]
     * @author 
     * @param dateStr
     * @param c
     * @return
     */
    private String castToDateRange(String dateStr , char c){
        if (dateStr==null||dateStr.isEmpty()||"B0".equals(dateStr)||"".equals(dateStr)){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        char[] dateArray = dateStr.toCharArray();
        char[] tmpArray = new char[13];
        int j= 0;
        for (int i = 0 ; i < dateArray.length; i++) {

            char tmp = dateArray[i];
            if('B' == tmp){
                continue;
            }
            tmpArray[j] = dateArray[i];
            j++;
        }

        for (int i = 0; i < tmpArray.length; i++) {
            if(i== 6){
                sb.append('-');
            }else {
                int val = tmpArray[i];

                if(val != 48){ //48 -> 0
                    val -= c;
                }else{
                    val =0;
                }
                sb.append(val);
            }
            if(i==3 || i == 10){
                sb.append("年");
            }else if(i==5 || i == 12){
                sb.append("月");
            }
        }
        sb.append("的还款记录");
        return sb.toString();
    }
    /**
     * @author 
     * @date 2018/11/28
     */
    private String getClosedType(String str){
        if (str==null || str.isEmpty()){
            return str;
        }
        switch (str){
            case "C1" :return "判决";
            case "C2" :return "调解";
            case "C3" :return "其他";
            default:
                return str;
        }
    }
    private String getPersonnelType(String str){
        if (str==null || str.isEmpty()){
            return str;
        }
        switch (str){
            case "C1" :return "在职职工";
            case "C2" :return "离岗";
            case "C3" :return "失业";
            case "C4" :return "离退休人员";
            case "C5" :return "三无人员";
            case "C6" :return "居民";
            case "C7" :return "学生";
            default:
                return str;
        }
    }
    //******************xj begin********************//
    /**
     *  CARD2 policy2  特殊交易类型
     * @param str
     * @return
     */
    private String getSpecialTrade(String str){
        switch (str){
            case "T1":  return "其他";
            case "T2":  return "提前还款（部分）";
            case "T3":  return "提前还款（全部）";
            case "T4":  return "担保人代还";
            case "T5":  return "展期（延期）";
            default: return "--";
        }
    }
    /**
     *  GUALOAN policy2  担保贷款五级分类
     * @param str
     * @return
     */
    private String getGuaLoanType(String str){
        switch (str){
            case "A1":  return "正常";
            case "A2":  return "关注";
            case "A3":  return "次级";
            case "A4":  return "可疑";
            case "A5":  return "损失";
            case "A9":  return "未知";
            default: return "--";
        }
    }
    /**
     * policy9  使用性质
     * @param str
     * @return
     */
    private String getPledgeFlag(String str){
        switch (str){
            case "A0":  return "未抵押";
            case "A1":  return "已抵押";
            default: return "--";
        }
    }
    /**
     * policy9  使用性质
     * @param str
     * @return
     */
    private String getState(String str){
        switch (str){
            case "A":  return "正常";
            case "B":  return "转出";
            case "C":  return "被盗抢";
            case "D":  return "停驶";
            case "E":  return "注销";
            case "G":  return "违法未处理";
            case "H":  return "海关监管";
            case "I":  return "事故未处理";
            case "J":  return "嫌疑车";
            case "K":  return "查封";
            case "L":  return "暂扣";
            case "M":  return "强制注销";
            case "N":  return "事故逃逸";
            case "O":  return "锁定";
            case "Z":  return "其它";
            default: return "--";
        }
    }
    /**
     * policy9  使用性质
     * @param str
     * @return
     */
    private String getUseCharacter(String str){
        switch (str){
            case "B":  return "半挂车";
            case "D":  return "电车";
            case "G":  return "全挂车";
            case "H":  return "货车";
            case "J":  return "轮式机械";
            case "K":  return "客车";
            case "M":  return "摩托车";
            case "N":  return "三轮汽车";
            case "Q":  return "半挂牵引车";
            case "T":  return "拖拉机代码";
            case "Z":  return "专项作业车";
            case "X":  return "其它";
            default: return "--";
        }
    }
    /**
     * policy7  等级
     * @param str
     * @return
     */
    private String getGrade(String str){
        switch (str){
            case "C1":  return "国家级机构颁发的执业资格证书";
            case "C2":  return "省市级机构颁发的执业资格证书";
            case "C3":  return "地市级机构颁发的执业资格证书";
            case "C4":  return "独立行业协会或制订行业标准的企业颁发的执业资格证书";
            case "C5":  return "其他机构颁发的执业资格证书";
            default: return "--";
        }
    }
    //******************xj end********************//
    /**
     *  当前缴费状态
     * @param status
     * @return
     */
    private String getStateT(String status){
        switch (status){
            case "A0":return "正常";
            case "A1":return "欠费";
            default:return "";
        }
    }
    /**
     * 离退休类别
     * @param str
     * @return
     */
    private String getRetireType(String str) {
        switch (str){
            case "C1" :return "离休";
            case "C2" :return "正常退休";
            case "C3" :return "退职";
            case "C4" :return "因病提前退休/因病退休";
            case "C5" :return "特殊工种提前退休/特殊工种退休";
            case "C6" :return "工伤提前退休/原工伤退休";
            case "C7" :return "政策性提前退休";
            case "C8" :return "特殊政策提前退休";
            case "C9" :return "其他提前退休";
            default:
                return "其它";
        }
    }
    /**
     * 停发原因
     * @param str
     * @return
     */
    private String getPauseReason(String str) {
        switch (str){
            case "A1" :return "离退休人员未提供生存证明";
            case "A2" :return "离退休人员劳改劳教";
            case "A3" :return "离退休人员失踪";
            case "A4" :return "离退休人员出国定居";
            case "A5" :return "离退休人员死亡";
            case "A6" :return "其他原因停发养老金";
            case "C2" :return "离退休人员被判刑收监执行或被劳动教养";
            case "C5" :return "银行帐号错误";
            case "C6" :return "邮寄地址错误";
            case "C9" :return "其他原因暂停养老待遇";
            case "D3" :return "离退休人员港澳台定居";
            default:
                return "";
        }
    }
    /**
     * 账户类别
     * @param str
     * @return
     */
    private String getAcctType(String str){
        switch (str){
            case "A1" : return "贷款";
            case "A2" : return "贷记卡";
            case "A3" : return "准贷记卡";
            default: return "";
        }
    }
    /**
     *
     * @param str
     * @return
     */
    private Integer getNum(String str , String c){
        String res = "";

        if(str.startsWith(c)){
            res = str.substring(1);
        }
        Integer r = 0;
        if("--".equals(res)||"".equals(res)){
            return r;
        }

        return Integer.valueOf(res);
    }


    public static void main(String[] args) {
    	CreditInfo creditInfo = new CreditInfo();
        StringBuffer json = new StringBuffer();       
        //Resource resource = new ClassPathResource("credit.json");
        String reportFile = "C:\\Users\\admin\\Desktop\\440102197502076016.txt";  //本地
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(reportFile)))) {
            String s;
            while ((s = br.readLine()) != null) {
                json.append(s);
            }
        } catch (IOException e) {
            logger.error(""+e.getMessage());
        }
        
        try {
			String rsaStr =  RSAUtils.RSADecoder(json.toString());
			System.out.println(rsaStr);
			String a  = creditInfo.creditInfoTheParsing(rsaStr);
			System.out.println(a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
            logger.error(""+e.getMessage());
		}
    }
    
    private static String rev(String str) {
    	if(null==str||"0".equals(str)||"".equals(str)){
    		return "--";
    	}
		return str;
	}
    
    private static String ren(String str) {
    	if(null==str||"0".equals(str)||"".equals(str)){
    		return null;
    	}
		return str;
	}
    
    private static long rel(String str) {
    	if(null==str||"0".equals(str)||"".equals(str)){
    		return new Long("253370736000");
    	}
		return Long.parseLong(str);
	}
    
}
