/**
 * 
 */
package com.jjb.ams.app.controller.query;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
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
import com.jjb.ams.app.controller.AmsDataController;
import com.jjb.ecms.biz.service.common.CommonService;
import com.jjb.ecms.biz.service.query.ApplyProcessQueryService;
import com.jjb.ecms.facility.dto.ApplyProcessQueryDto;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: 申请进度查询
 * @author JYData-R&D-BigK.K
 * @date 2016年9月18日 上午9:57:08
 * @version V1.0
 */
@Controller
@RequestMapping("/ams_applyProcess")
public class AmsApplyProcessController extends AmsDataController {
	@Autowired
	private ApplyProcessQueryService applyProcessQueryService;
//	@Autowired
//	private ApplyQueryService applyQueryService;
//	@Autowired
//	private ApplyInputService applyInputService;
	@Autowired
	private CommonService commonService;
//	@Autowired
//	private CacheContext cacheContext;
//	@Autowired
//	private TmAppHistoryDao tmAppHistoryDao;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 加载页面
	 * 
	 * @return
	 */
	@RequestMapping("/applyProcessQuery")
	public String applyProcessQuery() {

		Query query = getBasicQuery();
		if(query!=null){
			setAttr("query", query);
		}
		return "amsManage/applyInfo/applyInfoQuery.ftl";
	}

	/**
	 * 申请进度查询
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
	public Json viewDetailPermissions(){
		String permission=getPara("permission");
		Json json=Json.newSuccess();
		String org=OrganizationContextHolder.getOrg();
		String userNo=OrganizationContextHolder.getUserNo();
		List<String> resourceCodeList=new ArrayList<String>();
		if(StringUtils.isNotEmpty(org)&&StringUtils.isNotEmpty(userNo)){
			resourceCodeList=commonService.getCurrUserResourceCodeList();
		}
		if(CollectionUtils.isNotEmpty(resourceCodeList)){
			if(permission!=null&&permission.equals("false")){//申请进度查询详细页面
				if(!resourceCodeList.contains(EcmsAuthority.APPLY_PROCESS_QUERY_DETAIL.name())){
					String msg="无查看详情权限！机构号["+org+"],用户名["+userNo+"]";
					json.setFail(msg);
				}
			}
			if(permission!=null&&permission.equals("true")){//重审权限
				if(!resourceCodeList.contains(EcmsAuthority.APPLY_REAUDIT.name())){
					String msg="无重审权限！机构号["+org+"],用户名["+userNo+"]";
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
	public Json applyModifyInfoSave(){
		Json json = Json.newSuccess();
		Query query = getBasicQuery();
		setBasicData(query,"申请件进度查询的修改","I",logger);
		json.setMsg("操作成功！");
	
		return json;
	}
	
	/**
	 * 导出到excel
	 * @throws ParseException 
	 */

	@RequestMapping("/exportExcel")
	public void exportToExcel(){
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
		sheet.autoSizeColumn((short)0); //调整第一列宽度
		sheet.autoSizeColumn((short)10);
		HSSFRow row = sheet.createRow(0);
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		String[] rowHeaders = { "申请件编号","姓名","证件类型","证件号码","申请类型","卡产品代码","受理网点","移动电话","审批状态", "任务所属人", "影像","拒绝原因"};
		HSSFCell cell;
		for (int i = 0; i < rowHeaders.length; 	i++) {
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
			cell.setCellValue(list.get(i).getAppType());
			cell = row.createCell(5);
			cell.setCellValue(list.get(i).getProductCd());
			cell = row.createCell(6);
			cell.setCellValue(list.get(i).getOwningBranch());
			cell = row.createCell(7);
			cell.setCellValue(list.get(i).getCellPhone());
			cell = row.createCell(8);
			cell.setCellValue(list.get(i).getRtfState());
			cell = row.createCell(9);
			cell.setCellValue(list.get(i).getOwner());
			cell = row.createCell(10);
			cell.setCellValue(list.get(i).getImageNum());
			cell = row.createCell(11);
			cell.setCellValue(list.get(i).getRefuseCode());	
		}
		try {
			HttpServletResponse response = getResponse();
			String fileName = "申请件进度查询信息表.xls";
			fileName = URLEncoder.encode(fileName,"UTF8"); 
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			OutputStream ouputStream = response.getOutputStream();
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (IOException e) {
			logger.warn("申请件进度查询信息表下载失败！"+e.getMessage());
		}
	}
	
}
