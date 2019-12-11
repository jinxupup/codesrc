package com.jjb.ecms.service.dto.T1001;

import java.io.Serializable;
import java.util.ArrayList;

import com.jjb.ecms.service.dto.BasicResponse;

import lombok.Data;

/**
 * @Description: 补件请求数据类
 * @author JYData-R&D-Big Star
 * @date 2016年1月13日 下午2:44:45
 * @version V1.0
 */
@Data
public class T1001Resp extends BasicResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String idType;
	public String idNo;
	public Integer curPage;// 当前页数
	public Integer rowCnt;// 每行页数
	public Integer totalCnt;// 总记录数
	public ArrayList<T1001Patch> patchs;
	
}
