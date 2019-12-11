// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DES2.java

package com.jjb.ecms.adapter.utils.ccif;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
/**
 *@ClassName TransPIN
 *@Description 九江综合前置加密报文，不要修改，不要删除
 *@Author lixing
 *Date 2018/10/16 9:32
 *Version 1.0
 */
public class DES2
{

	private static final String ALGORITHM = "DES";

	public DES2()
	{
	}

	public static byte[] encrypt(byte data[], byte key[])
		throws Exception
	{
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		javax.crypto.SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
		cipher.init(1, securekey, sr);
		return cipher.doFinal(data);
	}

	public static byte[] decrypt(byte data[], byte key[])
		throws Exception
	{
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		javax.crypto.SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
		cipher.init(2, securekey, sr);
		return cipher.doFinal(data);
	}
}
