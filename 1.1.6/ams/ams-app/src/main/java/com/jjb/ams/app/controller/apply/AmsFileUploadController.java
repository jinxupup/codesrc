package com.jjb.ams.app.controller.apply;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * @Description: 申请资料上传
 * @author JYData-R&D-L.L
 * @date 2016年9月12日 下午3:23:09
 * @version V1.0
 */
@Controller
@RequestMapping("/ams_applyFileUpload")
public class AmsFileUploadController extends BaseController {
//	
//	@Autowired
//	private ApplyInputService applyInfoService;
//	@Autowired
//	private ApplyFileUploadService applyFileUploadService;
//	@Autowired
//	private CacheContext cacheContext;
//	@Autowired
//	private TmAppnoSeqDao tmAppnoSeqDao;
//	@Autowired
//	private AlreadyCardDao alreadyCardDao;
//	@Autowired
//	private TmMirCardService tmMirCardService ;
//	@Autowired
//	private CommonService commonService;
//	@Autowired
//	private TmAppInstalMerchantService tmAppInstalMerchantService;
//	
//	//调用工作流启动方法
//	@Autowired
//	private ActivitiService activitiService;
//	
//	private Logger logger = LoggerFactory.getLogger(getClass());
//	
//	private static final String ATTACH_CARD_PREFIX = "fc";
//	List<TmAppUpload> uplist;
//
	@RequestMapping("/fileUpload")
	public String fileUpload(String isEdit) {
		if(StringUtils.isNotBlank(isEdit) && isEdit.equals(Indicator.Y.name())){
			setEdit();
		}
		return "/amsTask/applyCC/applyUpload/applyFileUpload_V1.ftl";
	}
//
//	/**
//	 * 批量上传申请件资料
//	 * @return
//	 * @throws IOException
//	 */
//	@ResponseBody
//	 
//	@RequestMapping("/upload")
//	public Json uploadFile() throws IOException {
//		InputStream fileDatas = null;
//		Json json = Json.newSuccess();
//		try {
//			uplist = new ArrayList<TmAppUpload>();
//			
//			MultipartFile file = getFile("fileName");
//			if(file == null){
//				throw new ProcessException("批量上传的申请件不存在，请重试！");
//			}
//			fileDatas = file.getInputStream();
//			String fileName = file.getOriginalFilename();
//			logger.info("文件上传提交开始："+fileName);
//			String filetype = fileName.substring(fileName.lastIndexOf(".") + 1);
//			Workbook wbs;
//			if(filetype.equalsIgnoreCase("XLS")){
//				wbs = new HSSFWorkbook(fileDatas); // excel2003
//			}else{
//				wbs = new XSSFWorkbook(fileDatas); // excel2007
//			}
//			checkXls(wbs,fileName);//规则校验，同文件检测
//			//获取sheet所有申请者信息
//			List<Map<Integer, HashMap<String, Serializable>>> sheetDataList = dealWithSheet(wbs,fileName);
//			List<ApplyInfoDto> applyInfoDtoList = makeSheetSaveModel(sheetDataList);
//			
//			//发起工作流
//			int dataNum = 0;
//			for(ApplyInfoDto applyInfoDto:applyInfoDtoList){
//				dataNum++;
//				logger.info("文件上传提交开始：" + dataNum);
//				try {
//					applyInfoDto.setBatchInputFalg(Indicator.Y.name());//申请件批量导入的标志
//					applyInfoService.saveApplyInput(applyInfoDto);
//				} catch (Exception e) {
//					// TODO: handle exception
//					logger.error("文件上传保存 数据失败", e.getMessage());
//				}
//				
//				//分期申请，累计商户使用额度
//				TmAppInstalLoan instalmentCredit = applyInfoDto.getTmAppInstalLoan();
//				TmAppMain tmAppMain = applyInfoDto.getTmAppMain();
//				if(Indicator.Y.name().equals(tmAppMain.getIsInstalment()) && instalmentCredit != null && StringUtils.isNotBlank(instalmentCredit.getMccNo())){
//					TmAppInstalMerchant merchant = tmAppInstalMerchantService.getTmAppInstalMerchantByMccNo(instalmentCredit.getMccNo()); 
//					if(merchant != null){
//						logger.info("分期申请[{}]，excel批量导入提交完成，更新商户[{}]审批中累计金额，更新前InAuditLmt：{}",instalmentCredit.getAppNo(),merchant.getMerId(),merchant.getInAuditLmt());
//						merchant.setInAuditLmt((merchant.getInAuditLmt()==null?BigDecimal.ZERO:merchant.getInAuditLmt())
//								.add(instalmentCredit.getCashAmt()));
//						logger.info("TmAppInstalMerchant金额更新后，InAuditLmt：{}",merchant.getInAuditLmt());
//						tmAppInstalMerchantService.updateTmAppInstalMerchant(merchant);
//					}
//				}
//				
//				json = Json.newSuccess();
//				Map<String, Serializable> vars = new HashMap<String, Serializable>();
//				//保存工作流节点数据	
//				ApplyNodeCommonData applyNodeCommonData = (ApplyNodeCommonData)applyInfoDto.getTmAppNodeInfoRecordMap().get(AppConstant.APPLY_NODE_COMMON_DATA);
//				vars.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
//				
//				TmProduct tmProduct = cacheContext.getProduct(applyInfoDto.getTmAppMain().getProductCd());
//				String appNo = applyInfoDto.getAppNo();
//				if(tmProduct != null && StringUtils.isNotBlank(tmProduct.getProcdefKey())){
//					activitiService.startNewProcess(tmProduct.getProcdefKey(), appNo, vars);
//				}else {
//					String processKey = activitiService.getDefProcess();
//					if(StringUtils.isNotEmpty(processKey)){
//						activitiService.startNewProcess(processKey, appNo, vars);
//					}else {
//						logger.info("没有获取到默认的流程定义，请检查流程！"+LogPrintUtils.printAppNoLog(appNo, null,null));
//					}
//				}
//			}
//			//保存文件上传记录
//			for(TmAppUpload tmAppUpload : uplist){
//				applyFileUploadService.saveTmAppUpload(tmAppUpload);
//			}
//			logger.info("文件上传提交结束："+fileName);
//			json.setMsg("文件上传成功!");
//			
//		} catch (FileExistsException e) {
//			throw new ProcessException(e.getMessage());
//		} catch (SecurityException e) {
//			throw new ProcessException(e.getMessage());
//		} catch (IllegalArgumentException e) {
//			throw new ProcessException(e.getMessage());
//		} catch (Exception e){
//			logger.error("文件上传提交异常", e);
//			json.setFail(e.getMessage());
//		} finally {
//			if (fileDatas != null)
//				fileDatas.close();
//		}
//		return json;
//	}
//	
//	/**
//	 * 校验同文件重复
//	 * @param wbs
//	 * @param fileName
//	 */
//	private void checkXls(Workbook wbs,String fileName){
//		
//		if(CollectionUtils.sizeGtZero(applyFileUploadService.getTmAppUploadByName(fileName))) {
//			throw new ProcessException("同文件重复上传,请重试!");
//		}
//		int sheetNum = wbs.getNumberOfSheets();
//		//Sheet个数最大2个
//		if(sheetNum>2){
//			throw new ProcessException("Sheet个数最多为2个");
//		}
//		for(int i=0;i<sheetNum;i++){
//			Sheet childSheet = wbs.getSheetAt(i);
//			//第1个sheet:名称：“A-B” ,第2个sheet:名称：“S”,且每一张表的最大记录是10000条
//			if(i==0 && !childSheet.getSheetName().equals("A-B")){
//				throw new ProcessException("第1个sheet名称必须为A-B");
//			}
//			if(i==1 && !childSheet.getSheetName().equals("S")){
//				throw new ProcessException("第2个sheet名称必须为S");
//			}
//			if(wbs.getSheetAt(i).getLastRowNum()>10001){
//				throw new ProcessException("每个sheet页最大10000条记录，第"+(i+1)+"工作表超过了一万条记录");
//			}
//		}
//	}
//	
//	/**
//	 * 处理上传资料数据
//	 * @param childSheet
//	 * @param fileName
//	 * @param string
//	 * @return
//	 */
//	private List<Map<Integer, HashMap<String, Serializable>>> dealWithSheet(Workbook wbs, String fileName) {
//		int sheetNo = wbs.getNumberOfSheets();
//		if(sheetNo == 0){
//			throw new ProcessException("没读到上传文件信息!");
//		}
//		List<Map<Integer, HashMap<String, Serializable>>> dataList = new ArrayList<Map<Integer,HashMap<String,Serializable>>>();
//		for(int i = 0; i < sheetNo; i++){
//			Sheet childSheet = wbs.getSheetAt(i);
//			Row hssfRow = childSheet.getRow(1);
//			for (int cellNum = 0; cellNum < hssfRow.getLastCellNum(); cellNum++) {//列号从0开始
//				if(i == 0 && !ErrorMsgUtil.EnCellName[cellNum].equals(hssfRow.getCell(cellNum).getStringCellValue())){
//					throw new ProcessException("第"+(i+1)+"页第2行第"+(cellNum+1)+"列不符合模板规范。");
//				}
//				if(i == 1 && !ErrorMsgUtil.EnCellFcName[cellNum].equals(hssfRow.getCell(cellNum).getStringCellValue())){
//					throw new ProcessException("第"+(i+1)+"页第2行第"+(cellNum+1)+"列不符合模板规范。");
//				}
//			}
//			// 存放excel数据Map<行号, HashMap<列名, 值>>
//			Map<Integer, HashMap<String, Serializable>> data = new HashMap<Integer, HashMap<String, Serializable>>();
//			for (int rowNum = 2; rowNum <= childSheet.getLastRowNum(); rowNum++) {//行号从0开始
//				String validaters = "";
//				TmAppUpload upmodel = new TmAppUpload();
//				upmodel.setOrg(OrganizationContextHolder.getOrg());
//				upmodel.setFileName(fileName);
//				upmodel.setBatchDate(new Date());
//				upmodel.setLineNo(rowNum+1);
//				upmodel.setStartBpmn("0");
//				upmodel.setFailReason(null);			
//				if(childSheet.getRow(rowNum)==null){
//					validaters += "不能取到第"+(i+1)+"页第"+(rowNum+1)+"行数据.";
//					upmodel.setStartBpmn("1");
//					upmodel.setFailReason(validaters);
//					uplist.add(upmodel);
//					continue;
//				}
//				HashMap<String, Serializable> cellvalueMap = new HashMap<String, Serializable>();
//				// 循环第二行key值获取值
//				String lnameLogo = null;
//				String nameLogo = null;
//				String fcLnameLogo = null;
//				String fcNameLogo = null;
//				//读取所有的列
//				for (int cellNum = 0; cellNum < hssfRow.getLastCellNum(); cellNum++) {//列
//					String cellname = hssfRow.getCell(cellNum).getStringCellValue();
//					Cell cellUtil = childSheet.getRow(rowNum).getCell(cellNum);
//					String cellValue = cellUtil==null?null:cellUtil.toString().trim();
//					if(cellValue != null && StringUtils.isBlank(cellValue)){//对空值的处理
//						cellValue = null;
//					}
//					//对主附卡的姓名拼音做特殊处理
//					if("lnameLogo".equals(cellname)){
//						lnameLogo = cellValue;
//					}else if("nameLogo".equals(cellname)){
//						nameLogo = cellValue;
//					}else if("fcLnameLogo".equals(cellname)){
//						fcLnameLogo = cellValue;
//					}else if("fcNameLogo".equals(cellname)){
//						fcNameLogo = cellValue;
//					}else{
//						cellvalueMap.put(cellname, cellValue);
//					}
//				}
//				if(cellvalueMap == null || cellvalueMap.isEmpty() || cellvalueMap.size() == 0){
//					break;
//				}
//				if(StringUtils.isNotEmpty(lnameLogo) && StringUtils.isNotEmpty(nameLogo)){
//					cellvalueMap.put("embLogo", lnameLogo + nameLogo);//主卡凸印姓名拼音
//				}else {
//					cellvalueMap.put("embLogo", "");
//				}
//				if(StringUtils.isNotEmpty(fcLnameLogo) && StringUtils.isNotEmpty(fcNameLogo)){
//					cellvalueMap.put("fcEmbLogo", fcLnameLogo + fcNameLogo);//附卡凸印姓名拼音
//				}else {
//					cellvalueMap.put("fcEmbLogo", "");
//				}
//				//必输项验证
//				String appType = DataTypeUtils.getStringValue(cellvalueMap.get("appType"));
//				if(StringUtils.isEmpty(appType)){
//					logger.error("申请类型为空");
//					throw new ProcessException("申请件批量导入申请类型为空,错误出现在第"+(i+1)+"页第"+(rowNum+1)+"行");
//				}else if(AppType.S.name().equals(appType)) {
//					//独立附卡需要带出主卡信息
//					String fcPrimCardNo = DataTypeUtils.getStringValue(cellvalueMap.get("fcPrimCardNo"));
//					if(StringUtils.isNotEmpty(fcPrimCardNo)){//主卡卡号
//						ApplyInfoDto applyInfoDto = new ApplyInfoDto();
//						applyInfoDto.setTmAppMain(new TmAppMain());
//						applyInfoDto = commonService.queryPrimApplicantInfoByPrimCardNo(fcPrimCardNo, applyInfoDto);
//						if (applyInfoDto != null) {
//							TmAppMain tmAppMain = applyInfoDto.getTmAppMain();
//							if (tmAppMain != null) {
//								cellvalueMap.put("productCd", tmAppMain.getProductCd());//独立附卡需要做处理，否则获取不到必输项
//							}
//							if (applyInfoDto.getTmAppCardFaceInfoMap() != null){
//								List<TmAppCardfaceInfo> cardfaces = applyInfoDto.getTmAppCardFaceInfoMap().get(AppConstant.MTAB);
//								if(CollectionUtils.sizeGtZero(cardfaces)){
//									for (TmAppCardfaceInfo tmAppCardfaceInfo : cardfaces) {
//										if(tmAppCardfaceInfo != null && StringUtils.isNotEmpty(tmAppCardfaceInfo.getPyhCd())){
//											cellvalueMap.put("cardFace", tmAppCardfaceInfo.getPyhCd());
//											break;
//										}
//									}						
//								}
//							}
//						}
//					}else {
//						throw new ProcessException("申请件批量导入第"+(i+1)+"页第"+(rowNum+1)+"行[fcPrimCardNo-主卡卡号]必填");
//					}
//				}
//				String productCd = DataTypeUtils.getStringValue(cellvalueMap.get("productCd"));
//				if(StringUtils.isEmpty(productCd)){
//					logger.error("申请卡产品为空");
//					throw new ProcessException("申请件批量导入申请卡产品为空,错误出现在第"+(i+1)+"页第"+(rowNum+1)+"行");
//				}
//
//				List<ValidFieldInfoDto> validFieldInfoDtoList = AppCommonUtil.validateFields(appType, productCd, cacheContext, ATTACH_CARD_PREFIX, logger);
//				if(CollectionUtils.sizeGtZero(validFieldInfoDtoList)){
//					for (ValidFieldInfoDto validFieldInfoDto : validFieldInfoDtoList) {
//						String field = validFieldInfoDto.getField();
//						Serializable value = cellvalueMap.get(field);
//						String fieldValue = value==null?"":value.toString().trim();//去空格
//						if(StringUtils.isEmpty(fieldValue)){
//							if(validFieldInfoDto.getNotEmptyFlag()){//必输项
//								logger.error("申请件批量上传第"+(i+1)+"页第"+(rowNum+1)+"行["+validFieldInfoDto.getFieldName()+"为空");
//								throw new ProcessException("申请件批量上传第"+(i+1)+"页第"+(rowNum+1)+"行["+validFieldInfoDto.getFieldName()+"为空");
//							}
//						}else {
//							if(validFieldInfoDto.getBetweenFlag()){//区间
//								Integer newValue = Integer.valueOf(fieldValue);
//								if(newValue.compareTo(Integer.valueOf(validFieldInfoDto.getBetweenMin())) < 0 || newValue.compareTo(Integer.valueOf(validFieldInfoDto.getBetweenMax())) > 0){
//									logger.error("申请件批量上传第"+(i+1)+"页第"+(rowNum+1)+"行["+field+"-"+validFieldInfoDto.getFieldName()+"]不在区间["+validFieldInfoDto.getBetweenMin()+","+validFieldInfoDto.getBetweenMax()+"]范围内");
//									throw new ProcessException("申请件批量上传第"+(i+1)+"页第"+(rowNum+1)+"行["+validFieldInfoDto.getFieldName()+"]不在区间["+validFieldInfoDto.getBetweenMin()+","+validFieldInfoDto.getBetweenMax()+"]范围内");
//								}
//							}
//							if(validFieldInfoDto.getLengthFlag()){//字符串长度
//								if(fieldValue.length() > Integer.valueOf(validFieldInfoDto.getLengthMax())){
//									logger.error("申请件批量上传第"+(i+1)+"页第"+(rowNum+1)+"行["+field+"-"+validFieldInfoDto.getFieldName()+"]超过["+validFieldInfoDto.getLengthMax()+"]长度");
//									throw new ProcessException("申请件批量上传第"+(i+1)+"页第"+(rowNum+1)+"行["+validFieldInfoDto.getFieldName()+"]超过["+validFieldInfoDto.getLengthMax()+"]长度");
//								}
//							}
//							if(validFieldInfoDto.getRegexpFlag()){//正则
//								if(!fieldValue.matches(validFieldInfoDto.getRegexp())){
//									logger.error("申请件批量上传第"+(i+1)+"页第"+(rowNum+1)+"行["+field+"-"+validFieldInfoDto.getFieldName()+"]格式不正确");
//									throw new ProcessException("申请件批量上传第"+(i+1)+"页第"+(rowNum+1)+"行["+validFieldInfoDto.getFieldName()+"]格式不正确");
//								}
//							}
//						}
//					}
//				}
//
//				//数据联动校验
//				String msg = logicCheckRow(cellvalueMap,rowNum,i);
//				if(StringUtils.isNotBlank(msg)){
//					throw new ProcessException(msg);
//				}
//				//验证分期信息
//				String instalCheckMsg = instalCheck(cellvalueMap,rowNum,i);//分期信息校验
//				if(StringUtils.isNotBlank(instalCheckMsg)){
//					logger.error("验证分期申请资料信息失败,Msg["+instalCheckMsg+"]");
//					throw new ProcessException(instalCheckMsg);
//				}
//				
//				String appNo = tmAppnoSeqDao.getAppNo(OrganizationContextHolder.getOrg());// 生成appNo
//				cellvalueMap.put("appNo", appNo);
//				if(AppType.A.name().equals(appType) || AppType.B.name().equals(appType)){
//				upmodel.setContent((cellvalueMap.get("bscSuppInd")==null?"":cellvalueMap.get("bscSuppInd").toString())+"|"
//						+(cellvalueMap.get("idType")==null?"":cellvalueMap.get("idType").toString())+"|"
//						+(cellvalueMap.get("idNo")==null?"":cellvalueMap.get("idNo").toString())+"|"
//						+(cellvalueMap.get("productCd")==null?"":cellvalueMap.get("productCd").toString())+"|"
//						+(cellvalueMap.get("appNo")==null?"":cellvalueMap.get("appNo").toString())+"|"
//						+(cellvalueMap.get("appType")==null?"":cellvalueMap.get("appType").toString())+"|"
//						+(cellvalueMap.get("name")==null?"":cellvalueMap.get("name").toString())+"|"
//						+(cellvalueMap.get("isInstalment")==null?"":cellvalueMap.get("isInstalment").toString()));
//				}else if(AppType.S.name().equals(appType)){
//					upmodel.setContent((cellvalueMap.get("bscSuppInd")==null?"":cellvalueMap.get("bscSuppInd").toString())+"|"
//							+(cellvalueMap.get("fcIdType")==null?"":cellvalueMap.get("fcIdType").toString())+"|"
//							+(cellvalueMap.get("fcIdNo")==null?"":cellvalueMap.get("fcIdNo").toString())+"|"
//							+(cellvalueMap.get("poductCd")==null?"":cellvalueMap.get("poductCd").toString())+"|"
//							+(cellvalueMap.get("appNo")==null?"":cellvalueMap.get("appNo").toString())+"|"
//							+(cellvalueMap.get("appType")==null?"":cellvalueMap.get("appType").toString())+"|"
//							+(cellvalueMap.get("fcName")==null?"":cellvalueMap.get("fcName").toString()));
//				}
//				uplist.add(upmodel);
//
//				data.put(rowNum, cellvalueMap);
//			}
//			dataList.add(data);
//		}
//		
//		return dataList;
//	}
//	
//	private String instalCheck(HashMap<String, Serializable> cellvalueMap, int rowNum, int sheetNo) {
//		
//		String isInstalment = DataTypeUtils.getStringValue(cellvalueMap.get("isInstalment"));
//		if(Indicator.Y.name().equals(isInstalment)){
//			//申请分期信息，验证分期信息的有效性
//			String productCd = DataTypeUtils.getStringValue(cellvalueMap.get("productCd"));
//			TmProduct product = cacheContext.getProduct(productCd);
//			if(!Indicator.Y.name().equals(product.getIsInstalment()))
//				return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行卡产品["+productCd+"]不支持分期申请";
//			if(StringUtils.isBlank(DataTypeUtils.getStringValue(cellvalueMap.get("instalmentCreditActivityNo"))) )
//				return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行分期活动编号为空";
//			if(StringUtils.isBlank(DataTypeUtils.getStringValue(cellvalueMap.get("mccNo"))))
//				return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行分期商户号为空";
//			if(DataTypeUtils.getBigDecimalValue(cellvalueMap.get("cashAmt"))  == null)
//				return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行分期借款金额为空";
//			if(DataTypeUtils.getBigDecimalValue(cellvalueMap.get("cashAmt")).compareTo(BigDecimal.ZERO) == 0)
//				return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行分期借款金额为0";
//			if(DataTypeUtils.getIntegerValue(cellvalueMap.get("loanInitTerm")) == null)
//				return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行分期期数为空";
//			if(!Indicator.Y.name().equals(product.getIfUseCustomRate())){
//				if(StringUtils.isNotBlank(DataTypeUtils.getStringValue(cellvalueMap.get("loanFeeCalcMethod")))
//						|| StringUtils.isNotBlank(DataTypeUtils.getStringValue(cellvalueMap.get("loanFeeMethod")))
//						|| DataTypeUtils.getBigDecimalValue(cellvalueMap.get("appFeeAmt"))  != null
//						|| DataTypeUtils.getBigDecimalValue(cellvalueMap.get("appFeeRate")) != null)
//					return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行产品["+productCd+"]不支持自定义手续费收取";
//			}
//			if(StringUtils.isNotBlank(DataTypeUtils.getStringValue(cellvalueMap.get("loanFeeCalcMethod")))){
//				if(DataTypeUtils.getStringValue(cellvalueMap.get("loanFeeCalcMethod")).equals("A") && DataTypeUtils.getBigDecimalValue(cellvalueMap.get("appFeeAmt"))  == null)
//					return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行手续费按固定金额收取，手续费固定金额为空";
//				if(DataTypeUtils.getStringValue(cellvalueMap.get("loanFeeCalcMethod")).equals("R") && DataTypeUtils.getBigDecimalValue(cellvalueMap.get("appFeeRate"))  == null)
//					return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行手续费按比例收取，手续费收取比例为空";
//				if(!DataTypeUtils.getStringValue(cellvalueMap.get("loanFeeCalcMethod")).equals("A") && !DataTypeUtils.getStringValue(cellvalueMap.get("loanFeeCalcMethod")).equals("R"))
//					return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行手续费计算方式填写不正确";
//				if(DataTypeUtils.getBigDecimalValue(cellvalueMap.get("appFeeAmt"))  != null && DataTypeUtils.getBigDecimalValue(cellvalueMap.get("appFeeRate")) != null)
//					return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行手续费收取比例与固定金额只能填写一个";
//			}
//			if(StringUtils.isNotBlank(DataTypeUtils.getStringValue(cellvalueMap.get("loanFeeMethod")))){
//				try {
//					LoanFeeMethod.valueOf(DataTypeUtils.getStringValue(cellvalueMap.get("loanFeeMethod")));
//				} catch (Exception e) {
//					return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行手续费收取方式填写不正确";
//				}
//			}
//			List<TmAppInstalProgram> appInstalProgramList = cacheContext.getInstalProgramByProductCd(productCd);
//			TmAppInstalProgram appInstalProgram = null;
//			for(TmAppInstalProgram program:appInstalProgramList){
//				if(DataTypeUtils.getStringValue(cellvalueMap.get("instalmentCreditActivityNo")).equals(program.getProgramId()) && "A".equals(program.getProgramStatus())){
//					appInstalProgram = program;
//					break;
//				}
//			}
//			if(appInstalProgram == null)
//				return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行该产品不支持分期活动["+DataTypeUtils.getStringValue(cellvalueMap.get("instalmentCreditActivityNo"))+"]";
//			Date statrDate = appInstalProgram.getProgramStartDate();
//			Date endDate= appInstalProgram.getProgramEndDate();
//			Date nowDate = new Date();
//			if(!(DateUtils.truncatedCompareTo(nowDate, statrDate, Calendar.DAY_OF_MONTH) >= 0 
//					&& DateUtils.truncatedCompareTo(nowDate, endDate, Calendar.DAY_OF_MONTH) <= 0))
//				return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行分期活动生效日期不在允许范围内";
//			BigDecimal maxAmtActivity = appInstalProgram.getProgramMaxAmount();
//			BigDecimal minAmtActivity = appInstalProgram.getProgramMinAmount();
//			BigDecimal cashAmt = DataTypeUtils.getBigDecimalValue(cellvalueMap.get("cashAmt"));
//			if(maxAmtActivity != null && cashAmt.compareTo(maxAmtActivity) > 0)
//				return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行分期申请金额超过活动允许最大申请金额";
//			if(minAmtActivity != null && cashAmt.compareTo(minAmtActivity) < 0)
//				return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行分期申请金额小于活动允许最小申请金额";
//			
//			List<TmAppInstalProgramTerms> appInstalProgramTermList = cacheContext.getTmAppInstalProgramTermByProgramId(appInstalProgram.getProgramId());
//			TmAppInstalProgramTerms appInstalProgramTerm = null;
//			for(TmAppInstalProgramTerms terms : appInstalProgramTermList){
//				if(DataTypeUtils.getIntegerValue(cellvalueMap.get("loanInitTerm")).intValue() == terms.getTerm().intValue()){
//					appInstalProgramTerm = terms;
//					break;
//				}
//			}
//			if(appInstalProgramTerm == null)
//				return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行申请分期期数不支持";
//			BigDecimal maxTermAmt = appInstalProgramTerm.getTermMaxAmt();
//			BigDecimal minTermAmt = appInstalProgramTerm.getTermMinAmt();
//			if(maxTermAmt != null && cashAmt.compareTo(maxTermAmt) > 0)
//				return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行分期申请金额超过该期数允许最大申请金额";
//			if(minTermAmt != null && cashAmt.compareTo(minTermAmt) < 0)
//				return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行分期申请金额小于该期数允许最小申请金额";
//			
//			List<TmAppInstalProgramMerchant> appInstalProgramMerchantList = cacheContext.getTmAppInstalProgramMerchantByProgramId(appInstalProgram.getProgramId());
//			TmAppInstalProgramMerchant appInstalProgramMerchant = null;
//			for(TmAppInstalProgramMerchant programMerchant : appInstalProgramMerchantList){
//				if(DataTypeUtils.getStringValue(cellvalueMap.get("mccNo")).equals(programMerchant.getMerId())){
//					appInstalProgramMerchant = programMerchant;
//					break;
//				}
//			}
//			if(appInstalProgramMerchant == null)
//				return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行该分期活动不支持商户号["+DataTypeUtils.getStringValue(cellvalueMap.get("mccNo"))+"]";
//			
//			TmAppInstalMerchant merchant = cacheContext.getTmAppInstalMerchantByMerId(DataTypeUtils.getStringValue(cellvalueMap.get("mccNo")));
//			if(merchant == null)
//				return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行商户["+DataTypeUtils.getStringValue(cellvalueMap.get("mccNo"))+"]信息不存在";
//			//校验商户可用额度
//			BigDecimal usedAmt = (merchant.getFinishAuditLmt()==null?BigDecimal.ZERO:merchant.getFinishAuditLmt())
//					.add(merchant.getInAuditLmt()==null?BigDecimal.ZERO:merchant.getInAuditLmt())
//					.add(merchant.getPostingLmt()==null?BigDecimal.ZERO:merchant.getPostingLmt());
//			BigDecimal merLmt = merchant.getMerLmt()==null?BigDecimal.ZERO:merchant.getMerLmt();
//			logger.info("分期申请，商户额度检验，商户号[{}]，商户授信额度：{}，商户已用信额度:{}=审批中金额:{}+每日审批通过金额:{}+已入账未出账单金额(当日之前):{}，申请金额：{}",
//					merchant.getMerId(),merLmt,usedAmt,merchant.getInAuditLmt(),merchant.getFinishAuditLmt(),merchant.getPostingLmt(),cashAmt);
//			if(merLmt.subtract(usedAmt).compareTo(cashAmt) < 0){
//				logger.info("商户可用额度不足");
//				return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行商户可用额度不足";
//			}
//				
//		}else {
//			if(StringUtils.isNotBlank(DataTypeUtils.getStringValue(cellvalueMap.get("instalmentCreditActivityNo"))) 
//					|| StringUtils.isNotBlank(DataTypeUtils.getStringValue(cellvalueMap.get("mccNo")))
//					|| StringUtils.isNotBlank(DataTypeUtils.getStringValue(cellvalueMap.get("loanFeeCalcMethod")))
//					|| StringUtils.isNotBlank(DataTypeUtils.getStringValue(cellvalueMap.get("loanFeeMethod")))
//					|| DataTypeUtils.getIntegerValue(cellvalueMap.get("loanInitTerm")) != null
//					|| DataTypeUtils.getBigDecimalValue(cellvalueMap.get("cashAmt")) != null
//					|| DataTypeUtils.getBigDecimalValue(cellvalueMap.get("appFeeRate")) != null
//					|| DataTypeUtils.getBigDecimalValue(cellvalueMap.get("appFeeAmt")) != null)
//				return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行填写分期信息，但未申请分期";
//		}
//		return null;
//	}
//	
//	/**
//	 * 联动验证
//	 * 
//	 * 第一页为独立主卡或主附同申
//	 * 第二页为独立附卡
//	 * @param cellvalue
//	 * @param rowNum
//	 * @param appType
//	 * @return
//	 */
//	public String logicCheckRow(HashMap<String, Serializable> cellvalueMap,int rowNum,int sheetNo){
//		String appType = DataTypeUtils.getStringValue(cellvalueMap.get("appType"));
//		if(appType.equals(AppType.A.name())||appType.equals(AppType.B.name())){
//			if(appType.equals(AppType.A.name())){
//				//主附同申附卡如果证件类型为身份证或临时身份证，则校验身份证有效性, 性别，生日有效性
//				if(IdType.I.name().equals(DataTypeUtils.getStringValue(cellvalueMap.get("fcIdType")))){
//					// 证件号码有效性
//					if(!IdentificationCodeUtil.isIdentityCode(DataTypeUtils.getStringValue(cellvalueMap.get("fcIdNo")))){
//						return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行证件号码无效";
//					}
//					// 性别有效性质
//					if(!ApplyInfoValidityUtil.isGender(cellvalueMap.get("fcIdNo").toString(), cellvalueMap.get("fcGender").toString())){
//						return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行性别有误";
//					}
//					// 生日有效性
//					if(!ApplyInfoValidityUtil.isBirthday(cellvalueMap.get("fcIdNo").toString(),DataTypeUtils.getDateValue(cellvalueMap.get("fcBirthday")))){
//						return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行生日有误";
//					}
//				}
//				//附卡证件到期日校验
//				if(DataTypeUtils.getStringValue(cellvalueMap.get("fcIdLastAll")).equals(Indicator.N.name()) && DataTypeUtils.getDateValue(cellvalueMap.get("fcIdLastAll"))==null){
//					return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行证件到期日为必填";
//				}
//			}
//			//主卡如果证件类型为身份证或临时身份证，则校验身份证有效性, 性别，生日有效性
//			if("I".equals(cellvalueMap.get("idType").toString())){
//				// 证件号码有效性
//				if(!IdentificationCodeUtil.isIdentityCode(cellvalueMap.get("idNo").toString())){
//					return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行证件号码无效";
//				}
//				// 性别有效性质
//				if(!ApplyInfoValidityUtil.isGender(cellvalueMap.get("idNo").toString(), cellvalueMap.get("gender").toString())){
//					return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行性别有误";
//				}
//				// 生日有效性
//				if(!ApplyInfoValidityUtil.isBirthday(cellvalueMap.get("idNo").toString(),DataTypeUtils.getDateValue(cellvalueMap.get("birthday")))){
//					return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行生日有误";
//				}
//			}
//			
//			//主卡联系人证件类型为身份证或临时身份证，则校验身份证有效性, 性别，生日有效性
//			if(cellvalueMap.get("contactIdType") != null && cellvalueMap.get("contactIdNo") != null && "I".equals(cellvalueMap.get("contactIdType").toString())){
//				// 证件号码有效性
//				if(!IdentificationCodeUtil.isIdentityCode(cellvalueMap.get("contactIdNo").toString())){
//					return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行证件号码无效";
//				}
//				// 性别有效性质
//				if(!ApplyInfoValidityUtil.isGender(cellvalueMap.get("contactIdNo").toString(), cellvalueMap.get("contactGender").toString())){
//					return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行性别有误";
//				}
//				// 生日有效性
//				if(!ApplyInfoValidityUtil.isBirthday(cellvalueMap.get("contactIdNo").toString(),DataTypeUtils.getDateValue(cellvalueMap.get("contactBirthday")))){
//					return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行生日有误";
//				}
//			}
//			//主卡其他联系人证件类型为身份证或临时身份证，则校验身份证有效性
//			if(cellvalueMap.get("contactOIdType") != null && cellvalueMap.get("contactOIdNo") != null && "I".equals(cellvalueMap.get("contactOIdType").toString())){
//				// 证件号码有效性
//				if(!IdentificationCodeUtil.isIdentityCode(cellvalueMap.get("contactOIdNo").toString())){
//					return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"其他联系人证件号码无效";
//				}
//			}
//			//主卡证件到期日校验
//			if(DataTypeUtils.getStringValue(cellvalueMap.get("idLastAll")).equals(Indicator.N.name()) && DataTypeUtils.getDateValue(cellvalueMap.get("idLastAll"))==null){
//				return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行证件到期日为必填";
//			}
//			if(!DataTypeUtils.getStringValue(cellvalueMap.get("ddInd")).equals(Indicator.N.name())){
//				if(DataTypeUtils.getStringValue(cellvalueMap.get("ddBankAcctName"))==null	|| DataTypeUtils.getStringValue(cellvalueMap.get("ddBankAcctNo"))==null){
//					return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行约定还款账号/姓名为必填";
//				}else {
//					if(!DataTypeUtils.getStringValue(cellvalueMap.get("ddBankAcctNo")).matches("^\\d{16,19}")){
//						return "约定本行还款扣款账号格式不正确";
//					}
//				}				
//			}
//			String cardFetchMethod = DataTypeUtils.getStringValue(cellvalueMap.get("cardFetchMethod"));
//			if(cardFetchMethod.equals(CardFetchMethod.B.name()) && cellvalueMap.get("fetchBranch") == null){
//				return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行领卡网点为必填";
//			}else if((cardFetchMethod.equals(CardFetchMethod.A.name())||cardFetchMethod.equals(CardFetchMethod.E.name()))
//					&& cellvalueMap.get("cardMailerInd") == null){
//				return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行卡片寄送地址为必填";
//			}
//			
//			//卡产品校验
//			String productCd = DataTypeUtils.getStringValue(cellvalueMap.get("productCd"));
//			try {
//				LinkedHashMap<Object, Object> productMap = cacheContext.getSimpleProductLinkedMap("A");
//				if (StringUtils.isNotBlank(productCd)) {
//					String product = (String) productMap.get(productCd);
//					LinkedHashMap<Object, Object> cardFaceMap = cacheContext.getSimpleProductCardFaceLinkedMap(productCd);
//					if (product == null) {
//						logger.error("申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行卡产品编号["+productCd+"]无效");
//						throw new ProcessException("该申请资料卡产品编号无效");
//					} else if (!cardFaceMap.containsKey(DataTypeUtils.getStringValue(cellvalueMap.get("cardFace")))) {
//						for (Object s : cardFaceMap.values()) {
//							logger.debug("可选卡面ID：" + s.toString());
//						}
//						logger.error("申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行卡面信息["+DataTypeUtils.getStringValue(cellvalueMap.get("cardFace"))+"]无效");
//						throw new ProcessException("申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行卡面信息无效");
//					}
//				} else {
//					logger.error("未获取到系统配置有效的卡产品,请重试！");
//					throw new ProcessException("未获取到系统配置有效的卡产品,请重试！");
//				}
//			} catch (Exception e) {
//				logger.error("申请资料第"+(sheetNo+1)+"页第"+rowNum+"行卡产品编号["+productCd+"]无效");
//				throw new ProcessException("申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行卡产品编号["+productCd+"]或卡面无效");
//			}
//		}else if(appType.equals(AppType.S.name())){
//			//主附同申附卡如果证件类型为身份证或临时身份证，则校验身份证有效性, 性别，生日有效性
//			if(IdType.I.name().equals(DataTypeUtils.getStringValue(cellvalueMap.get("fcIdType")))){
//				// 证件号码有效性
//				if(!IdentificationCodeUtil.isIdentityCode(cellvalueMap.get("fcIdNo").toString())){
//					return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行证件号码无效";
//				}
//				// 性别有效性质
//				if(!ApplyInfoValidityUtil.isGender(cellvalueMap.get("fcIdNo").toString(), cellvalueMap.get("fcGender").toString())){
//					return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行性别有误";
//				}
//				// 生日有效性
//				if(!ApplyInfoValidityUtil.isBirthday(cellvalueMap.get("fcIdNo").toString(),DataTypeUtils.getDateValue(cellvalueMap.get("fcBirthday")))){
//					return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行生日有误";
//				}
//			}
//			if(DataTypeUtils.getStringValue(cellvalueMap.get("fcIdLastAll")).equals(Indicator.N.name()) && DataTypeUtils.getDateValue(cellvalueMap.get("fcIdLastAll"))==null){
//				return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行证件到期日为必填";
//			}
//			//卡片寄送方式校验
//			String cardFetch =  DataTypeUtils.getStringValue(cellvalueMap.get("fcCardFetch"));
//			if (StringUtils.isNotEmpty(cardFetch)){
//				if(cardFetch.equals(CardFetchMethod.B.toString()) && StringUtils.isBlank(DataTypeUtils.getStringValue(cellvalueMap.get("fcFetchBranch")))){
//					return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行领卡网点为必填 ";
//				}else if((cardFetch.equals(CardFetchMethod.A.toString()) || cardFetch.equals(CardFetchMethod.E.toString()))
//						&& StringUtils.isBlank(DataTypeUtils.getStringValue(cellvalueMap.get("fcCardMailerInd")))) {
//					return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"行卡片寄送地址为必填";
//				}
//			}else {
//				return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"领卡方式为必填";
//			}
//
//			//主卡卡号验证
//			String fcPrimCardNo = DataTypeUtils.getStringValue(cellvalueMap.get("fcPrimCardNo"));
//			if(StringUtils.isNotBlank(fcPrimCardNo)){
//				String flag = tmMirCardService.validateByPrimCardNo(fcPrimCardNo);
//				if(StringUtils.isNotEmpty(flag) && !flag.equals("true")){
//					if(flag.equals("S")){
//				     	return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"附卡不能申请独立附卡";
//				    }else if(flag.equals("V")){
//				    	return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"主卡卡号无效";
//				    }else if(flag.equals("B")){
//				    	return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"主卡卡产品无效";
//				    }else if(flag.equals("O")){
//				    	return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"公务卡不能申请独立附卡";
//				    }else{
//				     	return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"未检索到有效的主卡持卡人信息,请核实主卡账户和卡片状态";
//				    }
//				}
//			}else {
//				return "申请资料第"+(sheetNo+1)+"页第"+(rowNum+1)+"主卡卡号为必填";
//			}
//		}
//
//		return "";
//	}
//	
//	/**
//	 * 数据分类处理
//	 * @param data
//	 * @return
//	 */
//	private List<ApplyInfoDto> makeSheetSaveModel(List<Map<Integer, HashMap<String, Serializable>>> dataList){
//		List<ApplyInfoDto> applyInfoDtoList = null;
//		if(dataList!=null && dataList.size()>0){
//			applyInfoDtoList = new ArrayList<ApplyInfoDto>();
//			for (Map<Integer, HashMap<String, Serializable>> data : dataList) {
//				for (Entry<Integer,HashMap<String,Serializable>> en: data.entrySet()) {
//					HashMap<String, Serializable> cellvalueMap = en.getValue();
//	                if(cellvalueMap.get("org")==null) {
//	                	cellvalueMap.put("org", OrganizationContextHolder.getOrg());
//					}
//	                ApplyInfoDto applyInfoDto = new ApplyInfoDto();
//					Map<String , Serializable> tmAppNodeInfoRecordMap = new HashMap<String, Serializable>();
//					ApplyNodeCommonData applyNodeCommonData = new ApplyNodeCommonData();
//					Date now = new Date();
//					String userName = OrganizationContextHolder.getUserNo();
//					
//					//TmAppMain处理
//					TmAppMain tmAppMain = new TmAppMain();
//					tmAppMain.updateFromMap(cellvalueMap);
//					tmAppMain.setIfRefuse(Indicator.Y.name());//是否拒绝 Y：通过 N：拒绝
//					tmAppMain.setRtfState(RtfState.B10.name());
//					tmAppMain.setIsUrgentCard(StringUtils.isBlank(DataTypeUtils.getStringValue(cellvalueMap
//							.get("isUrgentCard")))?Indicator.N.name():DataTypeUtils.getStringValue(cellvalueMap.get("isUrgentCard")));
//					tmAppMain.setIsPriority(StringUtils.isBlank(DataTypeUtils.getStringValue(cellvalueMap
//							.get("isPriority")))?Indicator.N.name():DataTypeUtils.getStringValue(cellvalueMap.get("isPriority")));
//					tmAppMain.setIsClt(StringUtils.isBlank(DataTypeUtils.getStringValue(cellvalueMap
//							.get("isClt")))?Indicator.N.name():DataTypeUtils.getStringValue(cellvalueMap.get("isClt")));
//					
//					tmAppMain.setCreateDate(now);
//					tmAppMain.setCreateUser(userName);
//					tmAppMain.setOwner(null);
//					tmAppMain.setAssignee(null);
//					
//					if(StringUtils.isBlank(tmAppMain.getIsInstalment()))
//						tmAppMain.setIsInstalment(Indicator.N.name());
//					
//					applyInfoDto.setAppNo(tmAppMain.getAppNo());
//					
//					String appType = tmAppMain.getAppType();
//					if(StringUtils.isBlank(appType)){
//						throw new ProcessException("["+tmAppMain.getName()+"]申请类型为空!");
//					}
//					TmProduct tmProduct = cacheContext.getProduct(DataTypeUtils.getStringValue(cellvalueMap.get("productCd")));
//					if(tmProduct == null){
//						throw new ProcessException("找不到对应的卡产品，请检验["+tmAppMain.getName()+"]的卡产品");
//					}
//					String prodRealFlag = tmProduct.getIfRealtimeIssuing()==null?Indicator.N.name():tmProduct.getIfRealtimeIssuing();
//					tmAppMain.setRealtimeIssuingFlag(StringUtils.isBlank(DataTypeUtils.getStringValue(cellvalueMap
//							.get("realtimeIssuingFlag")))?prodRealFlag:DataTypeUtils.getStringValue(cellvalueMap.get("realtimeIssuingFlag")));
//					// 设置历史信息
//					List<TmAppHistory> tmAppHistoryList = new ArrayList<TmAppHistory>();
//					TmAppHistory tmAppHistory = new TmAppHistory();
//					tmAppHistory = AppCommonUtil.insertApplyHist(tmAppMain.getAppNo(),userName,
//							RtfState.valueOf(tmAppMain.getRtfState()),null,tmAppMain.getRemark());
//	
//					//卡面信息
//					List<TmAppCardfaceInfo> tmAppCardfaceInfoList = new ArrayList<TmAppCardfaceInfo>();
//					Map<String, List<TmAppCardfaceInfo>> tmAppCardfaceInfoMap = new HashMap<String, List<TmAppCardfaceInfo>>();
//					Map<Object, Object> cacheCFMap = null;
//					if(StringUtils.isNotEmpty(tmProduct.getProductCd())){
//						cacheCFMap =  cacheContext.getSimpleProductCardFaceLinkedMap(tmProduct.getProductCd());
//					}
//					if(appType.equals(AppType.B.name()) || appType.equals(AppType.A.name())){
//						if(appType.equals(AppType.A.name())){
//							//主附同申附卡部分处理
//							TmAppAttachApplierInfo attachCust = setTmAppAttachApplierInfo(cellvalueMap);
//							attachCust.setCreateDate(now);
//							attachCust.setCreateUser(userName);
//							
//							//附卡卡面
//							TmAppCardfaceInfo attchTmAppCardfaceInfo = new TmAppCardfaceInfo();
//							attchTmAppCardfaceInfo.updateFromMap(cellvalueMap);
//							attchTmAppCardfaceInfo.setBscSuppInd(BscSuppIndicator.S.name());
//							attchTmAppCardfaceInfo.setPyhCd(DataTypeUtils.getStringValue(cellvalueMap.get("cardFace")));
//						//	attchTmAppCardfaceInfo.setPyhDescrip(tmProduct.getProductDesc());
//							if(cacheCFMap != null && cacheCFMap.size() > 0){
//								for (Object obj : cacheCFMap.values()) {
//									TmAclDict aclDict = (TmAclDict) obj;
//									if(DataTypeUtils.getStringValue(cellvalueMap.get("cardFace")).equals(aclDict.getCode())){
//										attchTmAppCardfaceInfo.setPyhDescrip(aclDict.getCodeName());//卡面描述
//										break;
//									}
//								}
//							}
//							attchTmAppCardfaceInfo.setAttachNo(5);
//							attchTmAppCardfaceInfo.setUpdateDate(new Date());
//							attchTmAppCardfaceInfo.setUpdateUser(OrganizationContextHolder.getUserNo());
//	
//							tmAppCardfaceInfoList.add(attchTmAppCardfaceInfo);
//							tmAppCardfaceInfoMap.put(AppConstant.ATTATCH_TABS+5, tmAppCardfaceInfoList);
//						}
//						//主卡申请人信息
//						TmAppPrimApplicantInfo primCust = new TmAppPrimApplicantInfo();
//						primCust.updateFromMap(cellvalueMap);
//						if (cellvalueMap.get("idType") != null && cellvalueMap.get("idType").toString().equals(IdType.I.name())) {
//							primCust.setIdNo(cellvalueMap.get("idNo") == null?null:cellvalueMap.get("idNo").toString().toUpperCase());
//						}
//						primCust.setEmbLogo(DataTypeUtils.getStringValue(cellvalueMap.get("embLogo")));
//						primCust.setAppnoExternal(DataTypeUtils.getStringValue(cellvalueMap.get("appNo")));
//						primCust.setCreateDate(now);
//						primCust.setCreateUser(userName);
//						applyInfoDto.setTmAppPrimApplicantInfo(primCust);
//						
//						//亲属联系人信息
//						TmAppContact tmAppContact = new TmAppContact();
//						tmAppContact.updateFromMap(cellvalueMap);
//						if (cellvalueMap.get("contactIdType") != null && cellvalueMap.get("contactIdType").toString().equals(IdType.I.name())) {
//							primCust.setIdNo(cellvalueMap.get("contactIdNo") == null?null:cellvalueMap.get("contactIdNo").toString().toUpperCase());
//						}
//						tmAppContact.setContactType("1");
//						tmAppContact.setCreateDate(now);
//						tmAppContact.setCreateUser(userName);
//						
//						//其他联系人信息
//						TmAppContact otherContactInfo = new TmAppContact();
//						otherContactInfo.setContactName(DataTypeUtils.getStringValue(cellvalueMap.get("contactOName")));
//						otherContactInfo.setContactIdType(DataTypeUtils.getStringValue(cellvalueMap.get("contactOIdType")));
//						if (cellvalueMap.get("contactOIdType") != null && cellvalueMap.get("contactOIdType").toString().equals(IdType.I.name())) {
//							otherContactInfo.setContactIdNo(DataTypeUtils.getStringValue(cellvalueMap.get("contactOIdNo")));
//						}
//						otherContactInfo.setContactMobile(DataTypeUtils.getStringValue(cellvalueMap.get("contactOMobile")));
//						if(StringUtils.isBlank(otherContactInfo.getContactName()) || StringUtils.isBlank(otherContactInfo.getContactIdType())
//							|| StringUtils.isBlank(otherContactInfo.getContactIdNo()) || StringUtils.isBlank(otherContactInfo.getContactMobile())){
//							
//							otherContactInfo = null;
//						}else {
//							otherContactInfo.setContactBirthday(DataTypeUtils.getDateValue(cellvalueMap.get("contactOBirthday")));
//							otherContactInfo.setContactCorpFax(DataTypeUtils.getStringValue(cellvalueMap.get("contactOCorpFax")));
//							otherContactInfo.setContactCorpPost(DataTypeUtils.getStringValue(cellvalueMap.get("contactOCorpPost")));
//							otherContactInfo.setContactEmpName(DataTypeUtils.getStringValue(cellvalueMap.get("contactOEmpName")));
//							otherContactInfo.setContactEmpPhone(DataTypeUtils.getStringValue(cellvalueMap.get("contactOEmpPhone")));
//							otherContactInfo.setContactGender(DataTypeUtils.getStringValue(cellvalueMap.get("contactOGender")));
//							otherContactInfo.setContactRelation(DataTypeUtils.getStringValue(cellvalueMap.get("contactORelation")));
//							otherContactInfo.setContactTelephone(DataTypeUtils.getStringValue(cellvalueMap.get("contactOTelephone")));
//							otherContactInfo.setAppNo(DataTypeUtils.getStringValue(cellvalueMap.get("appNo")));
//							otherContactInfo.setOrg(DataTypeUtils.getStringValue(cellvalueMap.get("org")));
////							otherContactInfo.setJpaVersion(0);
//							otherContactInfo.setContactType("2");
//							otherContactInfo.setCreateDate(now);
//							otherContactInfo.setCreateUser(userName);
//						}
//						Map<String, TmAppContact> contactInfoMap = new HashMap<String, TmAppContact>();
//						contactInfoMap.put(AppConstant.M_CON_ITEM_INFO_PREFIX+1, tmAppContact);
//						if(otherContactInfo != null){
//							contactInfoMap.put(AppConstant.M_CON_ITEM_INFO_PREFIX+2, otherContactInfo);
//						}
//						applyInfoDto.setTmAppContactMap(contactInfoMap);
//											
//						//附件信息
//						TmAppPrimAnnexEvi tmAppPrimAnnexEvi = new TmAppPrimAnnexEvi();
//						tmAppPrimAnnexEvi.updateFromMap(cellvalueMap);
//						tmAppPrimAnnexEvi.setCreateDate(now);
//						tmAppPrimAnnexEvi.setCreateUser(userName);
//						applyInfoDto.setTmAppPrimAnnexEvi(tmAppPrimAnnexEvi);
//																
//						//保存节点数据
//						applyNodeCommonData = AppCommonUtil.setApplyNodeCommonData(applyNodeCommonData,tmAppMain);
//						tmAppNodeInfoRecordMap.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
//						applyInfoDto.setTmAppNodeInfoRecordMap(tmAppNodeInfoRecordMap);
//						
//						tmAppHistory.setName(primCust.getName());
//						tmAppHistory.setIdType(primCust.getIdType());
//						tmAppHistory.setIdNo(primCust.getIdNo());
//						
//						//主卡卡面
//						TmAppCardfaceInfo mainTmAppCardfaceInfo = new TmAppCardfaceInfo();
//						mainTmAppCardfaceInfo.updateFromMap(cellvalueMap);
//						mainTmAppCardfaceInfo.setBscSuppInd(BscSuppIndicator.B.name());
//						mainTmAppCardfaceInfo.setAttachNo(1);
//						mainTmAppCardfaceInfo.setPyhCd(DataTypeUtils.getStringValue(cellvalueMap.get("cardFace")));
//				//		mainTmAppCardfaceInfo.setPyhDescrip(tmProduct.getProductDesc());
//						
//						if(cacheCFMap != null && cacheCFMap.size() > 0){
//							for (Object obj : cacheCFMap.values()) {
//								TmAclDict aclDict = (TmAclDict) obj;
//								if(DataTypeUtils.getStringValue(cellvalueMap.get("cardFace")).equals(aclDict.getCode())){
//									mainTmAppCardfaceInfo.setPyhDescrip(aclDict.getCodeName());//卡面描述
//									break;
//								}
//							}
//						}
//						mainTmAppCardfaceInfo.setUpdateDate(new Date());
//						mainTmAppCardfaceInfo.setUpdateUser(OrganizationContextHolder.getUserNo());
//	
//						tmAppCardfaceInfoList.add(mainTmAppCardfaceInfo);
//						tmAppCardfaceInfoMap.put(AppConstant.MTAB, tmAppCardfaceInfoList);
//						applyInfoDto.setTmAppCardFaceInfoMap(tmAppCardfaceInfoMap);
//						
//						//分期申请信息
//						if(Indicator.Y.name().equals(tmAppMain.getIsInstalment())){
//							TmAppInstalLoan tmAppInstalLoan = new TmAppInstalLoan();
//							tmAppInstalLoan.updateFromMap(cellvalueMap);
//							tmAppInstalLoan.setAppNo(tmAppMain.getAppNo());
//							tmAppInstalLoan.setCreateDate(new Date());
//							tmAppInstalLoan.setCreateUser(OrganizationContextHolder.getUserNo());
//							tmAppInstalLoan.setLoanRegStatus(LoanRegStatus.U.name());
//							applyInfoDto.setTmAppInstalLoan(tmAppInstalLoan);
//						}
//						
//					}else if(appType.equals(AppType.S.name())){
//						tmAppMain.setAppLmt(null);// 申请额度与主卡共享
//						
//						//附卡申请人信息
//						TmAppAttachApplierInfo attachCust = setTmAppAttachApplierInfo(cellvalueMap);
//						attachCust.setCreateDate(now);
//						attachCust.setCreateUser(userName);
//						Map<String, TmAppAttachApplierInfo> tmAppAttachInfoMap = new HashMap<String, TmAppAttachApplierInfo>();
//						tmAppAttachInfoMap.put(AppConstant.ATTATCH_TABS+5, attachCust);
//						applyInfoDto.setTmAppAttachInfoMap(tmAppAttachInfoMap);
//						
//						if(attachCust != null){
//							tmAppMain.setName(attachCust.getName());
//							tmAppMain.setIdNo(attachCust.getIdNo());
//							tmAppMain.setIdType(attachCust.getIdType());
//							tmAppMain.setCellphone(attachCust.getCellphone());
//							tmAppMain.setCorpName(attachCust.getCorpName());
//							tmAppMain.setEmpPhone(attachCust.getEmpPhone());
//						}
//						
//						//保存节点数据
//						applyNodeCommonData = AppCommonUtil.setApplyNodeCommonData(applyNodeCommonData,tmAppMain);
//						tmAppNodeInfoRecordMap.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
//						applyInfoDto.setTmAppNodeInfoRecordMap(tmAppNodeInfoRecordMap);
//						
//						tmAppHistory.setName(attachCust.getName());
//						tmAppHistory.setIdType(attachCust.getIdType());
//						tmAppHistory.setIdNo(attachCust.getIdNo());
//						
//						//附卡卡面
//						TmAppCardfaceInfo attachTmAppCardfaceInfo = new TmAppCardfaceInfo();
//						attachTmAppCardfaceInfo.updateFromMap(cellvalueMap);
//						attachTmAppCardfaceInfo.setBscSuppInd(BscSuppIndicator.S.name());
//						attachTmAppCardfaceInfo.setPyhCd(DataTypeUtils.getStringValue(cellvalueMap.get("cardFace")));
//				//		attachTmAppCardfaceInfo.setPyhDescrip(tmProduct.getProductDesc());
//						if(cacheCFMap != null && cacheCFMap.size() > 0){
//							for (Object obj : cacheCFMap.values()) {
//								TmAclDict aclDict = (TmAclDict) obj;
//								if(DataTypeUtils.getStringValue(cellvalueMap.get("cardFace")).equals(aclDict.getCode())){
//									attachTmAppCardfaceInfo.setPyhDescrip(aclDict.getCodeName());//卡面描述
//									break;
//								}
//							}
//						}
//						attachTmAppCardfaceInfo.setAttachNo(5);
//						attachTmAppCardfaceInfo.setUpdateDate(new Date());
//						attachTmAppCardfaceInfo.setUpdateUser(OrganizationContextHolder.getUserNo());
//	
//						tmAppCardfaceInfoList.add(attachTmAppCardfaceInfo);
//						tmAppCardfaceInfoMap.put(AppConstant.ATTATCH_TABS+5, tmAppCardfaceInfoList);
//						applyInfoDto.setTmAppCardFaceInfoMap(tmAppCardfaceInfoMap);
//					}
//					//卡片信息
//					TmAppPrimCardInfo tmAppPrimCardInfo = new TmAppPrimCardInfo();
//					tmAppPrimCardInfo.updateFromMap(cellvalueMap);
//					tmAppPrimCardInfo.setInputName(userName);
//					tmAppPrimCardInfo.setInputDate(now);
//					tmAppPrimCardInfo.setCreateDate(now);
//					tmAppPrimCardInfo.setCreateUser(userName);
//					applyInfoDto.setTmAppPrimCardInfo(tmAppPrimCardInfo);
//					
//					//备注
//					Map<String, TmAppMemo> tmAppMemoMapLast = new HashMap<String, TmAppMemo>();
//					TmAppMemo tmAppMemo = new TmAppMemo();
//					tmAppMemo.setMemoInfo(StringUtils.isBlank(DataTypeUtils.getStringValue(cellvalueMap.get("memo")))?DataTypeUtils.getStringValue(cellvalueMap.get("remark")):"");
//					tmAppMemo.setAppNo(tmAppMain.getAppNo());
//					tmAppMemo.setMemoType(AppConstant.APP_MEMO);
//					tmAppMemo.setCreateDate(now);
//					tmAppMemo.setCreateUser(userName);
//					tmAppMemo.setMemoVersion(0);
//					tmAppMemo.setRtfState(RtfState.B10.name());
//					tmAppMemo.setTaskKey(EcmsAuthority.INPUT.name());
//					tmAppMemo.setTaskDesc(EcmsAuthority.INPUT.lab);
//					tmAppMemoMapLast.put(AppConstant.APP_MEMO + "-"+ EcmsAuthority.INPUT.name(), tmAppMemo);
//					applyInfoDto.setTmAppMemoMapLast(tmAppMemoMapLast);
//					
//					List<AlreadyCardsCardInfoDto> alreadyCardsCardInfoDtos = alreadyCardDao.getAlreadyCardsCardInfoDtos(tmAppMain.getIdType(), tmAppMain.getIdNo());
//					if(CollectionUtils.sizeGtZero(alreadyCardsCardInfoDtos)){
//						tmAppMain.setIsOldCust(Indicator.Y.name());//是否是老客户的标志
//					}
//					applyInfoDto.setTmAppMain(tmAppMain);
//					
//					tmAppHistoryList.add(tmAppHistory);
//					applyInfoDto.setTmAppHistoryList(tmAppHistoryList);
//					
//					applyInfoDtoList.add(applyInfoDto);
//				}
//			}
//		}
//		
//		return applyInfoDtoList;
//	}
//	
//	/**
//	 * 附卡信息设置
//	 * @param dataMap
//	 * @return
//	 */
//	private TmAppAttachApplierInfo setTmAppAttachApplierInfo(HashMap<String, Serializable> dataMap){
//		TmAppAttachApplierInfo attachCust = new TmAppAttachApplierInfo();	
//		attachCust.setOrg(DataTypeUtils.getStringValue(dataMap.get("org")));
//		attachCust.setAppNo(DataTypeUtils.getStringValue(dataMap.get("appNo")));
//		attachCust.setAttachNo(5);
//		attachCust.setIfSelectedCard(DataTypeUtils
//				.getStringValue(dataMap.get("fcIfSelectedCard"))==null?Indicator.N.name():DataTypeUtils.getStringValue(dataMap.get("fcIfSelectedCard")));
//		attachCust.setCardNo(DataTypeUtils.getStringValue(dataMap.get("fcCardNo")));
//		attachCust.setPrimCardNo(DataTypeUtils.getStringValue(dataMap.get("fcPrimCardNo")));
//		attachCust.setRelationshipToBsc(DataTypeUtils.getStringValue(dataMap.get("fcRelationshipToBsc")));
//		attachCust.setName(DataTypeUtils.getStringValue(dataMap.get("fcName")));
//		attachCust.setEmbLogo(DataTypeUtils.getStringValue(dataMap.get("fcEmbLogo")));
//		attachCust.setGender(DataTypeUtils.getStringValue(dataMap.get("fcGender")));
//		attachCust.setAge(DataTypeUtils.getStringValue(dataMap.get("fcAge")));
//		attachCust.setBirthday(DataTypeUtils.getDateValue(dataMap.get("fcBirthday")));
//		attachCust.setMaritalStatus(DataTypeUtils.getStringValue(dataMap.get("fcMaritalStatus")));
//		attachCust.setPrOfCountry(DataTypeUtils.getStringValue(dataMap.get("fcPrOfCountry")));
//		attachCust.setResidencyCountryCd(DataTypeUtils.getStringValue(dataMap.get("fcResidencyCountryCd")));
//		attachCust.setNationality(DataTypeUtils.getStringValue(dataMap.get("fcNationality")));
//		attachCust.setQualification(DataTypeUtils.getStringValue(dataMap.get("fcQualification")));
//		attachCust.setDegree(DataTypeUtils.getStringValue(dataMap.get("fcDegree")));
//		attachCust.setIdType(DataTypeUtils.getStringValue(dataMap.get("fcIdType")));
//		if (dataMap.get("fcIdType")!=null && dataMap.get("fcIdType").equals(IdType.I.name())) {
//			attachCust.setIdNo(dataMap.get("fcIdNo") == null?null:dataMap.get("fcIdNo").toString().toUpperCase());
//		}
//		attachCust.setIdLastDate(DataTypeUtils.getDateValue(dataMap.get("fcIdLastDate")));
//		attachCust.setIdIssuerAddress(DataTypeUtils.getStringValue(dataMap.get("fcIdIssuerAddress")));
//		attachCust.setYearIncome(DataTypeUtils.getBigDecimalValue(dataMap.get("fcYearIncome")));
//		attachCust.setMotherLogo(DataTypeUtils.getStringValue(dataMap.get("fcMotherLogo")));
//		attachCust.setEmail(DataTypeUtils.getStringValue(dataMap.get("fcEmail")));
//		attachCust.setHouseOwnership(DataTypeUtils.getStringValue(dataMap.get("fcHouseOwnership")));
//		attachCust.setHomeStandFrom(DataTypeUtils.getDateValue(dataMap.get("fcHomeStandFrom")));
//		attachCust.setCellphone(DataTypeUtils.getStringValue(dataMap.get("fcCellphone")));
//		attachCust.setCardFetch(DataTypeUtils.getStringValue(dataMap.get("fcCardFetch")));
//		attachCust.setFetchBranch(DataTypeUtils.getStringValue(dataMap.get("fcFetchBranch")));
//		attachCust.setCardMailerInd(DataTypeUtils.getStringValue(dataMap.get("fcCardMailerInd")));
//		attachCust.setBankmemFlag(DataTypeUtils.getStringValue(dataMap.get("fcBankmemFlag")));
//		attachCust.setBankmemberNo(DataTypeUtils.getStringValue(dataMap.get("fcBankmemberNo")));
//		attachCust.setCorpName(DataTypeUtils.getStringValue(dataMap.get("fcCorpName")));
//		attachCust.setEmpAddrCtryCd(DataTypeUtils.getStringValue(dataMap.get("fcEmpAddrCtryCd")));
//		attachCust.setEmpProvince(DataTypeUtils.getStringValue(dataMap.get("fcEmpProvince")));
//		attachCust.setEmpCity(DataTypeUtils.getStringValue(dataMap.get("fcEmpCity")));
//		attachCust.setEmpZone(DataTypeUtils.getStringValue(dataMap.get("fcEmpZone")));
//		attachCust.setEmpStructure(DataTypeUtils.getStringValue(dataMap.get("fcEmpStructure")));
//		attachCust.setEmpType(DataTypeUtils.getStringValue(dataMap.get("fcEmpType")));
//		attachCust.setEmpAdd(DataTypeUtils.getStringValue(dataMap.get("fcEmpAdd")));
//		attachCust.setEmpPhone(DataTypeUtils.getStringValue(dataMap.get("fcEmpPhone")));
//		attachCust.setEmpPostcode(DataTypeUtils.getStringValue(dataMap.get("fcEmpPostcode")));
//		attachCust.setCorpFax(DataTypeUtils.getStringValue(dataMap.get("fcCorpFax")));
//		attachCust.setEmpStandFrom(DataTypeUtils.getDateValue(dataMap.get("fcEmpStandFrom")));
//		attachCust.setEmpDepapment(DataTypeUtils.getStringValue(dataMap.get("fcEmpDepapment")));
//		attachCust.setOccupation(DataTypeUtils.getStringValue(dataMap.get("fcOccupation")));
//		attachCust.setTitleOfTechnical(DataTypeUtils.getStringValue(dataMap.get("fcTitleOfTechnical")));
//		attachCust.setEmpPost(DataTypeUtils.getStringValue(dataMap.get("fcEmpPost")));
//		attachCust.setPhotoUseFlag(DataTypeUtils.getStringValue(dataMap.get("fcPhotoUseFlag")));
//		attachCust.setPosPinVerifyInd(DataTypeUtils.getStringValue(dataMap.get("fcPosPinVerifyInd")));
//		attachCust.setEmpStatus(DataTypeUtils.getStringValue(dataMap.get("fcEmpStatus")));
//		attachCust.setEmpStability(DataTypeUtils.getStringValue(dataMap.get("fcEmpStability")));
//		attachCust.setHomeAddrCtryCd(DataTypeUtils.getStringValue(dataMap.get("fcHomeAddrCtryCd")));
//		attachCust.setHomeState(DataTypeUtils.getStringValue(dataMap.get("fcHomeState")));
//		attachCust.setHomeCity(DataTypeUtils.getStringValue(dataMap.get("fcHomeCity")));
//		attachCust.setHomeZone(DataTypeUtils.getStringValue(dataMap.get("fcHomeZone")));
//		attachCust.setHomePostcode(DataTypeUtils.getStringValue(dataMap.get("fcHomePostcode")));
//		attachCust.setHomeAdd(DataTypeUtils.getStringValue(dataMap.get("fcHomeAdd")));
//		attachCust.setHomePhone(DataTypeUtils.getStringValue(dataMap.get("fcHomePhone")));
//		attachCust.setHomePhoneDn(DataTypeUtils.getStringValue(dataMap.get("fcHomePhoneDn")));
//		attachCust.setFamilyAvgeVenue(DataTypeUtils.getBigDecimalValue(dataMap.get("fcFamilyAvgeVenue")));
//		attachCust.setOtherAsk(DataTypeUtils.getStringValue(dataMap.get("fcOtherAsk")));
//		attachCust.setOtherAnswer(DataTypeUtils.getStringValue(dataMap.get("fcOtherAnswer")));
//		attachCust.setStatus(null);//附卡拒绝标志
//		attachCust.setAppnoAttachExternal(DataTypeUtils.getStringValue(dataMap.get("appNo")));
//		attachCust.setBankCustomerId(DataTypeUtils.getStringValue(dataMap.get("fcBankCustomerId")));
//		attachCust.setIdLastAll(DataTypeUtils.getStringValue(dataMap.get("fcIdLastAll")));
//		attachCust.setRaiseNum(DataTypeUtils.getIntegerValue(dataMap.get("fcRaiseNum")));
//		attachCust.setJobGrade(DataTypeUtils.getStringValue(dataMap.get("fcJobGrade")));
//		attachCust.setOldCorpName(DataTypeUtils.getStringValue(dataMap.get("fcOldCorpName")));
//		attachCust.setOldCorpProveName(DataTypeUtils.getStringValue(dataMap.get("fcOldCorpProveName")));
//		attachCust.setOldCorpProveCnt(DataTypeUtils.getStringValue(dataMap.get("fcOldCorpProveCnt")));
//		attachCust.setSmAmtVerifyInd(DataTypeUtils
//				.getStringValue(dataMap.get("fcSmAmtVerifyInd"))==null?Indicator.Y.name():DataTypeUtils.getStringValue(dataMap.get("fcSmAmtVerifyInd")));
//		
//		
//		return attachCust;
//	}
//	
//	
//	
//	
//	/**
//	 * 查询上传记录 分页查询
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/list")
//	public Page<TmAppUpload> list(){
//		Page<TmAppUpload> page = getPage(TmAppUpload.class);
//		applyFileUploadService.getTmAppUploadPage(page);
//		for(TmAppUpload tmAppUpload : page.getRows()){
//			if(tmAppUpload.getStartBpmn().equals("0")){
//				tmAppUpload.setStartBpmn("上传成功");
//			} else {
//				tmAppUpload.setStartBpmn("上传失败");
//			}
//		}
//		return page;
//	}
//	
//	/**
//	 * 删除某条上传记录
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("/delete")
//	public Json delete(int id){
//		Json json = Json.newSuccess();	
//		applyFileUploadService.deleteTmAppUpload(id);
//		return json;
//	}
//	
//	/**
//	 * 下载某条上传记录
//	 * @return
//	 */
//	@RequestMapping("/download")
//	public void download(int id){
//		HttpServletResponse response = getResponse();
//		TmAppUpload tmAppUpload = applyFileUploadService.getTmAppUploadByKey(id);
//		if(tmAppUpload == null){
//			throw new ProcessException("没有找到下载的文件!");
//		}
//		response.setContentType("text/plain");
//		String fileName;
//		try {
//			fileName = URLEncoder.encode(tmAppUpload.getFileName().replace(".xls", ""), "UTF-8");
//			response.setHeader("Content-Disposition","attachment; filename=" + fileName + ".txt");  
//		} catch (UnsupportedEncodingException e1) {			
//			e1.printStackTrace();
//		}
//	    BufferedOutputStream buff = null;
//	    ServletOutputStream outSTr = null;
//	    try {
//	        outSTr = response.getOutputStream(); // 建立   
//	        buff = new BufferedOutputStream(outSTr); 
//	        //把内容写入文件 
//	        StringBuffer write = new StringBuffer();
//	        write.append(tmAppUpload.getOrg()+"||"); 
//	        write.append(tmAppUpload.getFileName()+"||");
//	        write.append(tmAppUpload.getLineNo()+"||");    
//	        write.append(tmAppUpload.getContent()+"||");
//	        write.append(tmAppUpload.getStartBpmn()+"||");
//	        write.append(tmAppUpload.getFailReason()+"||");
//	        write.append(tmAppUpload.getBatchDate());
//	        write.append("\r\n");
//
//	        buff.write(write.toString().getBytes("UTF-8"));
//	        buff.flush();
//	        buff.close();
//	      } catch (Exception e) {
//	       logger.error("下载文件记录异常", e);
//	      } finally {
//	    	  try {
//	    		  buff.close();
//			      outSTr.close();
//		      } catch (Exception e) {
//		    	  logger.error("下载文件记录关闭异常", e);
//		      }
//	      }
//	 }
}