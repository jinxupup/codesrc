package com.jjb.dmp.infrastructure;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jjb.unicorn.facility.util.DataTypeUtils;

/**
 * TM_DMP_STRATEGY_VAR策略类别变量
 * @author jjb
 */
public class TmDmpStrategyVar implements Serializable {
    private static final long serialVersionUID = 1L;

    private String org;

    private String stClass;

    private String varType;

    private String varCd;

    private String varName;

    private String isInput;

    private String isOutput;

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
     * <p>变量类型</p>
     */
    public String getVarType() {
        return varType;
    }

    /**
     * <p>变量类型</p>
     */
    public void setVarType(String varType) {
        this.varType = varType;
    }

    /**
     * <p>变量代码</p>
     */
    public String getVarCd() {
        return varCd;
    }

    /**
     * <p>变量代码</p>
     */
    public void setVarCd(String varCd) {
        this.varCd = varCd;
    }

    /**
     * <p>变量名称</p>
     */
    public String getVarName() {
        return varName;
    }

    /**
     * <p>变量名称</p>
     */
    public void setVarName(String varName) {
        this.varName = varName;
    }

    /**
     * <p>输入变量</p>
     */
    public String getIsInput() {
        return isInput;
    }

    /**
     * <p>输入变量</p>
     */
    public void setIsInput(String isInput) {
        this.isInput = isInput;
    }

    /**
     * <p>输出变量</p>
     */
    public String getIsOutput() {
        return isOutput;
    }

    /**
     * <p>输出变量</p>
     */
    public void setIsOutput(String isOutput) {
        this.isOutput = isOutput;
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
        map.put("varType", varType);
        map.put("varCd", varCd);
        map.put("varName", varName);
        map.put("isInput", isInput);
        map.put("isOutput", isOutput);
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
        if (map.containsKey("varType")) this.setVarType(DataTypeUtils.getStringValue(map.get("varType")));
        if (map.containsKey("varCd")) this.setVarCd(DataTypeUtils.getStringValue(map.get("varCd")));
        if (map.containsKey("varName")) this.setVarName(DataTypeUtils.getStringValue(map.get("varName")));
        if (map.containsKey("isInput")) this.setIsInput(DataTypeUtils.getStringValue(map.get("isInput")));
        if (map.containsKey("isOutput")) this.setIsOutput(DataTypeUtils.getStringValue(map.get("isOutput")));
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
        "varType="+varType+
        "varCd="+varCd+
        "varName="+varName+
        "isInput="+isInput+
        "isOutput="+isOutput+
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
        if (varType == null) varType = "";
        if (varCd == null) varCd = "";
        if (varName == null) varName = "";
        if (isInput == null) isInput = "";
        if (isOutput == null) isOutput = "";
        if (remark == null) remark = "";
        if (ifUsed == null) ifUsed = "";
        if (createDate == null) createDate = new Date();
        if (createBy == null) createBy = "";
        if (updateDate == null) updateDate = new Date();
        if (updateBy == null) updateBy = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}