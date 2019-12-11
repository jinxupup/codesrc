package com.jjb.ecms.biz.service.param.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.cache.BranchCacheContext;
import com.jjb.ecms.biz.cache.ProductCacheContext;
import com.jjb.ecms.biz.dao.param.TmProductBranchDao;
import com.jjb.ecms.biz.dao.param.TmProductCardFaceDao;
import com.jjb.ecms.biz.dao.param.TmProductDao;
import com.jjb.ecms.biz.dao.param.TmProductProcessDao;
import com.jjb.ecms.biz.service.param.ProductParamService;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.ecms.infrastructure.TmProductBranch;
import com.jjb.ecms.infrastructure.TmProductCardFace;
import com.jjb.ecms.infrastructure.TmProductProcess;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

@Service("productParamService")
public class ProductParamServiceImpl implements ProductParamService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TmProductDao productDao;
	@Autowired
	private TmProductCardFaceDao productCardFaceDao;
	@Autowired
	private TmProductBranchDao productBranchDao;
	@Autowired
	private ProductCacheContext productCacheContext;
	@Autowired
	private BranchCacheContext branchCacheContext;
	@Autowired
	private TmProductProcessDao productProcessDao;
	/**
	 * 获取产品参数
	 * @param page
	 * @return
	 */
	public Page<TmProduct> getProductPage(Page<TmProduct> page, TmProduct product){
		return productDao.getPage(page,product);
	}
	/**
	 * 获取产品参数
	 * @param page
	 * @return
	 */
	public Page<TmProductProcess> getProductProcessPage(Page<TmProductProcess> page, TmProductProcess product){
		return productProcessDao.getPage(page,product);
	}
	/**
	 * 保存产品参数
	 * @param product
	 */
	public void saveTmProduct(TmProduct product){
		if(product!=null){
			logger.info("保存产品参数"+product.toString());
			product.setCreateTime(new Date());
			product.setCreateUser(OrganizationContextHolder.getUserNo());
			productDao.save(product);
			refreshProductParam();
		}
	}
	/**
	 * 保存产品渠道流程参数
	 * @param tmProductDitProc
	 */
	public void saveTmProductProc(TmProductProcess tmProductDitProc){
		if(tmProductDitProc!=null){
			logger.info("保存产品参数"+tmProductDitProc.toString());
			productProcessDao.save(tmProductDitProc);
			/*refreshProductParam();？？*/
		}
	}
	/**
	 * 保存产品参数
	 * @param product
	 */
	public void saveTmProductList(List<TmProduct> product){
		if(product==null){
			logger.info("保存产品参数新增列表为空");
			return;
		}
		logger.info("保存产品参数,len["+product.size()+"]");
		for (int i = 0; i < product.size(); i++) {
			saveTmProduct(product.get(i));
		}
		refreshProductParam();
	}
	/**
	 * 根据参数获取产品列表
	 * @param TmProduct
	 * @return List
	 */
	public List<TmProduct> getTmProductByParam(TmProduct pro){
		if(pro==null){
			return null;
		}
		List<TmProduct> list = productDao.queryForList(pro);
		return list;
	}
	/**
	 * 根据产品渠道流程参数获取产品列表
	 * @param TmProductDitProc
	 * @return List
	 */
	public List<TmProductProcess> getTmProductDitProcByParam(TmProductProcess pro){
		if(pro==null){
			return null;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("_SORT_NAME", "appSource");
		map.put("_SORT_ORDER", "asc");
		List<TmProductProcess> list = productProcessDao.queryForList(pro,map);
		return list;
	}
	/**
	 * 根据ID获取产品参数
	 * @param TmProduct
	 * @return TmProduct
	 */
	public TmProduct getTmProductById(Integer id){
		TmProduct product = new TmProduct();
		product.setId(id);
		return productDao.queryByKey(product);
	}
	/**
	 * 更新产品参数
	 * @param product
	 */
	public void updateTmProduct(TmProduct product){
		if(product!=null){
			TmProduct oldProduct = getTmProductById(product.getId());
			product.setOrg(oldProduct.getOrg());
			product.setCreateTime(oldProduct.getCreateTime());
			product.setCreateUser(oldProduct.getCreateUser());
			product.setUpdateTime(new Date());
			product.setUpdateUser(OrganizationContextHolder.getUserNo());
			productDao.update(product);
			refreshProductParam();
		}
	}
	/**
	 * 更新产品渠道流程参数
	 * @param product
	 */
	public void updateTmProductProc(TmProductProcess tmProductDitProc){
		if(tmProductDitProc!=null){
			productProcessDao.update(tmProductDitProc);
			/*refreshProductParam();??*/
		}
	}
	/**
	 * 删除产品参数（物理删除）
	 * @param id
	 */
	public void deleteTmProduct(int id){
		refreshProductParam();
	}
	/**
	 * 删除产品渠道流程参数
	 * @param tmProductDitProc
	 */
	public void deleteTmProductProcess(TmProductProcess tmProductDitProc){
		productProcessDao.deleteProductByPram(tmProductDitProc);
	}
	/**
	 * 设置卡产品对于卡面的数据
	 * @param cardFaceList
	 */
	public void setTmProductCardFace(String productCd ,List<String> cardFaceList){
		if(StringUtils.isEmpty(productCd)){
			return;
		}
		TmProductCardFace pcf1 = new TmProductCardFace();
		pcf1.setProductCd(productCd);
		List<TmProductCardFace> dbList = productCardFaceDao.getProductCardFaceByPram(pcf1);
		//删除老数据
		if(CollectionUtils.sizeGtZero(dbList)){
			for (int i = 0; i < dbList.size(); i++) {
				productCardFaceDao.deleteByKey(dbList.get(i));
			}
		}
		if(CollectionUtils.sizeGtZero(cardFaceList)){
			//新增新数据
			for (int i = 0; i < cardFaceList.size(); i++) {
				if(StringUtils.isNotEmpty(cardFaceList.get(i))){
					TmProductCardFace pcf = new TmProductCardFace();
					pcf.setProductCd(productCd);
					pcf.setCardFaceCd(cardFaceList.get(i));
					productCardFaceDao.save(pcf);
				}
			}
		}
		if(CollectionUtils.sizeGtZero(dbList) && CollectionUtils.sizeGtZero(cardFaceList)){
			refreshProductParam();
		}
	}
	
	
	public void setTmProductBranch(String productCd, List<String> branchList) {
		if(StringUtils.isEmpty(productCd)){
			return;
		}
		TmProductBranch tmProductBranch = new TmProductBranch();
		tmProductBranch.setProductCd(productCd);
		List<TmProductBranch> productBranchList = productBranchDao.getProductBranchByParam(tmProductBranch);
		if (CollectionUtils.sizeGtZero(productBranchList)) {
			for (int i = 0; i < productBranchList.size(); i++) {
				productBranchDao.deleteByKey(productBranchList.get(i));
			}
		}
		if(CollectionUtils.sizeGtZero(branchList)){
			for (int i = 0; i < branchList.size(); i++) {
				if (StringUtils.isNotEmpty(branchList.get(i))) {
					TmProductBranch productBranch = new TmProductBranch();
					productBranch.setProductCd(productCd);
					productBranch.setBranchCode(branchList.get(i));
					productBranchDao.save(productBranch);
				}
			}
		}
		if(CollectionUtils.sizeGtZero(productBranchList) || CollectionUtils.sizeGtZero(branchList)){
			refreshProductBranch();
		}
	}
	
	/**
	 * 根据产品代码获取机构信息
	 * @param productCd
	 * @return List
	 */
	public List<TmProductBranch> getTmProductBranchByProductCd(String productCd) {
		if(StringUtils.isEmpty(productCd)){
			return null;
		}
		TmProductBranch pb = new TmProductBranch();
		pb.setProductCd(productCd);
		return productBranchDao.getProductBranchByParam(pb);
	}
	 
	
	/**
	 * 根据产品代码获取卡面信息
	 * @param productCd
	 * @return List
	 */
	public List<TmProductCardFace> getTmCardFaceByProductCd(String productCd){
		if(StringUtils.isEmpty(productCd)){
			return null;
		}
		TmProductCardFace pcf1 = new TmProductCardFace();
		pcf1.setProductCd(productCd);
		return productCardFaceDao.getProductCardFaceByPram(pcf1);
	}
	
	
	/**
	 * 刷新产品参数
	 */
	private void refreshProductParam(){
		productCacheContext.refresh();
	}
	
	private void refreshProductBranch(){
		productCacheContext.refresh();
		branchCacheContext.refresh();
	}

	@Override
	@Transactional
	public TmProduct getTmProductByCd(String productCd) {
		// TODO Auto-generated method stub
		TmProduct entity = new TmProduct();
		entity.setProductCd(productCd);
		return productDao.queryForOne(entity);
	}
	
}
