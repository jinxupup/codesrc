/**
 * 
 */
package com.jjb.ecms.biz.dao.query.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jjb.acl.access.service.AccessResourceService;
import com.jjb.acl.facility.enums.bus.EcmsAuthority;
import com.jjb.acl.infrastructure.TmAclResource;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.query.ApplyProcessQueryDao;
import com.jjb.ecms.facility.dto.ApplyProcessQueryDto;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;

/**
 * @Description: TODO
 * @author JYData-R&D-BigK.K
 * @date 2016年9月18日 下午3:27:49
 * @version V1.0  
 */

@Repository("applyProcessQueryDao")
public class ApplyProcessQueryDaoImpl extends AbstractBaseDao<ApplyProcessQueryDto> implements ApplyProcessQueryDao {
	
	public static final String  selectMain = "com.jjb.ecms.biz.ApplyProcessQuery.selectMain";//查询独立主卡或主附同申申请进度
	public static final String  selectApplyByState = "com.jjb.ecms.biz.ApplyProcessQuery.selectApplyByState";//根据状态类型查询进件信息
	
	@Autowired
	private AccessResourceService accessResourceService;
	@Autowired
	private CacheContext cacheContext;
	/*申请进度查询
	 */
	@Override
	public Page<ApplyProcessQueryDto> applyProcessList(
			Page<ApplyProcessQueryDto> page) {
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
	
		
		Page<ApplyProcessQueryDto> mainPage= queryForPageList(selectMain, page.getQuery(), page);
		return mainPage;
	}
	
	@Override
	public List<ApplyProcessQueryDto> applyProcessList(Map<String, Object> param) {
		if(param==null || param.isEmpty()){
			throw new ProcessException("请输入查询条件");
		}
		// TODO Auto-generated method stub
		return super.queryForList(selectMain, param);
	}

	/**
	 * 通过roleId获取到可以查看的任务名(TASK_DEF_KEY_)
	 * @param roleId
	 * @return
	 */
	public String[] getTaskDefKeys(int roleId){
		List<TmAclResource> resourceList = accessResourceService.getResourceCodes(roleId);//获取到该角色的权限
/*
		List<String> taskNames = AppCommonUtil.initNodeAuthList();
*/
		List<String>  taskNames=new ArrayList<>();
		//存放节点操作权限
		taskNames = cacheContext.getAuthBySystemType("initNodeAuthList");
		List<String> taskDefKeyList = new ArrayList<String>();//存放TASK_DEF_KEY_
		for(TmAclResource enty : resourceList){
			if(taskNames.contains(enty.getResourceCode())){//说明在这个任务名list里面
				taskDefKeyList.add(EcmsAuthority.valueOf(enty.getResourceCode()).lab);//和task表中的TASK_DEF_KEY_一样
			}
		}
		
		return taskDefKeyList.toArray(new String[0]);
	}
	/**
	 * 根据状态类型查询申请件
	 */
	@Override
	public List<ApplyProcessQueryDto> getApplyByRtfStateType(Map<String, Object> param) {
		if(param==null || param.isEmpty()){
			throw new ProcessException("请输入有效的状态类型");
		}
		return super.queryForList(selectApplyByState, param);
	}
}
