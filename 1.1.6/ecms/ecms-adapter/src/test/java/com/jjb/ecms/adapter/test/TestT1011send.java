package com.jjb.ecms.adapter.test;

import com.jjb.unicorn.facility.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TestT1011send {

	private static Logger logger = LoggerFactory.getLogger(TestT1011send.class);

	public static void sendMessage() {
		try {

			// 准入及老客户查询[T1002]----START
//			String xmlReq = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Service><ServiceHeader><ServiceSn>7162658026174615327</ServiceSn><ServiceId>T1011</ServiceId><Org>000064540000</Org><ChannelId>06</ChannelId><RequstTime>20180427162658</RequstTime><VersionId>01</VersionId></ServiceHeader><ServiceBody><Request><Name>金鹏</Name><IdType>I</IdType><IdNo>340827197304180017</IdNo><Cellphone>13965806892</Cellphone><MaritalStatus>M</MaritalStatus><AppProducts>000003</AppProducts><AppAmount>500000.11</AppAmount><CompanyName>金游世界科技有限股份公司</CompanyName><FirstContactName>阴阴阴</FirstContactName><FirstContactPhone>13900000000</FirstContactPhone><ImageNum>12345678978978978</ImageNum><WeCode>ac8123132dad3454sdsad3123</WeCode><PptyProvince>江西省</PptyProvince><PptyCity>九江市</PptyCity><PptyArea>赣</PptyArea><PptyAreaCode>A</PptyAreaCode><ChannelType>A</ChannelType></Request></ServiceBody></Service>";
//			String xmlReq = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Service><ServiceHeader><ServiceSn>7162658026174615327</ServiceSn><ServiceId>T1011</ServiceId><Org>000064540000</Org><ChannelId>06</ChannelId><RequstTime>20180427162658</RequstTime><VersionId>01</VersionId></ServiceHeader><ServiceBody><Request><Name>陈伟</Name><IdType>I</IdType><IdNo>34088119910201531X</IdNo><Cellphone>15255654111</Cellphone><MaritalStatus>M</MaritalStatus><AppProducts>000003</AppProducts><AppAmount>500000.11</AppAmount><CompanyName>金游世界科技有限股份公司</CompanyName><FirstContactName>阴阴阴</FirstContactName><FirstContactPhone>13900000000</FirstContactPhone><ImageNum>12345678978978978</ImageNum><WeCode>ac8123132dad3454sdsad3123</WeCode><PptyProvince>江西省</PptyProvince><PptyCity>九江市</PptyCity><PptyArea>赣</PptyArea><PptyAreaCode>A</PptyAreaCode><ChannelType>A</ChannelType></Request></ServiceBody></Service>";
			String xmlReq = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Service><ServiceHeader><ServiceSn>7162658026174615327</ServiceSn><ServiceId>T1011</ServiceId><Org>000064540000</Org><ChannelId>06</ChannelId><RequstTime>20180427162658</RequstTime><VersionId>01</VersionId></ServiceHeader><ServiceBody><Request><Name>刘明新</Name><IdType>I</IdType><IdNo>360722199008185118</IdNo><Cellphone>18070275717</Cellphone><MaritalStatus>M</MaritalStatus><AppProducts>000003</AppProducts><AppAmount>500000.11</AppAmount><CompanyName>金游世界科技有限股份公司</CompanyName><FirstContactName>阴阴阴</FirstContactName><FirstContactPhone>13900000000</FirstContactPhone><ImageNum>12345678978978978</ImageNum><WeCode>ac8123132dad3454sdsad3123</WeCode><PptyProvince>江西省</PptyProvince><PptyCity>九江市</PptyCity><PptyArea>赣</PptyArea><PptyAreaCode>A</PptyAreaCode><ChannelType>A</ChannelType></Request></ServiceBody></Service>";
//			String xmlReq = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><service><ServiceBody><Request><AppAmount>10000</AppAmount><AppProducts>O</AppProducts><Cellphone>13612536363</Cellphone><CompanyName>锦咏数据</CompanyName><FirstContactName>王丽霞</FirstContactName><FirstContactPhone>13248678985</FirstContactPhone><IdNo>320101199107190025</IdNo><IdType>I</IdType><MaritalStatus>M</MaritalStatus><Name>黄芮</Name></Request></ServiceBody><ServiceHeader><ChannelId>02</ChannelId><Mac>0000000000000000</Mac><Org>000064540000</Org><RequstTime>2019092621709487</RequstTime><ServiceId>T1011</ServiceId><ServiceSn>0e290727-e1fb-45ca-829f-7041b8e02762</ServiceSn><VersionId>01</VersionId></ServiceHeader></service>";
//			String xmlReq = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Service><ServiceHeader><ServiceSn>7162658026174615327</ServiceSn><ServiceId>T1002</ServiceId><Org>000064540000</Org><ChannelId>06</ChannelId><RequstTime>20180427162658</RequstTime><VersionId>01</VersionId></ServiceHeader><ServiceBody><Request><IdType>I</IdType><IdNo>36050219821222251X</IdNo><Name>何大壮</Name><Cellphone>18521064139</Cellphone><AppType>B</AppType><PrimCardNo></PrimCardNo><ProductCd>000001</ProductCd><InputNo>sysauto</InputNo><InputName>录入呗</InputName></Request></ServiceBody></Service>";
			// 准入及老客户查询[T1002]----END

//			Socket s = new Socket("127.0.0.1", 11000);
			Socket s = new Socket("10.109.3.215", 11000);
//			Socket s = new Socket("10.2.32.76", 11000);
//			Socket s = new Socket("10.109.3.214",11000);
//			Socket s = new Socket("10.250.1.67", 9901);//九江67-行内征信

			// 发送对象
			int connectTimeOut = 50000;
			int lvMsgLength = 8;
			String resp = sendSocketMsg("", xmlReq, connectTimeOut, lvMsgLength, s);
			int len = 0;
			if (resp != null) {
				len = resp.length();
			}
			System.out.println("响应--" + len);
			System.out.println("响应--" + resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		sendMessage();
		System.exit(0);
	}

	public static String sendSocketMsg(String servId, String reqXml, Integer connectTimeOut, int lvMsgLength,
			Socket socket) throws Exception {
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

			// 响应流
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
			if (len2 != -1) {
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
				if (len2 != -1) {
					String SocketContent = new String(buffer, 0, len2, "utf-8");// 获取报文头报文体报文尾
					sb.append(SocketContent);
					if (sb != null && StringUtils.isNotEmpty(sb.toString()) && sb.length() > lvMsgLength) {
						respStr = sb.toString().substring(lvMsgLength);
					}
				}
			}
			return respStr;
		} catch (Exception e) {
			logger.error("联机交易[" + servId + "]发生异常,错误信息：" + e.getMessage());
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
