package com.jjb.acl.gmp.api;

public class BranchInfo {

	/**
	 * 机构号
	 */
	private String branchId;

	/**
	 * 机构名称
	 */
	private String branchName;

	/**
	 * 上级管理机构
	 */
	private String parentMagBranch;

	/**
	 * 机构等级1-5
	 */
	private String level;
	
	/**
	 * 机构路径
	 */
	private String branchPath;

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getParentMagBranch() {
		return parentMagBranch;
	}

	public void setParentMagBranch(String parentMagBranch) {
		this.parentMagBranch = parentMagBranch;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getBranchPath() {
		return branchPath;
	}

	public void setBranchPath(String branchPath) {
		this.branchPath = branchPath;
	}
	
}
