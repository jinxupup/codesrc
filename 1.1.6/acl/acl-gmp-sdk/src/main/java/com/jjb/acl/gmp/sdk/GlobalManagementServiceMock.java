package com.jjb.acl.gmp.sdk;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jjb.acl.gmp.api.GlobalManagementService;
import com.jjb.acl.gmp.api.OrgInstanceInfo;
import com.jjb.acl.gmp.api.SystemStatus;
import com.jjb.unicorn.facility.exception.ProcessException;


public class GlobalManagementServiceMock implements GlobalManagementService
{
	private SystemStatus status = new SystemStatus();

	@Override
	public Map<String, String> getOrgList() {
		return null;
	}

	@Override
	public SystemStatus getSystemStatus() {
		return status;
	}
	
	public void setupBatchDate(Date processDate, Date lastProcessDate)
	{
		status.setProcessDate(processDate);
		status.setLastProcessDate(lastProcessDate);
	}

	@Override
	public Map<String, Map<String, String>> getInstanceRoute() {
		return null;
	}

	@Override
	public List<String> getServeOrg(String system, String instanceName) {
		return null;
	}

	@Override
	public List<OrgInstanceInfo> getOrgIntanceInfo(String orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInstance(String system, String org) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startBatch(String jobName, Map<String, String> param)
			throws ProcessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long startIndieBatchByOrg(String org, String jobName, LinkedHashMap<String, String> jobParam) throws ProcessException {
		// TODO Auto-generated method stub
		return null;
	}

}
