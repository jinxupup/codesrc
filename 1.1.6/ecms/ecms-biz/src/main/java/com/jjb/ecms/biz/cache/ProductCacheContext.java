package com.jjb.ecms.biz.cache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jjb.acl.access.service.AccessDictService;
import com.jjb.acl.infrastructure.TmAclBranch;
import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.ecms.biz.dao.param.TmProductBranchDao;
import com.jjb.ecms.biz.dao.param.TmProductCardFaceDao;
import com.jjb.ecms.biz.dao.param.TmProductDao;
import com.jjb.ecms.biz.dao.param.TmProductProcessDao;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.ecms.infrastructure.TmProductBranch;
import com.jjb.ecms.infrastructure.TmProductCardFace;
import com.jjb.ecms.infrastructure.TmProductProcess;
import com.jjb.unicorn.cache.RedisUtil;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.SerializeUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 
 * @Description: 缓存
 * @author JYData-R&D-HN
 * @date 2016年9月25日 下午3:41:42
 * @version V1.0
 */
@Component
public class ProductCacheContext implements Serializable {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private static final long serialVersionUID = 1L;
	@Autowired
	private TmProductDao productDao;
	@Autowired
	private TmProductCardFaceDao cardFaceDao;
	@Autowired
	private TmProductBranchDao productBranchDao;
	@Autowired
	private AccessDictService accessDictService;
	@Autowired
	private BranchCacheContext branchCacheContext;
	@Autowired
	private TmProductProcessDao productProcessDao;
	@Autowired
	private RedisUtil redisUtil;

	/**
	 * 从数据库查询所有卡产品信息
	 * 
	 * @return
	 */
	public List<TmProduct> getProductListForDB() {
		TmProduct product = new TmProduct();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("_SORT_NAME", "productCd");
		map.put("_SORT_ORDER", "asc");
		map.put("productStatus", "A");// A:有效 ,B:无效，C:删除
		return productDao.queryForList(product, map);
	}

	/**
	 * 初始化所有卡产品Map
	 * 
	 * @return
	 */
	public LinkedHashMap<Object, Object> initProductMap() {

		long delrs1 = redisUtil.del(AppConstant.ALL_PRODUCT);
		logger.info("初始化【"+AppConstant.ALL_PRODUCT+"】，删除Redis操作的结果："+delrs1);
		long delrs2 = redisUtil.del(AppConstant.ALL_PRODUCT_MAP);
		logger.info("初始化【"+AppConstant.ALL_PRODUCT_MAP+"】，删除Redis操作的结果："+delrs2);

		List<TmProduct> list = getProductListForDB();
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		LinkedHashMap<Object, Object> productMap = new LinkedHashMap<>();
		if (CollectionUtils.sizeGtZero(list)) {
			for (TmProduct tmProduct : list) {
				if (StringUtils.isNotBlank(tmProduct.getProductCd())) {
					productMap.put(tmProduct.getProductCd(), tmProduct);
					redisUtil.hsetnx(AppConstant.ALL_PRODUCT, tmProduct.getProductCd(), SerializeUtils.serialize(tmProduct));
				}
			}
			if (productMap != null && productMap.size() > 0) {
				redisUtil.setByte(AppConstant.ALL_PRODUCT_MAP, SerializeUtils.serialize(productMap));
				logger.info("初始化【" + AppConstant.ALL_PRODUCT_MAP + "】，新增数量：" + productMap.size());
			}
		}
		return productMap;
	}

	public LinkedHashMap<Object, Object> getAllProduct() {
		LinkedHashMap<Object, Object> map = null;
		byte[] bytes = redisUtil.getByByte(AppConstant.ALL_PRODUCT_MAP);
		Object obj = SerializeUtils.unSerialize(bytes);
		if (obj != null && obj instanceof LinkedHashMap) {
			map = (LinkedHashMap<Object, Object>) obj;
		} else {
			map = initProductMap();
		}
		return map;
	}

