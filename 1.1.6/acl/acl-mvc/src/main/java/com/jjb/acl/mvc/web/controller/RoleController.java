package com.jjb.acl.mvc.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jjb.acl.biz.utils.SystemAuditHistoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.acl.biz.service.AuthAuditDetailService;
import com.jjb.acl.biz.service.AuthAuditLogService;
import com.jjb.acl.biz.service.BranchService;
import com.jjb.acl.biz.service.DictService;
import com.jjb.acl.biz.service.ResourceService;
import com.jjb.acl.biz.service.RoleResourceService;
import com.jjb.acl.biz.service.RoleService;
import com.jjb.acl.biz.service.UserRoleService;
import com.jjb.acl.biz.service.UserService;
import com.jjb.acl.facility.enums.auth.AuditType;
import com.jjb.acl.facility.enums.auth.ResourceAuthType;
import com.jjb.acl.facility.model.ResourceTreeFace;
import com.jjb.acl.infrastructure.TmAclBranch;
import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.acl.infrastructure.TmAclResource;
import com.jjb.acl.infrastructure.TmAclRole;
import com.jjb.acl.infrastructure.TmAclRoleResource;
import com.jjb.acl.infrastructure.TmAclUserRole;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.kits.StrKit;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Tree;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * 
 * @author BIG.D.W.K
 *
 */
