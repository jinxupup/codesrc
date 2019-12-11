package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 申请件卡片与银行专用栏信息表
 * @author jjb
 */
public class TmAppPrimCardInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String org;

    private String appNo;

    private String cardfaceCd;

    private String cardFetchMethod;

    private String cardMailerInd;

    private String cardSendAddType;

    private String fetchBranch;

    private String stmtMediaType;

    private String stmtMailAddrInd;

    private String billingCycle;

    private String ddInd;

    private String ddBankAcctNo;

    private String ddBankName;

    private String ddBankAcctName;

    private String ddBankBranch;

    private String ddBankAcctNoType;

    private String creditTypeOther;

    private String otherCardNo;

    private String isPrdChange;

    private String spreaderNo;

    private String spreaderName;

    private String spreaderTelephone;

    private String spreaderCardNo;

    private String spreaderIsBankEmployee;

    private String spreaderBranchOne;

    private String spreaderBranchTwo;

    private String spreaderBranchThree;

    private String spreaderType;

    private String spreaderMode;

    private String spreaderCorpPreNo;

    private String spreaderSupCode;

    private String spreaderTeamCode;

    private String spreaderAreaCode;

    private String spreaderIsEff;

    private String reviewNo;

    private String reviewName;

    private String reviewTelephone;

    private String reviewBranchOne;

    private String reviewBranchTwo;

    private String reviewBranchThree;

    private String preNo;

    private String preName;

    private String preTelephone;

    private String preBranchOne;

    private String preBranchTwo;

    private String preBranchThree;

    private String inputNo;

    private String inputName;

    private String inputTelephone;

    private String inputBranchOne;

    private String inputBranchTwo;

    private String inputBranchThree;

    private Date inputDate;

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
     * <p>卡面代码</p>
     */
    public String getCardfaceCd() {
        return cardfaceCd;
    }

    /**
     * <p>卡面代码</p>
     */
    public void setCardfaceCd(String cardfaceCd) {
        this.cardfaceCd = cardfaceCd;
    }

    /**
     * <p>介质卡领取方式</p>
     */
    public String getCardFetchMethod() {
        return cardFetchMethod;
    }

    /**
     * <p>介质卡领取方式</p>
     */
    public void setCardFetchMethod(String cardFetchMethod) {
        this.cardFetchMethod = cardFetchMethod;
    }

    /**
     * <p>卡片寄送地址标志</p>
     */
    public String getCardMailerInd() {
        return cardMailerInd;
    }

    /**
     * <p>卡片寄送地址标志</p>
     */
    public void setCardMailerInd(String cardMailerInd) {
        this.cardMailerInd = cardMailerInd;
    }

    /**
     * <p>介质卡寄送地址类型</p>
     */
    public String getCardSendAddType() {
        return cardSendAddType;
    }

    /**
     * <p>介质卡寄送地址类型</p>
     */
    public void setCardSendAddType(String cardSendAddType) {
        this.cardSendAddType = cardSendAddType;
    }

    /**
     * <p>领卡网点</p>
     */
    public String getFetchBranch() {
        return fetchBranch;
    }

    /**
     * <p>领卡网点</p>
     */
    public void setFetchBranch(String fetchBranch) {
        this.fetchBranch = fetchBranch;
    }

    /**
     * <p>账单介质类型</p>
     */
    public String getStmtMediaType() {
        return stmtMediaType;
    }

    /**
     * <p>账单介质类型</p>
     */
    public void setStmtMediaType(String stmtMediaType) {
        this.stmtMediaType = stmtMediaType;
    }

    /**
     * <p>纸质账单或相关函件寄送地址标志</p>
     */
    public String getStmtMailAddrInd() {
        return stmtMailAddrInd;
    }

    /**
     * <p>纸质账单或相关函件寄送地址标志</p>
     */
    public void setStmtMailAddrInd(String stmtMailAddrInd) {
        this.stmtMailAddrInd = stmtMailAddrInd;
    }

    /**
     * <p>账单日</p>
     */
    public String getBillingCycle() {
        return billingCycle;
    }

    /**
     * <p>账单日</p>
     */
    public void setBillingCycle(String billingCycle) {
        this.billingCycle = billingCycle;
    }

    /**
     * <p>约定还款类型</p>
     */
    public String getDdInd() {
        return ddInd;
    }

    /**
     * <p>约定还款类型</p>
     */
    public void setDdInd(String ddInd) {
        this.ddInd = ddInd;
    }

    /**
     * <p>约定还款扣款账号</p>
     */
    public String getDdBankAcctNo() {
        return ddBankAcctNo;
    }

    /**
     * <p>约定还款扣款账号</p>
     */
    public void setDdBankAcctNo(String ddBankAcctNo) {
        this.ddBankAcctNo = ddBankAcctNo;
    }

    /**
     * <p>约定还款银行名称</p>
     */
    public String getDdBankName() {
        return ddBankName;
    }

    /**
     * <p>约定还款银行名称</p>
     */
    public void setDdBankName(String ddBankName) {
        this.ddBankName = ddBankName;
    }

    /**
     * <p>约定还款扣款人姓名</p>
     */
    public String getDdBankAcctName() {
        return ddBankAcctName;
    }

    /**
     * <p>约定还款扣款人姓名</p>
     */
    public void setDdBankAcctName(String ddBankAcctName) {
        this.ddBankAcctName = ddBankAcctName;
    }

    /**
     * <p>约定还款开户行号</p>
     */
    public String getDdBankBranch() {
        return ddBankBranch;
    }

    /**
     * <p>约定还款开户行号</p>
     */
    public void setDdBankBranch(String ddBankBranch) {
        this.ddBankBranch = ddBankBranch;
    }

    /**
     * <p>约定还款账户类型</p>
     */
    public String getDdBankAcctNoType() {
        return ddBankAcctNoType;
    }

    /**
     * <p>约定还款账户类型</p>
     */
    public void setDdBankAcctNoType(String ddBankAcctNoType) {
        this.ddBankAcctNoType = ddBankAcctNoType;
    }

    /**
     * <p>已有信用卡类型</p>
     */
    public String getCreditTypeOther() {
        return creditTypeOther;
    }

    /**
     * <p>已有信用卡类型</p>
     */
    public void setCreditTypeOther(String creditTypeOther) {
        this.creditTypeOther = creditTypeOther;
    }

    /**
     * <p>已有信用卡卡号</p>
     */
    public String getOtherCardNo() {
        return otherCardNo;
    }

    /**
     * <p>已有信用卡卡号</p>
     */
    public void setOtherCardNo(String otherCardNo) {
        this.otherCardNo = otherCardNo;
    }

    /**
     * <p>是否同意卡片自动降级</p>
     */
    public String getIsPrdChange() {
        return isPrdChange;
    }

    /**
     * <p>是否同意卡片自动降级</p>
     */
    public void setIsPrdChange(String isPrdChange) {
        this.isPrdChange = isPrdChange;
    }

    /**
     * <p>推广人编号</p>
     */
    public String getSpreaderNo() {
        return spreaderNo;
    }

    /**
     * <p>推广人编号</p>
     */
    public void setSpreaderNo(String spreaderNo) {
        this.spreaderNo = spreaderNo;
    }

    /**
     * <p>推广人姓名</p>
     */
    public String getSpreaderName() {
        return spreaderName;
    }

    /**
     * <p>推广人姓名</p>
     */
    public void setSpreaderName(String spreaderName) {
        this.spreaderName = spreaderName;
    }

    /**
     * <p>推广人联系电话</p>
     */
    public String getSpreaderTelephone() {
        return spreaderTelephone;
    }

    /**
     * <p>推广人联系电话</p>
     */
    public void setSpreaderTelephone(String spreaderTelephone) {
        this.spreaderTelephone = spreaderTelephone;
    }

    /**
     * <p>推广人卡号</p>
     */
    public String getSpreaderCardNo() {
        return spreaderCardNo;
    }

    /**
     * <p>推广人卡号</p>
     */
    public void setSpreaderCardNo(String spreaderCardNo) {
        this.spreaderCardNo = spreaderCardNo;
    }

    /**
     * <p>推广人是否本行员工</p>
     */
    public String getSpreaderIsBankEmployee() {
        return spreaderIsBankEmployee;
    }

    /**
     * <p>推广人是否本行员工</p>
     */
    public void setSpreaderIsBankEmployee(String spreaderIsBankEmployee) {
        this.spreaderIsBankEmployee = spreaderIsBankEmployee;
    }

    /**
     * <p>推广人所属中心(一级机构)</p>
     */
    public String getSpreaderBranchOne() {
        return spreaderBranchOne;
    }

    /**
     * <p>推广人所属中心(一级机构)</p>
     */
    public void setSpreaderBranchOne(String spreaderBranchOne) {
        this.spreaderBranchOne = spreaderBranchOne;
    }

    /**
     * <p>推广人所属分行(二级机构)</p>
     */
    public String getSpreaderBranchTwo() {
        return spreaderBranchTwo;
    }

    /**
     * <p>推广人所属分行(二级机构)</p>
     */
    public void setSpreaderBranchTwo(String spreaderBranchTwo) {
        this.spreaderBranchTwo = spreaderBranchTwo;
    }

    /**
     * <p>推广人所属网点(三级机构，隶属网点)</p>
     */
    public String getSpreaderBranchThree() {
        return spreaderBranchThree;
    }

    /**
     * <p>推广人所属网点(三级机构，隶属网点)</p>
     */
    public void setSpreaderBranchThree(String spreaderBranchThree) {
        this.spreaderBranchThree = spreaderBranchThree;
    }

    /**
     * <p>推广渠道</p>
     */
    public String getSpreaderType() {
        return spreaderType;
    }

    /**
     * <p>推广渠道</p>
     */
    public void setSpreaderType(String spreaderType) {
        this.spreaderType = spreaderType;
    }

    /**
     * <p>推广方式</p>
     */
    public String getSpreaderMode() {
        return spreaderMode;
    }

    /**
     * <p>推广方式</p>
     */
    public void setSpreaderMode(String spreaderMode) {
        this.spreaderMode = spreaderMode;
    }

    /**
     * <p>推广单位预审人编号</p>
     */
    public String getSpreaderCorpPreNo() {
        return spreaderCorpPreNo;
    }

    /**
     * <p>推广单位预审人编号</p>
     */
    public void setSpreaderCorpPreNo(String spreaderCorpPreNo) {
        this.spreaderCorpPreNo = spreaderCorpPreNo;
    }

    /**
     * <p>推广主管代码</p>
     */
    public String getSpreaderSupCode() {
        return spreaderSupCode;
    }

    /**
     * <p>推广主管代码</p>
     */
    public void setSpreaderSupCode(String spreaderSupCode) {
        this.spreaderSupCode = spreaderSupCode;
    }

    /**
     * <p>推广团队代码</p>
     */
    public String getSpreaderTeamCode() {
        return spreaderTeamCode;
    }

    /**
     * <p>推广团队代码</p>
     */
    public void setSpreaderTeamCode(String spreaderTeamCode) {
        this.spreaderTeamCode = spreaderTeamCode;
    }

    /**
     * <p>推广区域代码</p>
     */
    public String getSpreaderAreaCode() {
        return spreaderAreaCode;
    }

    /**
     * <p>推广区域代码</p>
     */
    public void setSpreaderAreaCode(String spreaderAreaCode) {
        this.spreaderAreaCode = spreaderAreaCode;
    }

    /**
     * <p>推广是否有效</p>
     */
    public String getSpreaderIsEff() {
        return spreaderIsEff;
    }

    /**
     * <p>推广是否有效</p>
     */
    public void setSpreaderIsEff(String spreaderIsEff) {
        this.spreaderIsEff = spreaderIsEff;
    }

    /**
     * <p>复核人编号</p>
     */
    public String getReviewNo() {
        return reviewNo;
    }

    /**
     * <p>复核人编号</p>
     */
    public void setReviewNo(String reviewNo) {
        this.reviewNo = reviewNo;
    }

    /**
     * <p>复核人姓名</p>
     */
    public String getReviewName() {
        return reviewName;
    }

    /**
     * <p>复核人姓名</p>
     */
    public void setReviewName(String reviewName) {
        this.reviewName = reviewName;
    }

    /**
     * <p>复核人联系电话</p>
     */
    public String getReviewTelephone() {
        return reviewTelephone;
    }

    /**
     * <p>复核人联系电话</p>
     */
    public void setReviewTelephone(String reviewTelephone) {
        this.reviewTelephone = reviewTelephone;
    }

    /**
     * <p>复核人所属中心(一级机构)</p>
     */
    public String getReviewBranchOne() {
        return reviewBranchOne;
    }

    /**
     * <p>复核人所属中心(一级机构)</p>
     */
    public void setReviewBranchOne(String reviewBranchOne) {
        this.reviewBranchOne = reviewBranchOne;
    }

    /**
     * <p>复核人所属分行(二级机构)</p>
     */
    public String getReviewBranchTwo() {
        return reviewBranchTwo;
    }

    /**
     * <p>复核人所属分行(二级机构)</p>
     */
    public void setReviewBranchTwo(String reviewBranchTwo) {
        this.reviewBranchTwo = reviewBranchTwo;
    }

    /**
     * <p>复核人所属网点(三级机构，隶属网点)</p>
     */
    public String getReviewBranchThree() {
        return reviewBranchThree;
    }

    /**
     * <p>复核人所属网点(三级机构，隶属网点)</p>
     */
    public void setReviewBranchThree(String reviewBranchThree) {
        this.reviewBranchThree = reviewBranchThree;
    }

    /**
     * <p>预审人编号</p>
     */
    public String getPreNo() {
        return preNo;
    }

    /**
     * <p>预审人编号</p>
     */
    public void setPreNo(String preNo) {
        this.preNo = preNo;
    }

    /**
     * <p>预审人姓名</p>
     */
    public String getPreName() {
        return preName;
    }

    /**
     * <p>预审人姓名</p>
     */
    public void setPreName(String preName) {
        this.preName = preName;
    }

    /**
     * <p>预审人联系电话</p>
     */
    public String getPreTelephone() {
        return preTelephone;
    }

    /**
     * <p>预审人联系电话</p>
     */
    public void setPreTelephone(String preTelephone) {
        this.preTelephone = preTelephone;
    }

    /**
     * <p>预审人所属中心(一级机构)</p>
     */
    public String getPreBranchOne() {
        return preBranchOne;
    }

    /**
     * <p>预审人所属中心(一级机构)</p>
     */
    public void setPreBranchOne(String preBranchOne) {
        this.preBranchOne = preBranchOne;
    }

    /**
     * <p>预审人所属分行(二级机构)</p>
     */
    public String getPreBranchTwo() {
        return preBranchTwo;
    }

    /**
     * <p>预审人所属分行(二级机构)</p>
     */
    public void setPreBranchTwo(String preBranchTwo) {
        this.preBranchTwo = preBranchTwo;
    }

    /**
     * <p>预审人所属网点(三级机构，隶属网点)</p>
     */
    public String getPreBranchThree() {
        return preBranchThree;
    }

    /**
     * <p>预审人所属网点(三级机构，隶属网点)</p>
     */
    public void setPreBranchThree(String preBranchThree) {
        this.preBranchThree = preBranchThree;
    }

    /**
     * <p>录入人员编号</p>
     */
    public String getInputNo() {
        return inputNo;
    }

    /**
     * <p>录入人员编号</p>
     */
    public void setInputNo(String inputNo) {
        this.inputNo = inputNo;
    }

    /**
     * <p>录入人员姓名</p>
     */
    public String getInputName() {
        return inputName;
    }

    /**
     * <p>录入人员姓名</p>
     */
    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    /**
     * <p>录入人联系电话</p>
     */
    public String getInputTelephone() {
        return inputTelephone;
    }

    /**
     * <p>录入人联系电话</p>
     */
    public void setInputTelephone(String inputTelephone) {
        this.inputTelephone = inputTelephone;
    }

    /**
     * <p>录入人所属中心(一级机构)</p>
     */
    public String getInputBranchOne() {
        return inputBranchOne;
    }

    /**
     * <p>录入人所属中心(一级机构)</p>
     */
    public void setInputBranchOne(String inputBranchOne) {
        this.inputBranchOne = inputBranchOne;
    }

    /**
     * <p>录入人所属分行(二级机构)</p>
     */
    public String getInputBranchTwo() {
        return inputBranchTwo;
    }

    /**
     * <p>录入人所属分行(二级机构)</p>
     */
    public void setInputBranchTwo(String inputBranchTwo) {
        this.inputBranchTwo = inputBranchTwo;
    }

    /**
     * <p>录入人所属网点(三级机构，隶属网点)</p>
     */
    public String getInputBranchThree() {
        return inputBranchThree;
    }

    /**
     * <p>录入人所属网点(三级机构，隶属网点)</p>
     */
    public void setInputBranchThree(String inputBranchThree) {
        this.inputBranchThree = inputBranchThree;
    }

    /**
     * <p>录入日期</p>
     */
    public Date getInputDate() {
        return inputDate;
    }

    /**
     * <p>录入日期</p>
     */
    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
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
     * <p>更新用户</p>
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * <p>更新用户</p>
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
        map.put("cardfaceCd", cardfaceCd);
        map.put("cardFetchMethod", cardFetchMethod);
        map.put("cardMailerInd", cardMailerInd);
        map.put("cardSendAddType", cardSendAddType);
        map.put("fetchBranch", fetchBranch);
        map.put("stmtMediaType", stmtMediaType);
        map.put("stmtMailAddrInd", stmtMailAddrInd);
        map.put("billingCycle", billingCycle);
        map.put("ddInd", ddInd);
        map.put("ddBankAcctNo", ddBankAcctNo);
        map.put("ddBankName", ddBankName);
        map.put("ddBankAcctName", ddBankAcctName);
        map.put("ddBankBranch", ddBankBranch);
        map.put("ddBankAcctNoType", ddBankAcctNoType);
        map.put("creditTypeOther", creditTypeOther);
        map.put("otherCardNo", otherCardNo);
        map.put("isPrdChange", isPrdChange);
        map.put("spreaderNo", spreaderNo);
        map.put("spreaderName", spreaderName);
        map.put("spreaderTelephone", spreaderTelephone);
        map.put("spreaderCardNo", spreaderCardNo);
        map.put("spreaderIsBankEmployee", spreaderIsBankEmployee);
        map.put("spreaderBranchOne", spreaderBranchOne);
        map.put("spreaderBranchTwo", spreaderBranchTwo);
        map.put("spreaderBranchThree", spreaderBranchThree);
        map.put("spreaderType", spreaderType);
        map.put("spreaderMode", spreaderMode);
        map.put("spreaderCorpPreNo", spreaderCorpPreNo);
        map.put("spreaderSupCode", spreaderSupCode);
        map.put("spreaderTeamCode", spreaderTeamCode);
        map.put("spreaderAreaCode", spreaderAreaCode);
        map.put("spreaderIsEff", spreaderIsEff);
        map.put("reviewNo", reviewNo);
        map.put("reviewName", reviewName);
        map.put("reviewTelephone", reviewTelephone);
        map.put("reviewBranchOne", reviewBranchOne);
        map.put("reviewBranchTwo", reviewBranchTwo);
        map.put("reviewBranchThree", reviewBranchThree);
        map.put("preNo", preNo);
        map.put("preName", preName);
        map.put("preTelephone", preTelephone);
        map.put("preBranchOne", preBranchOne);
        map.put("preBranchTwo", preBranchTwo);
        map.put("preBranchThree", preBranchThree);
        map.put("inputNo", inputNo);
        map.put("inputName", inputName);
        map.put("inputTelephone", inputTelephone);
        map.put("inputBranchOne", inputBranchOne);
        map.put("inputBranchTwo", inputBranchTwo);
        map.put("inputBranchThree", inputBranchThree);
        map.put("inputDate", inputDate);
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
        if (map.containsKey("cardfaceCd")) this.setCardfaceCd(DataTypeUtils.getStringValue(map.get("cardfaceCd")));
        if (map.containsKey("cardFetchMethod")) this.setCardFetchMethod(DataTypeUtils.getStringValue(map.get("cardFetchMethod")));
        if (map.containsKey("cardMailerInd")) this.setCardMailerInd(DataTypeUtils.getStringValue(map.get("cardMailerInd")));
        if (map.containsKey("cardSendAddType")) this.setCardSendAddType(DataTypeUtils.getStringValue(map.get("cardSendAddType")));
        if (map.containsKey("fetchBranch")) this.setFetchBranch(DataTypeUtils.getStringValue(map.get("fetchBranch")));
        if (map.containsKey("stmtMediaType")) this.setStmtMediaType(DataTypeUtils.getStringValue(map.get("stmtMediaType")));
        if (map.containsKey("stmtMailAddrInd")) this.setStmtMailAddrInd(DataTypeUtils.getStringValue(map.get("stmtMailAddrInd")));
        if (map.containsKey("billingCycle")) this.setBillingCycle(DataTypeUtils.getStringValue(map.get("billingCycle")));
        if (map.containsKey("ddInd")) this.setDdInd(DataTypeUtils.getStringValue(map.get("ddInd")));
        if (map.containsKey("ddBankAcctNo")) this.setDdBankAcctNo(DataTypeUtils.getStringValue(map.get("ddBankAcctNo")));
        if (map.containsKey("ddBankName")) this.setDdBankName(DataTypeUtils.getStringValue(map.get("ddBankName")));
        if (map.containsKey("ddBankAcctName")) this.setDdBankAcctName(DataTypeUtils.getStringValue(map.get("ddBankAcctName")));
        if (map.containsKey("ddBankBranch")) this.setDdBankBranch(DataTypeUtils.getStringValue(map.get("ddBankBranch")));
        if (map.containsKey("ddBankAcctNoType")) this.setDdBankAcctNoType(DataTypeUtils.getStringValue(map.get("ddBankAcctNoType")));
        if (map.containsKey("creditTypeOther")) this.setCreditTypeOther(DataTypeUtils.getStringValue(map.get("creditTypeOther")));
        if (map.containsKey("otherCardNo")) this.setOtherCardNo(DataTypeUtils.getStringValue(map.get("otherCardNo")));
        if (map.containsKey("isPrdChange")) this.setIsPrdChange(DataTypeUtils.getStringValue(map.get("isPrdChange")));
        if (map.containsKey("spreaderNo")) this.setSpreaderNo(DataTypeUtils.getStringValue(map.get("spreaderNo")));
        if (map.containsKey("spreaderName")) this.setSpreaderName(DataTypeUtils.getStringValue(map.get("spreaderName")));
        if (map.containsKey("spreaderTelephone")) this.setSpreaderTelephone(DataTypeUtils.getStringValue(map.get("spreaderTelephone")));
        if (map.containsKey("spreaderCardNo")) this.setSpreaderCardNo(DataTypeUtils.getStringValue(map.get("spreaderCardNo")));
        if (map.containsKey("spreaderIsBankEmployee")) this.setSpreaderIsBankEmployee(DataTypeUtils.getStringValue(map.get("spreaderIsBankEmployee")));
        if (map.containsKey("spreaderBranchOne")) this.setSpreaderBranchOne(DataTypeUtils.getStringValue(map.get("spreaderBranchOne")));
        if (map.containsKey("spreaderBranchTwo")) this.setSpreaderBranchTwo(DataTypeUtils.getStringValue(map.get("spreaderBranchTwo")));
        if (map.containsKey("spreaderBranchThree")) this.setSpreaderBranchThree(DataTypeUtils.getStringValue(map.get("spreaderBranchThree")));
        if (map.containsKey("spreaderType")) this.setSpreaderType(DataTypeUtils.getStringValue(map.get("spreaderType")));
        if (map.containsKey("spreaderMode")) this.setSpreaderMode(DataTypeUtils.getStringValue(map.get("spreaderMode")));
        if (map.containsKey("spreaderCorpPreNo")) this.setSpreaderCorpPreNo(DataTypeUtils.getStringValue(map.get("spreaderCorpPreNo")));
        if (map.containsKey("spreaderSupCode")) this.setSpreaderSupCode(DataTypeUtils.getStringValue(map.get("spreaderSupCode")));
        if (map.containsKey("spreaderTeamCode")) this.setSpreaderTeamCode(DataTypeUtils.getStringValue(map.get("spreaderTeamCode")));
        if (map.containsKey("spreaderAreaCode")) this.setSpreaderAreaCode(DataTypeUtils.getStringValue(map.get("spreaderAreaCode")));
        if (map.containsKey("spreaderIsEff")) this.setSpreaderIsEff(DataTypeUtils.getStringValue(map.get("spreaderIsEff")));
        if (map.containsKey("reviewNo")) this.setReviewNo(DataTypeUtils.getStringValue(map.get("reviewNo")));
        if (map.containsKey("reviewName")) this.setReviewName(DataTypeUtils.getStringValue(map.get("reviewName")));
        if (map.containsKey("reviewTelephone")) this.setReviewTelephone(DataTypeUtils.getStringValue(map.get("reviewTelephone")));
        if (map.containsKey("reviewBranchOne")) this.setReviewBranchOne(DataTypeUtils.getStringValue(map.get("reviewBranchOne")));
        if (map.containsKey("reviewBranchTwo")) this.setReviewBranchTwo(DataTypeUtils.getStringValue(map.get("reviewBranchTwo")));
        if (map.containsKey("reviewBranchThree")) this.setReviewBranchThree(DataTypeUtils.getStringValue(map.get("reviewBranchThree")));
        if (map.containsKey("preNo")) this.setPreNo(DataTypeUtils.getStringValue(map.get("preNo")));
        if (map.containsKey("preName")) this.setPreName(DataTypeUtils.getStringValue(map.get("preName")));
        if (map.containsKey("preTelephone")) this.setPreTelephone(DataTypeUtils.getStringValue(map.get("preTelephone")));
        if (map.containsKey("preBranchOne")) this.setPreBranchOne(DataTypeUtils.getStringValue(map.get("preBranchOne")));
        if (map.containsKey("preBranchTwo")) this.setPreBranchTwo(DataTypeUtils.getStringValue(map.get("preBranchTwo")));
        if (map.containsKey("preBranchThree")) this.setPreBranchThree(DataTypeUtils.getStringValue(map.get("preBranchThree")));
        if (map.containsKey("inputNo")) this.setInputNo(DataTypeUtils.getStringValue(map.get("inputNo")));
        if (map.containsKey("inputName")) this.setInputName(DataTypeUtils.getStringValue(map.get("inputName")));
        if (map.containsKey("inputTelephone")) this.setInputTelephone(DataTypeUtils.getStringValue(map.get("inputTelephone")));
        if (map.containsKey("inputBranchOne")) this.setInputBranchOne(DataTypeUtils.getStringValue(map.get("inputBranchOne")));
        if (map.containsKey("inputBranchTwo")) this.setInputBranchTwo(DataTypeUtils.getStringValue(map.get("inputBranchTwo")));
        if (map.containsKey("inputBranchThree")) this.setInputBranchThree(DataTypeUtils.getStringValue(map.get("inputBranchThree")));
        if (map.containsKey("inputDate")) this.setInputDate(DataTypeUtils.getDateValue(map.get("inputDate")));
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
        ", cardfaceCd="+cardfaceCd+
        "cardfaceCd="+cardfaceCd+
        ", cardFetchMethod="+cardFetchMethod+
        "cardFetchMethod="+cardFetchMethod+
        ", cardMailerInd="+cardMailerInd+
        "cardMailerInd="+cardMailerInd+
        ", cardSendAddType="+cardSendAddType+
        "cardSendAddType="+cardSendAddType+
        ", fetchBranch="+fetchBranch+
        "fetchBranch="+fetchBranch+
        ", stmtMediaType="+stmtMediaType+
        "stmtMediaType="+stmtMediaType+
        ", stmtMailAddrInd="+stmtMailAddrInd+
        "stmtMailAddrInd="+stmtMailAddrInd+
        ", billingCycle="+billingCycle+
        "billingCycle="+billingCycle+
        ", ddInd="+ddInd+
        "ddInd="+ddInd+
        ", ddBankAcctNo="+ddBankAcctNo+
        "ddBankAcctNo="+ddBankAcctNo+
        ", ddBankName="+ddBankName+
        "ddBankName="+ddBankName+
        ", ddBankAcctName="+ddBankAcctName+
        "ddBankAcctName="+ddBankAcctName+
        ", ddBankBranch="+ddBankBranch+
        "ddBankBranch="+ddBankBranch+
        ", ddBankAcctNoType="+ddBankAcctNoType+
        "ddBankAcctNoType="+ddBankAcctNoType+
        ", creditTypeOther="+creditTypeOther+
        "creditTypeOther="+creditTypeOther+
        ", otherCardNo="+otherCardNo+
        "otherCardNo="+otherCardNo+
        ", isPrdChange="+isPrdChange+
        "isPrdChange="+isPrdChange+
        ", spreaderNo="+spreaderNo+
        "spreaderNo="+spreaderNo+
        ", spreaderName="+spreaderName+
        "spreaderName="+spreaderName+
        ", spreaderTelephone="+spreaderTelephone+
        "spreaderTelephone="+spreaderTelephone+
        ", spreaderCardNo="+spreaderCardNo+
        "spreaderCardNo="+spreaderCardNo+
        ", spreaderIsBankEmployee="+spreaderIsBankEmployee+
        "spreaderIsBankEmployee="+spreaderIsBankEmployee+
        ", spreaderBranchOne="+spreaderBranchOne+
        "spreaderBranchOne="+spreaderBranchOne+
        ", spreaderBranchTwo="+spreaderBranchTwo+
        "spreaderBranchTwo="+spreaderBranchTwo+
        ", spreaderBranchThree="+spreaderBranchThree+
        "spreaderBranchThree="+spreaderBranchThree+
        ", spreaderType="+spreaderType+
        "spreaderType="+spreaderType+
        ", spreaderMode="+spreaderMode+
        "spreaderMode="+spreaderMode+
        ", spreaderCorpPreNo="+spreaderCorpPreNo+
        "spreaderCorpPreNo="+spreaderCorpPreNo+
        ", spreaderSupCode="+spreaderSupCode+
        "spreaderSupCode="+spreaderSupCode+
        ", spreaderTeamCode="+spreaderTeamCode+
        "spreaderTeamCode="+spreaderTeamCode+
        ", spreaderAreaCode="+spreaderAreaCode+
        "spreaderAreaCode="+spreaderAreaCode+
        ", spreaderIsEff="+spreaderIsEff+
        "spreaderIsEff="+spreaderIsEff+
        ", reviewNo="+reviewNo+
        "reviewNo="+reviewNo+
        ", reviewName="+reviewName+
        "reviewName="+reviewName+
        ", reviewTelephone="+reviewTelephone+
        "reviewTelephone="+reviewTelephone+
        ", reviewBranchOne="+reviewBranchOne+
        "reviewBranchOne="+reviewBranchOne+
        ", reviewBranchTwo="+reviewBranchTwo+
        "reviewBranchTwo="+reviewBranchTwo+
        ", reviewBranchThree="+reviewBranchThree+
        "reviewBranchThree="+reviewBranchThree+
        ", preNo="+preNo+
        "preNo="+preNo+
        ", preName="+preName+
        "preName="+preName+
        ", preTelephone="+preTelephone+
        "preTelephone="+preTelephone+
        ", preBranchOne="+preBranchOne+
        "preBranchOne="+preBranchOne+
        ", preBranchTwo="+preBranchTwo+
        "preBranchTwo="+preBranchTwo+
        ", preBranchThree="+preBranchThree+
        "preBranchThree="+preBranchThree+
        ", inputNo="+inputNo+
        "inputNo="+inputNo+
        ", inputName="+inputName+
        "inputName="+inputName+
        ", inputTelephone="+inputTelephone+
        "inputTelephone="+inputTelephone+
        ", inputBranchOne="+inputBranchOne+
        "inputBranchOne="+inputBranchOne+
        ", inputBranchTwo="+inputBranchTwo+
        "inputBranchTwo="+inputBranchTwo+
        ", inputBranchThree="+inputBranchThree+
        "inputBranchThree="+inputBranchThree+
        ", inputDate="+inputDate+
        "inputDate="+inputDate+
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
        if (cardfaceCd == null) cardfaceCd = "";
        if (cardFetchMethod == null) cardFetchMethod = "";
        if (cardMailerInd == null) cardMailerInd = "";
        if (cardSendAddType == null) cardSendAddType = "";
        if (fetchBranch == null) fetchBranch = "";
        if (stmtMediaType == null) stmtMediaType = "";
        if (stmtMailAddrInd == null) stmtMailAddrInd = "";
        if (billingCycle == null) billingCycle = "";
        if (ddInd == null) ddInd = "";
        if (ddBankAcctNo == null) ddBankAcctNo = "";
        if (ddBankName == null) ddBankName = "";
        if (ddBankAcctName == null) ddBankAcctName = "";
        if (ddBankBranch == null) ddBankBranch = "";
        if (ddBankAcctNoType == null) ddBankAcctNoType = "";
        if (creditTypeOther == null) creditTypeOther = "";
        if (otherCardNo == null) otherCardNo = "";
        if (isPrdChange == null) isPrdChange = "";
        if (spreaderNo == null) spreaderNo = "";
        if (spreaderName == null) spreaderName = "";
        if (spreaderTelephone == null) spreaderTelephone = "";
        if (spreaderCardNo == null) spreaderCardNo = "";
        if (spreaderIsBankEmployee == null) spreaderIsBankEmployee = "";
        if (spreaderBranchOne == null) spreaderBranchOne = "";
        if (spreaderBranchTwo == null) spreaderBranchTwo = "";
        if (spreaderBranchThree == null) spreaderBranchThree = "";
        if (spreaderType == null) spreaderType = "";
        if (spreaderMode == null) spreaderMode = "";
        if (spreaderCorpPreNo == null) spreaderCorpPreNo = "";
        if (spreaderSupCode == null) spreaderSupCode = "";
        if (spreaderTeamCode == null) spreaderTeamCode = "";
        if (spreaderAreaCode == null) spreaderAreaCode = "";
        if (spreaderIsEff == null) spreaderIsEff = "";
        if (reviewNo == null) reviewNo = "";
        if (reviewName == null) reviewName = "";
        if (reviewTelephone == null) reviewTelephone = "";
        if (reviewBranchOne == null) reviewBranchOne = "";
        if (reviewBranchTwo == null) reviewBranchTwo = "";
        if (reviewBranchThree == null) reviewBranchThree = "";
        if (preNo == null) preNo = "";
        if (preName == null) preName = "";
        if (preTelephone == null) preTelephone = "";
        if (preBranchOne == null) preBranchOne = "";
        if (preBranchTwo == null) preBranchTwo = "";
        if (preBranchThree == null) preBranchThree = "";
        if (inputNo == null) inputNo = "";
        if (inputName == null) inputName = "";
        if (inputTelephone == null) inputTelephone = "";
        if (inputBranchOne == null) inputBranchOne = "";
        if (inputBranchTwo == null) inputBranchTwo = "";
        if (inputBranchThree == null) inputBranchThree = "";
        if (inputDate == null) inputDate = new Date();
        if (createDate == null) createDate = new Date();
        if (createUser == null) createUser = "";
        if (updateDate == null) updateDate = new Date();
        if (updateUser == null) updateUser = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}