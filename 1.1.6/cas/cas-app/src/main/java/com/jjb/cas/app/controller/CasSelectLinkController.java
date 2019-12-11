package com.jjb.cas.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.acl.access.service.AccessDictService;
import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.web.controller.BaseController;

@Controller
@RequestMapping("/cas_selectLink")
public class CasSelectLinkController extends BaseController {

	@Autowired
	AccessDictService accessDictService;

	@ResponseBody
	@RequestMapping("/dict")
	public Json dict() {

		String dicttype = getPara("dicttype");
		Json j = Json.newSuccess();
		try {
			List<TmAclDict> list = accessDictService.getByType(dicttype);
			j.setObj(list);
		} catch (Exception e) {
			j.setFail("获取业务字典值失败，字典类型[" + dicttype + "]");
		}

		return j;

	}

}