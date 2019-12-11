package com.jjb.cmp.app.controller.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
	
	private static final String key_algorithm="AES";
	private static final String default_cipher_algorithm="SHA1PRNG";
	
	/**
	 * 加密
	 * @param content
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String content, String key)
				throws Exception {
			KeyGenerator kgen = KeyGenerator.getInstance(key_algorithm);
			SecureRandom random = SecureRandom.getInstance(default_cipher_algorithm);
			random.setSeed(key.getBytes());
			kgen.init(128, random);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, key_algorithm);
			Cipher cipher = Cipher.getInstance(key_algorithm);
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			byte[] byteRresult = cipher.doFinal(byteContent);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteRresult.length; i++) {
				String hex = Integer.toHexString(byteRresult[i] & 0xFF);
				if (hex.length() == 1) {
					hex = '0' + hex;
				}
				sb.append(hex.toUpperCase());
			}
			return sb.toString();
		}
	
	/**
	 * 解密
	 * @param content
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String content, String key)
			throws Exception {
		if (content.length() < 1)
			return null;
		byte[] byteRresult = new byte[content.length() / 2];
		for (int i = 0; i < content.length() / 2; i++) {
			int high = Integer.parseInt(content.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(content.substring(i * 2 + 1, i * 2 + 2),	16);
			byteRresult[i] = (byte) (high * 16 + low);
		}
		KeyGenerator kgen = KeyGenerator.getInstance(key_algorithm);
		SecureRandom random = SecureRandom.getInstance(default_cipher_algorithm);
		random.setSeed(key.getBytes());
		kgen.init(128, random);
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, key_algorithm);
		Cipher cipher = Cipher.getInstance(key_algorithm);
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
		byte[] result = cipher.doFinal(byteRresult);
		return new String(result);
	}
	
	public static void main(String[] args) throws Exception {
		com.alibaba.fastjson.JSONObject json=new com.alibaba.fastjson.JSONObject();
        //json.put("batchNo", "05c86d1d-632a-42f6-a94d-195e8427661d");
		//json.put("custName", "高下");
		json.put("idNo", "362325198501121215");
		json.put("sysId", "cmp");
		
		String s=json.toJSONString();
		String sen=encrypt(s,"hhh***");
		String sde=decrypt(sen,"hhh***");
		System.out.println(s);
		System.out.println(sen);
		System.out.println(sde);
	}

}
