/**
 *
 */
package com.jjb.cas.app.controller.query;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.acl.facility.enums.bus.EcmsAuthority;
import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.cas.app.controller.CasDataController;
import com.jjb.ecms.biz.dao.apply.TmAppMainDao;
import com.jjb.ecms.biz.dao.apply.TmAppModifyHisDao;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.common.CommonService;
import com.jjb.ecms.biz.service.manage.AbnormalProcessAppService;
import com.jjb.ecms.biz.service.manage.ApplyInfoQueryService;
import com.jjb.ecms.biz.service.query.ApplyProcessQueryService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.BizAuditHistoryUtils;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.facility.dto.ApplyInfoQueryDto;
import com.jjb.ecms.facility.dto.ApplyProcessQueryDto;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppMemo;
import com.jjb.ecms.infrastructure.TmAppModifyHis;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @author JYData-R&D-BigK.K
 * @version V1.0
 * @Description: 申请进度查询
 * @date 2016年9月18日 上午9:57:08
 */
@Controller
@RequestMapping("/cas_applyQuery")
public class CasApplyQueryController extends CasDataController {
    @Autowired
    private ApplyProcessQueryService applyProcessQueryService;
    @Autowired
    private ApplyQueryService applyQueryService;
    @Autowired
    private ApplyInfoQueryService applyInfoQueryService;
    @Autowired
    private ApplyInputService applyInputService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private AbnormalProcessAppService abnormalProcessAppService;
    @Autowired
    private TmAppModifyHisDao tmAppModifyHisDao;
    @Autowired
    private TmAppMainDao appMainDao;
    @Autowired
    private BizAuditHistoryUtils bizAuditHistoryUtils;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 加载页面
     *
     * @return
     */
    @RequestMapping("/applyProcessQuery")
    public String applyProcessQuery() {

        Query query = getBasicQuery();
        if (query != null) {
            setAttr("query", query);
        }
        return "casManage/applyProgress/casApplyProcessPage.ftl";
    }

    /**
     * 审批进度查询
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/applyProcessList")
    public Page<ApplyProcessQueryDto> applyProcessList() {
        Query query = getBasicQuery();
        String[] rtfStates = getParas("rtfState");
        query.put("rtfState", rtfStates);
        Page<ApplyProcessQueryDto> page = getPage(ApplyProcessQueryDto.class);
        page.setQuery(query);
        page = applyProcessQueryService.applyProcessList(page);
        return page;
    }

    /**
     * 判断用户申请进度查询详情权限
     */
    @ResponseBody
    @RequestMapping("/viewDetailPermissions")
    public Json viewDetailPermissions() {
        String permission = getPara("permission");
        Json json = Json.newSuccess();
        String org = OrganizationContextHolder.getOrg();
        String userNo = OrganizationContextHolder.getUserNo();
        List<String> resourceCodeList = new ArrayList<String>();
        if (StringUtils.isNotEmpty(org) && StringUtils.isNotEmpty(userNo)) {
            resourceCodeList = commonService.getCurrUserResourceCodeList();
        }
        if (CollectionUtils.isNotEmpty(resourceCodeList)) {
            if (permission != null && permission.equals("false")) {//申请进度查询详细页面
                if (!resourceCodeList.contains(EcmsAuthority.APPLY_PROCESS_QUERY_DETAIL.name())) {
                    String msg = "无查看详情权限！机构号[" + org + "],用户名[" + userNo + "]";
                    json.setFail(msg);
                }
            }
            if (permission != null && permission.equals("true")) {//重审权限
                if (!resourceCodeList.contains(EcmsAuthority.APPLY_REAUDIT.name())) {
                    String msg = "无重审权限！机构号[" + org + "],用户名[" + userNo + "]";
                    json.setFail(msg);
                }
            }
        }
        return json;
    }

