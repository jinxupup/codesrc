package com.jjb.ecms.biz.dao.apply;

import java.util.List;

import com.jjb.ecms.infrastructure.TmAppCustInfo;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * @Description: 附卡申请人信息表
 * @author JYData-R&D-Big T.T
 * @date 2016年8月31日 下午6:43:58
 * @version V1.0
 */
public interface TmAppCustInfoDao extends BaseDao<TmAppCustInfo> {

	/**
	 * 根据申请件编号appNo和status获取对应附卡申请人信息
	 * @param appNo status
	 * @return
	 */
	public List<TmAppCustInfo> getTmAppCustInfoList(String appNo,String status,String bscSuppInd);

	/**
	 * 保存附卡申请人信息
	 * @param appNo
	 * @return
	 */
	public void saveTmAppCustInfo(TmAppCustInfo tmAppCustInfo) ;

	/**
	 * 更新附卡申请人信息
	 * @param appNo
	 * @return
	 */
	public void updateTmAppCustInfo(TmAppCustInfo tmAppCustInfo) ;

	/**
	 * 删除附卡预录入申请人信息
	 * @param appNo
	 * @return
	 */
	public void deleteTmAppCustInfo(TmAppCustInfo tmAppCustInfo) ;

	/**
	 * 根据appNo和attachNo获取附卡信息s
	 */
	public TmAppCustInfo getTmAppCustInfo(String appNo, Integer attachNo ,String bscSuppInd);
}