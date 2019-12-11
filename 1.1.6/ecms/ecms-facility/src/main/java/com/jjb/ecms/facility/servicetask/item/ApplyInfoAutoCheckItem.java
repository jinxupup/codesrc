package com.jjb.ecms.facility.servicetask.item;

import java.io.Serializable;
import java.math.BigDecimal;

import com.jjb.acl.facility.enums.sys.Indicator;

import lombok.Data;

/**
  * @Description: 自动初审规则数据项
  * @author JYData-R&D-Big Star
  * @date 2016年9月8日 下午3:54:37
  * @version V1.0
 */
@Data
public class ApplyInfoAutoCheckItem implements Serializable {

	private static final long serialVersionUID = 248386080884361085L;

	// 申请中是否存在附属卡标志
	private Boolean attCardFlag;
	// 申请中是否存在主卡标志
	private Boolean primCardFlag;
	// 主卡申请人年龄
	private Integer mage;
	// 附卡申请人最大年龄
	private Integer smaxAge;
	// 附卡申请人最小年龄
	private Integer sminAge;
	private String gender; // 性别
	private Boolean pBlackList; // 是否个人黑名单
	private Boolean ifautograph; // 签名是否有效
	private Boolean ifInfo; // 证件是否有效
	private String productCd; // 产品编号
	private BigDecimal pbocBankCreditSumCreditLimit; // 人行记录银行信贷总授信额度
	private BigDecimal pbocCreditCardSumCreditLimit; // 人行记录贷记卡总信用额度
	private Integer pbocCreditMaxCurrExceedNum;        // 人行记录贷记卡明当前最大逾期期数
	private BigDecimal pbocCreditMaxCurrExceedAmt;         // 人行记录贷记卡明当前最大逾期总额
	private Integer pbocCreditMaxCreditCard12MonthMinRepyNum; // 人行记录贷记卡12个月内未还最低还款额最大次数
	private BigDecimal pbocCreditCardSumSemiCredit180Balance; // 人行记录准贷记卡透支180天以上未付余额
	private BigDecimal pbocLoanLimit; // 人行记录贷款合同总金额
	private BigDecimal pboccurrOverdueSum; // 人行记录贷款当前逾期总额(元)
	private Integer pbocCurrExceedNum;      // 人行记录贷款当前逾期期数
	private Integer pbocTotalExceedNum;     // 人行记录贷款累计逾期次数
	private Integer pbocMaxExceedStage;     // 人行记录贷款最高逾期期数
	private BigDecimal pbocDay31Exceed60Balance; // 人行记录贷款逾期31-60天未归还贷款总本金
	private BigDecimal pbocDay61Exceed90Balance; // 人行记录贷款逾期61－90天未归还贷款总本金
	private BigDecimal pbocDay91Exceed180Balance;// 人行记录贷款逾期91－180天未归还贷款总本金
	private BigDecimal pbocDayExceed180Balance;// 人行记录贷款逾期180天以上未归还贷款总本金
	private Integer innnerCurrExceedNum;      // 行内信贷记录当前逾期期数
	private Integer innnerCurrExceedAmt;      // 行内信贷记录当前逾期总额
	private Integer innnerTotalExceedNum;     // 行内信贷记录累计逾期次数
	private Integer innnerMaxExceedStage;     // 行内信贷记录最高逾期期数
	private BigDecimal innnerDay31Exceed60Balance; // 行内信贷记录逾期31-60天未归还贷款本金
	private BigDecimal innnerDay61Exceed90Balance; // 行内信贷记录逾期61－90天未归还贷款本金
	private BigDecimal innnerDay91Exceed180Balance;// 行内信贷记录逾期91－180天未归还贷款本金
	private BigDecimal innnerDayExceed180Balance;// 行内信贷记录逾期180天以上未归还贷款本金
	private Indicator objIndicator1;//（贷记卡）近6月内最高逾期期数达到2期或以上，且最大透支额度达1000元或以上 ccOve2For6MUse1000
	private Indicator objIndicator2;//（贷记卡）近12个月内最高逾期期数达到3期或以上，且最大透支额度达1000元或以上 ccOve3For1YUse1000
	private Indicator objIndicator3;//（贷记卡）近12个月内逾期2期次数达2次或以上，且最大透支额度达1000元或以上 ccOve2Cnt2For1YUse1000
	private Indicator objIndicator4;//准贷记卡近12个月逾期达4期或以上 sccOve4For1Year
	private String result;
//	private ApplyRefuseCode applyRefuseCode;


}
