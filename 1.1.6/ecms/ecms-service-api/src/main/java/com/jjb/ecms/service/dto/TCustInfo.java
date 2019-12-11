package com.jjb.ecms.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * 用于联机交易时的客户信息实体类
 * 
 * @author hp
 *
 */
@Data
public class TCustInfo implements Serializable {
	private static final long serialVersionUID = -3049384988652027684L;
	public String ifSelectedCard;// 是否自选卡号
	public String cardNo;// 自选卡号
	public String bscSuppInd;// 申请人类型
	public Integer attachNo;// 附卡编号
	public String primCardNo;// 申请人主卡卡号
	public String relationshipToBsc;// 与主卡持卡人关系
	public String name;// 姓名
	public String embLogo;// 凸印姓名
	public String gender;// 性别
	public String age;// 年龄
	public String nationality;// 国籍
	public String residencyCountryCd;// 永久居住地国家代码
	public String idType;// 证件类型
	public String idNo;// 证件号码
	public Date idLastDate;// 证件到期日
	public String idLastAll;// 证件是否永久有效
	public String idIssuerAddress;// 发证机关所在地址
	public Date birthday;// 生日
	public BigDecimal yearIncome;// 年收入
	public String qualification;// 教育状况/学历
	public String degree;// 学位
	public String maritalStatus;// 婚姻状况
	public String homeAddrCtryCd;// 家庭国家代码
	public String homeState;// 家庭所在省
	public String homeCity;// 家庭所在市
	public String homeZone;// 家庭所在区县
	public String houseOwnership;// 住宅状况/住宅持有类型
	public String homeAdd;// 家庭地址
	public String homePostcode;// 家庭住宅邮编
	public String homePhone;// 家庭电话
	public String cellphone;// 移动电话
	public String email;// 电子邮箱
	public String familyMember;// 家庭人口
	public String bankmemFlag;// 是否行内员工
	public String bankmemberNo;// 本行员工号
	public String corpName;// 公司名称
	public String empStructure;// 公司性质
	public String empType;// 公司行业类别
	public BigDecimal empWorkyears;// 本单位工作年限
	public String empAddrCtryCd;// 公司国家代码
	public String empProvince;// 公司所在省
	public String empCity;// 公司所在市
	public String empZone;// 公司所在区/县
	public String empAdd;// 公司地址
	public String empPostcode;// 公司邮编
	public String empPhone;// 公司电话
	public String empDepartment;// 任职部门
	public String empPost;// 职务
	public String empPostName;// 职务名称
	public String occupation;// 职业
	public String titleOfTechnical;// 职称
	public String jobGrade;// 岗位级别
	public String posPinVerifyInd;// 是否消费凭密
	public String photoUseFlag;// 是否彩照卡
	public String smAmtVerifyInd;// 小额免密
	public String otherAsk;// 预留问题
	public String otherAnswer;// 预留答案
	public String bankCustomerId;// 行内客户号
	public String groupNum;// 申请团办编号
	public String custType;// 客户类型,申请进件提交赋值时拷贝T1005Req.clientType

	public Map<String, Serializable> convertToMap() {
		HashMap<String, Serializable> map = new HashMap<String, Serializable>();
		map.put("ifSelectedCard", this.ifSelectedCard);
		map.put("cardNo", this.cardNo);
		map.put("bscSuppInd", this.bscSuppInd);
		map.put("attachNo", this.attachNo);
		map.put("primCardNo", this.primCardNo);
		map.put("relationshipToBsc", this.relationshipToBsc);
		map.put("name", this.name);
		map.put("embLogo", this.embLogo);
		map.put("gender", this.gender);
		map.put("age", this.age);
		map.put("nationality", this.nationality);
		map.put("residencyCountryCd", this.residencyCountryCd);
		map.put("idType", this.idType);
		map.put("idNo", this.idNo);
		map.put("idLastDate", this.idLastDate);
		map.put("idLastAll", this.idLastAll);
		map.put("idIssuerAddress", this.idIssuerAddress);
		map.put("birthday", this.birthday);
		map.put("yearIncome", this.yearIncome);
		map.put("qualification", this.qualification);
		map.put("degree", this.degree);
		map.put("maritalStatus", this.maritalStatus);
		map.put("homeAddrCtryCd", this.homeAddrCtryCd);
		map.put("homeState", this.homeState);
		map.put("homeCity", this.homeCity);
		map.put("homeZone", this.homeZone);
		map.put("houseOwnership", this.houseOwnership);
		map.put("homeAdd", this.homeAdd);
		map.put("homePostcode", this.homePostcode);
		map.put("homePhone", this.homePhone);
		map.put("cellphone", this.cellphone);
		map.put("email", this.email);
		map.put("familyMember", this.familyMember);
		map.put("bankmemFlag", this.bankmemFlag);
		map.put("bankmemberNo", this.bankmemberNo);
		map.put("corpName", this.corpName);
		map.put("empStructure", this.empStructure);
		map.put("empType", this.empType);
		map.put("empWorkyears", this.empWorkyears);
		map.put("empAddrCtryCd", this.empAddrCtryCd);
		map.put("empProvince", this.empProvince);
		map.put("empCity", this.empCity);
		map.put("empZone", this.empZone);
		map.put("empAdd", this.empAdd);
		map.put("empPostcode", this.empPostcode);
		map.put("empPhone", this.empPhone);
		map.put("empDepartment", this.empDepartment);
		map.put("empPost", this.empPost);
		map.put("empPostName", this.empPostName);
		map.put("occupation", this.occupation);
		map.put("titleOfTechnical", this.titleOfTechnical);
		map.put("jobGrade", this.jobGrade);
		map.put("posPinVerifyInd", this.posPinVerifyInd);
		map.put("photoUseFlag", this.photoUseFlag);
		map.put("smAmtVerifyInd", this.smAmtVerifyInd);
		map.put("otherAsk", this.otherAsk);
		map.put("otherAnswer", this.otherAnswer);
		map.put("bankCustomerId", this.bankCustomerId);
		map.put("groupNum", this.groupNum);
		map.put("custType", this.custType);

		return map;
	}

}
