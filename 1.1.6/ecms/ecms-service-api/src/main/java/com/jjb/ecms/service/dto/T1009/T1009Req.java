package com.jjb.ecms.service.dto.T1009;

import java.io.Serializable;

import com.jjb.ecms.service.dto.BasicRequest;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import lombok.Data;
/**
 *@ClassName T1009Req
 *@Description 排行榜查询请求报文
 *@Author lixing
 *Date 2018/12/24 15:33
 *Version 1.0
 */
@Data
public class T1009Req extends BasicRequest implements Serializable {

	private static final long serialVersionUID = -4085333700917194561L;
	@XStreamOmitField
	public static final String servId="T1009";

	//查询类型
	public String QuyType;
	//查询渠道
	public String AppSource;
	//查询名次总数
	public String TotalCnt;
	//推广人工号
	public String SpreaderNO;

	
}
