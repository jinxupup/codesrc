package com.jjb.unicorn.facility.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 生成日志内部流水号
 * 格式是：yyyyMMddHHmmssSSS(17)+aicseq(6位数字)+|（分隔符）+系统类型+|+进程id
 * @author H.N
 * @date 2017-2-25 下午4:48:08
 * @version 1.0
 *
 */
public class TradeIdWorker {
	
	private static Logger logger = LoggerFactory.getLogger(TradeIdWorker.class);
	private static TradeIdWorker instance=new TradeIdWorker();
	
	private AtomicLong al=new AtomicLong();
	
	private int seqlen=6;
	
	String instanceType="";
	
	String progressId="";
	public static TradeIdWorker getInstance() {
        return instance;
    }
	public TradeIdWorker(){
		
		instanceType=System.getenv("instanceType");
		
		progressId=System.getProperty("process.id");
		
	}
	public  String  getLocTraderId() {
		
		String aicseq=new String(MsgUtil.leftPad(al.getAndIncrement()+"", seqlen));
		
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+aicseq+"|"+instanceType+"|"+progressId;
		
	}
}
