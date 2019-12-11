package com.jjb.ecms.service.dto.Trans90020;

import java.io.Serializable;

import com.jjb.ecms.service.dto.BasicRequest;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import lombok.Data;

/**
 * 获取行内客户号 </br>
 * 直接访问信用卡前置获取行内客户号
 * @author hejn
 *
 */
@Data
public class Trans90020Req  extends BasicRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	@XStreamOmitField
	public static final String servId="90020";
	private String reqXml;
}
