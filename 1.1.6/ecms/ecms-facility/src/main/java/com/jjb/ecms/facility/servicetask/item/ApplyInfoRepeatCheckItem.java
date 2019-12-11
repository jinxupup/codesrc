package com.jjb.ecms.facility.servicetask.item;

import java.io.Serializable;

import com.jjb.acl.facility.enums.sys.Indicator;

import lombok.Data;

/**
  * @Description: 重复申请规则Item-serviceTask
  * @author JYData-R&D-Big Star
  * @date 2016年9月6日 下午2:03:26
  * @version V1.0
 */
@Data
public class ApplyInfoRepeatCheckItem implements Serializable {

	private static final long serialVersionUID = -4813018338417847227L;
	private boolean existInAps; // 申请卡产品在信审系统中是否存在
	private boolean existInCps; // 卡产品在发卡系统中是否重复申请
	private boolean applyAttach; // 是否申请附卡
	private boolean attachToPrimMcard; // 附卡是否有对应主卡
	private String attachToPrimMcardStatus; // 附属卡对应主卡状态
	private String acctBlockCode; // 已申请卡片账户锁定码
	private String cardBlockCode; // 已申请卡片封锁码
	private String result;
//	private ApplyRefuseCode applyRefuseCode; // 拒绝原因码
	private String productCd; // 产品编号
	private Indicator inqString;
	
}
