 package com.jjb.ecms.app.controller.manage;


import javax.swing.SortOrder;

import com.jjb.acl.biz.utils.SystemAuditHistoryUtils;
import com.jjb.ecms.biz.util.BizAuditHistoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.ecms.biz.service.manage.TmAppAuditQuotaService;
import com.jjb.ecms.infrastructure.TmAppAuditQuota;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * @description 终审人员额度分配查询Controller
 * @author J.J
 * @date 2017年7月11日下午5:34:41
 */

@Controller
@RequestMapping("/auditQuota")
public class ApplyAuditQuotaController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	
	@Autowired
	private TmAppAuditQuotaService tmAppAuditQuotaService;

	@Autowired
	private BizAuditHistoryUtils bizAuditHistoryUtils;
	@Autowired
	private SystemAuditHistoryUtils systemAuditHistoryUtils;


	/**
	 * 审批人员额度分配页面
	 */
	@RequestMapping("/auditQuotaPage")
	public String page(){
		return "/applyManage/applyFinalAuditQuota/applyFinalAuditQuotaPage_V1.ftl";
	}
	
	/**
	 * 审批人员额度分配列表
	 */
	@ResponseBody
	@RequestMapping("/auditQuotaList")
	public Page<TmAppAuditQuota> list(TmAppAuditQuota TmAppAuditQuota){
		Page<TmAppAuditQuota> page = getPage(TmAppAuditQuota.class);
		page.setSortName("id");
		page.setSortOrder(SortOrder.ASCENDING.name());
		page = tmAppAuditQuotaService.getPage(page,TmAppAuditQuota);
		return page;
	}
	
	/**
	 * 审批人员额度分配编辑页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/auditQuotaEditPage")
	public String auditQuotaEditPage(Integer id){
		if (id != null) {
			TmAppAuditQuota TmAppAuditQuota = tmAppAuditQuotaService.getTmAppAuditQuotaById(id);
			if (TmAppAuditQuota != null) {
				setAttr("tmAppAuditQuota",TmAppAuditQuota);
				setEdit();
			}
		}
		return "/applyManage/applyFinalAuditQuota/applyFinalAuditQuotaEditPage_V1.ftl";
	}
	
	/**
	 * 审批人员额度信息编辑
	 * @param 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/auditQuotaEdit")
	public Json auditQuotaEdit(TmAppAuditQuota TmAppAuditQuota){
		Json json = Json.newSuccess();
		if (TmAppAuditQuota != null) {
			if (TmAppAuditQuota.getId() != null) {
				try {
					tmAppAuditQuotaService.updateTmAppAuditQuota(TmAppAuditQuota);
					TmAppAuditQuota tmAppAuditQuotaById = tmAppAuditQuotaService.getTmAppAuditQuotaById(TmAppAuditQuota.getId());
					systemAuditHistoryUtils.saveSystemAudit("操作员ID: "+TmAppAuditQuota.getOperatorId(),"审批额度管理","UPDATE",tmAppAuditQuotaById.convertToMap().toString(),TmAppAuditQuota.convertToMap().toString());
				} catch (Exception e) {
					logger.error("审批人员额度信息更新失败",e);
					json.setS(false);
					json.setMsg("审批人员额度信息更新失败！");
				}
			} else {
				try {
					tmAppAuditQuotaService.saveTmAppAuditQuota(TmAppAuditQuota);
					//保存审计历史
					systemAuditHistoryUtils.saveSystemAudit("操作员ID: "+TmAppAuditQuota.getOperatorId(),"审批额度管理","SAVE","",TmAppAuditQuota.convertToMap().toString());

				} catch (Exception e) {
					logger.error("审批人员额度信息添加失败",e);
					json.setS(false);
					json.setMsg("操作员 [" + TmAppAuditQuota.getOperatorId() + "] 已存在！");
					logger.error("操作员 [" + TmAppAuditQuota.getOperatorId() + "] 已存在！");
				}
			}
		} 
		return json;
	}
	
	/**
	 * 删除终审人员额度分配
	 */
	@ResponseBody
	@RequestMapping("/auditQuotaDelete")
	public Json deleteauditQuota(Integer id){
		Json json = Json.newSuccess();
		try{
			//保存审计历史
			TmAppAuditQuota tmAppAuditQuotaById = tmAppAuditQuotaService.getTmAppAuditQuotaById(id);
			systemAuditHistoryUtils.saveSystemAudit("操作员ID: "+tmAppAuditQuotaById.getOperatorId(),"审批额度管理","DELETE",tmAppAuditQuotaById.convertToMap().toString(),"");
			tmAppAuditQuotaService.deleteTmAppAuditQuotaById(id);
		}catch(Exception e){
			json.setFail(e.getMessage());
		}
		return json;
	}

}
