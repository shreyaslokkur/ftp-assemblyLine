package com.lks.core.model;

import com.lks.orm.entities.UserRole;

/**
 * Created by lokkur on 8/10/2015.
 */
public class UserModelDO extends AbstractDO {

    private String username;
    private String password;
    private String branchName;
    private String userRole;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
