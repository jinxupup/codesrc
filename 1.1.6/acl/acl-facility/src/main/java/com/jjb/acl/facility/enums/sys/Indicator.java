package com.jjb.acl.facility.enums.sys;

import com.jjb.unicorn.facility.meta.EnumInfo;

/**
 * @Description: 是否枚举类
 * @author JYData-R&D-Big Star
 * @date 2016年11月23日 上午10:46:41
 * @version V1.0
 */
@EnumInfo({
	"Y|是",
	"N|否"
})
public enum Indicator {
	/**
	 * 是
	 */
	Y("是"),
	/**
	 * 
	 */
	N("否");

	public String state;
	public String lab;

	private Indicator(String lab) {
		this.state = name();
		this.lab = lab;
	}
}
