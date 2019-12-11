package com.jjb.acl.mvc.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.acl.biz.service.AuthAuditDetailService;
import com.jjb.acl.biz.service.AuthAuditLogService;
import com.jjb.acl.biz.service.RoleResourceService;
import com.jjb.acl.biz.service.RoleService;
import com.jjb.acl.biz.service.UserRoleService;
import com.jjb.acl.biz.service.UserService;
import com.jjb.acl.facility.enums.auth.AuditType;
import com.jjb.acl.facility.enums.auth.AuthStatus;
import com.jjb.acl.infrastructure.TmAclAuthAuditLog;
import com.jjb.acl.infrastructure.TmAclAuthDetail;
import com.jjb.acl.infrastructure.TmAclRole;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * 
 * @ClassName AuthCheckController
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author H.N
 * @Date 2017年12月5日 下午4:07:43
 * @version 1.0.0
 */
@Controller
@RequestMapping("/acl/authAudit")
public class AuthAuditController extends BaseController{
	@Autowired
	private AuthAuditLogService authAuditLogService	;
	
	@Autowired
	private RoleResourceService roleResourceService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private AuthAuditDetailService authAuditDetailService;
	@Autowired
	private UserService userService;
	
	@RequestMapping("/page")
	public String page(){
		
		return "acl/auth/authAudit.ftl";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Page<TmAclAuthAuditLog> list(){
		//只展示当前登陆者能审核的数据，待审核的数据。
		Page<TmAclAuthAuditLog> page = getPage(TmAclAuthAuditLog.class);
		page = authAuditLogService.getPage(page,"WL");
		return page;
	}
	
	
	@RequestMapping("/mySubmitPage")
	public String mySubmitPage(){
		
		return "acl/auth/mySubmit-list.ftl";
	}

	@ResponseBody
	@RequestMapping("/mySubmitList")
	public Page<TmAclAuthAuditLog> mySubmitList(){
		//只展示当前登陆者提交过的数据。
		Page<TmAclAuthAuditLog> page = getPage(TmAclAuthAuditLog.class);
		page = authAuditLogService.getPage(page,"MS");
		return page;
	}
	
	@RequestMapping("/myCheckedPage")
	public String myCheckedPage(){
		
		return "acl/auth/myChecked-list.ftl";
	}

	@ResponseBody
	@RequestMapping("/myCheckedList")
	public Page<TmAclAuthAuditLog> myCheckedList(){
		//只展示当前登陆者提交过的数据。
		Page<TmAclAuthAuditLog> page = getPage(TmAclAuthAuditLog.class);
		page = authAuditLogService.getPage(page,"MC");
		return page;
	}
	
	@ResponseBody
	@RequestMapping("/agree")
	public Json agree(){
		//授权审核通过
		Json j = Json.newSuccess();
		Integer logId = getParaToInt("id");
		TmAclAuthAuditLog tmAclAuthAuditLog = authAuditLogService.getTmAclAuthAuditLogById(logId);
		try{
			/*
			 * 1.登陆者不能审核自己提交的数据。
			 * 2.审核通过后，将相应角色的变更更新到用户角色表或角色资源表中，资源权限变更即可生效；重置密码类型审核通过，做重置密码操作；
			 */
			if(!tmAclAuthAuditLog.getStatus().equals(AuthStatus.W.name())){
				j.setFail("该笔授权申请已审核，请刷新！");
				return j;
			}
			String userNo = OrganizationContextHolder.getUserNo();//登录用户名
			if(userNo.equals(tmAclAuthAuditLog.getCreateBy())){
				j.setFail("不能审核自己提交的授权申请！");
				return j;
			}
			String auditType = tmAclAuthAuditLog.getAuditType();
			//在更新用户对应角色表时，也要更新角色主表的更新时间和更新者
			TmAclRole tmAclRole = new TmAclRole();
			
			tmAclRole.setRoleId(tmAclAuthAuditLog.getRoleId());
			tmAclRole.setUpdateBy(userNo);
			tmAclRole.setUpdateDate(new Date());
			if(AuditType.A.name().equals(auditType)){
				//角色成员添加
				List<String> userIds = new ArrayList<String>();
				userIds.add(tmAclAuthAuditLog.getUserId().toString());
				userRoleService.saveTmAclRoleUsers(tmAclAuthAuditLog.getRoleId(), userIds);
				roleService.updateRole(tmAclRole);
				
			}else if(AuditType.B.name().equals(auditType)){
				//角色资源添加
				List<TmAclAuthDetail> details = authAuditDetailService.getTmAclAuthDetailByLogId(logId);
				String[] resources = new String[details.size()];
				String sysType = null;
				for (int i = 0; i < details.size(); i++) {
					resources[i] = details.get(i).getResourceCode();
					sysType = details.get(i).getSysType();
				}
				roleResourceService.saveRoleResource(tmAclAuthAuditLog.getRoleId(), sysType, resources);
				roleService.updateRole(tmAclRole);
			}else if(AuditType.C.name().equals(auditType)){
				//用户重置密码
				userService.resetPassword(tmAclAuthAuditLog.getUserNo());
				
			}else{
				j.setFail("未定义的审核类型，请检查！");
				return j;
			}
			
			tmAclAuthAuditLog.setAuditBranchNo(OrganizationContextHolder.getBranchCode());
			tmAclAuthAuditLog.setCheckBy(userNo);
			tmAclAuthAuditLog.setCheckDate(new Date());
			tmAclAuthAuditLog.setStatus(AuthStatus.S.name());
			authAuditLogService.updateAuditLog(tmAclAuthAuditLog);
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	
	@RequestMapping("/checkRemarkPage")
	public String checkRemarkPage(){
		Integer logId = getParaToInt("logId");
		setAttr("logId", logId);
		return "acl/auth/audit-remark.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/refuse")
	public Json refuse(){
		//授权审核拒绝
		Json j = Json.newSuccess();
		Integer logId = getParaToInt("logId");
		String checkRemark = getPara("check_remark");
		TmAclAuthAuditLog tmAclAuthAuditLog = authAuditLogService.getTmAclAuthAuditLogById(logId);
		try{
			/*
			 * 1.登陆者不能审核自己提交的数据。
			 * 2.审核拒绝不做任何操作
			 */
			if(!tmAclAuthAuditLog.getStatus().equals(AuthStatus.W.name())){
				j.setFail("该笔授权申请已审核，请刷新！");
				return j;
			}
			String userNo = OrganizationContextHolder.getUserNo();//登录用户名
			if(userNo.equals(tmAclAuthAuditLog.getCreateBy())){
				j.setFail("不能审核自己提交的授权申请！");
				return j;
			}
			
			tmAclAuthAuditLog.setAuditBranchNo(OrganizationContextHolder.getBranchCode());
			tmAclAuthAuditLog.setCheckBy(userNo);
			tmAclAuthAuditLog.setCheckDate(new Date());
			tmAclAuthAuditLog.setStatus(AuthStatus.F.name());
			tmAclAuthAuditLog.setCheckRemark(checkRemark);
			authAuditLogService.updateAuditLog(tmAclAuthAuditLog);
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		
		return j;
	}

}
