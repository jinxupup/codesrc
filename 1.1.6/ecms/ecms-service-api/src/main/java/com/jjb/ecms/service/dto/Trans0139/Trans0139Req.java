package com.jjb.ecms.service.dto.Trans0139;

import java.io.Serializable;

import com.jjb.ecms.service.dto.BasicRequest;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import lombok.Data;

/**
 * 行内征信信息查询请求</br>
 * 1.法院被执行人信息查询
 * @author hejn
 *
 */
@Data
public class Trans0139Req extends BasicRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	@XStreamOmitField
	public static final String servId="04003000031";
	@XStreamOmitField
	public static final String servIdSimple="Trans0139";
	
	private String appNo;//申请件编号、业务流水号
	private String name;//客户姓名
	private String idNo;//客户证件号码
	private String cellPhone;//客户手机
	private String idType;//客户证件类型

}
