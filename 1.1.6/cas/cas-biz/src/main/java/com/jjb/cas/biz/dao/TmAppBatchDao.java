package com.jjb.cas.biz.dao;

import java.util.List;

import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.unicorn.base.dao.BaseDao;

/**
 * @Description: TODO
 * @author JYData-R&D-Big Star
 * @date 2016年10月9日 下午2:17:04
 * @version V1.0  
 */
public interface TmAppBatchDao extends BaseDao<TmAppMain> {
	
	
	public List<String> getTmAppMainByRtfState();
	
	public List<String> getR2001AppNoList();
	
	public List<String> getR0001AppNoList();

	/**
	 * @return
	 */
	public List<String> getTmAppAttachFail();

	/**
	 * @return
	 */
	public List<Integer> getTmAppRfe();


}
