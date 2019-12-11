package com.jjb.ecms.service.dto.Trans90020;

import java.io.Serializable;

import com.jjb.ecms.service.dto.BasicResponse;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import lombok.Data;

/**
 * 申请行内客户号响应结果
 * @author Administrator
 *
 */
@Data
public class Trans90020Resp  extends BasicResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	@XStreamOmitField
	public static final String servId="2004800059";
	
	public String bankCustomerId;//行内客户号
	public String busReturnCode;//业务响应码；SSSS：成功；F001：外部请求处理失败；
	public String busRespDate;//处理日期；YYYYMMDD
	
	
}
