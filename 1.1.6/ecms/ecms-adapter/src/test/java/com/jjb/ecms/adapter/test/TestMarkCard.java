/**
 * 
 */
package com.jjb.ecms.adapter.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jjb.ecms.adapter.client.socket.ext.ImmediatelyBuildCardServiceImpl;
import com.jjb.ecms.adapter.client.socket.ext.MessageSendServiceImpl;
/**
 *@ClassName TestSend
 *@Description 发送交易到九江银行九江综合前置测试
 *@Author lixing
 *Date 2018/10/17 20:16
 *Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/service-context.xml")
public class TestMarkCard {


	@Autowired
	private ImmediatelyBuildCardServiceImpl immediatelyBuildCardServiceImpl;

	@Autowired
	private MessageSendServiceImpl messageSendServiceImpl;
	@Test
	public void getTnty() throws Exception {
//		messageSendServiceImpl.Trans0005(new Trans0005Req());

//      StringBuffer xmlReq = new StringBuffer();
//      xmlReq.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
//      xmlReq.append("<service>");
//      xmlReq.append("<SYS_HEAD>");
//
//      xmlReq.append("<Mac>"+"1111111111111111"+"</Mac>");
//      xmlReq.append("<MsgId>"+"11111111111111111111111111111111"+"</MsgId>");
//      xmlReq.append("<SourceSysId>"+"1005"+"</SourceSysId>");
//      xmlReq.append("<ConsumerId>"+"1005"+"</ConsumerId>");
//      xmlReq.append("<ServiceCode>"+"2004800059"+"</ServiceCode>");
//      xmlReq.append("<ServiceScene>"+"01"+"</ServiceScene>");
//
//      xmlReq.append("</SYS_HEAD>");
//      xmlReq.append("<APP_HEAD>");
//
//      xmlReq.append("<TranDate>"+TranDate+"</TranDate>");
//      xmlReq.append("<TranTime>"+TranTime+"</TranTime>");
//      xmlReq.append("<TranTellerNo>"+"888888"+"</TranTellerNo>");
//      xmlReq.append("<TranSeqNo>"+ GUIDHelper.getGUID("1005")+"</TranSeqNo>");
//      xmlReq.append("<GlobalSeqNo>"+ GUIDHelper.getGUID("1005")+"</GlobalSeqNo>");
//      xmlReq.append("<BranchId>"+StringUtils.valueOf(T9000Req.servId)+"</BranchId>");
//
//      xmlReq.append("</APP_HEAD>");
//      xmlReq.append("<BODY>");
//      xmlReq.append(body);

//      xmlReq.append("<BscSuppInd>B</BscSuppInd>");
//      xmlReq.append("<ProductCd>1001011</ProductCd>");
//      xmlReq.append("<AppType>A</AppType>");
//      xmlReq.append("<AppNo>1001</AppNo>");
//      xmlReq.append("<PosPinVerifyInd>Y</PosPinVerifyInd>");
//      xmlReq.append("<IdType>I</IdType>");
//      xmlReq.append("<IdNo>421121199406047616</IdNo>");
//      xmlReq.append("<MobileNo>13162931312</MobileNo>");
//      xmlReq.append("<CardFetchMethod>A</CardFetchMethod>");
//      xmlReq.append("<CreditLimit>1000</CreditLimit>");
//      xmlReq.append("<StmtMediaType>E</StmtMediaType>");
//      xmlReq.append("<DesignCd>1003</DesignCd>");
//      xmlReq.append("<EmbName>CHENGGANG</EmbName>");
//      xmlReq.append("<OwningBranch>九江</OwningBranch>");
//      xmlReq.append("<SalesInd>Y</SalesInd>");
//      xmlReq.append("<SpecLoanInd>N</SpecLoanInd>");
//      xmlReq.append("<Guaranty>N</Guaranty>");
//      xmlReq.append("<ChannelId>01</ChannelId>");
//      xmlReq.append("<CardNo></CardNo>");
//      xmlReq.append("<PrimCardNo></PrimCardNo>");
//      xmlReq.append("<BscLogicalCardNo></BscLogicalCardNo>");
//      xmlReq.append("<PrimAppId></PrimAppId>");
//      xmlReq.append("<PhotoUseFlag></PhotoUseFlag>");
//      xmlReq.append("<BankMemberNo></BankMemberNo>");
//      xmlReq.append("<Name>勒布朗.詹姆斯</Name>");
//      xmlReq.append("<Gender></Gender>");
//      xmlReq.append("<Birthday></Birthday>");
//      xmlReq.append("<MaritalStatus></MaritalStatus>");
//      xmlReq.append("<Nationality></Nationality>");
//      xmlReq.append("<NbrOfDependents></NbrOfDependents>");
//      xmlReq.append("<Qualification></Qualification>");
//      xmlReq.append("<HomeAddress></HomeAddress>");
//      xmlReq.append("<HomeZip></HomeZip>");
//      xmlReq.append("<HomeStandFrom></HomeStandFrom>");
//      xmlReq.append("<HomePhone></HomePhone>");
//      xmlReq.append("<Email></Email>");
//      xmlReq.append("<HouseOwnership></HouseOwnership>");
//      xmlReq.append("<HouseLoanFlag></HouseLoanFlag>");
//      xmlReq.append("<CompanyName></CompanyName>");
//      xmlReq.append("<CompanyAddress></CompanyAddress>");
//      xmlReq.append("<CompanyZip></CompanyZip>");
//      xmlReq.append("<CompanyPhone></CompanyPhone>");
//      xmlReq.append("<CompanyCategory></CompanyCategory>");
//      xmlReq.append("<IndustryCategory></IndustryCategory>");
//      xmlReq.append("<Occupation></Occupation>");
//      xmlReq.append("<TitleOfTechnical></TitleOfTechnical>");
//      xmlReq.append("<Title></Title>");
//      xmlReq.append("<RevenuePerYear></RevenuePerYear>");
//      xmlReq.append("<FamilyAverageRevenue></FamilyAverageRevenue>");
//      xmlReq.append("<DdInd></DdInd>");
//      xmlReq.append("<DdBankBranch></DdBankBranch>");
//      xmlReq.append("<DdBankAcctNo></DdBankAcctNo>");
//      xmlReq.append("<ObligateQuestion></ObligateQuestion>");
//      xmlReq.append("<ObligateAnswer></ObligateAnswer>");
//      xmlReq.append("<ContactName></ContactName>");
//      xmlReq.append("<ContactRelationship></ContactRelationship>");
//      xmlReq.append("<ContactIdType></ContactIdType>");
//      xmlReq.append("<ContactIdNo></ContactIdNo>");
//      xmlReq.append("<ContactCorpPhone></ContactCorpPhone>");
//      xmlReq.append("<CivilServiceLimit></CivilServiceLimit>");
//      xmlReq.append("<MicroCreditLimit></MicroCreditLimit>");
//      xmlReq.append("<BusinessLimit></BusinessLimit>");
//      xmlReq.append("<CarLoan></CarLoan>");
//      xmlReq.append("<HouseLoan></HouseLoan>");
//      xmlReq.append("<RenovationLoan></RenovationLoan>");
//      xmlReq.append("<TravelLoan></TravelLoan>");
//      xmlReq.append("<WeddingLoan></WeddingLoan>");
//      xmlReq.append("<DurableLoan></DurableLoan>");
//      xmlReq.append("<CardCycleLimit></CardCycleLimit>");
//      xmlReq.append("<CardCycleCashLimit></CardCycleCashLimit>");
//      xmlReq.append("<CardCycleNetLimit></CardCycleNetLimit>");
//      xmlReq.append("<CardTxnLimit></CardTxnLimit>");
//      xmlReq.append("<CardTxnCashLimit></CardTxnCashLimit>");
//      xmlReq.append("<CardTxnNetLimit></CardTxnNetLimit>");
//      xmlReq.append("<StmtMailAddrInd></StmtMailAddrInd>");
//      xmlReq.append("<CobrandNo></CobrandNo>");
//      xmlReq.append("<HomeCountryCd></HomeCountryCd>");
//      xmlReq.append("<HomeState></HomeState>");
//      xmlReq.append("<HomeCity></HomeCity>");
//      xmlReq.append("<HomeDistrict></HomeDistrict>");
//      xmlReq.append("<CompanyCountryCd></CompanyCountryCd>");
//      xmlReq.append("<CompanyState></CompanyState>");
//      xmlReq.append("<CompanyCity></CompanyCity>");
//      xmlReq.append("<CompanyDistrict></CompanyDistrict>");
//      xmlReq.append("<ContactBirthday></ContactBirthday>");
//      xmlReq.append("<ContactCorpPost></ContactCorpPost>");
//      xmlReq.append("<ContactCorpFax></ContactCorpFax>");
//      xmlReq.append("<ContactCorpName></ContactCorpName>");
//      xmlReq.append("<ContactGender></ContactGender>");
//      xmlReq.append("<ContactMobileNo></ContactMobileNo>");
//      xmlReq.append("<DdBankAcctName></DdBankAcctName>");
//      xmlReq.append("<DdBankName></DdBankName>");
//      xmlReq.append("<RelationshipToBsc></RelationshipToBsc>");
//      xmlReq.append("<AppPromotionCd></AppPromotionCd>");
//      xmlReq.append("<AppSource></AppSource>");
//      xmlReq.append("<BarCode></BarCode>");
//      xmlReq.append("<RecomCardNo></RecomCardNo>");
//      xmlReq.append("<RecomName></RecomName>");
//      xmlReq.append("<RepresentName></RepresentName>");
//      xmlReq.append("<DriveLicenseId></DriveLicenseId>");
//      xmlReq.append("<DriveLicRegDate></DriveLicRegDate>");
//      xmlReq.append("<EmpStability></EmpStability>");
//      xmlReq.append("<EmpStatus></EmpStatus>");
//      xmlReq.append("<HouseType></HouseType>");
//      xmlReq.append("<IdIssuerAddress></IdIssuerAddress>");
//      xmlReq.append("<LanguageInd></LanguageInd>");
//      xmlReq.append("<LiquidAsset></LiquidAsset>");
//      xmlReq.append("<PrOfCountry></PrOfCountry>");
//      xmlReq.append("<ResidencyCountryCd></ResidencyCountryCd>");
//      xmlReq.append("<SocialInsAmt></SocialInsAmt>");
//      xmlReq.append("<SocialStatus></SocialStatus>");
//      xmlReq.append("<CompanyFax></CompanyFax>");
//      xmlReq.append("<CardMailerInd></CardMailerInd>");
//      xmlReq.append("<BillingCycle></BillingCycle>");
//      xmlReq.append("<CardExpireDate></CardExpireDate>");
//      xmlReq.append("<BankCustomerId></BankCustomerId>");
//      xmlReq.append("<CustRateDiscount></CustRateDiscount>");
//      xmlReq.append("<ContactOName></ContactOName>");
//      xmlReq.append("<ContactORelationship></ContactORelationship>");
//      xmlReq.append("<ContactOIdType></ContactOIdType>");
//      xmlReq.append("<ContactOIdNo></ContactOIdNo>");
//      xmlReq.append("<ContactOMobileNo></ContactOMobileNo>");
//      xmlReq.append("<ContactOCorpName></ContactOCorpName>");
//      xmlReq.append("<ContactOCorpPost></ContactOCorpPost>");
//      xmlReq.append("<ContactOCorpPhone></ContactOCorpPhone>");
//      xmlReq.append("<IsUrgentCard></IsUrgentCard>");
//      xmlReq.append("<EmpDepartment></EmpDepartment>");
//      xmlReq.append("<CreateUser></CreateUser>");
//      xmlReq.append("<FetchBranch></FetchBranch>");
//      xmlReq.append("<Degree></Degree>");
//      xmlReq.append("<CorpBegDate></CorpBegDate>");
//      xmlReq.append("<EcifNo></EcifNo>");
//      xmlReq.append("<QUicsTxnInd></QUicsTxnInd>");
//      xmlReq.append("<IdLastDate></IdLastDate>");
//      xmlReq.append("<LoanInitTerm></LoanInitTerm>");
//      xmlReq.append("<LoanInitPrin></LoanInitPrin>");
//      xmlReq.append("<LoanFeeMethod></LoanFeeMethod>");
//      xmlReq.append("<DistributeMethod></DistributeMethod>");
//      xmlReq.append("<LoanFeeCalcMethod></LoanFeeCalcMethod>");
//      xmlReq.append("<FeeRate></FeeRate>");
//      xmlReq.append("<FeeAmt></FeeAmt>");
//      xmlReq.append("<RlBankName></RlBankName>");
//      xmlReq.append("<RlBankBranch></RlBankBranch>");
//      xmlReq.append("<RlBankAcctNo></RlBankAcctNo>");
//      xmlReq.append("<RlBankAcctName></RlBankAcctName>");
//      xmlReq.append("<LoanTarget></LoanTarget>");
//      xmlReq.append("<LargeLoanSendMode></LargeLoanSendMode>");
//      xmlReq.append("<DdOtherBankInd></DdOtherBankInd>");
//      xmlReq.append("<CustLargeSpecLimit></CustLargeSpecLimit>");
//      xmlReq.append("<SpecLoanType></SpecLoanType>");
//      xmlReq.append("<LoanMerchantId></LoanMerchantId>");
//      xmlReq.append("<LoanProgramId></LoanProgramId>");
//      xmlReq.append("<MarketerName></MarketerName>");
//      xmlReq.append("<MarketerId></MarketerId>");
//      xmlReq.append("<MarketerBranchId></MarketerBranchId>");
//      xmlReq.append("<LoanPrinApp></LoanPrinApp>");

//      xmlReq.append("</BODY>");
//      xmlReq.append("</service>");
//		immediatelyBuildCardServiceImpl.Trans0059(new Trans0059Req());

	}
}
