package com.jjb.ecms.app.controller.manage;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jjb.acl.biz.utils.SystemAuditHistoryUtils;
import org.apache.commons.io.FileExistsException;
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

import com.jjb.acl.biz.service.BranchService;
import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.acl.infrastructure.TmAclBranch;
import com.jjb.ecms.biz.service.manage.RiskListUploadService;
import com.jjb.ecms.biz.util.ErrorMsgUtil;
import com.jjb.ecms.facility.servicetask.item.TmBankItem;
import com.jjb.ecms.infrastructure.TmBatchUpload;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.CStruct2;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.DataTypeUtils;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;

/**
 *
 * @author BIG.LTM
 * modify by H.N 20171109
 *
 */

@Controller
@RequestMapping("/acl/branch")
public class BranchController extends BaseController{

	@Autowired
	private BranchService branchService	;
	@Autowired
	private RiskListUploadService riskListUploadService;
	@Autowired
	private SystemAuditHistoryUtils systemAuditHistoryUtils;

	private Logger logger = LoggerFactory.getLogger(getClass());
	List<TmBatchUpload> uplist;
	@RequestMapping("/page")
	public String page(){
//		return "acl/branch/branch.ftl";
		return "acl/branch/bankBranchManagePage_V1.ftl";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Page<TmAclBranch> list(){
		String branchId = getPara("branchId");
//		String name = getPara("branchName");
		String branchLevel = getPara("branchLevel");
		Page<TmAclBranch> page = getPage(TmAclBranch.class);
		page.getQuery().put("branchCode", branchId);
//		page.getQuery().put("branchName", name);
		page.getQuery().put("branchLevel", branchLevel);
		page = branchService.getPage(page);

/*		TmAclBranch tmAclBranch = new TmAclBranch();
		Page<TmAclBranch> page = getPage(TmAclBranch.class);
		if(null != branchId && !branchId.equals("")){
			tmAclBranch.setBranchCode(branchId);
		}
		if(null != name && !name.equals("")){
			tmAclBranch.setBranchName(name);
		}

		page = branchService.getPage(page, tmAclBranch);*/
		return page;
	}

	@RequestMapping("/addpage")
	public String addPage(){
		String branchCode  = getPara("branchCode");
		if(branchCode!=null){
			setAttr("branch", branchService.getTmAclBranch(branchCode));
		}

//		setBranchTree();
//		return "acl/branch/branch-form.ftl";
		return "acl/branch/bankBranchInfo_V1.ftl";
	}

	@ResponseBody
	@RequestMapping("/add")
	public Json add(TmAclBranch tmAclBranch){
		Json j = Json.newSuccess();
		try{
			if (StringUtils.isNotEmpty(tmAclBranch.getBranchCode()) && StringUtils.isNotEmpty(tmAclBranch.getBranchName())) {
				branchService.saveTmAclBranch(tmAclBranch);
				//记录审计历史
				systemAuditHistoryUtils.saveSystemAudit("网点机构名称: "+tmAclBranch.getBranchName(),"网点机构管理","SAVE","",tmAclBranch.convertToMap().toString());
			} else {
				j.setFail("不能插入空的机构网点");
			}
		}catch(Exception e){
			logger.error("新增网点机构失败", e);
			j.setFail(e.getMessage());
		}

		return j;
	}

	@RequestMapping("/editpage")
	public String editpage(){
		String branchCode  = getPara("branchCode");
//		TmAclBranch tmAclBranch = branchService.getTmAclBranch(branchCode);
//		setAttr("branch", tmAclBranch);
//		setBranchTree();
//		setEdit();
//
//
//		//是否只能查看，不启用编辑按钮
//		setAttr("noEdit", getPara("noEdit"));

//		return "acl/branch/branch-form.ftl";

		TmAclBranch tmAclBranch = branchService.getTmAclBranch(branchCode);
		setAttr("tmAclBranch", tmAclBranch);
		setEdit();
		return "acl/branch/bankBranchInfo_V1.ftl";
	}

	@ResponseBody
	@RequestMapping("/edit")
	public Json edit(TmAclBranch tmAclBranch){
		Json j = Json.newSuccess();

		try{
            TmAclBranch odeTmAclBranch = branchService.getTmAclBranch(tmAclBranch.getBranchCode());
			branchService.editTmAclBranch(tmAclBranch);
            //记录审计历史
			systemAuditHistoryUtils.saveSystemAudit("网点机构名称: "+tmAclBranch.getBranchName(),"网点机构管理","UPDATE",odeTmAclBranch.convertToMap().toString(),tmAclBranch.convertToMap().toString());
		}catch(Exception e){
			logger.error("编辑网点机构失败", e);
			j.setFail(e.getMessage());
		}
		return j;
	}

	@ResponseBody
	@RequestMapping("/delete")
	public Json delete(String branchCode){
		Json j = Json.newSuccess();
		try{
            TmAclBranch tmAclBranch = branchService.getTmAclBranch(branchCode);
            branchService.deleteTmAclBranch(branchCode);
			//记录审计历史
			systemAuditHistoryUtils.saveSystemAudit("网点机构名称: "+tmAclBranch.getBranchName(),"网点机构管理","DELETE",tmAclBranch.convertToMap().toString(),"");
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		return j;
	}

	@ResponseBody
	@RequestMapping("/deletes")
	public Json deletes(){
		Json j = Json.newSuccess();
		List<String> ids = getList(String.class, "ids");
		try{
			branchService.deleteBatchTmAclBranch(ids);
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		return j;
	}

	/**
	 *批量导入网点机构
	 */

	@RequestMapping("/fileUpload")
	public String fileUpload(String isEdit) {

		if (StringUtils.isNotBlank(isEdit) && isEdit.equals(Indicator.Y.name())) {
			setEdit();
		}

		return "/applyManage/bankList/blBankFileUpload_V1.ftl";
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
				throw new ProcessException("批量上传的网点机构不存在，请重试！");
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

			//获取sheet黑名单信息
			List<Map<Integer, HashMap<String, Serializable>>> sheetDataList = dealWithSheet(wbs, fileName);
			List<TmAclBranch> tmAclBranches = makeSheetSaveModel(sheetDataList);

			//发起工作流
			int dataNum = 0;
			for (TmAclBranch bank : tmAclBranches) {
				dataNum++;
				logger.info("文件上传提交开始：" + dataNum);
				try {
					/*personalBlack.setMemo("batch");*/
					/*更新单个网点机构*/
					branchService.saveTmAclBranch(bank);
				} catch (Exception e) {
					logger.error("文件上传保存 数据失败",e);
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
            //记录审计历史
			systemAuditHistoryUtils.saveSystemAudit("批量新增上传","网点机构管理","ALLSAVE","","");
			logger.info("文件上传提交结束：" + fileName);
			json.setMsg("文件上传成功!");

		} catch (FileExistsException e) {
			throw new ProcessException(e.getMessage());
		} catch (SecurityException e) {
			throw new ProcessException(e.getMessage());
		} catch (IllegalArgumentException e) {
			throw new ProcessException(e.getMessage());
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
			//第1个sheet:名称：“A-B” ,第2个sheet:名称：“S”,且每一张表的最大记录是10000条
			if (i == 0 && !childSheet.getSheetName().equals("A-B")) {
				throw new ProcessException("第1个sheet名称必须为A-B");
			}
			if (i == 1 && !childSheet.getSheetName().equals("S")) {
				throw new ProcessException("第2个sheet名称必须为S");
			}
			if (wbs.getSheetAt(i).getLastRowNum() > 10001) {
				throw new ProcessException("每个sheet页最大10000条记录，第" + (i + 1) + "工作表超过了一万条记录");
			}
		}
	}

	/**
	 * 处理批量黑名单文件数据
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
			for (int cellNum = 0; cellNum < hssfRow.getLastCellNum(); cellNum++) {//列号从0开始
				if (i == 0 && !ErrorMsgUtil.BankList[cellNum].equals(hssfRow.getCell(cellNum).getStringCellValue())) {
					throw new ProcessException("第" + (i + 1) + "页第2行第" + (cellNum + 1) + "列不符合模板规范。");
				}
				if (i == 1 && !ErrorMsgUtil.BankList[cellNum].equals(hssfRow.getCell(cellNum).getStringCellValue())) {
					throw new ProcessException("第" + (i + 1) + "页第2行第" + (cellNum + 1) + "列不符合模板规范。");
				}
			}
			// 存放excel数据Map<行号, HashMap<列名, 值>>
			Map<Integer, HashMap<String, Serializable>> data = new HashMap<Integer, HashMap<String, Serializable>>();
			for (int rowNum = 2; rowNum <= childSheet.getLastRowNum(); rowNum++) {//行号从0开始
				String validaters = "";

				TmBatchUpload tmBatchUpload = new TmBatchUpload();
				tmBatchUpload.setOrg(OrganizationContextHolder.getOrg());
				tmBatchUpload.setFileName(fileName);
				tmBatchUpload.setBatchDate(new Date());
				tmBatchUpload.setLineNo(rowNum + 1);
				tmBatchUpload.setStartBpmn("0");
				tmBatchUpload.setFailReason(null);
				tmBatchUpload.setUploadCategory("bankList");
				if (childSheet.getRow(rowNum) == null) {
					validaters += "不能取到第" + (i + 1) + "页第" + (rowNum + 1) + "行数据.";
					tmBatchUpload.setStartBpmn("1");
					tmBatchUpload.setFailReason(validaters);
					uplist.add(tmBatchUpload);
					continue;
				}

				HashMap<String, Serializable> cellvalueMap = new HashMap<String, Serializable>();
				// 循环第二行key值获取值
				CStruct2 cs = new CStruct2<TmBankItem>(TmBankItem.class, "gbk");

				for (int cellNum = 0; cellNum < hssfRow.getLastCellNum(); cellNum++) {
					String cellname = hssfRow.getCell(cellNum).getStringCellValue();
					String cellValue = childSheet.getRow(rowNum).getCell(cellNum) == null ? "" : childSheet.getRow(rowNum).getCell(cellNum).toString().trim();
					if (org.apache.commons.lang.StringUtils.isBlank(cellValue)) {//对空值的处理
						cellValue = null;
					}
					cellvalueMap = cs.putValue(cellname, cellValue, cellvalueMap);
					validaters = cs.validatefield(cellname, cellvalueMap.get(cellname));//内容格式、长度验证
					if (org.apache.commons.lang.StringUtils.isNotBlank(validaters)) {
						logger.error(cellname + "字段不合法");
						throw new ProcessException(validaters + ",错误出现在第" + (i + 1) + "页第" + (rowNum + 1) + "行第" + (cellNum + 1) + "列");
					}
				}
				if (cellvalueMap == null || cellvalueMap.isEmpty() || cellvalueMap.size() == 0) {
					break;
				}
/*				//必输项验证
				String appType = DataTypeUtils.getStringValue(cellvalueMap.get("blacklistSrc"));
				if (org.apache.commons.lang.StringUtils.isBlank(appType)) {
					logger.error("黑名单来源为空");
					throw new ProcessException("黑名单批量导入数据来源为空,错误出现在第" + (i + 1) + "页第" + (rowNum + 1) + "行");
				}
				String idType = DataTypeUtils.getStringValue(cellvalueMap.get("idType"));
				if (org.apache.commons.lang.StringUtils.isBlank(idType)) {
					logger.error("证件类型为空");
					throw new ProcessException("黑名单批量导入证件类型为空,错误出现在第" + (i + 1) + "页第" + (rowNum + 1) + "行");
				}
				String idNo = DataTypeUtils.getStringValue(cellvalueMap.get("idNo"));
				if (org.apache.commons.lang.StringUtils.isBlank(idNo)) {
					logger.error("证件号为空");
					throw new ProcessException("黑名单批量导入证件号为空,错误出现在第" + (i + 1) + "页第" + (rowNum + 1) + "行");
				}
				String validDate = DataTypeUtils.getStringValue(cellvalueMap.get("validDate"));
				if (org.apache.commons.lang.StringUtils.isBlank(validDate)) {
					logger.error("记录有效期为空");
					throw new ProcessException("黑名单批量导入记录有效期为空,错误出现在第" + (i + 1) + "页第" + (rowNum + 1) + "行");
				}
				String actType = DataTypeUtils.getStringValue(cellvalueMap.get("actType"));
				if (org.apache.commons.lang.StringUtils.isBlank(actType)) {
					logger.error("行动类型为空");
					throw new ProcessException("黑名单批量导入行动类型为空,错误出现在第" + (i + 1) + "页第" + (rowNum + 1) + "行");
				}
				//证件类型为身份证或临时身份证，则校验身份证有效性
				if ("I".equals(cellvalueMap.get("idType").toString())) {
					// 证件号码有效性
					if (!IdentificationCodeUtil.isIdentityCode(cellvalueMap.get("idNo").toString())) {
						logger.error("证件号码无效");
						throw new ProcessException("黑名单批量导入行证件号码无效,错误出现在第" + (i + 1) + "页第" + (rowNum + 1) + "行");
					}
				}*/
				tmBatchUpload.setContent((cellvalueMap.get("branchCode") == null ? "" : cellvalueMap.get("branchCode").toString()) + "|"
						+ (cellvalueMap.get("parentCode") == null ? "" : cellvalueMap.get("parentCode").toString()) + "|"
						+ (cellvalueMap.get("branchName") == null ? "" : cellvalueMap.get("branchName").toString()) + "|"
						+ (cellvalueMap.get("city") == null ? "" : cellvalueMap.get("city").toString()) + "|"
						+ (cellvalueMap.get("empAdd") == null ? "" : cellvalueMap.get("empAdd").toString()) + "|"
						+ (cellvalueMap.get("cardCollectInd") == null ? "" : cellvalueMap.get("cardCollectInd").toString()) + "|"
						+ (cellvalueMap.get("branchIssueInd") == null ? "" : cellvalueMap.get("branchIssueInd").toString()));
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
	 * @return*/


	private List<TmAclBranch> makeSheetSaveModel(List<Map<Integer, HashMap<String, Serializable>>> dataList) {

		List<TmAclBranch> bankList = null;

		if (dataList != null && dataList.size() > 0) {

			bankList = new ArrayList<TmAclBranch>();

			for (Map<Integer, HashMap<String, Serializable>> data : dataList) {
				for (Map.Entry<Integer, HashMap<String, Serializable>> en : data.entrySet()) {
					HashMap<String, Serializable> cellvalueMap = en.getValue();
             /*       if (cellvalueMap.get("org") == null) {
                        cellvalueMap.put("org", OrganizationContextHolder.getOrg());
                    }*/

					TmAclBranch bank = new TmAclBranch();
					bank.setBranchCode(DataTypeUtils.getStringValue(cellvalueMap.get("branchCode")));
					bank.setBranchName(DataTypeUtils.getStringValue(cellvalueMap.get("branchName")));
					bank.setEmpAdd(DataTypeUtils.getStringValue(cellvalueMap.get("empAdd")));
//					bank.setCity(DataTypeUtils.getStringValue(cellvalueMap.get("city")));
					bank.setParentCode(DataTypeUtils.getStringValue(cellvalueMap.get("parentCode")));
					bank.setBranchIssueInd(DataTypeUtils.getStringValue(cellvalueMap.get("branchIssueInd")));
					bank.setCardCollectInd(DataTypeUtils.getStringValue(cellvalueMap.get("cardCollectInd")));

					bankList.add(bank);
				}
			}
		}
		return bankList;
	}





//	//设置机构树信息
//	private void setBranchTree(){
//		List<TmAclBranch> branchList = branchService.getAllBranch();
//		String branchTree = JSONObject.toJSONString(branchList);
//		setAttr("branchTree", branchTree);
//	}


}
