package com.jjb.ecms.app.controller.param;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jjb.acl.biz.utils.SystemAuditHistoryUtils;
import com.jjb.ecms.biz.util.BizAuditHistoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.acl.facility.enums.bus.Gender;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.ext.sms.SendSmsSupport;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.apply.TmAppMsgSendService;
import com.jjb.ecms.biz.service.param.SmsTemplateService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppMsgSend;
import com.jjb.ecms.infrastructure.TmMessageTemplate;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.MapBuilder;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * @Description: 短信模板管理
 * @author JYData-R&D-Big Star
 * @date 2017年3月15日 下午4:56:21
 * @version V1.0
 */
@Controller
@RequestMapping("/smsTemplate")
public class SmsTemplateController extends BaseController {

	@Autowired
	private CacheContext cacheContext;

	@Autowired
	private SmsTemplateService smsTemplateService;

	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired
	private SendSmsSupport sendSmsSupport;
	@Autowired
	private TmAppMsgSendService tmAppMsgSendService;
	@Autowired
	private BizAuditHistoryUtils bizAuditHistoryUtils;
	@Autowired
	private SystemAuditHistoryUtils systemAuditHistoryUtils;



	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 短信管理页面
	 * @return
	 */
	@RequestMapping("/smsTemplatePage")
	public String personalBlacklistpage(){

		return "/applyManage/smsTemplate/smsTemplateList_V1.ftl";
	}

	/**
	 * 短信发送页面
	 * @return
	 */
	@RequestMapping("/smsTemplateSendPage")
	public String MessageSendpage(){

		return "applyManage/smsTemplate/smsTemplateSend_V1.ftl";
	}

	/**
	 * 短信列表页面,分页查询
	 */
	@ResponseBody
	@RequestMapping("/smsTemplateList")
	public Page<TmMessageTemplate> list(){
		Page<TmMessageTemplate> page = getPage(TmMessageTemplate.class);
		page = smsTemplateService.getPage(page);
		logger.info("查询的记录条数是："+page.getRows().size());
		return page;
	}

	/**
	 * @Author lixing
	 * @Description 删除
	 * @Date 2018/10/13 15:49
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public Json delete(int id){

		Json json = Json.newSuccess();
		TmMessageTemplate tmMessageTemplate = new TmMessageTemplate();
		tmMessageTemplate.setId(id);
		tmMessageTemplate = smsTemplateService.queryByCode(tmMessageTemplate);
		smsTemplateService.deleteTmMessageTemplate(id);
		//添加审计历史
		systemAuditHistoryUtils.saveSystemAudit("信息代码: "+tmMessageTemplate.getTeCode(),"短信模板","DELETE",tmMessageTemplate.convertToMap().toString(),"");
		return json;
	}

	/**
	 * 添加短信模板
	 * @param
	 * @return 添加短信模板
	 */
	@RequestMapping("/smsTemplateAddPage")
	public String addpage(){

		return "/applyManage/smsTemplate/smsTemplateAdd_V1.ftl";
	}

	/**
	 * 添加短信模板
	 * @param tmMessageTemplate
	 * @return json
	 */
	@ResponseBody
	@RequestMapping("/add")
	public Json add(TmMessageTemplate tmMessageTemplate){
		Json json = Json.newSuccess();
		smsTemplateService.saveTmMessageTemplate(tmMessageTemplate);
		//记录审计历史
		systemAuditHistoryUtils.saveSystemAudit("信息代码: "+tmMessageTemplate.getTeCode(),"短信模板","SAVE","",tmMessageTemplate.convertToMap().toString());
		return json;
	}

	/**
	 * 编辑短信模板
	 * @param tmMessageTemplate
	 * @return json
	 */
	@ResponseBody
	@RequestMapping("/edit")
	public Json edit(TmMessageTemplate tmMessageTemplate){
		Json json = Json.newSuccess();
		TmMessageTemplate template = new TmMessageTemplate();
		template.setId(tmMessageTemplate.getId());
		TmMessageTemplate odeTmMessageTemplate = smsTemplateService.queryByCode(template);
		smsTemplateService.updateTmMessageTemplate(tmMessageTemplate);
		//添加审计历史
		systemAuditHistoryUtils.saveSystemAudit("信息代码: "+tmMessageTemplate.getTeCode(),"短信模板","UPDATE",odeTmMessageTemplate.convertToMap().toString(),tmMessageTemplate.convertToMap().toString());
        return json;
	}

