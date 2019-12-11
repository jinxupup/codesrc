package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 核心卡信息处理异常表
 * @author jjb
 */
public class TmMirCardExce implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String appNo;

    private String idNo;

    private String name;

    private Date creatDate;

    private Date updateTime;

    private String remark;

    /**
     * <p>ID</p>
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>ID</p>
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
     * <p>创建日期</p>
     */
    public Date getCreatDate() {
        return creatDate;
    }

    /**
     * <p>创建日期</p>
     */
    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
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
     * <p>备注</p>
     */
    public String getRemark() {
        return remark;
    }

    /**
     * <p>备注</p>
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("appNo", appNo);
        map.put("idNo", idNo);
        map.put("name", name);
        map.put("creatDate", creatDate);
        map.put("updateTime", updateTime);
        map.put("remark", remark);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getLongValue(map.get("id")));
        if (map.containsKey("appNo")) this.setAppNo(DataTypeUtils.getStringValue(map.get("appNo")));
        if (map.containsKey("idNo")) this.setIdNo(DataTypeUtils.getStringValue(map.get("idNo")));
        if (map.containsKey("name")) this.setName(DataTypeUtils.getStringValue(map.get("name")));
        if (map.containsKey("creatDate")) this.setCreatDate(DataTypeUtils.getDateValue(map.get("creatDate")));
        if (map.containsKey("updateTime")) this.setUpdateTime(DataTypeUtils.getDateValue(map.get("updateTime")));
        if (map.containsKey("remark")) this.setRemark(DataTypeUtils.getStringValue(map.get("remark")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", id="+id+
        "id="+id+
        ", appNo="+appNo+
        "appNo="+appNo+
        ", idNo="+idNo+
        "idNo="+idNo+
        ", name="+name+
        "name="+name+
        ", creatDate="+creatDate+
        "creatDate="+creatDate+
        ", updateTime="+updateTime+
        "updateTime="+updateTime+
        ", remark="+remark+
        "remark="+remark+
        "]";
    }

    public void fillDefaultValues() {
        if (id == null) id = 0l;
        if (appNo == null) appNo = "";
        if (idNo == null) idNo = "";
        if (name == null) name = "";
        if (creatDate == null) creatDate = new Date();
        if (updateTime == null) updateTime = new Date();
        if (remark == null) remark = "";
    }
}