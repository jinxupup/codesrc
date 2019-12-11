/**
 * 
 */
package com.jjb.ecms.biz.dao.param;

import java.util.List;

import com.jjb.ecms.facility.dto.FieldProductDto;
import com.jjb.ecms.infrastructure.TmFieldProduct;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * @Description: 页面字段配置
 * @author JYData-R&D-Big T.T
 * @date 2017年9月14日 上午11:48:21
 * @version V1.0  
 */

public interface FieldProductDtoDao extends BaseDao<FieldProductDto>{
	/**
	 * 根据字段id或卡产品获取字段配置信息
	 * @param fieldProductDto
	 * @return
	 */
	public List<FieldProductDto> getFieldProductDtoList(FieldProductDto fieldProductDto);
	/**
	 * 查询定制化的字段与产品信息
	 * @param tmFieldProduct
	 * @return
	 */
	public List<FieldProductDto> getFieldProductDtoList2(TmFieldProduct tmFieldProduct);
	/**
	 * 获取所有已配置字段的卡产品信息
	 * @return
	 */
	public List<FieldProductDto> getProductList(FieldProductDto fieldProductDto);
	
	/**
	 * 根据卡产品代码删除FieldProduct
	 * @param fieldProduct
	 * @return
	 */
	public void deleteFieldProduct(FieldProductDto fieldProduct);
}
