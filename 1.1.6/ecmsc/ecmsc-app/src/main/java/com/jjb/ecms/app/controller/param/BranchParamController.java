package com.jjb.ecms.app.controller.param;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.web.controller.BaseController;

/** 
 * @description 网点参数配置
 * @author hn
 * @date 2016年11月9日10:27:37
 * modify by H.N 20171109
 * modify by hn 20171113
 */

@Controller
@RequestMapping("/branchParam")
public class BranchParamController extends BaseController{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
//	@Autowired
//	private BranchService branchService;
//	@Autowired
//	private CacheContext cacheContext;
//	@Autowired
//	private BranchCacheContext branchCacheContext;
//	/**
//	 * 获取核心bmp网点机构参数
//	 */
//	@Autowired
//	private UnifiedBranchFacility unifiedBranchFacility;
//	/**
//	 * 获取核心bmp参数
//	 */
//	@Autowired
//	private UnifiedParameterFacility parameterFacility;
	
	/**
	 * 同步核心(bmp)产品参数
	 * @return
	 */
	@RequestMapping("/syncBmpBranches")
	public String syncBmpBranches(){
		try {/*

			Long start = System.currentTimeMillis();
			String org = OrganizationContextHolder.getOrg();
			assert org != null;
			com.jjb.acl.gmp.sdk.OrganizationContextHolder.setCurrentOrg(org);
			List<BMPBranch> list = unifiedBranchFacility.getBranchAll(org);//分行网点
			Map<String, TmAclBranch> branchMap = branchCacheContext.initTmAclBranchMap();//这个地方不能用缓存，清空之后容易出问题
			logger.info("PID-["+start+"]...开始同步BMP核心网点机构参数,预计同步总数量"+(list==null ? 0:list.size())+"，操作人["+OrganizationContextHolder.getUserNo()+"]");
			if(CollectionUtils.sizeGtZero(list)){
				for (BMPBranch bmpBranch : list) {
					boolean isExit = true;
					TmAclBranch branch = cacheContext.getTmAclBranchByCode(bmpBranch.getBranchId());
					logger.info("PID-["+start+"]...开始同步网点机构["+bmpBranch.getBranchId()+"-"+bmpBranch.getBranchName()
							+"]相关参数，操作人["+OrganizationContextHolder.getUserNo()+"]");
					if(branch==null){
						logger.info("PID-["+start+"]...该网点机构["+bmpBranch.getBranchId()+"-"+bmpBranch.getBranchName()
								+"]在当前系统中不存在，操作人["+OrganizationContextHolder.getUserNo()+"]");
						branch = new TmAclBranch();
						isExit = false;
					}
					branch.setOrg(org);//机构号
					branch.setBranchCode(bmpBranch.getBranchId());//分行号
					branch.setBranchName(bmpBranch.getBranchName());//分行名称
					branch.setParentCode(bmpBranch.getParentMagBranch());//上级管理分行
					branch.setIssuerBranch(bmpBranch.getIssuerBranch());//发卡行
					branch.setParentPath(bmpBranch.getBranchParentPath());//上级分行路径
					branch.setBranchLevel(bmpBranch.getLevel());//分行级别
					branch.setDasBranchName(bmpBranch.getDasBranchName());//报表分行名称
					branch.setGlpBranch(bmpBranch.getGlpBranch());//核算分行
					branch.setDasCollectBranch(bmpBranch.getDasCollectBranch());//报表汇总分行
//					branch.setBranchSequence(bmpBranch.get);//分行顺序号
//					branch.setBranchDasInd(bmpBranch.get);//是否参与报表
					// 发卡网点校验
					BranchApp branchCredit = parameterFacility.retrieveParameterObject(bmpBranch.getBranchId(), BranchApp.class);
					if(StringUtils.isEmpty(branch.getBranchIssueInd()) 
							&& branchCredit!=null && branchCredit.branchIssueInd){
						branch.setBranchIssueInd("Y");//发卡权限标识
					}else{
						if(StringUtils.isEmpty(branch.getBranchIssueInd())){
							branch.setBranchIssueInd("N");//发卡权限标识
						}
					}
					if(StringUtils.isEmpty(branch.getCardCollectInd()) 
							&& branchCredit!=null && branchCredit.cardCollectInd){
						branch.setCardCollectInd("Y");//领卡分行标识
					}else{
						if(StringUtils.isEmpty(branch.getCardCollectInd())){
							branch.setCardCollectInd("N");//发卡权限标识
						}
					}
					branch.setCancelInd(bmpBranch.getCancelInd()==null? "N":bmpBranch.getCancelInd().name());//是否撤销
					branch.setEmpAdd(bmpBranch.getAdress());//地址
					branch.setZone(bmpBranch.getDistrict());//区
					branch.setZipCode(bmpBranch.getZip());//邮编
					branch.setCity(bmpBranch.getCity());//所在城市
					branch.setCountryCd(bmpBranch.getCountryCd());//所在国家代码
					branch.setPhone(bmpBranch.getPhone1());//联系电话1
					branch.setPhone2(bmpBranch.getPhone2());//联系电话2
					if(branch.getCreateDate()==null){
						branch.setCreateDate(new Date());//创建时间
					}
					if(StringUtils.isEmpty(branch.getCreateBy())){
						branch.setCreateBy(OrganizationContextHolder.getUserNo());//创建人
					}
					branch.setUpdateDate(new Date());//最后更新时间
					branch.setUpdateBy(OrganizationContextHolder.getUserNo());//最后修改人
//					branch.setRemark(bmpBranch.get);//备注
					if(branchMap!=null && branchMap.containsKey(branch.getBranchCode())){
						branchMap.remove(branch.getBranchCode());
					}
					if(isExit){
						branchService.editTmAclBranch(branch);
					}else{
						branchService.saveTmAclBranch(branch);
					}
				}
				if(branchMap!=null && branchMap.size()>0){
					for (Object key : branchMap.keySet()) {
						if(key!=null && StringUtils.isNotEmpty(key.toString())){
							logger.info("PID-["+start+"]...因核心网点机构参数["+key+"]中不存在当前系统中设置的网点，故做删除!操作人["+OrganizationContextHolder.getUserNo()+"]");
							branchService.deleteTmAclBranchByBranchCode(key.toString());
						}
						
					}
				}
			}
			branchCacheContext.refresh();
			logger.info("PID-["+start+"]...结束同步BMP核心网点机构参数，操作人["+OrganizationContextHolder.getUserNo()+"]，耗时("+(System.currentTimeMillis()-start)+")");
		
		*/} catch (Exception e) {
			logger.error("同步BMP核心网点参数异常", e);
			throw new ProcessException("同步BMP核心网点参数异常,错误信息:"+e.getMessage());
		}
		return "acl/branch/bankBranchManagePage_V1.ftl";
	}
}
