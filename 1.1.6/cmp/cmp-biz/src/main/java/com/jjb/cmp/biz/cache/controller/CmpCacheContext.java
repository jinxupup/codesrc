package com.jjb.cmp.biz.cache.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jjb.acl.access.service.AccessDictService;
import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 缓存处理
 *
 * @author hp
 *
 */
@Component
public class CmpCacheContext implements Serializable {
	private static final long serialVersionUID = 1L;
	@Autowired
	private AccessDictService accessDictService;

	/**
	 * 通过字典类型查询业务字典列表
	 *
	 * @param type
	 * @return
	 */
	public List<TmAclDict> getAclDictByType(String type) {
		return accessDictService.getByType(type);
	}
	/**
	 * 根据参数类型 与 vaule( value的常见用法为‘上级代码’) 查询参数字典列表
	 *
	 * @param type
	 * @param value
	 * @return getByTypeAndValue
	 */
	public List<TmAclDict> getByTypeAndValue(String type, String value) {
		List<TmAclDict> allList = accessDictService.getByType(type);
		List<TmAclDict> retList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(allList)) {
			for (int i = 0; i < allList.size(); i++) {
				TmAclDict dict = allList.get(i);
				if(dict!=null && StringUtils.equals(dict.getValue(), value)) {
					retList.add(dict);
				}
			}
		}
		return retList;
	}

	/**
	 * 通过业务字典代码查询单条业务字典数据
	 *
	 * @param type
	 * @return
	 */
	public TmAclDict getAclDictByCode(String type, String code) {
		return accessDictService.get(type, code);
	}

	/**
	 * 刷新系统全部缓存
	 *
	 * @see
	 */
	public void refresh() {
		accessDictService.initAclDictParm();
	}
}
