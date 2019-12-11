package com.jjb.ecms.adapter.test;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jjb.ecms.util.IdentificationCodeUtil;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.facility.util.StringUtils;

public class CallCreditSysService {

	private static Logger logger = LoggerFactory.getLogger(CallCreditSysService.class);

	public static void sendMessage(String idNo,String name,int i) {
		String gender = IdentificationCodeUtil.getGender(idNo);
		Date brD = IdentificationCodeUtil.getBirthdayDate(idNo);		
		String brDay = DateUtils.dateToString(brD, DateUtils.DAY_YMD);
		try {

			// socket传字符串
//			out = new DataOutputStream(s.getOutputStream());
			//申请进度查询[T1000]----START
//			String xmlReq = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><service><ServiceBody><Request><AppSource>02</AppSource><CurPage>1</CurPage><RowCnt>30</RowCnt><Spreader>IT</Spreader><StateType></StateType></Request></ServiceBody><ServiceHeader><ChannelId>02</ChannelId><Mac>0000000000000000</Mac><Org>000064540000</Org><RequstTime>20190117160169</RequstTime><ServiceId>T1000</ServiceId><ServiceSn>35d3b676-af9f-4fed-a043-743582349a94</ServiceSn><VersionId>01</VersionId></ServiceHeader></service>";
				//微信请求
//			String xmlReq = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><process><ServiceBody><Name>张三</Name></ServiceBody><ServiceHeader><ChannelId>02</ChannelId><Mac>0000000000000000</Mac><Org>000064540000</Org><RequstTime>2018102911010709</RequstTime><ServiceId>T1000</ServiceId><ServiceSn>9a98fc20-3bdc-491b-b839-00a4ba025e0e</ServiceSn><VersionId>01</VersionId></ServiceHeader></process>";
//			String xmlReq = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><process><ServiceBody><Request><AppNo>20181024100000000075</AppNo></Request></ServiceBody><ServiceHeader><ChannelId>02</ChannelId><Mac>0000000000000000</Mac><Org>000064540000</Org><RequstTime>2018102971110235</RequstTime><ServiceId>T1000</ServiceId><ServiceSn>53a087e8-517c-435a-ad20-4a1c53bf2786</ServiceSn><VersionId>01</VersionId></ServiceHeader></process>";
			//申请进度查询----END
			
			//准入及老客户查询[T1002]----START
//			String xmlReq = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Service><ServiceHeader><ServiceSn>7162658026174615327</ServiceSn><ServiceId>T1002</ServiceId><Org>000064540000</Org><ChannelId>06</ChannelId><RequstTime>20180427162658</RequstTime><VersionId>01</VersionId></ServiceHeader><ServiceBody><Request><IdType>I</IdType><IdNo>430523198904020716</IdNo><Name>李李</Name><Cellphone></Cellphone><AppSource></AppSource><ProductCd>000002</ProductCd><InputNo>工号</InputNo><InputName>录入人</InputName></Request></ServiceBody></Service>";
			//准入及老客户查询[T1002]----END			
			
			//进件提交[T1005]--------START
				//根据多客户 替换姓名，号码，证件号码
			String xmlReq ="<?xml version=\"1.0\" encoding=\"UTF-8\"?><service><ServiceBody><Request><AppLmt>12312</AppLmt><AppSource>51</AppSource><AppType>B</AppType><ApplyType>N</ApplyType><Org>000064540000</Org><OwningBranch>06101</OwningBranch><ProductCd>510001</ProductCd><RealtimeIssuingFlag>Y</RealtimeIssuingFlag><Custs><Cust><Age>25</Age><Birthday>"+brDay+"</Birthday><BscSuppind>B</BscSuppind><CardMailerInd>H</CardMailerInd><Cellphone>13052292100</Cellphone><CorpName>按实际大家</CorpName><Degree>U</Degree><Email>871284791754@qq.com</Email><EmbLogo>CE YUAN</EmbLogo><EmpAdd>爱上你考虑到了</EmpAdd><EmpAddrCtryCd>156</EmpAddrCtryCd><EmpCity>北京市</EmpCity><EmpDepartment>纤维球</EmpDepartment><EmpPhone>0351-7827353</EmpPhone><EmpPost>D</EmpPost><EmpProvince>北京市</EmpProvince><EmpStructure>H</EmpStructure><EmpType>J1</EmpType><EmpWorkyears>1</EmpWorkyears><EmpZone>东城区</EmpZone><Gender>"+gender+"</Gender><HomeAdd>奥斯卡了加大加</HomeAdd><HomeAddrCtryCd>156</HomeAddrCtryCd><HomeCity>合肥市</HomeCity><HomeState>安徽省</HomeState><HomeZone>瑶海区</HomeZone><HouseOwnership>E</HouseOwnership><IdLastAll>Y</IdLastAll><IdLastDate null=\"true\"/><IdNo>"+idNo+"</IdNo><IdType>I</IdType><MaritalStatus>S</MaritalStatus><Name>"+name+"</Name><Nationality>156</Nationality><OtherAsk>无</OtherAsk><PhotoUseFlag>N</PhotoUseFlag><PosPinVerifyInd>Y</PosPinVerifyInd><Qualification>B</Qualification><ResidencyCountryCd>156</ResidencyCountryCd><SmAmtVerifyInd>Y</SmAmtVerifyInd><YearIncome>12</YearIncome></Cust></Custs><DdBankAcctName>爱打架</DdBankAcctName><DdBankAcctNo null=\"true\"/><DdBankName null=\"true\"/><DdInd null=\"true\"/><HouseMonthyAmt>1202</HouseMonthyAmt><InputDate>20190116031130</InputDate><InputName null=\"true\"/><InputNo>34122719930226701X</InputNo><IsPrdChange>Y</IsPrdChange><SpreaderMode>A</SpreaderMode><SpreaderName>苏胜龙</SpreaderName><SpreaderNo>34122719930226701X</SpreaderNo><SpreaderOrg>06101</SpreaderOrg><SpreaderType>C</SpreaderType><StmtMailAddrInd>H</StmtMailAddrInd><StmtMediaType>E</StmtMediaType><BuildingName>是,每年的法律南斯拉夫</BuildingName><CardFetchMethod>E</CardFetchMethod><CardMailerInd>H</CardMailerInd><ClientType>A00</ClientType><ContactMobile>13052928398</ContactMobile><ContactName>爱迪生</ContactName><ContactOmobile>13054112312</ContactOmobile><ContactOname>阿萨德</ContactOname><ContactOrelation>W</ContactOrelation><ContactRelation>W</ContactRelation><ExtCheckIdFlag>1</ExtCheckIdFlag><ExtCheckIdRs>{\"AFR\": [{\"checkRs\": \"F\",\"currCnt\": 1,\"checkRsDesc\": \"核验接口调用超时\"},{\"checkRs\": \"S\",\"currCnt\": 2,\"checkRsDesc\": \"成功\"}]}</ExtCheckIdRs><SubmitType>SUBMIT</SubmitType></Request></ServiceBody><ServiceHeader><ChannelId>02</ChannelId><Mac>0000000000000000</Mac><Org>000064540000</Org><RequstTime>201901161501900</RequstTime><ServiceId>T1005</ServiceId><ServiceSn>c2a59040-e98e-4513-a8b0-824fd9f425c3</ServiceSn><VersionId>01</VersionId></ServiceHeader></service>";

//			String xmlReq = "<service><ServiceBody><Request><AppLmt>20000</AppLmt><AppSource>05</AppSource><AppType>B</AppType><ApplyType>N</ApplyType><BuildingName>bloomy</BuildingName><CardFetchMethod>E</CardFetchMethod><CardMailerInd>C</CardMailerInd><ClientType>A00</ClientType><ContactMobile>15236699999</ContactMobile><ContactName>com</ContactName><ContactOmobile>15236998888</ContactOmobile><ContactOname>天天</ContactOname><ContactOrelation>W</ContactOrelation><ContactRelation>W</ContactRelation><Custs><Cust><Age>30</Age><AppSource>51cc</AppSource><Birthday>19890119</Birthday><BscSuppind>B</BscSuppind><CardMailerInd>C</CardMailerInd><Cellphone>15514785699</Cellphone><CorpName>上海锦咏数据</CorpName><Degree>U</Degree><Email>rghb@163.com</Email><EmbLogo>WANG LILI</EmbLogo><EmpAdd> 哦路欧诺</EmpAdd><EmpAddrCtryCd>156</EmpAddrCtryCd><EmpCity>北京市</EmpCity><EmpDepartment>测试</EmpDepartment><EmpPhone>027-25699666</EmpPhone><EmpPost>C</EmpPost><EmpProvince>辽宁省</EmpProvince><EmpStructure>L</EmpStructure><EmpType>J1</EmpType><EmpWorkyears>85</EmpWorkyears><EmpZone>东城区</EmpZone><Gender>F</Gender><HomeAdd>村口额</HomeAdd><HomeAddrCtryCd>156</HomeAddrCtryCd><HomeCity>合肥市</HomeCity><HomeState>安徽省</HomeState><HomeZone>瑶海区</HomeZone><HouseOwnership>D</HouseOwnership><IdLastAll>Y</IdLastAll><IdLastDate null=\"true\"/><IdNo>440111198901194205</IdNo><IdType>I</IdType><MaritalStatus>M</MaritalStatus><Name>后台测试一</Name><Nationality>156</Nationality><OtherAsk>无</OtherAsk><OwningBranch null=\"true\"/><PhotoUseFlag>N</PhotoUseFlag><PosPinVerifyInd>Y</PosPinVerifyInd><ProductCd>000003</ProductCd><Qualification>D</Qualification><ResidencyCountryCd>156</ResidencyCountryCd><SmAmtVerifyInd>Y</SmAmtVerifyInd><SubmitType>SUBMIT</SubmitType><YearIncome>16</YearIncome><idNo>420624198702093626</idNo><spreader>420624198702093626</spreader></Cust></Custs><DdBankAcctName>王丽丽</DdBankAcctName><DdBankAcctNo null=\"true\"/><DdBankName null=\"true\"/><DdInd null=\"true\"/><HouseMonthyAmt>3569</HouseMonthyAmt><InputDate>20190119112616</InputDate><InputName null=\"true\"/><InputNo>420624198702093626</InputNo><IsPrdChange>Y</IsPrdChange><Org>000064540000</Org><OwningBranch>06101</OwningBranch><ProductCd>000003</ProductCd><RealtimeIssuingFlag>Y</RealtimeIssuingFlag><SpreaderMode>A</SpreaderMode><SpreaderName>代然</SpreaderName><SpreaderNo>420624198702093626</SpreaderNo><SpreaderOrg>06101</SpreaderOrg><SpreaderType>C</SpreaderType><StmtMailAddrInd>C</StmtMailAddrInd><StmtMediaType>E</StmtMediaType><SubmitType>SUBMIT</SubmitType></Request></ServiceBody><ServiceHeader><ChannelId>02</ChannelId><Mac>0000000000000000</Mac><Org>000064540000</Org><RequstTime>201901191101482</RequstTime><ServiceId>T1005</ServiceId><ServiceSn>0d9976a6-e978-401f-a6dc-073868af18d6</ServiceSn><VersionId>01</VersionId></ServiceHeader></service>";	
//			微信
//			String xmlReq = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><process><ServiceBody><Request><AppSource>02</AppSource><AppType>B</AppType><ApplyType>N</ApplyType><ClientType>A00</ClientType><Custs><Cust><Age>25</Age><AppSource>51cc</AppSource><Birthday>19930226</Birthday><BscSuppind>B</BscSuppind><CardFetchMethod>E</CardFetchMethod><CardMailerInd>无</CardMailerInd><Cellphone>13052292102</Cellphone><ContactMobile>13053494654</ContactMobile><ContactName>打开</ContactName><ContactRelation>O</ContactRelation><CorpName>看到</CorpName><DdBankAcctName>苏胜龙</DdBankAcctName><DdBankAcctNo/><DdInd/><Degree>U</Degree><Email>djjdjd@163.com</Email><EmbLogo>SU SHENGLONG</EmbLogo><EmpAdd>附近</EmpAdd><EmpAddrCtryCd>156</EmpAddrCtryCd><EmpCity>景德镇市</EmpCity><EmpPhone>13000000000</EmpPhone><EmpPost>B</EmpPost><EmpProvince>江西省</EmpProvince><EmpStructure>F</EmpStructure><EmpWorkyears>1</EmpWorkyears><EmpZone>珠山区</EmpZone><Gender>M</Gender><HomeAdd>附近解放军队</HomeAdd><HomeAddrCtryCd>156</HomeAddrCtryCd><HomeCity>上饶市</HomeCity><HomeState>江西省</HomeState><HomeZone>上饶县</HomeZone><HouseOwnership>A</HouseOwnership><IdLastAll>N</IdLastAll><IdLastDate>20211106</IdLastDate><IdNo>34122719930226701X</IdNo><IdType>I</IdType><InputDate>20181106032814</InputDate><InputName>于海</InputName><InputNo>5362</InputNo><IsPrdChange>Y</IsPrdChange><MaritalStatus>M</MaritalStatus><Name>苏胜龙</Name><Nationality>156</Nationality><OtherAsk>无</OtherAsk><PhotoUseFlag>N</PhotoUseFlag><PosPinVerifyInd>Y</PosPinVerifyInd><ProductCd>000003</ProductCd><Qualification>A</Qualification><ResidencyCountryCd>156</ResidencyCountryCd><SmAmtVerifyInd>Y</SmAmtVerifyInd><StmtMailAddrInd>C</StmtMailAddrInd><StmtMediaType>E</StmtMediaType><SubmitType>SUBMIT</SubmitType><YearIncome>A</YearIncome><workNumber>5362</workNumber><ExtCheckIdFlag>1</ExtCheckIdFlag><ExtCheckIdRs>{\"AFR\": [{\"checkRs\": \"F\",\"currCnt\": 1,\"checkRsDesc\": \"核验接口调用超时\"},{\"checkRs\": \"S\",\"currCnt\": 2,\"checkRsDesc\": \"成功\"}]}</ExtCheckIdRs></Cust></Custs><Org>000064540000</Org><OwningBranch>4564</OwningBranch><ProductCd>000003</ProductCd><RealtimeIssuingFlag>Y</RealtimeIssuingFlag><SubmitType>SUBMIT</SubmitType></Request></ServiceBody><ServiceHeader><ChannelId>02</ChannelId><Mac>0000000000000000</Mac><Org>000064540000</Org><RequstTime>2018113101511162</RequstTime><ServiceId>T1005</ServiceId><ServiceSn>fa9969c4-85be-4d4c-a28c-bf6bf64c9313</ServiceSn><VersionId>01</VersionId></ServiceHeader></process>";
			//进件提交--------END
			
			//推广员查询[T1007]----START
//			String xmlReq = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Service><ServiceHeader><ServiceSn>7162658026174615327</ServiceSn><ServiceId>T1007</ServiceId><Org>000064540000</Org><ChannelId>06</ChannelId><RequstTime>20180427162658</RequstTime><VersionId>01</VersionId></ServiceHeader><ServiceBody><Request><UserNo>3839</UserNo><PhoneNo></PhoneNo><RtfState></RtfState><BranchCode></BranchCode><CurPage>1</CurPage><RowCnt>50</RowCnt></Request></ServiceBody></Service>";
//			String xmlReq="<?xml version=\"1.0\" encoding=\"UTF-8\"?><process><ServiceHeader><ChannelId>02</ChannelId><Mac>0000000000000000</Mac><Org>000064540000</Org><RequstTime>2018102921410187</RequstTime><ServiceId>T1007</ServiceId><ServiceSn>fa3d176e-22b1-4bc0-ba43-52906e54a8e8</ServiceSn><VersionId>01</VersionId></ServiceHeader></process>";
			//推广员查询----END

			//预审提交[T1006]----START
//			String xmlReq = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Service><ServiceHeader><ServiceSn>7162658026174615327</ServiceSn><ServiceId>T1006</ServiceId><Org>000064540000</Org><ChannelId>06</ChannelId><RequstTime>20180427162658</RequstTime><VersionId>01</VersionId></ServiceHeader><ServiceBody><Request><AppNo>"+idNo+"</AppNo><OpUserNo>IT</OpUserNo><SignFileInd>Y</SignFileInd><IdFileInd>Y</IdFileInd><JobFileInd>Y</JobFileInd><ConfirmType>P</ConfirmType><Pics><Pic><ImageNum>20181019130000000027</ImageNum><PicTypeCode>A0</PicTypeCode><PicTypeDesc>身份证件正面</PicTypeDesc><PicName>id001.jpg</PicName><PicSort>1</PicSort><PicUrl>http://10.109.3.215/abc/abc/abc.jpg</PicUrl></Pic><Pic><ImageNum>20181019130000000027</ImageNum><PicTypeCode>A1</PicTypeCode><PicTypeDesc>身份证件反面</PicTypeDesc><PicName>id002.jpg</PicName><PicSort>1</PicSort><PicUrl>http://10.109.3.215/abc/abc/abc1.jpg</PicUrl></Pic></Pics></Request></ServiceBody></Service>";
//			String xmlReq = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><service><ServiceBody><Request><AppNo>20190108180000001547</AppNo><Branch>08101</Branch><CfOwningBranch>08101</CfOwningBranch><ConfirmType>P</ConfirmType><IdFileInd>A</IdFileInd><JobFileInd>A</JobFileInd><OpUserNo>0026</OpUserNo><Picss><ImageNum>20190108180000001547</ImageNum><PicName>41018219921010333X_0026_G_20190108063551876828.jpeg</PicName><PicSort>5</PicSort><PicTypeCode>G</PicTypeCode><PicTypeDesc>房产证明</PicTypeDesc><PicUrl>/20190108/</PicUrl><ImageNum>20190108180000001547</ImageNum><PicName>41018219921010333X_0026_G_20190108063559562342.jpeg</PicName><PicSort>5</PicSort><PicTypeCode>G</PicTypeCode><PicTypeDesc>房产证明</PicTypeDesc><PicUrl>/20190108/</PicUrl><ImageNum>20190108180000001547</ImageNum><PicName>41018219921010333X_0026_G_20190108063606511775.jpeg</PicName><PicSort>5</PicSort><PicTypeCode>G</PicTypeCode><PicTypeDesc>房产证明</PicTypeDesc><PicUrl>/20190108/</PicUrl><ImageNum>20190108180000001547</ImageNum><PicName>41018219921010333X_0026_G_20190108063614035728.jpeg</PicName><PicSort>5</PicSort><PicTypeCode>G</PicTypeCode><PicTypeDesc>房产证明</PicTypeDesc><PicUrl>/20190108/</PicUrl><ImageNum>20190108180000001547</ImageNum><PicName>41018219921010333X_0026_F1_20190108063712711816.jpeg</PicName><PicSort>5</PicSort><PicTypeCode>F1</PicTypeCode><PicTypeDesc>营业执照</PicTypeDesc><PicUrl>/20190108/</PicUrl></Picss><SignFileInd>A</SignFileInd><SpreaderMode>ABCD</SpreaderMode><SpreaderType>A</SpreaderType><Pics><Pic><ImageNum>20190108180000001547</ImageNum><PicName>/20190108/41018219921010333X_0026_N1_20190108063448434833.jpeg</PicName><PicSort>1</PicSort><PicTypeCode>N1</PicTypeCode><PicTypeDesc>申请人与客户进件合照</PicTypeDesc><PicUrl>/20190108/41018219921010333X_0026_N1_20190108063448434833.jpeg</PicUrl></Pic><Pic><ImageNum>20190108180000001547</ImageNum><PicName>/20190108/41018219921010333X_0026_N2_20190108063501676773.jpeg</PicName><PicSort>2</PicSort><PicTypeCode>N2</PicTypeCode><PicTypeDesc>申请人声明及签字</PicTypeDesc><PicUrl>/20190108/41018219921010333X_0026_N2_20190108063501676773.jpeg</PicUrl></Pic><Pic><ImageNum>20190108180000001547</ImageNum><PicName>/20190108/41018219921010333X_0026_A1_20190108063523887511.jpeg</PicName><PicSort>3</PicSort><PicTypeCode>A1</PicTypeCode><PicTypeDesc>身份>证正面</PicTypeDesc><PicUrl>/20190108/41018219921010333X_0026_A1_20190108063523887511.jpeg</PicUrl></Pic><Pic><ImageNum>20190108180000001547</ImageNum><PicName>/20190108/41018219921010333X_0026_A0_20190108063534735432.jpeg</PicName><PicSort>4</PicSort><PicTypeCode>A0</PicTypeCode><PicTypeDesc>身份证反面</PicTypeDesc><PicUrl>/20190108/41018219921010333X_0026_A0_20190108063534735432.jpeg</PicUrl></Pic><Pic><ImageNum>20190108180000001547</ImageNum><PicName>/20190108/41018219921010333X_0026_G_20190108063551876828.jpeg</PicName><PicSort>5</PicSort><PicTypeCode>G</PicTypeCode><PicTypeDesc>房产证明</PicTypeDesc><PicUrl>/20190108/41018219921010333X_0026_G_20190108063551876828.jpeg</PicUrl></Pic><Pic><ImageNum>20190108180000001547</ImageNum><PicName>/20190108/41018219921010333X_0026_G_20190108063559562342.jpeg</PicName><PicSort>5</PicSort><PicTypeCode>G</PicTypeCode><PicTypeDesc>房产证明</PicTypeDesc><PicUrl>/20190108/41018219921010333X_0026_G_20190108063559562342.jpeg</PicUrl></Pic><Pic><ImageNum>20190108180000001547</ImageNum><PicName>/20190108/41018219921010333X_0026_G_20190108063606511775.jpeg</PicName><PicSort>5</PicSort><PicTypeCode>G</PicTypeCode><PicTypeDesc>房产证明</PicTypeDesc><PicUrl>/20190108/41018219921010333X_0026_G_20190108063606511775.jpeg</PicUrl></Pic><Pic><ImageNum>20190108180000001547</ImageNum><PicName>/20190108/41018219921010333X_0026_G_20190108063614035728.jpeg</PicName><PicSort>5</PicSort><PicTypeCode>G</PicTypeCode><PicTypeDesc>房产证明</PicTypeDesc><PicUrl>/20190108/41018219921010333X_0026_G_20190108063614035728.jpeg</PicUrl></Pic><Pic><ImageNum>20190108180000001547</ImageNum><PicName>/20190108/41018219921010333X_0026_F1_20190108063712711816.jpeg</PicName><PicSort>5</PicSort><PicTypeCode>F1</PicTypeCode><PicTypeDesc>营业执>照</PicTypeDesc><PicUrl>/20190108/41018219921010333X_0026_F1_20190108063712711816.jpeg</PicUrl></Pic></Pics></Request></ServiceBody><ServiceHeader><ChannelId>02</ChannelId><Mac>0000000000000000</Mac><Org>000064540000</Org><RequstTime>201901081801901</RequstTime><ServiceId>T1006</ServiceId><ServiceSn>e3f19511-db9f-433e-852e-87354cc6195d</ServiceSn><VersionId>01</VersionId></ServiceHeader></service>";
			//预审提交----END
			//外部决策[T1008]----START
//			String xmlReq = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Service><ServiceHeader><ServiceSn>7162658026174615327</ServiceSn><ServiceId>T1008</ServiceId><Org>000064540000</Org><ChannelId>06</ChannelId><RequstTime>20180427162658</RequstTime><VersionId>01</VersionId></ServiceHeader><ServiceBody><Request><AppNo>20181101100000000107</AppNo><FinalResult>1</FinalResult><FinalCreditLmt>0</FinalCreditLmt><FinalReason></FinalReason><FinalConfirmTime>20181127144455</FinalConfirmTime><ConfirmType>P</ConfirmType></Request></ServiceBody></Service>";
//			String xmlReq = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><Service><ServiceHeader><ServiceSn>000579</ServiceSn><ServiceId>T1008</ServiceId><Org>000064540000</Org><ChannelId>51</ChannelId><RequstTime>20190111172804</RequstTime><VersionId>01</VersionId></ServiceHeader><ServiceBody><Request><AppNo>20190111160000001591</AppNo><FinalResult>2</FinalResult><FinalCreditLmt>0.00</FinalCreditLmt><FinalReason></FinalReason><FinalConfirmTime>20180810000000</FinalConfirmTime></Request></ServiceBody></Service>";
			//外部决策[T1008]----END
			//排行榜[T1009]----START
//			String xmlReq = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><service><ServiceBody><Request><AppSource>05</AppSource><QuyType>2</QuyType><SpreaderNO>34122719930226701X</SpreaderNO><TotalCnt>30</TotalCnt></Request></ServiceBody><ServiceHeader><ChannelId>02</ChannelId><Mac>0000000000000000</Mac><Org>000064540000</Org><RequstTime>201901171401833</RequstTime><ServiceId>T1009</ServiceId><ServiceSn>fb9bcca2-4398-469f-ac39-0de732c13a22</ServiceSn><VersionId>01</VersionId></ServiceHeader></service>";
			//排行榜[T1009]----END
			//新征审-推送审批结果
//			String xmlReq = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Service><ServiceHeader><RequstTime>20180503000038</RequstTime><ChannelId>cas</ChannelId><ServiceId>90110</ServiceId><ServiceSn>1525248536022</ServiceSn><Org>000064540000</Org><OpId>sysauto</OpId><VersionId>01</VersionId></ServiceHeader><ServiceBody><EXT_ATTRIBUTES/><Request><APP_NO>20180420170000000041</APP_NO><RTF_STATE>M05</RTF_STATE><ACC_LMT></ACC_LMT><REFUSE_CODE>CD-信用拒绝-[D005,D006]-[累计逾期期数>=10,贷款当前逾期期数>=1且金额>=500]</REFUSE_CODE><APPLY_DATE>20180420</APPLY_DATE><UPDATE_DATE>20180424</UPDATE_DATE></Request></ServiceBody></Service>";
			//11001-补件查询及提交-
//			String xmlReq = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Service><ServiceHeader><ServiceSn>2140544005510740747</ServiceSn><ServiceId>11001</ServiceId><Org>000064540000</Org><ChannelId>06</ChannelId><RequstTime>20180502140544</RequstTime><VersionId>01</VersionId></ServiceHeader><ServiceBody><EXT_ATTRIBUTES><AUTH/></EXT_ATTRIBUTES><Request><ID_TYPE>I</ID_TYPE><ID_NO>430981199108231416</ID_NO><APP_NO>20180504170000000073</APP_NO><OPERATOR_ID>100098</OPERATOR_ID><OPT_TYPE>01</OPT_TYPE></Request></ServiceBody></Service>";
			//九江行内征信
//			String xmlReq = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><process><SYS_HEAD><Mac>0000000000001</Mac><MsgId>3f19c1ad638e4303be10e8239e8847fa</MsgId><SourceSysId>300660</SourceSysId><ConsumerId>300660</ConsumerId><ServiceCode>2004800006</ServiceCode><ServiceScene>01</ServiceScene><ReplyAdr></ReplyAdr><ReplyAdr></ReplyAdr></SYS_HEAD><APP_HEAD><TranDate>20180521</TranDate><TranTime>085700</TranTime><TranTellerNo>0053</TranTellerNo><TranSeqNo>2004800A6A256E1005977856322306048</TranSeqNo><GlobalSeqNo>2004800A6A256E1005977856322306049</GlobalSeqNo><BranchId>06101</BranchId><TerminalCode>111</TerminalCode><CityCode></CityCode><AuthrTellerNo>6427</AuthrTellerNo><AuthrPwd>111</AuthrPwd><AuthrCardFlag></AuthrCardFlag><AuthrCardNo></AuthrCardNo><LangCode></LangCode></APP_HEAD><BODY><IdntTp>101</IdntTp><IdentNo>110108195607175419</IdentNo><CstNm>李龙云</CstNm><QryRsn>02</QryRsn><QryFmtCd>30</QryFmtCd><QryTp>0</QryTp><QryPolcyCd>1</QryPolcyCd><ImgBsnNo/><EfftDayNum/><ImgSvDt/><RsltTp>2</RsltTp><GtFlMth>1</GtFlMth><IntfId>zhengxin_QuryCustCreditReport</IntfId><UsrKey>c93d8361a72d4b9589749968c5874c5a</UsrKey><IntVrNo>1</IntVrNo></BODY></process>";
//			String xmlReq = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Service><ServiceHeader><ServiceSn>7162658026174615327</ServiceSn><ServiceId>T1009</ServiceId><Org>000064540000</Org><ChannelId>06</ChannelId><RequstTime>20180427162658</RequstTime><VersionId>01</VersionId></ServiceHeader><ServiceBody><Request><QuyType>1</QuyType><SpreaderNO>7871</SpreaderNO><AppSource>Y</AppSource><TotalCnt>10</TotalCnt></Request></ServiceBody></Service>";
//			String xmlReq = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Service><ServiceHeader><ServiceSn>7162658026174615327</ServiceSn><ServiceId>T1010</ServiceId><Org>000064540000</Org><ChannelId>06</ChannelId><RequstTime>20180427162658</RequstTime><VersionId>01</VersionId></ServiceHeader><ServiceBody><Request><QuyType>1</QuyType><SpreaderNO>7871</SpreaderNO><CurOpUser>11111</CurOpUser><AccepUser>22222</AccepUser></Request></ServiceBody></Service>";

			Socket s = new Socket("127.0.0.1", 11000);
//			Socket s = new Socket("10.109.3.215",11000);
//			Socket s = new Socket("10.109.3.214",11000);
//			Socket s = new Socket("10.250.1.67", 9901);//九江67-行内征信
			
			// 发送对象
			int connectTimeOut= 50000;
			int lvMsgLength = 8;
			String resp  = sendSocketMsg("", xmlReq, connectTimeOut, lvMsgLength,s);
			int len = 0;
			if(resp!=null) {
				len = resp.length();
			}
			System.out.println("次数"+i+"--"+len);
			System.out.println("次数"+i+"--"+resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws Exception {
		
		int i = 0;
		Map<String, String> infoMap = initInput();
//		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//		taskExecutor.setMaxPoolSize(50);
//		taskExecutor.initialize();
		ExecutorService ec = Executors.newCachedThreadPool();
		
		for(Entry<String, String> enty: infoMap.entrySet()){
			 i++;
			final int ss = i;
			System.out.println("开始第"+i+"次执行");
			sendMessage(enty.getKey(), enty.getValue(), ss);
//			new Thread(new Runnable() {
//				
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					
//				}
//			}).start();
//			Thread.sleep(3);
//			CallCreditSysService c = new CallCreditSysService();
//			ec.execute(new Runnable() {
//				public void run() {
//					sendMessage(enty.getKey(), enty.getValue(), ss);
//				}
//			});
		}
		System.exit(0);
	}
	public static Map<String, String> initInput(){
		Map<String, String> infoMap = new HashMap<String, String>();
		infoMap.put("652200198212169298","测员");
//		infoMap.put("360681194612208998","合伙二");
//		infoMap.put("410300196202282297","合伙三");
//		infoMap.put("32041119950924737X","合伙四");
//		infoMap.put("330727198503185918","合伙五");
//		infoMap.put("370201196909252890","合伙六");
//		infoMap.put("640223197312189663","合伙七");
//		infoMap.put("152525197312185683","合伙八");
//		infoMap.put("220722197312183715","合伙九");
//		infoMap.put("41020019731218921X","合伙十");
//		infoMap.put("20181019130000000027","");

//		infoMap.put("610114200204116309-1","合伙一");
//		infoMap.put("360681194612208998-1","合伙二");
//		infoMap.put("410300196202282297-1","合伙三");
//		infoMap.put("32041119950924737X-1","合伙四");
//		infoMap.put("330727198503185918-1","合伙五");
//		infoMap.put("370201196909252890-1","合伙六");
//		infoMap.put("640223197312189663-1","合伙七");
//		infoMap.put("152525197312185683-1","合伙八");
//		infoMap.put("220722197312183715-1","合伙九");
//		infoMap.put("41020019731218921X-1","合伙十");
		
		return infoMap;
	}
	private String setRequest() {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<Service>");
		sb.append("</Service>");
		
		return sb.toString();
	}

	public static String sendSocketMsg(String servId,String reqXml,
			Integer connectTimeOut, int lvMsgLength,Socket socket) throws Exception {
		long start = System.currentTimeMillis();
		InputStream ips = null;
		// 向服务端程序发送数据
		OutputStream out = socket.getOutputStream();
		socket.setSoTimeout(connectTimeOut);
		
		// 报文头58位,报文体的长度
//		long len = reqXml.getBytes("GBK").length;
//		long len = reqXml.getBytes("GB2312").length;
		long len = reqXml.getBytes().length;
		
//		long len = reqXml.getBytes(cs).length;
//		long len = reqXml.length();
//		String reqXmlS = new String(reqXml.getBytes(Charset.defaultCharset()), "GBK");
//		char[] chrCharArray = reqXmlS.toCharArray();
//		long len = chrCharArray.length;;
		// 报文头都是无文件
		String request = String.format("%0" + lvMsgLength + "d", (len)) + reqXml;
		String respStr = "";
		try {
			OutputStreamWriter opsw = new OutputStreamWriter(out);
			BufferedWriter outData = new BufferedWriter(opsw);
//			outData.write(request);
			
//			outData.write(request.toCharArray(), 6, (int) len);
			outData.write(request.toCharArray());
			outData.flush();
			
			//响应流
			InputStream is = null;
			is = socket.getInputStream();
			StringBuilder sb = new StringBuilder();
			byte[] buffer = new byte[lvMsgLength];  
			int len2 = -1;
			String rs = null;
//			BufferedReader inData = null;
//			inData = new BufferedReader(new InputStreamReader(is));
//			while (inData.readLine()!=""&&(rs = inData.readLine()) != null) {
//			}
			len2 = is.read(buffer);
			if(len2!=-1){
				String socketLength = new String(buffer, 0, len2, "UTF-8"); // 获取报文长度描述
				sb.append(socketLength);
				for (int i = 0; i < socketLength.length(); i++) {
					if (socketLength.charAt(i) == '0') {
						continue;
					}
					socketLength = socketLength.substring(i); // 获取报文长度
				}
				buffer = new byte[Integer.valueOf(socketLength)];
				
				len2 = is.read(buffer);
				if(len2!=-1){
					String SocketContent = new String(buffer, 0, len2, "utf-8");// 获取报文头报文体报文尾
					sb.append(SocketContent);
					if(sb!=null && StringUtils.isNotEmpty(sb.toString()) && sb.length() > lvMsgLength){
						respStr = sb.toString().substring(lvMsgLength);
					}
				}
			}
			return respStr;
		} catch (Exception e) {
			logger.error("联机交易["+servId+"]发生异常,错误信息：" + e.getMessage());
			throw e;
		} finally {
			if (ips != null) {
				ips.close();
			}
			if (out != null) {
				out.close();
			}
			if (socket != null) {
				socket.close();
			}
		}
	}

}
