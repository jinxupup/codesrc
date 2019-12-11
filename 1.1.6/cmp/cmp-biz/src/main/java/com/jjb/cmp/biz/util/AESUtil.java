package com.jjb.cmp.biz.util;

import java.nio.charset.StandardCharsets;
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
			byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
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
//		json.put("custName", "高下");
		json.put("name", "马克");
		json.put("idNo", "362325198501121215");
//		json.put("idNo", "360104197811151924");
//		json.put("sysId", "cmp");
//		json.put("imageNum", "16b95ff6-2335-4b29-aa72-0a19fa7a4559");

		String s=json.toJSONString();
		String sen=encrypt(s,"hhh***");
		String sde=decrypt("5CD473AAB0FEFA31CFD13222B03CBAF638F3B25380D27D5BBEFA2DD756350B25712D4033456EA33DC4522867C8B93F2B355BC56F5FE436B0EF17BF43E96E43A7FADEC328A0A1C5F4001674B4ECE72DE436DD533885464CA9CEC32C25E35380BD","hhh***");
		System.out.println(s);
		System.out.println(sen);
		System.out.println(sde);


	}
	// {"idNo":"360104197811151924"} 7FF686ED51527F6593B9A589CC139C60DC282BAED623A26F4C75DB2DFE1ECA18
	// {"idNo":"321281199412174054"} 5489359954A06E0D23D069CCBB82A82D469E87084BEFC1B7F536F87BBDA332D4
	// {"name":"马克","idNo":"321281199412174054"} 9A726E4DC73D46A0DCF57463E89BB009CC2025F6DDE7846762B3FFEB50858B9B469E87084BEFC1B7F536F87BBDA332D4
}
