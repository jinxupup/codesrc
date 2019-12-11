package com.jjb.ecms.facility.servicetask.item;

import java.math.BigDecimal;
import java.util.Date;

import com.jjb.unicorn.facility.cstruct.CChar;

/**
 * @Description: 黑名单上传接口
 * @version V1.0
 */
public class TmRiskListItem {

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
	
	@CChar(value = 1, autoTrim = true, order = 150)
	public String risklistSrc;		// 黑名单来源
    
	@CChar(value = 80, autoTrim = true, order = 200)
	public String name;				// 客户姓名

	@CChar(value = 1, autoTrim = true, order = 250)
	public String idType;				// 证件类型

	@CChar(value = 30, autoTrim = true, order = 300)
	public String idNo;				// 证件号码

	@CChar(value = 20, autoTrim = true,  formatPattern = REG_CELLPHONE, order = 350)
    public String cellphone;			// 移动电话

	@CChar(value = 20, autoTrim = true, formatPattern = REG_PHONE, order = 400)
	public String homePhone;			// 家庭电话

	@CChar(value = 200, autoTrim = true, order = 450)
	public String homeAdd;			// 家庭地址

	@CChar(value = 80, autoTrim = true, order = 500)
	public String corpName;			// 公司名称

	@CChar(value = 20, autoTrim = true, formatPattern = REG_PHONE, order = 550)
	public String empPhone;			// 公司电话

	@CChar(value = 200, autoTrim = true, order = 600)
	public String empAdd;				// 公司地址

	@CChar(value = 400, autoTrim = true, order = 650)
    public String reason;				// 单原因说明

	@CChar(value = 400, autoTrim = true, order = 700)
    public String memo;				// 备注

	@CChar(value = 10, datePattern = "yyyy-MM-dd", order = 750)
    public Date validDate;			// 记录有效期

	@CChar(value = 5, autoTrim = true, order = 800)
    public String actType;			// 风险名单类型

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
	public Date updateDate;    //更新（编辑）时间

	@CChar(value = 10, datePattern = "yyyy-MM-dd", order = 1600)
	public Date createDate;		//创建时间
    

}