    /**
     * 申请件进度查询的修改页面
     * 保存操作
     */
    @ResponseBody
    @RequestMapping("/applyModifyInfoSave")
    public Json applyModifyInfoSave() {
        Json json = Json.newSuccess();

      try{
        Query query = getBasicQuery();
        TmAppMain appNewPage = getBean(TmAppMain.class);
        String appNo = appNewPage.getAppNo();
        TmAppMain appOldPage = appMainDao.getTmAppMainByAppNo(appNo);
        setBasicData(query, "申请件进度查询的修改", "I", logger);
        TmAppModifyHis tmAppModifyHis = new TmAppModifyHis();
        tmAppModifyHis.setTableName("申请人信息");
        tmAppModifyHis.setColumnName("accLmt");
        tmAppModifyHis.setNewValue(String.valueOf(appNewPage.getAccLmt()));
        tmAppModifyHis.setOldValue(String.valueOf(appOldPage.getAccLmt()));
        tmAppModifyHis.setUpdateUser(OrganizationContextHolder.getUserNo());
        tmAppModifyHis.setUpdateTime(new Date());
        tmAppModifyHis.setOrg(OrganizationContextHolder.getOrg());
        tmAppModifyHis.setTaskName("申请件进度查询的修改");
        tmAppModifyHis.setAppNo(appNo);
//					tmAppModifyHis.setJpaVersion(1);
        tmAppModifyHis.setReservedField1("核准额度");
        tmAppModifyHisDao.saveTmAppModifyHis(tmAppModifyHis);
          //推广人注记
          String spreaderMemo = StringUtils.valueOf(query.get("memoInfo"));
          if(StringUtils.isNotBlank(spreaderMemo)){
              TmAppMemo tmAppMemo = new TmAppMemo();
              tmAppMemo.setAppNo(appNo);
              tmAppMemo.setMemoType(AppConstant.APP_MEMO);
              tmAppMemo.setTaskKey(EcmsAuthority.INPUT.name());
              tmAppMemo.setTaskDesc(EcmsAuthority.INPUT.lab);
              if(appNewPage.getRtfState()!=null) {
                  tmAppMemo.setRtfState(appNewPage.getRtfState());
              }
              tmAppMemo.setMemoInfo(spreaderMemo);
              applyInputService.saveTmAppMemo(tmAppMemo);
          }
    }catch (Exception e) {
            // TODO: handle exception
            logger.error("申请件进度查询的修改保存数据异常");
            throw new ProcessException("申请件保存数据异常",e);
        }
        json.setMsg("操作成功！");
        return json;
    }

    /**
     * 导出到excel
     *
     * @throws ParseException
     */

/*    @RequestMapping("/exportApplyQueryExcel")
    public void exportToExcel() {
        Query query = getBasicQuery();
        String[] rtfStates = getParas("rtfState");
        query.put("rtfState", rtfStates);
        Page<ApplyProcessQueryDto> page = getPage(ApplyProcessQueryDto.class);
        page.setQuery(query);
        page.setPageSize(0);//此处不分页

        applyProcessQueryService.applyProcessList(page);

        List<ApplyProcessQueryDto> list = page.getRows();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("申请件进度查询信息表");
        sheet.autoSizeColumn((short) 0); //调整第一列宽度
        sheet.autoSizeColumn((short) 10);
        HSSFRow row = sheet.createRow(0);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        String[] rowHeaders = {"申请件编号", "姓名", "证件类型", "证件号码", "卡号","申请类型", "卡产品代码", "受理网点", "移动电话", "审批状态", "任务所属人", "影像", "授信额度","拒绝原因"};
        HSSFCell cell;
        for (int i = 0; i < rowHeaders.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(rowHeaders[i]);
            cell.setCellStyle(style);
        }
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            cell = row.createCell(0);
            cell.setCellValue(list.get(i).getAppNo());
            cell = row.createCell(1);
            cell.setCellValue(list.get(i).getName());
            cell = row.createCell(2);
            cell.setCellValue(list.get(i).getIdType());
            cell = row.createCell(3);
            cell.setCellValue(list.get(i).getIdNo());

            cell = row.createCell(4);
            cell.setCellValue(list.get(i).getCardNo());

            cell = row.createCell(5);
            cell.setCellValue(list.get(i).getAppType());
            cell = row.createCell(6);
            cell.setCellValue(list.get(i).getProductCd());
            cell = row.createCell(7);
            cell.setCellValue(list.get(i).getOwningBranch());
            cell = row.createCell(8);
            cell.setCellValue(list.get(i).getCellPhone());
            cell = row.createCell(9);
            cell.setCellValue(list.get(i).getRtfState());
            cell = row.createCell(10);
            cell.setCellValue(list.get(i).getOwner());
            cell = row.createCell(11);
            cell.setCellValue(list.get(i).getImageNum());
            cell = row.createCell(12);
            cell.setCellValue(list.get(i).getAccLmt());
            cell = row.createCell(13);
            cell.setCellValue(list.get(i).getRefuseCode());
        }
        try {
            HttpServletResponse response = getResponse();
            String fileName = "申请件进度查询信息表.xls";
            fileName = URLEncoder.encode(fileName, "UTF8");
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            OutputStream ouputStream = response.getOutputStream();
            workbook.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (IOException e) {
            logger.info("申请件进度查询信息表下载失败！" + e.getMessage());
        }
    }*/








