package com.jjb.ecms.biz.dao.apply.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.jjb.ecms.biz.dao.apply.TmAppFlagDao;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.infrastructure.TmAppFlag;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @ClassName TmAppFlagDaoImpl
 * Company jydata-tech
 * Author wxl
 * Date Date 2019/10/15 15:32
 * Version 1.0
 */
@Repository("tmAppFlagDao")
public class TmAppFlagDaoImpl extends AbstractBaseDao<TmAppFlag> implements TmAppFlagDao {

    /**
     * 保存申请件标签
     *
     * @param tmAppFlag 对应类
     */
    @Override
    public void saveTmAppFlag(TmAppFlag tmAppFlag) {
    	if(tmAppFlag!=null) {
    		tmAppFlag.setId(null);
            tmAppFlag.setCreateDate(new Date());
            if(StringUtils.isEmpty(tmAppFlag.getCreateUser())){
            	String user = OrganizationContextHolder.getUserNo();
            	if(StringUtils.isEmpty(user)) {
            		user = AppConstant.SYS_AUTO;
            	}
            	tmAppFlag.setCreateUser(user);
            }
            tmAppFlag.setJpaVersion(0);
            tmAppFlag.setFlagStatus("A");//有效
    		save(tmAppFlag);
    	}
    }

    /**
     * 按AppNo 查找申请件标签
     *
     * @param appNo 申请件编号
     * @return 该AppNo 下的所有标签
     */
    @Override
	public List<TmAppFlag> getTmAppFlagListByAppNo(String appNo) {
		if (StringUtils.isNotEmpty(appNo)) {
			TmAppFlag tmAppFlag = new TmAppFlag();
			tmAppFlag.setFlagStatus("A");//查询"有效"的标签
			tmAppFlag.setAppNo(appNo);
			return queryForList(tmAppFlag);
		}else {
			throw new ProcessException("申请件编号不能为空!");
		}
	}

    /**
     * 删除申请件标签
     *
     * @param tmAppFlag
     */
    @Override
    public void deleteTmAppFlag(TmAppFlag tmAppFlag) {
    	if(tmAppFlag!=null && tmAppFlag.getId()!=null) {
    		deleteByKey(tmAppFlag);
    	}
    }
    /**
     * 更新申请件标签
     * @param TmAppFlag
     * @return
     */
    @Override
    public void updateTmAppFlag(TmAppFlag tmAppFlag) {
    	if(tmAppFlag!=null) {
    		tmAppFlag.setUpdateDate(new Date());
    		if(StringUtils.isEmpty(tmAppFlag.getUpdateUser())) {
    			String user = OrganizationContextHolder.getUserNo();
            	if(StringUtils.isEmpty(user)) {
            		user = AppConstant.SYS_AUTO;
            	}
            	tmAppFlag.setCreateUser(user);
    		}
    		update(tmAppFlag);
    	}
    }
}