    @RequestMapping("/editpage")
	public String editpage(){
		String id  = getPara("id");
		TmMessageTemplate tmMessageTemplate = new TmMessageTemplate();
		tmMessageTemplate.setId(Integer.valueOf(id));
		TmMessageTemplate tem = smsTemplateService.queryTmMessageTemplate(tmMessageTemplate);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(tem.getStartTime()!=null){
			setAttr("startTime", sdf.format(tem.getStartTime()));
		}
		if(tem.getEndTime()!=null){
			setAttr("endTime", sdf.format(tem.getEndTime()));
		}
		setAttr("tmMessageTemplate", tem);
		setEdit();
		return "/applyManage/smsTemplate/smsTemplateAdd_V1.ftl";
	}

	@ResponseBody
	@RequestMapping("/groupSendMessage")
	public Json sendMessage(TmAppMsgSend tmAppMsgSend){
		Json json = Json.newSuccess();
		json.setMsg("无需要发送的该类型短信");
		String smsType=tmAppMsgSend.getMsgType();
		//没有给短信类型时给一个默认短信类型
		if(StringUtils.isEmpty(smsType)){
			json.setMsg("未获取到短信类型");
			return json;
		}
		//记录审计历史
		systemAuditHistoryUtils.saveSystemAudit("短信类型:"+smsType,"短信模板","批量发送","","");
		List<TmAppMsgSend> list= tmAppMsgSendService.getTmAppMsgSendListByMsgType(smsType);
		if(StringUtils.isNotEmpty(list)) {
			for (TmAppMsgSend tmAppMsgSend1 : list) {
				try {
					//判断是否为待发送状态或发送失败的短信，防止重复发送
					if (StringUtils.equals(tmAppMsgSend1.getCondition(), "W") || StringUtils.equals(tmAppMsgSend1.getCondition(), "F")) {
						json = Json.newSuccess();
						String appNo = tmAppMsgSend1.getAppNo();
						String org = tmAppMsgSend1.getOrg();
						TmAppMain tmAppMain = applyQueryService.getTmAppMainByAppNo(appNo);
						if (tmAppMain == null) {
							tmAppMain = new TmAppMain();//预防空指针
						}

						String productCd = tmAppMain.getProductCd();//获取卡产品代码
						String description = "";
						TmProduct product = cacheContext.getProduct(productCd);
						if (product != null) {
							description = product.getProductDesc();
						} else {
							logger.info("未获取到卡产品代码[" + productCd + "]对应的卡产品！");
						}
						Map<String, Object> params = new MapBuilder<String, Object>().add("productCd", productCd).add("description", description).build();
						String name = tmAppMain.getName();
						String idNo = tmAppMain.getIdNo();
						String cellPhone = tmAppMain.getCellphone();
						Gender gender = AppCommonUtil.getGender(appNo, null, idNo);
						String msgContent=tmAppMsgSend1.getMsgContent();

						params.put(AppConstant.name, name);
						params.put(AppConstant.gender, gender);
						params.put(AppConstant.cellPhone, cellPhone);
						params.put(AppConstant.sms_type, smsType);
						params.put(AppConstant.app_no, appNo);
						params.put(AppConstant.org, org);
						params.put(AppConstant.msgContent,msgContent);

						sendSmsSupport.sendMessage(params, false,tmAppMsgSend1);

						logger.info("申请编号[{}]，申请类型[{}]", tmAppMain.getAppNo(), tmAppMain.getAppType());
					}
				} catch (Exception e) {
					logger.error("短信发送出现异常，发送失败,", e);
					tmAppMsgSend1.setCondition("F");
					if (StringUtils.isBlank(tmAppMsgSend1.getMsgSendTimes())) {
						tmAppMsgSend1.setMsgSendTimes("1");
					} else {
						int t = Integer.parseInt(tmAppMsgSend1.getMsgSendTimes()) + 1;
						String s = String.valueOf(t);
						tmAppMsgSend1.setMsgSendTimes(s);
					}
					tmAppMsgSend1.setRemark("短信发送出现异常");
					tmAppMsgSendService.updateTmAppMsgSend(tmAppMsgSend1);
				}
			}
		}
		return json;
	}
	@ResponseBody
	@RequestMapping("/sendMessageOne")
	public Json sendMessageOne(TmAppMsgSend tmAppMsgSend){
		Json json = Json.newSuccess();
		TmAppMsgSend tmAppMsgSn = new TmAppMsgSend();
		if (StringUtils.isNotEmpty(tmAppMsgSend.getAppNo())) {
			tmAppMsgSn = tmAppMsgSendService.getTmAppMsgSendByAppNo(tmAppMsgSend.getAppNo());
			tmAppMsgSn.setUpdateTime(new Date());
		}
		try {
			if (StringUtils.equals(tmAppMsgSn.getCondition(), "W") || StringUtils.equals(tmAppMsgSn.getCondition(), "F")) {
				String smsType = tmAppMsgSn.getMsgType();
				String appNo = tmAppMsgSn.getAppNo();
				String org = tmAppMsgSn.getOrg();
				//记录审计历史
				systemAuditHistoryUtils.saveSystemAudit("appNo:"+appNo,"短信模板","发送短信","","");

				TmAppMain tmAppMain = applyQueryService.getTmAppMainByAppNo(appNo);
				if (tmAppMain == null) {
					tmAppMain = new TmAppMain();//预防空指针
				}

				String productCd = tmAppMain.getProductCd();//获取卡产品代码
				String description = "";
				TmProduct product = cacheContext.getProduct(productCd);
				if (product != null) {
					description = product.getProductDesc();
				} else {
					logger.info("未获取到卡产品代码[" + productCd + "]对应的卡产品！");
				}
				Map<String, Object> params = new MapBuilder<String, Object>().add("productCd", productCd).add("description", description).build();
				String name = tmAppMain.getName();
				String idNo = tmAppMain.getIdNo();
				String cellPhone = tmAppMain.getCellphone();
				Gender gender = AppCommonUtil.getGender(appNo, null, idNo);
				String msgContent=tmAppMsgSn.getMsgContent();

				params.put(AppConstant.name, name);
				params.put(AppConstant.gender, gender);
				params.put(AppConstant.cellPhone, cellPhone);
				params.put(AppConstant.sms_type, smsType);
				params.put(AppConstant.app_no, appNo);
				params.put(AppConstant.org, org);
				params.put(AppConstant.msgContent,msgContent);

				sendSmsSupport.sendMessage(params, false,tmAppMsgSn);

				logger.info("申请编号[{}]，申请类型[{}]", tmAppMain.getAppNo(), tmAppMain.getAppType());
			}
		}catch (Exception e){
			logger.error("短信发送出现异常，发送失败,", e);
			tmAppMsgSn.setCondition("F");
			if (StringUtils.isBlank(tmAppMsgSn.getMsgSendTimes())) {
				tmAppMsgSn.setMsgSendTimes("1");
			} else {
				int t = Integer.parseInt(tmAppMsgSn.getMsgSendTimes()) + 1;
				String s = String.valueOf(t);
				tmAppMsgSn.setMsgSendTimes(s);
			}
			tmAppMsgSn.setRemark("短信发送出现异常");
			tmAppMsgSendService.updateTmAppMsgSend(tmAppMsgSn);
		}
		return json;
	}


