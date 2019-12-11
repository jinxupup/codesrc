package com.jjb.ecms.biz.service.apply;


public interface ApplyCardNoService {
	
	/**
	 * 自选卡号有效性验证
	 * @param cardNo
	 * @param appNo
	 * @param attachNo 附卡编号
	 * @return
	 */
	public String validateByCardNo(String cardNo,String appNo,Integer attchNo,String bscSuppInd);
		
	/**
	 * 解锁卡号时移除已经保存的卡号
	 * @param cardNo
	 * @param appNo 
	 * @param attachNo 附卡编号
	 * @param validBit 自选卡号校验位
	 */
	public void unlockSelectedCardNo(String cardNo, String appNo, Integer attachNo, String bscSuppInd);
}