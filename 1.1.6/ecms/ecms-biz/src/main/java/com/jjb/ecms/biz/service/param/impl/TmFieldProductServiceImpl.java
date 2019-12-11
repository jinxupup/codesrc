/**
 * 
 */
package com.jjb.ecms.biz.service.param.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.dao.param.FieldProductDtoDao;
import com.jjb.ecms.biz.dao.param.TmFieldProductDao;
import com.jjb.ecms.biz.service.param.TmFieldProductService;
import com.jjb.ecms.facility.dto.FieldProductDto;
import com.jjb.ecms.infrastructure.TmFieldProduct;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: TODO
 * @author JYData-R&D-Big T.T
 * @date 2017年9月15日 上午10:21:47
 * @version V1.0  
 */
@Service("tmFieldProductService")
public class TmFieldProductServiceImpl implements TmFieldProductService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TmFieldProductDao tmFieldProductDao;

	@Autowired
	private FieldProductDtoDao fieldProductDtoDao;
	
	/**
	 * 根据条件获取tmFieldProduct列表list
	 * @param tmFieldProduct
	 * @return
	 */
	@Override
	@Transactional
	public List<TmFieldProduct> getTmFieldProductList(TmFieldProduct tmFieldProduct) {
		// TODO Auto-generated method stub
		List<TmFieldProduct> tmFieldProductList = null;
		if(tmFieldProduct != null){
			tmFieldProductList = tmFieldProductDao.getTmFieldProductList(tmFieldProduct);
		}
		return tmFieldProductList;
	}


	/**
	 * 根据卡产品代码删除FieldProduct
	 * @param fieldProduct
	 * @return
	 */
	@Override
	@Transactional
	public String deleteFieldProduct(FieldProductDto fieldProductDto) {
		if(fieldProductDto != null &&
			(StringUtils.isNotBlank(fieldProductDto.getProductCd()) || fieldProductDto.getFieldId() != null)){
				fieldProductDtoDao.deleteFieldProduct(fieldProductDto);
				return "";	
		}else {
			return "没有需要删除的条件";
		}
		
	}
	/**
	 * 保存操作
	 */
	@Override
	@Transactional
	public void saveTmFieldProduct(TmFieldProduct tmFieldProduct) {
		// TODO Auto-generated method stub
		if(tmFieldProduct != null){
			tmFieldProduct.setCreateDate(new Date());
			tmFieldProduct.setCreateUser(OrganizationContextHolder.getUserNo());
//			tmFieldProduct.setJpaVersion(0);
			tmFieldProductDao.save(tmFieldProduct);
		}else {
			logger.error("实例tmFieldProduct不存在，保存tmFieldProduct操作失败");
			throw new ProcessException("实例tmFieldProduct不存在，保存tmFieldProduct操作失败");
		}
	}

	/**
	 * 删除操作
	 */
	@Override
	@Transactional
	public void deleteTmFieldProduct(TmFieldProduct tmFieldProduct) {
		// TODO Auto-generated method stub
		if(tmFieldProduct != null
				&& StringUtils.isNotBlank(tmFieldProduct.getProductCd()) && tmFieldProduct.getFieldEn() != null){
			tmFieldProductDao.deleteByKey(tmFieldProduct);
		}else {
			logger.error("删除[productCd={},fieldId={}]操作失败",tmFieldProduct.getProductCd(),tmFieldProduct.getFieldEn());
			throw new ProcessException("实例tmFieldProduct/主键不存在，删除tmFieldProduct操作失败");
		}
	}
	/**
	 * 查询定制化的字段与产品信息
	 * @param tmFieldProduct
	 * @return
	 */
	@Override
	@Transactional
	public List<FieldProductDto> getFieldProductDtoList2(TmFieldProduct tmFieldProduct) {
		return fieldProductDtoDao.getFieldProductDtoList2(tmFieldProduct);
	}
}
