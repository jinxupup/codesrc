package com.jjb.ecms.adapter.test;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jjb.unicorn.facility.util.StringUtils;

public class TestT1013send {

	private static Logger logger = LoggerFactory.getLogger(TestT1013send.class);

	public static void sendMessage(int i) {
		try {
			//进件提交[T1013]--------START
			String xmlReq = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Service><ServiceBody><Request><CellPhone>15625112222</CellPhone><IdNo>21040319770802465X</IdNo><ExitCard></ExitCard><Name>测试</Name></Request></ServiceBody><ServiceHeader><ChannelId>01</ChannelId><Org>000064540000</Org><RequstTime>20190623184611</RequstTime><ServiceId>T1013</ServiceId><ServiceSn>1004900A02220B000fdbd91f68c00000</ServiceSn><VersionId>01</VersionId></ServiceHeader></Service>";
//			Socket s = new Socket("127.0.0.1", 11000);
			Socket s = new Socket("10.109.3.215",11000);
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
		sendMessage(1);
		System.exit(0);
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
