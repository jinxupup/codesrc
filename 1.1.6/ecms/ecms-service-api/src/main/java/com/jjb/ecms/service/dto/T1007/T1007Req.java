package com.jjb.ecms.service.dto.T1007;

import java.io.Serializable;

import com.jjb.ecms.service.dto.BasicRequest;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import lombok.Data;

/**
 * 推广人(系统用户)查询请求
 * 
 * @author hp
 *
 */
@Data
public class T1007Req extends BasicRequest implements Serializable {

	private static final long serialVersionUID = -4085333700917194561L;
	@XStreamOmitField
	public static final String servId="T1007";
	private String userNo;// 用户工号
	private String phoneNo;// 用户电话
	private String branchCode;// 机构网点
	private String quyType;//查询类型
	private Integer curPage=1;// 当前页数
	private Integer rowCnt=50;// 每页行数
}