	/**
	 * 根据卡产品状态获取卡产品map列表
	 * 
	 * @param productStatus
	 * @param productType   产品类型
	 * @return
	 */
	public LinkedHashMap<Object, Object> getSimpleProductLinkedMap(String productStatus, String productType) {

		LinkedHashMap<Object, Object> productMap = getAllProduct();

		LinkedHashMap<Object, Object> map = new LinkedHashMap<Object, Object>();
		if (productMap != null && productMap.size() > 0) {
			for (Object obj1 : productMap.values()) {
				TmProduct p1 = (TmProduct) obj1;
//				if (StringUtils.isNotEmpty(productType) && !StringUtils.equals(p1.getProductType(), productType)) {
//					continue;
//				}
				if (p1.getProductStatus() != null && productStatus.contains(p1.getProductStatus())) {
					map.put(p1.getProductCd(), p1.getProductDesc());
				} else if (StringUtils.isEmpty(productStatus)) {
					map.put(p1.getProductCd(), p1.getProductDesc());
				}
			}
		}

		return map;
	}

	/**
	 * 获取机构有权限查看的产品列表</br>
	 */
	public LinkedHashMap<Object, Object> getProductByBranchMap() {
		LinkedHashMap<Object, Object> productMap = getAllProduct();
//		LinkedHashMap<String, TmAclBranch> branchMap = branchCacheContext.getSubBranchByBranchOrUser(null,
//				OrganizationContextHolder.getUserNo());
		LinkedHashMap<Object, Object> retMap = new LinkedHashMap<Object, Object>();
		if (productMap != null && productMap.size() > 0) {
			for (Object obj1 : productMap.values()) {
				TmProduct p1 = (TmProduct) obj1;
//				// 如果产品是队列发卡的产品。
//				// 如果产品不是独立发卡产品，所有用户与网点均可使用</br>
//				//如果产品是独立发卡产品，那么需要与当前登录用户管理的网点做匹配，如果匹配上则可使用
//				if (p1 != null && StringUtils.isNotEmpty(p1.getProductCd())
//						&& StringUtils.isNotEmpty(p1.getIfEnableHairpin())
//						&& p1.getIfEnableHairpin().equals(Indicator.Y.name())) {
//					LinkedHashMap<Object, Object> productBranchmap = getProductBranchByProduct(p1.getProductCd());
//					if (productBranchmap != null && productBranchmap.size() > 0) {
//						for (Object obj : productBranchmap.values()) {
//							if (obj != null) {
//								TmAclBranch tab = (TmAclBranch) obj;
//								// 如果其是独立发卡发卡产品，并且当前登录用户也具有该网点权限
//								if (tab.getIfEnableHairpin() != null
//										&& tab.getIfEnableHairpin().equals(Indicator.Y.name())
//										&& branchMap.containsKey(tab.getBranchCode())) {
//									retMap.put(p1.getProductCd(), p1.getProductDesc());
//								}
//							}
//						}
//					}
//				} else {
//					retMap.put(p1.getProductCd(), p1.getProductDesc());
//				}
				
				retMap.put(p1.getProductCd(), p1.getProductDesc());
			}
		}
		return retMap;
	}

	/**
	 * 根据产品编号获取产品信息
	 * 
	 * @param productCd
	 * @return
	 */
	public TmProduct getProduct(String productCd) {
		if(StringUtils.isEmpty(productCd)) {
			return null;
		}
		byte[] bytes = redisUtil.hget(AppConstant.ALL_PRODUCT, productCd.getBytes());
		Object obj = SerializeUtils.unSerialize(bytes);
		if (obj != null && obj instanceof TmProduct) {
			return (TmProduct) obj;
		} else {
			LinkedHashMap<Object, Object> productMap = getAllProduct();
			if (productMap != null && productMap.containsKey(productCd)) {
				return (TmProduct) productMap.get(productCd);
			}
		}
		return null;
	}

	/**
	 * 从数据库查询产品对应卡面信息 非特殊情况不要调用
	 * 
	 * @return
	 */
	public List<TmProductCardFace> getProductCardFaceListForDB() {
		TmProductCardFace product = new TmProductCardFace();
		return cardFaceDao.queryForList(product);
	}

