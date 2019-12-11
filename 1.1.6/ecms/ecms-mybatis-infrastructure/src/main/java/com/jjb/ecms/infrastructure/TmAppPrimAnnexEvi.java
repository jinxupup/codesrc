package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 申请附件证明信息
 * @author jjb
 */
public class TmAppPrimAnnexEvi implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String org;

    private String appNo;

    private BigDecimal eviIncome;

    private String indSignFile;

    private String indIdFile;

    private String indJobFile;

    private String indLiveFile;

    private String indLiveAddr;

    private BigDecimal estatesNoInstallAmt;

    private BigDecimal estatesInstallAmt;

    private BigDecimal estatesInstallLoanAmt;

    private String buildingName;

    private BigDecimal houseMonthyAmt;

    private String otherCcBank1;

    private BigDecimal otherCcLmt1;

    private String otherCcBank2;

    private BigDecimal otherCcLmt2;

    private String carIdNum;

    private String cardEmissions;

    private String carModel;

    private BigDecimal cardPrice;

    private Date createDate;

    private String createUser;

    private Date updateDate;

    private String updateUser;

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
     * <p>核实个人年收入</p>
     */
    public BigDecimal getEviIncome() {
        return eviIncome;
    }

    /**
     * <p>核实个人年收入</p>
     */
    public void setEviIncome(BigDecimal eviIncome) {
        this.eviIncome = eviIncome;
    }

    /**
     * <p>签名状况</p>
     */
    public String getIndSignFile() {
        return indSignFile;
    }

    /**
     * <p>签名状况</p>
     */
    public void setIndSignFile(String indSignFile) {
        this.indSignFile = indSignFile;
    }

    /**
     * <p>身份证明文件状况</p>
     */
    public String getIndIdFile() {
        return indIdFile;
    }

    /**
     * <p>身份证明文件状况</p>
     */
    public void setIndIdFile(String indIdFile) {
        this.indIdFile = indIdFile;
    }

    /**
     * <p>工作证明状况</p>
     */
    public String getIndJobFile() {
        return indJobFile;
    }

    /**
     * <p>工作证明状况</p>
     */
    public void setIndJobFile(String indJobFile) {
        this.indJobFile = indJobFile;
    }

    /**
     * <p>居住证明状况</p>
     */
    public String getIndLiveFile() {
        return indLiveFile;
    }

    /**
     * <p>居住证明状况</p>
     */
    public void setIndLiveFile(String indLiveFile) {
        this.indLiveFile = indLiveFile;
    }

    /**
     * <p>地址是否一致</p>
     */
    public String getIndLiveAddr() {
        return indLiveAddr;
    }

    /**
     * <p>地址是否一致</p>
     */
    public void setIndLiveAddr(String indLiveAddr) {
        this.indLiveAddr = indLiveAddr;
    }

    /**
     * <p>房产无按揭总价</p>
     */
    public BigDecimal getEstatesNoInstallAmt() {
        return estatesNoInstallAmt;
    }

    /**
     * <p>房产无按揭总价</p>
     */
    public void setEstatesNoInstallAmt(BigDecimal estatesNoInstallAmt) {
        this.estatesNoInstallAmt = estatesNoInstallAmt;
    }

    /**
     * <p>房产按揭总价</p>
     */
    public BigDecimal getEstatesInstallAmt() {
        return estatesInstallAmt;
    }

    /**
     * <p>房产按揭总价</p>
     */
    public void setEstatesInstallAmt(BigDecimal estatesInstallAmt) {
        this.estatesInstallAmt = estatesInstallAmt;
    }

    /**
     * <p>房产按揭贷款额度</p>
     */
    public BigDecimal getEstatesInstallLoanAmt() {
        return estatesInstallLoanAmt;
    }

    /**
     * <p>房产按揭贷款额度</p>
     */
    public void setEstatesInstallLoanAmt(BigDecimal estatesInstallLoanAmt) {
        this.estatesInstallLoanAmt = estatesInstallLoanAmt;
    }

    /**
     * <p>楼盘名称</p>
     */
    public String getBuildingName() {
        return buildingName;
    }

    /**
     * <p>楼盘名称</p>
     */
    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    /**
     * <p>房贷月还款额</p>
     */
    public BigDecimal getHouseMonthyAmt() {
        return houseMonthyAmt;
    }

    /**
     * <p>房贷月还款额</p>
     */
    public void setHouseMonthyAmt(BigDecimal houseMonthyAmt) {
        this.houseMonthyAmt = houseMonthyAmt;
    }

    /**
     * <p>已有卡发卡行1</p>
     */
    public String getOtherCcBank1() {
        return otherCcBank1;
    }

    /**
     * <p>已有卡发卡行1</p>
     */
    public void setOtherCcBank1(String otherCcBank1) {
        this.otherCcBank1 = otherCcBank1;
    }

    /**
     * <p>已有卡额度1</p>
     */
    public BigDecimal getOtherCcLmt1() {
        return otherCcLmt1;
    }

    /**
     * <p>已有卡额度1</p>
     */
    public void setOtherCcLmt1(BigDecimal otherCcLmt1) {
        this.otherCcLmt1 = otherCcLmt1;
    }

    /**
     * <p>已有卡发卡行2</p>
     */
    public String getOtherCcBank2() {
        return otherCcBank2;
    }

    /**
     * <p>已有卡发卡行2</p>
     */
    public void setOtherCcBank2(String otherCcBank2) {
        this.otherCcBank2 = otherCcBank2;
    }

    /**
     * <p>已有卡额度2</p>
     */
    public BigDecimal getOtherCcLmt2() {
        return otherCcLmt2;
    }

    /**
     * <p>已有卡额度2</p>
     */
    public void setOtherCcLmt2(BigDecimal otherCcLmt2) {
        this.otherCcLmt2 = otherCcLmt2;
    }

    /**
     * <p>车辆识别号</p>
     */
    public String getCarIdNum() {
        return carIdNum;
    }

    /**
     * <p>车辆识别号</p>
     */
    public void setCarIdNum(String carIdNum) {
        this.carIdNum = carIdNum;
    }

    /**
     * <p>车辆排量</p>
     */
    public String getCardEmissions() {
        return cardEmissions;
    }

    /**
     * <p>车辆排量</p>
     */
    public void setCardEmissions(String cardEmissions) {
        this.cardEmissions = cardEmissions;
    }

    /**
     * <p>车辆型号</p>
     */
    public String getCarModel() {
        return carModel;
    }

    /**
     * <p>车辆型号</p>
     */
    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    /**
     * <p>车辆总价</p>
     */
    public BigDecimal getCardPrice() {
        return cardPrice;
    }

    /**
     * <p>车辆总价</p>
     */
    public void setCardPrice(BigDecimal cardPrice) {
        this.cardPrice = cardPrice;
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
     * <p>创建人</p>
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * <p>创建人</p>
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * <p>更新时间</p>
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * <p>更新时间</p>
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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
        map.put("org", org);
        map.put("appNo", appNo);
        map.put("eviIncome", eviIncome);
        map.put("indSignFile", indSignFile);
        map.put("indIdFile", indIdFile);
        map.put("indJobFile", indJobFile);
        map.put("indLiveFile", indLiveFile);
        map.put("indLiveAddr", indLiveAddr);
        map.put("estatesNoInstallAmt", estatesNoInstallAmt);
        map.put("estatesInstallAmt", estatesInstallAmt);
        map.put("estatesInstallLoanAmt", estatesInstallLoanAmt);
        map.put("buildingName", buildingName);
        map.put("houseMonthyAmt", houseMonthyAmt);
        map.put("otherCcBank1", otherCcBank1);
        map.put("otherCcLmt1", otherCcLmt1);
        map.put("otherCcBank2", otherCcBank2);
        map.put("otherCcLmt2", otherCcLmt2);
        map.put("carIdNum", carIdNum);
        map.put("cardEmissions", cardEmissions);
        map.put("carModel", carModel);
        map.put("cardPrice", cardPrice);
        map.put("createDate", createDate);
        map.put("createUser", createUser);
        map.put("updateDate", updateDate);
        map.put("updateUser", updateUser);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("appNo")) this.setAppNo(DataTypeUtils.getStringValue(map.get("appNo")));
        if (map.containsKey("eviIncome")) this.setEviIncome(DataTypeUtils.getBigDecimalValue(map.get("eviIncome")));
        if (map.containsKey("indSignFile")) this.setIndSignFile(DataTypeUtils.getStringValue(map.get("indSignFile")));
        if (map.containsKey("indIdFile")) this.setIndIdFile(DataTypeUtils.getStringValue(map.get("indIdFile")));
        if (map.containsKey("indJobFile")) this.setIndJobFile(DataTypeUtils.getStringValue(map.get("indJobFile")));
        if (map.containsKey("indLiveFile")) this.setIndLiveFile(DataTypeUtils.getStringValue(map.get("indLiveFile")));
        if (map.containsKey("indLiveAddr")) this.setIndLiveAddr(DataTypeUtils.getStringValue(map.get("indLiveAddr")));
        if (map.containsKey("estatesNoInstallAmt")) this.setEstatesNoInstallAmt(DataTypeUtils.getBigDecimalValue(map.get("estatesNoInstallAmt")));
        if (map.containsKey("estatesInstallAmt")) this.setEstatesInstallAmt(DataTypeUtils.getBigDecimalValue(map.get("estatesInstallAmt")));
        if (map.containsKey("estatesInstallLoanAmt")) this.setEstatesInstallLoanAmt(DataTypeUtils.getBigDecimalValue(map.get("estatesInstallLoanAmt")));
        if (map.containsKey("buildingName")) this.setBuildingName(DataTypeUtils.getStringValue(map.get("buildingName")));
        if (map.containsKey("houseMonthyAmt")) this.setHouseMonthyAmt(DataTypeUtils.getBigDecimalValue(map.get("houseMonthyAmt")));
        if (map.containsKey("otherCcBank1")) this.setOtherCcBank1(DataTypeUtils.getStringValue(map.get("otherCcBank1")));
        if (map.containsKey("otherCcLmt1")) this.setOtherCcLmt1(DataTypeUtils.getBigDecimalValue(map.get("otherCcLmt1")));
        if (map.containsKey("otherCcBank2")) this.setOtherCcBank2(DataTypeUtils.getStringValue(map.get("otherCcBank2")));
        if (map.containsKey("otherCcLmt2")) this.setOtherCcLmt2(DataTypeUtils.getBigDecimalValue(map.get("otherCcLmt2")));
        if (map.containsKey("carIdNum")) this.setCarIdNum(DataTypeUtils.getStringValue(map.get("carIdNum")));
        if (map.containsKey("cardEmissions")) this.setCardEmissions(DataTypeUtils.getStringValue(map.get("cardEmissions")));
        if (map.containsKey("carModel")) this.setCarModel(DataTypeUtils.getStringValue(map.get("carModel")));
        if (map.containsKey("cardPrice")) this.setCardPrice(DataTypeUtils.getBigDecimalValue(map.get("cardPrice")));
        if (map.containsKey("createDate")) this.setCreateDate(DataTypeUtils.getDateValue(map.get("createDate")));
        if (map.containsKey("createUser")) this.setCreateUser(DataTypeUtils.getStringValue(map.get("createUser")));
        if (map.containsKey("updateDate")) this.setUpdateDate(DataTypeUtils.getDateValue(map.get("updateDate")));
        if (map.containsKey("updateUser")) this.setUpdateUser(DataTypeUtils.getStringValue(map.get("updateUser")));
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
        ", eviIncome="+eviIncome+
        "eviIncome="+eviIncome+
        ", indSignFile="+indSignFile+
        "indSignFile="+indSignFile+
        ", indIdFile="+indIdFile+
        "indIdFile="+indIdFile+
        ", indJobFile="+indJobFile+
        "indJobFile="+indJobFile+
        ", indLiveFile="+indLiveFile+
        "indLiveFile="+indLiveFile+
        ", indLiveAddr="+indLiveAddr+
        "indLiveAddr="+indLiveAddr+
        ", estatesNoInstallAmt="+estatesNoInstallAmt+
        "estatesNoInstallAmt="+estatesNoInstallAmt+
        ", estatesInstallAmt="+estatesInstallAmt+
        "estatesInstallAmt="+estatesInstallAmt+
        ", estatesInstallLoanAmt="+estatesInstallLoanAmt+
        "estatesInstallLoanAmt="+estatesInstallLoanAmt+
        ", buildingName="+buildingName+
        "buildingName="+buildingName+
        ", houseMonthyAmt="+houseMonthyAmt+
        "houseMonthyAmt="+houseMonthyAmt+
        ", otherCcBank1="+otherCcBank1+
        "otherCcBank1="+otherCcBank1+
        ", otherCcLmt1="+otherCcLmt1+
        "otherCcLmt1="+otherCcLmt1+
        ", otherCcBank2="+otherCcBank2+
        "otherCcBank2="+otherCcBank2+
        ", otherCcLmt2="+otherCcLmt2+
        "otherCcLmt2="+otherCcLmt2+
        ", carIdNum="+carIdNum+
        "carIdNum="+carIdNum+
        ", cardEmissions="+cardEmissions+
        "cardEmissions="+cardEmissions+
        ", carModel="+carModel+
        "carModel="+carModel+
        ", cardPrice="+cardPrice+
        "cardPrice="+cardPrice+
        ", createDate="+createDate+
        "createDate="+createDate+
        ", createUser="+createUser+
        "createUser="+createUser+
        ", updateDate="+updateDate+
        "updateDate="+updateDate+
        ", updateUser="+updateUser+
        "updateUser="+updateUser+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (appNo == null) appNo = "";
        if (eviIncome == null) eviIncome = BigDecimal.ZERO;
        if (indSignFile == null) indSignFile = "";
        if (indIdFile == null) indIdFile = "";
        if (indJobFile == null) indJobFile = "";
        if (indLiveFile == null) indLiveFile = "";
        if (indLiveAddr == null) indLiveAddr = "";
        if (estatesNoInstallAmt == null) estatesNoInstallAmt = BigDecimal.ZERO;
        if (estatesInstallAmt == null) estatesInstallAmt = BigDecimal.ZERO;
        if (estatesInstallLoanAmt == null) estatesInstallLoanAmt = BigDecimal.ZERO;
        if (buildingName == null) buildingName = "";
        if (houseMonthyAmt == null) houseMonthyAmt = BigDecimal.ZERO;
        if (otherCcBank1 == null) otherCcBank1 = "";
        if (otherCcLmt1 == null) otherCcLmt1 = BigDecimal.ZERO;
        if (otherCcBank2 == null) otherCcBank2 = "";
        if (otherCcLmt2 == null) otherCcLmt2 = BigDecimal.ZERO;
        if (carIdNum == null) carIdNum = "";
        if (cardEmissions == null) cardEmissions = "";
        if (carModel == null) carModel = "";
        if (cardPrice == null) cardPrice = BigDecimal.ZERO;
        if (createDate == null) createDate = new Date();
        if (createUser == null) createUser = "";
        if (updateDate == null) updateDate = new Date();
        if (updateUser == null) updateUser = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}