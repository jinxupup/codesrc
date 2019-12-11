package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 申请件异常池
 * @author jjb
 */
public class TmAppExcePool implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String appNo;

    private String name;

    private String idNo;

    private String cellphone;

    private String appType;

    private String productCd;

    private String appSource;

    private String nextRtfState;

    private String exceType;

    private String exceRtfState;

    private String exceActTaskName;

    private String exceActTaskId;

    private String exceStatus;

    private String exceReason;

    private Date exceTime;

    private Date updateTime;

    private String updateUser;

    private Integer jpaVersion;

    /**
     * <p>自增主键</p>
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>自增主键</p>
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * <p>申请件编号</p>
     */
    public String getAppNo() {
        return appNo;
    }

    /**
     * <p>申请件编号</p>
     */
    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    /**
     * <p>姓名</p>
     */
    public String getName() {
        return name;
    }

    /**
     * <p>姓名</p>
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>证件号码</p>
     */
    public String getIdNo() {
        return idNo;
    }

    /**
     * <p>证件号码</p>
     */
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    /**
     * <p>移动电话</p>
     */
    public String getCellphone() {
        return cellphone;
    }

    /**
     * <p>移动电话</p>
     */
    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    /**
     * <p>申请类型</p>
     */
    public String getAppType() {
        return appType;
    }

    /**
     * <p>申请类型</p>
     */
    public void setAppType(String appType) {
        this.appType = appType;
    }

    /**
     * <p>卡产品代码</p>
     */
    public String getProductCd() {
        return productCd;
    }

    /**
     * <p>卡产品代码</p>
     */
    public void setProductCd(String productCd) {
        this.productCd = productCd;
    }

    /**
     * <p>申请渠道</p>
     */
    public String getAppSource() {
        return appSource;
    }

    /**
     * <p>申请渠道</p>
     */
    public void setAppSource(String appSource) {
        this.appSource = appSource;
    }

    /**
     * <p>下一个审批状态</p>
     */
    public String getNextRtfState() {
        return nextRtfState;
    }

    /**
     * <p>下一个审批状态</p>
     */
    public void setNextRtfState(String nextRtfState) {
        this.nextRtfState = nextRtfState;
    }

    /**
     * <p>异常类型</p>
     */
    public String getExceType() {
        return exceType;
    }

    /**
     * <p>异常类型</p>
     */
    public void setExceType(String exceType) {
        this.exceType = exceType;
    }

    /**
     * <p>异常时审批状态</p>
     */
    public String getExceRtfState() {
        return exceRtfState;
    }

    /**
     * <p>异常时审批状态</p>
     */
    public void setExceRtfState(String exceRtfState) {
        this.exceRtfState = exceRtfState;
    }

    /**
     * <p>异常时任务名称</p>
     */
    public String getExceActTaskName() {
        return exceActTaskName;
    }

    /**
     * <p>异常时任务名称</p>
     */
    public void setExceActTaskName(String exceActTaskName) {
        this.exceActTaskName = exceActTaskName;
    }

    /**
     * <p>异常时任务编号</p>
     */
    public String getExceActTaskId() {
        return exceActTaskId;
    }

    /**
     * <p>异常时任务编号</p>
     */
    public void setExceActTaskId(String exceActTaskId) {
        this.exceActTaskId = exceActTaskId;
    }

    /**
     * <p>异常处理状态</p>
     */
    public String getExceStatus() {
        return exceStatus;
    }

    /**
     * <p>异常处理状态</p>
     */
    public void setExceStatus(String exceStatus) {
        this.exceStatus = exceStatus;
    }

    /**
     * <p>异常原因</p>
     */
    public String getExceReason() {
        return exceReason;
    }

    /**
     * <p>异常原因</p>
     */
    public void setExceReason(String exceReason) {
        this.exceReason = exceReason;
    }

    /**
     * <p>异常发生时间</p>
     */
    public Date getExceTime() {
        return exceTime;
    }

    /**
     * <p>异常发生时间</p>
     */
    public void setExceTime(Date exceTime) {
        this.exceTime = exceTime;
    }

    /**
     * <p>更新时间</p>
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * <p>更新时间</p>
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * <p>更新人</p>
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * <p>更新人</p>
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * <p>乐观锁</p>
     */
    public Integer getJpaVersion() {
        return jpaVersion;
    }

    /**
     * <p>乐观锁</p>
     */
    public void setJpaVersion(Integer jpaVersion) {
        this.jpaVersion = jpaVersion;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("appNo", appNo);
        map.put("name", name);
        map.put("idNo", idNo);
        map.put("cellphone", cellphone);
        map.put("appType", appType);
        map.put("productCd", productCd);
        map.put("appSource", appSource);
        map.put("nextRtfState", nextRtfState);
        map.put("exceType", exceType);
        map.put("exceRtfState", exceRtfState);
        map.put("exceActTaskName", exceActTaskName);
        map.put("exceActTaskId", exceActTaskId);
        map.put("exceStatus", exceStatus);
        map.put("exceReason", exceReason);
        map.put("exceTime", exceTime);
        map.put("updateTime", updateTime);
        map.put("updateUser", updateUser);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getLongValue(map.get("id")));
        if (map.containsKey("appNo")) this.setAppNo(DataTypeUtils.getStringValue(map.get("appNo")));
        if (map.containsKey("name")) this.setName(DataTypeUtils.getStringValue(map.get("name")));
        if (map.containsKey("idNo")) this.setIdNo(DataTypeUtils.getStringValue(map.get("idNo")));
        if (map.containsKey("cellphone")) this.setCellphone(DataTypeUtils.getStringValue(map.get("cellphone")));
        if (map.containsKey("appType")) this.setAppType(DataTypeUtils.getStringValue(map.get("appType")));
        if (map.containsKey("productCd")) this.setProductCd(DataTypeUtils.getStringValue(map.get("productCd")));
        if (map.containsKey("appSource")) this.setAppSource(DataTypeUtils.getStringValue(map.get("appSource")));
        if (map.containsKey("nextRtfState")) this.setNextRtfState(DataTypeUtils.getStringValue(map.get("nextRtfState")));
        if (map.containsKey("exceType")) this.setExceType(DataTypeUtils.getStringValue(map.get("exceType")));
        if (map.containsKey("exceRtfState")) this.setExceRtfState(DataTypeUtils.getStringValue(map.get("exceRtfState")));
        if (map.containsKey("exceActTaskName")) this.setExceActTaskName(DataTypeUtils.getStringValue(map.get("exceActTaskName")));
        if (map.containsKey("exceActTaskId")) this.setExceActTaskId(DataTypeUtils.getStringValue(map.get("exceActTaskId")));
        if (map.containsKey("exceStatus")) this.setExceStatus(DataTypeUtils.getStringValue(map.get("exceStatus")));
        if (map.containsKey("exceReason")) this.setExceReason(DataTypeUtils.getStringValue(map.get("exceReason")));
        if (map.containsKey("exceTime")) this.setExceTime(DataTypeUtils.getDateValue(map.get("exceTime")));
        if (map.containsKey("updateTime")) this.setUpdateTime(DataTypeUtils.getDateValue(map.get("updateTime")));
        if (map.containsKey("updateUser")) this.setUpdateUser(DataTypeUtils.getStringValue(map.get("updateUser")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", id="+id+
        "id="+id+
        ", appNo="+appNo+
        "appNo="+appNo+
        ", name="+name+
        "name="+name+
        ", idNo="+idNo+
        "idNo="+idNo+
        ", cellphone="+cellphone+
        "cellphone="+cellphone+
        ", appType="+appType+
        "appType="+appType+
        ", productCd="+productCd+
        "productCd="+productCd+
        ", appSource="+appSource+
        "appSource="+appSource+
        ", nextRtfState="+nextRtfState+
        "nextRtfState="+nextRtfState+
        ", exceType="+exceType+
        "exceType="+exceType+
        ", exceRtfState="+exceRtfState+
        "exceRtfState="+exceRtfState+
        ", exceActTaskName="+exceActTaskName+
        "exceActTaskName="+exceActTaskName+
        ", exceActTaskId="+exceActTaskId+
        "exceActTaskId="+exceActTaskId+
        ", exceStatus="+exceStatus+
        "exceStatus="+exceStatus+
        ", exceReason="+exceReason+
        "exceReason="+exceReason+
        ", exceTime="+exceTime+
        "exceTime="+exceTime+
        ", updateTime="+updateTime+
        "updateTime="+updateTime+
        ", updateUser="+updateUser+
        "updateUser="+updateUser+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (appNo == null) appNo = "";
        if (name == null) name = "";
        if (idNo == null) idNo = "";
        if (cellphone == null) cellphone = "";
        if (appType == null) appType = "";
        if (productCd == null) productCd = "";
        if (appSource == null) appSource = "";
        if (nextRtfState == null) nextRtfState = "";
        if (exceType == null) exceType = "";
        if (exceRtfState == null) exceRtfState = "";
        if (exceActTaskName == null) exceActTaskName = "";
        if (exceActTaskId == null) exceActTaskId = "";
        if (exceStatus == null) exceStatus = "";
        if (exceReason == null) exceReason = "";
        if (exceTime == null) exceTime = new Date();
        if (updateTime == null) updateTime = new Date();
        if (updateUser == null) updateUser = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}