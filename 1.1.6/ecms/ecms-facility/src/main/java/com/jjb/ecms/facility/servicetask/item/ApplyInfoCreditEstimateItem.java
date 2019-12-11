package com.jjb.ecms.facility.servicetask.item;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * @Description: 授信额度计算用ITEM
 * @author JYData-R&D-Big Star
 * @date 2016年11月23日 上午10:42:57
 * @version V1.0
 */
@Data
public class ApplyInfoCreditEstimateItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3853358806218085459L;
	private Integer pointResult; // 评分结果
	private BigDecimal sugLmt; // 系统建议额度
	private String productCd; // 产品编号


}