	/**
	 * 初始化产品与卡面关系数据Map
	 * 
	 * @return
	 */
	public LinkedHashMap<Object, LinkedHashMap<Object, Object>> initProductCardFaceMap() {
		String key = AppConstant.ALL_PRODUCT_CARDFACE;
//		long delNum = redisUtil.hlen(key);
		Long delNum = redisUtil.del(key);
		logger.info("初始化【" + AppConstant.ALL_PRODUCT_CARDFACE + "】，删除Redis操作的结果：" + delNum);

		List<TmProductCardFace> list = getProductCardFaceListForDB();
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		LinkedHashMap<Object, LinkedHashMap<Object, Object>> productCardFaceMap = new LinkedHashMap<>();
		LinkedHashMap<Object, Object> cardFaceMap = new LinkedHashMap<Object, Object>();
		if (CollectionUtils.sizeGtZero(list)) {
			for (int i = 0; i < list.size(); i++) {
				TmProductCardFace pcf = list.get(i);
				TmAclDict aclDict = accessDictService.get("CardFace", pcf.getCardFaceCd());
				if (aclDict == null) {
					continue;
				}
				if (productCardFaceMap.containsKey(pcf.getProductCd())) {
					cardFaceMap = productCardFaceMap.get(pcf.getProductCd());
					cardFaceMap.put(pcf.getCardFaceCd(), aclDict);
					productCardFaceMap.put(pcf.getProductCd(), cardFaceMap);
					redisUtil.hsetnx(key, pcf.getProductCd(), SerializeUtils.serialize(cardFaceMap));
				} else {
					cardFaceMap = new LinkedHashMap<Object, Object>();
					cardFaceMap.put(pcf.getCardFaceCd(), aclDict);
					productCardFaceMap.put(pcf.getProductCd(), cardFaceMap);
					redisUtil.hsetnx(key, pcf.getProductCd(), SerializeUtils.serialize(cardFaceMap));
				}
			}
		}
//		if(productCardFaceMap!=null && productCardFaceMap.size()>0) {
//			redisUtil.set(key, SerializeUtils.serialize(productCardFaceMap));
//		}
		return productCardFaceMap;
	}

	/**
	 * 根据卡产品编号获取配置的卡面信息Map集合
	 * 
	 * @param productCd
	 * @return
	 */
	public LinkedHashMap<Object, Object> getSimpleProductCardFaceLinkedMap(String productCd) {
		if (StringUtils.isEmpty(productCd)) {
			return null;
		}
		String key = AppConstant.ALL_PRODUCT_CARDFACE;
		byte[] bytes = redisUtil.hget(key, productCd.getBytes());
		Object obj = SerializeUtils.unSerialize(bytes);
		if (obj != null && obj instanceof LinkedHashMap) {
			return (LinkedHashMap<Object, Object>) obj;
		} else {
			LinkedHashMap<Object, LinkedHashMap<Object, Object>> productCardFaceMap = initProductCardFaceMap();
			if (productCardFaceMap != null && productCardFaceMap.size() > 0) {
				return productCardFaceMap.get(productCd);
			}
		}
		return null;
	}

	/**
	 * 从数据库查询产品对应机构信息 非特殊情况不要调用
	 * 
	 * @return
	 */
	public List<TmProductBranch> getProductBranchListForDB() {
		TmProductBranch productBranch = new TmProductBranch();
		return productBranchDao.queryForList(productBranch);
	}

	public LinkedHashMap<Object, LinkedHashMap<Object, Object>> initProductBranchMap() {
		String key = AppConstant.ALL_PRODUCT_BRANCH;
		long delNum = redisUtil.del(key);
		logger.info("初始化【" + AppConstant.ALL_PRODUCT_BRANCH + "】，删除Redis操作的结果：" + delNum);

		List<TmProductBranch> list = getProductBranchListForDB();
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		LinkedHashMap<Object, LinkedHashMap<Object, Object>> productBranchMap = new LinkedHashMap<Object, LinkedHashMap<Object, Object>>();
		LinkedHashMap<Object, Object> branchMap = new LinkedHashMap<Object, Object>();
		if (CollectionUtils.sizeGtZero(list)) {
			for (int i = 0; i < list.size(); i++) {
				TmProductBranch pb = list.get(i);
				if (productBranchMap.containsKey(pb.getProductCd())) {
					branchMap = productBranchMap.get(pb.getProductCd());
					TmAclBranch tmAclBranch = branchCacheContext.getTmAclBranchByCode(pb.getBranchCode());
					branchMap.put(pb.getBranchCode(), tmAclBranch);
					productBranchMap.put(pb.getProductCd(), branchMap);
					redisUtil.hsetnx(key, pb.getProductCd(), SerializeUtils.serialize(branchMap));
				} else {
					branchMap = new LinkedHashMap<Object, Object>();
					TmAclBranch tmAclBranch = branchCacheContext.getTmAclBranchByCode(pb.getBranchCode());
					branchMap.put(pb.getBranchCode(), tmAclBranch);
					productBranchMap.put(pb.getProductCd(), branchMap);
					redisUtil.hsetnx(key, pb.getProductCd(), SerializeUtils.serialize(branchMap));
				}
			}
		}
		return productBranchMap;
	}

