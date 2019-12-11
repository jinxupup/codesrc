package com.jjb.ecms.biz.service.apply.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.facility.enums.bus.IdType;
import com.jjb.ecms.biz.dao.apply.TmMirCardDao;
import com.jjb.ecms.biz.dao.node.ApplyCardCustrInfoDtoDao;
import com.jjb.ecms.biz.dao.param.TmProductDao;
import com.jjb.ecms.biz.service.apply.TmMirCardService;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.facility.dto.ApplyCardCustrInfoDto;
import com.jjb.ecms.infrastructure.TmAppCustInfo;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmMirCard;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.ecms.util.IdentificationCodeUtil;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

@Service("tmMirCardService")
public class TmMirCardServiceImpl implements TmMirCardService {
	
	@Autowired
	private TmMirCardDao tmMirCardDao;
	
	@Autowired
	private TmProductDao tmProductDao;

	@Autowired
	private ApplyCardCustrInfoDtoDao applyCardCustrInfoDtoDao;
	
	@Override
	@Transactional
	public Page<TmMirCard> getTmMirCardPage(Page<TmMirCard> page) {
		// TODO Auto-generated method stub
		Page<TmMirCard> p = tmMirCardDao.getPage(page);
		
		return p;
	}

	@Override
	@Transactional
	public TmMirCard getTmMirCardByCardNo(String cardNo) {
		// TODO Auto-generated method stub
		TmMirCard tmMirCard = tmMirCardDao.getByCardNo(cardNo);
		
		return tmMirCard;
	}

	/**
	 * 返回的是E:表示卡号/身份证号不正确
	 * 		 O:表示卡号是公务卡
	 * 		 S:表示卡号是附卡
	 * 		 V:表示卡号无效
	 * 		 B:表示卡产品无效
	 * 		 true:表示卡号正确成功
	 * 		 IdNo:表示身份证号正确且对应的主卡卡号存在
	 * @return 
	 */
	@Override
	@Transactional
	public String validateByPrimCardNo(String cardNo) {
		// TODO Auto-generated method stub
		TmMirCard tmMirCard = tmMirCardDao.getByCardNo(cardNo);
		String bscSuppInd = ""; //主附卡指示
		String blockCode = "";//主卡有效表示
		String productCd = "";
		String productStatus = ""; //卡产品状态是否有效
		if(tmMirCard != null){
			bscSuppInd = tmMirCard.getBscSuppInd();
			blockCode = tmMirCard.getBlockCode();
			productCd = tmMirCard.getProductCd();
						
			//判断是不是主卡卡号，B是主卡，S是附卡
			if(StringUtils.isNotEmpty(bscSuppInd)){
				if(bscSuppInd.equals(AppConstant.bscSuppInd_S)){
					return "S";
				}else if(bscSuppInd.equals(AppConstant.bscSuppInd_B)){
					//判断主卡是否有效,等于9|C|P|R|T|F|K都无效
					if(StringUtils.isNotEmpty(blockCode)){
						if(blockCode.equals("9") || blockCode.equals("C")
							|| blockCode.equals("P") || blockCode.equals("R") || blockCode.equals("T")
							|| blockCode.equals("F") || blockCode.equals("K")){
							
							return "V";
						}
					}else{
						if(StringUtils.isNotEmpty(productCd)){
							TmProduct tmProduct = new TmProduct();
							tmProduct.setProductCd(productCd);
							
							List<TmProduct> tmProductList = tmProductDao.getProductByPram(tmProduct);
							if(CollectionUtils.sizeGtZero(tmProductList)){
								tmProduct = tmProductList.get(0); //根据卡产品代码获得卡产品对象
								String subCardType = tmProduct.getSubCardType(); //获取卡的类型
								//判断是不是公务卡，O是公务卡，N是普通卡
								if(StringUtils.isNotEmpty(subCardType)){
									if(subCardType.equals("O")){
										return "O";
									}
									if(subCardType.equals("N")){
										productStatus = tmProduct.getProductStatus();//A表示卡产品有效，B表示卡产品无效
										if(StringUtils.isNotEmpty(productStatus) && productStatus.equals("A")){
											return "true";
										}else{
											return "B";
										}
									}
								}	
							}
						}
					}
				}
			}
		}else{//不是卡号就按身份证号查
			Boolean idFlag = IdentificationCodeUtil.isIdentityCode(cardNo);
			if(idFlag){
				ApplyCardCustrInfoDto applyCardCustrInfoDto = new ApplyCardCustrInfoDto();
				applyCardCustrInfoDto.setIdType(IdType.I.name());
				applyCardCustrInfoDto.setIdNo(cardNo);
				List<ApplyCardCustrInfoDto> list = applyCardCustrInfoDtoDao.getApplyCardCustrInfoDtoList(applyCardCustrInfoDto);
				if(CollectionUtils.sizeGtZero(list)){
					for (ApplyCardCustrInfoDto cardCustrInfoDto : list) {
						bscSuppInd = cardCustrInfoDto.getBscSuppInd();
						blockCode = cardCustrInfoDto.getBlockCode();
						productCd = cardCustrInfoDto.getProductCd();
						cardNo = cardCustrInfoDto.getCardNo();
						if(StringUtils.isNotEmpty(bscSuppInd) && bscSuppInd.equals(AppConstant.bscSuppInd_B) 
							&& StringUtils.isEmpty(blockCode) && StringUtils.isNotEmpty(productCd) && StringUtils.isNotEmpty(cardNo)){
							
							TmProduct tmProduct = new TmProduct();
							tmProduct.setProductCd(productCd);
							List<TmProduct> tmProductList = tmProductDao.getProductByPram(tmProduct);
							if(CollectionUtils.sizeGtZero(tmProductList)){
								tmProduct = tmProductList.get(0); //根据卡产品代码获得卡产品对象
								String subCardType = tmProduct.getSubCardType(); //获取卡的类型
								//判断是不是公务卡，O是公务卡，N是普通卡
								if(StringUtils.isNotEmpty(subCardType) && subCardType.equals("N")){
									productStatus = tmProduct.getProductStatus();//A表示卡产品有效，B表示卡产品无效
									if(StringUtils.isNotEmpty(productStatus) && productStatus.equals("A")){
										return "IdNo";
									}
								}
							}
						}
					}
				}
			}	
		}
		//都不符合条件，直接返回卡号不存在
		return "E";
	}
	
	/**
	 * 根据申请将信息查找已制卡信息
	 * @param appMain
	 * @param tmAppCustInfo
	 * @return
	 */
	@Override
	@Transactional
	public List<TmMirCard> getTmMirCardByCustInfo(TmAppMain appMain,
			TmAppCustInfo tmAppCustInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appNo", appMain.getAppNo());
		map.put("idNo", tmAppCustInfo.getIdNo());
		map.put("bscSuppInd", tmAppCustInfo.getBscSuppInd());
		List<TmMirCard> tmMirCardList1 = tmMirCardDao.getTmMirCardList(map);
		return tmMirCardList1;
	}

	@Override
	@Transactional
	public void saveTmMirCard(TmMirCard tmMirCard){
			tmMirCardDao.saveTmMirCard(tmMirCard);
	}
	@Override
	public void updateTmMirCard(TmMirCard tmMirCard) throws ProcessException {
		tmMirCardDao.updateTmMirCard(tmMirCard);
	}
}
