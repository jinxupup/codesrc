package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 节点信息记录表
 * @author jjb
 */
public class TmAppNodeInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String org;

    private String appNo;

    private String proNum;

    private String proName;

    private String infoType;

    private String content;

    private String resultCode;

    private String remark;

    private String memo;

    private String operatorId;

    private Date setupDate;

    private Integer jpaVersion;

    /**
     * <p>标识</p>
     */
    public Integer getId() {
        return id;
    }

    /**
     * <p>标识</p>
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * <p>机构号</p>
     */
    public String getOrg() {
        return org;
    }

    /**
     * <p>机构号</p>
     */
    public void setOrg(String org) {
        this.org = org;
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
     * <p>流程实例号</p>
     */
    public String getProNum() {
        return proNum;
    }

    /**
     * <p>流程实例号</p>
     */
    public void setProNum(String proNum) {
        this.proNum = proNum;
    }

    /**
     * <p>流程节点名称</p>
     */
    public String getProName() {
        return proName;
    }

    /**
     * <p>流程节点名称</p>
     */
    public void setProName(String proName) {
        this.proName = proName;
    }

    /**
     * <p>信息类别</p>
     */
    public String getInfoType() {
        return infoType;
    }

    /**
     * <p>信息类别</p>
     */
    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    /**
     * <p>信息内容</p>
     */
    public String getContent() {
        return content;
    }

    /**
     * <p>信息内容</p>
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * <p>处理结果号</p>
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * <p>处理结果号</p>
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * <p>备注</p>
     * <p>备注</p>
     */
    public String getRemark() {
        return remark;
    }

    /**
     * <p>备注</p>
     * <p>备注</p>
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * <p>备忘</p>
     */
    public String getMemo() {
        return memo;
    }

    /**
     * <p>备忘</p>
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * <p>操作员ID</p>
     */
    public String getOperatorId() {
        return operatorId;
    }

    /**
     * <p>操作员ID</p>
     */
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * <p>处理日期</p>
     */
    public Date getSetupDate() {
        return setupDate;
    }

    /**
     * <p>处理日期</p>
     */
    public void setSetupDate(Date setupDate) {
        this.setupDate = setupDate;
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
        map.put("org", org);
        map.put("appNo", appNo);
        map.put("proNum", proNum);
        map.put("proName", proName);
        map.put("infoType", infoType);
        map.put("content", content);
        map.put("resultCode", resultCode);
        map.put("remark", remark);
        map.put("memo", memo);
        map.put("operatorId", operatorId);
        map.put("setupDate", setupDate);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("appNo")) this.setAppNo(DataTypeUtils.getStringValue(map.get("appNo")));
        if (map.containsKey("proNum")) this.setProNum(DataTypeUtils.getStringValue(map.get("proNum")));
        if (map.containsKey("proName")) this.setProName(DataTypeUtils.getStringValue(map.get("proName")));
        if (map.containsKey("infoType")) this.setInfoType(DataTypeUtils.getStringValue(map.get("infoType")));
        if (map.containsKey("content")) this.setContent(DataTypeUtils.getStringValue(map.get("content")));
        if (map.containsKey("resultCode")) this.setResultCode(DataTypeUtils.getStringValue(map.get("resultCode")));
        if (map.containsKey("remark")) this.setRemark(DataTypeUtils.getStringValue(map.get("remark")));
        if (map.containsKey("memo")) this.setMemo(DataTypeUtils.getStringValue(map.get("memo")));
        if (map.containsKey("operatorId")) this.setOperatorId(DataTypeUtils.getStringValue(map.get("operatorId")));
        if (map.containsKey("setupDate")) this.setSetupDate(DataTypeUtils.getDateValue(map.get("setupDate")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", id="+id+
        "id="+id+
        ", org="+org+
        "org="+org+
        ", appNo="+appNo+
        "appNo="+appNo+
        ", proNum="+proNum+
        "proNum="+proNum+
        ", proName="+proName+
        "proName="+proName+
        ", infoType="+infoType+
        "infoType="+infoType+
        ", content="+content+
        "content="+content+
        ", resultCode="+resultCode+
        "resultCode="+resultCode+
        ", remark="+remark+
        "remark="+remark+
        ", memo="+memo+
        "memo="+memo+
        ", operatorId="+operatorId+
        "operatorId="+operatorId+
        ", setupDate="+setupDate+
        "setupDate="+setupDate+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (appNo == null) appNo = "";
        if (proNum == null) proNum = "";
        if (proName == null) proName = "";
        if (infoType == null) infoType = "";
        if (content == null) content = "";
        if (resultCode == null) resultCode = "";
        if (remark == null) remark = "";
        if (memo == null) memo = "";
        if (operatorId == null) operatorId = "";
        if (setupDate == null) setupDate = new Date();
        if (jpaVersion == null) jpaVersion = 0;
    }
}