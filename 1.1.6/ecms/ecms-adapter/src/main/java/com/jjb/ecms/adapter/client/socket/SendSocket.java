package com.jjb.ecms.adapter.client.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jjb.ecms.adapter.socket.SocketServerProcessUtils;
import com.jjb.ecms.adapter.utils.AdapterConstants;
import com.jjb.ecms.adapter.utils.UnifiedMessageUtil;
import com.jjb.ecms.adapter.utils.ccif.TransPIN;
import com.jjb.unicorn.facility.service.YakMessage;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.socket.codec.YakMsgConstants;
/*import com.jjccb.security.SM4;
import com.jjccb.security.SecurityUtils;*/

/**
 * @Description: 发送Socket交易支持类 ,所有调用外部系统socket均走此方法发送
 * @author JYData-R&D-Big H.N
 * @date 2018年4月14日 下午4:59:09
 * @version V1.0
 */
@Component
public class SendSocket {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdapterConstants constants;
	@Autowired
	private ConnectorHelper connectorHelper;
	@Autowired
	private UnifiedMessageUtil unifiedMessageUtil;
	@Autowired
	private SocketServerProcessUtils sspUtils;
	/**
	 * 默认编码格式utf-8
	 * 第一位：报文添加长度验证位时计算使用；
	 * 第二位： 解析返回报文使用；
	 * 第三位：发送的报文整体长度计算使用；
	 */
	private String[] charset = AdapterConstants.charset;

	static final String ENC_KEY = "1234567890abcdef";
	static final String MAC_KEY = "1234567890abcdef";


	public static String convertNumber(int data, String pattern) {
		DecimalFormat df = new DecimalFormat(pattern);

		return df.format(data);
	}

	/**
	 * @Author lixing
	 * @Description 加密
	 * @Date 2018/10/18 11:30
	 */
	public String encrypt(String reqXml) {
        String reqXmlS = null;
/*		try {
			//1.组装报文数据
			byte[] b = reqXml.getBytes("UTF-8");

			//2.生成MAC
			byte[] mac = TransPIN.GetMac(b, b.length, MAC_KEY.getBytes());
			String s = new String(mac);
			String length = convertNumber(reqXml.getBytes("UTF-8").length, "00000000");
			String trans = String.format("%s%s%s", length, reqXml, s);//3.拼接交易数据
			//4.加密交易数据
			byte[] bs =
					SM4.encodeCBCSMS4(trans.getBytes("UTF-8"), ENC_KEY.getBytes(), SecurityUtils.PKCS5_PADDING);
			//4.BASE64 转码
			reqXmlS = SecurityUtils.base64Encrypt(bs);
			//5. 按约定组传输数据
		} catch (UnsupportedEncodingException e) {
			logger.error("短信发送加密报文发生异常,错误信息：" + e.getMessage());
		}*/
		return reqXmlS;
	}


	/**
	 * @Author lixing
	 * @Description 解密
	 * @Date 2018/10/18 11:31
	 */
	public String decrypt(String respStr){
		String xml = "";

	/*	byte[] bws = SecurityUtils.base64Decrypt(respStr);
	    //3.解密明文数据: 参数解析
		//3.1 第一个参数： 参与加密的报文字节数组
		//3.2 第二个参数： 加密密钥字节数组
		//3.3 第三个参数： 补位模式：默认用PKCS5_PADDING即可
		byte[] bss = SM4.decodeCBCSMS4(bws,ENC_KEY.getBytes(),SecurityUtils.PKCS5_PADDING);
		//2. MAC校验处理
		try {
			xml = new String(bss,"UTF-8");

		} catch (UnsupportedEncodingException e) {
			logger.error("解密异常",e);
		}*/
		return xml;
	}



	public String sendSocketMsg(String servId,String host, int port, String reqXml,
			String charsetName, Integer connectTimeOut, int lvMsgLength,
			String org,String opUser)
			throws Exception {
		long start = System.currentTimeMillis();
		Socket socket = new Socket(host, port);
		InputStream is = null;
		OutputStream out = socket.getOutputStream();
		socket.setSoTimeout(connectTimeOut);
		setCharset(servId, charsetName);//设置编码格式

		String reqXmlS = new String(reqXml.getBytes(charset[0]), charset[1]);

		char[] chrCharArray = reqXmlS.toCharArray();
		long len = chrCharArray.length;;

		logger.info("报文char长度:"+len+",报文Bytes长度："+reqXmlS.getBytes().length+",报文String长度:"+reqXml.length());
		logger.info("交易["+servId+"],xml编码格式["+charset[1]+"],请求报文：" + reqXmlS);
		String respStr = "";
		try {
			//创建一个client
			ShortTermSocketClient client =connectorHelper.createConnector(servId,
					host,port,charset[1], connectTimeOut,lvMsgLength);
			YakMessage sendMsg = new YakMessage();
			sendMsg.setRawMessage(reqXmlS.getBytes());
			unifiedMessageUtil.setYakReqUnifiedHeaderMessage(sendMsg, servId, null, null);
			// TODO 报文头
			Map<Integer, String> map = new HashMap<Integer, String>();
			// map.put(1, "A002");
			// 测试:A002作为校验信息
			sendMsg.setHeadAttributes(map);
			sendMsg.getCustomAttributes().put(YakMsgConstants.MESSAGE_KEY, reqXmlS);
			YakMessage resMse = client.write(sendMsg);
			if(resMse!=null && resMse.getRawMessage()!=null){
				respStr = new String(resMse.getRawMessage(),charset[2]);
                logger.info("交易["+servId+"]响应报文[" + respStr+"]");
            }
			return respStr;

		} catch (Exception e) {
			logger.error("交易["+servId+"]发生异常,错误信息：" + e.getMessage());
			throw e;
		} finally {
			if (is != null) {
				is.close();
			}
			if (out != null) {
				out.close();
			}
			if (socket != null) {
				socket.close();
			}
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

	/**
	 * 拼接报文头和报文体
	 * @param reqXml
	 * @return
	 * @throws IOException
	 */
	private String fullMessage(String reqXml, Integer lvMsgLength,String charset)
			throws IOException {
		if(lvMsgLength==null || lvMsgLength<4){
			lvMsgLength = constants.defLvMsgLength;
		}
		if(StringUtils.isEmpty(charset)){
			charset = "UTF-8";
		}
		// 报文头58位,报文体的长度
		long len = reqXml.getBytes(charset).length;
		// 报文头都是无文件
		String reqHead = String.format("%0" + lvMsgLength + "d", len) + reqXml;
		return reqHead;
	}


}
