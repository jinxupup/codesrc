package com.jjb.ecms.biz.service.manage.impl;

import com.jjb.ecms.biz.dao.manage.TmRiskListDao;
import com.jjb.ecms.biz.service.manage.TmRiskListService;
import com.jjb.ecms.infrastructure.TmRiskList;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 个人黑名单管理
 * @author JYData-R&D-L.L
 * @date 2016年9月1日 上午11:53:59
 * @version V1.0
 */
@Service("tmRiskListService")
public class tmRiskListServiceImpl implements TmRiskListService {

	@Autowired
	private TmRiskListDao tmRiskListDao;

	/**
	 * 个人黑名单 分页查询
	 * @param page
	 * @return 个人黑名单记录
	 */
	@Override
	@Transactional
	public Page<TmRiskList> getTmRiskListPage(
			Page<TmRiskList> page) {

		return tmRiskListDao.getTmRiskListPage(page);
	}

	/**
	 * 通过id查询个人黑名单
	 * @param id
	 * @return 个人黑名单记录
	 */
	@Override
	@Transactional
	public void delete(int id) {

		tmRiskListDao.delete(id);
	}

	/**
	 * 删除个人黑名单
	 * @param id
	 */
	@Override
	@Transactional
	public void saveTmRiskList(TmRiskList tmRiskList) {

		tmRiskListDao.save(tmRiskList);
	}

	/**
	 * 添加个人黑名单
	 * @param
	 */
	@Override
	@Transactional
	public TmRiskList getRiskList(int id) {

		return tmRiskListDao.getRiskList(id);
	}

	/**
	 * 更新个人黑名单
	 * @param tmRiskList
	 */
	@Override
	@Transactional
	public void updateTmRiskList(TmRiskList tmRiskList) {

		tmRiskListDao.update(tmRiskList);
	}

	/**
	 * 查询个人风险名单
	 *
	 * @param tmRiskList
	 */
	@Override
	public List<TmRiskList> getTmRiskList(TmRiskList tmRiskList) {
		return tmRiskListDao.queryForList(tmRiskList);
	}

	@Override
	public List<TmRiskList> getTmRiskListInfo(String name, String cellPhone, String idNo) {
		List<TmRiskList> tmRiskLists = new ArrayList<>();
		if (StringUtils.isNotBlank(idNo) || (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(cellPhone))) {
			TmRiskList tmRiskList = new TmRiskList();
			if(StringUtils.isNotEmpty(cellPhone)) {
				tmRiskList.setCellPhone(cellPhone);
			}
			if(StringUtils.isNotEmpty(name)) {
				tmRiskList.setName(name);
			}
			if(StringUtils.isNotEmpty(idNo)) {
				tmRiskList.setIdNo(idNo);
			}
			tmRiskLists = tmRiskListDao.queryForList(tmRiskList);
			return tmRiskLists;
		}
		return tmRiskLists;
	}

}