@Controller
@RequestMapping("/acl/role")
public class RoleController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleResourceService roleResourceService;
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private DictService dictService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BranchService branchService;
	
	@Autowired
	private AuthAuditLogService authAuditLogService;
	
	@Autowired
	private AuthAuditDetailService authAuditDetailService;
	@Autowired
	private CommonExceptionPageUtils commonExceptionPageUtils;
	@Autowired
	private SystemAuditHistoryUtils systemAuditHistoryUtils;
	
	@RequestMapping("/page")
	public String page(){
		return "acl/role/role.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/list")
	public Page<TmAclRole> list(){
		Page<TmAclRole> page = getPage(TmAclRole.class);
		
		/*modify by H.N 20171031:
		 * ①登录者身份是四个小写字母[root]时，才显示[超级管理员/root]信息。否则，列表中不显示[超级管理员/root]这一条记录。
		 * ③不能显示[超级管理员/root]的删除按钮
		 */
		String isNotSuperUser = "";
		try {
			boolean isRoot = this.isRoot();
			if(!isRoot){//如果不是root用户
				isNotSuperUser = "no";
			}
		} catch(Exception e) {//职位未定义或其他错误，则认为是非super
			isNotSuperUser = "no";
		}

		page.getQuery().put("isNotSuperUser", isNotSuperUser);
		page = roleService.getRolePage(page);
		
		return page;
	}
	
	@RequestMapping("/addpage")
	public String addpage(){
		return "acl/role/addRole.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public Json add(TmAclRole tmAclRole){
		Json j = Json.newSuccess();
		String roleName = null;
		try{
			//如果DB中已经存在名称为root的职位则不可以再新建一个root职位 add by H.N
			roleName = StringUtils.trimAllWhitespace(tmAclRole.getRoleName());
			if(null != roleName && roleName.equals("root")){//如果准备新建的职位名称为root
				if(null != roleService.getRootRole()){
					j.setFail("系统已经存在root角色，不允许重复创建。");
					return j;
				}
			}

			roleService.saveTmAclRole(tmAclRole);
			//添加审计历史
			systemAuditHistoryUtils.saveSystemAudit("角色名称: "+tmAclRole.getRoleName(),"角色管理","SAVE","",tmAclRole.convertToMap().toString());
			j.setObj(tmAclRole);
		}catch(Exception e){
			logger.error("创建用户失败 已存在该用户 roleName ["+roleName+"]", e);
			j.setFail(e.getMessage());
		}
		return j;
	}
	
	@RequestMapping("/roleResourcePage")
	public String addResourcePage(){
		//系统类型
		String sysType = getPara("sysType");
		//角色ID
		Integer roleId = getParaToInt("roleId");
		if(roleId==null || roleId.equals("")) {
			return commonExceptionPageUtils.doExcepiton("角色ID为空，请重试!");
		}
		String flag = getPara("flag");
		TmAclRole tmAclRole = new TmAclRole();
		tmAclRole = roleService.getTmAclRole(roleId);		
		setAttr("tmAclRole", tmAclRole);
		
		Tree<TmAclResource> tree = new Tree<TmAclResource>();
		List<TmAclResource> list = new ArrayList<TmAclResource>();
		Map<String,Tree<TmAclResource>> treeList = new HashMap<String, Tree<TmAclResource>>();
			
		List<String> sysTypes = new ArrayList<String>();
		if(StrKit.isBlank(sysType)){	 
			//如果为空，查询业务字典中定义类的系统类型
			List<TmAclDict> dictSysTypeList = dictService.getByType("SYS_TYPE");  //根据系统类型查询系统代码
			for (TmAclDict tmAclDict : dictSysTypeList) {
				sysTypes.add(tmAclDict.getCode());
			}	
		}else{
			sysTypes.add(sysType);		
		}
		
		for (String sysCode : sysTypes) {
			list = resourceService.getListBySysType(sysCode);//:List<TmAclResource>

			//modify by H.N 20171031
			//增加[去除root用户没有权限的节点]的操作
			if(!tmAclRole.getRoleName().equals("root")){//当前编辑的角色不是root（只有root用户可以编辑root角色信息）
				Integer rootRoleID = -1;
				TmAclRole rootRole = this.roleService.getRootRole();
				if(null != rootRole){
					rootRoleID = this.roleService.getRootRole().getRoleId();
				}
				List<TmAclRoleResource> rootRoleResourceList = roleResourceService.getTmAclRoleResourceList(rootRoleID);
				
				int size = list.size();
				for(int i = 0; i < size; i++){
					TmAclResource aclResource = list.get(i);
					String acl_sys_type = aclResource.getSysType();
					String acl_resource_code = aclResource.getResourceCode();
					boolean rootHaveFlg = false;
					for(TmAclRoleResource rootRoleResource : rootRoleResourceList){
						String root_sys_type = rootRoleResource.getSysType();
						String root_resource_code = rootRoleResource.getResourceCode();
						
						if(acl_sys_type.equals(root_sys_type) && acl_resource_code.equals(root_resource_code)){
							rootHaveFlg = true;
							break;
						}
					}
					if(!rootHaveFlg){
						list.remove(i);
						i--;
						size--;
					}
				}
				
				//如果不是ROOT，删除状态为S的资源显示
				size = list.size();
				for(int i = 0; i < size; i++){
					TmAclResource aclResource = list.get(i);
					String isUsed = aclResource.getIsUsed();
					if(isUsed.equals("S")){
						list.remove(i);
						i--;
						size--;
					}
				}
			}

			tree =Tree.listToTree(list, new ResourceTreeFace());
			treeList.put(sysCode, tree);
		}
		
		setAttr("sysType", sysType);
		setAttr("treeList", treeList);
		setAttr("flag", flag);
		

		List<TmAclRoleResource> tmAclRoleResourceList = roleResourceService.getTmAclRoleResourceList(roleId);
		setAttr("tmAclRoleResourceList", tmAclRoleResourceList);
		
		return "acl/role/addRoleResource.ftl";
	}
	
	@RequestMapping("/editpage")
	public String editpage(){
		Integer roleId = getParaToInt("roleId");
		TmAclRole tmAclRole = roleService.getTmAclRole(roleId);
		setAttr("tmAclRole", tmAclRole);
		return "acl/role/roleTab.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/edit")
	public Json edit(TmAclRole tmAclRole){
		Json j = Json.newSuccess();
		try{
			//如果DB中已经存在名称为root的职位则不可以再将另一个非root的角色名称修改为root
			String roleName = StringUtils.trimAllWhitespace(tmAclRole.getRoleName());
			if(null != roleName && roleName.equals("root")){//如果准备修改的职位名称为root
				TmAclRole resultTm = roleService.getRootRole();
				if(null != resultTm){//DB中存在root角色
					int roleId = resultTm.getRoleId();
					if(roleId != tmAclRole.getRoleId()){//当前修改的角色并非是root角色
						j.setFail("系统已经存在root角色，不允许重复创建。");
						return j;	
					}
				}
			}
			TmAclRole ofeTmAclRole = roleService.getTmAclRole(tmAclRole.getRoleId());
			roleService.editTmAclRole(tmAclRole);
			//添加审计历史
			systemAuditHistoryUtils.saveSystemAudit("角色名称: "+tmAclRole.getRoleName(),"角色管理","UPDATE",ofeTmAclRole.convertToMap().toString(),tmAclRole.convertToMap().toString());

		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		return j;
	}
	
	@ResponseBody
	@RequestMapping("/editRoleResources")
	public Json editRoleResources(){
			Integer roleId = getParaToInt("roleId");
		String[] resources = getParas("roleResources");
		String sysType = getPara("sysType");
		Json j = Json.newSuccess();
		TmAclRole curTmAclRole = new TmAclRole();
		curTmAclRole = roleService.getTmAclRole(roleId);	
		try{
			List<String> list = new ArrayList<String>();
			if(resources!=null){
				list = Arrays.asList(resources);
			}
			List<TmAclRoleResource> tmAclRoleResourceList = roleResourceService.getTmAclRoleResourceList(roleId, sysType);//DB内当前时点存在的对应角色对应系统的资源list
			//如果root用户去除了某个功能，则其他用户同样去除此功能
			if(curTmAclRole.getRoleName().equals("root")){//判断当前编辑的角色是否为root
				//判断去除了哪些资源（新增的root资源不用做更改，去除的root资源要同样去除其他角色的对应资源权限）
				for(TmAclRoleResource tm : tmAclRoleResourceList){
					if(!list.contains(tm.getResourceCode())){//去除
						//删除非root用户的对应资源
						roleResourceService.deleteByResource(tm.getResourceCode(), sysType);
					}
				}
			}else {
				/*
				 * 1.非root角色，角色资源变更，判断是否有添加资源，若有，则需判断添加资源是否需要授权审核，需要则添加授权审核记录；若没有需要授权审核的资源，则直接处理
				 * 若是去除资源，则无需判断审核
				 * 2.默认ACL系统资源为管理资源，其他系统为业务资源，角色里面分配资源时，不能同时有ACL系统资源和其他系统资源。
				 * 3.角色成员添加时，ACL资源角色只能添加用户类型为管理员的用户，反之，其他系统资源的角色，只能添加业务人员类型的用户。C-综合管理员都可以添加。
				 * Root角色不受限。
				 * modify suny 20171204
				 */
//				List<String> sysTypeList = roleResourceService.getTmAclRoleResourceSysTypeListByRoleId(roleId);
//				
//				List<Integer> userIds = userRoleService.getRoleUserIdListByRoleId(roleId);
//				String userType = null;
//				if(!CollectionUtils.isEmpty(userIds)){
//					for (int i = 0; i < userIds.size(); i++) {
//						TmAclUser user = userService.getTmAclUser(userIds.get(i));
//						if(!UserType.C.name().equals(user.getUserType())){
//							userType = user.getUserType();
//							break;
//						}
//					}
//
//
//				}
//				if(sysType.equals("ACL")){
//					//该角色中只能包含ACL资源,,该角色成员是管理员
//					if(!CollectionUtils.isEmpty(sysTypeList) && (sysTypeList.size()!=1 || !sysTypeList.contains("ACL"))){
//						j.setFail("角色资源设置中不能同时包含ACL系统管理资源与其他系统资源！");
//						return j;
//					}
//					if(userType != null && !userType.equals(UserType.A.name())){
//						j.setFail("角色中用户类型是业务人员，不能添加ACL系统资源！");
//						return j;
//					}
//				}else {
//					//该角色资源中不能包含ACL资源，，该角色成员是业务人员
//					if(!CollectionUtils.isEmpty(sysTypeList) && sysTypeList.contains("ACL")){
//						j.setFail("角色资源设置中不能同时包含ACL系统管理资源与其他系统资源！");
//						return j;
//					}
//					if(userType != null && userType.equals(UserType.A.name())){
//						j.setFail("角色中用户类型是管理员，不能添加业务系统资源！");
//						return j;
//					}
//				}
				List<String> oldResources = new ArrayList<String>();
				for(TmAclRoleResource roleResource:tmAclRoleResourceList){
					oldResources.add(roleResource.getResourceCode());
				}
				List<TmAclResource> newAddResources = new ArrayList<TmAclResource>();
				String resourceAuthType = ResourceAuthType.A.name();//资源授权类型
				for(String resource:list){
					
					if(!oldResources.contains(resource)){
						TmAclResource aclResource = resourceService.getTmAclResource(sysType, resource);
						newAddResources.add(aclResource);
						//新增资源
						if(ResourceAuthType.B.name().equals(aclResource.getResourceAuthType())){
							resourceAuthType = ResourceAuthType.B.name();
						}else if(ResourceAuthType.C.name().equals(aclResource.getResourceAuthType())
								&& !ResourceAuthType.B.name().equals(resourceAuthType) ){
							resourceAuthType = ResourceAuthType.C.name();
						}
					}
				}
				if(!"IT".equals(OrganizationContextHolder.getUserNo()) && !ResourceAuthType.A.name().equals(resourceAuthType)){
					//资源变更需进行授权
					TmAclBranch tmAclBranch = branchService.getTmAclBranch(OrganizationContextHolder.getBranchCode());
					int logId = authAuditLogService.saveTmAclAuthAuditLog(null, curTmAclRole, newAddResources, tmAclBranch, AuditType.B.name(),resourceAuthType);
					for(String resource:list){
						authAuditDetailService.saveTmAuthAuditDetail(logId,curTmAclRole,sysType,resource);
					}
					j.setMsg("资源变更需"+(resourceAuthType.equals(ResourceAuthType.B.name())?"上级":"同机构内") +"授权审核，授权申请已提交。");
					return j;
				}
			}
			List<TmAclRoleResource> tmARList = roleResourceService.getTmAclRoleResourceList(roleId);
			List<String> odeList = new ArrayList<String>();
			for (TmAclRoleResource resource : tmARList) {
				odeList.add(resource.getResourceCode());
			}
			roleResourceService.saveRoleResource(roleId, sysType, resources);
			//保存审计历史
			systemAuditHistoryUtils.saveSystemAudit("角色名称: "+curTmAclRole.getRoleName(),"角色资源","UPDATE",odeList.toString(),list.toString());


			//modify by H.N 20171031
			//在更新用户对应角色表时，也要更新角色主表的更新时间和更新者
			TmAclRole tmAclRole = new TmAclRole();
			String userNo = OrganizationContextHolder.getUserNo();//登录用户名
			tmAclRole.setRoleId(roleId);
			tmAclRole.setUpdateBy(userNo);
			tmAclRole.setUpdateDate(new Date());
			roleService.updateRole(tmAclRole);
		}catch(Exception e){
			j.setS(false);
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Json delete(int roleId){
		
		
		Json j = Json.newSuccess();
		try{
			TmAclRole role = roleService.getTmAclRole(roleId);
			roleService.deleteTmAclRole(roleId);
			//保存审计历史
			systemAuditHistoryUtils.saveSystemAudit("角色名称: "+role.getRoleName(),"角色管理","DELETE",role.convertToMap().toString(),"");
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		return j;
	}
	
	@ResponseBody
	@RequestMapping("/deletes")
	public Json deletes(){
		Json j = Json.newSuccess();
		
		List<Integer> roleIds = getList(Integer.class, "roleIds");
		
		try{
			roleService.deleteBatchTmAclRole(roleIds);
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	
	/**
	 * 判断当前登录的用户是否是root权限
	 * @return
	 */
	private boolean isRoot(){
		boolean result = false;
		String userNo = OrganizationContextHolder.getUserNo();//登录用户名
		int userID = userService.getUserByUserNo(userNo).getUserId();//登录用户名对应的用户ID
		List<TmAclUserRole> userRoleList = userRoleService.getTmAclUserRole(userID);
		for(TmAclUserRole tmAclUserRole : userRoleList){
			int roleID = tmAclUserRole.getRoleId();//职位ID
			String roleName = roleService.getTmAclRole(roleID).getRoleName();//职位名称
			if(null != roleName && !roleName.equals("")
					&& roleName.equals("root")){
				result = true;
				break;
			}
		}
		return result;
	}
	/**
	 * 
	 * @Description 展示角色拥有资源列表
	 * @return
	 */
	@RequestMapping("/roleHasResourcePage")
	public String roleHasResourcePage(){
		//角色ID
		Integer roleId = getParaToInt("roleId");
		//系统类型
		String sysType = getPara("sysType");
		if(roleId == null){
			return "acl/role/addRoleHasResource.ftl";
		}
		TmAclRole tmAclRole = new TmAclRole();
		tmAclRole = roleService.getTmAclRole(roleId);		
		
		Tree<TmAclResource> tree = new Tree<TmAclResource>();
		List<TmAclResource> list = new ArrayList<TmAclResource>();
		Map<String,Tree<TmAclResource>> treeList = new HashMap<String, Tree<TmAclResource>>();
		List<String> sysTypes = new ArrayList<String>();
		if(StrKit.isBlank(sysType)){	 
			//查询角色拥有资源的系统类型
			List<String> sys = roleResourceService.getTmAclRoleResourceSysTypeListByRoleId(roleId);
			if(!CollectionUtils.isEmpty(sys))
				sysTypes.addAll(sys);
		}else{
			sysTypes.add(sysType);		
		}	
		
		for (String sysCode : sysTypes) {
			
			list = roleResourceService.getTmAclResourceListByRoleIdAndSysCode(roleId, sysCode);//:List<TmAclResource>

			if(!tmAclRole.getRoleName().equals("root")){//当前编辑的角色不是root（只有root用户可以编辑root角色信息）
				
				//如果不是ROOT，删除状态为S的资源显示
				int size = list.size();
				for(int i = 0; i < size; i++){
					TmAclResource aclResource = list.get(i);
					String isUsed = aclResource.getIsUsed();
					if(isUsed.equals("S")){
						list.remove(i);
						i--;
						size--;
					}
				}
			}

			tree =Tree.listToTree(list, new ResourceTreeFace());
			treeList.put(sysCode, tree);
		}
		
		setAttr("treeList", treeList);
		List<TmAclRoleResource> tmAclRoleResourceList = roleResourceService.getTmAclRoleResourceList(roleId);
		setAttr("tmAclRoleResourceList", tmAclRoleResourceList);
		setAttr("sysType", sysType);
		setAttr("roleId", roleId);
		return "acl/role/addRoleHasResource.ftl";
	}
	
}