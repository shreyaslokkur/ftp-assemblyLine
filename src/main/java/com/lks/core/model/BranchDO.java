package com.lks.core.model;

/**
 * Created by lokkur on 8/10/2015.
 */
public class BranchDO extends AbstractDO {

    private String branchName;
    private String branchAddress;

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
}
