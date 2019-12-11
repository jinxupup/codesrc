package com.jjb.acl.facility.enums.bus.loan;

import com.jjb.unicorn.facility.meta.EnumInfo;

/**
 * @Description: 自选卡号锁定原因
 * @author JYData-R&D-Big Star
 * @date 2016年3月15日 上午11:47:09
 * @version V1.0
 */
@EnumInfo({ "S|自选卡号占用", "L|吉祥卡号占用", "B|黑色卡号占用" })
public enum LockReason {
	S, L, B
}
