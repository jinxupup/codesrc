package com.jjb.ecms.service.dto.T1009;

import java.io.Serializable;

import lombok.Data;

/**
 * 推广人(系统用户)信息
 * 
 * @author hp
 *
 */
@Data
public class Rank implements Serializable {

	private static final long serialVersionUID = -4085333700917194561L;
	public String Ranking;// 排名名次
	public String Name;// 客户姓名
	public String Cellphone;// 手机号码
	public String SuccCnt;// 成功申请数量

}
