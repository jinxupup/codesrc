package com.jjb.ecms.app.controller.manage;

import com.jjb.acl.biz.utils.SystemAuditHistoryUtils;
import com.jjb.acl.infrastructure.TmAclUser;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.apply.TmAppUserRelationDao;
import com.jjb.ecms.biz.service.apply.TmAppMsgSendService;
import com.jjb.ecms.biz.service.common.CommonService;
import com.jjb.ecms.biz.util.BizAuditHistoryUtils;
import com.jjb.ecms.infrastructure.TmAppUserRelation;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @Description: 业务人员管理
 * @author 高树冲
 * @date 2017年3月15日 下午4:56:21
 * @version V1.0
 */
@Controller
@RequestMapping("/manageRelation")
public class ManageRelationController extends BaseController {
    @Autowired
    private CacheContext cacheContext;

    @Autowired
    private TmAppMsgSendService tmAppMsgSendService;
    @Autowired
    private TmAppUserRelationDao tmAppUserRelationDao;
    @Autowired
    private CommonService commonService;
    @Autowired
    private SystemAuditHistoryUtils systemAuditHistoryUtils;

    private Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 业务人员管理页面
     * @return
     */
    @RequestMapping("/userManagePage")
    public String userManagePage(){

        return "applyManage/userManage/userManage.ftl";
    }

    /**
     * 业务人员管理页面
     * @return
     */
    @ResponseBody
    @RequestMapping("/userList")
    public Page<TmAppUserRelation> userList(){
        //String currUserNo = OrganizationContextHolder.getUserNo();
        //TmAppUserRelation currUser=tmAppMsgSendService.getTmAppUserRelationByUserNo(currUserNo);
        Page<TmAppUserRelation> page = getPage(TmAppUserRelation.class);
		/*//如果是普通业务员，只能看到自己的用户信息
		if(currUser==null){
			return page;
		}else if(StringUtils.equals(currUser.getUserType(),"B")) {
			page.getQuery().put("userNo", currUserNo);
		}else if(StringUtils.equals(currUser.getUserType(),"H")){
			//如果是组长，判断组别是否为空，不为空则查看组员信息否则只看到自己信息
			if(StringUtils.isNotBlank(currUser.getGroups())) {
				page.getQuery().put("groups", currUser.getGroups());
			}else{
				page.getQuery().put("userNo", currUserNo);
			}
			//如果用户类型为空则只看到自己的信息
		}else if(StringUtils.isBlank(currUser.getUserType())){
			page.getQuery().put("userNo", currUserNo);
		}*/
        page = tmAppMsgSendService.getUserPage(page);
        return page;
    }

    @RequestMapping("/editPage")
    public String editPage(String userNo){
        if(StringUtils.isNotBlank(userNo)) {
            TmAppUserRelation tmAppUserRelation = tmAppMsgSendService.getTmAppUserRelationByUserNo(userNo);
            setAttr("user", tmAppUserRelation);
            //setEdit();
        }
        return "/applyManage/userManage/userEdit.ftl";
    }

