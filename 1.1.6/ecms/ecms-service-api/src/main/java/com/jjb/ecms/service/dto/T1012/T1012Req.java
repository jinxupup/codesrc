package com.jjb.ecms.service.dto.T1012;

import com.jjb.ecms.service.dto.BasicRequest;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 大额分期准入请求数据类
 */
@Data
public class T1012Req extends BasicRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	@XStreamOmitField
	public static final String servId="T1012";

	private String Name;// 客户姓名
	private String IdType;// 证件类型
	private String IdNo;// 证件号码
}
