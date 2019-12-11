package com.jjb.ecms.adapter.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description: 定义常用的变量
 * @author JYData-R&D-Big H.N
 * @date 2016年3月31日 下午2:37:32
 * @version V1.0
 */
@Component
public class AdapterConstants {

	//报文长度验证位，一般默认8位长度，看系统设置
	@Value("#{env['lvMsgLength']?:8}")
	public Integer defLvMsgLength;
	//IP
	@Value("#{env['host']?:'127.0.0.1'}")
	public String host;
	//服务器端口
	@Value("#{env['port']?:'9902'}")
	public int port;
	//默认超时时间30秒
	@Value("#{env['connectTimeOutMillis']?:30000}")
	public Integer defTimeOut;
	/**
	 * 默认编码格式utf-8
	 * 第一位：报文添加长度验证位时计算使用；
	 * 第二位： 解析返回报文使用；
	 * 第三位：发送的报文整体长度计算使用；
	 */
	public static final String[] charset = new String[] {"UTF-8","UTF-8","UTF-8"};
	
	public static final String charsetName = "UTF-8|UTF-8|UTF-8";

	public static final String MESSAGE_KEY = "MESSAGE_KEY";

}