    /**
     * 业务人员信息页面
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(){
        /*if(StringUtils.isNotBlank(userNo)) {
            TmAppUserRelation tmAppUserRelation = tmAppMsgSendService.getTmAppUserRelationByUserNo(userNo);
            setAttr("user", tmAppUserRelation);
            setEdit();
        }*/
        return "/applyManage/userManage/userAdd.ftl";
    }
    /**
     * 业务人员增加页面
     * @return
     */
    @ResponseBody
    @RequestMapping("/addUser")
    public Json addUser(TmAppUserRelation tmAppUserRelation){
        Json j = Json.newSuccess();
        //保存审计历史
        systemAuditHistoryUtils.saveSystemAudit("业务员工号: "+tmAppUserRelation.getUserNo(),"业务人员管理","SAVE","",tmAppUserRelation.convertToMap().toString());
        try{
            /*
             * A-管理员  B-业务人员
             */
            tmAppUserRelation.setOrg(OrganizationContextHolder.getOrg());
            String currUserNo = OrganizationContextHolder.getUserNo();
            // TmAppUserRelation currUser=tmAppMsgSendService.getTmAppUserRelationByUserNo(currUserNo);
           /* if(currUser!=null && StringUtils.equals(currUser.getUserType(),"B")){
                j.setMsg("登陆用户无添加用户权限");
                return j;
            }*/
            tmAppUserRelation.setCreateUser(currUserNo);
            /*if(!"IT".equals(currUserNo)){
                if(!UserType.B.name().equals(tmAppUserRelation.getUserType()) && !StringUtils.equals(tmAppUserRelation.getHighterUserNo(),currUserNo) && !StringUtils.equals(currUser.getUserType(),"A") ){
                    //添加组长，只能由管理员或该用户上级业务员添加
                    j.setFail("只能由上级用户或管理员添加组长！");
                    return j;
                }
            }*/
            if(StringUtils.isNotBlank(tmAppUserRelation.getHighterUserNo())){
                TmAclUser tmAclUser=commonService.getUserByUserNo(tmAppUserRelation.getHighterUserNo());
                tmAppUserRelation.setHighterUser(tmAclUser.getUserName());
            }
            if(StringUtils.isNotBlank(tmAppUserRelation.getUserNo())) {
                TmAclUser tmAclUser=commonService.getUserByUserNo(tmAppUserRelation.getUserNo());
                tmAppUserRelation.setName(tmAclUser.getUserName());
                tmAppUserRelationDao.save(tmAppUserRelation);
                cacheContext.refresh();
            }
        }catch(Exception e){
            logger.error("添加业务人员出现异常");
            j.setFail(e.getMessage());
        }

        return j;
    }

    @ResponseBody
    @RequestMapping("/editUser")
    public Json editUser(TmAppUserRelation tmAppUserRelation){
        Json j = Json.newSuccess();
        //保存审计历史
        TmAppUserRelation odeTmAppUserRelation = tmAppUserRelationDao.getTmAppUserRelationByUserNo(tmAppUserRelation.getUserNo());
        systemAuditHistoryUtils.saveSystemAudit("业务员工号: "+tmAppUserRelation.getUserNo(),"业务人员管理","UPDATE",odeTmAppUserRelation.convertToMap().toString(),tmAppUserRelation.convertToMap().toString());
        try{
            //后期完善：dao方法要放在service层
            if(StringUtils.isNotBlank(tmAppUserRelation.getUserNo())) {
                if(StringUtils.isNotBlank(tmAppUserRelation.getHighterUserNo())){
                    TmAclUser tmAclUser=commonService.getUserByUserNo(tmAppUserRelation.getHighterUserNo());
                    tmAppUserRelation.setHighterUser(tmAclUser.getUserName());
                }
                tmAppUserRelationDao.update(tmAppUserRelation);
                cacheContext.refresh();
            }
        }catch(Exception e){
            logger.error("编辑业务人员提交出现异常");
            j.setFail(e.getMessage());
        }

        return j;
    }

    @ResponseBody
    @RequestMapping("/deleteUser")
    public Json deleteUser(TmAppUserRelation tmAppUserRelation){

        Json j = Json.newSuccess();
        //保存审计历史
        systemAuditHistoryUtils.saveSystemAudit("业务员工号: "+tmAppUserRelation.getUserNo(),"业务员信息管理","DELETE",tmAppUserRelation.convertToMap().toString(),"");
        try{
            //后期完善：dao方法要放在service层
            if(StringUtils.isNotBlank(tmAppUserRelation.getUserNo())) {
                TmAppUserRelation deleteUser=tmAppMsgSendService.getTmAppUserRelationByUserNo(tmAppUserRelation.getUserNo());
                tmAppUserRelationDao.deleteByKey(deleteUser);
                cacheContext.refresh();
            }
        }catch(Exception e){
            logger.error("删除业务人员出现异常");
            j.setFail(e.getMessage());
        }

        return j;
    }

    @ResponseBody
    @RequestMapping("/changeStatus")
    public Json changeStatus(TmAppUserRelation tmAppUserRelation){
        Json j = Json.newSuccess();
        try{
            if(StringUtils.isNotBlank(tmAppUserRelation.getUserNo())) {
                TmAppUserRelation changeUser=tmAppMsgSendService.getTmAppUserRelationByUserNo(tmAppUserRelation.getUserNo());
                if(changeUser.getCondition()==null || changeUser.getCondition().equals("S")){
                    changeUser.setCondition("A");
                    systemAuditHistoryUtils.saveSystemAudit("业务员工号: "+tmAppUserRelation.getUserNo(),"业务员信息管理","正常作业","","");
                }else{
                    changeUser.setCondition("S");
                    systemAuditHistoryUtils.saveSystemAudit("业务员工号: "+tmAppUserRelation.getUserNo(),"业务员信息管理","暂停作业","","");

                }
                tmAppUserRelationDao.update(changeUser);
                cacheContext.refresh();
            }

        }catch(Exception e){
            logger.error("改变业务人员状态出现异常");
            j.setFail(e.getMessage());
        }
        return j;
    }
}
