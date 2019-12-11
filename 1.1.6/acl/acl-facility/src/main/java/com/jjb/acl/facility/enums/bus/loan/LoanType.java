package com.jjb.acl.facility.enums.bus.loan;

import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.unicorn.facility.meta.EnumInfo;

/**
 * 分期类型
 * @author Big.R
 *
 */
@EnumInfo({
	"R|消费转分期",
	"C|现金分期",
	"B|账单分期",
	"P|POS分期",
	"M|大额分期（专项分期）",
	"MCAT|随借随还",
	"MCEP|等额本金",
	"MCEI|等额本息",
	"K|标准大额分期",
	"J|现金大额分期",
	"I|强制账户分期",
	"S|余额账户分期",
	"D|账单延迟分期"
})
public enum LoanType {

	/**
	 *	消费转分期 
	 */
	R("消费转分期", Indicator.N),
	/**
	 *	现金分期 
	 */
	C("现金分期", Indicator.N),
	/**
	 * 	账单分期
	 */
	B("账单分期", Indicator.N),
	/**
	 *	POS分期 
	 */
	P("POS分期", Indicator.N),
	/**
	 *	大额分期（专项分期） 
	 */
	M("大额分期（专项分期", Indicator.N),
	/**
	 * 随借随还Micro Credit at Any Time
	 */
	MCAT("随借随还", Indicator.N),
	/**
	 * 等额本金Micro Credit Equal Principal
	 */
	MCEP("等额本金", Indicator.Y),
	/**
	 * 等额本息Micro Credit Equal Installments of principal and interest
	 */
	MCEI("等额本息", Indicator.Y),
	/**
	 *	标准大额分期 
	 */
	K("标准大额分期",Indicator.N),
	/**
	 *	现金大额分期 
	 */
	J("现金大额分期",Indicator.N),
	/**
	 * 强制账户分期
	 */
	I("强制账户分期",Indicator.N),
	/**
	 * 余额分期
	 */
	S("余额分期",Indicator.N),
	/**
	 * 账单延迟分期
	 */
	D("账单延迟分期",Indicator.N),
	;

	
	private String description;
	private Indicator useSchedule;

	private LoanType(String description, Indicator useSchedule){
		this.description = description;
		this.useSchedule = useSchedule;
	}
	
	public String getDescription() {
		return description;
	}

	public Indicator getUseSchedule() {
		return useSchedule;
	}

}
