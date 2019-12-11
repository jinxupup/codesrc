package com.jjb.ecms.biz.cache;

import java.io.Serializable;

import org.springframework.stereotype.Component;


@Component
public class InstalParamCacheContext implements Serializable{
//
//	private Logger logger = LoggerFactory.getLogger(getClass());
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	
//	@Autowired
//	private TmAppInstalMerchantDao appInstalMerchantDao;
//	@Autowired
//	private TmAppInstalProgramDao appInstalProgramDao;
//	@Autowired
//	private TmAppInstalProgramTermsDao appInstalProgramTermsDao;
//	@Autowired
//	private TmAppInstalProgramMerchantDao appInstalProgramMerchantDao;
//	
//	private LinkedHashMap<Object, Object> mccMap = new LinkedHashMap<Object, Object>();//存放商户映射
//	private LinkedHashMap<Object, Object> activiMap = new LinkedHashMap<Object, Object>();//存放商户映射
//	
//	private List<TmAppInstalProgramTerms> termsLists = new ArrayList<TmAppInstalProgramTerms>();//存放所有的分期信息
//	private List<TmAppInstalProgramMerchant> mccLists = new ArrayList<TmAppInstalProgramMerchant>();//存放所有的商户列
//	private LinkedHashMap<String, LinkedHashMap<Object, Object>> instalProgramCache = new LinkedHashMap<String, LinkedHashMap<Object, Object>>();
//	private Map<String, List<TmAppInstalProgram>> productToInstalProgramMap = new HashMap<String, List<TmAppInstalProgram>>();
//	private Map<String, List<TmAppInstalProgramTerms>> programTotermsMap = new HashMap<String, List<TmAppInstalProgramTerms>>();
//	private Map<String, List<TmAppInstalProgramMerchant>> programToMersMap = new HashMap<String, List<TmAppInstalProgramMerchant>>();
//	
//	/**
//	 * 得到所有的商户信息
//	 * @param filters
//	 * @return
//	 */
//	public LinkedHashMap<Object, Object> getMccName(){
//		if(mccMap==null || mccMap.size()==0){
//			return initMccMap();
//		}else{
//			return mccMap;
//		}
//	}
//	/**
//	 * 根据参数查询出相应的商户信息
//	 * @param filters
//	 * @return
//	 */
//	public LinkedHashMap<Object, Object> initMccMap(String filters){
//		LinkedHashMap<Object, Object> map = new LinkedHashMap<Object, Object>();
//		if(StringUtils.isNotBlank(filters)){
//			if(mccLists==null || mccLists.size()==0){
//				mccLists = appInstalProgramMerchantDao.queryForList(new TmAppInstalProgramMerchant());
//			}
//			if(CollectionUtils.sizeGtZero(mccLists)){
//				for(TmAppInstalProgramMerchant appInstalProgramMerchant:mccLists){
//					if(appInstalProgramMerchant.getProgramId().equals(filters)){
//						map.put(appInstalProgramMerchant.getMerId(), appInstalProgramMerchant.getMerName());
//					}
//				}
//			}
//		}
//		return map;
//	}
//	
//	public LinkedHashMap<Object, Object> getMersByProgId(String filters){
//		LinkedHashMap<Object, Object> map = new LinkedHashMap<Object, Object>();
//		if(StringUtils.isNotBlank(filters)){
//			if(mccLists==null || mccLists.size()==0){
//				mccLists = initAllMerList();
//			}
//			if(CollectionUtils.sizeGtZero(mccLists)){
//				for(TmAppInstalProgramMerchant appInstalProgramMerchant:mccLists){
//					if(appInstalProgramMerchant.getProgramId().equals(filters)){
//						map.put(appInstalProgramMerchant.getMerId(), appInstalProgramMerchant.getMerName());
//					}
//				}
//			}
//		}
//		return map;
//	}
//	private List<TmAppInstalProgramMerchant> initAllMerList() {
//		mccLists = appInstalProgramMerchantDao.queryForList(new TmAppInstalProgramMerchant());
//		if(mccLists == null)
//			mccLists = new ArrayList<TmAppInstalProgramMerchant>();
//		return mccLists;
//	}
//	/**
//	 * 初始化所有商户信息
//	 * @return
//	 */
//	public LinkedHashMap<Object, Object> initMccMap(){
//		mccMap = new LinkedHashMap<Object, Object>();
//		List<TmAppInstalMerchant> mccLists = appInstalMerchantDao.queryAllMer();
//		if(CollectionUtils.sizeGtZero(mccLists)){
//			for(TmAppInstalMerchant appInstalMerchant:mccLists){
//				mccMap.put(appInstalMerchant.getMerId(), appInstalMerchant.getMerName());
//			}
//		}
//		return mccMap;
//	}
//	/**
//	 * 得到分期活动号
//	 * @param filters
//	 * @return
//	 */
//	public LinkedHashMap<Object, Object> getActivityName(String filters){
//		if(StringUtils.isBlank(filters)){
//			if(activiMap==null || activiMap.size()==0){
//				return initActiviMap();
//			}else{
//				return activiMap;
//			}
//		}else{
//			return getActiviMap(filters);
//		}
//	}
//	/**
//	 * 初始化分期活动参数
//	 * @return
//	 */
//	public LinkedHashMap<Object, Object> initActiviMap(){
//		activiMap = new LinkedHashMap<Object, Object>();
//		List<TmAppInstalProgram> actLists = appInstalProgramDao.getActiLists();
//		if(CollectionUtils.sizeGtZero(actLists)){
//			for(TmAppInstalProgram appInstalProgram:actLists){
//				activiMap.put(appInstalProgram.getProgramId(),appInstalProgram.getProgramDesc());
//			}
//		}
//		return activiMap;
//	}
//	/**
//	 * 初始化分期活动参数
//	 * @param filters
//	 * @return
//	 */
//	public LinkedHashMap<Object, Object> getActiviMap(String filters){
//		LinkedHashMap<Object, Object> map = new LinkedHashMap<Object, Object>();
//		if(instalProgramCache==null || instalProgramCache.size()==0){
////			instalProgramCache = appInstalProgramDao.getInstalProgramCache();
//			instalProgramCache = initInstalProgs();
//		}
//		if(instalProgramCache != null && !instalProgramCache.isEmpty()){
////			List<String> list = instalProgramCache.get(filters+"-"+OrganizationContextHolder.getBranchCode());
//			map = instalProgramCache.get(filters);
//			if(map == null || map.isEmpty())
//				map = new LinkedHashMap<Object, Object>();
//		}
//		return map;
//	}
//	/**
//	 * 
//	 * @Description 初始化分期活动参数，key=产品编号，value=产品对应的分期活动map
//	 * @return
//	 */
//	private LinkedHashMap<String, LinkedHashMap<Object, Object>> initInstalProgs() {
//		instalProgramCache = new LinkedHashMap<String, LinkedHashMap<Object,Object>>();
//		LinkedHashMap<String, Set<String>> prodProgs = appInstalProgramDao.getInstalProgramCacheWithNoBranch();
//		for(Map.Entry<String, Set<String>> prodProg:prodProgs.entrySet()){
//			Set<String> list = prodProg.getValue();
//			LinkedHashMap<Object, Object> value = new LinkedHashMap<Object, Object>();
//			if(list!=null && list.size()>0){
//				for(String da:list){
//					String[] _da = da.split("-");
//					if(_da.length==2){
//						value.put(_da[0], _da[1]);
//					}
//				}
//			}
//			instalProgramCache.put(prodProg.getKey(), value);
//		}
//		
//		return instalProgramCache;
//	}
//	/**
//	 * 得到分期期数根据活动号
//	 * @param filters
//	 * @return
//	 */
//	public LinkedHashMap<Object, Object> getTermsByTerm(String filters){
//		
//		if(termsLists==null || termsLists.size()==0){
//			termsLists = initAllProgTerms();
//			
//		}
//		LinkedHashMap<Object, Object> map = new LinkedHashMap<Object, Object>();
//		if(StringUtils.isNotBlank(filters)){
//			if(CollectionUtils.sizeGtZero(termsLists)){
//				for(TmAppInstalProgramTerms appInstalProgramTerms:termsLists){
//					if(appInstalProgramTerms.getProgramId().equals(filters)){
////						map.put(appInstalProgramTerms.getTerm().toString(), appInstalProgramTerms.getProgramId());
////						map.put(appInstalProgramTerms.getId().toString(), appInstalProgramTerms.getTerm());
//						map.put(appInstalProgramTerms.getTerm().toString(), appInstalProgramTerms.getTerm());
//					}
//				}
//			}
//		}/*else{
//			if(CollectionUtils.sizeGtZero(termsLists)){
//				for(TmAppInstalProgramTerms appInstalProgramTerms:termsLists){
//					map.put(appInstalProgramTerms.getTerm().toString(), appInstalProgramTerms.getProgramId());
//				}
//			}
//		}*/
//		return map;
//	}
//	
//	private List<TmAppInstalProgramTerms> initAllProgTerms() {
//		termsLists = appInstalProgramTermsDao.queryForList(new TmAppInstalProgramTerms());
//		if(termsLists == null)
//			termsLists = new ArrayList<TmAppInstalProgramTerms>();
//		return termsLists;
//	}
//
//	public void refresh() {
//		
//		logger.info("====>开始[initActiviMap]参数刷新,原参数大小["+(activiMap==null ? 0:activiMap.size())+"]");
//		initActiviMap();
//		logger.info("====>结束[initActiviMap]参数刷新,新参数大小["+(activiMap==null ? 0:activiMap.size())+"]");
//		logger.info("====>开始[initInstalProgs]参数刷新,原参数大小["+(instalProgramCache==null ? 0:instalProgramCache.size())+"]");
//		initInstalProgs();
//		logger.info("====>结束[initInstalProgs]参数刷新,新参数大小["+(instalProgramCache==null ? 0:instalProgramCache.size())+"]");
//		logger.info("====>开始[initAllProgTerms]参数刷新,原参数大小["+(termsLists==null ? 0:termsLists.size())+"]");
//		initAllProgTerms();
//		logger.info("====>结束[initAllProgTerms]参数刷新,新参数大小["+(termsLists==null ? 0:termsLists.size())+"]");
//		logger.info("====>开始[initAllMerList]参数刷新,原参数大小["+(mccLists==null ? 0:mccLists.size())+"]");
//		initAllMerList();
//		logger.info("====>结束[initAllMerList]参数刷新,新参数大小["+(mccLists==null ? 0:mccLists.size())+"]");
//		logger.info("====>开始[initMccMap]参数刷新,原参数大小["+(mccMap==null ? 0:mccMap.size())+"]");
//		initMccMap();
//		logger.info("====>结束[initMccMap]参数刷新,新参数大小["+(mccMap==null ? 0:mccMap.size())+"]");
//		logger.info("====>开始[initProductToInstalProgramMap]参数刷新,原参数大小["+(productToInstalProgramMap==null ? 0:productToInstalProgramMap.size())+"]");
//		initProductToInstalProgramMap();
//		logger.info("====>结束[initProductToInstalProgramMap]参数刷新,新参数大小["+(productToInstalProgramMap==null ? 0:productToInstalProgramMap.size())+"]");
//		logger.info("====>开始[initProgramToTermsMap]参数刷新,原参数大小["+(programTotermsMap==null ? 0:programTotermsMap.size())+"]");
//		initProgramToTermsMap();
//		logger.info("====>结束[initProgramToTermsMap]参数刷新,新参数大小["+(programTotermsMap==null ? 0:programTotermsMap.size())+"]");
//		logger.info("====>开始[initProgramToMersMap]参数刷新,原参数大小["+(programToMersMap==null ? 0:programToMersMap.size())+"]");
//		initProgramToMersMap();
//		logger.info("====>结束[initProgramToMersMap]参数刷新,新参数大小["+(programToMersMap==null ? 0:programToMersMap.size())+"]");
//		
//	}
//	public List<TmAppInstalProgram> getInstalProgramByProductCd(String productCd) {
//		if(productToInstalProgramMap == null || productToInstalProgramMap.size() <= 0){
//			productToInstalProgramMap = initProductToInstalProgramMap();
//		}
//		return productToInstalProgramMap.get(productCd);
//	}
//	private Map<String, List<TmAppInstalProgram>> initProductToInstalProgramMap() {
//		productToInstalProgramMap = new HashMap<String, List<TmAppInstalProgram>>();
//		List<TmAppInstalProgram> allPrograms = appInstalProgramDao.queryForList(new TmAppInstalProgram());
//		for(TmAppInstalProgram program : allPrograms){
//			String productCd = program.getProductCd();
//			List<TmAppInstalProgram> programList = null;
//			if(productToInstalProgramMap.get(productCd) == null){
//				programList = new ArrayList<TmAppInstalProgram>();
//				productToInstalProgramMap.put(productCd, programList);
//			}else {
//				programList = productToInstalProgramMap.get(productCd);
//			}
//			programList.add(program);
//		}
//		return productToInstalProgramMap;
//	}
//	public List<TmAppInstalProgramTerms> getTmAppInstalProgramTermByProgramId(
//			String programId) {
//		if(programTotermsMap == null || programTotermsMap.size() <= 0){
//			programTotermsMap = initProgramToTermsMap();
//		}
//		return programTotermsMap.get(programId);
//	}
//	private Map<String, List<TmAppInstalProgramTerms>> initProgramToTermsMap() {
//		programTotermsMap = new HashMap<String, List<TmAppInstalProgramTerms>>();
//		List<TmAppInstalProgramTerms> termsLists = appInstalProgramTermsDao.queryForList(new TmAppInstalProgramTerms());
//		for(TmAppInstalProgramTerms terms : termsLists){
//			String progsId = terms.getProgramId();
//			List<TmAppInstalProgramTerms> appInstalProgramTerms = null;
//			if(programTotermsMap.get(progsId) == null){
//				appInstalProgramTerms = new ArrayList<TmAppInstalProgramTerms>();
//				programTotermsMap.put(progsId, appInstalProgramTerms);
//			}else {
//				appInstalProgramTerms = programTotermsMap.get(progsId);
//			}
//			appInstalProgramTerms.add(terms);
//		}
//		return programTotermsMap;
//	}
//	public List<TmAppInstalProgramMerchant> getTmAppInstalProgramMerchantByProgramId(
//			String programId) {
//		if(programToMersMap == null || programToMersMap.size() <= 0){
//			programToMersMap = initProgramToMersMap();
//		}
//		return programToMersMap.get(programId);
//	}
//	private Map<String, List<TmAppInstalProgramMerchant>> initProgramToMersMap() {
//		programToMersMap = new HashMap<String, List<TmAppInstalProgramMerchant>>();
//		List<TmAppInstalProgramMerchant> merLists = appInstalProgramMerchantDao.queryForList(new TmAppInstalProgramMerchant());
//		for(TmAppInstalProgramMerchant mer : merLists){
//			String progsId = mer.getProgramId();
//			List<TmAppInstalProgramMerchant> appInstalProgramMerchants = null;
//			if(programToMersMap.get(progsId) == null){
//				appInstalProgramMerchants = new ArrayList<TmAppInstalProgramMerchant>();
//				programToMersMap.put(progsId, appInstalProgramMerchants);
//			}else {
//				appInstalProgramMerchants = programToMersMap.get(progsId);
//			}
//			appInstalProgramMerchants.add(mer);
//		}
//		return programToMersMap;
//	}
//	public TmAppInstalMerchant getTmAppInstalMerchantByMerId(String mccNo) {
//		
//		return appInstalMerchantDao.queryForByMercId(mccNo);
//	}	
}
