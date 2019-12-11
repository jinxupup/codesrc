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

import com.jjb.acl.infrastructure.TmAclBranch;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.service.manage.ApplySpreaderInfoService;
import com.jjb.ecms.infrastructure.TmAppSprePerBank;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * @description 推广人信息维护Controller
 * @author J.J
 * @date 2017年7月6日下午5:06:57
 */
	@Controller
	@RequestMapping("/spreaderInfo")
	public class ApplySpreaderInfoController extends BaseController {

		private Logger logger = LoggerFactory.getLogger(getClass());

		@Autowired
		private CacheContext cacheContext;

		@Autowired
		private ApplySpreaderInfoService applySpreaderInfoService;

		@Autowired
		private BizAuditHistoryUtils bizAuditHistoryUtils;
		@Autowired
		private SystemAuditHistoryUtils systemAuditHistoryUtils;


	/**
		 * 推广人信息管理页面
		 */
		@RequestMapping("/spreaderPage")
		public String page(){
			return "/applyManage/applySpreader/applySpreaderPage_V1.ftl";
		}
		
		/**
		 * 推广人信息管理列表
		 */
		@ResponseBody
		@RequestMapping("/spreaderList")
		public Page<TmAppSprePerBank> list(){
			Page<TmAppSprePerBank> page = getPage(TmAppSprePerBank.class);
			page.setSortName("id");
			page.setSortOrder(SortOrder.ASCENDING.name());
			page = applySpreaderInfoService.getPageForTranferUser(page);
			return page;
		}
		
		/**
		 * 推广人信息保存/修改页面
		 * @param id
		 * @return
		 */
		@RequestMapping("/spreaderEditPage")
		public String spreaderEditPage(Integer id){
			if (id != null) {
				TmAppSprePerBank spreader = applySpreaderInfoService.getSpreaderById(id);
				if (spreader != null) {
					setAttr("tmAppSprePerBank",spreader);
					setEdit();
				}
			}
			return "/applyManage/applySpreader/applySpreaderEditPage_V1.ftl";
		}
		
		/**
		 * 推广人信息保存/修改
		 * @param
		 * @return
		 */
		@ResponseBody
		@RequestMapping("/spreaderEdit")
		public Json spreaderEdit() {
			Json json = Json.newSuccess();
			TmAppSprePerBank tmAppSprePerBank = getBean(TmAppSprePerBank.class);
			TmAclBranch tmAclBranch=cacheContext.getTmAclBranchByCode(tmAppSprePerBank.getSpreaderBankId());
			//String tmAppSprePerBankName=tmAclBranch.getBranchName();
			String tmAppSprePerSpreaderOrg=tmAclBranch.getParentCode();
			if(StringUtils.isNotEmpty(tmAclBranch.getParentCode())){
				TmAclBranch parentBr =cacheContext.getTmAclBranchByCode(tmAclBranch.getParentCode());
				tmAppSprePerSpreaderOrg=parentBr.getBranchCode()+"-"+parentBr.getBranchName();
			}
			//tmAppSprePerBank.setSpreaderBankName(tmAppSprePerBankName);
			tmAppSprePerBank.setSpreaderOrg(tmAppSprePerSpreaderOrg);
			if (tmAppSprePerBank != null) {
				if (tmAppSprePerBank.getId() != null) {
					try {
						TmAppSprePerBank spreaderById = applySpreaderInfoService.getSpreaderById(tmAppSprePerBank.getId());
						applySpreaderInfoService.updateSpreader(tmAppSprePerBank);
						//保存审计历史
						systemAuditHistoryUtils.saveSystemAudit("推广人姓名:"+tmAppSprePerBank.getSpreaderName(),"推广人信息","UPDATE",spreaderById.convertToMap().toString(),tmAppSprePerBank.convertToMap().toString());
					} catch (ProcessException e) {
						json.setS(false);
						json.setFail("推广人"+tmAppSprePerBank.getSpreaderName()+"信息为空！修改失败！");
						logger.error("推广人"+tmAppSprePerBank.getSpreaderName()+"信息为空！修改失败！");
					}
				} else {
					try {
						applySpreaderInfoService.saveSpreader(tmAppSprePerBank);
						//保存审计历史
						systemAuditHistoryUtils.saveSystemAudit("推广人姓名:"+tmAppSprePerBank.getSpreaderName(),"推广人信息","SAVE","",tmAppSprePerBank.convertToMap().toString());

					} catch (ProcessException e) {
						json.setS(false);
						json.setFail("推广人"+tmAppSprePerBank.getSpreaderName()+"信息为空或已存在！保存失败！");
						logger.error("推广人"+tmAppSprePerBank.getSpreaderName()+"信息为空或已存在！保存失败！");
					}
				}
			}
			return json;
		}
		
		/**
		 * 删除推广人信息
		 */
		@ResponseBody
		@RequestMapping("/spreaderDelete")
		public Json delete(Integer id){
			Json j = Json.newSuccess();
			//保存审计历史
			TmAppSprePerBank spreader = applySpreaderInfoService.getSpreaderById(id);
			systemAuditHistoryUtils.saveSystemAudit("推广人姓名:"+spreader.getSpreaderName(),"推广人信息","DELETE",spreader.convertToMap().toString(),"");
			try{
				applySpreaderInfoService.deleteSpreaderById(id);
			}catch(Exception e){
				j.setFail(e.getMessage());
			}
			return j;
		}
		
		@ResponseBody
		@RequestMapping("/deletes")
		public Json deletes(){
			Json j = Json.newSuccess();
			return j;
		}
		
	}