	@ResponseBody
	@RequestMapping("/settCondition")
	public Json settCondition(TmAppMsgSend tmAppMsgSend){
		Json json = Json.newSuccess();
		TmAppMsgSend tmAppMsgSn = new TmAppMsgSend();
		//记录审计历史
		if (StringUtils.equals(tmAppMsgSend.getCondition(), "N")) {
			systemAuditHistoryUtils.saveSystemAudit("appNo:"+tmAppMsgSend.getAppNo(),"短信模板","标记为无效","","");

		} else if (StringUtils.equals(tmAppMsgSend.getCondition(), "S")) {
			systemAuditHistoryUtils.saveSystemAudit("appNo:"+tmAppMsgSend.getAppNo(),"短信模板","标记为已发送","","");

		} else if (StringUtils.equals(tmAppMsgSend.getCondition(), "W")) {
			systemAuditHistoryUtils.saveSystemAudit("appNo:"+tmAppMsgSend.getAppNo(),"短信模板","标记为待发送","","");

		} else if (StringUtils.equals(tmAppMsgSend.getCondition(), "F")) {
			systemAuditHistoryUtils.saveSystemAudit("appNo:"+tmAppMsgSend.getAppNo(),"短信模板","标记为发送失败","","");
		}
		if (StringUtils.isNotEmpty(tmAppMsgSend.getAppNo())) {
			tmAppMsgSn = tmAppMsgSendService.getTmAppMsgSendByAppNo(tmAppMsgSend.getAppNo());
			tmAppMsgSn.setCondition(tmAppMsgSend.getCondition());
			tmAppMsgSn.setUpdateTime(new Date());
			tmAppMsgSendService.updateTmAppMsgSend(tmAppMsgSn);
		}
		return json;
	}

