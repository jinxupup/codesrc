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

public class TestT1005send {

	private static Logger logger = LoggerFactory.getLogger(TestT1005send.class);

	public static void sendMessage(String idNo,String name,int i) {
		String gender = IdentificationCodeUtil.getGender(idNo);
		Date brD = IdentificationCodeUtil.getBirthdayDate(idNo);		
		String brDay = DateUtils.dateToString(brD, DateUtils.DAY_YMD);
		try {
			//进件提交[T1005]--------START
			//根据多客户 替换姓名，号码，证件号码
//			String xmlReq ="<?xml version=\"1.0\" encoding=\"UTF-8\"?><service><ServiceBody><Request><AppLmt>12312</AppLmt><AppSource>51</AppSource><AppType>B</AppType><ApplyType>N</ApplyType><Org>000064540000</Org><OwningBranch>06101</OwningBranch><ProductCd>510001</ProductCd><RealtimeIssuingFlag>Y</RealtimeIssuingFlag><Custs><Cust><Age>25</Age><Birthday>"+brDay+"</Birthday><BscSuppind>B</BscSuppind><CardMailerInd>H</CardMailerInd><Cellphone>13052292100</Cellphone><CorpName>按实际大家</CorpName><Degree>U</Degree><Email>871284791754@qq.com</Email><EmbLogo>CE YUAN</EmbLogo><EmpAdd>爱上你考虑到了</EmpAdd><EmpAddrCtryCd>156</EmpAddrCtryCd><EmpCity>北京市</EmpCity><EmpDepartment>纤维球</EmpDepartment><EmpPhone>0351-7827353</EmpPhone><EmpPost>D</EmpPost><EmpProvince>北京市</EmpProvince><EmpStructure>H</EmpStructure><EmpType>J1</EmpType><EmpWorkyears>1</EmpWorkyears><EmpZone>东城区</EmpZone><Gender>"+gender+"</Gender><HomeAdd>奥斯卡了加大加</HomeAdd><HomeAddrCtryCd>156</HomeAddrCtryCd><HomeCity>合肥市</HomeCity><HomeState>安徽省</HomeState><HomeZone>瑶海区</HomeZone><HouseOwnership>E</HouseOwnership><IdLastAll>Y</IdLastAll><IdLastDate null=\"true\"/><IdNo>"+idNo+"</IdNo><IdType>I</IdType><MaritalStatus>S</MaritalStatus><Name>"+name+"</Name><Nationality>156</Nationality><OtherAsk>无</OtherAsk><PhotoUseFlag>N</PhotoUseFlag><PosPinVerifyInd>Y</PosPinVerifyInd><Qualification>B</Qualification><ResidencyCountryCd>156</ResidencyCountryCd><SmAmtVerifyInd>Y</SmAmtVerifyInd><YearIncome>12</YearIncome></Cust></Custs><DdBankAcctName>爱打架</DdBankAcctName><DdBankAcctNo null=\"true\"/><DdBankName null=\"true\"/><DdInd null=\"true\"/><HouseMonthyAmt>1202</HouseMonthyAmt><InputDate>20190116031130</InputDate><InputName null=\"true\"/><InputNo>34122719930226701X</InputNo><IsPrdChange>Y</IsPrdChange><SpreaderMode>A</SpreaderMode><SpreaderName>苏胜龙</SpreaderName><SpreaderNo>34122719930226701X</SpreaderNo><SpreaderOrg>06101</SpreaderOrg><SpreaderType>C</SpreaderType><StmtMailAddrInd>H</StmtMailAddrInd><StmtMediaType>E</StmtMediaType><BuildingName>是,每年的法律南斯拉夫</BuildingName><CardFetchMethod>E</CardFetchMethod><CardMailerInd>H</CardMailerInd><ClientType>A00</ClientType><DeviceType>IOS</DeviceType><DeviceGps>WE-222,NS-2323</DeviceGps><DeviceGpsCN>不知道在哪里</DeviceGpsCN><WeChatNickName>XAAAA=WC</WeChatNickName><TelOperatorsType>电信</TelOperatorsType><TelBelong>上海</TelBelong><ContactMobile>13052928398</ContactMobile><ContactName>爱迪生</ContactName><ContactOmobile>13054112312</ContactOmobile><ContactOname>阿萨德</ContactOname><ContactOrelation>W</ContactOrelation><ContactRelation>W</ContactRelation><ExtCheckIdFlag>1</ExtCheckIdFlag><ExtCheckIdRs>{\"AFR\": [{\"checkRs\": \"F\",\"currCnt\": 1,\"checkRsDesc\": \"核验接口调用超时\"},{\"checkRs\": \"S\",\"currCnt\": 2,\"checkRsDesc\": \"成功\"}]}</ExtCheckIdRs><SubmitType>SUBMIT</SubmitType></Request></ServiceBody><ServiceHeader><ChannelId>02</ChannelId><Mac>0000000000000000</Mac><Org>000064540000</Org><RequstTime>201901161501900</RequstTime><ServiceId>T1005</ServiceId><ServiceSn>c2a59040-e98e-4513-a8b0-824fd9f425c3</ServiceSn><VersionId>01</VersionId></ServiceHeader></service>";
			String xmlReq = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Service><ServiceBody><Request><AppSource>01</AppSource><AppType>B</AppType><ApplyType>N</ApplyType><CardFetchMethod>A</CardFetchMethod><CardMailerInd>C</CardMailerInd><ClientType>A00</ClientType><ContactMobile>15625123333</ContactMobile><ContactName>测算</ContactName><ContactOmobile>15625120034</ContactOmobile><ContactOname>代垫</ContactOname><ContactOrelation>H</ContactOrelation><ContactRelation>D</ContactRelation><Custs><Cust><Age>43</Age><Birthday>"+brDay+"</Birthday><BscSuppind>B</BscSuppind><Cellphone>15625112222</Cellphone><CorpName>测算</CorpName><Email>123123@126.com</Email><EmbLogo>CESHI</EmbLogo><EmpAdd>阿赛爱死</EmpAdd><EmpAddrCtryCd>156</EmpAddrCtryCd><EmpCity>110100</EmpCity><EmpDepartment>测算</EmpDepartment><EmpPhone>15625120000</EmpPhone><EmpPost>E</EmpPost><EmpProvince>110000</EmpProvince><EmpZone>110103</EmpZone><Gender>"+gender+"</Gender><HomeAdd>测试</HomeAdd><HomeAddrCtryCd>156</HomeAddrCtryCd><HomeCity>360100</HomeCity><HomeState>360000</HomeState><HomeZone>360102</HomeZone><HouseOwnership>W</HouseOwnership><IdLastAll>N</IdLastAll><IdLastDate>2019-06-23</IdLastDate><IdNo>"+idNo+"</IdNo><IdType>I</IdType><MaritalStatus>M</MaritalStatus><Name>"+name+"</Name><Nationality>156</Nationality><Occupation>A</Occupation><PhotoUseFlag>S</PhotoUseFlag><PosPinVerifyInd>Y</PosPinVerifyInd><ResidencyCountryCd>156</ResidencyCountryCd><SmAmtVerifyInd>Y</SmAmtVerifyInd></Cust></Custs><ExtCheckIdFlag>1</ExtCheckIdFlag><InputName>网申客户</InputName><InputNo>06102</InputNo><IsPrdChange>Y</IsPrdChange><Org>000064540000</Org><OwningBranch>06102</OwningBranch><ProductCd>000002</ProductCd><RealtimeIssuingFlag>Y</RealtimeIssuingFlag><SpreaderMode>E</SpreaderMode><SpreaderName>网申客户</SpreaderName><SpreaderNo>06102</SpreaderNo><SpreaderOrg>06102</SpreaderOrg><SpreaderType>B</SpreaderType><StmtMailAddrInd>C</StmtMailAddrInd><SubmitType>SUBMIT</SubmitType><DeviceType>IOS</DeviceType><DeviceGps>WE-222,NS-2323</DeviceGps><DeviceGpsCN>不知道在哪里</DeviceGpsCN><WeChatNickName>XAAAA=WC</WeChatNickName><TelOperatorsType>电信</TelOperatorsType><TelBelong>上海</TelBelong></Request></ServiceBody><ServiceHeader><ChannelId>01</ChannelId><Org>000064540000</Org><RequstTime>20190623184611</RequstTime><ServiceId>T1005</ServiceId><ServiceSn>1004900A02220B000fdbd91f68c00000</ServiceSn><VersionId>01</VersionId></ServiceHeader></Service>";
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
		}
		System.exit(0);
	}
	public static Map<String, String> initInput(){
		Map<String, String> infoMap = new HashMap<String, String>();
		infoMap.put("652200198212169298","测员");
//		infoMap.put("360681194612208998","合伙二");
		
		return infoMap;
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
