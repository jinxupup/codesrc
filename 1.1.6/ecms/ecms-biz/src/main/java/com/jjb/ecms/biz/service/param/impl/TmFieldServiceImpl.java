package com.jjb.ecms.biz.service.param.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.dao.param.TmFieldDao;
import com.jjb.ecms.biz.service.param.TmFieldService;
import com.jjb.ecms.infrastructure.TmField;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

@Service("tmFieldService")
public class TmFieldServiceImpl implements TmFieldService {

	@Autowired
	private TmFieldDao tmFieldDao;

	/**
	 * 获取字段列表
	 * @param page
	 * @return
	 */
	@Override
	@Transactional
	public Page<TmField> getTmFieldPage(Page<TmField> page) {
		page = tmFieldDao.getTmFiledPage(page);
		List<TmField> tmFieldList = page.getRows();
		if (CollectionUtils.sizeGtZero(tmFieldList)) {// 把字段与字段名放在同一个字段里
			for (TmField tmField : tmFieldList) {
				tmField.setFieldEn(tmField.getFieldEn() + "-"	+ tmField.getFieldName());
			}
			page.setRows(tmFieldList);
		}
		
		return page;
	}

	/**
	 * 保存字段
	 * @param fieldManage
	 */
	@Override
	@Transactional
	public void saveTmField(TmField tmField) {
		if (tmField != null) {
//			tmField.setJpaVersion(0);
			tmField.setCreateDate(new Date());
			tmField.setCreateUser(OrganizationContextHolder.getUserNo());
			tmField.setOrg(OrganizationContextHolder.getOrg());
			tmFieldDao.save(tmField);
		}
	}
	
	/**
	 * 根据条件获取TmAppFieldManage信息list
	 * @param fieldManage
	 * @return
	 */
	@Override
	@Transactional
	public List<TmField> getTmFieldList(TmField tmField) {
		List<TmField> fields = null;
		if(tmField != null){
			tmField.setOrg(OrganizationContextHolder.getOrg());
			fields = tmFieldDao.queryForList(tmField);
		}
		
		return fields;
	}

	/**
	 * 根据主键id获取TmAppFieldManage信息
	 * @param id
	 * @return
	 */
	public TmField getTmFieldById(Integer id) {
		TmField tmField = null;
		if(id != null){
			tmField = new TmField();
			tmField.setOrg(OrganizationContextHolder.getOrg());
			tmField.setFieldId(id);
			tmField = tmFieldDao.queryByKey(tmField);
		}
		
		return tmField;
	}
	
	/**
	 * 根据id更新字段
	 * @param id
	 * @param fieldManage
	 * @throws Exception
	 */
	@Override
	@Transactional
	public void updateTmField(TmField tmField) throws Exception {
		if(tmField != null && tmField.getFieldId() != null){
			TmField field = tmFieldDao.queryByKey(tmField);
			field.setBetweenMax(tmField.getBetweenMax());
			field.setBetweenMin(tmField.getBetweenMin());
			field.setFieldChange(tmField.getFieldChange());
			field.setComponentType(tmField.getComponentType());
			field.setDefValue(tmField.getDefValue());
			field.setDictType(tmField.getDictType());
			field.setFieldEn(tmField.getFieldEn());
			field.setFieldAr(tmField.getFieldAr());
			field.setFieldName(tmField.getFieldName());
			field.setIfCancel(tmField.getIfCancel());
			field.setIfReadonly(tmField.getIfReadonly());
			field.setIfUsed(tmField.getIfUsed());
			field.setInputAr(tmField.getInputAr());
			field.setLabelAr(tmField.getLabelAr());
			field.setMaxLength(tmField.getMaxLength());
			field.setFieldNullable(tmField.getFieldNullable());
			field.setFieldRegexp(tmField.getFieldRegexp());
			field.setRemark(tmField.getRemark());//区分第一、二联系人或省市区信息等
			field.setShowCode(tmField.getShowCode());
			field.setTabDesc(tmField.getTabDesc());
			field.setTabName(tmField.getTabName());
			field.setTextName(tmField.getTextName());
			field.setUpdateDate(new Date());
			field.setUpdateUser(OrganizationContextHolder.getUserNo());
			tmFieldDao.update(field);
		}else {
			throw new ProcessException("没找到需要更新的字段信息id");
		}
	}

	/* 
	 *更改启用状态
	 */
	@Override
	@Transactional
	public void updateByIfUsed(Integer id, String ifUsed) throws Exception {
		// TODO Auto-generated method stub
		if(id != null){
			TmField tmField = new TmField();
			tmField.setFieldId(id);
			try {
				tmField = tmFieldDao.queryByKey(tmField);
				if(tmField != null && StringUtils.isNotBlank(ifUsed)){
					tmField.setIfUsed(ifUsed);
					tmFieldDao.update(tmField);
				}
			} catch (Exception e) {
				// TODO: handle exception
				throw new ProcessException("启用/禁用操作失败",e);
			}
		}else {
			throw new ProcessException("没找到启用/禁用操作的id");
		}
	}

	/* 
	 * 删除操作
	 */
	@Override
	@Transactional
	public void deleteTmField(Integer id) {
		// TODO Auto-generated method stub
		if(id != null){
			TmField tmField = new TmField();
			tmField.setFieldId(id);
			try {
				tmFieldDao.deleteByKey(tmField);
			} catch (Exception e) {
				// TODO: handle exception
				throw new ProcessException("删除["+id+"]操作失败",e);
			}
		}else {
			throw new ProcessException("没找到删除操作的id");
		}
	}
}
