package com.jjb.acl.mvc.web.controller;import java.util.ArrayList;
import java.util.Date;
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
import com.jjb.acl.biz.service.RoleResourceService;
import com.jjb.acl.biz.service.RoleService;
import com.jjb.acl.biz.service.UserRoleService;
import com.jjb.acl.biz.service.UserService;
import com.jjb.acl.facility.enums.auth.AuditType;
import com.jjb.acl.facility.enums.auth.ResourceAuthType;
import com.jjb.acl.facility.enums.auth.UserType;
import com.jjb.acl.infrastructure.TmAclAuthAuditLog;
import com.jjb.acl.infrastructure.TmAclBranch;
import com.jjb.acl.infrastructure.TmAclRole;
import com.jjb.acl.infrastructure.TmAclUser;
import com.jjb.acl.infrastructure.TmAclUserRole;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.web.controller.BaseController;

/** 
 * @author  ltm
 * @date 创建时间：2016年8月31日 下午5:30:59 
 * @version    
 */

@Controller
@RequestMapping("/acl/userRole")
public class UserRoleController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleResourceService roleResourceService;
	@Autowired
	private AuthAuditLogService authAuditLogService;
	@Autowired
	private BranchService branchService;
	@Autowired
	private CommonExceptionPageUtils commonExceptionPageUtils;
	@Autowired
	private SystemAuditHistoryUtils systemAuditHistoryUtils;
	
	@RequestMapping("/page")
	public String page(){
		Integer roleId = getParaToInt("roleId");
		if(roleId==null || roleId.equals("")) {
			return commonExceptionPageUtils.doExcepiton("角色ID为空，请重试!");
		}
		TmAclRole tmAclRole = roleService.getTmAclRole(roleId);
		setAttr("tmAclRole", tmAclRole);
		return "acl/role/userRole.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/list")
	public Page<TmAclUser> list(){
		Integer roleId = getParaToInt("roleId");
		List<TmAclUser> list1= userService.selectByRoleId(roleId);
		Page<TmAclUser> page = new Page<TmAclUser>();
		page.setRows(list1);
		 
		return page;
	}
	/**
	 * 很据userId查询角色
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/lists")
	public Page<TmAclRole> lists(){
		Integer userId = getParaToInt("userId");
		List<TmAclRole> list1 = roleService.getTmAclRoleName(userId);
		Page<TmAclRole> page = new Page<TmAclRole>();
		page.setRows(list1);
		 
		return page;
	}
	
	
	@RequestMapping("/userList")
	public String userList(){
		
		Integer roleId = getParaToInt("roleId");
		setAttr("roleId", roleId);
		return "acl/role/userList.ftl";
	}
	@RequestMapping("/roleListPage")
	public String roleListPage(){
		
		Integer userId = getParaToInt("userId");
		setAttr("userId1", userId);
		return "acl/user/roleList.ftl";
	}
	/**
	 * 角色添加用户
	 * @param tmAclUserRole
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/add")
	public Json add(TmAclUserRole tmAclUserRole){
		
		String ids = getPara("ids");
		List<String> userIds  = new ArrayList<String>();
		String array[] = ids.split(",");
		for (int i = 0; i < array.length; i++) {
			userIds.add(array[i]);
		}
		Integer roleId = getParaToInt("roleId");
		TmAclRole role = roleService.getTmAclRole(roleId);
			Json j = Json.newSuccess();
			
			try{
				/*
				 * 1.若是新增角色成员，判断该角色中是否有需要授权审核的资源，若有，则不能直接添加，需新增授权审核记录，审核通过才能将新增成员添加到角色组中；若没有需审核资源，则直接处理；
				 * 若没有新增的成员，则无需审核
				 * 2.角色成员添加时，ACL资源角色只能添加用户类型为管理员的用户，反之，其他系统资源的角色，只能添加业务人员类型的用户。C-综合管理员都可以添加。
				 * modify suny 20171204
				 */
				List<String> resourceAuthTypeList = roleService.getRoleResourceAuthTypeListByRoleId(roleId);
				if(CollectionUtils.isEmpty(resourceAuthTypeList)){
					j.setFail("请先给角色分配资源后再添加成员！");
					return j;
				}
				List<String> sysTypeList = roleResourceService.getTmAclRoleResourceSysTypeListByRoleId(roleId);
				String userType = UserType.B.name();
				if(!CollectionUtils.isEmpty(sysTypeList) && sysTypeList.contains("ACL")){
					userType = UserType.A.name();//管理员
				}
				List<Integer> oldUsersIdList = userRoleService.getRoleUserIdListByRoleId(roleId);
				
				List<TmAclUser> addUsers = new ArrayList<TmAclUser>();
				for(String userId:userIds){
					if(!oldUsersIdList.contains(Integer.valueOf(userId))){
						//新增成员
						TmAclUser user = userService.getTmAclUser(Integer.parseInt(userId));
						if(!user.getUserType().equals(userType) && !user.getUserType().equals(UserType.C.name())){
							j.setFail("角色成员只能添加"+(userType.equals(UserType.A.name())?"管理级人员":"业务级人员") +"。");
							return j;
						}
						addUsers.add(user);
					}
				}
				if(!"IT".equals(OrganizationContextHolder.getUserNo()) && !CollectionUtils.isEmpty(addUsers)){
					boolean needAuth = false;
					String resourceAuthType = ResourceAuthType.A.name();
					if(resourceAuthTypeList.contains(ResourceAuthType.B.name())){
						needAuth = true;
						resourceAuthType = ResourceAuthType.B.name();
					}else if(resourceAuthTypeList.contains(ResourceAuthType.C.name())){
						needAuth = true;
						resourceAuthType = ResourceAuthType.C.name();
					}
					
					if(needAuth && addUsers.size() >0){
						TmAclBranch tmAclBranch = branchService.getTmAclBranch(OrganizationContextHolder.getBranchCode());
						StringBuffer userNo = new StringBuffer();
						for(TmAclUser user:addUsers){
							//判断是否已提交一笔申请待审核
							List<TmAclAuthAuditLog> log = authAuditLogService.getTmAclAuthAuditLogUntreated(user.getUserId(),role.getRoleId(),AuditType.A.name());
							if(!CollectionUtils.isEmpty(log)){
								userNo.append(user.getUserNo()+",");
								continue;
							}
							authAuditLogService.saveTmAclAuthAuditLog(user, role, null, tmAclBranch, AuditType.A.name(), resourceAuthType);
						}
						j.setMsg("角色成员添加等待"+(resourceAuthType.equals(ResourceAuthType.B.name())?"上级":"同机构内") +"授权审核。"+(userNo.length()>0?"用户["+userNo.toString()+"]已存在待审核的申请。":""));
						return j;
					}
					
				}
				
				userRoleService.saveTmAclRoleUsers(roleId, userIds);
				//保存审计历史
				systemAuditHistoryUtils.saveSystemAudit("角色名称: "+role.getRoleName(),"角色成员添加","SAVE","",userIds.toString());


				//modify by H.N 20171031
				//在更新用户对应角色表时，也要更新角色主表的更新时间和更新者
				TmAclRole tmAclRole = new TmAclRole();
				String userNo = OrganizationContextHolder.getUserNo();//登录用户名
				tmAclRole.setRoleId(roleId);
				tmAclRole.setUpdateBy(userNo);
				tmAclRole.setUpdateDate(new Date());
				roleService.updateRole(tmAclRole);
			}catch(Exception e){
				logger.error("角色添加用户失败 userIds ["+userIds+"]  roleId ["+roleId+"]", e);
				j.setFail(e.getMessage());
			}
			
			return j;
		}

	@ResponseBody
	@RequestMapping("/delete")
	public Json delete(int userId){
		Integer roleId = getParaToInt("roleId");
		Json j = Json.newSuccess();
		try{
			TmAclRole role = roleService.getTmAclRole(roleId);
			userRoleService.deleteTmAclUserRoleBykey(userId,roleId);
			//保存审计历史
			systemAuditHistoryUtils.saveSystemAudit("角色名称: "+role.getRoleName(),"角色成员添加","DELETE","["+userId+"]","");
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	/**
	 * 用户添加角色
	 * @param tmAclUserRole
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addRole")
	public Json addRole(TmAclUserRole tmAclUserRole){
		
		String ids = getPara("ids");
		List<String> roleIds  = new ArrayList<String>();
		String array[] = ids.split(",");
		for (int i = 0; i < array.length; i++) {
			roleIds.add(array[i]);
		}
		Integer userId = getParaToInt("userId");
		
			Json j = Json.newSuccess();
			
			try{
				
				/*
				 * 1.若是给用户添加角色，判断该角色中是否有需要授权审核的资源，若有，则不能直接添加，需新增授权审核记录，审核通过才能将新增角色添加到用户；若没有需要审核的资源，则直接处理；
				 * 若没有新增的角色，则无需审核
				 * 2.该用户是管理员，只能添加ACL管理资源的角色；反之，业务人员，不能添加ACL资源的角色。C-综合管理员都可以添加。
				 * modify suny 20171205
				 */
				TmAclUser curUser = userService.getTmAclUser(userId);
				String userType = curUser.getUserType();//A-管理员  B-业务人员  C-综合管理员
				List<Integer> oldRoleIdList = userRoleService.getUserRoleIdListByUserId(userId);
				List<TmAclRole> addRoles = new ArrayList<TmAclRole>();
				for(String roleId:roleIds){
					if(!oldRoleIdList.contains(roleId)){
						//新增角色
						TmAclRole role = roleService.getTmAclRole(Integer.parseInt(roleId));
						List<String> sysTypeList = roleResourceService.getTmAclRoleResourceSysTypeListByRoleId(Integer.parseInt(roleId));
						if(CollectionUtils.isEmpty(sysTypeList)){
							j.setFail("角色-"+role.getRoleName()+"，没有分配资源！");
							return j;
						}else if(sysTypeList.contains("ACL") && !UserType.A.name().equals(userType) && !UserType.C.name().equals(userType)){
							//ACL资源，该用户必须是管理员
							j.setFail("角色-"+role.getRoleName()+"，只能添加管理级人员！");
							return j;
						}else if(UserType.A.name().equals(userType) && !sysTypeList.contains("ACL")){
							//管理员，资源必须是ACL
							j.setFail("角色-"+role.getRoleName()+"，只能添加业务级人员！");
							return j;
						}
						addRoles.add(role);
					}
				}
				if(!"IT".equals(OrganizationContextHolder.getUserNo()) && !CollectionUtils.isEmpty(addRoles)){
					//判断新增角色是否需授权
					List<String> notNeedAuthRoles = new ArrayList<String>();
					StringBuffer res = new StringBuffer();
					for(TmAclRole role:addRoles){
						List<String> resourceAuthTypeList = roleService.getRoleResourceAuthTypeListByRoleId(role.getRoleId());
						TmAclBranch tmAclBranch = branchService.getTmAclBranch(OrganizationContextHolder.getBranchCode());
						
						String resourceAuthType = ResourceAuthType.A.name();
						if(!CollectionUtils.isEmpty(resourceAuthTypeList)){
							if(resourceAuthTypeList.contains(ResourceAuthType.B.name())){
								resourceAuthType = ResourceAuthType.B.name();
							}else if(resourceAuthTypeList.contains(ResourceAuthType.C.name())){
								resourceAuthType = ResourceAuthType.C.name();
							}
						}
						if(resourceAuthType.equals(ResourceAuthType.A.name())){
							//无需授权审核
							notNeedAuthRoles.add(role.getRoleId().toString());
						}else {
							//判断是否已提交一笔申请待审核
							List<TmAclAuthAuditLog> log = authAuditLogService.getTmAclAuthAuditLogUntreated(curUser.getUserId(),role.getRoleId(),AuditType.A.name());
							if(!CollectionUtils.isEmpty(log)){
								res.append("["+role.getRoleName()+"]已存在授权审核申请，");
							}else {
								authAuditLogService.saveTmAclAuthAuditLog(curUser, role, null, tmAclBranch, AuditType.A.name(), resourceAuthType);
								res.append("["+role.getRoleName()+"]权限需"+(resourceAuthType.equals(ResourceAuthType.B.name())?"上级":"同机构内")+"授权审核，");
							}
							
						}
					}
					if(!CollectionUtils.isEmpty(notNeedAuthRoles))
						userRoleService.saveTmAclRole(userId, notNeedAuthRoles);
					if(res.length() > 0)
						j.setMsg(res.toString());
				}else {
					userRoleService.saveTmAclRole(userId, roleIds);
				}
				//保存审计历史
				TmAclUser userByUserNo = userService.getTmAclUser(tmAclUserRole.getUserId());
				systemAuditHistoryUtils.saveSystemAudit("登陆名: "+userByUserNo.getUserNo(),"用户角色添加","SAVE","",userByUserNo.convertToMap().toString());
			}catch(Exception e){
				logger.error("用户添加角色失败  userId ["+userId+"]  roleIds ["+roleIds+"]", e);
				j.setFail(e.getMessage());
			}
			
			return j;
		}
	@ResponseBody
	@RequestMapping("/deleteRole")
	public Json deleteRole(int roleId){
		Integer userId = getParaToInt("userId");
		Json j = Json.newSuccess();
		try{
			userRoleService.deleteTmAclUserRole(roleId,userId);
			//保存审计历史
			TmAclUser userByUserNo = userService.getTmAclUser(userId);
			systemAuditHistoryUtils.saveSystemAudit("登陆名: "+userByUserNo.getUserNo(),"用户角色添加","DELETE",userByUserNo.convertToMap().toString(),"");
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	
	@RequestMapping("/roleHasUserlistPage")
	public String roleHasUserlistPage(){
		Integer roleId = getParaToInt("roleId");
		setAttr("roleId", roleId);
		return "acl/role/roleHasUserList.ftl";
	}
	
}
