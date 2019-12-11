package com.jjb.ecms.adapter.client.socket;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jjb.ecms.adapter.utils.AdapterConstants;
import com.jjb.ecms.adapter.utils.UnifiedMessageUtil;
import com.jjb.unicorn.facility.service.YakMessage;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.socket.codec.YakMsgConstants;

/**
 * @Description: 发送Socket交易支持类 ,所有调用外部系统socket均走此方法发送
 * @author JYData-R&D-Big H.N
 * @date 2018年4月14日 下午4:59:09
 * @version V1.0
 */
@Component
public class SendSocketSupport {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdapterConstants constants;
    @Autowired
    private ConnectorHelper connectorHelper;
	@Autowired
	private UnifiedMessageUtil unifiedMessageUtil;
	/**
	 * 默认编码格式utf-8
	 * 第一位：报文添加长度验证位时计算使用；
	 * 第二位： 解析返回报文使用；
	 * 第三位：发送的报文整体长度计算使用；
	 */
	private String[] charset = AdapterConstants.charset;
	
	public String sendSocketMsg(String servId,String host, int port, String reqXml,
			String charsetName, String connectTimeOut, String lvMsgLength,
			String org,String opUser)
			throws Exception {
		long start = System.currentTimeMillis();
//		charsetName="UTF-8|UTF-8|UTF-8";
//		host="10.250.1.54";
//		port=8123;
//		Socket socket = new Socket(host, port);
//		//响应流
//		InputStream is = null;
//		// 向服务端程序发送数据
//		OutputStream out = socket.getOutputStream();
//		socket.setSoTimeout(timeOut);

		setCharset(servId, charsetName);//设置编码格式
		Integer msgLen = setlvMsgLength(servId, lvMsgLength);//设置请求报文长度验证位
		Integer timeOut = setConnectTimeOut(servId, connectTimeOut, msgLen);//设置超时时间
		
		String reqXmlS = new String(reqXml.getBytes(charset[0]), charset[1]);
		
		char[] chrCharArray = reqXmlS.toCharArray();
		long len = chrCharArray.length;;
		
		logger.info("报文char长度:"+len+",报文Bytes长度："+reqXmlS.getBytes().length+",报文String长度:"+reqXml.length());
		logger.info("交易["+servId+"],xml编码格式["+charset[1]+"],请求报文：" + reqXmlS);
		String respStr = "";
		try {
			//创建一个client
			ShortTermSocketClient client =connectorHelper.createConnector(servId, 
					host,port,charset[1], timeOut,msgLen);
			YakMessage sendMsg = new YakMessage();
//			sendMsg.setRawMessage(reqXmlS.getBytes());
			unifiedMessageUtil.setYakReqUnifiedHeaderMessage(sendMsg, servId, null, null);
			// TODO 报文头
			Map<Integer, String> map = new HashMap<Integer, String>();
			// map.put(1, "A002");//测试:A002作为校验信息
			sendMsg.setHeadAttributes(map);
			sendMsg.getCustomAttributes().put(YakMsgConstants.MESSAGE_KEY, reqXmlS);
			YakMessage resMse = client.write(sendMsg);
			if(resMse!=null && resMse.getRawMessage()!=null){
				respStr = new String(resMse.getRawMessage(),charset[2]);
			}
			logger.info("交易["+servId+"]响应报文[" + respStr+"]");
			
//			String request=fullMessage(reqXmlS,msgLen,charset[0]);
//			OutputStreamWriter opsw = new OutputStreamWriter(out);
//			BufferedWriter outData = new BufferedWriter(opsw);
////			outData.write(request);
//			outData.write(request.toCharArray());
//			outData.flush();
//			try {
//				socket.shutdownOutput();
//			} catch (Exception e) {
//				logger.error("交易["+servId+"]关闭socket-output流失败"+e.getMessage());
//			}
//			is = socket.getInputStream();
//			StringBuilder sb = new StringBuilder();
//			byte[] buffer = new byte[msgLen];  
//			int len2 = -1;
//			len2 = is.read(buffer);
//			String socketLength = new String(buffer, 0, len2, charset[2]); // 获取报文长度描述
//			sb.append(socketLength);
//			for (int i = 0; i < socketLength.length(); i++) {
//				if (socketLength.charAt(i) == '0') {
//					continue;
//				}
//				socketLength = socketLength.substring(i); // 获取报文长度
//			}
//
//			buffer = new byte[Integer.valueOf(socketLength)];
//			len2 = is.read(buffer);
//			String socketContent = new String(buffer, 0, len2, charset[2]);// 获取报文头报文体报文尾
//			sb.append(socketContent);
//			logger.info("交易["+servId+"]响应报文[" + sb+"]");
//			if(sb!=null && StringUtils.isNotEmpty(sb.toString()) && sb.length() > msgLen){
//				respStr = sb.toString().substring(msgLen);
//			}
			return respStr;
		} catch (Exception e) {
			logger.error("交易["+servId+"]发生异常,错误信息：" + e.getMessage());
			throw e;
		} finally {
//			if (is != null) {
//				is.close();
//			}
//			if (out != null) {
//				out.close();
//			}
//			if (socket != null) {
//				socket.close();
//			}
			logger.info("交易["+servId+"]耗时("+(System.currentTimeMillis()-start)+")");
		}
	}

