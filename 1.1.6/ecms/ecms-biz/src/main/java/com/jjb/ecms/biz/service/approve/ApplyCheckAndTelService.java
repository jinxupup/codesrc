/**
 * 
 */
package com.jjb.ecms.biz.service.approve;

import com.jjb.ecms.facility.nodeobject.ApplyNodeTelCheckBisicData;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.unicorn.facility.model.Query;

/**
 * @Description: 初审、电调提交保存或发起流程服务接口
 * @author JYData-R&D-Big T.T
 * @date 2018年2月23日 下午3:20:35
 * @version V1.0  
 */

public interface ApplyCheckAndTelService {

	/**
	 * 初审、电调提交保存或发起流程
	 * @param ifSave false：提交；true：保存
	 * @param type 节点类型   C：初审  T：电调
	 * @param query 操作数据
	 * @param applyNodeTelCheckBisicData 电调数据
	 */
	public void saveOrSubmitDataService(Boolean ifSave, String type,
			Query query,ApplyNodeTelCheckBisicData applyNodeTelCheckBisicData);
	
	public void preSubmitDataService(String type, Query query, TmAppPrimCardInfo tmAppPrimCardInfo);

	public void fileSubmitDataService(String type, Query query);
}
