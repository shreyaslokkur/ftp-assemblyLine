package com.lks.orm.entities;

public class Branch implements IEntity {

	private String branchName;
	private String branchAddress;
	private boolean enabled;

	public Branch(String branchName, String branchAddress, boolean enabled) {
		this.branchName = branchName;
		this.branchAddress = branchAddress;
		this.enabled = enabled;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
