package com.jjb.ecms.adapter.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jjb.unicorn.facility.util.StringUtils;

public class TestPosSum {

	private static Logger logger = LoggerFactory.getLogger(TestPosSum.class);

	public static void sendMessage(String idNo,String name,int i) {
		try {

			String xmlReq="<?xml version=\"1.0\" encoding=\"UTF-8\" ?><service><SYS_HEAD><Mac>0000000000001</Mac><MsgId>100010145338b9140453760908100014</MsgId><SourceSysId>200340</SourceSysId><ConsumerId>200340</ConsumerId><ServiceCode>04001000005</ServiceCode><ServiceScene>01</ServiceScene><ReplyAdr></ReplyAdr><ExtendContent></ExtendContent></SYS_HEAD><APP_HEAD><TranDate>20181119</TranDate><TranTime>100138</TranTime><TranTellerNo>1549</TranTellerNo><TranSeqNo>20034020181119100138004</TranSeqNo><GlobalSeqNo></GlobalSeqNo><BranchId>72701</BranchId><TerminalCode>98</TerminalCode><CityCode>72</CityCode><AuthrTellerNo>001</AuthrTellerNo><AuthrPwd></AuthrPwd><AuthrCardFlag></AuthrCardFlag><AuthrCardNo></AuthrCardNo><LangCode>CHINESE</LangCode></APP_HEAD><BODY><CardNo>6251950000000132</CardNo><PcsCd>000000</PcsCd><TxnAmt>100.00</TxnAmt><Ccy>CNY</Ccy><MrchTp>5722</MrchTp><POSEntrMdCd>012</POSEntrMdCd><CardSeqId></CardSeqId><POSCdtnCd>64</POSCdtnCd><AgncInstIdentCd></AgncInstIdentCd><SndInstIdentCd></SndInstIdentCd><TrackData2></TrackData2><CnlSubdvdTp></CnlSubdvdTp><CardAcptTmlIdent>S0220324</CardAcptTmlIdent><CardAcptIdentCd>123451234512345</CardAcptIdentCd><CardAcptNmLo>商户分期</CardAcptNmLo><IdvIdentCd></IdvIdentCd><ScrRelCtrlInf></ScrRelCtrlInf><ICCData></ICCData><SpecificData></SpecificData><AgngPrds>12</AgngPrds><AgngAvyCd>00001</AgngAvyCd><KpAcctInd>0</KpAcctInd></BODY></service>";
			Socket s = new Socket("172.20.12.235",7074);
			
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
		infoMap.put("", "");
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
//		String request = String.format("%0" + lvMsgLength + "d", (len)) + reqXml;
		String request = reqXml;
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
			BufferedReader inData = null;
			inData = new BufferedReader(new InputStreamReader(is));
			String rs2 = "";
//			while (inData.readLine()!=""&&(rs = inData.readLine()) != null) {
			while ((rs = inData.readLine()) != null) {
				System.out.println(rs2+rs);
				rs2 = rs2+rs;
			}
			System.out.println(rs2);
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
