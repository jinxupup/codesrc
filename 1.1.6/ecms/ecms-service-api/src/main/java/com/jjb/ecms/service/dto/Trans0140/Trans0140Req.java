package com.jjb.ecms.service.dto.Trans0140;

import java.io.Serializable;

import com.jjb.ecms.service.dto.BasicRequest;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import lombok.Data;

/**
 * 账户资产信息查询请求</br>
 * @author hejn
 *
 */
@Data
public class Trans0140Req extends BasicRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	@XStreamOmitField
	public static final String servId="04003000031";
	@XStreamOmitField
	public static final String servIdSimple="Trans0140";
	
	
	private String name;//客户姓名
	private String idNo;//客户证件号码
	private String cellPhone;//客户手机
	private String idType;//客户证件类型

}
