package com.jjb.ecms.biz.service.manage.impl;

import java.util.Date;
import java.util.List;

import com.jjb.acl.infrastructure.TmAclBranch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.dao.manage.ApplySpreaderInfoDao;
import com.jjb.ecms.biz.service.manage.ApplySpreaderInfoService;
import com.jjb.ecms.infrastructure.TmAppSprePerBank;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @description 推广人信息
 * @author J.J
 * @date 2017年7月7日下午2:53:40
 */

@Service("applySpreaderInfoService")
public class ApplySpreaderInfoServiceImpl implements ApplySpreaderInfoService{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ApplySpreaderInfoDao applySpreaderInfoDao;
//	@Autowired
//	private CacheContext cacheContext;
	/**
	 * 获取推广人信息</br>
	 * 不支持模糊搜索
	 * @param page
	 * @param
	 * @return
	 */
	@Override
	@Transactional
	public Page<TmAppSprePerBank> getPage(Page<TmAppSprePerBank> page, TmAppSprePerBank spreader) {
		return applySpreaderInfoDao.getPage(page, spreader);
	}
	/**
	 * 获取推广人信息</br>
	 * 支持模糊搜索
	 * @param page
	 * @return
	 */
	@Override
	@Transactional
	public Page<TmAppSprePerBank> getPageForTranferUser(Page<TmAppSprePerBank> page) {
		return applySpreaderInfoDao.getPageForTranferUser(page);
	}

	/**
	 * 
	 * 通过参数获取推广人信息
	 */
	@Override
	@Transactional
	public List<TmAppSprePerBank> getSpreaderByParam(TmAppSprePerBank spreader) throws ProcessException{
		if (spreader == null) {
			throw new ProcessException("推广人信息为空！");
		}
		List<TmAppSprePerBank> list = applySpreaderInfoDao.queryForList(spreader);
		return list;
	}

	/**
	 * 保存推广人信息
	 */
	@Override
	@Transactional
	public void saveSpreader(TmAppSprePerBank spreader) throws ProcessException{
		if(spreader == null){
			throw new ProcessException("推广人信息为空！");
		} else {
			TmAppSprePerBank spreader1 = new TmAppSprePerBank();
			spreader1.setSpreaderNum(spreader.getSpreaderNum());
			List<TmAppSprePerBank> spreaderList = getSpreaderByParam(spreader1);
			if (spreaderList != null && spreaderList.size() > 0) {
				logger.info("推广人["+spreader.getSpreaderNum()+"]已存在，请重新设置");
				throw new ProcessException("推广人["+spreader.getSpreaderNum()+"]已存在，请重新设置");
			}  else {
				logger.info("保存推广人"+spreader.getSpreaderNum() + " " + spreader.getSpreaderName());
				if(StringUtils.isEmpty(spreader.getSpreaderStatus())) {
					spreader.setSpreaderStatus("1");//新增时默认设置有效
				}
				spreader.setOrg(OrganizationContextHolder.getOrg());
				spreader.setCreateDate(new Date());
				spreader.setCreateUser(OrganizationContextHolder.getUserNo());
				applySpreaderInfoDao.save(spreader);
//				cacheContext.initSpreaderInfoMap();
			}
		}
	}

	/**
	 * 通过ID获取推广人信息
	 */
	@Override
	@Transactional
	public TmAppSprePerBank getSpreaderById(int id) {
		TmAppSprePerBank spreader = new TmAppSprePerBank();
		spreader.setId(id);
		return applySpreaderInfoDao.queryByKey(spreader);
	}
	
	/**
	 * 更新推广人信息
	 */
	@Override
	@Transactional
	public void updateSpreader(TmAppSprePerBank spreader) throws ProcessException{
		if (spreader != null && spreader.getId() != null) {
			TmAppSprePerBank oldSpreader = getSpreaderById(spreader.getId());
			spreader.setSpreaderStatus(StringUtils.setValue(spreader.getSpreaderStatus(), "1"));//如果修改的话，默认为有效
			spreader.setOrg(oldSpreader.getOrg());
			spreader.setCreateDate(oldSpreader.getCreateDate());
			spreader.setCreateUser(oldSpreader.getCreateUser());
			spreader.setUpdateUser(OrganizationContextHolder.getUserNo());
			spreader.setUpdateDate(new Date());
			applySpreaderInfoDao.update(spreader);
//			cacheContext.initSpreaderInfoMap();
		} else {
			throw new ProcessException("推广人信息为空！修改失败！");
		}
	}

	/**
	 * 删除推广人信息
	 */
	@Override
	@Transactional
	public void deleteSpreaderById(Integer id){
		TmAppSprePerBank spreader = new TmAppSprePerBank();
		spreader.setId(id);
		applySpreaderInfoDao.deleteByKey(spreader);
//		cacheContext.initSpreaderInfoMap();
	}
	

}
