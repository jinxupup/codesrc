/**
 * 
 */
package com.jjb.ecms.adapter.test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Description: TODO
 * @author JYData-R&D-Big T.T
 * @date 2017年8月15日 下午3:12:48
 * @version V1.0  
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/service-context.xml")
public class Testa {
	
	@Test
	public void getTnty() throws UnknownHostException,
	IOException{
		String xmlReq = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Service xmlns=\"http://www.jjb.com/dataspec/\"><ServiceHeader><ServiceSn>A149999266200000274</ServiceSn><ServiceId>12106</ServiceId><Org>000064163056</Org><CHANNEL_ID>01</CHANNEL_ID><OpId>0170068</OpId><REQUST_TIME>20170714083742</REQUST_TIME><VersionId>01</VersionId><Mac></Mac></ServiceHeader><ServiceBody><EXT_ATTRIBUTES></EXT_ATTRIBUTES><Request><Org>000064163056</Org><APP_CODE></APP_CODE><ID_TYPE>I</ID_TYPE><ID_NO>511024200603145160</ID_NO><OPERATOR_ID>IT</OPERATOR_ID></Request></ServiceBody></Service>";
		String lenthEncode = "utf-8";
		long len = xmlReq.getBytes(lenthEncode).length;
		String length=String.format("%08d",len);
		xmlReq = length+ xmlReq;
		byte[] bt = xmlReq.getBytes(lenthEncode);
		Socket socket = new Socket("127.0.0.1", 8888);
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(bt);
		outputStream.flush();
		socket.close();
	}
}
