package com.jjb.ecms.biz.service.apply;

import java.util.List;

import com.jjb.ecms.infrastructure.TmAppCustInfo;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmMirCard;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Page;


public interface TmMirCardService{
	
	public Page<TmMirCard> getTmMirCardPage(Page<TmMirCard> page);
		
	public TmMirCard getTmMirCardByCardNo(String cardNo);
	
	public String validateByPrimCardNo(String cardNo);

	/**
	 * 根据申请将信息查找已制卡信息
	 * @param appMain
	 * @param tmAppCustInfo
	 * @return
	 */
	public List<TmMirCard> getTmMirCardByCustInfo(TmAppMain appMain, TmAppCustInfo tmAppCustInfo);


	public void saveTmMirCard(TmMirCard tmMirCard) throws ProcessException;
	public void updateTmMirCard(TmMirCard tmMirCard) throws ProcessException;
}