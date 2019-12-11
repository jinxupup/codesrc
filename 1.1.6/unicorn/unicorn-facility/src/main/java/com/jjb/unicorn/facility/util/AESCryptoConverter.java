package com.jjb.unicorn.facility.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

/**
 * 秘钥加密解密类
 * @author jjb
 *
 */
public class AESCryptoConverter {

	private static final String SECRET_KEY = "secret.key";

	public final static String DEFAULT_KEY="yak-global-aec-key";
	
	private static String key = DEFAULT_KEY;
	
	static{
		//系统属性中取值
		 String systemKey = System.getProperty(SECRET_KEY);
		 if(StringUtils.isNotBlank(systemKey)){
			 key = systemKey;
		 }else{//从系统环境变量取，如取不到则使用默认
			 String envKey = System.getenv(SECRET_KEY);
			 if(StringUtils.isNotBlank(systemKey)){
				 key = envKey;
			 }
		 }
	}
	
	/**
	 * 加密
	 * @param plainText 明文密码
	 * @return
	 */
	public static String encrypt(String plainText){
		String result = AESUtils.encrypt(plainText, key);
		Assert.notNull(result, "加密结果不能为null!");
		return result;
	}
	
	/**
	 * 解密
	 * @param secrectText 密文
	 * @return
	 */
	public static String decrypt(String secrectText){
		String plainText = AESUtils.decrypt(secrectText, key);;
		return plainText;
	}
	
	public static void main(String[] args) throws IOException {
		String plainText = null;
		//首先从参数中取
		if(args!=null&&args.length>0){
			plainText = args[0];
		}else{ //否则提示用户输入
			BufferedReader buf = new BufferedReader( 
					new InputStreamReader(System.in));
			 plainText = buf.readLine();
			buf.close();
		}
		String secrectText = AESCryptoConverter.encrypt(plainText);
		System.exit(0);
	}
}