	@ResponseBody
	@RequestMapping("/selectMessageOne")
	public Page<TmAppMsgSend> selectMessageOne(){
		Page<TmAppMsgSend> page = getPage(TmAppMsgSend.class);
		page = tmAppMsgSendService.getPage(page);
		return page;
	}


    private String getString(TmMessageTemplate tmMessageTemplate, TmMessageTemplate odeTmMessageTemplate) {
        String tmMessage = "";
        if (StringUtils.equals(odeTmMessageTemplate.getTeCode(),tmMessageTemplate.getTeCode())) {
        } else {
            tmMessage += "-信息代码:" + odeTmMessageTemplate.getTeCode() + "-->" + tmMessageTemplate.getTeCode();
        }
        if (StringUtils.equals(odeTmMessageTemplate.getTeDesc(),tmMessageTemplate.getTeDesc())) {
        } else {
            tmMessage += "-信息描述:" + odeTmMessageTemplate.getTeDesc() + "-->" + tmMessageTemplate.getTeDesc();
        }
        if (StringUtils.equals(odeTmMessageTemplate.getSendingMethod(),tmMessageTemplate.getSendingMethod())) {
        } else {
            tmMessage += "-发送方法:" + odeTmMessageTemplate.getSendingMethod() + "-->" + tmMessageTemplate.getSendingMethod();
        }
        if (StringUtils.equals(odeTmMessageTemplate.getMsgcategory(),tmMessageTemplate.getMsgcategory())) {
        } else {
            tmMessage += "-信息分类:" + odeTmMessageTemplate.getMsgcategory() + "-->" + tmMessageTemplate.getMsgcategory();
        }
        if (StringUtils.equals(odeTmMessageTemplate.getStartTime(),tmMessageTemplate.getStartTime().toString())) {
        } else {
            tmMessage += "-发送起始时间:" + odeTmMessageTemplate.getStartTime() + "-->" + tmMessageTemplate.getStartTime();
        }
        if (StringUtils.equals(odeTmMessageTemplate.getEndTime(),tmMessageTemplate.getEndTime().toString())) {
        } else {
            tmMessage += "-发送结束时间:" + odeTmMessageTemplate.getEndTime() + "-->" + tmMessageTemplate.getEndTime();
        }
        if (StringUtils.equals(odeTmMessageTemplate.getContentTemplate(),tmMessageTemplate.getContentTemplate())) {
        } else {
            tmMessage += "-内容模板:" + odeTmMessageTemplate.getContentTemplate() + "-->" + tmMessageTemplate.getContentTemplate();
        }
        return tmMessage;
    }
}
