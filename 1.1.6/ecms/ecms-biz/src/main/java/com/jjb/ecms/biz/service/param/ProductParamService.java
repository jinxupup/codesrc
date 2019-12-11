package com.jjb.ecms.biz.service.param;

import java.util.List;

import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.ecms.infrastructure.TmProductBranch;
import com.jjb.ecms.infrastructure.TmProductCardFace;
import com.jjb.ecms.infrastructure.TmProductProcess;
import com.jjb.unicorn.facility.model.Page;


/**
 * @description 产品参数
 * @author hn
 * @date 2016年8月29日14:38:00 
 */
public interface ProductParamService {

	/**
	 * 获取产品参数
	 * @param page
	 * @return
	 */
	public Page<TmProduct> getProductPage(Page<TmProduct> page,TmProduct product);
	/**
	 * 获取产品参数
	 * @param page
	 * @return
	 */
	public Page<TmProductProcess> getProductProcessPage(Page<TmProductProcess> page,TmProductProcess product);
	/**
	 * 保存产品参数
	 * @param product
	 */
	public void saveTmProduct(TmProduct product);
	/**
	 * 保存产品渠道流程参数
	 * @param product
	 */
	public void saveTmProductProc(TmProductProcess tmProductDitProc);
	/**
	 * 保存产品参数
	 * @param product
	 */
	public void saveTmProductList(List<TmProduct> product);
	/**
	 * 根据参数获取产品列表
	 * @param TmProduct
	 * @return List
	 */
	public List<TmProduct> getTmProductByParam(TmProduct pro);
	/**
	 * 根据参数获取产品渠道流程列表
	 * @param TmProductDitProc
	 * @return List
	 */
	public List<TmProductProcess> getTmProductDitProcByParam(TmProductProcess pro);
	/**
	 * 根据ID获取产品参数
	 * @param TmProduct
	 * @return TmProduct
	 */
	public TmProduct getTmProductById(Integer id);
	/**
	 * 更新产品参数
	 * @param product
	 */
	public void updateTmProduct(TmProduct product);
	/**
	 * 更新产品渠道流程参数
	 * @param tmProductDitProc
	 */
	public void updateTmProductProc(TmProductProcess tmProductDitProc);
	/**
	 * 删除产品参数（物理删除）
	 * @param id
	 */
	public void deleteTmProduct(int id);
	/**
	 * 删除产品渠道流程参数
	 * @param tmProductDitProc
	 */
	public void deleteTmProductProcess(TmProductProcess tmProductDitProc);

	/**
	 * 设置卡产品对于卡面的数据
	 * @param cardFaceList
	 */
	public void setTmProductCardFace(String productCd ,List<String> cardFaceList);
	
	/**
	 * 根据产品代码获取卡面信息
	 * @param productCd
	 * @return List
	 */
	public List<TmProductCardFace> getTmCardFaceByProductCd(String productCd);
	

	/**
	 * 设置卡产品对于机构的数据
	 * @param productCd, branchList
	 */
	public void setTmProductBranch(String productCd ,List<String> branchList);
	
	/**
	 * 根据产品代码获取机构信息
	 * @param productCd
	 * @return List
	 */
	public List<TmProductBranch> getTmProductBranchByProductCd(String productCd);
	
	/**
	 * 根据卡产品获取对象的卡产品
	 */
	public TmProduct getTmProductByCd(String productCd);
}
