package com.jjb.acl.facility.model;

import com.jjb.acl.infrastructure.TmAclResource;
import com.jjb.unicorn.facility.kits.StrKit;
import com.jjb.unicorn.facility.model.TreeFace;

/**
 * 资源TreeFace
 * @author BIG.D.W.K
 *
 */
public class ResourceTreeFace extends TreeFace<TmAclResource>{

	@Override
	public String getId(TmAclResource t) {
		return t.getResourceCode();
	}

	@Override
	public String getName(TmAclResource t) {
		return t.getResourceName();
	}

	@Override
	public String getPId(TmAclResource t) {
		return t.getParentResourceCode();
	}

	@Override
	public boolean isRoot(TmAclResource t) {
		if(StrKit.isBlank(t.getParentResourceCode())){
			return true;
		}
		return false;
	}

}
