/**
 * 
 */
package com.jjb.ecms.biz.dao.param.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.param.FieldProductDtoDao;
import com.jjb.ecms.facility.dto.FieldProductDto;
import com.jjb.ecms.infrastructure.TmFieldProduct;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: TODO
 * @author JYData-R&D-Big T.T
 * @date 2017年9月14日 上午11:50:49
 * @version V1.0  
 */

@Repository("fieldProductDtoDao")
public class FieldProductDtoDaoImpl extends AbstractBaseDao<FieldProductDto> implements FieldProductDtoDao {
	public static final String  fieldProductCustomOne = "com.jjb.ecms.biz.FieldProductDtoMapper.selectByTmFieldAndFieldProduct";
	/* 
	 * 获取卡产品关联的页面字段
	 */
	@Override
	public List<FieldProductDto> getFieldProductDtoList(FieldProductDto fieldProductDto) {
		// TODO Auto-generated method stub
		if(fieldProductDto == null){
			fieldProductDto = new FieldProductDto();
		}
		return queryForList("com.jjb.ecms.biz.FieldProductDtoMapper.selectAll", fieldProductDto);
	}
	/**
	 * 获取产品管理的页面字段
	 */
	@Override
	public List<FieldProductDto> getFieldProductDtoList2(TmFieldProduct tmFieldProduct) {
		// TODO Auto-generated method stub
		if(tmFieldProduct != null){
			
			Map<String, Object> params = new HashMap<String, Object>();
			Map<String, Serializable> dataMap = tmFieldProduct.convertToMap();
			params.putAll(dataMap);
			params.put("_SORT_NAME", "fieldRegion,fieldSort,reviewSort,obText1");
			params.put("_SORT_ORDER", "asc");
			return queryForList(fieldProductCustomOne, params);
		}
		
		return null;
	}
	
	/**
	 * 获取所有已配置字段的卡产品信息
	 * @return
	 */
	@Override
	public List<FieldProductDto> getProductList(FieldProductDto fieldProductDto) {
		// TODO Auto-generated method stub
		if(fieldProductDto == null){
			fieldProductDto = new FieldProductDto();
		}
		
		return queryForList("com.jjb.ecms.biz.FieldProductDtoMapper.selectProducts", fieldProductDto);
	}
	
	/**
	 * 删除FieldProduct
	 * @param productCd
	 */
	public void deleteFieldProduct(FieldProductDto fieldProductDto) {
		if(fieldProductDto != null && (StringUtils.isNotBlank(fieldProductDto.getProductCd()) || fieldProductDto.getFieldId() != null)){
			delete("com.jjb.ecms.biz.FieldProductDtoMapper.deleteFieldProduct", fieldProductDto);
		}
	}
}
