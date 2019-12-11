package com.jjb.ecms.app.controller.report;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.ecms.app.controller.utils.PoiExcelUtil;
import com.jjb.ecms.biz.service.report.ApplyRejectReportService;
import com.jjb.ecms.facility.dto.ApplyRejectMonthDto;
import com.jjb.unicorn.facility.kits.StrKit;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * 
 * @ClassName ApplyRejectMonthReportController
 * @Description TODO 审批拒绝汇总月报
 * @author H.N
 * @Date 2017年12月22日 上午10:19:48
 * @version 1.0.0
 */
@Controller
@RequestMapping("/applyRejectMonth")
public class ApplyRejectMonthReportController extends BaseController{

	private Logger logger = org.slf4j.LoggerFactory.getLogger(ApplyRejectMonthReportController.class);
	
	@Autowired
	private ApplyRejectReportService applyRejectReportService;
	
	@Value("#{env['exportExcelDir']}")
	private String exportExcelDir;
	
	@RequestMapping("getPage")
	public String page(){
		return "report/applyRejectReportMonth.ftl";
	}
	
	@RequestMapping("getList")
	@ResponseBody
	public Page<ApplyRejectMonthDto> getRejectMonthPage(){
		Page<ApplyRejectMonthDto> page = getPage(ApplyRejectMonthDto.class);
		String updateDate = (String) page.getQuery().get("updateDate");
		if(updateDate!=null && updateDate !=""){
			updateDate = updateDate+"-01";
		}else{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date nowDate = new Date();
			updateDate = dateFormat.format(nowDate);
		}
		page.getQuery().put("updateDate", updateDate);
		page = applyRejectReportService.getRejectPageMonth(page);
		
		return page;
	}
	
	@RequestMapping("/export")
	public void exportInstallmentData(HttpServletRequest request,HttpServletResponse response){
		try{
			String productCd = getPara("productCd");
			String owningBranch = getPara("owningBranch");
			String updateDate = getPara("updateDate");
			String out = updateDate;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productCd", productCd);
			map.put("owningBranch", owningBranch);
			if(updateDate!=null && updateDate !=""){
				updateDate = updateDate+"-01";
			}
			map.put("updateDate", updateDate);
			
			if(StrKit.isBlank("updateDate")||StrKit.isBlank("updateDate")){
				logger.info("导出标准大额分期放款状态报表失败，没有选择导出时间");
				return;
			}
			List<ApplyRejectMonthDto> list = applyRejectReportService.getRejectReprtMonth(map);
			
			final String fileName = "审批拒绝汇总月报("+out+").xlsx";
			final String filePath = exportExcelDir;
			String fields = "申请编号,姓名,证件类型,证件号,产品代码,产品描述,审批状态,审批状态描述,审批时间,分支行";
    		String[] numberCell = {};
    		Map<String,String> bookMappings = new LinkedHashMap<String,String>();
    		bookMappings.put("申请编号","appNO");
    		bookMappings.put("姓名","name");
    		bookMappings.put("证件类型","idType");
    		bookMappings.put("证件号","idNo");
    		bookMappings.put("产品代码","productCd");
    		bookMappings.put("产品描述","productDesc");
    		bookMappings.put("审批状态","rtfState");
    		bookMappings.put("审批状态描述","stateDesc");
    		bookMappings.put("审批时间","failedTime");
    		bookMappings.put("分支行","owningBranch");
			PoiExcelUtil.exportTempExcelAndDownload(getRequest(),getResponse(),filePath,fileName,"标准大额分期报表"+out,fields,list,bookMappings,numberCell);
		}catch(Exception e){
			logger.error("导出标准大额分期放款失败明细报表出错",e);
		}
	}
	
}