	public LinkedHashMap<Object, Object> getProductBranchByProduct(String productCd) {

		if (StringUtils.isEmpty(productCd)) {
			return null;
		}
		String key = AppConstant.ALL_PRODUCT_BRANCH;
		byte[] bytes = redisUtil.hget(key, productCd.getBytes());
		Object obj = SerializeUtils.unSerialize(bytes);
		if (obj != null && obj instanceof LinkedHashMap) {
			return (LinkedHashMap<Object, Object>) obj;
		} else {
			LinkedHashMap<Object, LinkedHashMap<Object, Object>> prodBranchMap = initProductCardFaceMap();
			if (prodBranchMap != null && prodBranchMap.size() > 0) {
				return prodBranchMap.get(productCd);
			}
		}
		return null;
	}

	/**
	 * 从数据库查询产品对应机构信息 非特殊情况不要调用
	 * 
	 * @return
	 */
	public List<TmProductProcess> getProductProcessListForDB() {
		TmProductProcess productProcess = new TmProductProcess();
		return productProcessDao.queryForList(productProcess);
	}

	public Map<String, TmProductProcess> initProductProcessMap() {
		String key = AppConstant.ALL_PRODUCT_PROCESS;
		long delNum = redisUtil.del(key);
		logger.info("初始化【" + AppConstant.ALL_PRODUCT_PROCESS + "】，删除Redis操作的结果：" + delNum);

		List<TmProductProcess> list = getProductProcessListForDB();
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		Map<String, TmProductProcess> productProcessMap = new HashMap<>();
		if (CollectionUtils.sizeGtZero(list)) {
			for (int i = 0; i < list.size(); i++) {
				TmProductProcess pb = list.get(i);
				if (StringUtils.isEmpty(pb.getProcdefKey())) {
					continue;
				}
				if (StringUtils.equals(pb.getIsDefault(), "Y")) {
					String field = pb.getProductCd() + "-def";
					productProcessMap.put(field, pb);
					redisUtil.hsetnx(key, field, SerializeUtils.serialize(pb));
				}
				if (StringUtils.isNotEmpty(pb.getAppSource()) && StringUtils.isNotEmpty(pb.getProductCd())) {
					String field = pb.getProductCd() + "-" + pb.getAppSource();
					productProcessMap.put(field, pb);
					redisUtil.hsetnx(key, field, SerializeUtils.serialize(pb));
				}
			}
		}
		return productProcessMap;
	}

	public TmProductProcess getProductProcessByProduct(String key) {
		if(StringUtils.isEmpty(key)) {
			return null;
		}
		String keys = AppConstant.ALL_PRODUCT_PROCESS;
		byte[] bytes = redisUtil.hget(keys, key.getBytes());
		Object obj = SerializeUtils.unSerialize(bytes);
		if (obj != null && obj instanceof TmProductProcess) {
			return (TmProductProcess) obj;
		} else {
			Map<String,TmProductProcess> map = initProductProcessMap();
			if (map != null && map.size() > 0) {
				return map.get(key);
			}
		}
		return null;
	}

	/**
	 * 刷新网点机构缓存
	 */
	public void refresh() {
		initProductMap();
		initProductCardFaceMap();
		initProductBranchMap();
		initProductProcessMap();
	}
}
