package com.jjb.ecms.app.controller.manage;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.ecms.biz.service.apply.ApplyCardNoService;
import com.jjb.ecms.biz.service.apply.TmLuckyCardService;
import com.jjb.ecms.infrastructure.TmLuckyCard;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;

/**
  * @Description: 解锁卡号
  * @author JYData-R&D-L.L
  * @date 2016年9月1日 上午11:57:29
  * @version V1.0
 */
@Controller
@RequestMapping("/card")
public class UnlockCardController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
    private TmLuckyCardService tmLuckyCardService;
	@Autowired
	private ApplyCardNoService applyCardNoService;
	
	/**
	 *解锁信息 查询
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/unlockCardList")
	public Page<TmLuckyCard> unlockCardList() {
		
		Page<TmLuckyCard> page = getPage(TmLuckyCard.class);
		Query query = page.getQuery();
		if(query != null){
			String name = query.get("name")==null?null:query.get("name").toString();
			String idType = query.get("idType")==null?null:query.get("idType").toString();
			String idNo = query.get("idNo")==null?null:query.get("idNo").toString();
			String cellphone = query.get("cellphone")==null?null:query.get("cellphone").toString();
			String cardNo = query.get("cardNo")==null?null:query.get("cardNo").toString();
			String appNo = query.get("appNo")==null?null:query.get("appNo").toString();
			if(StringUtils.isNotBlank(name) || StringUtils.isNotBlank(idNo) 
					|| (StringUtils.isNotBlank(idType)&& StringUtils.isNotBlank(idNo)) 
					|| StringUtils.isNotBlank(cellphone) || StringUtils.isNotBlank(cardNo) || StringUtils.isNotBlank(appNo)){
				query.put("status", Indicator.Y.name());
				page.setQuery(query);
				page = tmLuckyCardService.getUnlockCardPage(page);
			}
		}
		
		return page;
	}
	/**
	 * 解锁卡号页面
	 * @return 解锁卡号页面
	 */
	@RequestMapping("/unlockCard")
	public String page(){
		return "/applyManage/applyCardManage/applyUnlockCard_V1.ftl";
	}
	
	/**
	 * 解锁卡号
	 * @param cardNo
	 * @return json
	 */
	@ResponseBody
	@RequestMapping("/unlock")
	public Json delete(String cardNo,String appNo,Integer attchNo,String bscSuppInd){
		Json json = Json.newSuccess();
//		com.jjb.acl.gmp.sdk.OrganizationContextHolder.setCurrentOrg(OrganizationContextHolder.getOrg());
		try {
			tmLuckyCardService.unlock(cardNo, appNo, attchNo, bscSuppInd);
//			applyCardNoService.unlockSelectedCardNo(cardNo, appNo, attachNo);// 对应卡信息表移除卡号
		} catch (Exception e) {
			logger.error("解锁卡号失败", e);
			json.setFail(e.getMessage());
		}
		return json;		
	}
	
}