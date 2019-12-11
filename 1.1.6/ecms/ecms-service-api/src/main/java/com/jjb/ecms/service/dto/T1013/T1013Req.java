package com.jjb.ecms.service.dto.T1013;

import java.io.Serializable;

import com.jjb.ecms.service.dto.BasicRequest;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import lombok.Data;

/**
 *@ClassName T1013Req
 *@Description 合伙人准入申请结果
 *@Author 何嘉能
 *Version 1.0
 */
@Data
public class T1013Req extends BasicRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XStreamOmitField
	public static final String servId="T1013";
	private String Name;	//客户姓名
    private String IdNo;	//证件号码
	private String CellPhone;	//手机号码
	private String ExitCard;//是否已有卡客户 ; Y：已有卡 ;N：无卡
	private String PartnerType;//合伙人类型
	private String CorpName;//单位名称
	private String EmpDepartment;//部门

}
