package com.jjb.ecms.service.dto.T1001;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;


/**
 * 补件结果信息模型
 * 
 * @author H.N
 */
@Data
public class T1001Patch implements Serializable {
    private static final long serialVersionUID = 1L;

	public String name;// 姓名
	public String productCd;// 卡产品代码
	public String productName;// 卡产品名称
	public Date applyDate;// 申请受理日期
	public String rtfState;// 申请件状态
	public String appNo;// 申请件编号
	public String pbType;// 补件类型
	public Date pbStartDate;// 补件开始时间
	public Date pbTimeoutDate;// 补件超时时间
	public Date pbStBatchDate;// 补件开始业务时间
	public Date pbOutBatchDate;// 补件超时业务时间
	public String appCode;// 业务流水号\影像批次号
	public String operatorId;// 任务所属人

}    