    /**
     * 删除该申请件进度
     */
    @ResponseBody
    @RequestMapping("/applyQueryDelete")
    public Json delete(String appNo ,String remark) {
        bizAuditHistoryUtils.saveAuditHistory(appNo, "删除申请件");
        Long tokenId = System.currentTimeMillis();
        logger.info("删除申请件========>" + LogPrintUtils.printAppNoLog(appNo, tokenId, null));
        Json j = Json.newSuccess();
        try {
            if (StringUtils.isEmpty(appNo)) {
                j.setMsg("未查询到申请件[" + appNo + "]信息,无法执行后续操作");
            }
            abnormalProcessAppService.delete(appNo);
            TmAppMain main = applyQueryService.getTmAppMainByAppNo(appNo);
            main.setRtfState(RtfState.A20.name());
            main.setFileFlag("F");
            applyInputService.updateTmAppMain(main);

            //保存删除记录备份
            TmAppMemo tmAppMemo = new TmAppMemo();
            tmAppMemo.setOrg(main.getOrg());
            tmAppMemo.setAppNo(appNo);
            tmAppMemo.setMemoType(AppConstant.APP_REMARK);
//            tmAppMemo.setTaskKey("删除系统自动备注");
            tmAppMemo.setTaskKey("CAS_APPLY_DELETE");
            tmAppMemo.setRtfState(main.getRtfState());
            tmAppMemo.setMemoVersion(1);
            tmAppMemo.setMemoInfo(remark);//备注
            tmAppMemo.setTaskDesc("人员操作删除");
            tmAppMemo.setCreateDate(new Date());
            tmAppMemo.setCreateUser(main.getCreateUser());
            tmAppMemo.setJpaVersion(main.getJpaVersion());
            applyInputService.saveTmAppMemo(tmAppMemo);


            TmAppHistory appHistory = AppCommonUtil.insertApplyHist(appNo, OrganizationContextHolder.getUserName(),
                    RtfState.A20, "", remark);
            applyInputService.saveTmAppHistory(appHistory);
//            TmAppAudit audit = applyQueryService.getTmAppAuditByAppNo(appNo);
//            if (audit!=null && "Y".equals(audit.getIsInstalment())) {
//                TmAppInstalLoan appInstalLoan = new TmAppInstalLoan();
//                appInstalLoan.setAppNo(appNo);
//                appInstalLoan = appInstalLoanDao.queryForOne(appInstalLoan);
//                if (appInstalLoan != null) {
//                    appInstalLoan.setStatus(RtfState.A20.name());
//                    appInstalLoanDao.updateByAppNo(appInstalLoan);
//                    logger.info("录入无效删除分期贷款信息,appNo:{}", appNo);
//                }
//            }
        } catch (Exception e) {
            logger.error("删除申请件[" + appNo + "]失败", e);
            j.setFail("删除申请件[" + appNo + "]失败" + e.getMessage());
        }
        logger.info("删除申请件========>" + LogPrintUtils.printAppNoEndLog(appNo, tokenId, null));
        return j;
    }

    /**
     * 申请件信息查询
     *
     * @return
     */
    @RequestMapping("/applyInfoQuery")
    public String applyInfoQuery() {

        Query query = getQuery();
        if (query != null) {
            setAttr("query", query);
        }
        return "casManage/applyInfo/applyInfoQuery.ftl";
    }

