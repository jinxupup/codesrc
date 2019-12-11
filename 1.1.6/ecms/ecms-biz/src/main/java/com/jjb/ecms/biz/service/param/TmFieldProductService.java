/**
 * 
 */
package com.jjb.ecms.biz.service.param;

import java.util.List;

import com.jjb.ecms.facility.dto.FieldProductDto;
import com.jjb.ecms.infrastructure.TmFieldProduct;

/**
 * @Description: TODO
 * @author JYData-R&D-Big T.T
 * @date 2017年9月15日 上午9:46:46
 * @version V1.0  
 */

public interface TmFieldProductService {
	
	/**
	 * 根据条件获取tmFieldProduct列表list
	 * @param tmFieldProduct
	 * @return
	 */
	public List<TmFieldProduct> getTmFieldProductList(TmFieldProduct tmFieldProduct);
	
	/**
	 * 保存操作
	 */
	public void saveTmFieldProduct(TmFieldProduct tmFieldProduct);
	
	/**
	 * 删除操作
	 */
	public void deleteTmFieldProduct(TmFieldProduct tmFieldProduct);
	/**
	 * 删除产品与字段的关联关系
	 * @param fieldProductDto
	 * @return
	 */
	public String deleteFieldProduct(FieldProductDto fieldProductDto);
	/**
	 * 查询定制化的字段与产品信息
	 * @param tmFieldProduct
	 * @return
	 */
	public List<FieldProductDto> getFieldProductDtoList2(TmFieldProduct tmFieldProduct);
}
