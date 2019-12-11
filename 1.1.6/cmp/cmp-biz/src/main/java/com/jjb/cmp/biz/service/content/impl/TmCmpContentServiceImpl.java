package com.jjb.cmp.biz.service.content.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.cmp.biz.cache.controller.CmpCacheContext;
import com.jjb.cmp.biz.dao.TmCmpContentDao;
import com.jjb.cmp.biz.dao.TmCmpContentDtoDao;
import com.jjb.cmp.biz.service.content.TmCmpContentService;
import com.jjb.cmp.biz.service.content.TmCmpMainService;
import com.jjb.cmp.dto.TmCmpContentDto;
import com.jjb.cmp.infrastructure.TmCmpContent;
import com.jjb.cmp.infrastructure.TmCmpMain;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @ClassName ImageContentServiceImpl
 * @Description TODO
 * @Author smh Date 2018/12/31 11:57 Version 1.0
 */
@Service("tmCmpContentService")
public class TmCmpContentServiceImpl implements TmCmpContentService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TmCmpContentDao tmCmpContentDao;
    @Autowired
    private TmCmpContentDtoDao tmCmpContentDtoDao;
    @Autowired
    private TmCmpMainService tmCmpMainService;
    @Autowired
    private CmpCacheContext cmpCacheContext;

    @Override
    public Page<TmCmpContentDto> queryImageList(Page<TmCmpContentDto> page) {
        page = tmCmpContentDtoDao.queryImageList(page);
        return page;
    }

    /**
     * 根据条件查询内容清单
     *
     * @param contentDto
     * @return
     */
    @Override
    public List<TmCmpContentDto> quyContentByParam(TmCmpContentDto contentDto) {
        return tmCmpContentDtoDao.quyContentByParam(contentDto);
    }

    @Override
    public List<TmCmpContent> getTmCmpContentByBatchNo(String batchNo) {
        return tmCmpContentDao.getTmCmpContentByBatchNo(batchNo);
    }

    @Override
    public TmCmpContent getTmCmpContent(TmCmpContent tmCmpContent) {
        return tmCmpContentDao.getTmCmpContent(tmCmpContent);
    }

    @Override
    public void updateTmCmpContent(TmCmpContent tmCmpContent) {
        tmCmpContentDao.updateTmCmpContent(tmCmpContent);
    }

    @Override
    public void saveTmCmpContent(TmCmpContent tmCmpContent) {
        tmCmpContentDao.saveTmCmpContent(tmCmpContent);
    }

    @Override
    public List<TmCmpContent> getTmCmpContents(TmCmpContent tmCmpContent) {
        return tmCmpContentDao.getTmCmpContents(tmCmpContent);
    }



    /**
     * 保存一个新的内容信息至数据库
     *
     * @param batchNo      批次号
     * @param TmCmpContent 单条内容信息
     * @return
     */
    @Transactional
    public boolean saveNewContentInfo(String batchNo, TmCmpContent tmCmpContent) {
        try {
            if (tmCmpContent == null) {
                throw new ProcessException("内容数据为空，请确认批次号[" + batchNo + "]上传有效内容信息");
            }
            // 更新内容管理主表信息
            TmCmpMain cmpMain = tmCmpMainService.getTmCmpMainByBatchNo(batchNo);
            if (cmpMain == null) {
                throw new ProcessException("根据批次号[" + batchNo + "]系统未查询到有效内容主体信息，请核实相关批次信息");
            }
            cmpMain.setUpdateUser(StringUtils.setValue(tmCmpContent.getUpdateUser(), OrganizationContextHolder.getUserNo()));
            cmpMain.setUpdateDate(new Date());
            tmCmpMainService.updateTmCmpMain(cmpMain);
            // 更新内容清单
            String supTypeDesc = "";
            String subTypeDesc = "";
            String supType = tmCmpContent.getSupType();
            TmAclDict supDict = cmpCacheContext.getAclDictByCode("fileBigType", supType);
            if (supDict != null) {
                supTypeDesc = supDict.getCodeName();
            } else {
                supTypeDesc = supType;
            }
            String subType = tmCmpContent.getSubType();
            TmAclDict subDict = cmpCacheContext.getAclDictByCode("ApplyPatchBoltType", subType);
            if (subDict != null) {
                subTypeDesc = subDict.getCodeName();
            } else {
                subTypeDesc = subType;
            }
            if (StringUtils.isEmpty(supTypeDesc) || StringUtils.isEmpty(subTypeDesc)) {
                throw new ProcessException("未获取到有效的参数");
            }
            tmCmpContent.setBatchNo(batchNo);
            tmCmpContent.setSupType(supType);
            tmCmpContent.setSupTypeDesc(supTypeDesc);
            tmCmpContent.setSubType(subType);
            tmCmpContent.setSubTypeDesc(subTypeDesc);
//			tmCmpContent.setContRelPath("http://10.109.3.205:80/");//采用动态ip，不写死
            tmCmpContent.setContStatus("A");

            tmCmpContent.setUpdateDate(new Date());
            saveTmCmpContent(tmCmpContent);
        } catch (Exception e) {
            logger.error("内容信息入库失败", e);
            return false;
        }
        return true;
    }

    /**
     *wxl
     * 删除fastdfs地址对应的一行记录
     * 删除TM_CMP_CONTENT表里的fastdfs地址;cont abs path
     */
    @Override
    public void deleteFile(TmCmpContent tm) {
        int i = tmCmpContentDao.deleteByKey(tm);
        if (i == 0) {
            throw new ProcessException("删除图片地址失败 ！！！");
        }
    }

	@Override
	public TmCmpContent queryById(Integer id) {
		// TODO Auto-generated method stub
		return this.tmCmpContentDao.selectByPrimaryKey(id);
	}
}
