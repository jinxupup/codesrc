package com.jjb.ecms.service.dto.T1000;

import java.io.Serializable;
import java.util.ArrayList;

import com.jjb.ecms.service.dto.BasicResponse;

import lombok.Data;

/**
  * @Description: 申请进度查询返回数据类
  * @author JYData-R&D-Big Star
  * @date 2016年11月23日 上午10:10:02
  * @version V1.0
 */
@Data
public class T1000Resp extends BasicResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	
	public int curPage;//当前页数
	public int rowCnt;//每页记录数
	public int totalCnt;//总记录数
	
	public int succCnt;// 核卡数量
	public int effSuccCnt;// 有效核卡数量
	public int refuseCnt;// 拒绝数量
	public int approveCnt;// 审核中数量
	public int preCnt;// 预审中数量

	public ArrayList<T1000Apply> applys;
	
}
