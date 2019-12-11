package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 待补件信息表
 * @author jjb
 */
public class TmAppRfe implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String org;

    private String appNo;

    private String appType;

    private String name;

    private String idType;

    private String idNo;

    private String cellphone;

    private String spreaderBank;

    private String spreaderName;

    private String spreaderNum;

    private String pbSource;

    private String pbType;

    private Date pbStartDate;

    private Date pbStBatchDate;

    private Date pbTimeoutDate;

    private Date pbOutBatchDate;

    private String pbStartTime;

    private String pbTimeWait;

    private Integer jpaVersion;

    private String isOk;

    private String operatorId;

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
     * <p>证件类型</p>
     */
    public String getIdType() {
        return idType;
    }

    /**
     * <p>证件类型</p>
     */
    public void setIdType(String idType) {
        this.idType = idType;
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
     * <p>推广人所属分行</p>
     */
    public String getSpreaderBank() {
        return spreaderBank;
    }

    /**
     * <p>推广人所属分行</p>
     */
    public void setSpreaderBank(String spreaderBank) {
        this.spreaderBank = spreaderBank;
    }

    /**
     * <p>推荐人姓名</p>
     */
    public String getSpreaderName() {
        return spreaderName;
    }

    /**
     * <p>推荐人姓名</p>
     */
    public void setSpreaderName(String spreaderName) {
        this.spreaderName = spreaderName;
    }

    /**
     * <p>推荐人编号</p>
     */
    public String getSpreaderNum() {
        return spreaderNum;
    }

    /**
     * <p>推荐人编号</p>
     */
    public void setSpreaderNum(String spreaderNum) {
        this.spreaderNum = spreaderNum;
    }

    /**
     * <p>补件渠道</p>
     * <p>补件渠道</p>
     */
    public String getPbSource() {
        return pbSource;
    }

    /**
     * <p>补件渠道</p>
     * <p>补件渠道</p>
     */
    public void setPbSource(String pbSource) {
        this.pbSource = pbSource;
    }

    /**
     * <p>补件类型</p>
     */
    public String getPbType() {
        return pbType;
    }

    /**
     * <p>补件类型</p>
     */
    public void setPbType(String pbType) {
        this.pbType = pbType;
    }

    /**
     * <p>补件开始时间</p>
     */
    public Date getPbStartDate() {
        return pbStartDate;
    }

    /**
     * <p>补件开始时间</p>
     */
    public void setPbStartDate(Date pbStartDate) {
        this.pbStartDate = pbStartDate;
    }

    /**
     * <p>补件开始业务时间</p>
     */
    public Date getPbStBatchDate() {
        return pbStBatchDate;
    }

    /**
     * <p>补件开始业务时间</p>
     */
    public void setPbStBatchDate(Date pbStBatchDate) {
        this.pbStBatchDate = pbStBatchDate;
    }

    /**
     * <p>补件超时时间</p>
     */
    public Date getPbTimeoutDate() {
        return pbTimeoutDate;
    }

    /**
     * <p>补件超时时间</p>
     */
    public void setPbTimeoutDate(Date pbTimeoutDate) {
        this.pbTimeoutDate = pbTimeoutDate;
    }

    /**
     * <p>补件超时业务时间</p>
     */
    public Date getPbOutBatchDate() {
        return pbOutBatchDate;
    }

    /**
     * <p>补件超时业务时间</p>
     */
    public void setPbOutBatchDate(Date pbOutBatchDate) {
        this.pbOutBatchDate = pbOutBatchDate;
    }

    /**
     * <p>补件开始时间点</p>
     */
    public String getPbStartTime() {
        return pbStartTime;
    }

    /**
     * <p>补件开始时间点</p>
     */
    public void setPbStartTime(String pbStartTime) {
        this.pbStartTime = pbStartTime;
    }

    /**
     * <p>补件间隔天数</p>
     */
    public String getPbTimeWait() {
        return pbTimeWait;
    }

    /**
     * <p>补件间隔天数</p>
     */
    public void setPbTimeWait(String pbTimeWait) {
        this.pbTimeWait = pbTimeWait;
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

    /**
     * <p>是否补件完成</p>
     */
    public String getIsOk() {
        return isOk;
    }

    /**
     * <p>是否补件完成</p>
     */
    public void setIsOk(String isOk) {
        this.isOk = isOk;
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

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("org", org);
        map.put("appNo", appNo);
        map.put("appType", appType);
        map.put("name", name);
        map.put("idType", idType);
        map.put("idNo", idNo);
        map.put("cellphone", cellphone);
        map.put("spreaderBank", spreaderBank);
        map.put("spreaderName", spreaderName);
        map.put("spreaderNum", spreaderNum);
        map.put("pbSource", pbSource);
        map.put("pbType", pbType);
        map.put("pbStartDate", pbStartDate);
        map.put("pbStBatchDate", pbStBatchDate);
        map.put("pbTimeoutDate", pbTimeoutDate);
        map.put("pbOutBatchDate", pbOutBatchDate);
        map.put("pbStartTime", pbStartTime);
        map.put("pbTimeWait", pbTimeWait);
        map.put("jpaVersion", jpaVersion);
        map.put("isOk", isOk);
        map.put("operatorId", operatorId);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("appNo")) this.setAppNo(DataTypeUtils.getStringValue(map.get("appNo")));
        if (map.containsKey("appType")) this.setAppType(DataTypeUtils.getStringValue(map.get("appType")));
        if (map.containsKey("name")) this.setName(DataTypeUtils.getStringValue(map.get("name")));
        if (map.containsKey("idType")) this.setIdType(DataTypeUtils.getStringValue(map.get("idType")));
        if (map.containsKey("idNo")) this.setIdNo(DataTypeUtils.getStringValue(map.get("idNo")));
        if (map.containsKey("cellphone")) this.setCellphone(DataTypeUtils.getStringValue(map.get("cellphone")));
        if (map.containsKey("spreaderBank")) this.setSpreaderBank(DataTypeUtils.getStringValue(map.get("spreaderBank")));
        if (map.containsKey("spreaderName")) this.setSpreaderName(DataTypeUtils.getStringValue(map.get("spreaderName")));
        if (map.containsKey("spreaderNum")) this.setSpreaderNum(DataTypeUtils.getStringValue(map.get("spreaderNum")));
        if (map.containsKey("pbSource")) this.setPbSource(DataTypeUtils.getStringValue(map.get("pbSource")));
        if (map.containsKey("pbType")) this.setPbType(DataTypeUtils.getStringValue(map.get("pbType")));
        if (map.containsKey("pbStartDate")) this.setPbStartDate(DataTypeUtils.getDateValue(map.get("pbStartDate")));
        if (map.containsKey("pbStBatchDate")) this.setPbStBatchDate(DataTypeUtils.getDateValue(map.get("pbStBatchDate")));
        if (map.containsKey("pbTimeoutDate")) this.setPbTimeoutDate(DataTypeUtils.getDateValue(map.get("pbTimeoutDate")));
        if (map.containsKey("pbOutBatchDate")) this.setPbOutBatchDate(DataTypeUtils.getDateValue(map.get("pbOutBatchDate")));
        if (map.containsKey("pbStartTime")) this.setPbStartTime(DataTypeUtils.getStringValue(map.get("pbStartTime")));
        if (map.containsKey("pbTimeWait")) this.setPbTimeWait(DataTypeUtils.getStringValue(map.get("pbTimeWait")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
        if (map.containsKey("isOk")) this.setIsOk(DataTypeUtils.getStringValue(map.get("isOk")));
        if (map.containsKey("operatorId")) this.setOperatorId(DataTypeUtils.getStringValue(map.get("operatorId")));
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
        ", appType="+appType+
        "appType="+appType+
        ", name="+name+
        "name="+name+
        ", idType="+idType+
        "idType="+idType+
        ", idNo="+idNo+
        "idNo="+idNo+
        ", cellphone="+cellphone+
        "cellphone="+cellphone+
        ", spreaderBank="+spreaderBank+
        "spreaderBank="+spreaderBank+
        ", spreaderName="+spreaderName+
        "spreaderName="+spreaderName+
        ", spreaderNum="+spreaderNum+
        "spreaderNum="+spreaderNum+
        ", pbSource="+pbSource+
        "pbSource="+pbSource+
        ", pbType="+pbType+
        "pbType="+pbType+
        ", pbStartDate="+pbStartDate+
        "pbStartDate="+pbStartDate+
        ", pbStBatchDate="+pbStBatchDate+
        "pbStBatchDate="+pbStBatchDate+
        ", pbTimeoutDate="+pbTimeoutDate+
        "pbTimeoutDate="+pbTimeoutDate+
        ", pbOutBatchDate="+pbOutBatchDate+
        "pbOutBatchDate="+pbOutBatchDate+
        ", pbStartTime="+pbStartTime+
        "pbStartTime="+pbStartTime+
        ", pbTimeWait="+pbTimeWait+
        "pbTimeWait="+pbTimeWait+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        ", isOk="+isOk+
        "isOk="+isOk+
        ", operatorId="+operatorId+
        "operatorId="+operatorId+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (appNo == null) appNo = "";
        if (appType == null) appType = "";
        if (name == null) name = "";
        if (idType == null) idType = "";
        if (idNo == null) idNo = "";
        if (cellphone == null) cellphone = "";
        if (spreaderBank == null) spreaderBank = "";
        if (spreaderName == null) spreaderName = "";
        if (spreaderNum == null) spreaderNum = "";
        if (pbSource == null) pbSource = "";
        if (pbType == null) pbType = "";
        if (pbStartDate == null) pbStartDate = new Date();
        if (pbStBatchDate == null) pbStBatchDate = new Date();
        if (pbTimeoutDate == null) pbTimeoutDate = new Date();
        if (pbOutBatchDate == null) pbOutBatchDate = new Date();
        if (pbStartTime == null) pbStartTime = "";
        if (pbTimeWait == null) pbTimeWait = "";
        if (jpaVersion == null) jpaVersion = 0;
        if (isOk == null) isOk = "";
        if (operatorId == null) operatorId = "";
    }
}