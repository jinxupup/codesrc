package com.jjb.ecms.app.controller.manage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jjb.acl.biz.utils.SystemAuditHistoryUtils;
import com.jjb.ecms.biz.util.BizAuditHistoryUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.manage.ApplyFileUploadDao;
import com.jjb.ecms.biz.ext.risk.CellBjjRiskSysSupport;
import com.jjb.ecms.biz.service.manage.ApplyFileUploadService;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.biz.util.http.HttpClientUtil2;
import com.jjb.ecms.biz.util.http.HttpHelper;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.infrastructure.TmAppUpload;
import com.jjb.ecms.util.HttpClientUtil;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * @ClassName CreditReportController
 * @Description TODO  决策跑批的相关操作
 * @Author smh
 * Date 2019/1/16 14:59
 * Version 1.0
 */
@Controller
@RequestMapping("/batchDecision")
public class BatchDecisionController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(CellBjjRiskSysSupport.class);

    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private ApplyFileUploadDao applyFileUploadDao;
    @Autowired
    private ApplyFileUploadService applyFileUploadService;
    @Autowired
    private BizAuditHistoryUtils bizAuditHistoryUtils;
    @Autowired
    private SystemAuditHistoryUtils systemAuditHistoryUtils;


    /**
     * @Author smh
     * @Description TODO  跳转到上传处理页面
     * @Date 2019/1/16 15:02
     */
    @RequestMapping("/uploadPage")
    public String uploadPage() {
        return "batchDecision/batchDecisionPage.ftl";
    }

    /**
     * @Author smh
     * @Description TODO  跳转到上传处理页面
     * @Date 2019/1/18 14:41
     */
    @RequestMapping("/fileUploadList")
    public String fileUploadList(){
        return "batchDecision/batchDecisionPage.ftl";
    }

    /**
     * @Author smh
     * @Description TODO  删除某条上传记录
     * @Date 2019/1/17 17:58
     */
    @ResponseBody
    @RequestMapping("/delete")
    public Json delete(int id){
        Json json = Json.newSuccess();
        TmAppUpload tmAppUploadByKey = applyFileUploadService.getTmAppUploadByKey(id);
        applyFileUploadService.deleteTmAppUpload(id);
        //记录审计历史
        systemAuditHistoryUtils.saveSystemAudit("文件名称: "+tmAppUploadByKey.getFileName(),"决策跑批","DELETE",tmAppUploadByKey.convertToMap().toString(),"");
        return json;
    }

    /**
     * @Author smh
     * @Description TODO  分页查询
     * @Date 2019/1/17 17:58
     */
    @ResponseBody
    @RequestMapping("/list")
    public Page<TmAppUpload> list(){
        Page<TmAppUpload> page = getPage(TmAppUpload.class);
        applyFileUploadService.getTmAppUploadPageByFnOrData(page);
        for(TmAppUpload tmAppUpload : page.getRows()){
            if(tmAppUpload.getStartBpmn().equals("0")){
                tmAppUpload.setStartBpmn("上传成功");
            } else {
                tmAppUpload.setStartBpmn("上传失败");
            }
        }
        return page;
    }

    /**
     * @Author smh
     * @Description TODO 上传处理
     * @Date 2019/1/16 15:51
     */
    @ResponseBody
    @RequestMapping("/uploadAndHandle")
    public Json uploadAndHandle(){
/*
        HttpServletResponse response = getResponse();
*/
        Json json = Json.newSuccess();
        Query query=getQuery();
        try {
               if (query==null){
                   throw new ProcessException("未获取有效的参数");
               }
               if (StringUtils.isEmpty(query.get("productCode"))){
                   throw new ProcessException("请选择有效的业务条线");
               }
            String productCode= String.valueOf(query.get("productCode"));

               //记录审计历史
            systemAuditHistoryUtils.saveSystemAudit("ALL","决策跑批","上传提交","","");
            //创建一成功输出的表格
            HSSFWorkbook workbook = new HSSFWorkbook();
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
            String formatDate = dateFormat.format(date);
            HSSFSheet hssfSheet = workbook.createSheet("policy_data_" + formatDate);
            hssfSheet.autoSizeColumn((short) 0); //调整第一列宽度
            hssfSheet.autoSizeColumn((short) 10);
            HSSFRow row = hssfSheet.createRow(0);
            HSSFCellStyle style = workbook.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
            String[] rowHeaders = {"AppNo", "Buscore", "FinalTotScore",
                    "FinalAuditresult", "RejectReason", "RiskLevel1",
                    "RiskLevel2", "FinalCreditlimt",
                    "FinalOtherLimit", "AdjustIndex",
                    "AuditisriskJj", "NewBuscore", "NewRiskLevel", "NewFinalCreditlimt"};
            HSSFCell cell;
               for (int q = 0; q < rowHeaders.length; q++) {
                cell = row.createCell(q);
                cell.setCellValue(rowHeaders[q]);
                cell.setCellStyle(style);
            }

               //创建一个失败输出的表格
               HSSFWorkbook failWorkBook = new HSSFWorkbook();
               HSSFSheet failHSSFSheet = failWorkBook.createSheet("policy_data_fail" + formatDate);
               failHSSFSheet.autoSizeColumn((short) 0); //调整第一列宽度
               failHSSFSheet.autoSizeColumn((short) 50);
               HSSFRow failRow = failHSSFSheet.createRow(0);
               HSSFCellStyle  failStyle = failWorkBook.createCellStyle();
               failStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
               String[] failRowHeaders = {"AppNo", "RowsNum/原文件行数","Date","FailReason"};
               HSSFCell failCell;
               for (int d = 0; d < failRowHeaders.length; d++) {
                   failCell = failRow.createCell(d);
                   failCell.setCellValue(failRowHeaders[d]);
                   failCell.setCellStyle(failStyle);
               }
               int v=1;
               //创建一个输入流
            InputStream fileDatas = null;

            //决策的配置信息
            Map<String, String> bjjRiskConf = cacheContext.getInterNetAddrParam(AppDitDicConstant.ext_bjj_risk_conf);
               MultipartFile file =null;
           try {
                file = getFile("fileName");
           }catch (Exception e){
               throw new ProcessException("上传文件不能为空");
           }
            if (file == null) {
                throw new ProcessException("上传的文件不存在");
            }
            String fileName = file.getOriginalFilename();
            // 创建输入流，读取Excel
            fileDatas = file.getInputStream();
            String filetype = fileName.substring(fileName.lastIndexOf(".") + 1);
            org.apache.poi.ss.usermodel.Workbook wbs;
            if (filetype.equalsIgnoreCase("XLS")) {
                wbs = new HSSFWorkbook(fileDatas); // excel2003
            } else {
                wbs = new XSSFWorkbook(fileDatas); // excel2007
            }
            // Excel的Sheet数量
            int sheet_size = wbs.getNumberOfSheets();
            if (sheet_size == 0) {
                throw new ProcessException("没有读到上传文件信息!");
            }
            //sheet从0开始
            for (int index = 0; index < sheet_size; index++) {
                String failedReason = "";
                int totalCloum=1;
                // 创建一个Sheet对象
                Sheet sheet = wbs.getSheetAt(index);
                //得到sheet的总的行数
                int totalRows = sheet.getPhysicalNumberOfRows();
                //得到Excel的列数(前提是有行数)
                if (totalRows>=1&&sheet.getRow(0)!=null){
                    totalCloum=sheet.getRow(0).getPhysicalNumberOfCells();
                }
                // 从第二行开始遍历取值操作(第一行是字段,不取)
                for (int i = 1; i <totalRows ; i++) {
                    //初始化一个app_no
                    String app_no = "";
                    //获取第i行的值
                    Row hssfRow = sheet.getRow(i);
                    List innerList = new ArrayList();
                    // 循环取这行每一列中的值存入innerList集合
                    /*=========================totalCloum-1??=====================================*/
                    for (int j = 0; j < totalCloum; j++) {
                        Cell numCell= hssfRow.getCell(j);
                        if (numCell!=null){
                            numCell.setCellType(Cell.CELL_TYPE_STRING);
                        }
                        String info = numCell.getStringCellValue();
                        if (StringUtils.isEmpty(info)) {
                            info = "";
                        }
                        innerList.add(info);
                    }
                    //再去遍历这个innerList将里面的值按顺序存到一个json对象中
                    int k = innerList.size();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", innerList.get(0));
                    jsonObject.put("phone_no", innerList.get(1));
                    jsonObject.put("app_no", innerList.get(2));
                    jsonObject.put("id_card", innerList.get(3));
                    jsonObject.put("qryRsn", innerList.get(4));
                    jsonObject.put("tdMultiLoan1M", innerList.get(5));
                    jsonObject.put("billCount6M", innerList.get(6));
                    jsonObject.put("carrierHitBlackList6M", innerList.get(7));
                    jsonObject.put("salaryW", innerList.get(8));
                    jsonObject.put("cust_id", innerList.get(9));
                    jsonObject.put("auditisrisk_51", innerList.get(10));
                    jsonObject.put("lmk_card_cnt", innerList.get(11));
                    jsonObject.put("lmk_lmt_tot", innerList.get(12));
                    jsonObject.put("blacklist", innerList.get(13));
                    jsonObject.put("risk_policy_type", innerList.get(14));
                    jsonObject.put("score4HasCreditReport", innerList.get(15));
                    jsonObject.put("productCode",query.get("productCode"));
/*
                    jsonObject.put("riskProCd001","000120190110347921");
*/
                    app_no = StringUtils.valueOf(innerList.get(2));
                    boolean success = true;
                    JSONObject responseJs = new JSONObject();
                    //进行决策,返回一个json对象
                    try {
                        responseJs = sendBjjRiskSys("decision", jsonObject, bjjRiskConf,productCode);
                    } catch (Exception e) {
                        success = false;
                        failedReason = e.getMessage();
                    }
                    if (success && responseJs != null) {
                        //将结果输出到成功的Excel表格中
                        //检查responseJs并返回里面需要的数据
                        HashMap hashMap = checkResonseJsData(responseJs);
                        //i初始为1,之后累加,满足从第二行开始增加
                        row = hssfSheet.createRow(i);
                        cell = row.createCell(0);
                        cell.setCellValue(StringUtils.valueOf(hashMap.get("app_no")));
                        cell = row.createCell(1);
                        cell.setCellValue(StringUtils.valueOf(hashMap.get("buscore")));
                        cell = row.createCell(2);
                        cell.setCellValue(StringUtils.valueOf(hashMap.get("final_tot_score")));
                        cell = row.createCell(3);
                        cell.setCellValue(StringUtils.valueOf(hashMap.get("final_auditresult")));
                        cell = row.createCell(4);
                        cell.setCellValue(StringUtils.valueOf(hashMap.get("reject_reason")));
                        cell = row.createCell(5);
                        cell.setCellValue(StringUtils.valueOf(hashMap.get("risklevel1")));
                        cell = row.createCell(6);
                        cell.setCellValue(StringUtils.valueOf(hashMap.get("risklevel2")));
                        cell = row.createCell(7);
                        cell.setCellValue(StringUtils.valueOf(hashMap.get("final_creditlimt")));
                        cell = row.createCell(8);
                        cell.setCellValue(StringUtils.valueOf(hashMap.get("final_other_limit")));
                        cell = row.createCell(9);
                        cell.setCellValue(StringUtils.valueOf(hashMap.get("adjustIndex")));
                        cell = row.createCell(10);
                        cell.setCellValue(StringUtils.valueOf(hashMap.get("auditisrisk_jj")));
                        cell = row.createCell(11);
                        cell.setCellValue(StringUtils.valueOf(hashMap.get("new_buscore")));
                        cell = row.createCell(12);
                        cell.setCellValue(StringUtils.valueOf(hashMap.get("new_riskLevel")));
                        cell = row.createCell(13);
                        cell.setCellValue(StringUtils.valueOf(hashMap.get("new_final_creditlimt")));
                        //保存跑批决策成功的信息
                        TmAppUpload tmAppUpload = new TmAppUpload();
                        tmAppUpload.setFileName(fileName);
                        tmAppUpload.setFailReason(failedReason);
                        tmAppUpload.setBatchDate(date);
                        tmAppUpload.setStartBpmn("0");
                        tmAppUpload.setContent("决策信息");
                        tmAppUpload.setLineNo(i+1);
                        applyFileUploadDao.saveTmAppUpload(tmAppUpload);
                    }
                    else{
                        //将结果输出到失败的Excel表格中
                        failRow = failHSSFSheet.createRow(v);
                        failCell = failRow.createCell(0);
                        failCell.setCellValue(app_no);
                        failCell = failRow.createCell(1);
                        failCell.setCellValue(i+1);
                        failCell = failRow.createCell(2);
                        failCell.setCellValue(formatDate);
                        failCell = failRow.createCell(3);
                        failCell.setCellValue(failedReason);
                        v++;
                        //保存跑批决策成功的信息
                        TmAppUpload tmAppUpload = new TmAppUpload();
                        tmAppUpload.setFileName(fileName);
                        tmAppUpload.setFailReason(failedReason);
                        tmAppUpload.setBatchDate(new Date());
                        tmAppUpload.setStartBpmn("1");
                        tmAppUpload.setContent("决策信息");
                        tmAppUpload.setLineNo(i+1);
                        applyFileUploadDao.saveTmAppUpload(tmAppUpload);
                    }
                }
            }
            //操作完成 输出Excel表
            try {
                //success
                FileOutputStream fout = new FileOutputStream("E:/"+"policy_data_"+formatDate+".xls");
                workbook.write(fout);
                fout.close();
                FileOutputStream failFout = new FileOutputStream("E:/"+"policy_data_fail"+formatDate+".xls");
                failWorkBook.write(failFout);
                failFout.close();
                json.setMsg("操作成功，成功信息保存路径为："+"E:/"+"policy_data_"+formatDate+".xls;"+
                       "失败信息保存路径为："+"E:/"+"policy_data_"+formatDate+".xls");
              /*  HttpServletResponse response=getResponse();
                String outputFileName = "policy_data_" + formatDate + ".xls";
                outputFileName = URLEncoder.encode(outputFileName, "UTF8");
                response.reset();
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment; filename=\"" + outputFileName + "\"");
                OutputStream ouputStream = response.getOutputStream();
                workbook.write(ouputStream);
                ouputStream.flush();
                ouputStream.close();*/
            }catch(IOException e){
                logger.info("信息表下载失败！" + e.getMessage());
                json.setFail("信息表下载失败！" + e.getMessage());
                json.setS(false);
            }
            }catch (Exception e){
                    logger.info("跑批失败"+e.getMessage());
                    json.setFail("跑批失败！" + e.getMessage());
                    json.setS(false);
                }
        return json;
    }

    /**
     * @Author smh
     * @Description TODO  检查responseJs里面需要的数据并返回
     * @Date 2019/1/17 14:29
     */
    private HashMap checkResonseJsData(JSONObject responseJs) {
        //创建一个map集合 返回使用
        HashMap map = new HashMap();
        //获取app_no
        String app_no = "";
        if (responseJs.containsKey("requestParam")) {
            JSONObject js1 = responseJs.getJSONObject("requestParam");
            if (js1.containsKey("app_no")) {
                app_no = js1.getString("app_no");
                if (StringUtils.isEmpty(app_no)) {
                    app_no = "";
                }
            }
        }
        map.put("app_no", app_no);
        //获取buscore
        String buscore = "";
        if (responseJs.containsKey("decisionResult")) {
            JSONObject js1 = responseJs.getJSONObject("decisionResult");
            if (js1.containsKey("resultMap")) {
                JSONObject js2 = js1.getJSONObject("resultMap");
                if (js2.containsKey("buscore")) {
                    buscore = js2.getString("buscore");
                    if (StringUtils.isEmpty(buscore)) {
                        buscore = "";
                    }
                }
            }
        }
        map.put("buscore", buscore);

        //获取final_tot_score
        String final_tot_score = "";
        if (responseJs.containsKey("decisionResult")) {
            JSONObject js1 = responseJs.getJSONObject("decisionResult");
            if (js1.containsKey("resultMap")) {
                JSONObject js2 = js1.getJSONObject("resultMap");
                if (js2.containsKey("final_tot_score")) {
                    final_tot_score = js2.getString("final_tot_score");
                    if (StringUtils.isEmpty(final_tot_score)) {
                        final_tot_score = "";
                    }
                }
            }
        }
        map.put("final_tot_score", final_tot_score);

        //获取final_auditresult
        String final_auditresult = "";
        if (responseJs.containsKey("decisionResult")) {
            JSONObject js1 = responseJs.getJSONObject("decisionResult");
            if (js1.containsKey("resultMap")) {
                JSONObject js2 = js1.getJSONObject("resultMap");
                if (js2.containsKey("final_auditresult")) {
                    final_auditresult = js2.getString("final_auditresult");
                    if (StringUtils.isEmpty(final_auditresult)) {
                        final_auditresult = "";
                    }
                }
            }
        }
        map.put("final_auditresult", final_auditresult);

        //获取reject_reason
        String reject_reason = "";
        StringBuilder reason = new StringBuilder();
        if (responseJs.containsKey("decisionResult")) {
            JSONObject js1 = responseJs.getJSONObject("decisionResult");
            if (js1.containsKey("resultMap")) {
                JSONObject js2 = js1.getJSONObject("resultMap");
                if (js2.containsKey("reject_reason")) {
                    JSONArray jsonArray = js2.getJSONArray("reject_reason");
                    if (jsonArray == null) {
                        reject_reason = "";
                    }
                    for (int j = 0; j < jsonArray.size(); j++) {
                        reason.append(jsonArray.getString(j) + ",");
                    }
                    reject_reason = reason.toString();
                }
            }
        }
        map.put("reject_reason", reject_reason);

        //获取RiskLevel1
        String risklevel1 = "";
        if (responseJs.containsKey("decisionResult")) {
            JSONObject js1 = responseJs.getJSONObject("decisionResult");
            if (js1.containsKey("resultMap")) {
                JSONObject js2 = js1.getJSONObject("resultMap");
                if (js2.containsKey("risklevel1")) {
                    risklevel1 = js2.getString("risklevel1");
                    if (StringUtils.isEmpty(risklevel1)) {
                        risklevel1 = "";
                    }
                }
            }
        }
        map.put("risklevel1", risklevel1);

        //获取risklevel2
        String risklevel2 = "";
        if (responseJs.containsKey("decisionResult")) {
            JSONObject js1 = responseJs.getJSONObject("decisionResult");
            if (js1.containsKey("resultMap")) {
                JSONObject js2 = js1.getJSONObject("resultMap");
                if (js2.containsKey("risklevel2")) {
                    risklevel2 = js2.getString("risklevel2");
                    if (StringUtils.isEmpty(risklevel2)) {
                        risklevel2 = "";
                    }
                }
            }
        }
        map.put("risklevel2", risklevel2);

        //获取final_creditlimt
        String final_creditlimt = "";
        if (responseJs.containsKey("decisionResult")) {
            JSONObject js1 = responseJs.getJSONObject("decisionResult");
            if (js1.containsKey("resultMap")) {
                JSONObject js2 = js1.getJSONObject("resultMap");
                if (js2.containsKey("final_creditlimt")) {
                    final_creditlimt = js2.getString("final_creditlimt");
                    if (StringUtils.isEmpty(final_creditlimt)) {
                        final_creditlimt = "";
                    }
                }
            }
        }
        map.put("final_creditlimt", final_creditlimt);

        //获取 final_other_limit
        String final_other_limit = "";
        if (responseJs.containsKey("decisionResult")) {
            JSONObject js1 = responseJs.getJSONObject("decisionResult");
            if (js1.containsKey("resultMap")) {
                JSONObject js2 = js1.getJSONObject("resultMap");
                if (js2.containsKey("final_other_limit")) {
                    final_other_limit = js2.getString("final_other_limit");
                    if (StringUtils.isEmpty(final_other_limit)) {
                        final_other_limit = "";
                    }
                }
            }
        }
        map.put("final_other_limit", final_other_limit);

        //获取adjustIndex
        String adjustIndex = "";
        if (responseJs.containsKey("decisionResult")) {
            JSONObject js1 = responseJs.getJSONObject("decisionResult");
            if (js1.containsKey("resultMap")) {
                JSONObject js2 = js1.getJSONObject("resultMap");
                if (js2.containsKey("adjustIndex")) {
                    adjustIndex = js2.getString("adjustIndex");
                    if (StringUtils.isEmpty(adjustIndex)) {
                        adjustIndex = "";
                    }
                }
            }
        }
        map.put("adjustIndex", adjustIndex);

        //获取auditisrisk_jj
        String auditisrisk_jj = "";
        if (responseJs.containsKey("decisionResult")) {
            JSONObject js1 = responseJs.getJSONObject("decisionResult");
            if (js1.containsKey("resultMap")) {
                JSONObject js2 = js1.getJSONObject("resultMap");
                if (js2.containsKey("auditisrisk_jj")) {
                    auditisrisk_jj = js2.getString("auditisrisk_jj");
                    if (StringUtils.isEmpty(auditisrisk_jj)) {
                        auditisrisk_jj = "";
                    }
                }
            }
        }
        map.put("auditisrisk_jj", auditisrisk_jj);

        //获取new_buscore
        String new_buscore = "";
        if (responseJs.containsKey("decisionResult")) {
            JSONObject js1 = responseJs.getJSONObject("decisionResult");
            if (js1.containsKey("resultMap")) {
                JSONObject js2 = js1.getJSONObject("resultMap");
                if (js2.containsKey("new_buscore")) {
                    new_buscore = js2.getString("new_buscore");
                    if (StringUtils.isEmpty(new_buscore)) {
                        new_buscore = "";
                    }
                }
            }
        }
        map.put("new_buscore", new_buscore);

        //获取new_riskLevel
        String new_riskLevel = "";
        if (responseJs.containsKey("decisionResult")) {
            JSONObject js1 = responseJs.getJSONObject("decisionResult");
            if (js1.containsKey("resultMap")) {
                JSONObject js2 = js1.getJSONObject("resultMap");
                if (js2.containsKey("new_riskLevel")) {
                    new_riskLevel = js2.getString("new_riskLevel");
                    if (StringUtils.isEmpty(new_riskLevel)) {
                        new_riskLevel = "";
                    }
                }
            }
        }
        map.put("new_riskLevel", new_riskLevel);

        //获取new_riskLevel
        String new_final_creditlimt = "";
        if (responseJs.containsKey("decisionResult")) {
            JSONObject js1 = responseJs.getJSONObject("decisionResult");
            if (js1.containsKey("resultMap")) {
                JSONObject js2 = js1.getJSONObject("resultMap");
                if (js2.containsKey("new_final_creditlimt")) {
                    new_final_creditlimt = js2.getString("new_final_creditlimt");
                    if (StringUtils.isEmpty(new_final_creditlimt)) {
                        new_final_creditlimt = "";
                    }
                }
            }
        }
        map.put("new_final_creditlimt", new_final_creditlimt);

        return map;
    }

    /**
     * @Author smh
     * @Description TODO  获取json响应数据
     * @Date 2019/1/17 10:31
     */
    private JSONObject sendBjjRiskSys(String servId, JSONObject jsonObject
            , Map<String, String> bjjRiskConf,String productCode) throws IOException {
        long start = System.currentTimeMillis();
        JSONObject resJs = new JSONObject();
        String riskUrl = StringUtils.valueOf(bjjRiskConf.get("riskDecisionUrl"));
        String ip = StringUtils.valueOf(bjjRiskConf.get("riskDecisionHost"));
        String port = StringUtils.valueOf(bjjRiskConf.get("riskDecisionPort"));
        String riskCallMode = StringUtils.valueOf(bjjRiskConf.get("riskCallMode"));
        if (StringUtils.isEmpty(riskUrl)){
            throw new ProcessException("无法调用风控系统.系统配置[风控地址]为空！");
        }
        String resp = null;
        try {
            logger.info(LogPrintUtils.printCommonStartLog(start, servId) + "风控决策请求:{}", jsonObject.toJSONString());
            if (StringUtils.isEmpty(riskCallMode) || StringUtils.equals(riskCallMode, "1")) {
                JSONObject jss =new JSONObject();
                jss.put("requestParam", jsonObject);
                jss.put("productCode",productCode);
                byte[] byRes = HttpHelper.post(riskUrl, jss.toJSONString().getBytes("UTF-8"));
                resp = new String(byRes);
            }
            if (StringUtils.equals(riskCallMode, "2") && StringUtils.isNotEmpty(ip)) {
                resp = HttpClientUtil2.pbocQuery(servId, riskUrl, ip, port, jsonObject);
            } else if (StringUtils.equals(riskCallMode, "2") && StringUtils.isEmpty(ip)) {
                byte[] byRes = HttpHelper.post(riskUrl, jsonObject.toJSONString().getBytes("UTF-8"));
                resp = new String(byRes);
            }
            if (StringUtils.equals(riskCallMode, "3")) {
                resp = sendHttpRisk("decision", riskUrl, jsonObject);
            }
        } catch (Exception e) {
            logger.error("发送/接收调用风控系统发生异常.", e);
            throw new ProcessException("发送/接收调用风控系统发生异常." + e.getMessage());
        } finally {
            logger.info("风控决策[耗时" + (System.currentTimeMillis() - start) + "毫秒],响应[" + resJs + "]");
        }
        if (StringUtils.isEmpty(resp)) {
            throw new ProcessException("风控决策结果返回为空.请重试或联系管理员！");
        }
        try {
            resJs = JSON.parseObject(resp);
        } catch (Exception e) {
            logger.error("解析风控响应数据异常", e);
            throw new ProcessException("解析风控决策结果数据异常.请联系管理员");
        }
        if (resJs != null && !StringUtils.equals(resJs.get("stateCode"), "00000")) {
            String stateCode = StringUtils.valueOf(resJs.getString("stateCode"));
            String stateDesc = StringUtils.valueOf(resJs.getString("stateDesc"));
            String tErMsg = stateCode + "-" + stateDesc;
            logger.error("调用风控决策系统返回错误码:[" + tErMsg + "]");
            throw new ProcessException("风控决策失败.错误码:[" + tErMsg + "]");
        }
        return resJs;
    }


    /**
     * @Author smh
     * @Description TODO 发送Http请求
     * @Date 2019/1/17 14:30
     */
    private static String sendHttpRisk(String servId, String url, JSONObject jsReq) {
        long start = System.currentTimeMillis();
        if (jsReq == null) {
            logger.warn(LogPrintUtils.printCommonStartLog(start, servId) + "服务编码[" + servId + "]请求失败，请求参数为空");
            throw new ProcessException("联机交易[" + servId + "]失败，请求参数为空");
        }
        String resp = "";
        try {
            logger.info(LogPrintUtils.printCommonStartLog(start, servId) + "风控决策请求:{}", jsReq.toJSONString());
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json");
            post.setHeader("Connection", "close");
            RequestConfig reqConfig = RequestConfig.custom().setConnectTimeout(65000).setConnectionRequestTimeout(10000)
                    .setSocketTimeout(65000).build();
            post.setConfig(reqConfig);
            StringEntity entity = new StringEntity(jsReq.toJSONString(), Charset.forName("UTF-8"));
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            post.setEntity(entity);

            resp = HttpClientUtil.execute(post);
        } catch (Exception e) {
            logger.error("联机交易[" + servId + "]发生异常.", e);
            throw new ProcessException("联机交易[" + servId + "]发生异常." + e.getMessage());
        } finally {
            logger.info(LogPrintUtils.printCommonStartLog(start, servId) + "，响应数据[" + resp + "]");
        }
        return resp;
    }




}
