package com.jjb.unicorn.facility.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 掩码工具类，包括身份证、卡号、手机号
 * 
 * @author H.N
 * 
 */
@Component
public class CodeMarkUtils {

	private final static String asterisk = "*";
	
	@Value("#{env['makeSensitiveInfo'] ?: true}")
	boolean makeSensitiveInfo = true;

	private static String getAsterisk(int length) {
		return StringUtils.repeat(asterisk, length);
	}

	/**
	 * 身份证掩码生日部分
	 * 
	 * @param idCardNo
	 *            身份证号
	 * @return
	 */
	private static String markIDCard(String idCardNo) {
		if (idCardNo == null)
			return null;
		if (idCardNo.length() < 15)
			return idCardNo;
		int length = idCardNo.length() == 15 ? 6 : 8;
		return idCardNo.replaceAll("(?<=\\d{6})(\\d{" + length + "})(?=\\d+)",
				getAsterisk(length));
	}

	/**
	 * 手机号掩码
	 * 
	 * @param mobileNo
	 *            手机号
	 * @return
	 */
	public static String markMobile(String mobileNo) {
		if (mobileNo == null)
			return null;
		if (mobileNo.length() < 11) {
			return mobileNo;
		}
		return mobileNo.replaceAll("(?<=\\d{3})(\\d{4})(?=\\d+)",
				getAsterisk(4));
	}

	/**
	 * 卡号掩码
	 * 
	 * @param cardNo
	 *            卡号
	 * @param start
	 *            卡号起始显示长度
	 * @param end
	 *            结尾显示长度
	 * @return
	 */
	private static String markCreditCard(String cardNo, int start, int end) {
		if (cardNo == null)
			return null;
		if (cardNo.length() < 10)
			return cardNo;
		int length = cardNo.length() - start - end;
		return cardNo.replaceAll("(?<=\\d{" + start + "})(\\d{" + length
				+ "})(?=\\d{" + end + "})", getAsterisk(length));
	}

	/**
	 * 返回卡号后四位
	 * 
	 * @param cardNo
	 *            卡号
	 * @return
	 */
	public static String subCreditCard(String cardNo) {
		if (cardNo == null || cardNo.length() < 4)
			return null;
		return cardNo.substring(cardNo.length() - 4);
	}

	/**
	 * 卡号掩码，默认前6位和后4位不进行掩码
	 * 
	 * @param cardNo
	 * @return
	 */
	private static String markCreditCard(String cardNo) {
		return markCreditCard(cardNo, 6, 4);
	}
	
	
	/**
	 * 
	 * @param source 需要处理的xml
	 * @param label 需要匹配的标签
	 * @param value 已经处理好的数据
	 * @return
	 */
	public static String xmlReplace(String source,String label,String value)
	{
		if(StringUtils.isBlank(source)||StringUtils.isBlank(label)||StringUtils.isBlank(value)){
			return source;
		}
		 label = label.toUpperCase();
		  String regReplace = "(\\<"+label+"\\>)\\S+(\\<\\/"+label+"\\>)";
		  String strLogger = source;
	      strLogger = source.replaceAll (regReplace,"$1"+value+"$2");
	      return strLogger;
	}
	
	
	/**
	 * 
	 * @param 需要处理的xml
	 * @param 需要匹配的标签
	 * @param 需要处理的数据
	 * @param 选择处理方式 type为1：信用卡号，2：电话，3：身份证号
	 * @return
	 */
	public static String xmlReplace2(String source,String label,String value,int type)
	{
		if(StringUtils.isBlank(source)||StringUtils.isBlank(label)||StringUtils.isBlank(value)){
			return source;
		}
		//xml文件中对应的标签名称需要修改
		 label = label.toUpperCase();
		  String regReplace ="(\\<"+label+"\\>)\\S+(\\<\\/"+label+"\\>)";
		  String strLogger = source;
		  switch (type) {
			case 1:
				 strLogger = source.replaceAll (regReplace,"$1"+CodeMarkUtils.subCreditCard(value)+"$2");
				break;
			case 2:
				 strLogger = source.replaceAll (regReplace,"$1"+CodeMarkUtils.markMobile(value)+"$2");
				break;
			case 3:
				 strLogger = source.replaceAll (regReplace,"$1"+CodeMarkUtils.markIDCard(value)+"$2");
				break;
		  }
		  return strLogger;
	}
	
	
	public String makeMask(String message){
		if(StringUtils.isBlank(message)){
			return "";
		}
		if(makeSensitiveInfo){
			return "[" + Base64.encodeBase64String(message.getBytes()) + "]";
		}else{
			return message;
		}
	}
	
	/**
	 * <p>目的：为交易日志监控专门使用的消息加密方法</p>
	 * <p>承诺：不使用开关限制，默认全部掩码</p>
	 * @param message 需要加密消息对象
	 * @return
	 */
	public String makeTradeMask(String message){
		if(StringUtils.isBlank(message)){
			return "";
		}
		return Base64.encodeBase64String(message.getBytes());
	}
	
	
	public String makeCardMask(String cardNo){
		if(StringUtils.isBlank(cardNo)){
			return "";
		}
		if(makeSensitiveInfo){
			return "[" + Base64.encodeBase64String(cardNo.getBytes())+"]" + " | [" + markCreditCard(cardNo)+ "]";
		}else{
			return cardNo;
		}
		
	}
	
	/**
	 * <p>目的：为交易日志监控专门使用的卡号加密方法</p>
	 * <p>承诺：不使用开关限制，默认全部掩码</p>
	 * @param cardNo 卡号
	 * @return
	 */
	public String makeTradeCardMask(String cardNo){
		if(StringUtils.isBlank(cardNo)){
			return "";
		}
		return markCreditCard(cardNo);
	}

	public String makeIDCardMask(String idNo){
		
		if(StringUtils.isBlank(idNo)){
			return "";
		}
		if(makeSensitiveInfo){
			return "[" + Base64.encodeBase64String(idNo.getBytes())+"]" + " | [" + markIDCard(idNo)+ "]";
		}else{
			return idNo;
		}
		
	}
	
	
	public static void main(String[] args) {
		CodeMarkUtils codeMarkUtils = new CodeMarkUtils();
//		System.out.println(CodeMarkUtils.markIDCard("210726198801129801"));
//		System.out.println(CodeMarkUtils.markMobile("13421211212"));
//		System.out.println(CodeMarkUtils.markCreditCard("1001101010101010101"));
//		System.out.println(CodeMarkUtils.subCreditCard("100110101010190111"));
		System.out.println(codeMarkUtils.makeMask("6283350030000131"));
		System.out.println(codeMarkUtils.makeCardMask("6283350030000131"));
		System.out.println(codeMarkUtils.makeIDCardMask("220104198506020059"));
//		CodeMarkUtils.makeMask("6283350030000131")
		/*long start = System.currentTimeMillis();
		System.out.println(start);
		for(int i=0;i<100000;i++){
			System.out.println(CodeMarkUtils.makeMask("6283350030000131"));
		}
		System.out.println(System.currentTimeMillis()-start);*/
//		BigDecimal b = new BigDecimal(2.19);
//		System.out.println(b.setScale(0, BigDecimal.ROUND_DOWN));
	}
}
