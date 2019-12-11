package com.jjb.ecms.app.controller.manage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.cas.biz.pbocQuery.PbocQueryService;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * @Description: TODO   中联惠捷征信查询管理
 * @Author: shiminghong
 * @Data: 2019/6/4 17:58
 * @Version 1.0
 */

@Controller
@RequestMapping("_pbocQuery")
public class PbocQueryController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private PbocQueryService pbocQueryService;
    @Autowired
    private ApplyInputService applyInputService;

    /**
     * 惠捷征信查询页面
     *
     * @return
     */
    @RequestMapping("/pbocQueryPage")
    public String getPage() {
        return "applyManage/applyPbocQueryManage/pbocQueryPage.ftl";
    }

    /**
     * 惠捷征信查询
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/pbocQuery")
    public Json pbocQuery() {
        Json json = Json.newSuccess();
        Query query = getQuery();
        String name = String.valueOf(query.get("name"));
        String idNo = String.valueOf(query.get("idNo"));
        String nameValue = name == null ? "" : name.toString().trim();//去空格
        String idNoValue = idNo == null ? "" : idNo.toString().trim();//去空格
        TmAppHistory tmAppHistory = null;
        String  filePath="";

        //查询系统里面已有的客户信息
/*
        Map<String,Object> parameter = new HashMap<String,Object>();
        parameter.put("idNo",idNo);
        parameter.put("name",nameValue);
        List<TmAppMain> tmAppMainList= tmAppMainDao.getTmAppMainByParam(parameter);
        if (null==tmAppMainList&&tmAppMainList.size()==0){
            throw new ProcessException("此客户信息不存在");
        }
        TmAppMain appMain=null;
        for(int i=0;i<tmAppMainList.size();i++){
            appMain=tmAppMainList.get(0);
        }
        String appNo=appMain.getAppNo();
        if (StringUtils.isBlank(appNo)){
            throw  new ProcessException("申请件编号为空，系统中该客户信息异常");
        }
*/
        try {
            /**
             * 中联惠捷征信查询
             */
            filePath = pbocQueryService.pbocQuery(nameValue, idNoValue);
/*            JSONObject respJson = JSON.parseObject(respJs);
            JSONObject js1 = respJson.getJSONObject("response");
            String url = js1.getString("resultInfo");*/
            tmAppHistory = AppCommonUtil.insertApplyHist("",
                    OrganizationContextHolder.getUserNo(), null, "", "惠捷征信查询返回的URL为： " + filePath);
            json.setMsg(filePath);
        } catch (Exception e) {
            json.setFail("调用征信查询失败:" + e.getMessage());
            json.setS(false);
            logger.error("[" + name + "]" + "调用征信查询失败:" + e.getMessage());
            tmAppHistory = AppCommonUtil.insertApplyHist("",
                    OrganizationContextHolder.getUserNo(), null, "", "调用征信查询失败:" + e.getMessage());
        } finally {
            tmAppHistory.setName(nameValue);
            tmAppHistory.setIdNo(idNoValue);
            tmAppHistory.setProName("征信查询");
            applyInputService.saveTmAppHistory(tmAppHistory);
        }
        return json;
    }
}
