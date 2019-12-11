package com.jjb.ecms.service.dto.T1009;

import java.io.Serializable;
import java.util.List;

import com.jjb.ecms.service.dto.BasicResponse;

import lombok.Data;
/**
 *@ClassName T1009Req
 *@Description 排行榜查询返回报文
 *@Author lixing
 *Date 2018/12/24 15:33
 *Version 1.0
 */
@Data
public class T1009Resp extends BasicResponse implements Serializable {

	private static final long serialVersionUID = -4085333700917194561L;

	public List<Rank> Ranks;
    //当前推广人核卡数量
	public String SpreadNum;
	//当前推广人有效核卡数量
	public String ApprovalNum;
	//当前推广人待预审数量
	public String PreCnt;


}
