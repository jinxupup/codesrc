package com.jjb.ecms.service.dto.T1001;

import java.io.Serializable;

import com.jjb.ecms.service.dto.BasicRequest;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import lombok.Data;

/**
 * @Description: 补件请求数据类
 * @author JYData-R&D-Big Star
 * @date 2016年1月13日 下午2:44:45
 * @version V1.0
 */
@Data
public class T1001Req extends BasicRequest implements Serializable {
	
	private static final long serialVersionUID = -4085333700917194561L;
	@XStreamOmitField
	public static final String servId="T1001";
	
	private String idType;// 证件类型
	private String idNo;// 证件号码
	private String appCode;// 业务流水号\影像批次号
	private String pbType;// 补件类型
	private String operatorId;// 操作人工号
	private String optType;// 调用方法类型
	private String appNo;// 申请件编号
	private String pbSource;// 补件渠道
	private String isOk;// 补件完成标志
	private Integer curPage = 1;// 当前页数
	private Integer rowCnt=10;// 每页行数

}
