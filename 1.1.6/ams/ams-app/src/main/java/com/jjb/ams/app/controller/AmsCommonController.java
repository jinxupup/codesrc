package com.jjb.ams.app.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.ams.app.controller.utils.AmsExceptionPageUtils;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.ext.cmp.CmpSystemSupport;
import com.jjb.ecms.biz.ext.ecss.BankImagesSysInfo;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.commonDialog.AlreadyCardService;
import com.jjb.ecms.biz.service.commonDialog.ApplyHistoryService;
import com.jjb.ecms.biz.service.commonDialog.ApplyUpdateHistoryService;
import com.jjb.ecms.biz.service.node.ApplyInfoPreDtoService;
import com.jjb.ecms.biz.service.query.ApplyTaskTransferListService;
import com.jjb.ecms.biz.util.BizAuditHistoryUtils;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.facility.dto.AlreadyCardsCardInfoDto;
import com.jjb.ecms.facility.dto.ApplyInfoPreDto;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppModifyHis;
import com.jjb.ecms.infrastructure.TmBizAudit;
import com.jjb.ecms.infrastructure.TmTaskTransfer;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * @description: 弹出框的通用controller
 * @author -BigZ.Y
 * @date 2016年9月6日 下午7:04:33
 */
@Controller
@RequestMapping("/ams_commonDialog")
public class AmsCommonController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired
	private AlreadyCardService alreadyCardService;
	@Autowired
	private ApplyHistoryService applyHistoryService;
	@Autowired
	private ApplyTaskTransferListService applyTaskTransferListService;
	@Autowired
	private ApplyUpdateHistoryService applyUpdateHistoryService;
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private ApplyInfoPreDtoService applyInfoPreDtoService;
	@Autowired
	private BankImagesSysInfo bankImagesSysInfo;
	@Autowired
	private AmsExceptionPageUtils exceptionPageUtils;
	@Autowired
	private CmpSystemSupport cmpSystemSupport;//内容管理平台支持类
	@Autowired
	private BizAuditHistoryUtils bizAuditHistoryUtils;



	/**
	 * 打开审计历史页面
	 *
	 * @param appNo
	 * @return
	 */
	@RequestMapping("/openAudit")
	public String openAudit(String appNo) {
		try {
			if (StringUtils.isNotBlank(appNo)) {
				List<TmBizAudit> auditHistoryList = bizAuditHistoryUtils.findAuditHistory(appNo);
				setAttr("auditHistoryList", auditHistoryList);
				return "/amsTask/applyCC/commonDialog/amsAuditHistoryPage.ftl";
			} else {
				throw new ProcessException("无效的申请件编号，请刷新后重试!");
			}
		} catch (Exception e) {
			logger.error("打开审计历史页面异常", e);
			return exceptionPageUtils.doExcepiton("打开审计历史页面"+e.getMessage(),appNo);
		}
	}

	/*
	 * 跳转已有卡窗口
	 */
	@RequestMapping("/alreadyCardLayout")
	public String alreadyCardLayout() {

		String appNo = getPara("appNo");
		List<AlreadyCardsCardInfoDto> alreadyCardsCardInfoDtos = alreadyCardService.getAlreadyCardsCardInfoDtos(appNo);
		if(CollectionUtils.sizeGtZero(alreadyCardsCardInfoDtos)){
			setAttr("applyName", alreadyCardsCardInfoDtos.get(0).getName());
			setAttr("applyIdNo", alreadyCardsCardInfoDtos.get(0).getIdNo());
		}
		setAttr("alreadyCardsCardInfoDtos", alreadyCardsCardInfoDtos);// 为页面赋值

		setAttr("appNo", appNo);
		return "applyCC/commonDialog/alreadyCard/alreadyCardLayout.ftl";
	}

