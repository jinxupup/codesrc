package com.jjb.ecms.facility.servicetask.item;

import java.math.BigDecimal;
import java.util.Date;

import com.jjb.unicorn.facility.cstruct.CChar;

/**
 * @Description: 网点机构上传接口
 * @version V1.0
 */
public class TmBankItem {

	public final String REG_CELLPHONE = "^1[3456789]\\d{9}$";
    public final String REG_PHONE = "^(0\\d{2,3}\\-\\d{6,8}(\\-\\d{1,6})?$)|(1[3456789]\\d{9}$)";
	public final String REG_EMAIL = "^([a-zA-Z0-9_]+@[a-z0-9]+\\.[a-z]+(\\.[a-z]+)?)$";
	public final String REG_LOGONAME = "^[a-zA-Z\\s]+$";
	public final String REG_POSTCODE = "^\\d{6}$";
	public final String REG_MONEY = "^(0|([1-9](\\d{1,14})?))(\\.\\d{1,2})?$";
	public final String REG_INT = "^(0|([1-9](\\d)?))$";
	public final String REG_COUNTY_CD = "^\\d{1,3}$";
	public final String REG_FAX = "^((0\\d{9,12}(\\d{3,5})?)|(1[3456789]\\d{9}))$";
	public final String REG_RATIO = "^\\d{1,5}(\\.\\d{1,2})?$";
	public final String REG_MONTH = "^0|([1-9](\\d{1,2})?)$";
	public final String REG_CILLING_CYCLE = "^(([1-9](\\s)?)|(1\\d)|(2[0-8]))$";


	@CChar(value = 12, autoTrim = true, order = 100)
	public String org;					// 机构号
	@CChar(value = 12, autoTrim = true, order = 100)
	public String branchCode;					// 分行号
	@CChar(value = 12, autoTrim = true, order = 100)
	public String parentCode;					// 上级管理分行

	@CChar(value = 80, autoTrim = true, order = 500)
	public String branchName;			// 分行名称

	@CChar(value = 80, autoTrim = true, order = 500)
	public String city;			// 所在城市

	@CChar(value = 200, autoTrim = true, order = 600)
	public String empAdd;				// 地址

	@CChar(value = 5, autoTrim = true, order = 800)
    public String cardCollectInd;			// 领卡权限标志

	@CChar(value = 5, autoTrim = true, order = 850)
    public String branchIssueInd;			// 发卡权限标志
    
	@CChar(value = 4, autoTrim = true, order = 900)
    public Integer jpaVersion;
    
	@CChar(value = 40, autoTrim = true, order = 950)
    public String obText1;

	@CChar(value = 40, autoTrim = true, order = 1000)
	public String obText2;

	@CChar(value = 40, autoTrim = true, order = 1100)
	public String obText3;

	@CChar(value = 18 ,precision = 2, formatPattern = REG_MONEY, order = 1200)
    public BigDecimal obDecimal1;

	@CChar(value = 18 ,precision = 2, formatPattern = REG_MONEY, order = 1300)
	public BigDecimal obDecimal2;

	@CChar(value = 10, datePattern = "yyyy-MM-dd", order = 1400)
	public Date obDate1;

	@CChar(value = 10, datePattern = "yyyy-MM-dd", order = 1500)
	public Date obDate2;

	@CChar(value = 10, datePattern = "yyyy-MM-dd", order = 1600)
	public Date upDate;
    

}
