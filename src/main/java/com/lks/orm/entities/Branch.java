package com.lks.orm.entities;

public class Branch implements IEntity {

	private int branchCode;
	private String branchName;
	private String zone;
	private String region;
	private boolean enabled;


	public Branch(int branchCode, String branchName, String zone, String region, boolean enabled) {
		this.branchCode = branchCode;
		this.branchName = branchName;
		this.zone = zone;
		this.region = region;
		this.enabled = enabled;
	}

	public int getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(int branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
