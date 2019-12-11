package com.jjb.acl.gmp.api;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.meta.RPCQueueName;
import com.jjb.unicorn.facility.meta.RPCVersion;
/**
 * 全局、不分机构的管理类服务。这个接口里是个大杂烩，所有全局相关的通讯、服务需求都写在这里了。
 * @author LI.J
 *
 */
@RPCQueueName("global.gmp.rpc.global-management")
@RPCVersion("1.0.0")
public interface GlobalManagementService
{
	/**
	 * 获取所有机构的实例路由表.
	 * @param org
	 * @return 返回由org为key的map，value里为另一个map，为队列前缀/实例映射
	 */
	Map<String , Map<String, String>> getInstanceRoute();
	
	/**
	 * 机构号/描述的键值对，按机构号排序
	 */
	Map<String,String>  getOrgList();
	
	/**
	 * 获取当前系统状态，包括联机和批量日期等
	 */
	SystemStatus getSystemStatus();

	/**
	 * 取指定实例对应的服务机构。每个应用实例都应该按这个列表检查所操作的数据，只能操作列表中对应机构的数据
	 * @param system 子系统类型
	 * @param instanceName 实例名
	 * @return
	 */
	List<String> getServeOrg(String system, String instanceName);
	
	/**
	 * 获取机构实例信息
	 * 
	 * @return
	 */
	List<OrgInstanceInfo> getOrgIntanceInfo(String orgId);
	
	/**
	 * 根据指定子系统取得服务于指定机构的实例。
	 * @param system 子系统类型
	 * @param org 机构名
	 * @return 实例名
	 */
	String getInstance(String system, String org);
	
	/**
	 * 非金融调用启动批量
	 * @param jobName JOB名称
	 * @param param 批量启动时参数，key变量名，value值
	 * @throws ProcessException
	 */
	void startBatch(String jobName, Map<String, String> param) throws ProcessException;
	
	/**
	 * <p>多机构独立批量启动方法</p>
	 * @param org 调用系统机构号
	 * @param jobName 批量名
	 * @param jobParam 批量参数
	 * @return ExecutionId 执行器ID
	 * @throws ProcessException
	 */
	public Long startIndieBatchByOrg(String org, String jobName, LinkedHashMap<String, String> jobParam) throws ProcessException;
}
