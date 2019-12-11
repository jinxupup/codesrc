package com.jjb.ecms.biz.service.query;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jjb.acl.infrastructure.TmAclBranch;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 支持申请进度查询工具类
 * @Description:
 * @author JYData-R&D-HN
 * @date 2017年12月20日 下午2:51:48
 * @version V1.0
 */
@Component
public class ApplyProcessUtils {

    @Autowired
    private CacheContext cacheContext;
    /**
     * 设置查询网点清单
     * @param <T>
     * @param page
     * @param branchCode
     */
    public <T> void setBranchToPage(Page<T> page, String branchCode) {

        /**
         * 根据branchCode从(全行)机构网点中查询单个TmAclBranch实体数据
         * @return
         */
        TmAclBranch tmAclBranch = cacheContext.getTmAclBranchByCode(branchCode);


        if(tmAclBranch==null){
            throw new ProcessException("机构["+branchCode+"]在系统中不存在!");
        }

        if (!StringUtils.equals(tmAclBranch.getParentCode(),"") && !StringUtils.equals(tmAclBranch.getBranchLevel(),"1")) {
            //获取下级网点
            LinkedHashMap<Object,Object> map = cacheContext.getSubBranchByBranchOrUser(branchCode, null);
            List<String> branchList = new ArrayList<String>();
            Set<Object> key = map.keySet();
/*			if(map!=null && map.size()>0){
				for(Object enty : map.keySet()){
					if(enty!=null && StringUtils.isNotEmpty(enty.toString())){
						branchList.add(enty.toString());
					}
				}
			}*/
            if(key != null && !key.isEmpty()){
/*
				page.getQuery().put("owningBranch", branchList.toArray(new String[0]));
*/
                page.getQuery().put("owningBranch",key.toArray(new String[0]));
            }
        } else {
            page.getQuery().remove("owningBranch");
        }
    }

    public <T> void ifSelectOwningBranch(Page<T> page){
        //如果选择了受理网点
        if(page.getQuery().get("owningBranch") != null && StringUtils.isNotBlank(page.getQuery().get("owningBranch").toString())){
            String owningBranch = page.getQuery().get("owningBranch").toString();//获取选择的受理网点
            setBranchToPage(page, owningBranch);

        }else{//说明默认的网点过滤
            //获取当前用户机构
            String branchCode = OrganizationContextHolder.getBranchCode();
            if(StringUtils.isNotEmpty(branchCode)){
                setBranchToPage(page, branchCode);
            }
        }
    }


    /**
     * 设置查询网点清单
     * @param <T>
     * @param
     * @param branchCode
     */
    public <T> void setBranchToPage(Map<String, Object> params, String branchCode) {
        TmAclBranch tmAclBranch = cacheContext.getTmAclBranchByCode(branchCode);
        if(tmAclBranch==null){
            throw new ProcessException("机构["+branchCode+"]在系统中不存在!");
        }
        if (tmAclBranch.getParentCode()!=null) {
            //获取下级网点
            LinkedHashMap<Object,Object> map = cacheContext.getSubBranchByBranchOrUser(branchCode, null);
            List<String> branchList = new ArrayList<String>();
            if(map!=null && map.size()>0){
                for(Object enty : map.keySet()){
                    if(enty!=null && StringUtils.isNotEmpty(enty.toString())){
                        branchList.add(enty.toString());
                    }
                }
            }
            if(branchList != null && branchList.size() > 0){
                params.put("owningBranch", branchList.toArray(new String[0]));
            }
        } else {
            params.remove("owningBranch");
        }
    }

    public <T> void ifSelectOwningBranch(Map<String, Object> params){
        //如果选择了受理网点
        if(params.get("owningBranch") != null && StringUtils.isNotBlank(params.get("owningBranch").toString())){
            String owningBranch = params.get("owningBranch").toString();//获取选择的受理网点
            setBranchToPage(params, owningBranch);

        }else{//说明默认的网点过滤
            //获取当前用户机构
            String branchCode = OrganizationContextHolder.getBranchCode();
            if(StringUtils.isNotEmpty(branchCode)){
                setBranchToPage(params, branchCode);
            }
        }
    }


}
