package com.jjb.ecms.biz.service.manage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.dao.apply.TmAppExcePoolDao;
import com.jjb.ecms.biz.dao.apply.TmAppMainDao;
import com.jjb.ecms.biz.dao.manage.AbnormalProcessAppDao;
import com.jjb.ecms.biz.service.manage.AbnormalProcessAppService;
import com.jjb.ecms.facility.dto.ApplyAbnormalProcessDto;
import com.jjb.ecms.infrastructure.TmAppExcePool;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.unicorn.facility.model.Page;

/**
 * @Description: 异常流程申请件管理实现类
 * @author JYData-R&D-L.L
 * @date 2016年9月22日 下午5:08:01
 * @version V1.0
 */
@Service("abnormalProcessAppService")
public class AbnormalProcessAppServiceImpl implements AbnormalProcessAppService{

	@Autowired
	AbnormalProcessAppDao abnormalProcessAppDao;
	@Autowired
	private TmAppMainDao tmAppMainDao;
	@Autowired
	private TmAppExcePoolDao tmAppExcePoolDao;
	/**
	 * 查询异常流程申请件
	 * @return
	 */
	@Override
	@Transactional
	public List<ApplyAbnormalProcessDto> getAbnormalProcessApp(String appNo, String rtfState, String cardNo, String idNo, String idType) {

		return abnormalProcessAppDao.getAbnormalProcessApp(appNo, rtfState,cardNo,idNo,idType);
	}
	/**
	 * 删除异常件
	 * @param appNo
	 */
	@Override
	@Transactional
	public void delete(String appNo) {
		abnormalProcessAppDao.delete(appNo);

	}
	@Override
	@Transactional
	public Page<ApplyAbnormalProcessDto> getAbnormalProcessApp(
			Page<ApplyAbnormalProcessDto> page) {
		// TODO Auto-generated method stub
		return abnormalProcessAppDao.getAbnormalProcessApp(page);
	}
	@Override
	public List<TmAppMain> getTmAppMainMkCarfEx() {
		return tmAppMainDao.getTmAppMainMkCarfEx();
	}

	@Override
	public List<TmAppMain> getTmAppMianMkCardAgain() {
		return tmAppMainDao.getTmAppMianMkCardAgain();
	}

	/**
	 * 分页查询
	 */
	@Override
	public Page<TmAppExcePool> getTmAppExcePoolPage(Page<TmAppExcePool> page) {
		return tmAppExcePoolDao.getTmAppExcePoolPage(page);
	}
	/**
	 * 根据条件查询异常件清单
	 */
	@Override
	public List<TmAppExcePool> getTmAppExcePoolList(TmAppExcePool tmAppExcePool) {
		return tmAppExcePoolDao.getTmAppExcePoolList(tmAppExcePool);
	}

	@Override
	public void saveTmAppExcePool(TmAppExcePool tmAppExcePool) {
		tmAppExcePoolDao.saveTmAppExcePool(tmAppExcePool);
	}

	@Override
	public void updateTmAppExcePool(TmAppExcePool tmAppExcePool) {
		tmAppExcePoolDao.updateTmAppExcePool(tmAppExcePool);
	}
	/**
	 * 根据表主键删除
	 */
	@Override
	public void deleteTmAppExcePool(TmAppExcePool tmAppExcePool) {
		tmAppExcePoolDao.deleteTmAppExcePool(tmAppExcePool);
	}
}
