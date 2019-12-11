package com.jjb.ecms.service.dto.T1007;

import java.io.Serializable;
import java.util.List;

import com.jjb.ecms.service.dto.BasicResponse;

import lombok.Data;

/**
 * 推广人(系统用户)查询响应
 * 
 * @author hp
 *
 */
@Data
public class T1007Resp extends BasicResponse implements Serializable {

	private static final long serialVersionUID = -4085333700917194561L;
	public Integer curPage;// 当前页数
	public Integer rowCnt;// 每页行数
	public Long totalCnt;// 总记录数
	public List<T1007UserInfo> users;//推广人清单

}