    /**
     * @Author smh
     * @Description TODO 申请件信息查询按钮
     * @Date 2018/12/3 14:28
     */
    @ResponseBody
    @RequestMapping("/applyInfoList")
    public Page<ApplyInfoQueryDto> applyInfoList() {
        Query query = getQuery();
        String[] rtfStates = getParas("rtfState");
        query.put("rtfState", rtfStates);
        Page<ApplyInfoQueryDto> page = getPage(ApplyInfoQueryDto.class);
        page.setQuery(query);
        page = applyInfoQueryService.applyInfoList(page);
        return page;
    }


    /**
     *@Author smh
     *@Description TODO 导出到excel,申请件信息查询导出
     *@Date 2019/1/9 17:11
     */
    @RequestMapping("/exportExcelInfo")
    public void exportToExcelInfo() {
        Integer rtfStatesNum = 1;
        Query query = getQuery();
        String[] rtfStates = getParas("rtfState");
        query.put("rtfState", rtfStates);
        Page<ApplyInfoQueryDto> page = getPage(ApplyInfoQueryDto.class);
        page.setQuery(query);
        page.setPageSize(0);//此处不分页
        page=applyInfoQueryService.applyInfoList(page);
        List<ApplyInfoQueryDto> list = page.getRows();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("申请件信息查询信息表");
        sheet.autoSizeColumn((short) 0); //调整第一列宽度
        sheet.autoSizeColumn((short) 10);
        HSSFRow row = sheet.createRow(0);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        String[] rowHeaders = {"申请件编号","卡号", "姓名", "证件类型", "证件号码", "客户类型","申请渠道","审批状态",
                "公司名称", "公司电话", "推广人编号", "预审人工号",  "录入员", "复核员", "初审员","补件操作员",
                "电话调查员","终审员","推广主管代码","推广团队代码","推广区域代码","授信额度","任务所属人"};
        HSSFCell cell;
        for (int i = 0; i < rowHeaders.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(rowHeaders[i]);
            cell.setCellStyle(style);
        }
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            cell = row.createCell(0);
            cell.setCellValue(list.get(i).getAppNo());
            cell = row.createCell(1);
            cell.setCellValue(list.get(i).getCardNo());
            cell = row.createCell(2);
            cell.setCellValue(list.get(i).getName());
            cell = row.createCell(3);
            cell.setCellValue(list.get(i).getIdType());
            cell = row.createCell(4);
            cell.setCellValue(list.get(i).getIdNo());
            cell = row.createCell(5);
            cell.setCellValue(list.get(i).getCustType());
            cell = row.createCell(6);
            cell.setCellValue(list.get(i).getAppSource());
            cell = row.createCell(7);
            cell.setCellValue(list.get(i).getRtfState());
            cell = row.createCell(8);
            cell.setCellValue(list.get(i).getCorpName());
            cell = row.createCell(9);
            cell.setCellValue(list.get(i).getEmpPhone());
            cell = row.createCell(10);
            cell.setCellValue(list.get(i).getSpreaderNo());
            cell = row.createCell(11);
            cell.setCellValue(list.get(i).getPreNo());
            cell = row.createCell(12);
            cell.setCellValue(list.get(i).getInputNo());
            cell = row.createCell(13);
            cell.setCellValue(list.get(i).getReviewNo());
            cell = row.createCell(14);
            cell.setCellValue(list.get(i).getCheckNo());
            cell = row.createCell(15);
            cell.setCellValue(list.get(i).getPatchBoltNo());
            cell = row.createCell(16);
            cell.setCellValue(list.get(i).getPhoneNo());
            cell = row.createCell(17);
            cell.setCellValue(list.get(i).getFinalNo());
            cell = row.createCell(18);
            cell.setCellValue(list.get(i).getSpreaderSupCode());
            cell = row.createCell(19);
            cell.setCellValue(list.get(i).getSpreaderTeamCode());
            cell = row.createCell(20);
            cell.setCellValue(list.get(i).getSpreaderAreaCode());
            cell = row.createCell(21);
            cell.setCellValue(list.get(i).getAccLmt());
            cell = row.createCell(22);
            cell.setCellValue(list.get(i).getAccLmt());
            cell = row.createCell(23);
            cell.setCellValue(list.get(i).getTaskOwner());
        }
        if (rtfStates != null) {
            query.remove("rtfState");
            for (String rtfState : rtfStates) {
                query.put("rtfState"+rtfStatesNum, rtfState);
                rtfStatesNum++;
            }
        }
        bizAuditHistoryUtils.saveAuditHistoryByOrdType("申请件信息数据导出");
        try {

            HttpServletResponse response = getResponse();
            String fileName = "申请件信息查询信息表.xls";
            fileName = URLEncoder.encode(fileName, "UTF8");
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            OutputStream ouputStream = response.getOutputStream();
            workbook.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (IOException e) {
            logger.info("申请件信息查询信息表下载失败！" + e.getMessage());
        }
    }

