// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TransPIN.java

package com.jjb.ecms.adapter.utils.ccif;
/**
 *@ClassName TransPIN
 *@Description 九江综合前置加密报文，不要修改，不要删除
 *@Author lixing
 *Date 2018/10/16 9:31
 *Version 1.0
 */
public class TransPIN
{

	public TransPIN()
	{
	}

	public static byte[] EncryptPIN(byte bpin[], byte account[], byte key[])
	{
		String pin = new String(bpin);
		byte result[] = new byte[8];
		int pinLen = pin.length();
		String tempStr = String.format("%02d%s", new Object[] {
			Integer.valueOf(pinLen), pin
		});
		byte temp1[] = new byte[16];
		System.arraycopy(tempStr.getBytes(), 0, temp1, 0, tempStr.length());
		for (int i = tempStr.length(); i < 16; i++)
			temp1[i] = 70;

		byte temp2[] = new byte[16];
		for (int i = 0; i < 4; i++)
			temp2[i] = 48;

		if (account.length > 12)
			System.arraycopy(account, account.length - 13, temp2, 4, 12);
		else
			System.arraycopy(account, 0, temp2, (4 + account.length) - 12, account.length);
		byte abTemp1[] = Ascii2Binary(temp1, 16);
		byte abTemp2[] = Ascii2Binary(temp2, 16);
		byte abTemp3[] = new byte[8];
		for (int i = 0; i < 8; i++)
			abTemp3[i] = (byte)(abTemp1[i] ^ abTemp2[i]);

		try
		{
			result = DES2.encrypt(abTemp3, key);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	public static byte[] EncryptPIN(String pwd, String account, String pmac)
	{
		byte key[] = new byte[8];
		byte temp1[] = new byte[8];
		byte temp2[] = new byte[8];
		byte mac[] = new byte[16];
		temp1 = Ascii2Binary(pmac.getBytes(), 16);
		try
		{
			key = DES2.decrypt(temp1, "87654321".getBytes());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		temp2 = EncryptPIN(pwd.getBytes(), account.getBytes(), key);
		mac = Binary2Ascii(temp2, 8);
		return mac;
	}

	public static String EncryptKey(String pinKey, String mainKey)
	{
		byte temp1[] = new byte[8];
		byte temp2[] = new byte[8];
		byte temp3[] = new byte[16];
		temp1 = Ascii2Binary(pinKey.getBytes(), 16);
		try
		{
			temp2 = DES2.encrypt(temp1, mainKey.getBytes());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		temp3 = Binary2Ascii(temp2, 8);
		return new String(temp3);
	}

	public static byte[] DecryptPIN(byte pin[], byte account[], byte key[])
	{
		int sPinLen = 0;
		byte temp1[] = new byte[16];
		byte temp2[] = new byte[16];
		byte abTemp1[] = new byte[8];
		byte abTemp2[] = new byte[8];
		byte abTemp3[] = new byte[8];
		byte achLen[] = new byte[2];
		byte outPIn[] = new byte[8];
		try
		{
			abTemp3 = DES2.decrypt(pin, key);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		int index = 0;
		for (int i = 0; i < 4; i++)
			temp2[i] = 48;

		index += 4;
		if (account.length >= 13)
		{
			System.arraycopy(account, account.length - 13, temp2, index, 12);
		} else
		{
			for (int i = 0; i < 12; i++)
				temp2[index + i] = 48;

			System.arraycopy(account, 0, temp2, (index + account.length) - 12, account.length);
		}
		abTemp2 = Ascii2Binary(temp2, 16);
		for (int i = 0; i < 8; i++)
			abTemp1[i] = (byte)(abTemp2[i] ^ abTemp3[i]);

		temp1 = Binary2Ascii(abTemp1, 8);
		System.arraycopy(temp1, 0, achLen, 0, 2);
		String str = new String(achLen);
		sPinLen = Integer.parseInt(str);
		System.arraycopy(temp1, 2, outPIn, 0, sPinLen);
		return (new String(outPIn)).trim().getBytes();
	}

	private static boolean isXDigit(byte b)
	{
		if (b >= 48 && b <= 57)
			return true;
		if (b >= 97 && b <= 102)
			return true;
		return b >= 65 && b <= 70;
	}

	public static byte[] Ascii2Binary(byte ascii[], int len)
	{
		byte bin[] = new byte[len / 2];
		int sRc = 0;
		for (int i = 0; i < len && sRc == 0; i += 2)
			if (isXDigit(ascii[i]))
				bin[i / 2] = (byte)(ToHex(ascii[i]) << 4);

		for (int i = 1; i < len && sRc == 0; i += 2)
			if (isXDigit(ascii[i]))
				bin[i / 2] += ToHex(ascii[i]);

		return bin;
	}

	private static byte ToHex(byte ch)
	{
		if (ch >= 48 && ch <= 57)
			return (byte)(ch & 0xf);
		else
			return (byte)((ch & 0xf) + 9 & 0xf);
	}

	public static byte[] Binary2Ascii(byte bin[], int len)
	{
		String str = "";
		for (int i = 0; i < bin.length; i++)
			str = (new StringBuilder(String.valueOf(str))).append(String.format("%02X", new Object[] {
				Byte.valueOf(bin[i])
			})).toString();

		return str.getBytes();
	}

	public static byte[] GetMac(byte pchData[], int sLen, byte pMACKey[])
	{
		pMACKey = Ascii2Binary(pMACKey, 16);
		byte temp3[] = new byte[8];
		byte source[] = new byte[8];
		byte dest[] = new byte[8];
		byte result[] = new byte[16];
		byte sourceBuf[] = new byte[20480];
		for (int i = 0; i < 20480; i++)
			sourceBuf[i] = 0;

		for (int i = 0; i < sLen; i++)
			sourceBuf[i] = pchData[i];

		int DF = 8;
		int remain = sLen % DF;
		if (remain != 0)
			sLen += DF - remain;
		int count = sLen / DF;
		for (int k = 0; k < count; k++)
		{
			for (int i = 0; i < 8; i++)
				source[i] = sourceBuf[k * DF + i];

			for (int j = 0; j < 8; j++)
				source[j] = (byte)(dest[j] ^ source[j]);

			try
			{
				dest = DES2.encrypt(source, pMACKey);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		for (int k = 0; k < 8; k++)
			temp3[k] = dest[k];

		result = Binary2Ascii(temp3, 8);
		return result;
	}

	public static byte[] GetMac2(byte pchData[], int sLen, byte pMACKey[])
	{
		byte temp1[] = null;
		temp1 = Ascii2Binary(pMACKey, 16);
		try
		{
			pMACKey = DES2.decrypt(temp1, "87654321".getBytes());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		byte bb[] = Binary2Ascii(pMACKey, 8);
		byte temp3[] = new byte[8];
		byte source[] = new byte[8];
		byte dest[] = new byte[8];
		byte result[] = new byte[16];
		byte sourceBuf[] = new byte[4096];
		for (int i = 0; i < 4096; i++)
			sourceBuf[i] = 0;

		for (int i = 0; i < sLen; i++)
			sourceBuf[i] = pchData[i];

		int DF = 8;
		int remain = sLen % DF;
		if (remain != 0)
			sLen += DF - remain;
		int count = sLen / DF;
		for (int k = 0; k < count; k++)
		{
			for (int i = 0; i < 8; i++)
				source[i] = sourceBuf[k * DF + i];

			for (int j = 0; j < 8; j++)
				source[j] = (byte)(dest[j] ^ source[j]);

			try
			{
				dest = DES2.encrypt(source, pMACKey);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		for (int k = 0; k < 8; k++)
			temp3[k] = dest[k];

		result = Binary2Ascii(temp3, 8);
		return result;
	}

	public static byte[] getRealKey(String key, String mainkey)
	{
		byte key2[] = null;
		if (mainkey == null || mainkey.trim().equals(""))
			return key2;
		byte temp1[] = Ascii2Binary(key.getBytes(), 16);
		try
		{
			key2 = DES2.decrypt(temp1, mainkey.getBytes());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return key2;
		}
		return key2;
	}

	public static String DecryptPIN(String pin, String account, String pinkey)
	{
		byte key[] = getRealKey(pinkey, "87654321");
		byte pin2[] = Ascii2Binary(pin.getBytes(), 16);
		byte out[] = new byte[8];
		out = DecryptPIN(pin2, account.getBytes(), key);
		return new String(out);
	}

	public static void main(String args1[])
		throws Exception
	{
	}

	private static void testMac2()
	{
		String mackey = "71016309BA8D9DCE";
		String macKey2 = EncryptKey(mackey, "87654321");
		String data = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\t\t<service>\t\t\t<SYS_HEAD>\t\t\t\t<Mac>00000000000000</Mac>\t\t\t\t<MsgId>1c1e4ecf-ac30-4d1e-978a-1f241569c556</MsgId>\t\t\t\t<SourceSysId>100360</SourceSysId>\t\t\t\t<ConsumerId>100360</ConsumerId>\t\t\t\t<ServiceCode>CaChlQryAcctNoInfo</ServiceCode>\t\t\t\t<ServiceScene>01</ServiceScene>\t\t\t\t<ReplyAdr></ReplyAdr>\t\t\t\t<ExtendContent></ExtendContent>\t\t\t</SYS_HEAD>\t\t\t<APP_HEAD>\t\t\t\t<TranDate>20170413</TranDate>\t\t\t\t<TranTime>090504</TranTime>\t\t\t\t<TranTellerNo>9988</TranTellerNo>\t\t\t\t<TranSeqNo>100360C02203C6852326736195485696</TranSeqNo>\t\t\t\t<GlobalSeqNo>100360C02203C6852326536378843136</GlobalSeqNo>\t\t\t\t<BranchId>731</BranchId>\t\t\t\t<TerminalCode>00000001</TerminalCode>\t\t\t\t<CityCode></CityCode>\t\t\t\t<AuthrTellerNo></AuthrTellerNo>\t\t\t\t<AuthrPwd></AuthrPwd>\t\t\t\t<AuthrCardFlag></AuthrCardFlag>\t\t\t\t<AuthrCardNo></AuthrCardNo>\t\t\t\t<LangCode>CHINESE</LangCode>\t\t\t</APP_HEAD>\t\t\t<BODY>\t\t\t\t<AcNo>9558880200001851475</AcNo>\t\t\t\t<VoucherNo></VoucherNo>\t\t\t\t<PayMode></PayMode>\t\t\t\t<TrsPassword>1234567890ABCDEF</TrsPassword>\t\t\t\t<Currency></Currency>\t\t\t\t<CRFlag></CRFlag>\t\t\t\t<AcSer></AcSer>\t\t\t\t<OptCode>0</OptCode>\t\t\t\t<FrzInd></FrzInd>\t\t\t\t<OpenDate></OpenDate>\t\t\t\t<SysTrcNo></SysTrcNo>\t\t\t\t<Amount></Amount>\t\t\t</BODY>\t\t</service>";
		byte bb[] = GetMac(data.getBytes(), data.length(), mackey.getBytes());
		byte cc[] = GetMac2(data.getBytes(), data.length(), macKey2.getBytes());
	}

	private static void testMac()
	{
		String pwd = "111111";
		String account = "6231467272734826460";
		String pkey = "A136D4917E3C20A0";
		String mackey = "144595FBC058B8B8";
		String data = "23806231467272747822860";
		byte temp1[] = getRealKey("144595FBC058B8B8", "87654321");
		byte bb[] = GetMac(data.getBytes(), data.length(), mackey.getBytes());
	}

	public static byte[] testRealKey(String key, String mainkey)
	{
		byte key2[] = null;
		if (mainkey == null || mainkey.trim().equals(""))
			return key2;
		byte temp1[] = Ascii2Binary(key.getBytes(), 16);
		try
		{
			key2 = DES2.encrypt(temp1, mainkey.getBytes());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return key2;
		}
		return key2;
	}

	public static String genPINKey()
	{
		return "4DDB65EA5ACE3368";
	}

	public static String genMacKey()
	{
		return "71016309BA8D9DCE";
	}
}
