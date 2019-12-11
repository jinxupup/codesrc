package com.jjb.ecms.adapter.test;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class NewSocketClient {

	private static String ip = "10.109.3.215";
	private static int port = 12000;
	// private static int success = 0;
	// private static int fail = 0;
	// private static int yewu = 0;

	// 初始化请求
	public static String requestHandle() {
		StringBuilder result = new StringBuilder();
		try {
			// 参数转换成xml格式字符串
			String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><service><ServiceBody><Request><AppSource>02</AppSource><QuyType>1</QuyType><SpreaderNO>910079</SpreaderNO><TotalCnt>30</TotalCnt></Request></ServiceBody><ServiceHeader><ChannelId>02</ChannelId><Mac>0000000000000000</Mac><Org>000064540000</Org><RequstTime>201902511402949</RequstTime><ServiceId>T1009</ServiceId><ServiceSn>ae20673a-3ec9-40bd-b774-0480428ce4fb</ServiceSn><VersionId>01</VersionId></ServiceHeader></service>";
			System.out.println("===请求报文:"+xmlStr);
			result.append(NewSocketClient.requestMethod(xmlStr));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	
	
	/**
	 * @author ZhangXY 调用九江ESB接口
	 * @throws UnsupportedEncodingException
	 */
	public static String requestMethod(String xmlStr) throws UnsupportedEncodingException {

		StringBuffer reBuffer = new StringBuffer();
		
//		String returnStr="";

		byte[] request = xmlStr.getBytes("UTF-8");

		// 生成八位长度头，按bete字节utf8.计算，不够左补零 8位长度头本身不计算在内
		int headlen = 8;

		byte len_bytes[] = null;
		String length = request.length + "";
		StringBuilder sb = new StringBuilder();
		if (length.length() > headlen)
			len_bytes = new byte[0];
		else {

			for (int i = 0; i < headlen - length.length(); i++) {
				sb.append("0");
			}
			sb.append(length);
		}

		Socket c = null;
		OutputStream os = null;
		InputStream is = null;

		try {

			// socket请求ESB前置
			c = new Socket(ip, port);
			os = c.getOutputStream();
			os.write(sb.toString().getBytes());
			os.write(request);
			
			
			is = c.getInputStream();
			
			sb = new StringBuilder();
			byte[] buffer = new byte[headlen];  
			int len2 = -1;
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
				System.err.println("报文长度"+socketLength);
				buffer = new byte[Integer.valueOf(socketLength)];
				
				len2 = is.read(buffer);
				if(len2!=-1){
					String SocketContent = new String(buffer, 0, len2, "utf-8");// 获取报文头报文体报文尾
					reBuffer.append(SocketContent);
//					returnStr=returnStr+SocketContent;
				}
				System.err.println("核心返回结果="+reBuffer+"结束");
			}
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (c != null) {
					c.close();
				}
				if (is != null) {
					is.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return reBuffer.toString().replaceAll("\n", "");
	}
	
	
	/**
	 * 过滤String 字符串中不可见的字符
	 * 去除特殊字符
	 * @param in
	 * @return
	 */
	public static String stripNonValidCharacters(String in) {
		String str = "";
		if (in != null && !in.equals("")) {
			StringBuffer out = new StringBuffer(); // Used to hold the output.
//			Pattern p = Pattern.compile("\\s+");
//			Matcher m = p.matcher(in);
//			in = m.replaceAll(" ");
//			in = in.replace("  ", " ");

			char current; // Used to reference the current character.

			if (in == null || ("".equals(in)))
				return ""; // vacancy test.
			for (int i = 0; i < in.length(); i++) {
				current = in.charAt(i); // NOTE: No IndexOutOfBoundsException
										// caught
										// here; it should not happen.
				if ((current == 0x9) || (current == 0xA) || (current == 0xD)
						|| ((current >= 0x20) && (current <= 0xD7FF))
						|| ((current >= 0xE000) && (current <= 0xFFFD))
						|| ((current >= 0x10000) && (current <= 0x10FFFF)))
					out.append(current);
			}
			str = out.toString();
		}
		return str;
	}

//	/**
//	 * @author ZhangXY 调用九江ESB接口
//	 * @throws UnsupportedEncodingException
//	 */
//	public static String requestMethod(String xmlStr) throws UnsupportedEncodingException {
//
//		StringBuffer reBuffer = new StringBuffer();
//
//		// =============把文件生成流start===================
//
//		// File reqfile = new File(filePath);
//
//		byte[] request = xmlStr.getBytes("UTF-8");
//
//		// try {
//		// FileInputStream fileInputStream = new FileInputStream(reqfile);
//		//
//		// request = new byte[fileInputStream.available()];
//		//
//		// fileInputStream.read(request);
//		// } catch (Exception e) {
//		// }
//
//		// =============把文件生成流end===================
//
//		// 生成八位长度头，按bete字节utf8.计算，不够左补零 8位长度头本身不计算在内
//		int headlen = 8;
//
//		byte len_bytes[] = null;
//		String length = request.length + "";
//		StringBuilder sb = new StringBuilder();
//		if (length.length() > headlen){
//			len_bytes = new byte[0];
//		}else {
//
//			for (int i = 0; i < headlen - length.length(); i++) {
//				sb.append("0");
//			}
//			sb.append(length);
//		}
//
//		Socket c = null;
//		OutputStream os = null;
//		InputStream is = null;
//
//		try {
//			System.err.println("开始请求。。。。。。。。。。");
//			// socket请求ESB前置
//			c = new Socket(ip, port);
//			os = c.getOutputStream();
//			os.write(sb.toString().getBytes());
//			os.write(request);
//
//			is = c.getInputStream();
//
//			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//			String resp = null;
//
//			while ((resp = br.readLine()) != null) {
//				System.err.println("="+resp+"=");
//				reBuffer.append(resp);
////				if(resp.equals("</Service>")){
////					break;
////				}
//			}
//
////			int startIndex = resp.indexOf("<ReturnCode>");
////			int endIndex = resp.indexOf("</ReturnCode>");
////			String returnCode = resp.substring(startIndex, endIndex);
////			if ("<ReturnCode>00000000000000".equals(returnCode)) {
////				// success++;
////				// System.out.println("成功：" + success + "笔");
////			} else if ("<ReturnCode>DS00101".equals(returnCode)) {
////				// yewu++;
////				// System.out.println("无此节点：" + yewu + "笔");
////			} else {
////				// fail++;
////				// System.out.println("失败：" + fail + "笔");
////			}
//			os.flush();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (os != null) {
//					os.close();
//				}
//				if (c != null) {
//					c.close();
//				}
//				if (is != null) {
//					is.close();
//				}
//				
//			
//
//				// if (reqfile.exists()) {
//				// reqfile.delete();
//				// }
//
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		return reBuffer.toString().substring(8);
//	}

	public static void main(String[] args) throws Exception {
		String resultStr = NewSocketClient.requestHandle();
		System.out.println("===响应报文"+resultStr);
	}

	// public static void DOM4Jcreate(File file)throws Exception{
	//
	// file=new File("D:\\项目\\wxgzh\\04-开发\\样例\\500002.xml");
	// org.dom4j.Document document=DocumentHelper.createDocument();
	// org.dom4j.Element root=document.addElement("rss");
	// root.addAttribute("version", "2.0");
	// org.dom4j.Element channel=root.addElement("channel");
	// org.dom4j.Element title=channel.addElement("title");
	// title.setText("新闻国内");
	// //...
	// XMLWriter writer=new XMLWriter(new
	// FileOutputStream(file),OutputFormat.createPrettyPrint());
	// writer.setEscapeText(false);//字符是否转义,默认true
	// writer.write(document);
	// writer.close();
	// }

}
