package com.jjb.acl.mvc.web.controller;

import java.util.List;

import com.jjb.acl.biz.utils.SystemAuditHistoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.acl.biz.service.AuthAuditLogService;
import com.jjb.acl.biz.service.BranchService;
import com.jjb.acl.biz.service.UserService;
import com.jjb.acl.facility.enums.auth.AuditType;
import com.jjb.acl.facility.enums.auth.ResourceAuthType;
import com.jjb.acl.facility.enums.auth.UserType;
import com.jjb.acl.infrastructure.TmAclAuthAuditLog;
import com.jjb.acl.infrastructure.TmAclBranch;
import com.jjb.acl.infrastructure.TmAclUser;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * 
 * @author BIG.D.W.K
 *
 */
@Controller
@RequestMapping("/acl/user")
public class UserController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserService userService;
//	@Autowired
//	private RoleService roleService;
	@Autowired
	private AuthAuditLogService authAuditLogService;
	@Autowired
	private BranchService branchService;
	@Autowired
	private SystemAuditHistoryUtils systemAuditHistoryUtils;
	
	@RequestMapping("/page")
	public String page(){
		return "acl/user/user.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/list")
	public Page<TmAclUser> list(){
		Page<TmAclUser> page = getPage(TmAclUser.class);
		if(null==page.getQuery()){
			page.setQuery(new Query());
		}
		TmAclBranch tmAclBranch = branchService.getTmAclBranch(OrganizationContextHolder.getBranchCode());
		if(tmAclBranch!=null && tmAclBranch.getBranchLevel().equals("1"))
			page.getQuery().put("branchLevel", "1");
		if(!"IT".equals(OrganizationContextHolder.getUserNo())){
			page.getQuery().put("notIsSuperUser", "Y");
		}
		page = userService.getUserPage(page);
		
		return page;
	}
	
	@ResponseBody
	@RequestMapping("/listWithOutRole")
	public Page<TmAclUser> listWithOutRole(){
		Page<TmAclUser> page = getPage(TmAclUser.class);
		if(null==page.getQuery()){
			page.setQuery(new Query());
		}
		TmAclBranch tmAclBranch = branchService.getTmAclBranch(OrganizationContextHolder.getBranchCode());
		if(!tmAclBranch.getBranchLevel().equals("1"))
			page.getQuery().put("byBranchFlag", "true");
		Integer roleId = getParaToInt("roleId");
		page = userService.selectWithOutRoleId(page, roleId);
		
		return page;
	}
	
	@ResponseBody
	@RequestMapping("/edit")
	public Json edit(TmAclUser tmAclUser){
		Json j = Json.newSuccess();
		
		try{
			TmAclUser userByUserNo = userService.getUserByUserNo(tmAclUser.getUserNo());
			userService.editTmAclUser(tmAclUser);
			//保存审计历史
			systemAuditHistoryUtils.saveSystemAudit("登陆名: "+tmAclUser.getUserNo(),"风险名单","UPDATE",userByUserNo.convertToMap().toString(),tmAclUser.convertToMap().toString());
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	
	@RequestMapping("/editpage")
	public String editpage(int userId){
		
		TmAclUser tmAclUser = userService.getTmAclUser(userId);
		setAttr("user", tmAclUser);
		setEdit();
		return "acl/user/user-form.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public Json add(TmAclUser tmAclUser){
		
		Json j = Json.newSuccess();
		
		try{
			/*
			 * 若新增用户类型为管理员，所属机构必须是下级机构，
			 * 等同于管理员用户只能由上级机构创建，最高级机构管理员由IT初始化，IT不受此限;
			 * A-管理员  B-业务人员
			 */
			String currUser = OrganizationContextHolder.getUserNo();
			String currUserBranchId = OrganizationContextHolder.getBranchCode();
			if(!"IT".equals(currUser)){
				if(!UserType.B.name().equals(tmAclUser.getUserType()) && currUserBranchId.equals(tmAclUser.getBranchCode())){
					//添加管理员，，添加的管理员所属机构不能与当前登陆者为同一机构
					j.setFail("只能添加下级机构的管理级用户！");
					return j;
				}
			}
			//--modify suny 20171202-----end--
			userService.saveTmAclUser(tmAclUser);
			//添加审计历史
			systemAuditHistoryUtils.saveSystemAudit("登陆名: "+tmAclUser.getUserNo(),"用户管理","SAVE","",tmAclUser.convertToMap().toString());
		}catch(Exception e){
			logger.error("添加下级机构的管理级用户失败 UserType ["+tmAclUser.getUserType()+"] BranchCode ["+tmAclUser.getBranchCode()+"]", e);
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	
	@RequestMapping("/addpage")
	public String addpage(){
		
		Integer userId = getParaToInt("userId");
		if(userId!=null){
			setAttr("user", userService.getTmAclUser(userId));
		}
		
		return "acl/user/user-form.ftl";
	}
	@ResponseBody
	@RequestMapping("/delete")
	public Json delete(int userId){
		Json j = Json.newSuccess();
		
		try{
			userService.deleteTmAclUser(userId);
			//保存审计历史
			TmAclUser tmAclUser = userService.getTmAclUser(userId);
			systemAuditHistoryUtils.saveSystemAudit("登陆名: "+tmAclUser.getUserNo(),"用户管理","DELETE",tmAclUser.convertToMap().toString(),"");

		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		return j;
	}
	
	@ResponseBody
	@RequestMapping("/deletes")
	public Json deletes(){
		Json j = Json.newSuccess();
		
		List<Integer> ids = getList(Integer.class, "ids");
		
		try{
			userService.deleteBatchTmAclUser(ids);
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	
	/**
	 * 重置密码
	 * @param userNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/resetpassword")
	public Json resetpassword(String userNo){
		Json j = Json.newSuccess();
		TmAclUser tmAclUserDb = null;
		try{
			/*
			 * 如果被修改用户所属角色组中有资源属性为需变更授权，则需记录授权审核；
			 * IT操作员不需审核；
			 * modify suny 20171202
			 */
			
			List<String> resourceAuthTypeList = userService.getUserResourceAuthTypeList(userNo);
			if(!"IT".equals(OrganizationContextHolder.getUserNo()) && !CollectionUtils.isEmpty(resourceAuthTypeList) && (resourceAuthTypeList.contains(ResourceAuthType.B.name()) || resourceAuthTypeList.contains(ResourceAuthType.C.name()))){
				//重置密码需授权审核
				
				tmAclUserDb = userService.getUserByUserNo(userNo);
				//判断是否已提交一笔申请待审核
				List<TmAclAuthAuditLog> log = authAuditLogService.getTmAclAuthAuditLogUntreated(tmAclUserDb.getUserId(),null,AuditType.C.name());
				if(!CollectionUtils.isEmpty(log)){
					j.setFail("已存在用户["+userNo+"]待审核的重置密码申请");
					return j;
				}
				TmAclBranch tmAclBranch = branchService.getTmAclBranch(OrganizationContextHolder.getBranchCode());
				if(resourceAuthTypeList.contains(ResourceAuthType.B.name())){
					authAuditLogService.saveTmAclAuthAuditLog(tmAclUserDb, null, null, tmAclBranch, AuditType.C.name(),ResourceAuthType.B.name());
					j.setMsg("该用户密码重置需经上级审核重置，已提交审核申请。");
				}else if(resourceAuthTypeList.contains(ResourceAuthType.C.name())){
					authAuditLogService.saveTmAclAuthAuditLog(tmAclUserDb, null, null, tmAclBranch, AuditType.C.name(),ResourceAuthType.C.name());
					j.setMsg("该用户密码重置需经同机构内审核重置，已提交审核申请。");
				}
				//保存审计历史
				systemAuditHistoryUtils.saveSystemAudit("登陆名: "+tmAclUserDb.getUserNo(),"用户管理","重置密码","","");
			}else {
				userService.resetPassword(userNo);
			}

		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	
	@ResponseBody
	@RequestMapping("/editpassword")
	public Json editpassword(String userNo,String oldPassword,String plainPassword){
		Json j = Json.newSuccess();
		
		try{
			userService.editPassword(userNo, plainPassword,"N");
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		
		return j;
	}
}