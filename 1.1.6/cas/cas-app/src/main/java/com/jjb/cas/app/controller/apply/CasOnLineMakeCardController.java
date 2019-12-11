package com.jjb.cas.app.controller.apply;

import com.jjb.ecms.biz.util.BizAuditHistoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.cas.biz.rule.utils.OnlineMakeCardSupport;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * @ClassName OnLineMakeCardController Company jydata-tech
 * @Description TODO Author zhangwenlu Date 2018/10/30 11:09 Version 1.0
 */
@Controller
@RequestMapping("/cas_onLineMakeCard")
public class CasOnLineMakeCardController extends BaseController {
	@Autowired
	private OnlineMakeCardSupport onlineMakeCardSupport;
	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired
	private BizAuditHistoryUtils bizAuditHistoryUtils;

	@ResponseBody
	@RequestMapping("/onLineMake")
	public Json onLineMake(String appNo) {
		bizAuditHistoryUtils.saveAuditHistory(appNo,"实时建账制卡");//保存审计历史
		Json json = Json.newSuccess();
		ApplyInfoDto applyInfoDto = applyQueryService.getApplyInfoByAppNo(appNo);
		if (applyInfoDto != null) {
			try {
				onlineMakeCardSupport.onlineMakeCard(applyInfoDto);
			} catch (Exception e) {
				json.setS(false);
				json.setFail(e.getMessage());
			}
		}
		return json;
	}
}
