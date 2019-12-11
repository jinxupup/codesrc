package com.jjb.ecms.app.controller.manage;

import com.jjb.acl.biz.utils.SystemAuditHistoryUtils;
import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.acl.mvc.web.controller.CommonExceptionPageUtils;
import com.jjb.ecms.biz.service.manage.RiskListUploadService;
import com.jjb.ecms.biz.service.manage.TmRiskListService;
import com.jjb.ecms.biz.util.BizAuditHistoryUtils;
import com.jjb.ecms.biz.util.ErrorMsgUtil;
import com.jjb.ecms.facility.servicetask.item.TmRiskListItem;
import com.jjb.ecms.infrastructure.TmBatchUpload;
import com.jjb.ecms.infrastructure.TmRiskList;
import com.jjb.ecms.util.IdentificationCodeUtil;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.CStruct2;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.DataTypeUtils;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JYData-R&D-L.L
 * @version V1.0
 * @Description: 风险名单管理
 * @date 2016年8月31日 下午6:44:04
 */

@Controller
@RequestMapping("/tmRiskList")
public class TmRiskListController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TmRiskListService tmRiskListService;
    @Autowired
    private RiskListUploadService riskListUploadService;
    @Autowired
    private CommonExceptionPageUtils commonExceptionPageUtils;
    @Autowired
    private SystemAuditHistoryUtils systemAuditHistoryUtils;
    List<TmBatchUpload> uplist;


    /**
     * 风险名单查询页面
     *
     * @param
     * @return 风险名单页面
     */
    @RequestMapping("/tmRiskList")
    public String tmRiskListpage() {
        return "/applyManage/applyTmRiskList/tmRiskList.ftl";
    }

    /**
     * 风险名单 分页查询
     *
     * @param
     * @return 风险名单记录
     */
    @ResponseBody
    @RequestMapping("/list")
    public Page<TmRiskList> list() {
        Page<TmRiskList> page = getPage(TmRiskList.class);
        page = tmRiskListService.getTmRiskListPage(page);
        return page;
    }

    /**
     * 删除风险名单
     *
     * @param id
     * @return json
     */
    @ResponseBody
    @RequestMapping("/delete")
    public Json delete(int id) {
        Json json = Json.newSuccess();
        if (StringUtils.isBlank(StringUtils.valueOf(id))){
            json.setFail("未获取到有效的信息，请重试");
        }
        //记录审计历史
        TmRiskList tmRisk = tmRiskListService.getRiskList(id);
        String oper = "";
        if (StringUtils.isEmpty(tmRisk.getCellPhone())) {
            oper += "身份证号:" + tmRisk.getIdNo();
        } else {
            oper += "手机号:" + tmRisk.getCellPhone();
        }
        systemAuditHistoryUtils.saveSystemAudit(oper,"风险名单","DELETE",tmRisk.convertToMap().toString(),"");
        tmRiskListService.delete(id);
        return json;
    }

    /**
     * 风险名单添加页面
     *
     * @param
     * @return 风险名单添加页面
     */
    @RequestMapping("/addpage")
    public String addpage() {
        return "/applyManage/applyTmRiskList/tmRiskListAdd.ftl";
    }

    /**
     * 添加风险名单
     *
     * @param tmRiskList
     * @return json
     */
    @ResponseBody
    @RequestMapping("/add")
    public Json add(TmRiskList tmRiskList) {
        Json json = Json.newSuccess();
        if (StringUtils.isEmpty(tmRiskList)){
            json.setFail("未获取到有效的信息，请重试");
        }
        //保存审计历史
        String oper = "";
        if (StringUtils.isEmpty(tmRiskList.getCellPhone())) {
            oper += "身份证号:" + tmRiskList.getIdNo();
        } else {
            oper += "手机号:" + tmRiskList.getCellPhone();
        }
        systemAuditHistoryUtils.saveSystemAudit(oper,"风险名单","SAVE","",tmRiskList.convertToMap().toString());
        tmRiskList.setCreateDate(new Date());
        tmRiskListService.saveTmRiskList(tmRiskList);
        return json;
    }

    /**
     * 编辑风险名单页面
     *
     * @param id
     * @return 风险名单编辑页面
     */
    @RequestMapping("/editpage")
    public String editpage(int id) {
        TmRiskList tmRiskList = tmRiskListService.getRiskList(id);
        if (StringUtils.isEmpty(tmRiskList)){
            return commonExceptionPageUtils.doExcepiton("未获取到有效信息，请重试!");
        }
        setAttr("tmRiskList", tmRiskList);
        setEdit();
        return "/applyManage/applyTmRiskList/tmRiskListAdd.ftl";

    }

    /**
     * 欺诈弹窗查看
     *
     * @param id
     * @return
     */
    @RequestMapping("/viewDialog")
    public String viewDialog(int id) {
        TmRiskList tmRiskList = tmRiskListService.getRiskList(id);
        setAttr("tmRiskList", tmRiskList);
        setEdit();
        return "/applyManage/applyTmRiskList/blPersonalDialog_V1.ftl";
    }

    /**
     * 编辑风险名单
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/edit")
    public Json edit(TmRiskList tmRiskList) {
        Json json = Json.newSuccess();
        if (StringUtils.isEmpty(tmRiskList)){
            json.setFail("未获取到有效的信息，请重试");
        }
        TmRiskList tmRisk = tmRiskListService.getRiskList(tmRiskList.getId());
        //添加审计历史
        String oper = "";
        if (StringUtils.isEmpty(tmRiskList.getCellPhone())) {
            oper += "身份证号:" + tmRiskList.getIdNo();
        } else {
            oper += "手机号:" + tmRiskList.getCellPhone();
        }
        systemAuditHistoryUtils.saveSystemAudit(oper,"风险名单","UPDATE",tmRisk.convertToMap().toString(),tmRiskList.convertToMap().toString());
        tmRiskList.setUpdateDate(new Date());
        tmRiskList.setJpaVersion(tmRiskList.getJpaVersion()+1);
        tmRiskListService.updateTmRiskList(tmRiskList);
        return json;
    }

    /**
     * 批量导入风险名单
     *
     * @param isEdit
     * @return
     */
    @RequestMapping("/fileUpload")
    public String fileUpload(String isEdit) {
        if (StringUtils.isNotBlank(isEdit) && isEdit.equals(Indicator.Y.name())) {
            setEdit();
        }
        return "/applyManage/applyTmRiskList/riskListFileUpload_V1.ftl";
    }

    /**
     * 点击提交上文的文件
     *
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/upload")
    public Json uploadFile() throws IOException {
        InputStream fileDatas = null;
        Json json = Json.newSuccess();
        try {
            uplist = new ArrayList<TmBatchUpload>();

            MultipartFile file = getFile("fileName");
            if (file == null) {
                throw new ProcessException("批量上传的风险名单不存在，请重试！");
            }

            fileDatas = file.getInputStream();
            String fileName = file.getOriginalFilename();
            logger.info("文件上传提交开始：" + fileName);

            String filetype = fileName.substring(fileName.lastIndexOf(".") + 1);
            Workbook wbs;
            if (filetype.equalsIgnoreCase("XLS")) {
                wbs = new HSSFWorkbook(fileDatas); // excel2003
            } else {
                wbs = new XSSFWorkbook(fileDatas); // excel2007
            }

            checkXls(wbs, fileName);//规则校验，同文件检测

            //获取sheet风险名单信息
            List<Map<Integer, HashMap<String, Serializable>>> sheetDataList = dealWithSheet(wbs, fileName);
            List<TmRiskList> tmRiskList = makeSheetSaveModel(sheetDataList);

            for (TmRiskList riskList : tmRiskList) {
                logger.info("文件上传提交开始：");
                try {
                    riskList.setMemo(fileName+"--批量batch");
                    tmRiskListService.saveTmRiskList(riskList);
                } catch (Exception e) {
                    logger.error("文件上传保存数据失败",e);
                }
                json = Json.newSuccess();
//                Map<String, Serializable> vars = new HashMap<String, Serializable>();
//                //保存工作流节点数据
//                ApplyNodeCommonData applyNodeCommonData = (ApplyNodeCommonData) applyInfoDto.getTmAppNodeInfoRecordMap().get(AppCommonConstant.APPLY_NODE_COMMON_DATA);
//                vars.put(AppCommonConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
//
//                String processKey = activitiService.getDefProcess();
//                if (StringUtils.isNotEmpty(processKey)) {
//                    activitiService.startNewProcess(processKey, appNo, vars);
//                } else {
//                    logger.info("没有获取到默认的流程定义，请检查流程！" + LogPrintUtils.printAppNoLog(appNo, null,null));
//                }
            }

            //保存文件上传记录
            for (TmBatchUpload tmBatchUpload : uplist) {
                riskListUploadService.saveTmRiskUpload(tmBatchUpload);
            }
            //保存审计历史
            systemAuditHistoryUtils.saveSystemAudit("风险名单文件","风险名单","批量上传","","");
            logger.info("文件上传提交结束：" + fileName);
            json.setMsg("文件上传成功!");

        } catch (Exception e) {
            logger.error("文件上传提交异常", e);
            json.setFail(e.getMessage());
        } finally {
            if (fileDatas != null)
                fileDatas.close();
        }
        return json;
    }

    /**
     * 校验同文件重复
     *
     * @param wbs
     * @param fileName
     */
    private void checkXls(Workbook wbs, String fileName) {

        if (CollectionUtils.sizeGtZero(riskListUploadService.getTmRiskUploadByName(fileName))) {
            throw new ProcessException("同文件重复上传,请重试!");
        }
        int sheetNum = wbs.getNumberOfSheets();

        //Sheet个数最大2个
        if (sheetNum > 2) {
            throw new ProcessException("Sheet个数最多为2个");
        }

        for (int i = 0; i < sheetNum; i++) {
            Sheet childSheet = wbs.getSheetAt(i);
            //第1个sheet:名称：“A” ,第2个sheet:名称：“B”,且每一张表的最大记录是10000条
            if (i == 0 && !childSheet.getSheetName().equals("A")) {
                throw new ProcessException("第1个sheet名称必须为A");
            }
            if (i == 1 && !childSheet.getSheetName().equals("B")) {
                throw new ProcessException("第2个sheet名称必须为B");
            }
            if (wbs.getSheetAt(i).getLastRowNum() > 10001) {
                throw new ProcessException("每个sheet页最大10000条记录，第" + (i + 1) + "工作表超过了一万条记录");
            }
        }
    }

    /**
     * 处理批量风险名单文件数据
     *
     * @param wbs
     * @param fileName
     * @return
     */
    private List<Map<Integer, HashMap<String, Serializable>>> dealWithSheet(Workbook wbs, String fileName) {
        int sheetNo = wbs.getNumberOfSheets();
        if (sheetNo == 0) {
            throw new ProcessException("没有读到上传文件信息!");
        }

        List<Map<Integer, HashMap<String, Serializable>>> dataList = new ArrayList<Map<Integer, HashMap<String, Serializable>>>();
        for (int i = 0; i < sheetNo; i++) {
            Sheet childSheet = wbs.getSheetAt(i);
            Row hssfRow = childSheet.getRow(1);
            //检验两个Sheet的第二行（英文字段）  是否符合BlPersonal 数组中的字段
            for (int cellNum = 0; cellNum < hssfRow.getLastCellNum(); cellNum++) {//列号从0开始
                if (i == 0 && !ErrorMsgUtil.BlPersonal[cellNum].equals(hssfRow.getCell(cellNum).getStringCellValue())) {
                    throw new ProcessException("第" + (i + 1) + "页第2行第" + (cellNum + 1) + "列不符合模板规范。");
                }
                if (i == 1 && !ErrorMsgUtil.BlPersonal[cellNum].equals(hssfRow.getCell(cellNum).getStringCellValue())) {
                    throw new ProcessException("第" + (i + 1) + "页第2行第" + (cellNum + 1) + "列不符合模板规范。");
                }
            }
            // 存放excel数据Map<行号, HashMap<列名, 值>>  rowNum = 2开始取值
            Map<Integer, HashMap<String, Serializable>> data = new HashMap<Integer, HashMap<String, Serializable>>();
            for (int rowNum = 2; rowNum <= childSheet.getLastRowNum(); rowNum++) {//行号从2开始（实际是excel中的第三行）
                String validaters = "";
                TmBatchUpload tmBatchUpload = new TmBatchUpload();
                tmBatchUpload.setOrg(OrganizationContextHolder.getOrg());
                tmBatchUpload.setFileName(fileName);
                tmBatchUpload.setBatchDate(new Date());
                tmBatchUpload.setLineNo(rowNum + 1);
                tmBatchUpload.setStartBpmn("0");
                tmBatchUpload.setFailReason(null);
                tmBatchUpload.setUploadCategory("RiskList");//风险名单
                if (childSheet.getRow(rowNum) == null) {
                    validaters += "不能取到第" + (i + 1) + "页第" + (rowNum + 1) + "行数据.";
                    tmBatchUpload.setStartBpmn("1");
                    tmBatchUpload.setFailReason(validaters);
                    uplist.add(tmBatchUpload);
                    continue;
                }

                HashMap<String, Serializable> cellvalueMap = new HashMap<String, Serializable>();
                // 循环第二行key值获取值
                CStruct2 cs = new CStruct2<TmRiskListItem>(TmRiskListItem.class, "gbk");

                for (int cellNum = 0; cellNum < hssfRow.getLastCellNum(); cellNum++) {
                    String cellname = hssfRow.getCell(cellNum).getStringCellValue();
                    String cellValue = childSheet.getRow(rowNum).getCell(cellNum) == null ? "" : childSheet.getRow(rowNum).getCell(cellNum).toString().trim();
                    if (StringUtils.isBlank(cellValue)) {//对空值的处理
                        cellValue = null;
                    }
                    //格式化值并放入集合
                    cellvalueMap = cs.putValue(cellname, cellValue, cellvalueMap);
                    validaters = cs.validatefield(cellname, cellvalueMap.get(cellname));//内容格式、长度验证
                    if (StringUtils.isNotBlank(validaters)) {
                        logger.error(cellname + "字段不合法");
                        throw new ProcessException(validaters + ",错误出现在第" + (i + 1) + "页第" + (rowNum + 1) + "行第" + (cellNum + 1) + "列");
                    }
                }
                if (cellvalueMap == null || cellvalueMap.isEmpty() || cellvalueMap.size() == 0) {
                    break;
                }
                //必输项验证  证件号码+证件类型/手机号码、风险名单来源、风险名单类型  为必填项
                String risklistSrc = DataTypeUtils.getStringValue(cellvalueMap.get("risklistSrc"));
                if (StringUtils.isBlank(risklistSrc)) {
                    logger.error("风险名单来源为空");
                    throw new ProcessException("风险名单批量导入数据来源为空,错误出现在第" + (i + 1) + "页第" + (rowNum + 1) + "行");
                }
                String idType = DataTypeUtils.getStringValue(cellvalueMap.get("idType"));
                String idNo = DataTypeUtils.getStringValue(cellvalueMap.get("idNo"));
                if (StringUtils.isNotBlank(idType)&&StringUtils.isBlank(idNo)) {
                    logger.error("证件号为空");
                    throw new ProcessException("风险名单批量导入证件类型为空,错误出现在第" + (i + 1) + "页第" + (rowNum + 1) + "行");
                }
                if (StringUtils.isNotBlank(idNo)&&StringUtils.isBlank(idType)) {
                    logger.error("证件类型为空");
                    throw new ProcessException("风险名单批量导入证件类型为空,错误出现在第" + (i + 1) + "页第" + (rowNum + 1) + "行");
                }
                String cellPhone=DataTypeUtils.getStringValue(cellvalueMap.get("cellPhone"));
                if (StringUtils.isBlank(idNo)&&StringUtils.isBlank(idType)&&StringUtils.isBlank(cellPhone)){
                    logger.error("证件号码+证件类型 或 手机号码为空");
                    throw new ProcessException("风险名单批量导入证件号码+证件类型 或 手机号码为空,错误出现在第" + (i + 1) + "页第" + (rowNum + 1) + "行");
                }
                String validDate = DataTypeUtils.getStringValue(cellvalueMap.get("validDate"));
                //如果没有有效期则默认有效期时间为2099-01-01
                if (StringUtils.isBlank(validDate)) {
                    try {
                        cellvalueMap.put("validDate",new SimpleDateFormat("yyyy-MM-dd").parse("2099-01-01"));
                    } catch (ParseException e) {
                    throw new ProcessException("风险名单批量导入默认有效期时间时间转换失败"+ (i + 1) + "页第" + (rowNum + 1) + "行");
                    }
                }
                String actType = DataTypeUtils.getStringValue(cellvalueMap.get("actType"));
                if (StringUtils.isBlank(actType)) {
                    logger.error("风险名单类型为空");
                    throw new ProcessException("风险名单批量导入风险名单类型为空,错误出现在第" + (i + 1) + "页第" + (rowNum + 1) + "行");
                }
                //证件类型为身份证或临时身份证，则校验身份证有效性
                if ("I".equals(cellvalueMap.get("idType").toString())) {
                    // 证件号码有效性
                    if (!IdentificationCodeUtil.isIdentityCode(cellvalueMap.get("idNo").toString())) {
                        logger.error("证件号码无效");
                        throw new ProcessException("风险名单批量导入行证件号码无效,错误出现在第" + (i + 1) + "页第" + (rowNum + 1) + "行");
                    }
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                tmBatchUpload.setContent("风险名单来源="+(cellvalueMap.get("risklistSrc") == null ? "" : cellvalueMap.get("risklistSrc").toString()) + "|"
                        + "姓名="+(cellvalueMap.get("name") == null ? "" : cellvalueMap.get("name").toString()) + "|"
                        + "证件类型="+(cellvalueMap.get("idType") == null ? "" : cellvalueMap.get("idType").toString()) + "|"
                        + "证件号码="+(cellvalueMap.get("idNo") == null ? "" : cellvalueMap.get("idNo").toString()) + "|"
                        + "移动电话="+(cellvalueMap.get("cellPhone") == null ? "" : cellvalueMap.get("cellPhone").toString()) + "|"
                        + "家庭电话="+(cellvalueMap.get("homePhone") == null ? "" : cellvalueMap.get("homePhone").toString()) + "|"
                        + "记录有效期="+(cellvalueMap.get("validDate") == null ? "" : dateFormat.format(cellvalueMap.get("validDate"))) + "|"
                        + "风险名单类型="+(cellvalueMap.get("actType") == null ? "" : cellvalueMap.get("actType").toString()) + "|"
                        + "上风险名单原因="+(cellvalueMap.get("reason") == null ? "" : cellvalueMap.get("reason").toString()));
                uplist.add(tmBatchUpload);
                data.put(rowNum, cellvalueMap);
            }
            dataList.add(data);
        }
        return dataList;
    }

    /**
     * 数据分类处理
     *
     * @param dataList
     * @return
     */
    private List<TmRiskList> makeSheetSaveModel(List<Map<Integer, HashMap<String, Serializable>>> dataList) {

        List<TmRiskList> tmRiskLists = null;

        if (dataList != null && dataList.size() > 0) {

            tmRiskLists = new ArrayList<TmRiskList>();

            for (Map<Integer, HashMap<String, Serializable>> data : dataList) {
                for (Map.Entry<Integer, HashMap<String, Serializable>> en : data.entrySet()) {
                    HashMap<String, Serializable> cellvalueMap = en.getValue();
                    if (cellvalueMap.get("org") == null) {
                        cellvalueMap.put("org", OrganizationContextHolder.getOrg());
                    }

                    TmRiskList tmRiskList = new TmRiskList();
                    tmRiskList.setOrg(DataTypeUtils.getStringValue(cellvalueMap.get("org")));
                    tmRiskList.setRisklistSrc(DataTypeUtils.getStringValue(cellvalueMap.get("risklistSrc")));
                    tmRiskList.setName(DataTypeUtils.getStringValue(cellvalueMap.get("name")));
                    tmRiskList.setIdType(DataTypeUtils.getStringValue(cellvalueMap.get("idType")));
                    tmRiskList.setIdNo(DataTypeUtils.getStringValue(cellvalueMap.get("idNo")));
                    tmRiskList.setCellPhone(DataTypeUtils.getStringValue(cellvalueMap.get("cellPhone")));
                    tmRiskList.setHomePhone(DataTypeUtils.getStringValue(cellvalueMap.get("homePhone")));
                    tmRiskList.setHomeAdd(DataTypeUtils.getStringValue(cellvalueMap.get("homeAdd")));
                    tmRiskList.setCorpName(DataTypeUtils.getStringValue(cellvalueMap.get("corpName")));
                    tmRiskList.setEmpPhone(DataTypeUtils.getStringValue(cellvalueMap.get("empPhone")));
                    tmRiskList.setEmpAdd(DataTypeUtils.getStringValue(cellvalueMap.get("empAdd")));
                    tmRiskList.setReason(DataTypeUtils.getStringValue(cellvalueMap.get("reason")));
                    tmRiskList.setMemo(DataTypeUtils.getStringValue(cellvalueMap.get("memo")));
                    tmRiskList.setValidDate(DataTypeUtils.getDateValue(cellvalueMap.get("validDate")));
                    tmRiskList.setActType(DataTypeUtils.getStringValue(cellvalueMap.get("actType")));
                    tmRiskList.setCreateDate(new Date());

                    tmRiskLists.add(tmRiskList);
                }
            }
        }
        return tmRiskLists;
    }

    /**
     * 查询上传记录 分页查询
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/tmRiskUploadList")
    public Page<TmBatchUpload> tmRiskUploadList() {
        Page<TmBatchUpload> page = getPage(TmBatchUpload.class);
        Query query = getQuery();
        if (query!=null){
            page.setQuery(query);
        }
        //设定上传记录为RiskList（风险名单）
        query.put("uploadCategory","RiskList");
        riskListUploadService.getTmRiskUploadPage(page);
        for (TmBatchUpload tmBlackUpload : page.getRows()) {
            if (tmBlackUpload.getStartBpmn().equals("0")) {
                tmBlackUpload.setStartBpmn("上传成功");
            } else {
                tmBlackUpload.setStartBpmn("上传失败");
            }
        }
        return page;
    }

    /**
     * 删除某条上传记录
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteTmRiskUpload")
    public Json deleteTmRiskUpload(int id) {
        Json json = Json.newSuccess();
        //保存审计历史
        TmBatchUpload tmRiskUploadByKey = riskListUploadService.getTmRiskUploadByKey(id);
        systemAuditHistoryUtils.saveSystemAudit("风险名单上传记录","风险名单","DELETE",tmRiskUploadByKey.getContent(),"");
        riskListUploadService.deleteTmRiskUpload(id);
        return json;
    }

    /**
     * 下载某条上传记录
     *
     * @return
     */
    @RequestMapping("/download")
    public void download(int id) {
        HttpServletResponse response = getResponse();
        TmBatchUpload tmBatchUpload = riskListUploadService.getTmRiskUploadByKey(id);
        if (tmBatchUpload == null) {
            throw new ProcessException("没有找到下载的文件!");
        }
        response.setContentType("text/plain");
        String fileName;
        try {
            fileName = URLEncoder.encode(tmBatchUpload.getFileName().replace(".xls", ""), "UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".txt");
        } catch (UnsupportedEncodingException e1) {
        	logger.error("下载风险名单记录异常", e1);
        }
        BufferedOutputStream buff = null;
        ServletOutputStream outSTr = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            outSTr = response.getOutputStream(); // 建立
            buff = new BufferedOutputStream(outSTr);
            //把内容写入文件
            StringBuffer write = new StringBuffer();
            write.append("机构号："+tmBatchUpload.getOrg() + "||");
            write.append("文件名："+tmBatchUpload.getFileName() + "||");
            write.append("文件行号："+tmBatchUpload.getLineNo() + "||");
            write.append("文件内容："+tmBatchUpload.getContent() + "||");
            write.append("处理状态："+tmBatchUpload.getStartBpmn() + "||");
            write.append("失败原因："+tmBatchUpload.getFailReason() + "||");
            write.append("处理日期："+dateFormat.format(tmBatchUpload.getBatchDate()));
            write.append("\r\n");

            buff.write(write.toString().getBytes("UTF-8"));
            buff.flush();
            buff.close();
        } catch (Exception e) {
            logger.error("下载文件记录异常", e);
        } finally {
            try {
                buff.close();
                outSTr.close();
            } catch (Exception e) {
                logger.error("下载文件记录关闭异常", e);
            }
        }
    }

    @RequestMapping("/uploadTemplate")
    public void uploadTemplate() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = null;
        int cycle = 2;
        for (int a = 0; a < cycle; a++) {
            if (StringUtils.equals(StringUtils.valueOf(a), "0")) {
                sheet = workbook.createSheet("A");
            } else if (StringUtils.equals(StringUtils.valueOf(a), "1")) {
                sheet = workbook.createSheet("B");
            }
            sheet.autoSizeColumn((short)0); //调整第一列宽度
            sheet.autoSizeColumn((short)10);
            HSSFRow firstRow = sheet.createRow(0);
            HSSFCellStyle style = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            style.setFont(font);
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
            HSSFCell cell;
            String[] rowCNHeaders = {"风险名单来源", "姓名", "证件类型", "证件号码", "移动电话", "家庭电话", "家庭住址",
                    "公司名称", "公司电话", "公司地址", "上风险名单原因", "上风险名单说明", "有效记录期", "风险名单类型"};
            for (int i = 0; i < rowCNHeaders.length; i++) {
                cell = firstRow.createCell(i);
                cell.setCellValue(rowCNHeaders[i]);
                cell.setCellStyle(style);
            }
            HSSFRow secondRow = sheet.createRow(1);
            String[] rowENHeaders = {"risklistSrc", "name", "idType", "idNo", "cellPhone", "homePhone", "homeAdd",
                    "corpName", "empPhone", "empAdd", "reason", "memo", "validDate", "actType"};
            for (int i = 0; i < rowCNHeaders.length; i++) {
                cell = secondRow.createCell(i);
                cell.setCellValue(rowENHeaders[i]);
                cell.setCellStyle(style);
            }
        }
        try {
            HttpServletResponse response = getResponse();
            String fileName = "风险信息名单批量上传模板.xls";
            fileName = URLEncoder.encode(fileName, "UTF8");
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            OutputStream ouputStream = response.getOutputStream();
            workbook.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (IOException e) {
            logger.error("生成Excel表格失败！" + e.getMessage());
        }

    }



}