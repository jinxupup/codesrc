package com.jjb.cas.quartz;

import com.jjb.ecms.biz.service.apply.TmMirCardBatchService;
import com.jjb.ecms.biz.service.apply.TmMirCardService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.infrastructure.TmEcmsBatch;
import com.jjb.ecms.infrastructure.TmMirCard;
import com.jjb.ecms.infrastructure.TmMirCardBatch;
import com.jjb.ecms.infrastructure.TmMirCardExce;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * @author JYData-R&D-HN
 * @version V1.0
 * @Description: 卡数据批量存入审批系统定时任务
 * @date 2016年9月23日 下午3:56:12
 */
@Component
public class AutoSaveMirCardQuartz extends BaseController implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
    private TmMirCardBatchService tmMirCardBatchService;
    @Autowired
    private TmMirCardService tmMirCardService;
    @Autowired
    private AppCommonUtil appCommonUtil;


    public void mirCardMessageSave() throws Exception {
        appCommonUtil.setOrg(OrganizationContextHolder.getOrg());
        String org = OrganizationContextHolder.getOrg();
        List<TmEcmsBatch> list = tmMirCardBatchService.getTmEcmsBatchList();
        if(list != null && list.size()>0) {
            TmEcmsBatch tmEcmsBatch = list.get(list.size()-1);
            if (StringUtils.isNotBlank(tmEcmsBatch.getIfWrittenTn()) || StringUtils.equals(tmEcmsBatch.getIfWrittenTn(), "Y")) {
                Page<TmMirCardBatch> page = new Page<TmMirCardBatch>();
                int pageSize = 10;
                page.setPageSize(pageSize);
//                List<TmMirCardBatch> totalList = tmMirCardBatchService.getTmMirCardBatchList();
//                int listSize = totalList.size();
//                int totalPages = listSize / pageSize;
//                if (listSize % pageSize != 0) {
//                    totalPages += 1;
//                    if (listSize < pageSize) {
//                        pageSize = listSize;
//                    }
//                }
//                page.setPageSize(pageSize);
                //得到数据总页数
                page = tmMirCardBatchService.getTmMirCardBatchListPage(page);
            	long totalPages = page.getTotal() / pageSize;
            	if(page.getTotal() % pageSize !=0){
                    totalPages = totalPages+1;
                }
                	//比较总页数与当前页数大小，判断是否已完成所有数据查询
					for (long i = 0; i < totalPages; i++) {
                        page.setPageNumber((int)i+1);
                        page = tmMirCardBatchService.getTmMirCardBatchListPage(page);
                        if(page==null || page.getRows()==null || page.getRows().size()==0) {
                            logger.info("系统未检索到需要处理的卡数据");
                            return;
                        }
                        List<TmMirCardBatch> cardList = page.getRows();
                        if (StringUtils.isNotEmpty(cardList)) {
                            for (TmMirCardBatch tmMirCardBatch : cardList) {
                                TmMirCard dbCard = null;
                                try {
                                    if (tmMirCardBatch != null && StringUtils.isNotEmpty(tmMirCardBatch.getCardNo())) {
                                        dbCard = tmMirCardService.getTmMirCardByCardNo(tmMirCardBatch.getCardNo());
                                        boolean isExist = true;//已存在
                                        if (dbCard == null) {
                                            dbCard = new TmMirCard();
                                            isExist = false;
                                        }
                                        dbCard.setOrg(StringUtils.setValue(dbCard.getOrg(), org));
                                        dbCard.setCardNo(tmMirCardBatch.getCardNo());
                                        dbCard.setLatestCardNo(StringUtils.setValue(tmMirCardBatch.getLatestCardNo(), dbCard.getLatestCardNo()));
                                        dbCard.setCustId(StringUtils.setValue(tmMirCardBatch.getCustId(), dbCard.getCustId()));
                                        dbCard.setProductCd(StringUtils.setValue(tmMirCardBatch.getProductCd(), dbCard.getProductCd()));
                                        dbCard.setBscSuppInd(StringUtils.setValue(tmMirCardBatch.getBscSuppInd(), dbCard.getBscSuppInd()));
                                        dbCard.setOwningBranch(StringUtils.setValue(tmMirCardBatch.getOwningBranch(), tmMirCardBatch.getOwningBranch()));
                                        dbCard.setAppNo(StringUtils.setValue(tmMirCardBatch.getAppNo(), dbCard.getOwningBranch()));
                                        dbCard.setBlockCode(tmMirCardBatch.getBlockCode());
                                        dbCard.setSetupDate(tmMirCardBatch.getSetupDate() == null ? dbCard.getSetupDate() : tmMirCardBatch.getSetupDate());
                                        dbCard.setName(StringUtils.setValue(tmMirCardBatch.getName(), tmMirCardBatch.getName()));
                                        dbCard.setCardExpireDate(tmMirCardBatch.getCardExpireDate());
                                        dbCard.setIdNo(StringUtils.setValue(tmMirCardBatch.getIdNo(), dbCard.getIdNo()));
                                        dbCard.setIdType(StringUtils.setValue(tmMirCardBatch.getIdType(), "I"));
                                        dbCard.setActivateDate(tmMirCardBatch.getActivateDate() == null ? dbCard.getActivateDate() : tmMirCardBatch.getActivateDate());
                                        dbCard.setOverdueNumber(tmMirCardBatch.getOverdueNumber());
                                        if (tmMirCardBatch.getOverdueNumber() != null && tmMirCardBatch.getOverdueNumber() != "") {
                                            dbCard.setOverdueInd("N");
                                        } else {
                                            dbCard.setOverdueInd("Y");
                                        }
                                        if (isExist) {
                                            tmMirCardService.updateTmMirCard(dbCard);
                                        } else {
                                            tmMirCardService.saveTmMirCard(dbCard);
                                        }
                                    }
                                } catch (Exception e) {
                                    if (dbCard != null && StringUtils.isNotEmpty(dbCard.getAppNo())) {
                                        TmMirCardExce tmExcMirCardError = new TmMirCardExce();
                                        tmExcMirCardError.setAppNo(dbCard.getAppNo());
                                        tmExcMirCardError.setCreatDate(new Date());
                                        tmExcMirCardError.setIdNo(dbCard.getIdNo());
                                        tmExcMirCardError.setName(dbCard.getName());
                                        tmExcMirCardError.setRemark(e.getMessage());
                                        tmMirCardBatchService.saveTmExcMirCardError(tmExcMirCardError);
                                    }
                                }
                            }
                        }
                    }
                tmEcmsBatch.setIfProcessed("Y");
                tmEcmsBatch.setIfWrittenTn(null);
                tmMirCardBatchService.updateTmEcmsBatch(tmEcmsBatch);
            }
        }
    }

}