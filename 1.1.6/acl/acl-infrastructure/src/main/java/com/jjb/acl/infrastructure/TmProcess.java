package com.jjb.acl.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 服务进程定义
 * @author jjb
 */
public class TmProcess implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer processId;

    private Integer instanceId;

    private String nodeCode;

    private String processStatus;

    private String artifactId;

    private String artifactVersion;

    private String processType;

    private String programArgs;

    private Integer jvmHeapMax;

    private Integer jvmMaxPerm;

    private String jvmArgs;

    private Date lastStartup;

    private Date lastHeartbeat;

    private String jmxHeapFree;

    private String memo;

    private Integer jpaVersion;

    private String hstHeapFree;

    private String systemName;

    private Date hstHeapFreeTime;

    private String useFlag;

    /**
     * <p>进程编号</p>
     */
    public Integer getProcessId() {
        return processId;
    }

    /**
     * <p>进程编号</p>
     */
    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    /**
     * <p>实例号</p>
     */
    public Integer getInstanceId() {
        return instanceId;
    }

    /**
     * <p>实例号</p>
     */
    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    /**
     * <p>节点代码</p>
     */
    public String getNodeCode() {
        return nodeCode;
    }

    /**
     * <p>节点代码</p>
     */
    public void setNodeCode(String nodeCode) {
        this.nodeCode = nodeCode;
    }

    /**
     * <p>进程状态</p>
     */
    public String getProcessStatus() {
        return processStatus;
    }

    /**
     * <p>进程状态</p>
     */
    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    /**
     * <p>程序名</p>
     */
    public String getArtifactId() {
        return artifactId;
    }

    /**
     * <p>程序名</p>
     */
    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    /**
     * <p>程序版本</p>
     */
    public String getArtifactVersion() {
        return artifactVersion;
    }

    /**
     * <p>程序版本</p>
     */
    public void setArtifactVersion(String artifactVersion) {
        this.artifactVersion = artifactVersion;
    }

    /**
     * <p>进程类型</p>
     */
    public String getProcessType() {
        return processType;
    }

    /**
     * <p>进程类型</p>
     */
    public void setProcessType(String processType) {
        this.processType = processType;
    }

    /**
     * <p>程序参数</p>
     */
    public String getProgramArgs() {
        return programArgs;
    }

    /**
     * <p>程序参数</p>
     */
    public void setProgramArgs(String programArgs) {
        this.programArgs = programArgs;
    }

    /**
     * <p>最大堆大小(MB)</p>
     * <p>JVM启动参数</p>
     */
    public Integer getJvmHeapMax() {
        return jvmHeapMax;
    }

    /**
     * <p>最大堆大小(MB)</p>
     * <p>JVM启动参数</p>
     */
    public void setJvmHeapMax(Integer jvmHeapMax) {
        this.jvmHeapMax = jvmHeapMax;
    }

    /**
     * <p>最大持久代大小(MB)</p>
     * <p>启动参数</p>
     */
    public Integer getJvmMaxPerm() {
        return jvmMaxPerm;
    }

    /**
     * <p>最大持久代大小(MB)</p>
     * <p>启动参数</p>
     */
    public void setJvmMaxPerm(Integer jvmMaxPerm) {
        this.jvmMaxPerm = jvmMaxPerm;
    }

    /**
     * <p>运行参数</p>
     * <p>启动参数，其它JVM参数</p>
     */
    public String getJvmArgs() {
        return jvmArgs;
    }

    /**
     * <p>运行参数</p>
     * <p>启动参数，其它JVM参数</p>
     */
    public void setJvmArgs(String jvmArgs) {
        this.jvmArgs = jvmArgs;
    }

    /**
     * <p>上次启动时间</p>
     */
    public Date getLastStartup() {
        return lastStartup;
    }

    /**
     * <p>上次启动时间</p>
     */
    public void setLastStartup(Date lastStartup) {
        this.lastStartup = lastStartup;
    }

    /**
     * <p>上次心跳报文</p>
     */
    public Date getLastHeartbeat() {
        return lastHeartbeat;
    }

    /**
     * <p>上次心跳报文</p>
     */
    public void setLastHeartbeat(Date lastHeartbeat) {
        this.lastHeartbeat = lastHeartbeat;
    }

    /**
     * <p>当前堆空闲</p>
     * <p>运行状态</p>
     */
    public String getJmxHeapFree() {
        return jmxHeapFree;
    }

    /**
     * <p>当前堆空闲</p>
     * <p>运行状态</p>
     */
    public void setJmxHeapFree(String jmxHeapFree) {
        this.jmxHeapFree = jmxHeapFree;
    }

    /**
     * <p>备注</p>
     */
    public String getMemo() {
        return memo;
    }

    /**
     * <p>备注</p>
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * <p>乐观锁版本号</p>
     */
    public Integer getJpaVersion() {
        return jpaVersion;
    }

    /**
     * <p>乐观锁版本号</p>
     */
    public void setJpaVersion(Integer jpaVersion) {
        this.jpaVersion = jpaVersion;
    }

    /**
     * <p>历史最小堆空闲百分比</p>
     */
    public String getHstHeapFree() {
        return hstHeapFree;
    }

    /**
     * <p>历史最小堆空闲百分比</p>
     */
    public void setHstHeapFree(String hstHeapFree) {
        this.hstHeapFree = hstHeapFree;
    }

    /**
     * <p>系统名称</p>
     * <p>jyd-4.2.5版本新增字段，用于模块拆分后，启动用应用时的jar包的路径拼接，如果该字段为‘’或者null时用Artifact_Id字段“-”分割后第一个字符串拼接，有值时用该字段拼接。</p>
     */
    public String getSystemName() {
        return systemName;
    }

    /**
     * <p>系统名称</p>
     * <p>jyd-4.2.5版本新增字段，用于模块拆分后，启动用应用时的jar包的路径拼接，如果该字段为‘’或者null时用Artifact_Id字段“-”分割后第一个字符串拼接，有值时用该字段拼接。</p>
     */
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    /**
     * <p>历史最小堆出现时间</p>
     */
    public Date getHstHeapFreeTime() {
        return hstHeapFreeTime;
    }

    /**
     * <p>历史最小堆出现时间</p>
     */
    public void setHstHeapFreeTime(Date hstHeapFreeTime) {
        this.hstHeapFreeTime = hstHeapFreeTime;
    }

    /**
     * <p>是否启用</p>
     */
    public String getUseFlag() {
        return useFlag;
    }

    /**
     * <p>是否启用</p>
     */
    public void setUseFlag(String useFlag) {
        this.useFlag = useFlag;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("processId", processId);
        map.put("instanceId", instanceId);
        map.put("nodeCode", nodeCode);
        map.put("processStatus", processStatus);
        map.put("artifactId", artifactId);
        map.put("artifactVersion", artifactVersion);
        map.put("processType", processType);
        map.put("programArgs", programArgs);
        map.put("jvmHeapMax", jvmHeapMax);
        map.put("jvmMaxPerm", jvmMaxPerm);
        map.put("jvmArgs", jvmArgs);
        map.put("lastStartup", lastStartup);
        map.put("lastHeartbeat", lastHeartbeat);
        map.put("jmxHeapFree", jmxHeapFree);
        map.put("memo", memo);
        map.put("jpaVersion", jpaVersion);
        map.put("hstHeapFree", hstHeapFree);
        map.put("systemName", systemName);
        map.put("hstHeapFreeTime", hstHeapFreeTime);
        map.put("useFlag", useFlag);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("processId")) this.setProcessId(DataTypeUtils.getIntegerValue(map.get("processId")));
        if (map.containsKey("instanceId")) this.setInstanceId(DataTypeUtils.getIntegerValue(map.get("instanceId")));
        if (map.containsKey("nodeCode")) this.setNodeCode(DataTypeUtils.getStringValue(map.get("nodeCode")));
        if (map.containsKey("processStatus")) this.setProcessStatus(DataTypeUtils.getStringValue(map.get("processStatus")));
        if (map.containsKey("artifactId")) this.setArtifactId(DataTypeUtils.getStringValue(map.get("artifactId")));
        if (map.containsKey("artifactVersion")) this.setArtifactVersion(DataTypeUtils.getStringValue(map.get("artifactVersion")));
        if (map.containsKey("processType")) this.setProcessType(DataTypeUtils.getStringValue(map.get("processType")));
        if (map.containsKey("programArgs")) this.setProgramArgs(DataTypeUtils.getStringValue(map.get("programArgs")));
        if (map.containsKey("jvmHeapMax")) this.setJvmHeapMax(DataTypeUtils.getIntegerValue(map.get("jvmHeapMax")));
        if (map.containsKey("jvmMaxPerm")) this.setJvmMaxPerm(DataTypeUtils.getIntegerValue(map.get("jvmMaxPerm")));
        if (map.containsKey("jvmArgs")) this.setJvmArgs(DataTypeUtils.getStringValue(map.get("jvmArgs")));
        if (map.containsKey("lastStartup")) this.setLastStartup(DataTypeUtils.getDateValue(map.get("lastStartup")));
        if (map.containsKey("lastHeartbeat")) this.setLastHeartbeat(DataTypeUtils.getDateValue(map.get("lastHeartbeat")));
        if (map.containsKey("jmxHeapFree")) this.setJmxHeapFree(DataTypeUtils.getStringValue(map.get("jmxHeapFree")));
        if (map.containsKey("memo")) this.setMemo(DataTypeUtils.getStringValue(map.get("memo")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
        if (map.containsKey("hstHeapFree")) this.setHstHeapFree(DataTypeUtils.getStringValue(map.get("hstHeapFree")));
        if (map.containsKey("systemName")) this.setSystemName(DataTypeUtils.getStringValue(map.get("systemName")));
        if (map.containsKey("hstHeapFreeTime")) this.setHstHeapFreeTime(DataTypeUtils.getDateValue(map.get("hstHeapFreeTime")));
        if (map.containsKey("useFlag")) this.setUseFlag(DataTypeUtils.getStringValue(map.get("useFlag")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", processId="+processId+
        "processId="+processId+
        ", instanceId="+instanceId+
        "instanceId="+instanceId+
        ", nodeCode="+nodeCode+
        "nodeCode="+nodeCode+
        ", processStatus="+processStatus+
        "processStatus="+processStatus+
        ", artifactId="+artifactId+
        "artifactId="+artifactId+
        ", artifactVersion="+artifactVersion+
        "artifactVersion="+artifactVersion+
        ", processType="+processType+
        "processType="+processType+
        ", programArgs="+programArgs+
        "programArgs="+programArgs+
        ", jvmHeapMax="+jvmHeapMax+
        "jvmHeapMax="+jvmHeapMax+
        ", jvmMaxPerm="+jvmMaxPerm+
        "jvmMaxPerm="+jvmMaxPerm+
        ", jvmArgs="+jvmArgs+
        "jvmArgs="+jvmArgs+
        ", lastStartup="+lastStartup+
        "lastStartup="+lastStartup+
        ", lastHeartbeat="+lastHeartbeat+
        "lastHeartbeat="+lastHeartbeat+
        ", jmxHeapFree="+jmxHeapFree+
        "jmxHeapFree="+jmxHeapFree+
        ", memo="+memo+
        "memo="+memo+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        ", hstHeapFree="+hstHeapFree+
        "hstHeapFree="+hstHeapFree+
        ", systemName="+systemName+
        "systemName="+systemName+
        ", hstHeapFreeTime="+hstHeapFreeTime+
        "hstHeapFreeTime="+hstHeapFreeTime+
        ", useFlag="+useFlag+
        "useFlag="+useFlag+
        "]";
    }

    public void fillDefaultValues() {
        if (instanceId == null) instanceId = 0;
        if (nodeCode == null) nodeCode = "";
        if (processStatus == null) processStatus = "";
        if (artifactId == null) artifactId = "";
        if (artifactVersion == null) artifactVersion = "";
        if (processType == null) processType = "";
        if (programArgs == null) programArgs = "";
        if (jvmHeapMax == null) jvmHeapMax = 0;
        if (jvmMaxPerm == null) jvmMaxPerm = 0;
        if (jvmArgs == null) jvmArgs = "";
        if (lastStartup == null) lastStartup = new Date();
        if (lastHeartbeat == null) lastHeartbeat = new Date();
        if (jmxHeapFree == null) jmxHeapFree = "";
        if (memo == null) memo = "";
        if (jpaVersion == null) jpaVersion = 0;
        if (hstHeapFree == null) hstHeapFree = "";
        if (systemName == null) systemName = "";
        if (hstHeapFreeTime == null) hstHeapFreeTime = new Date();
        if (useFlag == null) useFlag = "";
    }
}