//	/*
//	 * 跳转影像调阅窗口
//	 */
//	@RequestMapping("/imageViewLayout")
//	public String imageLayout(String appNo) {
//		setAttr("appNo", appNo);
//		return "apply/commonDialog/imageView/hrxjEcssImage.ftl";
//	}

	/**
	 * 打开影像调阅窗口页面
	 *
	 * @param appNo
	 * @return
	 */
	@RequestMapping("/showImages")
	public String showImages(String appNo) {
		try {
			if (StringUtils.isNotBlank(appNo)) {
				LinkedHashMap<String, String> map = bankImagesSysInfo.getImgUrlList(appNo);
				setAttr("picUrlMap", map);
				bizAuditHistoryUtils.saveAuditHistory(appNo, "旧-影像调阅");//保存审计历史
				return "/amsTask/applyCC/commonDialog/applyImgEss.ftl";
			} else {
				throw new com.jjb.unicorn.facility.exception.ProcessException("无效的申请件编号，请刷新后重试!");
			}
		} catch (Exception e) {
			logger.error("影像调阅异常", e);
			return exceptionPageUtils.doExcepiton("影像URL获取异常"+e.getMessage(),appNo);
		}
	}

	/**
	 *  获取cmp内容平台URl-进行影像调阅</br>
	 *1.判断申请件是否存在影像批次号</br>
	 *2.如果不存在影像批次号，则调用cmp系统获取一个影像批次号</br>
	 *3.组装打开cmp内容平台的url地址返回  
	 *
	 * @param appNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getCmpSysUrl")
	public Json openCmpSys(String appNo, String sysId) {
		Json json = Json.newSuccess();
		long start = System.currentTimeMillis();
		String showContentUrl = "";
		try {
			showContentUrl = cmpSystemSupport.getCmpSystemShowContentUrl(appNo, sysId, start);
		} catch (Exception e) {
			logger.error(LogPrintUtils.printAppNoLog(appNo, start, "getCmpSysUrl")+"影像调阅异常", e);
			json.setS(false);
			json.setMsg(e.getMessage());
			return json;
		}finally {
			logger.info("获取内容管理平台url结束，"+LogPrintUtils.printAppNoEndLog(appNo, start, "getCmpSysUrl")+",showContentUrl["+showContentUrl+"]");
		}
		json.setObj(showContentUrl);
		bizAuditHistoryUtils.saveAuditHistory(appNo ,"影像调阅");//保存审计历史
		return json;
	}

	/*
	 * 跳转审批历史窗口
	 */
	@RequestMapping("/approveHistoryLayout")
	public String approveHistoryLayout() {
		String appNo = getPara("appNo");
		if(StringUtils.isEmpty(appNo)){
			throw new ProcessException("查看审批历史信息时的appNo为空！");
		}
		// 获取审批历史
		List<TmAppHistory> tmAppHistoryList = applyHistoryService
				.getTmAppHistoryList(appNo);
		setAttr("tmAppHistoryList", tmAppHistoryList);
		bizAuditHistoryUtils.saveAuditHistory(appNo,"查看审批历史信息");//保存审计记录
		return "/amsTask/applyCC/commonDialog/applyHistory/applyHistoryLayout.ftl";
	}

	/*
	 * 跳转案件流转记录窗口
	 */
	@RequestMapping("/taskTransRecordLayout")
	public String taskTransRecordLayout() {
		String appNo = getPara("appNo");
		if (StringUtils.isEmpty("appNo")) {
			throw new ProcessException("查看案件流转记录时的appNo为空！");
		}
		//获取案件流转记录
		List<TmTaskTransfer> taskTransList = applyTaskTransferListService.getTransferTaskListByAppNo(appNo);
		setAttr("tmTaskTransferList",taskTransList);
		return "/amsTask/applyCC/commonDialog/taskTransRecord/taskTransRecordLayout.ftl";
	}

	/*
	 * 跳转修改历史窗口
	 */
	@RequestMapping("/updateHistoryLayout")
	public String updateHistoryLayout() {
		String appNo = getPara("appNo");
		// 获取申请信息修改历史
		List<TmAppModifyHis> tmAppModifyHisList = applyUpdateHistoryService
				.getTmAppModifyHisList(appNo);
		setAttr("tmAppModifyHisList", tmAppModifyHisList);
		bizAuditHistoryUtils.saveAuditHistory(appNo,"查看申请信息修改历史");//保存审计历史
		return "/amsTask/applyCC/commonDialog/updateHistory/updateHistoryLayout.ftl";
	}

	/**
	 * 历史申请
	 */
	@RequestMapping("/applyHistoryLayout")
	public String applyHistoryLayout(){
		String appNo = getPara("appNo");
		TmAppMain tmAppMain = applyQueryService.getTmAppMainByAppNo(appNo);
		String idType = null;
		String idNo = null;
		if(tmAppMain != null){
			idType = tmAppMain.getIdType();
			idNo = tmAppMain.getIdNo();
		}
		if(StringUtils.isEmpty(idType) || StringUtils.isEmpty(idNo)){
			throw new ProcessException("历史申请查看出现异常！appNo=["+appNo+"],idType="+idType+",idNo="+idNo);
		}
		ApplyInfoPreDto applyInfoPreDto = new ApplyInfoPreDto();
		applyInfoPreDto.setAppNo(appNo);
		applyInfoPreDto.setIdType(idType);
		applyInfoPreDto.setIdNo(idNo);
		List<ApplyInfoPreDto> applyInfoPreDtoList = applyInfoPreDtoService.getApplyInfoPreDtoList(applyInfoPreDto);
		setAttr("applyInfoPreDtoList", applyInfoPreDtoList);
		bizAuditHistoryUtils.saveAuditHistory(appNo,"查看历史申请信息");//保存审计历史

		return "/amsTask/applyCC/commonDialog/applyHistory/applyHistoryLayout.ftl";
	}

	/**
	 * 第三方黑名单查询
	 */
	@RequestMapping("/applyCis5systemLayout")
	public String applyCis5systemLayout(String appNo){
		setAttr("appNo",appNo);
		/*try {
			custName = new String(custName.getBytes("iso8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error("客户姓名[{}]编码格式转换异常",custName);
		}
		List<String> cisOtherInfoList = commonCisService.zzcBlackList(loanAmount, "I", idNo, custName, mobile);
//		List<String> cisOtherInfoList = commonCisService.zzcBlackList("10000", "I", "220802194709209166", "众志诚", "15842154142");
		if (CollectionUtils.sizeGtZero(cisOtherInfoList)) {
			setAttr("cisOtherInfoList", cisOtherInfoList);
		}
		*/
		return "/amsTask/applyCC/commonDialog/applyCis5Info/applyCis5InfoLayout.ftl";
	}

	/**
	 * 调用查询公务卡单位地址信息
	 */
	@RequestMapping(value = {"/offialCardCorp"})
	public String offialCardCorp() {
		return "/amsTask/applyCC/commonDialog/offialCard/offialCardCorpLayout.ftl";
	}

	/**
	 * 获取公务卡公司信息
	 * @return
	 */
	/*@ResponseBody
	@RequestMapping(value = {"/getOffialCardCorp"})
	public Page<OfficialCardCorpInfo> getOffialCardCorp() {
		com.jjb.acl.gmp.sdk.OrganizationContextHolder.setCurrentOrg(OrganizationContextHolder.getOrg());
		List<OfficialCardCorpInfo> cardCorpList = new ArrayList<OfficialCardCorpInfo>();
		Map<String, OfficialCardCorpInfo> map = parameterFacility.retrieveParameterObject(OfficialCardCorpInfo.class);
		for (Entry<String, OfficialCardCorpInfo> enty : map.entrySet()) {
			OfficialCardCorpInfo officialCardCorp = new OfficialCardCorpInfo();
			officialCardCorp.corpNo = enty.getValue().corpNo;
			officialCardCorp.corpName = enty.getValue().corpName;
			officialCardCorp.corpPhone = enty.getValue().corpPhone;
			officialCardCorp.corpPostcode = enty.getValue().corpPostcode;
			officialCardCorp.empAddrCtryCd = enty.getValue().empAddrCtryCd;
			officialCardCorp.empProvince = enty.getValue().empProvince;
			officialCardCorp.empCity = enty.getValue().empCity;
			officialCardCorp.empZone = enty.getValue().empZone;
			officialCardCorp.corpAddress = enty.getValue().corpAddress;
			officialCardCorp.empStructure = enty.getValue().empStructure;
			officialCardCorp.empType = enty.getValue().empType;
			cardCorpList.add(officialCardCorp);
		}

		Page<OfficialCardCorpInfo> page = getPage(OfficialCardCorpInfo.class);
		page.setRows(cardCorpList);
		page.setTotal(cardCorpList.size());
		return page;
	}*/

	/**
	 * 根据地区代码查询地区名称
	 * @param dictType
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectAreaByCode")
	public Json selectAreaCode(String dicType, String areaCode) {
		Json json = Json.newSuccess();
		try {
			if(StringUtils.isEmpty(dicType) || StringUtils.isEmpty(areaCode)){
				logger.error("获取地区代码清单异常,请求参数dicType["+dicType+"],areaCode["+areaCode+"]");
				json = Json.newFail();
				json.setS(false);
				json.setMsg("操作无效，请刷新页面重试!");
				return json;
			}
			if(areaCode.length()>4){
				areaCode = areaCode.substring(0, 4);
			}
			//获取TM_ACL_DICT中TYPE为AreaCode的记录，也即发证机关所在地，其中Map的key为地区名称,value为地区代码
			Map<Object,Object> aclDictParam = cacheContext.getAclDictByType(dicType);
			String sb = null;
			if(aclDictParam != null && aclDictParam.size() > 0){
				sb = StringUtils.valueOf(aclDictParam.get(areaCode));
			}else{
				throw new ProcessException("未配置地区代码数据，请配置");
			}
			String str = areaCode;
			if(StringUtils.isNotEmpty(sb)){
				str = str+"-"+sb;
			}
			json.setObj(str);
		} catch (Exception e) {
			json.setS(false);
			json.setMsg("获取发证机关所在地地区名称失败,"+e.getMessage());
		}
		return json;
	}
}