    /**
     *@Author smh
     *@Description TODO  导出到excel,审批进度查询导出
     *@Date 2019/1/9 17:11
     */
    @RequestMapping("/exportExcelProcess")
    public void exportExcelProcess() {
        Integer rtfStatesNum = 1;
        Query query = getBasicQuery();
        String[] rtfStates = getParas("rtfState");
        query.put("rtfState", rtfStates);
        Page<ApplyProcessQueryDto> page = getPage(ApplyProcessQueryDto.class);
        page.setQuery(query);
        page.setPageSize(0);//此处不分页

        page=applyProcessQueryService.applyProcessList(page);

        List<ApplyProcessQueryDto> list = page.getRows();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("审批进度查询信息表");
        sheet.autoSizeColumn((short) 0); //调整第一列宽度
        sheet.autoSizeColumn((short) 10);
        HSSFRow row = sheet.createRow(0);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        String[] rowHeaders = {"申请件编号", "姓名", "证件类型", "证件号码", "卡号","申请类型代码", "申请卡产品代码", "受理网点", "移动电话", "审批状态", "任务所属人", "上一操作人", "授信额度","拒绝原因"};
        HSSFCell cell;
        for (int i = 0; i < rowHeaders.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(rowHeaders[i]);
            cell.setCellStyle(style);
        }
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            cell = row.createCell(0);
            cell.setCellValue(list.get(i).getAppNo());
            cell = row.createCell(1);
            cell.setCellValue(list.get(i).getName());
            cell = row.createCell(2);
            cell.setCellValue(list.get(i).getIdType());
            cell = row.createCell(3);
            cell.setCellValue(list.get(i).getIdNo());
            cell = row.createCell(4);
            cell.setCellValue(list.get(i).getCardNo());
            cell = row.createCell(5);
            cell.setCellValue(list.get(i).getAppType());
            cell = row.createCell(6);
            cell.setCellValue(list.get(i).getProductCd());
            cell = row.createCell(7);
            cell.setCellValue(list.get(i).getOwningBranch());
            cell = row.createCell(8);
            cell.setCellValue(list.get(i).getCellPhone());
            cell = row.createCell(9);
            cell.setCellValue(list.get(i).getRtfState());
            cell = row.createCell(10);
            cell.setCellValue(list.get(i).getOwner());
            cell = row.createCell(11);
            cell.setCellValue(list.get(i).getTaskLastOpUser());
            cell = row.createCell(12);
            cell.setCellValue(list.get(i).getAccLmt());
            cell = row.createCell(13);
            cell.setCellValue(list.get(i).getRefuseCode());
        }
        if (rtfStates != null) {
            query.remove("rtfState");
            for (String rtfState : rtfStates) {
                query.put("rtfState"+rtfStatesNum, rtfState);
                rtfStatesNum++;
            }
        }
        bizAuditHistoryUtils.saveAuditHistoryByOrdType("审批进度查询数据导出");
        try {
            HttpServletResponse response = getResponse();
            String fileName = "申请件进度查询信息表.xls";
            fileName = URLEncoder.encode(fileName, "UTF8");
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            OutputStream ouputStream = response.getOutputStream();
            workbook.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (IOException e) {
            logger.info("申请件进度查询信息表下载失败！" + e.getMessage());
        }

    }

}
