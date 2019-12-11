package com.jjb.ecms.service.dto.Trans0004;

import java.io.Serializable;

import com.jjb.ecms.service.dto.BasicResponse;

import lombok.Data;
/**
 *@ClassName Trans0004Resp
 *@Description 约定还款返回
 *@Author lixing
 *Date 2018/12/31 14:32
 *Version 1.0
 */
@Data
public class Trans0004Resp extends BasicResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	public String ReturnCode;
	public String ReturnMsg;

}
