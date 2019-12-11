package com.jjb.ecms.service.dto.T1000;

import java.io.Serializable;
import java.util.Date;

import com.jjb.ecms.service.dto.BasicRequest;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import lombok.Data;

/**
 * @Description: 申请进度查询请求数据类
 * @author JYData-R&D-Big Star
 * @date 2016年11月23日 上午10:08:31
 * @version V1.0
 */
@Data
public class T1000Req extends BasicRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	@XStreamOmitField
	public static final String servId="T1000";

	private String idType;// 证件类型
	private String idNo;// 证件号码
	private String name;// 姓名
	private String cellphone;// 手机号码
	private String rtfState;// 审批状态
	private String stateType;//状态类型,F：拒绝 ；S：通过 ；A：审批中；P：待预审；
	private Date startDate;// 开始日期
	private Date endDate;// 截止日期
	private String appNo;// 申请件编号
	private String imageNum;// 影像批次号
	private String owningBranch;// 受理网点
	private String cardNo;// 卡号
	private String appSource;// 申请渠道
	private String spreader;// 推广人工号或姓名
	private String inputUser;// 录入人员工号或姓名
	private Integer curPage=1;// 当前页数
	private Integer rowCnt=10;// 每页行数

}
