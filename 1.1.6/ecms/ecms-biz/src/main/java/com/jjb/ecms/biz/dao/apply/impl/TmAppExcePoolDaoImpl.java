package com.jjb.ecms.biz.dao.apply.impl;

import com.jjb.ecms.biz.dao.apply.TmAppExcePoolDao;
import com.jjb.ecms.infrastructure.TmAppExcePool;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("tmAppExcePoolDao")
public class TmAppExcePoolDaoImpl extends AbstractBaseDao<TmAppExcePool> implements TmAppExcePoolDao {

//	private Logger logger = LoggerFactory.getLogger(getClass());
	public static final String  selectAll = "com.jjb.ecms.infrastructure.mapping.TmAppExcePoolMapper.selectAll";
	/**
	 * 分页查询
	 */
	@Override
	public Page<TmAppExcePool> getTmAppExcePoolPage(Page<TmAppExcePool> page) {
		return queryForPageList(selectAll, page.getQuery(),page);
	}
	/**
	 * 根据条件查询异常件清单
	 */
	@Override
	public List<TmAppExcePool> getTmAppExcePoolList(TmAppExcePool tmAppExcePool) {
		if(tmAppExcePool!=null){
			return queryForList(tmAppExcePool);
		}
		return null;
	}

	@Override
	public void saveTmAppExcePool(TmAppExcePool tmAppExcePool) {
		if(tmAppExcePool!=null) {
			if(tmAppExcePool.getExceTime()==null) {
				tmAppExcePool.setExceTime(new Date());
			}
			if(StringUtils.isEmpty(tmAppExcePool.getUpdateUser())) {
				tmAppExcePool.setUpdateUser(OrganizationContextHolder.getUserNo());
			}
			save(tmAppExcePool);
		}
	}

	@Override
	public void updateTmAppExcePool(TmAppExcePool tmAppExcePool) {
		if(tmAppExcePool!=null) {
			if(tmAppExcePool.getUpdateTime()==null) {
				tmAppExcePool.setUpdateTime(new Date());
			}
			if(StringUtils.isEmpty(tmAppExcePool.getUpdateUser())) {
				tmAppExcePool.setUpdateUser(OrganizationContextHolder.getUserNo());
			}
			super.update(tmAppExcePool);
		}
		
	}
	/**
	 * 根据表主键删除
	 */
	@Override
	public void deleteTmAppExcePool(TmAppExcePool tmAppExcePool) {
		if(tmAppExcePool!=null && tmAppExcePool.getId()!=null) {
			super.deleteByKey(tmAppExcePool);
		}
	}
	
}