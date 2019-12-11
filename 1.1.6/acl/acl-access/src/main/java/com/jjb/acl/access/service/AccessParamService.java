package com.jjb.acl.access.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jjb.acl.infrastructure.TmAclParam;
import com.jjb.unicorn.base.service.BaseService;
import com.jjb.unicorn.facility.context.AppHolder;
import com.jjb.unicorn.facility.kits.StrKit;

/**
 * 
 * @author BIG.D.W.K
 * 
 */
@Service
public class AccessParamService extends BaseService<TmAclParam> {
	
	//默认机构号
	private static String defaultOrg = "";
	private static TmAclParam logoImg = null;
	private static TmAclParam webappTitle = null;
	
	//默认机构信息
	private final String BASE_ORG="000000000000";
	private final String ORG_PARAM="OrgParam";
	private final String DEFAULT_ORG_ID="DefaultOrgId";
	//logo信息
	private final String LOGO_PARAM = "LogoParam";
	private final String LOGO_IMG = "LogoImg";
	//标题信息
	private final String TITLE_PARAM = "TitleParam";
	private final String WEBAPP_TITLE = "WebappTitle";
	
	public String getDefaultOrg(){
		if(StrKit.notBlank(defaultOrg)){
			return defaultOrg;
		}
		
		TmAclParam tmAclParam = new TmAclParam();
		tmAclParam.setOrg(BASE_ORG);
		tmAclParam.setType(ORG_PARAM);
		tmAclParam.setCode(DEFAULT_ORG_ID);
		
		List<TmAclParam> list = super.queryForList(tmAclParam);
		if(StrKit.notBlankList(list)){
			defaultOrg = list.get(0).getValue();
			return defaultOrg;
		}
		return null;
	}
	
	
	public TmAclParam getLogoImg(){
		if(logoImg!=null){
			return logoImg;
		}
		
		TmAclParam tmAclParam = new TmAclParam();
		tmAclParam.setOrg(getDefaultOrg());
		tmAclParam.setType(LOGO_PARAM);
		tmAclParam.setCode(LOGO_IMG);
		List<TmAclParam> list = super.queryForList(tmAclParam);
		if(StrKit.notBlankList(list)){
			logoImg = list.get(0);
			return logoImg;
		}
		return null;
	}
	
	public TmAclParam getWebappTitle(){
		if(webappTitle!=null){
			return webappTitle;
		}
		
		TmAclParam tmAclParam = new TmAclParam();
		tmAclParam.setOrg(getDefaultOrg());
		tmAclParam.setType(TITLE_PARAM);
		tmAclParam.setCode(WEBAPP_TITLE);
		String sysType = AppHolder.getSysType();
		if(sysType!=null && !sysType.equals("")){
			tmAclParam.setTypeName(sysType);
		}
		List<TmAclParam> list = super.queryForList(tmAclParam);
		if(StrKit.notBlankList(list)){
			webappTitle = list.get(0);
			return webappTitle;
		}
		return null;
	}
	
}
