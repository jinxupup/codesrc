package com.jjb.ecms.service.dto.T9000;

import java.io.Serializable;

import com.jjb.ecms.service.dto.BasicResponse;

import lombok.Data;

/**
 * @Description: 接收推送审批结论与风控结论响应数据
 * @author hp
 *
 */
@Data
public class T9000Resp extends BasicResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	public String ReturnCode;
	public String ReturnMsg;
	
}
