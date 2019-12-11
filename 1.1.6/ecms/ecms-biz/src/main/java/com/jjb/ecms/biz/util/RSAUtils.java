 package com.jjb.ecms.biz.util;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class RSAUtils {
	private static Logger logger = LoggerFactory.getLogger(RSAUtils.class);


	public static final String KEY_ALGORITHM = "RSA";
	public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
	public static final String PUBLIC_KEY = "publicKey";
	public static final String PRIVATE_KEY = "privateKey";
    private static final int MAX_ENCRYPT_BLOCK = 245;  
 ;
	/** RSA密钥长度必须是64的倍数，在512~65536之间。默认是1024 */
	public static final int KEY_SIZE = 2048 * 4;

	public static final String PLAIN_TEXT = "张三";
	
	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 256;
	
	private static PrivateKey privateKey;

	private static PrivateKey getPrivateKey() throws Exception {
		if (privateKey == null) {
			privateKey = RSAUtils
					.loadPrivateKeyByString("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQClI1kDm42CwyqFngohdtEuuEZRk4lJonnWmybWRCFIq3R8IH5uszLTZeW6NMFD/+9dkQuy0jiM6mNp7WgXhNjkHhRgYB5kjrHXYklmWEpNm8yeaScFiDx1gHRkGgiXrf+cfeSPq91DXy7eM0lQktT0xF6nHUBkZVW0ancMtjSRWFznlFvQFj6eSvF/yv+8qn9zfMRMOJQDqRDMVydraWa4TVMEpRievl/fNxWg5sZXQrGRnZN6BEdTkFkTpGty0b4SFK/9u0YwL0X9hvfgfZ6rr6wcQ5ScAhkblVXOpPWEjJ5SBd7c58frjwLw0ELL45sAF6rc19om/YA1neCEmx85AgMBAAECggEAQZbujf5EkIFP7E4/G9eDchkC5PfCeZqs5c4t+MCKr8BZvctyLCnEsC4ytmipsoZ+3hz0FoxkpaOa5MHErEK5mrFPyo+iwrHidPe2y42ZxYVyGiENvKbFzK2+jNvCOeyEL/XYyC+s7Etr5Bler0ACkU1Fgv6dSsLs7YGz0zqzWl6ozhWbScslZDDOf/NmmIetNdtSl0eqLr6oymi/F2avrro1egP+1v2Wm5fbzwRmRfDFvRs+R1sdCWfv/Oys8AGmNvsb+pRR77sfe19yqT2SYp4s3IP7CoXkVU9vPAsclltDcyVzCmzVzYqAWzt0PF9OxAEclFA3+n3yIGavmjCC+QKBgQDYPLSMbmyKBuDx6T/hw302YOBhFlstzVbC7E3IOUe6g3HRhGtfqcTyLc2Vt7IFn13wU2Wj5PuCV5yA5JJrsF7MVrteG36p8FuUXo24nWu6rnPR9/HeZExKaYg4JumBhgOIPfxn/7eHSHuc2IO3FagTVfGhSZGvUcSJNKxKL99dqwKBgQDDgSuulBduOFlP0B4LMIZF+DB5w/mrdwGu2wF4NtCpnaiAJDdajYIJs2TnIOOBLqD09KT9gJp/bWWyuXWMXvRPIvmeJVi2kRdifDpaa0CrKW0IAS5UnK9O2dNSN7DTR3PEK4N+JJwtaoXMXB8nTNjlM05VrsOtHg9RdfhLZpyqqwKBgQC3miO7MW9bayfIf8JMEU7ZwzwBc5gLtdQ7QaVxoqEyzrTvrz30RhQBhb6Ppx+zAnQiwM0GckDTlUw/bNSeN2zml6YT68iKxpkby4UOAiwcoE+bMqndqM/WRvDwrKmL3pxVLDyKmWfdqD+9F2IPLIY/lLPLvMnEUOtNjLnVa/x+bQKBgGL0urt+0e1EMFCpsstPIGfCHCdF/tW/QubSi4fhlvSDjIpvymTXQ37NgsFKcF0u0OGsAucddRYStWslXE5Usaxss5RMqv6dqgx7fL3Pk8gPC9UFZAIH3OQ4dTIkZihK/lnI/KGYvkRuqpKp/krSJua8dWYBM6IomPBOmgLwSVRJAoGAY30Pypw4aGJQ3jUhM7+EJHi0hndtQgGm/N3jjM+XQHZUqnLpPcc4Qi01E1mrFUaWjBNeQB7/SlL8sber49IU0NWXtUsPVn72IB+2RU8BtVk6J47PAGBL7VTi0oaPh5TPeTG7bBWYwTsipOebuRRQl6Gw81EJqwueLBF0gwC1uVM=");
		}
		return privateKey;
	}

	/**
	 * 生成密钥对。注意这里是生成密钥对KeyPair，再由密钥对获取公私钥
	 * 
	 * @return
	 */
	public static Map<String, byte[]> generateKeyBytes() {

		try {
			// 实例化密钥生成器
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
			// 初始化密钥生成器
			keyPairGenerator.initialize(KEY_SIZE);
			// 生成密钥对
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			// 甲方公钥
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//			System.out.println("系数：" + publicKey.getModulus() + "  加密指数：" + publicKey.getPublicExponent());
//			System.out.println(encryptBASE64(publicKey.getEncoded()));
			// 甲方私钥
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
//			System.out.println("系数：" + privateKey.getModulus() + "解密指数：" + privateKey.getPrivateExponent());
//			System.out.println(encryptBASE64(privateKey.getEncoded()));
			// 将密钥存储在map中
			Map<String, byte[]> keyMap = new HashMap<String, byte[]>();
			keyMap.put(PUBLIC_KEY, publicKey.getEncoded());
			keyMap.put(PRIVATE_KEY, privateKey.getEncoded());
			return keyMap;
		} catch (Exception e) {
			logger.error("生成密钥对 PUBLIC_KEY   PRIVATE_KEY 失败,返回null "+e.getMessage());
		}
		return null;
	}

	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	public static String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	/**
	 * 还原公钥，X509EncodedKeySpec 用于构建公钥的规范
	 * 
	 * @param keyBytes
	 * @return
	 */
	public static PublicKey restorePublicKey(byte[] keyBytes) {
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);

		try {
			KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
			PublicKey publicKey = factory.generatePublic(x509EncodedKeySpec);
			return publicKey;
		} catch (Exception e) {
			logger.error("还原公钥失败 keyBytes = ["+keyBytes+"]"+e.getMessage());
		}
		return null;
	}

	/**
	 * 还原私钥，PKCS8EncodedKeySpec 用于构建私钥的规范
	 * 
	 * @param keyBytes
	 * @return
	 */
	public static PrivateKey restorePrivateKey(byte[] keyBytes) {
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
		try {
			KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
			PrivateKey privateKey = factory.generatePrivate(pkcs8EncodedKeySpec);
			return privateKey;
		} catch (Exception e) {
			logger.error("还原私钥失败 keyBytes = ["+keyBytes+"]"+e.getMessage());
		}
		return null;
	}

	/**
	 * 加密，三步走。
	 * 
	 * @param key
	 * @param plainText
	 * @return
	 */
	public static byte[] RSAEncode(PublicKey key, byte[] plainText) {

		try {
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(plainText);
		} catch (Exception e) {
			logger.error("加密失败 key = ["+key+"]"+e.getMessage());
		}
		return null;

	}

	/**
	 * 解密，三步走。
	 * 
	 * @param key
	 * @param encodedText
	 * @return
	 */
	public static String RSADecode(PrivateKey key, byte[] encodedText) {

		try {
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			return new String(cipher.doFinal(encodedText));
		} catch (Exception e) {
			logger.error("解密失败key = ["+key+"]"+e.getMessage());
		}
		return null;
	}

	public static RSAPublicKey loadPublicKeyByString(String publicKeyString) throws Exception {
		try {
			byte[] buffer = Base64.decodeBase64(publicKeyString);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NullPointerException e) {
			throw new Exception("公钥数据为空");
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥数据为空");
		}
	}

	public static RSAPrivateKey loadPrivateKeyByString(String privateKeyString) throws Exception {
		try {
			byte[] buffer = Base64.decodeBase64(privateKeyString);
			// PKCS12EncodedKeySpec keySpec=new PKCs1
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("私钥非法");
		}
	}

	/**
	 * RSA解密
	 * 
	 * @param rasEncodeString
	 * @return
	 * @throws Exception
	 */
	public static String RSADecoder(String rasEncodeString) throws Exception {
		byte[] encryptedData = Base64.decodeBase64(rasEncodeString);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, getPrivateKey());
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return new String(decryptedData,"UTF-8");
	}

	public static void main(String[] args) throws Exception {

		// 加密
		//PublicKey publicKey = restorePublicKey(decryptBASE64("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApSNZA5uNgsMqhZ4KIXbRLrhGUZOJSaJ51psm1kQhSKt0fCB+brMy02XlujTBQ//vXZELstI4jOpjae1oF4TY5B4UYGAeZI6x12JJZlhKTZvMnmknBYg8dYB0ZBoIl63/nH3kj6vdQ18u3jNJUJLU9MRepx1AZGVVtGp3DLY0kVhc55Rb0BY+nkrxf8r/vKp/c3zETDiUA6kQzFcna2lmuE1TBKUYnr5f3zcVoObGV0KxkZ2TegRHU5BZE6RrctG+EhSv/btGMC9F/Yb34H2eq6+sHEOUnAIZG5VVzqT1hIyeUgXe3OfH648C8NBCy+ObABeq3NfaJv2ANZ3ghJsfOQIDAQAB"));
		//byte[] encodedText = rsaEncode(publicKey, PLAIN_TEXT.getBytes("utf-8"));
		//String encodeBase64String = Base64.encodeBase64String(encodedText);
//		System.out.println("RSA encoded: " + encodeBase64String);

		// 解密
		//PrivateKey privateKey = restorePrivateKey(decryptBASE64("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQClI1kDm42CwyqFngohdtEuuEZRk4lJonnWmybWRCFIq3R8IH5uszLTZeW6NMFD/+9dkQuy0jiM6mNp7WgXhNjkHhRgYB5kjrHXYklmWEpNm8yeaScFiDx1gHRkGgiXrf+cfeSPq91DXy7eM0lQktT0xF6nHUBkZVW0ancMtjSRWFznlFvQFj6eSvF/yv+8qn9zfMRMOJQDqRDMVydraWa4TVMEpRievl/fNxWg5sZXQrGRnZN6BEdTkFkTpGty0b4SFK/9u0YwL0X9hvfgfZ6rr6wcQ5ScAhkblVXOpPWEjJ5SBd7c58frjwLw0ELL45sAF6rc19om/YA1neCEmx85AgMBAAECggEAQZbujf5EkIFP7E4/G9eDchkC5PfCeZqs5c4t+MCKr8BZvctyLCnEsC4ytmipsoZ+3hz0FoxkpaOa5MHErEK5mrFPyo+iwrHidPe2y42ZxYVyGiENvKbFzK2+jNvCOeyEL/XYyC+s7Etr5Bler0ACkU1Fgv6dSsLs7YGz0zqzWl6ozhWbScslZDDOf/NmmIetNdtSl0eqLr6oymi/F2avrro1egP+1v2Wm5fbzwRmRfDFvRs+R1sdCWfv/Oys8AGmNvsb+pRR77sfe19yqT2SYp4s3IP7CoXkVU9vPAsclltDcyVzCmzVzYqAWzt0PF9OxAEclFA3+n3yIGavmjCC+QKBgQDYPLSMbmyKBuDx6T/hw302YOBhFlstzVbC7E3IOUe6g3HRhGtfqcTyLc2Vt7IFn13wU2Wj5PuCV5yA5JJrsF7MVrteG36p8FuUXo24nWu6rnPR9/HeZExKaYg4JumBhgOIPfxn/7eHSHuc2IO3FagTVfGhSZGvUcSJNKxKL99dqwKBgQDDgSuulBduOFlP0B4LMIZF+DB5w/mrdwGu2wF4NtCpnaiAJDdajYIJs2TnIOOBLqD09KT9gJp/bWWyuXWMXvRPIvmeJVi2kRdifDpaa0CrKW0IAS5UnK9O2dNSN7DTR3PEK4N+JJwtaoXMXB8nTNjlM05VrsOtHg9RdfhLZpyqqwKBgQC3miO7MW9bayfIf8JMEU7ZwzwBc5gLtdQ7QaVxoqEyzrTvrz30RhQBhb6Ppx+zAnQiwM0GckDTlUw/bNSeN2zml6YT68iKxpkby4UOAiwcoE+bMqndqM/WRvDwrKmL3pxVLDyKmWfdqD+9F2IPLIY/lLPLvMnEUOtNjLnVa/x+bQKBgGL0urt+0e1EMFCpsstPIGfCHCdF/tW/QubSi4fhlvSDjIpvymTXQ37NgsFKcF0u0OGsAucddRYStWslXE5Usaxss5RMqv6dqgx7fL3Pk8gPC9UFZAIH3OQ4dTIkZihK/lnI/KGYvkRuqpKp/krSJua8dWYBM6IomPBOmgLwSVRJAoGAY30Pypw4aGJQ3jUhM7+EJHi0hndtQgGm/N3jjM+XQHZUqnLpPcc4Qi01E1mrFUaWjBNeQB7/SlL8sber49IU0NWXtUsPVn72IB+2RU8BtVk6J47PAGBL7VTi0oaPh5TPeTG7bBWYwTsipOebuRRQl6Gw81EJqwueLBF0gwC1uVM="));
		//byte[] decryptBASE64 = Base64.decodeBase64(encodeBase64String);
//		System.out.println(decryptBASE64.length);
//		System.out.println("RSA decoded: " + RSADecode(privateKey, decryptBASE64));
	
	     String key = "1221#12";
	     String password = "aMDUJxeUWKJzsr0vwP9ZrCtj+Ua+trKqO0uRKdzMwocuw55qi2GnSsgrFpTJCeN2e3FNiRZfdsPWTCe2DnXjb7oGq7U8x2gk05HxW4VHKf16bO9sMNJG8EivJvZf2KcHytHoj+Yfa7dVSnGVmqbW6rlcIhYgV3o9IpyOdUmvXvW9k8oygZS3JdMpFRpvTerEqDlaBY3GBd/pbryuXDMTlk7xK4m9TF6sbvQ6rAYQ/Wf9AFOu66yxm7s4TMu0kb53n726bBNJchz6WnzwNsGU3aRUl0EkQHQQcGmGhiIGNR6G9Bh+G8cRD4cS1NluzgIsWqX7OZODS/wSlfJ03QiMjQ==";
	     System.out.println(rsaEncode(key));
	     System.out.println(RSADecoder(password));
	}
	
	/**
	 * RSA加密
	 * @param oriString
	 * @return
	 * @throws Exception
	 */
	    public static String rsaEncode(String oriString) throws Exception {
		if(oriString == null || "".equals(oriString)){
			return "";
		}
		byte[] data = oriString.getBytes("utf-8");
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();  
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, getPublicKey());
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {  
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(data, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_ENCRYPT_BLOCK;  
        }  
        byte[] encryptedData = out.toByteArray();  
        out.close();  
		return Base64.encodeBase64String(encryptedData);
	}
	
	private static PublicKey publicKey;
	private static PublicKey getPublicKey() throws Exception{
		if(publicKey == null){
			publicKey = RSAUtils.restorePublicKey(RSAUtils .decryptBASE64("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApSNZA5uNgsMqhZ4KIXbRLrhGUZOJSaJ51psm1kQhSKt0fCB+brMy02XlujTBQ//vXZELstI4jOpjae1oF4TY5B4UYGAeZI6x12JJZlhKTZvMnmknBYg8dYB0ZBoIl63/nH3kj6vdQ18u3jNJUJLU9MRepx1AZGVVtGp3DLY0kVhc55Rb0BY+nkrxf8r/vKp/c3zETDiUA6kQzFcna2lmuE1TBKUYnr5f3zcVoObGV0KxkZ2TegRHU5BZE6RrctG+EhSv/btGMC9F/Yb34H2eq6+sHEOUnAIZG5VVzqT1hIyeUgXe3OfH648C8NBCy+ObABeq3NfaJv2ANZ3ghJsfOQIDAQAB"));
		}
		return publicKey;
	}
	
}