/**
 * 
 */
package com.jjb.ecms.biz.service.approve;

import com.jjb.ecms.infrastructure.TmAppAudit;
import com.jjb.ecms.infrastructure.TmAppMain;

/**
 * @Description: TODO
 * @author JYData-R&D-BigK.K
 * @date 2016年9月7日 下午3:43:09
 * @version V1.0  
 */
public interface PersonCheckInfoService {



	/**
	 * 提交人工核查信息(提交和退回)
	 * @param main
	 * @param audit
	 */
	void submitPersonCheckResult(TmAppMain main,TmAppAudit audit);
}
