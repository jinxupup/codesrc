package com.jjb.ecms.service.dto.T1010;

import java.io.Serializable;

import com.jjb.ecms.service.dto.BasicRequest;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import lombok.Data;

/**
 *@ClassName T1010Req
 *@Description 排行榜查询请求报文
 *@Author lixing
 *Date 2018/12/24 15:33
 *Version 1.0
 */
@Data
public class T1010Req extends BasicRequest implements Serializable {

	private static final long serialVersionUID = -4085333700917194561L;
	@XStreamOmitField
	public static final String servId="T1010";
	private String AppNo;
	//当前客户经理
    private String CurOpUser;
	//操作类型
	private String OpType;
	//接收案件客户经理
	private String AccepUser;
	//备注
	private String Remark;


}
