package com.jjb.ecms.service.dto.T1011;

import com.jjb.ecms.service.dto.BasicRequest;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description: 大额分期准入请求数据类
 */
@Data
public class T1011Req extends BasicRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	@XStreamOmitField
	public static final String servId="T1011";

	private String Name;// 客户姓名
	private String IdType;// 证件类型
	private String IdNo;// 证件号码
	private String Cellphone;// 手机号码
	private String MaritalStatus;// 婚姻状况
	private String AppProducts;// 申请产品
	private BigDecimal AppAmount;// 申请贷款金额
	private String CompanyName;// 单位名称
	private String FirstContactName;// 第一联系人姓名
	private String FirstContactPhone;// 第一联系人手机
	private String ImageNum;// 影像批次号
	private String WeCode;// 微信个人识别码
	private String PptyProvince;// 所属省
	private String PptyCity;// 所属市
	private String PptyArea;// 车牌归属地
	private String PptyAreaCode;// 车牌归属地字母代号
	private String ChannelType;// 渠道类型

}
