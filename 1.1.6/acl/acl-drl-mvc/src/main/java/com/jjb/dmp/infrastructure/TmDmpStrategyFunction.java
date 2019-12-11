package com.jjb.dmp.infrastructure;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jjb.unicorn.facility.util.DataTypeUtils;

/**
 * TM_DMP_STRATEGY_FUNCTION自定义函数
 * @author jjb
 */
public class TmDmpStrategyFunction implements Serializable {
    private static final long serialVersionUID = 1L;

    private String org;

    private String stClass;

    private String funCd;

    private String funName;

    private String funParam;

    private String dataType;

    private String funContent;

    private String remark;

    private String ifUsed;

    private Date createDate;

    private String createBy;

    private Date updateDate;

    private String updateBy;

    private Integer jpaVersion;

    /**
     * <p>ORG</p>
     */
    public String getOrg() {
        return org;
    }

    /**
     * <p>ORG</p>
     */
    public void setOrg(String org) {
        this.org = org;
    }

    /**
     * <p>策略方案JAVA类</p>
     */
    public String getStClass() {
        return stClass;
    }

    /**
     * <p>策略方案JAVA类</p>
     */
    public void setStClass(String stClass) {
        this.stClass = stClass;
    }

    /**
     * <p>函数代码</p>
     */
    public String getFunCd() {
        return funCd;
    }

    /**
     * <p>函数代码</p>
     */
    public void setFunCd(String funCd) {
        this.funCd = funCd;
    }

    /**
     * <p>函数名称</p>
     */
    public String getFunName() {
        return funName;
    }

    /**
     * <p>函数名称</p>
     */
    public void setFunName(String funName) {
        this.funName = funName;
    }

    /**
     * <p>函数参数</p>
     */
    public String getFunParam() {
        return funParam;
    }

    /**
     * <p>函数参数</p>
     */
    public void setFunParam(String funParam) {
        this.funParam = funParam;
    }

    /**
     * <p>返回数据类型</p>
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * <p>返回数据类型</p>
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * <p>函数主体</p>
     */
    public String getFunContent() {
        return funContent;
    }

    /**
     * <p>函数主体</p>
     */
    public void setFunContent(String funContent) {
        this.funContent = funContent;
    }

    /**
     * <p>说明</p>
     */
    public String getRemark() {
        return remark;
    }

    /**
     * <p>说明</p>
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * <p>是否启用</p>
     */
    public String getIfUsed() {
        return ifUsed;
    }

    /**
     * <p>是否启用</p>
     */
    public void setIfUsed(String ifUsed) {
        this.ifUsed = ifUsed;
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
     * <p>创建用户</p>
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * <p>创建用户</p>
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * <p>修改时间</p>
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * <p>修改时间</p>
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * <p>修改用户</p>
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * <p>修改用户</p>
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * <p>版本</p>
     */
    public Integer getJpaVersion() {
        return jpaVersion;
    }

    /**
     * <p>版本</p>
     */
    public void setJpaVersion(Integer jpaVersion) {
        this.jpaVersion = jpaVersion;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("org", org);
        map.put("stClass", stClass);
        map.put("funCd", funCd);
        map.put("funName", funName);
        map.put("funParam", funParam);
        map.put("dataType", dataType);
        map.put("funContent", funContent);
        map.put("remark", remark);
        map.put("ifUsed", ifUsed);
        map.put("createDate", createDate);
        map.put("createBy", createBy);
        map.put("updateDate", updateDate);
        map.put("updateBy", updateBy);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("stClass")) this.setStClass(DataTypeUtils.getStringValue(map.get("stClass")));
        if (map.containsKey("funCd")) this.setFunCd(DataTypeUtils.getStringValue(map.get("funCd")));
        if (map.containsKey("funName")) this.setFunName(DataTypeUtils.getStringValue(map.get("funName")));
        if (map.containsKey("funParam")) this.setFunParam(DataTypeUtils.getStringValue(map.get("funParam")));
        if (map.containsKey("dataType")) this.setDataType(DataTypeUtils.getStringValue(map.get("dataType")));
        if (map.containsKey("funContent")) this.setFunContent(DataTypeUtils.getStringValue(map.get("funContent")));
        if (map.containsKey("remark")) this.setRemark(DataTypeUtils.getStringValue(map.get("remark")));
        if (map.containsKey("ifUsed")) this.setIfUsed(DataTypeUtils.getStringValue(map.get("ifUsed")));
        if (map.containsKey("createDate")) this.setCreateDate(DataTypeUtils.getDateValue(map.get("createDate")));
        if (map.containsKey("createBy")) this.setCreateBy(DataTypeUtils.getStringValue(map.get("createBy")));
        if (map.containsKey("updateDate")) this.setUpdateDate(DataTypeUtils.getDateValue(map.get("updateDate")));
        if (map.containsKey("updateBy")) this.setUpdateBy(DataTypeUtils.getStringValue(map.get("updateBy")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        "org="+org+
        "stClass="+stClass+
        "funCd="+funCd+
        "funName="+funName+
        "funParam="+funParam+
        "dataType="+dataType+
        "funContent="+funContent+
        "remark="+remark+
        "ifUsed="+ifUsed+
        "createDate="+createDate+
        "createBy="+createBy+
        "updateDate="+updateDate+
        "updateBy="+updateBy+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (stClass == null) stClass = "";
        if (funCd == null) funCd = "";
        if (funName == null) funName = "";
        if (funParam == null) funParam = "";
        if (dataType == null) dataType = "";
        if (funContent == null) funContent = "";
        if (remark == null) remark = "";
        if (ifUsed == null) ifUsed = "";
        if (createDate == null) createDate = new Date();
        if (createBy == null) createBy = "";
        if (updateDate == null) updateDate = new Date();
        if (updateBy == null) updateBy = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}