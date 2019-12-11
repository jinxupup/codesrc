package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 多卡同申记录表
 * @author jjb
 */
public class TmAppOrderMain implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String org;

    private String appNo;

    private String idNo;

    private String allProductCds;

    private String validProductCds;

    private String validProductType;

    private String timerState;

    private String reqJson;

    private String exceptionMsg;

    private Date createDate;

    private Date updateDate;

    private Integer jpaVersion;

    /**
     * <p>标志</p>
     * <p>主键自增</p>
     */
    public Integer getId() {
        return id;
    }

    /**
     * <p>标志</p>
     * <p>主键自增</p>
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
     * <p>身份证号码</p>
     */
    public String getIdNo() {
        return idNo;
    }

    /**
     * <p>身份证号码</p>
     */
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    /**
     * <p>所有的产品编号</p>
     */
    public String getAllProductCds() {
        return allProductCds;
    }

    /**
     * <p>所有的产品编号</p>
     */
    public void setAllProductCds(String allProductCds) {
        this.allProductCds = allProductCds;
    }

    /**
     * <p>有效的产品编号</p>
     */
    public String getValidProductCds() {
        return validProductCds;
    }

    /**
     * <p>有效的产品编号</p>
     */
    public void setValidProductCds(String validProductCds) {
        this.validProductCds = validProductCds;
    }

    /**
     * <p>有效产品状态</p>
     */
    public String getValidProductType() {
        return validProductType;
    }

    /**
     * <p>有效产品状态</p>
     */
    public void setValidProductType(String validProductType) {
        this.validProductType = validProductType;
    }

    /**
     * <p>定时器状态</p>
     */
    public String getTimerState() {
        return timerState;
    }

    /**
     * <p>定时器状态</p>
     */
    public void setTimerState(String timerState) {
        this.timerState = timerState;
    }

    /**
     * <p>进件报文</p>
     */
    public String getReqJson() {
        return reqJson;
    }

    /**
     * <p>进件报文</p>
     */
    public void setReqJson(String reqJson) {
        this.reqJson = reqJson;
    }

    /**
     * <p>异常信息</p>
     */
    public String getExceptionMsg() {
        return exceptionMsg;
    }

    /**
     * <p>异常信息</p>
     */
    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    /**
     * <p>创建时间</p>
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * <p>创建时间</p>
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * <p>最后修改时间</p>
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * <p>最后修改时间</p>
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("org", org);
        map.put("appNo", appNo);
        map.put("idNo", idNo);
        map.put("allProductCds", allProductCds);
        map.put("validProductCds", validProductCds);
        map.put("validProductType", validProductType);
        map.put("timerState", timerState);
        map.put("reqJson", reqJson);
        map.put("exceptionMsg", exceptionMsg);
        map.put("createDate", createDate);
        map.put("updateDate", updateDate);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("appNo")) this.setAppNo(DataTypeUtils.getStringValue(map.get("appNo")));
        if (map.containsKey("idNo")) this.setIdNo(DataTypeUtils.getStringValue(map.get("idNo")));
        if (map.containsKey("allProductCds")) this.setAllProductCds(DataTypeUtils.getStringValue(map.get("allProductCds")));
        if (map.containsKey("validProductCds")) this.setValidProductCds(DataTypeUtils.getStringValue(map.get("validProductCds")));
        if (map.containsKey("validProductType")) this.setValidProductType(DataTypeUtils.getStringValue(map.get("validProductType")));
        if (map.containsKey("timerState")) this.setTimerState(DataTypeUtils.getStringValue(map.get("timerState")));
        if (map.containsKey("reqJson")) this.setReqJson(DataTypeUtils.getStringValue(map.get("reqJson")));
        if (map.containsKey("exceptionMsg")) this.setExceptionMsg(DataTypeUtils.getStringValue(map.get("exceptionMsg")));
        if (map.containsKey("createDate")) this.setCreateDate(DataTypeUtils.getDateValue(map.get("createDate")));
        if (map.containsKey("updateDate")) this.setUpdateDate(DataTypeUtils.getDateValue(map.get("updateDate")));
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
        ", idNo="+idNo+
        "idNo="+idNo+
        ", allProductCds="+allProductCds+
        "allProductCds="+allProductCds+
        ", validProductCds="+validProductCds+
        "validProductCds="+validProductCds+
        ", validProductType="+validProductType+
        "validProductType="+validProductType+
        ", timerState="+timerState+
        "timerState="+timerState+
        ", reqJson="+reqJson+
        "reqJson="+reqJson+
        ", exceptionMsg="+exceptionMsg+
        "exceptionMsg="+exceptionMsg+
        ", createDate="+createDate+
        "createDate="+createDate+
        ", updateDate="+updateDate+
        "updateDate="+updateDate+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (appNo == null) appNo = "";
        if (idNo == null) idNo = "";
        if (allProductCds == null) allProductCds = "";
        if (validProductCds == null) validProductCds = "";
        if (validProductType == null) validProductType = "";
        if (timerState == null) timerState = "";
        if (reqJson == null) reqJson = "";
        if (exceptionMsg == null) exceptionMsg = "";
        if (createDate == null) createDate = new Date();
        if (updateDate == null) updateDate = new Date();
        if (jpaVersion == null) jpaVersion = 0;
    }
}