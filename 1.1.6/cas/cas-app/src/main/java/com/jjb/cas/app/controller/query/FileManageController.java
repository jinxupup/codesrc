package com.jjb.cas.app.controller.query;

import com.jjb.cas.app.controller.CasDataController;
import com.jjb.ecms.biz.service.query.ApplyProcessQueryService;
import com.jjb.ecms.biz.util.BizAuditHistoryUtils;
import com.jjb.ecms.facility.dto.ApplyProcessQueryDto;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 进件归档管理
 * @author 高树冲
 * @date 2017年3月15日 下午4:56:21
 * @version V1.0
 */
@Controller
@RequestMapping("/fileManage")
public class FileManageController extends CasDataController {
    @Autowired
    private ApplyProcessQueryService applyProcessQueryService;
    @Autowired
    private BizAuditHistoryUtils bizAuditHistoryUtils;


    /**
     * 进件归档管理页面
     * @return
     */
    @RequestMapping("/fileManagePage")
    public String fileManagePage(){

        return "casTask/apply/applyFileManage/fileManage.ftl";
    }

    /**
     * 归档操作页面
     * @return
     */
    @RequestMapping("/fileManagePageDetail")
    public String filedPage(){

        return "casTask/applyFileManage/fileDetailPage_V1.ftl";
    }

    /**
     * 归档查询
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/fileList")
    public Page<ApplyProcessQueryDto> applyProcessList() {
        Query query = getBasicQuery();
        String[] rtfStates = getParas("rtfState");
        query.put("rtfState", rtfStates);
        Page<ApplyProcessQueryDto> page = getPage(ApplyProcessQueryDto.class);
        page.setQuery(query);
        //保存设计记录
//        bizAuditHistoryUtils.saveAuditHistoryByOperatorId("进件归档管理--条件搜索: "+page.getQuery());
        page = applyProcessQueryService.applyProcessList(page);
        return page;
    }


}
