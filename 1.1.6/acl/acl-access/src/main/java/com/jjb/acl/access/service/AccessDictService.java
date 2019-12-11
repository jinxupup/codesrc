package com.jjb.acl.access.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.unicorn.base.service.BaseService;
import com.jjb.unicorn.cache.RedisUtil;
import com.jjb.unicorn.facility.kits.StrKit;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.SerializeUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 
 * @author BIG.D.W.K
 * 
 */
@Service
public class AccessDictService extends BaseService<TmAclDict> {

	private Logger logger = LoggerFactory.getLogger(getClass());
	// 缓存
//	private static Map<String, List<TmAclDict>> dictGetByTypeCache = new ConcurrentHashMap<String, List<TmAclDict>>();
	private static final String selectALL = "com.jjb.acl.infrastructure.mapping.TmAclDictMapper.selectAll";
	private static final String ACL_DICT = "AclDict";
	private static final String ACL_DICT_PARENT = "AclDictParent";
	@Autowired
	private RedisUtil redisUtil;

	// @Cacheable(value="dictGetCache",key="#type.concat(#code)")
	public TmAclDict get(String type, String code) {
		if (StringUtils.isNotEmpty(code)) {
			List<TmAclDict> list = getByType(type);
			if (StrKit.notBlankList(list)) {
				for (TmAclDict tmAclDict : list) {
					if (tmAclDict.getCode().equals(code)) {
						return tmAclDict;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 业务字典类型缓存
	 * 
	 * @param type
	 * @return
	 */
	// @Cacheable(value="dictGetByTypeCache",key="#type")
	public List<TmAclDict> getByType(String type) {
		String key = ACL_DICT;
		if (StringUtils.isEmpty(type)) {
			return null;
		}
		byte[] bytes = redisUtil.hget(key, type.getBytes());
		Object obj = SerializeUtils.unSerialize(bytes);
		List<TmAclDict> list = null;
		if (obj != null && obj instanceof List) {
			try {
				list = (List<TmAclDict>) obj;
			} catch (Exception e) {
				logger.error("强转redis缓存数据成List<TmAclDict>对象失败");
			}
		} else {
			TmAclDict tmAclDict = new TmAclDict();
			tmAclDict.setType(type);
			list = queryAclDictForList(tmAclDict);
//			list = super.queryForList(tmAclDict);
			if (CollectionUtils.sizeGtZero(list)) {
//				redisUtil.setByte(key, SerializeUtils.serialize(list));
				redisUtil.hsetnx(key, type, SerializeUtils.serialize(list));
				logger.info("初始化KEY-【" + key + "】,Field-【" + type + "】，新增数量：" + list.size());
			}
		}
		return list;
	}

	public LinkedHashMap<Object, Object> getMapByType(String type) {
		if (StringUtils.isEmpty(type)) {
			return null;
		}
		List<TmAclDict> list = getByType(type);
		LinkedHashMap<Object, Object> retMap = new LinkedHashMap<>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TmAclDict dict = list.get(i);
				retMap.put(dict.getCode(), dict.getCodeName());
			}
		}
		return retMap;
	}
	
	/**
	 * 根据参数类型 与 vaule( value的常见用法为‘上级代码’) 查询参数字典列表
	 * 
	 * @param type
	 * @param value
	 * @return
	 */
	public List<TmAclDict> getByTypeAndValue(String type, String value) {

		String key = ACL_DICT_PARENT;
		if (StringUtils.isEmpty(type)) {
			return null;
		}
		String field = type+value;
		byte[] bytes = redisUtil.hget(key, field.getBytes());
		Object obj = SerializeUtils.unSerialize(bytes);
		List<TmAclDict> list = null;
		if (obj != null && obj instanceof List) {
			try {
				list = (List<TmAclDict>) obj;
			} catch (Exception e) {
				logger.error("强转redis缓存数据成List<TmAclDict>对象失败");
			}
		} else {
			TmAclDict tmAclDict = new TmAclDict();
			tmAclDict.setType(type);
			tmAclDict.setValue(value);
			list = queryAclDictForList(tmAclDict);
			if (CollectionUtils.sizeGtZero(list)) {
				redisUtil.hsetnx(key, field, SerializeUtils.serialize(list));
				logger.info("初始化KEY-【" + key + "】,Field-【" + field + "】，新增数量：" + list.size());
			}
		}
		
		
		return list;
	}

//	public int getSize() {
//		return dictGetByTypeCache == null ? 0 : dictGetByTypeCache.size();
//	}

	public void initAclDictParm() {
		logger.info("初始化[TmAclDict]参数数据,置空业务字典中所有字典数据==========");
		long delRs = redisUtil.del(ACL_DICT);
		logger.info("初始化【ACL_DICT】，删除Redis操作的结果：" + delRs);
		
		long delRs2 = redisUtil.del(ACL_DICT_PARENT);
		logger.info("初始化【ACL_DICT_PARENT】，删除Redis操作的结果：" + delRs2);
		
//		dictGetByTypeCache = new LinkedHashMap<String, List<TmAclDict>>();
	}
	
	/**
	 * 根据参数查询业务字典并排序</br>
	 * 默认拍下是TmAclDict.sort ，第二顺序位,code
	 * @param tmAclDict
	 * @return
	 */
	public List<TmAclDict> queryAclDictForList(TmAclDict tmAclDict){
		Map<String,Object> map = new HashMap<String,Object>();
		if(tmAclDict.getType()!=null && !tmAclDict.getType().equals("")) {
			map.put("type", tmAclDict.getType());
		}
		if(tmAclDict.getValue()!=null && !tmAclDict.getValue().equals("")) {
			map.put("value", tmAclDict.getValue());
		}
		map.put("_SORT_NAME", "sort,code");
		map.put("_SORT_ORDER", "asc");
		return super.queryForList(selectALL,map);
	}
	
}
