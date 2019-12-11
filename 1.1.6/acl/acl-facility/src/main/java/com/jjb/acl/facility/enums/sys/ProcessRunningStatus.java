package com.jjb.acl.facility.enums.sys;

import com.jjb.unicorn.facility.meta.EnumInfo;

/**
 * 托管进程运行状态
 * @author LI.J
 *
 */
@EnumInfo({
	"STARTING|正在启动",
	"RUNNING|正在运行",
	"STOPPING|正在停止",
	"STOPPED|已停止"
})
public enum ProcessRunningStatus {
    /**
     * 正在启动
     */
    STARTING,
    /**
     * 正在运行 
     */
    RUNNING,
    /**
     * 正在停止 
     */
    STOPPING,
    /**
     * 已停止
     */
    STOPPED;

}