	/**
	 * 设置超时时间
	 * @param servId
	 * @param connectTimeOut
	 * @param msgLen
	 * @return
	 */
	private Integer setConnectTimeOut(String servId, String connectTimeOut,
			Integer msgLen) {
		Integer timeOut = constants.defTimeOut;
		try {
			if (connectTimeOut != null && !connectTimeOut.trim().equals("")) {
				timeOut = Integer.valueOf(connectTimeOut);
			}
		} catch (Exception e) {
			timeOut = constants.defTimeOut;
			logger.error("交易["+servId+"]转换socket超时时间[" + connectTimeOut + "]异常,赋值默认时间 ["+timeOut+"]秒");
		}
		logger.info("交易["+servId+"]报文长度位["+msgLen+"]，超时时间["+timeOut+"]....联机调用外部系统开始");
		return timeOut;
	}

	/**
	 * 设置请求报文长度验证位
	 * @param servId
	 * @param lvMsgLength
	 * @return
	 */
	private Integer setlvMsgLength(String servId, String lvMsgLength) {
		//报文验证长度
		Integer msgLen = constants.defLvMsgLength;
		if (lvMsgLength != null && !lvMsgLength.trim().equals("")) {
			try {
				msgLen = Integer.valueOf(lvMsgLength);
			} catch (Exception e) {
				msgLen = constants.defLvMsgLength;
				logger.error("交易["+servId+"]转换交易报文长度验证位[" + lvMsgLength + "]异常,赋值默认验证长度["+msgLen+"]位");
			}
		}
		return msgLen;
	}

	/**
	 * 设置编码格式
	 * @param servId
	 * @param charsetName
	 */
	private void setCharset(String servId, String charsetName) {
		if(StringUtils.isNotBlank(charsetName)){
			charset = charsetName.split("\\|");
		}
		
		if(charset == null || charset.length != 3){
			if(charset!=null && charset.length==1){
				charset = new String[] {charset[0],"UTF-8","UTF-8"};
			}else if(charset!=null && charset.length==2){
				charset = new String[] {charset[0],charset[1],"UTF-8"};
			}else{
				charset = new String[] {"UTF-8","UTF-8","UTF-8"};
				
			}
			logger.info(""+servId+"交易报文编码[{}]参数不正确,设置默认utf-8编码格式",charsetName);
		}
		String cs = "";
		for (int i = 0; i < charset.length; i++) {
			cs = cs + "|" + charset[0];
		}
		logger.info("交易["+servId+"],系统当前默认编码格式:"+Charset.defaultCharset()+",交易报文编码格式为"+cs);
	}
	


}
