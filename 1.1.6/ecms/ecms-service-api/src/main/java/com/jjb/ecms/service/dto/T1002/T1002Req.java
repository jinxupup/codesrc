package com.jjb.ecms.service.dto.T1002;

import java.io.Serializable;

import com.jjb.ecms.service.dto.BasicRequest;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import lombok.Data;

/**
 * 准入及老客户查询
 * @author hp
 *
 */
@Data
public class T1002Req extends BasicRequest implements Serializable {
	
	private static final long serialVersionUID = -4085333700917194561L;
	@XStreamOmitField
	public static final String servId="T1002";
	private String name       ;//客户姓名 
	private String idType     ;//证件类型 
	private String idNo       ;//证件号码 
	private String cellphone  ;//手机号码 
	private String appType    ;//申请类型 
	private String primCardNo ;//主卡卡号 
	private String productCd  ;//产品代码 
	private String inputNo    ;//录入员编号
	private String inputName  ;//录入员姓名
	
}