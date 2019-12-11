package com.jjb.ecms.service.dto.T1000;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @Description: 申请进度查询返回数据类
 * @author JYData-R&D-Big Star
 * @date 2018年10月16日 下午2:44:45
 * @version V1.0
 */
@Data
public class T1000Apply implements Serializable {

	private static final long serialVersionUID = 7317686372940078499L;

	public String name;// 姓名
	public String idType;//
	public String idNo;//
	public String productCode;// 卡产品代码
	public String productName;// 申请卡产品名称
	public String cardNo;// 已有卡卡号
	public String blockCode;// 已有卡卡片状态
	public String appSource;// 申请渠道
	public String cellphone;// 手机号码
	public String imageNum;// 影像批次号
	public String owningBranch; // 受理网点
	public Date applyDate;// 申请受理日期
	public String rtfState;// 申请件状态
	public String appNo;// 申请件编号
	public String refuseCode;// 拒绝原因
	public String remark;// 备注
	public String accLmt;////授信额度，根据申请状态区分，系统终审之前该额度为系统预估建议的额度，终审之后该额度为客户的最终额度。
	public String corpName;// 单位名称
	public String CardStatus;// 核卡状态 。 A:未激活 ；NF:未完成首刷；CF:已完成首刷
	
}
